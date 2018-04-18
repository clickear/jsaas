package com.redxun.sys.org.dao;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.JpaDao;
import com.redxun.sys.org.entity.OsGroupSys;
/**
 * 
 * @author mansan
 * 用户组系统数据访问
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Repository
public class OsGroupSysDao extends JpaDao<OsGroupSys,String>{
	@Override
	protected Class getEntityClass() {
		return OsGroupSys.class;
	}
	/**
	 * 删除用户组下的授权访问的子系统
	 * @param groupId
	 */
	public void deleteByGroupId(String groupId){
		String ql="delete from OsGroupSys where groupId=?";
		this.delete(ql, new Object[]{groupId});
	}
}
