package com.redxun.mobile.core.controller;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.constants.MBoolean;
import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.QueryParam;
import com.redxun.core.seq.IdGenerator;
import com.redxun.core.util.StringUtil;
import com.redxun.hr.core.entity.HrDutyRegister;
import com.redxun.hr.core.manager.HrDutyRegisterManager;
import com.redxun.mobile.model.ListModel;
import com.redxun.mobile.util.CommonUtil;
import com.redxun.oa.info.entity.InfInbox;
import com.redxun.oa.info.entity.InfInnerMsg;
import com.redxun.oa.info.entity.InsNews;
import com.redxun.oa.info.manager.InfInboxManager;
import com.redxun.oa.info.manager.InfInnerMsgManager;
import com.redxun.oa.info.manager.InsNewsManager;
import com.redxun.oa.mail.entity.InnerMail;
import com.redxun.oa.mail.entity.MailBox;
import com.redxun.oa.mail.entity.MailFolder;
import com.redxun.oa.mail.manager.InnerMailManager;
import com.redxun.oa.mail.manager.MailBoxManager;
import com.redxun.oa.mail.manager.MailFolderManager;
import com.redxun.org.api.service.UserService;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.GenericController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.core.manager.SysFileManager;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.manager.OsDimensionManager;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsUserManager;

@Controller
@RequestMapping("/mobile/oa/")
public class OaController extends GenericController {

	@Resource
	private HrDutyRegisterManager hrDutyRegisterManager;

	@Resource
	private InsNewsManager insNewsManager;

	@Resource
	private InfInnerMsgManager infInnerMsgManager;

	@Resource
	private InnerMailManager innerMailManager;
	@Resource
	private MailFolderManager mailFolderManager;
	@Resource
	private InfInboxManager infInboxManager;
	@Resource
	private UserService userService;
	@Resource
	private OsUserManager osUserManager;
	@Resource
	MailBoxManager mailBoxManager;
	@Resource
	IdGenerator idGenerator;
	@Resource
	SysFileManager sysFileManager;
	@Resource
	OsDimensionManager osDimensionManager;
	@Resource
	OsGroupManager osGroupManager;

	// 获取维度。
	@RequestMapping("getDimen")
	@ResponseBody
	public ListModel getDimen(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		// String tenantId=ContextUtil.getCurrentTenantId();
		// String dimId=request.getParameter("dimId");
		// queryFilter.addFieldParam("tenantId", tenantId);
		List list = osDimensionManager.getAll(queryFilter);
		return CommonUtil.getListModel(list, queryFilter.getPage()
				.getTotalItems());
	}

	// 获取部门通过维度或者上级部门。
	@RequestMapping("getGroup")
	@ResponseBody
	public ListModel getGroup(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		// String tenantId=ContextUtil.getCurrentTenantId();
		String dimId = request.getParameter("dimId");
		String groupId = request.getParameter("groupId");
		QueryFilter filter = new QueryFilter();
		List userList=new ArrayList();
		if (StringUtils.isNotEmpty(groupId)) {
			filter.addFieldParam("parentId", groupId);
			userList=osUserManager.getByGroupId(groupId);;
		} else if (StringUtils.isNotEmpty(dimId)) {
			filter.addFieldParam("osDimension.dimId", dimId);
			filter.addFieldParam("parentId", "0");
			filter.addFieldParam("tenantId", ContextUtil.getCurrentTenantId());
		}
		List groupList = osGroupManager.getAll(filter);
		groupList.addAll(userList);
		return CommonUtil.getListModel(groupList, queryFilter.getPage()
				.getTotalItems());
	}
	
