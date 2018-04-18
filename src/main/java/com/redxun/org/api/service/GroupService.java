package com.redxun.org.api.service;

import java.util.List;
import java.util.Map;

import com.redxun.org.api.model.GroupType;
import com.redxun.org.api.model.IGroup;

/**
 * 用户组服务。
 * @author ray
 *
 */
public interface GroupService {
	
	/**
	 * 根据用户ID和组类别获取该用户所在的组织。
	 * <pre>
	 *  例如：张三在可以有多个岗位，这里就获取岗位他的岗位列表。
	 * </pre>
	 * @param groupType		用户组类型
	 * @param userId		用户ID
	 * @return
	 */
	List<IGroup>  getByTypeAndUserId(String groupType,String userId);
	
	
	/**
	 *根据用户ID获取用户当前所在的组，这个包括他所有的组。
	 * <pre>
	 * 比如这个用户有如下的组：
	 * role(角色): 角色列表
	 * pos(岗位) : 岗位列表
	 * org(部门) : 部门列表
	 * </pre>
	 * @param userId	用户ID
	 * @return	返回一个Map，键为维度类型，值为组列表。
	 */
	Map<String,List<IGroup>> getGroupsMapByUserId(String userId);
	
	/**
	 * 获取用户组织。
	 * @param userId
	 * @return
	 */
	Map<String,List<String>> getGroupIdsMapByUserId(String userId);
	
	
	/**
	 * 根据用户ID获取用户所属的组,这里不对组类别进行区分，返回统一的组织列表，可能包括角色，部门等。
	 * @param userId
	 * @return
	 */
	List<IGroup> getGroupsByUserId(String userId);
	
	/**
	 * 根据用户组返回组ID的列表。
	 * @param userId
	 * @return
	 */
	List<String> getGroupsIdByUserId(String userId);
	
	
	/**
	 * 返回用户的主组织。
	 * @param userId
	 * @return
	 */
	IGroup getMainByUserId(String userId);
	
	/**
	 * 获是用户所在的主组织对应的主部门路径
	 * @param userId
	 * @return
	 */
	String getFullDepNames(String userId);
	
	
	/**
	 * 根据组织ID和类型获取组织对象。
	 * 
	 * @param groupType
	 * @param groupId
	 * @return
	 */
	IGroup getById(String groupType,String groupId);
	
	
	/**
	 * 根据组ID获取用户组。
	 * @param groupType
	 * @param groupId
	 * @return
	 */
	IGroup getById(String groupId);
	
	
	/**
	 * 根据组织ID和类型获取组织对象。
	 * @param groupType 组织类型
	 * @param code		组织别名
	 * @return
	 */
	IGroup getByCode(String groupType,String code);
	
	/**
	 * 返回当前的组织类型。
	 * <pre>
	 *  返回平台支持的组织类型：
	 *  比如：
	 *  role：角色
	 *  pos: 岗位
	 *  org: 部门
	 * </pre>
	 * @return
	 */
	List<GroupType> getGroupTypes();

}
