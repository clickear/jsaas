
package com.redxun.oa.info.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.oa.info.dao.InsMsgboxBoxDefDao;
import com.redxun.oa.info.dao.InsMsgboxBoxDefQueryDao;
import com.redxun.oa.info.entity.InsMsgboxBoxDef;

/**
 * 
 * <pre> 
 * 描述：INS_MSGBOX_BOX_DEF 处理接口
 * 作者:mansan
 * 日期:2017-09-01 10:58:03
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class InsMsgboxBoxDefManager extends ExtBaseManager<InsMsgboxBoxDef>{
	@Resource
	private InsMsgboxBoxDefDao insMsgboxBoxDefDao;
	@Resource
	private InsMsgboxBoxDefQueryDao insMsgboxBoxDefQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return insMsgboxBoxDefDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return insMsgboxBoxDefQueryDao;
	}
	
	public InsMsgboxBoxDef getInsMsgboxBoxDef(String uId){
		InsMsgboxBoxDef insMsgboxBoxDef = get(uId);
		return insMsgboxBoxDef;
	}
	
	/**
	 * 删除该消息盒子的所有中间表
	 * @param boxId
	 */
	public void delByBoxId(String boxId){
		insMsgboxBoxDefQueryDao.delByBoxId(boxId);
	}
	
	/**
	 * 根据消息盒子获取中间表
	 * @param boxId
	 * @return
	 */
	public List<InsMsgboxBoxDef> getByBoxId(String boxId){
		return insMsgboxBoxDefDao.getByMsgBoxId(boxId);
	}
}
