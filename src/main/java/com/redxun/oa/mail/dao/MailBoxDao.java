package com.redxun.oa.mail.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.mail.entity.MailBox;
/**
 * <pre> 
 * 描述：内部邮件收件箱数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class MailBoxDao extends BaseJpaDao<MailBox> {
	/**
	 * 根据内部邮件Id和文件夹Id获取对应的内部邮件收件箱实体
	 * @param mailId 内部邮件Id
	 * @param folderId  文件夹Id
	 * @return
	 */
	public MailBox getMailBoxByInnerMailIdAndFolderId(String mailId,String folderId){
		String jpql="from MailBox mb where mb.innerMail.mailId=? and mb.mailFolder.folderId=?";
		return (MailBox)this.getUnique(jpql, new Object[]{mailId,folderId});
	}
	
	/**
	 * 获取当前用户文件夹目录未读邮件数量
	 * @param userId 当前用户Id
	 * @param folderId 文件夹Id
	 * @return
	 */
	public Long getUnreadReceiveFolderInnerMail(String userId, String folderId){
		String jpql="select count(*) from MailBox mb where mb.userId=? and mb.mailFolder.folderId=? and mb.isRead=?";
		return (Long)this.getUnique(jpql, new Object[]{userId,folderId,MBoolean.NO.name()});
	}
	
	public MailBox getByUserIdAndMailId(String userId, String mailId){
		String jpql="from MailBox mb where mb.innerMail.mailId=? and mb.userId=?";
		List<MailBox> list=this.getByJpql(jpql, new Object[]{mailId,userId});
		if(list.size()>0){
			return list.get(0);
		}
		return null;
		//return (MailBox)this.getUnique(jpql, new Object[]{mailId,userId});
	}
	
    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return MailBox.class;
    }
    
}
