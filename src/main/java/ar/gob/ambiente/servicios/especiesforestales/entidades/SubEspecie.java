/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.especiesforestales.entidades;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author rincostante
 */
@Entity
public class SubEspecie implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    @Column (nullable=false, length=50, unique=true)
    @NotNull(message = "El campo nombre no puede quedar nulo")
    @Size(message = "El campo nombre debe tener entre 1 y 50 caracteres", min = 1, max = 50)    
    private String nombre;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @NotNull(message = "El campo especie no puede quedar vacío")
    @JoinColumn(name="especie_id", nullable=false)    // agregar a Especie     @OneToMany(mappedBy="especie") | private List<SubEspecie> subespecies;   
    private Especie especie;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="rango_id", nullable=true)    
    private Rango rango;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="autor_id", nullable=true)    
    private Autor autor;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="publicador_id", nullable=true)     
    private Autor publicador;
    
    @Column (nullable=false, length=200)
    @NotNull(message = "El campo nombreCompleto no puede quedar nulo")
    @Size(message = "El campo nombre debe tener entre 1 y 200 caracteres", min = 1, max = 200)  
    private String nombreCompleto; 
    
    @Column (nullable=true, length=200)
    @Size(message = "El campo basonimo no puede superar los 50 caracteres", max = 200)        
    private String basonimo;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="publicacion_id", nullable=true)    
    private Publicacion publicacion;
    
    @Column (nullable=true, length=50)
    @Size(message = "El campo distProvincia no puede superar los 50 caracteres", max = 50)     
    private String distProvincia;
    
    @Column (nullable=true, length=100)
    @Size(message = "El campo distRegion no puede superar los 100 caracteres", max = 100)      
    private String distRegion;
    
    @Column (nullable=true, length=20)
    @Size(message = "El campo tmbConocidoComo no puede superar los 20 caracteres", max = 20)     
    private String tmbConocidoComo;
    
    @Column (nullable=true, length=50)
    @Size(message = "El campo tipoCita no puede superar los 50 caracteres", max = 50)        
    private String tipoCita;
    
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="adminentidad_id")
    private AdminEntidad adminentidad;    

    public AdminEntidad getAdminentidad() {
        return adminentidad;
    }

    public void setAdminentidad(AdminEntidad adminentidad) {
        this.adminentidad = adminentidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public Rango getRango() {
        return rango;
    }

    public void setRango(Rango rango) {
        this.rango = rango;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Autor getPublicador() {
        return publicador;
    }

    public void setPublicador(Autor publicador) {
        this.publicador = publicador;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getBasonimo() {
        return basonimo;
    }

    public void setBasonimo(String basonimo) {
        this.basonimo = basonimo;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public String getDistProvincia() {
        return distProvincia;
    }

    public void setDistProvincia(String distProvincia) {
        this.distProvincia = distProvincia;
    }

    public String getDistRegion() {
        return distRegion;
    }

    public void setDistRegion(String distRegion) {
        this.distRegion = distRegion;
    }

    public String getTmbConocidoComo() {
        return tmbConocidoComo;
    }

    public void setTmbConocidoComo(String tmbConocidoComo) {
        this.tmbConocidoComo = tmbConocidoComo;
    }

    public String getTipoCita() {
        return tipoCita;
    }

    public void setTipoCita(String tipoCita) {
        this.tipoCita = tipoCita;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubEspecie)) {
            return false;
        }
        SubEspecie other = (SubEspecie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.servicios.especiesforestales.entidades.SubEspecie[ id=" + id + " ]";
    }
    
}
