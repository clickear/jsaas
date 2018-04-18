
/**
 * 
 * <pre> 
 * 描述：布局权限设置 DAO接口
 * 作者:mansan
 * 日期:2017-08-28 15:58:17
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.oa.info.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.oa.info.entity.InsPortalPermission;

@Repository
public class InsPortalPermissionQueryDao extends BaseMybatisDao<InsPortalPermission> {

	@Override
	public String getNamespace() {
		return InsPortalPermission.class.getName();
	}
	
	public void delByLayoutId(String layoutId){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("layoutId", layoutId);
		this.deleteBySqlKey("delByLayoutId", params);
	}

}

