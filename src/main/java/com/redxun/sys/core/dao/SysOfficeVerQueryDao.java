
/**
 * 
 * <pre> 
 * 描述：OFFICE版本 DAO接口
 * 作者:ray
 * 日期:2018-01-15 15:34:18
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.sys.core.entity.SysOfficeVer;

@Repository
public class SysOfficeVerQueryDao extends BaseMybatisDao<SysOfficeVer> {

	@Override
	public String getNamespace() {
		return SysOfficeVer.class.getName();
	}

	
	/**
	 * 按照版本倒序获取版本数据。
	 * @param officeId
	 * @return
	 */
	public List<SysOfficeVer> getByOfficeId(String officeId){
		List<SysOfficeVer> list= this.getBySqlKey("getByOfficeId", officeId);
		return list;
	}
	
	/**
	 * 根据officeId获取最大的版本。
	 * @param officeId
	 * @return
	 */
	public Integer getVersionByOfficeId(String officeId){
		Integer version= (Integer) this.getOne("getVersionByOfficeId", officeId);
		return version;
	}
}

