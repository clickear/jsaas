package com.redxun.sys.core.dao;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.sys.core.entity.SysQuartzLog;

/**
 * <pre>
 * 描述：SysQuartzLog数据访问类
 * @author cjx
 * @Email: chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用。
 * </pre>
 */
@Repository
public class SysQuartzLogDao extends BaseJpaDao<SysQuartzLog> {
	@PersistenceContext
	private EntityManager em;
	@SuppressWarnings("rawtypes")
	@Override
	protected Class getEntityClass() {
		return SysQuartzLog.class;
	}
	/**
	 * 删除所有日志
	 */
	public void deleteAll() {
		String jpSql = "delete from SysQuartzLog";
		Query query = em.createQuery(jpSql);
		query.executeUpdate();
	}
}
