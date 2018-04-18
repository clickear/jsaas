package com.redxun.oa.pro.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.pro.entity.ProVers;
/**
 * <pre> 
 * 描述：ProVers数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class ProVersDao extends BaseJpaDao<ProVers> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return ProVers.class;
    }
    
    
    /**
     * 通过projectId来查找未发布的版本
     * @param projectId
     * @return
     */
    public List<ProVers>  getByProjectId(String projectId){
    	String ql="select p from ProVers p left join p.project pj where pj.projectId=? and p.status<>'DEPLOYED'";
		return this.getByJpql(ql, projectId);
    	
    }
    
}
