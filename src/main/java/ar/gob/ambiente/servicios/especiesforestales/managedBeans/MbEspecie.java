/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
 *
 * @author carmendariz
 */
public class MbEspecie implements Serializable{
   
    private Especie current;
    private DataModel items = null;
    private List<Especie> listaFilter;

    @EJB
    private GeneroFacade generoFacade;
    @EJB
    private FamiliaFacade familiaFacade;
    @EJB
    private EspecieFacade especieFacade;
    @EJB
    private OrigenFacade origenFacade;
    @EJB
    private MorfologiaFacade morfologiaFacade;
    @EJB
    private CitesFacade citesFacade;
    @EJB
    private RangoFacade rangoFacade;
    @EJB
    private PublicacionFacade pubFacade;
    @EJB
    private AutorFacade autorFacade;
    
    private Familia selectedFamilia;
    private Genero selectedGenero;
    
    //private int selectedItemIndex;
    //private String selectParam;    
    //private List<String> listaNombres;  
    private List<Familia> listaFamilia;
    private List<Genero> listaGenero;
    private List<Origen> listaOrigenes;
    private List<Morfologia> listaMorfologias;
    private List<Cites> listaCites;
    private List<Rango> listaRangos;
    private List<Publicacion> listaPublicaciones;
    private List<Autor> listaAutores;
    
    private int update; // 0=updateNormal | 1=deshabiliar | 2=habilitar
    private MbLogin login;
    private Usuario usLogeado;   
    private boolean iniciado;
    /**
     * Creates a new instance of MbEspecie
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
     * @return La entidad gestionada
     */
 
    public Especie getSelected() {
        if (current == null) {
            current = new Especie();
            //selectedItemIndex = -1;
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
     * @return acción para la edición de la entidad
     */
    public String prepareEdit() {
        listaFamilia = familiaFacade.getActivos();
        listaOrigenes = origenFacade.findAll();
        listaMorfologias = morfologiaFacade.findAll();
        listaCites = citesFacade.findAll();
        listaRangos = rangoFacade.findAll();
        listaPublicaciones = pubFacade.findAll();
        listaAutores = autorFacade.findAll();
        
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
        //buscarEspecie();
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
     * @return mensaje que notifica la actualizacion de estado
     */    
    public void deshabilitar() {
          update = 1;
          update();        
          recreateModel();     
    }
    
    /**
     * 
     * @param event
     */
    public void familiaChangeListener(ValueChangeEvent event) {
        Familia fam = (Familia) event.getNewValue();
        
        listaGenero = generoFacade.getGenerosXFamilia(fam);
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
            if(update == 0){
                if(getFacade().noExiste(current.getGenero(), current.getNombre(), current.getSubEspecie())){
                    getFacade().edit(current);
                    JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EspecieUpdated"));
                    return "view";
                }else{
                    esp = getFacade().getExistente(current.getGenero(), current.getNombre(), current.getSubEspecie());
                    if(esp.getId() == current.getId()){
                        getFacade().edit(current);
                        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EspecieUpdated"));
                        return "view";
                    }else{
                        JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("EspecieExistente"));
                        return null;
                    }
                }
            }else if(update == 1){
                getFacade().edit(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EspecieDeshabilitada"));
                return "view";
            }else{
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
     * @param id equivalente al id de la entidad persistida
     * @return la entidad correspondiente
     */
    public Especie getEspecie(java.lang.Long id) {
        return especieFacade.find(id);
    }    
    
    /*********************
    ** Métodos privados **
    **********************/
    /**
     * @return el Facade
     */
    private EspecieFacade getFacade() {
        return especieFacade;
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
