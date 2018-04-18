
/**
 * 
 * <pre> 
 * 描述：人员属性值 DAO接口
 * 作者:mansan
 * 日期:2017-12-14 14:09:43
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.org.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.redxun.sys.org.entity.OsAttributeValue;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class OsAttributeValueQueryDao extends BaseMybatisDao<OsAttributeValue> {

	@Override
	public String getNamespace() {
		return OsAttributeValue.class.getName();
	}
	public OsAttributeValue getSpecialValueByUser(String attributeId,String userId){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("attributeId", attributeId);
		map.put("targetId", userId);
		return this.getUnique("getSpecialValueByTarget", map);
	}  
	
	public List<OsAttributeValue> getUserAttributeValue(String userId){
		Map<String , Object> params=new HashMap<String, Object>();
		params.put("targetId", userId);
		return  this.getBySqlKey("getUserAttributeValue", params);
	}
	
	public OsAttributeValue getByAttrGroup(String tenantId, String dimId,String groupId, String key){
		Map<String , Object> params=new HashMap<String, Object>();
		params.put("dimId", dimId);
		params.put("groupId", groupId);
		params.put("key", key);
		params.put("tenantId", tenantId);
		
		return  this.getUnique("getByAttrGroup", params);
	}
}

