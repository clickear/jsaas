
/**
 * 
 * <pre> 
 * 描述：机构类型授权菜单 DAO接口
 * 作者:mansan
 * 日期:2017-12-19 11:00:46
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.core.dao;

import java.util.HashMap;
import java.util.Map;

import com.redxun.sys.core.entity.SysInstTypeMenu;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class SysInstTypeMenuQueryDao extends BaseMybatisDao<SysInstTypeMenu> {

	@Override
	public String getNamespace() {
		return SysInstTypeMenu.class.getName();
	}

	public void deleteByInstTypeId(String typeId) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("typeId",typeId);
		this.deleteBySqlKey("deleteByInstTypeId", params);
	}

}

