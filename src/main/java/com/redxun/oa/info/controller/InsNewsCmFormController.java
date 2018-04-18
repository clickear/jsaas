package com.redxun.oa.info.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.info.entity.InsNews;
import com.redxun.oa.info.entity.InsNewsCm;
import com.redxun.oa.info.manager.InsNewsCmManager;
import com.redxun.oa.info.manager.InsNewsManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * [InsNewsCm]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/info/insNewsCm/")
public class InsNewsCmFormController extends BaseFormController {

    @Resource
    private InsNewsCmManager insNewsCmManager;
    @Resource
    private InsNewsManager insNewsManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("insNewsCm")
    public InsNewsCm processForm(HttpServletRequest request) {
        String commId = request.getParameter("commId");
        InsNewsCm insNewsCm = null;
        if (StringUtils.isNotEmpty(commId)) {
            insNewsCm = insNewsCmManager.get(commId);
        } else {
            insNewsCm = new InsNewsCm();
        }

        return insNewsCm;
    }
    /**
     * 保存实体数据
     * @param request
     * @param insNewsCm
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("insNewsCm") @Valid InsNewsCm insNewsCm, BindingResult result) {
    	String code = (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);  
    	String validCode=request.getParameter("validCode");
    	if(code==null || !code.equals(validCode)){
    		return new JsonResult(false,"验证码不正确！");
    	}
    	
    	String isReply = request.getParameter("reply");//获取是否是子评论
    	String newId = request.getParameter("newId");//该条评论所属的新闻Id
    	String cmId = request.getParameter("cmId");//如果是子评论,回复那条评论的Id
        String msg = null;
        //判断是否是子评论
        if("no".equals(isReply)){//如果不是子评论
        	insNewsCm.setCommId(idGenerator.getSID());
        	insNewsCm.setAgreeNums(0);
        	insNewsCm.setNewId(newId);
        	insNewsCm.setInsNews(insNewsManager.get(newId));//该评论所属新闻
        	insNewsCm.setIsReply(isReply);
        	insNewsCm.setRefuseNums(0);
            insNewsCmManager.create(insNewsCm);
            msg = getMessage("insNewsCm.created", new Object[]{insNewsCm.getIdentifyLabel()}, "评论成功回复!");
        }else if("yes".equals(isReply)){//如果是子评论
        	insNewsCm.setCommId(idGenerator.getSID());
        	insNewsCm.setAgreeNums(0);
        	insNewsCm.setInsNews(insNewsManager.get(newId));
        	insNewsCm.setIsReply(isReply);
        	insNewsCm.setRepId(cmId);
        	insNewsCm.setRefuseNums(0);
            insNewsCmManager.create(insNewsCm);
            msg = getMessage("insNewsCm.created", new Object[]{insNewsCm.getIdentifyLabel()}, "评论成功回复!");
        }
        
        return new JsonResult(true, msg);
    }
}

