package com.lawu.utils;

public class PropertiesUtil {

//	private static PropertiesUtil instance = null;
//	private static Properties config = null;
//
//	/**
//	 * 获得配置文件的健值
//	 * 
//	 * @param property
//	 * @param defaultValue
//	 * @return
//	 */
//	public static String getPropertyValue(String property,String filename) {
//
//		if (instance == null) {
//			instance = new PropertiesUtil();
//			config = new Properties();
//			try {
//				InputStream is = PropertiesUtil.class.getResourceAsStream("/" + filename);
//				config.load(is);
//
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//		}
//		try {
//			String value = config.getProperty(property);
//			if (value != null && !"".equals(value)) {
//				return value;
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		return "";
//	}
}
