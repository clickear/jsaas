package com.redxun.sys.core.manager;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.sys.core.dao.SysFormFieldDao;
import com.redxun.sys.core.entity.SysFormField;
/**
 * <pre> 
 * 描述：SysFormField业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class SysFormFieldManager extends BaseManager<SysFormField>{
	@Resource
	private SysFormFieldDao sysFormFieldDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysFormFieldDao;
	}
}