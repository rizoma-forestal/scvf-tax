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
public class Familia_svfFacade extends AbstractFacade<Familia_svf> {

    @PersistenceContext(unitName = "ar.gob.ambiente.servicios_especiesForestales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Familia_svfFacade() {
        super(Familia_svf.class);
    }
    
    public List<Familia_svf> getNoInsertadas(){
        em = getEntityManager();
        String queryString = "SELECT fam FROM Familia_svf fam "
                + "WHERE fam.id_insertado IS NULL";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }
    
    public Familia_svf getByNombre(String nombre){
        List<Familia_svf> lstFamSvf;
        em = getEntityManager();
        String queryString = "SELECT fam FROM Familia_svf fam "
                + "WHERE fam.nombre = :nombre";
        Query q = em.createQuery(queryString)
                .setParameter("nombre", nombre);        
        lstFamSvf = q.getResultList();
        if(lstFamSvf.isEmpty()){
            return null;
        }else{
            return lstFamSvf.get(0);
        }
    }
    
    public Familia_svf getById_svf(int id_svf){
        List<Familia_svf> lstFamSvf;
        em = getEntityManager();
        String queryString = "SELECT fam FROM Familia_svf fam "
                + "WHERE fam.id_svf = :id_svf";
        Query q = em.createQuery(queryString)
                .setParameter("id_svf", id_svf);        
        lstFamSvf = q.getResultList();
        if(lstFamSvf.isEmpty()){
            return null;
        }else{
            return lstFamSvf.get(0);
        }
    }    
    
}
