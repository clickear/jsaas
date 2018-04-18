/**
 * 
 * <pre> 
 * 描述：机构类型 DAO接口
 * 作者:mansan
 * 日期:2017-07-10 18:35:31
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.sys.core.entity.SysInstType;

@Repository
public class SysInstTypeDao extends BaseJpaDao<SysInstType> {

	@Override
	protected Class getEntityClass() {
		return SysInstType.class;
	}

	public List<SysInstType> getValidExludePlatform(){
		String ql="from SysInstType t where t.enabled=? and typeCode!='PLATFORM'";
		return this.getByJpql(ql, new Object[]{MBoolean.YES.name()});
	}
	
	public List<SysInstType> getValidAll(){
		String ql="from SysInstType t where t.enabled=? ";
		return this.getByJpql(ql, new Object[]{MBoolean.YES.name()});
	}
	
	public SysInstType getByCode(String typeCode){
		String ql="from SysInstType t where t.typeCode=?";
		return(SysInstType) this.getUnique(ql, typeCode);
	}
	

}

