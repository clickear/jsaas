package com.redxun.oa.res.manager;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.res.dao.OaMeetRoomDao;
import com.redxun.oa.res.entity.OaMeetRoom;
/**
 * <pre> 
 * 描述：OaMeetRoom业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class OaMeetRoomManager extends BaseManager<OaMeetRoom>{
	@Resource
	private OaMeetRoomDao oaMeetRoomDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return oaMeetRoomDao;
    }
}