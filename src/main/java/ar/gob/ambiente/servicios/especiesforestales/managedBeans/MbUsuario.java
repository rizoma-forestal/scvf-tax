
package ar.gob.ambiente.servicios.especiesforestales.managedBeans;

import ar.gob.ambiente.servicios.especiesforestales.entidades.AdminEntidad;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Rol;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Usuario;
import ar.gob.ambiente.servicios.especiesforestales.entidades.util.JsfUtil;
import ar.gob.ambiente.servicios.especiesforestales.facades.RolFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.UsuarioFacade;
import java.io.Serializable;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceRef;
import ar.gob.ambiente.servicios.especiesforestales.wsExt.AccesoAppWebService_Service;
import ar.gob.ambiente.servicios.especiesforestales.wsExt.AccesoAppWebService;
import ar.gob.ambiente.servicios.especiesforestales.wsExt.Aplicacion;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Bean de respaldo para la gestión de los Usuarios
 * @author rincostante
 */
public class MbUsuario implements Serializable{
    /**
     * Variable privada: Cliente del servicio soap de aplicaciones
     */
    @WebServiceRef
    private AccesoAppWebService_Service service;
    /**
     * Variable privada: Usuario Entidad que se gestiona mediante el bean
     */
    private Usuario current;
    /**
     * Variable privada: List<Usuario> Listado de Usuarios para poblar la tabla con todos los orígenes registrados
     */
    private List<Usuario> listado;
    /**
     * Variable privada: List<Usuario> Listado de flitrado de la tabla
     */
    private List<Usuario> listFilter;
    /**
     * Variable privada: Usuario usuario logeado
     */
    private Usuario usLogeado;
    /**
     * Variable privada: MbLogin bean de gestión de la sesión del usuario
     */
    private MbLogin login;   
    /**
     * Variable privada: boolean que indica si se inició el bean
     */
    private boolean iniciado;  
    /**
     * Variable privada: Entero que indica el tipo de actualización que se hará:
     * 0=updateNormal | 1=deshabiliar | 2=habilitar
     */
    private int update;
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Rol
     */
    @EJB
    private RolFacade rolFacade;
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Usuario
     */
    @EJB
    private UsuarioFacade usuarioFacade;    
    /**
     * Variable privada: List<Rol> Listado de los roles vinculados al Usuario
     */ 
    private List<Rol> listaRol;
    /**
     * Variable privada: List<Aplicacion> Listado de las aplicaciones vinculadas al Usuario
     */ 
    private List<Aplicacion> lstApp;
    /**
     * Variable privada: Long id de la Aplicación
     */
    private Long idApp;
    /**
     * Variable privada: List<String> Lista de pares usNombre - usNombreCompleto disponibles para poblar el combo
     */
    private List<String> lstUsDisponibles;
    /**
     * Variable privada: String datos del usuario usNombre - usNombreCompleto
     */
    private String usSeleccionado;
    /**
     * Variable privada: Logger logger
     */
    private static final Logger logger = Logger.getLogger(Usuario.class.getName());

    /**
     * Constructor
     */
    public MbUsuario() {
    }
    
