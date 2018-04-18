package com.redxun.sys.core.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.core.query.Page;
import com.redxun.core.query.SortParam;
import com.redxun.sys.core.entity.SysSearch;
import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
/**
 * <pre> 
 * 描述：SysSearch数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class SysSearchDao extends BaseJpaDao<SysSearch> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return SysSearch.class;
    }
    /**
     * 查找自身的某实体过滤器
     * @param userId
     * @param entityName
     * @return 
     * List<SysSearch>
     * @exception 
     * @since  1.0.0
     */
    public List<SysSearch> getByEntityNameUserId(String entityName,String userId){
    	String ql="from SysSearch m where m.createBy=? and m.entityName=?";
    	return this.getByJpql(ql, new Object[]{userId,entityName});
    }
    
    /**
     * 按搜索条件名查找某实体及用户下的条件列表
     * @param entityName
     * @param userId
     * @param name
     * @return 
     */
    public List<SysSearch> getByEntityNameUserIdName(String entityName,String userId,String name,Page page,SortParam sortParam){
        List params=new ArrayList();
        String ql="from SysSearch m where m.entityName=? and m.createBy=? ";
        params.add(entityName);
        params.add(userId);
        if(StringUtils.isNotEmpty(name)){
            ql+="and name like ?";
            params.add("%" + name + "%");
        }
        //加上排序
        if(sortParam!=null){
            ql+=" order by " + sortParam.getProperty() + " " + sortParam.getDirection();
        }
        return this.getByJpql(ql,params.toArray(),page);
    }
    
}
