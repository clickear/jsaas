package com.redxun.offdoc.core.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.FileUtil;
import com.redxun.offdoc.core.entity.OdDocTemplate;
import com.redxun.offdoc.core.manager.OdDocTemplateManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.entity.SysTreeCat;
import com.redxun.sys.core.manager.SysTreeCatManager;
import com.redxun.sys.core.manager.SysTreeManager;

/**
 * [OdDocTemplate]管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/offdoc/core/odDocTemplate/")
public class OdDocTemplateController extends TenantListController{
    @Resource
    OdDocTemplateManager odDocTemplateManager;
    @Resource
    SysTreeManager sysTreeManager;
    @Resource
    private SysTreeCatManager sysTreeCatManager;
   
    @Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		QueryFilter filter=super.getQueryFilter(request);
  		//查找分类下的模型
  		String treeId=request.getParameter("treeId");
  		if(StringUtils.isNotEmpty(treeId)){
  			SysTree sysTree=sysTreeManager.get(treeId);
  			/*if(sysTree!=null){
  				filter.addLeftLikeFieldParam("sysTree.path", sysTree.getPath());
  			}*/
  			
  			filter.addFieldParam("sysTree.path",  sysTree.getPath());//("sysTree.path", sysTree.getPath());
  		}
  		return filter;
		/*return QueryFilterBuilder.createQueryFilter(request);*/
	}
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                odDocTemplateManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除！");
    }
    
    @RequestMapping("getByTempType")
    @ResponseBody
    public List<OdDocTemplate> getByTempType(HttpServletRequest request,HttpServletResponse response){
    	String tempType=request.getParameter("tempType");
    	if("1".equals(tempType)){
    		tempType="套红分类";
    	}
    	QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
    	queryFilter.addFieldParam("tempType", tempType);
    	List<OdDocTemplate> docTemplates=odDocTemplateManager.getAll(queryFilter);
    	return docTemplates;
    }
    
    /**
     * List页面
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("list")
    public ModelAndView List(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	//String tempType=request.getParameter("tempType");
       // return new ModelAndView("offdoc/core/odDocTemplateList.jsp").addObject("tempType", tempType);//.addObject(attributeName, attributeValue);
        return getPathView(request);//.addObject("odDocTemplate",odDocTemplate);
    }
    /**
     * 套红模板
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("redprintingList")
    public ModelAndView redprintingList(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String tempType=request.getParameter("tempType");
    	SysTreeCat cat=sysTreeCatManager.getByKey(tempType, ContextUtil.getCurrentTenantId());
        return new ModelAndView("offdoc/core/odDocTemplateList.jsp").addObject("catKey", tempType).addObject("name", cat==null?"":cat.getName());//.addObject(attributeName, attributeValue);
    }
    /**
     * 发文模板
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("dispatchList")
    public ModelAndView dispatchList(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String tempType=request.getParameter("tempType");
    	SysTreeCat cat=sysTreeCatManager.getByKey(tempType, ContextUtil.getCurrentTenantId());
       return new ModelAndView("offdoc/core/odDocTemplateList.jsp").addObject("catKey", tempType).addObject("name", cat==null?"":cat.getName());//.addObject(attributeName, attributeValue);
    }
    /**
     * 收文模板
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("incomingList")
    public ModelAndView incomingList(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String tempType=request.getParameter("tempType");
        SysTreeCat cat=sysTreeCatManager.getByKey(tempType, ContextUtil.getCurrentTenantId());
        return new ModelAndView("offdoc/core/odDocTemplateList.jsp").addObject("catKey", tempType).addObject("name", cat==null?"":cat.getName());//.addObject(attributeName, attributeValue);
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
        OdDocTemplate odDocTemplate=null;
        if(StringUtils.isNotEmpty(pkId)){
           odDocTemplate=odDocTemplateManager.get(pkId);
        }else{
        	odDocTemplate=new OdDocTemplate();
        }
     
        return getPathView(request).addObject("odDocTemplate",odDocTemplate);
    }
    
    
    /**
     * 获取
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getDocFile")
    public void getDocFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String docPath=request.getParameter("docPath");
    	// 创建file对象
		File file = new File(WebAppUtil.getAppAbsolutePath()+"/WEB-INF/offdoc/"+docPath);
		// 设置response的编码方式
		response.setContentType("application/x-msdownload");
		// 写明要下载的文件的大小
		// response.setContentLength((int) file.length());
		//response.setHeader("Content-Disposition", "attachment;filename=" + sysFile.getFileName());

		FileUtil.downLoad(file, response);
    }
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	String parentId=request.getParameter("parentId");
    	//复制添加
    	OdDocTemplate odDocTemplate=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		odDocTemplate=odDocTemplateManager.get(pkId);
    	}else{
    		odDocTemplate=new OdDocTemplate();
    		odDocTemplate.setTreeId(parentId);	
    	}
    	return getPathView(request).addObject("odDocTemplate",odDocTemplate);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return odDocTemplateManager;
	}
	
	/**
	 * 当没有分类tree的时候右边加载一个空的grid防止报错
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("listBlank")
	@ResponseBody
	public List<OdDocTemplate> listBlank(HttpServletRequest request,HttpServletResponse response){
		return null;}

}
