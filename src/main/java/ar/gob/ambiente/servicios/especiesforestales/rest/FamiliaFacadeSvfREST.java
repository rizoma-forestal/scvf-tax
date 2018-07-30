
package ar.gob.ambiente.servicios.especiesforestales.rest;

import ar.gob.ambiente.servicios.especiesforestales.annotation.Secured;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Familia;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Genero;
import ar.gob.ambiente.servicios.especiesforestales.facades.FamiliaFacade;
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
 * Servicio que implementa los métodos expuestos por la API REST para la entidad Familia
 * Solo forestales para consulta y consumo de SACVeFor
 * @author rincostante
 */
@Stateless
@Path("svf_familias")
public class FamiliaFacadeSvfREST {

    @EJB
    private FamiliaFacade familiaFacade;
    @EJB
    private GeneroFacade generoFacade;

    /**
     * @api {get} /svf_familias/:id Ver una Familia
     * @apiExample {curl} Ejemplo de uso:
     *     curl -X GET -d /especiesVegetales/rest/svf_familias/2 -H "authorization: xXyYvWzZ"
     * @apiVersion 1.0.0
     * @apiName GetFamilia
     * @apiGroup Familias
     * @apiHeader {String} Authorization Token recibido al autenticar el usuario
     * @apiHeaderExample {json} Ejemplo de header:
     *     {
     *       "Authorization": "xXyYvWzZ"
     *     } 
     * @apiParam {Long} id Identificador único de la Familia
     * @apiDescription Método para obtener una Familia existente según el id remitido.
     * Obtiene la familia mediante el método local find(id)
     * @apiSuccess {ar.gob.ambiente.sacvefor.servicios.especies.Familia} Familia Detalle de la familia registrada.
     * @apiSuccessExample Respuesta exitosa:
     *     HTTP/1.1 200 OK
     *     {
     *       {
     *          "id": "2",
     *          "nombre": "Andreaeaceae",
     *          "subFamilia": "",
     *          "esSacvefor": "true"
     *       }
     *     }
     * @apiError FamiliaNotFound No existe familia registrada con ese id.
     * @apiErrorExample Respuesta de error:
     *     HTTP/1.1 400 Not Found
     *     {
     *       "error": "No hay familia registrada con el id recibido"
     *     }
     */           
    @GET
    @Path("{id}")
    @Secured
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Familia find(@PathParam("id") Long id) {
        return familiaFacade.find(id);
    }

    /**
     * @api {get} /svf_familias Ver todas las Familias
     * @apiExample {curl} Ejemplo de uso:
     *     curl -X GET -d /especiesVegetales/rest/svf_familias -H "authorization: xXyYvWzZ"
     * @apiVersion 1.0.0
     * @apiName GetFamilias
     * @apiGroup Familias
     * @apiHeader {String} Authorization Token recibido al autenticar el usuario
     * @apiHeaderExample {json} Ejemplo de header:
     *     {
     *       "Authorization": "xXyYvWzZ"
     *     } 
     * @apiDescription Método para obtener un listado de las Familias existentes.
     * Obtiene las familias mediante el método local findAll()
     * @apiSuccess {ar.gob.ambiente.sacvefor.servicios.especies.Familia} Familias Listado con todas las Familias registradas.
     * @apiSuccessExample Respuesta exitosa:
     *     HTTP/1.1 200 OK
     *     {
     *       "familias": [
     *          {"id": "2",
     *          "nombre": "Andreaeaceae",
     *          "subFamilia": "",
     *          "esSacvefor": "true"},
     *          {"id": "3",
     *          "nombre": "Andreaeobryaceae",
     *          "subFamilia": "",
     *          "esSacvefor": "true"}
     *          ]
     *     }
     * @apiError FamiliasNotFound No existen familias registradas.
     * @apiErrorExample Respuesta de error:
     *     HTTP/1.1 400 Not Found
     *     {
     *       "error": "No hay Familias registradas"
     *     }
     */      
    @GET
    @Secured
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Familia> findAll() {
        return familiaFacade.getSvfActivas();
    } 
    
    /**
     * @api {get} /svf_familias/:id/generos Ver los Géneros de una Familia
     * @apiExample {curl} Ejemplo de uso:
     *     curl -X GET -d /especiesVegetales/rest/svf_familias/2/generos -H "authorization: xXyYvWzZ"
     * @apiVersion 1.0.0
     * @apiName GetGeneros
     * @apiGroup Familias
     * @apiHeader {String} Authorization Token recibido al autenticar el usuario
     * @apiHeaderExample {json} Ejemplo de header:
     *     {
     *       "Authorization": "xXyYvWzZ"
     *     } 
     * @apiParam {Long} id Identificador único de la Familia
     * @apiDescription Método para obtener los Géneros asociados a una Familia existente según el id remitido.
     * Obtiene los géneros mediante el método local getSvfGenerosXIdFamilia(id)
     * @apiSuccess {ar.gob.ambiente.sacvefor.servicios.especies.Genero} Genero Listado de los Géneros registrados vinculados a la Familia cuyo id se recibió.
     * @apiSuccessExample Respuesta exitosa:
     *     HTTP/1.1 200 OK
     *     {
     *       "generos": [
     *          {"id": "2",
     *          "nombre": "Andreaea",
     *          "familia": 
     *              {
     *                  "id": "1",
     *                  "nombre": "Andreaeaceae",
     *                  "subFamilia": "",
     *                  "esSacvefor": "true"
     *              },
     *          "esSacvefor": "true"}
     *          ]
     *     }
     * @apiError GenerosNotFound No existen géneros registrados vinculados a la id de la familia.
     *
     * @apiErrorExample Respuesta de error:
     *     HTTP/1.1 400 Not Found
     *     {
     *       "error": "No hay géneros registrados vinculados al id de la familia recibido."
     *     }
     */    
    @GET
    @Path("{id}/generos")
    @Secured
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Genero> findByFamilia(@PathParam("id") Long id) {
        return generoFacade.getSvfGenerosXIdFamilia(id);
    }     

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Familia> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return familiaFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(familiaFacade.count());
    }
}
