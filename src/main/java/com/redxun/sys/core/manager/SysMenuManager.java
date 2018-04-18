package com.redxun.sys.core.manager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.util.BeanUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.security.metadata.MenuGroupModel;
import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.MenuUtil;
import com.redxun.sys.core.dao.SysMenuDao;
import com.redxun.sys.core.dao.SysMenuQueryDao;
import com.redxun.sys.core.entity.MenuNode;
import com.redxun.sys.core.entity.Subsystem;
import com.redxun.sys.core.entity.SysMenu;
/**
 * 
 * <pre> 
 * 描述：SysMenu业务服务类
 * 构建组：ent-base-web
 * 作者：csx
 * 邮箱: chshxuan@163.com
 * 日期:2014年8月1日-上午10:43:05
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class SysMenuManager extends BaseManager<SysMenu>{
	@Resource
	private SysMenuDao sysMenuDao;
	@Resource
	private SysMenuQueryDao sysMenuQueryDao;
	
	@Resource
	private SubsystemManager subsystemManager;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysMenuDao;
	}
	
	
	public List<SysMenu> getBySystemKey(String systemKey){
		Subsystem subsystem=subsystemManager.getByKey(systemKey);
		if(subsystem==null) return new ArrayList<SysMenu>();
		return sysMenuQueryDao.getBySysId(subsystem.getSysId());
	}
	
	
	
	/**
	 * 获得系统下的导航菜单列表
	 * @param sysId
	 * @param isMgr
	 * @return
	 */
	public List<SysMenu> getUrlMenuBySysIdIsMgr(String sysId){
		return sysMenuDao.getBySysIdIsMgrIsBtnMenu(sysId, MBoolean.NO.name());
	}
	
	/**
	 * 通过系统或是否非功能菜单获得菜单列表
	 * @param sysId
	 * @param isBtnMenu
	 * @return
	 */
	public List<SysMenu> getBySysIdIsBtnMenu(String sysId,String isBtnMenu){
		return sysMenuQueryDao.getBySysIdIsBtnMenu(sysId,isBtnMenu);
	}
	
	public void deleteCascade(String menuId){
		SysMenu sysMenu=sysMenuDao.get(menuId);
		SysMenu parentMenu=sysMenuDao.get(sysMenu.getParentId());
		if(parentMenu!=null && parentMenu.getChilds()>0){
			parentMenu.setChilds(parentMenu.getChilds()-1);
			sysMenuDao.update(parentMenu);
		}
		if(StringUtils.isNotEmpty(sysMenu.getPath())){
			sysMenuDao.delByPath(sysMenu.getPath());
		}else{
			sysMenuDao.delete(sysMenu.getMenuId());
		}
	}
	/**
	 * 创建系统子菜单
	 * @param sysMenu 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	public void createSysMenu(SysMenu sysMenu){
		SysMenu parentMenu=sysMenuDao.get(sysMenu.getParentId());
		int depth=1;
		String parentPath="0.";
		if(parentMenu!=null){
			depth=parentMenu.getDepth()+1;
			Long childs=sysMenuDao.getChildsCount(parentMenu.getMenuId());
			//更新父节点的子节点数
			parentMenu.setChilds(childs.intValue()+1);
			sysMenuDao.update(parentMenu);
			//设置与父菜单一样的子系统配置
			Subsystem subsystem=subsystemManager.get(parentMenu.getSysId());
			sysMenu.setSubsystem(subsystem);
		}
		
		sysMenu.setMenuId(IdUtil.getId());
		sysMenu.setPath(parentPath+sysMenu.getMenuId()+".");
		sysMenu.setDepth(depth);
		sysMenu.setChilds(0);
		int sn=sysMenuDao.getCountsByParentId(sysMenu.getParentId(), sysMenu.getSubsystem().getSysId());
		//获取该父类下的子类数
		sysMenu.setSn(sn+1);
		sysMenuDao.create(sysMenu);
	}
	
	public SysMenu insert(String name,String parentId,String sysId){
		Subsystem subsystem=subsystemManager.get(sysId);
		SysMenu parentSysMenu=sysMenuDao.get(parentId);
		String parentPath="0.";
		int depth=1;
		if(parentSysMenu!=null){
			depth=parentSysMenu.getDepth()+1;
			parentPath=parentSysMenu.getPath();
			Long childs=sysMenuDao.getChildsCount(parentSysMenu.getMenuId());
			//更新父节点的子节点数
			parentSysMenu.setChilds(childs.intValue()+1);
			sysMenuDao.update(parentSysMenu);
		}
		SysMenu sysMenu=new SysMenu();
		sysMenu.setMenuId(IdUtil.getId());
		sysMenu.setKey(sysMenu.getMenuId());
		sysMenu.setName(name);
		sysMenu.setSubsystem(subsystem);
		sysMenu.setPath(parentPath+sysMenu.getMenuId()+".");
		sysMenu.setDepth(depth);
		sysMenu.setParentId(parentId);
		sysMenu.setChilds(0);
		int sn=sysMenuDao.getCountsByParentId(parentId, sysId);
		//获取该父类下的子类数
		sysMenu.setSn(sn+1);
		sysMenuDao.create(sysMenu);
		return sysMenu;
	}
	
	 public List<SysMenu> getByParentIdSysId(String sysId,String parentId){
		 return sysMenuDao.getByParentIdSysId(sysId, parentId);
	 }
	 /**
	  * 获取所有的菜单分组及其下菜单项
	  * @param sysId
	  * @param tenantId
	  * @return 
	  * List<MenuGroup>
	  * @exception 
	  * @since  1.0.0
	  */
	 public List<MenuNode> getMenuGroups(String sysId,boolean isCache){
		
		 List<MenuNode> menuGroups=MenuUtil.getMenuGroups(sysId);
		 if(isCache){
			 //优先从缓存中获取
			 if(menuGroups!=null){
				 return menuGroups;
			 }
		 }
		 menuGroups=new ArrayList<MenuNode>();
		 List<SysMenu> topMenus=sysMenuDao.getByParentIdSysId(sysId, "0");
		 for(SysMenu menu:topMenus){
			 MenuNode mn=new MenuNode();
			 BeanUtil.copyProperties(mn, menu);
			 
			 List<MenuNode> menuItems=getMenuItems(menu);
			 if(menuItems.size()>0){
				 mn.setLeaf(false);
				 mn.setExpanded(true);
			 }else{
				 mn.setLeaf(true);
				 mn.setExpanded(false);
			 }
			 mn.setChildren(menuItems);
			 menuGroups.add(mn);
		 }
		 if(isCache){
			 //加至缓存中
			 MenuUtil.setMenuGroups(sysId, menuGroups);
		 }
		 
		 return menuGroups;
	 }
	 
	 /**
	  *  获取菜单下的子菜单项
	  * @param sysMenu
	  * @return 
	  * List<MenuItem>
	  * @exception 
	  * @since  1.0.0
	  */
	 private List<MenuNode> getMenuItems(SysMenu sysMenu){
		 List<MenuNode> items=new ArrayList<MenuNode>();
		 if(sysMenu.getChilds()<=0) return items;
		 List<SysMenu> subMenus=sysMenuDao.getByParentId(sysMenu.getMenuId());
		 for(SysMenu menu:subMenus){
			 MenuNode item=new MenuNode();
			 BeanUtil.copyProperties(item, menu);
			 if(menu.getChilds()>0){
				 item.setLeaf(false);
				 item.setChildren(getMenuItems(menu));
			 }else{
				 item.setLeaf(true);
			 }
			 items.add(item);
		 }
		 return items;
	 }
	 
	 /**
	  * 获得用户组授权的菜单 
	  * @param groupId
	  * @return
	  */
	 public List<SysMenu> getGrantMenusByGroupId(String groupId){
		 return sysMenuQueryDao.getGrantMenusByGroupId(groupId);
	 }
	 
	 
	 public List<SysMenu> getGrantMenusBySysIdGroupId(String sysId,String groupId){
			return sysMenuQueryDao.getGrantMenusBySysIdGroupId(sysId, groupId);
		}
 	
	
	public List<SysMenu> getGrantMenusBySysIdUserId(String sysId,String userId,String tenantId, String isBtnMenu){
		return sysMenuQueryDao.getGrantMenusBySysIdUserId(sysId, userId,tenantId,isBtnMenu);
	}
	 
	 /**
	  * 获得菜单URL对应的用户组ID列表
	  * @return
	  */
	 public Map<String,Set<String>> getUrlGroupIdMap(){
		 Map<String,Set<String>> map=new HashMap<String, Set<String>>();
		 List<MenuGroupModel> list=sysMenuQueryDao.getMenuGroupUrlMap();
		 for(MenuGroupModel model:list){
			 Set<String> groupIdSet=map.get(model.getUrl());
			
			 if(groupIdSet==null){
				 groupIdSet=new HashSet<String>();
				 map.put(model.getUrl(), groupIdSet);
			 }
			 groupIdSet.add(model.getGroupKey());
		 }
		 return map;
	 }
	 /**
	  * 按父Id获得其下一级的子菜单列表
	  * @param parentId
	  * @return
	  */
	 public List<SysMenu> getByParentId(String parentId){
		 return sysMenuDao.getByParentId(parentId);
	 }
	 
	 /**
	  * 按父Id获得其下一级的子菜单列表,不含按钮
	  * @param parentId
	  * @return
	  */
	 public List<SysMenu> getMenusByParentId(String parentId){
		 return sysMenuDao.getMenusByParentId(parentId);
	 }
	 
	 /**
	  * 取得某个菜单目录下的子菜单目录列表
	  * @param parentId
	  * @param menuKey
	  * @return
	  */
	 public SysMenu getByParentIdMenuKey(String parentId,String menuKey){
		 return sysMenuDao.getByParentIdMenuKey(parentId, menuKey);
	 }
	 
 	/**
     * 通过实体名称及是否为按钮菜单获得其某模块下的功能按钮
     * @param entityName
     * @param isBtnMenu
     * @return
     */
    public List<SysMenu> getByEntityNameIsBtnMenu(String entityName,String isBtnMenu){
    	return sysMenuDao.getByEntityNameIsBtnMenu(entityName, isBtnMenu);
    }
    
    /**
     * 获得某模块下的所有功能按钮配置
     * @param entityName
     * @return
     */
    public List<SysMenu> getModuleButtons(String entityName){
    	return sysMenuDao.getByEntityNameIsBtnMenu(entityName, MBoolean.YES.name());
    }
    
    /**
     * 获得某菜单的所有子菜单
     * @param path
     * @return
     */
    public List<SysMenu> getSysMenuLeftLike(String path){
    	return sysMenuDao.getSysMenuLeftLike(path);
    }
    
    /**
     * 根据机构类型获取菜单.
     * @param instType
     * @return
     */
    public List<SysMenu> getByInstType(String instType){
    	return sysMenuQueryDao.getByInstType(instType);
    }
    
    public List<SysMenu> getBySysId(String sysId){
    	return sysMenuQueryDao.getBySysId(sysId);
    }
    
  
    
    public SysMenu getByKey(String key){
    	return sysMenuDao.getByKey(key);
    }
    
    /**
     * 根据用户和菜单（主部门菜单）判断用户是否有菜单权限
     * @author Stephen
     * @param userId
     * @param menuId
     * @return
     */
    public SysMenu getByUserMenuId(String userId,String menuId){
    	return sysMenuQueryDao.getByUserMenuId(userId,menuId);
    }
    
    
    public boolean isBoListExist(SysMenu sysMenu){
    	return sysMenuQueryDao.isBoListExist(sysMenu.getBoListId(), sysMenu.getMenuId());
    }


	public List<SysMenu> getGrantMenusByTypeId(String typeId) {
		return sysMenuQueryDao.getGrantMenusByTypeId(typeId);
	}


	public List<SysMenu> getUrlMenuByTenantMgr(String sysId, String tenantId,String isBtnMenu) {
		return sysMenuQueryDao.getUrlMenuByTenantMgr(sysId,tenantId,isBtnMenu);
	}


	public List<SysMenu> getMenusByTenantUser(String parentId,String userId) {
		return sysMenuQueryDao.getMenusByTenantUser(parentId,userId);
	}


	public List<SysMenu> getByTenantType(String sysId, String instType) {
		return sysMenuQueryDao.getByTenantType(sysId,instType);
	}
	
	/**
	 * 表单key是否存在。
	 * @param key
	 * @param menuId
	 * @return
	 */
	public boolean isKeyExist(String key,String menuId){
		return sysMenuQueryDao.isKeyExist(key, menuId);
	}
    
    
    
}