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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author rincostante
 */
@XmlRootElement(name = "especie")
@Entity
@Table(name = "especie")
public class Especie implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column (nullable=false, length=50)
    @NotNull(message = "El campo nombre no puede quedar vacío")
    @Size(message = "El campo nombre debe tener entre 1 y 50 caracteres", min = 1, max = 50)
    private String nombre;   
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="genero_id")
    private Genero genero;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="autorEsp_id", nullable=true)
    private Autor autorEspecie;    
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="autor_id", nullable=true)
    private Autor autores;      
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cites_id", nullable=true)
    private Cites cites;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="morfologia_id", nullable=true)
    private Morfologia morfologia;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="origen_id", nullable=true)
    private Origen origen;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="publicacion_id", nullable=true)
    private Publicacion publicacion; 
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="rango_id", nullable=true)
    private Rango rango;    
    
    @Column (nullable=true, length=100)
    @Size(message = "El campo subEspecie no puede tener más de 50 caracteres", max = 100)
    private String subEspecie;   
    
    @Column (nullable=true, length=3500)
    @Size(message = "El campo sinonimo no puede tener más de 3500 caracteres", max = 3500)
    private String sinonimo;      

    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="adminentidad_id")
    private AdminEntidad adminentidad;
    
    
    /**
     * Campo que muestra el Género y nombre de especie para conformar el nombre científico 
     */
    @Transient
    String nombrecientifico;
    
    /**
     * Campo que muestra el nombre completo de la especie.
     * Conformado por: Familia Género Especie AutorEspecie Rango SubEspecie Autores
     * Puede no haber: AutorEspecie, SubEspecie
     * @return 
     */
    @Transient
    String nombreCompleto;
    
    public String getNombreCompleto() {
        nombreCompleto = getGenero().getFamilia().getNombre() + " " + getGenero().getNombre() + " " + getNombre();
        if(this.autorEspecie != null){
            nombreCompleto = nombreCompleto + " " + getAutorEspecie().getNombre();
        }
        if(this.subEspecie != null){
            nombreCompleto = nombreCompleto + " " + getSubEspecie();
        }
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }    

    public Autor getAutorEspecie() {
        return autorEspecie;
    }

    public void setAutorEspecie(Autor autorEspecie) {
        this.autorEspecie = autorEspecie;
    }

    public Autor getAutores() {
        return autores;
    }

    public void setAutores(Autor autores) {
        this.autores = autores;
    }

    public Cites getCites() {
        return cites;
    }

    public void setCites(Cites cites) {
        this.cites = cites;
    }

    public Morfologia getMorfologia() {
        return morfologia;
    }

    public void setMorfologia(Morfologia morfologia) {
        this.morfologia = morfologia;
    }

    public Origen getOrigen() {
        return origen;
    }

    public void setOrigen(Origen origen) {
        this.origen = origen;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public Rango getRango() {
        return rango;
    }

    public void setRango(Rango rango) {
        this.rango = rango;
    }

    public String getSubEspecie() {
        return subEspecie;
    }

    public void setSubEspecie(String subEspecie) {
        this.subEspecie = subEspecie;
    }

    public String getSinonimo() {
        return sinonimo;
    }

    public void setSinonimo(String sinonimo) {
        this.sinonimo = sinonimo;
    }

    public String getNombrecientifico() {
        return genero.getNombre()+ "." + getNombre();
    }

    public void setNombrecientifico(String nombrecientifico) {
        this.nombrecientifico = nombrecientifico;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    @XmlTransient
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

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Especie)) {
            return false;
        }
        Especie other = (Especie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.servicios.especiesforestales.entidades.Especie[ id=" + id + " ]";
    }
    
}
