package com.redxun.kms.core.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.Page;
import com.redxun.core.query.QueryFilter;
import com.redxun.kms.core.dao.KdDocReadDao;
import com.redxun.kms.core.entity.KdDocRead;
/**
 * <pre> 
 * 描述：KdDocRead业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class KdDocReadManager extends BaseManager<KdDocRead>{
	@Resource
	private KdDocReadDao kdDocReadDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return kdDocReadDao;
	}
	

    /**
     * 查询所有该文档的阅读记录
     * @param docId
     * @return
     */
    public List<KdDocRead> getReader(String docId,QueryFilter queryFilter){
    	return kdDocReadDao.getReader(docId,queryFilter);
    }
}