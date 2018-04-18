
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

import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.info.entity.InsMsgboxBoxDef;

@Repository
public class InsMsgboxBoxDefDao extends BaseJpaDao<InsMsgboxBoxDef> {


	@Override
	protected Class getEntityClass() {
		return InsMsgboxBoxDef.class;
	}
	
	public List<InsMsgboxBoxDef> getByMsgBoxId(String boxId){
		String ql = "from InsMsgboxBoxDef m where m.boxId = ? order by m.sn";
		return this.getByJpql(ql, new Object[]{boxId});
	}

}

