package com.redxun.kms.core.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.kms.core.dao.KdDocRightDao;
import com.redxun.kms.core.entity.KdDocRight;

/**
 * <pre>
 * 描述：KdDocRight业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class KdDocRightManager extends BaseManager<KdDocRight> {
	@Resource
	private KdDocRightDao kdDocRightDao;

	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return kdDocRightDao;
	}

	/**
	 * 根据文档名和用户名查询是否已有这个权限关联
	 * 
	 * @param docId
	 * @param identityId
	 * @param right
	 * @return true 已存在 false 未存在
	 */
	public boolean getByDocIdIdentityIdRight(String docId, String identityType, String identityId, String right) {
		return kdDocRightDao.getByDocIdIdentityIdRgiht(docId, identityType, identityId, right);
	}

	/**
     * 根据right类别和identityType组别查询这个文档的所有权限
     * @param docId
     * @param identityType user或者group
     * @param right read、edit、download或print
     * @return
     */
	
	public List<KdDocRight> getAllByDocIdRight(String docId, String identityType, String right) {
		return kdDocRightDao.getAllByDocIdRight(docId, identityType, right);
	}
	
	/**
     * 删除所有docId为此的权限
     * @param docId
     */
    public void delByDocId(String docId){
    	kdDocRightDao.delByDocId(docId);
    }
    
    /**
     * 判断是否全部人可读
     * @param docId
     * @return true为有所有人可读
     */
    public boolean getIsAll(String docId){
    	return kdDocRightDao.getIsAll(docId);
    }
}