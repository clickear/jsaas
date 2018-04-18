package com.redxun.sys.core.dao;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.sys.core.entity.SysModule;
/**
 * <pre> 
 * 描述：SysModule数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class SysModuleDao extends BaseJpaDao<SysModule> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return SysModule.class;
    }
    
    /**
     * 按实体模块名称获取模块记录
     * @param entityClassName
     * @return 
     * SysModule
     * @exception 
     * @since  1.0.0
     */
    public SysModule getByEntityClass(String entityClassName){
    	String ql="from SysModule sm where sm.entityName=?";
    	return(SysModule)this.getUnique(ql, entityClassName);
    }

}
