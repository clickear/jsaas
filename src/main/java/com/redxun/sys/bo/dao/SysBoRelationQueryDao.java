
/**
 * 
 * <pre> 
 * 描述：BO关系定义 DAO接口
 * 作者:ray
 * 日期:2017-02-15 15:02:17
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.bo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.sys.bo.entity.SysBoRelation;

@Repository
public class SysBoRelationQueryDao extends BaseMybatisDao<SysBoRelation> {

	@Override
	public String getNamespace() {
		return SysBoRelation.class.getName();
	}
	
	public void delByMainId(String boDefId){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("boDefid", boDefId);
		this.deleteBySqlKey("delByMainId", params);
	}
	
	public List<SysBoRelation> getByBoDefId(String boDefId){
		return  this.getBySqlKey("getByBoDefId", boDefId);
	}
	
	
	public SysBoRelation getByDefEntId(String boDefId,String entId){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("boDefid", boDefId);
		params.put("entId", entId);
		return  this.getUnique("getByDefEntId", params);
	}
	
	
	public SysBoRelation getByBoDefIdAndEntName(String boDefId,String entName){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("boDefid", boDefId);
		params.put("entName", entName);
		return  this.getUnique("getByBoDefIdAndEntName", params);
	}

}

