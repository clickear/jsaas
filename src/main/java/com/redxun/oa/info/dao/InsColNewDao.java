package com.redxun.oa.info.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.core.query.QueryFilter;
import com.redxun.oa.info.entity.InsColNew;
import com.redxun.oa.info.entity.InsNews;
import com.redxun.saweb.context.ContextUtil;
/**
 * <pre> 
 * 描述：InsColNew数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class InsColNewDao extends BaseJpaDao<InsColNew> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return InsColNew.class;
    }
    /**
     * 删除某栏目下信息关联列表
     * @param colId
     */
    public void delByColId(String colId){
    	String tenantId = ContextUtil.getCurrentTenantId();
    	String ql="delete from InsColNew where insColumn.colId=? and insColumn.tenantId = ?";
    	delete(ql, new Object[]{colId,tenantId});
    }
    /**
     * 删除某信息关联的栏目列表
     * @param newsId
     */
    public void delByNewId(String newId){
    	String tenantId = ContextUtil.getCurrentTenantId();
    	String ql="delete from InsColNew where insNews.newId=? and insNews.tenantId = ?";
    	delete(ql,new Object[]{newId,tenantId});
    }
    
    /**
     * 查找某个栏目某个信息的关联关系
     * @param colId
     * @param newId
     * @return
     */
    public InsColNew getByColIdNewId(String colId,String newId){
    	String tenantId = ContextUtil.getCurrentTenantId();
    	String ql="from InsColNew cn where cn.insColumn.colId=? and insNews.newId=? and insNews.tenantId = ?";
    	return (InsColNew)this.getUnique(ql, new Object[]{colId,newId,tenantId});
    }
    
    public Boolean booleanByColIdNewId(String colId,String newId){
    	String tenantId = ContextUtil.getCurrentTenantId();
    	String ql="from InsColNew cn where cn.insColumn.colId=? and insNews.newId=? and insNews.tenantId = ?";
    	return (this.getByJpql(ql, new Object[]{colId,newId,tenantId}).size()>0);
    }
    
    /**
     * 删除某个栏目某个信息的关联关系
     * @param colId
     * @param newId
     * @return
     */
    public void delByColIdNewId(String colId,String newId){
    	String ql="delete InsColNew cn where cn.insColumn.colId=? and insNews.newId=?";
    	this.delete(ql, new Object[]{colId,newId});
    }
}
