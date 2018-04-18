package com.redxun.oa.doc.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.doc.dao.DocFolderDao;
import com.redxun.oa.doc.entity.DocFolder;
/**
 * <pre> 
 * 描述：DocFolder业务服务类
 * 作者：陈茂昌
 * 日期:2015.12.09
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class DocFolderManager extends BaseManager<DocFolder>{
	@Resource
	private DocFolderDao docFolderDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return docFolderDao;
	}
    /**
     * 查询个人文件夹或者是公司文件夹(区分租户)
     * @param UserId,tenantId
     * @return 文件夹List
     */
	public List<DocFolder> getByUserId(String UserId,String tenantId,String type){
		return docFolderDao.getByUserId(UserId,tenantId,type);
	}
	
	/**
     * 查询公司文件夹(区分租户)
     * @param type , tenantId
     * @return 文件夹List
     */
	public List<DocFolder> getCompanyFolder(String type,String tenantId){
		return docFolderDao.getCompanyFolder(type, tenantId);
	}
	/**
     * 查询是否共享文档：通过share是否为YES(区分租户)
     * @param tenantId(租户Id)
     * @return
     */
	public List<DocFolder> getShareFolder(String tenantId){
		return docFolderDao.getShareFolder(tenantId);
	}
    /**
     * 根据路径与传入的folderId的匹配，获取适合的文件夹列表
     * @param folderId,type(PERSONAL:COMPANY)
     * @return
     */
	public List<DocFolder> getSpecialPathFolder(String folderId,String type){
		return docFolderDao.getSpecialPathFolder(folderId,type);
	}
	
    /**
     * 根据路径与传入的folderId的匹配获取适合的列表
     * @param folderId,type(PERSONAL:COMPANY),share(YES:NO)
     * @return
     */
		public List<DocFolder> getSpecialPathFolderWithShare(String folderId,String type,String share,String tenantId){
			return docFolderDao.getSpecialPathFolderWithShare(folderId, type, share,tenantId);
		}
	
	/**
	 * 根据parent=folderId获取子目录文件夹(获取兄弟节点)
	 * 
	 * @param folderId
	 * @return 文件夹list
	 */
	public List<DocFolder> getSubFolder(String folderId) {
		return docFolderDao.getSubFolder(folderId);
	}
}