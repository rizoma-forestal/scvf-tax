
package sacvefor;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase que implementa la abstracta para el acceso a datos de la entidad Especie para la migración desde el SACVeFor.
 * @deprecated solo utilizada para la migración desde el SACVeFor, ya realizada
 * @author rincostante
 */
@Stateless
public class Especie_svfFacade extends AbstractFacade<Especie_svf> {

    @PersistenceContext(unitName = "ar.gob.ambiente.servicios_especiesForestales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Especie_svfFacade() {
        super(Especie_svf.class);
    }
    
    public List<Especie_svf> getNoInsertadas(){
        em = getEntityManager();
        String queryString = "SELECT esp FROM Especie_svf esp "
                + "WHERE esp.id_insertado IS NULL";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }   
}
