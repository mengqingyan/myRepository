/**
 * 
 */
package com.revencoft.example.service.webservice;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.revencoft.example.service.webservice.exception.CustomAxis2Exception;

/**
 * @author mengqingyan
 * @version 
 */
public class CheckAccountCommand extends HystrixCommand<String>{
	
	private final Logger log = Logger.getLogger(getClass());
	
	private static String url = "https://183.62.250.17:16888/services/ObOnlineService?wsdl";
	// private static String url = "https://ac.pmec.com:9003/services/ObOnlineService?wsdl";
	// private static String url = "http://localhost:1688/axis2/services/ObOnlineService?wsdl";
	private static String namespace = "http://service.axis2.kaihu.yg.com";

	private Axis2Client axis2Client;
	private String account;

	/**
	 * @param commandConfig
	 * @param axis2Client
	 * @param account
	 */
	public CheckAccountCommand(Setter commandConfig, Axis2Client axis2Client,
			String account) {
		super(commandConfig);
		this.axis2Client = axis2Client;
		this.account = account;
	}

	/* (non-Javadoc)
	 * @see com.netflix.hystrix.HystrixCommand#run()
	 */
	@Override
	protected String run() throws Exception {

		String operateName = "checkLoginAccount";
		String xml = Axis2Client.checkAccountXml(account);
		String[] param = new String[] { xml };
		
		log.info("access axis2 WS for checkAccount.");
				
		try {
			Object obj = axis2Client.excute(url, namespace, operateName, param);
			return (String) obj;
		} catch (CustomAxis2Exception e) {
			throw handleException(e);
		}
	}
	
	
	
	@Override
	protected String getFallback() {
		return "NULL";
	}

	/**
	 * 处理异常，对于客户端自己的异常，抛出HystrixBadRequestException，不算入短路统计内。
	 */
	protected Exception handleException(CustomAxis2Exception e) {
		HttpStatus status = e.getStatusCode();
		if (status.equals(HttpStatus.BAD_REQUEST)) {
			throw new HystrixBadRequestException(e.getMessage(), e);
		}
		throw e;
	}
}
