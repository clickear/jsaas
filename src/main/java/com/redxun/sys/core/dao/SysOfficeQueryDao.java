
/**
 * 
 * <pre> 
 * 描述：OFFICE附件 DAO接口
 * 作者:ray
 * 日期:2018-01-15 15:34:18
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.core.dao;

import com.redxun.sys.core.entity.SysOffice;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class SysOfficeQueryDao extends BaseMybatisDao<SysOffice> {

	@Override
	public String getNamespace() {
		return SysOffice.class.getName();
	}

}

