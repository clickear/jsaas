package com.redxun.org.impl.profile;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.util.BeanUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.context.IProfileService;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.manager.OsGroupManager;

@Service
public class SubGroupProfileService implements IProfileService {
	
	@Resource
	private OsGroupManager osGroupManager;

	@Override
	public String getType() {
		return "subGroup";
	}

	@Override
	public String getName() {
		return "组织或以下";
	}

	@Override
	public Set<String> getCurrentProfile() {
		String userId=ContextUtil.getCurrentUserId();
		List<OsGroup> deps=osGroupManager. getBelongDeps(userId);
		if(BeanUtil.isEmpty(deps)) return Collections.emptySet();
		Set<String> set=new HashSet<String>();
		for(OsGroup group:deps){
			String path=group.getPath();
			path=StringUtil.trimPrefix(path, "0.");
			path=StringUtil.trimSuffix(path, ".");
			String[] aryGroup=path.split("[.]");
			for(String groupId:aryGroup){
				set.add(groupId);
			}
		}
		return set;
	}

	public static void main(String[] args) {
		/*String a="0.0240000000196046.33333.";
		a=StringUtil.trimPrefix(a, "0.");
		a=StringUtil.trimSuffix(a, ".");
		String[] ary=a.split("[.]");
		System.out.println("ok");*/
	}
}
