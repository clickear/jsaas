
/**
 * 
 * <pre> 
 * 描述：定时器日志 DAO接口
 * 作者:ray
 * 日期:2016-12-29 09:07:01
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.core.dao;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.sys.core.entity.SysQuartzLog;

@Repository
public class SysQuartzLogQueryDao extends BaseMybatisDao<SysQuartzLog> {

	@Override
	public String getNamespace() {
		return SysQuartzLog.class.getName();
	}

	
	public void cleanAll() {
		this.updateBySqlKey("cleanAll");
	}
	
}

