
package ar.gob.ambiente.servicios.especiesforestales.facades;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Familia;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Genero;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase que implementa la abstracta para el acceso a datos de la entidad Género.
 * @author carmendariz
 */
@Stateless
public class GeneroFacade extends AbstractFacade<Genero> {
    
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
    public GeneroFacade() {
        super(Genero.class);
    }

    /**
     * Método que devuelve todas las Generos que contienen la cadena recibida como parámetro 
     * dentro de alguno de sus campos string, en este caso el nombre.
     * @param stringParam String cadena que buscará en todos los campos de tipo varchar de la tabla correspondiente
     * @return List<Genero> Listado de todos los géneros que contienen el nombre recibido como parámetro
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
     * @param aBuscar String cadena que buscara para ver si ya existe en la BDD
     * @return boolean True o False según exista o no
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
     * @param id Long ID de la entidad
     * @return boolean True o False según tenga o no dependencias
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
     * Metodo que obtiene un listado con los nombres de los Géneros habilitados registrados
     * @return List<String> Listado de los nombres de todos los Géneros registrados
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
     * @return List<Genero> Listado de los Géneros habilitados
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
     * Método que devuelve todos los Géneros habilitados (forestales, solo para SACVeFor)
     * A utilizar por API REST
     * @return List<Genero> Listado de los Géneros vinculados al SACVeFor
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
     * Método que devuelve todos los Géneros habilitados de una familia
     * @param familia Familia Familia a la cual pertenecen los Géneros obtenidos
     * @return List<Genero> Listado de los Géneros vinculados a la Familia recibida
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
     * Método que devuelve los Géneros habilitados según el id de una familia.
     * A utilizar por API REST
     * @param id Long Id de la Familia cuyos Géneros se devuelven
     * @return List<Genero> Listado de los Géneros vinculados a la Familia cuyo id se recibió
     */
    public List<Genero> getGenerosXIdFamilia(Long id){
        em = getEntityManager();        
        String queryString = "SELECT gen FROM Genero gen "
                + "WHERE gen.familia.id = :id "
                + "AND gen.adminentidad.habilitado = true "
                + "ORDER BY gen.nombre";
        Query q = em.createQuery(queryString)
                .setParameter("id", id);
        return q.getResultList();
    }
    
    /**
     * Método que devuelve todos los géneros habilitados (forestales, solo para SACVeFor)
     * a partir del id de la familia
     * A utilizar por API REST
     * @param id Long id de la Familia
     * @return List<Genero> Listado de los Géneros vinculados a la Familia cuyo id se recibió
     */
    public List<Genero> getSvfGenerosXIdFamilia(Long id){
        em = getEntityManager();        
        String queryString = "SELECT gen FROM Genero gen "
                + "WHERE gen.familia.id = :id "
                + "AND gen.adminentidad.habilitado = true "
                + "AND gen.esSacvefor = true "
                + "ORDER BY gen.nombre";
        Query q = em.createQuery(queryString)
                .setParameter("id", id);
        return q.getResultList();
    }    
}
