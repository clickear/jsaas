package com.redxun.oa.mail.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.SortParam;
import com.redxun.oa.mail.entity.OutMail;
/**
 * <pre> 
 * 描述：外部邮件数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class OutMailDao extends BaseJpaDao<OutMail> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return OutMail.class;
    }
    
    /**
     *根据文件夹folderId查询该文件夹下邮件
     * @param mailFolderId  文件夹Id
     * @return
     */
    public List<OutMail> getMailByFolderId(String mailFolderId,QueryFilter queryFilter){
    	String jpl="from OutMail m where m.mailFolder.folderId=?";
    	if(queryFilter.getOrderByList().size()>0){	    		
	    	SortParam sortParam=queryFilter.getOrderByList().get(0);
	    	jpl+=" order by m."+sortParam.getProperty()+ " " + sortParam.getDirection();
	    }else{
	    	jpl+=" order by m.createTime desc";
    	}
    	return this.getByJpql(jpl,new Object[]{mailFolderId});
    }
    
    /**
     * 根据外部邮件UID查询外部邮件邮件
     * @param uid  外部邮件UID
     * @return
     */
    public List<OutMail> getMailByUID(String uId,String tenantId){
    	String jpl="from OutMail m where m.uid = ? and m.tenantId=?";
    	return this.getByJpql(jpl, new Object[]{uId,tenantId});
    }
    
    /**
     * 根据外部邮件mailId获取邮件内容
     * @param mailId 外部邮件Id
     * @return
     */
    public String getMailContentByMailId(String mailId){
    	String jpl="from OutMail m where m.mailId=?";
    	return this.getByJpql(jpl,new Object[]{mailId}).get(0).getContent();
    }
    
}
