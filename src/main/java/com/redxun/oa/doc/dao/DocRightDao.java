package com.redxun.oa.doc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.kms.core.entity.KdDocRight;
import com.redxun.oa.doc.entity.DocRight;
import com.redxun.saweb.context.ContextUtil;

/**
 * <pre>
 * 描述：DocRight数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class DocRightDao extends BaseJpaDao<DocRight> {

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getEntityClass() {
		return DocRight.class;
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
		String tenantId = ContextUtil.getCurrentTenantId();
		String ql = "select count(*) from DocRight r where r.doc.docId = ? and r.identityType=? and r.identityId = ? and r.rights=? and r.tenantId = ?";
		Long a = (Long) this.getUnique(ql, new Object[] { docId, identityType, identityId, right, tenantId });
		if (a > 0L) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除所有docId为此的权限
	 * 
	 * @param docId
	 */
	public void delByDocId(String docId) {
		String tenantId = ContextUtil.getCurrentTenantId();
		String ql = "delete DocRight r where r.doc.docId = ? and r.tenantId = ?";
		this.delete(ql, new Object[] { docId, tenantId });
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
		String tenantId = ContextUtil.getCurrentTenantId();
		String ql = "from DocRight r where r.doc.docId = ? and r.identityType=? and r.rights=? and r.tenantId = ?";
		return this.getByJpql(ql, new Object[] { docId, identityType, right, tenantId });
	}

}
