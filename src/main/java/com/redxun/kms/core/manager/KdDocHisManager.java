package com.redxun.kms.core.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.kms.core.dao.KdDocHisDao;
import com.redxun.kms.core.entity.KdDocHis;
/**
 * <pre> 
 * 描述：KdDocHis业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class KdDocHisManager extends BaseManager<KdDocHis>{
	@Resource
	private KdDocHisDao kdDocHisDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return kdDocHisDao;
	}
	
	/**
     * 获得当前文档的历史版本
     * @param docId
     * @return
     */
    public List<KdDocHis> getDocVersion(String docId,QueryFilter queryFilter){
    	return kdDocHisDao.getDocVersion(docId,queryFilter);
    }
}