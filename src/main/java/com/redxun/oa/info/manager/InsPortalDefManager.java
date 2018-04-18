
package com.redxun.oa.info.manager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.oa.info.dao.InsPortalDefDao;
import com.redxun.oa.info.dao.InsPortalDefQueryDao;
import com.redxun.oa.info.dao.InsPortalPermissionDao;
import com.redxun.oa.info.entity.InsPortalDef;
import com.redxun.oa.info.entity.InsPortalPermission;
import com.redxun.org.api.context.ProfileUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.org.manager.OsGroupManager;

/**
 * 
 * <pre> 
 * 描述：ins_portal_def 处理接口
 * 作者:mansan
 * 日期:2017-08-15 16:07:14
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class InsPortalDefManager extends ExtBaseManager<InsPortalDef>{
	@Resource
	private InsPortalDefDao insPortalDefDao;
	@Resource
	private InsPortalDefQueryDao insPortalDefQueryDao;
	@Resource
	private OsGroupManager osGroupManager;
	@Resource
	private InsPortalPermissionDao insPortalPermissionDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return insPortalDefDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return insPortalDefQueryDao;
	}
	

	public InsPortalDef getInsPortalDef(String uId){
		InsPortalDef insPortalDef = get(uId);
		return insPortalDef;
	}
	
	/**
	 * 更新主表
	 * @param insPortalDef
	 */
	public void update(InsPortalDef insPortalDef){
		super.update(insPortalDef);
	}
	
	/**
	 * 保存新的主表
	 * @param insPortalDef
	 */
	public void create(InsPortalDef insPortalDef){
		super.create(insPortalDef);
	}
	
    /**
     * 根据门户key，租户Id，用户Id查找是否有这个门户
     * @param key
     * @param tenantId
     * @param userId
     * @return 如果有则为这个门户portal，如果没则为空
     */
	 public InsPortalDef getByIdKey(String key, String tenantId,String userId){
		return insPortalDefDao.getByIdKey(key, tenantId, userId);
    }
	 
	    /**
	     * 根据门户Key，租户Id查找是否有这个门户
	     * @param key
	     * @param tenantId
	     * @return 如果有则为这个门户portal，如果没则为空
	     */
	public InsPortalDef getByKey(String key, String tenantId){
		return insPortalDefDao.getByKey(key, tenantId);
	}
	
	public InsPortalDef getPortalDef(){
		String userId = ContextUtil.getCurrentUserId();
		Map<String,Set<String>> profiles = ProfileUtil.getCurrentProfile();
		Set<String> groupIds = profiles.get("group");
		Set<String> subGroupIds = profiles.get("subGroup");
		List<InsPortalDef> portals = new ArrayList<InsPortalDef>();
		portals.addAll(getPortalDefByUserId(userId));
		if(groupIds!=null&&groupIds.size()>0){
			for(String ids:groupIds){
				portals.addAll(getPortalDefByGroupId(ids));
			}
		}
		if(subGroupIds!=null&&subGroupIds.size()>0){
			for(String ids:subGroupIds){
				portals.addAll(getPortalDefBySubGroupId(ids));
			}
		}
		
		InsPortalDef portal = getPriorityPortal(portals);

		if(portal!=null&&StringUtils.isEmpty(portal.getPortId())){
			portal = insPortalDefDao.getDefaultPortal();
		}
		
		return portal;
	}
	
	/**
	 * 计算优先级最高的布局
	 * @param portals
	 * @return
	 */
	public InsPortalDef getPriorityPortal(List<InsPortalDef> portals){
		InsPortalDef portal = new InsPortalDef();
		if(portals.size()==0){
			portal = insPortalDefDao.getDefaultPortal();
			return portal;
		}
		int i = 0;
		for(InsPortalDef p:portals){
			if(p.getPriority()>i){
				i = p.getPriority();
				portal = p;
			}
		}
		
		return portal;
	}
	
	public List<InsPortalDef> getPortalDefByUserId(String userId){
		List<InsPortalPermission> portalPermissions = insPortalPermissionDao.getByUserId(userId);
		List<InsPortalDef> portals = new ArrayList<InsPortalDef>();
		for(InsPortalPermission p:portalPermissions){
			InsPortalDef portal = get(p.getLayoutId());
			if(portal!=null){
				portals.add(portal);
			}			
		}		
		return portals;
	}
	
	public List<InsPortalDef> getPortalDefByGroupId(String groupId){
		List<InsPortalPermission> portalPermissions = insPortalPermissionDao.getByGroupId(groupId);
		List<InsPortalDef> portals = new ArrayList<InsPortalDef>();
		for(InsPortalPermission p:portalPermissions){
			InsPortalDef portal = get(p.getLayoutId());
			if(portal!=null){
				portals.add(portal);
			}			
		}		
		return portals;
	}
	
	public List<InsPortalDef> getPortalDefBySubGroupId(String subGroupId){
		List<InsPortalPermission> portalPermissions = insPortalPermissionDao.getBySubGroupId(subGroupId);
		List<InsPortalDef> portals = new ArrayList<InsPortalDef>();
		for(InsPortalPermission p:portalPermissions){
			InsPortalDef portal = get(p.getLayoutId());
			if(portal!=null){
				portals.add(portal);
			}			
		}		
		return portals;
	}
	
}
