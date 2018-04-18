package com.redxun.oa.res.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.res.dao.OaMeetAttDao;
import com.redxun.oa.res.entity.OaMeetAtt;
/**
 * <pre> 
 * 描述：OaMeetAtt业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class OaMeetAttManager extends BaseManager<OaMeetAtt>{
	@Resource
	private OaMeetAttDao oaMeetAttDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return oaMeetAttDao;
	}
	 /**
     * 得到会议参与人员中间表中所有会议Id为meetId的，并按序号从小到大排序
     * @param meetId 会议Id
     * @return 
     */
	public List<OaMeetAtt> getByMeetId(String meetId){
		return oaMeetAttDao.getByMeetId(meetId);
	}
	/**
     * 得到会议参与人员中间表中所有会议Id为meetId的，并按序号从小到大排序
     * @param meetId 会议Id
     * @return 
     */
	public OaMeetAtt getMeetIdByuserId(String meetId,String userId){
		return oaMeetAttDao.getMeetIdByuserId(meetId,userId);
	}
    /**
     * 根据当前登录用户，查询自己参与的全部会议，并按时间升序排
     * @param userId  当前用户Id
     * @return 
     */
	public List<OaMeetAtt> getByUserId(String userId){
    	return oaMeetAttDao.getByUserId(userId);
	}
	
	/**
	 * 根据会议ID删除子表里面相关联的信息
	 * 
	 * */
	public List<OaMeetAtt> getByMeetIdSummary(String meetId){
		return oaMeetAttDao.getByMeetIdSummary(meetId);
	}
}