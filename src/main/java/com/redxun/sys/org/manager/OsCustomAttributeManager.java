
package com.redxun.sys.org.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.dao.IDao;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.org.dao.OsCustomAttributeDao;
import com.redxun.sys.org.dao.OsCustomAttributeQueryDao;
import com.redxun.sys.org.entity.OsCustomAttribute;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;

/**
 * 
 * <pre> 
 * 描述：自定义属性 处理接口
 * 作者:mansan
 * 日期:2017-12-14 14:02:29
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class OsCustomAttributeManager extends ExtBaseManager<OsCustomAttribute>{
	@Resource
	private OsCustomAttributeDao osCustomAttributeDao;
	@Resource
	private OsCustomAttributeQueryDao osCustomAttributeQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return osCustomAttributeQueryDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return osCustomAttributeQueryDao;
	}
	
	public OsCustomAttribute getOsCustomAttribute(String uId){
		OsCustomAttribute osCustomAttribute = get(uId);
		return osCustomAttribute;
	}
	/**
	 * 将自定义属性添加进modelAndView返回到前端构建表单
	 * @param modelAndView
	 */
	public void addCustomAttribute(ModelAndView modelAndView){
		String tenantId=ContextUtil.getCurrentTenantId();
		List<OsCustomAttribute> osCustomAttributes=getAllByTenantId(tenantId);
		modelAndView.addObject("osCustomAttributes", osCustomAttributes);
	}
	/**
	 * 将用户类型的自定义属性全部取出来
	 * @param tenantId
	 * @return
	 */
	public List<OsCustomAttribute> getUserTypeAttributeByTenantId(String tenantId){
		return osCustomAttributeQueryDao.getUserTypeAttributeByTenantId(tenantId);
	}
}
