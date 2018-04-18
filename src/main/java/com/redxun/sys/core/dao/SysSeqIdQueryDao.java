
/**
 * 
 * <pre> 
 * 描述：系统流水号 DAO接口
 * 作者:ray
 * 日期:2018-03-21 15:51:00
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.sys.core.dao;

import com.redxun.sys.core.entity.SysSeqId;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.org.api.model.ITenant;

@Repository
public class SysSeqIdQueryDao extends BaseMybatisDao<SysSeqId> {

	@Override
	public String getNamespace() {
		return SysSeqId.class.getName();
	}

	public SysSeqId getByAlias(String alias,String tenantId){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("alias", alias);
		params.put("tenantId", tenantId);
		SysSeqId seqId=this.getUnique("getByAlias", params);
    	
    	if(seqId!=null){
    		return seqId;
    	}else{
    		params.put("tenantId", ITenant.ADMIN_TENANT_ID);
    		return (SysSeqId)this.getUnique("getByAlias", params);
    	}
	}
	
	
	public int updateVersion(SysSeqId ent){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("curDate", ent.getCurDate());
		params.put("newVal", ent.getNewVal());
		params.put("seqId", ent.getSeqId());
		params.put("curVal", ent.getCurVal());
		
		
		int rtn=this.updateBySqlKey("updateVersion", params);
		return rtn;
	}
	
	
}

