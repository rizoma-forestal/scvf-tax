/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.especiesforestales.facades;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Publicacion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author rincostante
 */
@Stateless
public class PublicacionFacade extends AbstractFacade<Publicacion> {
    @PersistenceContext(unitName = "ar.gob.ambiente.servicios_especiesForestales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PublicacionFacade() {
        super(Publicacion.class);
    }
    
    /**
     * Metodo que verifica si ya existe la entidad.
     * @param nombre
     * @param anio
     * @return: devuelve True o False
     */
    public boolean noExiste(String nombre, int anio){
        em = getEntityManager();       
        String queryString = "SELECT pub.nombre FROM Publicacion pub "
                + "WHERE pub.nombre = :nombre "
                + "AND pub.anio = :anio";
        Query q = em.createQuery(queryString)
                .setParameter("nombre", nombre)
                .setParameter("anio", anio);
        return q.getResultList().isEmpty();
    }  
    
    public Publicacion getExistente(String nombre, int anio){
        em = getEntityManager();       
        String queryString = "SELECT pub FROM Publicacion pub "
                + "WHERE pub.nombre = :nombre "
                + "AND pub.anio = :anio";
        Query q = em.createQuery(queryString)
                .setParameter("nombre", nombre)
                .setParameter("anio", anio);
        return (Publicacion)q.getSingleResult();
    }
    
    /**
     * Método que verifica si la entidad tiene dependencias
     * @param id: ID de la entidad
     * @return: True o False
     */
    public boolean noTieneDependencias(Long id){
        em = getEntityManager();       
        
        String queryString = "SELECT sub FROM SubEspecie sub " 
                + "WHERE sub.publicacion.id = :idParam";      
        
        Query q = em.createQuery(queryString)
                .setParameter("idParam", id);
        
        return q.getResultList().isEmpty();
    }    

    public boolean existe(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
