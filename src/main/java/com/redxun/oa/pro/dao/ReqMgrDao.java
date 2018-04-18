package com.redxun.oa.pro.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.doc.entity.Doc;
import com.redxun.oa.pro.entity.ReqMgr;
/**
 * <pre> 
 * 描述：ReqMgr数据访问类
 * 构建组：miweb
 * 作者：cmc
 * 日期:2014-12-15-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class ReqMgrDao extends BaseJpaDao<ReqMgr> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return ReqMgr.class;
    }
   
    
    /**
     * 通过projectId查找对应的需求
     * @param ProjectId
     * @return
     */
    public List<ReqMgr> getByProjectId(String ProjectId){
    	String ql="select r from ReqMgr r left join r.project p where p.projectId=? order by r.path";
    	return this.getByJpql(ql, ProjectId);
    }
}
