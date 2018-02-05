
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
 * Entidad que encapsula los datos de los Autores de las taxonomías.
 * Se omite la documentación de los métodos get y set.
 * @author rincostante
 */
@XmlRootElement(name = "autor")
@Entity
@Table(name = "autor")
public class Autor implements Serializable {
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
    @Size(message = "El campo nombre debe tener entre 1 y 150 caracteres", min = 1, max = 150)
    private String nombre;    
    
    @OneToMany(mappedBy="autorEspecie")
    private List<Especie> especiesXAutEsp;     
    
    /**
     * Variable privada de tipo List<Especie>: Listado de Especies vinculadas al autor
     */
    @OneToMany(mappedBy="autores")
    private List<Especie> especiesXAutores;            

    /**
     * Constructor
     */
    public Autor(){
        especiesXAutEsp = new ArrayList<>();
        especiesXAutores = new ArrayList<>();
    }
    
    /**
     * Método que obtiene las Especies vinculadas al autor.
     * Excluído para las entidades serializadas por la API
     * @return List<Especie> Listado de las Especies vinculadas
     */
    @XmlTransient
    public List<Especie> getEspeciesXAutores() {
        return especiesXAutores;
    }

    public void setEspeciesXAutores(List<Especie> especiesXAutores) {
        this.especiesXAutores = especiesXAutores;
    }

    @XmlTransient
    public List<Especie> getEspeciesXAutEsp() {
        return especiesXAutEsp;
    }

    public void setEspeciesXAutEsp(List<Especie> especies) {
        this.especiesXAutEsp = especies;
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
     * Método que crea un hash con a partir de la id del autor
     * @return int Un entero con el hash
     */    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Método que compara una instancia de autor con otra según su id
     * @param object La instancia de autor a comparar con la presente
     * @return boolean Verdadero si son iguales, falso si son distintas
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Autor)) {
            return false;
        }
        Autor other = (Autor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Método que devuelve un String con el id del autor
     * @return String id del autor en formato String
     */    
    @Override
    public String toString() {
        return "ar.gob.ambiente.servicios.especiesforestales.entidades.Autor[ id=" + id + " ]";
    }
    
}
