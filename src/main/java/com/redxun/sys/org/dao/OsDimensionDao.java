package com.redxun.sys.org.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.org.api.model.ITenant;
import com.redxun.sys.org.entity.OsDimension;
/**
 * <pre> 
 * 描述：OsDimension数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class OsDimensionDao extends BaseJpaDao<OsDimension> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return OsDimension.class;
    }
   
    /**
     * 取得某租用下维度Key的值
     * @param dimKey
     * @param tenantId
     * @return 
     * OsDimension
     * @exception 
     * @since  1.0.0
     */
    public OsDimension getByDimKeyTenantId(String dimKey,String tenantId){
    	String ql="from OsDimension where dimKey=? and tenantId in (?,?)";
    	return (OsDimension) getUnique(ql, new Object[]{dimKey,tenantId,ITenant.PUBLIC_TENANT_ID});
    }
    
  
    
}
