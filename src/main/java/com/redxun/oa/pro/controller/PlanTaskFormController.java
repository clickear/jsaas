package com.redxun.oa.pro.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.org.manager.OsUserManager;
import com.redxun.oa.info.manager.InfInnerMsgManager;
import com.redxun.oa.pro.dao.PlanTaskDao;
import com.redxun.oa.pro.dao.ReqMgrDao;
import com.redxun.oa.pro.entity.PlanTask;
import com.redxun.oa.pro.entity.ProWorkAtt;
import com.redxun.oa.pro.entity.ProWorkMat;
import com.redxun.oa.pro.entity.ReqMgr;
import com.redxun.oa.pro.manager.PlanTaskManager;
import com.redxun.oa.pro.manager.ProWorkAttManager;
import com.redxun.oa.pro.manager.ProWorkMatManager;
import com.redxun.oa.pro.manager.ProjectManager;
import com.redxun.oa.pro.manager.ReqMgrManager;

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
 * 计划管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/pro/planTask/")
public class PlanTaskFormController extends BaseFormController {

    @Resource
    private PlanTaskManager planManager;
    @Resource
    private OsUserManager osUserManager;
    @Resource
    private ProWorkMatManager proWorkMatManager;
    @Resource
    private ProjectManager projectManager;
    @Resource
    private ReqMgrManager reqMgrManager;
    @Resource
    InfInnerMsgManager infInnerMsgManager;
    @Resource
    ProWorkAttManager proWorkAttManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("planTask")
    public PlanTask processForm(HttpServletRequest request) {
        String planId = request.getParameter("planId");
        PlanTask planTask = null;
        if (StringUtils.isNotEmpty(planId)) {
            planTask = planManager.get(planId);
        } else {
            planTask = new PlanTask();
        }

        return planTask;
    }
    /**
     * 保存实体数据
     * @param request
     * @param planTask
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("planTask") @Valid PlanTask planTask, BindingResult result) {
        String SID=idGenerator.getSID();
        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(planTask.getPlanId())) {//新建
            planTask.setPlanId(SID);
            if(planTask.getProject()!=null&&StringUtils.isNotBlank(planTask.getProject().getProjectId())){//如果有project
            	if(StringUtils.isBlank(planTask.getReqMgr().getReqId())||planTask.getReqMgr()==null){//如果没reqMgr
            		planTask.setReqMgr(null);//置空reqMgr
            	}
            	planTask.setVersion(projectManager.get(planTask.getProject().getProjectId()).getVersion());//如果有project则把project当前的version设置进去
            }else{planTask.setProject(null);}//如果没project则把project置空
            if(planTask.getStartTime()==null){
         	   if("执行中".equals(planTask.getStatus())){
         		   Date date=new Date();
         		   planTask.setStartTime(date);
         	   }
            }
            planManager.create(planTask);
            ProWorkMat proWorkMat=new ProWorkMat();//此行以下都是创建“动态”
            proWorkMat.setActionId(idGenerator.getSID());
            proWorkMat.setType("MYACTION");//设置为该项目的动态的区别符
            if(planTask.getProject()!=null){//如果是建立项目的需求则是这个提示
            	 proWorkMat.setTypePk(planTask.getProject().getProjectId());
                 proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"创建了项目'"+projectManager.get(planTask.getProject().getProjectId()).getName()+"'的计划:"+planTask.getSubject());
                 String context=proWorkMat.getContent();
                 List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
                 for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
                 	if(proWorkAtt.getTypePk().equals(proWorkMat.getTypePk())){
                 		String userId=proWorkAtt.getUserId();
                 		infInnerMsgManager.sendMessage(userId, "", context, "", true);
                 	}
     			}
            }else{
            	proWorkMat.setTypePk(planTask.getReqMgr().getReqId());
                proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"创建了需求'"+reqMgrManager.get(planTask.getReqMgr().getReqId()).getSubject()+"'的计划:"+planTask.getSubject());
                String context=proWorkMat.getContent();
                List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
                for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
                	if(proWorkAtt.getTypePk().equals(proWorkMat.getTypePk())){
                		String userId=proWorkAtt.getUserId();
                		infInnerMsgManager.sendMessage(userId, "", context, "", true);
                	}
    			}
            }
           
            proWorkMatManager.create(proWorkMat);
            msg = getMessage("planTask.created", new Object[]{planTask.getSubject()}, "计划成功创建!");
        } else {//更新
        	 if(planTask.getProject()!=null&&StringUtils.isNotBlank(planTask.getProject().getProjectId())){//如果有project
        		 planManager.detach(planTask.getProject());
        		 if(planTask.getReqMgr()==null||StringUtils.isBlank(planTask.getReqMgr().getReqId())){//如果没ReqMgr
        			planManager.detach(planTask.getReqMgr());
             		planTask.setReqMgr(null);	
             	}else{
             		planManager.detach(planTask.getReqMgr());
             	}
             }else{
            	 planManager.detach(planTask.getProject());
            	 planManager.detach(planTask.getReqMgr());
            	 planTask.setProject(null);
            }
        	 if("完成".equals(planTask.getStatus())){
        		 Date date=new Date();
        		 planTask.setEndTime(date);
        		 planTask.setStatus("完成");
        		 if((planTask.getStartTime()!=null)&&(planTask.getLast()==null)){//如果开始时间和结束时间都有则计算耗时
        			 planTask.setLast((int) ((planTask.getEndTime().getTime()-planTask.getStartTime().getTime())/1000/60));
        		 }
        	 }
        	planManager.update(planTask);
            ProWorkMat proWorkMat=new ProWorkMat();
            proWorkMat.setActionId(idGenerator.getSID());
            proWorkMat.setType("MYACTION");//设置为该项目的动态的区别符
            if(planTask.getProject()!=null&&planTask.getProject().getProjectId().length()>0){//如果是建立项目的需求则是这个提示
           	    proWorkMat.setTypePk(planTask.getProject().getProjectId());
                proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"更新了项目'"+projectManager.get(planTask.getProject().getProjectId()).getName()+"'的计划:"+planTask.getSubject());
                String context=proWorkMat.getContent();
                infInnerMsgManager.sendMessage(ContextUtil.getCurrentUserId(), "", context, "", true);
           }else{
           	   proWorkMat.setTypePk(planTask.getReqMgr().getReqId());
               proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"更新了需求'"+reqMgrManager.get(planTask.getReqMgr().getReqId()).getSubject()+"'的计划:"+planTask.getSubject());
               
               String context=proWorkMat.getContent();
               List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
               for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
   				String userId=proWorkAtt.getUserId();
   				infInnerMsgManager.sendMessage(userId, "", context, "", true);
   			}
           }
            
            proWorkMatManager.create(proWorkMat);
            msg = getMessage("planTask.updated", new Object[]{planTask.getSubject()}, "计划成功更新!");
        }
        
        return new JsonResult(true, msg);
    }
}

