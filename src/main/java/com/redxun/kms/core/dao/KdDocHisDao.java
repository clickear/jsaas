package com.redxun.kms.core.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.core.query.QueryFilter;
import com.redxun.kms.core.entity.KdDocHis;
import com.redxun.saweb.context.ContextUtil;
/**
 * <pre> 
 * 描述：KdDocHis数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class KdDocHisDao extends BaseJpaDao<KdDocHis> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return KdDocHis.class;
    }
    
    /**
     * 获得当前文档的历史版本
     * @param docId
     * @return
     */
    public List<KdDocHis> getDocVersion(String docId,QueryFilter queryFilter){
		String tenantId = ContextUtil.getCurrentTenantId();
    	String ql = "from KdDocHis k where k.kdDoc.docId = ? and k.tenantId = ?";
    	return this.getByJpql(ql, new Object[]{docId,tenantId},queryFilter.getPage());
    }
    
}
