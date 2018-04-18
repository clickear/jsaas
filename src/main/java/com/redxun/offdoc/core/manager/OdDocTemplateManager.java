package com.redxun.offdoc.core.manager;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.offdoc.core.dao.OdDocTemplateDao;
import com.redxun.offdoc.core.entity.OdDocTemplate;
/**
 * <pre> 
 * 描述：OdDocTemplate业务服务类
 * 作者：陈茂昌
 * 日期:2016-3-8-上午16:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class OdDocTemplateManager extends BaseManager<OdDocTemplate>{
	@Resource
	private OdDocTemplateDao odDocTemplateDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return odDocTemplateDao;
	}
}