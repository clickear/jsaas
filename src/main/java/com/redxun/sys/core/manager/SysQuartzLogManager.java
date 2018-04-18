package com.redxun.sys.core.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.sys.core.dao.SysQuartzLogDao;
import com.redxun.sys.core.dao.SysQuartzLogQueryDao;
import com.redxun.sys.core.entity.SysQuartzLog;

/**
 * <pre>
 * 描述：SysQuartzLog业务服务类
 * @author cjx
 * @Email: chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用。
 * </pre>
 */
@Service
public class SysQuartzLogManager extends BaseManager<SysQuartzLog> {
	@Resource
	private SysQuartzLogDao sysQuartzLogDao;
	@Resource
	private SysQuartzLogQueryDao sysQuartzLogQueryDao;

	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysQuartzLogDao;
	}

	public void cleanAll() {
		this.sysQuartzLogQueryDao.cleanAll();
	}
	
	@Override
	public List<SysQuartzLog> getAll(QueryFilter queryFilter){
		 return  sysQuartzLogQueryDao.getAll(queryFilter);
	}
	
	
}