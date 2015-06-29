package com.revencoft.example.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.revencoft.example.annotation.SecurityAnnotation;


/**
 * 
 * @deprecated(加密工具类)
 */
public class SecurityUtil {

	private static String Algorithm = "DES"; // 加密算法 DES,DESede,Blowfish
	public static final String SECURITY_WEB = "70469B26CBF1E575"; // web url加密
	public static final String SECURITY_SERVICES = "652419B1ABD9F888"; // 接口url加密	
	public static final String SECURITY_DATABASE = "123456B1ABD9F999"; // 数据库数据加密
	public static final String SECURITY_ERROR = "500";// 加密异常
	public static final String SECURITY_SERVICES_WAYONE = "098765B1ABD9F888"; // 道一接口url加密

	@SuppressWarnings("unused")
	private static byte[] getKey() throws Exception {
		KeyGenerator keygen = KeyGenerator.getInstance(Algorithm);
		SecretKey deskey = keygen.generateKey();
		return deskey.getEncoded();
	}

	private static byte[] encode(byte[] input, byte[] key) throws Exception {
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] cipherByte = c1.doFinal(input);
		return cipherByte;
	}

	private static byte[] decode(byte[] input, byte[] key) throws Exception {
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.DECRYPT_MODE, deskey);
		byte[] clearByte = c1.doFinal(input);
		return clearByte;
	}

	private static String byte2hex(byte bytes[]) {
		StringBuffer retString = new StringBuffer();
		for (int i = 0; i < bytes.length; ++i) {
			retString.append(Integer.toHexString(0x0100 + (bytes[i] & 0x00FF)).substring(1).toUpperCase());
		}
		return retString.toString();
	}

	private static byte[] hex2byte(String hex) {
		byte[] bts = new byte[hex.length() / 2];
		for (int i = 0; i < bts.length; i++) {
			bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bts;
	}

	/**
	 * 
	 * <b>Summary: </b> encryption(加密 securityKey default WEB)
	 * 
	 * @return
	 * @return String
	 */
	public static String encryption(String str) throws Exception {
		return byte2hex(SecurityUtil.encode(str.getBytes(), hex2byte(SECURITY_WEB)));
	}

	public static String encryption(String str, String securityKey) throws Exception {
		return byte2hex(SecurityUtil.encode(str.getBytes(), hex2byte(securityKey)));
	}

	/**
	 * 
	 * <b>Summary: </b> decode(解密 securityKey default WEB)
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 * @return String
	 */
	public static String decode(String code) throws Exception {
		return new String(SecurityUtil.decode(hex2byte(code), hex2byte(SECURITY_WEB)));
	}

	public static String decode(String code, String securityKey) throws Exception {
		return new String(SecurityUtil.decode(hex2byte(code), hex2byte(securityKey)));
	}

	/**
	 * 
	 * <b>Summary: </b> doEncryptionAndDecode(根据注解进行对象属性加解密)
	 * 
	 * @param o
	 * @param flag
	 *            true加密 false解密
	 * @param securityKey
	 * @throws Exception
	 * @return void
	 */
	public static void doEncryptionAndDecode(Object o, boolean flag, String securityKey) throws Exception {
		if (null == o)
			return;
		Class<?> clazz = Class.forName(o.getClass().getCanonicalName());
		Field[] fileds = clazz.getDeclaredFields();
		for (Field field : fileds) {
			SecurityAnnotation encryption = field.getAnnotation(SecurityAnnotation.class);
			if ("java.lang.String".equals(field.getType().getCanonicalName()) && null != encryption
					&& encryption.value()) {
				String fieldName = field.getName();
				String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				Method set = clazz.getMethod("set" + methodName, new Class[] { field.getType() });
				Method get = clazz.getMethod("get" + methodName, new Class[] {});
				String value = (String) get.invoke(o, new Object[] {});
				if (null != value && !"".equals(value)) {
					if (flag) {
						set.invoke(o, new Object[] { encryption(value, securityKey) });
					} else {
						set.invoke(o, new Object[] { decode(value, securityKey) });
					}
				} else {
					continue;
				}
			}
		}
	}

	/**
	 * 
	 * <b>Summary: </b> doEncryptionAndDecode(根据注解进行集合对象属性加解密)
	 * 
	 * @param list
	 * @param flag
	 *            true加密 false解密
	 * @param securityKey
	 * @throws Exception
	 * @return void
	 */
	public static void doEncryptionAndDecode(List<?> list, boolean flag, String securityKey) throws Exception {
		for (Object o : list) {
			doEncryptionAndDecode(o, flag);
		}
	}

	public static void doEncryptionAndDecode(Object o, boolean flag) throws Exception {
		doEncryptionAndDecode(o, flag, SECURITY_DATABASE);
	}

	public static void doEncryptionAndDecode(List<?> list, boolean flag) throws Exception {
		doEncryptionAndDecode(list, flag, SECURITY_DATABASE);
	}

	public static void main(String[] args) {
		String test = "5555566664446655555"; // 加密字符一个1-7个字节加密后长度16位，8>=
												// <16个字节加密32位
		if (args != null && args.length > 0) {
			test = args[0];
		}
		try {
			// defalut web
			String code = encryption(test);
			System.out.println(code);
			System.out.println(decode(code));
			// services
			String code2 = encryption(test, SecurityUtil.SECURITY_SERVICES);
			System.out.println(code2);
			System.out.println(decode(code2, SecurityUtil.SECURITY_SERVICES));
			// database
			String code3 = encryption(test, SecurityUtil.SECURITY_DATABASE);
			System.out.println(code3);
			System.out.println(decode(code3, SecurityUtil.SECURITY_DATABASE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
