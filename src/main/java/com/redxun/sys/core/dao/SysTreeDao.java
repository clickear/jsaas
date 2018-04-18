package com.redxun.sys.core.dao;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.org.api.model.ITenant;
import com.redxun.sys.core.entity.SysTree;
/**
 * <pre> 
 * 描述：SysTree数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class SysTreeDao extends BaseJpaDao<SysTree> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return SysTree.class;
    }
    /**
     * 按Key获得系统树节点
     * @param key
     * @return
     */
    public SysTree getByKey(String key){
    	String ql="from SysTree where key=?";
    	return (SysTree)this.getUnique(ql, new Object[]{key});
    }
    
    public List<SysTree> getByCatKey(String catKey){
    	String ql="from SysTree where catKey=? order by sn asc";
    	return this.getByJpql(ql, catKey);
    }
    
    /**
     * 获得某分类下的租户下的树菜单
     * @param catKey
     * @param tenantId
     * @return
     */
    public List<SysTree> getByCatKeyTenantId(String catKey,String tenantId){
    	String ql="from SysTree where catKey=? and tenantId=? order by sn asc";
    	return this.getByJpql(ql, new Object[]{catKey,tenantId});
    }
    
    public List<SysTree> getByCatKeyTreeId(String catKey,String treeId,String tenantId){
    	String ql = "from SysTree where catKey = ? and tenantId = ? and path like ? order by sn asc";
    	String path = "%"+treeId +"%";
    	return this.getByJpql(ql, new Object[]{catKey,tenantId, path});
    }	
    
    public int getCountsByParentId(String parentId,String catKey,String tenantId){
    	String ql="select count(*) from SysTree where parentId=? and catKey=? and tenantId=?";
    	return ((Long)this.getUnique(ql, parentId,catKey,tenantId)).intValue();
    }
    
    public int getCountsByParentId(String parentId){
    	String ql="select count(*) from SysTree s where s.parentId=?";
    	return ((Long)this.getUnique(ql,parentId)).intValue();
    }
    /**
     * 取得用户某种分类的分类树
     * @param userId
     * @param catKey
     * @return
     */
    public List<SysTree> getByUserIdCatKey(String userId,String catKey){
    	String ql="from SysTree st where st.userId=? and st.catKey=? order by st.path asc,st.sn asc";
    	return this.getByJpql(ql, new Object[]{userId,catKey});
    }
    
    /**
     * 按父ID、分类Key、租用ID获得列表
     * @param parentId
     * @param catKey
     * @param tenantId
     * @return 
     * List<SysTree>
     * @exception 
     * @since  1.0.0
     */
    public List<SysTree> getByParentIdCatKey(String parentId,String catKey,String tenantId){
    	String ql="select st from SysTree st where st.parentId=? and st.catKey=? and st.tenantId=?";
    	return this.getByJpql(ql, parentId,catKey,tenantId);
    }
    /**
     * 按父ID取列表
     * @param parentId
     * @return 
     * List<SysTree>
     * @exception 
     * @since  1.0.0
     */
    public List<SysTree> getByParentId(String parentId){
    	String ql="select st from SysTree st where st.parentId=?";
    	return this.getByJpql(ql, parentId);
    }
    
    /**
     * 按路径删除其记录
     * @param path 
     * void
     * @exception 
     * @since  1.0.0
     */
    public void delByPath(String path){
    	String jpql="delete from SysTree where path like ?";
    	this.delete(jpql, path+"%");
    }
    
    /**
     * 取得某一分类下的Key对应的节点
     * @param key
     * @param catKey
     * @param tenentId
     * @return 
     * SysTree
     * @exception 
     * @since  1.0.0
     */
    public SysTree getByKey(String key,String catKey,String tenantId){
    	String jpql="from SysTree where key=? and catKey=? and tenantId=?";
    	return (SysTree) getUnique(jpql, key,catKey,tenantId);
    }
    
    /**
     * 知识仓库中获取主目录
     * @param catKey
     * @param tenantId
     * @return
     */
    public List<SysTree> getMainMenu(String catKey,String tenantId){
		String ql="from SysTree where parentId is NULL and catKey=? and tenantId=?";
		return this.getByJpql(ql,catKey,tenantId);
	}
    
    /**
     * 获取知识分类
     * @param tenantId
     * @param parentId
     * @param name
     * @param key
     * @return
     */
    public List<SysTree> getByTreeIdNameKey(String tenantId,String path,String name,String key){
    	List<Object> params=new ArrayList<Object>();
    	String ql="from SysTree tree where tree.tenantId in (?,?)";
    	params.add(tenantId);
    	params.add(ITenant.PUBLIC_TENANT_ID);
    	if(StringUtils.isNotEmpty(path)){
    		ql+=" and tree.path like ?";
    		params.add(path+"%");
    	}
    	if(StringUtils.isNotEmpty(name)){
    		ql+=" and tree.name like ?";
    		params.add("%"+ name + "%");
    	}
    	if(StringUtils.isNotEmpty(key)){
    		ql+=" and tree.key like ?";
    		params.add("%"+ key + "%");
    	}
    	return this.getByJpql(ql, params.toArray());
    }
    
}
