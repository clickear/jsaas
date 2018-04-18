package com.redxun.sys.core.dao;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.sys.core.entity.SysFormSchema;
/**
 * <pre> 
 * 描述：SysFormSchema数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class SysFormSchemaDao extends BaseJpaDao<SysFormSchema> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return SysFormSchema.class;
    }
    /**
     * 按模块Id获得实体的表单配置方案
     * @param moduleId
     * @return 
     * SysFormSchema
     * @exception 
     * @since  1.0.0
     */
    public SysFormSchema getByModuleId(String moduleId){
    	String ql="from SysFormSchema m where m.sysModule.moduleId=?";
    	return(SysFormSchema) this.getUnique(ql, moduleId);
    }
}
