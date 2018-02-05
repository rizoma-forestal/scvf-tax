
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad que encapsula la información concerniente a los usuarios de la aplicación.
 * Tanto sea de la interface como de la API rest
 * @author rincostante
 */
@XmlRootElement(name = "usuario")
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Variable privada: Identificador único
     */  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Variable privada de tipo Rol: rol al que pertenece el usuario
     */
    @ManyToOne /*(fetch=FetchType.LAZY)*/
    @JoinColumn(name="rol_id")
    private Rol rol;
    
    /**
     * Variable privada: indica el login del usuario
     */        
    @Column (nullable=false, length=20, unique=true)
    @NotNull(message = "{entidades.fieldNotNullError}")
    @Size(message = "{endidades.stringSizeError}", min = 1, max = 20)
    private String nombre;     
    
    /**
     * Variable privada: indica el nombre completo del usuario
     */        
    @Column (nullable=false, length=100)
    @NotNull(message = "{entidades.fieldNotNullError}")
    @Size(message = "{endidades.stringSizeError}", min = 1, max = 100)
    private String nombreCompleto;       
    
    /**
     * Variable privada de tipo AdmEntidad: encapsula los datos propios para su trazabilidad.
     */
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @NotNull(message = "{enitdades.objectNotNullError}") 
    private AdminEntidad admin;   

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public AdminEntidad getAdmin() {
        return admin;
    }

    public void setAdmin(AdminEntidad admin) {
        this.admin = admin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Método que crea un hash con a partir de la id del usuario
     * @return int Un entero con el hash
     */      
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Método que compara una instancia de Usuario con otra según su id
     * @param object La instancia de Usuario a comparar con la presente
     * @return boolean Verdadero si son iguales, falso si son distintas
     */          
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Método que devuelve un String con el id del Usuario
     * @return String id del Usuario en formato String
     */   
    @Override
    public String toString() {
        return "ar.gob.ambiente.servicios.especiesforestales.entidades.Usuario[ id=" + id + " ]";
    }
}
