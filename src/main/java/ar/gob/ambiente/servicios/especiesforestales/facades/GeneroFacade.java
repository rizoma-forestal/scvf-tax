/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.especiesforestales.facades;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Familia;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Genero;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author carmendariz
 */
@Stateless
public class GeneroFacade extends AbstractFacade<Genero> {
    @PersistenceContext(unitName = "ar.gob.ambiente.servicios_especiesForestales_war_1.0-SNAPSHOTPU")
    private EntityManager em;


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GeneroFacade() {
        super(Genero.class);
    }

    /**
     * Método que devuelve todas las Generos que contienen la cadena recibida como parámetro 
     * dentro de alguno de sus campos string, en este caso el nombre.
     * @param stringParam: cadena que buscará en todos los campos de tipo varchar de la tabla correspondiente
     * @return: El conjunto de resultados provenientes de la búsqueda. 
     */      
    public Genero getXNombre(String stringParam){
        List<Genero> lstGen;
        em = getEntityManager();
        String queryString = "SELECT gen FROM Genero gen "
                + "WHERE gen.nombre = :stringParam "
                + "AND gen.adminentidad.habilitado = true";
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", stringParam);        
        lstGen = q.getResultList();
        if(lstGen.isEmpty()){
            return null;
        }else{
            return lstGen.get(0);
        }
    }

    /**
     * Metodo que verifica si ya existe la entidad.
     * @param aBuscar: es la cadena que buscara para ver si ya existe en la BDD
     * @return: devuelve True o False
     */
    public boolean existe(String aBuscar){
        em = getEntityManager();
        String queryString = "SELECT gen.nombre FROM Genero gen "
                + "WHERE gen.nombre = :stringParam "
                + "AND gen.adminentidad.habilitado = true";
        
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", aBuscar);
        return q.getResultList().isEmpty();
    }    
    
    /**
     * Método que verifica si la entidad tiene dependencia (Hijos)
     * @param id: ID de la entidad
     * @return: True o False
     */
    public boolean tieneDependencias(Long id){
        em = getEntityManager();        
        
        String queryString = "SELECT esp FROM Especie esp " 
                + "WHERE esp.genero.id = :idParam ";        
        
        Query q = em.createQuery(queryString)
                .setParameter("idParam", id);
        
        return q.getResultList().isEmpty();
    }
    
    /**
     * Metodo para el autocompletado de la búsqueda por nombre
     * @return 
     */
    public List<String> getNombres(){
        em = getEntityManager();
        String queryString = "SELECT gen.nombre FROM Genero gen "
                + "AND gen.adminentidad.habilitado = true";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    } 
   /**
     * Método que devuelve un LIST con las entidades HABILITADAS
     * @return: True o False
     */
    public List<Genero> getActivos(){
        em = getEntityManager();        
        List<Genero> result;
        String queryString = "SELECT gen FROM Genero gen " 
                + "WHERE gen.adminentidad.habilitado = true";                   
        Query q = em.createQuery(queryString);
        result = q.getResultList();
        return result;
    }   
    
    /**
     * Método que devuelve todos los Géneros (forestales, solo para SACVeFor)
     * A utilizar por API REST
     * @return los Géneros vinculados al SACVeFor
     */
    public List<Genero> getSvfActivos(){
        em = getEntityManager();        
        List<Genero> result;
        String queryString = "SELECT gen FROM Genero gen " 
                + "WHERE gen.adminentidad.habilitado = true "
                + "AND gen.esSacvefor = true "
                + "ORDER BY gen.nombre";                   
        Query q = em.createQuery(queryString);
        result = q.getResultList();
        return result;
    }    
    
    /**
     * Método que devuelve todos los géneros de una familia
     * @param familia
     * @return 
     */
    public List<Genero> getGenerosXFamilia(Familia familia){
        em = getEntityManager();        
        String queryString = "SELECT gen FROM Genero gen "
                + "WHERE gen.familia = :familia "
                + "AND gen.adminentidad.habilitado = true";
        Query q = em.createQuery(queryString)
                .setParameter("familia", familia);
        return q.getResultList();
    }

    /**
     * Método que devuelve el género según el id de una familia.
     * A utilizar en el servcio
     * @param id
     * @return 
     */
    public List<Genero> getGenerosXIdFamilia(Long id){
        em = getEntityManager();        
        String queryString = "SELECT gen FROM Genero gen "
                + "WHERE gen.familia.id = :id "
                + "AND gen.adminentidad.habilitado = true";
        Query q = em.createQuery(queryString)
                .setParameter("id", id);
        return q.getResultList();
    }
    
    /**
     * Método que devuelve todos los géneros (forestales, solo para SACVeFor)
     * a partir del id de la familia
     * A utilizar por API REST
     * @param id correspondiente al id de la Familia
     * @return los Géneros correspondientes al id de la Familia solicitado
     */
    public List<Genero> getSvfGenerosXIdFamilia(Long id){
        em = getEntityManager();        
        String queryString = "SELECT gen FROM Genero gen "
                + "WHERE gen.familia.id = :id "
                + "AND gen.adminentidad.habilitado = true "
                + "AND gen.esSacvefor = true";
        Query q = em.createQuery(queryString)
                .setParameter("id", id);
        return q.getResultList();
    }    
}
