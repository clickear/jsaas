
/**
 * 
 * <pre> 
 * 描述：公告栏目管理 DAO接口
 * 作者:mansan
 * 日期:2018-04-16 17:38:10
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.oa.info.dao;

import com.redxun.oa.info.entity.InsNewsColumn;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class InsNewsColumnQueryDao extends BaseMybatisDao<InsNewsColumn> {

	@Override
	public String getNamespace() {
		return InsNewsColumn.class.getName();
	}

}

