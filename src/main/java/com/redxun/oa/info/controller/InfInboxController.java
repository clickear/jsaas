package com.redxun.oa.info.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.oa.info.entity.InfInbox;
import com.redxun.oa.info.entity.InfInnerMsg;
import com.redxun.oa.info.entity.SendMsg;
import com.redxun.oa.info.manager.InfInboxManager;
import com.redxun.oa.info.manager.InfInnerMsgManager;
import com.redxun.org.api.model.IGroup;
import com.redxun.org.api.service.GroupService;
import com.redxun.org.api.service.UserService;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;

/**
 * 内部短消息收件箱管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/oa/info/infInbox/")
public class InfInboxController extends TenantListController {
	@Resource
	InfInboxManager infInboxManager;
	@Resource
	InfInnerMsgManager infInnerMsgManager;
	@Resource
	UserService userService;
	@Resource
	GroupService groupService;

	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				infInboxManager.delete(id);
			}
		}
		return new JsonResult(true, "成功删除！");
	}

	/**
	 * 删除已收消息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delMsg")
	@ResponseBody
	public JsonResult delMsg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		String uId = request.getParameter("pkId");// MsgId
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				InfInbox inbox = infInboxManager.getByMsgId(id, userId);
				infInboxManager.delete(inbox.getRecId());
			}
		}
		return new JsonResult(true, "成功删除！");
	}

	/**
	 * 逻辑删除已发信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delSendMsg")
	@ResponseBody
	public JsonResult delSendMsg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = request.getParameter("pkId");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				infInboxManager.delete(id);
			}
		}
		return new JsonResult(true, "成功删除！");
	}

	/**
	 * 重发功能
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("reSend")
	@ResponseBody
	public JsonResult reSend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = request.getParameter("pkId");// 获取重发Msg
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				InfInbox infbox = infInboxManager.get(id);
				// 新建一个InnerMsg,把原来的Msg信息复制过来
				InfInnerMsg infInnerMsg = new InfInnerMsg();
				InfInnerMsg msg = infbox.getInfInnerMsg();
				infInnerMsg.setMsgId(idGenerator.getSID());
				infInnerMsg.setCategory("个人消息");
				
				infInnerMsg.setContent(msg.getContent());
				infInnerMsg.setSender(msg.getSender());
				infInnerMsg.setSenderId(msg.getSenderId());
				infInnerMsg.setDelFlag(msg.getDelFlag());
				infInnerMsg.setLinkMsg(msg.getLinkMsg());
				infInnerMsgManager.create(infInnerMsg);
				// 判断是发给个人还是发给组
				if (null != (infbox.getRecUserId())) {// 如果是发给个人
					// 创建Inbox
					String[] userIds = infbox.getRecUserId().split(",");
					for (String userId : userIds) {
						InfInbox inbox = new InfInbox();
						inbox.setRecType("REC");
						inbox.setFullname(userService.getByUserId(userId).getFullname());
						inbox.setInfInnerMsg(infInnerMsg);
						inbox.setRecUserId(userId);
						inbox.setIsRead("no");
						inbox.setIsDel("no");
						infInboxManager.create(inbox);
					}
				}
				if (null != (infbox.getGroupId())) {// 如果是发给组
					// 创建Inbox
					String[] groupIds = infbox.getGroupId().split(",");
					for (String groupId : groupIds) {
						InfInbox inbox = new InfInbox();
						inbox.setRecType("REC");
						inbox.setGroupName(groupService.getById(groupId).getIdentityName());
						inbox.setInfInnerMsg(infInnerMsg);
						inbox.setGroupId(groupId);
						inbox.setIsRead("no");
						inbox.setIsDel("no");
						infInboxManager.create(inbox);
					}
				}
				InfInbox sendbox = new InfInbox();
				sendbox.setRecType("SEND");
				sendbox.setFullname(infbox.getFullname());
				sendbox.setRecUserId(infbox.getRecUserId());
				sendbox.setGroupId(infbox.getGroupId());
				sendbox.setGroupName(infbox.getGroupName());
				sendbox.setInfInnerMsg(infInnerMsg);
				infInboxManager.create(sendbox);
			}
		}
		return new JsonResult(true, "成功重发！");
	}
	
	private void reSendToUser(InfInnerMsg msg){
		InfInnerMsg infInnerMsg = new InfInnerMsg();
		infInnerMsg.setMsgId(idGenerator.getSID());
		infInnerMsg.setCategory("个人消息");
		
		infInnerMsg.setContent(msg.getContent());
		infInnerMsg.setSender(msg.getSender());
		infInnerMsg.setSenderId(msg.getSenderId());
		infInnerMsg.setDelFlag(msg.getDelFlag());
		infInnerMsg.setLinkMsg(msg.getLinkMsg());
		infInnerMsgManager.create(infInnerMsg);
	}
	
	private void reSendToGroup(){
		
	}

	/**
	 * 跟新已读状态
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("updateStatus")
	@ResponseBody
	public void updateStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = request.getParameter("pkId");
		String userId = ContextUtil.getCurrentUserId();
		if (StringUtils.isNotEmpty(uId)) {
			InfInbox inbox = infInboxManager.getByMsgId(uId, userId);
			inbox.setIsRead("yes");
			infInboxManager.update(inbox);
		}
	}

	/**
	 * 获取已收消息的List
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("received")
	@ResponseBody
	public JsonPageResult<InfInnerMsg> received(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		List<InfInnerMsg> list = infInnerMsgManager.getInnerMsgByRecId(userId, queryFilter);
		JsonPageResult<InfInnerMsg> result = new JsonPageResult<InfInnerMsg>(list, queryFilter.getPage().getTotalItems());
		return result;

	}

	/**
	 * 门户的已收信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("portalReceived")
	@ResponseBody
	public JsonPageResult<InfInnerMsg> portalReceived(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		List<InfInnerMsg> list = infInnerMsgManager.getInnerMsgByRecId(userId, queryFilter);
		JsonPageResult<InfInnerMsg> result = new JsonPageResult<InfInnerMsg>(list, queryFilter.getPage().getTotalItems());
		return result;

	}

	/**
	 * 获取组消息的List
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("groupedMsg")
	@ResponseBody
	public JsonPageResult<InfInnerMsg> groupedMsg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		// int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		String userId = ContextUtil.getCurrentUserId();// 获取用户Id
		List<InfInnerMsg> innerMsgs = new ArrayList<InfInnerMsg>();// 显示的类
//		List<OsGroup> groups = groupService.getBelongGroups(userId);// 获取当前用户所在的用户组
		List<IGroup> groups = groupService.getGroupsByUserId(userId);
		if (groups.size() > 0) {
			for (IGroup group : groups) {// 遍历每个组
				List<InfInnerMsg> list = infInnerMsgManager.getGroupMsgByRecId(group.getIdentityId(), queryFilter);// 获得每个组收到的消息
				innerMsgs.addAll(list);
			}
		}
		JsonPageResult<InfInnerMsg> result = new JsonPageResult<InfInnerMsg>(innerMsgs, queryFilter.getPage().getTotalItems());
		return result;

	}

	/**
	 * 获取已发消息的List
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sended")
	@ResponseBody
	public JsonPageResult<InfInbox> sended(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		String sender = userService.getByUserId(userId).getFullname(); // 发信人名字
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		List<InfInbox> list = infInboxManager.getInboxBySender(sender,userId,queryFilter);
		JsonPageResult<InfInbox> result = new JsonPageResult<InfInbox>(list, queryFilter.getPage().getTotalItems());
		return result;
	}

	/**
	 * 查看明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		InfInbox infInbox = null;
		if (StringUtils.isNotEmpty(pkId)) {
			infInbox = infInboxManager.get(pkId);
		} else {
			infInbox = new InfInbox();
		}
		return getPathView(request).addObject("infInbox", infInbox);
	}

	/**
	 * 已收消息页面的Get页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("recGet")
	public ModelAndView recGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		String isGroup = request.getParameter("isGroup");
		String userId = ContextUtil.getCurrentUserId();
		InfInnerMsg infInnerMsg = null;
		if (StringUtils.isNotEmpty(pkId)) {
			infInnerMsg = infInnerMsgManager.get(pkId);
		}
		if (!"yes".equals(isGroup)) {
			InfInbox inbox = infInboxManager.getByMsgId(pkId, userId);
			inbox.setIsRead("yes");
			infInboxManager.update(inbox);
		}
		return getPathView(request).addObject("infInnerMsg", infInnerMsg);
	}

	/**
	 * Portal页面 的我的消息的Get页面,
	 * 
	 * @param request
	 * @param response
	 * @return 返回List,每页一个InnerMsg,有多少行就有多少页,如果上一页下一页就行数加减一
	 * @throws Exception
	 */
	@RequestMapping("recPortalGet")
	public ModelAndView recPoratlGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		String userId = ContextUtil.getCurrentUserId();// 当前用户Id
		//更新状态
		String uId = request.getParameter("pkId");
		InfInnerMsg infInnerMsg = null;
		infInnerMsg = infInnerMsgManager.get(uId);
		// 更改已读
		InfInbox inbox = infInboxManager.getByMsgId(infInnerMsg.getMsgId(), userId);
		inbox.setIsRead("yes");
		infInboxManager.update(inbox);
		return getPathView(request).addObject("infInnerMsg", infInnerMsg);
	}

	/**
	 * index页面有新消息时,点击消息按钮时的Get页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("indexGet")
	public ModelAndView indexGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ContextUtil.getCurrentUserId();// 当前用户Id
		String uId = request.getParameter("uId");// 点击的是第几行
		int pageIndex = Integer.parseInt(uId) - 1;// 行数减一,因为行数是以1起,而page是以0起
		InfInnerMsg infInnerMsg = null;
		if (infInnerMsgManager.getIndexInnerMsgByRecId(userId, pageIndex).size() > 0) {// 判断是否有新消息
			infInnerMsg = infInnerMsgManager.getIndexInnerMsgByRecId(userId, pageIndex).get(0);// 得到对应的第几页的InnerMsg
		}
		int length = infInnerMsgManager.getNewMsgCountByRecId(userId);// 这个List的长度,方便判断是否是最后一页
		// 更改已读
		InfInbox inbox = infInboxManager.getByMsgId(infInnerMsg.getMsgId(), userId);
		inbox.setIsRead("yes");
		infInboxManager.update(inbox);

		return getPathView(request).addObject("infInnerMsg", infInnerMsg).addObject("uId", uId).addObject("length", length);
	}

	/**
	 * 已发消息页面的Get页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sendGet")
	public ModelAndView sendGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		SendMsg sendMsg = null;
		InfInbox inbox = null;
		if (StringUtils.isNotEmpty(pkId)) {
			inbox = infInboxManager.get(pkId);
			StringBuffer recName = new StringBuffer();
			if (inbox.getFullname() != null) {
				recName.append(inbox.getFullname());
			}
			if (inbox.getGroupName() != null) {
				recName.append(",");
				recName.append(inbox.getGroupName());
			}
			sendMsg = new SendMsg(inbox.getRecId(), recName.toString(), inbox.getInfInnerMsg().getContent(), inbox.getInfInnerMsg().getCreateTime());
		}
		return getPathView(request).addObject("sendMsg", sendMsg);
	}

	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		InfInbox infInbox = null;
		if (StringUtils.isNotEmpty(pkId)) {
			infInbox = infInboxManager.get(pkId);
			if ("true".equals(forCopy)) {
				infInbox.setRecId(null);
			}
		} else {
			infInbox = new InfInbox();
		}
		return getPathView(request).addObject("infInbox", infInbox);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return infInboxManager;
	}

}
