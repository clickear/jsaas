
package com.redxun.sys.core.manager;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.sys.core.dao.SysInstTypeMenuDao;
import com.redxun.sys.core.dao.SysInstTypeMenuQueryDao;
import com.redxun.sys.core.entity.SysInstTypeMenu;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;

/**
 * 
 * <pre> 
 * 描述：机构类型授权菜单 处理接口
 * 作者:mansan
 * 日期:2017-12-19 11:00:46
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class SysInstTypeMenuManager extends ExtBaseManager<SysInstTypeMenu>{
	@Resource
	private SysInstTypeMenuDao sysInstTypeMenuDao;
	@Resource
	private SysInstTypeMenuQueryDao sysInstTypeMenuQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysInstTypeMenuDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return sysInstTypeMenuQueryDao;
	}
	
	public SysInstTypeMenu getSysInstTypeMenu(String uId){
		SysInstTypeMenu sysInstTypeMenu = get(uId);
		return sysInstTypeMenu;
	}

	public void deleteByInstTypeId(String typeId) {
		
		sysInstTypeMenuQueryDao.deleteByInstTypeId(typeId);
		
	}
}
