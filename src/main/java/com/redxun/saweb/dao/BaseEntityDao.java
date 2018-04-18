package com.redxun.saweb.dao;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.core.entity.BaseEntity;

/**
 * 实体基础类
 * @author mansan
 *  @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class BaseEntityDao extends BaseJpaDao<BaseEntity>{
	
	private Class entityClass;
	@Override
	protected Class getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}

	public BaseEntityDao(Class cls) {
		this.entityClass=cls;
	}
	
	public BaseEntityDao() {
	
	}

}
