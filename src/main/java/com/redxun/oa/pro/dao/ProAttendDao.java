package com.redxun.oa.pro.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.doc.entity.Doc;
import com.redxun.oa.pro.entity.ProAttend;
import com.redxun.oa.pro.entity.ReqMgr;
/**
 * <pre> 
 * 描述：ProAttend数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class ProAttendDao extends BaseJpaDao<ProAttend> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return ProAttend.class;
    }
    
    
    public List<ProAttend> getByProjectId(String ProjectId){
    	String ql="select p from ProAttend p left join p.project pro where pro.projectId=?";
    	return this.getByJpql(ql, ProjectId);
    }
    
    /**
     * 通过组Id查找参与人员
     * @param GroupId
     * @return
     */
    public List<ProAttend> getByGroupId(String GroupId){
    	String ql="select p from ProAttend p where p.groupId=?";
    	return this.getByJpql(ql, GroupId);
    }
   
}
