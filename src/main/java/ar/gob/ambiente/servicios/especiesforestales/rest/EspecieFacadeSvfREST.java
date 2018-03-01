
package ar.gob.ambiente.servicios.especiesforestales.rest;

import ar.gob.ambiente.servicios.especiesforestales.annotation.Secured;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Especie;
import ar.gob.ambiente.servicios.especiesforestales.facades.EspecieFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Servicio que implementa los métodos expuestos por la API REST para la entidad Especie
 * Solo forestales para consulta y consumo de SACVeFor
 * @author rincostante
 */
@Stateless
@Path("svf_especies")
public class EspecieFacadeSvfREST {

    @EJB
    private EspecieFacade especieFacade;
    
    /**
     * @api {get} /svf_especies/:id Ver una Especie
     * @apiExample {curl} Ejemplo de uso:
     *     curl -X GET -d /especiesVegetales/rest/svf_especies/3136 -H "authorization: xXyYvWzZ"
     * @apiVersion 1.0.0
     * @apiName GetEspecie
     * @apiGroup Especies
     * @apiHeader {String} Authorization Token recibido al autenticar el usuario
     * @apiHeaderExample {json} Ejemplo de header:
     *     {
     *       "Authorization": "xXyYvWzZ"
     *     } 
     * @apiParam {Long} id Identificador único de la Especie
     * @apiDescription Método para obtener una Especie existente según el id remitido.
     * Obtiene la especie mediante el método local find(id)
     * @apiSuccess {ar.gob.ambiente.sacvefor.servicios.especies.Especie} Especie Detalle de la especie registrada.
     * @apiSuccessExample Respuesta exitosa:
     *     HTTP/1.1 200 OK
     *     {
     *           {
     *              "id": "3136",
     *              "nombre": "americana",
     *              "genero": 
     *                  {
     *                      "id": "6760",
     *                      "nombre": "Cordia",
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
     *                  "nombreCientifico": "Cordia americana"
     *                  "nombreCompleto": "Boraginaceae Cordia americana"
     *            }
     *     }
     *
     * @apiError EspecieNotFound No existe especie registrada con ese id.
     * @apiErrorExample Respuesta de error:
     *     HTTP/1.1 400 Not Found
     *     {
     *       "error": "No hay especie registrada con el id recibido"
     *     }
     */         
    @GET
    @Secured
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Especie find(@PathParam("id") Long id) {
        return especieFacade.find(id);
    }

    /**
     * @api {get} /svf_especies Ver todas las Especies
     * @apiExample {curl} Ejemplo de uso:
     *     curl -X GET -d /especiesVegetales/rest/svf_especies -H "authorization: xXyYvWzZ"
     * @apiVersion 1.0.0
     * @apiName GetEspecies
     * @apiGroup Especies
     *
     * @apiHeader {String} Authorization Token recibido al autenticar el usuario
     * 
     * @apiHeaderExample {json} Ejemplo de header:
     *     {
     *       "Authorization": "xXyYvWzZ"
     *     } 
     * 
     * @apiDescription Método para obtener un listado de las Especies existentes.
     * Obtiene las especies mediante el método local findAll()
     * 
     * @apiSuccess {ar.gob.ambiente.sacvefor.servicios.especies.Especie} Especie Listado con todos las Especies registradas.
     *
     * @apiSuccessExample Respuesta exitosa:
     *     HTTP/1.1 200 OK
     *     {
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
     *                  "nombreCompleto": "Boraginaceae Cordia americana"
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
     *     }
     *
     * @apiError EspeciesNotFound No existen especies registradas.
     *
     * @apiErrorExample Respuesta de error:
     *     HTTP/1.1 400 Not Found
     *     {
     *       "error": "No hay Especies registradas"
     *     }
     */              
    @GET
    @Secured
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Especie> findAll() {
        return especieFacade.getSvfActivas();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Especie> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return especieFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(especieFacade.count());
    }
}
