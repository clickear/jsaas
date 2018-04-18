package com.redxun.saweb.manager;

import com.redxun.core.dao.IDao;
import com.redxun.core.entity.BaseEntity;
import com.redxun.core.manager.BaseManager;
import com.redxun.saweb.dao.BaseEntityDao;

/**
 * 
 * <pre> 
 * 描述：TODO
 * 构建组：ent-base-web
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2014年9月16日-下午9:27:36
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
public class BaseEntityManager extends BaseManager<BaseEntity>{
	
	BaseEntityDao baseEntityDao;
	
	@Override
	protected IDao getDao() {
		return baseEntityDao;
	}
	
	public BaseEntityManager() {
		
	}
	
	public BaseEntityManager(BaseEntityDao baseEntityDao) {
		this.baseEntityDao = baseEntityDao;
	}

	public void setBaseEntityDao(BaseEntityDao baseEntityDao) {
		this.baseEntityDao = baseEntityDao;
	}
	
}