    /**
     * Método que se ejecuta luego de instanciada la clase e inicializa los datos del usuario
     * Lista las aplicaciones disponibles mediante verAplicaciones()
     */  
    @PostConstruct
    public void init(){
        lstUsDisponibles = new ArrayList();
        iniciado = false;
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        login = (MbLogin)ctx.getSessionMap().get("mbLogin");
        if(login != null){
            usLogeado = login.getUsLogeado();    
            lstApp = verAplicaciones();
        }
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

    /********************************
     ** Getters y Setters *********** 
     ********************************/
    
    public String getUsSeleccionado() {
        return usSeleccionado;
    }

    public void setUsSeleccionado(String usSeleccionado) {
        this.usSeleccionado = usSeleccionado;
    }

    
    public List<String> getLstUsDisponibles() {
        return lstUsDisponibles;
    }

    public void setLstUsDisponibles(List<String> lstUsDisponibles) {
        this.lstUsDisponibles = lstUsDisponibles;
    }

    public Usuario getCurrent() {
        return current;
    }

    public void setCurrent(Usuario current) {
        this.current = current;
    }
    
    public List<Rol> getListaRol() {
        return listaRol;
    }

    public void setListaRol(List<Rol> listaRol) {
        this.listaRol = listaRol;
    }
    /**
     * Método que instancia el listado de usuarios existentes
     * @return List<Usuario> listado de usuarios
     */
    public List<Usuario> getListado() {
        if(listado == null){
            listado = getFacade().findAll();
        }
        return listado;
    }

    public void setListado(List<Usuario> listado) {
        this.listado = listado;
    }

    public List<Usuario> getListFilter() {
        return listFilter;
    }

    public void setListFilter(List<Usuario> listFilter) {
        this.listFilter = listFilter;
    }

    
    /********************************
     ** Métodos de inicialización ***
     ********************************/    
    
    /**
     * Método que instancia a la entidad Usuario
     * @return Rol entidad a gestionar
     */
    public Usuario getSelected() {
        if (current == null) {
            current = new Usuario();
        }
        return current;
    }  
    
    /**
     * Redireccionamiento a la vista con el listado
     * previo reseteo del listado
     * @return String nombre de la vista
     */
    public String prepareList() {
        iniciado = true;
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
     * Redireccionamiento a la vista edit para editar un Usuario
     * @return String nombre de la vista para la edición
     */
    public String prepareEdit() {
        // cargo los roles
        listaRol = rolFacade.getActivos();
        update = 0;
        return "edit";        
    }    
    
    /**
     * Busca la aplicación, lista los usuarios existentes y los disponibles mediante el método verUsuariosPorIdApp(),
     * carga los roles, instancia la entidad y 
     * redirecciona a la vista new para crear un Usuario
     * @return String nombre de la vista
     */
    public String prepareCreate() {
        // inicializo variables
        idApp = Long.valueOf(0);      
        boolean usIngresa;
        String us;
        
        // obetengo la app        
        Iterator itApp = lstApp.listIterator();
        while(itApp.hasNext()){
            Aplicacion app = (Aplicacion)itApp.next();
            if(app.getUrl().equals(ResourceBundle.getBundle("/Bundle").getString("RutaAplicacion"))){
                idApp = app.getId();
            }
        }
        
        // obtengo los usuarios existentes
        List<Usuario> lstUsExist = usuarioFacade.findAll();
        // limpio el listado de usuarios disponibles
        if(lstUsDisponibles != null){
            lstUsDisponibles.clear();
        }
        // obtengo los usuarios disponibles para la app, solo si obtuve el idApp
        if(idApp > 0){
            List<ar.gob.ambiente.servicios.especiesforestales.wsExt.Usuario> lstUsDisp = verUsuariosPorIdApp();
            Iterator itUsDisp = lstUsDisp.listIterator();
            while(itUsDisp.hasNext()){
                ar.gob.ambiente.servicios.especiesforestales.wsExt.Usuario usDisp = (ar.gob.ambiente.servicios.especiesforestales.wsExt.Usuario)itUsDisp.next();
                Iterator itUsExist = lstUsExist.listIterator();
                usIngresa = true;
                while(itUsExist.hasNext()){
                    Usuario usExist = (Usuario)itUsExist.next();
                    if(usExist.getNombre().equals(usDisp.getNombre())){
                        usIngresa = false;
                    }
                }
                if(usIngresa){
                    // agrego el usuario a la lista 
                    us = usDisp.getNombre() + "-" + usDisp.getNombreCompleto();
                    lstUsDisponibles.add(us);
                }
            }
        }

        // cargo los roles
        listaRol = rolFacade.getActivos();
        
        // creo la entidad
        current = new Usuario();
        
        return "new";
    }    
    
    /**
     * Método que redirecciona a la página principal
     * @return String nombre de la página principal
     */
    public String prepareInicio(){
        recreateModel();
        return "/faces/index";
    }
    
    /**
     * Método para preparar la búsqueda
     * @return String ruta a la vista que muestra los resultados de la consulta en forma de listado
     */
    public String prepareSelect(){
        return "list";
    }
    
    /**
     * Método que prepara la habilitación de un Rol.
     * Setea el tipo de actualización, la ejecuta y resetea el listado
     */  
    public String habilitar() {
        current.getAdmin().setHabilitado(true);
        update();        
        recreateModel();
        return "view";
    } 
     /**
     * Método que prepara la deshabilitación de un Rol.
     * Setea el tipo de actualización, la ejecuta y resetea el listado
     */    
    public String deshabilitar() {
        current.getAdmin().setHabilitado(false);
        update();        
        recreateModel();

        return "view";
    } 
    

    public void prepareHabilitar(){
        update = 2;
        update();        
    }
    

    public void prepareDesHabilitar(){
        update = 1;
        update();        
    }    
    

    /*************************
    ** Métodos de operación **
    **************************/
    /**
     * Méto que inserta uno nuevo Usuario en la base de datos, previamente genera una entidad de administración
     * con los datos necesarios y luego se la asigna a la persona
     * @return String mensaje que notifica la inserción
     */
    public String create() {
        try {
            // Creación de la entidad de administración y asignación
            Date date = new Date(System.currentTimeMillis());
            AdminEntidad admEnt = new AdminEntidad();
            admEnt.setFechaAlta(date);
            admEnt.setHabilitado(true);
            admEnt.setUsAlta(usLogeado);
            current.setAdmin(admEnt);

            // asigno los datos del usuario seleccionado para persistirlo
            current.setNombre(usSeleccionado.substring(0, usSeleccionado.indexOf("-")));
            current.setNombreCompleto(usSeleccionado.substring(usSeleccionado.indexOf("-") + 1, usSeleccionado.length()));

            // Inserción
            getFacade().create(current);
            recreateModel();
            return "view";

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("UsuarioCreatedErrorOccured"));
            return null;
        }
    }    
    
    /**
     * Método que actualiza un nuevo Docente en la base de datos.
     * Previamente actualiza los datos de administración
     * @return String mensaje que notifica la actualización
     */
    public String update() {    
        try {
            // Actualización de datos de administración de la entidad según el valor de update
            Date date = new Date(System.currentTimeMillis());
            if(update == 1){
                current.getAdmin().setFechaBaja(date);
                current.getAdmin().setUsBaja(usLogeado);
                current.getAdmin().setHabilitado(false);
            }if(update == 2){
                current.getAdmin().setFechaModif(date);
                current.getAdmin().setUsModif(usLogeado);
                current.getAdmin().setHabilitado(true);
                current.getAdmin().setFechaBaja(null);
                current.getAdmin().setUsBaja(usLogeado);
            }if(update == 0){
                current.getAdmin().setFechaModif(date);
                current.getAdmin().setUsModif(usLogeado);
            }

            // Actualizo
            switch (update) {
                case 0:
                    getFacade().edit(current);
                    JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioUpdated"));
                    return "view";
                case 1:
                    getFacade().edit(current);
                    JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioDeshabilitado"));
                    return "view";
                default:
                    getFacade().edit(current);
                    JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioHabilitado"));
                    return "view";
            }
                
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("UsuarioUpdatedErrorOccured"));
            return null;
        }
    }    
    
