package com.redxun.sys.org.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.query.SqlQueryFilter;
import com.redxun.sys.org.entity.OsDimension;
import com.redxun.sys.org.entity.OsGroup;

/**
 *  OsGroup的用户组的DAO的查询
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Repository
public class OsGroupQueryDao extends BaseMybatisDao<OsGroup>{
	@Override
	public String getNamespace() {
		return OsGroup.class.getName();
	}
	
	/**
	 * 查找某个用户组下的某关系用户组,并且按条件过滤
	 * @param filter
	 * @return
	 */
	public List<OsGroup> getByGroupIdRelTypeId(SqlQueryFilter filter){
		Map<String,Object> params=filter.getParams();
		return this.getBySqlKey("getByGroupIdRelTypeId", params,filter.getPage());
	}
	/**
	 * 取得跟用户有某种关系的用户组列表
	 * @param userId
	 * @param relTypeId
	 * @return
	 */
	public List<OsGroup> getByUserIdRelTypeId(String userId,String relTypeId){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("relTypeId", relTypeId);
		return this.getBySqlKey("getByUserIdRelTypeId", params);
	}
	/**
	 * 取得某用户某维度某种关系的用户组
	 * @param dimId
	 * @param userId
	 * @param relTypeId
	 * @return
	 */
	public List<OsGroup> getByDimIdUserIdRelTypeId(String dimId,String userId,String relTypeId){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("dimId", dimId);
		params.put("relTypeId", relTypeId);
		return this.getBySqlKey("getByDimIdUserIdRelTypeId",params);
	}
	/**
	 * 取得与关系2方某种分类关系下的用户组
	 * @param party2
	 * @param relType
	 * @return
	 */
	public List<OsGroup> getByParty2RelType(String party2,String relType){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("party2", party2);
		params.put("relType", relType);
		return this.getBySqlKey("getByParty2RelType",params);
	}
	
	/**
	 * 取得某用户某维度某种关系的主用户组
	 * @param dimId
	 * @param userId
	 * @param relTypeId
	 * @param isMain
	 * @return
	 */
	public List<OsGroup> getByDimIdUserIdRelTypeIdIsMain(String dimId,String userId,String relTypeId,String isMain){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("dimId", dimId);
		params.put("relTypeId", relTypeId);
		params.put("isMain", isMain);
		return this.getBySqlKey("getByDimIdUserIdRelTypeIdIsMain",params);
	}
	
	/**
	 * 获取某维度下的某用户所在的group。
	 * @param dimId
	 * @param userId
	 * @return
	 */
	public List<OsGroup> getByDimIdUserId(String dimId,String userId){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("dimId", dimId);
		List<OsGroup> osGroups= this.getBySqlKey("getByDimIdUserId",params);
		return osGroups;
		
	}
	
	public OsGroup getByParentIdGroupName(String parentId,String name){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("parentId", parentId);
		params.put("name", name);
		List<OsGroup> osGroups= this.getBySqlKey("getByParentIdGroupName",params);
		if(osGroups.size()>0) return osGroups.get(0);
		return null;
	}
	
	/**
	 * 取得用户拥有的用户组
	 * @param userId
	 * @return
	 */
	public List<OsGroup> getByUserId(String userId){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		return this.getBySqlKey("getByUserId", params);
	}
	
	/**
	 * 根据用户ID查找他所在的的组。
	 * @param userId
	 * @return
	 */
	public OsGroup getMainGroupByUserId(String userId){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		return this.getUnique("getMainGroupByUserId", params);
	}
	
	/**
	 * 根据用户ID和维度名查找他所在的组
	 * @param userId
	 * @param dimName
	 * @return
	 * */
	
	public List<OsGroup> getByReportUserIdByGroup(String userId,String dimName){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("dimName", dimName);
		return this.getBySqlKey("getByReportUserIdByGroup", params);
	}
	
	/**
	 * 获取微信用户。
	 * @param tenantId
	 * @return
	 */
	public List<OsGroup> getSyncWx(String tenantId){
		return this.getBySqlKey("getSyncWx", tenantId);
	}
	
	/**
	 * 获取微信用户。
	 * @param groupId
	 * @return
	 */
	public void updWxFlag(String groupId){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("groupId", groupId);
		this.updateBySqlKey("updWxFlag", params);
	}

	public Integer getMaxPid() {
		// TODO Auto-generated method stub
		return (Integer) this.getOne("getMaxPid", null);
	}
	
	public void updateWxPid(String groupId,Integer wxParentPid,Integer wxPid){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("wxPid", wxPid);
		params.put("wxParentPid", wxParentPid);				
		params.put("groupId", groupId);
		this.updateBySqlKey("updateWxPid", params);
	}
	
	/**
	 * 根据菜单获取有权限的组织。
	 * @param menuId
	 * @return
	 */
	public List<OsGroup> getByMenuId(String menuId){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("menuId", menuId);
		List<OsGroup> list= this.getBySqlKey("getByMenuId", params);
		return list;
	}

	public List<OsGroup> getByDimAndLevel(String dimId, String rankLevel) {
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("dimId", dimId);
		params.put("rankLevel", rankLevel);
		List<OsGroup> list= this.getBySqlKey("getByDimAndLevel", params);
		return list;
	}
	
}
