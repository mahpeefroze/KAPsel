package de.kapsel.core.user;

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
import javax.servlet.http.HttpSession;

import de.kapsel.core.user.beans.LoginBean;

@WebFilter("/views/secure/*")
public class LoginFilter implements Filter{

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        
        if(session==null || ((LoginBean) session.getAttribute("loginBean")).isLogged()==false){
        	response.sendRedirect(request.getContextPath() + "/views/home.xhtml");
        }else{
        	chain.doFilter(req, res);
        }
		
	}

	
	@Override
	public void destroy() {
		
	}

}
