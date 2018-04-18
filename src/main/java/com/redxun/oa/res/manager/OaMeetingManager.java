package com.redxun.oa.res.manager;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.redxun.bpm.activiti.handler.ProcessEndHandler;
import com.redxun.bpm.activiti.handler.ProcessStartAfterHandler;
import com.redxun.bpm.activiti.handler.TaskAfterHandler;
import com.redxun.bpm.core.entity.BpmInst;
import com.redxun.bpm.core.entity.IExecutionCmd;
import com.redxun.bpm.core.entity.ProcessStartCmd;
import com.redxun.bpm.core.entity.config.ProcessConfig;
import com.redxun.bpm.core.manager.BpmInstManager;
import com.redxun.core.constants.MStatus;
import com.redxun.core.dao.IDao;
import com.redxun.core.json.JSONUtil;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.seq.IdGenerator;
import com.redxun.core.util.BeanUtil;
import com.redxun.oa.res.dao.OaMeetingDao;
import com.redxun.oa.res.entity.OaMeetAtt;
import com.redxun.oa.res.entity.OaMeetRoom;
import com.redxun.oa.res.entity.OaMeeting;
/**
 * <pre> 
 * 描述：OaMeeting业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class OaMeetingManager extends BaseManager<OaMeeting> implements 
ProcessStartAfterHandler,TaskAfterHandler,ProcessEndHandler{
	@Resource
	private OaMeetingDao oaMeetingDao;
	@Resource
    BpmInstManager bpmInstManager;
	@Resource
	OaMeetAttManager oaMeetAttManager;
	@Resource
	protected IdGenerator idGenerator;
    @Resource
	OaMeetRoomManager oaMeetRoomManager;
	
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return oaMeetingDao;
	}
	
	public void deleteCasecade(String proId){
		OaMeeting oaMeeting=get(proId);
		if(oaMeeting!=null){
			if(StringUtils.isNotEmpty(oaMeeting.getBpmInstId())){
				bpmInstManager.deleteCasecadeByActInstId(oaMeeting.getBpmInstId(), "");
			}
			delete(proId);
		}
	}
	
	
	/**
	 * 通过Json更新供应商的值
	 * @param json
	 * @param busKey
	 */
	public void updateFromJson(String json,String busKey){
		OaMeeting oaMeeting=get(busKey);
		if(oaMeeting==null) return;
		OaMeeting newProvider=JSON.parseObject(json,OaMeeting.class);
		try {
			BeanUtil.copyNotNullProperties(oaMeeting, newProvider);
		} catch (Exception e) {
			e.printStackTrace();
		}
		oaMeetingDao.update(oaMeeting);
	}
	/**
	 * 2.任务审批完成时调用，用于更新供应商的数据
	 */
	@Override
	public void taskAfterHandle(IExecutionCmd cmd,String nodeId,String busKey){
		updateFromJson(cmd.getJsonData(),busKey);
	}
	/**
	 * 3.流程成功审批完成时，对供应商的审批状态进行更新
	 */
	@Override
	public void endHandle(BpmInst bpmInst) {
		String busKey=bpmInst.getBusKey();
		OaMeeting oaMeeting=oaMeetingDao.get(busKey);
		if(oaMeeting!=null){
			oaMeeting.setStatus(MStatus.ENABLED.name());
			oaMeetingDao.update(oaMeeting);
		}
	}
	
	/**
	 * 通过Json数据创建供应商
	 * @param json
	 * @param bpmInstId
	 * @return
	 */
	public OaMeeting createFromJson(String json,String actInstId){
		OaMeeting oaMeeting=JSON.parseObject(json, OaMeeting.class);
		JSONObject jsonObject=JSONObject.fromObject(json);
		String[] fullName=JSONUtil.getString(jsonObject, "fullName_name").split(",");
		String userIds=JSONUtil.getString(jsonObject, "fullName");
		String roomId=JSONUtil.getString(jsonObject, "roomId");
		OaMeetAtt oaMeetAtt=null;
		oaMeeting.getOaMeetAtts().clear();
		if(StringUtils.isNotEmpty(userIds)){
			String[] userId=userIds.split(",");
		if (userId.length > 0) {
			for (int i = 0; i < userId.length; i++) {
				if (StringUtils.isNotEmpty(oaMeeting.getMeetId())) {
					oaMeetAtt=oaMeetAttManager.getMeetIdByuserId(oaMeeting.getMeetId(),userId[i]);
					if (oaMeetAtt != null) {
					
						continue;
					}
				}
			
		oaMeetAtt = new OaMeetAtt();
		oaMeetAtt.setAttId(idGenerator.getSID());
		oaMeetAtt.setUserId(userId[i]);
		oaMeetAtt.setFullName(fullName[i]);
		oaMeetAtt.setStatus("DRAFT");
		oaMeetAtt.setOaMeeting(oaMeeting);
		oaMeeting.getOaMeetAtts().add(oaMeetAtt);
			}
			}
		}
		OaMeetRoom oRoom=oaMeetRoomManager.get(roomId);
		oaMeeting.setOaMeetRoom(oRoom);
		oaMeeting.setBpmInstId(actInstId);
		oaMeetingDao.create(oaMeeting);
		return oaMeeting;
	}
	
	/**
	 * 通过流程实例创建完成后通过表单的数据创建供应商信息
	 */
//	@Override
	public String processStartAfterHandle(String json, String actInstId) {
		OaMeeting oaMeeting=createFromJson(json,actInstId);;
		oaMeeting.setStatus(MStatus.DISABLED.name());
		return oaMeeting.getMeetId();
	}
	
	
	
	
	
	
	/**
     * 得到会议室的信息
     * @param roomId 会议室Id
     * @return 
     */
	public List<OaMeeting> getByDateRoomId(String roomId){
		return oaMeetingDao.getByDateRoomId(roomId);
	}
	
	/**
     * 检查这个时间段是否有会议室被占用
     */
    
    public boolean getByRoomId(String roomId,Date start,Date end){
    	List<OaMeeting> oaRoom=oaMeetingDao.getByRoomId(roomId, start, end);
    	return oaRoom!=null?true:false;
    	
    }

	public String processStartAfterHandle(ProcessConfig processConfig, ProcessStartCmd cmd, BpmInst bpmInst) {
		// TODO Auto-generated method stub
		return null;
	}

}