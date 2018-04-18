package com.redxun.oa.mail.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.UIDFolder;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.SortParam;
import com.redxun.core.util.FileUtil;
import com.redxun.oa.mail.entity.OutMail;
import com.redxun.oa.mail.entity.MailConfig;
import com.redxun.oa.mail.entity.MailFolder;
import com.redxun.oa.mail.manager.MailConfigManager;
import com.redxun.oa.mail.manager.MailFolderManager;
import com.redxun.oa.mail.manager.OutMailManager;
import com.redxun.org.api.context.CurrentContext;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.config.upload.FileExt;
import com.redxun.saweb.config.upload.FileUploadConfig;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.manager.SysFileManager;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.sun.mail.pop3.POP3Folder;

/**
 * 外部邮件Controller
 * 
 * @author zwj 
 * 用途：处理对外部邮件相关操作的请求映射
 */
@Controller
@RequestMapping("/oa/mail/outMail/")
public class OutMailController extends TenantListController {

	@Resource(name = "iJson")
	ObjectMapper objectMapper;

	@Resource
	FileUploadConfig fileUploadConfig;

	@Resource
	CurrentContext currentContext;

	@Resource
	SysFileManager sysFileManager;

	@Resource
	OutMailManager outMailManager;

	@Resource
	MailConfigManager mailConfigManager;

	@Resource
	MailFolderManager mailFolderManager;

