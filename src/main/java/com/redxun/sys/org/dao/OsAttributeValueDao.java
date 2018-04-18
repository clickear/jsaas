
/**
 * 
 * <pre> 
 * 描述：人员属性值 DAO接口
 * 作者:mansan
 * 日期:2017-12-14 14:09:43
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.org.dao;

import com.redxun.sys.org.entity.OsAttributeValue;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class OsAttributeValueDao extends BaseJpaDao<OsAttributeValue> {


	@Override
	protected Class getEntityClass() {
		return OsAttributeValue.class;
	}

}

