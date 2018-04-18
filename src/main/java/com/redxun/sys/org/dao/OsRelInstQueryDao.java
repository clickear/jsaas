package com.redxun.sys.org.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.query.SortParam;
import com.redxun.core.query.SqlQueryFilter;
import com.redxun.sys.org.entity.OsRelInst;
/**
 * 
 * @author csx
 * 关系实例查询
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Repository
public class OsRelInstQueryDao extends BaseMybatisDao<OsRelInst>{
	
	@Override
	public String getNamespace() {
		return OsRelInst.class.getName();
	}
	
	public List<OsRelInst> getByGroupIdRelTypeId(String groupId,String relTypeId,SqlQueryFilter filter){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("groupId", groupId);
		params.put("relTypeId", relTypeId);
		//
		if(filter!=null){
			params.putAll(filter.getParams());
		}
		return this.getBySqlKey("getByGroupIdRelTypeId", params);
	}
	
	
	public List<OsRelInst> getByGroupIdRelTypeId(String groupId,String relTypeId){
		return getByGroupIdRelTypeId(groupId,relTypeId,null);
	}
	
	
}
