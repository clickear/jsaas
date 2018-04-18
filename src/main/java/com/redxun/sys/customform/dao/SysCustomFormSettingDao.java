
/**
 * 
 * <pre> 
 * 描述：自定义表单配置设定 DAO接口
 * 作者:mansan
 * 日期:2017-05-16 10:25:38
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.customform.dao;

import com.redxun.sys.customform.entity.SysCustomFormSetting;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class SysCustomFormSettingDao extends BaseJpaDao<SysCustomFormSetting> {


	@Override
	protected Class getEntityClass() {
		return SysCustomFormSetting.class;
	}

}

