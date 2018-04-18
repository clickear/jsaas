package com.redxun.oa.mail.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.oa.mail.entity.InnerMail;
import com.redxun.oa.mail.entity.MailBox;
import com.redxun.oa.mail.entity.MailFolder;
import com.redxun.oa.mail.manager.InnerMailManager;
import com.redxun.oa.mail.manager.MailBoxManager;
import com.redxun.oa.mail.manager.MailFolderManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.manager.SysFileManager;
import com.redxun.sys.org.manager.OsUserManager;

/**
 * 内部邮件Controller
 * 
 * @author zwj
 *  用途：处理对内部邮件相关操作的请求映射
 */

@Controller
@RequestMapping("/oa/mail/innerMail/")
public class InnerMailController extends TenantListController {
	@Resource
	InnerMailManager innerMailManager;

	@Resource
	SysFileManager sysFileManager;

	@Resource
	OsUserManager osUserManager;

	@Resource
	MailFolderManager mailFolderManager;

	@Resource
	MailBoxManager mailBoxManager;

	/**
	 * 根据内部邮件Id删除对应的邮件
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
				innerMailManager.delete(id); // 删除内部邮件实体
			}
		}
		return new JsonResult(true, "成功删除！");
	}

	/**
	 * 查看邮件信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		String folderId=request.getParameter("folderId");
		InnerMail innerMail = null;
		MailBox mailBox=null;
		List<SysFile> attach = new ArrayList<SysFile>();         //附件列表
		if (StringUtils.isNotEmpty(pkId)) {
			innerMail = innerMailManager.get(pkId);
			if(StringUtils.isNotEmpty(folderId)){
				mailBox = mailBoxManager.getMailBoxByInnerMailIdAndFolderId(pkId, folderId); // 根据邮件Id和文件夹Id获取内部邮件收件箱实体
				if(!MBoolean.YES.name().equals(mailBox.getIsRead())){    //如果该邮件未读
					mailBox.setIsRead(MBoolean.YES.name()); // 设为邮件的阅读标识为已读
					mailBoxManager.update(mailBox);
				}
			}
			String attachIds = innerMail.getFileIds(); // 获取邮件的附件Id列表
			String[] fileIds = {};
			if (StringUtils.isNotEmpty(attachIds)) {
				fileIds = attachIds.split(",");
				for (int i = 0; i < fileIds.length; i++) {
					attach.add(sysFileManager.get(fileIds[i])); // 将附件Sys实体放入集合
				}
			} else
				attach = null;
		} else {
			innerMail = new InnerMail();
		}
		return getPathView(request).addObject("innerMail", innerMail).addObject("attach", attach);
	}

	/**
	 * 获取内部邮件收件箱目录的未读邮件
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getgUnreadReceiveFolderInnerMail")
	@ResponseBody
	public JsonResult getgUnreadReceiveFolderInnerMail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String folderId=request.getParameter("folderId");
		Long num = mailBoxManager.getUnreadReceiveFolderInnerMail(ContextUtil.getCurrentUserId(), folderId); // 获取收件箱未读邮件数量
		String unReadNum = String.valueOf(num);
		return new JsonResult(true,"获取未读信息",unReadNum);
	}
	
	/**
	 * 编辑邮件信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		String operation = request.getParameter("operation"); // 获取邮件操作的名称，操作包括（回复，转发）
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		InnerMail innerMail = null;
		String fileIds = "";
		String fileNames = "";
		if (StringUtils.isNotEmpty(pkId)) { // 如果邮件Id不为空
			innerMail = innerMailManager.get(pkId);
			innerMail.setUserId(ContextUtil.getCurrentUserId());
			String senderId = innerMail.getSenderId();
			String sender = innerMail.getSender();
			innerMail.setSender(ContextUtil.getCurrentUser().getFullname());
			innerMail.setSenderId(ContextUtil.getCurrentUserId());
			fileIds = innerMail.getFileIds(); // 获取附件Id列表
			fileNames = innerMail.getFileNames(); // 获取附件名称列表
			if ("true".equals(forCopy)) {
				innerMail.setMailId(null);
			}
			if ("reply".equals(operation)) { // 回复
				innerMail.setRecIds(senderId);
				innerMail.setRecNames(sender);
				innerMail.setCcIds("");
				innerMail.setCcNames("");
				innerMail.setSubject("Re:" + innerMail.getSubject()); // 设置邮件主题
			}
			if ("transmit".equals(operation)) { // 转发
				innerMail.setRecIds("");
				innerMail.setRecNames("");
				innerMail.setCcIds("");
				innerMail.setCcNames("");
				innerMail.setSubject("Fw:" + innerMail.getSubject()); // 设置邮件主题
			}
		} else {
			innerMail = new InnerMail();
			innerMail.setUserId(ContextUtil.getCurrentUserId());
			innerMail.setSenderId(ContextUtil.getCurrentUserId());
			innerMail.setSender(ContextUtil.getCurrentUser().getFullname());
		}
		return getPathView(request).addObject("innerMail", innerMail).addObject("fileIds", fileIds).addObject("fileNames", fileNames);
	}

	/**
	 * 根据folderId获取对应文件夹下面的邮件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getInnerMailByFolderId")
	@ResponseBody
	public JsonPageResult<InnerMail> getInnerMailByFolderId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String folderId = request.getParameter("folderId"); // 获取当前文件夹Id
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		List<InnerMail> list = innerMailManager.getInnerMailByFolderId(folderId, queryFilter); // 获取对应文件夹的内部邮件
		JsonPageResult<InnerMail> result = new JsonPageResult<InnerMail>(list, queryFilter.getPage().getTotalItems());
		return result;
	}
	/**
	 * 获取当前用户的门户的内部邮件显示
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getPortalInMail")
	@ResponseBody
	public JsonPageResult<InnerMail> getPortalInMail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ContextUtil.getCurrentUserId(); // 当前用户Id
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		List<InnerMail> list = innerMailManager.getInnerMailByUserId(userId, queryFilter,pageSize);  //获取当前用户的内部邮件
		JsonPageResult<InnerMail> result = new JsonPageResult<InnerMail>(list, queryFilter.getPage().getTotalItems());
		return result;

	}

	/**
	 * 发送内部邮件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sendMail")
	@ResponseBody
	public JsonResult sendMail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String data = request.getParameter("data"); // 获取表格数据
		String urge=request.getParameter("urge");
		InnerMail innerMail = (InnerMail) JSONUtil.json2Bean(data, InnerMail.class); // 将json转成实体类
		innerMail.setMailId(idGenerator.getSID());
		String operation=request.getParameter("operation");
		if("reply".equals(operation)){
			String oldMailId=request.getParameter("oldMailId");
			MailBox mailBox=mailBoxManager.getByUserIdAndMailId(ContextUtil.getCurrentUserId(),oldMailId);
			mailBox.setReply(MBoolean.YES.name());
			mailBoxManager.update(mailBox);
		}

		Set<MailBox> mailBoxs = new HashSet<MailBox>();
		if (StringUtils.isNotEmpty(innerMail.getRecIds())) { // 处理收件人 如果收件人Id不为空，则设置收件人姓名列表
			String[] recIds = innerMail.getRecIds().split(",");
			for (int i = 0; i < recIds.length; i++) { // 遍历收件人Id 获取收件人姓名列表
				if (StringUtils.isNotEmpty(recIds[i])) { // 如果不为空
					if (i == 0) {
						innerMail.setRecNames(osUserManager.get(recIds[i]).getFullname()); // 获取当前收件人ID的用户名
						Long folderNum = mailFolderManager.getInnerMailFolderNum(recIds[i]); // 获取当前收件人的邮箱文件夹目录
						if (folderNum < 5L) // 数量小于5
							mailFolderManager.createInnerMailFolder(idGenerator.getSID(), recIds[i]); // 创建初始化的5个基本目录
						MailBox mailBox = new MailBox(); // 创建收件人消息
						mailBox.setBoxId(idGenerator.getSID());
						mailBox.setInnerMail(innerMail); // 设置对应内部邮件
						mailBox.setMailFolder(mailFolderManager.getInnerReceiveMailFolderByUserId(recIds[i])); // 设置对应邮件目录文件夹
						mailBox.setUserId(recIds[i]); // 设置用户Id
						mailBox.setIsDel(MBoolean.NO.name()); // 设置删除标记
						mailBox.setIsRead(MBoolean.NO.name()); // 设置阅读标记
						mailBox.setReply(MBoolean.NO.name()); // 设置回复标记
						mailBoxs.add(mailBox);
						continue;
					}
					innerMail.setRecNames(innerMail.getRecNames() + "," + osUserManager.get(recIds[i]).getFullname());
/*					Long folderNum = mailFolderManager.getInnerMailFolderNum(recIds[i]); // 获取当前收件人的邮箱文件夹目录
				    if (folderNum < 5L) 
						mailFolderManager.createInnerMailFolder(idGenerator.getSID(), recIds[i]);*/
					MailBox mailBox = new MailBox(); // 创建收件人消息
					mailBox.setBoxId(idGenerator.getSID());
					mailBox.setInnerMail(innerMail); // 设置对应内部邮件
					mailBox.setMailFolder(mailFolderManager.getInnerReceiveMailFolderByUserId(recIds[i])); // 设置对应邮件目录文件夹
					mailBox.setUserId(recIds[i]); // 设置用户Id
					mailBox.setIsDel(MBoolean.NO.name()); // 设置删除标记
					mailBox.setIsRead(MBoolean.NO.name()); // 设置阅读标记
					mailBox.setReply(MBoolean.NO.name()); // 设置回复标记
					mailBoxs.add(mailBox);
				}
			}
		}
		if (StringUtils.isNotEmpty(innerMail.getCcIds())) { // 处理抄送人，如果抄送人Id不为空，则设置抄送人姓名列表
			String[] ccIds = innerMail.getCcIds().split(",");
			String[] recIds = innerMail.getRecIds().split(",");
			for (int i = 0; i < ccIds.length; i++) { // 遍历抄送人Id，获取抄送人姓名列表
				if (StringUtils.isNotEmpty(ccIds[i])) { // 如果不为空
					boolean repeatFlag = false;
					for (int j = 0; j < recIds.length; j++) { // 判断是收件人和抄送人中是否有重复的用户
						if (ccIds[i].equals(recIds[j]))
							repeatFlag = true;
					}
					if (repeatFlag) // 如果有过滤重复用户Id（收件人和抄送人重复的用户）
						continue;
					if (i == 0) {
						innerMail.setCcNames(osUserManager.get(ccIds[i]).getFullname()); // 获取当前抄送人ID的用户名
						Long folderNum = mailFolderManager.getInnerMailFolderNum(ccIds[i]); // 获取当前抄送人的邮箱文件夹目录
						if (folderNum < 5L) // 数量小于5
							mailFolderManager.createInnerMailFolder(idGenerator.getSID(), ccIds[i]); // 创建初始化的5个基本目录
						MailBox mailBox = new MailBox(); // 创建抄送人消息
						mailBox.setBoxId(idGenerator.getSID());
						mailBox.setInnerMail(innerMail); // 设置对应内部邮件
						mailBox.setMailFolder(mailFolderManager.getInnerReceiveMailFolderByUserId(ccIds[i])); // 设置对应邮件目录文件夹
						mailBox.setUserId(ccIds[i]); // 设置用户Id
						mailBox.setIsDel(MBoolean.NO.name()); // 设置删除标记
						mailBox.setIsRead(MBoolean.NO.name()); // 设置阅读标记
						mailBox.setReply(MBoolean.NO.name()); // 设置回复标记
						mailBoxs.add(mailBox);
						continue;
					}
					innerMail.setCcNames(innerMail.getCcNames() + "," + osUserManager.get(ccIds[i]).getFullname());
					Long folderNum = mailFolderManager.getInnerMailFolderNum(ccIds[i]); // 获取当前抄送人的邮箱文件夹目录
					if (folderNum < 5L) // 数量小于5
						mailFolderManager.createInnerMailFolder(idGenerator.getSID(), ccIds[i]); // 创建初始化的5个基本目录
					MailBox mailBox = new MailBox(); // 创建抄送人消息
					mailBox.setBoxId(idGenerator.getSID());
					mailBox.setInnerMail(innerMail); // 设置对应内部邮件
					mailBox.setMailFolder(mailFolderManager.getInnerReceiveMailFolderByUserId(ccIds[i])); // 设置对应邮件目录文件夹
					mailBox.setUserId(ccIds[i]); // 设置用户Id
					mailBox.setIsDel(MBoolean.NO.name()); // 设置删除标记
					mailBox.setIsRead(MBoolean.NO.name()); // 设置阅读标记
					mailBox.setReply(MBoolean.NO.name()); // 设置回复标记
					mailBoxs.add(mailBox);
				}
			}
		}
		MailBox mailBox = new MailBox(); // 设置发件人发件箱信息
		mailBox.setBoxId(idGenerator.getSID());
		mailBox.setInnerMail(innerMail);
		mailBox.setMailFolder(mailFolderManager.getInnerMailFolderByUserIdAndType(ContextUtil.getCurrentUserId(), MailFolder.TYPE_SENDER_FOLDER)); // 设置发件箱信息
		mailBox.setUserId(ContextUtil.getCurrentUserId());
		mailBox.setIsDel(MBoolean.NO.name()); // 设置删除标记
		mailBox.setIsRead(MBoolean.NO.name()); // 设置阅读标记
		mailBox.setReply(MBoolean.NO.name()); // 设置回复标记
		mailBoxs.add(mailBox);
		innerMail.setSender(ContextUtil.getCurrentUser().getFullname());
		innerMail.setSenderTime(new Date());
		innerMail.setDelFlag(MBoolean.NO.name());
		innerMail.setStatus((short) 1);
		innerMail.setUrge(urge);
		innerMail.setMailBoxs(mailBoxs);
		if(StringUtils.isEmpty(innerMail.getContent())){
			innerMail.setContent("无");
		}
		innerMailManager.create(innerMail);
		return new JsonResult(true, "发送内部邮件成功");
	}

	/**
	 * 移动邮件到对应的文件夹
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("moveInnerMailToFolder")
	@ResponseBody
	public JsonResult moveInnerMailToFolder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String targetFolder = request.getParameter("target"); // 目标文件夹
		String uIds = request.getParameter("ids"); // 邮件Id列表
		String folderId = request.getParameter("to"); // 目标所在folder
		String tmpfolderId = request.getParameter("nowFolderId"); // 原来所在folder
		String[] ids = {};
		if (StringUtils.isNotEmpty(uIds)) {
			ids = uIds.split(",");
		}
		if ("del".equals(targetFolder)) { // 移动到垃圾箱
			for (String id : ids) {
				MailBox tmpmailBox = mailBoxManager.getMailBoxByInnerMailIdAndFolderId(id, tmpfolderId); // 根据邮件Id和文件夹Id获取邮件
				tmpmailBox.setMailFolder(mailFolderManager.getInnerMailFolderByUserIdAndType(ContextUtil.getCurrentUserId(), MailFolder.TYPE_DEL_FOLDER)); // 将邮件的文件夹设为垃圾箱目录
				mailBoxManager.update(tmpmailBox);
			}
			return new JsonResult(true, "邮件已放入垃圾箱！");
		}
		if ("draft".equals(targetFolder)) { // 移动到草稿箱
			for (String id : ids) {
				MailBox tmpmailBox = mailBoxManager.getMailBoxByInnerMailIdAndFolderId(id, tmpfolderId); // 根据邮件Id和文件夹Id获取邮件
				tmpmailBox.setMailFolder(mailFolderManager.getInnerMailFolderByUserIdAndType(ContextUtil.getCurrentUserId(), MailFolder.TYPE_DRAFT_FOLDER)); // 将邮件的文件夹设为草稿目录
				mailBoxManager.update(tmpmailBox);
			}
			return new JsonResult(true, "邮件已存到草稿箱！");
		}
		if ("all".equals(targetFolder)) { // 移动到任何文件夹目录
			for (String id : ids) {
				MailBox tmpmailBox = mailBoxManager.getMailBoxByInnerMailIdAndFolderId(id, tmpfolderId); // 根据邮件Id和文件夹Id获取邮件
				tmpmailBox.setMailFolder(mailFolderManager.getInnerMailFolderByUserIdAndFolderId(ContextUtil.getCurrentUserId(), folderId)); // 将邮件的文件夹设为目标文件夹
				mailBoxManager.update(tmpmailBox);
			}
		}
		return new JsonResult(true, "内部邮件移动成功！");
	}

	/**
	 * 根据邮件Id获取对应邮件的内容
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getInnerMailContentByMailId")
	@ResponseBody
	public ModelAndView getInnerMailContentByMailId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String mailId = request.getParameter("mailId");
		String content = innerMailManager.getInnerMailContentByMailId(mailId); // 根据邮件Id获取邮件内容
		request.setAttribute("mailContent", content);
		return new ModelAndView("/oa/mail/mailContent.jsp");
	}

	/**
	 * 将内部邮件保存到草稿箱
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("saveInnerMailToDraftFolder")
	@ResponseBody
	public JsonResult saveInnerMailToDraftFolder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String data = request.getParameter("data"); // 获取表格数据
		String urge = request.getParameter("urge"); // 获取邮件重要程度参数
		InnerMail innerMail = (InnerMail) JSONUtil.json2Bean(data, InnerMail.class); // 将json转成实体类
		innerMail.setMailId(idGenerator.getSID());
		if(StringUtils.isEmpty(innerMail.getSubject()))  //如果邮件的主题为空
			innerMail.setSubject("无");
		if(StringUtils.isEmpty(innerMail.getContent())) //如果邮件的内容为空
			innerMail.setContent("无");
		if (StringUtils.isEmpty(innerMail.getFileIds())) { // 如果附件fileIds 为空
			innerMail.setFileIds(null);
			innerMail.setFileNames(null);
		}else{
			String []fileIds=innerMail.getFileIds().split(",");
			String fileNames="";
			for (int i = 0; i < fileIds.length; i++) {
				if(i==0){
					fileNames=sysFileManager.get(fileIds[i]).getFileName();
					continue;
				}
			    fileNames=fileNames+","+sysFileManager.get(fileIds[i]).getFileName();
			}
			innerMail.setFileNames(fileNames);
		}
		if (StringUtils.isNotEmpty(innerMail.getRecIds())) { // 处理收件人 如果收件人Id不为空，则设置收件人姓名列表
			String[] recIds = innerMail.getRecIds().split(",");
			for (int i = 0; i < recIds.length; i++) { // 遍历收件人Id 获取收件人姓名列表
				if (StringUtils.isNotEmpty(recIds[i])) { // 如果不为空
					if (i == 0) {
						innerMail.setRecNames(osUserManager.get(recIds[i]).getFullname()); // 获取当前收件人ID的用户名
						continue;
					}
					innerMail.setRecNames(innerMail.getRecNames() + "," + osUserManager.get(recIds[i]).getFullname());
				}
			}
		}
		else{
			innerMail.setRecIds("无");
			innerMail.setRecNames("无");
		}
		if (StringUtils.isNotEmpty(innerMail.getCcIds())) { // 处理抄送人，如果抄送人Id不为空，则设置抄送人姓名列表
			String[] ccIds = innerMail.getCcIds().split(",");
			String[] recIds = innerMail.getRecIds().split(",");
			for (int i = 0; i < ccIds.length; i++) { // 遍历抄送人Id，获取抄送人姓名列表
				if (StringUtils.isNotEmpty(ccIds[i])) { // 如果不为空
					boolean repeatFlag = false;
					for (int j = 0; j < recIds.length; j++) { // 判断是收件人和抄送人中是否有重复的用户
						if (ccIds[i].equals(recIds[j]))
							repeatFlag = true;
					}
					if (repeatFlag) // 如果有过滤重复用户Id（收件人和抄送人重复的用户）
						continue;
					if (i == 0) {
						innerMail.setCcNames(osUserManager.get(ccIds[i]).getFullname()); // 获取当前抄送人ID的用户名
						continue;
					}
					innerMail.setCcNames(innerMail.getCcNames() + "," + osUserManager.get(ccIds[i]).getFullname());
				}
			}
		}
		else{
			innerMail.setCcIds(null);
			innerMail.setCcNames(null);
		}
		innerMail.setSender(ContextUtil.getCurrentUser().getFullname());
		innerMail.setSenderTime(new Date());
		innerMail.setDelFlag(MBoolean.NO.name());
		innerMail.setStatus((short) 0);
		innerMail.setUrge(urge);
		Set<MailBox> mailBoxs=new HashSet<MailBox>();
		MailBox mailBox = new MailBox(); // 设置内部邮件箱收件箱
		mailBox.setBoxId(idGenerator.getSID());
		mailBox.setInnerMail(innerMail);
		mailBox.setMailFolder(mailFolderManager.getInnerMailFolderByUserIdAndType(ContextUtil.getCurrentUserId(), MailFolder.TYPE_DRAFT_FOLDER)); // 设置草稿箱信息
		mailBox.setUserId(ContextUtil.getCurrentUserId());
		mailBox.setIsDel(MBoolean.NO.name()); // 设置删除标记
		mailBox.setIsRead(MBoolean.NO.name()); // 设置阅读标记
		mailBox.setReply(MBoolean.NO.name()); // 设置回复标记
		mailBoxs.add(mailBox);
		innerMail.setMailBoxs(mailBoxs);
		innerMailManager.create(innerMail);
		return new JsonResult(true, "成功保存到草稿箱");
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return innerMailManager;
	}

}
