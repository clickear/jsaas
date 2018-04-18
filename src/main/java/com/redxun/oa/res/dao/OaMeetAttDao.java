package com.redxun.oa.res.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.res.entity.OaMeetAtt;
/**
 * <pre> 
 * 描述：OaMeetAtt数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class OaMeetAttDao extends BaseJpaDao<OaMeetAtt> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return OaMeetAtt.class;
    }
    /**
     * 得到会议参与人员中间表中所有会议Id为meetId的，并按序号从小到大排序
     * @param meetId 会议Id
     * @return 
     */
	public List<OaMeetAtt> getByMeetId(String meetId){
		String ql="from OaMeetAtt oa where oa.oaMeeting.meetId= ?";
    	return this.getByJpql(ql, meetId);
	}
	/**
     * 得到会议参与人员中间表中会议Id为meetId和用户Id为userId
     * @param meetId 会议Id
     * @param userId 用户Id
     * @return 
     */
	public OaMeetAtt getMeetIdByuserId(String meetId,String userId){
		String ql="from OaMeetAtt oa where oa.oaMeeting.meetId= ? and oa.userId= ?";
		return (OaMeetAtt) this.getUnique(ql, new Object[]{meetId,userId});
	}
    /**
     * 根据当前登录用户，查询自己参与的全部会议，并按时间升序排
     * @param userId  当前用户Id
     * @return 
     */
	public List<OaMeetAtt> getByUserId(String userId){
		String ql="from OaMeetAtt oa where oa.userId= ? order by oa.createTime desc";
    	return this.getByJpql(ql, userId);
	}
	
	
	/**
	 * 根据会议申请ID获取该会议的参与人的会议总结信息
	 * 
	 * */
	public List<OaMeetAtt> getByMeetIdSummary(String meetId){
		String ql="from OaMeetAtt oa where oa.oaMeeting.meetId= ? order by oa.createTime desc";
    	return this.getByJpql(ql, meetId);
	}
}
