package com.redxun.oa.doc.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.doc.entity.DocFolder;
/**
 * <pre> 
 * 描述：文件夹数据访问类
 * 作者：陈茂昌
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class DocFolderDao extends BaseJpaDao<DocFolder> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return DocFolder.class;
    }
    
    /**
     * 根据文件夹Id(folderId)获取相应的文件夹
     * @param  folderId(文件夹ID)
     * @return list(文件夹list)
     */
    public List<DocFolder> getByFolderId(String folderId){
    	String ql="from DocFolder df where df.folderId=?";
    	return this.getByJpql(ql,folderId);
    }
    
     /**
      * 查询个人文件夹(区分租户)
      * @param UserID：docFolder的字段，有userId的代表是个人文件夹
      * @return 文件夹List
      */
    public List<DocFolder> getByUserId(String UserId,String tenantId,String type){
    	String ql="from DocFolder df where df.userId=? and df.tenantId=? and df.type=? order by sn asc";
    	return this.getByJpql(ql, UserId,tenantId,type);
    }
    
    
    /**
     * 查询公司文件夹(区分租户)
     * @param type：docFolder的字段，代表是个人还是公司文件夹
     * @return 文件夹List
     */
   public List<DocFolder> getCompanyFolder(String type,String tenantId){
   	String ql="from DocFolder df where df.type=? and df.tenantId=? order by sn asc";
   	return this.getByJpql(ql, type,tenantId);
   }
    
    /**
     * 查询是否共享文档：通过share是否为YES(区分租户)
     * @param tenantId(租户Id)
     * @return
     */
    public List<DocFolder> getShareFolder(String tenantId){
    	String ql="from DocFolder df where df.share=? and df.tenantId=?";
    	String  yes="YES";
    	return this.getByJpql(ql,yes,tenantId);
    }
    
    /**
     * 根据路径与传入的folderId的匹配，获取适合的文件夹列表
     * @param folderId，type(PERSONAL:COMPANY)
     * @return
     */
    public List<DocFolder> getSpecialPathFolder(String folderId,String type){
    	String ql="from DocFolder df where df.path LIKE ? and df.type=? order by sn asc";
    	return this.getByJpql(ql,"%"+folderId+"%",type);
    }
    
    /**
     * 根据路径与传入的folderId的匹配获取适合的列表
     * @param folderId,type(PERSONAL:COMPANY),share(YES:NO)
     * @return
     */
    public List<DocFolder> getSpecialPathFolderWithShare(String folderId,String type,String share,String tenantId){
    	String ql="from DocFolder df where df.path LIKE ? and df.type=? and df.share=? and tenantId=? order by sn asc";
    	return this.getByJpql(ql,"%"+folderId+"%",type,share,tenantId);
    }
    
    
    /**
     * 根据parent=folderId获取子目录文件夹(获取兄弟节点)
     * @param folderId 
     * @return 文件夹list
     */
    public List<DocFolder> getSubFolder(String folderId){
    String ql="from DocFolder df where df.parent=? order by sn asc";
    return this.getByJpql(ql, folderId);
    }
    
    
}
