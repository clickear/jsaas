package com.redxun.oa.info.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
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
public class InfInnerMsgQueryDao extends BaseMybatisDao<InfInnerMsg> {
	
	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return InfInnerMsg.class.getName();
	}

	public List getUnreadMsgList(String userId,
			QueryFilter queryFilter) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return this.getBySqlKey("getUnreadMsgList", params,queryFilter.getPage());
	}

	
}
