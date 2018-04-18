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

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.sys.db.entity.SysSqlCustomQuery;
@Repository
public class SysSqlCustomQueryDao extends BaseJpaDao<SysSqlCustomQuery> {
	@Override
	protected Class getEntityClass() {
		return SysSqlCustomQuery.class;
	}
	
	
	/**
	 * 根据别名（标识） 租户ID查找
	 * @param key
	 * @param tenantId
	 * @return
	 */
	public SysSqlCustomQuery getByAlias(String key,String tenantId) {
		String jpql = "select t from SysSqlCustomQuery t where  t.key=? and t.tenantId=?";
		return (SysSqlCustomQuery)this.getUnique(jpql, new Object[]{key,tenantId});
	}
	
	
}
