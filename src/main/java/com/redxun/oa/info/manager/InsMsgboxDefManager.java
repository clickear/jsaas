
package com.redxun.oa.info.manager;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.oa.info.dao.InsMsgboxDefDao;
import com.redxun.oa.info.dao.InsMsgboxDefQueryDao;
import com.redxun.oa.info.entity.InsMsgboxDef;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;

/**
 * 
 * <pre> 
 * 描述：栏目消息盒子表 处理接口
 * 作者:mansan
 * 日期:2017-09-01 11:35:24
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class InsMsgboxDefManager extends ExtBaseManager<InsMsgboxDef>{
	@Resource
	private InsMsgboxDefDao insMsgboxDefDao;
	@Resource
	private InsMsgboxDefQueryDao insMsgboxDefQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return insMsgboxDefDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return insMsgboxDefQueryDao;
	}
	
	public InsMsgboxDef getInsMsgboxDef(String uId){
		InsMsgboxDef insMsgboxDef = get(uId);
		return insMsgboxDef;
	}
	
	public InsMsgboxDef getByKey(String boxKey){
		return insMsgboxDefDao.getbyKey(boxKey);
	}
}
