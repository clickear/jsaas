package com.redxun.oa.res.dao;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.res.entity.OStatus;
import com.redxun.oa.res.entity.OaCar;
/**
 * <pre> 
 * 描述：OaCar数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class OaCarDao extends BaseJpaDao<OaCar> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return OaCar.class;
    }
    /**
     * 根据类型获取到相对应的车
     * @param sysDicId 车辆类型sysDicId
     * @return 
     */
	public List<OaCar> getCarByDicId(String sysDicId){
		String ql="from OaCar oa where oa.sysDicId= ? ";
    	return this.getByJpql(ql, sysDicId);
	}
	/**
	 * 取到空闲时间段，空闲状态的车辆信息
	 * 
	 * */
    public List<OaCar> getByTimeOrStuts(Date startTime,Date endTime){
    	
    	
    	
    	String ql="from OaCar oa where oa.carId not in "
    			+ "(select o1.oaCar.carId from OaCarApp o1 where"
    			+ " (o1.startTime<= ? and ? <= o1.endTime) or (o1.startTime <= ? and ? <=o1.endTime)"
    			+ " or (o1.startTime >= ? and ? >= o1.endTime)) and (oa.status = ? or oa.status= ?)";
    	return this.getByJpql(ql, new Object[]{startTime,startTime,endTime,endTime,startTime,endTime,OStatus.INFREE.name(),OStatus.INUSED.name()});
    }
}
