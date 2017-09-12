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
 * @author carmendariz
 */
@Stateless
public class FamiliaFacade extends AbstractFacade<Familia> {
    @PersistenceContext(unitName = "ar.gob.ambiente.servicios_especiesForestales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    /**
     *
     */
    public FamiliaFacade() {
        super(Familia.class);
    }
    
    /**
     * Método que devuelve todas las Familias que contienen la cadena recibida como parámetro 
     * dentro de alguno de sus campos string, en este caso el nombre.
     * @param stringParam: cadena que buscará en todos los campos de tipo varchar de la tabla correspondiente
     * @return: El conjunto de resultados provenientes de la búsqueda. 
     */      
    public Familia getXNombre(String stringParam){
        List<Familia> lstFam;
        em = getEntityManager();
        String queryString = "SELECT fam FROM Familia fam "
                + "WHERE fam.nombre = :stringParam";
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", stringParam);        
        lstFam = q.getResultList();
        if(lstFam.isEmpty()){
            return null;
        }else{
            return lstFam.get(0);
        }
    }

    /**
     * Metodo que verifica si ya existe la entidad.
     * @param aBuscar: es la cadena que buscara para ver si ya existe en la BDD
     * @return: devuelve True o False
     */
    public boolean existe(String aBuscar){
        em = getEntityManager();       
        String queryString = "SELECT fam.nombre FROM Familia fam "
                + "WHERE fam.nombre = :stringParam ";
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", aBuscar);
        return q.getResultList().isEmpty();
    }      
    
    /**
     * Metodo que verifica si ya existe la entidad.
     * @param aBuscar: es la cadena que buscara para ver si ya existe en la BDD
     * @return: devuelve True o False
     */
    public Familia getExistente(String aBuscar){
        em = getEntityManager();       
        String queryString = "SELECT fam.nombre FROM Familia fam "
                + "WHERE fam.nombre = :stringParam ";
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", aBuscar);
        return (Familia)q.getResultList();
    }        
    
    
    /**
     * Método que verifica si la entidad tiene dependencia (Hijos) en estado HABILITADO
     * @param id: ID de la entidad
     * @return: True o False
     */
    public boolean noTieneDependencias(Long id){
        em = getEntityManager();       
        
        String queryString = "SELECT gen FROM Genero gen " 
                + "WHERE gen.familia.id = :idParam "
                + "AND gen.adminentidad.habilitado = true";      
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
        String queryString = "SELECT fam.nombre FROM Familia fam "
                + "WHERE fam.adminentidad.habilitado = true";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }
    
   /**
     * Método que devuelve un LIST con las entidades HABILITADAS
     * @return: True o False
     */
    public List<Familia> getActivos(){
        em = getEntityManager();        
        List<Familia> result;
        String queryString = "SELECT fam FROM Familia fam " 
                + "WHERE fam.adminentidad.habilitado = true "
                + "ORDER BY fam.nombre";                  
        Query q = em.createQuery(queryString);
        result = q.getResultList();
        return result;
    }    

    /**
     * Método que devuelve todos las Familias (forestales, solo para SACVeFor)
     * A utilizar por API REST
     * @return las Familias vinculadas al SACVeFor
     */
    public List<Familia> getSvfActivas(){
        em = getEntityManager();        
        List<Familia> result;
        String queryString = "SELECT fam FROM Familia fam " 
                + "WHERE fam.adminentidad.habilitado = true "
                + "AND fam.esSacvefor = true "
                + "ORDER BY fam.nombre";                   
        Query q = em.createQuery(queryString);
        result = q.getResultList();
        return result;
    }
}