	/**
	 * 根据主键删除外部邮件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				outMailManager.delete(id);
			}
		}
		return new JsonResult(true, "成功删除！");
	}

	/**
	 * 将外部邮件改为删除状态
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delStatus")
	@ResponseBody
	public JsonResult delStatus(HttpServletRequest request) throws Exception {
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) { // 如果mailId不为空
			String[] ids = uId.split(",");
			for (String id : ids) {
				OutMail mail = outMailManager.get(id);
				mail.setStatus(OutMail.STATUS_DELETED); // 将邮件状态改为DELETED
				outMailManager.update(mail);
			}
		}
		return new JsonResult(true, "成功删除！");
	}

	/**
	 * 根据主键查看外部邮件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		OutMail mail = null;
		Set<SysFile> attach = null; // 外部邮件附件列表
		if (StringUtils.isNotEmpty(pkId)) {
			mail = outMailManager.get(pkId);
			attach = mail.getInfMailFiles(); // 外部邮件附件列表
			if("0".equals(mail.getReadFlag())){
				System.out.println("未读");
				mail.setReadFlag("1"); // 设置为已读
				outMailManager.update(mail);
			}
		} else {
			mail = new OutMail();
		}

		return getPathView(request).addObject("mail", mail).addObject("attach", attach);
	}

	/**
	 * 根据外部邮件mailId获取邮件内容
	 * 
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("getMailContentByMailId")
	public ModelAndView getMailContentByMailId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String mailId = request.getParameter("mailId");
		String content = outMailManager.getMailContentByMailId(mailId); // 根据邮件Id获取邮件内容
		request.setAttribute("mailContent", content); // 邮件内容
		return new ModelAndView("/oa/mail/mailContent.jsp");
	}

	/**
	 * 根据folderId查询对应文件夹下的外部邮件
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("getMailByFolderId")
	@ResponseBody
	public JsonPageResult<OutMail> getMailByFolderId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String mailfolderId = request.getParameter("folderId"); // 文件夹Id
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("mailFolder.folderId", mailfolderId);
		queryFilter.addSortParam("sendDate", SortParam.SORT_DESC);
		queryFilter.addFieldParam("status", OutMail.STATUS_COMMEN); // 只获取正常状态的外部邮件
		List<OutMail> list = outMailManager.getAll(queryFilter);
		JsonPageResult<OutMail> result = new JsonPageResult<OutMail>(list, queryFilter.getPage().getTotalItems());
		return result;

	}
	
	/**
	 * 获取门户Portal的外部邮件List
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getPortalOutMail")
	@ResponseBody
	public JsonPageResult<OutMail> getPortalOutMail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ContextUtil.getCurrentUserId(); // 当前用户Id
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("mailFolder.type", MailFolder.TYPE_RECEIVE_FOLDER);
		queryFilter.addFieldParam("mailFolder.inOut", MailFolder.FOLDER_FLAG_OUT);
		queryFilter.addFieldParam("userId", userId);
		queryFilter.addSortParam("sendDate", SortParam.SORT_DESC);
		queryFilter.addFieldParam("status", OutMail.STATUS_COMMEN); // 只获取正常状态的外部邮件
		List<OutMail> list = outMailManager.getAll(queryFilter);
		JsonPageResult<OutMail> result = new JsonPageResult<OutMail>(list, queryFilter.getPage().getTotalItems());
		return result;

	}

	/**
	 * 将外部存邮件到草稿箱
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveToDraft")
	@ResponseBody
	public JsonResult saveToDraft(HttpServletRequest request) throws Exception {
		String data = request.getParameter("data");
		String configId = request.getParameter("configId");
		JSONObject jsonObject = JSONObject.fromObject(data); // 转成json对象
		String fileIdsstring = jsonObject.getString("fileIds"); // 附件ID列表
		String[] fileIds = fileIdsstring.split(",");
		OutMail mail = (OutMail) JSONObject.toBean(jsonObject, OutMail.class); // 将json对象转成mail对象
		mail.setMailConfig(mailConfigManager.get(configId));
		mail.setMailFolder(mailFolderManager.getMailFolderByConfigIdAndType(configId, MailFolder.TYPE_DRAFT_FOLDER)); // 根据configId获取对应配置下的草稿箱文件夹
		if (fileIds.length > 0) { // 如果存在有附件
			if (!"".equals(fileIds[0])) {
				for (int i = 0; i < fileIds.length; i++) {
					SysFile attach = sysFileManager.get(fileIds[i]);
					mail.getInfMailFiles().add(attach); // 保存附件
				}
			}
		}
		if (StringUtils.isEmpty(mail.getSubject())) // 设置主题
			mail.setSubject("无");
		if (StringUtils.isEmpty(mail.getSenderAddrs())) // 设置发件人
			mail.setSenderAddrs("无");
		if (StringUtils.isEmpty(mail.getRecAddrs())) // 设置收件人
			mail.setRecAddrs("无");
		mail.setSendDate(new Date());
		mail.setMailId(idGenerator.getSID());
		mail.setUserId(ContextUtil.getCurrentUserId());
		mail.setReadFlag("0");
		mail.setReplyFlag("0");
		mail.setStatus(OutMail.STATUS_COMMEN);
		outMailManager.create(mail);
		return new JsonResult(true, "成功保存");
	}

	/**
	 * 将外部邮件移动到垃圾箱
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("moveToDelFolder")
	@ResponseBody
	public JsonResult moveToDelFolder(HttpServletRequest request) throws Exception {
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				OutMail mail = outMailManager.get(id);
				mail.setMailFolder(mailFolderManager.getMailFolderByConfigIdAndType(mail.getConfigId(), MailFolder.TYPE_DEL_FOLDER)); // 根据configId获取对应配置下的垃圾箱文件夹
				outMailManager.update(mail);
			}
		}
		return new JsonResult(true, "成功删除！");
	}

	/**
	 * 将外部邮件移动到指定目录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("moveMail")
	@ResponseBody
	public JsonResult moveMail(HttpServletRequest request) throws Exception {
		String folderId = request.getParameter("to"); // 目标文件夹Id
		String uId = request.getParameter("ids"); // 需要移动的外部邮件的邮件Id列表
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				OutMail mail = outMailManager.get(id);
				mail.setMailFolder(mailFolderManager.get(folderId)); // 获取目标文件夹
				outMailManager.update(mail);
			}
		}
		return new JsonResult(true, "移动成功！");
	}

	/**
	 * 编辑外部邮件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId"); // 邮件Id
		String operation = request.getParameter("operation"); // 邮件操作类型
		String fileIds = request.getParameter("fileIds"); // 附件Id列表
		String configId = request.getParameter("configId");
		StringBuffer tmpFileIds = new StringBuffer();
		StringBuffer tmpFileNames = new StringBuffer();
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		OutMail mail = null;
		if (StringUtils.isNotEmpty(pkId)) {
			mail = outMailManager.get(pkId);
			String senderAddrs = mail.getSenderAddrs();
			String senderAlias = mail.getSenderAlias();
			String account=mail.getMailConfig().getMailAccount();
			String oldRecAddrs = mail.getRecAddrs();
			String oldRecAlias = mail.getRecAlias();
			mail.setSenderAddrs(mail.getRecAddrs()); // 将发件人改为本账号地址
			mail.setSenderAlias(mail.getRecAlias()); // 设置发件人别名
			if(account.equals(senderAddrs)){
				mail.setRecAddrs(oldRecAddrs); 
				mail.setRecAlias(oldRecAlias);
			}else{
				mail.setRecAddrs(senderAddrs); // 将收件人改为对方账号地址
				mail.setRecAlias(senderAlias); // 设置收件人别名
			}
			for (SysFile sysFile : mail.getInfMailFiles()) { // 获取附件列表,拼接回填附件列表的Id串和文本串
				tmpFileIds.append(sysFile.getFileId());
				tmpFileIds.append(",");
				tmpFileNames.append(sysFile.getFileName());
				tmpFileNames.append(",");
			}
			if (tmpFileIds.length() > 0) { // 删除最后一个逗号
				tmpFileIds.deleteCharAt(tmpFileIds.length() - 1);
				tmpFileNames.deleteCharAt(tmpFileNames.length() - 1);
			}
			if ("true".equals(forCopy)) {
				mail.setMailId(null);
			}
			if ("reply".equals(operation)) { // 回复
				mail.setSubject("Re:" + mail.getSubject());
			}
			if ("transmit".equals(operation)) { // 转发
				mail.setRecAddrs("");
				mail.setRecAlias("");
				mail.setSubject("Fw:" + mail.getSubject());
			}
		} else {
			if ("mailsTransmit".equals(operation)) { // 多封邮件转发
				String[] s = fileIds.split(",");
				for (int i = 0; i < s.length; i++) { // 拼接回填的eml附件列表
					SysFile sysFile = sysFileManager.get(s[i]);
					tmpFileIds.append(sysFile.getFileId());
					tmpFileIds.append(",");
					tmpFileNames.append(sysFile.getFileName());
					tmpFileNames.append(",");
				}
				if (tmpFileIds.length() > 0) { // 删除最后一个逗号
					tmpFileIds.deleteCharAt(tmpFileIds.length() - 1);
					tmpFileNames.deleteCharAt(tmpFileNames.length() - 1);
				}
			}
			mail = new OutMail();
			MailConfig mailConfig = mailConfigManager.get(configId);
			mail.setMailConfig(mailConfig);
		}
		return getPathView(request).addObject("mail", mail).addObject("fileIds", tmpFileIds.toString()).addObject("fileNames", tmpFileNames.toString());
	}

	/**
	 * 发送外部邮件
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sendMail")
	@ResponseBody
	public JsonResult sendMail(HttpServletRequest request) throws Exception {
		String data = request.getParameter("data");
		String configId = request.getParameter("configId");
		JSONObject jsonObject = JSONObject.fromObject(data); // 转成json对象
		String fileIdsString = jsonObject.getString("fileIds"); // 附件ID列表
		String[] fileIds = fileIdsString.split(",");
		OutMail mail = (OutMail) JSONObject.toBean(jsonObject, OutMail.class); // 将json对象转成mail对象
		if (StringUtils.isEmpty(mail.getMailId())) {
			mail.setMailConfig(mailConfigManager.get(configId));
			mail.setMailFolder(mailFolderManager.getMailFolderByConfigIdAndType(configId, MailFolder.TYPE_SENDER_FOLDER)); // 将该邮件的文件夹保存为发件箱
		} else {
			mail.setMailConfig(outMailManager.get(mail.getMailId()).getMailConfig());
			mail.setMailFolder(mailFolderManager.getMailFolderByConfigIdAndType(outMailManager.get(mail.getMailId()).getConfigId(), MailFolder.TYPE_SENDER_FOLDER));
		}
		if (fileIds.length > 0) { // 有附件
			if (!"".equals(fileIds[0])) {
				for (int i = 0; i < fileIds.length; i++) {
					SysFile attach = sysFileManager.get(fileIds[i]);
					mail.getInfMailFiles().add(attach);
				}
			}
		}
		mail.setSendDate(new Date());
		if ("true".equals(mail.getMailConfig().getSsl())) { // 判断是否使用SSL安全连接
			if (sendMailBySmtpSsl(mail)) {
				mail.setMailId(idGenerator.getSID());
				mail.setUserId(ContextUtil.getCurrentUserId());
				mail.setReadFlag("1"); // 设置为已读
				mail.setReplyFlag("1"); // 设置为已回复
				mail.setStatus(OutMail.STATUS_COMMEN);
				outMailManager.create(mail);
				return new JsonResult(true, "成功发送！");
			} else {
				return new JsonResult(true, "发送失败！");
			}
		} else {
			if (sendMailBySmtp(mail)) {
				mail.setMailId(idGenerator.getSID());
				mail.setUserId(ContextUtil.getCurrentUserId());
				mail.setReadFlag("1"); // 设置为已读
				mail.setReplyFlag("1"); // 设置为已回复
				mail.setStatus(OutMail.STATUS_COMMEN);
				outMailManager.create(mail);
				return new JsonResult(true, "成功发送！");
			} else {
				return new JsonResult(true, "发送失败！");
			}
		}
	}

	/**
	 * 将多封邮件装成eml文件，并转发
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("mailsTransmit")
	@ResponseBody
	public JsonResult mailsTransmit(HttpServletRequest request) throws Exception {
		String uId = request.getParameter("ids"); // 邮件Id列表
		String[] ids = uId.split(",");
		List<OutMail> mails = new ArrayList<OutMail>(); // 所操作的邮件实体
		for (String id : ids) {
			mails.add(outMailManager.get(id));
		}
		Properties p = new Properties();
		p.put("mail.smtp.auth", "true");
		p.put("mail.transport.protocol", "smtp");
		p.put("mail.smtp.host", mails.get(0).getMailConfig().getSmtpHost());
		p.put("mail.smtp.port", mails.get(0).getMailConfig().getSmtpPort());
		Session session = Session.getInstance(p);
		ArrayNode arrayNode = objectMapper.createArrayNode();
		for (int i = 0; i < mails.size(); i++) { // 将每一封要转发的邮件转成eml文件
			Message msg = new MimeMessage(session); // 建立信息
			msg.setFrom(new InternetAddress(mails.get(i).getSenderAddrs())); // 设置发件人
			List<InternetAddress> rec = new ArrayList<InternetAddress>();
			String[] recs = mails.get(i).getRecAddrs().split(","); // 获取邮件收件人
			for (int j = 0; j < recs.length; j++) {
				if ("无".equals(recs[j]))
					continue;
				rec.add(new InternetAddress(recs[j]));
			}
			InternetAddress[] address = (InternetAddress[]) rec.toArray(new InternetAddress[rec.size()]); // 将收件人转成InternetAddress数组
			msg.setRecipients(Message.RecipientType.TO, address); // 设置收件人
			if (StringUtils.isNotEmpty(mails.get(i).getCcAddrs())) { // 设置抄送人
				List<InternetAddress> ccs = new ArrayList<InternetAddress>();
				recs = mails.get(i).getCcAddrs().split(","); // 获取邮件抄送人
				for (int j = 0; j < recs.length; j++) {
					if ("无".equals(recs[j]))
						continue;
					ccs.add(new InternetAddress(recs[j]));
				}
				address = (InternetAddress[]) ccs.toArray(new InternetAddress[ccs.size()]); // 将收件人转成InternetAddress数组
				msg.setRecipients(Message.RecipientType.CC, address); // 设置抄送人
			}
			if (StringUtils.isNotEmpty(mails.get(i).getBccAddrs())) { // 设置暗送人
				List<InternetAddress> bccs = new ArrayList<InternetAddress>();
				recs = mails.get(i).getBccAddrs().split(",");
				for (int j = 0; j < recs.length; j++) {
					if ("无".equals(recs[j]))
						continue;
					bccs.add(new InternetAddress(recs[j]));
				}
				address = (InternetAddress[]) bccs.toArray(new InternetAddress[bccs.size()]); // 将收件人转成InternetAddress数组
				msg.setRecipients(Message.RecipientType.BCC, address); // 设置暗送人
			}
			msg.setSentDate(new Date()); // 发送日期
			String subject = MimeUtility.encodeText(mails.get(i).getSubject(), "utf-8", "B"); // 加密主题
			msg.setSubject(subject); // 主题

			Multipart mainPart = new MimeMultipart(); // 附件
			BodyPart bp = new MimeBodyPart();// 创建一个包含附件内容的MimeBodyPart
			// 设置HTML内容
			bp.setContent(mails.get(i).getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(bp);

			// 添加附件
			MimeBodyPart mimeBodyPart;
			FileDataSource fileDataSource;
			Set<SysFile> set = mails.get(i).getInfMailFiles(); // 附件集合
			Iterator<SysFile> it = set.iterator();
			while (it.hasNext()) { // 遍历附件集合
				SysFile file = it.next();
				mimeBodyPart = new MimeBodyPart();
				fileDataSource = new FileDataSource(WebAppUtil.getUploadPath() + "/" + file.getPath());
				mimeBodyPart.setDataHandler(new DataHandler(fileDataSource));
				mimeBodyPart.setFileName(MimeUtility.encodeText(file.getFileName()).replaceAll("\n", "").replaceAll("\r", "")); // 原附件
				mainPart.addBodyPart(mimeBodyPart);
			}

			msg.setContent(mainPart); // 设置邮件内容
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM"); // 设置邮件日期
			String tempPath = sdf.format(new Date());
			// 生成eml文件保存到服务器
			IUser curUser = currentContext.getCurrentUser();
			String account = curUser.getUsername();
			String fullPath = WebAppUtil.getUploadPath() + "/" + account + "/" + tempPath;
			String pkId = idGenerator.getSID();
			String fileFullPath = fullPath + pkId + ".eml";
			File dirFile = new File(fullPath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			msg.writeTo(new FileOutputStream(new File(fullPath + "/" + pkId + ".eml"))); // 生成eml文件
			SysFile sysFile = new SysFile();
			String fileName = mails.get(i).getSubject();
			String filerExt = "eml";
			String relFilePath = account + "/" + tempPath;
			sysFile.setFileId(pkId);
			sysFile.setFileName(fileName + "." + filerExt);
			sysFile.setPath(relFilePath + "/" + pkId + "." + filerExt);
			sysFile.setExt("." + filerExt);
			FileExt fileExt = fileUploadConfig.getFileExtMap().get(filerExt.toLowerCase()); // 设置minetype
			if (fileExt != null) {
				sysFile.setMineType(fileExt.getMineType());
			} else {
				sysFile.setMineType("Unkown");
			}
			sysFile.setDesc(""); // 设置描述

			FileChannel fc = null; // 计算文件大小
			try {
				File f = new File(fileFullPath);
				if (f.exists() && f.isFile()) {
					FileInputStream fis = new FileInputStream(f);
					fc = fis.getChannel();
					long size = fc.size();
					sysFile.setTotalBytes(size);
					fis.close();
				} else {
					logger.debug("file not exist!");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (null != fc) {
					try {
						fc.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			sysFile.setNewFname(pkId + "." + filerExt); // 新文件名字
			sysFileManager.create(sysFile);
			ObjectNode objectNode = objectMapper.createObjectNode();
			objectNode.put("fileId", sysFileManager.get(pkId).getFileId()); // 附件Id列表
			objectNode.put("fileName", sysFileManager.get(pkId).getFileName()); // 附件名称列表
			arrayNode.add(objectNode);

		}
		return new JsonResult(true,"", arrayNode);
	}

	/**
	 * 发送外部邮件
	 * 
	 * @param mail
	 *            外部邮件实体
	 * @return
	 */
	public boolean sendMailBySmtp(OutMail mail) throws Exception {
		Properties p = new Properties(); // 设置连接服务器属性
		p.put("mail.smtp.auth", "true");
		p.put("mail.transport.protocol", "smtp");
		p.put("mail.smtp.host", mail.getMailConfig().getSmtpHost());
		p.put("mail.smtp.port", mail.getMailConfig().getSmtpPort());
		// 建立会话
		try {
			Session session = Session.getInstance(p);
			Message msg = new MimeMessage(session); // 建立信息
			msg.setFrom(new InternetAddress(mail.getSenderAddrs())); // 设置发件人
			List<InternetAddress> rec = new ArrayList<InternetAddress>();
			String[] recs = mail.getRecAddrs().split(",");
			for (int i = 0; i < recs.length; i++) { // 收件人列表
				rec.add(new InternetAddress(recs[i]));
			}
			InternetAddress[] address = (InternetAddress[]) rec.toArray(new InternetAddress[rec.size()]);
			msg.setRecipients(Message.RecipientType.TO, address); // 设置收件人
			if (StringUtils.isNotEmpty(mail.getCcAddrs())) { // 设置抄送人
				if(!"无".equals(mail.getCcAddrs())){
					List<InternetAddress> ccs = new ArrayList<InternetAddress>();
					recs = mail.getCcAddrs().split(",");
					for (int i = 0; i < recs.length; i++) { // 抄送人列表
						ccs.add(new InternetAddress(recs[i]));
					}
					address = (InternetAddress[]) ccs.toArray(new InternetAddress[ccs.size()]);
					msg.setRecipients(Message.RecipientType.CC, address);
				}
			}
			if (StringUtils.isNotEmpty(mail.getBccAddrs())) { // 设置暗送人
				if(!"无".equals(mail.getBccAddrs())){
					List<InternetAddress> bccs = new ArrayList<InternetAddress>();
					recs = mail.getBccAddrs().split(",");
					for (int i = 0; i < recs.length; i++) { // 暗送人列表
						bccs.add(new InternetAddress(recs[i]));
					}
					address = (InternetAddress[]) bccs.toArray(new InternetAddress[bccs.size()]);
					msg.setRecipients(Message.RecipientType.BCC, address); // 设置暗送人
				}
			}
			msg.setSentDate(new Date()); // 发送日期
			String subject = MimeUtility.encodeText(mail.getSubject(), "utf-8", "B"); // 加密主题
			msg.setSubject(subject); // 设置主题

			Multipart mainPart = new MimeMultipart();
			BodyPart bp = new MimeBodyPart();// 创建一个包含附件内容的MimeBodyPart
			// 设置HTML内容
			bp.setContent(mail.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(bp);

			// 添加附件
			MimeBodyPart mimeBodyPart;
			FileDataSource fileDataSource;
			Set<SysFile> set = mail.getInfMailFiles();
			Iterator<SysFile> it = set.iterator();
			while (it.hasNext()) { // 遍历附件集合
				SysFile file = it.next();
				mimeBodyPart = new MimeBodyPart();
				fileDataSource = new FileDataSource(WebAppUtil.getUploadPath() + "/" + file.getPath());
				mimeBodyPart.setDataHandler(new DataHandler(fileDataSource));
				mimeBodyPart.setFileName(MimeUtility.encodeText(file.getFileName()));
				mainPart.addBodyPart(mimeBodyPart);
			}

			msg.setContent(mainPart);
			Transport tran = session.getTransport("smtp");
			tran.connect(mail.getMailConfig().getSmtpHost(), mail.getMailConfig().getMailAccount(), mail.getMailConfig().getMailPwd());
			tran.sendMessage(msg, msg.getAllRecipients()); // 发送外部邮件
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 使用SSL安全连接发送外部邮件
	 * 
	 * @param mail
	 * @return
	 */
	public boolean sendMailBySmtpSsl(OutMail mail) throws Exception {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		Properties props = new Properties();
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.put("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.host", mail.getMailConfig().getSmtpHost());
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		// 建立会话
		try {
			final String username = mail.getMailConfig().getMailAccount(); // 邮箱用户名
			final String password = mail.getMailConfig().getMailPwd(); // 邮箱密码
			Session session = Session.getInstance(props, new Authenticator() { // 认证
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username, password);
						}
					});

			Message msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(mail.getSenderAddrs())); // 设置发件人
			List<InternetAddress> rec = new ArrayList<InternetAddress>();
			String[] recs = mail.getRecAddrs().split(",");
			for (int i = 0; i < recs.length; i++) { // 收件人列表
				rec.add(new InternetAddress(recs[i]));
			}
			InternetAddress[] address = (InternetAddress[]) rec.toArray(new InternetAddress[rec.size()]);
			msg.setRecipients(Message.RecipientType.TO, address); // 设置收件人
			if (StringUtils.isNotEmpty(mail.getCcAddrs())) { // 设置抄送人
				List<InternetAddress> ccs = new ArrayList<InternetAddress>();
				recs = mail.getCcAddrs().split(",");
				for (int i = 0; i < recs.length; i++) { // 抄送人列表
					ccs.add(new InternetAddress(recs[i]));
				}
				address = (InternetAddress[]) ccs.toArray(new InternetAddress[ccs.size()]);
				msg.setRecipients(Message.RecipientType.CC, address);// 设置抄送人
			}
			if (StringUtils.isNotEmpty(mail.getBccAddrs())) { // 设置暗送人
				List<InternetAddress> bccs = new ArrayList<InternetAddress>();
				recs = mail.getBccAddrs().split(",");
				for (int i = 0; i < recs.length; i++) { // 暗送人列表
					bccs.add(new InternetAddress(recs[i]));
				}
				address = (InternetAddress[]) bccs.toArray(new InternetAddress[bccs.size()]);
				msg.setRecipients(Message.RecipientType.BCC, address); // 设置暗送人
			}

			msg.setSentDate(new Date()); // 发送日期
			String subject = MimeUtility.encodeText(mail.getSubject(), "utf-8", "B"); // 加密主题
			msg.setSubject(subject); // 设置主题

			Multipart mainPart = new MimeMultipart();
			BodyPart bp = new MimeBodyPart();// 创建一个包含附件内容的MimeBodyPart
			// 设置HTML内容
			bp.setContent(mail.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(bp);

			// 添加附件
			MimeBodyPart mimeBodyPart;
			FileDataSource fileDataSource;
			Set<SysFile> set = mail.getInfMailFiles();
			Iterator<SysFile> it = set.iterator();

			while (it.hasNext()) { // 遍历附件集合
				SysFile file = it.next();
				mimeBodyPart = new MimeBodyPart();
				fileDataSource = new FileDataSource(WebAppUtil.getUploadPath() + "/" + file.getPath());
				mimeBodyPart.setDataHandler(new DataHandler(fileDataSource));
				mimeBodyPart.setFileName(MimeUtility.encodeText(file.getFileName()));
				mainPart.addBodyPart(mimeBodyPart);
			}

			msg.setContent(mainPart);

			Transport tran = session.getTransport("smtp");
			tran.connect(mail.getMailConfig().getSmtpHost(), mail.getMailConfig().getMailAccount(), mail.getMailConfig().getMailPwd());
			tran.sendMessage(msg, msg.getAllRecipients()); // 发送外部邮件
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 收取外部邮件
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getMail")
	@ResponseBody
	public JsonResult getMail(HttpServletRequest request) throws Exception {
		String configId = request.getParameter("configId");
		MailConfig mailConfig = mailConfigManager.get(configId);
		if ("pop3".equals(mailConfig.getProtocol())) { // 使用pop3协议收邮件
			if ("true".equals(mailConfig.getSsl())) // 是否使用ssl安全连接(pop3)
				getMailByPop3Ssl(mailConfig); // pop3SSL收取邮件
			else
				getMailByPop3(mailConfig); // pop3收取邮件
			return new JsonResult(true, "收取邮件成功");
		}
		if ("imap".equals(mailConfig.getProtocol())) { // 使用imap协议收邮件
			if ("true".equals(mailConfig.getSsl())) // 是否使用ssl安全连接(imap)
				getMailByIMAPSsl(mailConfig);// imapSSL收取邮件
			else
				getMailByIMAP(mailConfig);// imap收取邮件
			return new JsonResult(true, "收取邮件成功");
		}
		return new JsonResult(true, "收取邮件失败");
	}

	/**
	 * POP3SSL安全连接收取外部邮件
	 * 
	 * @param mailConfig
	 *            外部邮箱账号配置
	 * @throws Exception
	 */
	public void getMailByPop3Ssl(MailConfig mailConfig) throws Exception {
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		Properties props = new Properties(); // 设置连接邮箱服务器的属性
		props.put("mail.pop3.ssl.enable", "true".equals(mailConfig.getSsl()) ? " true" : " false");
		props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.pop3.socketFactory.fallback", "false");
		props.put("mail.pop3.auth.plain.disable", "true");
		props.put("mail.pop3.host", mailConfig.getRecpHost());
		props.put("mail.pop3.port", mailConfig.getRecpPort());
		Session session = Session.getInstance(props);
		Store store = session.getStore(mailConfig.getProtocol());
		store.connect(mailConfig.getRecpHost(), Integer.parseInt(mailConfig.getRecpPort()), mailConfig.getMailAccount(), mailConfig.getMailPwd());
		Folder folder = store.getFolder("INBOX"); // 收件箱文件夹
		folder.open(Folder.READ_ONLY);
		try {
			String uId = "";
			if (folder instanceof POP3Folder) {
				POP3Folder inbox = (POP3Folder) folder;
				Message[] messages = inbox.getMessages();
				// Message m;
				int newMailNumber = 0;
				for (int i = 0; i < messages.length; i++) {
					MimeMessage m = (MimeMessage) messages[i];

					InternetAddress[] address;
					uId = inbox.getUID(m); // 邮件UID
					if (outMailManager.getMailByUID(uId,ContextUtil.getCurrentTenantId()).isEmpty()) {
						StringBuffer addr = new StringBuffer("");
						StringBuffer alias = new StringBuffer("");
						OutMail mail = new OutMail();
						mail.setUid(uId);
						mail.setUserId(ContextUtil.getCurrentUserId());
						mail.setMailConfig(mailConfig);
						mail.setMailFolder(mailFolderManager.getMailFolderByConfigIdAndType(mailConfig.getConfigId(), MailFolder.TYPE_RECEIVE_FOLDER)); // 设置邮件的文件夹为收件箱
						try {
							if (m.getSubject() != null) {
								if (m.getSubject().contains("=?") && m.getSubject().endsWith("?=")) {
									mail.setSubject(handle(m.getSubject())); // 处理"=?...?="主题
								} else {
									String charset; // 主题字符集
									try {
										charset = trans((MimeMessage) m); // 判断是否需要解码，如果需要，则获取对应的字符集
									} catch (AddressException e) { // 发件人异常
										continue;
									}
									if ("notrans".equals(charset)) { // 不需要转码
										if (StringUtils.isNotEmpty(m.getSubject()))
											mail.setSubject(m.getSubject());
										else
											mail.setSubject("无主题");
									} else {
										if (StringUtils.isNotEmpty(m.getSubject())) {
											String subject = m.getSubject();
											mail.setSubject(new String(subject.getBytes("iso-8859-1"), charset)); // 转成对应的字符集
										} else
											mail.setSubject("无主题");
									}
								}
							} else
								mail.setSubject("无主题");
						} catch (MessageRemovedException ex) {
							ex.printStackTrace();
							continue;
						}
						StringBuffer ss = new StringBuffer();
						getMailContent(m, ss); // 解析邮件内容

						mail.setContent(removeFourChar(ss.toString().trim())); // 设置内容，过滤表情(4个字节码的表情，如：新浪表情)
						try {
							address = (InternetAddress[]) m.getFrom(); // 设置发件人
						} catch (AddressException e) {
							continue;
						}

						if (address != null && address.length > 0) {
							addr.append(handle((address[0].getAddress().replaceAll("\r", "").replaceAll("\n", ""))));
							if (StringUtils.isNotEmpty(address[0].getPersonal())) { // 如果发件人别名不为空
								// alias.append(handle(address[0].getPersonal().replaceAll("\r",
								// "").replaceAll("\n", "")));
								if (address[0].getPersonal().startsWith("=?")) // 处理发件人别名
									alias.append(handle(address[0].getPersonal())); // 处理发件人别名乱码问题
								else {
									String charset = trans((MimeMessage) m); // 判断是否需要转码，如需转码则返回别名字符集
									if ("notrans".equals(charset)) { // 不转码
										alias.append(address[0].getPersonal());
									} else { // 转码
										String from = address[0].getPersonal();
										alias.append(handleFrom(from, (MimeMessage) m));
									}
								}
							} else {
								alias.append("无");
							}
						} else {
							addr.append("无");
							alias.append("无");
						}

						mail.setSenderAddrs(addr.toString()); // 设置发送人
						mail.setSenderAlias(alias.toString()); // 设置发送人别名

						StringBuffer toaddr = new StringBuffer("");
						StringBuffer toalias = new StringBuffer("");
						address = (InternetAddress[]) m.getRecipients(Message.RecipientType.TO); // 设置收件人
						if (address != null && address.length > 0) {
							for (int j = 0; j < address.length; j++) {
								if (StringUtils.isNotEmpty(address[j].getAddress())) {
									toaddr.append(handle(address[j].getAddress().replaceAll("\r", "").replaceAll("\n", ""))); // 处理收件人乱码问题
									toaddr.append(",");
								} else {
									toaddr.append("无");
								}
								if (StringUtils.isNotEmpty(address[j].getPersonal())) {
									toalias.append(handle(address[j].getPersonal().replaceAll("\r", "").replaceAll("\n", ""))); // 处理收件人别名乱码问题
									toalias.append(",");
								} else {
									toalias.append("无");
								}
							}
						} else {
							toaddr.append("无");
							toalias.append("无");
						}

						if (!"无".equals(toaddr.toString())) // 如果收件人存在则去掉最后一个逗号
							toaddr.deleteCharAt(toaddr.length() - 1);
						if (!"无".equals(toalias.toString()))
							toalias.deleteCharAt(toalias.length() - 1);

						mail.setRecAddrs(toaddr.toString());
						mail.setRecAlias(toalias.toString());

						StringBuffer ccaddr = new StringBuffer("");
						StringBuffer ccalias = new StringBuffer("");
						address = (InternetAddress[]) m.getRecipients(Message.RecipientType.CC); // 设置抄送人
						if (address != null && address.length > 0) {
							for (int j = 0; j < address.length; j++) {
								if (StringUtils.isNotEmpty(address[j].getAddress())) {
									ccaddr.append(handle(address[j].getAddress().replaceAll("\r", "").replaceAll("\n", ""))); // 处理抄送人乱码问题
									ccaddr.append(',');
								} else {
									ccaddr.append("无");
								}
								if (StringUtils.isNotEmpty(address[j].getPersonal())) {
									ccalias.append(handle(address[j].getPersonal().replaceAll("\r", "").replaceAll("\n", "")));// 处理抄送人别名乱码问题
									ccalias.append(',');
								} else {
									ccalias.append("无");
								}
							}
						} else {
							ccaddr.append("无");
							ccalias.append("无");
						}

						if (!"无".equals(ccaddr.toString()))
							ccaddr.deleteCharAt(ccaddr.length() - 1);
						if (!"无".equals(ccalias.toString()))
							ccalias.deleteCharAt(ccalias.length() - 1);

						mail.setCcAddrs(ccaddr.toString());
						mail.setCcAlias(ccalias.toString());

						StringBuffer bccaddr = new StringBuffer("");
						StringBuffer bccalias = new StringBuffer("");
						address = (InternetAddress[]) m.getRecipients(Message.RecipientType.BCC); // 设置暗送人
						if (address != null && address.length > 0) {
							for (int j = 0; j < address.length; j++) {
								if (StringUtils.isNotEmpty(address[j].getAddress())) {
									bccaddr.append(handle(address[j].getAddress().replaceAll("\r", "").replaceAll("\n", ""))); // 处理暗送人乱码问题
									bccaddr.append(',');
								} else {
									bccaddr.append("无");
								}
								if (StringUtils.isNotEmpty(address[j].getPersonal())) {
									bccalias.append(handle(address[j].getPersonal().replaceAll("\r", "").replaceAll("\n", ""))); // 处理按送人别名乱码问题
									bccalias.append(',');
								} else {
									bccalias.append("无");
								}
							}
						} else {
							bccaddr.append("无");
							bccalias.append("无");
						}

						if (!"无".equals(bccaddr.toString()))
							bccaddr.deleteCharAt(bccaddr.length() - 1);
						if (!"无".equals(bccalias.toString()))
							bccalias.deleteCharAt(bccalias.length() - 1);

						mail.setBccAddrs(bccaddr.toString());
						mail.setBccAlias(bccalias.toString());

						saveAttachMent((Part) m, mail); // 保存附件
						try {
							mail.setSendDate(m.getSentDate()); // 设置发送日期
						} catch (Exception ex) {
							ex.printStackTrace();
							mail.setSendDate(new Date());
						}
						if (mail.getSendDate() == null) { // 如果为空
							mail.setSendDate(new Date());
						}
						mail.setReadFlag("0");
						mail.setReplyFlag("0");
						mail.setStatus(OutMail.STATUS_COMMEN);
						outMailManager.create(mail);
						newMailNumber++;
					}
				}
				logger.debug(newMailNumber + "封新邮件！");
			} else {
				logger.debug("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			folder.close(false);
			store.close();
		}

	}

	/**
	 * POP3收取外部邮件
	 * 
	 * @param mailConfig
	 *            外部邮箱账号配置
	 * @throws Exception
	 */
	public void getMailByPop3(MailConfig mailConfig) throws Exception {
		Properties props = new Properties();
		props.put("mail.pop3.auth.plain.disable", "true");
		props.put("mail.pop3.host", mailConfig.getRecpHost());
		props.put("mail.pop3.port", mailConfig.getRecpPort());
		Session session = Session.getInstance(props);
		Store store = session.getStore(mailConfig.getProtocol());
		store.connect(mailConfig.getRecpHost(), Integer.parseInt(mailConfig.getRecpPort()), mailConfig.getMailAccount(), mailConfig.getMailPwd());
		Folder folder = store.getFolder("INBOX"); // 收件箱目录
		folder.open(Folder.READ_ONLY);
		try {
			String uId = "";
			if (folder instanceof POP3Folder) {
				POP3Folder inbox = (POP3Folder) folder;
				Message[] messages = inbox.getMessages();
				Message m;
				int newMailNumber = 0;
				for (int i = 0; i < messages.length; i++) {
					m = (MimeMessage) messages[i];

					InternetAddress[] address;
					uId = inbox.getUID(m); // 邮件UId
					if (outMailManager.getMailByUID(uId,ContextUtil.getCurrentTenantId()).isEmpty()) {
						StringBuffer addr = new StringBuffer("");
						StringBuffer alias = new StringBuffer("");
						OutMail mail = new OutMail();
						mail.setUid(uId);
						mail.setUserId(ContextUtil.getCurrentUserId());
						mail.setMailConfig(mailConfig);
						mail.setMailFolder(mailFolderManager.getMailFolderByConfigIdAndType(mailConfig.getConfigId(), MailFolder.TYPE_RECEIVE_FOLDER)); // 设置邮件的文件夹目录为收件箱

						try {
							if (m.getSubject() != null) {
								if (m.getSubject().contains("=?") && m.getSubject().endsWith("?=")) { // 处理"=?...?="主题
									mail.setSubject(handle(m.getSubject()));
								} else {
									String charset;
									try {
										charset = trans((MimeMessage) m); // 判断是否转码，需要转码则返回主题字符集
									} catch (AddressException e) { // 处理发件人的地址异常
										continue;
									}
									if ("notrans".equals(charset)) { // 不需要转码
										if (StringUtils.isNotEmpty(m.getSubject()))
											mail.setSubject(m.getSubject());
										else
											mail.setSubject("无主题");
									} else {
										if (StringUtils.isNotEmpty(m.getSubject())) {
											String subject = m.getSubject();
											mail.setSubject(new String(subject.getBytes("iso-8859-1"), charset)); // 乱码主题转码
										} else
											mail.setSubject("无主题");
									}
								}
							} else
								mail.setSubject("无主题");
						} catch (MessageRemovedException ex) {
							ex.printStackTrace();
							continue;
						}

						StringBuffer ss = new StringBuffer(1000);
						getMailContent(m, ss); // 解析见邮件正文内容
						mail.setContent(removeFourChar(ss.toString().trim())); // 设置内容

						try {
							address = (InternetAddress[]) m.getFrom(); // 设置发件人
						} catch (AddressException e) {
							continue;
						}
						if (address != null && address.length > 0) {
							addr.append(handle((address[0].getAddress().replaceAll("\r", "").replaceAll("\n", ""))));
							if (StringUtils.isNotEmpty(address[0].getPersonal())) {
								if (address[0].getPersonal().startsWith("=?")) // 处理发件人别名
									alias.append(handle(address[0].getPersonal())); // 处理发件人别名乱码问题
								else {
									String charset = trans((MimeMessage) m); // 判断是否需要转码，如果需要转码，则返回对应主题字符集
									if ("notrans".equals(charset)) { // 不转码
										alias.append(address[0].getPersonal());
									} else { // 转码
										String from = address[0].getPersonal();
										alias.append(handleFrom(from, (MimeMessage) m));
									}
								}
							} else {
								alias.append("无");
							}
						} else {
							addr.append("无");
							alias.append("无");
						}

						mail.setSenderAddrs(addr.toString());
						mail.setSenderAlias(alias.toString());

						StringBuffer toaddr = new StringBuffer("");
						StringBuffer toalias = new StringBuffer("");
						address = (InternetAddress[]) m.getRecipients(Message.RecipientType.TO); // 设置收件人
						if (address != null && address.length > 0) {
							for (int j = 0; j < address.length; j++) {
								if (StringUtils.isNotEmpty(address[j].getAddress())) {
									toaddr.append(handle(address[j].getAddress().replaceAll("\r", "").replaceAll("\n", ""))); // 收件人乱码问题
									toaddr.append(",");
								} else {
									toaddr.append("无");
								}
								if (StringUtils.isNotEmpty(address[j].getPersonal())) {
									toalias.append(handle(address[j].getPersonal().replaceAll("\r", "").replaceAll("\n", ""))); // 处理收件人别名乱码问题
									toalias.append(",");
								} else {
									toalias.append("无");
								}
							}
						} else {
							toaddr.append("无");
							toalias.append("无");
						}

						if (!"无".equals(toaddr.toString())) // 删除收件人最后一个逗号
							toaddr.deleteCharAt(toaddr.length() - 1);
						if (!"无".equals(toalias.toString())) // 删除收件人别名最后一个逗号
							toalias.deleteCharAt(toalias.length() - 1);

						mail.setRecAddrs(toaddr.toString());
						mail.setRecAlias(toalias.toString());

						StringBuffer ccaddr = new StringBuffer("");
						StringBuffer ccalias = new StringBuffer("");
						address = (InternetAddress[]) m.getRecipients(Message.RecipientType.CC); // 设置抄送人
						if (address != null && address.length > 0) {
							for (int j = 0; j < address.length; j++) {
								if (StringUtils.isNotEmpty(address[j].getAddress())) {
									ccaddr.append(handle(address[j].getAddress().replaceAll("\r", "").replaceAll("\n", ""))); // 处理抄送人乱码问题
									ccaddr.append(',');
								} else {
									ccaddr.append("无");
								}
								if (StringUtils.isNotEmpty(address[j].getPersonal())) {
									ccalias.append(handle(address[j].getPersonal().replaceAll("\r", "").replaceAll("\n", ""))); // 处理抄送人别名乱码问题
									ccalias.append(',');
								} else {
									ccalias.append("无");
								}
							}
						} else {
							ccaddr.append("无");
							ccalias.append("无");
						}

						if (!"无".equals(ccaddr.toString())) // 删除抄送人最后一个逗号
							ccaddr.deleteCharAt(ccaddr.length() - 1);
						if (!"无".equals(ccalias.toString())) // 删除抄送人别名最后一个逗号
							ccalias.deleteCharAt(ccalias.length() - 1);

						mail.setCcAddrs(ccaddr.toString());
						mail.setCcAlias(ccalias.toString());

						StringBuffer bccaddr = new StringBuffer("");
						StringBuffer bccalias = new StringBuffer("");
						address = (InternetAddress[]) m.getRecipients(Message.RecipientType.BCC); // 设置暗送人
						if (address != null && address.length > 0) {
							for (int j = 0; j < address.length; j++) {
								if (StringUtils.isNotEmpty(address[j].getAddress())) {
									bccaddr.append(handle(address[j].getAddress().replaceAll("\r", "").replaceAll("\n", ""))); // 处理暗送人乱码
									bccaddr.append(',');
								} else {
									bccaddr.append("无");
								}
								if (StringUtils.isNotEmpty(address[j].getPersonal())) {
									bccalias.append(handle(address[j].getPersonal().replaceAll("\r", "").replaceAll("\n", ""))); // 处理暗送人别名乱码
									bccalias.append(',');
								} else {
									bccalias.append("无");
								}
							}
						} else {
							bccaddr.append("无");
							bccalias.append("无");
						}

						if (!"无".equals(bccaddr.toString())) // 删除最后一个逗号
							bccaddr.deleteCharAt(bccaddr.length() - 1);
						if (!"无".equals(bccalias.toString())) // 删除最后一个逗号
							bccalias.deleteCharAt(bccalias.length() - 1);

						mail.setBccAddrs(bccaddr.toString());
						mail.setBccAlias(bccalias.toString());

						saveAttachMent((Part) m, mail); // 保存邮件附件到本地

						try {
							mail.setSendDate(m.getSentDate());
						} catch (Exception ex) {
							ex.printStackTrace();
							mail.setSendDate(new Date());
						}
						if (mail.getSendDate() == null) {
							mail.setSendDate(new Date());
						}

						mail.setReadFlag("0");
						mail.setReplyFlag("0");
						mail.setStatus(OutMail.STATUS_COMMEN);
						outMailManager.create(mail);
						newMailNumber++;
					}
				}
				logger.debug(newMailNumber + "封新邮件！");
			} else {
				logger.debug("error");
			}
		} finally {
			folder.close(false);
			store.close();
		}
	}

	/**
	 * imap收取邮件
	 * 
	 * @param mailConfig
	 *            外部邮箱账号配置
	 * @throws Exception
	 */
	public void getMailByIMAP(MailConfig mailConfig) throws Exception {
		Properties props = new Properties(); // 设置链接IMAP服务器的属性
		props.put("mail.imap.host", mailConfig.getRecpHost());
		props.put("mail.imap.port", mailConfig.getRecpPort());
		props.setProperty("mail.store.protocol", mailConfig.getProtocol());
		int newMailNumber = 0;
		Session session = Session.getInstance(props);
		URLName urln = new URLName(mailConfig.getProtocol(), mailConfig.getRecpHost(), Integer.parseInt(mailConfig.getRecpPort()), null, mailConfig.getMailAccount(), mailConfig.getMailPwd());
		IMAPStore store = (IMAPStore) session.getStore(urln);
		store.connect();
		Folder folder = (IMAPFolder) store.getFolder("INBOX"); // 收件箱目录
		folder.open(Folder.READ_ONLY);
		Message messages[] = null;
		FetchProfile profile = new FetchProfile();// 感兴趣的信息
		profile.add(UIDFolder.FetchProfileItem.UID);// 邮件标识id
		String uId = "0";

		if (folder instanceof IMAPFolder) {
			IMAPFolder inbox = (IMAPFolder) folder;
			messages = inbox.getMessages();
			inbox.fetch(messages, profile);
			for (int i = 0; i < messages.length; i++) {
				uId = String.valueOf(inbox.getUID(messages[i])); // 邮件UID

				if (outMailManager.getMailByUID(uId,ContextUtil.getCurrentTenantId()).isEmpty()) { // 如果邮件不存在
					Message message = messages[i];
					OutMail mail = new OutMail();
					mail.setUid(uId);
					mail.setUserId(ContextUtil.getCurrentUserId());
					mail.setMailConfig(mailConfig);
					mail.setMailFolder(mailFolderManager.getMailFolderByConfigIdAndType(mailConfig.getConfigId(), MailFolder.TYPE_RECEIVE_FOLDER)); // 设置邮件的文件夹为对应的收件箱目录
					Date sentDate = message.getSentDate();
					if (sentDate == null) // 如果发送时间为空，则设为现在的时间
						sentDate = new Date();
					// 邮件发送时间
					mail.setSendDate(sentDate);

					if (StringUtils.isNotEmpty(message.getSubject()))
						mail.setSubject(handle(message.getSubject().replaceAll("\r", "").replaceAll("\n", ""))); // 处理邮件主题乱码问题
					else
						mail.setSubject("无主题");

					StringBuffer mailContent = new StringBuffer();
					getMailContent(message, mailContent); // 解析邮件正文内容
					mail.setContent(mailContent.toString()); // 设置内容

					InternetAddress address[] = (InternetAddress[]) message.getFrom();
					if (address == null || address.length == 0) { // 如果发送人为空
						mail.setSenderAddrs("无");
						mail.setSenderAlias("无");
					}
					mail.setSenderAddrs(address[0].getAddress()); // 设置发送人邮箱地址
					mail.setSenderAlias(address[0].getPersonal()); // 设置发送人别名

					address = (InternetAddress[]) message.getRecipients(Message.RecipientType.TO);
					if (address == null || address.length == 0) { // 收件人为空
						mail.setRecAddrs("无");
						mail.setRecAlias("无");
					} else {
						StringBuffer addresses = new StringBuffer("");
						StringBuffer name = new StringBuffer("");
						for (int j = 0; j < address.length; j++) { // 遍历收件人地址列表
							String email = address[j].getAddress();
							if (email == null) // 如果当前收件人地址为空
								continue;
							String personal = address[j].getPersonal(); // 获取收件人别名
							if (personal == null) // 如果为空，则邮箱地址作为别名
								personal = email;
							switch (j) {
							case 0: // 第一个收件人
								addresses.append(handle(email));
								name.append(handle(personal));
								break;
							default: // 剩下的收件人
								addresses.append(",").append(handle(email));
								name.append(",").append(handle(personal));
							}
						}
						mail.setRecAddrs(addresses.toString()); // 设置收件人
						mail.setRecAlias(name.toString()); // 设置收件人别名
					}

					address = (InternetAddress[]) message.getRecipients(Message.RecipientType.CC);
					if (address == null) {
						mail.setCcAddrs("无");
						mail.setCcAlias("无");
					} else {
						StringBuffer addresses = new StringBuffer("");
						StringBuffer name = new StringBuffer("");
						for (int j = 0; j < address.length; j++) { // 设置抄送人
							String email = address[j].getAddress();
							if (email == null) // 如果邮箱地址为空
								continue;
							String personal = address[j].getPersonal(); // 获取抄送人别名
							if (personal == null) // 如果别名为空，则设为邮箱地址
								personal = email;
							switch (j) {
							case 0: // 第一个抄送人
								addresses.append(handle(email));
								name.append(handle(personal));
								break;
							default: // 剩下的抄送人
								addresses.append(",").append(handle(email));
								name.append(",").append(handle(personal));
							}
						}
						mail.setCcAddrs(addresses.toString()); // 设置抄送人
						mail.setCcAlias(name.toString()); // 设置抄送人别名
					}

					address = (InternetAddress[]) message.getRecipients(Message.RecipientType.BCC);
					if (address == null) {
						mail.setBccAddrs("无");
						mail.setBccAlias("无");
					} else {
						StringBuffer addresses = new StringBuffer("");
						StringBuffer name = new StringBuffer("");
						for (int j = 0; j < address.length; j++) { // 设置暗送人
							String email = address[j].getAddress();
							if (email == null) // 如果邮箱地址为空
								continue;
							String personal = address[j].getPersonal();
							if (personal == null) // 如果暗送人别名为空，则设为邮箱地址
								personal = email;
							switch (j) {
							case 0:// 第一个暗送人
								addresses.append(handle(email));
								name.append(handle(personal));
								break;
							default:// 剩下的按送人
								addresses.append(",").append(handle(email));
								name.append(",").append(handle(personal));
							}
						}
						mail.setBccAddrs(addresses.toString()); // 设置暗送人
						mail.setBccAlias(name.toString()); // 设置暗送人别名
					}
					saveAttachMent((Part) message, mail);// 保存附件到本地
					Flags flags = message.getFlags();
					if (flags.contains(Flags.Flag.SEEN)) {
						mail.setReadFlag("1");
					} else {
						mail.setReadFlag("0");
						newMailNumber++;
					}
					mail.setReplyFlag("0");
					mail.setStatus(OutMail.STATUS_COMMEN);
					outMailManager.create(mail);
				}
			}
			logger.debug(newMailNumber + "封新邮件！");
		}
	}

	/**
	 * imapSSL收取外部邮件
	 * 
	 * @param mailConfig
	 * @throws Exception
	 */
	public void getMailByIMAPSsl(MailConfig mailConfig) throws Exception {
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		Properties props = new Properties(); // 设置连接IMAPSSL服务器的属性
		props.put("mail.imap.ssl.enable", "true".equals(mailConfig.getSsl()) ? " true" : " false");
		props.put("mail.imap.auth.plain.disable", "true");
		props.put("mail.imap.auth.login.disable", "true");
		props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.imap.socketFactory.fallback", "false");
		props.setProperty("mail.imap.socketFactory.port", "993");
		props.put("mail.imap.host", mailConfig.getRecpHost());
		props.put("mail.imap.port", mailConfig.getRecpPort());
		props.setProperty("mail.store.protocol", "imap");
		final String username = mailConfig.getMailAccount(); // 邮箱用户名
		final String password = mailConfig.getMailPwd(); // 邮箱密码
		int newMailNumber = 0;
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		URLName urln = new URLName(mailConfig.getProtocol(), mailConfig.getRecpHost(), Integer.parseInt(mailConfig.getRecpPort()), null, mailConfig.getMailAccount(), mailConfig.getMailPwd());
		IMAPStore store = (IMAPStore) session.getStore(urln);
		store.connect();
		Folder folder = (IMAPFolder) store.getFolder("INBOX"); // 收件箱目录
		folder.open(Folder.READ_ONLY);
		Message messages[] = null;
		FetchProfile profile = new FetchProfile();// 感兴趣的信息
		profile.add(UIDFolder.FetchProfileItem.UID);// 邮件标识id
		String uId = "0";

		if (folder instanceof IMAPFolder) {
			IMAPFolder inbox = (IMAPFolder) folder;
			messages = inbox.getMessages();
			inbox.fetch(messages, profile);
			for (int i = 0; i < messages.length; i++) {
				uId = String.valueOf(inbox.getUID(messages[i])); // 邮件UID

				if (outMailManager.getMailByUID(uId,ContextUtil.getCurrentTenantId()).isEmpty()) { // 如果邮件不存在
					Message message = messages[i];
					OutMail mail = new OutMail();
					mail.setUid(uId);
					mail.setUserId(ContextUtil.getCurrentUserId());
					mail.setMailConfig(mailConfig);
					mail.setMailFolder(mailFolderManager.getMailFolderByConfigIdAndType(mailConfig.getConfigId(), MailFolder.TYPE_RECEIVE_FOLDER));// 设置邮件的文件夹为对应账号配置的收件箱
					Date sentDate = message.getSentDate();
					if (sentDate == null) // 如果发送日期为空
						sentDate = new Date();
					mail.setSendDate(sentDate);// 邮件发送时间
					if (StringUtils.isNotEmpty(message.getSubject()))
						mail.setSubject(handle(message.getSubject().replaceAll("\r", "").replaceAll("\n", ""))); // 处理邮件主题乱码问题
					else
						mail.setSubject("无主题");
					StringBuffer mailContent = new StringBuffer();
					getMailContent(message, mailContent);// 解析邮件正文内容
					mail.setContent(mailContent.toString()); // 设置内容

					InternetAddress address[] = (InternetAddress[]) message.getFrom();
					if (address == null || address.length == 0) { // 如果发件人为空
						mail.setSenderAddrs("无");
						mail.setSenderAlias("无");
					}
					mail.setSenderAddrs(address[0].getAddress()); // 设置发件人
					mail.setSenderAlias(address[0].getPersonal()); // 设置发件人别名

					address = (InternetAddress[]) message.getRecipients(Message.RecipientType.TO);
					if (address == null || address.length == 0) { // 如果收件人为空
						mail.setRecAddrs("无");
						mail.setRecAlias("无");
					} else {
						StringBuffer addresses = new StringBuffer("");
						StringBuffer name = new StringBuffer("");
						for (int j = 0; j < address.length; j++) { // 遍历收件人
							String email = address[j].getAddress();
							if (email == null) // 如果邮件地址为空
								continue;
							String personal = address[j].getPersonal();
							if (personal == null) // 如果收件人别名为空，则设置为收件人邮箱地址
								personal = email;
							switch (j) {
							case 0: // 第一个收件人
								addresses.append(handle(email));
								name.append(handle(personal));
								break;
							default: // 剩下的收件人
								addresses.append(",").append(handle(email));
								name.append(",").append(handle(personal));
							}
						}
						mail.setRecAddrs(addresses.toString()); // 设置收件人
						mail.setRecAlias(name.toString()); // 设置收件人别名
					}

					address = (InternetAddress[]) message.getRecipients(Message.RecipientType.CC);
					if (address == null) {
						mail.setCcAddrs("无");
						mail.setCcAlias("无");
					} else {
						StringBuffer addresses = new StringBuffer("");
						StringBuffer name = new StringBuffer("");
						for (int j = 0; j < address.length; j++) { // 遍历抄送人
							String email = address[j].getAddress();
							if (email == null) // 如果抄送人为空
								continue;
							String personal = address[j].getPersonal();
							if (personal == null) // 如果抄送人别名为空，则设置为抄送人邮箱地址
								personal = email;
							switch (j) {
							case 0: // 第一个抄送人
								addresses.append(handle(email));
								name.append(handle(personal));
								break;
							default: // 剩下的抄送人
								addresses.append(",").append(handle(email));
								name.append(",").append(handle(personal));
							}
						}
						mail.setCcAddrs(addresses.toString()); // 设置抄送人
						mail.setCcAlias(name.toString());// 设置抄送人别名
					}

					address = (InternetAddress[]) message.getRecipients(Message.RecipientType.BCC);
					if (address == null) {
						mail.setBccAddrs("无");
						mail.setBccAlias("无");
					} else {
						StringBuffer addresses = new StringBuffer("");
						StringBuffer name = new StringBuffer("");
						for (int j = 0; j < address.length; j++) { // 遍历暗送人
							String email = address[j].getAddress();
							if (email == null) // 如果暗送人地址为空
								continue;
							String personal = address[j].getPersonal();
							if (personal == null) // 如果暗送人别名为空，则设置为暗送人地址
								personal = email;
							switch (j) {
							case 0:// 第一个暗送人
								addresses.append(handle(email));
								name.append(handle(personal));
								break;
							default:// 剩下的暗送人
								addresses.append(",").append(handle(email));
								name.append(",").append(handle(personal));
							}
						}
						mail.setBccAddrs(addresses.toString()); // 设置暗送人
						mail.setBccAlias(name.toString()); // 设置暗送人别名
					}
					saveAttachMent((Part) message, mail);// 保存附件到本地
					Flags flags = message.getFlags();
					if (flags.contains(Flags.Flag.SEEN)) {
						mail.setReadFlag("1");
					} else {
						mail.setReadFlag("0");
						newMailNumber++;
					}
					mail.setReplyFlag("0");
					mail.setStatus(OutMail.STATUS_COMMEN);
					outMailManager.create(mail);
				}
			}
			logger.debug(newMailNumber + "封新邮件！");
		}

	}

	/**
	 * 判断乱码字符串是否解码
	 * 
	 * @param message
	 *            外部邮件信息
	 * @return
	 * @throws Exception
	 */
	public String trans(MimeMessage message) throws Exception {
		String subject = "";
		if (message.getSubject() != null)
			subject = message.getSubject();
		InternetAddress[] addresses;
		addresses = (InternetAddress[]) message.getFrom(); // 获取发件人
		String fromname = "";
		if (addresses != null && addresses.length > 0) {
			fromname = addresses[0].getPersonal(); // 获取发件人别名
			if (fromname == null)
				fromname = "";
		}
		addresses = (InternetAddress[]) message.getRecipients(Message.RecipientType.TO);
		String toname = "";
		if (addresses != null && addresses.length > 0) {
			toname = addresses[0].getPersonal(); // 获取收件人别名
			if (toname == null)
				toname = "";
		}
		addresses = (InternetAddress[]) message.getRecipients(Message.RecipientType.CC);
		String ccname = "";
		if (addresses != null && addresses.length > 0) {
			ccname = addresses[0].getPersonal(); // 获取抄送人别名
			if (ccname == null)
				ccname = "";
		}
		addresses = (InternetAddress[]) message.getRecipients(Message.RecipientType.BCC);
		String bccname = "";
		if (addresses != null && addresses.length > 0) {
			bccname = addresses[0].getPersonal(); // 获取暗送人别名
			if (bccname == null)
				bccname = "";
		}
		if (StringUtils.isNotEmpty(subject) || StringUtils.isNotEmpty(fromname) || StringUtils.isNotEmpty(toname) || StringUtils.isNotEmpty(ccname) || StringUtils.isNotEmpty(bccname)) { // 当主题或者发件人或者收件人或者抄送人或者暗送人的某一个不为空的时候
			String to = "";
			String cc = "";
			String bcc = "";
			if (message.getHeader("To") != null && message.getHeader("To").length > 0)
				to = message.getHeader("To")[0]; // 从邮件头部获取收件人
			if (message.getHeader("CC") != null && message.getHeader("CC").length > 0)
				cc = message.getHeader("CC")[0]; // 从邮件头部获取抄送人
			if (message.getHeader("BCC") != null && message.getHeader("BCC").length > 0)
				bcc = message.getHeader("BCC")[0]; // 从邮件头部获取暗送人
			if (StringUtils.isNotEmpty(to) && StringUtils.isNotEmpty(cc) && StringUtils.isNotEmpty(bcc)) { // 当收件人，抄送人，暗送人都不为空的时候
				String subject1 = "";
				if (message.getHeader("Subject") != null && message.getHeader("Subject").length > 0)
					subject1 = message.getHeader("Subject")[0]; // 从邮件头部获取主题

				if ((!subject.contains("=?") && subject1.startsWith("=?")) || (!fromname.contains("=?") && message.getHeader("From")[0].startsWith("=?")) || (!toname.contains("=?") && message.getHeader("To")[0].startsWith("=?")) || (!ccname.contains("=?") && message.getHeader("CC")[0].startsWith("=?")) || (!bccname.contains("=?") && message.getHeader("BCC")[0].startsWith("=?"))) // 当主题或者发件人或者收件人或者抄送人或者暗送人，当中的某一个主题或别名满足不含"=?"开头的字符串同时未解码的时候以"=?"开头的时候
					return "notrans";
				else
					return getCharset(message); // 获取字符集
			} else {
				String subject1 = "";
				if (message.getHeader("Subject") != null && message.getHeader("Subject").length > 0)
					subject1 = message.getHeader("Subject")[0]; // 从邮件头部获取主题
				if ((!subject.contains("=?") && subject1.startsWith("=?")) || (!fromname.contains("=?") && message.getHeader("From")[0].startsWith("=?")))// 当主题或者发件人不包含"=?"以及从邮件头部获取的信息以"=?"开头
					return "notrans";
				else
					return getCharset(message); // 获取字符集
			}

		} else
			return "utf-8";
	}

	/**
	 * 从外部邮件头部获取邮件编码
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public String getCharset(MimeMessage message) throws Exception { // 获取字符集
		String charset = "gb2312"; // 默认编码
		String s = message.getContentType(); // 从contenttype获取字符集
		if (s.indexOf("charset") != -1) {
			int index = s.indexOf("charset");
			int startIndex = index + 8;
			int endIndex = 0;
			if (s.indexOf("\"", startIndex + 1) > 0) // 如果charset后带双引号，则结束索引为下一个双引号
				endIndex = s.indexOf("\"", startIndex + 1);
			else
				endIndex = s.length();
			charset = s.substring(startIndex, endIndex);
			if (charset.contains("\\")) { // 如果字符串中有反斜杠，则从开始截取到反斜杠
				charset = charset.substring(0, charset.indexOf("\\"));
			}
			if (charset.contains("\"")) { // 如果字符串中有双引号
				StringBuffer sb2 = new StringBuffer(charset);
				sb2.deleteCharAt(0); // 删除最后一个字符
				charset = sb2.toString();
				if (charset.contains("/")) { // 如果包含"/"
					charset = "gb2312";
				}
			} else { // 没有双引号
				if (charset.contains("/")) { // 如果包含"/"
					charset = "gb2312";
				}
			}
			charset = charset.trim(); // 去除首位空白
			return charset;
		}
		s = message.getHeader("Subject")[0]; // 主题获取字符集
		if (s.contains("=?")) { // 从一个问号和第二个问号中获取主题字符集
			StringBuffer sb = new StringBuffer(s);
			int startIndex = sb.indexOf("?");
			int endIndex = sb.indexOf("?", startIndex + 1);
			charset = sb.substring(startIndex + 1, endIndex);
			return charset;
		}
		s = message.getHeader("From")[0]; // 发件人获取字符集
		if (s.contains("=?")) { // 从一个问号和第二个问号中获取发件人字符集
			StringBuffer sb = new StringBuffer(s);
			int startIndex = sb.indexOf("?");
			int endIndex = sb.indexOf("?", startIndex + 1);
			charset = sb.substring(startIndex + 1, endIndex);
			return charset;
		}

		if (message.getHeader("To") != null && message.getHeader("To").length > 0) // 收件人获取字符集
			s = message.getHeader("To")[0];
		else
			s = "";
		if (s.contains("=?")) { // 从一个问号和第二个问号中获取收件人字符集
			StringBuffer sb = new StringBuffer(s);
			int startIndex = sb.indexOf("?");
			int endIndex = sb.indexOf("?", startIndex + 1);
			charset = sb.substring(startIndex + 1, endIndex);
			return charset;
		}
		StringBuffer c = new StringBuffer(""); // 内容获取字符集
		getMailContent(message, c);
		if (c.toString().indexOf("charset") != -1) { // 从邮件正文获取字符集
			int index = c.toString().indexOf("charset");
			int startIndex = index + 8;
			int endIndex = 0;
			if (c.toString().indexOf("\"", startIndex + 1) > 0)
				endIndex = c.toString().indexOf("\"", startIndex + 1);
			else
				endIndex = c.toString().length();
			charset = c.toString().substring(startIndex, endIndex);
			if (charset.contains("\"")) { // 如果包含双引号
				StringBuffer sb2 = new StringBuffer(charset);
				sb2.deleteCharAt(0);
				charset = sb2.toString();
				if (charset.contains("/")) { // 如果包含"/"
					charset = "gb2312";
				}
			} else {
				if (charset.contains("/")) { // 如果包含"/"
					charset = "gb2312";
				}
			}
			charset = charset.trim();
			return charset;
		}
		charset = charset.trim();
		return charset;
	}

	/**
	 * 处理"=?...?="的乱码内容
	 * 
	 * @param subject
	 *            需要解码的字符串
	 * @return
	 * @throws Exception
	 */
	public String handle(String subject) throws Exception {
		if (!(subject.contains("=?"))) { // 如果不含"=?"
			return subject;
		}
		subject = handleComplex(subject);
		StringBuffer sb = new StringBuffer(subject);
		String n = "";
		int temp = 0;
		char[] a;

		if (subject.contains("=?GB") || subject.contains("=?gb")) { // 处理GBK,GB2312,GB18030以及GB开头的编码的字符串
			if (subject.contains("=?GB")) {
				while (subject.indexOf("GB", temp) != -1) {
					a = subject.toCharArray();
					temp = subject.indexOf("GB", temp);
					temp = subject.indexOf("?", temp);
					if (a[temp + 1] >= 'a' && a[temp + 1] <= 'z') {
						a[temp + 1] = Character.toUpperCase(a[temp + 1]);
					}
					n = new String(a);
				}
			}
			if (subject.contains("=?gb")) {
				while (subject.indexOf("gb", temp) != -1) {
					a = subject.toCharArray();
					temp = subject.indexOf("gb", temp);
					temp = subject.indexOf("?", temp);
					if (a[temp + 1] >= 'a' && a[temp + 1] <= 'z') {
						a[temp + 1] = Character.toUpperCase(a[temp + 1]);
					}
					n = new String(a);
				}
			}
			return MimeUtility.decodeText(n);
		}

		if ((subject.indexOf("=?utf") != -1) || (subject.indexOf("=?UTF") != -1)) { // 处理UTF8的编码的字符串
			if (subject.contains("=?utf-8")) { // 处理utf-8编码的字符串
				while (subject.indexOf("utf-8", temp) != -1) {
					a = subject.toCharArray();
					temp = subject.indexOf("utf-8", temp);
					if (a[temp + 6] >= 'a' && a[temp + 6] <= 'z') {
						a[temp + 6] = Character.toUpperCase(a[temp + 6]);
					}
					n = new String(a);
					temp = temp + 5;
				}
			}
			if (subject.contains("=?utf8")) { // 处理utf8编码的字符串
				while (subject.indexOf("utf8", temp) != -1) {
					a = subject.toCharArray();
					temp = subject.indexOf("utf8", temp);
					if (a[temp + 5] >= 'a' && a[temp + 5] <= 'z') {
						a[temp + 5] = Character.toUpperCase(a[temp + 5]);
					}
					n = new String(a);
					temp = temp + 4;
				}
			}
			if (subject.contains("=?UTF8")) { // 处理UTF8编码的字符串
				while (subject.indexOf("UTF8", temp) != -1) {
					a = subject.toCharArray();
					temp = subject.indexOf("UTF8", temp);
					if (a[temp + 5] >= 'a' && a[temp + 5] <= 'z') {
						a[temp + 5] = Character.toUpperCase(a[temp + 5]);
					}
					n = new String(a);
					temp = temp + 4;
				}
			}
			if (subject.contains("=?UTF-8")) { // 处理UTF-8编码的字符串
				while (subject.indexOf("UTF-8", temp) != -1) {
					a = subject.toCharArray();
					temp = subject.indexOf("UTF-8", temp);
					if (a[temp + 6] >= 'a' && a[temp + 6] <= 'z') {
						a[temp + 6] = Character.toUpperCase(a[temp + 6]);
					}
					n = new String(a);
					temp = temp + 5;
				}
			}
			return MimeUtility.decodeText(n);
		}

		logger.debug("else");
		return MimeUtility.decodeText(sb.toString()); // 其他编码直接解码
	}

	/**
	 * 处理发件人别名函数
	 * 
	 * @param from
	 *            发件人
	 * @param message
	 *            外部邮件信息
	 * @return
	 * @throws Exception
	 */
	public String handleFrom(String from, MimeMessage message) throws Exception {
		if (from.startsWith("=?") && from.endsWith("?=")) { // 如果以"=?"开始和"?="结束
			return handle(from);
		} else {
			String charset = getCharset(message); // 获取字符集
			if (from.equals(new String(from.getBytes(charset)))) {
				return from;
			} else {
				from = (new String(from.getBytes("iso-8859-1"), charset)); // 转成对应的字符集编码的字符
				return from;
			}
		}
	}

	/**
	 * 处理"=?"，"=?="错误串，并使其只能正常显示
	 * 
	 * @param subject
	 * @return
	 * @throws Exception
	 */
	public String handleComplex(String subject) throws Exception {
		int temp = 0;
		while (subject.indexOf("=?", temp) > -1) { // 去除加密字符串中的无用空格
			int startIndex = subject.indexOf("=?", temp);
			int endIndex = subject.indexOf("?=", startIndex + 2);
			String a = subject.substring(0, startIndex);
			String b = subject.substring(startIndex, endIndex).replaceAll("\\s*", "");
			String c = subject.substring(endIndex);
			subject = a + b + c;
			temp = endIndex;
		}
		temp = 0;

		if (subject.contains("=?=")) { // 处理加密字符串无用的"="
			StringBuffer s = new StringBuffer(subject);
			while (s.indexOf("=?=", temp) != -1) {
				if (s.charAt(s.indexOf("=?=", temp) - 1) != '=' || (s.charAt(s.indexOf("=?=", temp) - 1) != '=' && s.charAt(s.indexOf("=?=", temp) - 2) != '=')) {
					s.deleteCharAt(s.indexOf("=?=", temp));
					temp = s.indexOf("=?=", temp) + 3;
					continue;
				}
				temp = s.indexOf("=?=", temp) + 3;
			}
			if (!MimeUtility.decodeText(s.toString()).contains("=?") && !MimeUtility.decodeText(s.toString()).endsWith("?="))
				subject = s.toString();
		}
		if (subject.indexOf("=?") <= 0)
			return subject;
		else {
			int i = 0;
			while (subject.indexOf("=?", i) > 0) { // 处理多个加密字符串连在一起 如?==? 加空格
				subject = stringInsert(subject, " =", subject.indexOf("=?", i));
				i = subject.indexOf("=?", i) + 2;
			}
			StringBuffer sb = new StringBuffer(subject);
			if (sb.charAt(sb.indexOf("=?") - 1) == ' ' && sb.charAt(sb.indexOf("=?") - 2) == ' ')
				sb.deleteCharAt(sb.indexOf("=?") - 1);
			subject = sb.toString();
			return subject;
		}
	}

	/**
	 * 在字符串某个位置插入字符
	 * 
	 * @param a
	 *            原来字符串
	 * @param b
	 *            要插入的字符串
	 * @param t
	 *            插入位置
	 * @return
	 */
	public String stringInsert(String a, String b, int t) {
		return a.substring(0, t) + b + a.substring(t + 1, a.length());
	}

	/**
	 * 解析邮件正文内容
	 * 
	 * @param part
	 * @param content
	 * @throws Exception
	 */
	public void getMailContent(Part part, StringBuffer content) throws Exception {
		try {
			String contenttype = part.getContentType();
			int nameindex = contenttype.indexOf("name");
			boolean conname = false;
			if (nameindex != -1)
				conname = true;
			if (part.isMimeType("text/plain") && !conname) {
				content.append((String) part.getContent());
			} else if (part.isMimeType("text/html") && !conname) {
				content.append((String) part.getContent());
			} else if (part.isMimeType("multipart/*")) {
				Multipart multipart = (Multipart) part.getContent();
				int counts = multipart.getCount();
				for (int i = 0; i < counts; i++) {
					getMailContent(multipart.getBodyPart(i), content);
				}
			} else if (part.isMimeType("message/rfc822")) {
				getMailContent((Part) part.getContent(), content);
			} else {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断此邮件是否包含附件
	 * 
	 * @param part
	 * @return
	 * @throws Exception
	 */
	public boolean isContainAttach(Part part) throws Exception {
		boolean attachflag = false;
		if (part.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) part.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart mpart = mp.getBodyPart(i);
				String disposition = mpart.getDisposition();
				if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE))))
					attachflag = true;
				else if (mpart.isMimeType("multipart/*")) {
					attachflag = isContainAttach((Part) mpart);
				} else {
					String contype = mpart.getContentType();
					if (contype.toLowerCase().indexOf("application") != -1)
						attachflag = true;
					if (contype.toLowerCase().indexOf("name") != -1)
						attachflag = true;
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			attachflag = isContainAttach((Part) part.getContent());
		}
		return attachflag;
	}

	/**
	 * 保存附件
	 * 
	 * @param part
	 * @param mail
	 * @throws Exception
	 */
	public void saveAttachMent(Part part, OutMail mail) throws Exception {
		String fileName = "";
		if (part.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) part.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart mpart = mp.getBodyPart(i);
				String disposition = mpart.getDisposition();
				if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {
					fileName = mpart.getFileName();
					/*
					 * if (fileName.toLowerCase().indexOf("gb2312") != -1) {
					 * fileName = handle(fileName); }
					 */
					if (fileName != null) {
						fileName = handle(fileName.replaceAll("\r", "").replaceAll("\n", "")); // 处理附件名乱码问题
						SysFile sysFile = new SysFile(); // 将附件信息保存到SysFile实体中，然后保存到数据库中
						String fileId = idGenerator.getSID();
						String filerExt = FileUtil.getFileExt(fileName); // 获取附件名后缀名
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
						String tempPath = sdf.format(new Date());
						IUser curUser = currentContext.getCurrentUser();
						String account = curUser.getUsername();
						String relFilePath = account + "/" + tempPath;
						String fullPath = WebAppUtil.getUploadPath() + "/" + relFilePath;
						String fileFullPath = fullPath + "/" + fileId + "." + filerExt;
						File dirFile = new File(fullPath);
						if (!dirFile.exists()) {
							dirFile.mkdirs();
						}
						copy(mpart.getInputStream(), new FileOutputStream(fileFullPath)); // 将附件复制到本地
						sysFile.setFileId(fileId);
						sysFile.setFileName(fileName);
						sysFile.setPath(relFilePath + "/" + fileId + "." + filerExt);
						sysFile.setExt("." + filerExt);

						FileExt fileExt = fileUploadConfig.getFileExtMap().get(filerExt.toLowerCase()); // 设置minetype
						if (fileExt != null) {
							sysFile.setMineType(fileExt.getMineType());
						} else {
							sysFile.setMineType("Unkown");
						}

						sysFile.setDesc("");

						FileChannel fc = null; // 计算文件大小
						try {
							File f = new File(fileFullPath);
							if (f.exists() && f.isFile()) {
								FileInputStream fis = new FileInputStream(f);
								fc = fis.getChannel();
								long size = fc.size();
								sysFile.setTotalBytes(size);
								fis.close();
							} else {
								logger.debug("file not exist!");
							}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							if (null != fc) {
								try {
									fc.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}

						sysFile.setNewFname(fileId + "." + filerExt); // 新文件名字
						sysFileManager.create(sysFile);
						mail.getInfMailFiles().add(sysFile);
					}
				} else if (mpart.isMimeType("multipart/*")) {
					saveAttachMent(mpart, mail);
				} else {
					fileName = mpart.getFileName();
					if (fileName != null) {
						fileName = handle(fileName);
						SysFile sysFile = new SysFile();
						String fileId = idGenerator.getSID();
						String filerExt = FileUtil.getFileExt(fileName); // 获取附件的后缀名
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
						String tempPath = sdf.format(new Date());
						IUser curUser = currentContext.getCurrentUser();
						String account = curUser.getUsername();
						String relFilePath = account + "/" + tempPath;
						String fullPath = WebAppUtil.getUploadPath() + "/" + relFilePath;
						String fileFullPath = fullPath + "/" + fileId + "." + filerExt;
						File dirFile = new File(fullPath);
						if (!dirFile.exists()) {
							dirFile.mkdirs();
						}
						copy(mpart.getInputStream(), new FileOutputStream(fileFullPath)); // 将附件复制到本地
						sysFile.setFileId(fileId);
						sysFile.setFileName(fileName);
						sysFile.setPath(relFilePath + "/" + fileId + "." + filerExt);
						sysFile.setExt("." + filerExt);

						FileExt fileExt = fileUploadConfig.getFileExtMap().get(filerExt.toLowerCase()); // 设置minetype
						if (fileExt != null) {
							sysFile.setMineType(fileExt.getMineType());
						} else {
							sysFile.setMineType("Unkown");
						}
						sysFile.setDesc("");

						FileChannel fc = null; // 计算文件大小
						try {
							File f = new File(fileFullPath);
							if (f.exists() && f.isFile()) {
								FileInputStream fis = new FileInputStream(f);
								fc = fis.getChannel();
								long size = fc.size();
								sysFile.setTotalBytes(size);
								fis.close();
							} else {
								logger.debug("file not exist!");
							}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							if (null != fc) {
								try {
									fc.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
						sysFile.setNewFname(fileId + "." + filerExt); // 新文件名字
						sysFileManager.create(sysFile);
						mail.getInfMailFiles().add(sysFile);
					}
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			saveAttachMent((Part) part.getContent(), mail);
		}
	}

	/**
	 * 文件拷贝
	 * 
	 * @param is
	 * @param os
	 * @throws IOException
	 */
	public void copy(InputStream is, OutputStream os) throws IOException {
		byte[] bytes = new byte[1024];
		int len = 0;
		while ((len = is.read(bytes)) != -1) {
			os.write(bytes, 0, len);
		}
		if (os != null)
			os.close();
		if (is != null)
			is.close();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return outMailManager;
	}

	/**
	 * 过滤字符串中的四字节码的表情，如新浪评论表情
	 * 
	 * @param content
	 * @return
	 */
	public String removeFourChar(String content) {
		byte[] conbyte = content.getBytes();
		for (int i = 0; i < conbyte.length; i++) {
			if ((conbyte[i] & 0xF8) == 0xF0) {
				for (int j = 0; j < 4; j++) {
					conbyte[i + j] = 0x30;
				}
				i += 3;
			}
		}
		content = new String(conbyte);
		return content.replaceAll("0000", "");
	}

}
