package com.redxun.oa.mail.dao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.core.query.Page;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.QueryParam;
import com.redxun.core.query.SortParam;
import com.redxun.core.util.BeanUtil;
import com.redxun.oa.mail.entity.InnerMail;
import com.redxun.oa.mail.entity.MailFolder;
/**
 * <pre> 
 * 描述：内部邮件数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class InnerMailDao extends BaseJpaDao<InnerMail> {
	/**
	 * 获取内部邮件时并且设置阅读和回复标识
	 * @param folderId  内部邮箱文件夹Id
	 * @param queryFilter  查询条件
	 * @return
	 */
	@SuppressWarnings({ "rawtypes"})
	public List<InnerMail> getInnerMailByFolderId(String folderId,QueryFilter queryFilter){
		List<Object> params=new ArrayList<Object>();
		String ql="select m.innerMail as mail, m.isRead as isRead, m.reply as reply from MailBox m where m.mailFolder.folderId=? and m.mailFolder.inOut = 'IN'";
		params.add(folderId);
		Map<String,QueryParam> queryParams=queryFilter.getQueryParams();
		
		if(queryParams.get("subject")!=null){        //如果主体不为空
			ql+=" and m.innerMail.subject like ?";
			params.add(queryParams.get("subject").getValue());
		}
		
		if(queryParams.get("sender")!=null){     //如果发件人Id不为空
			ql+=" and m.innerMail.sender like ?";
			params.add(queryParams.get("sender").getValue());
		}
		
		if(queryParams.get("senderTime1")!=null){     //如果起始时间不为空
			ql+=" and m.innerMail.senderTime >=?";
			params.add(queryParams.get("senderTime1").getValue());
		}
		
		if(queryParams.get("senderTime2")!=null){     //如果结束时间不为空
			ql+=" and m.innerMail.senderTime <=?";
			params.add(queryParams.get("senderTime2").getValue());
		}
		
		if(queryFilter.getOrderByList().size()>0){	    		
	    	SortParam sortParam=queryFilter.getOrderByList().get(0);
	    	ql+=" order by m.innerMail."+sortParam.getProperty()+ " " + sortParam.getDirection();
	    }else{
	    	ql+=" order by m.innerMail.createTime desc";
    	}
		
		List list=this.getByJpql(ql,params.toArray() , queryFilter.getPage());
		List<InnerMail> innerMailList=new ArrayList<InnerMail>();
		for(int i=0;i<list.size();i++){
			Object[] rows=(Object[])list.get(i);  //获取集合中的查询结果
			InnerMail tmpMail=new InnerMail();
			InnerMail mail=(InnerMail)rows[0];    //获取内部邮件实体
			BeanUtil.copyProperties(tmpMail, mail);
			String isRead=(String)rows[1];      //获取对应内部邮件实体的阅读标识
			String reply=(String)rows[2];        //获取对应内部邮件实体的回复标识
			tmpMail.setIsRead(isRead);      //设置阅读标识
			tmpMail.setIsReply(reply);   //设置回复标识
			innerMailList.add(tmpMail);
		}
		return innerMailList;
	}
	
	/**
	 * portal门户的显示获取内部邮件时并且设置阅读和回复标识
	 * @param userId
	 * @param queryFilter
	 * @param pageNum
	 * @return
	 */
	@SuppressWarnings({ "rawtypes"})
	public List<InnerMail> getInnerMailByUserId(String userId,QueryFilter queryFilter,int pageNum){
		List<Object> params=new ArrayList<Object>();
		String ql="select m.innerMail as mail, m.isRead as isRead, m.reply as reply from MailBox m where m.mailFolder.type = ? and m.mailFolder.inOut = ? and m.userId =?";
		params.add(MailFolder.TYPE_RECEIVE_FOLDER);
		params.add(MailFolder.FOLDER_FLAG_IN);
		params.add(userId);
		ql+=" order by m.createTime desc";
		Page page = new Page(0, pageNum);
		List list=this.getByJpql(ql,params.toArray() , page);
		List<InnerMail> innerMailList=new ArrayList<InnerMail>();
		for(int i=0;i<list.size();i++){
			Object[] rows=(Object[])list.get(i);  //获取集合中的查询结果
			InnerMail mail=(InnerMail)rows[0];    //获取内部邮件实体
			String isRead=(String)rows[1];      //获取对应内部邮件实体的阅读标识
			String reply=(String)rows[2];        //获取对应内部邮件实体的回复标识
			mail.setIsRead(isRead);      //设置阅读标识
			mail.setIsReply(reply);   //设置回复标识
			innerMailList.add(mail);
		}
		return innerMailList;
	}
	
	/**
	 * 根据邮件Id 获取邮件内容
	 * @param mailId 内部邮件Id
	 * @return
	 */
	public String getInnerMailContentByMailId(String mailId){
		String jpql="select m.content from InnerMail m where m.mailId=?";
		return (String)this.getUnique(jpql, new Object[]{mailId});
	}
	
	
    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return InnerMail.class;
    }
    
}
