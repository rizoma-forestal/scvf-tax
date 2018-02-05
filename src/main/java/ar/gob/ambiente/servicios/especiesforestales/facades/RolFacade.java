
package ar.gob.ambiente.servicios.especiesforestales.facades;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Rol;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase que implementa la abstracta para el acceso a datos de la entidad Rol que indica el rol de los usuarios.
 * @author carmendariz
 */
@Stateless
public class RolFacade extends AbstractFacade<Rol> {
    
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
    public RolFacade() {
        super(Rol.class);
    }
/**
     * Método que devuelve todas los Roles que contienen la cadena recibida como parámetro 
     * dentro de alguno de sus campos string, en este caso el nombre.
     * @param stringParam String cadena que buscará en todos los campos de tipo varchar de la tabla correspondiente
     * @return List<Rol> El conjunto de resultados provenientes de la búsqueda. 
     */      
    public List<Rol> getXString(String stringParam){
        em = getEntityManager();
        List<Rol> result;
        
        String queryString = "SELECT rol FROM Rol rol "
                + "WHERE rol.nombre LIKE :stringParam ";
        
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", "%" + stringParam + "%");        
        
        result = q.getResultList();
        return result;
    }

    /**
     * Metodo que verifica si ya existe la entidad.
     * @param aBuscar String cadena que buscara para ver si ya existe en la BDD
     * @return boolean devuelve True o False según el caso
     */
    public boolean existe(String aBuscar){
        em = getEntityManager();       
        String queryString = "SELECT rol.nombre FROM Rol rol "
                + "WHERE rol.nombre = :stringParam ";
        
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", aBuscar);
        return q.getResultList().isEmpty();
    }        
    
    
    /**
     * Método que verifica si la entidad tiene dependencia (Hijos) en estado HABILITADO
     * @param id Long ID de la entidad
     * @return boolean True o False según el caso
     */
    public boolean noTieneDependencias(Long id){
        em = getEntityManager();       
        
        String queryString = "SELECT usu FROM Usuario usu " 
                + "WHERE usu.rol.id = :idParam "
                + "AND usu.adminentidad.habilitado = true";      
        
        Query q = em.createQuery(queryString)
                .setParameter("idParam", id);
        
        return q.getResultList().isEmpty();
    }
    
    /**
    * Metodo que devuelve el listado de los nombres de todos los Roles habilitados registrados
    * @return List<String> listado de los nombres de todos los Roles registrados
    */  
    public List<String> getNombres(){
        em = getEntityManager();
        String queryString = "SELECT rol.nombre FROM Rol rol "
                + "WHERE rol.adminentidad.habilitado = true";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }
    
   /**
     * Método que devuelve un LIST con las entidades HABILITADAS
     * @return List<Rol> Listado de los Roles 
     */
    public List<Rol> getActivos(){
        em = getEntityManager();        
        List<Rol> result;
        String queryString = "SELECT rol FROM Rol rol " 
                + "WHERE rol.adminentidad.habilitado = true";                   
        Query q = em.createQuery(queryString);
        result = q.getResultList();
        return result;
    }    
    
}
