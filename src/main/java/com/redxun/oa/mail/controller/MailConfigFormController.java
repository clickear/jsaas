package com.redxun.oa.mail.controller;

import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redxun.core.json.JsonResult;
import com.redxun.oa.mail.entity.MailConfig;
import com.redxun.oa.mail.manager.MailConfigManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;

/**
 * 外部邮件账号配置Controller
 * 
 * @author zwj 
 * 用途：处理对内部邮件相关操作的请求映射
 */
@Controller
@RequestMapping("/oa/mail/mailConfig/")
public class MailConfigFormController extends BaseFormController {

	final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory"; // 安全连接所需类

	@Resource
	private MailConfigManager mailConfigManager;

	/**
	 * 处理表单
	 * 
	 * @param request
	 * @return
	 */
	@ModelAttribute("mailConfig")
	public MailConfig processForm(HttpServletRequest request) {
		String configId = request.getParameter("configId");
		MailConfig mailConfig = null;
		if (StringUtils.isNotEmpty(configId)) {
			mailConfig = mailConfigManager.get(configId);
		} else {
			mailConfig = new MailConfig();
		}

		return mailConfig;
	}

	/**
	 * 保存实体数据
	 * 
	 * @param request
	 * @param mailConfig
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult save(HttpServletRequest request, @ModelAttribute("mailConfig") @Valid MailConfig mailConfig, BindingResult result) {

		if (result.hasFieldErrors()) {
			return new JsonResult(false, getErrorMsg(result));
		}
		String msg = null;
		if (StringUtils.isEmpty(mailConfig.getConfigId())) {
			mailConfig.setConfigId(idGenerator.getSID());
			if (doCreateValidConfig(mailConfig)) { // 验证外部邮箱账号的连通性,连通服务器就创建外部邮箱配置
				if(!mailConfigManager.isMailConfigExist(mailConfig.getMailAccount(),ContextUtil.getCurrentTenantId())){
					mailConfigManager.create(mailConfig);
					msg = getMessage("mailConfig.created", new Object[] { mailConfig.getIdentifyLabel() }, "邮箱账号配置成功创建!");
				}
				else{
					msg = getMessage("mailConfig.created", new Object[] { mailConfig.getIdentifyLabel() }, "该账号已存在，不能重复创建!");
				}
			} else
				msg = getMessage("mailConfig.created", new Object[] { mailConfig.getIdentifyLabel() }, "邮箱账号配置创建失败!");
		} else {
			mailConfigManager.update(mailConfig);
			msg = getMessage("mailConfig.updated", new Object[] { mailConfig.getIdentifyLabel() }, "邮箱账号配置成功更新!");
		}
		return new JsonResult(true, msg);
	}

	/**
	 * 验证外部邮箱账号的连通性
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("validateMailConfig")
	@ResponseBody
	public JsonResult testMailConfig(HttpServletRequest request) {
		String data = request.getParameter("data");
		JSONObject jsonObject = JSONObject.fromObject(data); // 转成json对象
		MailConfig mailConfig = (MailConfig) JSONObject.toBean(jsonObject, MailConfig.class);
		if (doCreateValidConfig(mailConfig)) // 账号连通情况良好
			return new JsonResult(true, "服务器可用！");
		else
			return new JsonResult(true, "服务器不可用！请检查你的配置是否有误！");
	}

	/**
	 * 验证账号邮箱服务器连通性
	 * 
	 * @param mailConfig
	 * @return
	 */
	public boolean doCreateValidConfig(MailConfig mailConfig) {
		if ("true".equals(mailConfig.getSsl())) { // 是否使用ssl安全连接
			if ("pop3".equals(mailConfig.getProtocol())) { // 是否使用pop3ssl连接
				if (testSmtpSsl(mailConfig) && testPop3Ssl(mailConfig)) { // 测试账号的smtp和pop3ssl安全连接下账号连通性
					return true;
				}
				return false;
			} else {
				if (testSmtpSsl(mailConfig) && testImapSsl(mailConfig)) { // 测试账号的smtp和imapssl安全连接下账号连通性
					return true;
				}
				return false;
			}
		} else {
			if ("pop3".equals(mailConfig.getProtocol())) { // 使用pop3连接
				if (testSmtp(mailConfig) && testPop3(mailConfig)) { // 测试账号的smtp和pop3账号连通性
					return true;
				}
				return false;
			} else {
				if (testSmtp(mailConfig) && testImap(mailConfig)) { // 测试账号的smtp和imap账号连通性
					return true;
				}
				return false;
			}
		}
	}

