package com.redxun.oa.pro.controller;

import java.util.List;
import java.util.Set;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.org.manager.OsUserManager;
import com.redxun.oa.info.manager.InfInnerMsgManager;
import com.redxun.oa.pro.entity.ProAttend;
import com.redxun.oa.pro.entity.ProWorkAtt;
import com.redxun.oa.pro.entity.ProWorkMat;
import com.redxun.oa.pro.manager.ProAttendManager;
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
 * 人员管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/pro/proAttend/")
public class ProAttendFormController extends BaseFormController {

    @Resource
    private ProAttendManager proAttendManager;
    @Resource
    private ProjectManager projectManager;
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
    @ModelAttribute("proAttend")
    public ProAttend processForm(HttpServletRequest request) {
        String attId = request.getParameter("attId");
        ProAttend proAttend = null;
        if (StringUtils.isNotEmpty(attId)) {
            proAttend = proAttendManager.get(attId);
        } else {
            proAttend = new ProAttend();
        }

        return proAttend;
    }
    /**
     * 保存实体数据
     * @param request
     * @param proAttend
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("proAttend") @Valid ProAttend proAttend, BindingResult result) {
    	String SID=idGenerator.getSID();
    	String thisProjectId=proAttend.getProject().getProjectId();
    	StringBuffer user=new StringBuffer("");
     	StringBuffer group=new StringBuffer("");
     	Set<ProAttend> proAttends=projectManager.get(thisProjectId).getProAttends();
        for (ProAttend proAttend2 : proAttends) {//把改项目所有的人员以及组都取出并以空格分隔变成StringBuffer
        	String[] userids=proAttend2.getUserId().split(",");
        	if(proAttend2.getAttId().equals(proAttend.getAttId())){
        		user.append(proAttend.getAlexist());
        	}else{
        		for (String string : userids) {
    				user.append(string);
    				user.append(" ");
    			}
        	}
        	
			String[] groupids=proAttend2.getGroupId().split(",");
			if(proAttend2.getAttId().equals(proAttend.getAttId())){
        		group.append(proAttend.getAlexist());
        	}else{
        		for (String string : groupids) {
    				group.append(string);
    				group.append(" ");
    			}
        	}
			
		}
        if (result.hasFieldErrors()) {//验证表单，有错就返回
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(proAttend.getAttId())) {
            proAttend.setAttId(SID);
            /*以下将表格里的参与人和组与项目之前的参与人和组对比，不允许重复*/
            String[] thisAttGroup=proAttend.getGroupId().split(",");
            for (String string : thisAttGroup) {
            	if(StringUtils.isNotBlank(string))
            	{if(group.toString().contains(string)){
            		String[] GG=group.toString().split(" ");
            		for (String string2 : GG) {
            			if(string2.equals(string)){
            				 return new JsonResult(false, "已经包含了重复的组哦");
            			}
					}
				}}
			}
            String[] thisAttUser=proAttend.getUserId().split(",");
            for (String string : thisAttUser) {
            	if(StringUtils.isNotBlank(string)){
				if(user.toString().contains(string)){
					
					String[] GG=user.toString().split(" ");
            		for (String string2 : GG) {
            			if(string2.equals(string)){
            				 return new JsonResult(false, "已经包含了重复的人哦");
            			}
					}
				}}
			}
            proAttend.setPartType("JOIN");//设置参与类型为JOIN
            proAttendManager.create(proAttend);
            //以下都是创建“动态”
            ProWorkMat proWorkMat=new ProWorkMat();//此行以下都是创建“动态”
            proWorkMat.setActionId(idGenerator.getSID());
            proWorkMat.setType("MYACTION");//设置为该项目的动态的区别符
            proWorkMat.setTypePk(SID);
            proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"为项目'"+projectManager.get(thisProjectId).getName()+"'新增了人员");
            String context=proWorkMat.getContent();
            List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
            for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
            	if(proWorkAtt.getTypePk().equals(proWorkMat.getTypePk())){
            		String userId=proWorkAtt.getUserId();
   				 	infInnerMsgManager.sendMessage(userId, null,context , "", true);
            	}
			}
            proWorkMatManager.create(proWorkMat);
            msg = getMessage("proAttend.created", new Object[]{proAttend.getIdentifyLabel()}, "人员成功创建!");
        } else {
        	/*以下将表格里的参与人和组与项目之前的参与人和组对比，不允许重复，另外，对本身回填的数据进行例外处理*/
            String[] thisAttGroup=proAttend.getGroupId().split(",");
            for (String string : thisAttGroup) {
            	if((StringUtils.isNotBlank(string))&&(!proAttend.getAlexist().contains(string)))//先把回填的例外处理
            	{if(group.toString().contains(string)){
            		String[] GG=group.toString().split(" ");
            		for (String string2 : GG) {
            			if(string2.equals(string)){
            				 return new JsonResult(false, "已经包含了重复的组哦");
            			}
					}
				}}
			}
            String[] thisAttUser=proAttend.getUserId().split(",");
            for (String string : thisAttUser) {
            	if((StringUtils.isNotBlank(string))&&(!proAttend.getAlexist().contains(string))){//先把回填的例外处理
				if(user.toString().contains(string)){
					String[] GG=user.toString().split(" ");
            		for (String string2 : GG) {
            			if(string2.equals(string)){
            				
            					return new JsonResult(false, "已经包含了重复的人哦");
            				
            				 
            			}
					}
				}}
			}
        	proAttend.setPartType("JOIN");//设置参与类型为JOIN
            proAttendManager.update(proAttend);
            ProWorkMat proWorkMat=new ProWorkMat();//此行以下都是创建“动态”
            proWorkMat.setActionId(idGenerator.getSID());
            proWorkMat.setType("MYACTION");//设置为该项目的动态的区别符
            proWorkMat.setTypePk(proAttend.getAttId());
            proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"为项目'"+projectManager.get(thisProjectId).getName()+"'更新了人员");
            String context=proWorkMat.getContent();
            List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
            for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
            	if(proWorkAtt.getTypePk().equals(proWorkMat.getTypePk())){
            		String userId=proWorkAtt.getUserId();
   				 	infInnerMsgManager.sendMessage(userId, null,context , "", true);
            	}
			}
            proWorkMatManager.create(proWorkMat);
            msg = getMessage("proAttend.updated", new Object[]{proAttend.getIdentifyLabel()}, "人员成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

