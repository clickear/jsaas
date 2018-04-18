package com.redxun.oa.pro.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.pro.dao.ProWorkMatDao;
import com.redxun.oa.pro.entity.ProWorkMat;
import com.redxun.sys.core.entity.SysFile;
/**
 * <pre> 
 * 描述：ProWorkMat业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class ProWorkMatManager extends BaseManager<ProWorkMat>{
	@Resource
	private ProWorkMatDao proWorkMatDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return proWorkMatDao;
	}
public List<ProWorkMat> getByProjectId(String projectId){
	
	return proWorkMatDao.getByProjectId(projectId);}


public List<ProWorkMat> getActByProjectId(String projectId) {
	// TODO Auto-generated method stub
	return proWorkMatDao.getActByProjectId(projectId);
}

}