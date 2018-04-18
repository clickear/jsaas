
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

import com.redxun.sys.log.entity.LogModule;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class LogModuleDao extends BaseJpaDao<LogModule> {


	@Override
	protected Class getEntityClass() {
		return LogModule.class;
	}

}

