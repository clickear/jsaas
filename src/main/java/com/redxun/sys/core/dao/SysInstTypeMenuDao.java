
/**
 * 
 * <pre> 
 * 描述：机构类型授权菜单 DAO接口
 * 作者:mansan
 * 日期:2017-12-19 11:00:46
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.core.dao;

import com.redxun.sys.core.entity.SysInstTypeMenu;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class SysInstTypeMenuDao extends BaseJpaDao<SysInstTypeMenu> {


	@Override
	protected Class getEntityClass() {
		return SysInstTypeMenu.class;
	}

	public void deleteByInstTypeId(String typeId) {
		String hql = "delete from SysInstTypeMenu where instTypeId = ?";
		this.delete(hql, new Object[]{typeId});
		
	}

}

