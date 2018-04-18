
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
import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class DemoQueryDao extends BaseMybatisDao<Demo> {

	@Override
	public String getNamespace() {
		return Demo.class.getName();
	}

}

