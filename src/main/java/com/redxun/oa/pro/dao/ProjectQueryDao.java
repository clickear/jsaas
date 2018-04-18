package com.redxun.oa.pro.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.query.SortParam;
import com.redxun.core.query.SqlQueryFilter;
import com.redxun.oa.pro.entity.Project;

@Repository
public class ProjectQueryDao extends BaseMybatisDao<Project>{

	@Override
	public String getNamespace() {
		return Project.class.getName();
	}

	
	
	public List<Project> getMyAttendProject(String userId,String tenantId,SqlQueryFilter sqlQueryFilter){
		Map<String,Object> params=sqlQueryFilter.getParams();
		params.put("userId", userId);
		params.put("tenantId", tenantId);
	    SortParam sortParam=sqlQueryFilter.getSortParam();
	    if(sortParam!=null){
	    	String property=sortParam.getProperty();
	    	String direction=sortParam.getDirection();
	    	params.put("property", property);
	    	params.put("direction", direction);
	    }
	    return this.getBySqlKey("getMyAttendProject", params, sqlQueryFilter.getPage());
	}
	
	

	

}
