package com.redxun.kms.core.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.kms.core.dao.KdDocCmmtDao;
import com.redxun.kms.core.entity.KdDocCmmt;

/**
 * <pre>
 * 描述：KdDocCmmt业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2016-4-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class KdDocCmmtManager extends BaseManager<KdDocCmmt> {
	@Resource
	private KdDocCmmtDao kdDocCmmtDao;

	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return kdDocCmmtDao;
	}

	/**
	 * 判断是否已经点评过
	 * 
	 * @param docId
	 * @param identityType
	 * @param identityId
	 * @param right
	 * @return true 已经点评过 false 尚未点评
	 */
	public boolean getIsByDocId(String docId, String userId) {
		return kdDocCmmtDao.getIsByDocId(docId, userId);
	}

	/**
	 * 获取该DocId文档的所有的点评
	 * @param docId
	 * @return
	 */
	
	public List<KdDocCmmt> getByDocId(String docId) {
		return kdDocCmmtDao.getByDocId(docId);
	}
}