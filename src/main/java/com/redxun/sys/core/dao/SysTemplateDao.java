package com.redxun.sys.core.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.org.api.model.ITenant;
import com.redxun.sys.core.entity.SysTemplate;
/**
 * <pre> 
 * 描述：SysTemplate数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class SysTemplateDao extends BaseJpaDao<SysTemplate> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return SysTemplate.class;
    }
    
    /**
     * 获得分类下的模板
     * @param treeId
     * @return
     */
    public List<SysTemplate> getByTreeId(String treeId){
    	String ql="from SysTemplate st where st.sysTree.treeId=?";
    	return this.getByJpql(ql, new Object[]{treeId});
    }
    
    /**
     * 按分类Key获得模板列表
     * @param catKey
     * @return
     */
    public List<SysTemplate> getByCatKey(String catKey){
    	String ql="from SysTemplate st where st.catKey=?";
    	return this.getByJpql(ql, new Object[]{catKey});
    }
    
    /**
     * 按分类Key,租户ID获得模板列表
     * @param catKey
     * @param tenantId
     * @return
     */
    public List<SysTemplate> getByCatKey(String catKey,String tenantId){
    	String ql="from SysTemplate st where st.catKey=? and tenantId in (?,?)";
    	return this.getByJpql(ql, new Object[]{catKey,tenantId,ITenant.PUBLIC_TENANT_ID});
    }
}
