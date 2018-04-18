
/**
 * 
 * <pre> 
 * 描述：BO属性表 DAO接口
 * 作者:ray
 * 日期:2017-02-15 15:02:18
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.bo.dao;

import com.redxun.sys.bo.entity.SysBoAttr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class SysBoAttrQueryDao extends BaseMybatisDao<SysBoAttr> {

	@Override
	public String getNamespace() {
		return SysBoAttr.class.getName();
	}
	
	/**
	 * 根据实体ID获取属性。
	 * @param entId
	 * @return
	 */
	public List<SysBoAttr> getAttrsByEntId(String entId){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("entId", entId);
		return this.getBySqlKey("getAttrsByEntId", params);
	}

	/**
	 * 根据boDefId 删除属性数据。
	 * @param boDefId
	 */
	public void delByMainId(String entId){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("entId", entId);
		this.deleteBySqlKey("delByMainId", params);
	}
	
	/**
	 * 根据bodefId获取属性。
	 * @param boDefId
	 * @param tenantId
	 * @return
	 */
	public List<SysBoAttr> getByBoDefId(String boDefId){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("boDefId", boDefId);
		return this.getBySqlKey("getByBoDefId", params);
	}
	
}

