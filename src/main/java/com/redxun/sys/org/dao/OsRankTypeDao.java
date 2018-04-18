package com.redxun.sys.org.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.sys.org.entity.OsRankType;
/**
 * <pre> 
 * 描述：OsRankType数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class OsRankTypeDao extends BaseJpaDao<OsRankType> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return OsRankType.class;
    }
    
    /**
     * 按维度ID获得等级分类列表
     * @param dimId
     * @return 
     * List<OsRankType>
     * @exception 
     * @since  1.0.0
     */
    public List<OsRankType> getByDimId(String dimId){
    	String ql="from OsRankType r where r.osDimension.dimId=? order by r.level asc";
    	return this.getByJpql(ql, dimId);
    }
    
    public List<OsRankType> getByDimIdTenantId(String dimId,String tenantId){
    	String ql="from OsRankType r where r.osDimension.dimId=? and r.tenantId=? order by r.level asc";
    	return this.getByJpql(ql, new Object[]{dimId,tenantId});
    }
    /**
     * 删除维度下的等级
     * @param dimId
     */
    public void deleteByDimId(String dimId){
    	String ql="delete from OsRankType r where r.osDimension.dimId=? ";
    	this.delete(ql, new Object[]{dimId});
    }
    
    public OsRankType getbyKey(String key,String tenantId){
    	String hql="from OsRankType ort where ort.key=? and ort.tenantId=?";
    	return (OsRankType) this.getUnique(hql, key,tenantId);
    }
}
