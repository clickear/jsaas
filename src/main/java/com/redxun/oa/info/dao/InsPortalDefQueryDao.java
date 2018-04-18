
/**
 * 
 * <pre> 
 * 描述：ins_portal_def DAO接口
 * 作者:mansan
 * 日期:2017-08-15 16:07:14
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.oa.info.dao;

import com.redxun.oa.info.entity.InsPortalDef;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class InsPortalDefQueryDao extends BaseMybatisDao<InsPortalDef> {

	@Override
	public String getNamespace() {
		return InsPortalDef.class.getName();
	}

}

