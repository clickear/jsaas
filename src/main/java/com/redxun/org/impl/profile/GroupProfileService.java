package com.redxun.org.impl.profile;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.util.BeanUtil;
import com.redxun.org.api.context.IProfileService;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.manager.OsGroupManager;

@Service
public class GroupProfileService implements IProfileService {
	
	@Resource
	private OsGroupManager osGroupManager;

	@Override
	public String getType() {
		return "group";
	}

	@Override
	public String getName() {
		return "用户组";
	}

	@Override
	public Set<String> getCurrentProfile() {
		String userId=ContextUtil.getCurrentUserId();
		List<OsGroup> list= osGroupManager.getBelongGroups(userId);
		if(BeanUtil.isEmpty(list)) return Collections.emptySet();
		Set<String> set=new HashSet<String>();
		for(OsGroup group:list){
			set.add(group.getGroupId());
		}
		return set;
	}

}
