
/**
 * 
 * <pre> 
 * 描述：机构--子系统关系 DAO接口
 * 作者:陈茂昌
 * 日期:2017-09-13 15:57:55
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.core.dao;

import com.redxun.sys.core.entity.SysTypeSubRef;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class SysTypeSubRefDao extends BaseJpaDao<SysTypeSubRef> {


	@Override
	protected Class getEntityClass() {
		return SysTypeSubRef.class;
	}

}

