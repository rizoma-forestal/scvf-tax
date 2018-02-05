
package ar.gob.ambiente.servicios.especiesforestales.entidades.util;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class ParItemValor{
    private List<String> lPar;

    public List<String> getlPar() {
        return lPar;
    }

    @XmlElement
    public void setlPar(List<String> lPar) {
        this.lPar = lPar;
    }
}
