
package ar.gob.ambiente.servicios.especiesforestales.rest;

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
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Especie find(@PathParam("id") Long id) {
        return especieFacade.find(id);
    }

    @GET
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
