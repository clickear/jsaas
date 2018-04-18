
/**
 * 
 * <pre> 
 * 描述：项目 DAO接口
 * 作者:陈茂昌
 * 日期:2017-09-29 14:38:27
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.oa.article.dao;

import com.redxun.oa.article.entity.ProItem;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class ProItemDao extends BaseJpaDao<ProItem> {


	@Override
	protected Class getEntityClass() {
		return ProItem.class;
	}

}

