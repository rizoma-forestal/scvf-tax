/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.especiesforestales.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author rincostante
 */
@Entity
public class Cites implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column (nullable=false, length=50, unique=true)
    @NotNull(message = "El campo nombre no puede quedar nulo")
    @Size(message = "El campo nombre debe tener entre 1 y 50 caracteres", min = 1, max = 50)
    private String nombre;   
    
    /*
    ********** Agregar en Especie **************
    @ManyToOne(fetch=FetchType.LAZY)
    @NotNull(message = "El campo Cites no puede quedar vacío")
    @JoinColumn(name="cites_id", nullable=false)
    private Cites cites;
    
    ************* Agregar aquí *****************
    @OneToMany(mappedBy="cites")
    private List<Especie> especies;           

    public Cites(){
        especies = new ArrayList<>();
    }

    public List<Especie> getEspecies() {
        return especies;
    }

    public void setEspecies(List<Especie> especies) {
        this.especies = especies;
    }
    */          
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        if (!(object instanceof Cites)) {
            return false;
        }
        Cites other = (Cites) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.servicios.especiesforestales.entidades.Cites[ id=" + id + " ]";
    }
    
}