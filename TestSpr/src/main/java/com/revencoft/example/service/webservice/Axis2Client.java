package com.revencoft.example.service.webservice;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import sun.misc.BASE64Encoder;

import com.revencoft.example.tools.SecurityUtil;



public class Axis2Client {
	private static Log log = LogFactory.getLog(Axis2Client.class);
	public Axis2Client() {
		// TODO Auto-generated constructor stub
	}


	public Object excute(String srvcUrl, String namespace, String operateName,
			Object param[]) throws Exception {

		QName qname = new QName(namespace, operateName);
		RPCServiceClient client = new RPCServiceClient();
		Options options = new Options();
		options.setTo(new EndpointReference(srvcUrl));
		options.setAction("urn:" + operateName);
		// options.setTimeOutInMilliSeconds(60000000L);
		client.setOptions(options);

		OMElement element = client.invokeBlocking(qname, param);

		if (element != null && !"".equals(element)) {
			Iterator<?> values = element.getChildrenWithName(new QName(namespace, "return"));
			while (values.hasNext()) {
				OMElement omElement = (OMElement) values.next();
				return omElement.getText();
			}
		} else {
			return element;
		}
		return "-1";
	}

	// call menth 2
	public static String sendService(String url, String namespace,
			String operateName, String[] param) throws AxisFault {
		String xml = null;
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		EndpointReference targetEPR = new EndpointReference(url);
		options.setTo(targetEPR);
		QName opAddEntry = new QName(namespace, operateName);
		Object[] opAddEntryArgs = new Object[] { param };
		Class[] classes = new Class[] { String.class };
		xml = (String) serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0];
		System.out.println(xml);
		return xml;
	}
	
	public static void main(String[] args) throws Exception {
		//172.20.43.48
		//http://localhost:8080/axis2_web/
		//https://localhost:1688/axis2_web/services/ObOnlineService?wsdl
		String url = "https://183.62.250.17:16888/services/ObOnlineService?wsdl";
//		String url = "https://ac.pmec.com:9003/services/ObOnlineService?wsdl";
//		String url = "http://localhost:1688/axis2/services/ObOnlineService?wsdl";
		String namespace = "http://service.axis2.kaihu.yg.com";
//		String operateName = "uploadFile";
//		String xml = uploadReqXml("jpg","c:\\xxx.jpg");
//String operateName = "openAccount";
		String operateName = "checkLoginAccount";
		//String xml = checkAccountXml("003000000123456");
		String xml = checkAccountXml("086000000000010");
		
		
		String[] param = new String[] {xml};
		
		System.setProperty("javax.net.ssl.trustStore","D:/ssl/client.truststore");  
		//System.setProperty("javax.net.ssl.trustStorePassword", "yladmin"); 
		
		
		Axis2Client axClient = new Axis2Client();
		Object obj = null;
//		for(int i = 0; i<1000;i++) {
			obj = axClient.excute(url, namespace, operateName, param);
//		}
		System.out.println(obj);
		
		//operateName = "helloNoParam";
		//obj = axClient.excute(url, namespace, operateName, param);
		//System.out.println(obj);
		
		//sendService(url, namespace, operateName, param);

	}
	
	/**
	 * 测试用，生成上传文件的xml
	 * @param fileExt
	 * @param fileName
	 * @return
	 */
	private static String uploadReqXml(String fileExt,String fileName ){
		Document document = DocumentHelper.createDocument();  
		document.setXMLEncoding("UTF-8");
		Element root = document.addElement("root"); 
		root.addElement("fileExt").setText(fileExt);
		root.addElement("fileName").setText(fileName);
		String wayCode = null;
		String wayType = null;
		try{
			wayCode = SecurityUtil.encryption("003",SecurityUtil.SECURITY_SERVICES);
			wayType = SecurityUtil.encryption("2",SecurityUtil.SECURITY_SERVICES);
		} catch(Exception e){
			log.error("加密会员标识失败");
			wayCode = "";
			wayType = "";
		}
		root.addElement("wayCode").setText(wayCode);
		root.addElement("wayType").setText(wayType);
		log.error("test:"+document.asXML());
		FileInputStream fin = null;
		ByteArrayOutputStream bout = null;
		try {
			fin = new FileInputStream(new File(fileName));
			bout = new ByteArrayOutputStream();
			byte[] buffer = new byte[10240];
			while(fin.read(buffer) != -1){
				bout.write(buffer);
			}
			BASE64Encoder encoder = new BASE64Encoder();
			String fileContent = encoder.encode(bout.toByteArray());
			Element eFileContent = root.addElement("fileContent");
			eFileContent.setText(fileContent);
			String xml = document.asXML();
			return xml;
		} catch(FileNotFoundException e ){
			log.error("文件不存在!");
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			try{
				fin.close();
				bout.close();
			} catch(IOException e){
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 测试用，生成开户的xml
	 * @param fileExt
	 * @param fileName
	 * @return
	 */
	public static String openAccXml() {
		Document document = DocumentHelper.createDocument();  
		document.setXMLEncoding("UTF-8");
		Element root = document.addElement("root"); 
		String wayCode = null;
		String wayType = null;
		try{
			wayCode = SecurityUtil.encryption("003",SecurityUtil.SECURITY_SERVICES);
			wayType = SecurityUtil.encryption("2",SecurityUtil.SECURITY_SERVICES);
		} catch(Exception e){
			log.error("加密会员标识失败");
			wayCode = "";
			wayType = "";
		}
		root.addElement("wayCode").setText(wayCode);
		root.addElement("wayType").setText(wayType);
		root.addElement("userName").setText("biao");
		/*root.addElement("sex").setText("1");
		root.addElement("nationality").setText("1");
		root.addElement("tel").setText("");*/
		root.addElement("mobilePhone").setText("13751744122");
		root.addElement("fax").setText("");
		root.addElement("loginAccount").setText("003999999999990");
		/*root.addElement("cityId").setText("020");
		root.addElement("branchId").setText("");*/
		root.addElement("certType").setText("8");
		root.addElement("certNo").setText("123111"); //620825196204271704
		root.addElement("email").setText("111@163.com");
		/*root.addElement("zip").setText("123456");
		root.addElement("addr").setText("gz");
		root.addElement("bankId").setText("4");
		root.addElement("bankAcc").setText("12345");*/
		root.addElement("recommender").setText("111111111");
		/*root.addElement("certPhoto1").setText("2014040410535917632.jpg");
		root.addElement("certPhoto2").setText("2014040410535917632.jpg");
		root.addElement("headPhoto").setText("2014040410535917632.jpg");*/
		root.addElement("signProto").setText("1");
		/*Element risk = root.addElement("riskCheck");
		Element sub1 = risk.addElement("subject");
		sub1.addAttribute("oid", "1");
		sub1.addAttribute("answer", "0");
		Element sub2 = risk.addElement("subject");
		sub2.addAttribute("oid", "2");
		sub2.addAttribute("answer", "1");

		Element sub3 = risk.addElement("subject");
		sub3.addAttribute("oid", "3");
		sub3.addAttribute("answer", "0;2");
		
		Element sub4 = risk.addElement("subject");
		sub4.addAttribute("oid", "4");
		sub4.addAttribute("answer", "2");
		
		Element sub5 = risk.addElement("subject");
		sub5.addAttribute("oid", "5");
		sub5.addAttribute("answer", "0");
		
		Element sub6 = risk.addElement("subject");
		sub6.addAttribute("oid", "6");
		sub6.addAttribute("answer", "0");
		
		Element sub7 = risk.addElement("subject");
		sub7.addAttribute("oid", "7");
		sub7.addAttribute("answer", "0");*/
		log.error("test:"+document.asXML());
		
		return document.asXML();

	}

	
	/**
	 * 测试用，测试帐号是否存在
	 * @param 登录帐号
	 * @param 
	 * @return
	 */
	public static String checkAccountXml(String loginAccount){
		Document document = DocumentHelper.createDocument();  
		document.setXMLEncoding("UTF-8");
		Element root = document.addElement("root"); 
		root.addElement("loginAccount").setText(loginAccount);
		String wayCode = null;
		String wayType = null;
		try{
			wayCode = SecurityUtil.encryption("086",SecurityUtil.SECURITY_SERVICES);
			wayType = SecurityUtil.encryption("2",SecurityUtil.SECURITY_SERVICES);
		} catch(Exception e){
			log.error("加密会员标识失败");
			wayCode = "";
			wayType = "";
		}
		root.addElement("wayCode").setText(wayCode);
		root.addElement("wayType").setText(wayType);
		log.error("test:"+document.asXML());
		return document.asXML();	
	}

	/**
	 * 测试用，测试风险测评是否通过
	 * @param 登录帐号
	 * @param 
	 * @return
	 */
	private static String checkRiskXml(){
		Document document = DocumentHelper.createDocument();  
		document.setXMLEncoding("UTF-8");
		Element root = document.addElement("root"); 
		String wayCode = null;
		String wayType = null;
		try{
			wayCode = SecurityUtil.encryption("003",SecurityUtil.SECURITY_SERVICES);
			wayType = SecurityUtil.encryption("2",SecurityUtil.SECURITY_SERVICES);
		} catch(Exception e){
			log.error("加密会员标识失败");
			wayCode = "";
			wayType = "";
		}
		root.addElement("wayCode").setText(wayCode);
		root.addElement("wayType").setText(wayType);
		Element risk = root.addElement("riskCheck");
		Element sub1 = risk.addElement("subject");
		sub1.addAttribute("oid", "1");
		sub1.addAttribute("answer", "1");
		Element sub2 = risk.addElement("subject");
		sub2.addAttribute("oid", "2");
		sub2.addAttribute("answer", "0");

		Element sub3 = risk.addElement("subject");
		sub3.addAttribute("oid", "3");
		sub3.addAttribute("answer", "1;2;3;4");
		
		Element sub4 = risk.addElement("subject");
		sub4.addAttribute("oid", "4");
		sub4.addAttribute("answer", "0");
		
		Element sub5 = risk.addElement("subject");
		sub5.addAttribute("oid", "5");
		sub5.addAttribute("answer", "1");
		
		Element sub6 = risk.addElement("subject");
		sub6.addAttribute("oid", "6");
		sub6.addAttribute("answer", "0");
		
		Element sub7 = risk.addElement("subject");
		sub7.addAttribute("oid", "7");
		sub7.addAttribute("answer", "1");
		log.error("test:"+document.asXML());
		return document.asXML();	
	}
	
	/**
	 * 
	 * @Title: CertNoExistXml 
	
	 * @Description: 校验证件号是否存在
	
	 * @param @return    设定文件 
	
	 * @return String    返回类型 
	
	 * @throws
	 */
	private static String CertNoExistXml(){
		Document document = DocumentHelper.createDocument();  
		document.setXMLEncoding("UTF-8");
		Element root = document.addElement("root"); 
		String wayCode = null;
		String wayType = null;
		try{
			wayCode = SecurityUtil.encryption("003",SecurityUtil.SECURITY_SERVICES);
			wayType = SecurityUtil.encryption("2",SecurityUtil.SECURITY_SERVICES);
		} catch(Exception e){
			log.error("加密会员标识失败");
			wayCode = "";
			wayType = "";
		}
		root.addElement("wayCode").setText(wayCode);
		root.addElement("wayType").setText(wayType);
		
		root.addElement("certType").setText("8");
		root.addElement("certNo").setText("111111");
		return document.asXML();
	
	
	}
	
	private static String ContractBankCheckXml(){
		Document document = DocumentHelper.createDocument();  
		document.setXMLEncoding("UTF-8");
		Element root = document.addElement("root"); 
		String wayCode = null;
		String wayType = null;
		try{
			wayCode = SecurityUtil.encryption("003",SecurityUtil.SECURITY_SERVICES);
			wayType = SecurityUtil.encryption("2",SecurityUtil.SECURITY_SERVICES);
		} catch(Exception e){
			log.error("加密会员标识失败");
			wayCode = "";
			wayType = "";
		}
		root.addElement("wayCode").setText(wayCode);
		root.addElement("wayType").setText(wayType);
		
		root.addElement("loginAccount").setText("222222");
		return document.asXML();
	
	
	}
	
	private static String getuserinfosxml(){
		Document document = DocumentHelper.createDocument();  
		document.setXMLEncoding("UTF-8");
		Element root = document.addElement("root"); 
		String wayCode = null;
		String wayType = null;
		try{
			wayCode = SecurityUtil.encryption("086",SecurityUtil.SECURITY_SERVICES);
			wayType = SecurityUtil.encryption("2",SecurityUtil.SECURITY_SERVICES);
		} catch(Exception e){
			log.error("加密会员标识失败");
			wayCode = "";
			wayType = "";
		}
		root.addElement("wayCode").setText(wayCode);
		root.addElement("wayType").setText(wayType);
		
		root.addElement("loginAccount").setText("086000000000001");
		root.addElement("mobilePhone").setText("13535113513");
		return document.asXML();
	
	
	}
	
	
	private static String getuserstatusxml(){
		Document document = DocumentHelper.createDocument();  
		document.setXMLEncoding("UTF-8");
		Element root = document.addElement("root"); 
		String wayCode = null;
		String wayType = null;
		try{
			wayCode = SecurityUtil.encryption("086",SecurityUtil.SECURITY_SERVICES);
			wayType = SecurityUtil.encryption("2",SecurityUtil.SECURITY_SERVICES);
		} catch(Exception e){
			log.error("加密会员标识失败");
			wayCode = "";
			wayType = "";
		}
		root.addElement("wayCode").setText(wayCode);
		root.addElement("wayType").setText(wayType);
		
		root.addElement("loginAccount").setText("086000000000001");
		return document.asXML();
	
	
	}
	
}
