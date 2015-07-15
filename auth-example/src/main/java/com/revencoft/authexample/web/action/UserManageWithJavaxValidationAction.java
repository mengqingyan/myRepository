/**
 * 
 */
package com.revencoft.authexample.web.action;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.revencoft.authexample.service.auth.UserAuthService;
import com.revencoft.authexample.vo.JavaxValidationUser;
import com.revencoft.validation.constant.ValidationConstant;
import com.revencoft.validation.persistent.annotation.Auth;
import com.revencoft.validation.persistent.annotation.Valid;

/**
 * @author mengqingyan
 * @version 
 */
@Scope("prototype")
@Controller("userManageWithJavaxValidationAction")
public class UserManageWithJavaxValidationAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Logger log = Logger.getLogger(UserManageWithJavaxValidationAction.class);
	
	@Valid(methods="doLogin")
	private JavaxValidationUser user;
	
	@Autowired
	private  UserAuthService userService;
	
	public String doLogin() throws Exception {
		
		log.info("begin doLogin!");
		if(user == null) {
			return LOGIN;
		}
		sessionSave(ValidationConstant.sessionUserTokenKey, user.getUsername());
		return SUCCESS;
//		if(userService.containsUser(user)) {
//			sessionSave(ValidationConstant.sessionUserTokenKey, user.getUsername());
//			return SUCCESS;
//		}
//		return LOGIN;
	}
	
	

	public String doLogout() throws Exception {
		HttpSession session = getSession(false);
		if(session != null) {
			session.invalidate();
		}
		return SUCCESS;
	}
	
	/**
	 * 登录成功时，自动跳转到该方法，有权限才能够访问<br/>
	 * 两种方式：<br/>
	 * 1、注解方式：直接标注<br/>
	 * 2、配置方式：可以选择标注注解（默认若标注注解，以注解为准，忽略配置;可以通过修改标志位，使配置与注解取并集；）<br/>
	 * @see com.revencoft.validation.auth.service.resource.grantedauthority.AbstractResourceGrantedAuthorityService
	 * @see com.revencoft.authexample.service.auth.CustomResourceAuthoritiesService.init()
	 * @return
	 * @throws Exception
	 */
	@Auth(value = "admin,manager")
//	@Auth(value = "user")
	public String accessTest() throws Exception {
		log.info("access permited!");
		return SUCCESS;
	}


	public void setUser(JavaxValidationUser user) {
		this.user = user;
	}


	public JavaxValidationUser getUser() {
		return user;
	}


}
