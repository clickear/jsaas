
/**
 * 
 * <pre> 
 * 描述：自定义属性 DAO接口
 * 作者:mansan
 * 日期:2017-12-14 14:02:29
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.org.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.redxun.sys.org.entity.OsCustomAttribute;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class OsCustomAttributeQueryDao extends BaseMybatisDao<OsCustomAttribute> {

	@Override
	public String getNamespace() {
		return OsCustomAttribute.class.getName();
	}

	public List<OsCustomAttribute> getUserTypeAttributeByTenantId(String tenantId){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("tenantId", tenantId);
		return this.getBySqlKey("getUserTypeAttributeByTenantId", map);
	}
}

