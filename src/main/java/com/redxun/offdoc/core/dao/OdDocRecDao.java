package com.redxun.offdoc.core.dao;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.offdoc.core.entity.OdDocRec;
/**
 * <pre> 
 * 描述：OdDocRec数据访问类
 * 作者：陈茂昌
 * 日期:2016-3-8-上午16:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class OdDocRecDao extends BaseJpaDao<OdDocRec> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return OdDocRec.class;
    }
    public OdDocRec getByDocId(String docId){
    	String hql="select odr from OdDocRec odr  left join odr.odDocument od where od.docId=?";
    	return (OdDocRec) this.getUnique(hql, docId);
    }
    
    public OdDocRec getRecByDocId(String docId,String dType){
    	String hql="select odr from OdDocRec odr  left join odr.odDocument od where od.docId=? and odr.recDtype=?";
    	return (OdDocRec) this.getUnique(hql, docId,dType);
    }
}
