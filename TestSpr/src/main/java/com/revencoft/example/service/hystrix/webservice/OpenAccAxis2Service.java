/**
 * 
 */
package com.revencoft.example.service.hystrix.webservice;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.revencoft.example.annotation.Hystrix;
import com.revencoft.example.service.webservice.Axis2Client;

/**
 * 利用aop代理，将ws调用操作委托给Hystrix
 * @author mengqingyan
 * @version
 */
@Service
public class OpenAccAxis2Service {

	
	private final Logger log = Logger.getLogger(getClass());
	
	private Axis2Client axis2Client;
	
	private static String url = "https://183.62.250.17:16888/services/ObOnlineService?wsdl";
	// private static String url = "https://ac.pmec.com:9003/services/ObOnlineService?wsdl";
	// private static String url = "http://localhost:1688/axis2/services/ObOnlineService?wsdl";
	private static String namespace = "http://service.axis2.kaihu.yg.com";

	
	@Hystrix
	public String checkAccount(String account) throws Exception {

		String operateName = "checkLoginAccount";
		String xml = Axis2Client.checkAccountXml(account);
		String[] param = new String[] { xml };

		log.info("access axis2 WS for checkAccount.");

		Object obj = axis2Client.excute(url, namespace, operateName, param);
		return (String) obj;
	}
	
	@Hystrix
	public String getCustomerStatus(String account) throws Exception {
		String operateName = "GetCustomerStatus";
		String xml = Axis2Client.checkAccountXml(account);
		String[] param = new String[] { xml };

		log.info("access axis2 WS for GetCustomerStatus.");

		Object obj = axis2Client.excute(url, namespace, operateName, param);
		return (String) obj;
	}
	
	

	@PostConstruct
	public void init() {
		
		System.setProperty("javax.net.ssl.trustStore",
				"D:/ssl/client.truststore");

		axis2Client = new Axis2Client();
	}

}
