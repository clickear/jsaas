package com.redxun.oa.info.manager;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.oa.info.dao.InfInnerMsgDao;
import com.redxun.oa.info.dao.InfInnerMsgQueryDao;
import com.redxun.oa.info.entity.InfInbox;
import com.redxun.oa.info.entity.InfInnerMsg;
import com.redxun.org.api.model.IGroup;
import com.redxun.org.api.model.IUser;
import com.redxun.org.api.service.GroupService;
import com.redxun.org.api.service.UserService;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.IdUtil;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsUserManager;

/**
 * <pre>
 * 描述：InfInnerMsg业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class InfInnerMsgManager extends BaseManager<InfInnerMsg> {
	@Resource
	private InfInnerMsgDao infInnerMsgDao;
	@Resource
	private InfInboxManager infInboxManager;
	@Resource
	private InfInnerMsgManager infInnerMsgManager;
	@Resource
	private UserService userService;
	@Resource
	private GroupService groupService;
	@Resource
	private InfInnerMsgQueryDao infInnerMsgQueryDao;
	@Resource
	OsUserManager osUserManager;
	@Resource
	OsGroupManager osGroupManager;
	
	
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return infInnerMsgDao;
	}

	/**
	 * 获取新消息的条数
	 * 
	 * @param recId
	 * @return
	 */
	public int getNewMsgCountByRecId(String recId) {
		return infInnerMsgDao.getNewMsgCountByRecId(recId);
	}

	/**
	 * 获取所有发送人为userName的信息
	 * 
	 * @param userName
	 * @return
	 */
	public List<InfInnerMsg> getByUserName(String userName) {
		return infInnerMsgDao.getByUserName(userName);
	}

	/**
	 * 获取已收信息的List,可查询可排序
	 * 
	 * @param recId
	 * @param queryFilter
	 * @return
	 */
	public List<InfInnerMsg> getInnerMsgByRecId(String recId, QueryFilter queryFilter) {
		return infInnerMsgDao.getInnerMsgByRecId(recId, queryFilter);
	}

	/**
	 * 获取组消息的List
	 * 
	 * @param groupId
	 * @param queryFilter
	 * @return
	 */
	public List<InfInnerMsg> getGroupMsgByRecId(String groupId, QueryFilter queryFilter) {
		return infInnerMsgDao.getGroupMsgByRecId(groupId, queryFilter);
	}

	/**
	 * 获取Index门户里的已收消息中的未读消息的List,不需要排序不需要查询
	 * 
	 * @param recId
	 * @param pageIndex
	 *            开始页数
	 * @return
	 */
	public List<InfInnerMsg> getIndexInnerMsgByRecId(String recId, int pageIndex) {
		return infInnerMsgDao.getIndexInnerMsgByRecId(recId, pageIndex);
	}

	/**
	 * 获取Portal门户里的已收消息的List,不需要排序不需要查询
	 * 
	 * @param recId
	 * @param pageIndex
	 *            开始页数
	 * @return
	 */
	public List<InfInnerMsg> getPanelInnerMsgByRecId(String recId, int pageIndex) {
		return infInnerMsgDao.getPanelInnerMsgByRecId(recId, pageIndex);
	}
	/**
	 * 发送新消息
	 * @param userIds 收信人Id
	 * @param groupIds 收信组Id
	 * @param context 正文
	 * @param linkMsg 关联信息
	 * @param type 是否是系统消息，如果是sys则是
	 */
	public InfInnerMsg sendMessage(String userIds, String groupIds, String context, String linkMsg,boolean isSys) {
		return sendMessage(ContextUtil.getCurrentUserId(), userIds, groupIds, context, linkMsg,isSys);	
	}
	
	
	/**
	 * 发送内部消息。
	 * @param sendUserId	当为0时表示系统消息，否则为发送人ID
	 * @param userIds		用户ID使用逗号分割
	 * @param groupIds		用户组ID使用逗号分割
	 * @param content		发送内容
	 * @param linkMsg		连接
	 * @param isSys			系统自动发送的
	 */
	public InfInnerMsg sendMessage(String sendUserId,String userIds, String groupIds, String content, String linkMsg,boolean isSys) {
		String sendId=sendUserId;
		String sendName="系统";
		String tenantId="0";
				
		if(!"0".equals(sendUserId)){
			IUser user=userService.getByUserId(sendUserId);
			sendName = user.getFullname();
			tenantId=user.getTenant().getTenantId();
			
		}
		String category=isSys?"系统消息":"个人消息";
		
//		StringBuffer userName = new StringBuffer();
//		StringBuffer groupName = new StringBuffer();
		Set<InfInbox> inboxs = new HashSet<InfInbox>();
		
		String id = IdUtil.getId();
		// 新建一条内部短消息
		InfInnerMsg infInnerMsg = new InfInnerMsg();
		infInnerMsg.setMsgId(id);
		infInnerMsg.setDelFlag("no");// 是否删除
		infInnerMsg.setContent(content);// 内容
		infInnerMsg.setTenantId(tenantId);
		infInnerMsg.setSender(sendName);// 发送人名
		infInnerMsg.setSenderId(sendId);
		infInnerMsg.setCategory(category);// 信息类型

		if (linkMsg != null) {
			infInnerMsg.setLinkMsg(linkMsg);// 消息携带连接
		}
		// 处理收件人为用户
		handlerUser(sendUserId, userIds, tenantId,  inboxs, infInnerMsg);
		
		//处理收件人为用户组
		handlerGroup(sendUserId, groupIds, tenantId,  inboxs, infInnerMsg);
		
		//处理发送组
		handlerSendMsg(sendUserId,userIds, groupIds, tenantId,  inboxs, infInnerMsg);
	
		infInnerMsg.setInfInboxs(inboxs);
		infInnerMsg.setCreateBy(sendUserId);
		infInnerMsgManager.create(infInnerMsg);
		
		return infInnerMsg;
	}

	private void handlerGroup(String userFrom, String groupIds, String tenantId, 
			Set<InfInbox> inboxs, InfInnerMsg infInnerMsg) {
		if (StringUtils.isBlank(groupIds)) return;
		
		String[] groupId = groupIds.split(",");
		for (int i = 0; i < groupId.length; i++) {
			IGroup group=groupService.getById(groupId[i]);
			InfInbox inbox = new InfInbox();
			inbox.setRecId(IdUtil.getId());
			inbox.setRecType("REC");// 设置类型为收信
			inbox.setGroupName(group.getIdentityName());// 收信组名
//			groupName.append(group.getName());
//			groupName.append(",");
			inbox.setInfInnerMsg(infInnerMsg);
			inbox.setGroupId(groupId[i]);// 收信组Id
			inbox.setIsRead("no");
			inbox.setIsDel("no");
			inbox.setTenantId(tenantId);
			inbox.setCreateBy(userFrom);
			inbox.setCreateTime(new Date());
			inboxs.add(inbox);
		}
	}

	private void handlerUser(String userFrom, String userIds, String tenantId, 
			Set<InfInbox> inboxs, InfInnerMsg infInnerMsg) {
		if (StringUtils.isBlank(userIds)) return;
		
		String[] userId = userIds.split(",");
		// 新建内部短消息收箱,每个收信人一个收件箱
		for (int i = 0; i < userId.length; i++) {
			IUser user=userService.getByUserId(userId[i]);
			InfInbox inbox = new InfInbox();
			inbox.setRecId(IdUtil.getId());
			inbox.setRecType("REC");// 设置类型为收信
			inbox.setFullname(user.getFullname());// 设置收信人名
//			userName.append(user.getFullname());
//			userName.append(",");
			inbox.setInfInnerMsg(infInnerMsg);// 设置对应的内部短消息
			inbox.setRecUserId(userId[i]);// 设置接收人Id
			inbox.setIsRead("no");// 设置未读
			inbox.setIsDel("no");// 设置未删除
			inbox.setTenantId(tenantId);
			inbox.setCreateBy(userFrom);
			inbox.setCreateTime(new Date());
			inboxs.add(inbox);
			
		}
	}
	
	
	private void handlerSendMsg(String userFrom, String userIds,String groupIds, String tenantId, 
			Set<InfInbox> inboxs, InfInnerMsg infInnerMsg) {
		if (StringUtils.isBlank(userIds)) return;
		StringBuffer userName = new StringBuffer();
		StringBuffer groupName = new StringBuffer();
		
		InfInbox sendbox = new InfInbox();
		sendbox.setCreateBy(userFrom);
		sendbox.setRecId(IdUtil.getId());
		sendbox.setRecType("SEND");
		
		if(StringUtils.isNotEmpty(userIds)){
			String[] userId = userIds.split(",");
			if (userId.length > 0) {
				for (int i = 0; i < userId.length; i++) {				
					userName.append(osUserManager.get(userId[i]).getFullname());
					userName.append(",");							
				}
			}
		}
		if(StringUtils.isNotEmpty(groupIds)){
			String[] groupId = groupIds.split(",");
				if (groupId.length > 0) {
					for (int i = 0; i < groupId.length; i++) {
						groupName.append(osGroupManager.get(groupId[i]).getName());
						groupName.append(",");
					}
				}
		}
		if (StringUtils.isNotBlank(userIds)) {
			sendbox.setRecUserId(userIds);
			sendbox.setFullname(userName.substring(0, userName.length() - 1).toString());
		}
		if (StringUtils.isNotBlank(groupIds)) {
			sendbox.setGroupId(groupIds);
			sendbox.setGroupName(groupName.substring(0, groupName.length() - 1).toString());
		}
		sendbox.setInfInnerMsg(infInnerMsg);
		sendbox.setTenantId(tenantId);
		sendbox.setCreateTime(new Date());
		inboxs.add(sendbox);
	}

	public List<InfInnerMsg> getUnreadMsgList(String userId,
			QueryFilter queryFilter) {
		List<InfInnerMsg> list = infInnerMsgQueryDao.getUnreadMsgList(userId,queryFilter);
		return list;
	}
	
	
	
	
	

}