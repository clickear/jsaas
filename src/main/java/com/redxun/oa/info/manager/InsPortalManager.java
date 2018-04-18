package com.redxun.oa.info.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.info.dao.InsPortalDao;
import com.redxun.oa.info.entity.InsPortCol;
import com.redxun.oa.info.entity.InsPortal;
/**
 * <pre> 
 * 描述：InsPortal业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class InsPortalManager extends BaseManager<InsPortal>{
	@Resource
	private InsPortalDao insPortalDao;
	@Resource
	InsPortColManager insPortColManager;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return insPortalDao;
	}

    /**
     * 根据门户Key，租户Id查找是否有这个门户
     * @param key
     * @param tenantId
     * @return 如果有则为这个门户portal，如果没则为空
     */
	public InsPortal getByKey(String key, String tenantId){
		return insPortalDao.getByKey(key, tenantId);
    }
	
    /**
     * 根据门户key，租户Id，用户Id查找是否有这个门户
     * @param key
     * @param tenantId
     * @param userId
     * @return 如果有则为这个门户portal，如果没则为空
     */
	 public InsPortal getByIdKey(String key, String tenantId,String userId){
		return insPortalDao.getByIdKey(key, tenantId, userId);
    }
}