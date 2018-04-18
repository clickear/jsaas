package com.redxun.sys.core.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.Page;
import com.redxun.core.query.SortParam;
import com.redxun.sys.core.dao.SysSearchDao;
import com.redxun.sys.core.entity.SysSearch;
/**
 * <pre> 
 * 描述：SysSearch业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class SysSearchManager extends BaseManager<SysSearch>{
	@Resource
	private SysSearchDao sysSearchDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
            return sysSearchDao;
	}
	
	public List<SysSearch> getByEntityNameUserId(String entityName,String userId){
            return sysSearchDao.getByEntityNameUserId(entityName, userId);
	}
        
        public List<SysSearch> getByEntityNameUserIdName(String entityName,String userId,String name,Page page,SortParam sortParam){
            return sysSearchDao.getByEntityNameUserIdName(entityName,userId,name,page,sortParam);
        }
}