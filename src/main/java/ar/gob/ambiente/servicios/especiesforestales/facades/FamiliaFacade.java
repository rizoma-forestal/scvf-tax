
package ar.gob.ambiente.servicios.especiesforestales.facades;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Familia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase que implementa la abstracta para el acceso a datos de la entidad Familia.
 * @author carmendariz
 */
@Stateless
public class FamiliaFacade extends AbstractFacade<Familia> {
    
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
    public FamiliaFacade() {
        super(Familia.class);
    }
    
    /**
     * Método que devuelve todas las Familias que contienen la cadena recibida como parámetro 
     * dentro de alguno de sus campos string, en este caso el nombre.
     * @param stringParam String cadena que buscará en todos los campos de tipo varchar de la tabla correspondiente
     * @return List<Familia> Listado de todas las Familias encontradas
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
     * @param aBuscar String cadena que buscara para ver si ya existe en la BDD
     * @return boolean True o False según exista o no
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
     * Metodo que devuelve la Familia cuyo nombre se recibe como parámetro
     * @param aBuscar String nombre de la Familia a buscar
     * @return Familia Familia obtenida o null
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
     * @param id Long ID de la entidad
     * @return boolean True o False según tenga o no dependencias
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
     * Metodo que obtiene un listado con los nombres de las Familias habilitadas registradas
     * @return List<String> Listado de los nombres de todas las Familias registradas
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
     * @return List<Familia> Listado de las Familias habilitadas
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
     * @return List<Familia> las Familias vinculadas al SACVeFor
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
