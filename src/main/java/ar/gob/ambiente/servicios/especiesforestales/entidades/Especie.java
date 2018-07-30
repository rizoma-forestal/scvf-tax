
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * Entidad que encapsula la informacion concerniente a una Especie.
 * Se omite la documentación de los métodos get y set.
 * @author rincostante
 */
@XmlRootElement(name = "especie")
@Entity
@Table(name = "especie")
public class Especie implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Variable privada: Identificador único
     */             
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Variable privada: Nombre de la especie
     */       
    @Column (nullable=false, length=50)
    @NotNull(message = "El campo nombre no puede quedar vacío")
    @Size(message = "El campo nombre debe tener entre 1 y 50 caracteres", min = 1, max = 50)
    private String nombre;   
    
    /**
     * Variable privada de tipo Genero: represente el género al cual pertenece la especie
     */        
    @ManyToOne/*(fetch=FetchType.LAZY)*/
    @JoinColumn(name="genero_id")
    private Genero genero;
    
    /**
     * Variable privada de tipo Autor: representa al autor de la especie
     */      
    @ManyToOne/*(fetch=FetchType.LAZY)*/
    @JoinColumn(name="autorEsp_id", nullable=true)
    private Autor autorEspecie;    
    
    /**
     * Variable privada de tipo Autor: representa a los autores secundarios de la especie
     */         
    @ManyToOne/*(fetch=FetchType.LAZY)*/
    @JoinColumn(name="autor_id", nullable=true)
    private Autor autores;      
    
    /**
     * Variable privada de tipo Cites: representa al tipo de protección Cites que la especie pudiera tener
     */
    @ManyToOne/*(fetch=FetchType.LAZY)*/
    @JoinColumn(name="cites_id", nullable=true)
    private Cites cites;
    
    /**
     * Variable privada de tipo Morfologia: representa la morfología de la especie.
     */
    @ManyToOne/*(fetch=FetchType.LAZY)*/
    @JoinColumn(name="morfologia_id", nullable=true)
    private Morfologia morfologia;
    
    /**
     * Variable privada de tipo Origen: representa el origen de la especie
     */
    @ManyToOne/*(fetch=FetchType.LAZY)*/
    @JoinColumn(name="origen_id", nullable=true)
    private Origen origen;
    
    /**
     * Variable privada de tipo Publicación: representa la publicación de la especie
     */
    @ManyToOne/*(fetch=FetchType.LAZY)*/
    @JoinColumn(name="publicacion_id", nullable=true)
    private Publicacion publicacion; 
    
    /**
     * Variable privada de tipo Rango: representa el rango de la especie
     */
    @ManyToOne/*(fetch=FetchType.LAZY)*/
    @JoinColumn(name="rango_id", nullable=true)
    private Rango rango;    
    
    /**
     * Variable privada: sub especie de la que podría tratarse la especie
     */
    @Column (nullable=true, length=100)
    @Size(message = "El campo subEspecie no puede tener más de 50 caracteres", max = 100)
    private String subEspecie;   
    
    /**
     * Variable privada: sinónimos que la especie pudiera tener
     */
    @Column (nullable=true, length=3500)
    @Size(message = "El campo sinonimo no puede tener más de 3500 caracteres", max = 3500)
    private String sinonimo;    
    
    /**
     * Variable privada: identifica las especies consumidas por el SACVeFor
     */
    private boolean esSacvefor;

    /**
     * Variable privada de tipo AdminEntidad: representa la entidad administrativa de la especie
     */
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="adminentidad_id")
    private AdminEntidad adminentidad;
    
    
    /**
     * Variable pública: muestra el Género y nombre de especie para conformar el nombre científico.
     * No se persiste
     */
    @Transient
    String nombrecientifico;
    
    /**
     * Variable pública: muestra el nombre completo de la especie.
     * Conformado por: Familia Género Especie.
     * No se persiste
     * @return 
     */
    @Transient
    String nombreCompleto;

    public boolean isEsSacvefor() {
        return esSacvefor;
    }

    public void setEsSacvefor(boolean esSacvefor) {
        this.esSacvefor = esSacvefor;
    }
    
    /**
     * Método que construye el nombre completo de la especie
     * @return String nombre completo de la especie
     */
    public String getNombreCompleto() {
        nombreCompleto = getGenero().getFamilia().getNombre() + " " + getGenero().getNombre() + " " + getNombre();
        if(this.autorEspecie != null){
            nombreCompleto = nombreCompleto + " " + getAutorEspecie().getNombre();
        }
        if(this.subEspecie != null){
            nombreCompleto = nombreCompleto + " " + getSubEspecie();
        }
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }    

    public Autor getAutorEspecie() {
        return autorEspecie;
    }

    public void setAutorEspecie(Autor autorEspecie) {
        this.autorEspecie = autorEspecie;
    }

    public Autor getAutores() {
        return autores;
    }

    public void setAutores(Autor autores) {
        this.autores = autores;
    }

    public Cites getCites() {
        return cites;
    }

    public void setCites(Cites cites) {
        this.cites = cites;
    }

    public Morfologia getMorfologia() {
        return morfologia;
    }

    public void setMorfologia(Morfologia morfologia) {
        this.morfologia = morfologia;
    }

    public Origen getOrigen() {
        return origen;
    }

    public void setOrigen(Origen origen) {
        this.origen = origen;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public Rango getRango() {
        return rango;
    }

    public void setRango(Rango rango) {
        this.rango = rango;
    }

    public String getSubEspecie() {
        return subEspecie;
    }

    public void setSubEspecie(String subEspecie) {
        this.subEspecie = subEspecie;
    }

    public String getSinonimo() {
        return sinonimo;
    }

    public void setSinonimo(String sinonimo) {
        this.sinonimo = sinonimo;
    }

    public String getNombrecientifico() {
        return genero.getNombre()+ " " + getNombre();
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

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    /**
     * La entidad administrativa no estará presente en la información suministrada por la API
     * @return AdminEntidad entidad administrativa de la especie
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
     * Método que crea un hash con a partir de la id de la especie
     * @return int Un entero con el hash
     */     
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Método que compara una instancia de Especie con otra según su id
     * @param object La instancia de Especie a comparar con la presente
     * @return boolean Verdadero si son iguales, falso si son distintas
     */    
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

    /**
     * Método que devuelve un String con el id de la Especie
     * @return String id de la Especie en formato String
     */          
    @Override
    public String toString() {
        return "ar.gob.ambiente.servicios.especiesforestales.entidades.Especie[ id=" + id + " ]";
    }
    
}
