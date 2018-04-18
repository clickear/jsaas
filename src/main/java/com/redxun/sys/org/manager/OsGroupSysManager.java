package com.redxun.sys.org.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.sys.org.dao.OsGroupSysDao;
import com.redxun.sys.org.entity.OsGroupSys;
/**
 * 用户组系统服务类
 * @author mansan
 *@Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Service
public class OsGroupSysManager extends BaseManager<OsGroupSys>{
	@Resource
	private OsGroupSysDao osGroupSysDao;
	@Override
	protected IDao getDao() {
		return osGroupSysDao;
	}
	
	/**
	 * 删除用户组下的授权访问的子系统
	 * @param groupId
	 */
	public void deleteByGroupId(String groupId){
		osGroupSysDao.deleteByGroupId(groupId);
	}
}
