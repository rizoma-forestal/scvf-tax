/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.especiesforestales.facades;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author rincostante
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {
    @PersistenceContext(unitName = "ar.gob.ambiente.servicios_especiesForestales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    /**
     * Método para validad que no exista un Usuario con ese nombre
     * @param nomobre
     * @return 
     */
    public boolean noExiste(String nomobre){
        em = getEntityManager();
        String queryString = "SELECT us FROM Usuario us "
                + "WHERE us.nombre = :nomobre";
        Query q = em.createQuery(queryString)
                .setParameter("nomobre", nomobre);
        return q.getResultList().isEmpty();
    }        
    
    /**
     * Método que valida si una contraseña ya está en uso
     * @param clave: contraseña encriptada
     * @return 
     */
    public boolean verificarContrasenia(String clave){
        em = getEntityManager();
        String queryString = "SELECT us FROM Usuario us "
                + "WHERE us.calve = :clave";
        Query q = em.createQuery(queryString)
                .setParameter("clave", clave);
        return q.getResultList().isEmpty();
    }    

    public boolean tieneDependencias(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
