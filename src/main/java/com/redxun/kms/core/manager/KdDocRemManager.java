package com.redxun.kms.core.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.kms.core.dao.KdDocRemDao;
import com.redxun.kms.core.entity.KdDocRem;

/**
 * <pre>
 * 描述：KdDocRem业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class KdDocRemManager extends BaseManager<KdDocRem> {
	@Resource
	private KdDocRemDao kdDocRemDao;

	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return kdDocRemDao;
	}

	/**
	 * 判断是否在此分类已经推荐过此doc
	 * @param docId
	 * @param treeId
	 * @return true 有
	 * 			false 没有
	 */
	public boolean getByIsRem(String docId, String treeId) {
		return kdDocRemDao.getByIsRem(docId, treeId);
	}
	
	 /**
     * 获得该文档的所有推荐
     * @param docId
     * @return
     */
    public List<KdDocRem> getByDocId(String docId){
    	return kdDocRemDao.getByDocId(docId);
    }
    
    /**
     * 删除这个docId的文档的首页推荐
     * @param docId
     */
    public void delByDocId(String docId){
    	kdDocRemDao.delByDocId(docId);  	
    }
}