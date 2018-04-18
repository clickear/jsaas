package com.redxun.sys.core.manager;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.sys.core.dao.SysFormSchemaDao;
import com.redxun.sys.core.entity.SysFormSchema;
/**
 * <pre> 
 * 描述：SysFormSchema业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class SysFormSchemaManager extends BaseManager<SysFormSchema>{
	@Resource
	private SysFormSchemaDao sysFormSchemaDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysFormSchemaDao;
	}
	
	public SysFormSchema getByModuleId(String moduleId){
		return sysFormSchemaDao.getByModuleId(moduleId);
	}
}