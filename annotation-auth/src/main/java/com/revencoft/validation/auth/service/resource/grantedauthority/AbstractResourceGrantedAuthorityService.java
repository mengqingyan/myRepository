/**
 * 
 */
package com.revencoft.validation.auth.service.resource.grantedauthority;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.revencoft.validation.auth.GrantedAuthority;
import com.revencoft.validation.cache.Cache;
import com.revencoft.validation.cache.CachesHolder;
import com.revencoft.validation.persistent.annotation.Auth;
import com.revencoft.validation.utils.AuthorityUtil;
import com.revencoft.validation.utils.UriNameUtil;
import com.revencoft.validation.utils.matcher.RegexRequestMatcher;
import com.revencoft.validation.utils.matcher.RequestMatcher;


/**
 * 1.从数据库中加载所有资源权限信息
 * 2.根据当前访问的uri，获取访问该Uri所需的权限信息，并进行缓存，以后均从缓存中获取，无需再访问数据库
 * 3.可以结合注解使用
 * @author mengqingyan
 * @version 
 */
public abstract class AbstractResourceGrantedAuthorityService implements
		ResourceGrantedAuthorityService, InitializingBean {
	
	protected final Logger log = Logger.getLogger(getClass());
	
	private static final String resourceGrantedAuthorityKey = AbstractResourceGrantedAuthorityService.class + ".resource.authority";
	private static final String customResourceGrantedAuthorityKey = resourceGrantedAuthorityKey + ".custom";
	
	//格式为所访问资源--访问该资源所需的权限
	private Cache<String, Collection<GrantedAuthority>> resAuthCache = CachesHolder.getInstance().getNamedCache(resourceGrantedAuthorityKey);
	//格式为数据库中资源字符串（正则表达式格式）--访问该资源所需的权限
	private Cache<String, Collection<GrantedAuthority>> cusUriAuthCache = CachesHolder.getInstance().getNamedCache(customResourceGrantedAuthorityKey);
	
	/**
	 * 指定，若使用注解标注action方法时，是否只以注解为准；<br/>
	 * false:数据库配置与注解取并集
	 * true:只以注解为准，忽略数据库配置,若无注解或注解无效，则应用数据库配置
	 */
	protected boolean annotationOnlyWhenPresent = true;
	
	@Override
	public Collection<GrantedAuthority> loadAuthority(
			ActionInvocation invocation) {
		Assert.notNull(invocation, "invocation can't be null when load Resource grantedAuthority！");
		return getResourceGrantedAuthority(invocation);
	}

	@Override
	public Cache<String, Collection<GrantedAuthority>> getAllResourceAuthorities() {
		return resAuthCache;
	}

	private Collection<GrantedAuthority> getResourceGrantedAuthority(ActionInvocation invocation) {
		ActionProxy actionProxy = invocation.getProxy();
		String uri = UriNameUtil.getFullActionName(actionProxy);
		if(!resAuthCache.contains(uri)) {
			assembleResourceAuthority(uri, actionProxy);
		}
		return resAuthCache.get(uri);
	}

	/**
	 * 加载自定义资源权限信息
	 * @return
	 */
	protected abstract Map<String, Collection<GrantedAuthority>> loadCustomResourceAuthority();

	/**
	 * 根据Uri，得到访问它所需的权限，放入resAuthCache缓存
	 * @param uri 资源字符串
	 * @param actionProxy 
	 */
	protected void assembleResourceAuthority(String uri, ActionProxy actionProxy) {
		
		if(cusUriAuthCache == null || cusUriAuthCache.isEmpty() || actionProxy == null) {
			return;
		}
		Collection<GrantedAuthority> auths = null;
		
		boolean iAnnotationEffective = false;
		String methodName = actionProxy.getMethod();
		Object action = actionProxy.getAction();
		Method method = null;
		try {
			method = action.getClass().getMethod(methodName, new Class[0]);
		} catch (Exception e) {
			log.error("获取方法权限注解失败！", e);
		}
		if(method != null) {
			Auth auth = method.getAnnotation(Auth.class);
			if(auth != null) {
				Collection<GrantedAuthority> authorities = AuthorityUtil.getGrantedAuthorities(auth, auth.annotationType());
				if(authorities != null && !authorities.isEmpty()) {
					if(auths == null) {
						auths = new HashSet<GrantedAuthority>();
					}
					auths.addAll(authorities);
					iAnnotationEffective = true;
				}
			}
			
		}
		
		
		if(!(iAnnotationEffective && annotationOnlyWhenPresent)) {
			HttpServletRequest request = ServletActionContext.getRequest();
			
			Iterator<String> iterator = cusUriAuthCache.keySet().iterator();
			
			while (iterator.hasNext()) {
				String uriStr = iterator.next();
				RequestMatcher matcher = new RegexRequestMatcher(uriStr, request.getMethod(), true);
				if(matcher.matches(request)) {
					if(auths == null) {
						auths = cusUriAuthCache.get(uriStr);
					} else {
						auths.addAll(cusUriAuthCache.get(uriStr));
					}
				}
			}
		}
		
		
			
		if(auths == null) {
			auths = Collections.emptySet();
		}
		resAuthCache.put(uri, auths);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(cusUriAuthCache == null) {
			return;
		}
		Map<String, Collection<GrantedAuthority>> customResourceAuthority = loadCustomResourceAuthority();
		if(customResourceAuthority == null || customResourceAuthority.isEmpty()) {
			return;
		}
		Iterator<Entry<String, Collection<GrantedAuthority>>> iterator = customResourceAuthority.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Collection<GrantedAuthority>> entry = iterator.next();
			cusUriAuthCache.put(entry.getKey(), entry.getValue());
		}
	}

	public void setAnnotationOnlyWhenPresent(String annotationOnlyWhenPresent) {
		this.annotationOnlyWhenPresent = Boolean.parseBoolean(annotationOnlyWhenPresent);
	}
	
}
