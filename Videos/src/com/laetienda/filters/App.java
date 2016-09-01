package com.laetienda.filters;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import com.laetienda.utilities.DB;
import com.laetienda.utilities.Logger;
import com.laetienda.utilities.Lang;
import com.laetienda.utilities.Auth;
import com.laetienda.beans.Page;


public class App implements Filter {
	
	private Logger log;
	private DB db;
	private HttpSession session;
	private String[] pathParts;
	private Auth auth;
	private Lang lang;
	private Page page;
	private HashMap<String, Cookie> cookies;
	
	
    public App() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		log.info("Closing request.");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpReq = (HttpServletRequest)request;
		HttpServletResponse httpResp = (HttpServletResponse)response;
		
		session = httpReq.getSession();
		
		setLogger(httpReq);
		findPathParts(httpReq);
		findCookies(httpReq);
		setLang(httpReq);
		setAuth(httpReq, httpResp);
		
		page = new Page(httpReq, log);
		
		httpReq.setAttribute("pathParts", pathParts);
		httpReq.setAttribute("page", page);
		httpReq.setAttribute("cookies", cookies);

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		db = (DB)fConfig.getServletContext().getAttribute("db");
	}
	
	private void setLogger(HttpServletRequest httpReq) throws IOException, ServletException{
		
		log = (Logger)session.getAttribute("Logger");
		
		if(log == null){
			log = new Logger(db.getUserByUsername("Logger"), db.getEm());
			session.setAttribute("Logger", log);
		}
		
		log.info("running filter. Logger has been set.");
	}
	
	private void findPathParts(HttpServletRequest httpReq) throws IOException, ServletException{
		log.info("finding path parts for uri path. $uri: " + httpReq.getRequestURI());
		
		String path = httpReq.getRequestURI();
		
		String ctxPath = httpReq.getContextPath();
		log.debug("httpReq.getContextPath(): " + ctxPath);
		
		path = path.substring(path.indexOf(ctxPath) + ctxPath.length());
		log.debug("$path: " + path);
		
		if(path.equals("/")){
			pathParts = new String[]{"", "home"};
			
		}else{
			pathParts = path.split("/");
			log.debug("$pathParts.length: " + Integer.toString(pathParts.length));
					
			for(int c=0; c < pathParts.length; c++){
				log.debug("pathParts[" + c + "] = " + pathParts[c]);
			}
		}
	}
	
	private void findCookies(HttpServletRequest httpReq) throws IOException, ServletException{
		log.info("finding cookies");
		
		cookies = new HashMap<String, Cookie>();
		
		for(Cookie cookie : httpReq.getCookies()){
			cookies.put(cookie.getName(), cookie);
		}
	}
	
	private void setAuth(HttpServletRequest httpReq, HttpServletResponse response) throws IOException, ServletException{
		log.info("Setting authentication");
		
		auth = (Auth)session.getAttribute("auth");
		
		/*=====================================================================
		 * THIS PATHS DOES NOT REQUIRE AUTHENTICATION
		 * Also, they will not be saved as previous path
		 ======================================================================*/
		
		// /assets/* 
		if(		(pathParts.length >= 3 && pathParts[1].equals("assets"))
				
		// /images/*
			||	(pathParts.length == 3 && pathParts[1].equals("image"))
			
		//	/login
			||  (pathParts.length == 2 && pathParts[1].equals("login") )
			
		// /thankyou/*
			|| 	(pathParts.length == 3 && pathParts[1].equals("thankyou"))
				){
			
			log.debug("Serving content that does not require authentication");
			
		/*=====================================================================
			THE OTHER PATHS REQUIRES AUTH
		======================================================================*/
			
		}else{
			String previous_uri = (String)session.getAttribute("current_uri");
			
			if(previous_uri == null){
				session.setAttribute("previous_uri", httpReq.getRequestURI());
			}else{
				session.removeAttribute("previous_uri");
				session.setAttribute("previous_uri", previous_uri);
			}
						
			session.removeAttribute("current_uri");
			session.setAttribute("current_uri", httpReq.getRequestURI());
			
			if(auth == null){
				String temp = httpReq.getContextPath();
				response.sendRedirect(temp + "/login");
			}
		}
	}
	
	private void setLang(HttpServletRequest httpReq) throws IOException, ServletException{
		
		lang = (Lang)session.getAttribute("lang");
		
		if(lang == null){
			lang = new Lang(db.getEm(), db.getUserByUsername("Logger"), log);
			lang.setCookie(cookies.get("lang"));
			//lang.setHeaderLanguage(httpReq.getHeader("Accept-Language"));
						
			lang.setCookie(cookies.get("lang"));
			session.setAttribute("lang", lang);
		}
	}
}
