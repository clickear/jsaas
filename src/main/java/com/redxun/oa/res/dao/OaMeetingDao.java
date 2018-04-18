package com.redxun.oa.res.dao;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.res.entity.OaMeeting;
/**
 * <pre> 
 * 描述：OaMeeting数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class OaMeetingDao extends BaseJpaDao<OaMeeting> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return OaMeeting.class;
    }
	/**
     * 得到大于今天会议室的信息
     * @param roomId 会议室Id
     * @return 
     */
    public List<OaMeeting> getByDateRoomId(String roomId){
    	String ql="from OaMeeting oa where oa.oaMeetRoom.roomId= ? and ( (oa.start<=? and oa.end>=?) or oa.start>=? ) order by oa.end desc ";	
//    	Calendar calendar=Calendar.getInstance();
//    	calendar.set(Calendar.HOUR, 0);
//    	calendar.set(Calendar.MINUTE,0);
//    	calendar.set(Calendar.SECOND,0);
//    	Date startDate=calendar.getTime();
    	Date newDate=new Date();
    	return this.getByJpql(ql, new Object[]{roomId,newDate,newDate,newDate});
    }
    
	/**
     * 根据Id，开始时间，结束时间，获取数据
     * @param roomId 会议室Id
     * @param start 开始时间
     * @param end 结束时间
     * @return 
     */
    
    public List<OaMeeting> getByRoomId(String roomId,Date start,Date end){
    	String ql="from OaMeeting oa where oa.oaMeetRoom.roomId= ? and oa.start >= ? and oa.end <= ? ";
    	return this.getByJpql(ql, new Object[]{roomId,start,end});    	
    }
    
}
