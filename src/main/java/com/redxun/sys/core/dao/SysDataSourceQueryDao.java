
/**
 * 
 * <pre> 
 * 描述：数据源定义管理 DAO接口
 * 作者:ray
 * 日期:2017-02-07 09:03:54
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.core.dao;

import com.redxun.sys.core.entity.SysDataSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class SysDataSourceQueryDao extends BaseMybatisDao<SysDataSource> {

	@Override
	public String getNamespace() {
		return SysDataSource.class.getName();
	}
	
	public Integer isExist(SysDataSource dataSource){
		Integer rtn=(Integer) this.getOne("isExist", dataSource);
		return rtn;
	}
	
	public List<SysDataSource> getInitStart(){
		List<SysDataSource> list=this.getBySqlKey("getInitStart", null);
		return list;
	}
	
	public SysDataSource getByAlias(String alias){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("alias", alias);
		SysDataSource dataSource=this.getUnique("getByAlias", params);
		return dataSource;
		
	}

}

