package com.redxun.sys.core.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.sys.core.entity.SysAccount;
/**
 * <pre> 
 * 描述：SysAccount数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class SysAccountDao extends BaseJpaDao<SysAccount> {

    @SuppressWarnings("rawtypes")
    @Override
    protected Class getEntityClass() {
        return SysAccount.class;
    }
    /**
     * 取得租用ID下的用户账号
     * @param name
     * @param tenantId
     * @return 
     * SysAccount
     * @exception 
     * @since  1.0.0
     */
    public List<SysAccount> getByNameTenantId(String name,String tenantId){
    	String ql="from SysAccount s where s.name=? and s.tenantId=?";
    	return this.getByJpql(ql, new Object[]{name,tenantId});
    }
    
    /**
     * 
     * @param name
     * @return 
     */
    public SysAccount getByName(String name){
    	String []names=name.split("@");
    	if(names.length==2){
    		String ql="from SysAccount s where s.name=? and domain=?";
    		return (SysAccount) this.getUnique(ql,new Object[]{names[0],names[1]});
    	}else{
    		String ql="from SysAccount s where s.name=?";
    		return (SysAccount) this.getUnique(ql,new Object[]{name});
    	}
    }
    
    /**
     *  根据账号名与域名查找账号
     * @param name
     * @param domain
     * @return
     */
    public SysAccount getByNameDomain(String name,String domain){
    	String ql="from SysAccount sa where sa.name=? and sa.domain=?";
    	return (SysAccount) this.getUnique(ql,new Object[]{name,domain});
    }
    
    /**
     * 通过用户ID取得用户账号信息
     * @param userId
     * @return
    
    public SysAccount getByUserId(String userId){
    	String ql="from SysAccount s where s.userId=?";
    	return (SysAccount) this.getUnique(ql,new Object[]{userId});
    }
     */
    public List<SysAccount> getByUserId(String userId){
    	String ql="from SysAccount s where s.userId=?";
    	return this.getByJpql(ql,new Object[]{userId});
    }
    /**
     * 删除用户关联的账号
     * @param userId
     */
    public void delByUserId(String userId){
    	String ql="delete from SysAccount where userId=?";
    	this.delete(ql, new Object[]{userId});
    }
}
