package com.redxun.oa.product.manager;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.product.dao.OaProductDefDao;
import com.redxun.oa.product.entity.OaProductDef;
/**
 * <pre> 
 * 描述：OaProductDef业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class OaProductDefManager extends BaseManager<OaProductDef>{
	@Resource
	private OaProductDefDao oaProductDefDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return oaProductDefDao;
	}
}