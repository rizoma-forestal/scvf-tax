/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.especiesforestales.facades;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;

/**
 *
 * @author Administrador
 */
public abstract class AbstractFacade<T> {
    private final Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     *
     * @return
     */
    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }
   
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    /**
     * Método que obtiene las entidades según el valor de un campo dado.
     * @param table: Cadena que contiene el nombre de la tabla en la que realizar la búsqueda
     * @param campo: Cadena que indica el nombre del campo cuyo valor se desa buscar. El el nombre del campo en la base de datos
     * @param valor: Valor que se buscará.
     * @param tipo: Tipo de dato del campo (String/int)
     * @return
     */
    public List<T> findFieldValue(String table, String campo, Object valor, String tipo) {
        List<T> result;
        String queryString = "";
        switch (tipo) {
            case "String":
                queryString = "SELECT * FROM " + table + " WHERE " + campo + " = '" + valor + "'";
                break;
                
            case "int":
                queryString = "SELECT * FROM " + table + " WHERE " + campo + " = " + valor;
                break;                
        }
        javax.persistence.Query q = getEntityManager().createNativeQuery(queryString, entityClass);
        result = q.getResultList();
        return result;
    }
    
    /**
     * Método que obtiene las entidades según el valor de un campo dado de tipo TemporalType.
     * @param campo: Cadena que indica el nombre del campo cuyo valor se desa buscar.
     * @param date: Valor de la fecha a buscar.
     * @param temporalType: Especificación del tipo de fecha que se busca.
     * @return
     */
    public List<T> findXFecha(String campo, Date date, TemporalType temporalType) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setParameter(campo, date, temporalType);
        List<T> resultList = q.getResultList();
        return resultList;
    }     
}