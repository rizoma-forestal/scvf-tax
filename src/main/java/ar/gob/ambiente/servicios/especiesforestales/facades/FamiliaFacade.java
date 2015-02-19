/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.especiesforestales.facades;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Familia;
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
public class FamiliaFacade extends AbstractFacade<Familia> {
    @PersistenceContext(unitName = "ar.gob.ambiente.servicios_especiesForestales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FamiliaFacade() {
        super(Familia.class);
    }
    
    /**
     * Método que devuelve todas las Familias que contienen la cadena recibida como parámetro 
     * dentro de alguno de sus campos string, en este caso el nombre.
     * @param stringParam: cadena que buscará en todos los campos de tipo varchar de la tabla correspondiente
     * @return: El conjunto de resultados provenientes de la búsqueda. 
     */      
    public List<Familia> getXString(String stringParam){
        em = getEntityManager();
        List<Familia> result;
        
        String queryString = "SELECT fam FROM Familia fam "
                + "WHERE fam.nombre LIKE :stringParam ";
        
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", "%" + stringParam + "%");        
        
        result = q.getResultList();
        return result;
    }
    
    public boolean tieneDependencias(Long id){
        em = getEntityManager();        
        
        String queryString = "SELECT gen FROM Genero gen " 
                + "WHERE gen.familia.id = :idParam";        
        
        Query q = em.createQuery(queryString)
                .setParameter("idParam", id);
        
        return q.getResultList().isEmpty();
    }
    
    
    public boolean existe(String nombre){
        em = getEntityManager();       
        String queryString = "SELECT fam.nombre FROM Familia fam "
                + "WHERE fam.nombre = :nombre";
        Query q = em.createQuery(queryString)
                .setParameter("nombre", nombre);
        return q.getResultList().isEmpty();        
        
    }    

    public List<String> getNombres(){
        em = getEntityManager();
        String queryString = "SELECT fam.nombre FROM Familia fam";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }
}
