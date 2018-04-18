
/**
 * 
 * <pre> 
 * 描述：自定义查询 DAO接口
 * 作者:cjx
 * 日期:2017-02-21 15:32:09
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.db.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.sys.db.entity.SysSqlCustomQuery;

@Repository
public class SysSqlCustomQueryQueryDao extends BaseMybatisDao<SysSqlCustomQuery> {

	@Override
	public String getNamespace() {
		return SysSqlCustomQuery.class.getName();
	}

	
	/**
	 * 根据租户ID获取列表。
	 * @param tenantId
	 * @return
	 */
	public List<SysSqlCustomQuery> getByTenantId(String tenantId){
		return  this.getBySqlKey("getByTenantId", tenantId);
	}
}

