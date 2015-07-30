package com.revencoft.validation.utils;



/**
 * 
 * <p>Title: strfilter</p>
 * <p>Description:  防止sql注入</p>
 * @author  hehe
 * @version 1.0
 * @date 2014-3-28下午3:21:10
 */



public class StrFilter  {
	
	/**
	 * 
	 * <p>Description:参数处理结果 <p>
	 * @param pamram 入参
	 * @return 如果有注入sql返回false，否则ture
	 */
	public static boolean  paramCheck(String pamram) {
		String FilterKeys[] = { "drop", "exec", "insert", "select", "delete",
				"update", "create","master", "truncate", "declare" ,"merge"};
		if (null != pamram && !"".equals(pamram)) {
			for (String filterkey : FilterKeys) {
				if (pamram.toLowerCase().indexOf(filterkey) > -1) {
					return false;
				}
			}
		}

		return true;
		
	}

}


