
package ar.gob.ambiente.servicios.especiesforestales.managedBeans;

import ar.gob.ambiente.servicios.especiesforestales.entidades.AdminEntidad;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Autor;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Cites;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Genero;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Especie;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Familia;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Morfologia;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Origen;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Publicacion;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Rango;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Usuario;
import ar.gob.ambiente.servicios.especiesforestales.entidades.util.JsfUtil;
import ar.gob.ambiente.servicios.especiesforestales.facades.AutorFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.CitesFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.GeneroFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.EspecieFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.FamiliaFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.MorfologiaFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.OrigenFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.PublicacionFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.RangoFacade;
import java.io.Serializable;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpSession;

/**
 * Bean de respaldo para la gestión de Especies
 * @author carmendariz
 */
public class MbEspecie implements Serializable{
   
    /**
     * Variable privada: Especie Entidad que se gestiona mediante el bean
     */
    private Especie current;
    /**
     * Variable privada: DataModel Listado de Especies para poblar la tabla con todos los registrados
     */
    private DataModel items = null;
    /**
     * Variable privada: List<Especie> para el filtrado de la tabla
     */
    private List<Especie> listaFilter;

    /**
     * Variable privada: EJB inyectado para el acceso a datos de Géneros
     */
    @EJB
    private GeneroFacade generoFacade;
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Familias
     */
    @EJB
    private FamiliaFacade familiaFacade;
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Especies
     */
    @EJB
    private EspecieFacade especieFacade;
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Origenes
     */
    @EJB
    private OrigenFacade origenFacade;
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Morfologías
     */
    @EJB
    private MorfologiaFacade morfologiaFacade;
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Cites
     */
    @EJB
    private CitesFacade citesFacade;
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Rangos
     */
    @EJB
    private RangoFacade rangoFacade;
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Publicación
     */
    @EJB
    private PublicacionFacade pubFacade;
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Autores
     */
    @EJB
    private AutorFacade autorFacade;
    /**
     * Variable privada: Familia se instancia con la familia seleccionada de un combo para luego elegir el género
     */
    private Familia selectedFamilia;
    /**
     * Variable privada: List<Familia> listado con todas las Familias disponibles para su selección al crear o editar una Especie
     */
    private List<Familia> listaFamilia;
    /**
     * Variable privada: Género se instancia con el género seleccionado de un combo para luego elegir la Especie
     */
    private Genero selectedGenero;
    /**
     * Variable privada: List<Genero> listado con todas los Géneros disponibles para su selección al crear o editar una Especie
     */
    private List<Genero> listaGenero;
    /**
     * Variable privada: Origen se instancia con el Origen seleccionado de un combo para asignarlo a la Especie
     */
    private Origen selectedOrigen;
    /**
     * Variable privada: List<Origen> listado con todos los Orígenes disponibles para su selección al crear o editar una Especie
     */
    private List<Origen> listaOrigenes;
    /**
     * Variable privada: Morfología se instancia con la morfología seleccionada de un combo para asignarla a la Especie
     */
    private Morfologia selectedMorf;
    /**
     * Variable privada: List<Morfologia> listado con todas las morfologías disponibles para su selección al crear o editar una Especie
     */
    private List<Morfologia> listaMorfologias;
    /**
     * Variable privada: Cites se instancia con el tipo de Cites seleccionado de un combo para asignarlo a la Especie si corresponde
     */
    private Cites selectedCites;
    /**
     * Variable privada: List<Cites> listado con todos los tipos de Cites disponibles para su selección al crear o editar una Especie, si corresponde
     */
    private List<Cites> listaCites;
    /**
     * Variable privada: Rango se instancia con el tipo de Rango seleccionado de un combo para asignarlo a la Especie
     */
    private Rango selectedRango;
    /**
     * Variable privada: List<Rango> listado con todos los Rantos disponibles para su selección al crear o editar una Especie.
     */
    private List<Rango> listaRangos;
    /**
     * Variable privada: Rango se instancia con el tipo de Rango seleccionado de un combo para asignarlo a la Especie
     */
    private Publicacion selectedPublicacion;
    /**
     * Variable privada: List<Publicacion> listado con todas las Publicaciones disponibles para su selección al crear o editar una Especie.
     */
    private List<Publicacion> listaPublicaciones;
    /**
     * Variable privada: Autor se instancia con el tipo de Autor seleccionado de un combo para asignarlo a la Especie
     */
    private Autor selectedAutor;
    /**
     * Variable privada: List<Autor> listado con todos los Autores disponibles para su selección al crear o editar una Especie.
     */
    private List<Autor> listaAutores;
    /**
     * Variable privada: Entero que indica el tipo de actualización que se hará:
     * 0=updateNormal | 1=deshabiliar | 2=habilitar
     */
    private int update;
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
    public MbEspecie() {
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
                    if(!s.equals("mbLogin")){
                        session.removeAttribute(s);
                    }
                }
            }
        }
    }      

    public Origen getSelectedOrigen() {
        return selectedOrigen;
    }

    public void setSelectedOrigen(Origen selectedOrigen) {
        this.selectedOrigen = selectedOrigen;
    }

    public Morfologia getSelectedMorf() {
        return selectedMorf;
    }

    public void setSelectedMorf(Morfologia selectedMorf) {
        this.selectedMorf = selectedMorf;
    }

    public Cites getSelectedCites() {
        return selectedCites;
    }

    public void setSelectedCites(Cites selectedCites) {
        this.selectedCites = selectedCites;
    }

    public Rango getSelectedRango() {
        return selectedRango;
    }

    public void setSelectedRango(Rango selectedRango) {
        this.selectedRango = selectedRango;
    }

    public Publicacion getSelectedPublicacion() {
        return selectedPublicacion;
    }

    public void setSelectedPublicacion(Publicacion selectedPublicacion) {
        this.selectedPublicacion = selectedPublicacion;
    }

    public Autor getSelectedAutor() {
        return selectedAutor;
    }

    public void setSelectedAutor(Autor selectedAutor) {
        this.selectedAutor = selectedAutor;
    }

    public Genero getSelectedGenero() {
        return selectedGenero;
    }

    public void setSelectedGenero(Genero selectedGenero) {
        this.selectedGenero = selectedGenero;
    }

    public Familia getSelectedFamilia() {
        return selectedFamilia;
    }

    public void setSelectedFamilia(Familia selectedFamilia) {
        this.selectedFamilia = selectedFamilia;
    }

    public List<Origen> getListaOrigenes() {
        return listaOrigenes;
    }

    public void setListaOrigenes(List<Origen> listaOrigenes) {
        this.listaOrigenes = listaOrigenes;
    }

    public List<Morfologia> getListaMorfologias() {
        return listaMorfologias;
    }

    public void setListaMorfologias(List<Morfologia> listaMorfologias) {
        this.listaMorfologias = listaMorfologias;
    }

    public List<Cites> getListaCites() {
        return listaCites;
    }

    public void setListaCites(List<Cites> listaCites) {
        this.listaCites = listaCites;
    }

    public List<Rango> getListaRangos() {
        return listaRangos;
    }

    public void setListaRangos(List<Rango> listaRangos) {
        this.listaRangos = listaRangos;
    }

    public List<Publicacion> getListaPublicaciones() {
        return listaPublicaciones;
    }

    public void setListaPublicaciones(List<Publicacion> listaPublicaciones) {
        this.listaPublicaciones = listaPublicaciones;
    }

    public List<Autor> getListaAutores() {
        return listaAutores;
    }

    public void setListaAutores(List<Autor> listaAutores) {
        this.listaAutores = listaAutores;
    }

    public List<Familia> getListaFamilia() {
        return listaFamilia;
    }

    public void setListaFamilia(List<Familia> listaFamilia) {
        this.listaFamilia = listaFamilia;
    }

    public List<Especie> getListaFilter() {
        return listaFilter;
    }

    public void setListaFilter(List<Especie> listaFilter) {
        this.listaFilter = listaFilter;
    }

    public Especie getCurrent() {
        return current;
    }

    public void setCurrent(Especie current) {
        this.current = current;
    }


    public List<Genero> getListaGenero() {
        return listaGenero;
    }

    public void setListaGenero(List<Genero> listaGenero) {
        this.listaGenero = listaGenero;
    }    
    
    /*******************************
    ** Métodos para la navegación **
    ********************************/
    
    /**
     * Método que instancia la Especie a gestionar
     * @return Especie La entidad gestionada
     */
    public Especie getSelected() {
        if (current == null) {
            current = new Especie();
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
     * Método que instancia la Especie a crear y los listados necesarios 
     * para poblar los combos con los atributos a seleccionar.
     * @return String nombre de la vista para la creación
     */
    public String prepareCreate() {
        listaFamilia = familiaFacade.getActivos();
        listaOrigenes = origenFacade.findAll();
        listaMorfologias = morfologiaFacade.findAll();
        listaCites = citesFacade.findAll();
        listaRangos = rangoFacade.findAll();
        listaPublicaciones = pubFacade.findAll();
        listaAutores = autorFacade.findAll();
                
        current = new Especie();
        return "new";
    }
  
    /**
     * Redireccionamiento a la vista edit para editar una Especie
     * previo poblado de combos e instancido de las entidades "selected"
     * @return String nombre de la vista para la edición
     */
    public String prepareEdit() {
        // cargo todos los listados y el objeto seleccionado
        cargarCombos();

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
     * @return String nombre de la vista del listado
     */
    public String prepareSelect(){
        return "list";
    }
    
    /**
     * Método que prepara la habilitación de una Especie.
     * Setea el tipo de actualización, la ejecuta y resetea el listado
     */      
    public void habilitar() {
        update = 2;
        update();        
        recreateModel();
    }  
    
     /**
     * Método que prepara la deshabilitación de una Especie.
     * Setea el tipo de actualización, la ejecuta y resetea el listado
     */    
    public void deshabilitar() {
          update = 1;
          update();        
          recreateModel();     
    }
    
    /**
     * Método que actualiza el listado de Géneros en función de la Familia seleccionada }
     * que es seteada en la variable correspondiente.
     * @param event ValueChangeEvent evento de cambio de item seleccionado por el usuario
     */
    public void familiaChangeListener(ValueChangeEvent event) {
        selectedFamilia = (Familia) event.getNewValue();
        
        listaGenero = generoFacade.getGenerosXFamilia(selectedFamilia);
    }       
    
    /**
     * Método que setea el Género seleccionado
     * @param event ValueChangeEvent evento de cambio de item seleccionado por el usuario
     */
    public void generoChangeListener(ValueChangeEvent event) {
        selectedGenero = (Genero) event.getNewValue();
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
     * Método para que implementa la creación de una Especie:
     * Instancia la entidad administrativa, valida que no exista una Especie con los mismos datos no repetibles,
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
            if(getFacade().noExiste(current.getGenero(), current.getNombre(), current.getSubEspecie())){
                getFacade().create(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EspecieCreated"));
                return "view";
            }else{
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("EspecieExistente"));
                return null;
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("EspecieCreatedErrorOccured"));
            return null;
        }
    }
    
    /**
     * Método para que implementa la actualización de una Especie, sea para la edición, habilitación o deshabilitación:
     * Actualiza la entidad administrativa según corresponda, procede según el valor de "update",
     * ejecuta el método edit() del facade y devuelve el nombre de la vista detalle o null.
     * @return String nombre de la vista detalle o null
     */
    public String update() {
        Date date = new Date(System.currentTimeMillis());
        Especie esp;

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
        // acualizo según la operación seleccionada
        try {
            switch (update) {
                case 0:
                    if(getFacade().noExiste(current.getGenero(), current.getNombre(), current.getSubEspecie())){
                        // asigno los objetos de los combos
                        current.setGenero(selectedGenero);
                        current.setOrigen(selectedOrigen);
                        current.setMorfologia(selectedMorf);
                        current.setCites(selectedCites);
                        current.setRango(selectedRango);
                        current.setPublicacion(selectedPublicacion);
                        current.setAutores(selectedAutor);
                        // actualizo
                        getFacade().edit(current);
                        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EspecieUpdated"));
                        return "view";
                    }else{
                        esp = getFacade().getExistente(current.getGenero(), current.getNombre(), current.getSubEspecie());
                        if(Objects.equals(esp.getId(), current.getId())){
                            getFacade().edit(current);
                            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EspecieUpdated"));
                            return "view";
                        }else{
                            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("EspecieExistente"));
                            return null;
                        }
                    }
                case 1:
                    getFacade().edit(current);
                    JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EspecieDeshabilitada"));
                    return "view";
                default:
                    getFacade().edit(current);
                    JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EspecieHabilitada"));
                    return "view";
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("EspecieUpdatedErrorOccured"));
            return null;
        }
    }
 
    /*************************
    ** Métodos de selección **
    **************************/

    /**
     * Método para obtener una Especie según su id
     * @param id Long id de la entidad persistida
     * @return Especie la entidad correspondiente
     */
    public Especie getEspecie(java.lang.Long id) {
        return especieFacade.find(id);
    }    
    
    /*********************
    ** Métodos privados **
    **********************/
    /**
     * Método privado que devuelve el facade para el acceso a datos de las Especies
     * @return EJB EspecieFacade Acceso a datos
     */
    private EspecieFacade getFacade() {
        return especieFacade;
    }

    /**
     * método para cargar los objetos selected vinculados a los combos y los respectivos listados para la edición de la especie
     */
    private void cargarCombos() {
        // familia
        selectedFamilia = current.getGenero().getFamilia();
        listaFamilia = familiaFacade.getActivos();
        // género
        selectedGenero = current.getGenero();
        listaGenero = generoFacade.getGenerosXFamilia(selectedFamilia);
        // origen
        selectedOrigen = current.getOrigen();
        listaOrigenes = origenFacade.findAll();
        // Morfología
        selectedMorf = current.getMorfologia();
        listaMorfologias = morfologiaFacade.findAll();
        // Cites
        selectedCites = current.getCites();
        listaCites = citesFacade.findAll();
        // Rangos
        selectedRango = current.getRango();
        listaRangos = rangoFacade.findAll();
        // Publicaciones
        selectedPublicacion = current.getPublicacion();
        listaPublicaciones = pubFacade.findAll();
        // Autores
        selectedAutor = current.getAutores();
        listaAutores = autorFacade.findAll();
    }

    
    /********************************************************************
    ** Converter. Se debe actualizar la entidad y el facade respectivo **
    *********************************************************************/
    @FacesConverter(forClass = Especie.class)
    public static class EspecieControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MbEspecie controller = (MbEspecie) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mbEspecie");
            return controller.getEspecie(getKey(value));
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
            if (object instanceof Especie) {
                Especie o = (Especie) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Especie.class.getName());
            }
        }
    }        


}
