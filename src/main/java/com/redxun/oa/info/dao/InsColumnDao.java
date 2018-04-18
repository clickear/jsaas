package com.redxun.oa.info.dao;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.core.query.Page;
import com.redxun.oa.info.entity.InsColumn;
import com.redxun.saweb.context.ContextUtil;
/**
 * <pre> 
 * 描述：InsColumn数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class InsColumnDao extends BaseJpaDao<InsColumn> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return InsColumn.class;
    }
    
    /**
     * 栏目名称的搜索
     * @param name 栏目名称
     * @param page
     * @return
     */
	public List<InsColumn> getByName(String name, Page page){
    	List<String> params=new ArrayList<String>();
    	String tenantId = ContextUtil.getCurrentTenantId();
    	String ql="from InsColumn ins where ins.tenantId = ?";
    	params.add(tenantId);
    	if(StringUtils.isNotEmpty(name)){
            ql+=" and ins.name like ?";
            params.add("%" + name + "%");
        }
    	return this.getByJpql(ql, params.toArray(),page);
    }
    
    /**
     * 根据是否启用和允许关闭显示栏目
     * @return
     */
    public List<InsColumn> getByEnable(){
    	String tenantId = ContextUtil.getCurrentTenantId();
    	String ql = "from InsColumn where allowClose= yes and enabled= yes and tenantId = ?";
    	return this.getByJpql(ql, new Object[]{tenantId});
    }
}
