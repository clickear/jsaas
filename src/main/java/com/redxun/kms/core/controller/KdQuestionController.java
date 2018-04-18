package com.redxun.kms.core.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.Page;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.QueryParam;
import com.redxun.kms.core.entity.KdDocFav;
import com.redxun.kms.core.entity.KdMenu;
import com.redxun.kms.core.entity.KdQuestion;
import com.redxun.kms.core.entity.KdQuestionAnswer;
import com.redxun.kms.core.entity.KdUser;
import com.redxun.kms.core.manager.KdDocFavManager;
import com.redxun.kms.core.manager.KdQuestionAnswerManager;
import com.redxun.kms.core.manager.KdQuestionManager;
import com.redxun.kms.core.manager.KdUserManager;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.manager.SysFileManager;
import com.redxun.sys.core.manager.SysTreeManager;

/**
 * [KdQuestion]管理
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdQuestion/")
public class KdQuestionController extends TenantListController{
    @Resource
    KdQuestionManager kdQuestionManager;
    
    @Resource
    KdQuestionAnswerManager kdQuestionAnswerManager;
    
    @Resource
    SysTreeManager sysTreeManager;
    
    @Resource
    SysFileManager sysFileManager;
    
    @Resource
    KdUserManager kdUserManager;
    
    @Resource
    KdDocFavManager kdDocFavManager;
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                kdQuestionManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除！");
    }
    
    @RequestMapping("saveQuestion")
    @ResponseBody
    public JsonResult saveQuestion(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String formData=request.getParameter("formData");
    	KdQuestion kdQuestion=(KdQuestion)JSONUtil.json2Bean(formData, KdQuestion.class);
    	if (StringUtils.isEmpty(kdQuestion.getQueId())) {
            kdQuestion.setQueId(idGenerator.getSID());
            kdQuestion.setStatus(KdQuestion.STATUS_UNSOLVED);
            kdQuestion.setReplyCounts(0);
            kdQuestion.setViewTimes(0);
            if(StringUtils.isEmpty(kdQuestion.getFileIds()))
            	kdQuestion.setFileIds("0");
            String treeId=request.getParameter("treeId");
            kdQuestion.setSysTree(sysTreeManager.get(treeId));
            kdQuestionManager.create(kdQuestion);
        } else {
        	String treeId=request.getParameter("treeId");
        	kdQuestionManager.detach(kdQuestion.getSysTree());
        	kdQuestion.setSysTree(sysTreeManager.get(treeId));
            kdQuestionManager.update(kdQuestion);
        }
    	return new JsonResult(true,"提问成功！",kdQuestion);
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
        KdQuestion kdQuestion=null;
        IUser curUser=ContextUtil.getCurrentUser();
        List<SysFile> attach=new ArrayList<SysFile>();
        String isHasSlefAnswer=MBoolean.NO.name();
        if(StringUtils.isNotEmpty(pkId)){
           kdQuestion=kdQuestionManager.get(pkId);
           KdQuestionAnswer bestAnswer=kdQuestionAnswerManager.getBestAnswerByqueId(pkId);
           kdQuestion.setBestAnswer(bestAnswer);
           if(kdQuestion.getCreateBy().equals(ContextUtil.getCurrentUserId()))
        	   kdQuestion.setIsSelf(MBoolean.YES.name());    
           else
        	   kdQuestion.setIsSelf(MBoolean.NO.name());
           if(StringUtils.isNotEmpty(kdQuestion.getFileIds())){
        	   String[]fileIds=kdQuestion.getFileIds().split(",");
        	   for (int i = 0; i < fileIds.length; i++) {
        		   if("0".equals(fileIds[0]))
        			   break;
        		   attach.add(sysFileManager.get(fileIds[i]));
        	   }
           }
           kdQuestion.setViewTimes(kdQuestion.getViewTimes()+1);
           kdQuestionManager.update(kdQuestion);
           Set<KdQuestionAnswer> answers=kdQuestion.getKdQuestionAnswers();
			Iterator<KdQuestionAnswer> it = answers.iterator();
			String curUserId=ContextUtil.getCurrentUserId();
			while (it.hasNext()) {
				KdQuestionAnswer answer = it.next();
				if(curUserId.equals(answer.getAuthorId())){
					isHasSlefAnswer=MBoolean.YES.name();
					break;
				}
			}
        }else{
        	kdQuestion=new KdQuestion();
        }
        return getPathView(request).addObject("kdQuestion",kdQuestion).addObject("curUser", curUser).addObject("attach", attach).addObject("isHasSlefAnswer", isHasSlefAnswer);
    }
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	KdQuestion kdQuestion=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		kdQuestion=kdQuestionManager.get(pkId);
    		if("true".equals(forCopy)){
    			kdQuestion.setQueId(null);
    		}
    	}else{
    		kdQuestion=new KdQuestion();
    	}
    	return getPathView(request).addObject("kdQuestion",kdQuestion);
    }
    
    @RequestMapping("mgrEdit")
    public ModelAndView mgrEdit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	String fileNames="";
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	KdQuestion kdQuestion=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		kdQuestion=kdQuestionManager.get(pkId);
    		if("true".equals(forCopy)){
    			kdQuestion.setQueId(null);
    		}
    		String paths[]=kdQuestion.getSysTree().getPath().split("\\.");
        	String name="";
        	for (int i = 0; i < paths.length; i++) {
        		if(i==0){
        			name=sysTreeManager.get(paths[i]).getName();
        			continue;
        		}
    			name=name+"/"+sysTreeManager.get(paths[i]).getName();
    		}
        	kdQuestion.getSysTree().setName(name);
        	if(StringUtils.isNotEmpty(kdQuestion.getFileIds())){
	        	String[] fileNamearray=kdQuestion.getFileIds().split(",");
	        	for (int i = 0; i < fileNamearray.length; i++) {
	        		if(i==0){
	        			if("0".equals(fileNamearray[i]))
	        				break;
	        			fileNames=sysFileManager.get(fileNamearray[i]).getFileName();
	        			continue;
	        		}
	        		fileNames+=","+sysFileManager.get(fileNamearray[i]).getFileName();
				}
        	}
    	}else{
    		kdQuestion=new KdQuestion();
    	}
    	return getPathView(request).addObject("kdQuestion",kdQuestion).addObject("fileNames", fileNames);
    }
    
    @RequestMapping("mgrGet")
    public  ModelAndView mgrGet(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String pkId=request.getParameter("pkId");
        KdQuestion kdQuestion=null;
        List<SysFile> attach=new ArrayList<SysFile>();
        if(StringUtils.isNotEmpty(pkId)){
           kdQuestion=kdQuestionManager.get(pkId);
           KdQuestionAnswer bestAnswer=kdQuestionAnswerManager.getBestAnswerByqueId(pkId);
           kdQuestion.setBestAnswer(bestAnswer);
           if(StringUtils.isNotEmpty(kdQuestion.getFileIds())){
        	   String[]fileIds=kdQuestion.getFileIds().split(",");
        	   for (int i = 0; i < fileIds.length; i++) {
        		   if("0".equals(fileIds[0]))
        			   break;
        		   attach.add(sysFileManager.get(fileIds[i]));
        	   }
           }
        }else{
        	kdQuestion=new KdQuestion();
        }
        return getPathView(request).addObject("kdQuestion",kdQuestion).addObject("attach", attach);
    }
    
    @RequestMapping("index")
    public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	
    	List<KdQuestion> latestQuestions=kdQuestionManager.getLatestQuestion(ContextUtil.getCurrentTenantId());
    	List<KdQuestion> hostestQuestions=kdQuestionManager.getHostestQuestion(ContextUtil.getCurrentTenantId());
    	List<KdQuestion> solvedQuestions=kdQuestionManager.getByStatus(KdQuestion.STATUS_SOLVED,ContextUtil.getCurrentTenantId());
    	List<KdQuestion> unsolvedQuestions=kdQuestionManager.getByStatus(KdQuestion.STATUS_UNSOLVED,ContextUtil.getCurrentTenantId());
    	List<KdQuestion> bestQuestions=kdQuestionManager.getBestQuestion(ContextUtil.getCurrentTenantId());
    	List<KdQuestion> highrewardQuestions=kdQuestionManager.getHighRewardQuestion(ContextUtil.getCurrentTenantId());
    	List<KdUser> totalPoint = kdUserManager.getPointUser(new Page(0, 5),ContextUtil.getCurrentTenantId());
    	List<KdUser> experts=kdUserManager.getBySn(ContextUtil.getCurrentTenantId());
    	List<KdMenu> leftMenu=getLeftMenu();
    	return new ModelAndView("/kms/core/kmsIndex.jsp").addObject("latestQuestions",latestQuestions).addObject("hostestQuestions",hostestQuestions).addObject("solvedQuestions", solvedQuestions).addObject("unsolvedQuestions", unsolvedQuestions).addObject("bestQuestions", bestQuestions).addObject("highrewardQuestions", highrewardQuestions).addObject("experts", experts).addObject("leftMenu", iJson.toJson(leftMenu)).addObject("totalPoint", totalPoint);
    }
    
    @RequestMapping("personal")
    public ModelAndView personalIndex(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String userId = ContextUtil.getCurrentUserId();
    	String tab=request.getParameter("tab");
    	String type=request.getParameter("type");
    	List<KdQuestion> questionList=null;
    	List<KdQuestionAnswer> answerList=null;
    	List<KdQuestion> askMeQuestion=null;
    	List<KdQuestion> collectQuestion=null;
    	if("ask".equals(tab)){
    		questionList=kdQuestionManager.getByUserIdAndStatus(ContextUtil.getCurrentUserId(), type);
    	}
    	if("answer".equals(tab)){
    		answerList=kdQuestionAnswerManager.getByAuthorId(ContextUtil.getCurrentUserId());
    	}
		if("index".equals(tab)){
			askMeQuestion=kdQuestionManager.getByReplierId(ContextUtil.getCurrentUserId());
    	}
		if("collection".equals(tab)){
			collectQuestion=kdQuestionManager.getCollectQuestionByUserId(userId);
    	}
    	//IUser curUser=ContextUtil.getCurrentUser();
		KdUser curUser=kdUserManager.getByUserId(ContextUtil.getCurrentUserId());
    	return new ModelAndView("/kms/core/personalIndex.jsp").addObject("tab", tab).addObject("questionList", questionList).addObject("answerList", answerList).addObject("askMeQuestion", askMeQuestion).addObject("collectQuestion",collectQuestion).addObject("curUser", curUser).addObject("type", type).addObject("userId", userId);
    }
    
    @RequestMapping("refreshList")
    public ModelAndView refreshList(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String tab=request.getParameter("tab");
    	String type=request.getParameter("type");
    	List<KdQuestion> questions=null;
    	List<KdQuestionAnswer> answerLists=null;
    	List<KdQuestion> askMeQuestion=null;
    	List<KdQuestion> collectQuestion=null;
    	KdUser curUser=kdUserManager.getByUserId(ContextUtil.getCurrentUserId());
    	if("ask".equals(tab)){
    		questions=kdQuestionManager.getByUserIdAndStatus(ContextUtil.getCurrentUserId(), type);
    		return new ModelAndView("/kms/core/personalIndex.jsp").addObject("questionList",questions).addObject("tab", tab).addObject("curUser", curUser).addObject("type", type);
    	}
    	if("answer".equals(tab)){
    		answerLists=kdQuestionAnswerManager.getByAuthorIdAndBest(ContextUtil.getCurrentUserId(), type);
    		return new ModelAndView("/kms/core/personalIndex.jsp").addObject("answerList",answerLists).addObject("tab", tab).addObject("curUser", curUser).addObject("type", type);
    	}
    	if("index".equals(tab)){
			askMeQuestion=kdQuestionManager.getByReplierId(ContextUtil.getCurrentUserId());
			return new ModelAndView("/kms/core/personalIndex.jsp").addObject("askMeQuestion",askMeQuestion).addObject("tab", tab).addObject("curUser", curUser).addObject("type", type);
    	}
    	if("collection".equals(tab)){
    		String userId=ContextUtil.getCurrentUserId();
			collectQuestion=kdQuestionManager.getCollectQuestionByUserId(userId);
			return new ModelAndView("/kms/core/personalIndex.jsp").addObject("collectQuestion",collectQuestion).addObject("tab", tab).addObject("curUser", curUser).addObject("type", type);
    	}
    	return null;
    }
    
    @RequestMapping("getByPath")
    @ResponseBody
    public JsonPageResult<KdQuestion> getByPath(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
    	String treeId=request.getParameter("treeId");
		queryFilter.addFieldParam("sysTree.path",QueryParam.OP_LEFT_LIKE,sysTreeManager.get(treeId).getPath());
		queryFilter.addFieldParam("tenantId", ContextUtil.getCurrentTenantId());
    	List<KdQuestion> questions=kdQuestionManager.getAll(queryFilter);
    	JsonPageResult<KdQuestion> quesitonList=new JsonPageResult<KdQuestion>(questions, queryFilter.getPage().getTotalItems());
    	return quesitonList;
    }
    
    @RequestMapping("updateQuestionDetail")
    @ResponseBody
    public JsonResult updateQuestionDetail(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String queId=request.getParameter("queId");
    	String content=request.getParameter("content");
    	JSONObject jsonObject=JSONObject.fromObject(content);
    	String questionDetail=jsonObject.getString("questionDetail");
    	KdQuestion question=kdQuestionManager.get(queId);
    	question.setQuestion(questionDetail);
    	kdQuestionManager.update(question);
    	return new JsonResult(true, "补充问题成功！");
    }
    
    @RequestMapping("getAllQuestion")
    @ResponseBody
    public JsonPageResult<KdQuestion> getAllQuestion(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
    	queryFilter.addFieldParam("tenantId", ContextUtil.getCurrentTenantId());
    	List<KdQuestion> questions=kdQuestionManager.getAll(queryFilter);
    	JsonPageResult<KdQuestion> result=new JsonPageResult<KdQuestion>(questions, queryFilter.getPage().getTotalItems());
    	return result;
    }
    
    @RequestMapping("getByAddition")
    @ResponseBody
    public JsonPageResult<KdQuestion> getByAddition(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String catId=request.getParameter("catId");
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		if(StringUtils.isNotEmpty(catId))
			queryFilter.addFieldParam("sysTree.path",QueryParam.OP_LEFT_LIKE,sysTreeManager.get(catId).getPath());
		List<KdQuestion> questions = kdQuestionManager.getAll(queryFilter);
		JsonPageResult<KdQuestion> result = new JsonPageResult<KdQuestion>(questions, queryFilter.getPage().getTotalItems());
    	return result;
    }
    
    @RequestMapping("find")
    public ModelAndView find(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String catId=request.getParameter("catId");
    	String name="";
    	if(StringUtils.isNotEmpty(catId)){
	    	SysTree sysTree= sysTreeManager.get(catId);
	    	String[] paths={};
	    	if(StringUtils.isNotEmpty(sysTree.getPath())){
	    		paths=sysTree.getPath().split("\\.");
	    	}
	    	
	    	for (int i = 0; i < paths.length; i++) {
	    		if(i==0){
	    			name="<a class='cat' id='"+sysTreeManager.get(paths[i]).getTreeId()+"'>"+sysTreeManager.get(paths[i]).getName()+"</a>";
	    			continue;
	    		}
				name=name+">"+"<a class='cat' id='"+sysTreeManager.get(paths[i]).getTreeId()+"'>"+sysTreeManager.get(paths[i]).getName()+"</a>";
			}
    	}
    	
    	List<KdMenu> leftMenu=getLeftMenu();
    	return this.getPathView(request).addObject("leftMenu", iJson.toJson(leftMenu)).addObject("name", name);
    }
    
    @RequestMapping("expertEdit")
    public ModelAndView expertEdit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String uId=request.getParameter("uId");
    	KdUser user= kdUserManager.getByUserId(uId);
    	return this.getPathView(request).addObject("kdUser", user);
    }
    
    @RequestMapping("saveExpertQuestion")
    @ResponseBody
    public JsonResult saveExpertQuestion(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String uId=request.getParameter("uId");
    	String formData=request.getParameter("formData");
    	KdQuestion kdQuestion=(KdQuestion)JSONUtil.json2Bean(formData, KdQuestion.class);
    	kdQuestion.setQueId(idGenerator.getSID());
        kdQuestion.setStatus(KdQuestion.STATUS_UNSOLVED);
        kdQuestion.setReplyCounts(0);
        kdQuestion.setViewTimes(0);
    	kdQuestion.setReplierId(uId);
    	if(StringUtils.isEmpty(kdQuestion.getFileIds()))
        	kdQuestion.setFileIds("0");
    	kdQuestionManager.create(kdQuestion);
    	return new JsonResult(true,"提问成功！",kdQuestion);
    }
    
    @RequestMapping("collectQuestion")
    @ResponseBody
    public JsonResult collectQuestion(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String queId=request.getParameter("queId");
    	String userId=request.getParameter("uId");
    	if(StringUtils.isNotEmpty(queId)){
    		if(kdDocFavManager.getQuestionIsFav(userId, queId)){
    			System.out.println("6666");
    			return new JsonResult(true,"该问题你已经收藏了！");
    		}
    	}
    	if(StringUtils.isNotEmpty(queId)){
    		KdDocFav fav=new KdDocFav();
    		fav.setFavId(idGenerator.getSID());
    		fav.setKdQuestion(kdQuestionManager.get(queId));
    		kdDocFavManager.create(fav);
    		return new JsonResult(true,"收藏成功！");
    	}
    	else
    		return new JsonResult(true,"收藏失败！");
    }
    
    public List<KdMenu> getLeftMenu() {
		String tenantId = ContextUtil.getCurrentTenantId();
		
		List<SysTree> trees = sysTreeManager.getMainMenu("CAT_KMS_KDQUE", tenantId);
		List<KdMenu> menus = new ArrayList<KdMenu>();
		for (SysTree tree : trees) {
			KdMenu menu = new KdMenu();
			List<KdMenu> secondMenus = new ArrayList<KdMenu>();
			menu.setId(tree.getTreeId());
			menu.setName(tree.getName());
			List<SysTree> secondTrees = sysTreeManager.getByParentIdCatKey(tree.getTreeId(), "CAT_KMS_KDQUE", tenantId);
			for (SysTree secondTree : secondTrees) {
				KdMenu secondMenu = new KdMenu();
				List<KdMenu> thirdMenus = new ArrayList<KdMenu>();
				secondMenu.setId(secondTree.getTreeId());
				secondMenu.setName(secondTree.getName());
				List<SysTree> thirdTrees = sysTreeManager.getByParentIdCatKey(secondTree.getTreeId(), "CAT_KMS_KDQUE", tenantId);
				for (SysTree thirdTree : thirdTrees) {
					KdMenu thirdMenu = new KdMenu();
					thirdMenu.setId(thirdTree.getTreeId());
					thirdMenu.setName(thirdTree.getName());
					thirdMenus.add(thirdMenu);
				}
				secondMenu.setLowerMenu(thirdMenus);
				secondMenus.add(secondMenu);
			}
			menu.setLowerMenu(secondMenus);
			menus.add(menu);
		}
		return menus;
	}
    
   

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return kdQuestionManager;
	}

}
