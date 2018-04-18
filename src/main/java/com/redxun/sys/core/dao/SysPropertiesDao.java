
/**
 * 
 * <pre> 
 * 描述：系统参数 DAO接口
 * 作者:ray
 * 日期:2017-06-21 11:22:36
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.core.dao;

import com.redxun.sys.core.entity.SysProperties;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class SysPropertiesDao extends BaseJpaDao<SysProperties> {


	@Override
	protected Class getEntityClass() {
		return SysProperties.class;
	}
	
	public SysProperties getPropertiesByName(String name){
		String hql="from SysProperties sp where sp.alias=?";
		return (SysProperties) this.getUnique(hql, name);
	}
}

