package com.redxun.sys.core.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.sys.core.entity.SysInst;
/**
 * <pre> 
 * 描述：SysInst数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class SysInstDao extends BaseJpaDao<SysInst> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return SysInst.class;
    }
    /**
     * 根据域名查找机构
     * @param domain
     * @return
     */
    public SysInst getByDomain(String domain){
    	String ql="from SysInst st where st.domain=?";
    	return (SysInst)this.getUnique(ql, new Object[]{domain});
    }
    
    /**
     * 按状态获得机构
     * @param status
     * @return
     */
    public List<SysInst> getByStatus(String status){
    	String ql="from SysInst st where st.status=?";
    	return this.getByJpql(ql, new Object[]{status});
    }
    
    public SysInst getByNameCn(String nameCn){
    	String ql="from SysInst s where s.nameCn=?";
    	return (SysInst)this.getUnique(ql, new Object[]{nameCn});
	}
	
	public SysInst getByShortNameCn(String shortNameCn){
		String ql="from SysInst s where s.nameCnS=?";
    	return (SysInst)this.getUnique(ql, new Object[]{shortNameCn});
	}
    
}
