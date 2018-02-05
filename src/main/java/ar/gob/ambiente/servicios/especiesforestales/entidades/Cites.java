
package ar.gob.ambiente.servicios.especiesforestales.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entidad que encapsula los datos del tipo de protección de Cites que una Especie pudiera tener.
 * Se omite la documentación de los métodos get y set.
 * @author rincostante
 */
@XmlRootElement(name = "cites")
@Entity
@Table(name = "cites")
public class Cites implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Variable privada: Identificador único
     */         
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Variable privada: Nombre del autor
     */      
    @Column (nullable=false, length=50, unique=true)
    @NotNull(message = "El campo nombre no puede quedar nulo")
    @Size(message = "El campo nombre debe tener entre 1 y 50 caracteres", min = 1, max = 50)
    private String nombre;   

    /**
     * Variable privada tipo List<Especie>: Listado de Especies vinculadas al tipo Cites
     */
    @OneToMany(mappedBy="cites")
    private List<Especie> especies;           

    /**
     * Constructor
     */    
    public Cites(){
        especies = new ArrayList<>();
    }

    /**
     * Método que obtiene las Especies vinculadas al tipo de Cites.
     * Excluído para las entidades serializadas por la API
     * @return List<Especie> Listado de las Especies vinculadas
     */    
    @XmlTransient
    public List<Especie> getEspecies() {
        return especies;
    }

    public void setEspecies(List<Especie> especies) {
        this.especies = especies;
    }       
    
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

    /**
     * Método que crea un hash con a partir de la id del tipo de Cites
     * @return int Un entero con el hash
     */     
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Método que compara una instancia de Cites con otra según su id
     * @param object La instancia de autor a comparar con la presente
     * @return boolean Verdadero si son iguales, falso si son distintas
     */    
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

    /**
     * Método que devuelve un String con el id del tipo de Cites
     * @return String id del tipo de Cites en formato String
     */        
    @Override
    public String toString() {
        return "ar.gob.ambiente.servicios.especiesforestales.entidades.Cites[ id=" + id + " ]";
    }
    
}
