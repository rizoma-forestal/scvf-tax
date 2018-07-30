
package ar.gob.ambiente.servicios.especiesforestales.facades;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Cites;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase que implementa la abstracta para el acceso a datos de la entidad Cites.
 * @author rincostante
 */
@Stateless
public class CitesFacade extends AbstractFacade<Cites> {
    
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
    public CitesFacade() {
        super(Cites.class);
    }
    
    /**
     * Metodo que verifica si ya existe la entidad.
     * @param aBuscar: es la cadena que buscara para ver si ya existe en la BDD
     * @return: boolean True o False según exista o no la entidad
     */
    public boolean existe(String aBuscar){
        em = getEntityManager();       
        String queryString = "SELECT cites.nombre FROM Cites cites "
                + "WHERE cites.nombre = :stringParam ";
        
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
                + "WHERE esp.cites.id = :id";      
        Query q = em.createQuery(queryString)
                .setParameter("id", id);
        return q.getResultList().isEmpty();
    }          
    
}
