
/**
 * 
 * <pre> 
 * 描述：日志模块 DAO接口
 * 作者:陈茂昌
 * 日期:2017-09-21 14:38:42
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.log.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.redxun.sys.log.entity.LogModule;

import org.springframework.stereotype.Repository;

import com.redxun.bpm.core.entity.BpmSolUsergroup;
import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class LogModuleQueryDao extends BaseMybatisDao<LogModule> {

	@Override
	public String getNamespace() {
		return LogModule.class.getName();
	}
	
	public List getModuleList(){
		return this.getBySqlKey("getModuleList", null);
	}

	public LogModule getLogModuleByModuleAndSubModule(Map<String,Object> params){
		return this.getUnique("getLogModuleByModuleAndSubModule", params);
		
	}
}

