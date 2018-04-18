package com.redxun.oa.info.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.oa.info.entity.InsNewsCm;
import com.redxun.oa.info.entity.ReplyComment;
import com.redxun.oa.info.entity.SortReply;
import com.redxun.oa.info.manager.InsNewsCmManager;
import com.redxun.oa.info.manager.InsNewsManager;

/**
 * 新闻评论管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/info/insNewsCm/")
public class InsNewsCmController extends BaseListController{
    @Resource
    InsNewsCmManager insNewsCmManager;
    @Resource
    InsNewsManager insNewsManager;
   

   
	/**
	 * 新闻评论的List页面显示,加入一个新闻标题的属性在List中显示
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("listData")
	public void listData(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String export=request.getParameter("_export");
		//是否导出
		if(StringUtils.isNotEmpty(export)){
			String exportAll=request.getParameter("_all");
			if(StringUtils.isNotEmpty(exportAll)){
				exportAllPages(request,response);
			}else{
				exportCurPage(request,response);
			}
		}else{
  			response.setContentType("application/json");
			QueryFilter queryFilter=getQueryFilter(request);
			List<InsNewsCm> list=getBaseManager().getAll(queryFilter);
			List<InsNewsCm> list2 = new ArrayList<InsNewsCm>();
			for(int i=0;i<list.size();i++){
				InsNewsCm cm= list.get(i);
				cm.setNewsTitle(insNewsManager.get(cm.getNewId()).getSubject());//加入新闻标题的属性,在List页面显示
				list2.add(cm);
			}
			JsonPageResult<?> result=new JsonPageResult(list2,queryFilter.getPage().getTotalItems());
			String jsonResult=iJson.toJson(result);
			PrintWriter pw=response.getWriter();
			pw.println(jsonResult);
			pw.close();
		}
	}
	
	/**
	 * 获得这条评论下面的所有子评论	
	 * @param rplId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InsNewsCm> findAllReply(String rplId){
    	List<InsNewsCm> rplCms = new ArrayList<InsNewsCm>();
    	if(insNewsCmManager.getByReplyId(rplId).size()!=0){//判断这条评论下是否有子评论
    		for(InsNewsCm rplcm:insNewsCmManager.getByReplyId(rplId)){//遍历所有子评论
    			rplCms.add(rplcm);//将子评论加入所有子评论的List中
    			rplCms.addAll(findAllReply(rplcm.getCommId()));//再次判断这条子评论是否有子评论,如果有就加入
    		}
    	}
    	SortReply sort = new SortReply();
    	Collections.sort(rplCms,sort);//按照时间排序
    	return rplCms;
    }
	
	/**
	 * 删除评论
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");//获得评论的Id
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
            	//判断是否是子评论
            	if("yes".equals(insNewsCmManager.get(id).getIsReply())){//如果这是一条子评论,则只删除该条评论
            		insNewsCmManager.delete(id);
            	}else{//如果不是,则删除这条评论及这条评论下面的所有子评论
            		 List<InsNewsCm> replyCms = findAllReply(id);//获得这条评论下面的所有子评论
            		 for(InsNewsCm replyCm:replyCms){            		
            			 insNewsCmManager.delete(replyCm.getCommId());
                 	}
            		insNewsCmManager.delete(id);
            	}
            }
        }
        return new JsonResult(true,"成功删除！");
    }
    
	@RequestMapping("listByNewId")
	@ResponseBody
	public JsonPageResult<InsNewsCm> listByNewId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		String newId = request.getParameter("newId");
		List<InsNewsCm> insNewsCms = insNewsCmManager.getByNewId(newId);
		return new JsonPageResult<InsNewsCm>(insNewsCms, queryFilter.getPage().getTotalItems());
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
        InsNewsCm insNewsCm=null;
        if(StringUtils.isNotBlank(pkId)){
           insNewsCm=insNewsCmManager.get(pkId);
        }else{
        	insNewsCm=new InsNewsCm();
        }
        return getPathView(request).addObject("insNewsCm",insNewsCm);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	InsNewsCm insNewsCm=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		insNewsCm=insNewsCmManager.get(pkId);
    		if("true".equals(forCopy)){
    			insNewsCm.setCommId(null);
    		}
    	}else{
    		insNewsCm=new InsNewsCm();
    	}
    	return getPathView(request).addObject("insNewsCm",insNewsCm);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return insNewsCmManager;
	}

}
