package it.ubix.controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.ubix.model.Utente;

/**
 * Servlet Filter implementation class SecurityFilter
 */
@WebFilter("/SecurityFilter")
public class SecurityFilter implements Filter {

    public SecurityFilter() {
        System.out.println("Inizializzato");
    }


	public void destroy() {
		
	}

	/**
	 * Il filtro funziona su /resource/*
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
        Utente user=(Utente)req.getSession().getAttribute("user");	
        if(user!=null) {
            chain.doFilter(request, response);
        }else {
        	HttpServletResponse resp=(HttpServletResponse)response;
        	resp.sendError(401);
        }
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
