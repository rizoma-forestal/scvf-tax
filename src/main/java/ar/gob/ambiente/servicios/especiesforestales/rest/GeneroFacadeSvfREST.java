
package ar.gob.ambiente.servicios.especiesforestales.rest;

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

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Genero find(@PathParam("id") Long id) {
        return generoFacade.find(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Genero> findAll() {
        return generoFacade.getSvfActivos();
    }
    
    @GET
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
