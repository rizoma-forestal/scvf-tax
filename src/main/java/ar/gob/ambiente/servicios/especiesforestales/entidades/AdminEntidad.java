/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.especiesforestales.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author rincostante
 */
@Entity
public class AdminEntidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int usAlta;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaAlta;
    private int usModif;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaModif;
    private int usBaja;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaBaja;
    private boolean habilitado;

    public int getUsAlta() {
        return usAlta;
    }

    public void setUsAlta(int usAlta) {
        this.usAlta = usAlta;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public int getUsModif() {
        return usModif;
    }

    public void setUsModif(int usModif) {
        this.usModif = usModif;
    }

    public Date getFechaModif() {
        return fechaModif;
    }

    public void setFechaModif(Date fechaModif) {
        this.fechaModif = fechaModif;
    }

    public int getUsBaja() {
        return usBaja;
    }

    public void setUsBaja(int usBaja) {
        this.usBaja = usBaja;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
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
        if (!(object instanceof AdminEntidad)) {
            return false;
        }
        AdminEntidad other = (AdminEntidad) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.servicios.especiesforestales.entidades.AdminEntidad[ id=" + id + " ]";
    }
    
}
