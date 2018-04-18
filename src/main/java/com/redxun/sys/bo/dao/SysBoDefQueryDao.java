
/**
 * 
 * <pre> 
 * 描述：BO定义 DAO接口
 * 作者:ray
 * 日期:2017-02-15 15:02:18
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.bo.dao;

import com.redxun.sys.bo.entity.SysBoDef;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.util.StringUtil;

@Repository
public class SysBoDefQueryDao extends BaseMybatisDao<SysBoDef> {

	@Override
	public String getNamespace() {
		return SysBoDef.class.getName();
	}
	
	/**
	 * 判断bodef是否存在。
	 * @param alias			bo别名
	 * @param tenantId		租户ID
	 * @param id			主键
	 * @return
	 */
	public boolean getCountByAlias(String alias,String tenantId,String boDefId){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("alias", alias);
		params.put("tenantId", tenantId);
		if(StringUtil.isNotEmpty(boDefId)){
			params.put("id", boDefId);
		}
		Integer rtn=(Integer) this.getOne("getCountByAlias", params);
		return rtn>0;
	}
	
	public SysBoDef getByAlias(String alias,String tenantId){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("alias", alias);
		params.put("tenantId", tenantId);
		
		SysBoDef rtn=(SysBoDef) this.getOne("getByAlias", params);
		return rtn;
	}
	
	
	

}

