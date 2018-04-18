package com.redxun.saweb.util;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.util.DateUtil;
import com.redxun.core.util.StringUtil;
/**
 * 请求处理工具类
 * 
 * @author csx
 */
public class RequestUtil {
	
	
	public static String getString(HttpServletRequest request, String key){
		return getString(request,  key, "");
	}
	
	public static String getString(HttpServletRequest request, String key,String defaultValue){
		String val=request.getParameter(key);
		if(StringUtil.isNotEmpty(val)){
			return val;
		}
		return defaultValue;
	}
	
	
	
	
	/**
	 * 从request中取得int值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static int getInt(HttpServletRequest request, String key) {
		return getInt(request, key, 0);
	}
	/**
	 * 从request中取得int值,如果无值则返回缺省值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static int getInt(HttpServletRequest request, String key, int defaultValue) {
		String str = request.getParameter(key);
		if (StringUtils.isEmpty(str)) {
			return defaultValue;
		}
		return Integer.parseInt(str);
	}
	/**
	 * 从Request中取得long值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static long getLong(HttpServletRequest request, String key) {
		return getLong(request, key, 0);
	}
	/**
	 * 取得长整形数组
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static Long[] getLongAry(HttpServletRequest request, String key) {
		String[] aryKeys = request.getParameterValues(key);
		if (aryKeys == null || aryKeys.length == 0) {
			return null;
		}
		Long[] aryLong = new Long[aryKeys.length];
		for (int i = 0; i < aryKeys.length; i++) {
			aryLong[i] = Long.parseLong(aryKeys[i]);
		}
		return aryLong;
	}
	/**
	 * 根据一串逗号分隔的长整型字符串取得长整形数组
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static Long[] getLongAryByStr(HttpServletRequest request, String key) {
		String sysUserId = request.getParameter(key);
		String[] aryId = sysUserId.split(",");
		Long[] lAryId = new Long[aryId.length];
		for (int i = 0; i < aryId.length; i++) {
			lAryId[i] = Long.parseLong(aryId[i]);
		}
		return lAryId;
	}
	/**
	 * 根据一串逗号分隔的长整型字符串取得长整形数组
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String[] getStringAryByStr(HttpServletRequest request, String key) {
		String ids = request.getParameter(key);
		String[] aryId = ids.split(",");
		return aryId;
	}
	/**
	 * 根据键值取得整形数组
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static Integer[] getIntAry(HttpServletRequest request, String key) {
		String[] aryKey = request.getParameterValues(key);
		Integer[] aryInt = new Integer[aryKey.length];
		for (int i = 0; i < aryKey.length; i++) {
			aryInt[i] = Integer.parseInt(aryKey[i]);
		}
		return aryInt;
	}
	public static Float[] getFloatAry(HttpServletRequest request, String key) {
		String[] aryKey = request.getParameterValues(key);
		Float[] fAryId = new Float[aryKey.length];
		for (int i = 0; i < aryKey.length; i++) {
			fAryId[i] = Float.parseFloat(aryKey[i]);
		}
		return fAryId;
	}
	/**
	 * 从Request中取得long值,如果无值则返回缺省值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static long getLong(HttpServletRequest request, String key, long defaultValue) {
		String str = request.getParameter(key);
		if (StringUtils.isEmpty(str)) {
			return defaultValue;
		}
		return Long.parseLong(str);
	}
	/**
	 * 从Request中取得float值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static float getFloat(HttpServletRequest request, String key) {
		return getFloat(request, key, 0);
	}
	/**
	 * 从Request中取得float值,如无值则返回缺省值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static float getFloat(HttpServletRequest request, String key, float defaultValue) {
		String str = request.getParameter(key);
		if (StringUtils.isEmpty(str)) {
			return defaultValue;
		}
		return Float.parseFloat(request.getParameter(key));
	}
	/**
	 * 从Request中取得boolean值,如无值则返回缺省值 false, 如值为数字1，则返回true
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(HttpServletRequest request, String key) {
		return getBoolean(request, key, false);
	}
	/**
	 * 从Request中取得boolean值 对字符串,如无值则返回缺省值, 如值为数字1，则返回true
	 * 
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(HttpServletRequest request, String key, boolean defaultValue) {
		String str = request.getParameter(key);
		if (StringUtils.isEmpty(str)) {
			return defaultValue;
		}
		if (StringUtils.isNumeric(str)) {
			return (Integer.parseInt(str) == 1 ? true : false);
		}
		return Boolean.parseBoolean(str);
	}
	/**
	 * 从Request中取得boolean值,如无值则返回缺省值 0
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static Short getShort(HttpServletRequest request, String key) {
		return getShort(request, key, (short) 0);
	}
	/**
	 * 从Request中取得Short值 对字符串,如无值则返回缺省值
	 * 
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static Short getShort(HttpServletRequest request, String key, Short defaultValue) {
		String str = request.getParameter(key);
		if (StringUtils.isEmpty(str)) {
			return defaultValue;
		}
		return Short.parseShort(str);
	}
	/**
	 * 从Request中取得Date值,如无值则返回缺省值null, 如果有值则返回 yyyy-MM-dd 格式的日期
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(HttpServletRequest request, String key) throws ParseException {
		String str = request.getParameter(key);
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		return DateUtil.parseDate(str);
	}
	
	/**
	 * 从request中取得JsonObject,无值取null,这里的JsonObject是fastJson包的
	 * @param request
	 * @param key
	 * @return
	 */
	public static JSONObject getJSONObject(HttpServletRequest request, String key){
		return getJSONObject(request,  key, null);
	}
	
