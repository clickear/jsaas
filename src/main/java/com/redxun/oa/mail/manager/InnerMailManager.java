package com.redxun.oa.mail.manager;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.Page;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.seq.IdGenerator;
import com.redxun.oa.info.entity.InsPortalParams;
import com.redxun.oa.mail.dao.InnerMailDao;
import com.redxun.oa.mail.entity.InnerMail;
import com.redxun.oa.mail.entity.MailBox;
import com.redxun.oa.mail.entity.MailFolder;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.org.manager.OsUserManager;
/**
 * <pre> 
 * 描述：内部邮件业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class InnerMailManager extends BaseManager<InnerMail>{
	@Resource
	private InnerMailDao innerMailDao;
	
	@Resource
	IdGenerator idGenerator;
	
	@Resource
	OsUserManager osUserManager;
	
	
	
	@Resource
	MailFolderManager mailFolderManager;
	
	/**
	 * 获取内部邮件时并且设置阅读和回复标识
	 * @param folderId  内部邮箱文件夹Id
	 * @param queryFilter  查询条件
	 * @return
	 */
	public List<InnerMail> getInnerMailByFolderId(String folderId,QueryFilter queryFilter){
		return innerMailDao.getInnerMailByFolderId(folderId, queryFilter);
	}
	/**
	 * portal门户的显示获取内部邮件时并且设置阅读和回复标识
	 * @param userId
	 * @param queryFilter
	 * @param pageNum
	 * @return
	 */
	public List<InnerMail> getInnerMailByUserId(String userId,QueryFilter queryFilter,int pageNum){
		return innerMailDao.getInnerMailByUserId(userId, queryFilter,pageNum);
	}
	
	/**
	 * 根据邮件Id获取邮件内容
	 * @param mailId 内部邮件Id
	 * @return
	 */
	public String getInnerMailContentByMailId(String mailId){
		return innerMailDao.getInnerMailContentByMailId(mailId);
	}
	
	/**
	 * 
	 * @param subject 主题
	 * @param content 内容
	 * @param from 发件人Id
	 * @param to 收件人Id
	 * @param template 模板
	 */
	public void createInnerMail(String subject,String content,String from,String to,String template){
		InnerMail innerMail=new InnerMail();
		innerMail.setMailId(idGenerator.getSID());
		innerMail.setSender(osUserManager.get(from).getFullname());
		innerMail.setUserId(from);
		innerMail.setSenderTime(new Date());
		innerMail.setDelFlag(MBoolean.NO.name());
		innerMail.setStatus((short) 1);
		innerMail.setUrge("2");
		innerMail.setSubject(subject);
		innerMail.setContent(content);
		innerMail.setSenderId(from);
		innerMail.setRecIds(to);
		innerMail.setRecNames(osUserManager.get(to).getFullname());

		Set<MailBox> mailBoxs = new HashSet<MailBox>();

		innerMail.setRecNames(osUserManager.get(to).getFullname()); // 获取当前收件人ID的用户名
		Long folderNum = mailFolderManager.getInnerMailFolderNum(to); // 获取当前收件人的邮箱文件夹目录
		if (folderNum < 5L) // 数量小于5
			mailFolderManager.createInnerMailFolder(idGenerator.getSID(), to); // 创建初始化的5个基本目录
		MailBox mailBox = new MailBox(); // 创建收件人消息
		mailBox.setBoxId(idGenerator.getSID());
		mailBox.setInnerMail(innerMail); // 设置对应内部邮件
		mailBox.setMailFolder(mailFolderManager.getInnerReceiveMailFolderByUserId(to)); // 设置对应邮件目录文件夹
		mailBox.setUserId(to); // 设置用户Id
		mailBox.setIsDel(MBoolean.NO.name()); // 设置删除标记
		mailBox.setIsRead(MBoolean.NO.name()); // 设置阅读标记
		mailBox.setReply(MBoolean.NO.name()); // 设置回复标记
		mailBoxs.add(mailBox);
		
		MailBox sendmailBox = new MailBox(); // 设置发件人发件箱信息
		sendmailBox.setBoxId(idGenerator.getSID());
		sendmailBox.setInnerMail(innerMail);
		sendmailBox.setMailFolder(mailFolderManager.getInnerMailFolderByUserIdAndType(from, MailFolder.TYPE_SENDER_FOLDER)); // 设置发件箱信息
		sendmailBox.setUserId(from);
		sendmailBox.setIsDel(MBoolean.NO.name()); // 设置删除标记
		sendmailBox.setIsRead(MBoolean.NO.name()); // 设置阅读标记
		sendmailBox.setReply(MBoolean.NO.name()); // 设置回复标记
		mailBoxs.add(sendmailBox);
		
		innerMail.setMailBoxs(mailBoxs);
		innerMailDao.create(innerMail);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return innerMailDao;
	}
	
	/**
	 * 获取当前用户的门户的内部邮件显示
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getPortalInMail(InsPortalParams params) {
		String userId = ContextUtil.getCurrentUserId();
		QueryFilter queryFilter = new QueryFilter();
		Page page=new Page();
    	page.setPageIndex(0);
    	page.setPageSize(params.getPageSize());
		queryFilter.setPage(page);
		
		MailFolder mailFolder = mailFolderManager.getInnerMailFolderByUserIdAndType(userId, MailFolder.TYPE_RECEIVE_FOLDER);
		List<InnerMail> list = innerMailDao.getInnerMailByUserId(userId, queryFilter,params.getPageSize());  //获取当前用户的内部邮件
		
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("result", list);
		if(mailFolder != null ){
			result.put("folderId", mailFolder.getFolderId());
		}else{
			result.put("folderId", "0");
		}
		
		return result;

	}
}