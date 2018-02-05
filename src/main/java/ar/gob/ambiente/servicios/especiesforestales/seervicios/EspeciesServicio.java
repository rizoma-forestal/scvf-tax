
package ar.gob.ambiente.servicios.especiesforestales.seervicios;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Autor;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Cites;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Especie;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Familia;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Genero;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Morfologia;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Origen;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Publicacion;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Rango;
import ar.gob.ambiente.servicios.especiesforestales.facades.AutorFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.CitesFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.EspecieFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.FamiliaFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.GeneroFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.MorfologiaFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.OrigenFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.PublicacionFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.RangoFacade;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * EJB que actúa como interface de servicios de acceso a datos expuesta para ser consumida por el web service EspeciesVegWebService
 * @author Administrador
 * @deprecated Esta clase forma parte de la anterior implementación de API de servicios SOAP, reemplazada por la API Rest
 */
@Stateless
@LocalBean
public class EspeciesServicio{
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Especies
     */
    @EJB
    private EspecieFacade especieFacade;
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Especies
     */
    @EJB
    private FamiliaFacade familiaFacade;
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Especies
     */
    @EJB
    private GeneroFacade generoFacade;
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Especies
     */
    @EJB
    private AutorFacade autorFacade;
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Especies
     */
    @EJB
    private CitesFacade citesFacade;
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Especies
     */
    @EJB
    private MorfologiaFacade morfologiaFacade;
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Especies
     */
    @EJB
    private OrigenFacade origenFacade;
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Especies
     */
    @EJB
    private PublicacionFacade publicacionFacade;
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Especies
     */
    @EJB
    private RangoFacade rangoFacade;
    /**
     * Variable privada para el logeo en el servidor
     */
    private static final Logger logger = Logger.getLogger(Especie.class.getName());

