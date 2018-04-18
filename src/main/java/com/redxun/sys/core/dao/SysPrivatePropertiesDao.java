
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

import java.util.List;

import com.redxun.sys.core.entity.SysPrivateProperties;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class SysPrivatePropertiesDao extends BaseJpaDao<SysPrivateProperties> {


	@Override
	protected Class getEntityClass() {
		return SysPrivateProperties.class;
	}

	public  SysPrivateProperties getByProId(String proId,String tenantId){
		String hql="from SysPrivateProperties spp where spp.proId=? and spp.tenantId=?";
		return (SysPrivateProperties) this.getUnique(hql, proId,tenantId);
	}
	
	public List<SysPrivateProperties> getAllByProId(String proId){
		String hql="from SysPrivateProperties spp where spp.proId=?";
		return this.getByJpql(hql, proId);
	}
}

