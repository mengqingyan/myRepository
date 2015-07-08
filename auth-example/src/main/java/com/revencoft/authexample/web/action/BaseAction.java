/**
 * 
 */
package com.revencoft.authexample.web.action;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public abstract class BaseAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorMsg;

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected HttpSession getSession() {
		return getSession(true);
	}
	
	protected HttpSession getSession(boolean create) {
		return ServletActionContext.getRequest().getSession(create);
	}
	
	protected void sessionSave(String name, Object value) {
		getSession().setAttribute(name, value);
	}

	protected void requestSave(String name, Object obj) {
		getRequest().setAttribute(name, obj);
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	protected Map<String, Object> getParameters() {
		return ActionContext.getContext().getParameters();
	}

	protected void ClearSession() {
		Enumeration<String> attrNames = getSession().getAttributeNames();
		while (attrNames.hasMoreElements()) {
			String elem = (String) attrNames.nextElement();
			getSession().removeAttribute(elem);
		}
	}


	protected void responseWrite(String result) throws Exception {
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(result);
	}
	


	protected void responseWrite(Object obj) throws Exception {
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(JSONObject.toJSONString(obj));
	}
}
