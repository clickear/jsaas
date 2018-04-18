package com.redxun.oa.pro.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.pro.entity.ProWorkMat;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.entity.SysModule;
/**
 * <pre> 
 * 描述：ProWorkMat数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class ProWorkMatDao extends BaseJpaDao<ProWorkMat> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return ProWorkMat.class;
    }
    
    public List<ProWorkMat> getByProjectId(String projectId){
    	String ql="from ProWorkMat p where p.typePk=? and p.type<>'MYACTION'";
		return this.getByJpql(ql, projectId);}

	public List<ProWorkMat> getActByProjectId(String projectId) {
		// TODO Auto-generated method stub
		String ql="from ProWorkMat p where p.typePk=? and p.type='MYACTION'";
		return this.getByJpql(ql, projectId);}
	
    
}
