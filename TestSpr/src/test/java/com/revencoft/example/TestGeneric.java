/**
 * 
 */
package com.revencoft.example;

import org.junit.Assert;
import org.junit.Test;

import com.revencoft.example.generic.GenericUtils;
import com.revencoft.example.service.UserService;
import com.revencoft.example.service.impl.UserServiceImpl;

/**
 * @author mengqingyan
 * @version 
 */
public class TestGeneric {

	@Test
	public void testInstance() {
		UserService uSer = new UserServiceImpl();
		
		boolean testInstance = GenericUtils.testInstance(UserService.class, uSer);
		Assert.assertTrue(testInstance);
	}
	
	@Test
	public void testCreateInstance() {
		UserService serviceImpl = GenericUtils.createGeneric(UserServiceImpl.class);
		
		boolean testInstance = GenericUtils.testInstance(UserService.class, serviceImpl);
		Assert.assertTrue(testInstance);
	}
	
	@Test
	public void testCreateGenericArray() {
		UserService[] uSerArray = GenericUtils.createGenericArray(UserService.class, 10);
		uSerArray[0] = new UserServiceImpl();
		Assert.assertTrue(uSerArray.length == 10);
		System.out.println(uSerArray[0]);
	}
}
