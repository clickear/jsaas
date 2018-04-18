package com.redxun.offdoc.core.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.SqlQueryFilter;
import com.redxun.offdoc.core.dao.OdDocFlowDao;
import com.redxun.offdoc.core.dao.OdDocFlowQueryDao;
import com.redxun.offdoc.core.entity.OdDocFlow;
/**
 * <pre> 
 * 描述：OdDocFlow业务服务类
 * 作者：陈茂昌
 * 日期:2016-3-8-上午16:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class OdDocFlowManager extends BaseManager<OdDocFlow>{
	@Resource
	private OdDocFlowDao odDocFlowDao;
	@Resource
	private OdDocFlowQueryDao odDocFlowQueryDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return odDocFlowDao;
	}
    public List<OdDocFlow>  getOdDocFlowAndGroup(String dimId,String tenantId,SqlQueryFilter sqlQueryFilter){
    	return odDocFlowQueryDao.getOdDocFlowAndGroup(dimId, tenantId, sqlQueryFilter);
    }
    /**
     * 通过部门ID查找部门的收发文流程配置
     * @param groupId
     * @return
     */
    public OdDocFlow getByGroupId(String groupId){
    	return odDocFlowDao.getByGroupId(groupId);
    }
	
}