	/**
	 * 从request中取得JsonObject,无值取defaultValue,这里的JsonObject是fastJson包的
	 * @param request
	 * @param key
	 * @return
	 */
	public static JSONObject getJSONObject(HttpServletRequest request, String key,JSONObject defaultValue){
		String val=request.getParameter(key);
		if(StringUtil.isNotEmpty(val)){
			return JSONObject.parseObject(val);
		}
		return defaultValue;
	}
	

	
	/**
	 * 取得当前页URL,如有参数则带参数
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return
	 */
	public static String getUrl(HttpServletRequest request) {
		StringBuilder urlThisPage = new StringBuilder();
		urlThisPage.append(request.getRequestURI());
		Enumeration<?> e = request.getParameterNames();
		urlThisPage.append("?");
		while (e.hasMoreElements()) {
			String para = (String) e.nextElement();
			String values = request.getParameter(para);
			urlThisPage.append(para);
			urlThisPage.append("=");
			urlThisPage.append(values);
			urlThisPage.append("&");
		}
		return urlThisPage.substring(0, urlThisPage.length() - 1);
	}
	/**
	 * 获得URL,并且去掉不需要的参数
	 * 
	 * @param request
	 * @param excludeParams
	 * @return
	 */
	public static String getUrl(HttpServletRequest request, String excludeParams) {
		if (StringUtils.isEmpty(excludeParams)) {
			return getUrl(request);
		}
		StringBuilder urlThisPage = new StringBuilder();
		urlThisPage.append(request.getRequestURI());
		if (request.getParameterNames().hasMoreElements()) {
			return urlThisPage.toString();
		}
		Enumeration<?> e = request.getParameterNames();
		urlThisPage.append("?");
		while (e.hasMoreElements()) {
			String para = (String) e.nextElement();
			if (excludeParams.indexOf(para) != -1) {
				continue;
			}
			String values = request.getParameter(para);
			urlThisPage.append(para);
			urlThisPage.append("=");
			urlThisPage.append(values);
			urlThisPage.append("&");
		}
		return urlThisPage.substring(0, urlThisPage.length() - 1);
	}
	/**
	 * 返回请求中的绝对路径,如contextPath=/root,url=/root/a/b.do,则返回/a/b.do
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestURI(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String url = request.getRequestURI();
		if (StringUtils.isNotEmpty(contextPath)) {
			int index = url.indexOf(contextPath);
			if (index != -1) {
				return url.substring(contextPath.length());
			}
		}
		return url;
	}
	/**
	 * 取得上一页的路径。
	 * 
	 * @param request
	 * @return
	 */
	public static String getPrePage(HttpServletRequest request) {
		return request.getHeader("REFERER");
	}
	/**
	 * 获得Cookie的值
	 * 
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName) && StringUtils.isNotBlank(cookie.getValue())) {
					return cookie.getValue();
				}
			}
		}
		return "";
	}

	/**
	 * 是否存在cookie
	 * 
	 * @Description:
	 * @Title: isCookieName
	 * @param @param request
	 * @param @param cookieName
	 * @param @return 参数说明
	 * @return Boolean 返回类型
	 * @throws
	 */
	public static Boolean isCookieName(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 获取上下文参数。
	 * @param request
	 * @param remainArray
	 * @return
	 */
	public static Map<String,Object> getParameterValueMap(HttpServletRequest request,
			boolean remainArray) {
		Map<String,Object> map = new HashMap<String,Object>();
		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String key = params.nextElement().toString();
			String[] values = request.getParameterValues(key);
			if (values == null) continue;
			if (values.length == 1) {
				String tmpValue = values[0];
				if (tmpValue == null) continue;
				tmpValue = tmpValue.trim();
				if (tmpValue.equals("")) continue;
				if (tmpValue.equals("")) continue;
				map.put(key, tmpValue);
			} else {
				String rtn = getByAry(values);
				if (rtn.length() > 0) {
					if (remainArray)
						map.put(key, rtn.split(","));
					else
						map.put(key, rtn);
				}
			}
		}
		return map;
	}
	
	private static String getByAry(String[] aryTmp) {
		String rtn = "";
		for (int i = 0; i < aryTmp.length; i++) {
			String str = aryTmp[i].trim();
			if (!str.equals("")) {
				rtn += str + ",";
			}
		}
		if (rtn.length() > 0)
			rtn = rtn.substring(0, rtn.length() - 1);
		return rtn;
	}
}
