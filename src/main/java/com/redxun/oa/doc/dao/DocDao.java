package com.redxun.oa.doc.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.doc.entity.Doc;
import com.redxun.saweb.context.ContextUtil;
/**
 * <pre> 
 * 描述：Doc数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class DocDao extends BaseJpaDao<Doc> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return Doc.class;
    }
    
  
    /**
     * 返回文件所属的文件夹folderId跟当前文件夹FolderId匹配的文件，并且文件夹的路径包含这个folderId
     * @param folderId(Key传入的文件所属的文件夹Id)，type(文件夹类型:PERSONAL,COMPANY)
     * @return list 
     */
    public List<Doc> getDocByRight(String userId,String identityType,int right,String folderId,String type){
    	String tenantId = ContextUtil.getCurrentTenantId();
    	String ql="select d from Doc d left join d.docRights r where r.identityType = ? and r.identityId = ? and r.rights=? and d.docFolder.path like ? and d.docFolder.type=? and d.tenantId = ?";
    	return this.getByJpql(ql,new Object[]{identityType,userId,right,"%"+folderId+"%",type,tenantId});
    }
    
    /**针对手机端没有contextUtil内驻用户而设置可以手动传入tenantId参数
     * 返回文件所属的文件夹folderId跟当前文件夹FolderId匹配的文件，并且文件夹的路径包含这个folderId
     * @param folderId(Key传入的文件所属的文件夹Id)，type(文件夹类型:PERSONAL,COMPANY)
     * @return list 
     */
    public List<Doc> getDocByRight(String userId,String identityType,int right,String folderId,String type,String tenantId){
    	//String tenantId = ContextUtil.getCurrentTenantId();
    	String ql="select d from Doc d left join d.docRights r where r.identityType = ? and r.identityId = ? and r.rights=? and d.docFolder.path like ? and d.docFolder.type=? and d.tenantId = ?";
    	return this.getByJpql(ql,new Object[]{identityType,userId,right,"%"+folderId+"%",type,tenantId});
    }
    
    /**
	 * 根据权限获得所有为ALL的doc
	 * @param right
	 * @return
	 */
	public List<Doc> getAllByRgiht(int right, String folderId,String type){
		String tenantId = ContextUtil.getCurrentTenantId();
    	String ql="select d from Doc d left join d.docRights as rights where rights.identityType = 'ALL' and rights.identityId = 'ALL' and rights.rights=? and (d.docFolder.path like ? or d.docFolder.folderId=?) and d.docFolder.type=? and d.tenantId = ?";
    	return this.getByJpql(ql,new Object[]{right,"%"+folderId+"%",folderId,type,tenantId});
	}
	
	/**针对手机端没有contextUtil内驻用户而设置可以手动传入tenantId参数
	 * 根据权限获得所有为ALL的doc
	 * @param right
	 * @return
	 */
	public List<Doc> getAllByRgiht(int right, String folderId,String type,String tenantId){
		//String tenantId = ContextUtil.getCurrentTenantId();
    	String ql="select d from Doc d left join d.docRights as rights where rights.identityType = 'ALL' and rights.identityId = 'ALL' and rights.rights=? and (d.docFolder.path like ? or d.docFolder.folderId=?) and d.docFolder.type=? and d.tenantId = ?";
    	return this.getByJpql(ql,new Object[]{right,"%"+folderId+"%",folderId,type,tenantId});
	}
    
    
    /**
     * 通过文件夹Id(folderId)来查找匹配的此文件夹下的文档
     * @param folderId
     * @return list
     */
    public List<Doc> getByFolderId(String folderId,int right,String type){
    	String tenantId = ContextUtil.getCurrentTenantId();
    	String ql="select d from Doc d left join d.docRights as rights where d.docFolder.folderId=? and rights.identityType = 'ALL' and rights.identityId = 'ALL' and rights.rights=? and d.tenantId = ? and d.docFolder.type=?";
    	return this.getByJpql(ql, folderId,right,tenantId,type);
    }
    
    /**针对手机端没有contextUtil内驻用户而设置可以手动传入tenantId参数
     * 通过文件夹Id(folderId)来查找匹配的此文件夹下的文档
     * @param folderId
     * @return list
     */
    public List<Doc> getByFolderId(String folderId,int right,String type,String tenantId){
    	//String tenantId = ContextUtil.getCurrentTenantId();
    	String ql="select d from Doc d left join d.docRights as rights where d.docFolder.folderId=? and rights.identityType = 'ALL' and rights.identityId = 'ALL' and rights.rights=? and d.tenantId = ? and d.docFolder.type=?";
    	return this.getByJpql(ql, folderId,right,tenantId,type);
    }
}
