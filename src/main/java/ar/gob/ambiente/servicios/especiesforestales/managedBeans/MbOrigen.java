
package ar.gob.ambiente.servicios.especiesforestales.managedBeans;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Especie;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Origen;
import ar.gob.ambiente.servicios.especiesforestales.entidades.util.JsfUtil;
import ar.gob.ambiente.servicios.especiesforestales.facades.OrigenFacade;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

/**
 * Bean de respaldo para la gestión de los Origenes
 * @author rincostante
 */
public class MbOrigen implements Serializable{
    /**
     * Variable privada: Autor Entidad que se gestiona mediante el bean
     */
    private Origen current;
    /**
     * Variable privada: List<Origen> Listado de Orígenes para poblar la tabla con todos los orígenes registrados
     */
    private List<Origen> listado = null;
    /**
     * Variable privada: List<Origen> para el filtrado de la tabla
     */
    private List<Origen> listaFilter;   
    /**
     * Variable privada: List<Autor> Listado que muestra las Especies vinculadas al autor
     */
    private List<Especie> listEspFilter;    
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Origen
     */
    @EJB
    private OrigenFacade origenFacade;
    /**
     * Variable privada: boolean que indica si se inició el bean
     */
    private boolean iniciado;      

    /**
     * Contructor
     */
    public MbOrigen() {
    }

    public List<Especie> getListEspFilter() {
        return listEspFilter;
    }

    public void setListEspFilter(List<Especie> listEspFilter) {
        this.listEspFilter = listEspFilter;
    }

    public Origen getCurrent() {
        return current;
    }

    public void setCurrent(Origen current) {
        this.current = current;
    }

    /**
     * Método que obtiene el listado de todos los Origenes, solo si no está instanciado ya
     * @return List<Origen> listado con todos los Origenes registrados
     */
    public List<Origen> getListado() {
        if (listado == null || listado.isEmpty()) {
            listado = getFacade().findAll();
        }
        return listado;
    }

    public void setListado(List<Origen> listado) {
        this.listado = listado;
    }

    public List<Origen> getListaFilter() {
        return listaFilter;
    }

    public void setListaFilter(List<Origen> listaFilter) {
        this.listaFilter = listaFilter;
    }
    
    /****************************
     * Métodos de inicialización
     ****************************/
    /**
     * Método que se ejecuta luego de instanciada la clase e inicializa los datos del usuario
     */
    @PostConstruct
    public void init(){
        iniciado = false;
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
                    if(!s.equals("mbLogin")){
                        session.removeAttribute(s);
                    }
                }
            }
        }
    }   
    
    /**
     * Redireccionamiento a la vista con el listado
     * @return String nombre de la vista
     */
    public String prepareList() {
        recreateModel();
        return "list";
    }
    
    /**
     * Redireccionamiento a la vista detalle
     * @return String nombre de la vista
     */
    public String prepareView() {
        return "view";
    }
    
    /**
     * Redireccionamiento a la vista new para crear un Origen
     * @return String nombre de la vista
     */
    public String prepareCreate() {
        current = new Origen();
        return "new";
    }   
    
    /**
     * @return acción para la edición de la entidad
     */
    public String prepareEdit() {
        return "edit";
    }    
    
    /**
     * Redireccionamiento a la vista edit para editar un Origen
     * @return String nombre de la vista
     */
    public String prepareDestroy(){
        boolean libre = getFacade().noTieneDependencias(current.getId());

        if (libre){
            // Elimina
            performDestroy();
            recreateModel();
        }else{
            //No Elimina 
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("OrigenNonDeletable"));
        }
        return "view";
    }    
    
    
    /****************************
     * Métodos de validación
     ****************************/    
    
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
     */
    public void validarUpdate(FacesContext arg0, UIComponent arg1, Object arg2){
        if(!current.getNombre().equals((String)arg2)){
            validarExistente(arg2);
        }
    }    

    
    /**********************
     * Métodos de operación
     **********************/
    /**
    * Método para la creación de un nuevo Origen
    * @return String nombre de la vista detalle o null si hay error
    */   
    public String create() {     
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OrigenCreated"));
            return "view";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("OrigenCreatedErrorOccured"));
            return null;
        }
    }
    
    /**
     * Método para la actualización de un Origen existente
     * @return String nombre de la vista detalle o null si hay error
     */
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OrigenUpdated"));
            return "view";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("OrigenUpdatedErrorOccured"));
            return null;
        }
    }    
    
    /**
     * Método privado que Restea la entidad
     */
    private void recreateModel() {
        listado.clear();
    }    
    
    /**
     * Método que instancia a la entidad Origen
     * @return Origen entidad a gestionar
     */
    public Origen getSelected() {
        if (current == null) {
            current = new Origen();
        }
        return current;
    } 
    
    /**
     * Método que obtiene un Origen según su id
     * @param id Long equivalente al id de la entidad persistida
     * @return Origen la entidad correspondiente
     */
    public Origen getOrigen(java.lang.Long id) {
        return origenFacade.find(id);
    }  
    
    
    /*********************
    ** Métodos privados **
    **********************/
    /**
     * Método que devuelve el facade para el acceso a datos del Origen
     * @return EJB OrigenFacade Acceso a datos
     */
    private OrigenFacade getFacade() {
        return origenFacade;
    }    
    
    /**
     * Método para validar que el Origen cuyo nombre se recibe ya está registrado
     * @param arg2 Object Contenido del campo de texto correspondiente al nombre
     * @throws ValidatorException 
     */
    private void validarExistente(Object arg2) throws ValidatorException{
        if(!getFacade().existe((String)arg2)){
            throw new ValidatorException(new FacesMessage(ResourceBundle.getBundle("/Bundle").getString("CreateOrigenExistente")));
        }
    }    
    
    /**
     * Método privado que opera el borrado de la entidad
     */
    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OrigenDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("OrigenDeletedErrorOccured"));
        }
    }    
    
    
    /********************************************************************
    ** Converter. Se debe actualizar la entidad y el facade respectivo **
    *********************************************************************/
    @FacesConverter(forClass = Origen.class)
    public static class EspecieControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MbOrigen controller = (MbOrigen) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mbOrigen");
            return controller.getOrigen(getKey(value));
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
            if (object instanceof Origen) {
                Origen o = (Origen) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Origen.class.getName());
            }
        }
    }                    
}
