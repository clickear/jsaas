package com.redxun.oa.mail.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.mail.entity.MailFolder;
import com.redxun.oa.mail.entity.OutMail;
/**
 * <pre> 
 * 描述：邮件文件夹数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class MailFolderDao extends BaseJpaDao<MailFolder> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return MailFolder.class;
    }
    
    /**
     * 根据配置ID获取邮箱文件夹
     * @param configId 外部邮箱配置Id
     * @return
     */
   public List<MailFolder> getOutMailFolderByConfigId(String configId){
	   String jpl="from MailFolder m where m.mailConfig.configId=? and m.inOut=?";
	   return this.getByJpql(jpl, new Object[]{configId,MailFolder.FOLDER_FLAG_OUT});
   }
   
   /**
    * 根据配置ID获取邮箱文件夹，去除根目录
    * @param configId 外部邮箱配置Id
    * @return 
    */
   public List<MailFolder> getOutMailFolderOutOfRootByConfigId(String configId){
	   String jpl="from MailFolder m where m.mailConfig.configId=? and m.inOut=? and m.parentId !=?";
	   return this.getByJpql(jpl, new Object[]{configId,MailFolder.FOLDER_FLAG_OUT,"0"});
   }
   
   /**
    * 根据文件夹父Id删除子目录
    * @param parentId 父目录Id
    */
   public void deleteChildren(String parentId){
	   String jpl="delete from MailFolder m where m.parentId=?";
	   this.delete(jpl, new Object[]{parentId});
   }
   
   /**
    * 获取当前用户所有外部邮件文件夹
    * @param userId 用户Id
    * @return 
    */
   public List<MailFolder> getAllOutMailFolderByUserId(String userId){
	   String jpl ="from MailFolder m where m.userId=? and m.inOut=?";
	   return this.getByJpql(jpl, new Object[]{userId,MailFolder.FOLDER_FLAG_OUT});
   }
   
   /**
    * 根据外部邮箱账号配置Id和文件夹类型获取邮件文件夹
    * @param configId 外部邮箱账号配置Id
    * @param type 文件夹类型
    * @return
    */
   public MailFolder getMailFolderByConfigIdAndType(String configId,String type){
	   String jpl ="from MailFolder m where m.mailConfig.configId=? and m.type=?";
	   return this.getByJpql(jpl, new Object[]{configId,type}).get(0);
   }
   
/*   public List<MailFolder> getRootOutMailFolder(){
	   String jpl="from Mailfolder m where m.parentId=? and m.inOut=?";
	   return this.getByJpql(jpl,new Object[]{"0",MailFolder.FOLDER_FLAG_OUT});
   }*/
   
   /**
    * 根据configId获取当前外部邮箱账号配置下的收件箱的未读邮件数量
    * @param configId 外部邮箱账号配置Id
    * @return 
    */
   public Long getUnreadMailByConfigId(String configId){
		   	String ql="select count(*) from OutMail m where m.mailConfig.configId=? and readFlag=? and m.mailFolder.type=? and m.status=?";
		   	return(Long)this.getUnique(ql, new Object[]{configId,"0",MailFolder.TYPE_RECEIVE_FOLDER,OutMail.STATUS_COMMEN});
   }
   
   /**
    * 根据邮箱文件夹folderId获取内部邮件收件箱未读邮件的数量
    * @param folderId 文件夹Id
    * @return 
    */
   public Long	getInnerUnreadMailByFolderId(String folderId){
	   String ql="select count(*) from MailBox mb where mb.mailFolder.folderId=? and mb.isRead=?";
	   return(Long)this.getUnique(ql, new Object[]{folderId,MBoolean.NO.name()});
   }
   
   /**
    * 查询当前用户下所有内部邮箱的文件夹
    * @param userId 用户Id
    * @return 
    */
   public List<MailFolder> getInnerMailFolder(String userId){                                      
	   String jpl ="from MailFolder m where m.inOut=? and m.userId=?";
	   return this.getByJpql(jpl, new Object[]{MailFolder.FOLDER_FLAG_IN,userId});
   }
   
   /**
    * 根据用户Id获取该用户的内部邮件文件夹的数量
    * @param userId 用户Id
    * @return 
    */
   public Long getInnerMailFolderNum(String userId){
	   String jpql="select count(*) from MailFolder m where m.userId=? and m.inOut=?";
	   return (Long)this.getUnique(jpql, new Object[]{userId,MailFolder.FOLDER_FLAG_IN});
   }
   
   /**
    * 根据用户Id获取内部邮箱收件箱目录
    * @param userId 用户Id
    * @return 
    */
   public MailFolder getInnerReceiveMailFolderByUserId(String userId){
	   String jpql="select m from MailFolder m where m.type=? and m.userId=? and m.inOut=?";
	   return (MailFolder)this.getUnique(jpql, new Object[]{MailFolder.TYPE_RECEIVE_FOLDER,userId,MailFolder.FOLDER_FLAG_IN});
   }
   
   /**
    * 根据文件夹类型和用户Id获取内部邮件目录文件夹
    * @param userId 用户Id
    * @param type  文件夹类型
    * @return 
    */
   public MailFolder getInnerMailFolderByUserIdAndType(String userId,String type){
	   String jpql="select m from MailFolder m where m.type=? and m.userId=? and m.inOut=?";
	   return (MailFolder)this.getUnique(jpql, new Object[]{type,userId,MailFolder.FOLDER_FLAG_IN});
   }
   
   /**
    * 根据文件夹folderId和用户userId获取内部邮件目录文件夹
    * @param userId 用户Id
    * @param folderId  文件夹Id
    * @return 
    */
   public MailFolder getInnerMailFolderByUserIdAndFolderId(String userId,String folderId){
	   String jpql="select m from MailFolder m where m.folderId=? and m.userId=? and m.inOut=?";
	   return (MailFolder)this.getUnique(jpql, new Object[]{folderId,userId,MailFolder.FOLDER_FLAG_IN});
   }
   
   /**
    * 获取当前用户的内部邮件文件夹目录，去除根目录和发件箱
    * @param userId 用户Id
    * @return 
    */
   public List<MailFolder> getInnerMailFolderOutOfRootByUserId(String userId){
	   String jpl="from MailFolder m where m.userId=? and m.inOut=? and m.parentId !=? and m.type!=?";
	   return this.getByJpql(jpl, new Object[]{userId,MailFolder.FOLDER_FLAG_IN,"0",MailFolder.TYPE_SENDER_FOLDER});
   }
   
   public List<MailFolder> getInnerSenderFolder(String userId){
	   MailFolder mailFolder=getInnerMailFolderByUserIdAndType(userId,MailFolder.TYPE_SENDER_FOLDER);
	   String jpl="from MailFolder m where m.userId=? and m.inOut=? and m.path like ? ";
	   return this.getByJpql(jpl, new Object[]{userId,MailFolder.FOLDER_FLAG_IN,mailFolder.getPath()+"%"});
   }
}
