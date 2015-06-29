/**
 * 
 */
package com.revencoft.example.webservice;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.revencoft.example.SpringContextTestCase;
import com.revencoft.example.service.hystrix.webservice.OpenAccAxis2Service;

/**
 * @author mengqingyan
 * @version 
 */

@ActiveProfiles(profiles="testWS")
@DirtiesContext
@ContextConfiguration(locations={"/application-context.xml"})
public class TestHytrixWithAxis2WithAOP extends SpringContextTestCase{

	private final Logger log = Logger.getLogger(getClass());
	
	@Autowired
	private OpenAccAxis2Service opAccService;
	
	@Test
	public void checkAccountWithAop() throws Exception {
		String result = opAccService.checkAccount("086000000000010");
		System.out.println("res: " + result);
	}
	
	@Test
	public void multiAccessWithAop() throws Exception {
		for (int i = 0; i < 10; i++) {
			try {
				checkAccountWithAop();
			} catch (Exception e) {
				log.error("failure...");
			}
		}
		
		Thread.sleep(21000);
		
		checkAccountWithAop();
	}
	
	@Test
	public void getCustomStatusWithAop() throws Exception {
		String status = opAccService.getCustomerStatus("086000000000010");
		System.out.println("res: " + status);
	}
	
}
