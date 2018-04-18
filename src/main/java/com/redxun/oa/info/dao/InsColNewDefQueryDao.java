
/**
 * 
 * <pre> 
 * 描述：ins_col_new_def DAO接口
 * 作者:mansan
 * 日期:2017-08-25 10:08:03
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.oa.info.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.query.QueryFilter;
import com.redxun.oa.info.entity.InsColNew;
import com.redxun.oa.info.entity.InsColNewDef;

@Repository
public class InsColNewDefQueryDao extends BaseMybatisDao<InsColNewDef> {

	@Override
	public String getNamespace() {
		return InsColNewDef.class.getName();
	}
	
	public List<InsColNewDef> getByColId(QueryFilter queryFilter){
		return this.getBySqlKey("getByColId",queryFilter.getParams());
	}
	
	public List<InsColNewDef> getByNewId(QueryFilter queryFilter){
		return this.getBySqlKey("getByNewId",queryFilter.getParams());
	}
	
	public void delByNewId(String newId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("newId", newId);
		this.getBySqlKey("delByNewId", params);
		
	}

}

