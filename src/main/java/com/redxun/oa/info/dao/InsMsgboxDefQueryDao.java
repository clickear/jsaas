
/**
 * 
 * <pre> 
 * 描述：栏目消息盒子表 DAO接口
 * 作者:mansan
 * 日期:2017-09-01 11:35:24
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.oa.info.dao;

import com.redxun.oa.info.entity.InsMsgboxDef;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class InsMsgboxDefQueryDao extends BaseMybatisDao<InsMsgboxDef> {

	@Override
	public String getNamespace() {
		return InsMsgboxDef.class.getName();
	}

}

