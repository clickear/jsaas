
/**
 * 
 * <pre> 
 * 描述：INS_MSG_DEF DAO接口
 * 作者:mansan
 * 日期:2017-09-01 10:40:15
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.oa.info.dao;

import com.redxun.oa.info.entity.InsMsgDef;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class InsMsgDefDao extends BaseJpaDao<InsMsgDef> {


	@Override
	protected Class getEntityClass() {
		return InsMsgDef.class;
	}

}

