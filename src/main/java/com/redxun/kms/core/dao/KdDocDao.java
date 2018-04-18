package com.redxun.kms.core.dao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.core.query.Page;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.QueryParam;
import com.redxun.kms.core.entity.KdDoc;
import com.redxun.saweb.context.ContextUtil;
/**
 * <pre> 
 * 描述：KdDoc数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class KdDocDao extends BaseJpaDao<KdDoc> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return KdDoc.class;
    }
    
    /**
     * 个人中心的查询
     * @param userId
     * @param identityType
     * @param right
     * @param queryFilter
     * @return
     */
    public List<KdDoc> getPersonalDoc(String userId,String docType,String identityType,String right, QueryFilter queryFilter) {
		List<Object> params = new ArrayList<Object>();
		String tenantId = ContextUtil.getCurrentTenantId();
		String ql="select k from KdDoc k left join k.kdDocRights as rights where rights.identityType = ? and rights.identityId = ? and rights.right=? and k.docType = ? and k.tenantId = ? ";
		params.add(identityType);
		params.add(userId);
		params.add(right);
		params.add(docType);
		params.add(tenantId);
		Map<String, QueryParam> queryParams = queryFilter.getQueryParams();
		// 是否有查询状态
		if (queryParams.get("status") != null) {
			ql += " and k.status = ?";
			params.add(queryParams.get("status").getValue());
		}

		// 排序
			ql += " order by k.createTime desc";

		List<KdDoc> list = this.getByJpql(ql, params.toArray(), queryFilter.getPage());
		return list;
	}
    /**
	 * 个人中心页面，根据权限获得所有为ALL的doc
	 * @param right
	 * @return
	 */
	public List<KdDoc> getPersonalAllDoc(String right,QueryFilter queryFilter){
		List<Object> params = new ArrayList<Object>();
		String tenantId = ContextUtil.getCurrentTenantId();
		String ql="select k from KdDoc k left join k.kdDocRights as rights where rights.identityType = 'ALL' and rights.identityId = 'ALL' and rights.right=? and k.docType = 'KD_DOC' and k.tenantId = ?";
		params.add(right);
		params.add(tenantId);
		Map<String, QueryParam> queryParams = queryFilter.getQueryParams();
		// 是否有查询状态
		if (queryParams.get("status") != null) {
			ql += " and k.status = ?";
			params.add(queryParams.get("status").getValue());
		}
		ql += " order by k.createTime desc";
		List<KdDoc> list = this.getByJpql(ql, params.toArray(), queryFilter.getPage());
		return list;
	}
    
    
    /**
	 * 根据分类的路径获取
	 * @param path
	 * @param page
	 * @return
	 */
	public List<KdDoc> getByPath(String path,String docType, QueryFilter queryFilter){
		String ql="";
		String tenantId = ContextUtil.getCurrentTenantId();
		if(StringUtils.isEmpty(path)){
			ql="select q  from KdDoc q where q.docType = ? and q.tenantId = ?";
			return this.getByJpql(ql,new Object[]{docType,tenantId},queryFilter.getPage());
		}else{
			ql="select q  from KdDoc q where q.sysTree.path like ? and q.docType = ? and q.tenantId = ?";
			return this.getByJpql(ql,new Object[]{path+"%",docType,tenantId},queryFilter.getPage());
		}
		
	}
	
	/**
	 * 根据权限、用户人Id、用户类型查询所有的doc
	 * @param userId 用户Id
	 * @param identityType 用户类型 user/group
	 * @param right 权限类型 read/edit/..
	 * @param page 
	 * @return
	 */
	public List<KdDoc> getDocByRight(String docType,String userId,String identityType,String right,String treeId, QueryFilter queryFilter){
		List<Object> params = new ArrayList<Object>();
		String tenantId = ContextUtil.getCurrentTenantId();
		String ql="select k from KdDoc k left join k.kdDocRights as rights where rights.identityType = ? and rights.identityId = ? and rights.right=? and k.status='ISSUED' and k.docType=? and k.tenantId = ?";
		params.add(identityType);
		params.add(userId);
		params.add(right);
		params.add(docType);
		params.add(tenantId);
		Map<String, QueryParam> queryParams = queryFilter.getQueryParams();
		// 是否有查询状态
		if(StringUtils.isNotBlank(treeId)){
			ql += " and k.sysTree.path like ?";
			params.add(treeId+"%");
		}
		if (queryParams.get("authorType") != null) {
			ql += " and k.authorType = ?";
			params.add(queryParams.get("authorType").getValue());
		}
		if (queryParams.get("isEssence") != null) {
			ql += " and k.isEssence = ?";
			params.add(queryParams.get("isEssence").getValue());
		}
		if (queryParams.get("subject") != null) {
			ql += " and k.subject like ?";
			params.add("%"+queryParams.get("subject").getValue()+"%");
		}
		// 排序
			ql += " order by k.createTime desc";

		List<KdDoc> list = this.getByJpql(ql, params.toArray(), queryFilter.getPage());
		return list;
	}
	
