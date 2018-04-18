
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
import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class ProItemQueryDao extends BaseMybatisDao<ProItem> {

	@Override
	public String getNamespace() {
		return ProItem.class.getName();
	}

}

