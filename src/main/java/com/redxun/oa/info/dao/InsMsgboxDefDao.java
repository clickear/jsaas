
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

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.info.entity.InsMsgboxDef;

@Repository
public class InsMsgboxDefDao extends BaseJpaDao<InsMsgboxDef> {


	@Override
	protected Class getEntityClass() {
		return InsMsgboxDef.class;
	}
	
	public InsMsgboxDef getbyKey(String boxKey){
		String ql = "from InsMsgboxDef m where m.key = ?";
		return (InsMsgboxDef) this.getUnique(ql, new Object[]{boxKey});
	}

}

