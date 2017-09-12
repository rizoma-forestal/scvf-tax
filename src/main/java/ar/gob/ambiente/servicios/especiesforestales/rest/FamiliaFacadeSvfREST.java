
package ar.gob.ambiente.servicios.especiesforestales.rest;

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

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Familia find(@PathParam("id") Long id) {
        return familiaFacade.find(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Familia> findAll() {
        return familiaFacade.getSvfActivas();
    } 
    
    @GET
    @Path("{id}/generos")
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
