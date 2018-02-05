
package ar.gob.ambiente.servicios.especiesforestales.facades;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase que implementa la abstracta para el acceso a datos de la entidad Usuario.
 * @author rincostante
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {
    
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
    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    /**
     * Método para validad que no exista un Usuario con ese nombre
     * @param nombre String login del usuario
     * @return boolean True o False según el caso
     */
    public boolean noExiste(String nombre){
        em = getEntityManager();
        String queryString = "SELECT us FROM Usuario us "
                + "WHERE us.nombre = :nombre";
        Query q = em.createQuery(queryString)
                .setParameter("nombre", nombre);
        return q.getResultList().isEmpty();
    }        
    
    /**
     * Método que valida si una contraseña ya está en uso
     * @param clave String contraseña encriptada
     * @return boolean True o False según el caso
     */
    public boolean verificarContrasenia(String clave){
        em = getEntityManager();
        String queryString = "SELECT us FROM Usuario us "
                + "WHERE us.calve = :clave";
        Query q = em.createQuery(queryString)
                .setParameter("clave", clave);
        return q.getResultList().isEmpty();
    }    

    /**
    * Metodo para devuelve el conjunto de todos los nombres (login) de los usuarios habilitados
    * @return List<String> Listado de los nombres de los usuarios
    */  
    public List<String> getNombres(){
        em = getEntityManager();
        String queryString = "SELECT us.nombre FROM Usuario us "
                + "WHERE us.adminentidad.habilitado = true";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }
    
   /**
     * Método que devuelve un LIST con las entidades HABILITADAS
     * @return List<Usuario> Listado de los usuarios
     */
    public List<Usuario> getActivos(){
        em = getEntityManager();        
        List<Usuario> result;
        String queryString = "SELECT us FROM Usuario us " 
                + "WHERE us.adminentidad.habilitado = true";                   
        Query q = em.createQuery(queryString);
        result = q.getResultList();
        return result;
    }  
    
    /**
     * Método que devuelve los datos del usuario logeado
     * @param nombre String nombre del usuario
     * @return Usuario Usuario vinculado al nombre recibido o null
     */
    public Usuario getUsuario(String nombre){
        em = getEntityManager();
        List<Usuario> lUs;
        String queryString = "SELECT us FROM Usuario us "
                + "WHERE us.nombre = :nombre";
        Query q = em.createQuery(queryString)
                .setParameter("nombre", nombre);
        lUs = q.getResultList();
        if(!lUs.isEmpty()){
            return lUs.get(0);
        }else{
            return null;
        }
    }
    
    /**
     * Método que valida que el usuario recibido está registrado como usuario de la API
     * @param nombre String Nombre del usuario recibido, enviado por le cliente.
     * @return boolean Verdadero o falso según el caso
     */
    public boolean validarUsuarioApi(String nombre){
        em = getEntityManager();
        String queryString = "SELECT us FROM Usuario us "
                + "WHERE us.nombre = :nombre "
                + "AND us.rol.nombre = 'rest_client'";
        Query q = em.createQuery(queryString)
                .setParameter("nombre", nombre);
        return !q.getResultList().isEmpty();
    }
}
