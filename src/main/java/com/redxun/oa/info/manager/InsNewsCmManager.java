package com.redxun.oa.info.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.info.dao.InsNewsCmDao;
import com.redxun.oa.info.entity.InsNewsCm;
/**
 * <pre> 
 * 描述：InsNewsCm业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class InsNewsCmManager extends BaseManager<InsNewsCm>{
	@Resource
	private InsNewsCmDao insNewsCmDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return insNewsCmDao;
	}
    /**
     * 获取新闻newId的所有评论
     * @param newId
     * @return
     */
	public List<InsNewsCm> getByNewId(String newId){
		return insNewsCmDao.getByNewId(newId);
	}
    /**
     * 获取所有回复某条评论的子评论
     * @param repId 回复的那条评论的Id
     * @return
     */
	public List<InsNewsCm> getByReplyId(String repId){
		return insNewsCmDao.getByReplyId(repId);
	}
}