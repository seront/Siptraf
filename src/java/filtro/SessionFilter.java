/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtro;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import modelo.entity.Usuario;

/**
 *
 * @author seront
 */
@WebFilter(filterName = "SessionFilter", urlPatterns = {"/ingresoLinea.jsp","/ActorAdmin"})
public class SessionFilter implements Filter {
    
    

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException { 
        try {
            HttpServletRequest req=(HttpServletRequest)request;
            HttpSession session=req.getSession();
            Usuario usr=(Usuario) session.getAttribute("usuario");
            if(usr!=null){
                chain.doFilter(request, response);
            }else{
                req.setAttribute("mensaje", "Acceso denegado, debe iniciar sesión");
                RequestDispatcher rd=req.getRequestDispatcher("login.jsp");
                rd.forward(request,response);
            }                       
        } catch (IOException | ServletException t) {
	   request.setAttribute("mensaje", "Acceso denegado, debe iniciar sesión");
           RequestDispatcher rd=request.getRequestDispatcher("login.jsp");
                rd.forward(request,response);
            t.printStackTrace();
        }     
        
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroy() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
