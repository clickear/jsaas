package com.redxun.org.impl.profile;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.redxun.org.api.context.IProfileService;
import com.redxun.saweb.context.ContextUtil;

@Service
public class UserProfileService implements IProfileService {

	@Override
	public String getType() {
		return "user";
	}

	@Override
	public String getName() {
		return "用户";
	}

	@Override
	public Set<String> getCurrentProfile() {
		String currentUser=ContextUtil.getCurrentUserId();
		Set<String> set=new HashSet<String>();
		set.add(currentUser);
		return set;
	}

}
