/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.especiesforestales.managedBeans;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Usuario;
import ar.gob.ambiente.servicios.especiesforestales.entidades.util.CriptPass;
import ar.gob.ambiente.servicios.especiesforestales.facades.UsuarioFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.omnifaces.util.Faces;

/**
 *
 * @author rincostante
 */
public class MbLogin implements Serializable{
    
    private static final long serialVersionUID = -2152389656664659476L;
    private boolean logeado = false;   
    private String ambito;
    private Usuario usLogeado;

    @EJB
    private UsuarioFacade usuarioFacade;
    private boolean iniciado;
    
    /**
     * Creates a new instance of MbLogin
     */
    public MbLogin() {
    }
    
    /**
     * Método para inicializar el listado de los Mb activos
     */
    @PostConstruct
    public void init(){
        iniciado = false;
    }
/**
     * Método que borra de la memoria los MB innecesarios al cargar el listado 
     */
    public void iniciar() throws IOException{
        if(iniciado){
            String s;
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
            .getExternalContext().getSession(true);
            Enumeration enume = session.getAttributeNames();
            while(enume.hasMoreElements()){
                s = (String)enume.nextElement();
                if(s.substring(0, 2).equals("mb")){
                        if(!s.equals("mbLogin")){
                        session.removeAttribute(s);
                    }
                }
            }
        }else{
            // obtengo el nombre del usuario logeado de la cookie correspondiente
            String nomUsuario;
            String valueEnc = Faces.getRequestCookie(ResourceBundle.getBundle("/Bundle").getString("nameCookieUser"));
            
            try {
                // desencripto el nombre
                nomUsuario = CriptPass.desencriptar(valueEnc);
                
                // obtebtengo el usuario correspondiente al nombre desencriptado
                if(usuarioFacade.getUsuario(nomUsuario) != null){
                    usLogeado = usuarioFacade.getUsuario(nomUsuario);
                    ambito = usLogeado.getRol().getNombre();
                    iniciado = true;
                }else{
                    // si no tengo un usuario registrado en la aplicación para este nombre, redirecciono a la vista de error de acceso
                    FacesContext fc=FacesContext.getCurrentInstance();
                    fc.getExternalContext().redirect(ResourceBundle.getBundle("/Bundle").getString("logError"));
                }
            } catch (Exception ex) {
                Logger.getLogger(MbLogin.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
    }      

    public boolean isIniciado() {
        return iniciado;
    }

    public void setIniciado(boolean iniciado) {
        this.iniciado = iniciado;
    }

    public Usuario getUsLogeado() {
        return usLogeado;
    }

    public void setUsLogeado(Usuario usLogeado) {
        this.usLogeado = usLogeado;
    }

    public boolean isLogeado() {
        return logeado;
    }

    public void setLogeado(boolean logeado) {
        this.logeado = logeado;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String rol) {
        this.ambito = rol;
    }
    
    public void logout(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        iniciado = false;
    }  
}
