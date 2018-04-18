package com.redxun.sys.org.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.sys.org.dao.OsGroupMenuDao;
import com.redxun.sys.org.entity.OsGroupMenu;

@Service
public class OsGroupMenuManager  extends BaseManager<OsGroupMenu>{
	@Resource
	OsGroupMenuDao osGroupMenuDao;
	
	@Override
	protected IDao getDao() {
		return osGroupMenuDao;
	}
	
	/**
	 * 删除用户组下的授权菜单
	 * @param groupId
	 */
	public void deleteByGroupId(String groupId){
		osGroupMenuDao.deleteByGroupId(groupId);
	}
	
	/**
	 * 获得用户组下的菜单
	 * @param groupId
	 * @return
	 */
	public List<OsGroupMenu> getByGroupId(String groupId){
		return osGroupMenuDao.getByGroupId(groupId);
	}
	
	
	
}
