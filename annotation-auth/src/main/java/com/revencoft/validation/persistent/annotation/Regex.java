package com.revencoft.validation.persistent.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 正则验证
 * @author mengqingyan
 * 2015年3月19日
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
public @interface Regex {
	//验证规则
	Format regex() default Format.none;
	// pattern 指定的验证规则为首选项，若没指定pattern，则考虑regex指定的规则。
	String pattern() default "";
	
	public enum Format {
		
		email("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"),
		
		phone("0?(13|15|18|14|17)[0-9]{9}$"),
		
		none(null);
		
		private final String regex;
		
		private Format(String regex) {
			this.regex = regex;
		}
		
		public String getRegex() {
			return regex;
		}
		
	}
}
