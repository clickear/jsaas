package com.redxun.sys.org.manager;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.redxun.org.api.model.ITenant;
import com.redxun.org.api.model.IUser;
import com.redxun.org.api.service.UserService;
import com.redxun.sys.core.entity.SysAccount;
import com.redxun.sys.core.entity.SysInst;
import com.redxun.sys.core.manager.SysAccountManager;
import com.redxun.sys.core.manager.SysInstManager;
import com.redxun.sys.org.entity.OsUser;

@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	OsUserManager osUserManager;
	@Resource
    SysAccountManager sysAccountManager;
	@Resource
	SysInstManager sysInstManager;
	

	@Override
	public IUser getByUserId(String userId) {
		OsUser osUser= osUserManager.get(userId);
		if(osUser==null) return osUser;
		
		ITenant tenant=  getByTenant(osUser.getTenantId());
		osUser.setTenant(tenant);
		return (IUser) osUser;
	}
	
	private ITenant getByTenant(String tenantId){
		List<SysInst> insts=SysInstManager.getTenants();
		for(SysInst inst:insts){
			if(inst.getInstId().equals(tenantId)){
				return inst;
			}
		}
		return null;
	}

	@Override
	public IUser getByUsername(String username) {
		SysAccount sysAccount= sysAccountManager.getByName(username);
		if(sysAccount==null || StringUtils.isEmpty(sysAccount.getUserId())){
			return null;
		}
		OsUser osUser=osUserManager.get(sysAccount.getUserId());
		if(osUser==null){
			return null;
		}
		osUser.setSysAccount(sysAccount);
		ITenant tenant=  getByTenant(osUser.getTenantId());
		osUser.setTenant(tenant);
		return osUser;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<IUser> getByGroupIdAndType(String groupId, String groupType) {
		return(List)osUserManager.getBelongUsers(groupId);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<IUser> getByGroupId(String groupId) {
		List users= osUserManager.getBelongUsers(groupId);
		return users;
	}

}
