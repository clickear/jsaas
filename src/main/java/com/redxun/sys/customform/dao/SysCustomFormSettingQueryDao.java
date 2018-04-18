
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

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.saweb.context.ContextUtil;

@Repository
public class SysCustomFormSettingQueryDao extends BaseMybatisDao<SysCustomFormSetting> {

	@Override
	public String getNamespace() {
		return SysCustomFormSetting.class.getName();
	}

	/**
	 * 判断是否存在。
	 * @param setting
	 * @return
	 */
	public boolean isAliasExist(SysCustomFormSetting setting){
		String tenantId=ContextUtil.getCurrentTenantId();
		setting.setTenantId(tenantId);
		Integer rtn= (Integer) this.getOne("isAliasExist", setting);
		return (rtn>0);
	}
	
	/**
	 * 根据别名获取数据。
	 * @param alias
	 * @param tenantId
	 * @return
	 */
	public SysCustomFormSetting getByAlias(String alias,String tenantId){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("alias", alias);
		params.put("tenantId", tenantId);
		SysCustomFormSetting setting=this.getUnique("getByAlias", params);
		if(null==setting){
			params.put("tenantId", 1);
			return this.getUnique("getByAlias", params);
		}
		return setting;
			
	}
}

