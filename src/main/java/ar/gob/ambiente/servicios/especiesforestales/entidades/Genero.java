
package ar.gob.ambiente.servicios.especiesforestales.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entidad que encapsula la informacion concerniente a un Género.
 * Se omite la documentación de los métodos get y set.
 * @author rincostante
 */
@XmlRootElement(name = "genero")
@Entity
@Table(name = "genero")
public class Genero implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Variable privada: Identificador único
     */       
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Variable privada: Nombre de el género
     */ 
    private String nombre;
    
    /**
     * Variable privada de tipo Familia: familia a la que pertenece el género
     */
    @ManyToOne /*(fetch=FetchType.LAZY)*/
    @JoinColumn(name="familia_id")
    private Familia familia;
       
    /**
     * Variable privada de tipo AdminEntidad: representa la entidad administrativa del género
     */    
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="adminentidad_id")
    private AdminEntidad adminentidad;    
   
    /**
     * Variable privada tipo List<Especie>: Listado de las especies pertenecientes al género
     */
    @OneToMany(mappedBy="genero")
    private List<Especie> especies;

    /**
     * Variable privada: identifica a los Géneros consumidos por el SACVeFor
     */    
    private boolean esSacvefor;    

    /**
     * Constructor
     */
    public Genero(){
        especies = new ArrayList<>();
    }

    public boolean isEsSacvefor() {
        return esSacvefor;
    }

    public void setEsSacvefor(boolean esSacvefor) {
        this.esSacvefor = esSacvefor;
    }
    
    /**
     * El listado de especies pertenecientes al género no estará presente en la información suministrada por la API
     * @return List<Especie> listado de especies pertenecientes al género.
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

  
    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    /**
     * Método que crea un hash con a partir de la id del género
     * @return int Un entero con el hash
     */       
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Método que compara una instancia de Género con otra según su id
     * @param object La instancia de Género a comparar con la presente
     * @return boolean Verdadero si son iguales, falso si son distintas
     */            
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Genero)) {
            return false;
        }
        Genero other = (Genero) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Método que devuelve un String con el id del Género
     * @return String id del Género en formato String
     */  
    @Override
    public String toString() {
        return "ar.gob.ambiente.servicios.especiesforestales.entidades.Genero[ id=" + id + " ]";
    }
}