/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.especiesforestales.managedBeans;

import ar.gob.ambiente.servicios.especiesforestales.entidades.AdminEntidad;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Familia;
import ar.gob.ambiente.servicios.especiesforestales.entidades.util.JsfUtil;
import ar.gob.ambiente.servicios.especiesforestales.facades.FamiliaFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;


/**
 *
 * @author carmendariz
 */
public class MbFamilia implements Serializable{
    
    private Familia current;
    private DataModel items = null;
    
    @EJB
    private FamiliaFacade familiaFacade;
    //private PaginationHelper pagination;
    private int selectedItemIndex;
    private String selectParam;    
    private List<String> listaNombres;    

    /**
     * Creates a new instance of MbFamilia
     */
    public MbFamilia() {
        
    }
    
    public Familia getCurrent() {
        return current;
    }

    public void setCurrent(Familia current) {
        this.current = current;
    }

    /********************************
     ** Métodos para la navegación **
     ********************************/
    /**
     * @return La entidad gestionada
     */
    public Familia getSelected() {
        if (current == null) {
            current = new Familia();
            selectedItemIndex = -1;
        }
        return current;
    }   
    
    /**
     * @return el listado de entidades a mostrar en el list
     */
    public DataModel getItems() {
        if (items == null) {
            //items = getPagination().createPageDataModel();
            items = new ListDataModel(getFacade().findAll());
        }
        return items;
    }

  
    /*******************************
     ** Métodos de inicialización **
     *******************************/
    /**
     * @return acción para el listado de entidades
     */
    public String prepareList() {
        recreateModel();
        return "list";
    }
    
    public String iniciarList(){
        String redirect = "";
        if(selectParam != null){
            redirect = "list";
        }else{
            redirect = "administracion/familia/list";
        }
        recreateModel();
        return redirect;
    }

    /**
     * @return acción para el detalle de la entidad
     */
    public String prepareView() {
        return "view";
    }

    /** (Probablemente haya que embeberlo con el listado para una misma vista)
     * @return acción para el formulario de nuevo
     */
    public String prepareCreate() {
        current = new Familia();
        //selectedItemIndex = -1;
        return "new";
    }

    /**
     * @return acción para la edición de la entidad
     */
    public String prepareEdit() {
        return "edit";
    }
    
    public String prepareInicio(){
        recreateModel();
        return "/faces/index";
    }
    
    /**
     * Método para preparar la búsqueda
     * @return la ruta a la vista que muestra los resultados de la consulta en forma de listado
     */
    public String prepareSelect(){
        //items = null;
        //buscarFamilia();
        return "list";
    }

