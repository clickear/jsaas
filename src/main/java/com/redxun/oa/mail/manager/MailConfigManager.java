package com.redxun.oa.mail.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.mail.dao.MailConfigDao;
import com.redxun.oa.mail.entity.MailConfig;
/**
 * <pre> 
 * 描述：外部邮箱账号业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class MailConfigManager extends BaseManager<MailConfig>{
	@Resource
	private MailConfigDao mailConfigDao;
	@Resource
	private MailFolderManager mailFolderManager;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return mailConfigDao;
	}

	/**
	 * 创建外部邮箱账号时创建五个基本目录：根目录，收件箱，发件箱，草稿箱，垃圾箱
	 */
	@Override
	public void create(MailConfig entity) {      
		super.create(entity);
		mailFolderManager.createRootFolder(entity);    //创建根目录
		mailFolderManager.createReceiverFolder(entity);  //创建收件箱
		mailFolderManager.createSenderFolder(entity);   //创建发件箱
		mailFolderManager.createDraftFolder(entity);   //创建草稿箱
		mailFolderManager.createDelFolder(entity);   //创建垃圾箱
	}
	
	/**
	 * 获取当前用户下的所有外部邮箱账号配置（不分页，用于outlook-tree的显示外部邮箱账号列表）
	 * @param userId 用户Id
	 * @return
	 */
	public List<MailConfig> getAllByUserId(String userId){
		return mailConfigDao.getAllByUserId(userId);
	}
	
	/**
	 * 判断外部邮箱账号配置是否已存在
	 * @param mailAccount 外部邮箱地址
	 * @return
	 */
	public boolean isMailConfigExist(String mailAccount,String tenantId){
		return mailConfigDao.isMailConfigExist(mailAccount,tenantId);
	}
}