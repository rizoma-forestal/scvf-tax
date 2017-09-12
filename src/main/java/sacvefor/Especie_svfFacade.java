/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sacvefor;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
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