    /**
     * Método que obtiene un Usuario según su id
     * @param id Long id de la Usuario a buscar
     * @return Usuario la entidad correspondiente
     */
    public Usuario getUsuario(java.lang.Long id) {
        return getFacade().find(id);
    }    
    
    
    /*********************
    ** Métodos privados **
    **********************/
    /**
     * Método privado que devuelve el facade para el acceso a datos del Usuario 
     * @return EJB UsuarioFacade Acceso a datos
     */
    private UsuarioFacade getFacade() {
        return usuarioFacade;
    }    
    
    /**
     * Método privado que restea la entidad
     */
    private void recreateModel() {
        listado.clear();
        listado = null;
        lstUsDisponibles.clear();
        usSeleccionado = "";
    }      

    /**
     * Método privado para ver las aplicaciones mediante el servicio soap
     * @return List<Aplicacion> Aplicaciones existentes
     */
    private List<Aplicacion> verAplicaciones() {
        List<Aplicacion> result;
        try{
            AccesoAppWebService port = service.getAccesoAppWebServicePort();
            return port.verAplicaciones();
        }catch(Exception ex){
            result = null;
            // muestro un mensaje al usuario
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioAppErrorWs"));
            // lo escribo en el log del server
            logger.log(Level.SEVERE, "{0} - {1}", new Object[]{ResourceBundle.getBundle("/Bundle").getString("UsuarioAppErrorWs"), ex.getMessage()});
        }
        return result;
    }  
    
    /**
     * Método privado para listaro los usuarios por aplicación mediante el servicio soap
     * @return List<ar.gob.ambiente.servicios.especiesforestales.wsExt.Usuario> Listado de los usuarios por aplicación
     */
    private List<ar.gob.ambiente.servicios.especiesforestales.wsExt.Usuario> verUsuariosPorIdApp(){
        List<ar.gob.ambiente.servicios.especiesforestales.wsExt.Usuario> result;
        try{
            AccesoAppWebService port = service.getAccesoAppWebServicePort();
            return port.verUsuariosPorApp(idApp);
        }catch(Exception ex){
            result = null;
            // muestro un mensaje al usuario
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioUsErrorWs"));
            // lo escribo en el log del server
            logger.log(Level.SEVERE, "{0} - {1}", new Object[]{ResourceBundle.getBundle("/Bundle").getString("UsuarioUsErrorWs"), ex.getMessage()});
        }
        return result;
    }
    
    
    /********************************************************************
    ** Converter. Se debe actualizar la entidad y el facade respectivo **
    *********************************************************************/
    @FacesConverter(forClass = Usuario.class)
    public static class UsuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MbUsuario controller = (MbUsuario) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mbUsuario");
            return controller.getUsuario(getKey(value));
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
            if (object instanceof Usuario) {
                Usuario o = (Usuario) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Usuario.class.getName());
            }
        }
    }               
}
