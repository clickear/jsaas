package com.redxun.oa.res.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.res.entity.OaMeetAtt;
import com.redxun.oa.res.manager.OaMeetAttManager;

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
 * 会议人员管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/res/oaMeetAtt/")
public class OaMeetAttFormController extends BaseFormController {

    @Resource
    private OaMeetAttManager oaMeetAttManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("oaMeetAtt")
    public OaMeetAtt processForm(HttpServletRequest request) {
        String attId = request.getParameter("attId");
        OaMeetAtt oaMeetAtt = null;
        if (StringUtils.isNotEmpty(attId)) {
            oaMeetAtt = oaMeetAttManager.get(attId);
        } else {
            oaMeetAtt = new OaMeetAtt();
        }

        return oaMeetAtt;
    }
    /**
     * 保存实体数据
     * @param request
     * @param oaMeetAtt
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("oaMeetAtt") @Valid OaMeetAtt oaMeetAtt, BindingResult result) {
    	String submitThis=request.getParameter("submitThis");
        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(oaMeetAtt.getAttId())) {
            oaMeetAtt.setAttId(idGenerator.getSID());
            if("YES".equals(submitThis))//如果是yes则把状态改成提交否则是草稿
            {
            	oaMeetAtt.setStatus("PENDING AUDIT");//待审核
            }else {
            	oaMeetAtt.setStatus("DRAFT");//草稿
            }
            oaMeetAttManager.create(oaMeetAtt);
            msg = getMessage("oaMeetAtt.created", new Object[]{oaMeetAtt.getOaMeeting().getName()}, "个人总结完成!");
        } else {
       	 if("YES".equals(submitThis))
         {
       		oaMeetAtt.setStatus("PENDING AUDIT");
         }else {
        	 oaMeetAtt.setStatus("DRAFT");
         }
            oaMeetAttManager.update(oaMeetAtt);
            msg = getMessage("oaMeetAtt.updated", new Object[]{oaMeetAtt.getOaMeeting().getName()}, "个人总结更新成功!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

