/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.especiesforestales.servlets;

import ar.gob.ambiente.servicios.especiesforestales.entidades.Especie;
import ar.gob.ambiente.servicios.especiesforestales.facades.EspecieFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author carmendariz
 */

public class EspecieServlet extends HttpServlet {
    @EJB
    private EspecieFacade espFcd;
    private Especie especie;
    private List<Especie> especies;
  
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Especies</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>prueba de facade de especies</h1>");
            out.println("<h1>Servlet EspecieServlet at " + request.getContextPath() + "</h1>");
  
          //espFcd.existe("Pirulo");
            
            /*
            if(espFcd.existe("Pirulo")){
                out.println("<h1>No Existe</h1>");
                }
                else{
                    out.println("<h1>Ya existe</h1>");
                        }
            */
            
/*         if(espFcd.getUtilizado(Long.valueOf(1))){
                out.println("<h1>No Tiene Dependencias</h1>");
                }
                else{
                    out.println("<h1>Tiene Dependencias</h1>");
                        }*/
                        
            out.println("</body>");
            out.println("</html>");
            
        }
    }

} 
