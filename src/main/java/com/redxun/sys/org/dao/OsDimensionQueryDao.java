
/**
 * 
 * <pre> 
 * 描述：组织维度 DAO接口
 * 作者:ray
 * 日期:2017-05-17 09:37:59
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.org.dao;

import java.util.List;
import java.util.Map;

import com.redxun.sys.org.entity.OsDimension;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.SqlQueryFilter;

@Repository
public class OsDimensionQueryDao extends BaseMybatisDao<OsDimension> {

	@Override
	public String getNamespace() {
		return OsDimension.class.getName();
	}
	
	public List<OsDimension> filterateAll(QueryFilter queryFilter){
		return this.getPageBySqlKey("filterateAll", queryFilter);
	}
}

