package com.redxun.oa.res.dao;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.res.entity.OStatus;
import com.redxun.oa.res.entity.OaCarApp;
/**
 * <pre> 
 * 描述：OaCarApp数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class OaCarAppDao extends BaseJpaDao<OaCarApp> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return OaCarApp.class;
    }
    /**
     * 根据时间获取到超过结束时间的申请信息
     * @param 
     * @return 
     */
    public List<OaCarApp> getOaCarApp(){
    	String ql="from OaCarApp oa where oa.endTime <= ? and oa.oaCar.status = ?";
    	Date newDate=new Date();
    	return this.getByJpql(ql,newDate,OStatus.INUSED.name());
    }
}
