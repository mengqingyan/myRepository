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
import com.revencoft.example.service.webservice.OpenAccAxis2Service;

/**
 * @author mengqingyan
 * @version 
 */

@ActiveProfiles(profiles="testWS")
@DirtiesContext
@ContextConfiguration(locations={"/application-context.xml"})
public class TestHytrixWithAxis2 extends SpringContextTestCase{

	private final Logger log = Logger.getLogger(getClass());
	
	@Autowired
	private OpenAccAxis2Service opAccService;
	
	@Test
	public void checkAccount() throws Exception {
		String result = opAccService.checkAccount("086000000000010");
		System.out.println("res: " + result);
	}
	
	@Test
	public void multiAccess() throws Exception {
		for (int i = 0; i < 10; i++) {
			try {
				checkAccount();
			} catch (Exception e) {
				log.error("failure...");
			}
		}
		
		Thread.sleep(21000);
		
		checkAccount();
	}
	
}
