package com.redxun.oa.pro.controller;

import java.util.List;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.org.manager.OsUserManager;
import com.redxun.oa.info.manager.InfInnerMsgManager;
import com.redxun.oa.pro.entity.ProWorkAtt;
import com.redxun.oa.pro.entity.WorkLog;
import com.redxun.oa.pro.entity.ProWorkMat;
import com.redxun.oa.pro.manager.ProWorkAttManager;
import com.redxun.oa.pro.manager.WorkLogManager;
import com.redxun.oa.pro.manager.ProWorkMatManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 任务管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/pro/workLog/")
public class WorkLogFormController extends BaseFormController {

    @Resource
    private WorkLogManager workLogManager;
    @Resource
    private OsUserManager osUserManager;
    @Resource
    private ProWorkMatManager proWorkMatManager;
    @Resource
    InfInnerMsgManager infInnerMsgManager;
    @Resource
    ProWorkAttManager proWorkAttManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("workLog")
    public WorkLog processForm(HttpServletRequest request) {
        String logId = request.getParameter("logId");
        WorkLog workLog = null;
        if (StringUtils.isNotEmpty(logId)) {
            workLog = workLogManager.get(logId);
        } else {
            workLog = new WorkLog();
        }

        return workLog;
    }
    /**
     * 保存实体数据
     * @param request
     * @param workLog
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("workLog") @Valid WorkLog workLog, BindingResult result) {
        String type=request.getParameter("type");
        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(workLog.getLogId())) {
            workLog.setLogId(idGenerator.getSID());
            int l=(int) (workLog.getEndTime().getTime()-workLog.getStartTime().getTime());
            workLog.setLast(l/1000/60);//时间差
            workLog.setStatus(type);//是草稿还是提交
            if(StringUtils.isBlank(workLog.getPlanTask().getPlanId())){
            	workLog.setPlan(null);
            }
            if(workLog.getPlanTask()==null||StringUtils.isBlank(workLog.getPlanTask().getPlanId())){
        		workLog.setPlan(null);
        	}
            workLogManager.create(workLog);
            ProWorkMat proWorkMat=new ProWorkMat();
            proWorkMat.setActionId(idGenerator.getSID());
            proWorkMat.setType("MYACTION");//设置为该项目的动态的区别符
            proWorkMat.setTypePk(workLog.getLogId());
            proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"创建了日志'");
            String context=proWorkMat.getContent();
            List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
            for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
            	if(proWorkAtt.getTypePk().equals(proWorkMat.getTypePk())){
            		String userId=proWorkAtt.getUserId();
   				 	infInnerMsgManager.sendMessage(userId, null,context , "", true);
            	}
			}
            proWorkMatManager.create(proWorkMat);
            msg = getMessage("workLog.created", new Object[]{workLog.getIdentifyLabel()}, "日志成功创建!");
        } else {
        	int l=(int) (workLog.getEndTime().getTime()-workLog.getStartTime().getTime());
        	workLog.setLast(l/1000/60);//时间差
        	workLog.setStatus(type);//是草稿还是提交
        	if(StringUtils.isBlank(workLog.getPlanTask().getPlanId())){
        		workLog.setPlanTask(null);
        	}
        	if(workLog.getPlanTask()==null){}else
        	{workLogManager.detach(workLog.getPlanTask());}
        	workLogManager.update(workLog);
            ProWorkMat proWorkMat=new ProWorkMat();
            proWorkMat.setActionId(idGenerator.getSID());
            proWorkMat.setType("MYACTION");//设置为该项目的动态的区别符
            proWorkMat.setTypePk(workLog.getLogId());
            proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"更新了日志'");
            String context=proWorkMat.getContent();
            List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
            for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
            	if(proWorkAtt.getTypePk().equals(proWorkMat.getTypePk())){
            		String userId=proWorkAtt.getUserId();
            		infInnerMsgManager.sendMessage(userId, null,context , "", true);
            	}
			}
            proWorkMatManager.create(proWorkMat);
            msg = getMessage("workLog.updated", new Object[]{workLog.getIdentifyLabel()}, "日志成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

