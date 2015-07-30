/**
 * 
 */
package com.revencoft.validation.auth.decider.annotationdecider;

import java.lang.annotation.Annotation;
import java.util.Collection;

import com.opensymphony.xwork2.ActionInvocation;
import com.revencoft.validation.auth.GrantedAuthority;
import com.revencoft.validation.auth.support.UriAndAuthorityAnnotationsHolder;
import com.revencoft.validation.auth.support.UriAndAuthorityAnnotationseEntity;
import com.revencoft.validation.core.AnnotationsHolder;
import com.revencoft.validation.exception.ValidationException;
import com.revencoft.validation.persistent.annotation.Auth;
import com.revencoft.validation.utils.AuthorityUtil;
import com.revencoft.validation.utils.UriNameUtil;

/**
 * @author mengqingyan
 * @version 
 */
public class CacheableAuthAnnotationDecider extends
		AbstractCacheableAuthenticationAnnotationDecider {

	@Override
	public Class<? extends Annotation> getDecidedAnnotationType() {
		return Auth.class;
	}

	@Override
	public boolean decideToExec(final ActionInvocation invocation,
			Annotation annotation, Object target) throws ValidationException {
		return decideToExec(invocation, annotation, target, new CacheCallBack<String, UriAndAuthorityAnnotationsHolder>() {

			@Override
			public void addCacheableData(String key,
					UriAndAuthorityAnnotationsHolder value) {
				authCache.put(key, value);
			}

			
		});
	}

	@Override
	public boolean decideToExec(ActionInvocation invocation,
			Annotation annotation, Object target, CacheCallBack<String, UriAndAuthorityAnnotationsHolder> callBack)
			throws ValidationException {
		if(annotation == null) {
			//continue
			execCacheCallBack(callBack, invocation, null);
			return true;
		}
		
		Auth auth = (Auth) annotation;
		
		Collection<GrantedAuthority> uriAuthorities = AuthorityUtil.getGrantedAuthorities(auth, getDecidedAnnotationType());
		if(uriAuthorities == null || uriAuthorities.isEmpty()) {
			execCacheCallBack(callBack, invocation, null);
			return true;
		}
		
		Collection<GrantedAuthority> userAuthorities = userAuthoritiesService.loadAuthority(invocation);
		if(userAuthorities == null) {
			execCacheCallBack(callBack, invocation, uriAuthorities);
			return false;
		}
		boolean isAuthValid = false;
		
		for (GrantedAuthority userAuthority : userAuthorities) {
			if(uriAuthorities.contains(userAuthority)) {
				isAuthValid = true;
				break;
			}
		}
		execCacheCallBack(callBack, invocation, uriAuthorities);
		return isAuthValid;
	}
	
	
//	private Collection<GrantedAuthority> getGrantedAuthorities(Annotation auth) {
//		if(auth == null || auth.annotationType() != getDecidedAnnotationType()) {
//			return null;
//		}
//		Auth authAnno = (Auth) auth;
//		String roleStr = authAnno.value();
//		String[] roles = StringUtils.split(roleStr, ",");
//		if(roles == null || roles.length == 0) {
//			return null;
//		}
//		Set<GrantedAuthority> authrities = new HashSet<GrantedAuthority>();
//		for(String role : roles) {
//			authrities.add(new SimpleGrantedAuthority(role));
//		}
//		return authrities;
//	}

	private void execCacheCallBack(CacheCallBack<String, UriAndAuthorityAnnotationsHolder> callBack, ActionInvocation invocation, Collection<GrantedAuthority> authorities) {
		if(callBack == null) {
			return;
		}
		
		AnnotationsHolder annotationsHolder;
		try {
			annotationsHolder = createAnnotationsHolder(invocation);
		} catch (ValidationException e) {
			log.error("can't cache data!", e);
			return;
		}
		
		String fActionName = UriNameUtil.getFullActionName(invocation.getProxy());
		callBack.addCacheableData(fActionName,
				new UriAndAuthorityAnnotationseEntity(fActionName, authorities,
						validDecider, annotationsHolder));
	}

}
