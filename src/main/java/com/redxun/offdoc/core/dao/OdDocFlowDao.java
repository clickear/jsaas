package com.redxun.offdoc.core.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.offdoc.core.entity.OdDocFlow;
import com.redxun.sys.org.entity.OsGroup;
/**
 * <pre> 
 * 描述：OdDocFlow数据访问类
 * 作者：陈茂昌
 * 日期:2016-3-8-上午16:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class OdDocFlowDao extends BaseJpaDao<OdDocFlow> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return OdDocFlow.class;
    }
    
    /**
     * 通过部门ID查找部门的收发文流程配置
     * @param groupId
     * @return
     */
    public OdDocFlow getByGroupId(String groupId){
    	String hql="from OdDocFlow odf where odf.depId=? ";
    	return (OdDocFlow) this.getUnique(hql, groupId);
    }
    
}
