package com.redxun.saweb.controller;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redxun.core.bean.PropertyPlaceholderConfigurerExt;

import com.redxun.core.json.JsonResult;
import com.redxun.core.util.ExceptionUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.org.api.service.UserService;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.filter.SecurityUtil;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.util.WebAppUtil;
/**
 * 登录控制器(广州工程技术职业学院http://pt.gzvtc.cn)
 * 
 * @author csx
 * @Email keitch@redxun.cn
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（www.redxun.cn） 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/")
public class LoginGzvtcController extends BaseController {
	@Resource
	AuthenticationManager authenticationManager;
	@Resource
	UserService userService;
	
	@Resource
	PropertyPlaceholderConfigurerExt configProperties;
	
	/**
	 * @param licence
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decrypt(String licence) throws UnsupportedEncodingException {
		StringBuffer decryptLicence = new StringBuffer(licence);
		int decryptLicenceLen = decryptLicence.length();
		String part6 = decryptLicence.substring(0, 5);
		String part3 = decryptLicence.substring(5, 10);
		String part5 = decryptLicence.substring(10, 15);
		String part2 = decryptLicence.substring(decryptLicenceLen - 15, decryptLicenceLen - 10);
		String part4 = decryptLicence.substring(decryptLicenceLen - 10, decryptLicenceLen - 5);
		String part1 = decryptLicence.substring(decryptLicenceLen - 5, decryptLicenceLen);
		String partCenter = decryptLicence.substring(15, decryptLicenceLen - 15);
		return new String(new BigInteger(part1 + part2 + part3 + partCenter + part4 + part5 + part6, 16).toByteArray(), "UTF-8");
	}
	/**
	 * 
	 * http://localhost:8080/a/login2.do?taskid=240000000001243&&no=admin@redxun
	 * .cn&&p=1&&logintype=mini
	 * 
	 * @Description:
	 * @Title: login2
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws Exception 参数说明
	 * @return JsonResult 返回类型
	 * @throws
	 */
	@RequestMapping("login2")
	@ResponseBody
	public JsonResult login2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String logStr = "单点登录";
		try {
			// 读取配置
//			PropertiesUtil propertiesUtil = new PropertiesUtil();
//			Map<String, Object> map = propertiesUtil.findProperties2Map("/config/config.properties");
			
			// 获得序列号
			String licence=configProperties.getProperty("licence");
			//licence = map.get("licence").toString();
			
			String companyName  =configProperties.getProperty("app.companyName");
			companyName = companyName.substring(8, companyName.length());
			
			companyName = companyName.substring(0, companyName.length() - 8);
			
			// 解释序列号
			if (StringUtils.isEmpty(licence)) {
				return new JsonResult(false, "序列号不存在!");
			}
			String decryptLicence = decrypt(licence);
			// 序列号授权单位名称
			String schoolName = decryptLicence.substring(8, decryptLicence.length() - 8);

			if (!schoolName.equals(companyName)) {
				return new JsonResult(false, "序列号不正确!");
			}
			String taskId = request.getParameter("taskid");
			String logintype = request.getParameter("logintype");
			String username = null;
			String password = null;
			String ssoUserId = request.getParameter("no");
			String ssoPassword = request.getParameter("p");
			if (StringUtil.isEmpty(ssoUserId)) {
				return new JsonResult(false, "用户账号不存在!");
			}
			if (StringUtil.isEmpty(ssoPassword)) {
				return new JsonResult(false, "用户密码不存在!");
			}
			username = ssoUserId;
			if(username.indexOf("@")==-1){
				username=username+"@"+WebAppUtil.getOrgMgrDomain();
			}
			IUser user = userService.getByUsername(username);
			if (user == null) {
				return new JsonResult(false, "该用户账号不存在!");
			}
			// mini后台登录
			if (StringUtil.isNotEmpty(logintype) && logintype.equals("mini")) {
				// 密码是否一致
				if (user.getPwd().equals(ssoPassword)) {
					username = user.getUsername();
					password = user.getPwd();
				} else {
					return new JsonResult(false, "密码不正确");
				}
			} else {
				// 单点登录
				boolean result = this.singleSignOn(ssoUserId, ssoPassword);
				if (result) {
					username = user.getUsername();
					password = user.getPwd();
				} else {
					response.getWriter().println("ssoFail");
					return null;
				}
			}

			SecurityUtil.login(request, username, "", true);
			ContextUtil.getCurrentUser();
			if (ssoUserId != null && ssoPassword != null) {
				if (taskId != null && taskId.indexOf("n") == 0) {
					response.sendRedirect(request.getContextPath() + "/bpm/core/bpmInst/start.do?solId=" + taskId.substring(1));
					return null;
				}
				if (taskId != null && taskId.equals("s")) {
					response.sendRedirect(request.getContextPath() + "/index.do");
					return null;
				}
				if (taskId != null && taskId.indexOf("n") == -1) {
					response.sendRedirect(request.getContextPath() + "/bpm/core/bpmTask/toStart.do?taskId=" + taskId);
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(logStr, e);
			response.setContentType("text/json;charset=" + "UTF-8");
			response.getWriter().println(ExceptionUtil.getExceptionMessage(e));
			return null;
		} finally {
		}
		return new JsonResult(true, "Login Success");
	}
	private boolean singleSignOn(String userId, String encryptedPassword) throws IOException {
		// 读取配置
//		PropertiesUtil propertiesUtil = new PropertiesUtil();
//		Map<String, Object> map = propertiesUtil.findProperties2Map("/config/config.properties");
		//加密函数
		Date currDate = Calendar.getInstance().getTime();
		DateFormat dateFormatter = new SimpleDateFormat("yyyyMd");
		String dateFormatterStr = dateFormatter.format(currDate);
		DateFormat timeFormatter = new SimpleDateFormat("H:mm");
		String timeFormatterStr = timeFormatter.format(currDate).substring(0, 4);
		//加密公式  201781admin14:4gzvtc.cn
		//String code = dateFormatterStr + userId + timeFormatterStr + "gzvtc.cn";
		String cTicket = configProperties.getProperty("jsaas_ticket");
		String code = dateFormatterStr + userId + timeFormatterStr + cTicket;
		
		String encryptedCode = LoginGzvtcController.getDigestStr(code);
		
		if (encryptedCode.equals(encryptedPassword)) {
			return true;
		}
		return false;
	}
	public static String getDigestStr(String info) {
		try {
			byte[] res = info.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] result = md.digest(res);
			for (int i = 0; i < result.length; i++) {
				md.update(result[i]);
			}
			byte[] hash = md.digest();
			StringBuffer d = new StringBuffer("");
			for (int i = 0; i < hash.length; i++) {
				int v = hash[i] & 0xFF;
				if (v < 16) {
					d.append("0");
				}
				d.append(Integer.toString(v, 16).toUpperCase());
			}
			return d.toString();
		} catch (Exception e) {
			return null;
		}
	}
}
