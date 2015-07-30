package com.revencoft.validation.persistent.annotation.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import org.springframework.util.Assert;

import com.revencoft.validation.core.SimpleAnnotationHolder;
import com.revencoft.validation.utils.ValidateUtil;

/**
 * @author mengqingyan
 * @version: 2.0
 */

public class FieldAndAnnotationsEntity extends SimpleAnnotationHolder implements FieldAndAnnotationsHolder{
	
	private Field field;
	private List<Annotation> objectAnnotations;
	
	
	public FieldAndAnnotationsEntity() {
		super();
	}

	public FieldAndAnnotationsEntity(Field field) {
		super();
		this.field = field;
	}
	
	public Field getField() {
		return field;
	}


	public void setField(Field field) {
		this.field = field;
	}


	public List<Annotation> getObjectAnnotations() {
		return this.objectAnnotations;
	}

	public void setObjectAnnotations(List<Annotation> annotations) {
		this.objectAnnotations = annotations;
	}
	
	public boolean containsObjAnnotation() {
		if(this.objectAnnotations != null)
			return !this.objectAnnotations.isEmpty();
		return false;
	}

	@Override
	public String toString() {
		return String.format(
				"FieldAndAnnotationsEntity [field=%s, annotations=%s]",
				field.getName(), annotations);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((annotations == null) ? 0 : annotations.hashCode());
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldAndAnnotationsEntity other = (FieldAndAnnotationsEntity) obj;
		if (annotations == null) {
			if (other.annotations != null)
				return false;
		} else if (!annotations.equals(other.annotations))
			return false;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		return true;
	}

	@Override
	public boolean isBaseDataType() throws Exception {
		Assert.notNull(field, "field can't be null!");
		return ValidateUtil.isBaseDataType(field.getType());
	}

	@Override
	public Object getFieldValue(Object target) throws Exception {
		
		return ValidateUtil.readFieldValue(target, field);
	}

	

	
}
