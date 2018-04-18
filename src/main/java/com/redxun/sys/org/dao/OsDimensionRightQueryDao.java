
/**
 * 
 * <pre> 
 * 描述：维度授权 DAO接口
 * 作者:ray
 * 日期:2017-05-16 14:12:56
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.org.dao;

import java.util.HashMap;
import java.util.Map;

import com.redxun.sys.org.entity.OsDimensionRight;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class OsDimensionRightQueryDao extends BaseMybatisDao<OsDimensionRight> {

	@Override
	public String getNamespace() {
		return OsDimensionRight.class.getName();
	}
	
	public OsDimensionRight getDimRightByDimId(String dimId){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("dimId", dimId);
		return this.getUnique("getDimRightByDimId", params);
	}

}

