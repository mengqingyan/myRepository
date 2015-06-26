/**
 * 
 */
package com.revencoft.example;

import org.aopalliance.aop.Advice;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.target.HotSwappableTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.revencoft.example.service.BankService;
import com.revencoft.example.service.UserService;

/**
 * @author mengqingyan
 * @version 
 */
@DirtiesContext
@ContextConfiguration(locations={"classpath:application-context.xml"})
public class TestAutowire extends AbstractJUnit4SpringContextTests{
	
	private final Logger log = Logger.getLogger(getClass());
	
	@Autowired
	private UserService userService;
	
	@Test
	public void test() {
		
		System.out.println(userService);
	}
}
