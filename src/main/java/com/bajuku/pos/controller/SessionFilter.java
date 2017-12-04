package com.bajuku.pos.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class SessionFilter implements Filter{

    public void init(FilterConfig filterConfig) throws ServletException {    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req= (HttpServletRequest) request;
        HttpSession session= req.getSession(false);

        System.out.println("Filter path: "+ req.getRequestURI());

        if(req.getRequestURI().endsWith(".jsp") && (session== null || session.getAttribute("id")==null)){
                req.setAttribute("error", "User not authenticated");
                req.getRequestDispatcher("/").forward(req, response);
        }
        else{
            chain.doFilter(request, response);
        }
    }

    public void destroy() {    }
}
