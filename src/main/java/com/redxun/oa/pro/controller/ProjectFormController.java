package com.redxun.oa.pro.controller;

import java.util.List;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.org.manager.OsUserManager;
import com.redxun.oa.info.manager.InfInnerMsgManager;
import com.redxun.oa.pro.entity.ProVers;
import com.redxun.oa.pro.entity.ProWorkAtt;
import com.redxun.oa.pro.entity.ProWorkMat;
import com.redxun.oa.pro.entity.Project;
import com.redxun.oa.pro.manager.ProVersManager;
import com.redxun.oa.pro.manager.ProWorkAttManager;
import com.redxun.oa.pro.manager.ProWorkMatManager;
import com.redxun.oa.pro.manager.ProjectManager;

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
 * 项目管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/pro/project/")
public class ProjectFormController extends BaseFormController {

    @Resource
    private ProjectManager projectManager;
    @Resource
    private ProVersManager proVersManager;
    @Resource
    private ProWorkMatManager proWorkMatManager;
    @Resource
    private OsUserManager osUserManager;
    @Resource
    InfInnerMsgManager infInnerMsgManager;
    @Resource
    ProWorkAttManager proWorkAttManager;
    
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("project")
    public Project processForm(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        Project project = null;
        if (StringUtils.isNotEmpty(projectId)) {
            project = projectManager.get(projectId);
        } else {
            project = new Project();
        }

        return project;
    }
    
 
    /**
     * 保存实体数据
     * @param request
     * @param project
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("project") @Valid Project project, BindingResult result) {
    	
    	String SID=idGenerator.getSID();
        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(project.getProjectId())) {
            project.setProjectId(SID);
           project.setVersion("1.0.0.0");
           project.setStatus("DRAFTED");
            projectManager.create(project);
            ProVers proVers=new ProVers();//保存项目的同时初始化一个version为1.0.0.0
            proVers.setProject(project);
            proVers.setStatus("DRAFTED");
            proVers.setVersionId(idGenerator.getSID());
            proVers.setVersion("1.0.0.0");
            proVersManager.create(proVers);
            ProWorkMat proWorkMat=new ProWorkMat();
            proWorkMat.setActionId(idGenerator.getSID());
            proWorkMat.setType("MYACTION");//设置为该项目的动态的区别符
            proWorkMat.setTypePk(SID);//把项目的Id传给动态的类型主键
            proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"创建了项目'"+project.getName()+"'");
            proWorkMatManager.create(proWorkMat);
            msg = getMessage("project.created", new Object[]{project.getName()}, "项目成功创建!");
        } else {
            projectManager.update(project);
            ProWorkMat proWorkMat=new ProWorkMat();
            proWorkMat.setActionId(idGenerator.getSID());
            proWorkMat.setType("MYACTION");
            proWorkMat.setTypePk(project.getProjectId());
            proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"更新了项目'"+project.getName()+"'");
            proWorkMatManager.create(proWorkMat);
            String context=proWorkMat.getContent();
            List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
            for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
            	if(proWorkAtt.getTypePk().equals(proWorkMat.getTypePk())){
            		String userId=proWorkAtt.getUserId();
            		infInnerMsgManager.sendMessage(userId, null,context , "", true);
            	}
			}
            msg = getMessage("project.updated", new Object[]{project.getName()}, "项目成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

