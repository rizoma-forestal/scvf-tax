
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entidad que encapsula la información concerniente al rol de un usuario de la aplicación.
 * Tanto sea de la interface como de la API rest
 * @author carmendariz
 */
@XmlRootElement(name = "rol")
@Entity
@Table(name = "rol")
public class Rol implements Serializable {
    private static final long serialVersionUID = 1L;
     
    /**
     * Variable privada: Identificador único
     */  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Variable privada: Nombre del rol del usuario
     */ 
    private String nombre;
    
    /**
     * Variable privada de tipo AdminEntidad: representa la entidad administrativa del rol
     */    
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="adminentidad_id")
    private AdminEntidad adminentidad;
    
    /**
     * Variable privada de tipo List<Usuario>: Listado de usuarios que comparten el mismo rol
     */
    @XmlTransient
    @OneToMany(mappedBy="rol")
    private List<Usuario> usuarios;
    
    /**
     * Constructor
     */
    public Rol(){
        usuarios = new ArrayList<>();
    }

    /**
     * El listado de usuarios que comparten el mismo rol no estará presente en la información suministrada por la API
     * @return List<Usuario> listado de roles que comparten rango.
     */ 
    @XmlTransient
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    
    /**
     * Método que crea un hash con a partir de la id del rol
     * @return int Un entero con el hash
     */    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Método que compara una instancia de Rol con otra según su id
     * @param object La instancia de Rol a comparar con la presente
     * @return boolean Verdadero si son iguales, falso si son distintas
     */          
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rol)) {
            return false;
        }
        Rol other = (Rol) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Método que devuelve un String con el id del Rol
     * @return String id del Rol en formato String
     */          
    @Override
    public String toString() {
        return "ar.gob.ambiente.servicios.especiesforestales.entidades.Rol[ id=" + id + " ]";
    }
    
}
