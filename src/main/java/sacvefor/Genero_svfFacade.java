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