	// 获取部门通过维度或者上级部门。
	@RequestMapping("getGroupWithoutUser")
	@ResponseBody
	public ListModel getGroupWithoutUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		// String tenantId=ContextUtil.getCurrentTenantId();
		String dimId = request.getParameter("dimId");
		String groupId = request.getParameter("groupId");
		QueryFilter filter = new QueryFilter();
		if (StringUtils.isNotEmpty(groupId)) {
			filter.addFieldParam("parentId", groupId);
		} else if (StringUtils.isNotEmpty(dimId)) {
			filter.addFieldParam("osDimension.dimId", dimId);
			filter.addFieldParam("parentId", "0");
			filter.addFieldParam("tenantId", ContextUtil.getCurrentTenantId());
		}
		List groupList = osGroupManager.getAll(filter);
		return CommonUtil.getListModel(groupList, queryFilter.getPage()
				.getTotalItems());
	}
	
	

	// 通过groupId查询用户。
	@RequestMapping("getUsers")
	@ResponseBody
	public ListModel getUsers(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		String groupId = request.getParameter("groupId");
		List list = osUserManager.getByGroupId(groupId);
		return CommonUtil.getListModel(list, queryFilter.getPage()
				.getTotalItems());
	}

	// 通过名称查询用户。
	@RequestMapping("getUsersSearch")
	@ResponseBody
	public ListModel getUsersSearch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		String tenantId = ContextUtil.getCurrentTenantId();
		queryFilter.addFieldParam("tenantId", tenantId);
		List list = osUserManager.getByFilter(queryFilter);
		return CommonUtil.getListModel(list, queryFilter.getPage()
				.getTotalItems());
	}
	
	// 通过名称查询部门。
	@RequestMapping("getGroupsSearch")
	@ResponseBody
	public ListModel getGroupsSearch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		String tenantId = ContextUtil.getCurrentTenantId();
		queryFilter.addFieldParam("tenantId", tenantId);
		List<OsGroup> list = osGroupManager.getAll(queryFilter);
		return CommonUtil.getListModel(list, queryFilter.getPage()
				.getTotalItems());
	}

	@RequestMapping("signIn")
	@ResponseBody
	public JsonResult signIn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String addressInfo = request.getParameter("addressInfo");
		JsonResult signInResult = new JsonResult();
		if (StringUtils.isNotBlank(addressInfo)) {
			JSONObject addressJson = JSON.parseObject(addressInfo);
			signInResult = hrDutyRegisterManager.doSignInService(addressJson);
		}
		return new JsonResult(true, signInResult.getMessage(),signInResult);

	}
	

	@RequestMapping("signOut")
	@ResponseBody
	public JsonResult signOut(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String addressInfo = request.getParameter("addressInfo");
		JSONObject addressJson = JSON.parseObject(addressInfo);
		JsonResult signOutResult = hrDutyRegisterManager
				.doSignOutService(addressJson);
		return new JsonResult(true, signOutResult.getMessage());

	}

	@RequestMapping("isShowSignButton")
	@ResponseBody
	public JsonResult isShowSignButton(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonResult buttonStatusResult = hrDutyRegisterManager
				.isShowSignButton();
		return buttonStatusResult;
	}

	@RequestMapping("getSignHistory")
	@ResponseBody
	public JsonResult getSignHistory(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String searchDate = request.getParameter("date");
		if(StringUtil.isEmpty(searchDate)){
			return new JsonResult(false, "没有选择日期");
		}
		List<HrDutyRegister> list = hrDutyRegisterManager
				.getSignHistory(searchDate);
		return new JsonResult(true, "", list);
	}

	@RequestMapping("getUserMonthSignRecord")
	@ResponseBody
	public JsonResult getUserMonthSignRecord(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String searchDate = request.getParameter("date");
		List<HrDutyRegister> list = hrDutyRegisterManager
				.getUserMonthSignRecord(searchDate);
		return new JsonResult(true, "", list);
	}

	// 获取公司公告
	@RequestMapping("getAllNews")
	@ResponseBody
	public JsonResult getAllNews(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addSortParam("updateTime", "DESC");
		List<InsNews> insNews = insNewsManager.getAll(queryFilter);
		return new JsonResult(true, "获取成功", insNews);
	}
	

	// 增加阅读次数
	@RequestMapping("addNewsReadTime")
	@ResponseBody
	public JsonResult addNewsReadTime(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String newsId = request.getParameter("newsId");
		if (StringUtils.isNotBlank(newsId)) {
			InsNews insNews = insNewsManager.get(newsId.replace("\"", ""));
			insNews.setReadTimes(insNews.getReadTimes() + 1);
			insNewsManager.update(insNews);
		}
		return new JsonResult(true, "增加阅读次数成功");
	}
	
	// 发送内部短消息	
	@RequestMapping(value = "sendMsg", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult sendMsg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String userId=request.getParameter("userId");
		String groupId=request.getParameter("groupId");
		String content = request.getParameter("content");//Msg内容
		String linkMsg = request.getParameter("linkMsg");//消息携带连接
		InfInnerMsg infInnerMsg =infInnerMsgManager.sendMessage(userId, groupId, content, linkMsg,false);			
		String msg = getMessage("infInnerMsg.created",new Object[] { infInnerMsg.getIdentifyLabel() },"消息成功发送!");
		return new JsonResult(true, msg);
	}
	
	
	
	// 获取所有内部消息
	@RequestMapping("getAllMsgList")
	@ResponseBody
	public JsonPageResult<InfInbox> getAllMsgList(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("recType", "REC");
		queryFilter.addFieldParam("recUserId", userId);
		List<InfInbox> list = new ArrayList<InfInbox>(0);
		if (StringUtils.isNotBlank(userId)) {
			list = infInboxManager.getAll(queryFilter);
			return new JsonPageResult<InfInbox>(true, list, queryFilter
					.getPage().getTotalItems(), "获取已读消息成功");
		}
		return new JsonPageResult<InfInbox>(false, list, queryFilter.getPage()
				.getTotalItems(), "没有获取到已读消息");
	}

	// 获取已读内部消息
	@RequestMapping("getReadedMsgList")
	@ResponseBody
	public JsonPageResult<InfInbox> getReadedMsgList(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("recUserId", userId);
		queryFilter.addFieldParam("isRead", "yes");
		List<InfInbox> list = new ArrayList<InfInbox>(0);
		if (StringUtils.isNotBlank(userId)) {
			list = infInboxManager.getAll(queryFilter);
			return new JsonPageResult<InfInbox>(true, list, queryFilter
					.getPage().getTotalItems(), "获取已读消息成功");
		}
		return new JsonPageResult<InfInbox>(false, list, queryFilter.getPage()
				.getTotalItems(), "没有获取到已读消息");
	}

	// 获取未读内部消息
	@RequestMapping("getUnreadMsgList")
	@ResponseBody
	public JsonPageResult<InfInbox> getUnreadMsgList(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("recUserId", userId);
		queryFilter.addFieldParam("isRead", "no");
		List<InfInbox> list = new ArrayList<InfInbox>(0);
		if (StringUtils.isNotBlank(userId)) {
			list = infInboxManager.getAll(queryFilter);
			return new JsonPageResult<InfInbox>(true, list, queryFilter
					.getPage().getTotalItems(), "获取未读消息成功");
		}
		return new JsonPageResult<InfInbox>(false, list, queryFilter.getPage()
				.getTotalItems(), "没有获取到未读消息");
	}

	/**
	 * 更新内部消息已读状态
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("updateStatus")
	@ResponseBody
	public void updateStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String uId = request.getParameter("pkId");
		String userId = ContextUtil.getCurrentUserId();
		if (StringUtils.isNotEmpty(uId)) {
			InfInbox inbox = infInboxManager.getByMsgId(uId, userId);
			inbox.setIsRead("yes");
			infInboxManager.update(inbox);
		}
	}

	// 获取已发内部消息
	@RequestMapping("getSendedMsgList")
	@ResponseBody
	public JsonPageResult<InfInbox> getSendedMsgList(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		String sender = userService.getByUserId(userId).getFullname(); // 发信人名字
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		List<InfInbox> list = infInboxManager.getInboxBySender(sender, userId,
				queryFilter);
		return new JsonPageResult<InfInbox>(true, list, queryFilter.getPage()
				.getTotalItems(), "获取已发消息成功");
	}

	// 获得当前用户的所有内部邮件文件夹
	@RequestMapping("getUserMailFolder")
	@ResponseBody
	public JsonPageResult<MailFolder> getUserMailFolder(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("userId", userId);
		queryFilter.addFieldParam("type", QueryParam.OP_NOT_EQUAL,
				"ROOT-FOLDER");
		List<MailFolder> MailFolder = mailFolderManager.getAll(queryFilter);
		return new JsonPageResult<MailFolder>(true, MailFolder, queryFilter
				.getPage().getTotalItems(), "获取文件夹成功");
	}

	// 获取内部邮件收件箱的内容
	@RequestMapping("getRecieveMailList")
	@ResponseBody
	public JsonPageResult<InnerMail> getRecieveMailList(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addSortParam("createTime", "desc");
		queryFilter.addSortParam("status", "1");
		List<InnerMail> innerMails = innerMailManager.getInnerMailByUserId(
				userId, queryFilter, 10);
		return new JsonPageResult<InnerMail>(true, innerMails,
				innerMails.size(), "获取内部邮件收件箱成功");
	}

	// 获取内部邮件发件箱的内容
	@RequestMapping("getSendedMailList")
	@ResponseBody
	public JsonPageResult<InnerMail> getSendedMailList(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("senderId", userId);
		queryFilter.addSortParam("createTime", "desc");
		List<InnerMail> innerMails = innerMailManager.getAll(queryFilter);
		return new JsonPageResult<InnerMail>(true, innerMails,
				innerMails.size(), "获取内部邮件发件箱成功");
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
	public JsonResult getInnerMailByFolderId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String folderId = request.getParameter("folderId"); // 获取当前文件夹Id
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		List<InnerMail> list = innerMailManager.getInnerMailByFolderId(
				folderId, queryFilter); // 获取对应文件夹的内部邮件
		return new JsonResult(true, "获取成功", list);
	}

	// 获取内部邮件草稿箱的内容
	@RequestMapping("getDraftMailList")
	@ResponseBody
	public JsonPageResult<InnerMail> getDraftMailList(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		// 通过userId和type获取垃圾箱
		queryFilter.addFieldParam("userId", userId);
		queryFilter.addFieldParam("type", "DRAFT-FOLDER");
		List<MailFolder> mailFolders = mailFolderManager.getAll(queryFilter);
		queryFilter = new QueryFilter();
		List<InnerMail> list = innerMailManager.getInnerMailByFolderId(
				mailFolders.get(0).getFolderId(), queryFilter); // 获取对应文件夹的内部邮件
		JsonPageResult<InnerMail> result = new JsonPageResult<InnerMail>(list,
				queryFilter.getPage().getTotalItems());
		return result;
	}

	// 获取内部邮件垃圾箱的内容
	@RequestMapping("getRecyclMailList")
	@ResponseBody
	public JsonPageResult<InnerMail> getRecyclMailList(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		// 通过userId和type获取垃圾箱
		queryFilter.addFieldParam("userId", userId);
		queryFilter.addFieldParam("type", "DEL-FOLDER");
		List<MailFolder> mailFolders = mailFolderManager.getAll(queryFilter);
		queryFilter = new QueryFilter();
		List<InnerMail> list = innerMailManager.getInnerMailByFolderId(
				mailFolders.get(0).getFolderId(), queryFilter); // 获取对应文件夹的内部邮件
		JsonPageResult<InnerMail> result = new JsonPageResult<InnerMail>(list,
				queryFilter.getPage().getTotalItems());
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
	public JsonResult sendMail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String data = request.getParameter("data"); // 获取表格数据
		String urge = request.getParameter("urge"); // 获取邮件重要程度参数
		InnerMail innerMail = (InnerMail) JSONUtil.json2Bean(data,
				InnerMail.class); // 将json转成实体类
		innerMail.setMailId(idGenerator.getSID());
		if (StringUtils.isEmpty(innerMail.getFileIds())) { // 如果附件fileIds 为空
			innerMail.setFileIds(null);
			innerMail.setFileNames(null);
		}

		Set<MailBox> mailBoxs = new HashSet<MailBox>();
		if (StringUtils.isNotEmpty(innerMail.getRecIds())) { // 处理收件人
																// 如果收件人Id不为空，则设置收件人姓名列表
			String[] recIds = innerMail.getRecIds().split(",");
			for (int i = 0; i < recIds.length; i++) { // 遍历收件人Id 获取收件人姓名列表
				if (StringUtils.isNotEmpty(recIds[i])) { // 如果不为空
					if (i == 0) {
						innerMail.setRecNames(osUserManager.get(recIds[i])
								.getFullname()); // 获取当前收件人ID的用户名
						Long folderNum = mailFolderManager
								.getInnerMailFolderNum(recIds[i]); // 获取当前收件人的邮箱文件夹目录
						if (folderNum < 5L) // 数量小于5
							mailFolderManager.createInnerMailFolder(
									idGenerator.getSID(), recIds[i]); // 创建初始化的5个基本目录
						MailBox mailBox = new MailBox(); // 创建收件人消息
						mailBox.setBoxId(idGenerator.getSID());
						mailBox.setInnerMail(innerMail); // 设置对应内部邮件
						mailBox.setMailFolder(mailFolderManager
								.getInnerReceiveMailFolderByUserId(recIds[i])); // 设置对应邮件目录文件夹
						mailBox.setUserId(recIds[i]); // 设置用户Id
						mailBox.setIsDel(MBoolean.NO.name()); // 设置删除标记
						mailBox.setIsRead(MBoolean.NO.name()); // 设置阅读标记
						mailBox.setReply(MBoolean.NO.name()); // 设置回复标记
						mailBoxs.add(mailBox);
						continue;
					}
					innerMail.setRecNames(innerMail.getRecNames() + ","
							+ osUserManager.get(recIds[i]).getFullname());
					/*
					 * Long folderNum =
					 * mailFolderManager.getInnerMailFolderNum(recIds[i]); //
					 * 获取当前收件人的邮箱文件夹目录 if (folderNum < 5L)
					 * mailFolderManager.createInnerMailFolder
					 * (idGenerator.getSID(), recIds[i]);
					 */
					MailBox mailBox = new MailBox(); // 创建收件人消息
					mailBox.setBoxId(idGenerator.getSID());
					mailBox.setInnerMail(innerMail); // 设置对应内部邮件
					mailBox.setMailFolder(mailFolderManager
							.getInnerReceiveMailFolderByUserId(recIds[i])); // 设置对应邮件目录文件夹
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
						innerMail.setCcNames(osUserManager.get(ccIds[i])
								.getFullname()); // 获取当前抄送人ID的用户名
						Long folderNum = mailFolderManager
								.getInnerMailFolderNum(ccIds[i]); // 获取当前抄送人的邮箱文件夹目录
						if (folderNum < 5L) // 数量小于5
							mailFolderManager.createInnerMailFolder(
									idGenerator.getSID(), ccIds[i]); // 创建初始化的5个基本目录
						MailBox mailBox = new MailBox(); // 创建抄送人消息
						mailBox.setBoxId(idGenerator.getSID());
						mailBox.setInnerMail(innerMail); // 设置对应内部邮件
						mailBox.setMailFolder(mailFolderManager
								.getInnerReceiveMailFolderByUserId(ccIds[i])); // 设置对应邮件目录文件夹
						mailBox.setUserId(ccIds[i]); // 设置用户Id
						mailBox.setIsDel(MBoolean.NO.name()); // 设置删除标记
						mailBox.setIsRead(MBoolean.NO.name()); // 设置阅读标记
						mailBox.setReply(MBoolean.NO.name()); // 设置回复标记
						mailBoxs.add(mailBox);
						continue;
					}
					innerMail.setCcNames(innerMail.getCcNames() + ","
							+ osUserManager.get(ccIds[i]).getFullname());
					Long folderNum = mailFolderManager
							.getInnerMailFolderNum(ccIds[i]); // 获取当前抄送人的邮箱文件夹目录
					if (folderNum < 5L) // 数量小于5
						mailFolderManager.createInnerMailFolder(
								idGenerator.getSID(), ccIds[i]); // 创建初始化的5个基本目录
					MailBox mailBox = new MailBox(); // 创建抄送人消息
					mailBox.setBoxId(idGenerator.getSID());
					mailBox.setInnerMail(innerMail); // 设置对应内部邮件
					mailBox.setMailFolder(mailFolderManager
							.getInnerReceiveMailFolderByUserId(ccIds[i])); // 设置对应邮件目录文件夹
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
		mailBox.setMailFolder(mailFolderManager
				.getInnerMailFolderByUserIdAndType(
						ContextUtil.getCurrentUserId(),
						MailFolder.TYPE_SENDER_FOLDER)); // 设置发件箱信息
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
		if (StringUtils.isEmpty(innerMail.getContent())) {
			innerMail.setContent("无");
		}
		innerMailManager.create(innerMail);
		return new JsonResult(true, "发送内部邮件成功");
	}

	/**
	 * 编辑邮件信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("editMail")
	@ResponseBody
	public JsonResult<InnerMail> editMail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
				if (innerMail.getSubject().startsWith("回复")) { // 设置邮件主题
					innerMail.setSubject(innerMail.getSubject());
				} else {
					innerMail.setSubject("回复:" + innerMail.getSubject());
				}

			}
			if ("transmit".equals(operation)) { // 转发
				innerMail.setRecIds("");
				innerMail.setRecNames("");
				innerMail.setCcIds("");
				innerMail.setCcNames("");
				innerMail.setSubject("转发:" + innerMail.getSubject()); // 设置邮件主题
			}
		} else {
			innerMail = new InnerMail();
			innerMail.setUserId(ContextUtil.getCurrentUserId());
			innerMail.setSenderId(ContextUtil.getCurrentUserId());
			innerMail.setSender(ContextUtil.getCurrentUser().getFullname());
		}
		return new JsonResult<InnerMail>(true, "", innerMail);
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
	public JsonResult moveInnerMailToFolder(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
				MailBox tmpmailBox = mailBoxManager
						.getMailBoxByInnerMailIdAndFolderId(id, tmpfolderId); // 根据邮件Id和文件夹Id获取邮件
				tmpmailBox.setMailFolder(mailFolderManager
						.getInnerMailFolderByUserIdAndType(
								ContextUtil.getCurrentUserId(),
								MailFolder.TYPE_DEL_FOLDER)); // 将邮件的文件夹设为垃圾箱目录
				mailBoxManager.update(tmpmailBox);
			}
			return new JsonResult(true, "邮件已放入垃圾箱！");
		}
		if ("draft".equals(targetFolder)) { // 移动到草稿箱
			for (String id : ids) {
				MailBox tmpmailBox = mailBoxManager
						.getMailBoxByInnerMailIdAndFolderId(id, tmpfolderId); // 根据邮件Id和文件夹Id获取邮件
				tmpmailBox.setMailFolder(mailFolderManager
						.getInnerMailFolderByUserIdAndType(
								ContextUtil.getCurrentUserId(),
								MailFolder.TYPE_DRAFT_FOLDER)); // 将邮件的文件夹设为草稿目录
				mailBoxManager.update(tmpmailBox);
			}
			return new JsonResult(true, "邮件已存到草稿箱！");
		}
		if ("all".equals(targetFolder)) { // 移动到任何文件夹目录
			for (String id : ids) {
				MailBox tmpmailBox = mailBoxManager
						.getMailBoxByInnerMailIdAndFolderId(id, tmpfolderId); // 根据邮件Id和文件夹Id获取邮件
				tmpmailBox.setMailFolder(mailFolderManager
						.getInnerMailFolderByUserIdAndFolderId(
								ContextUtil.getCurrentUserId(), folderId)); // 将邮件的文件夹设为目标文件夹
				mailBoxManager.update(tmpmailBox);
			}
		}
		return new JsonResult(true, "内部邮件移动成功！");
	}

	@RequestMapping("saveInnerMailToDraftFolder")
	@ResponseBody
	public JsonResult saveInnerMailToDraftFolder(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String data = request.getParameter("data"); // 获取表格数据
		String urge = request.getParameter("urge"); // 获取邮件重要程度参数
		InnerMail innerMail = (InnerMail) JSONUtil.json2Bean(data,
				InnerMail.class); // 将json转成实体类
		innerMail.setMailId(idGenerator.getSID());
		if (StringUtils.isEmpty(innerMail.getSubject())) // 如果邮件的主题为空
			innerMail.setSubject("无");
		if (StringUtils.isEmpty(innerMail.getContent())) // 如果邮件的内容为空
			innerMail.setContent("无");
		if (StringUtils.isEmpty(innerMail.getFileIds())) { // 如果附件fileIds 为空
			innerMail.setFileIds(null);
			innerMail.setFileNames(null);
		} else {
			String[] fileIds = innerMail.getFileIds().split(",");
			String fileNames = "";
			for (int i = 0; i < fileIds.length; i++) {
				if (i == 0) {
					fileNames = sysFileManager.get(fileIds[i]).getFileName();
					continue;
				}
				fileNames = fileNames + ","
						+ sysFileManager.get(fileIds[i]).getFileName();
			}
			innerMail.setFileNames(fileNames);
		}
		if (StringUtils.isNotEmpty(innerMail.getRecIds())) { // 处理收件人
																// 如果收件人Id不为空，则设置收件人姓名列表
			String[] recIds = innerMail.getRecIds().split(",");
			for (int i = 0; i < recIds.length; i++) { // 遍历收件人Id 获取收件人姓名列表
				if (StringUtils.isNotEmpty(recIds[i])) { // 如果不为空
					if (i == 0) {
						innerMail.setRecNames(osUserManager.get(recIds[i])
								.getFullname()); // 获取当前收件人ID的用户名
						continue;
					}
					innerMail.setRecNames(innerMail.getRecNames() + ","
							+ osUserManager.get(recIds[i]).getFullname());
				}
			}
		} else {
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
						innerMail.setCcNames(osUserManager.get(ccIds[i])
								.getFullname()); // 获取当前抄送人ID的用户名
						continue;
					}
					innerMail.setCcNames(innerMail.getCcNames() + ","
							+ osUserManager.get(ccIds[i]).getFullname());
				}
			}
		} else {
			innerMail.setCcIds(null);
			innerMail.setCcNames(null);
		}
		innerMail.setSender(ContextUtil.getCurrentUser().getFullname());
		innerMail.setSenderTime(new Date());
		innerMail.setDelFlag(MBoolean.NO.name());
		innerMail.setStatus((short) 0);
		innerMail.setUrge(urge);
		Set<MailBox> mailBoxs = new HashSet<MailBox>();
		MailBox mailBox = new MailBox(); // 设置内部邮件箱收件箱
		mailBox.setBoxId(idGenerator.getSID());
		mailBox.setInnerMail(innerMail);
		mailBox.setMailFolder(mailFolderManager
				.getInnerMailFolderByUserIdAndType(
						ContextUtil.getCurrentUserId(),
						MailFolder.TYPE_DRAFT_FOLDER)); // 设置草稿箱信息
		mailBox.setUserId(ContextUtil.getCurrentUserId());
		mailBox.setIsDel(MBoolean.NO.name()); // 设置删除标记
		mailBox.setIsRead(MBoolean.NO.name()); // 设置阅读标记
		mailBox.setReply(MBoolean.NO.name()); // 设置回复标记
		mailBoxs.add(mailBox);
		innerMail.setMailBoxs(mailBoxs);
		innerMailManager.create(innerMail);
		return new JsonResult(true, "成功保存到草稿箱");
	}

	/**
	 * 根据内部邮件Id和文件夹Id删除内部邮件收件箱记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delMail")
	@ResponseBody
	public JsonResult delMail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String folderId = request.getParameter("folderId"); // 获取文件夹Id
		String uId = request.getParameter("ids"); // 邮件Id列表
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				MailBox mailBox = mailBoxManager
						.getMailBoxByInnerMailIdAndFolderId(id, folderId); // 根据邮件Id和文件夹Id获取内部邮件收件箱实体
				mailBoxManager.delete(mailBox.getBoxId());
			}
		}
		return new JsonResult(true, "成功删除！");
	}


	/**
	 * 搜索带图公告的
	 * @author Stephen
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getInsNewsImage")
	@ResponseBody
	public JsonResult getInsNewsImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("imgFileId", QueryParam.OP_NOT_EQUAL, "");
		queryFilter.addSortParam("updateTime", "desc");		
		List<InsNews> insNews = insNewsManager.getAll(queryFilter);
		return new JsonResult(true,"获取数据成功" ,insNews);
		
	}
	
	
	

}
