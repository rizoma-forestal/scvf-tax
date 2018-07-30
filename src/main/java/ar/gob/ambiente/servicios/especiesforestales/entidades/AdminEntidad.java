
package ar.gob.ambiente.servicios.especiesforestales.entidades;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Enditad administrativa que gestiona las inserciones, modificaciones y bajas del resto de las entidades.
 * Se omite la documentación de los métodos get y set.
 * @author rincostante
 */
@XmlRootElement(name = "adminentidad")
@Entity
@Table(name = "adminentidad")
public class AdminEntidad implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Variable privada: Identificador único
     */    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Variable privada: Usuario que dio de alta la entidad
     */        
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="usalta_id", nullable=false)
    @NotNull(message = "Debe haber un usuario dealta")
    private Usuario usAlta;

    /**
     * Variable privada: Fecha de alta de la entidad
     */  
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaAlta;
    
    /**
     * Variable privada: Usuario que modificó la entidad
     */      
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="usmodif_id", nullable=true)
    private Usuario usModif;
    
    /**
     * Variable privada: Fecha en que se modificó la entidad
     */      
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaModif;
    
    /**
     * Variable privada: Usuario que dio de baja la entidad
     */      
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="usbaja_id", nullable=true)
    private Usuario usBaja;
    
    /**
     * Variable privada: Fecha en que se dio de baja a la entidad
     */        
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaBaja;
    
    /**
     * Variable privada: Condición de habilitado de la entidad
     */        
    private boolean habilitado;

    /**
     * Campo que muestra la fecha de alta como string
     */
    @Transient
    String strFechaAlta;
    
    /**
     * Campo que muestra la fecha de baja como string
     */
    @Transient
    String strFechaModif;
    
    /**
     * Campo que muestra la fecha de baja como string
     */
    @Transient
    String strFechaBaja;
    
    /**
     * Método que genera un String con la fecha de baja
     * @return String La fecha en formato String
     */
    public String getStrFechaBaja() {
        if(fechaBaja != null){
            SimpleDateFormat formateador = new SimpleDateFormat("dd'/'MM'/'yyyy", new Locale("es_ES"));
            strFechaBaja = formateador.format(fechaBaja);
            return strFechaBaja;
        }
        return "";
    }

    /**
     * Método que setea la Fecha de baja en formato String
     * @param strFechaBaja Fecha de baja en formato String
     */
    public void setStrFechaBaja(String strFechaBaja) {
        this.strFechaBaja = strFechaBaja;
    }

    /**
     * Método que genera un String con la fecha de alta
     * @return String La fecha en formato String
     */
    public String getStrFechaAlta() {
        SimpleDateFormat formateador = new SimpleDateFormat("dd'/'MM'/'yyyy", new Locale("es_ES"));
        strFechaAlta = formateador.format(fechaAlta);   
        return strFechaAlta;
    }

    /**
     * Método que setea la Fecha de alta en formato String
     * @param strFechaAlta Fecha de alta en formato String
     */    
    public void setStrFechaAlta(String strFechaAlta) {
        this.strFechaAlta = strFechaAlta;
    }    
    
    /**
     * Método que genera un String con la fecha de modificación
     * @return String La fecha en formato String
     */
    public String getStrFechaModif(){
        if(fechaModif != null){
            SimpleDateFormat formateador = new SimpleDateFormat("dd'/'MM'/'yyyy", new Locale("es_ES"));
            strFechaModif = formateador.format(fechaModif);
            return strFechaModif;
        }
        return "";
    }
    
    /**
     * Método que setea la Fecha de modificación en formato String
     * @param strFechaModif Fecha de modificación en formato String
     */        
    public void setStrFechaModif(String strFechaModif){
        this.strFechaModif = strFechaModif;
    }
            
    public Usuario getUsAlta() {
        return usAlta;
    }

    public void setUsAlta(Usuario usAlta) {
        this.usAlta = usAlta;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Usuario getUsModif() {
        return usModif;
    }

    public void setUsModif(Usuario usModif) {
        this.usModif = usModif;
    }

    public Date getFechaModif() {
        return fechaModif;
    }

    public void setFechaModif(Date fechaModif) {
        this.fechaModif = fechaModif;
    }

    public Usuario getUsBaja() {
        return usBaja;
    }

    public void setUsBaja(Usuario usBaja) {
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

    /**
     * Método que crea un hash con a partir de la id de la entidad
     * @return int Un entero con el hash
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Método que compara una instancia de esta entidad con otra según su id
     * @param object La instancia de entidad a comparar con la presente
     * @return boolean Verdadero si son iguales, falso si son distintas
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdminEntidad)) {
            return false;
        }
        AdminEntidad other = (AdminEntidad) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    /**
     * Método que devuelve un String con el id de la entidad
     * @return String id de la entidad en formato String
     */
    @Override
    public String toString() {
        return "ar.gob.ambiente.servicios.especiesforestales.entidades.AdminEntidad[ id=" + id + " ]";
    }
    
}
