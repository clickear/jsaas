
/**
 * 
 * <pre> 
 * 描述：Demo DAO接口
 * 作者:mansan
 * 日期:2017-04-26 16:24:45
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.demo.dao;

import com.redxun.sys.demo.entity.Demo;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class DemoDao extends BaseJpaDao<Demo> {


	@Override
	protected Class getEntityClass() {
		return Demo.class;
	}

}

