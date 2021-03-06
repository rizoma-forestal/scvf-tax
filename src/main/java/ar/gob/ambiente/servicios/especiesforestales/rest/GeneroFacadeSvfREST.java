
package ar.gob.ambiente.servicios.especiesforestales.rest;

import ar.gob.ambiente.servicios.especiesforestales.annotation.Secured;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Especie;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Genero;
import ar.gob.ambiente.servicios.especiesforestales.facades.EspecieFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.GeneroFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Servicio que implementa los métodos expuestos por la API REST para la entidad Genero
 * Solo forestales para consulta y consumo de SACVeFor
 * @author rincostante
 */
@Stateless
@Path("svf_generos")
public class GeneroFacadeSvfREST {

    @EJB
    private GeneroFacade generoFacade;
    @EJB
    private EspecieFacade especieFacade;

    /**
     * @api {get} /svf_generos/:id Ver un Género
     * @apiExample {curl} Ejemplo de uso:
     *     curl -i -H "authorization: xXyYvWzZ" -X GET -d /especiesVegetales/rest/svf_generos/1
     * @apiVersion 1.0.0
     * @apiName GetGenero
     * @apiGroup Generos
     * @apiHeader {String} Authorization Token recibido al autenticar el usuario
     * @apiHeaderExample {json} Ejemplo de header:
     *     {
     *       "Authorization": "xXyYvWzZ"
     *     } 
     * @apiParam {Long} id Identificador único del Género
     * @apiDescription Método para obtener un Género existente según el id remitido.
     * Obtiene el género mediante el método local find(id)
     * @apiSuccess {ar.gob.ambiente.sacvefor.servicios.especies.Genero} Genero Detalle del género registrado.
     * @apiSuccessExample Respuesta exitosa:
     *     HTTP/1.1 200 OK
     *     {
     *       {
     *          "id": "1",
     *          "nombre": "Andreaea",
     *          "familia": 
     *              {
     *                  "id": "2",
     *                  "nombre": "Andreaeaceae",
     *                  "subFamilia": "",
     *                  "esSacvefor": "true"
     *              },
     *          "esSacvefor": "true"
     *       }
     *     }
     *
     * @apiError GeneroNotFound No existe género registrado con ese id.
     * @apiErrorExample Respuesta de error:
     *     HTTP/1.1 400 Not Found
     *     {
     *       "error": "No hay género registrado con el id recibido"
     *     }
     */          
    @GET
    @Secured
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Genero find(@PathParam("id") Long id) {
        return generoFacade.find(id);
    }

    /**
     * @api {get} /svf_generos Ver todos los Géneros
     * @apiExample {curl} Ejemplo de uso:
     *     curl -X GET -d /especiesVegetales/rest/svf_generos -H "authorization: xXyYvWzZ"
     * @apiVersion 1.0.0
     * @apiName GetGeneros
     * @apiGroup Generos
     * @apiHeader {String} Authorization Token recibido al autenticar el usuario
     * @apiHeaderExample {json} Ejemplo de header:
     *     {
     *       "Authorization": "xXyYvWzZ"
     *     } 
     * @apiDescription Método para obtener un listado de los Géneros existentes.
     * Obtiene los géneros mediante el método local findAll()
     * @apiSuccess {ar.gob.ambiente.sacvefor.servicios.especies.Genero} Genero Listado con todos los Géneros registrados.
     * @apiSuccessExample Respuesta exitosa:
     *     HTTP/1.1 200 OK
     *     {
     *       "generos": [
     *          {"id": "1",
     *          "nombre": "Andreaea",
     *          "familia": 
     *              {
     *                  "id": "2",
     *                  "nombre": "Andreaeaceae",
     *                  "subFamilia": "",
     *                  "esSacvefor": "true"
     *              },
     *          "esSacvefor": "true"},
     *          {"id": "2",
     *          "nombre": ""Andreaeobryum"",
     *          "familia": 
     *              {
     *                  "id": "3",
     *                  "nombre": ""Andreaeobryaceae"",
     *                  "subFamilia": "",
     *                  "esSacvefor": "true"
     *              },
     *          "esSacvefor": "true"}
     *          ]
     *     }
     * @apiError GenerosNotFound No existen géneros registrados.
     * @apiErrorExample Respuesta de error:
     *     HTTP/1.1 400 Not Found
     *     {
     *       "error": "No hay Géneros registrados"
     *     }
     */          
    @GET
    @Secured
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Genero> findAll() {
        return generoFacade.getSvfActivos();
    }
    
