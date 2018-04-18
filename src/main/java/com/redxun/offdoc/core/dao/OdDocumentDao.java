package com.redxun.offdoc.core.dao;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.offdoc.core.entity.OdDocument;
/**
 * <pre> 
 * 描述：OdDocument数据访问类
 * 作者：陈茂昌
 * 日期:2016-3-8-上午16:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class OdDocumentDao extends BaseJpaDao<OdDocument> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return OdDocument.class;
    }
    public OdDocument getByActInstId(String actInstId){
    	String hql="from OdDocument od where od.bpmInstId=?";
    	return (OdDocument) this.getUnique(hql, actInstId);
    }
    
}
