
/**
 * 
 * <pre> 
 * 描述：INS_MSGBOX_BOX_DEF DAO接口
 * 作者:mansan
 * 日期:2017-09-01 10:58:03
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.oa.info.dao;

import java.util.HashMap;
import java.util.Map;

import com.redxun.oa.info.entity.InsMsgboxBoxDef;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class InsMsgboxBoxDefQueryDao extends BaseMybatisDao<InsMsgboxBoxDef> {

	@Override
	public String getNamespace() {
		return InsMsgboxBoxDef.class.getName();
	}
	
	public void delByBoxId(String boxId){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("boxId", boxId);
		this.deleteBySqlKey("delByBoxId", params);
	}

}

