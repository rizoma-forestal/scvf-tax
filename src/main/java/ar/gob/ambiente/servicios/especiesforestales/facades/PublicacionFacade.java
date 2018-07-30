
package ar.gob.ambiente.servicios.especiesforestales.facades;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Publicacion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase que implementa la abstracta para el acceso a datos de la entidad Publicación.
 * @author rincostante
 */
@Stateless
public class PublicacionFacade extends AbstractFacade<Publicacion> {
    
    /**
     * Variable privada: EntityManager al que se le indica la unidad de persistencia mediante la cual accederá a la base de datos
     */ 
    @PersistenceContext(unitName = "ar.gob.ambiente.servicios_especiesForestales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    /**
     * Método que implementa el abstracto para la obtención del EntityManager
     * @return EntityManager para acceder a datos
     */   
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Constructor
     */
    public PublicacionFacade() {
        super(Publicacion.class);
    }
    
    /**
     * Metodo que verifica si ya existe la entidad.
     * @param nombre String nombre de la publicación a buscar
     * @param anio Entero año de la publicación a buscar
     * @return boolean devuelve True o False según el caso
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
    
    /**
     * Método que devuelve una Publicación existente con el nombre y año recibidos como parámetros
     * @param nombre String nombre de la Publicación
     * @param anio Entero año de la Publicación
     * @return Publicacion Publicación con el nombre y el año recibidos
     */
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
     * @param id Long ID de la entidad
     * @return boolean True o False según tenga o no tenga dependencias
     */
    public boolean noTieneDependencias(Long id){
        em = getEntityManager();       
        
        String queryString = "SELECT sub FROM SubEspecie sub " 
                + "WHERE sub.publicacion.id = :idParam";      
        
        Query q = em.createQuery(queryString)
                .setParameter("idParam", id);
        
        return q.getResultList().isEmpty();
    }    

    /**
     * Método que valida si existe una Publicación con el nombre recibido
     * @param nombre String nombre de la Publicación
     * @return boolean True o False según exista o no la Publicación
     */
    public boolean noExisteXNombre(String nombre) {
        em = getEntityManager();       
        String queryString = "SELECT pub.nombre FROM Publicacion pub "
                + "WHERE pub.nombre = :nombre";
        Query q = em.createQuery(queryString)
                .setParameter("nombre", nombre);
        return q.getResultList().isEmpty();
    }
}
