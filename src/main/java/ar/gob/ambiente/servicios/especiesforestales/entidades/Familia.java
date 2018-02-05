
package ar.gob.ambiente.servicios.especiesforestales.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entidad que encapsula la informacion concerniente a una Familia.
 * Se omite la documentación de los métodos get y set.
 * @author rincostante
 */
@XmlRootElement(name = "familia")
@Entity
@Table(name = "familia")
public class Familia implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Variable privada: Identificador único
     */               
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Variable privada: Nombre de la familia
     */       
    private String nombre;
    
    /**
     * Variable privada: Sub familia de la que la familia podría ser parte
     */       
    @Column (nullable=true, length=50)
    @Size(message = "El campo subFamilia no puede tener más de 50 caracteres", max = 50)
    private String subFamilia;     
    
    /**
     * Variable privada de tipo AdminEntidad: representa la entidad administrativa de la familia
     */    
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="adminentidad_id")
    private AdminEntidad adminentidad;
    
    /**
     * Variable privada tipo List<Genero>: Listado de los géneros pertenecientes a la familia
     */
    @OneToMany(mappedBy="familia")
    private List<Genero> generos;
    
    /**
     * Variable privada: identifica las familias consumidas por el SACVeFor
     */    
    private boolean esSacvefor;    

    /**
     * Constructor
     */
    public Familia(){
        generos = new ArrayList<>();
    }

    public boolean isEsSacvefor() {
        return esSacvefor;
    }

    public void setEsSacvefor(boolean esSacvefor) {
        this.esSacvefor = esSacvefor;
    }

    public String getSubFamilia() {
        return subFamilia;
    }

    public void setSubFamilia(String subFamilia) {
        this.subFamilia = subFamilia;
    }

    /**
     * El listado de géneros pertenecientes a la familia no estará presente en la información suministrada por la API
     * @return List<Genero> listado de géneros pertenecientes a la familia.
     */
    @XmlTransient
    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * La entidad administrativa no estará presente en la información suministrada por la API
     * @return AdminEntidad entidad administrativa de la familia
     */    
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

    /**
     * Método que crea un hash con a partir de la id de la familia
     * @return int Un entero con el hash
     */     
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Método que compara una instancia de Familia con otra según su id
     * @param object La instancia de Familia a comparar con la presente
     * @return boolean Verdadero si son iguales, falso si son distintas
     */        
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Familia)) {
            return false;
        }
        Familia other = (Familia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Método que devuelve un String con el id de la Familia
     * @return String id de la Familia en formato String
     */         
    @Override
    public String toString() {
        return "ar.gob.ambiente.servicios.especiesforestales.entidades.Familia[ id=" + id + " ]";
    }
    
}
