
package ar.gob.ambiente.servicios.especiesforestales.managedBeans;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Especie;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Publicacion;
import ar.gob.ambiente.servicios.especiesforestales.entidades.util.JsfUtil;
import ar.gob.ambiente.servicios.especiesforestales.facades.PublicacionFacade;
import java.io.Serializable;
import java.util.Calendar;
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
 * Bean de respaldo para la gestión de las Publicaciones
 * @author rincostante
 */
public class MbPublicacion implements Serializable{
    
    private Publicacion current;
    
    private List<Publicacion> listado = null;
    
    private List<Publicacion> listaFilter;  
    
    private List<Especie> listEspFilter;
    
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Publicación
     */
    @EJB
    private PublicacionFacade publicacionFacade;
    /**
     * Variable privada: boolean que indica si se inició el bean
     */
    private boolean iniciado;        

    /**
     * Constructor
     */
    public MbPublicacion() {
    }

    public List<Especie> getListEspFilter() {
        return listEspFilter;
    }

    public void setListEspFilter(List<Especie> listEspFilter) {
        this.listEspFilter = listEspFilter;
    }

    public Publicacion getCurrent() {
        return current;
    }

    public void setCurrent(Publicacion current) {
        this.current = current;
    }
    /**
     * Método que obtiene el listado de todos los Publicaciones, solo si no está instanciado ya
     * @return List<Publicacion> listado con todos los Publicaciones registradas
     */
    public List<Publicacion> getListado() {
        if (listado == null || listado.isEmpty()) {
            listado = getFacade().findAll();
        }
        return listado;
    }

    public void setListado(List<Publicacion> listado) {
        this.listado = listado;
    }

    public List<Publicacion> getListaFilter() {
        return listaFilter;
    }

    public void setListaFilter(List<Publicacion> listaFilter) {
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
     * Redireccionamiento a la vista new para crear una Publicacion
     * @return String nombre de la vista
     */
    public String prepareCreate() {
        current = new Publicacion();
        return "new";
    }   
    
    /**
     * Redireccionamiento a la vista edit para editar una Publicacion
     * @return String nombre de la vista
     */
    public String prepareEdit() {
        return "edit";
    }    
    
    /**
     * Método que verifica que la Publicacion que se quiere eliminar no esté siento utilizado por otra entidad y redirecciona a la vista detalle
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
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("PublicacionNonDeletable"));
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
    * Método para la creación de una nueva Publicacion
    * @return String nombre de la vista detalle o null si hay error
    */  
    public String create() {     
        try {
            if(getFacade().noExiste(current.getNombre(), current.getAnio())){
                getFacade().create(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PublicacionCreated"));
                return "view";
            }else{
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("PublicacionExistente"));
                return null;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PublicacionCreatedErrorOccured"));
            return null;
        }
    }
    
    /**
     * Método para la actualización de una Publicacion existente
     * @return String nombre de la vista detalle o null si hay error
     */
    public String update() {
        Publicacion pub;
        try {
            pub = getFacade().getExistente(current.getNombre(), current.getAnio());
            if(pub == null){
                // Actualizo todo porque no coincide con ninguno existente
                getFacade().edit(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PublicacionUpdated"));
                return "view";
            }else{
                if(pub.getId().equals(current.getId())){
                    // Actualizo porque es la misma entidad que encontré en la base
                    getFacade().edit(current);
                    JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PublicacionUpdated"));
                    return "view";
                }else{
                    // Retorno porque estoy usando un par nombre/año existente
                    JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("PublicacionExistente"));
                    return null;
                }
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PublicacionUpdatedErrorOccured"));
            return null;
        }
    }    
    
    /**
     * Método privado que restea la entidad
     */
    private void recreateModel() {
        listado.clear();
    }    
    
    /**
     * Método que instancia a la entidad Publicacion
     * @return Autor entidad a gestionar
     */
 
    public Publicacion getSelected() {
        if (current == null) {
            current = new Publicacion();
        }
        return current;
    } 
    
    /**
     * Método que obtiene una Publicacion según su id
     * @param id Long equivalente al id de la entidad persistida
     * @return Publicacion la entidad correspondiente
     */
    public Publicacion getPublicacion(java.lang.Long id) {
        return publicacionFacade.find(id);
    }  
    
    /**
     * Método para validar que el año ingresado tenga un formato válido
     * @param arg0: vista jsf que llama al validador
     * @param arg1: objeto de la vista que hace el llamado
     * @param arg2: contenido del campo de texto a validar 
     */
    public void validarAnio(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException{
        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
        if((int)arg2 > anioActual){
            throw new ValidatorException(new FacesMessage(ResourceBundle.getBundle("/Bundle").getString("PublicacionValadationAnioMayor")));
        }
    }        
    
    
    /*********************
    ** Métodos privados **
    **********************/
    /**
     * Método que devuelve el facade para el acceso a datos de la Publicacion
     * @return EJB PublicacionFacade Acceso a datos
     */
    private PublicacionFacade getFacade() {
        return publicacionFacade;
    }    
    
    /**
     * Método para validar que la Publicacion cuyo nombre se recibe ya está registrado
     * @param arg2 Object Contenido del campo de texto correspondiente al nombre
     * @throws ValidatorException 
     */
    private void validarExistente(Object arg2) throws ValidatorException{
        if(!getFacade().noExisteXNombre((String)arg2)){
            throw new ValidatorException(new FacesMessage(ResourceBundle.getBundle("/Bundle").getString("CreatePublicacionExistente")));
        }
    }    
    
    /**
     * Opera el borrado de la entidad
     */
    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PublicacionDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PublicacionDeletedErrorOccured"));
        }
    }    
    
    
    /********************************************************************
    ** Converter. Se debe actualizar la entidad y el facade respectivo **
    *********************************************************************/
    @FacesConverter(forClass = Publicacion.class)
    public static class PublicacionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MbPublicacion controller = (MbPublicacion) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mbPublicacion");
            return controller.getPublicacion(getKey(value));
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
            if (object instanceof Publicacion) {
                Publicacion o = (Publicacion) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Publicacion.class.getName());
            }
        }
    }                    
}
