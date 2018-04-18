
/**
 * 
 * <pre> 
 * 描述：表单实体对象 DAO接口
 * 作者:ray
 * 日期:2017-02-15 15:02:18
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.bo.dao;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.sys.bo.entity.SysBoEnt;

@Repository
public class SysBoEntDao extends BaseJpaDao<SysBoEnt> {


	@Override
	protected Class getEntityClass() {
		return SysBoEnt.class;
	}
	
	
	
	
}

