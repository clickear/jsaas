/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.redxun.sys.core.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.JoinType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.entity.BaseEntity;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.query.Page;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.QueryParam;
import com.redxun.saweb.config.upload.FileExtCategory;
import com.redxun.saweb.config.upload.FileUploadConfig;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseController;
import com.redxun.saweb.factory.BaseEntityManagerFactory;
import com.redxun.saweb.manager.BaseEntityManager;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.manager.SysFileManager;
import com.redxun.sys.core.manager.SysTreeManager;

/**
 * 附件文件管理控制器
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/core/sysFile/")
public class SysFileController extends BaseController{
    @Resource
    SysFileManager sysFileManager;
    @Resource
    BaseEntityManagerFactory baseEntityManagerFactory;
    
    @Resource
    FileUploadConfig fileUploadConfig;
    
    @Resource
    SysTreeManager sysTreeManager;
    
    /**
     * 取得我的文件
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("myFiles")
    @ResponseBody
    public JsonPageResult myFiles(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
    	queryFilter.addFieldParam("createBy", ContextUtil.getCurrentUserId());
    	queryFilter.addFieldParam("tenantId",ContextUtil.getCurrentTenantId());
    	
    	String treeId=request.getParameter("treeId");
    	if(StringUtils.isNotEmpty(treeId) && !("0".equals(treeId))){
    		SysTree sysTree=sysTreeManager.get(treeId);
    		queryFilter.addLeftLikeFieldParam("sysTree.path", sysTree.getPath());
    	}
    	List<SysFile> files=sysFileManager.getAll(queryFilter);
    	return new JsonPageResult(files,queryFilter.getPage().getTotalItems());
    }
    
    /**
     * 跳到文件变更目录的页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("moveFolder")
    public ModelAndView moveFolder(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String fileId=request.getParameter("fileId");
    	String fileIds=request.getParameter("fileIds");
    	ModelAndView mv=getPathView(request);
    	if(StringUtils.isNotEmpty(fileId)){
    		SysFile sysFile=sysFileManager.get(fileId);
    		mv.addObject("sysFile",sysFile);
    	}else{
    		mv.addObject("fileIds",fileIds);
    	}
    	return mv;
    }
    /**
     * 保存目录
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("saveFolder")
    @ResponseBody
    public JsonResult saveFolder(HttpServletRequest request) throws Exception{
    	String treeId=request.getParameter("treeId");
    	String fileId=request.getParameter("fileId");
    	String fileIds=request.getParameter("fileIds");
    	SysTree sysTree=sysTreeManager.get(treeId);
    	if(StringUtils.isNotEmpty(fileId)){
	    	SysFile sysFile=sysFileManager.get(fileId);
	    	sysFile.setSysTree(sysTree);
	    	sysFileManager.update(sysFile);
    	}else{
    		String[] fIds=fileIds.split("[,]");
    		for(String fId:fIds){
    			SysFile sysFile=sysFileManager.get(fId);
    			sysFile.setSysTree(sysTree);
    	    	sysFileManager.update(sysFile);
    		}
    	}
    	return new JsonResult(true,"成功保存");
    }
    
    /**
     * 显示预览记录
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("preview")
    public ModelAndView preview(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String recordId=request.getParameter("recordId");
        String fileId=request.getParameter("fileId");
        String entityName=request.getParameter("entityName");
        BaseEntityManager manager=baseEntityManagerFactory.getBaseEntityManager(entityName);
        SysFile sysFile=sysFileManager.get(fileId);
        BaseEntity entity=manager.get(recordId);
        return getPathView(request).addObject("entity",entity).addObject("sysFile",sysFile);
    }
    /**
     * 显示文件明细
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("get")
    public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String fileId=request.getParameter("fileId");
    	SysFile sysFile=sysFileManager.get(fileId);
    	return getPathView(request).addObject("sysFile",sysFile);
    }
    
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String fileId=request.getParameter("fileId");
        SysFile sysFile=sysFileManager.get(fileId);
        String fullPath=WebAppUtil.getUploadPath()+"/" + sysFile.getPath();
        String thumbnailPath=WebAppUtil.getAppAbsolutePath()+"/" + sysFile.getThumbnail();
        File thumbFile=new File(thumbnailPath);
        File file=new File(fullPath);
        
        if(thumbFile.exists()){
        	thumbFile.delete();
        }
        if(file.exists()){
            file.delete();
            sysFileManager.delete(fileId);
        }
        return new JsonResult(true,"成功删除！");
    }
    /**
     * 删除应用级别的图片文件
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("delIconFiles")
    @ResponseBody
    public JsonResult delIconFiles(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	  String fileId=request.getParameter("fileId");
    	  String fileIds=request.getParameter("fileIds");
    	  
    	  if(StringUtils.isNotEmpty(fileId)){
    		  removeIconFile(fileId);
    	  }else{
    		  String[] ids=fileIds.split("[,]");
    		  for(String id:ids){
    			  removeIconFile(id);
    		  }
    	  }
          return new JsonResult(true,"成功删除！");
    }
    
    @RequestMapping("delImageFile")
    @ResponseBody
    public JsonResult delImageFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String fileId=request.getParameter("fileId");
    	SysFile sysFile=sysFileManager.get(fileId);
    	String fullPath=null;
    	 String thumbnailPath=null;
    	if(SysFile.FROM_APP.equals(sysFile.getFrom())){
    		fullPath=WebAppUtil.getUploadPath()+"/" + sysFile.getPath();
            thumbnailPath=WebAppUtil.getUploadPath()+"/" + sysFile.getThumbnail();
    	}else{
    		fullPath=WebAppUtil.getUploadPath()+"/" + sysFile.getPath();
            thumbnailPath=WebAppUtil.getAppAbsolutePath()+"/" + sysFile.getThumbnail();
    	}
    	 File thumbFile=new File(thumbnailPath);
         File file=new File(fullPath);
         if(thumbFile.exists()){
        	 thumbFile.delete();
         }
         if(file.exists()){
        	 file.delete();
         }
         sysFileManager.delete(fileId);
    	 return new JsonResult(true,"成功删除！");
    }
    /**
     * 通过文件ID删除图标文件
     * @param fileId
     */
    private void removeIconFile(String fileId){
    	  SysFile sysFile=sysFileManager.get(fileId);
          String fullPath=WebAppUtil.getAppAbsolutePath()+"/" + sysFile.getPath();
          String thumbnailPath=WebAppUtil.getAppAbsolutePath()+"/" + sysFile.getThumbnail();
          File thumbFile=new File(thumbnailPath);
          File file=new File(fullPath);
          
          if(thumbFile.exists()){
          	thumbFile.delete();
          }
          if(file.exists()){
              file.delete();
          }
          sysFileManager.delete(fileId);
    }
    
    /**
     * 应用级别的图片文件的预览
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("imgPreview")
    public ModelAndView imgPreview(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String fileId=request.getParameter("fileId");
    	SysFile sysFile=sysFileManager.get(fileId);
    	return getPathView(request).addObject("sysFile",sysFile);
    }
    
    /**
     * 显示单条记录中对应的附件列表页
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("recordList")
    public ModelAndView recordList(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String recordId=request.getParameter("recordId");
        String entityName=request.getParameter("entityName");
        BaseEntityManager manager=baseEntityManagerFactory.getBaseEntityManager(entityName);
        BaseEntity entity=manager.get(recordId);
        return getPathView(request).addObject("entity",entity);
    }
    
    /**
     * 显示列表
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("recordListData")
    @ResponseBody
    public JsonPageResult recordListData(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String recordId=request.getParameter("recordId");
        String entityName=request.getParameter("entityName");
        String fileName=request.getParameter("fileName");
        String ext=request.getParameter("ext");

        QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
        queryFilter.addFieldParam("sysModule.entityName",JoinType.LEFT,QueryParam.OP_EQUAL,entityName);
        queryFilter.addFieldParam("recordId", recordId);

        if(StringUtils.isNotEmpty(fileName)){
            queryFilter.addFieldParam("fileName", QueryParam.OP_LIKE,fileName);
        }
        
        if(StringUtils.isNotEmpty(ext)){
            queryFilter.addFieldParam("ext", ext);
        }
        
        List <SysFile> dataList=sysFileManager.getAll(queryFilter);
        
        return new JsonPageResult(dataList, queryFilter.getPage().getTotalItems());
    }
    
    @RequestMapping("dialog")
    public ModelAndView dialog(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String types=request.getParameter("types");
        //表示上传的文件存在于Web应用下
        String from=request.getParameter("from");
        String onlyOne = request.getParameter("onlyOne");
        Map<String,String> typeMap=new HashMap<String, String>();
        if(StringUtils.isNotEmpty(types)){
           String[]fileTypes=types.split("[|]");
           for(String type:fileTypes){
               String exts=fileUploadConfig.getAllowExts(type);
               if(StringUtils.isNotEmpty(exts)){
                   typeMap.put(type,exts);
               }
           }
        }else{
            Map<String,FileExtCategory> catMaps=fileUploadConfig.getFileExtCatMap();
            Iterator<String> catKeyIt=catMaps.keySet().iterator();
            while(catKeyIt.hasNext()){
                String key=catKeyIt.next();
                String allowTypes=fileUploadConfig.getAllowExts(key);
                typeMap.put(key, allowTypes);
            }
        }
        return getPathView(request).addObject("typeMap",typeMap).addObject("from",from).addObject("types",types).addObject("onlyOne", onlyOne);
    }
    
    @RequestMapping("webUploader")
    public ModelAndView webUploader(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	JSONObject config=RequestUtil.getJSONObject(request, "config");
    	String types=config.getString("types");//上传文件类型
        //表示上传的文件存在于Web应用下
        String from=config.getString("from");
        String onlyOne = config.getString("onlyOne");
        StringBuffer typeMap=new StringBuffer();
        if(StringUtils.isNotEmpty(types)){
           String[] fileTypes=types.split(",");
           for(String type:fileTypes){
               String exts=fileUploadConfig.getAllowExts(type);
               if(StringUtils.isNotEmpty(exts)){
            	   typeMap.append(exts+",");
               }
           }
        }else{
            Map<String,FileExtCategory> catMaps=fileUploadConfig.getFileExtCatMap();
            Iterator<String> catKeyIt=catMaps.keySet().iterator();
            while(catKeyIt.hasNext()){
                String key=catKeyIt.next();
                String allowTypes=fileUploadConfig.getAllowExts(key);
                if(StringUtils.isNotEmpty(allowTypes)){
                	typeMap.append(allowTypes+",");
                }
            }
        }
        if(typeMap.length()>0){
        	typeMap.deleteCharAt(typeMap.length()-1);
        }
        return getPathView(request).addObject("typeMap",typeMap.toString()).addObject("from",from).addObject("types",types).addObject("onlyOne", onlyOne).addObject("config", config);
    }
    
    /**
     * 显示上传后的应用级的图片文件
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("appImages")
    public ModelAndView appImages(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	//来源
    	String from=request.getParameter("from");
    	//是否单一
    	String single=request.getParameter("single");
    	
    	if(StringUtils.isEmpty(from)){
    		from=SysFile.FROM_SELF;
    	}
    	
    	List<SysFile> imageFiles=sysFileManager.getImagesByFrom(from, ContextUtil.getCurrentUserId());
    	
    	return this.getPathView(request).addObject("imageFiles",imageFiles)
    			.addObject("single",single).addObject("from",from);
    }
    
    /**
     * 显示上传后的应用级的图片文件
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("appIcons")
    public ModelAndView appIcons(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	Page page=QueryFilterBuilder.createPage(request);
    	if(page.getPageSize()==Page.DEFAULT_PAGE_SIZE){
    		//每页100个图标
    		page.setPageSize(100);
    	}
    	List<SysFile> imageFiles=sysFileManager.getAppIcons(page);
    	return this.getPathView(request).addObject("imageFiles",imageFiles).addObject("page",page);
    }
    
}
