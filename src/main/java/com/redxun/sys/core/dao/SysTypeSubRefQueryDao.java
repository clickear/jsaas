
/**
 * 
 * <pre> 
 * 描述：机构--子系统关系 DAO接口
 * 作者:陈茂昌
 * 日期:2017-09-13 15:57:55
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.core.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.sys.core.entity.SysTypeSubRef;

@Repository
public class SysTypeSubRefQueryDao extends BaseMybatisDao<SysTypeSubRef> {

	@Override
	public String getNamespace() {
		return SysTypeSubRef.class.getName();
	}

	public void deleteByInstType(String instTypeId) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("instTypeId", instTypeId);
		this.deleteBySqlKey("deleteByInstType", params);
		
	}
	public List<SysTypeSubRef> getByInstId(String instId){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("instId", instId);
		return this.getBySqlKey("getByInstId", params);
	}
	 public SysTypeSubRef getBySysIdAndTypeId(String sysId,String typeId){
		 Map<String,Object> params=new HashMap<String,Object>();
		 params.put("sysId", sysId);
		 params.put("typeId", typeId);
		 return this.getUnique("getBySysIdAndTypeId", params);
	 }
	 public List<SysTypeSubRef> getByTypeId(String typeId){
		 Map<String,Object> params=new HashMap<String,Object>();
		 params.put("typeId", typeId);
		 return this.getBySqlKey("getByInstId", params);
	 }

}

