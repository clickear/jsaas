package com.redxun.sys.org.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;

import com.redxun.core.database.datasource.DataSourceUtil;
import com.redxun.saweb.util.IdUtil;
import com.redxun.sys.core.entity.SysAccount;
import com.redxun.sys.core.manager.SysAccountManager;
import com.redxun.sys.org.entity.OsDimension;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.entity.OsRelInst;
import com.redxun.sys.org.entity.OsRelType;
import com.redxun.sys.org.entity.OsUser;

/**
 * 外部组织架构数据导入示例
 * @author mansan
 *
 */
@Service
public class OrgDataImportManager {
	
	@Autowired
	OsGroupManager osGroupManager;
	@Autowired
	OsUserManager osUserManager;
	@Autowired
	OsDimensionManager osDemensionManager;
	@Autowired
	SysAccountManager sysAccountManager;
	@Autowired
	OsRelInstManager osRelInstManager;
	
	public void doSynDepartment(String tenantId) throws Exception{
		JdbcTemplate jdbcTemplate=DataSourceUtil.getJdbcTempByDsAlias("cemsys");
		String sql="select * from rm_department";
		List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);
		OsDimension osDemension=osDemensionManager.get(OsDimension.DIM_ADMIN_ID);
		List<OsGroup> osGroups=new ArrayList<OsGroup>();
		for(int i=0;i<list.size();i++){
			Map<String,Object> row=list.get(i);
			String id=(String)row.get("id");
			
			OsGroup osGroup=osGroupManager.get(id);
			boolean isNew=false;
			if(osGroup==null){
				osGroup=new OsGroup();
				isNew=true;
			}
			String depCode=(String)row.get("dpCode");
			String dpName=(String)row.get("dpName");
			String dpType=(String)row.get("dpType");
			String addressCode=(String)row.get("addressCode");
			//上级部门Id
			String belongDpId=(String)row.get("belongDpId");
			osGroup.setGroupId(id);
			osGroup.setName(dpName);
			osGroup.setKey(depCode);
			osGroup.setTenantId(tenantId);

			osGroup.setParentId(belongDpId);
			osGroup.setStatus("ENABLE");
			osGroup.setIsMain("NO");
			osGroup.setAreaCode(addressCode);
			if(StringUtils.isNotEmpty(dpType) && StringUtils.isNumeric(dpType)){
				osGroup.setRankLevel(new Integer(dpType));
			}
			osGroup.setOsDimension(osDemension);
			osGroup.setSn(0);
			
			if(isNew){
				osGroupManager.create(osGroup);
			}else{
				osGroupManager.update(osGroup);
			}
			osGroups.add(osGroup);
		}
		
		updateGroupPath(osGroups);
		
	}
	
	
	/**
	 * 更新部门的路径
	 * @param groups
	 */
	private void updateGroupPath(List<OsGroup> groups){
		for(OsGroup group: groups){	
			StringBuffer sb=new StringBuffer();
			doGenOrgPath(group,sb);
			group.setPath(sb.toString());
			osGroupManager.update(group);
		}
	}
	/**
	 * 更新组织的路径 
	 * @param group
	 * @param sb
	 */
	private void doGenOrgPath(OsGroup group,StringBuffer sb){

		if(StringUtils.isEmpty(group.getParentId()) || "0".equals(group.getParentId())){
			group.setChilds(1);
			group.setParentId("0");
			
			String path="0."+ group.getGroupId()+".";
			sb.insert(0, path);
			
			return;
		}
		
		sb.insert(0,group.getGroupId()+".");
		
		OsGroup parentGroup=osGroupManager.get(group.getParentId());
		
		if(parentGroup==null){
			return;
		}
		
		group.setChilds(1);
		
		doGenOrgPath(parentGroup,sb);

	}


	/**
	 * 同步部门用户
	 * @throws Exception
	 */
	public void doSyncDepUser(String tenantId,String domain) throws Exception{
		JdbcTemplate jdbcTemplate=DataSourceUtil.getJdbcTempByDsAlias("cemsys");
		String sql="select * from sm_user";
		List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);
		for(int i=0;i<list.size();i++){
			Map<String,Object> row=list.get(i);
			String id=(String)row.get("id");
			String userName=(String)row.get("userName");
			String password=(String)row.get("password");
			//String userTypeNum=(String)row.get("userTypeNum");
			String departmentId=(String)row.get("departmentId");
			String gender=(String)row.get("gender");
			OsUser osUser=osUserManager.get(id);
			boolean isNew=false;
			if(osUser==null){
				isNew=true;
				osUser=new OsUser();
			}
			osUser.setUserId(id);
			osUser.setFullname(userName);
			osUser.setTenantId(tenantId);
			osUser.setStatus("IN_JOB");
			osUser.setUserNo(userName);
			osUser.setSex(gender);
			
			if(isNew){
				osUserManager.create(osUser);
			}else{
				//删除旧的用户，同时更新
				SysAccount sysAcc=sysAccountManager.getByNameDomain(userName, domain);
				if(sysAcc!=null){
					sysAccountManager.delete(sysAcc.getAccountId());
				}
				//删除旧的用户账号关系
				osRelInstManager.delByGroupIdUserIdRelTypeId(departmentId, id, OsRelType.REL_CAT_GROUP_USER_BELONG_ID);
				osUserManager.update(osUser);
			}
			
			SysAccount acc=new SysAccount();
			acc.setAccountId(id);
			acc.setName(userName);
			acc.setFullname(userName);
			acc.setUserId(id);
			acc.setPwd(password);
			acc.setStatus("ENABLED");
			acc.setTenantId(tenantId);
			acc.setDomain(domain);
			
			sysAccountManager.create(acc);
			
			if(StringUtils.isNotEmpty(departmentId)){
				OsRelInst inst=new OsRelInst();
				inst.setInstId(IdUtil.getId());
				inst.setParty1(departmentId);
				inst.setParty2(id);
				inst.setRelType("GROUP-USER");
				inst.setStatus("ENABLED");
				inst.setIsMain("YES");
				inst.setDim1(OsDimension.DIM_ADMIN_ID);
				inst.setRelTypeKey("GROUP-USER-BELONG");
				inst.setRelTypeId("1");
				inst.setTenantId(tenantId);
				osRelInstManager.create(inst);
			}
		}
	}
	
	
	/**
	 * 删除原来的维度下的数据
	 */
	public void delDepAndUsers(){
		List<SysAccount> accountList=sysAccountManager.getAll();
		for(SysAccount acc:accountList){
			if(!acc.getName().equals("supacc")){
				sysAccountManager.deleteObject(acc);
				osUserManager.delete(acc.getUserId());
				osRelInstManager.delByUserId(acc.getUserId());
			}
		}
		
		List<OsGroup> groupList=osGroupManager.getByDimId("1");
		for(OsGroup g:groupList){
			if(!"IT_SUP".equals(g.getKey())){
				osGroupManager.delete(g.getGroupId());
			}
		}
	}
}