	/**
	 * 测试ssl下pop3服务器连通性
	 * 
	 * @param mailConfig
	 * @return
	 */
	public boolean testPop3Ssl(MailConfig mailConfig) {
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", mailConfig.getProtocol()); // 设置邮箱账号连接属性
		props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.pop3.socketFactory.fallback", "false");
		props.setProperty("mail.pop3.port", "995");
		props.setProperty("mail.pop3.ssl.enable", "true");
		Session session = Session.getInstance(props); // 获取session实例
		try {
			Store store = session.getStore();
			store.connect(mailConfig.getRecpHost(), mailConfig.getMailAccount(), mailConfig.getMailPwd()); // 连接服务器
		} catch (Exception e) {
			e.getMessage();
			return false;
		}
		return true;
	}

	/**
	 * 测试ssl下smtp服务器的连通性
	 * 
	 * @param mailConfig
	 * @return
	 */
	public boolean testSmtpSsl(MailConfig mailConfig) {
		Properties props = new Properties();
		props.setProperty("mail.smtp.port", mailConfig.getSmtpPort()); // smtp端口
		props.setProperty("mail.smtp.host", mailConfig.getSmtpHost()); // 发送主机
		props.setProperty("mail.transport.protocol", "smtp"); // 发送协议
		props.setProperty("mail.smtp.ssl.enable", "true"); // 使用ssl安全连接
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.auth", "true"); // 身份验证
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		Session session = Session.getInstance(props);
		try {
			Transport transport = session.getTransport();
			transport.connect(mailConfig.getSmtpHost(), mailConfig.getMailAccount(), mailConfig.getMailPwd());// 连接服务器
		} catch (Exception e) {
			e.getMessage();
			return false;
		}
		return true;
	}

	/**
	 * 测试pop3服务器连通性
	 * 
	 * @param mailConfig
	 * @return
	 */
	public boolean testPop3(MailConfig mailConfig) {
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", mailConfig.getProtocol());
		props.setProperty("mail.pop3.port", mailConfig.getRecpPort());
		props.setProperty("mail.pop3.host", mailConfig.getRecpHost());
		Session session = Session.getInstance(props);
		try {
			Store store = session.getStore();
			store.connect(mailConfig.getRecpHost(), mailConfig.getMailAccount(), mailConfig.getMailPwd());// 连接服务器
		} catch (Exception e) {
			e.getMessage();
			return false;
		}
		return true;
	}

	/**
	 * 测试smtp服务器连通性
	 * 
	 * @param mailConfig
	 * @return
	 */
	public boolean testSmtp(MailConfig mailConfig) {
		Properties props = new Properties();
		props.setProperty("mail.smtp.port", mailConfig.getSmtpPort()); // smtp端口
		props.setProperty("mail.smtp.host", mailConfig.getSmtpHost()); // 发送主机
		props.setProperty("mail.transport.protocol", "smtp"); // 发送协议
		props.setProperty("mail.smtp.auth", "true"); // 身份验证

		Session session = Session.getInstance(props);
		try {
			Transport transport = session.getTransport();
			transport.connect(mailConfig.getSmtpHost(), mailConfig.getMailAccount(), mailConfig.getMailPwd());// 连接服务器
		} catch (Exception e) {
			e.getMessage();
			return false;
		}
		return true;
	}

	/**
	 * 测试ssl下imap服务器连通性
	 * 
	 * @param mailConfig
	 * @return
	 */
	public boolean testImapSsl(MailConfig mailConfig) {
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", mailConfig.getProtocol());
		props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.imap.socketFactory.fallback", "false");
		props.setProperty("mail.imap.port", "993");
		props.setProperty("mail.imap.ssl.enable", "true");
		Session session = Session.getInstance(props);
		session.setDebug(true);
		try {
			Store store = session.getStore();
			store.connect(mailConfig.getRecpHost(), mailConfig.getMailAccount(), mailConfig.getMailPwd());// 连接服务器
		} catch (Exception e) {
			e.getMessage();
			return false;
		}
		return true;
	}

	/**
	 * 测试imap服务器的连通性
	 * 
	 * @param mailConfig
	 * @return
	 */
	public boolean testImap(MailConfig mailConfig) {
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", mailConfig.getProtocol());
		props.setProperty("mail.imap.port", mailConfig.getRecpPort());
		props.setProperty("mail.imap.host", mailConfig.getRecpHost());
		Session session = Session.getInstance(props);
		session.setDebug(true);
		try {
			Store store = session.getStore();
			store.connect(mailConfig.getRecpHost(), mailConfig.getMailAccount(), mailConfig.getMailPwd());// 连接服务器
		} catch (Exception e) {
			e.getMessage();
			return false;
		}
		return true;
	}

}
