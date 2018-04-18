package com.redxun.oa.res.manager;
import java.util.List;

import javax.annotation.Resource;

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
import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.util.BeanUtil;
import com.redxun.oa.res.dao.OaCarAppDao;
import com.redxun.oa.res.entity.OStatus;
import com.redxun.oa.res.entity.OaCarApp;
/**
 * <pre> 
 * 描述：OaCarApp业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class OaCarAppManager extends BaseManager<OaCarApp> implements 
ProcessStartAfterHandler,TaskAfterHandler,ProcessEndHandler{
	@Resource
	private OaCarAppDao oaCarAppDao;
	@Resource
    BpmInstManager bpmInstManager;
	
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return oaCarAppDao;
	}
	
	public void deleteCasecade(String proId){
		OaCarApp oaCarApp=get(proId);
		if(oaCarApp!=null){
			if(StringUtils.isNotEmpty(oaCarApp.getBpmInst())){
				bpmInstManager.deleteCasecadeByActInstId(oaCarApp.getBpmInst(), "");
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
		OaCarApp oaCarApp=get(busKey);
		if(oaCarApp==null) return;
		OaCarApp newCarApp=JSON.parseObject(json,OaCarApp.class);
		try {
			BeanUtil.copyNotNullProperties(oaCarApp, newCarApp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		oaCarAppDao.update(oaCarApp);
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
		OaCarApp oaCarApp=oaCarAppDao.get(busKey);
		if(oaCarApp!=null){
			oaCarApp.setStatus(OStatus.APPROVED.name());
			oaCarAppDao.update(oaCarApp);
		}
	}

	/**
	 * 通过Json数据创建供应商
	 * @param json
	 * @param bpmInstId
	 * @return
	 */
	public OaCarApp createFromJson(String json,String actInstId){
		OaCarApp oaCarApp=JSON.parseObject(json, OaCarApp.class);
		oaCarApp.setBpmInst(actInstId);
		oaCarAppDao.create(oaCarApp);
		return oaCarApp;
	}
	
	/**
	 * 通过流程实例创建完成后通过表单的数据创建供应商信息
	 */
//	@Override
	public String processStartAfterHandle(String json, String actInstId) {
		OaCarApp oaCarApp=createFromJson(json,actInstId);;
		oaCarApp.setStatus(OStatus.NOTAPPROVED.name());
		return oaCarApp.getAppId();
	}

	
	
	public List<OaCarApp> getOaCarApp(){
		return this.oaCarAppDao.getOaCarApp();
	}

	public String processStartAfterHandle(ProcessConfig processConfig, ProcessStartCmd cmd, BpmInst bpmInst) {
		// TODO Auto-generated method stub
		return null;
	}

}