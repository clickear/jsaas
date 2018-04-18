package com.redxun.oa.pro.controller;

import java.util.List;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.manager.SysFileManager;
import com.redxun.sys.core.manager.SysModuleManager;
import com.redxun.sys.org.manager.OsUserManager;
import com.redxun.oa.info.manager.InfInnerMsgManager;
import com.redxun.oa.pro.entity.ProWorkAtt;
import com.redxun.oa.pro.entity.ProWorkMat;
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
 * 动态管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/pro/proWorkMat/")
public class ProWorkMatFormController extends BaseFormController {

    @Resource
    private ProWorkMatManager proWorkMatManager;
    @Resource
    OsUserManager osUserManager;
    @Resource
    private ProjectManager projectManager;
    @Resource
    private ReqMgrManager reqMgrManager;
    @Resource
    private SysFileManager sysFileManager;
    @Resource
    private SysModuleManager sysModuleManager;
   
    @Resource
    InfInnerMsgManager infInnerMsgManager;
    @Resource
    ProWorkAttManager proWorkAttManager;
    
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("proWorkMat")
    public ProWorkMat processForm(HttpServletRequest request) {
        String actionId = request.getParameter("actionId");
        ProWorkMat proWorkMat = null;
        if (StringUtils.isNotEmpty(actionId)) {
            proWorkMat = proWorkMatManager.get(actionId);
        } else {
            proWorkMat = new ProWorkMat();
        }

        return proWorkMat;
    }
    /**
     * 保存实体数据的同时再针对本条评论新增动态
     * @param request
     * @param proWorkMat
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("proWorkMat") @Valid ProWorkMat proWorkMat, BindingResult result) {
        String SID=idGenerator.getSID();
        StringBuffer NAME=new StringBuffer("");
        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(proWorkMat.getActionId())) {
            proWorkMat.setActionId(SID);
            proWorkMatManager.create(proWorkMat);
            //对评论创建动态
            ProWorkMat proWorkMat1=new ProWorkMat();
            proWorkMat1.setActionId(idGenerator.getSID());
            proWorkMat1.setType("MYACTION");
            proWorkMat1.setTypePk(proWorkMat.getTypePk());//将评论的主体的主键赋给该条动态
            NAME.append(proWorkMat.getType());
            if("PROJECT".equals(proWorkMat.getType())){//如果评论的主体是项目，设置特殊动态内容
            	proWorkMat1.setTypePk(proWorkMat.getTypePk());//将评论的主体的主键赋给该条动态
            	NAME.append(":");
            	NAME.append(projectManager.get(proWorkMat.getTypePk()).getName());
            	
            }else{
            	proWorkMat1.setTypePk(reqMgrManager.get(proWorkMat.getTypePk()).getProject().getProjectId());
            	NAME.append(":");
            	NAME.append(reqMgrManager.get(proWorkMat.getTypePk()).getSubject());
            }
            proWorkMat1.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"新增了对'"+NAME+"'的评论");
            String context=proWorkMat1.getContent();
            List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
            for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
            	if(proWorkAtt.getTypePk().equals(proWorkMat1.getActionId())){
            		String userId=proWorkAtt.getUserId();
   				 	infInnerMsgManager.sendMessage(userId, null,context , "", true);
            	}
				
			}
            proWorkMatManager.create(proWorkMat1);
            String attach =request.getParameter("attach"); 
           /* if(StringUtils.isNotEmpty(attach)){//attach字段不为空则逗号切割它
         	   String[] fileIds=attach.split("[,]");
         	   for(String fileId:fileIds){//再把切出来的字段赋给fileId，通过它来查找出所有对应的sysFile
         		   SysFile sysFile=sysFileManager.get(fileId);
         		   sysFile.setRecordId(proWorkMat.getActionId());
         		   sysFile.setSysModule(sysModuleManager.getByEntityClass("com.redxun.oa.pro.entity.ProWorkMat"));
         		   sysFileManager.saveOrUpdate(sysFile);
         	   }
            }*/
        
            msg = getMessage("proWorkMat.created", new Object[]{proWorkMat.getType()}, "动态成功创建!");
        } else {
            proWorkMatManager.update(proWorkMat);
            //同上个if
            ProWorkMat proWorkMat1=new ProWorkMat();
            proWorkMat1.setActionId(idGenerator.getSID());
            proWorkMat1.setType("MYACTION");
            
            NAME.append(proWorkMat.getType());
            if("PROJECT".equals(proWorkMat.getType())){
            	proWorkMat1.setTypePk(proWorkMat.getTypePk());
            	NAME.append(":");
            	NAME.append(projectManager.get(proWorkMat.getTypePk()).getName());
            }else{
            	proWorkMat1.setTypePk(reqMgrManager.get(proWorkMat.getTypePk()).getProject().getProjectId());
            	NAME.append(":");
            	NAME.append(reqMgrManager.get(proWorkMat.getTypePk()).getSubject());
            }
            
            proWorkMat1.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"修改了对'"+NAME+"'的评论");
            String context=proWorkMat1.getContent();
            List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
            for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
            	if(proWorkAtt.getTypePk().equals(proWorkMat1.getActionId())){
            		String userId=proWorkAtt.getUserId();
            		infInnerMsgManager.sendMessage(userId, null,context , "", true);
            	}
			}
            proWorkMatManager.create(proWorkMat1);
            msg = getMessage("proWorkMat.updated", new Object[]{proWorkMat.getType()}, "动态成功更新!");
        }
        return new JsonResult(true, msg);
    }
}

