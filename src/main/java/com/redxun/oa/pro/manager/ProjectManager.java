package com.redxun.oa.pro.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.SqlQueryFilter;
import com.redxun.oa.pro.dao.ProjectDao;
import com.redxun.oa.pro.dao.ProjectQueryDao;
import com.redxun.oa.pro.entity.Project;
/**
 * <pre> 
 * 描述：Project业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class ProjectManager extends BaseManager<Project>{
	@Resource
	private ProjectDao projectDao;
	@Resource
	ProjectQueryDao projectQueryDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return projectDao;
	}
	
	public List<Project> getByTreeId(String treeId){
		return projectDao.getByTreeId(treeId);
	}
	public List<Project> getByReporId(String reporId){
		return projectDao.getByReporId(reporId);
	}
	public List<Project> getByUserId(String UserId){
		return projectDao.getByUserId(UserId);
	}
	
	public  List<Project> getMyAttendProject(String userId,String tenantId,SqlQueryFilter sqlQueryFilter){
		return projectQueryDao.getMyAttendProject(userId, tenantId, sqlQueryFilter);
	}
}