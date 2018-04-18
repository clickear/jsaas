
/**
 * 
 * <pre> 
 * 描述：系统参数 DAO接口
 * 作者:ray
 * 日期:2017-06-21 11:22:36
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.core.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.redxun.sys.core.entity.SysProperties;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.query.QueryFilter;

@Repository
public class SysPropertiesQueryDao extends BaseMybatisDao<SysProperties> {

	@Override
	public String getNamespace() {
		return SysProperties.class.getName();
	}
	public List getCategory(Map<String, Object> params){
		return this.getBySqlKey("getCategory", params);
	}
	public List<SysProperties> getGlobalPropertiesByTenantId(String tenantId){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("tenantId", tenantId);
		return this.getBySqlKey("getGlobalPropertiesByTenantId",params );
	}

}

