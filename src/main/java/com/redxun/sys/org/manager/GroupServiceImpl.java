package com.redxun.sys.org.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.org.api.model.GroupType;
import com.redxun.org.api.model.IGroup;
import com.redxun.org.api.service.GroupService;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.entity.OsRelType;


@Service
public class GroupServiceImpl implements GroupService {
	
	@Resource
	OsGroupManager osGroupManager;
	

	@Override
	public List getByTypeAndUserId(String groupType, String userId) {
		
		List<OsGroup>  groupList= osGroupManager.getByUserIdRelTypeId(userId,OsRelType.REL_CAT_GROUP_USER_BELONG_ID);
		List<IGroup> rtnList=new ArrayList<IGroup>();
		for(OsGroup group:groupList){
			if(group.getType().equals(groupType)){
				rtnList.add(group);
			}
		}
		return rtnList;
	
	}

	@Override
	public Map<String, List<IGroup>> getGroupsMapByUserId(String userId) {
		Map<String,List<IGroup>> groupMap=new HashMap<String, List<IGroup>>();
		
		List<OsGroup>  groupList= osGroupManager.getByUserIdRelTypeId(userId,OsRelType.REL_CAT_GROUP_USER_BELONG_ID);
		
		for(OsGroup group:groupList){
			String groupType=group.getType();
			List<IGroup> list=groupMap.get(groupType);
			if(groupMap.containsKey(groupType)){
				list=groupMap.get(groupType);
				list.add(group);
			}
			else{
				list=new ArrayList<IGroup>();
				list.add(group);
				groupMap.put(groupType, list);
			}
		}
		
		return groupMap;
	}

	@Override
	public List<IGroup> getGroupsByUserId(String userId) {
		List  groupList= osGroupManager.getByUserIdRelTypeId(userId,OsRelType.REL_CAT_GROUP_USER_BELONG_ID);
		return groupList;
	}
	
	@Override
	public List<String> getGroupsIdByUserId(String userId) {
		List<OsGroup>  groupList= osGroupManager.getByUserIdRelTypeId(userId,OsRelType.REL_CAT_GROUP_USER_BELONG_ID);
		List<String> groupIds=new ArrayList<String>();
		for(OsGroup group:groupList){
			groupIds.add(group.getGroupId());
		}
		return groupIds;
	}

	@Override
	public IGroup getById(String groupType, String groupId) {
		return  osGroupManager.get(groupId);
	}

	@Override
	public IGroup getByCode(String groupType, String code) {
		return null;
	}

	@Override
	public List<GroupType> getGroupTypes() {
		GroupType orgType=new GroupType(GroupType.GROUP_TYPE_ORG, "行政组织");
		GroupType roleType=new GroupType(GroupType.GROUP_TYPE_ROLE, "角色");
		List<GroupType> list=new ArrayList<GroupType>();
		list.add(orgType);
		list.add(roleType);
		return list;
	}

	@Override
	public Map<String, List<String>> getGroupIdsMapByUserId(String userId) {
		Map<String,List<String>> groupMap=new HashMap<String, List<String>>();
		
		List<OsGroup>  groupList= osGroupManager.getByUserIdRelTypeId(userId,OsRelType.REL_CAT_GROUP_USER_BELONG_ID);
		
		for(OsGroup group:groupList){
			String groupType=group.getType();
			List<String> list=groupMap.get(groupType);
			if(groupMap.containsKey(groupType)){
				list=groupMap.get(groupType);
				list.add(group.getGroupId());
			}
			else{
				list=new ArrayList<String>();
				list.add(group.getGroupId());
				groupMap.put(groupType, list);
			}
		}
		
		return groupMap;
		
		
		
	}

	@Override
	public IGroup getMainByUserId(String userId) {
		IGroup group= osGroupManager.getMainDeps(userId);
		return group;
	}

	@Override
	public IGroup getById(String groupId) {
		OsGroup org=osGroupManager.get(groupId);
		return org;
	}

	@Override
	public String getFullDepNames(String userId) {
		return osGroupManager.getMainDepFullNames(userId);
	}

}