/*	*//**
	 * 根据权限、用户人Id、用户类型、分类Id查询所有的doc
	 * @param userId 用户Id
	 * @param identityType 用户类型 user/group
	 * @param right 权限类型 read/edit/..
	 * @param treeId 分类Id
	 * @param page 
	 * @return
	 *//*
	public List<KdDoc> getDocByRightTreeId(String userId,String identityType,String right,String treeId,QueryFilter queryFilter){
		List<Object> params = new ArrayList<Object>();
		String ql="select k from KdDoc k left join k.kdDocRights as rights where rights.identityType = ? and rights.identityId = ? and rights.right=? and k.sysTree.treeId = ? and k.status='issued'";
		params.add(identityType);
		params.add(userId);
		params.add(right);
		params.add(treeId);
		List<KdDoc> list = this.getByJpql(ql, params.toArray(), queryFilter.getPage());
		return list;
	}
	*/
	
	/**
	 * 根据权限获得所有为ALL的doc
	 * @param right
	 * @return
	 */
	public List<KdDoc> getAllByRgiht(String right,String docType,String treeId,QueryFilter queryFilter){
		List<Object> params = new ArrayList<Object>();
		String tenantId = ContextUtil.getCurrentTenantId();
		String ql="select k from KdDoc k left join k.kdDocRights as rights where rights.identityType = 'ALL' and rights.identityId = 'ALL' and rights.right=?  and k.status='ISSUED' and k.docType=? and k.tenantId = ?";
		params.add(right);
		params.add(docType);
		params.add(tenantId);
		Map<String, QueryParam> queryParams = queryFilter.getQueryParams();
		// 是否有查询状态
		if(StringUtils.isNotBlank(treeId)){
			ql += " and k.sysTree.path like ?";
			params.add(treeId+"%");
		}
		if (queryParams.get("authorType") != null) {
			ql += " and k.authorType = ?";
			params.add(queryParams.get("authorType").getValue());
		}
		if (queryParams.get("isEssence") != null) {
			ql += " and k.isEssence = ?";
			params.add(queryParams.get("isEssence").getValue());
		}
		if (queryParams.get("subject") != null) {
			ql += " and k.subject like ?";
			params.add("%"+queryParams.get("subject").getValue()+"%");
		}
		ql += " order by k.createTime desc";
		List<KdDoc> list = this.getByJpql(ql, params.toArray(), queryFilter.getPage());
		return list;
	}
	

	/**
	 * 根据权限、分类Id获得所有为ALL的doc
	 * @param right
	 * @return
	 */
	public List<KdDoc> getAllByRgihtTreeId(String right,String treeId){
		String tenantId = ContextUtil.getCurrentTenantId();
		String ql="select k from KdDoc k left join k.kdDocRights as rights where rights.identityType = 'ALL' and rights.identityId = 'ALL' and rights.right=? and k.sysTree.path like ? and k.status='ISSUED' and k.docType = 'KD_DOC' and k.tenantId = ?";
		return this.getByJpql(ql, new Object[]{right,treeId+"%",tenantId});
	}
	
	/**
	 * 首页获取最新的Doc
	 * @return
	 */
	public List<KdDoc> getByNewest(String userId,String docType){
		String tenantId = ContextUtil.getCurrentTenantId();
		String ql="select k from KdDoc k left join k.kdDocRights as rights where rights.identityType = 'user' and rights.identityId = ? and rights.right='read' and k.status='ISSUED' and k.docType=? and k.tenantId = ? order by k.createTime desc";
		Page page=new Page(0,8);
		return this.getByJpql(ql, new Object[]{userId,docType,tenantId},page);
	}
	
	/**
	 * 首页获取最热门的Doc
	 * @return
	 */
	public List<KdDoc> getByHotest(String userId,String docType){
		String tenantId = ContextUtil.getCurrentTenantId();
		String ql="select k from KdDoc k left join k.kdDocRights as rights where rights.identityType = 'user' and rights.identityId = ? and rights.right='read' and k.status='ISSUED' and k.docType=? and k.tenantId = ? order by k.viewTimes desc";
		Page page=new Page(0,8);
		return this.getByJpql(ql, new Object[]{userId,docType,tenantId},page);
	}
	
	/**
	 * Home首页获得root推荐的Doc
	 * @return
	 */
	public List<KdDoc> getByRem(){
		String tenantId = ContextUtil.getCurrentTenantId();
		String ql="select k from KdDoc k left join k.kdDocRems as rems where k.status='ISSUED' and rems.recTreeId='root' and k.docType = 'KD_DOC' and k.tenantId = ? order by k.createTime desc";
		return this.getByJpql(ql, new Object[]{tenantId});
	}
	
	/**
	 * 首页获取精华的Doc
	 * @return
	 */
	public List<KdDoc> getByEssence(){
		String tenantId = ContextUtil.getCurrentTenantId();
		String ql="select k from KdDoc k where k.isEssence='YES' and k.status='ISSUED' and k.docType = 'KD_DOC' and k.tenantId = ? order by k.createTime desc";
		return this.getByJpql(ql, new Object[]{tenantId});
	}
	
	/**
	 * 个人中心获取所评论的所有Doc
	 * @param userId
	 * @return
	 */
	public List<KdDoc> getDocByCmmt(String userId,String docType,QueryFilter queryFilter){
		List<Object> params = new ArrayList<Object>();
		String tenantId = ContextUtil.getCurrentTenantId();
		String ql="select k from KdDoc k left join k.kdDocCmmts as cmmts where cmmts.createBy = ? and k.docType = ? and k.tenantId = ?";
		params.add(userId);
		params.add(docType);
		params.add(tenantId);
		Map<String, QueryParam> queryParams = queryFilter.getQueryParams();
		// 是否有查询状态
		if (queryParams.get("status") != null) {
			ql += " and k.status = ?";
			params.add(queryParams.get("status").getValue());
		}
		ql += " order by k.createTime desc";
		List<KdDoc> list = this.getByJpql(ql, params.toArray(), queryFilter.getPage());
		return list;
	}
	
	/**
	 * 个人中心获取所有我的文档
	 * @param userId
	 * @return
	 */
	public List<KdDoc> getMyDoc(String userId,String docType,QueryFilter queryFilter){
		List<Object> params = new ArrayList<Object>();
		String tenantId = ContextUtil.getCurrentTenantId();
		String ql="select k from KdDoc k where k.createBy = ? and k.docType =? and k.tenantId = ?";
		params.add(userId);
		params.add(docType);
		params.add(tenantId);
		Map<String, QueryParam> queryParams = queryFilter.getQueryParams();
		// 是否有查询状态
		if (queryParams.get("status") != null) {
			ql += " and k.status = ?";
			params.add(queryParams.get("status").getValue());
		}
		ql += " order by k.createTime desc";
		List<KdDoc> list = this.getByJpql(ql, params.toArray(), queryFilter.getPage());
		return list;
	}
	
	/**
	 * 个人中心获得所有收藏的文档
	 * @param userId
	 * @param queryFilter
	 * @return
	 */
	public List<KdDoc> getMyFav(String userId,String docType, QueryFilter queryFilter){
		List<Object> params = new ArrayList<Object>();
		String tenantId = ContextUtil.getCurrentTenantId();
		String ql="select k from KdDoc k left join k.kdDocFavs as favs where favs.createBy = ?  and k.docType = ? and k.tenantId = ?";
		params.add(userId);
		params.add(docType);
		params.add(tenantId);
		Map<String, QueryParam> queryParams = queryFilter.getQueryParams();
		// 是否有查询状态
		if (queryParams.get("status") != null) {
			ql += " and k.status = ?";
			params.add(queryParams.get("status").getValue());
		}
		ql += " order by k.createTime desc";
		List<KdDoc> list = this.getByJpql(ql, params.toArray(), queryFilter.getPage());
		return list;		
	}
	
	/**
	 * 判断文档是否还在
	 * @param docId
	 * @return
	 */
	 public boolean getIsAlive(String docId,String docType){
			String tenantId = ContextUtil.getCurrentTenantId();
	    	String ql = "select count(*) from KdDoc k where k.docId = ? and k.docType = ? and k.tenantId = ?";
	    	Long a =(Long)this.getUnique(ql, new Object[]{docId,docType,tenantId});
	    	if(a>0){
	    		return true;
	    	}else {
	    		return false;
	    	}
	    }
	 
	 /**
	  * 获取所有知识地图
	  * @param queryFilter
	  * @return
	  */
	 public List<KdDoc> getKdMapList(QueryFilter queryFilter){
			String tenantId = ContextUtil.getCurrentTenantId();
		 String ql="From KdDoc d where d.docType=? and d.tenantId = ?";
		 return this.getByJpql(ql, new Object[]{"KD_MAP",tenantId},queryFilter.getPage());
	 }
	 
	 
	 /**
	  * 根据路径获取知识地图
	  * @param path
	  * @param page
	  * @return
	  */
	 public List<KdDoc> getMapByPath(String path,Page page){
			String tenantId = ContextUtil.getCurrentTenantId();
		String ql="select m  from KdDoc m where m.sysTree.path like ? and m.docType=? and m.tenantId = ?";
		return this.getByJpql(ql,new Object[]{path+"%","KD_MAP",tenantId},page);
	 }
	 
	 /**
	  * 根据类型获取文档
	  * @param path
	  * @param page
	  * @return
	  */
	 public List<KdDoc> getByDocType(String docType,Page page){
		 String tenantId = ContextUtil.getCurrentTenantId();
		 String ql="from KdDoc d where d.docType=? and d.tenantId = ?";
		 return this.getByJpql(ql, new Object[]{docType,tenantId},page);
	 }
	 
	 /**
	 * 首页获取最新的地图
	 * @return
	 */
	public List<KdDoc> getMapByNewest(String userId){
		String tenantId = ContextUtil.getCurrentTenantId();
		String ql="select k from KdDoc k left join k.kdDocRights as rights where rights.identityType = 'user' and rights.identityId = ? and rights.right='read' and k.status='ISSUED' and k.docType=? and k.tenantId = ? order by k.createTime desc";
		Page page=new Page(0, 8);
		return this.getByJpql(ql, new Object[]{userId,"KD_MAP",tenantId},page);
	}
	
	/**
	 * 首页获取最热门的地图
	 * @return
	 */
	public List<KdDoc> getMapByHotest(String userId){
		String tenantId = ContextUtil.getCurrentTenantId();
		String ql="select k from KdDoc k left join k.kdDocRights as rights where rights.identityType = 'user' and rights.identityId = ? and rights.right='read' and k.status='ISSUED' and k.docType=? and k.tenantId = ? order by k.viewTimes desc";
		Page page=new Page(0, 8);
		return this.getByJpql(ql, new Object[]{userId,"KD_MAP",tenantId},page);
	}
}
