package com.redxun.sys.org.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.org.api.model.ITenant;
import com.redxun.sys.org.entity.OsRelType;
/**
 * <pre> 
 * 描述：OsRelType数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class OsRelTypeDao extends BaseJpaDao<OsRelType> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return OsRelType.class;
    }
    /**
     * 获得某种类型的关系列表
     * @param relType
     * @return
     */
    public List<OsRelType> getByRelType(String relType){
    	String ql="from OsRelType where relType=?";
    	return this.getByJpql(ql, new Object[]{relType});
    }
    
    /**
     * 获得用户的关系，排除用户从属关系
     * @return
     */
    public List<OsRelType> getUserRelTypeExcludeBelong(){
    	String ql="from OsRelType o where (o.relType=? or o.relType=?) and o.key!=?";
    	return this.getByJpql(ql, new Object[]{OsRelType.REL_TYPE_GROUP_USER,OsRelType.REL_TYPE_USER_USER,OsRelType.REL_CAT_GROUP_USER_BELONG});
    }
    
    /**
     * 获得某租户下的某种类型的关系列表
     * @param relType
     * @param tenantId
     * @return
     */
    public List<OsRelType> getByRelTypeTenantId(String relType,String tenantId){
    	String ql="from OsRelType where relType=? and tenantId=?";
    	return this.getByJpql(ql, new Object[]{relType,tenantId});
    }
    
    /**
     * 获得某租户下的Key对应的关系
     * @param key
     * @param tenantId
     * @return
     */
    public OsRelType getByKeyTenanId(String key,String tenantId){
    	String ql="from OsRelType where key=? and tenantId in (?,?)";
    	return (OsRelType)this.getUnique(ql, new Object[]{key,tenantId,ITenant.PUBLIC_TENANT_ID});
    }
    /**
     * 按Key获得某种关系类型
     * @param key
     * @return
     */
    public OsRelType getByRelTypeKey(String key){
    	String ql="from OsRelType where key=?";
    	return (OsRelType)this.getUnique(ql, new Object[]{key});
    }
    
    /**
     * 通过维度ID及关系类型获得所有关系
     * @param dimId
     * @param relType
     * @return
     */
    public List<OsRelType> getByDimId1RelType(String dimId,String relType,String tenantId){
    	String ql="from OsRelType o where (o.dim1 is null or o.dim1.dimId=?) and o.relType=? and o.tenantId in (?,?) order by o.createTime asc";
    	return this.getByJpql(ql, new Object[]{dimId,relType,tenantId,ITenant.PUBLIC_TENANT_ID});
    }
    /**
     * 获得租户下可用的某种关系类型定义
     * @param relType
     * @param tenantId
     * @return
     */
    public List<OsRelType> getByRelTypeTenantIdIncludePublic(String relType,String tenantId){
    	String ql="from OsRelType o where o.relType=? and o.tenantId in (?,?)";
    	return this.getByJpql(ql, new Object[]{relType,tenantId,ITenant.PUBLIC_TENANT_ID});
    }
    
    /**
     * 获得某个维度下的所有关系
     * @param dimId
     * @return
     */
    public List<OsRelType> getByDimIdRelType(String dimId){
    	String ql="from OsRelType o where o.dim1.dimId=? or (o.dim2.dimId=? and o.isTwoWay=?)";
    	return this.getByJpql(ql, new Object[]{dimId,dimId,MBoolean.YES.toString()});
    }
}
