
/**
 * 
 * <pre> 
 * 描述：BO属性表 DAO接口
 * 作者:ray
 * 日期:2017-02-15 15:02:18
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.bo.dao;

import com.redxun.sys.bo.entity.SysBoAttr;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class SysBoAttrDao extends BaseJpaDao<SysBoAttr> {


	@Override
	protected Class getEntityClass() {
		return SysBoAttr.class;
	}

}

