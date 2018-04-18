
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
import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class InsColumnDefQueryDao extends BaseMybatisDao<InsColumnDef> {

	@Override
	public String getNamespace() {
		return InsColumnDef.class.getName();
	}

}

