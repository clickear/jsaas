
/**
 * 
 * <pre> 
 * 描述：自定义属性 DAO接口
 * 作者:mansan
 * 日期:2017-12-14 14:02:29
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.org.dao;

import com.redxun.sys.org.entity.OsCustomAttribute;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class OsCustomAttributeDao extends BaseJpaDao<OsCustomAttribute> {


	@Override
	protected Class getEntityClass() {
		return OsCustomAttribute.class;
	}

}

