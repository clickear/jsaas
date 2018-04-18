package com.redxun.sys.org.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.sys.org.dao.OsRankTypeDao;
import com.redxun.sys.org.entity.OsRankType;
/**
 * <pre> 
 * 描述：OsRankType业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class OsRankTypeManager extends BaseManager<OsRankType>{
	@Resource
	private OsRankTypeDao osRankTypeDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return osRankTypeDao;
	}
	
	/**
     * 按维度ID获得等级分类列表
     * @param dimId
     * @return 
     * List<OsRankType>
     * @exception 
     * @since  1.0.0
     */
    public List<OsRankType> getByDimId(String dimId){
    	return osRankTypeDao.getByDimId(dimId);
    }
    
    public List<OsRankType> getByDimIdTenantId(String dimId,String tenantId){
    	return osRankTypeDao.getByDimIdTenantId(dimId, tenantId);
    }
    
    public OsRankType getByKey(String key,String tenantId){
    	return osRankTypeDao.getbyKey(key,tenantId);
    }
}