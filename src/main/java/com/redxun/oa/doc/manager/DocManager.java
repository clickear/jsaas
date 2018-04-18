package com.redxun.oa.doc.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.Page;
import com.redxun.core.query.QueryFilter;
import com.redxun.oa.doc.dao.DocDao;
import com.redxun.oa.doc.entity.Doc;
import com.redxun.oa.doc.entity.DocRight;
import com.redxun.oa.info.entity.InsPortalParams;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.manager.OsGroupManager;
/**
 * <pre> 
 * 描述：Doc业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class DocManager extends BaseManager<Doc>{
	@Resource
	private DocDao docDao;
	@Resource
	private OsGroupManager osGroupManager;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return docDao;
	}
	/**
	 * 通过FolderId来得到该folder下的对应的文件
	 * @param FolderId
	 * @return
	 */
	 public List<Doc> getByFolderId(String userId, int right, String folderId,String type){
		// 获得user的Doc
					List<Doc> rightDoc = docDao.getDocByRight(userId, DocRight.IDENTITYTYPE_USER, right, folderId,type);
					// 获得user所属的group的Doc
					List<OsGroup> groups = osGroupManager.getBelongGroups(userId);
					if (groups.size() > 0) {
						for (OsGroup group : groups) {// 遍历每个组
							List<Doc> list = docDao.getDocByRight(group.getGroupId(), DocRight.IDENTITYTYPE_GROUP, right, folderId, type);// 获得每个组的阅读权限
							for (int i = 0; i < list.size(); i++) {
								if (!rightDoc.contains(list.get(i))) {
									rightDoc.add(list.get(i));
								}
							}
						}
					}
					List<Doc> allDoc = docDao.getByFolderId(folderId,right,type);
					rightDoc.addAll(allDoc);
					return rightDoc;

	 }
	 /**针对手机端ContextUtil不能得到tenantId而重写的方法
		 * 通过FolderId来得到该folder下的对应的文件
		 * @param FolderId
		 * @return
		 */
		 public List<Doc> getByFolderId(String userId, int right, String folderId,String type,String tenantId){
			// 获得user的Doc
						List<Doc> rightDoc = docDao.getDocByRight(userId, DocRight.IDENTITYTYPE_USER, right, folderId,type);
						// 获得user所属的group的Doc
						List<OsGroup> groups = osGroupManager.getBelongGroups(userId);
						if (groups.size() > 0) {
							for (OsGroup group : groups) {// 遍历每个组
								List<Doc> list = docDao.getDocByRight(group.getGroupId(), DocRight.IDENTITYTYPE_GROUP, right, folderId, type);// 获得每个组的阅读权限
								for (int i = 0; i < list.size(); i++) {
									if (!rightDoc.contains(list.get(i))) {
										rightDoc.add(list.get(i));
									}
								}
							}
						}
						List<Doc> allDoc = docDao.getByFolderId(folderId,right,type,tenantId);
						rightDoc.addAll(allDoc);
						return rightDoc;

		 }
	 /**
	  * 搜索某userId的所有某个权限的Doc
	  * @param userId
	  * @param right	权限类型（int）
	  * @param folderId folderId(Key传入的文件所属的文件夹Id)
	  * @param type type(文件夹类型:PERSONAL,COMPANY)
	  * @return
	  */
		public List<Doc> getAllByRgiht(String userId, int right, String folderId,String type) {
			// 获得user的Doc
			List<Doc> rightDoc = docDao.getDocByRight(userId, DocRight.IDENTITYTYPE_USER, right, folderId,type);
			// 获得user所属的group的Doc
			List<OsGroup> groups = osGroupManager.getBelongGroups(userId);
			if (groups.size() > 0) {
				for (OsGroup group : groups) {// 遍历每个组
					List<Doc> list = docDao.getDocByRight(group.getGroupId(), DocRight.IDENTITYTYPE_GROUP, right, folderId, type);// 获得每个组的阅读权限
					for (int i = 0; i < list.size(); i++) {
						if (!rightDoc.contains(list.get(i))) {
							rightDoc.add(list.get(i));
						}
					}
				}
			}
			List<Doc> allDoc = docDao.getAllByRgiht(right, folderId,type);
			rightDoc.addAll(allDoc);
			return rightDoc;

		}
		
		
		/**针对手机端ContextUtil不能得到tenantId而重写的方法
		  * 搜索某userId的所有某个权限的Doc
		  * @param userId
		  * @param right	权限类型（int）
		  * @param folderId folderId(Key传入的文件所属的文件夹Id)
		  * @param type type(文件夹类型:PERSONAL,COMPANY)
		  * @return
		  */
			public List<Doc> getAllByRgiht(String userId, int right, String folderId,String type,String tenantId) {
				// 获得user的Doc
				List<Doc> rightDoc = docDao.getDocByRight(userId, DocRight.IDENTITYTYPE_USER, right, folderId,type);
				// 获得user所属的group的Doc
				List<OsGroup> groups = osGroupManager.getBelongGroups(userId);
				if (groups.size() > 0) {
					for (OsGroup group : groups) {// 遍历每个组
						List<Doc> list = docDao.getDocByRight(group.getGroupId(), DocRight.IDENTITYTYPE_GROUP, right, folderId, type);// 获得每个组的阅读权限
						for (int i = 0; i < list.size(); i++) {
							if (!rightDoc.contains(list.get(i))) {
								rightDoc.add(list.get(i));
							}
						}
					}
				}
				List<Doc> allDoc = docDao.getAllByRgiht(right, folderId,type,tenantId);
				rightDoc.addAll(allDoc);
				return rightDoc;

			}
		/**
		 * Portal门户中新闻Panel显示的List
		 * 
		 * @return
		 * @throws Exception
		 */
		public List<Doc> getPortalDoc(InsPortalParams params) {
			String userId = ContextUtil.getCurrentUserId();
			QueryFilter queryFilter = new QueryFilter();
			Page page = new Page();
			page.setPageIndex(0);
			page.setPageSize(params.getPageSize());
			queryFilter.setPage(page);
			queryFilter.addFieldParam("createBy",userId);
			queryFilter.addFieldParam("docFolder.type", "PERSONAL");
			List<Doc> dataList = getAll(queryFilter);
			return dataList;
		}
}