
package ar.gob.ambiente.servicios.especiesforestales.managedBeans;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Cites;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Especie;
import ar.gob.ambiente.servicios.especiesforestales.entidades.util.JsfUtil;
import ar.gob.ambiente.servicios.especiesforestales.facades.CitesFacade;
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
 * Bean de respaldo para la gestión de Cites
 * @author rincostante
 */
public class MbCites implements Serializable{
    
    /**
     * Variable privada: Cites Entidad que se gestiona mediante el bean
     */
    private Cites current;
    
    /**
     * Variable privada: List<Cites> Listado de Cites para poblar la tabla con todos los registrados
     */
    private List<Cites> listado = null;
    
    /**
     * Variable privada: List<Cites> para el filtrado de la tabla
     */
    private List<Cites> listaFilter;  
    
    /**
     * Listado que muestra las Especies vinculadas a tipo de Cites
     */
    private List<Especie> listEspFilter;
    
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Cites
     */    
    @EJB
    private CitesFacade citesFacade;

    /**
     * Variable privada: boolean que indica si se inició el bean
     */    
    private boolean iniciado;     

    /**
     * Constructor
     */
    public MbCites() {
    }

    public List<Especie> getListEspFilter() {
        return listEspFilter;
    }

    public void setListEspFilter(List<Especie> listEspFilter) {
        this.listEspFilter = listEspFilter;
    }

    public Cites getCurrent() {
        return current;
    }

    public void setCurrent(Cites current) {
        this.current = current;
    }

    /**
     * Método que obtiene el listado de todos los Cites, solo si no está instanciado ya
     * @return List<Cites> listado con todos los Cites registrados
     */    
    public List<Cites> getListado() {
        if (listado == null || listado.isEmpty()) {
            listado = getFacade().findAll();
        }
        return listado;
    }

    public void setListado(List<Cites> listado) {
        this.listado = listado;
    }

    public List<Cites> getListaFilter() {
        return listaFilter;
    }

    public void setListaFilter(List<Cites> listaFilter) {
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
     * Redireccionamiento a la vista new para crear un Cites
     * @return String nombre de la vista
     */
    public String prepareCreate() {
        current = new Cites();
        return "new";
    }   
    
    /**
     * Redireccionamiento a la vista edit para editar un Cites
     * @return String nombre de la vista
     */
    public String prepareEdit() {
        return "edit";
    }    
    
    /**
     * Método que verifica que el Cites que se quiere eliminar no esté siento utilizado por otra entidad y redirecciona a la vista detalle
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
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("CitesNonDeletable"));
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
    * Método para la creación de un nuevo Cites
    * @return String nombre de la vista detalle o null si hay error
    */   
    public String create() {     
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CitesCreated"));
            return "view";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("CitesCreatedErrorOccured"));
            return null;
        }
    }
    
    /**
     * Método para la actualización de un Autor existente
     * @return String nombre de la vista detalle o null si hay error
     */
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CitesUpdated"));
            return "view";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("CitesUpdatedErrorOccured"));
            return null;
        }
    }    
    
    /**
     * Método que restea la entidad
     */
    private void recreateModel() {
        listado.clear();
    }    
    
    /**
     * Método que instancia a la entidad Cites
     * @return Cites entidad a gestionar
     */
 
    public Cites getSelected() {
        if (current == null) {
            current = new Cites();
        }
        return current;
    } 
    
    /**
     * Método que obtiene un Cites según su id
     * @param id Long equivalente al id de la entidad persistida
     * @return Cites la entidad correspondiente
     */
    public Cites getCites(java.lang.Long id) {
        return citesFacade.find(id);
    }  
    
    
    /*********************
    ** Métodos privados **
    **********************/
    /**
     * Método que devuelve el facade para el acceso a datos del Cites
     * @return EJB CitesFacade Acceso a datos
     */
    private CitesFacade getFacade() {
        return citesFacade;
    }    
    
    /**
     * Método para validar que el Cites cuyo nombre se recibe ya está registrado
     * @param arg2 Object Contenido del campo de texto correspondiente al nombre
     * @throws ValidatorException 
     */    
    private void validarExistente(Object arg2) throws ValidatorException{
        if(!getFacade().existe((String)arg2)){
            throw new ValidatorException(new FacesMessage(ResourceBundle.getBundle("/Bundle").getString("CreateCitesExistente")));
        }
    }    
    
    /**
     * Opera el borrado de la entidad
     */
    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CitesDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("CitesDeletedErrorOccured"));
        }
    }    
    
    
    /********************************************************************
    ** Converter. Se debe actualizar la entidad y el facade respectivo **
    *********************************************************************/
    @FacesConverter(forClass = Cites.class)
    public static class EspecieControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MbCites controller = (MbCites) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mbCites");
            return controller.getCites(getKey(value));
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
            if (object instanceof Cites) {
                Cites o = (Cites) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Cites.class.getName());
            }
        }
    }                
}
