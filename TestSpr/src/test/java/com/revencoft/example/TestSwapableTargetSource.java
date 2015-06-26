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
public class TestSwapableTargetSource extends AbstractJUnit4SpringContextTests{
	
	private final Logger log = Logger.getLogger(getClass());
	
	@Autowired
	private UserService userService;
	@Autowired
	private BankService bankService;
	@Autowired
	private HotSwappableTargetSource swapper;
	
	@Test
	public void test() {
		Object userSer = applicationContext.getBean("swappable");
		log.info("userSer: " + userSer.getClass());
//		UserService uSer = (UserService) userSer;
		log.info("userSer instanceof UserService: " + (userSer instanceof UserService));
		
		log.info("userService: " + userService.getClass());
		log.info("bankService: " + bankService.getClass());
		Object old = swapper.swap(bankService);
		log.info("old: " + old.getClass());
		
		log.info("old: " + old.getClass());
		
		Object nowSer = applicationContext.getBean("swappable");
		log.info("nowSer: " + nowSer.getClass());
		
		log.info("nowSer instanceof BankService: " + (nowSer instanceof BankService));
		BankService bankSer = (BankService) nowSer;
		
		bankSer.printBankName("testBankName");
		
	}
	
	@Test
	public void testSwap() {
		UserService userRaw = (UserService) applicationContext.getBean("userService");
		UserService userRaw2 = (UserService) applicationContext.getBean("userService2");
		System.out.println("userRaw: " + userRaw);
		System.out.println("userRaw2: " + userRaw2);
//		AdvisedSupport advisedSupport = (AdvisedSupport) applicationContext.getBean("&swappable");
//		Advice advice = (Advice) applicationContext.getBean("userLogAdvice");
//		advisedSupport.addAdvice(advice );
		
		UserService userSer = (UserService) applicationContext.getBean("swappable");
		System.out.println("first userSer: " + userSer.getUserId());
		
		HotSwappableTargetSource swapper = (HotSwappableTargetSource) applicationContext.getBean("swapper");
		UserService old = (UserService) swapper.swap(userRaw2);
		System.out.println("old:" + old);
		
		
		UserService now = (UserService) applicationContext.getBean("swappable");
		System.out.println("now:" + now.getUserId());
		
	}

}
