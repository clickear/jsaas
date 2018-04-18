package com.redxun.oa.mail.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.mail.dao.MailFolderDao;
import com.redxun.oa.mail.entity.MailConfig;
import com.redxun.oa.mail.entity.MailFolder;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.IdUtil;

/**
 * <pre>
 * 描述：邮件文件夹业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class MailFolderManager extends BaseManager<MailFolder> {
	@Resource
	private MailFolderDao mailFolderDao;

	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return mailFolderDao;
	}

	/**
	 * 根据配置ID获取邮箱文件夹
	 * 
	 * @param configId 外部邮箱配置Id
	 * @return
	 */
	public List<MailFolder> getOutMailFolderByConfigId(String configId) {
		return mailFolderDao.getOutMailFolderByConfigId(configId);
	}
	
	/**
	 * 根据配置ID获取邮箱文件夹，去除根目录
	 * @param configId 外部邮箱配置Id
	 * @return
	 */
	public List<MailFolder> getOutMailFolderOutOfRootByConfigId(String configId){
		return mailFolderDao.getOutMailFolderOutOfRootByConfigId(configId);
	}

	/*
	 * public List<MailFolder> getRootOutMailFolder(){ return
	 * mailFolderDao.getRootOutMailFolder(); }
	 */

	/**
	 * 创建外部邮件文件夹：根目录
	 * 
	 * @param mailConfig 外部邮件账号配置
	 */
	public void createRootFolder(MailConfig mailConfig) {
		MailFolder folder = new MailFolder();
		folder.setFolderId(IdUtil.getId());
		folder.setMailConfig(mailConfig);
		folder.setFolderId(mailConfig.getConfigId());
		folder.setParentId("0");
		folder.setName(mailConfig.getMailAccount());
		folder.setUserId(ContextUtil.getCurrentUserId());
		folder.setDepth(0);
		folder.setPath(folder.getFolderId() + ".");
		folder.setInOut(MailFolder.FOLDER_FLAG_OUT);
		folder.setType(MailFolder.TYPE_ROOT_FOLDER);
		mailFolderDao.create(folder);
	}

	/**
	 * 创建外部邮件文件夹：收件箱目录
	 * 
	 * @param mailConfig 外部邮件账号配置
	 */
	public void createReceiverFolder(MailConfig mailConfig) {
		MailFolder folder = new MailFolder();
		folder.setFolderId(IdUtil.getId());
		folder.setMailConfig(mailConfig);
		folder.setParentId(mailConfig.getConfigId());
		folder.setName("收件箱");
		folder.setUserId(ContextUtil.getCurrentUserId());
		folder.setDepth(1);
		folder.setPath(mailConfig.getConfigId() + "." + folder.getFolderId() + ".");
		folder.setInOut(MailFolder.FOLDER_FLAG_OUT);
		folder.setType(MailFolder.TYPE_RECEIVE_FOLDER);
		mailFolderDao.create(folder);
	}

	/**
	 * 创建外部邮件文件夹：发件箱目录
	 * 
	 * @param mailConfig 外部邮件账号配置
	 */
	public void createSenderFolder(MailConfig mailConfig) {
		MailFolder folder = new MailFolder();
		folder.setFolderId(IdUtil.getId());
		folder.setMailConfig(mailConfig);
		folder.setParentId(mailConfig.getConfigId());
		folder.setName("发件箱");
		folder.setUserId(ContextUtil.getCurrentUserId());
		folder.setDepth(1);
		folder.setPath(mailConfig.getConfigId() + "." + folder.getFolderId() + ".");
		folder.setInOut(MailFolder.FOLDER_FLAG_OUT);
		folder.setType(MailFolder.TYPE_SENDER_FOLDER);
		mailFolderDao.create(folder);
	}

	/**
	 * 创建外部邮件文件夹：草稿箱目录
	 * 
	 * @param mailConfig 外部邮件账号配置
	 */
	public void createDraftFolder(MailConfig mailConfig) {
		MailFolder folder = new MailFolder();
		folder.setFolderId(IdUtil.getId());
		folder.setMailConfig(mailConfig);
		folder.setParentId(mailConfig.getConfigId());
		folder.setName("草稿箱");
		folder.setUserId(ContextUtil.getCurrentUserId());
		folder.setDepth(1);
		folder.setPath(mailConfig.getConfigId() + "." + folder.getFolderId() + ".");
		folder.setInOut(MailFolder.FOLDER_FLAG_OUT);
		folder.setType(MailFolder.TYPE_DRAFT_FOLDER);
		mailFolderDao.create(folder);
	}

	/**
	 * 创建外部邮件文件夹：垃圾箱目录
	 * 
	 * @param mailConfig 外部邮件账号配置
	 */
	public void createDelFolder(MailConfig mailConfig) {
		MailFolder folder = new MailFolder();
		folder.setFolderId(IdUtil.getId());
		folder.setMailConfig(mailConfig);
		folder.setParentId(mailConfig.getConfigId());
		folder.setName("垃圾箱");
		folder.setUserId(ContextUtil.getCurrentUserId());
		folder.setDepth(1);
		folder.setPath(mailConfig.getConfigId() + "." + folder.getFolderId() + ".");
		folder.setInOut(MailFolder.FOLDER_FLAG_OUT);
		folder.setType(MailFolder.TYPE_DEL_FOLDER);
		mailFolderDao.create(folder);
	}

	/**
	 * 创建外部邮件文件夹：其他用户自定义目录
	 * 
	 * @param mailConfig 外部邮件账号配置
	 */
	public void createOtherFolder(MailConfig mailConfig) {
		MailFolder folder = new MailFolder();
		folder.setFolderId(IdUtil.getId());
		folder.setMailConfig(mailConfig);
		folder.setParentId(mailConfig.getConfigId());
		folder.setName("1");
		folder.setUserId(ContextUtil.getCurrentUserId());
		folder.setDepth(1);
		folder.setType(MailFolder.TYPE_OTHER_FOLDER);
		mailFolderDao.create(folder);
	}

	/**
	 * 根据文件夹父Id删除子目录
	 * 
	 * @param parentId 父目录Id
	 */
	public void deleteChildren(String parentId) {
		mailFolderDao.deleteChildren(parentId);
	}

	/**
	 * 获取当前用户所有外部邮件文件夹
	 * 
	 * @param userId 用户Id
	 * @return
	 */
	public List<MailFolder> getAllOutMailFolderByUserId(String userId) {
		return mailFolderDao.getAllOutMailFolderByUserId(userId);
	}

	/**
	 * 根据外部邮箱账号配置Id和文件夹类型获取邮件文件夹
	 * 
	 * @param configId 外部邮箱账号配置Id
	 * @param type 文件夹类型
	 * @return
	 */
	public MailFolder getMailFolderByConfigIdAndType(String configId, String type) {
		return mailFolderDao.getMailFolderByConfigIdAndType(configId, type);
	}

	/**
	 * 根据configId获取当前外部邮箱账号配置下的收件箱的未读邮件数量
	 * 
	 * @param configId 外部邮箱账号配置Id
	 * @return
	 */
	public Long getUnreadMailByConfigId(String configId) {
		return mailFolderDao.getUnreadMailByConfigId(configId);
	}

	/**
	 * 根据邮箱文件夹folderId获取内部邮件收件箱未读邮件的数量
	 * 
	 * @param folderId 文件夹Id
	 * @return
	 */
	public Long getInnerUnreadMailByFolderId(String folderId) {
		return mailFolderDao.getInnerUnreadMailByFolderId(folderId);
	}

	/**
	 * 查询当前用户下所有内部邮箱的文件夹
	 * @param userId 用户Id
	 * @return
	 */
	public List<MailFolder> getInnerMailFolder(String userId) {
		return mailFolderDao.getInnerMailFolder(userId);
	}

	/**
	 * 创建默认内部邮箱目录
	 * 
	 * @param folderId 根目录的folderId
	 * @param userId 用户Id
	 */
	public void createInnerMailFolder(String folderId, String userId) {
		MailFolder rootFolder = new MailFolder(); // 创建根目录
		rootFolder.setFolderId(folderId);
		rootFolder.setUserId(userId);
		rootFolder.setName("我的内部邮件");
		rootFolder.setParentId("0");
		rootFolder.setDepth(0);
		rootFolder.setPath(rootFolder.getFolderId() + ".");
		rootFolder.setType(MailFolder.TYPE_ROOT_FOLDER);
		rootFolder.setInOut(MailFolder.FOLDER_FLAG_IN);

		MailFolder receiveFolder = new MailFolder(); // 创建收件箱目录
		receiveFolder.setFolderId(IdUtil.getId());
		receiveFolder.setUserId(userId);
		receiveFolder.setName("收件箱");
		receiveFolder.setParentId(folderId);
		receiveFolder.setDepth(1);
		receiveFolder.setPath(folderId + "." + receiveFolder.getFolderId() + ".");
		receiveFolder.setType(MailFolder.TYPE_RECEIVE_FOLDER);
		receiveFolder.setInOut(MailFolder.FOLDER_FLAG_IN);

		MailFolder senderFolder = new MailFolder(); // 创建发件箱目录
		senderFolder.setFolderId(IdUtil.getId());
		senderFolder.setUserId(userId);
		senderFolder.setName("发件箱");
		senderFolder.setParentId(folderId);
		senderFolder.setDepth(1);
		senderFolder.setPath(folderId + "." + senderFolder.getFolderId() + ".");
		senderFolder.setType(MailFolder.TYPE_SENDER_FOLDER);
		senderFolder.setInOut(MailFolder.FOLDER_FLAG_IN);

		MailFolder draftFolder = new MailFolder(); // 创建草稿箱目录
		draftFolder.setFolderId(IdUtil.getId());
		draftFolder.setUserId(userId);
		draftFolder.setName("草稿箱");
		draftFolder.setParentId(folderId);
		draftFolder.setDepth(1);
		draftFolder.setPath(folderId + "." + draftFolder.getFolderId() + ".");
		draftFolder.setType(MailFolder.TYPE_DRAFT_FOLDER);
		draftFolder.setInOut(MailFolder.FOLDER_FLAG_IN);

		MailFolder delFolder = new MailFolder(); // 创建垃圾箱目录
		delFolder.setFolderId(IdUtil.getId());
		delFolder.setUserId(userId);
		delFolder.setName("垃圾箱");
		delFolder.setParentId(folderId);
		delFolder.setDepth(1);
		delFolder.setPath(folderId + "." + delFolder.getFolderId() + ".");
		delFolder.setType(MailFolder.TYPE_DEL_FOLDER);
		delFolder.setInOut(MailFolder.FOLDER_FLAG_IN);

		mailFolderDao.create(rootFolder);
		mailFolderDao.create(receiveFolder);
		mailFolderDao.create(senderFolder);
		mailFolderDao.create(draftFolder);
		mailFolderDao.create(delFolder);

	}

	/**
	 * 根据用户Id获取该用户的内部邮件文件夹的数量
	 * @param userId 用户Id
	 * @return
	 */
	public Long getInnerMailFolderNum(String userId) {
		return mailFolderDao.getInnerMailFolderNum(userId);
	}

	/**
	 * 根据用户Id获取内部邮箱收件箱目录
	 * @param userId 用户Id
	 * @return
	 */
	public MailFolder getInnerReceiveMailFolderByUserId(String userId) {
		return mailFolderDao.getInnerReceiveMailFolderByUserId(userId);
	}

	/**
	 * 根据文件夹类型和用户Id获取内部邮件目录文件夹
	 * @param userId 用户Id
	 * @param type 文件夹类型
	 * @return
	 */
	public MailFolder getInnerMailFolderByUserIdAndType(String userId, String type) {
		return mailFolderDao.getInnerMailFolderByUserIdAndType(userId, type);
	}

	/**
	 * 根据文件夹folderId和用户userId获取内部邮件目录文件夹
	 * @param userId 用户Id
	 * @param folderId 文件夹folderId
	 * @return
	 */
	public MailFolder getInnerMailFolderByUserIdAndFolderId(String userId, String folderId) {
		return mailFolderDao.getInnerMailFolderByUserIdAndFolderId(userId, folderId);
	}

	/**
	 * 获取当前用户的内部邮件文件夹目录，去除根目录
	 * @param userId 用户Id
	 * @return
	 */
	public List<MailFolder> getInnerMailFolderOutOfRootByUserId(String userId) {
		return mailFolderDao.getInnerMailFolderOutOfRootByUserId(userId);
	}
	
	public List<MailFolder> getInnerSenderFolder(String userId){
		return mailFolderDao.getInnerSenderFolder(userId);
	}
	
	public void delete(String folderId){
		super.delete(folderId);
	    deleteChildren(folderId); // 删除这个文件夹下的子文件夹
	}
}