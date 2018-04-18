package com.redxun.sys.core.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonResult;
import com.redxun.core.util.DateUtil;
import com.redxun.core.util.FileUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.entity.SysOffice;
import com.redxun.sys.core.entity.SysOfficeVer;
import com.redxun.sys.core.manager.SysFileManager;
import com.redxun.sys.core.manager.SysOfficeManager;
import com.redxun.sys.core.manager.SysOfficeVerManager;
import com.redxun.sys.log.LogEnt;

@RequestMapping("/sys/core/office/")
@Controller
public class SysOfficeController {
	
	@Resource
	SysFileManager sysFileManager;
	@Resource
	SysOfficeManager sysOfficeManager;
	@Resource
	SysOfficeVerManager sysOfficeVerManager;
	
	@RequestMapping("office/{officeId}")
	public void office(HttpServletResponse response,@PathVariable("officeId") String officeId) throws Exception {
		String fileId=sysOfficeManager.getFileIdByOfficeId(officeId);
		download(response, fileId);
	}
	
	@RequestMapping("download/{fileId}")
	public void download(HttpServletResponse response,@PathVariable("fileId") String fileId) throws Exception {
		SysFile sysFile=sysFileManager.get(fileId);
		String fullPath = WebAppUtil.getUploadPath() + "/" + sysFile.getPath();
		File file = new File(fullPath);
		response.setHeader("Content-Disposition", "attachment;filename=" +sysFile.getFileName());
		FileUtil.downLoad(file,response);
	}
	
	@RequestMapping("officever/{officeId}")
	@ResponseBody
	public JSONObject officever(HttpServletResponse response,@PathVariable("officeId") String officeId) throws Exception {
		SysOffice office=sysOfficeManager.get(officeId);
		JSONObject json=new JSONObject();
		if(SysOffice.SUPPORT_VER.equals(office.getSupportVersion())){
			JSONArray ary= sysOfficeManager.getVersByOfficeId(officeId);
			json.put("supportVer", true);
			json.put("vers", ary);
		}
		else{
			json.put("supportVer", false);
		}
		return json;
	}
	
	
	@RequestMapping("getUser")
	@ResponseBody
	public JSONObject getUser() throws Exception {
		IUser user=ContextUtil.getCurrentUser();
		JSONObject json=new JSONObject();
		json.put("name", user.getFullname());
		return json;
	}
	
	

	@RequestMapping("saveOffice")
	@LogEnt(action = "保存OFFICE文档,用于OFFICE表单控件上传", module = "系统内核", submodule = "文件")
	public void saveOffice(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonResult<String> jsonResult = new JsonResult<String>();
		String officeId = RequestUtil.getString(request, "officeId", "");
		// 上传的文件类型
		String type =RequestUtil.getString( request,"type","docx");
		String name=RequestUtil.getString(request, "name", "");
		boolean ver=RequestUtil.getBoolean(request, "ver", true);
		//新增情况
		// 1.支持版本
		// 添加 SYS_OFFICE, 插入SYS_FILE, 插入SYS_OFFICE_VAR
		// 2.不支持 一致。
		
		// 保存的情况
		// 1.支持版本
		// 	1.更新SYS_OFFICE , 2插入 SYS_FILE 3. 插入SYS_OFFICE_VAR
		// 2.不支持
		//  1.更新SYS_OFFICE， 更新 SYS_FILE
		boolean isAdd=StringUtil.isEmpty(officeId);
		
		SysFile file= saveFile(officeId,ver, request );
		
		if(isAdd){
			//sysoffice
			SysOffice sysOffice=new SysOffice();
			officeId=IdUtil.getId();
			sysOffice.setId(officeId);
			sysOffice.setName(name);
			sysOffice.setSupportVersion(ver?SysOffice.SUPPORT_VER:SysOffice.NOT_SUPPORT_VER);
			sysOffice.setVersion(1);
			sysOfficeManager.create(sysOffice);
			//sysofficever
			SysOfficeVer officeVer=new SysOfficeVer();
			officeVer.setId(IdUtil.getId());
			officeVer.setFileId(file.getFileId());
			officeVer.setOfficeId(officeId);
			officeVer.setVersion(1);
			officeVer.setFileName(file.getFileName());
			sysOfficeVerManager.create(officeVer);
		}
		else{
			SysOffice sysOffice= sysOfficeManager.get(officeId);
			if(SysOffice.SUPPORT_VER.equals(sysOffice.getSupportVersion())){
				//sys_office
				Integer maxVersion=sysOfficeManager.getVersionByOfficeId(officeId) +1;
				sysOffice.setVersion(maxVersion);
				sysOfficeManager.update(sysOffice);
				//版本
				SysOfficeVer officeVer=new SysOfficeVer();
				officeVer.setId(IdUtil.getId());
				officeVer.setFileId(file.getFileId());
				officeVer.setOfficeId(officeId);
				officeVer.setVersion(maxVersion);
				officeVer.setFileName(file.getFileName());
				sysOfficeVerManager.create(officeVer);
			}
			else{
				sysOfficeManager.update(sysOffice);
			}
		}
		
		jsonResult.setSuccess(true);
		jsonResult.setMessage("成功上传!");
		jsonResult.setData(officeId +"," +type  +"," + name);

		response.getWriter().print(jsonResult.toString());
		
	}
	
	/**
	 * 写入文件。
	 * @param officeId
	 * @param version
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private SysFile saveFile(String officeId,boolean version,MultipartHttpServletRequest request ) throws IOException{
		
		String type =RequestUtil.getString( request,"type","docx");
		Map<String, MultipartFile> files = request.getFileMap();
		Iterator<MultipartFile> it = files.values().iterator();
		
		boolean isAdd=false;
		if(version || StringUtil.isEmpty(officeId)){
			isAdd=true;
		}
		SysFile file=null;
		String relFilePath="";
		
		
		MultipartFile multipartFile=null;
		if (it.hasNext()) {
			multipartFile = it.next();
		}
		String fileId="";
		if(!isAdd){
			fileId=sysOfficeManager.getFileIdByOfficeId(officeId);
			file=sysFileManager.get(fileId);
			relFilePath = file.getPath();
		}
		else{
			String tempPath=DateUtil.formatDate(new Date(), "yyyyMM");
			// 上传的相对路径
			IUser curUser = ContextUtil.getCurrentUser();
			String userId = curUser.getUserId();
			fileId=IdUtil.getId();
			relFilePath = userId + "/" + tempPath + "/" +fileId +"." + type;
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
			file.setFileId(fileId);
			String newFileName=fileId +"." + type;
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
