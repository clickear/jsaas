
package com.redxun.sys.core.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.DateUtil;
import com.redxun.core.util.EncryptUtil;
import com.redxun.core.util.FileUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.entity.SysOffice;
import com.redxun.sys.core.entity.SysOfficeVer;
import com.redxun.sys.core.entity.SysStamp;
import com.redxun.sys.core.manager.SysFileManager;
import com.redxun.sys.core.manager.SysStampManager;
import com.redxun.sys.log.LogEnt;

/**
 * office印章控制器
 * @author ray
 */
@Controller
@RequestMapping("/sys/core/sysStamp/")
public class SysStampController extends BaseMybatisListController{
    @Resource
    SysStampManager sysStampManager;
    @Resource
    SysFileManager sysFileManager;
    
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    @LogEnt(action = "del", module = "sys", submodule = "office印章")
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=RequestUtil.getString(request, "ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                sysStampManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除!");
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
        String pkId=RequestUtil.getString(request, "pkId");
        SysStamp sysStamp=null;
        if(StringUtils.isNotEmpty(pkId)){
           sysStamp=sysStampManager.get(pkId);
        }else{
        	sysStamp=new SysStamp();
        }
        return getPathView(request).addObject("sysStamp",sysStamp);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	SysStamp sysStamp=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		sysStamp=sysStampManager.get(pkId);
		    String pwd= EncryptUtil.decrypt(sysStamp.getPassword());
            sysStamp.setPassword(pwd);
    	}else{
    		sysStamp=new SysStamp();
    	}
    	return getPathView(request).addObject("sysStamp",sysStamp);
    }
    
    /**
     * 有子表的情况下编辑明细的json
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("getJson")
    @ResponseBody
    public String getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String uId=RequestUtil.getString(request, "ids");    	
        SysStamp sysStamp = sysStampManager.getSysStamp(uId);
        String json = JSONObject.toJSONString(sysStamp);
        return json;
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return sysStampManager;
	}
	
	
	@RequestMapping("saveStamp")
	@LogEnt(action = "保存保存印章", module = "系统内核", submodule = "文件")
	public void saveStamp(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonResult<String> jsonResult = new JsonResult<String>();
		String stampId = RequestUtil.getString(request, "stampId", "");
		SysFile file= saveFile(stampId, request );
		jsonResult.setSuccess(true);
		jsonResult.setMessage("成功上传!");
		jsonResult.setData(file.getFileId() );

		response.getWriter().print(jsonResult.toString());
		
	}
	
	/**
	 * 保存印章。
	 * @param stampId
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private SysFile saveFile(String stampId,MultipartHttpServletRequest request ) throws IOException{
		
		String type =RequestUtil.getString( request,"type","esb");
		Map<String, MultipartFile> files = request.getFileMap();
		Iterator<MultipartFile> it = files.values().iterator();
		
		boolean isAdd=false;
		if(StringUtil.isEmpty(stampId)){
			isAdd=true;
		}
		SysFile file=null;
		String relFilePath="";
		
		
		MultipartFile multipartFile=null;
		if (it.hasNext()) {
			multipartFile = it.next();
		}
		
		if(!isAdd){
		
			file=sysFileManager.get(stampId);
			relFilePath = file.getPath();
		}
		else{
			String tempPath=DateUtil.formatDate(new Date(), "yyyyMM");
			// 上传的相对路径
			IUser curUser = ContextUtil.getCurrentUser();
			String userId = curUser.getUserId();
			stampId=IdUtil.getId();
			relFilePath = userId + "/" + tempPath + "/" +stampId +"." + type;
		}
		// 全路径
		String fullPath = WebAppUtil.getUploadPath() + "/" + relFilePath;
		String path = fullPath.substring(0, fullPath.lastIndexOf("/"));
		FileUtil.createFolder(path, false);
		
		Long fileSize= multipartFile.getSize();
		
		InputStream is = multipartFile.getInputStream();
		//写入文件
		FileUtil.writeFile(fullPath, is);
		if(isAdd){
			file=new SysFile();
			file.setFileId(stampId);
			String newFileName=stampId +"." + type;
			file.setFileName(newFileName);
			file.setNewFname(newFileName);
			file.setPath(relFilePath);
			file.setExt(type);
			file.setDesc("");
			file.setDelStatus("undeleted");
		}
		file.setTotalBytes(fileSize);
		if(isAdd){
			sysFileManager.create(file);
		}
		else{
			sysFileManager.update(file);
		}
		return file;
	}

}
