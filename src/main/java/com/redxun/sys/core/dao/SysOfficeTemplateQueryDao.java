
/**
 * 
 * <pre> 
 * 描述：office模板 DAO接口
 * 作者:ray
 * 日期:2018-01-28 11:11:46
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.core.dao;

import com.redxun.sys.core.entity.SysOfficeTemplate;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class SysOfficeTemplateQueryDao extends BaseMybatisDao<SysOfficeTemplate> {

	@Override
	public String getNamespace() {
		return SysOfficeTemplate.class.getName();
	}

}

