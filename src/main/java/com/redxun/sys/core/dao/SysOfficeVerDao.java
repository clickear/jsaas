
/**
 * 
 * <pre> 
 * 描述：OFFICE版本 DAO接口
 * 作者:ray
 * 日期:2018-01-15 15:34:18
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.core.dao;

import com.redxun.sys.core.entity.SysOfficeVer;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class SysOfficeVerDao extends BaseJpaDao<SysOfficeVer> {

	@Override
	protected Class getEntityClass() {
		return SysOfficeVer.class;
	}

}

