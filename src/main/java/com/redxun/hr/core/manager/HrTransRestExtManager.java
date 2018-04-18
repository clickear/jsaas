package com.redxun.hr.core.manager;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.hr.core.dao.HrTransRestExtDao;
import com.redxun.hr.core.entity.HrTransRestExt;
/**
 * <pre> 
 * 描述：HrTransRestExt业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class HrTransRestExtManager extends BaseManager<HrTransRestExt>{
	@Resource
	private HrTransRestExtDao hrTransRestExtDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return hrTransRestExtDao;
	}
}