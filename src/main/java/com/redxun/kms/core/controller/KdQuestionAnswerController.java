package com.redxun.kms.core.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.util.BeanUtil;
import com.redxun.kms.core.entity.KdQuestion;
import com.redxun.kms.core.entity.KdQuestionAnswer;
import com.redxun.kms.core.entity.KdUser;
import com.redxun.kms.core.entity.KdUserLevel;
import com.redxun.kms.core.manager.KdQuestionAnswerManager;
import com.redxun.kms.core.manager.KdQuestionManager;
import com.redxun.kms.core.manager.KdUserLevelManager;
import com.redxun.kms.core.manager.KdUserManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;

/**
 * [KdQuestionAnswer]管理
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdQuestionAnswer/")
public class KdQuestionAnswerController extends TenantListController{
    @Resource
    KdQuestionAnswerManager kdQuestionAnswerManager;
   
    @Resource
    KdQuestionManager kdQuestionManager;
    
    @Resource
    KdUserLevelManager kdUserLevelManager;
    
    @Resource
    KdUserManager kdUserManager;
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                kdQuestionAnswerManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除！");
    }
    
    /**
     * 查看明细
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("get")
    public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String pkId=request.getParameter("pkId");
        KdQuestionAnswer kdQuestionAnswer=null;
        if(StringUtils.isNotEmpty(pkId)){
           kdQuestionAnswer=kdQuestionAnswerManager.get(pkId);
        }else{
        	kdQuestionAnswer=new KdQuestionAnswer();
        }
        return getPathView(request).addObject("kdQuestionAnswer",kdQuestionAnswer);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	KdQuestionAnswer kdQuestionAnswer=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		kdQuestionAnswer=kdQuestionAnswerManager.get(pkId);
    		if("true".equals(forCopy)){
    			kdQuestionAnswer.setAnswerId(null);
    		}
    	}else{
    		kdQuestionAnswer=new KdQuestionAnswer();
    	}
    	return getPathView(request).addObject("kdQuestionAnswer",kdQuestionAnswer);
    }
    
    @RequestMapping("delAnswer")
    @ResponseBody
    public JsonResult delAnswer(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String queId=request.getParameter("queId");
        if(StringUtils.isNotEmpty(queId)){
        	KdQuestionAnswer answer=kdQuestionAnswerManager.getByQueIdAndAuthorId(queId, ContextUtil.getCurrentUserId());
        	kdQuestionAnswerManager.delAnswer(answer);
        }
        return new JsonResult(true,"成功删除！");
    }
    
    @RequestMapping("saveAnswer")
    @ResponseBody
    public JsonResult saveAnswer(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String data=request.getParameter("data");
    	String queId=request.getParameter("queId");
    	String answerId=request.getParameter("answerId");
    	KdQuestionAnswer answer=new KdQuestionAnswer();
		answer.setKdQuestion(kdQuestionManager.get(queId));
		answer.setAuthorId(ContextUtil.getCurrentUserId());
		answer.setReplyContent(data);
    	if(StringUtils.isEmpty(answerId)){
    		answer.setAnswerId(idGenerator.getSID());
    		answer.setIsBest(MBoolean.NO.name());
    		kdQuestionAnswerManager.create(answer);
    		KdQuestion question= kdQuestionManager.get(queId);
    		question.setReplyCounts(question.getReplyCounts()+1);
    		kdQuestionManager.update(question);
    	}else{
    		KdQuestionAnswer o_answer=kdQuestionAnswerManager.get(answerId);
    		BeanUtil.copyProperties(o_answer,answer); 
    		kdQuestionAnswerManager.update(o_answer);
    	}
    	return new JsonResult(true, "成功保存回答！");
    }
    
    @RequestMapping("updateAnswer")
    @ResponseBody
    public JsonResult updateAnswer(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String queId=request.getParameter("queId");
    	String content=request.getParameter("content");
    	JSONObject jsonObject=JSONObject.fromObject(content);
    	String answerContent=jsonObject.getString("updateContent");
    	KdQuestionAnswer kdQuestionAnswer=kdQuestionAnswerManager.getByQueIdAndAuthorId(queId, ContextUtil.getCurrentUserId());
    	kdQuestionAnswer.setReplyContent(answerContent);
    	kdQuestionAnswerManager.update(kdQuestionAnswer);
    	return new JsonResult(true,"修改答案成功！");
    }
    
    @RequestMapping("collectAnswer")
    @ResponseBody
    public JsonResult collectAnswer(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String answerId=request.getParameter("answerId");
    	KdQuestionAnswer answer=kdQuestionAnswerManager.get(answerId);
    	answer.setIsBest(MBoolean.YES.name());
    	answer.getKdQuestion().setStatus(KdQuestion.STATUS_SOLVED);
    	KdUser kdUser=kdUserManager.getByUserId(answer.getCreateBy());
    	KdUserLevel kdUserLevel=kdUserLevelManager.getByPoint(kdUser.getPoint()+answer.getKdQuestion().getRewardScore(),ContextUtil.getCurrentTenantId());
    	kdUser.setPoint(kdUser.getPoint()+answer.getKdQuestion().getRewardScore());
    	kdUser.setGrade(kdUserLevel.getLevelName());
    	kdUserManager.update(kdUser);
    	kdQuestionAnswerManager.update(answer);
    	
    	return new JsonResult(true,"");
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return kdQuestionAnswerManager;
	}

}
