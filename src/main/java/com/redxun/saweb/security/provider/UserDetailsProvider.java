package com.redxun.saweb.security.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.redxun.org.api.model.IGroup;
import com.redxun.org.api.service.GroupService;
import com.redxun.org.api.service.UserService;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.entity.OsRelInst;
import com.redxun.sys.org.entity.OsRelType;
import com.redxun.sys.org.entity.OsUser;
import com.redxun.sys.org.manager.OsRelInstManager;
/**
 * 用户验证
 * @author keitch
 *  @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class UserDetailsProvider implements UserDetailsService {

    @Resource
    UserService  userService;
    @Resource
    GroupService groupService;
    @Resource
    OsRelInstManager osRelInstManager;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

    	OsUser user = (OsUser) userService.getByUsername(username);
        
        if (user == null) return null;
        
    	List<? extends IGroup> groupList= groupService.getGroupsByUserId(user.getUserId());
    	
    	IGroup mainGroup=groupService.getMainByUserId(user.getUserId());
    	if(mainGroup!=null){
    		OsGroup mainDep=(OsGroup)mainGroup;
    		user.setMainDep(mainDep);
    		//OsGroup company=(OsGroup)groupService.getById(((OsGroup)mainGroup).getParentId());
    		//user.setCompany(company);
    		if(StringUtils.isNotEmpty(mainDep.getPath())){
    			String[] mainDepIds=mainDep.getPath().split("[.]");
    			StringBuffer depBuf=new StringBuffer();
    			for(String depId:mainDepIds){
    				if("0".equals(depId)){
    					continue;
    				}
    				OsGroup group=(OsGroup)groupService.getById(depId);
    				if(group!=null){
    					depBuf.append(group.getName()).append("/");
    				}
    			}
    			
    			user.setDepPathNames(depBuf.toString());
    		}
    	}
    	
    	Collection<GrantedAuthority> roles=new ArrayList<GrantedAuthority>();
		for(IGroup group:groupList){
			user.getGroupIds().add(group.getIdentityId());
			GrantedAuthority role=new SimpleGrantedAuthority(group.getKey());
			roles.add(role);
		}
		user.setAuthorities(roles);
        //获得当前用户的上下级配置信息
		OsRelInst upLowRelInst=osRelInstManager.getByParty2RelTypeId(user.getUserId(), OsRelType.REL_CAT_USER_UP_LOWER_ID);
		user.setUpLowRelInst(upLowRelInst);
        return user;
    }
}
