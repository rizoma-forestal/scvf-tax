/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.especiesforestales.facades;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Especie;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Genero;
import ar.gob.ambiente.servicios.especiesforestales.entidades.util.Categorias;
import ar.gob.ambiente.servicios.especiesforestales.entidades.util.ParItemValor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
    
    public List<Especie> getXGenero(Long id){
        em = getEntityManager();
        String queryString = "SELECT esp FROM Especie esp "
                + "WHERE esp.genero.id = :id "
                + "AND esp.adminentidad.habilitado = true";
        Query q = em.createQuery(queryString)
                .setParameter("id", id);
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
    
    /**
     *
     * @param categorias
     * @return
     */
    public List<Especie> getEspeciesXCategorias(String categorias){
        String strValor;
        Map<Integer, String> mValores = new HashMap<>();
        Integer key;
        String queryString = "SELECT esp FROM Especie esp "
                + "WHERE esp.adminentidad.habilitado = true ";
        
        // armo un array con los valores recibidos
        String[] arrayValores = categorias.split(",");
        
        // recorro el array y guardo en un map los pares cuyo valor no es "0"
        for (int i = 0; i < arrayValores.length; i++) {
            strValor = arrayValores[i];
                if(!strValor.equals("0")){
                    mValores.put(i, strValor);
            }
        }
         
        // recorro el map para construir la query
        Iterator it = mValores.keySet().iterator();
        while(it.hasNext()){
            key = (Integer)it.next();
            if(key == 0){
                queryString = queryString + "AND esp.autores.id = " + Long.valueOf(mValores.get(key)) + " ";
            }
            if(key == 1){
                queryString = queryString + "AND esp.cites.id = " + Long.valueOf(mValores.get(key)) + " ";
            }
            if(key == 2){
                queryString = queryString + "AND esp.morfologia.id = " + Long.valueOf(mValores.get(key)) + " ";
            }
            if(key == 3){
                queryString = queryString + "AND esp.origen.id = " + Long.valueOf(mValores.get(key)) + " ";
            }
            if(key == 4){
                queryString = queryString + "AND esp.publicacion.id = " + Long.valueOf(mValores.get(key)) + " ";
            }
            if(key == 5){
                queryString = queryString + "AND esp.rango.id = " + Long.valueOf(mValores.get(key)) + " ";
            }
        }
        Query q = em.createQuery(queryString);
        return q.getResultList();        
    }
}
