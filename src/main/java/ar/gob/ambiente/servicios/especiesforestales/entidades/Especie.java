/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.especiesforestales.entidades;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;


/**
 *
 * @author rincostante
 */
@Entity
public class Especie implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String nombrevulgar;
    private String nombreingles;
    private boolean nativa;
    private boolean cites;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="genero_id")
    private Genero genero;
    
    
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="adminentidad_id")
    private AdminEntidad adminentidad;
    
    
 /**
     * Campo que muestra el Género y nombre de especie para conformar el nombre científico 
     */
    @Transient
    String nombrecientifico;

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

    public String getNombrevulgar() {
        return nombrevulgar;
    }

    public void setNombrevulgar(String nombrevulgar) {
        this.nombrevulgar = nombrevulgar;
    }

    public String getNombreingles() {
        return nombreingles;
    }

    public void setNombreingles(String nombreingles) {
        this.nombreingles = nombreingles;
    }

    public boolean isNativa() {
    return nativa;
    }
    
    public void setNativa(boolean nativa) {
    this.nativa = nativa;
    }
    
    public boolean isCites() {
    return cites;
    }
    
    public void setCites(boolean cites) {
    this.cites = cites;
    }
    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

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
