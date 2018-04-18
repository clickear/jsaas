
/**
 * 
 * <pre> 
 * 描述：表单实体对象 DAO接口
 * 作者:ray
 * 日期:2017-02-15 15:02:18
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.bo.dao;

import com.redxun.sys.bo.entity.SysBoEnt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.util.StringUtil;

@Repository
public class SysBoEntQueryDao extends BaseMybatisDao<SysBoEnt> {

	@Override
	public String getNamespace() {
		return SysBoEnt.class.getName();
	}
	
	/**
	 * 根据boDefId 获取实体列表。
	 * @param boDefId
	 * @return
	 */
	public List<SysBoEnt> getByBoDefId(String boDefId){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("boDefId", boDefId);
		return  this.getBySqlKey("getByBoDefId", params);
	}
	
	public Integer getCountByAlias(String alias,String tenantId,String dsName,String id){
		Map<String,Object>  params=new HashMap<String, Object>();
		params.put("alias", alias);
		params.put("tenantId", tenantId);
		params.put("dsName", dsName);
		if(StringUtil.isEmpty(id)){
			params.put("id", id);
		}
		return (Integer) this.getOne("getCountByAlias", params);
		
	}
	
	


}

