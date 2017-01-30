/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.gob.ambiente.servicios.especiesforestales.managedBeans;

import ar.gob.ambiente.servicios.especiesforestales.entidades.AdminEntidad;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Familia;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Genero;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Usuario;
import ar.gob.ambiente.servicios.especiesforestales.entidades.util.JsfUtil;
import ar.gob.ambiente.servicios.especiesforestales.facades.FamiliaFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.GeneroFacade;
import java.io.Serializable;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;




/**
 *
 * @author carmendariz
 */
public class MbGenero implements Serializable{
    
    
    private Genero current;
    private DataModel items = null;
    private List<Genero> listaFilter;

    @EJB
    private FamiliaFacade familiaFacade;
    
    @EJB
    private GeneroFacade generoFacade;
    
    //private int selectedItemIndex;
    //private String selectParam;    
    //private List<String> listaNombres;  
    private List<Familia> listaFamilia;
    private int update; // 0=updateNormal | 1=deshabiliar | 2=habilitar
    private MbLogin login;
    private Usuario usLogeado;
    private boolean iniciado;


    /**
     * Creates a new instance of MbGenero
     */
    public MbGenero() {
    }
   
    @PostConstruct
    public void init(){
        iniciado = false;
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        login = (MbLogin)ctx.getSessionMap().get("mbLogin");
        if(login != null) usLogeado = login.getUsLogeado();
    }
    /**
     * Método que borra de la memoria los MB innecesarios al cargar el listado 
     */
    public void iniciar(){
        if(!iniciado){
            String s;
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
            .getExternalContext().getSession(true);
            Enumeration enume = session.getAttributeNames();
            while(enume.hasMoreElements()){
                s = (String)enume.nextElement();
                if(s.substring(0, 2).equals("mb")){
                    if(!s.equals("mbUsuario") && !s.equals("mbLogin")){
                        session.removeAttribute(s);
                    }
                }
            }
        }
    }    

    public List<Genero> getListaFilter() {
        return listaFilter;
    }

    public void setListaFilter(List<Genero> listaFilter) {
        this.listaFilter = listaFilter;
    }
    
    public Genero getCurrent() {
        return current;
    }

    public void setCurrent(Genero current) {
        this.current = current;
    }
    

    public List<Familia> getListaFamilia() {
        return listaFamilia;
    }

    public void setListaFamilia(List<Familia> listaFamilia) {
        this.listaFamilia = listaFamilia;
    }

    /********************************
    ** Métodos para la navegación **
    ********************************/
    /**
     * @return La entidad gestionada
     */
    public Genero getSelected() {
        if (current == null) {
            current = new Genero();
        }
        return current;
    } 

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
     * @return acción para el listado de entidades a mostrar en el list
     */
    public String prepareList() {
        recreateModel();
        return "list";
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
        listaFamilia = familiaFacade.getActivos();
        current = new Genero();
        return "new";
    }
    
    /**
     * @return acción para la edición de la entidad
     */
    public String prepareEdit() {
        listaFamilia = familiaFacade.getActivos();        
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
       // buscarGenero();//
        return "list";
    }
    
        /**
     * @return mensaje que notifica la actualizacion de estado
     */    
   public void habilitar() {
        update = 2;
        update();        
        recreateModel();
    }  
     /**
     */    
    public void deshabilitar() {
       if (getFacade().tieneDependencias(current.getId())){
          update = 1;
          update();        
          recreateModel();
       } 
        else{
            //No Deshabilita 
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("GeneroNonDeletable"));            
        }
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
            throw new ValidatorException(new FacesMessage(ResourceBundle.getBundle("/Bundle").getString("CreateGeneroExistente")));
        }
    }
    
    /**
     * Restea la entidad
     */
    private void recreateModel() {
        items = null;
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
        admEnt.setUsAlta(usLogeado);
        current.setAdminentidad(admEnt);        
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("GeneroCreated"));
            return "view";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("GeneroCreatedErrorOccured"));
            return null;
        }
    }

    /**
     * @return mensaje que notifica la actualización
     */
    public String update() {
          Date date = new Date(System.currentTimeMillis());
        //Date dateBaja = new Date();
        
        // actualizamos según el valor de update
        if(update == 1){
            current.getAdminentidad().setFechaBaja(date);
            current.getAdminentidad().setUsBaja(usLogeado);
            current.getAdminentidad().setHabilitado(false);
        }
        if(update == 2){
            current.getAdminentidad().setFechaModif(date);
            current.getAdminentidad().setUsModif(usLogeado);
            current.getAdminentidad().setHabilitado(true);
            current.getAdminentidad().setFechaBaja(null);
            current.getAdminentidad().setUsBaja(usLogeado);
        }
        if(update == 0){
            current.getAdminentidad().setFechaModif(date);
            current.getAdminentidad().setUsModif(usLogeado);
        }
        // acualizo
        try {
            if(update == 0){
                getFacade().edit(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("GeneroUpdated"));
                return "view";
            }else if(update == 1){
                getFacade().edit(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("GeneroDeshabilitado"));
                return "view";
            }else{
                getFacade().edit(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("GeneroHabilitado"));
                return "view";
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("GeneroUpdatedErrorOccured"));
            return null;
        }
    }
 
    
    /*************************
    ** Métodos de selección **
    **************************/
    /**
     * @return la totalidad de las entidades persistidas formateadas
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(generoFacade.findAll(), false);
    }

    /**
     * @return de a una las entidades persistidas formateadas
     */
    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(generoFacade.findAll(), true);
    }

    /**
     * @param id equivalente al id de la entidad persistida
     * @return la entidad correspondiente
     */
    public Genero getGenero(java.lang.Long id) {
        return generoFacade.find(id);
    }    
    
    /*********************
    ** Métodos privados **
    **********************/
    /**
     * @return el Facade
     */
    private GeneroFacade getFacade() {
        return generoFacade;
    }
    
    /**
     * Opera el borrado de la entidad
     */
    private void performDestroy() {
        try {
            //getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("GeneroDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("GeneroDeletedErrorOccured"));
        }
    }
    
    /**
     * Método para revocar la sesión del MB
     * @return 
     */
    public String cleanUp(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true);
        session.removeAttribute("mbGenero");
   
        return "inicio";
    }      

    
    /********************************************************************
    ** Converter. Se debe actualizar la entidad y el facade respectivo **
    *********************************************************************/
    @FacesConverter(forClass = Genero.class)
    public static class GeneroControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MbGenero controller = (MbGenero) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mbGenero");
            return controller.getGenero(getKey(value));
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
            if (object instanceof Genero) {
                Genero o = (Genero) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Genero.class.getName());
            }
        }
    }        
}
