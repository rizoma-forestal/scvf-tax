
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
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;


/**
 * Bean de respaldo para la gestión de Géneros
 * @author carmendariz
 */
public class MbGenero implements Serializable{
    /**
     * Variable privada: Género Entidad que se gestiona mediante el bean
     */
    private Genero current;
    /**
     * Variable privada: DataModel Listado de Géneros para poblar la tabla con todos los registrados
     */
    private DataModel items = null;
    /**
     * Variable privada: List<Genero> para el filtrado de la tabla
     */
    private List<Genero> listaFilter;
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Familias
     */
    @EJB
    private FamiliaFacade familiaFacade;
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Géneros
     */
    @EJB
    private GeneroFacade generoFacade;
    /**
     * Variable privada: List<Familia> para seleccionar y asignar a un Género
     */
    private List<Familia> listaFamilia;
    /**
     * Variable privada: Entero que indica el tipo de actualización que se hará:
     * 0=updateNormal | 1=deshabiliar | 2=habilitar
     */
    private int update; // 0=updateNormal | 1=deshabiliar | 2=habilitar
    /**
     * Variable privada: MbLogin bean de gestión de la sesión del usuario
     */
    private MbLogin login;
    /**
     * Variable privada: Usuario usuario logeado
     */
    private Usuario usLogeado;
    /**
     * Variable privada: boolean que indica si se inició el bean
     */
    private boolean iniciado;
    
    /**
     * Constructor
     */
    public MbGenero() {
    }
   
    /**
     * Método que se ejecuta luego de instanciada la clase e inicializa los datos del usuario
     */    
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

    /**
     * Método que instancia los Items que componen el listado
     * @return DataModel Items que componen el listado
     */    
    public DataModel getItems() {
        if (items == null) {
            items = new ListDataModel(getFacade().findAll());
        }
        return items;
    }    
    

/*******************************
** Métodos de inicialización **
*******************************/
    /**
     * Redireccionamiento a la vista con el listado
     * previo reseteo del listado
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
     * Método que instancia el Género y puebla el listado de Familias para su selección
     * @return String nombre de la vista para la creación
     */
    public String prepareCreate() {
        listaFamilia = familiaFacade.getActivos();
        current = new Genero();
        return "new";
    }
    
    /**
     * Redireccionamiento a la vista edit para editar un Género
     * previa carga del listado de las Familias disponibles
     * @return String nombre de la vista para la edición
     */
    public String prepareEdit() {
        listaFamilia = familiaFacade.getActivos();        
        return "edit";
    }
 
    /**
     * Método que vuelve a la vista inicial
     * previo reseteo del listado
     * @return String nombre de la vista inicial
     */
    public String prepareInicio(){
        recreateModel();
        return "/faces/index";
    }
    
    /**
     * Método para preparar la búsqueda
     * @return String la ruta a la vista que muestra los resultados de la consulta en forma de listado
     */
    public String prepareSelect(){
        return "list";
    }
    
    /**
     * Método que prepara la habilitación de un Género.
     * Setea el tipo de actualización, la ejecuta y resetea el listado
     */  
    public void habilitar() {
        update = 2;
        update();        
        recreateModel();
    }  

     /**
     * Método que prepara la deshabilitación de un Género.
     * Setea el tipo de actualización, la ejecuta y resetea el listado
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
    
    /**
     * Método privado que valida que un Género no exista ya según sus datos únicos
     * @param arg2
     * @throws ValidatorException 
     */
    private void validarExistente(Object arg2) throws ValidatorException{
        if(!getFacade().existe((String)arg2)){
            throw new ValidatorException(new FacesMessage(ResourceBundle.getBundle("/Bundle").getString("CreateGeneroExistente")));
        }
    }
    
    /**
     * Método privado que restea el listado
     */
    private void recreateModel() {
        items = null;
    }

    /*************************
    ** Métodos de operación **
    **************************/
    
    /**
     * Método para que implementa la creación de un Género:
     * Instancia la entidad administrativa, valida que no exista un Género con los mismos datos únicos,
     * y si todo es correcto ejecuta el método create() del facade y devuelve el nombre de la vista detalle.
     * En caso contrario devuelve null
     * @return String nombre de la vista detalle o null
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
     * Método para que implementa la actualización de un Género, sea para la edición, habilitación o deshabilitación:
     * Actualiza la entidad administrativa según corresponda, procede según el valor de "update",
     * ejecuta el método edit() del facade y devuelve el nombre de la vista detalle o null.
     * @return String nombre de la vista detalle o null
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
              switch (update) {
                  case 0:
                      getFacade().edit(current);
                      JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("GeneroUpdated"));
                      return "view";
                  case 1:
                      getFacade().edit(current);
                      JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("GeneroDeshabilitado"));
                      return "view";
                  default:
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
     * Método que obtiene un Género según su id
     * @param id Long id de la Género a buscar
     * @return Género la entidad correspondiente
     */
    public Genero getGenero(java.lang.Long id) {
        return generoFacade.find(id);
    }    
    
    /*********************
    ** Métodos privados **
    **********************/
    /**
     * Método privado que devuelve el facade para el acceso a datos de las Géneros 
     * @return EJB GeneroFacade Acceso a datos
     */
    private GeneroFacade getFacade() {
        return generoFacade;
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
