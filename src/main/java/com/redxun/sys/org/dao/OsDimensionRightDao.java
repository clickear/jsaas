
/**
 * 
 * <pre> 
 * 描述：维度授权 DAO接口
 * 作者:ray
 * 日期:2017-05-16 14:12:56
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.org.dao;

import com.redxun.sys.org.entity.OsDimensionRight;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class OsDimensionRightDao extends BaseJpaDao<OsDimensionRight> {


	@Override
	protected Class getEntityClass() {
		return OsDimensionRight.class;
	}

}

