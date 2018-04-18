package com.redxun.oa.pro.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.pro.entity.Project;
import com.redxun.sys.org.entity.OsRelInst;
/**
 * <pre> 
 * 描述：Project数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class ProjectDao extends BaseJpaDao<Project> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return Project.class;
    }
    
  public List<Project> getByTreeId(String treeId){
	  String ql="select p from Project p left join p.sysTree t where t.treeId=? ";
		return this.getByJpql(ql, treeId);
  
  }
  
  public List<Project> getByReporId(String reporId){
	  String ql="from Project p where p.reporId=? ";
		return this.getByJpql(ql,reporId);
  
  }
  
  public List<Project> getByAttId(String attId){
	  String ql="from Project p where p.attId=? ";
		return this.getByJpql(ql,attId);
  }
  
  /**
   * 通过userId查找项目的list
   * @param UserId
   * @return
   */
  public List<Project> getByUserId(String UserId){
	  String ql="from Project p where p.createBy=? ";
		return this.getByJpql(ql,UserId);
  }
  


	 
 
}
