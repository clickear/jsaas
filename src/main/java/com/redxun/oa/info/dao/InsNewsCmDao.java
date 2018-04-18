package com.redxun.oa.info.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.info.entity.InsNewsCm;
import com.redxun.oa.info.entity.InsPortCol;
import com.redxun.saweb.context.ContextUtil;
/**
 * <pre> 
 * 描述：InsNewsCm数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class InsNewsCmDao extends BaseJpaDao<InsNewsCm> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return InsNewsCm.class;
    }
    /**
     * 获取新闻newId的所有评论
     * @param newId
     * @return
     */
    public List<InsNewsCm> getByNewId(String newId){
    	String tenantId = ContextUtil.getCurrentTenantId();
		String ql="from InsNewsCm insnc where insnc.insNews.newId=? and insnc.tenantId = ? order by createTime";
    	return this.getByJpql(ql, new Object[]{newId,tenantId});
	}
    /**
     * 获取所有回复某条评论的子评论
     * @param repId 回复的那条评论的Id
     * @return
     */
    public List<InsNewsCm> getByReplyId(String repId){
    	String tenantId = ContextUtil.getCurrentTenantId();
    	String ql="from InsNewsCm insnc where insnc.repId=? and insnc.tenantId = ? order by createTime";
    	return this.getByJpql(ql, new Object[]{repId,tenantId});
    }
}
