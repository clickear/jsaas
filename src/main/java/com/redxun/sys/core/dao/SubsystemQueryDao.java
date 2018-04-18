package com.redxun.sys.core.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.sys.core.entity.Subsystem;
/**
 * 子系统查询
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Repository
public class SubsystemQueryDao extends BaseMybatisDao<Subsystem>{
	@Override
	public String getNamespace() {
		return Subsystem.class.getName();
	}
	
	/**
	 * 获得用户组允许访问的子系统
	 * @param groupId
	 * @return
	 */
	public List<Subsystem> getGrantSubsByGroupId(String groupId){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("groupId", groupId);
		return this.getBySqlKey("getGrantSubsByGroupId", params);
	}
	
	public List<Subsystem> getGrantSubsByUserId(String userId,String tenantId,String instType){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("tenantId", tenantId);
		params.put("instType", instType);
		return this.getBySqlKey("getGrantSubsByUserId", params);
	}
	/**
	 * 通过的条件增加系统与机构类型关系表
	 * @param userId
	 * @param tenantId
	 * @param instType
	 * @return
	 */
	public List<Subsystem> getGrantSubsByUserIdBySTSR(String userId,String tenantId,String instType){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("tenantId", tenantId);
		params.put("instType", instType);
		return this.getBySqlKey("getGrantSubsByUserIdBySTSR", params);
	}
	
	public List<Subsystem> getByStatus(String status){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("status", status);
		return this.getBySqlKey("getByStatus", params);
	}
	
	public List<Subsystem> getByInstTypeStatus(String instType,String status){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("instType", instType);
		params.put("status", status);
		return this.getBySqlKey("getByInStTypeStatus", params);
	}
	
	/**
	 * 通过子系统和机构类型关系表获取相应的子系统
	 * @param instType
	 * @param status
	 * @return
	 */
	public List<Subsystem> getSubSysSTSR(String instType,String status){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("instType", instType);
		params.put("status", status);
		return this.getBySqlKey("getByInstTypeStatus", params);
	}
	
	public List<Subsystem> getByIsSaasStatus(String isSaas,String status){
		
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("status", status);
		params.put("isSaas", isSaas);
		
		return this.getBySqlKey("getByIsSaasStatus", params);
	}

}
