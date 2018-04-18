
/**
 * 
 * <pre> 
 * 描述：新闻公告权限表 DAO接口
 * 作者:mansan
 * 日期:2017-11-03 11:47:25
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.oa.info.dao;

import com.redxun.oa.info.entity.InsNewsCtl;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class InsNewsCtlQueryDao extends BaseMybatisDao<InsNewsCtl> {

	@Override
	public String getNamespace() {
		return InsNewsCtl.class.getName();
	}

}

