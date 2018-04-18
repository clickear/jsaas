
package com.redxun.sys.core.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.util.EncryptUtil;
import com.redxun.sys.core.dao.SysPrivatePropertiesDao;
import com.redxun.sys.core.dao.SysPrivatePropertiesQueryDao;
import com.redxun.sys.core.dao.SysPropertiesQueryDao;
import com.redxun.sys.core.entity.SysPrivateProperties;
import com.redxun.sys.core.entity.SysProperties;

/**
 * 
 * <pre> 
 * 描述：私有参数 处理接口
 * 作者:ray
 * 日期:2017-06-21 10:30:22
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class SysPrivatePropertiesManager extends ExtBaseManager<SysPrivateProperties>{
	@Resource
	private SysPrivatePropertiesDao sysPrivatePropertiesDao;
	@Resource
	private SysPrivatePropertiesQueryDao sysPrivatePropertiesQueryDao;
	
	@Resource
	private SysPropertiesQueryDao sysPropertiesQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysPrivatePropertiesQueryDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return sysPrivatePropertiesQueryDao;
	}
	
	public String getValByProId(String proId,String tenantId) throws Exception{
		SysProperties properties=sysPropertiesQueryDao.get(proId);
		SysPrivateProperties privateProperties =sysPrivatePropertiesDao.getByProId(proId,tenantId);
		if(privateProperties==null) return null;
		boolean isEncypt="YES".equals(properties.getEncrypt());
		if(isEncypt){
			return EncryptUtil.decrypt(privateProperties.getPriValue());
		}
		return privateProperties.getPriValue();
		
	}
	
	public SysPrivateProperties getByProId(String proId,String tenantId) {
		
		SysPrivateProperties privateProperties =sysPrivatePropertiesDao.getByProId(proId,tenantId);
		
		return privateProperties;
		
	}
	
	public List<SysPrivateProperties> getAllByProId(String proId){
		return sysPrivatePropertiesDao.getAllByProId(proId);
	}
	
	public void  deleteByProId(String proId){
		sysPrivatePropertiesQueryDao.deleteByProId(proId);
	}
	
}
