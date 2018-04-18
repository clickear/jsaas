
package com.redxun.oa.info.manager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.dao.mybatis.CommonDao;
import com.redxun.core.entity.SqlModel;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.script.GroovyEngine;
import com.redxun.oa.info.dao.InsMsgDefDao;
import com.redxun.oa.info.dao.InsMsgDefQueryDao;
import com.redxun.oa.info.dao.InsMsgboxBoxDefDao;
import com.redxun.oa.info.entity.InsMsgDef;
import com.redxun.oa.info.entity.InsMsgboxBoxDef;

/**
 * 
 * <pre> 
 * 描述：INS_MSG_DEF 处理接口
 * 作者:mansan
 * 日期:2017-09-01 10:40:15
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class InsMsgDefManager extends ExtBaseManager<InsMsgDef>{
	@Resource
	private InsMsgDefDao insMsgDefDao;
	@Resource
	private InsMsgDefQueryDao insMsgDefQueryDao;
    @Resource
    GroovyEngine groovyEngine;
    @Resource
    CommonDao commonDao;
    @Resource
    InsMsgboxBoxDefManager insMsgboxBoxDefManager;
    @Resource
    InsMsgboxBoxDefDao insMsgboxBoxDefDao;
    @Resource
    InsMsgDefManager insMsgDefManager;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return insMsgDefDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return insMsgDefQueryDao;
	}
	
	public InsMsgDef getInsMsgDef(String uId){
		InsMsgDef insMsgDef = get(uId);
		return insMsgDef;
	}
	
	

    
    public List<InsMsgDef> getByMsgBoxId(String boxId){
    	List<InsMsgboxBoxDef> msgbox = insMsgboxBoxDefDao.getByMsgBoxId(boxId);
    	List<InsMsgDef> msgs = new ArrayList<InsMsgDef>();
    	InsMsgDef msgDef  = new InsMsgDef();
    	for(InsMsgboxBoxDef m:msgbox){
    		msgDef = insMsgDefManager.get(m.getMsgId());
    		if(msgDef!=null){
    			msgs.add(msgDef);
    		}
    	}
    	return msgs;
    }
}
