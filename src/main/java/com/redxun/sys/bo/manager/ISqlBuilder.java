package com.redxun.sys.bo.manager;

import com.redxun.sys.bo.entity.SysBoEnt;
import com.redxun.core.entity.SqlModel;

/**
 * 此接口用于构建子表查询的sql语句。
 * @author yongguo
 *
 */
public interface ISqlBuilder {
	
	/**
	 * 根据外键查询数据。
	 * @param boEnt
	 * @param fk
	 * @return
	 */
	SqlModel getByFk(SysBoEnt boEnt,String fk);

}
