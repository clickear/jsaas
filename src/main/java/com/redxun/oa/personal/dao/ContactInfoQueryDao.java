package com.redxun.oa.personal.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.query.SortParam;
import com.redxun.core.query.SqlQueryFilter;
import com.redxun.oa.personal.entity.ContactInfo;

@Repository
public class ContactInfoQueryDao extends BaseMybatisDao<ContactInfo>{

	@Override
	public String getNamespace() {
		return ContactInfo.class.getName();
	}

	/**
	 * 获取所有邮箱联系人节点
	 * @param userId 用户Id
	 * @param sqlQueryFilter 查询参数对象
	 * @return
	 */
	public List<ContactInfo> getAllMailContact(String userId,SqlQueryFilter sqlQueryFilter){
		Map<String,Object> params=sqlQueryFilter.getParams();
		params.put("userId", userId);               //将userId加入到查询参数中去
		SortParam sortParam=sqlQueryFilter.getSortParam();
			if(sortParam!=null){  //如果排序参数不为空
				String property=sortParam.getProperty();
				String direction=sortParam.getDirection();
				params.put("property", property+"_"); //把排序字段放入查询参数中
				params.put("direction", direction);//把排序顺序放入查询参数中
			}
		return this.getBySqlKey("getAllMailContact", params,sqlQueryFilter.getPage());
	}
	
	/**
	 * 根据获取分组Id获取联系人节点
	 * @param sqlQueryFilter 查询参数对象
	 * @return
	 */
	public List<ContactInfo> getByGroupId(SqlQueryFilter sqlQueryFilter){
	Map<String,Object> params=sqlQueryFilter.getParams();
	SortParam sortParam=sqlQueryFilter.getSortParam();
		if(sortParam!=null){ //如果排序参数不为空
			String property=sortParam.getProperty();
			String direction=sortParam.getDirection();
			params.put("property", property+"_");//把排序字段放入查询参数中
			params.put("direction", direction);//把排序顺序放入查询参数中
		}
		return this.getBySqlKey("getByGroupId", params, sqlQueryFilter.getPage());
	}
}
