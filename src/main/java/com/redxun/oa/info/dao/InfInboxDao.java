package com.redxun.oa.info.dao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.QueryParam;
import com.redxun.core.query.SortParam;
import com.redxun.oa.info.entity.InfInbox;
import com.redxun.oa.info.entity.InfInnerMsg;
import com.redxun.oa.info.entity.InsPortal;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.org.entity.OsGroup;
/**
 * <pre> 
 * 描述：InfInbox数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class InfInboxDao extends BaseJpaDao<InfInbox> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return InfInbox.class;
    }
    /**
     * 获取收信人Id为userId的List
     * @param userId
     * @return
     */
    public List<InfInbox> getByUserId(String userId){
    	String ql="from InfInbox inf where inf.recUserId=?  order by inf.createTime desc";
    	return this.getByJpql(ql, new Object[]{userId});
    }
    /**
     * 获取收信组为groupId的List
     * @param groupId
     * @return
     */
    public List<InfInbox> getByGroupId(String groupId){
    	String ql="from InfInbox inf where inf.groupId=? order by inf.createTime desc";
    	return this.getByJpql(ql, new Object[]{groupId,});
    }
    /**
     * 获取消息为msgId的收信人Id为userId的InfInbox,用于设置具体的某人的某条消息的已读
     * @param msgId
     * @param userId
     * @return
     */
	public InfInbox getByMsgId(String msgId, String userId){
    	String ql="from InfInbox box where box.infInnerMsg.msgId=? and box.recUserId=? "
    			+ " and box.recType = 'REC' order by box.createTime desc";
    	return (InfInbox)this.getUnique(ql, new Object[]{msgId, userId});
    }
    
	/**
	 * 获取发信人为sender的所有发出消息,用于已发信息
	 * @param sender 发信人,当前用户
	 * @param queryFilter
	 * @return
	 */
    public List<InfInbox> getInboxBySender(String sender,String senderId,QueryFilter queryFilter){
    	List<Object> params = new ArrayList<Object>();
    	String ql = "select box ,box.infInnerMsg.content as content from InfInbox box  where box.infInnerMsg.sender = ? and box.infInnerMsg.senderId = ? and box.recType = 'SEND'";
    	params.add(sender);
    	params.add(senderId);
    	Map<String,QueryParam> queryParams = queryFilter.getQueryParams();
    	//收信人
    	if(queryParams.get("fullname")!=null){
    		ql+=" and box.fullname like ?";
    		params.add(queryParams.get("fullname").getValue());
    	}
    	//日期查询
    	if(queryParams.get("startTime")!=null){
    		ql+=" and box.createTime > ?";
    		params.add(queryParams.get("startTime").getValue());
    	}
    	if(queryParams.get("endTime")!=null){
    		ql+=" and box.createTime < ?";
    		params.add(queryParams.get("endTime").getValue());
    	}
    	//排序
    	if(queryFilter.getOrderByList().size()>0){	    		
	    	SortParam sortParam=queryFilter.getOrderByList().get(0);
	    	ql+=" order by box."+sortParam.getProperty()+ " " + sortParam.getDirection();
	    }else{
	    	ql+=" order by box.createTime desc";
    	}
    	List list = this.getByJpql(ql, params.toArray(), queryFilter.getPage());
    	List<InfInbox> inboxs = new ArrayList<InfInbox>();
    	for(int i=0;i<list.size();i++){
    		Object[] rows = (Object[])list.get(i);
    		InfInbox box = (InfInbox)rows[0];
    		String content = (String)rows[1];
    		if(box.getFullname()!=null){
				box.setRecName(box.getFullname());
			}else if(box.getGroupName()!=null){
				box.setRecName(box.getGroupName());
			}
    		box.setContent(content);
    		inboxs.add(box);
    	}
    	return inboxs;
    }
}
