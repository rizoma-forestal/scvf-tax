
package ar.gob.ambiente.servicios.especiesforestales.rest;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 * Configuración de la API, en principio incluye solo las Especies forestales
 * para consumo de SACVeFor
 * @author rincostante
 */
@javax.ws.rs.ApplicationPath("rest")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ar.gob.ambiente.servicios.especiesforestales.rest.EspecieFacadeSvfREST.class);
        resources.add(ar.gob.ambiente.servicios.especiesforestales.rest.FamiliaFacadeSvfREST.class);
        resources.add(ar.gob.ambiente.servicios.especiesforestales.rest.GeneroFacadeSvfREST.class);
    }
    
}
