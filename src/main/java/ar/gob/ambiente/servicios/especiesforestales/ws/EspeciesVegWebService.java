/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.especiesforestales.ws;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Autor;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Cites;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Especie;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Familia;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Genero;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Morfologia;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Origen;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Publicacion;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Rango;
import ar.gob.ambiente.servicios.especiesforestales.seervicios.EspeciesServicio;
import java.util.HashMap;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Administrador
 */
@WebService(serviceName="EspeciesVegWebService")
@Stateless()
public class EspeciesVegWebService {
    
    @EJB
    private EspeciesServicio espSrv;

    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "verAutores")
    public List<Autor> verAutores() {
        return espSrv.getAutores();
    }

    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "verCites")
    public List<Cites> verCites() {
        return espSrv.getCites();
    }
    
    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "verMorfologias")
    public List<Morfologia> verMorfologias() {
        return espSrv.getMorfologias();
    }    
    
    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "verOrigen")
    public List<Origen> verOrigen() {
        return espSrv.getOrigenes();
    }    
    
    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "verPublicacion")
    public List<Publicacion> verPublicacion() {
        return espSrv.getPublicaciones();
    }    
    
    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "verRango")
    public List<Rango> verRango() {
        return espSrv.getRangos();
    }   
    
    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "verFamilias")
    public List<Familia> verFamilias() {
        return espSrv.getFamilias();
    }
    
    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "verGeneros")
    public List<Genero> verGeneros() {
        return espSrv.getGeneros();
    }
    
    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "verEspecies")
    public List<Especie> verEspecies() {
        return espSrv.getEspecies();
    }    

    /**
     * Web service operation
     * @param id
     * @return 
     */
    @WebMethod(operationName = "buscarEspecie")
    public Especie buscarEspecie(@WebParam(name = "id") Long id) {
        return espSrv.getEspecie(id);
    }

    /**
     * Web service operation
     * @param familia
     * @return 
     */
    @WebMethod(operationName = "verGenerosPorFamilia")
    public List<Genero> verGenerosPorFamilia(@WebParam(name = "familia") Familia familia) {
        return espSrv.getGenerosXFamilia(familia);
    }

    /**
     * Web service operation
     * @param genero
     * @return 
     */
    @WebMethod(operationName = "verEspeciesPorGenero")
    public List<Especie> verEspeciesPorGenero(@WebParam(name = "genero") Genero genero) {
        return espSrv.getEspeciesXGenero(genero);
    }

    /**
     * Web service operation
     * @param nombre
     * @return 
     */
    @WebMethod(operationName = "buscarEspeciesPorNombre")
    public List<Especie> buscarEspeciesPorNombre(@WebParam(name = "nombre") String nombre) {
        return espSrv.getEspeciesXnombre(nombre);
    }

    /**
     * Web service operation
     * @param subespecie
     * @return 
     */
    @WebMethod(operationName = "buscarEspeciesPorSubespecie")
    public List<Especie> buscarEspeciesPorSubespecie(@WebParam(name = "subespecie") String subespecie) {
        return espSrv.getEspeciesXSubespecie(subespecie);
    }

    /**
     * Web service operation
     * @param categorias
     * @return 
     */
    @WebMethod(operationName = "buscarEspeciesPorCategorias")
    public List<Especie> buscarEspeciesPorCategorias(@WebParam(name = "categorias") List<HashMap<String, Long>> categorias) {
        return espSrv.getEspeciesXCategorias(categorias);
    }
}
