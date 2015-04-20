/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.especiesforestales.facades;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Especie;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Genero;
import java.util.HashMap;
import java.util.Iterator;
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
        String queryString = "SELECT esp FROM Especie esp "
                + "WHERE esp.nombre LIKE :stringParam "
                + "AND esp.adminentidad.habilitado =true";
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", "%" + stringParam + "%");
        result = q.getResultList();
        return result;
    }
    
       
    public boolean noExiste(Genero genero, String nombre, String subEspecie){
        em = getEntityManager();       
        String queryString = "SELECT esp.nombre FROM Especie esp "
                + "WHERE esp.nombre = :nombre "
                + "AND esp.subEspecie = :subEspecie "
                + "AND esp.genero = :genero";
        Query q = em.createQuery(queryString)
                .setParameter("nombre", nombre)
                .setParameter("subEspecie", subEspecie)
                .setParameter("genero", genero);
        return q.getResultList().isEmpty();        
    }    
    
    public Especie getExistente(Genero genero, String nombre, String subEspecie){
        em = getEntityManager();       
        String queryString = "SELECT esp.nombre FROM Especie esp "
                + "WHERE esp.nombre = :nombre "
                + "AND esp.subEspecie = :subEspecie "
                + "AND esp.genero = :genero";
        Query q = em.createQuery(queryString)
                .setParameter("nombre", nombre)
                .setParameter("subEspecie", subEspecie)
                .setParameter("genero", genero);
        return (Especie)q.getSingleResult();
    }

    public List<String> getNombre(){
        em = getEntityManager();
        String queryString = "SELECT esp.nombre FROM Especie esp";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }

    public List<Especie> getHabilitadas(){
        em = getEntityManager();
        String queryString = "SELECT esp FROM Especie esp "
                + "WHERE esp.adminentidad.habilitado = true";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }
    
    public List<Especie> getXGenero(Genero genero){
        em = getEntityManager();
        String queryString = "SELECT esp FROM Especie esp "
                + "WHERE esp.genero = :genero "
                + "AND esp.adminentidad.habilitado = true";
        Query q = em.createQuery(queryString)
                .setParameter("genero", genero);
        return q.getResultList();
    }
    
    public List<Especie> getXSubespecie(String subespecie){
        em = getEntityManager();
        String queryString = "SELECT esp FROM Especie esp "
                + "WHERE esp.subEspecie LIKE :subespecie "
                + "AND esp.adminentidad.habilitado = true";
        Query q = em.createQuery(queryString)
                .setParameter("subespecie", "%" + subespecie + "%");
        return q.getResultList();
    }
    
    public List<Especie> getEspeciesXCategorias(List<HashMap<String, Long>> categorias){
        String queryString = "SELECT esp FROM Especie esp "
                + "WHERE esp.adminentidad.habilitado = true ";
        Iterator<HashMap<String, Long>> it = categorias.iterator();
        HashMap<String, Long> hm;
        while(it.hasNext()){
            hm = it.next();
            if(hm.get("autor") != null){
                queryString = queryString + "AND esp.autor = " + hm.get("autor") + " ";
            }
            if(hm.get("cites") != null){
                queryString = queryString + "AND esp.cites = " + hm.get("cites") + " ";
            }
            if(hm.get("morfologia") != null){
                queryString = queryString + "AND esp.morfologia = " + hm.get("morfologia") + " ";
            }
            if(hm.get("origen") != null){
                queryString = queryString + "AND esp.origen = " + hm.get("origen") + " ";
            } 
            if(hm.get("publicacion") != null){
                queryString = queryString + "AND esp.publicacion = " + hm.get("publicacion") + " ";
            }
            if(hm.get("rango") != null){
                queryString = queryString + "AND esp.rango = " + hm.get("rango") + " ";
            }
        }
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }
}
