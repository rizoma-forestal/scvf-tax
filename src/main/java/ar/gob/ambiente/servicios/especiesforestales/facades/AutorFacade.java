
package ar.gob.ambiente.servicios.especiesforestales.facades;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Autor;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase que implementa la abstracta para el acceso a datos de la entidad Autor.
 * @author rincostante
 */
@Stateless
public class AutorFacade extends AbstractFacade<Autor> {
    
    /**
     * Variable privada: EntityManager al que se le indica la unidad de persistencia mediante la cual accederá a la base de datos
     */
    @PersistenceContext(unitName = "ar.gob.ambiente.servicios_especiesForestales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    /**
     * Método que implementa el abstracto para la obtención del EntityManager
     * @return EntityManager para acceder a datos
     */    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Constructor
     */
    public AutorFacade() {
        super(Autor.class);
    }
    
    /**
     * Metodo que verifica si ya existe la entidad.
     * @param aBuscar: es la cadena que buscara para ver si ya existe en la BDD
     * @return: boolean True o False según exista o no el nombre a buscar
     */
    public boolean existe(String aBuscar){
        em = getEntityManager();       
        String queryString = "SELECT autor.nombre FROM Autor autor "
                + "WHERE autor.nombre = :stringParam ";
        
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", aBuscar);
        return q.getResultList().isEmpty();
    }  
    
    /**
     * Método que verifica si la entidad tiene dependencias
     * @param id: ID de la entidad
     * @return: boolean True o False según tenga o no dependencias
     */
    public boolean noTieneDependencias(Long id){
        em = getEntityManager();       
        String queryString = "SELECT esp FROM Especie esp " 
                + "WHERE esp.autorEspecie.id = :id "
                + "OR esp.autores.id = :id";      
        Query q = em.createQuery(queryString)
                .setParameter("id", id);
        return q.getResultList().isEmpty();
    }      
}
