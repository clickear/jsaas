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
 * 版本管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/pro/proVers/")
public class ProVersFormController extends BaseFormController {

    @Resource
    private ProVersManager proVersManager;
    @Resource
    private OsUserManager osUserManager;
    @Resource
    private ProWorkMatManager proWorkMatManager;
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
    @ModelAttribute("proVers")
    public ProVers processForm(HttpServletRequest request) {
        String versionId = request.getParameter("versionId");
        ProVers proVers = null;
        if (StringUtils.isNotEmpty(versionId)) {
            proVers = proVersManager.get(versionId);
        } else {
            proVers = new ProVers();
        }

        return proVers;
    }
    /**
     * 保存实体数据
     * @param request
     * @param proVers
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("proVers") @Valid ProVers proVers, BindingResult result) {
        String SID=idGenerator.getSID();
        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(proVers.getVersionId())) {
            proVers.setVersionId(SID);
            proVersManager.create(proVers);
            ProWorkMat proWorkMat=new ProWorkMat();
            proWorkMat.setActionId(idGenerator.getSID());
            proWorkMat.setType("MYACTION");//设置为该项目的动态的区别符
            proWorkMat.setTypePk(proVers.getProject().getProjectId());
            proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"创建了项目'"+projectManager.get(proVers.getProject().getProjectId()).getName()+"'的版本："+proVers.getVersion());
            String context=proWorkMat.getContent();
            List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
            for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
            	if(proWorkAtt.getTypePk().equals(proWorkMat.getTypePk())){
            		String userId=proWorkAtt.getUserId();
            		infInnerMsgManager.sendMessage(userId, null,context , "", true);
            	}
			}
            proWorkMatManager.create(proWorkMat);
            msg = getMessage("proVers.created", new Object[]{proVers.getVersion()}, "版本成功创建!");
        } else {
            proVersManager.update(proVers);
            ProWorkMat proWorkMat=new ProWorkMat();
            proWorkMat.setActionId(idGenerator.getSID());
            proWorkMat.setType("MYACTION");//设置为该项目的动态的区别符
            proWorkMat.setTypePk(proVers.getProject().getProjectId());
            proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"更新了项目'"+projectManager.get(proVers.getProject().getProjectId()).getName()+"'的版本："+proVers.getVersion());
            String context=proWorkMat.getContent();
            List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
            for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
            	if(proWorkAtt.getTypePk().equals(proWorkMat.getTypePk())){
            		String userId=proWorkAtt.getUserId();
            		infInnerMsgManager.sendMessage(userId, null,context , "", true);
            	}
			}
            proWorkMatManager.create(proWorkMat);
            msg = getMessage("proVers.updated", new Object[]{proVers.getVersion()}, "版本成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

