
package com.redxun.sys.log.manager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.sys.log.LogModel;
import com.redxun.sys.log.dao.LogModuleDao;
import com.redxun.sys.log.dao.LogModuleQueryDao;
import com.redxun.sys.log.entity.LogModule;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;

/**
 * 
 * <pre> 
 * 描述：日志模块 处理接口
 * 作者:陈茂昌
 * 日期:2017-09-21 14:38:42
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class LogModuleManager extends ExtBaseManager<LogModule>{
	@Resource
	private LogModuleDao logModuleDao;
	@Resource
	private LogModuleQueryDao logModuleQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return logModuleQueryDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return logModuleQueryDao;
	}
	
	public LogModule getLogModule(String uId){
		LogModule logModule = get(uId);
		return logModule;
	}
	
	public List getModuleList(){
		return logModuleQueryDao.getModuleList();
	}
	
	public LogModule getLogModuleByModuleAndSubModule(String module,String subModule){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("module", module);
		params.put("subModule", subModule);
		return logModuleQueryDao.getLogModuleByModuleAndSubModule(params);
	}
}
