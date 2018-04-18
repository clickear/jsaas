package com.redxun.oa.doc.manager;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.doc.dao.DocRightDao;
import com.redxun.oa.doc.entity.Doc;
import com.redxun.oa.doc.entity.DocRight;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.manager.OsGroupManager;

/**
 * <pre>
 * 描述：DocRight业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class DocRightManager extends BaseManager<DocRight> {
	@Resource
	private DocRightDao docRightDao;
	@Resource
	private OsGroupManager osGroupManager;

	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return docRightDao;
	}

	/**
	 * 根据文档名和用户名查询是否已有这个权限关联
	 * 
	 * @param docId
	 * @param identityType
	 * @param identityId
	 * @param right
	 * @return true 已存在 false 未存在
	 */
	public boolean getByDocIdIdentityIdRight(String docId, String identityType, String identityId, int right) {
		boolean b = false;
		// 获得user所属的group的Doc
		if ("USER".equals(identityType)) {
			if (docRightDao.getByDocIdIdentityIdRight(docId, DocRight.IDENTITYTYPE_USER, identityId, right)) {
				b = true;
				return b;
			}
			List<OsGroup> groups = osGroupManager.getBelongGroups(identityId);
			if (groups.size() > 0) {
				for (OsGroup group : groups) {// 遍历每个组
					if (docRightDao.getByDocIdIdentityIdRight(docId, DocRight.IDENTITYTYPE_GROUP, group.getGroupId(), right)) {
						b = true;
						return b;
					}
				}
			}
		} else {
			if (docRightDao.getByDocIdIdentityIdRight(docId, DocRight.IDENTITYTYPE_GROUP, identityId, right)) {
				b = true;
				return b;
			}
		}

		return b;
	}

	/**
	 * 删除所有docId为此的权限
	 * 
	 * @param docId
	 */
	public void delByDocId(String docId) {
		docRightDao.delByDocId(docId);
	}
	
	/**
	 * 根据right类别和user（group）组别查询这个文档的所有权限
	 * 
	 * @param docId
	 * @param identityType
	 * @param right
	 * @return
	 */
	public List<DocRight> getAllByDocIdRight(String docId, String identityType, int right) {
		return docRightDao.getAllByDocIdRight(docId, identityType, right);
	}
}