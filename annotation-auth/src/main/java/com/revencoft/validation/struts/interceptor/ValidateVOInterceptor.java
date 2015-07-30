package com.revencoft.validation.struts.interceptor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.revencoft.validation.auth.decider.AuthenticationDecider;
import com.revencoft.validation.exception.handler.ValidationExceptionHandler;
import com.revencoft.validation.persistent.validator.CacheablePropertyValidator;
import com.revencoft.validation.persistent.validator.PropertyValidator;


/**
 * 增加自定义错误提示
 * @author mengqingyan
 * @since 1.0
 * @version 1.2.3 <br/>
 * 				修改获取缓存数据的同步锁，减小同步粒度<br/>
 * 
 * @version 2.1 重构数据验证，支持javax.validation
 */

public class ValidateVOInterceptor extends AbstractInterceptor implements InitializingBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6745736266626626620L;
	
	private static final Logger log = Logger.getLogger(ValidateVOInterceptor.class);

	private static final String IVALIDATION_ERROR ="validation.error";
	
	/**
	 * 权限控制
	 */
	private AuthenticationDecider authDecider;
	
	/**
	 * 数据验证
	 */
	private PropertyValidator validator;
	
	/**
	 * 异常处理
	 */
	private ValidationExceptionHandler exHandler;
	
	@Inject
	private Container container;
	
	public String intercept(ActionInvocation invocation) throws Exception {
		
		long begin = System.currentTimeMillis();
		try {
			authDecider.authenticate(invocation);
			validator.validate(invocation);
			
		} catch (Exception e) {
			exHandler.processValidationException(invocation, e);
			return IVALIDATION_ERROR;
		}
		long end = System.currentTimeMillis();
		log.debug("time elapsed by validation(ms): " + (end - begin));
		return invocation.invoke();
	}



	@Override
	public void init() {
		container.inject(exHandler);
	}



	@Override
	public void destroy() {
		super.destroy();
		if(validator instanceof CacheablePropertyValidator) {
			CacheablePropertyValidator cacheableValidator = (CacheablePropertyValidator) validator;
			cacheableValidator.refresh();
		}
	}


	public void afterPropertiesSet() throws Exception {
		Assert.notNull(authDecider, "AuthenticationDecider can't be null!");
		Assert.notNull(validator, "PropertyValidator can't be null!");
		Assert.notNull(exHandler, "ValidationExceptionHandler can't be null!");
	}

	public void setAuthDecider(AuthenticationDecider authDecider) {
		this.authDecider = authDecider;
	}

	
	public void setValidator(PropertyValidator validator) {
		this.validator = validator;
	}

	public void setExHandler(ValidationExceptionHandler exHandler) {
		this.exHandler = exHandler;
	}

}
