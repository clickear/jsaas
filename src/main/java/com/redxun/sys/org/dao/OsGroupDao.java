package com.redxun.sys.org.dao;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.org.api.model.ITenant;
import com.redxun.sys.org.entity.OsDimension;
import com.redxun.sys.org.entity.OsGroup;
/**
 * <pre> 
 * 描述：OsGroup数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class OsGroupDao extends BaseJpaDao<OsGroup> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return OsGroup.class;
    }
    
    /**
     * 按维度ID取得该维度下的所有用户组
     * @param dimId
     * @return 
     * List<OsGroup>
     * @exception 
     * @since  1.0.0
     */
    public List<OsGroup> getByDimId(String dimId){
		String ql="from OsGroup g where g.osDimension.dimId=? order by g.path asc";
		return this.getByJpql(ql, new Object[]{dimId});
	}
    
    /**
     * 按维度ID及租房Id取得该维度下的所有用户组
     * @param dimId
     * @param tenantId
     * @return
     */
   public List<OsGroup> getByDimIdTenantId(String dimId,String tenantId){
	   String ql="from OsGroup g where g.osDimension.dimId=? and g.tenantId=? order by g.path asc";
	   return this.getByJpql(ql, new Object[]{dimId,tenantId});
   }
   
   public List<OsGroup> getByDimIdGroupIdTenantId(String dimId,String parentId,String tenantId){
	   String ql="from OsGroup g where g.osDimension.dimId=? and g.parentId=? and g.tenantId=? order by g.path asc";
	   return this.getByJpql(ql, new Object[]{dimId,parentId,tenantId});
   }
   
   
    /**
     * 取得该父节点下的所有子结节点
     * @param parentId
     * @return 
     * List<OsGroup>
     * @exception 
     * @since  1.0.0
     */
    public List<OsGroup> getByParentId(String parentId){
    	String ql="from OsGroup g where g.parentId=? order by g.sn asc";
    	return this.getByJpql(ql, parentId);
    }
   /**
    * 取得某个父类下的所有子组及数
    * @param parentId
    * @return 
    * Long
    * @exception 
    * @since  1.0.0
    */
    public Long getChildCounts(String parentId){
    	String ql="select count(*) from OsGroup g where g.parentId=? ";
    	return(Long)this.getUnique(ql, parentId);
    }
    /**
     * 取得该父节点下的所有子结节点
     * @param parentId 父Id
     * @param tenantId 租用机构ID
     * @return 
     * List<OsGroup>
     * @exception 
     * @since  1.0.0
     */
    public List<OsGroup> getByParentId(String parentId,String tenantId){
    	String ql="from OsGroup g where g.parentId=? and g.tenantId=? order by g.sn asc";
    	return this.getByJpql(ql, new Object[]{parentId,tenantId});
    }
    /**
     * 取得该维度下该父节点下的所有子结节点
     * @param dimId
     * @param parentId
     * @return 
     * List<OsGroup>
     * @exception 
     * @since  1.0.0
     */
    public List<OsGroup> getByDimIdParentId(String dimId,String parentId){
    	String ql="from OsGroup g where g.osDimension.dimId=? and g.parentId=? order by g.path asc";
    	return this.getByJpql(ql,new Object[]{dimId,parentId});
    }
    
    public List<OsGroup> getByDepName(String depName){
    	String ql="from OsGroup g where g.osDimension.dimId=? and g.name=?";
    	return this.getByJpql(ql, new Object[]{OsDimension.DIM_ADMIN_ID,depName});
    }
    
    public List<OsGroup> getByGroupNameExcludeAdminDim(String groupName){
    	String ql="from OsGroup g where g.osDimension.dimId!=? and g.name=?";
    	return this.getByJpql(ql, new Object[]{OsDimension.DIM_ADMIN_ID,groupName});
    }
    
    
    /**
     * 按维度Id及name,key查找用户组列表
     * @param tenantId
     * @param dimId
     * @param name
     * @param key
     * @return
     */
    public List<OsGroup> getByDimIdNameKey(String tenantId,String dimId,String name,String key){
    	List<Object> params=new ArrayList<Object>();
    	String ql="from OsGroup g where tenantId in (?,?)";
    	params.add(tenantId);
    	params.add(ITenant.PUBLIC_TENANT_ID);
    	if(StringUtils.isNotEmpty(dimId)){
    		ql+=" and g.osDimension.dimId=?";
    		params.add(dimId);
    	}
    	if(StringUtils.isNotEmpty(name)){
    		ql+=" and g.name like ?";
    		params.add("%"+ name + "%");
    	}
    	if(StringUtils.isNotEmpty(key)){
    		ql+=" and g.key like ?";
    		params.add("%"+ key + "%");
    	}
    	return this.getByJpql(ql, params.toArray());
    }
    
    public List<OsGroup> getBykey(String key){
    	String hql="from OsGroup o where o.key=?";
    	return this.getByJpql(hql, key);
    	
    }
    
    /**
     *  获得该Id下的子类数
     * @param parentId
     * @return
     */
    public int getCountByparentId(String parentId){
    	String ql = "select count(*) from OsGroup g where g.parentId = ?";
    	long a =  (Long)this.getUnique(ql, new Object[]{parentId});
    	return (int)a;
    }
    
    public boolean isLDAPExist(String key){
    	String ql="select count(*) from OsGroup g where g.key = ?";
    	long a = (Long)this.getUnique(ql, new Object[]{key});
    	if(a>0L){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public OsGroup getByKey(String key){
    	String ql="from OsGroup g where g.key = ?";
    	return (OsGroup) this.getUnique(ql, new Object[]{key});
    }
    
    /**
	 * 根据parentId获取用户组
	 * @param parentId
	 * @return
	 */
	public List<OsGroup> getGroupByParentId(String parentId){
		String ql="from OsGroup g where g.parentId=?";
		return getByJpql(ql, parentId);

	}
	public OsGroup getByPath(String path){
		String hql="from OsGroup g where g.path=?";
		return (OsGroup) this.getUnique(hql, path);
	}

}
