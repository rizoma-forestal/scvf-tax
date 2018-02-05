
package ar.gob.ambiente.servicios.especiesforestales.entidades.util;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Categorias {
    private List<ParItemValor> lPares;

    public List<ParItemValor> getlPares() {
        return lPares;
    }

    @XmlElement
    public void setlPares(List<ParItemValor> lPares) {
        this.lPares = lPares;
    }
}
