
package com.redxun.sys.core.dao;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.sys.core.entity.SysTreeCat;
/**
 * <pre> 
 * 描述：SysTreeCat数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class SysTreeCatDao extends BaseJpaDao<SysTreeCat> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return SysTreeCat.class;
    }
    /**
     * 
     * 按Key与tenantId获取其值
     * @param key
     * @param tenantId
     * @return 
     * SysTreeCat
     * @exception 
     * @since  1.0.0
     */
    public SysTreeCat getByKey(String key,String tenantId){
    	String ql="from SysTreeCat where key=? and tenantId=?";
    	return(SysTreeCat)getUnique(ql, new Object[]{key,tenantId});
    }
    
}
