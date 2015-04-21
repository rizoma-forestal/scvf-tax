/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.especiesforestales.seervicios;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Autor;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Cites;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Especie;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Familia;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Genero;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Morfologia;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Origen;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Publicacion;
import ar.gob.ambiente.servicios.especiesforestales.entidades.Rango;
import ar.gob.ambiente.servicios.especiesforestales.entidades.util.Categorias;
import ar.gob.ambiente.servicios.especiesforestales.facades.AutorFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.CitesFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.EspecieFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.FamiliaFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.GeneroFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.MorfologiaFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.OrigenFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.PublicacionFacade;
import ar.gob.ambiente.servicios.especiesforestales.facades.RangoFacade;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Administrador
 */
@Stateless
@LocalBean
public class EspeciesServicio{
    // inyecto todos los facades
    @EJB
    private EspecieFacade especieFacade;
    @EJB
    private FamiliaFacade familiaFacade;
    @EJB
    private GeneroFacade generoFacade;
    @EJB
    private AutorFacade autorFacade;
    @EJB
    private CitesFacade citesFacade;
    @EJB
    private MorfologiaFacade morfologiaFacade;
    @EJB
    private OrigenFacade origenFacade;
    @EJB
    private PublicacionFacade publicacionFacade;
    @EJB
    private RangoFacade rangoFacade;
    private static final Logger logger = Logger.getLogger(Especie.class.getName());

    public List<Autor> getAutores() {
        List<Autor> lstAutores = new ArrayList();
        Date date;
        try{
            lstAutores = autorFacade.findAll();
            logger.log(Level.INFO, "Ejecutando el método getAutores() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getAutores() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstAutores;
    }

    public List<Cites> getCites() {
        List<Cites> lstCites = new ArrayList();
        Date date;
        try{
            lstCites = citesFacade.findAll();
            logger.log(Level.INFO, "Ejecutando el método getCites() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getCites() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstCites;
    }

    public List<Morfologia> getMorfologias() {
        List<Morfologia> lstMorfologias = new ArrayList();
        Date date;
        try{
            lstMorfologias = morfologiaFacade.findAll();
            logger.log(Level.INFO, "Ejecutando el método getMorfologias() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getMorfologias() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstMorfologias;
    }

    public List<Origen> getOrigenes() {
        List<Origen> lstOrigenes = new ArrayList();
        Date date;
        try{
            lstOrigenes = origenFacade.findAll();
            logger.log(Level.INFO, "Ejecutando el método getOrigenes() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getOrigenes() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstOrigenes;
    }

    public List<Publicacion> getPublicaciones() {
        List<Publicacion> lstPublicaciones = new ArrayList();
        Date date;
        try{
            lstPublicaciones = publicacionFacade.findAll();
            logger.log(Level.INFO, "Ejecutando el método getPublicaciones() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getPublicaciones() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstPublicaciones;
    }

    public List<Rango> getRangos() {
        List<Rango> lstRangos = new ArrayList();
        Date date;
        try{
            lstRangos = rangoFacade.findAll();
            logger.log(Level.INFO, "Ejecutando el método getRangos() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getRangos() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstRangos;
    }

    public List<Especie> getEspecies() {
        List<Especie> lstEspecies = new ArrayList();
        Date date;
        try{
            lstEspecies = especieFacade.getHabilitadas();
            logger.log(Level.INFO, "Ejecutando el método getEspecies() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getEspecies() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstEspecies;
    }

    public List<Familia> getFamilias() {
        List<Familia> lstFamilias = new ArrayList();
        Date date;
        try{
            lstFamilias = familiaFacade.getActivos();
            logger.log(Level.INFO, "Ejecutando el método getFamilias() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getFamilias() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstFamilias;
    }

    public List<Genero> getGeneros() {
        List<Genero> lstGeneros = new ArrayList();
        Date date;
        try{
            lstGeneros = generoFacade.getActivos();
            logger.log(Level.INFO, "Ejecutando el método getGeneros() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getFamilias() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstGeneros;
    }

    public List<Genero> getGenerosXFamilia(Long id) {
        List<Genero> lstGeneros = new ArrayList();
        Date date;
        try{
            lstGeneros = generoFacade.getGenerosXIdFamilia(id);
            logger.log(Level.INFO, "Ejecutando el método getGenerosXFamilia() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getGenerosXFamilia() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstGeneros;
    }

    public List<Especie> getEspeciesXGenero(Long idGenero) {
        List<Especie> lstEspecies = new ArrayList();
        Date date;
        try{
            lstEspecies = especieFacade.getXGenero(idGenero);
            logger.log(Level.INFO, "Ejecutando el método getEspeciesXGenero() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getEspeciesXGenero() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstEspecies;        
    }

    public List<Especie> getEspeciesXnombre(String cadena) {
        List<Especie> lstEspecies = new ArrayList();
        Date date;
        try{
            lstEspecies = especieFacade.getXString(cadena);
            logger.log(Level.INFO, "Ejecutando el método getEspeciesXnombre() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getEspeciesXnombre() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstEspecies;
    }

    public List<Especie> getEspeciesXSubespecie(String subespecie) {
        List<Especie> lstEspecies = new ArrayList();
        Date date;
        try{
            lstEspecies = especieFacade.getXSubespecie(subespecie);
            logger.log(Level.INFO, "Ejecutando el método getEspeciesXSubespecie() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getEspeciesXSubespecie() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstEspecies; 
    }

    public List<Especie> getEspeciesXCategorias(String categorias) {
        List<Especie> lstEspecies = new ArrayList();
        Date date;
        try{
            lstEspecies = especieFacade.getEspeciesXCategorias(categorias);
            logger.log(Level.INFO, "Ejecutando el método getEspeciesXCategorias() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getEspeciesXCategorias() desde el servicio de Especies vegetales. " + date + ". ", ex);
        }
        return lstEspecies; 
    }

    public Especie getEspecie(Long id) {
        Especie esp;
        Date date;
        try{
            esp = especieFacade.find(id);
            logger.log(Level.INFO, "Ejecutando el método getEspecie() desde el servicio");
            return esp;
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getEspecie() desde el servicio de Especies vegetales. " + date + ". ", ex);
            return null;
        }
    }
}
