package com.redxun.oa.res.manager;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.res.dao.OaCarDao;
import com.redxun.oa.res.entity.OaCar;
/**
 * <pre> 
 * 描述：OaCar业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class OaCarManager extends BaseManager<OaCar>{
	@Resource
	private OaCarDao oaCarDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return oaCarDao;
	}
	public List<OaCar> getCarByDicId(String sysDicId){
		return oaCarDao.getCarByDicId(sysDicId);
	}

	public List<OaCar> getByTimeOrStuts(Date startTime,Date endTime){
		return oaCarDao.getByTimeOrStuts(startTime,endTime);
	}
}