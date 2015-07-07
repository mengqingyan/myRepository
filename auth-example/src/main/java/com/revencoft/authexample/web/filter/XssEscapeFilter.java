/**
 * 
 */
package com.revencoft.authexample.web.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * @author mengqingyan
 * @version 
 */
public class XssEscapeFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response); 
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}
	
	private final class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

		/**
		 * @param request
		 */
		public XssHttpServletRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		@Override
		public String getHeader(String name) {
			return StringEscapeUtils.escapeHtml4(super.getHeader(name)) ;
		}

		@Override
		public String getQueryString() {
			return StringEscapeUtils.escapeHtml4(super.getQueryString());
		}

		@Override
		public String getParameter(String name) {
			return StringEscapeUtils.escapeHtml4(super.getParameter(name));
		}

		@Override
		public String[] getParameterValues(String name) {
			String[] values = super.getParameterValues(name);  
	        if(values != null) {  
	            int length = values.length;  
	            String[] escapseValues = new String[length];  
	            for(int i = 0; i < length; i++){  
	                escapseValues[i] = StringEscapeUtils.escapeHtml4(values[i]);  
	            }  
	            return escapseValues;  
	        }  
	        return super.getParameterValues(name);  
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			Map<String, String[]> escapeMap = new HashMap<String, String[]>();
			Enumeration<String>  e = this.getParameterNames();
			while(e.hasMoreElements()){
				String name = e.nextElement();
				escapeMap.put(name,this.getParameterValues(name));
			}
			return escapeMap;
		}
	}

}
