package com.redxun.sys.core.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.sys.core.entity.SysMenu;
/**
 * 
 * <pre> 
 * 描述：TODO
 * 构建组：ent-base-web
 * 作者：csx
 * 邮箱:chensx@redxun.cn
 * 日期:2014年8月1日-上午9:54:08
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class SysMenuDao extends BaseJpaDao<SysMenu> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return SysMenu.class;
    }
    
    public int getCountsByParentId(String parentId,String sysId){
    	String ql="select count(*) from SysMenu m where m.parentId=? and m.subsystem.sysId=?";
    	return ((Long)this.getUnique(ql, parentId,sysId)).intValue();
    }
   
  
    /**
     * 按系统Id及是否为SAAS菜单及菜单类型查询
     * @param sysId
     * @param isMgr
     * @param isBtnMenu
     * @return
     */
    public List<SysMenu> getBySysIdIsMgrIsBtnMenu(String sysId,String isBtnMenu){
    	
    	String jpql="from SysMenu m where m.subsystem.sysId=?  and m.isBtnMenu=? order by m.sn asc";
    	return this.getByJpql(jpql, new Object[]{sysId,isBtnMenu});
    }
    
    /**
     * 按系统Id及是否为SAAS菜单及菜单类型查询
     * @param sysId
     * @param isMgr
     * @param isBtnMenu
     * @return
     */
    public List<SysMenu> getBySysIdIsBtnMenu(String sysId,String isBtnMenu){
    	String jpql="from SysMenu m where m.subsystem.sysId=? and m.isBtnMenu=? order by m.sn asc";
    	return this.getByJpql(jpql, new Object[]{sysId,isBtnMenu});
    }
  
    
    /**
     * 通过父ID及系统ID获取菜单列表
     * @param sysId
     * @param parentId
     * @param tenantId
     * @return 
     * List<SysMenu>
     * @exception 
     * @since  1.0.0
     */
    public List<SysMenu> getByParentIdSysId(String sysId,String parentId){
    	String jpql="from SysMenu m where m.subsystem.sysId=? and m.parentId=? order by m.sn asc";
    	return this.getByJpql(jpql,sysId,parentId);
    }
    /**
     * 按父ID获取所有子菜单
     * @param parentId
     * @return 
     * List<SysMenu>
     * @exception 
     * @since  1.0.0
     */
    public List<SysMenu> getByParentId(String parentId){
    	String jpql="from SysMenu m where m.parentId=? order by m.sn asc";
    	return this.getByJpql(jpql,parentId);
    }
    
    /**
     * 获得某菜单下的非按钮子菜单
     * @param parentId 当前菜单Id
     * @return
     */
    public List<SysMenu> getMenusByParentId(String parentId){
    	String ql="from SysMenu m where m.parentId=? and m.isBtnMenu=? order by m.sn asc";
    	return this.getByJpql(ql, new Object[]{parentId,MBoolean.NO.name()});
	}
    /**
     * 获得某个菜单下的子菜单
     * @param parentId
     * @param menuKey
     * @return
     */
    public SysMenu getByParentIdMenuKey(String parentId,String menuKey){
    	String ql="from SysMenu m where m.parentId=? and m.key=?";
    	List<SysMenu> list=this.getByJpql(ql, new Object[]{parentId,menuKey});
    	if(list.size()>0){
    		return list.get(0);
    	}
    	return null;
    }
    
    /**
     * 按路径删除其记录
     * @param path 
     * void
     * @exception 
     * @since  1.0.0
     */
    public void delByPath(String path){
    	String jpql="delete from SysMenu where path like ?";
    	this.delete(jpql, path+"%");
    }
    /**
     * 按子系统Id删除子系统
     * @param sysId 
     * void
     * @exception 
     * @since  1.0.0
     */
    public void delBySysId(String sysId){
    	String jpql="delete from SysMenu sm where sm.subsystem.sysId=?";
    	this.delete(jpql, sysId);
    }
    
    /**
     * 更新子结节数
     * @param menuId 
     * void
     * @exception 
     * @since  1.0.0
     */
    public Long getChildsCount(String menuId){
    	String jpql="select count(*) from SysMenu m where m.parentId=?";
    	 return (Long)this.getUnique(jpql,menuId);
    }
    
    /**
     * 通过实体名称及是否为按钮菜单获得其某模块下的功能按钮
     * @param entityName
     * @param isBtnMenu
     * @return
     */
    public List<SysMenu> getByEntityNameIsBtnMenu(String entityName,String isBtnMenu){
    	String ql="from SysMenu sm where sm.entityName=? and sm.isBtnMenu=?";
    	return this.getByJpql(ql, new Object[]{entityName,isBtnMenu});
    }
    
    /**
     * 通过菜单的路径相似来查找所有它的子菜单
     * @param path
     * @return
     */
    public List<SysMenu> getSysMenuLeftLike(String path){
    	String ql="from SysMenu sm where sm.path like ?";
    	List<SysMenu> list=this.getByJpql(ql, path+"%");
    	return list;
    }
    
   
    
    public SysMenu getByKey(String key){
    	String ql = "from SysMenu where key = ?";
    	return (SysMenu) this.getUnique(ql, new Object[]{key});
    }
}
