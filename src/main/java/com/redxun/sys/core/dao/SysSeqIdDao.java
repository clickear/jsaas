package com.redxun.sys.core.dao;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.org.api.model.ITenant;
import com.redxun.sys.core.entity.SysSeqId;
/**
 * <pre> 
 * 描述：SysSeqId数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class SysSeqIdDao extends BaseJpaDao<SysSeqId> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return SysSeqId.class;
    }
    
    /**
     * 通过别名及机构Id获得流水号的设置
     * @param alias
     * @param tenantId
     * @return
     */
    public SysSeqId getByAlias(String alias,String tenantId){
    	String ql="from SysSeqId seq where seq.alias=? and seq.tenantId=?";
    	SysSeqId seqId=(SysSeqId)this.getUnique(ql, new Object[]{alias,tenantId});
    	if(seqId!=null){
    		return seqId;
    	}else{
    		return (SysSeqId)this.getUnique(ql, new Object[]{alias,ITenant.ADMIN_TENANT_ID});
    	}
    }
}
