
package ar.gob.ambiente.servicios.especiesforestales.facades;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Especie;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Genero;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase que implementa la abstracta para el acceso a datos de la entidad Especie.
 * @author rincostante
 */
@Stateless
public class EspecieFacade extends AbstractFacade<Especie> {
    
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
    public EspecieFacade() {
        super(Especie.class);
    }

    /**
     * Método que obtiene una Especie según su nombre
     * @param stringParam String nombre de la Especie a buscar
     * @return Especie Especie requerida
     */
    public Especie getXNombre(String stringParam){
        List<Especie> lstEsp;
        em = getEntityManager();
        String queryString = "SELECT esp FROM Especie esp "
                + "WHERE esp.nombre = :stringParam";
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", stringParam);        
        lstEsp = q.getResultList();
        if(lstEsp.isEmpty()){
            return null;
        }else{
            return lstEsp.get(0);
        }
    }    
    
    /**
     * Método que devuelve todas las Especies habilitadas que contienen la cadena recibida como parámetro 
     * dentro de alguno de sus campos string, en este caso el nombre.
     * @param stringParam: cadena que buscará en todos los campos de tipo varchar de la tabla correspondiente
     * @return List<Especie> Listado de todas las Especies encontradas. 
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
    
    /**
     * Método que verifica la existencia en la base de una Especie según los parámetros recibidos
     * @param genero Genero Género al cual pertenece la Especie
     * @param nombre String Nombre de la Especie buscada
     * @param subEspecie String Nombre de la Sub Especie, si existiera
     * @return boolean Verdadero o false según la Especie exista o no en la base
     */   
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
    
    /**
     * Método que obtiene un Especie según los parámetros recibidos 
     * @param genero Genero Género al cual pertenece la Especie
     * @param nombre String Nombre de la Especie buscada
     * @param subEspecie String Nombre de la Sub Especie, si existiera
     * @return Especie Especie que se ajusta a los parametros requeridos
     */
    public Especie getExistente(Genero genero, String nombre, String subEspecie){
        em = getEntityManager();       
        String queryString = "SELECT esp FROM Especie esp "
                + "WHERE esp.nombre = :nombre "
                + "AND esp.subEspecie = :subEspecie "
                + "AND esp.genero = :genero";
        Query q = em.createQuery(queryString)
                .setParameter("nombre", nombre)
                .setParameter("subEspecie", subEspecie)
                .setParameter("genero", genero);
        return (Especie)q.getSingleResult();
    }

    /**
     * Método que devuelve el conjunto los nombres de todas las Especies registradas en la base
     * @return List<String> Listado de los nombres de todas las Especies
     */
    public List<String> getNombre(){
        em = getEntityManager();
        String queryString = "SELECT esp.nombre FROM Especie esp";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }

    /**
     * Método que obtiene todas las Especies registradas en estado habilitado
     * @return List<Especie> Listado de todas las Especies habilitadas
     */
    public List<Especie> getHabilitadas(){
        em = getEntityManager();
        String queryString = "SELECT esp FROM Especie esp "
                + "WHERE esp.adminentidad.habilitado = true";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }
    
    /**
     * Método que devuelve todos las Especies habilitadas (forestales, solo para SACVeFor)
     * A utilizar por API REST
     * @return List<Especie> Listado de las Especies vinculados al SACVeFor
     */
    public List<Especie> getSvfActivas(){
        em = getEntityManager();        
        List<Especie> result;
        String queryString = "SELECT esp FROM Especie esp " 
                + "WHERE esp.adminentidad.habilitado = true "
                + "AND esp.esSacvefor = true "
                + "ORDER BY esp.nombre";                   
        Query q = em.createQuery(queryString);
        result = q.getResultList();
        return result;
    }      
    
    /**
     * Método que devuelve todas las Especies habilitadas pertenecientes a un mismo Género
     * @param id Long Id del Género al cual pertenecen las Especies obtenidas
     * @return List<Especie> Listado de las Especies pertenecientes al Género cuyo id se recibió
     */
    public List<Especie> getXGenero(Long id){
        em = getEntityManager();
        String queryString = "SELECT esp FROM Especie esp "
                + "WHERE esp.genero.id = :id "
                + "AND esp.adminentidad.habilitado = true";
        Query q = em.createQuery(queryString)
                .setParameter("id", id);
        return q.getResultList();
    }
    
    /**
     * Método que devuelve todos las Especies habilitadas (forestales, solo para SACVeFor)
     * a partir del id del Género
     * A utilizar por API REST
     * @param id correspondiente al id del Género
     * @return List<Especie> Listado de las Especies vinculadas al SACVeFor pertenecientes al Género cuyo id se recibió
     */    
    public List<Especie> getSvfEspeciesXIdGenero(Long id){
        em = getEntityManager();
        String queryString = "SELECT esp FROM Especie esp "
                + "WHERE esp.genero.id = :id "
                + "AND esp.adminentidad.habilitado = true "
                + "AND esp.esSacvefor = true "
                + "ORDER BY esp.nombre";
        Query q = em.createQuery(queryString)
                .setParameter("id", id);
        return q.getResultList();
    }    
    
    /**
     * Método que obtiene todas las Especies habilitadas que son sub especies de la recibida
     * @param subespecie String Sub Especie de la cual se obtendrán las Especies requeridas
     * @return List<Especie> Listado de las Especies que son sub especies de la recibida
     */
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
     * Método que obtiene las Especies habilitadas que pertenecen al conjunto de categorías recibido como parámetro.
     * Crea un array de String con los nombres de las categorías a buscar (Autores, Cites, Morfología, Origen, etc)
     * Lee el array y arma una query según los nombres obtenidos de la lectura. Ejecuta la query y devuelve el conjunto de resultados
     * @param categorias String
     * @return List<Especie> Listado de las Especies que pertenecen a las categorías requeridas
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
