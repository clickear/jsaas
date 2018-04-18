package com.redxun.offdoc.core.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.query.SortParam;
import com.redxun.core.query.SqlQueryFilter;
import com.redxun.offdoc.core.entity.OdDocFlow;

@Repository
public class OdDocFlowQueryDao extends BaseMybatisDao<OdDocFlow>{

	@Override
	public String getNamespace() {
		return OdDocFlow.class.getName();
	}

	
	
	public List<OdDocFlow> getOdDocFlowAndGroup(String dimId,String tenantId,SqlQueryFilter sqlQueryFilter){
		Map<String,Object> params=sqlQueryFilter.getParams();
		params.put("dimId", dimId);
		params.put("tenantId", tenantId);
	    SortParam sortParam=sqlQueryFilter.getSortParam();
	    if(sortParam!=null){
	    	String property=sortParam.getProperty();
	    	String direction=sortParam.getDirection();
	    	params.put("property", property);
	    	params.put("direction", direction);
	    }
	    
	    return this.getBySqlKey("getOdDocFlowAndGroup", params);
	}
	
	

	

}
