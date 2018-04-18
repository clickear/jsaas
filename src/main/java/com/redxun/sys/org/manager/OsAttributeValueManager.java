
package com.redxun.sys.org.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.dao.IDao;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.org.dao.OsAttributeValueDao;
import com.redxun.sys.org.dao.OsAttributeValueQueryDao;
import com.redxun.sys.org.entity.OsAttributeValue;
import com.redxun.sys.org.entity.OsUser;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;

/**
 * 
 * <pre> 
 * 描述：人员属性值 处理接口
 * 作者:mansan
 * 日期:2017-12-14 14:09:43
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class OsAttributeValueManager extends ExtBaseManager<OsAttributeValue>{
	@Resource
	private OsAttributeValueDao osAttributeValueDao;
	@Resource
	private OsAttributeValueQueryDao osAttributeValueQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return osAttributeValueDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return osAttributeValueQueryDao;
	}
	
	public OsAttributeValue getOsAttributeValue(String uId){
		OsAttributeValue osAttributeValue = get(uId);
		return osAttributeValue;
	}
	/**
	 * 获取用户特性的自定义属性值
	 * @param attributeId
	 * @param userId
	 * @return
	 */
	public OsAttributeValue getSpecialValueByUser(String attributeId,String userId){
		return osAttributeValueQueryDao.getSpecialValueByUser(attributeId,userId);
	}
	
	public List<OsAttributeValue> getUserAttributeValue(String userId){
		return osAttributeValueQueryDao.getUserAttributeValue(userId);
	}
}
