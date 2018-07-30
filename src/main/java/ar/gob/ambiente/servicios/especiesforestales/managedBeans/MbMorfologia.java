
package ar.gob.ambiente.servicios.especiesforestales.managedBeans;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Especie;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Morfologia;
import ar.gob.ambiente.servicios.especiesforestales.entidades.util.JsfUtil;
import ar.gob.ambiente.servicios.especiesforestales.facades.MorfologiaFacade;
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
 * Bean de respaldo para la gestión de las Morfologías
 * @author rincostante
 */
public class MbMorfologia implements Serializable{
    /**
     * Variable privada: Morfologia Entidad que se gestiona mediante el bean
     */
    private Morfologia current;
    /**
     * Variable privada: List<Morfologia> Listado de morfologías para poblar la tabla con todos los autores registrados
     */
    private List<Morfologia> listado = null;
    /**
     * Variable privada: List<Morfologia> para el filtrado de la tabla
     */
    private List<Morfologia> listaFilter;   
    /**
     * Variable privada: List<Autor> Listado que muestra las Especies vinculadas al autor
     */
    private List<Especie> listEspFilter;
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Morfología
     */
    @EJB
    private MorfologiaFacade morfologiaFacade;
    /**
     * Variable privada: boolean que indica si se inició el bean
     */
    private boolean iniciado;         
    /**
     * Constructor
     */
    public MbMorfologia() {
    }

    public List<Especie> getListEspFilter() {
        return listEspFilter;
    }

    public void setListEspFilter(List<Especie> listEspFilter) {
        this.listEspFilter = listEspFilter;
    }

    public Morfologia getCurrent() {
        return current;
    }

    public void setCurrent(Morfologia current) {
        this.current = current;
    }
    /**
     * Método que obtiene el listado de todos los Morfologías, solo si no está instanciado ya
     * @return List<Morfologia> listado con todos los Morfologías registradas
     */
    public List<Morfologia> getListado() {
        if (listado == null || listado.isEmpty()) {
            listado = getFacade().findAll();
        }
        return listado;
    }

    public void setListado(List<Morfologia> listado) {
        this.listado = listado;
    }

    public List<Morfologia> getListaFilter() {
        return listaFilter;
    }

    public void setListaFilter(List<Morfologia> listaFilter) {
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
     * Redireccionamiento a la vista new para crear una Morfologia
     * @return String nombre de la vista
     */
    public String prepareCreate() {
        current = new Morfologia();
        return "new";
    }   
    
    /**
     * Redireccionamiento a la vista edit para editar una Morfologia
     * @return String nombre de la vista
     */
    public String prepareEdit() {
        return "edit";
    }    
    
    /**
     * Método que verifica que la Morfologia que se quiere eliminar no esté siento utilizado por otra entidad y redirecciona a la vista detalle
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
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("MorfologiaNonDeletable"));
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
    * Método para la creación de una nueva Morfologia
    * @return String nombre de la vista detalle o null si hay error
    */   
    public String create() {     
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MorfologiaCreated"));
            return "view";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("MorfologiaCreatedErrorOccured"));
            return null;
        }
    }
    
    /**
     * Método para la actualización de una Morfologia existente
     * @return String nombre de la vista detalle o null si hay error
     */
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MorfologiaUpdated"));
            return "view";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("MorfologiaUpdatedErrorOccured"));
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
     * Método que instancia a la entidad Morfologia
     * @return Autor entidad a gestionar
     */
    public Morfologia getSelected() {
        if (current == null) {
            current = new Morfologia();
        }
        return current;
    } 
    
    /**
     * Método que obtiene una Morfologia según su id
     * @param id Long equivalente al id de la entidad persistida
     * @return Morfologia la entidad correspondiente
     */
    public Morfologia getMorfologia(java.lang.Long id) {
        return morfologiaFacade.find(id);
    }  
    
    
    /*********************
    ** Métodos privados **
    **********************/
    /**
     * Método que devuelve el facade para el acceso a datos de la Morfologia
     * @return EJB MorfologiaFacade Acceso a datos
     */
    private MorfologiaFacade getFacade() {
        return morfologiaFacade;
    }    
    /**
     * Método para validar que la Morfologia cuyo nombre se recibe ya está registrado
     * @param arg2 Object Contenido del campo de texto correspondiente al nombre
     * @throws ValidatorException 
     */
    private void validarExistente(Object arg2) throws ValidatorException{
        if(!getFacade().existe((String)arg2)){
            throw new ValidatorException(new FacesMessage(ResourceBundle.getBundle("/Bundle").getString("CreateMorfologiaExistente")));
        }
    }    
    
    /**
     * Opera el borrado de la entidad
     */
    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MorfologiaDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("MorfologiaDeletedErrorOccured"));
        }
    }    
    
    
    /********************************************************************
    ** Converter. Se debe actualizar la entidad y el facade respectivo **
    *********************************************************************/
    @FacesConverter(forClass = Morfologia.class)
    public static class EspecieControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MbMorfologia controller = (MbMorfologia) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mbMorfologia");
            return controller.getMorfologia(getKey(value));
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
            if (object instanceof Morfologia) {
                Morfologia o = (Morfologia) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Morfologia.class.getName());
            }
        }
    }              
}