    /**
     * @api {get} /svf_generos/:id/especies Ver las Especies de un Género
     * @apiExample {curl} Ejemplo de uso:
     *     curl -X GET -d /especiesVegetales/rest/svf_generos/6760/especies -H "authorization: xXyYvWzZ"
     * @apiVersion 1.0.0
     * @apiName GetEspecies
     * @apiGroup Generos
     * @apiHeader {String} Authorization Token recibido al autenticar el usuario
     * @apiHeaderExample {json} Ejemplo de header:
     *     {
     *       "Authorization": "xXyYvWzZ"
     *     } 
     * @apiParam {Long} id Identificador único del Género
     * @apiDescription Método para obtener las Especies asociadas a un Género existente según el id remitido.
     * Obtiene las especies mediante el método local getSvfEspeciesXIdGenero(id)
     * @apiSuccess {ar.gob.ambiente.sacvefor.servicios.especies.Especie} Especie Listado de las Especies registradas vinculadas al Género cuyo id se recibió.
     * @apiSuccessExample Respuesta exitosa:
     *     HTTP/1.1 200 OK
     *      {
     *          "especies": [
     *              {
     *                  "id": "3136",
     *                  "nombre": "americana",
     *                  "genero": 
     *                      {
     *                          "id": "6760",
     *                          "nombre": "Cordia",
     *                          "familia": 
     *                              {
     *                                  "id": "309",
     *                                  "nombre": "Boraginaceae",
     *                                  "subFamilia": "",
     *                                  "esSacvefor": "true"
     *                              },
     *                          "esSacvefor": "true"
     *                      },
     *                  "autores":
     *                      {
     *                          "id": "",
     *                          "nombre": ""
     *                      },
     *                  "rango":
     *                      {
     *                          "id": "",
     *                          "nombre": ""
     *                      },
     *                  "subEspecie": "",
     *                  "sinonimo": "",
     *                  "esSacvefor": "true",
     *                  "nombreCientifico": "Cordia americana",
     *                  "nombreCompleto:" "Boraginaceae Cordia americana"
     *              },
     *              {
     *                  "id": "3075",
     *                  "nombre": "trichotoma",
     *                  "genero": 
     *                      {
     *                          "id": "6760",
     *                          "nombre": "Cordia",
     *                          "familia": 
     *                              {
     *                                  "id": "309",
     *                                  "nombre": "Boraginaceae",
     *                                  "subFamilia": "",
     *                                  "esSacvefor": "true"
     *                              },
     *                          "esSacvefor": "true"
     *                      },
     *                  "autores":
     *                      {
     *                          "id": "",
     *                          "nombre": ""
     *                      },
     *                  "rango":
     *                      {
     *                          "id": "",
     *                          "nombre": ""
     *                      },
     *                  "subEspecie": "",
     *                  "sinonimo": "",
     *                  "esSacvefor": "true",
     *                  "nombreCientifico": "Cordia trichotoma",
     *                  "nombreCompleto": "Boraginaceae Cordia trichotoma"
     *              }
     *          ]
     *      }
     * @apiError EspeciesNotFound No existen especies registradas vinculadas a la id del género.
     * @apiErrorExample Respuesta de error:
     *     HTTP/1.1 400 Not Found
     *     {
     *       "error": "No hay especies registradas vinculadas al id del género recibido."
     *     }
     */      
    @GET
    @Secured
    @Path("{id}/especies")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Especie> findByGenero(@PathParam("id") Long id) {
        return especieFacade.getSvfEspeciesXIdGenero(id);
    }      

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Genero> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return generoFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(generoFacade.count());
    }
}
