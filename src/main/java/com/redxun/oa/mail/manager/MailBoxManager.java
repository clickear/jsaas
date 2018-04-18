package com.redxun.oa.mail.manager;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.mail.dao.MailBoxDao;
import com.redxun.oa.mail.entity.MailBox;
/**
 * <pre> 
 * 描述：内部邮件收件箱业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class MailBoxManager extends BaseManager<MailBox>{
	@Resource
	private MailBoxDao mailBoxDao;
	
	/**
	 * 根据内部邮件Id和文件夹Id获取对应的内部邮件收件箱实体
	 * @param mailId 内部邮件Id
	 * @param folderId  文件夹Id
	 * @return
	 */
	public MailBox getMailBoxByInnerMailIdAndFolderId(String mailId,String folderId){
		return mailBoxDao.getMailBoxByInnerMailIdAndFolderId(mailId, folderId);
	}
	
	/**
	 * 获取当前用户文件夹目录未读邮件数量
	 * @param userId 当前用户Id
	 * @param folderId 文件夹Id
	 * @return
	 */
	public Long getUnreadReceiveFolderInnerMail(String userId, String folderId){
		return mailBoxDao.getUnreadReceiveFolderInnerMail(userId, folderId);
	}
	
	public MailBox getByUserIdAndMailId(String userId, String mailId){
		return mailBoxDao.getByUserIdAndMailId(userId, mailId);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return mailBoxDao;
	}
}