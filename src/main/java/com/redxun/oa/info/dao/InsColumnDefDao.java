
/**
 * 
 * <pre> 
 * 描述：ins_column_def DAO接口
 * 作者:mansan
 * 日期:2017-08-16 11:39:47
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.oa.info.dao;

import com.redxun.oa.info.entity.InsColumnDef;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class InsColumnDefDao extends BaseJpaDao<InsColumnDef> {


	@Override
	protected Class getEntityClass() {
		return InsColumnDef.class;
	}

}

