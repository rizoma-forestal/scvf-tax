
package sacvefor;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase que implementa la abstracta para el acceso a datos de la entidad Género para la migración desde el SACVeFor.
 * @deprecated solo utilizada para la migración desde el SACVeFor, ya realizada
 * @author rincostante
 */
@Stateless
public class Genero_svfFacade extends AbstractFacade<Genero_svf> {

    @PersistenceContext(unitName = "ar.gob.ambiente.servicios_especiesForestales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Genero_svfFacade() {
        super(Genero_svf.class);
    }
    
    public List<Genero_svf> getNoInsertados(){
        em = getEntityManager();
        String queryString = "SELECT gen FROM Genero_svf gen "
                + "WHERE gen.id_insertado IS NULL";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }    
    
    public Genero_svf getById_svf(int id_svf){
        List<Genero_svf> lstGenSvf;
        em = getEntityManager();
        String queryString = "SELECT gen FROM Genero_svf gen "
                + "WHERE gen.id_svf = :id_svf";
        Query q = em.createQuery(queryString)
                .setParameter("id_svf", id_svf);        
        lstGenSvf = q.getResultList();
        if(lstGenSvf.isEmpty()){
            return null;
        }else{
            return lstGenSvf.get(0);
        }
    }       
}
