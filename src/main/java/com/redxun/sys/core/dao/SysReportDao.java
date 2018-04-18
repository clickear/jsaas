package com.redxun.sys.core.dao;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.sys.core.entity.SysModule;
import com.redxun.sys.core.entity.SysReport;
/**
 * <pre> 
 * 描述：SysReport数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class SysReportDao extends BaseJpaDao<SysReport> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return SysReport.class;
    }
    
    /**
     * 根据租户Id和key值查询是否有这个报表
     * @param key
     * @param tenantId
     * @return
     */
    public SysReport getByKey(String key,String tenantId){
    	String ql="from SysReport sr where sr.key=? and sr.tenantId=?";
    	return(SysReport)this.getUnique(ql, key,tenantId);
    }
}
