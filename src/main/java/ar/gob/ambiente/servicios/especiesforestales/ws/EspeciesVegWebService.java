
package ar.gob.ambiente.servicios.especiesforestales.ws;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Autor;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Cites;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Especie;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Familia;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Genero;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Morfologia;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Origen;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Publicacion;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Rango;
import ar.gob.ambiente.servicios.especiesforestales.seervicios.EspeciesServicio;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.util.List;
import javax.ejb.EJB;

/**
 * Servicio web SOAP generado para compartir la información con el SIIF en su etapa de desarrollo
 * @deprecated Este servicio forma parte de la anterior implementación de API de servicios SOAP, reemplazada por la API Rest
 * @author Administrador
 */
@WebService(serviceName="EspeciesVegWebService")
@Stateless()
public class EspeciesVegWebService {
    
    @EJB
    private EspeciesServicio espSrv;

    /**
     * Web service operation que obtiene los Autores registrados
     * @return List<Autor> Listado de Autores
     */
    @WebMethod(operationName = "verAutores")
    public List<Autor> verAutores() {
        return espSrv.getAutores();
    }

    /**
     * Web service operation que obtiene los tipos de Cites registrados
     * @return List<Cites> Listado de tipos de Cites
     */
    @WebMethod(operationName = "verCites")
    public List<Cites> verCites() {
        return espSrv.getCites();
    }
    
    /**
     * Web service operation que obtiene las Morfologías registradas
     * @return List<Morfologia> Listado de Morfologías
     */
    @WebMethod(operationName = "verMorfologias")
    public List<Morfologia> verMorfologias() {
        return espSrv.getMorfologias();
    }    
    
    /**
     * Web service operation que obtiene los Orígenes registrados
     * @return List<Origen> Listado de Orígenes
     */
    @WebMethod(operationName = "verOrigen")
    public List<Origen> verOrigen() {
        return espSrv.getOrigenes();
    }    
    
    /**
     * Web service operation que obtiene las Publicaciones registradas
     * @return List<Publicacion> Listado de Publicaciones
     */
    @WebMethod(operationName = "verPublicacion")
    public List<Publicacion> verPublicacion() {
        return espSrv.getPublicaciones();
    }    
    
    /**
     * Web service operation que obtiene los Rangos registrados
     * @return List<Rango> Listado de Rangos
     */
    @WebMethod(operationName = "verRango")
    public List<Rango> verRango() {
        return espSrv.getRangos();
    }   
    
    /**
     * Web service operation que obtiene las Familias registradas habilitadas
     * @return List<Familia> Listado de Familias registradas
     */
    @WebMethod(operationName = "verFamilias")
    public List<Familia> verFamilias() {
        return espSrv.getFamilias();
    }
    
    /**
     * Web service operation que obtiene los Generos registrados habilitados
     * @return List<Genero> Listado de Generos registrados
     */
    @WebMethod(operationName = "verGeneros")
    public List<Genero> verGeneros() {
        return espSrv.getGeneros();
    }
    
    /**
     * Web service operation que obtiene los Especies registradas habilitadas
     * @return List<Especie> Listado de Especies registradas
     */
    @WebMethod(operationName = "verEspecies")
    public List<Especie> verEspecies() {
        return espSrv.getEspecies();
    }    

    /**
     * Web service operation obtiene una Especie según su id
     * @param id Long id de la Especie
     * @return Especie Especie correspondiente a la id recibida
     */
    @WebMethod(operationName = "buscarEspecie")
    public Especie buscarEspecie(@WebParam(name = "id") Long id) {
        return espSrv.getEspecie(id);
    }

    /**
     * Web service operation obtiene un Género según el id de la Familia a la que pertenece
     * @param idFamilia Long id de la Familia
     * @return List<Genero> listado de Géneros pertenecientes a la Familia cuya id se recibió
     */
    @WebMethod(operationName = "verGenerosPorFamilia")
    public List<Genero> verGenerosPorFamilia(@WebParam(name = "idFamilia") Long idFamilia) {
        return espSrv.getGenerosXFamilia(idFamilia);
    }

    /**
     * Web service operation obtiene una Especie según el id del Género al que pertenece
     * @param idGenero Long id del Género
     * @return List<Especie> listado de las Especies pertenecientes al Género cuyo id se recibió
     */
    @WebMethod(operationName = "verEspeciesPorGenero")
    public List<Especie> verEspeciesPorGenero(@WebParam(name = "idGenero") Long idGenero) {
        return espSrv.getEspeciesXGenero(idGenero);
    }

    /**
     * Web service operation que obtiene todas las Especies que contengan en el nombre la cadena recibida
     * @param nombre String componente del nombre a buscar
     * @return List<Especie> listado de las Especies cuyo nombre contiene la cadena recibida
     */
    @WebMethod(operationName = "buscarEspeciesPorNombre")
    public List<Especie> buscarEspeciesPorNombre(@WebParam(name = "nombre") String nombre) {
        return espSrv.getEspeciesXnombre(nombre);
    }

    /**
     * Web service operation que obtiene las Especies que formen parte de la Subespecie dada
     * @param subespecie String subespecie contenedora de las Especies a buscar
     * @return List<Especie> listado de las Especies pertenecientes a la subespecie dada
     */
    @WebMethod(operationName = "buscarEspeciesPorSubespecie")
    public List<Especie> buscarEspeciesPorSubespecie(@WebParam(name = "subespecie") String subespecie) {
        return espSrv.getEspeciesXSubespecie(subespecie);
    }

    /**
     * Web service operation que obtiene todas las especies que entre sus categorías 
     * (Autores, Cites, Morfología, Origen, etc.) consigne una con alguno de los nombres recibidos
     * @param categorias String cadena que contiene uno o más nombres de categorías separados por ","
     * @return List<Especie> listado de las Especies obtenidas
     */
    @WebMethod(operationName = "buscarEspeciesPorCategorias")
    public List<Especie> buscarEspeciesPorCategorias(@WebParam(name = "categorias") String categorias) {
        return espSrv.getEspeciesXCategorias(categorias);
    }
}
