
package com.redxun.sys.demo.manager;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.sys.demo.dao.DemoDao;
import com.redxun.sys.demo.dao.DemoQueryDao;
import com.redxun.sys.demo.entity.Demo;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;

/**
 * 
 * <pre> 
 * 描述：Demo 处理接口
 * 作者:mansan
 * 日期:2017-04-26 16:24:45
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class DemoManager extends ExtBaseManager<Demo>{
	@Resource
	private DemoDao demoDao;
	@Resource
	private DemoQueryDao demoQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return demoDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return demoQueryDao;
	}
}
