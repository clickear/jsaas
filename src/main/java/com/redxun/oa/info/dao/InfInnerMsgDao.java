package com.redxun.oa.info.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.core.query.Page;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.QueryParam;
import com.redxun.core.query.SortParam;
import com.redxun.oa.info.entity.InfInbox;
import com.redxun.oa.info.entity.InfInnerMsg;
import com.redxun.saweb.context.ContextUtil;

/**
 * <pre>
 * 描述：InfInnerMsg数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class InfInnerMsgDao extends BaseJpaDao<InfInnerMsg> {

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getEntityClass() {
		return InfInnerMsg.class;
	}

	/**
	 * 获取新消息的条数
	 * 
	 * @param recId
	 * @return
	 */
	public int getNewMsgCountByRecId(String recId) {
		String ql = "select m from InfInnerMsg m left join m.infInboxs as box where box.recUserId = ? and box.isRead = 'no' and box.recType='REC' ";
		List<InfInnerMsg> list = this.getByJpql(ql, new Object[]{recId});
		return list.size();
	}

	/**
	 * 获取所有发送人为userName的信息
	 * 
	 * @param userName
	 * @return
	 */
	public List<InfInnerMsg> getByUserName(String userName) {
		String ql = "from InfInnerMsg inf where inf.sender=? and inf.tenantId = ?";
		return this.getByJpql(ql, new Object[]{userName});
	}

	/**
	 * 获取已收信息的List,可查询可排序
	 * 
	 * @param recId
	 * @param queryFilter
	 * @return
	 */
	public List<InfInnerMsg> getInnerMsgByRecId(String recId, QueryFilter queryFilter) {
		List<Object> params = new ArrayList<Object>();
		String ql = "select m ,box.isRead as isRead from InfInnerMsg m left join m.infInboxs as box  where box.recUserId = ? and box.recType = 'REC'";
		params.add(recId);
		Map<String, QueryParam> queryParams = queryFilter.getQueryParams();
		// 是否有发信人关键字的查询
		if (queryParams.get("sender") != null) {
			ql += " and m.sender like ?";
			params.add(queryParams.get("sender").getValue());
		}
		// 是否有已读未读的查询
		if (queryParams.get("isRead") != null) {
			ql += " and box.isRead like ?";
			params.add(queryParams.get("isRead").getValue());
		}
		//日期
		if (queryParams.get("startTime") != null) {
			ql += " and m.createTime > ?";
			params.add(queryParams.get("startTime").getValue());
		}
		if (queryParams.get("endTime") != null) {
			ql += " and m.createTime < ?";
			params.add(queryParams.get("endTime").getValue());
		}

		// 排序
		if (queryFilter.getOrderByList().size() > 0) {

			SortParam sortParam = queryFilter.getOrderByList().get(0);
			ql += " order by m." + sortParam.getProperty() + " " + sortParam.getDirection();
		} else {
			ql += " order by m.createTime desc";

		}
		List list = this.getByJpql(ql, params.toArray(), queryFilter.getPage());
		List<InfInnerMsg> innerMsgs = new ArrayList<InfInnerMsg>();
		for (int i = 0; i < list.size(); i++) {
			Object[] rows = (Object[]) list.get(i);
			InfInnerMsg msg = (InfInnerMsg) rows[0];
			String isRead = (String) rows[1];
			msg.setIsRead(isRead);
			innerMsgs.add(msg);
		}
		return innerMsgs;
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
		List<Object> params = new ArrayList<Object>();
		String ql = "select m ,box.isRead as isRead from InfInnerMsg m left join m.infInboxs as box  where box.recUserId = ? and box.recType = 'REC'";
		params.add(recId);
		ql += " order by m.createTime desc";
		Page page = new Page(pageIndex, 1);// 每页一条数据,第几页就是第几条
		List list = this.getByJpql(ql, params.toArray(), page);
		List<InfInnerMsg> innerMsgs = new ArrayList<InfInnerMsg>();
		for (int i = 0; i < list.size(); i++) {
			Object[] rows = (Object[]) list.get(i);
			InfInnerMsg msg = (InfInnerMsg) rows[0];
			String isRead = (String) rows[1];
			msg.setIsRead(isRead);
			innerMsgs.add(msg);
		}
		return innerMsgs;
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
		List<Object> params = new ArrayList<Object>();
		String ql = "select m ,box.isRead as isRead from InfInnerMsg m left join m.infInboxs as box where box.recUserId = ? and box.isRead = 'no' and box.recType = 'REC'";
		params.add(recId);
		ql += " order by m.createTime desc";
		Page page = new Page(pageIndex, 1);// 每页一条数据,第几页就是第几条
		List list = this.getByJpql(ql, params.toArray(), page);
		List<InfInnerMsg> innerMsgs = new ArrayList<InfInnerMsg>();
		for (int i = 0; i < list.size(); i++) {
			Object[] rows = (Object[]) list.get(i);
			InfInnerMsg msg = (InfInnerMsg) rows[0];
			String isRead = (String) rows[1];
			msg.setIsRead(isRead);
			innerMsgs.add(msg);
		}
		return innerMsgs;
	}

	/**
	 * 获取组消息的List
	 * 
	 * @param groupId
	 * @param queryFilter
	 * @return
	 */
	public List<InfInnerMsg> getGroupMsgByRecId(String groupId, QueryFilter queryFilter) {
		List<Object> params = new ArrayList<Object>();
		String ql = "select m from InfInnerMsg m left join m.infInboxs as box  where box.groupId = ? and box.recType = 'REC'";
		params.add(groupId);
		Map<String, QueryParam> queryParams = queryFilter.getQueryParams();
		//发信人
		if (queryParams.get("sender") != null) {
			ql += " and m.sender like ?";
			params.add(queryParams.get("sender").getValue());
		}
		//日期
		if (queryParams.get("startTime") != null) {
			ql += " and m.createTime > ?";
			params.add(queryParams.get("startTime").getValue());
		}
		if (queryParams.get("endTime") != null) {
			ql += " and m.createTime < ?";
			params.add(queryParams.get("endTime").getValue());
		}

		if (queryFilter.getOrderByList().size() > 0) {
			SortParam sortParam = queryFilter.getOrderByList().get(0);
			ql += " order by m." + sortParam.getProperty() + " " + sortParam.getDirection();
		} else {
			ql += " order by m.createTime desc";
		}
		List list = this.getByJpql(ql, params.toArray(), queryFilter.getPage());
		List<InfInnerMsg> innerMsgs = new ArrayList<InfInnerMsg>();
		for (int i = 0; i < list.size(); i++) {

			Object rows = (Object) list.get(i);
			InfInnerMsg msg = (InfInnerMsg) rows;
			msg.setIsRead("group");
			innerMsgs.add(msg);
		}
		return innerMsgs;
	}
}
