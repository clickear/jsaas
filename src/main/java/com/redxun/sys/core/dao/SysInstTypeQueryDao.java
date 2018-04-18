
/**
 * 
 * <pre> 
 * 描述：机构类型 DAO接口
 * 作者:mansan
 * 日期:2017-07-10 18:35:31
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.core.dao;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.sys.core.entity.SysInstType;

@Repository
public class SysInstTypeQueryDao extends BaseMybatisDao<SysInstType> {

	@Override
	public String getNamespace() {
		return SysInstType.class.getName();
	}
	
	

}

