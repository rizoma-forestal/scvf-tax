/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.especiesforestales.facades;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Especie;
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
public class EspecieFacade extends AbstractFacade<Especie> {
    @PersistenceContext(unitName = "ar.gob.ambiente.servicios_especiesForestales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EspecieFacade() {
        super(Especie.class);
    }

    
    /**
     * Método que devuelve todas las Especies que contienen la cadena recibida como parámetro 
     * dentro de alguno de sus campos string, en este caso el nombre.
     * @param stringParam: cadena que buscará en todos los campos de tipo varchar de la tabla correspondiente
     * @return: El conjunto de resultados provenientes de la búsqueda. 
     */      
    public List<Especie> getXString(String stringParam){
        em = getEntityManager();
        List<Especie> result;
        
        String queryString = "SELECT esp.* FROM Especie esp "
                + "WHERE esp.nombre LIKE :stringParam";
        
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", "%" + stringParam + "%");        
        
        result = q.getResultList();
        return result;
    }
    
       
    public boolean existe(String nombre){
        em = getEntityManager();       
        String queryString = "SELECT esp.nombre FROM Especie esp "
                + "WHERE esp.nombre = :nombre";
        Query q = em.createQuery(queryString)
                .setParameter("nombre", nombre);
        return q.getResultList().isEmpty();        
        
    }    

    public List<String> getNombre(){
        em = getEntityManager();
        String queryString = "SELECT esp.nombre FROM Especie esp";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }

}
