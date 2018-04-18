package com.redxun.oa.pro.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.pro.dao.ProAttendDao;
import com.redxun.oa.pro.entity.ProAttend;
/**
 * <pre> 
 * 描述：ProAttend业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class ProAttendManager extends BaseManager<ProAttend>{
	@Resource
	private ProAttendDao proAttendDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return proAttendDao;
	}
	
	
	//获取
 public List<ProAttend> getByProjectId(String projectId){
	 
	return proAttendDao.getByProjectId(projectId);
	 
 }
 
 public List<ProAttend> getByGroupId(String GroupId){
	return proAttendDao.getByGroupId(GroupId);
	 
 } 
}