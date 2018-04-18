package com.redxun.sys.core.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.sys.core.entity.SysSearch;
import com.redxun.sys.core.entity.SysSearchItem;
/**
 * <pre> 
 * 描述：SysSearchItem数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class SysSearchItemDao extends BaseJpaDao<SysSearchItem> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return SysSearchItem.class;
    }
    /**
     * 获得某个搜索条件下的
     * @param searchId
     * @param parentId
     * @return 
     * List<SysSearchItem>
     * @exception 
     * @since  1.0.0
     */
    public List<SysSearchItem> getBySearchIdParentId(String searchId,String parentId){
    	String ql="from SysSearchItem s where s.sysSearch.searchId=? and s.parentId=? order by sn asc";
    	return this.getByJpql(ql, new Object[]{searchId,parentId});
    }
    
    /**
     * 获得某个搜索下的所有搜索条件项列表
     * @param searchId
     * @return 
     * List<SysSearchItem>
     * @exception 
     * @since  1.0.0
     */
    public List<SysSearchItem> getBySearchId(String searchId){
    	String ql="from SysSearchItem s where s.sysSearch.searchId=? order by sn asc";
    	return this.getByJpql(ql, new Object[]{searchId});
    }
    
    /**
     * 按路径删除某个目录下的所有搜索条件项
     * @param path 
     * void
     * @exception 
     * @since  1.0.0
     */
    public void delByParentPath(String path){
    	String ql="delete from SysSearchItem si where si.path like ?";
    	this.delete(ql, path+"%");
    }
    
}
