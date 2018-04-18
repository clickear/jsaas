package com.redxun.kms.core.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.Page;
import com.redxun.core.query.QueryFilter;
import com.redxun.kms.core.dao.KdDocDao;
import com.redxun.kms.core.entity.KdCoverImage;
import com.redxun.kms.core.entity.KdDoc;
import com.redxun.kms.core.entity.KdDocRight;
import com.redxun.kms.core.entity.SortHotKdDoc;
import com.redxun.kms.core.entity.SortNewKdDoc;
import com.redxun.oa.info.entity.InsPortalParams;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.manager.OsGroupManager;

/**
 * <pre>
 * 描述：KdDoc业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2016-3-2-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class KdDocManager extends BaseManager<KdDoc> {
	@Resource
	private KdDocDao kdDocDao;
	@Resource
	private OsGroupManager osGroupManager;
	@Resource
	private KdDocRightManager kdDocRightManager;

	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return kdDocDao;
	}

	/*
	 * public List<KdDoc> getByTreeIdNameKey(String tenantId,String
	 * parentId,String name,String key){ return
	 * kdDocDao.getByTreeIdNameKey(tenantId, parentId, name, key); }
	 */

	/**
	 * 根据sysTree的path查询分类下的doc
	 * 
	 * @param path
	 * @param page
	 * @return
	 */
	public List<KdDoc> getByPath(String path, String docType, QueryFilter queryFilter) {
		return kdDocDao.getByPath(path, docType, queryFilter);
	}

	/**
	 * 搜索页面:搜索所有的某一权限的的Doc
	 * 
	 * @param userId
	 * @param right
	 * @param page
	 * @return
	 */
	public List<KdDoc> getAllByRgiht(String userId, String docType, String right, String treeId, QueryFilter queryFilter) {
		// 获得user的Doc
		List<KdDoc> rightDoc = kdDocDao.getDocByRight(docType, userId, KdDocRight.IDENTITYTYPE_USER, right, treeId, queryFilter);
		// 获得user所属的group的Doc
		List<OsGroup> groups = osGroupManager.getBelongGroups(userId);
		if (groups.size() > 0) {
			for (OsGroup group : groups) {// 遍历每个组
				List<KdDoc> list = kdDocDao.getDocByRight(docType, group.getGroupId(), KdDocRight.IDENTITYTYPE_GROUP, right, treeId, queryFilter);// 获得每个组的阅读权限
				for (int i = 0; i < list.size(); i++) {
					if (!rightDoc.contains(list.get(i))) {
						rightDoc.add(list.get(i));
					}
				}
			}
		}

		List<KdDoc> allDoc = kdDocDao.getAllByRgiht(right, docType, treeId, queryFilter);
		rightDoc.addAll(allDoc);
		return rightDoc;

	}

	/**
	 * 个人中心的搜索，
	 * 
	 * @param userId
	 *            用户Id
	 * @param right
	 *            权限类型（read...)
	 * @param queryFilter
	 *            个人中心页面的条件选择
	 * @return
	 */
	public List<KdDoc> getAllPersonalDoc(String userId, String docType, String right, QueryFilter queryFilter) {
		// 获得user的Doc
		List<KdDoc> rightDoc = kdDocDao.getPersonalDoc(userId, docType, KdDocRight.IDENTITYTYPE_USER, right, queryFilter);
		// 获得user所属的group的Doc
		List<OsGroup> groups = osGroupManager.getBelongGroups(userId);
		if (groups.size() > 0) {
			for (OsGroup group : groups) {// 遍历每个组
				List<KdDoc> list = kdDocDao.getPersonalDoc(group.getGroupId(), docType, KdDocRight.IDENTITYTYPE_GROUP, right, queryFilter);// 获得每个组的阅读权限
				for (int i = 0; i < list.size(); i++) {
					if (!rightDoc.contains(list.get(i))) {
						rightDoc.add(list.get(i));
					}
				}
			}
		}

		List<KdDoc> allDoc = kdDocDao.getPersonalAllDoc(right, queryFilter);
		rightDoc.addAll(allDoc);
		return rightDoc;

	}

	/**
	 * 获取最新的知识Doc
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<KdDoc> getByNewest(String userId, String docType, QueryFilter queryFilter) {
		List<KdDoc> rightDoc = kdDocDao.getByNewest(userId, docType);
		// 获得user所属的group的Doc
		List<OsGroup> groups = osGroupManager.getBelongGroups(userId);
		if (groups.size() > 0) {
			for (OsGroup group : groups) {// 遍历每个组
				List<KdDoc> list = kdDocDao.getDocByRight(docType, group.getGroupId(), KdDocRight.IDENTITYTYPE_GROUP, KdDocRight.RIGHT_READ, "", queryFilter);// 获得每个组的阅读权限
				for (int i = 0; i < list.size(); i++) {
					if (!rightDoc.contains(list.get(i))) {
						rightDoc.add(list.get(i));
					}
				}
			}
		}

		List<KdDoc> allDoc = kdDocDao.getAllByRgiht(KdDocRight.RIGHT_READ, docType, "", queryFilter);
		rightDoc.addAll(allDoc);

		SortNewKdDoc sort = new SortNewKdDoc();
		Collections.sort(rightDoc, sort);// 按照时间排序
		return rightDoc;
	}

	/**
	 * 获取最热的知识Doc
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<KdDoc> getByHotest(String userId, String docType, QueryFilter queryFilter) {
		List<KdDoc> rightDoc = kdDocDao.getByNewest(userId, docType);
		// 获得user所属的group的Doc
		List<OsGroup> groups = osGroupManager.getBelongGroups(userId);
		if (groups.size() > 0) {
			for (OsGroup group : groups) {// 遍历每个组
				List<KdDoc> list = kdDocDao.getDocByRight(docType, group.getGroupId(), KdDocRight.IDENTITYTYPE_GROUP, KdDocRight.RIGHT_READ, "", queryFilter);// 获得每个组的阅读权限
				for (int i = 0; i < list.size(); i++) {
					if (!rightDoc.contains(list.get(i))) {
						rightDoc.add(list.get(i));
					}
				}
			}
		}

		List<KdDoc> allDoc = kdDocDao.getAllByRgiht(KdDocRight.RIGHT_READ, docType, "", queryFilter);
		rightDoc.addAll(allDoc);

		SortHotKdDoc sort = new SortHotKdDoc();
		Collections.sort(rightDoc, sort);// 按照点击量排序
		return rightDoc;
	}

	/**
	 * 获取推荐的知识Doc
	 * 
	 * @return
	 */
	public List<KdDoc> getByRem() {
		return kdDocDao.getByRem();
	}

	/**
	 * 首页获取精华的Doc
	 * 
	 * @return
	 */
	public List<KdDoc> getByEssence(String userId) {
		List<KdDoc> allDoc = kdDocDao.getByEssence();
		List<KdDoc> docs = new ArrayList<KdDoc>();
		for (KdDoc doc : allDoc) {
			if (userId.equals(doc.getCreateBy())) {
				docs.add(doc);
				continue;
			} else if (kdDocRightManager.getIsAll(doc.getDocId())) {
				docs.add(doc);
				continue;
			} else if (kdDocRightManager.getByDocIdIdentityIdRight(doc.getDocId(), KdDocRight.IDENTITYTYPE_USER, userId, KdDocRight.RIGHT_READ)) {
				docs.add(doc);
				continue;
			} else {
				List<OsGroup> groups = osGroupManager.getBelongDeps(userId);
				for (OsGroup group : groups) {
					String groupId = group.getGroupId();
					if (kdDocRightManager.getByDocIdIdentityIdRight(doc.getDocId(), KdDocRight.IDENTITYTYPE_GROUP, groupId, KdDocRight.RIGHT_READ)) {
						docs.add(doc);
						break;
					}
				}
			}
		}
		return docs;
	}

	/**
	 * 获取所评论的所有Doc
	 * 
	 * @param userId
	 * @return
	 */
	public List<KdDoc> getDocByCmmt(String userId, String docType, QueryFilter queryFilter) {
		return kdDocDao.getDocByCmmt(userId, docType, queryFilter);
	}

	/**
	 * 获取所有我的文档
	 * 
	 * @param userId
	 * @return
	 */
	public List<KdDoc> getMyDoc(String userId, String docType, QueryFilter queryFilter) {
		return kdDocDao.getMyDoc(userId, docType, queryFilter);
	}

	/**
	 * 个人中心获得所有收藏的文档
	 * 
	 * @param userId
	 * @param queryFilter
	 * @return
	 */
	public List<KdDoc> getMyFav(String userId, String docType, QueryFilter queryFilter) {
		return kdDocDao.getMyFav(userId, docType, queryFilter);
	}

	/**
	 * 判断文档是否还在
	 * 
	 * @param docId
	 * @return
	 */
	public boolean getIsAlive(String docId, String docType) {
		return kdDocDao.getIsAlive(docId, docType);
	}

	/**
	 * 获取所有知识地图
	 * 
	 * @param queryFilter
	 * @return
	 */
	public List<KdDoc> getKdMapList(QueryFilter queryFilter) {
		return kdDocDao.getKdMapList(queryFilter);
	}

	/**
	 * 根据路径获取知识地图
	 * 
	 * @param path
	 * @param page
	 * @return
	 */
	public List<KdDoc> getMapByPath(String path, Page page) {
		return kdDocDao.getMapByPath(path, page);
	}

	/**
	 * 根据类型获取文档
	 * 
	 * @param docType
	 * @return
	 */
	public List<KdDoc> getByDocType(String docType, Page page) {
		return kdDocDao.getByDocType(docType, page);
	}

	/**
	 * 首页的知识文档栏目显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public List<KdCoverImage> getPortalKdDoc(InsPortalParams params){
		// 循环显示的带图片的推荐Doc
		List<KdCoverImage> remDoc = new ArrayList<KdCoverImage>();
		List<KdDoc> remdocs = getByRem();
		for (KdDoc doc : remdocs) {
			KdCoverImage image = new KdCoverImage();
			image.setDocId(doc.getDocId());
			image.setTitle(doc.getSubject());
			image.setPicUrl("/sys/core/file/imageView.do?fileId=" + doc.getCoverImgId());
			image.setJumpUrl("/kms/core/kdDoc/show.do?docId=" + doc.getDocId());
			image.setSummary(doc.getSummary());
			image.setCreateTime(doc.getCreateTime());
			remDoc.add(image);
		}
		return remDoc;
	}
	

}