package com.redxun.sys.org.dao;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.sys.org.entity.OsUser;
/**
 * <pre> 
 * 描述：OsUser数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class OsUserDao extends BaseJpaDao<OsUser> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return OsUser.class;
    }
    public boolean isLDAPExist(String userNo){
    	String ql="select count(*) from OsUser u where u.userNo = ?";
    	long a = (Long)this.getUnique(ql, new Object[]{userNo});
    	if(a==1L){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public OsUser getByUserNo(String userNo){
    	String ql="from OsUser u where u.userNo = ?";
    	return (OsUser) this.getUnique(ql, new Object[]{userNo});
    }
    
    public List<OsUser> getUsersByIds(List<String> list){
    	String hql;
    	if(list.size()>0){
    		String ids=StringUtils.join(list.toArray(),",");
        	hql="from OsUser u where u.userId in ("+ids+")";
    	}else{
    		hql="from OsUser u where 1!=1";
    	}
    	return  this.getByJpql(hql, null);
    }
    
}