    /**
     * Método que verifica que el Tipo de Capacitación que se quiere eliminar no esté siento utilizado por otra entidad
     * @return 
     */
    public String prepareDestroy(){
        //current = (Familia) getItems().getRowData();
        boolean libre = getFacade().tieneDependencias(current.getId());

        if (libre){
            // Elimina
            //selectedItemIndex = getItems().getRowIndex();
            destroy();
            recreateModel();
        }else{
            //No Elimina 
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("FamiliaNonDeletable"));
        }
        return "view";
    }
    
    /**
     * Método para validar que no exista ya una entidad con este nombre al momento de crearla
     * @param arg0: vista jsf que llama al validador
     * @param arg1: objeto de la vista que hace el llamado
     * @param arg2: contenido del campo de texto a validar 
     */
    public void validarInsert(FacesContext arg0, UIComponent arg1, Object arg2){
        validarExistente(arg2);
    }
    
    /**
     * Método para validar que no exista una entidad con este nombre, siempre que dicho nombre no sea el que tenía originalmente
     * @param arg0: vista jsf que llama al validador
     * @param arg1: objeto de la vista que hace el llamado
     * @param arg2: contenido del campo de texto a validar 
     * @throws ValidatorException 
     */
    public void validarUpdate(FacesContext arg0, UIComponent arg1, Object arg2){
        if(!current.getNombre().equals((String)arg2)){
            validarExistente(arg2);
        }
    }
    
    private void validarExistente(Object arg2) throws ValidatorException{
        if(!getFacade().existe((String)arg2)){
            throw new ValidatorException(new FacesMessage(ResourceBundle.getBundle("/Bundle").getString("CreateFamiliaExistente")));
        }
    }
    
    /**
     * Restea la entidad
     */
    private void recreateModel() {
        items = null;
        if(selectParam != null){
            selectParam = null;
        }
    }

    /*************************
    ** Métodos de operación **
    **************************/
    /**
     * @return 
     */
    public String create() {
        // Creación de la entidad de administración y asignación
        Date date = new Date(System.currentTimeMillis());
        AdminEntidad admEnt = new AdminEntidad();
        admEnt.setFechaAlta(date);
        admEnt.setHabilitado(true);
        admEnt.setUsAlta(1);
        current.setAdmin(admEnt);        
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FamiliaCreated"));
            return "view";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("FamiliaCreatedErrorOccured"));
            return null;
        }
    }

    /**
     * @return mensaje que notifica la actualización
     */
    public String update() {
        Date date = new Date(System.currentTimeMillis());
        AdminEntidad admEnt = new AdminEntidad();
        admEnt.setFechaModif(date);
        //admEnt.setHabilitado(true);
        admEnt.setUsModif(2);
        current.setAdmin(admEnt);
        
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FamiliaUpdated"));
            return "view";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("FamiliaUpdatedErrorOccured"));
            return null;
        }
    }

    /**
     * @return mensaje que notifica el borrado
     */    
    public String destroy() {
        
        //current = (Familia) getItems().getRowData();
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        //selectedItemIndex = getItems().getRowIndex();
        current.getAdmin().setHabilitado(false);
        //performDestroy();
        update();    
        //recreatePagination();
        recreateModel();
        return "view";
    }

    /**
     * @return mensaje que notifica la inserción
     */
    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "view";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "list";
        }
    }    
    
  
    
    /*************************
    ** Métodos de selección **
    **************************/
    /**
     * @return la totalidad de las entidades persistidas formateadas
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(familiaFacade.findAll(), false);
    }

    /**
     * @return de a una las entidades persistidas formateadas
     */
    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(familiaFacade.findAll(), true);
    }

    /**
     * @param id equivalente al id de la entidad persistida
     * @return la entidad correspondiente
     */
    public Familia getFamilia(java.lang.Long id) {
        return familiaFacade.find(id);
    }    
    
    /*********************
    ** Métodos privados **
    **********************/
    /**
     * @return el Facade
     */
    private FamiliaFacade getFacade() {
        return familiaFacade;
    }
    
    /**
     * Opera el borrado de la entidad
     */
    private void performDestroy() {
        try {
            //getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FamiliaDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("FamiliaDeletedErrorOccured"));
        }
    }

    /**
     * Actualiza el detalle de la entidad si la última se eliminó
     */
    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            /*
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
            */
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }
    
    
    /*
     * Métodos de búsqueda
     */
    public String getSelectParam() {
        return selectParam;
    }

    public void setSelectParam(String selectParam) {
        this.selectParam = selectParam;
    }
    
   /* private void buscarFamilia(){
        items = new ListDataModel(getFacade().getXString(selectParam)); 
    }   */
    
    /**
     * Método para llegar la lista para el autocompletado de la búsqueda de nombres
     * @param query
     * @return 
     *//*
    public List<String> completeNombres(String query){
        listaNombres = getFacade().getNombres();
        List<String> nombres = new ArrayList();
        Iterator itLista = listaNombres.listIterator();
        while(itLista.hasNext()){
            String nom = (String)itLista.next();
            if(nom.contains(query)){
                nombres.add(nom);
            }
        }
        return nombres;
    }
        */
    
    /********************************************************************
    ** Converter. Se debe actualizar la entidad y el facade respectivo **
    *********************************************************************/
@FacesConverter(forClass = Familia.class)
public static class FamiliaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MbFamilia controller = (MbFamilia) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mbFamilia");
            return controller.getFamilia(getKey(value));
        }

        
        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }
        
        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Familia) {
                Familia o = (Familia) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Familia.class.getName());
            }
        }
    }        
}
