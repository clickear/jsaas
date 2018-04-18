
/**
 * 
 * <pre> 
 * 描述：日志实体 DAO接口
 * 作者:陈茂昌
 * 日期:2017-09-21 14:43:53
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.log.dao;

import com.redxun.sys.log.entity.LogEntity;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class LogEntityDao extends BaseJpaDao<LogEntity> {


	@Override
	protected Class getEntityClass() {
		return LogEntity.class;
	}

}

