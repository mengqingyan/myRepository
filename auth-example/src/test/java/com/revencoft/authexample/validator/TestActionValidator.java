/**
 * 
 */
package com.revencoft.authexample.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revencoft.authexample.vo.JavaxValidationEmployee;
import com.revencoft.authexample.vo.JavaxValidationUser;

/**
 * @author mengqingyan
 * @version
 */
public class TestActionValidator {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testValidAction() {
		JavaxValidationUser user = new JavaxValidationUser();
		user.setUsername("managerUser");
		user.setPassword("manager123");
		user.setAddress(null);
		JavaxValidationEmployee emp = new JavaxValidationEmployee();
		emp.setUsername("emptest");
		user.setEmp(emp);
		Set<ConstraintViolation<JavaxValidationUser>> violations = validator
				.validate(user);
		Assert.assertEquals(violations.size(), 2);
	}

}
