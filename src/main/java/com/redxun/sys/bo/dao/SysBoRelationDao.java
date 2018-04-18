
/**
 * 
 * <pre> 
 * 描述：BO关系定义 DAO接口
 * 作者:ray
 * 日期:2017-02-15 15:02:17
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.bo.dao;

import com.redxun.sys.bo.entity.SysBoRelation;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class SysBoRelationDao extends BaseJpaDao<SysBoRelation> {


	@Override
	protected Class getEntityClass() {
		return SysBoRelation.class;
	}

}

