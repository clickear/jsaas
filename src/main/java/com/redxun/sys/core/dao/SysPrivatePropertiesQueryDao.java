
/**
 * 
 * <pre> 
 * 描述：私有参数 DAO接口
 * 作者:ray
 * 日期:2017-06-21 10:30:22
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.core.dao;

import java.util.HashMap;
import java.util.Map;

import com.redxun.sys.core.entity.SysPrivateProperties;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class SysPrivatePropertiesQueryDao extends BaseMybatisDao<SysPrivateProperties> {

	@Override
	public String getNamespace() {
		return SysPrivateProperties.class.getName();
	}

	public void deleteByProId(String proId){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("proId", proId);
		this.deleteBySqlKey("deleteByProId", proId);
	}
}