    /**
     * Método que obtiene los autores registrados mediente el método findAll()
     * Inscribe en el log del server el resultado de la operación
     * @return List<Autor> Listado de los autores registrados
     */
    public List<Autor> getAutores() {
        List<Autor> lstAutores = new ArrayList();
        Date date;
        try{
            lstAutores = autorFacade.findAll();
            logger.log(Level.INFO, "Ejecutando el método getAutores() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getAutores() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstAutores;
    }

    /**
     * Método que obtiene los tipos de Cites registrados mediente el método findAll()
     * Inscribe en el log del server el resultado de la operación
     * @return List<Cites> Listado de los tipos de Cites registrados
     */    
    public List<Cites> getCites() {
        List<Cites> lstCites = new ArrayList();
        Date date;
        try{
            lstCites = citesFacade.findAll();
            logger.log(Level.INFO, "Ejecutando el método getCites() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getCites() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstCites;
    }

    /**
     * Método que obtiene las Morfologías registradas mediente el método findAll()
     * Inscribe en el log del server el resultado de la operación
     * @return List<Morfologia> Listado de las Morfologías registradas
     */    
    public List<Morfologia> getMorfologias() {
        List<Morfologia> lstMorfologias = new ArrayList();
        Date date;
        try{
            lstMorfologias = morfologiaFacade.findAll();
            logger.log(Level.INFO, "Ejecutando el método getMorfologias() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getMorfologias() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstMorfologias;
    }

    /**
     * Método que obtiene los Orígenes registrados mediente el método findAll()
     * Inscribe en el log del server el resultado de la operación
     * @return List<Origen> Listado de los Orígenes registrados
     */
    public List<Origen> getOrigenes() {
        List<Origen> lstOrigenes = new ArrayList();
        Date date;
        try{
            lstOrigenes = origenFacade.findAll();
            logger.log(Level.INFO, "Ejecutando el método getOrigenes() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getOrigenes() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstOrigenes;
    }

    /**
     * Método que obtiene las Publicaciones registradas mediente el método findAll()
     * Inscribe en el log del server el resultado de la operación
     * @return List<Publicacion> Listado de las Publicaciones registradas
     */       
    public List<Publicacion> getPublicaciones() {
        List<Publicacion> lstPublicaciones = new ArrayList();
        Date date;
        try{
            lstPublicaciones = publicacionFacade.findAll();
            logger.log(Level.INFO, "Ejecutando el método getPublicaciones() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getPublicaciones() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstPublicaciones;
    }

    /**
     * Método que obtiene los Rangos registrados mediente el método findAll()
     * Inscribe en el log del server el resultado de la operación
     * @return List<Rango> Listado de los Rangos registrados
     */    
    public List<Rango> getRangos() {
        List<Rango> lstRangos = new ArrayList();
        Date date;
        try{
            lstRangos = rangoFacade.findAll();
            logger.log(Level.INFO, "Ejecutando el método getRangos() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getRangos() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstRangos;
    }

    /**
     * Método que obtiene las Especies registradas habilitadas mediente el método getHabilitadas()
     * Inscribe en el log del server el resultado de la operación
     * @return List<Especie> Listado de las Especies registradas habilitadas
     */      
    public List<Especie> getEspecies() {
        List<Especie> lstEspecies = new ArrayList();
        Date date;
        try{
            lstEspecies = especieFacade.getHabilitadas();
            logger.log(Level.INFO, "Ejecutando el método getEspecies() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getEspecies() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstEspecies;
    }

    /**
     * Método que obtiene las Familias registradas habilitadas mediente el método getActivos()
     * Inscribe en el log del server el resultado de la operación
     * @return List<Familia> Listado de las Familias registradas habilitadas
     */     
    public List<Familia> getFamilias() {
        List<Familia> lstFamilias = new ArrayList();
        Date date;
        try{
            lstFamilias = familiaFacade.getActivos();
            logger.log(Level.INFO, "Ejecutando el método getFamilias() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getFamilias() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstFamilias;
    }

    /**
     * Método que obtiene los Generos registrados habilitados mediente el método getActivos()
     * Inscribe en el log del server el resultado de la operación
     * @return List<Genero> Listado de los Generos registrados habilitados
     */        
    public List<Genero> getGeneros() {
        List<Genero> lstGeneros = new ArrayList();
        Date date;
        try{
            lstGeneros = generoFacade.getActivos();
            logger.log(Level.INFO, "Ejecutando el método getGeneros() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getFamilias() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstGeneros;
    }
    
    /**
     * Método que obtiene los Generos registrados habilitados 
     * según el id de la Familia a la que pertenecien mediente el método getGenerosXIdFamilia(Long id)
     * Inscribe en el log del server el resultado de la operación
     * @param id Long id de la Familia
     * @return List<Genero> Listado de los Generos registrados habilitados pertenecientes al id de la Familia solicitado
     */    
    public List<Genero> getGenerosXFamilia(Long id) {
        List<Genero> lstGeneros = new ArrayList();
        Date date;
        try{
            lstGeneros = generoFacade.getGenerosXIdFamilia(id);
            logger.log(Level.INFO, "Ejecutando el método getGenerosXFamilia() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getGenerosXFamilia() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstGeneros;
    }

    /**
     * Método que obtiene las Especies registradas habilitadas 
     * según el id del Género al que pertenecien mediente el método getXGenero(Long id)
     * Inscribe en el log del server el resultado de la operación
     * @param idGenero Long id del Género
     * @return List<Especie> Listado de las Especies registradas habilitadas pertenecientes al id del Género solicitado
     */      
    public List<Especie> getEspeciesXGenero(Long idGenero) {
        List<Especie> lstEspecies = new ArrayList();
        Date date;
        try{
            lstEspecies = especieFacade.getXGenero(idGenero);
            logger.log(Level.INFO, "Ejecutando el método getEspeciesXGenero() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getEspeciesXGenero() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstEspecies;        
    }

    /**
     * Método que obtiene las Especies registradas habilitadas según su nombre, mediente el método getXString(String nombre)
     * Inscribe en el log del server el resultado de la operación
     * @param cadena String parte del nombre a buscar
     * @return List<Especie> Listado de las Especies registradas habilitadas que contengan el nombre dado
     */       
    public List<Especie> getEspeciesXnombre(String cadena) {
        List<Especie> lstEspecies = new ArrayList();
        Date date;
        try{
            lstEspecies = especieFacade.getXString(cadena);
            logger.log(Level.INFO, "Ejecutando el método getEspeciesXnombre() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getEspeciesXnombre() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstEspecies;
    }

    /**
     * Método que obtiene las Especies registradas habilitadas según se vincule a la sub espeice recibida,
     * mediente el método getXSubespecie(String nombre)
     * Inscribe en el log del server el resultado de la operación
     * @param subespecie String nombre de la sub especie
     * @return List<Especie> Listado de las Especies registradas habilitadas vinculadas a la sub especie dada
     */        
    public List<Especie> getEspeciesXSubespecie(String subespecie) {
        List<Especie> lstEspecies = new ArrayList();
        Date date;
        try{
            lstEspecies = especieFacade.getXSubespecie(subespecie);
            logger.log(Level.INFO, "Ejecutando el método getEspeciesXSubespecie() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getEspeciesXSubespecie() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstEspecies; 
    }

    /**
     * Método que obtiene las Especies registradas habilitadas que pertenezcan a alguna de las categorías recibidas,
     * mediente el método getEspeciesXCategorias(String categorias).
     * Se entiende como categoría: Autores, Cites, Morfología, Origen, etc.
     * Inscribe en el log del server el resultado de la operación
     * @param categorias String nombre de Autores, Cites, Morfología, Origen, etc
     * @return List<Especie> Listado de las Especies registradas habilitadas que pertenezcan a alguna de las categorías recibidas
     */       
    public List<Especie> getEspeciesXCategorias(String categorias) {
        List<Especie> lstEspecies = new ArrayList();
        Date date;
        try{
            lstEspecies = especieFacade.getEspeciesXCategorias(categorias);
            logger.log(Level.INFO, "Ejecutando el método getEspeciesXCategorias() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getEspeciesXCategorias() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstEspecies; 
    }

    /**
     * Método que obtiene una Espece según su id, mediante el método find(Long id)
     * @param id Long identificador de la Especie buscada
     * @return Especie Especie correspondiente al id recibido
     */
    public Especie getEspecie(Long id) {
        Especie esp;
        Date date;
        try{
            esp = especieFacade.find(id);
            logger.log(Level.INFO, "Ejecutando el método getEspecie() desde el servicio");
            return esp;
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getEspecie() desde el servicio de Especies vegetales. " + date + ". ", ex);
            return null;
        }
    }
}
