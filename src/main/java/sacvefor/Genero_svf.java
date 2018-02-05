
package sacvefor;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entidad que encapsula la informacion concerniente a un Género migrada desde SACVeFor
 * @deprecated solo utilizada para la migración desde el SACVeFor, ya realizada
 * @author rincostante
 */
@Entity
public class Genero_svf implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int id_svf;

    @Column (nullable=false, length=30)
    @NotNull(message = "El campo nombre no puede quedar nulo")
    @Size(message = "El campo nombre debe tener entre 1 y 30 caracteres", min = 1, max = 30)    
    private String nombre;    
    
    private int id_familia_svf;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="familia_svf_id")
    private Familia_svf familia_svf; 
    
    private Long id_insertado;

    public Long getId_insertado() {
        return id_insertado;
    }

    public void setId_insertado(Long id_insertado) {
        this.id_insertado = id_insertado;
    }

    public int getId_svf() {
        return id_svf;
    }

    public void setId_svf(int id_svf) {
        this.id_svf = id_svf;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_familia_svf() {
        return id_familia_svf;
    }

    public void setId_familia_svf(int id_familia_svf) {
        this.id_familia_svf = id_familia_svf;
    }

    public Familia_svf getFamilia_svf() {
        return familia_svf;
    }

    public void setFamilia_svf(Familia_svf familia_svf) {
        this.familia_svf = familia_svf;
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
        if (!(object instanceof Genero_svf)) {
            return false;
        }
        Genero_svf other = (Genero_svf) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sacvefor.Genero[ id=" + id + " ]";
    }
    
}
