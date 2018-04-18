package com.redxun.oa.info.manager;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redxun.core.dao.IDao;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.Page;
import com.redxun.core.query.QueryFilter;
import com.redxun.oa.info.dao.InfInboxDao;
import com.redxun.oa.info.entity.InfInbox;
import com.redxun.oa.info.entity.InfInnerMsg;
import com.redxun.oa.info.entity.InsPortalParams;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.QueryFilterBuilder;

/**
 * <pre>
 * 描述：InfInbox业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class InfInboxManager extends BaseManager<InfInbox> {
	@Resource
	private InfInboxDao infInboxDao;
	@Resource
	InfInnerMsgManager infInnerMsgManager;

	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return infInboxDao;
	}

	/**
	 * 获取收信人Id为userId的List
	 * 
	 * @param userId
	 * @return
	 */
	public List<InfInbox> getByUserId(String userId) {
		return infInboxDao.getByUserId(userId);
	}

	/**
	 * 获取收信组为groupId的List
	 * 
	 * @param groupId
	 * @return
	 */
	public List<InfInbox> getByGroupId(String groupId) {
		return infInboxDao.getByGroupId(groupId);
	}

	/**
	 * 获取消息为msgId的收信人Id为userId的InfInbox,用于设置具体的某人的某条消息的已读
	 * 
	 * @param msgId
	 * @param userId
	 * @return
	 */
	public InfInbox getByMsgId(String msgId, String userId) {
		return infInboxDao.getByMsgId(msgId, userId);
	}

	/**
	 * 获取发信人为sender的所有发出消息,用于已发信息
	 * 
	 * @param sender
	 *            发信人
	 * @param queryFilter
	 * @return
	 */
	public List<InfInbox> getInboxBySender(String sender,String senderId , QueryFilter queryFilter) {
		return infInboxDao.getInboxBySender(sender,senderId, queryFilter);
	}

	/**
	 * 门户的已收信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public List<InfInnerMsg> getPortalMsg(InsPortalParams params) {
		String userId = ContextUtil.getCurrentUserId();
		QueryFilter queryFilter = new QueryFilter();
		Page page=new Page();
    	page.setPageIndex(0);
    	page.setPageSize(params.getPageSize());
		queryFilter.setPage(page);
		List<InfInnerMsg> list = infInnerMsgManager.getInnerMsgByRecId(userId, queryFilter);
		return list;

	}
}