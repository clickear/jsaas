package com.redxun.oa.pro.controller;

import java.util.List;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.org.manager.OsUserManager;
import com.redxun.oa.info.manager.InfInnerMsgManager;
import com.redxun.oa.pro.entity.ProWorkAtt;
import com.redxun.oa.pro.entity.ProWorkMat;
import com.redxun.oa.pro.entity.ReqMgr;
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
 * 需求管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/pro/reqMgr/")
public class ReqMgrFormController extends BaseFormController {

    @Resource
    private ReqMgrManager reqMgrManager;
    @Resource
    private ProWorkMatManager proWorkMatManager;
    @Resource
    private OsUserManager osUserManager;
    @Resource
    private ProjectManager projectManager;
    @Resource
    InfInnerMsgManager infInnerMsgManager;
    @Resource
    ProWorkAttManager proWorkAttManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("reqMgr")
    public ReqMgr processForm(HttpServletRequest request) {
        String reqId = request.getParameter("reqId");
        String reqCode = request.getParameter("reqCode");
        String level=request.getParameter("level");
        ReqMgr reqMgr = null;
        if (StringUtils.isNotEmpty(reqId)) {
            reqMgr = reqMgrManager.get(reqId);
        } else {
            reqMgr = new ReqMgr();
        }

        return reqMgr;
    }
    /**
     * 保存实体数据
     * @param request
     * @param reqMgr
     * @param result	
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("reqMgr") @Valid ReqMgr reqMgr, BindingResult result) {
          String submitThis=request.getParameter("submitThis");
          String SID=idGenerator.getSID();
        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(reqMgr.getReqId())) {
            reqMgr.setReqId(idGenerator.getSID());
            if("YES".equals(submitThis))//如果是yes则把状态改成提交否则是草稿
            {
            	reqMgr.setStatus("PENDING AUDIT");//待审核
            }else {
           	 reqMgr.setStatus("DRAFT");//草稿
            }
            if(reqMgrManager.get(reqMgr.getParentId())!=null){//如果此需求的父Id能找到对应的需求则设置有效的路径   父路径+sid.
            	reqMgr.setPath(reqMgrManager.get(reqMgr.getParentId()).getPath()+reqMgr.getReqId()+".");
            }else{
            	reqMgr.setPath("0."+reqMgr.getReqId()+".");//否则设置为     0.sid.
            }
            reqMgrManager.create(reqMgr);
            ProWorkMat proWorkMat=new ProWorkMat();
            proWorkMat.setActionId(idGenerator.getSID());
            proWorkMat.setType("MYACTION");//设置为该项目的动态的区别符
            proWorkMat.setTypePk(reqMgr.getProject().getProjectId());
            proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"创建了项目'"+projectManager.get(reqMgr.getProject().getProjectId()).getName()+"'的需求："+reqMgr.getSubject());
            String context=proWorkMat.getContent();
            List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
            for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
            	if(proWorkAtt.getTypePk().equals(proWorkMat.getTypePk())){
            		String userId=proWorkAtt.getUserId();
   				 	infInnerMsgManager.sendMessage(userId, null,context , "", true);
            	}
			}
            proWorkMatManager.create(proWorkMat);
            msg = getMessage("reqMgr.created", new Object[]{reqMgr.getSubject()}, "需求成功创建!");
        } else {
        	//reqMgr.setStatus("PENDING AUDIT");
        	 if("YES".equals(submitThis))
             {
             	reqMgr.setStatus("PENDING AUDIT");
             }else {
            	 reqMgr.setStatus("DRAFT");
             }
        	 reqMgrManager.update(reqMgr);
        	 ProWorkMat proWorkMat=new ProWorkMat();
             proWorkMat.setActionId(idGenerator.getSID());
             proWorkMat.setType("MYACTION");//设置为该项目的动态的区别符
             proWorkMat.setTypePk(reqMgr.getProject().getProjectId());
             proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"更新了项目'"+projectManager.get(reqMgr.getProject().getProjectId()).getName()+"'的需求："+reqMgr.getSubject());
             String context=proWorkMat.getContent();
             List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
             for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
            	 if(proWorkAtt.getTypePk().equals(proWorkMat.getTypePk())){
             		String userId=proWorkAtt.getUserId();
             		infInnerMsgManager.sendMessage(userId, null,context , "", true);
             	}
 			}
             proWorkMatManager.create(proWorkMat);
        	 msg = getMessage("reqMgr.updated", new Object[]{reqMgr.getSubject()}, "需求成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

