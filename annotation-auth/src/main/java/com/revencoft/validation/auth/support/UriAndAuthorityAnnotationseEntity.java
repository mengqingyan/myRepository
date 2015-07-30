/**
 * 
 */
package com.revencoft.validation.auth.support;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;

import com.revencoft.validation.auth.GrantedAuthority;
import com.revencoft.validation.core.AnnotationsHolder;
import com.revencoft.validation.persistent.annotation.decider.AnnotationDecider;

/**
 * @author mengqingyan
 * @version 2.0
 */
public class UriAndAuthorityAnnotationseEntity implements UriAndAuthorityAnnotationsHolder {

	private String uri;
	
	private Collection<GrantedAuthority> authorities;
	
	private AnnotationDecider annoDecider;
	
	private AnnotationsHolder annoHolder;
	
	
	/**
	 * @param uri
	 * @param authorities
	 * @param annoDecider
	 * @param annoHolder
	 */
	public UriAndAuthorityAnnotationseEntity(String uri,
			Collection<GrantedAuthority> authorities,
			AnnotationDecider annoDecider, AnnotationsHolder annoHolder) {
		super();
		this.uri = uri;
		this.authorities = authorities;
		this.annoDecider = annoDecider;
		this.annoHolder = annoHolder;
	}

	@Override
	public String getUri() {
		return uri;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public AnnotationDecider getAnnotationDecider() {
		return annoDecider;
	}

	@Override
	public <T extends Annotation> T contains(Class<T> clazz) {
		return annoHolder.contains(clazz);
	}

	@Override
	public List<Annotation> getAnnotations() {
		return annoHolder.getAnnotations();
	}

	@Override
	public void setAnnotations(List<Annotation> annotations) {
		annoHolder.setAnnotations(annotations);
	}

	
}
