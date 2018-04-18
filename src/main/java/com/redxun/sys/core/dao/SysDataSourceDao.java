
/**
 * 
 * <pre> 
 * 描述：数据源定义管理 DAO接口
 * 作者:ray
 * 日期:2017-02-07 09:03:54
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.core.dao;

import com.redxun.sys.core.entity.SysDataSource;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class SysDataSourceDao extends BaseJpaDao<SysDataSource> {


	@Override
	protected Class getEntityClass() {
		return SysDataSource.class;
	}

}

