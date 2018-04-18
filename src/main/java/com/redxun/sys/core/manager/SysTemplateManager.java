package com.redxun.sys.core.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.sys.core.dao.SysTemplateDao;
import com.redxun.sys.core.entity.SysTemplate;
/**
 * <pre> 
 * 描述：SysTemplate业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class SysTemplateManager extends BaseManager<SysTemplate>{
	@Resource
	private SysTemplateDao sysTemplateDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysTemplateDao;
	}
	
	 /**
     * 获得分类下的模板
     * @param treeId
     * @return
     */
    public List<SysTemplate> getByTreeId(String treeId){
    	return sysTemplateDao.getByTreeId(treeId);
    }
    
    /**
     * 按分类Key获得模板列表
     * @param catKey
     * @return
     */
    public List<SysTemplate> getByCatKey(String catKey){
    	return sysTemplateDao.getByCatKey(catKey);
    }
    /**
     * 按分类Key及租户ID获得模板更表
     * @param catKey
     * @param tenantId
     * @return
     */
    public List<SysTemplate> getByCatKey(String catKey,String tenantId){
    	return sysTemplateDao.getByCatKey(catKey, tenantId);
    }
}