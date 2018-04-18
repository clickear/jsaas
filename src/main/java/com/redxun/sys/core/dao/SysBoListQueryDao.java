
/**
 * 
 * <pre> 
 * 描述：系统自定义业务管理列表 DAO接口
 * 作者:mansan
 * 日期:2017-05-21 12:11:18
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.core.dao;

import com.redxun.sys.core.entity.SysBoList;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class SysBoListQueryDao extends BaseMybatisDao<SysBoList> {

	@Override
	public String getNamespace() {
		return SysBoList.class.getName();
	}

}

