package com.redxun.sys.org.manager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.IPage;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.SqlQueryFilter;
import com.redxun.org.api.model.IdentityInfo;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.IdUtil;
import com.redxun.sys.core.entity.SysAccount;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.manager.SysAccountManager;
import com.redxun.sys.core.manager.SysFileManager;
import com.redxun.sys.org.dao.OsRelInstDao;
import com.redxun.sys.org.dao.OsUserDao;
import com.redxun.sys.org.dao.OsUserQueryDao;
import com.redxun.sys.org.entity.OsDimension;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.entity.OsRelInst;
import com.redxun.sys.org.entity.OsRelType;
import com.redxun.sys.org.entity.OsUser;
/**
 * <pre> 
 * 描述：用户业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class OsUserManager extends BaseManager<OsUser>{
	@Resource
	private OsUserDao osUserDao;
	@Resource
	private OsRelInstDao osRelInstDao;
	@Resource
	private OsUserQueryDao osUserQueryDao;
	
	@Resource
	private SysAccountManager sysAccountManager;
	@Resource
	private OsRelTypeManager osRelTypeManager;
	@Resource
	private SysFileManager sysFileManager;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return osUserDao;
	}
	
	public List<OsUser> getByDepIdGroupId(String depId,String groupId){
		return osUserQueryDao.getByDepIdGroupId(depId, groupId);
	}
	
	/**
	 * 查找某个用户组下某种关系的用户，并且可按用户编号、用户名进行过滤
	 * @param groupId
	 * @param relTypeId
	 * @param userNo
	 * @param fullname
	 * @param page
	 * @return
	 */
	public List<OsUser> getByGroupIdRelTypeId(String groupId,String relTypeId,String userNo,String fullname,IPage page){
		return osUserQueryDao.getByGroupIdRelTypeId(groupId, relTypeId, userNo, fullname, page);
	}
	
	/**
	 * 获得认证实例的用户Id及用户名 
	 * @param identityInfos
	 * @return  返回数组格式如：String[]{'1,2','张三,李四'}
	 */
	public String[] getUserInfoIdNames(Collection<IdentityInfo> identityInfos){
		StringBuffer userNames=new StringBuffer();
		StringBuffer userIds=new StringBuffer();
		//显示用户
		for(IdentityInfo info:identityInfos){
			if(info instanceof OsUser){
				OsUser user=(OsUser)info;
				userNames.append(user.getFullname()).append(",");
				userIds.append(user.getUserId()).append(",");
			}else if(info instanceof OsGroup){
				OsGroup osGroup=(OsGroup)info;
				List<OsUser> osUsers=getBelongUsers(osGroup.getGroupId());
				for(OsUser user:osUsers){
					userNames.append(user.getFullname()).append(",");
					userIds.append(user.getUserId()).append(",");
				}
			}
		}

		if(userNames.length()>0){
			userNames.deleteCharAt(userNames.length()-1);
			userIds.deleteCharAt(userIds.length()-1);
		}
		
		return new String[]{userIds.toString(),userNames.toString()};
	}
	
	@Override
	public void create(OsUser entity) {
		super.create(entity);
		createUserGroups(entity);
	}
	
	public void createUserGroups(OsUser entity){
		OsRelType osRelType=osRelTypeManager.get(OsRelType.REL_CAT_GROUP_USER_BELONG_ID);
		//若主部门
		if(entity.getMainDep()!=null){
			
	    	OsRelInst inst=new OsRelInst();
	    	inst.setParty1(entity.getMainDep().getGroupId());
	    	inst.setParty2(entity.getUserId());
	    	inst.setRelTypeKey(osRelType.getKey());
	    	inst.setRelType(osRelType.getRelType());
	    	inst.setOsRelType(osRelType);
	    	inst.setDim1(OsDimension.DIM_ADMIN_ID);
	    	inst.setStatus(MBoolean.ENABLED.toString());
	    	inst.setInstId(IdUtil.getId());
	    	inst.setTenantId(entity.getTenantId());
	    	inst.setIsMain(MBoolean.YES.name());
	    	osRelInstDao.create(inst);
		}
		//从部门
		if(entity.getCanDeps()!=null){
			for(OsGroup osGroup:entity.getCanDeps()){
				OsRelInst inst=new OsRelInst();
		    	inst.setParty1(osGroup.getGroupId());
		    	inst.setParty2(entity.getUserId());
		    	inst.setRelTypeKey(osRelType.getKey());
		    	inst.setRelType(osRelType.getRelType());
		    	inst.setOsRelType(osRelType);
		    	inst.setDim1(OsDimension.DIM_ADMIN_ID);
		    	inst.setStatus(MBoolean.ENABLED.toString());
		    	inst.setInstId(IdUtil.getId());
		    	inst.setIsMain(MBoolean.NO.name());
		    	inst.setTenantId(entity.getTenantId());
		    	osRelInstDao.create(inst);
			}
		}
		
		//其他用户组
		if(entity.getCanGroups()!=null){
			for(OsGroup osGroup:entity.getCanGroups()){
				OsRelInst inst=new OsRelInst();
		    	inst.setParty1(osGroup.getGroupId());
		    	inst.setParty2(entity.getUserId());
		    	inst.setRelType(osRelType.getRelType());
		    	inst.setRelTypeKey(osRelType.getKey());
		    	inst.setOsRelType(osRelType);
		    	if(osGroup.getOsDimension()!=null){
		    		inst.setDim1(osGroup.getOsDimension().getDimId());
		    	}
		    	inst.setStatus(MBoolean.ENABLED.toString());
		    	inst.setInstId(IdUtil.getId());
		    	inst.setIsMain(MBoolean.NO.name());
		    	inst.setTenantId(entity.getTenantId());
		    	osRelInstDao.create(inst);
			}
		}
	}
	
	@Override
	public void update(OsUser entity) {
		super.update(entity);
		//删除旧的关系
		osRelInstDao.delByParty2(entity.getUserId());
		//创建新的关系用户
		createUserGroups(entity);
	}
	
	/**
	 * 通过账号获得用户
	 * @param accountName
	 * @return
	 */
	public List<OsUser> getByAccName(String accountName){
		List<OsUser> osUsers=new ArrayList<OsUser>();
		SysAccount account=sysAccountManager.getByNameTenantId(accountName, ContextUtil.getCurrentTenantId());
		
		if(account!=null&& StringUtils.isNotEmpty(account.getUserId())){
			OsUser osUser=get(account.getUserId());
			osUsers.add(osUser);
		}
		return osUsers;
	}
	
	/**
	 * 通过账号获得唯一的用户信息
	 * @param accName
	 * @return
	 */
	public OsUser getSingleUserByAccName(String accName,String tenantId){
		SysAccount account=sysAccountManager.getByNameTenantId(accName, tenantId);
		
		if(account!=null&& StringUtils.isNotEmpty(account.getUserId())){
			OsUser osUser=get(account.getUserId());
			return osUser;
		}
		return null;
	}

	/**
	 * 查找某个用户组下的用户
	 * @param groupId
	 * @param relTypeId
	 * @return
	 */
	public List<OsUser> getByGroupIdRelTypeId(String groupId,String relTypeId){
		return osUserQueryDao.getByGroupIdRelTypeId(groupId, relTypeId);
	}
	/**
	 * 获得某个用户组下从属关系的用户
	 * @param groupId
	 * @return
	 */
	public List<OsUser> getBelongUsers(String groupId){
		return osUserQueryDao.getByGroupIdRelTypeId(groupId,OsRelType.REL_CAT_GROUP_USER_BELONG_ID);
	}
	
	/**
	 * 查找某个用户组下的用户,并且按条件过滤
	 * @param filter
	 * @return
	 */
	public List<OsUser> getByGroupIdRelTypeId(SqlQueryFilter filter){
		return osUserQueryDao.getByGroupIdRelTypeId(filter);
	}
	
	public List<OsUser> getByGroupPathRelTypeId(SqlQueryFilter filter){
		return osUserQueryDao.getByGroupPathRelTypeId(filter);
	}
	
	@Override
	public void delete(String id) {
		osRelInstDao.delByParty2(id);
		//同时删除该与该用户关联的账号
		sysAccountManager.delByUserId(id);
		super.delete(id);
	}
	

	/**
	 * 初始化管理员
	 */
	public OsUser initAdminUser(String instId,String fullname,String email,String mobile,String userNo){
		OsUser osUser=new OsUser();
		osUser.setFullname(fullname);
		osUser.setEmail(email);
		osUser.setUserNo(userNo);
		osUser.setTenantId(instId);
		osUser.setStatus(OsUser.STATUS_IN_JOB);
		osUser.setFrom(OsUser.FROM_SYS);
		osUser.setSex("Male");
		osUserDao.create(osUser);
		return osUser;
	}
	
	/**
	 * 搜索用户。
	 * @param filter
	 * @return
	 */
	public List<OsUser> getByFilter(QueryFilter filter){
		return osUserQueryDao.getByFilter(filter);
	} 
	
	/**
	 * 获取部门下用户。
	 * @param groupId
	 * @return
	 */
	public List<OsUser> getByGroupId(String groupId){
		return osUserQueryDao.getByGroupId(groupId);
	}
	
	public List<OsUser> getUsersByIds(List<String> userIds){
		return  osUserDao.getUsersByIds(userIds);
	}
	
	/**
	 * 将用户头像暂时设置到photo里方便前端获取
	 * @param user
	 * @param request
	 */
	public void virtualSetUserPhoto(OsUser user,HttpServletRequest request){
		String photoId=user.getPhoto();
		String fullPath ="";
		if(StringUtils.isNotBlank(photoId)){
			SysFile sysFile=sysFileManager.get(photoId);
			fullPath = request.getContextPath() + "/sys/core/file/download/"+sysFile.getFileId()+".do";
		}else{
			fullPath = request.getContextPath()+"/styles/images/index/index_tap_06.png";
		}
		user.setPhoto(fullPath);
	}
}