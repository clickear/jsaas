
/**
 * 
 * <pre> 
 * 描述：系统自定义业务管理列表 DAO接口
 * 作者:mansan
 * 日期:2017-05-21 12:11:18
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.core.dao;

import com.redxun.sys.core.entity.SysBoList;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class SysBoListDao extends BaseJpaDao<SysBoList> {

	@Override
	protected Class getEntityClass() {
		return SysBoList.class;
	}
	
	/**
	 * 
	 * @param key
	 * @param tenantId
	 * @return
	 */
	public SysBoList getByKey(String key,String tenantId){
		String ql="from SysBoList bo where bo.key=? and bo.tenantId=?";
		return (SysBoList)this.getUnique(ql, new Object[]{key,tenantId});
	}

}

