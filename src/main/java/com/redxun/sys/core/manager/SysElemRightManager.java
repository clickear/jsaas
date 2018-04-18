package com.redxun.sys.core.manager;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.sys.core.dao.SysElemRightDao;
import com.redxun.sys.core.entity.SysElemRight;
/**
 * <pre> 
 * 描述：SysElemRight业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class SysElemRightManager extends BaseManager<SysElemRight>{
	@Resource
	private SysElemRightDao sysElemRightDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysElemRightDao;
	}
}