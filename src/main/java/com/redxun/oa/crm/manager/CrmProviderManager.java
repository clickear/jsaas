package com.redxun.oa.crm.manager;
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
import com.redxun.core.constants.MStatus;
import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.util.BeanUtil;
import com.redxun.oa.crm.dao.CrmProviderDao;
import com.redxun.oa.crm.entity.CrmProvider;
/**
 * <pre> 
 * 描述：CrmProvider业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class CrmProviderManager extends BaseManager<CrmProvider> implements 
ProcessStartAfterHandler,TaskAfterHandler,ProcessEndHandler{
	@Resource
	private CrmProviderDao crmProviderDao;
	@Resource
    BpmInstManager bpmInstManager;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return crmProviderDao;
	}
	
	public void deleteCasecade(String proId){
		CrmProvider crmProvider=get(proId);
		if(crmProvider!=null){
			if(StringUtils.isNotEmpty(crmProvider.getActInstId())){
				bpmInstManager.deleteCasecadeByActInstId(crmProvider.getActInstId(), "");
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
		CrmProvider orgProvider=get(busKey);
		if(orgProvider==null) return;
		CrmProvider newProvider=JSON.parseObject(json,CrmProvider.class);
		try {
			BeanUtil.copyNotNullProperties(orgProvider, newProvider);
		} catch (Exception e) {
			e.printStackTrace();
		}
		crmProviderDao.update(orgProvider);
	}
	/**
	 * 2.任务审批完成时调用，用于更新供应商的数据
	 */
	@Override
	public void taskAfterHandle(IExecutionCmd cmd, String nodeId, String busKey) {
		updateFromJson(cmd.getJsonData(),busKey);
	}
	/**
	 * 3.流程成功审批完成时，对供应商的审批状态进行更新
	 */
	@Override
	public void endHandle(BpmInst bpmInst) {
		String busKey=bpmInst.getBusKey();
		CrmProvider crmProvider=crmProviderDao.get(busKey);
		if(crmProvider!=null){
			crmProvider.setStatus(MStatus.ENABLED.name());
			crmProviderDao.update(crmProvider);
		}
	}

	/**
	 * 通过Json数据创建供应商
	 * @param json
	 * @param bpmInstId
	 * @return
	 */
	public CrmProvider createFromJson(String json,String actInstId){
		CrmProvider crmProvider=JSON.parseObject(json, CrmProvider.class);
		crmProvider.setActInstId(actInstId);
		crmProviderDao.create(crmProvider);
		return crmProvider;
	}
	
	/**
	 * 通过流程实例创建完成后通过表单的数据创建供应商信息
	 */
//	@Override
	public String processStartAfterHandle(String json, String actInstId) {
		CrmProvider crmProvider=createFromJson(json,actInstId);
		crmProvider.setStatus(MStatus.INIT.name());
		return crmProvider.getProId();
	}

	public String processStartAfterHandle(ProcessConfig processConfig, ProcessStartCmd cmd, BpmInst bpmInst) {
		// TODO Auto-generated method stub
		return null;
	}
	
}