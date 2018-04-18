package com.redxun.kms.core.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.core.query.Page;
import com.redxun.core.query.QueryFilter;
import com.redxun.kms.core.entity.KdDocRead;
import com.redxun.saweb.context.ContextUtil;
/**
 * <pre> 
 * 描述：KdDocRead数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class KdDocReadDao extends BaseJpaDao<KdDocRead> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return KdDocRead.class;
    }
    
    /**
     * 查询所有该文档的阅读记录
     * @param docId
     * @return
     */
    public List<KdDocRead> getReader(String docId,QueryFilter queryFilter){
		String tenantId = ContextUtil.getCurrentTenantId();
    	String ql = "from KdDocRead r where r.kdDoc.docId = ? and r.tenantId = ? order by r.createTime desc";
    	return this.getByJpql(ql,new Object[]{docId,tenantId},queryFilter.getPage());
    }
}
