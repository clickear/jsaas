package com.redxun.saweb.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.redxun.org.api.model.IUser;
import com.redxun.saweb.config.upload.FileExt;
import com.redxun.saweb.config.upload.FileUploadConfig;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.ImageUtil;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.manager.SysFileManager;
import com.redxun.sys.core.util.SysPropertiesUtil;
/**
 * UEditor的上传处理
 * @author mansan
 *
 */
@Controller
@RequestMapping("/ueditor")
public class UEditorController extends BaseController {
	@Resource
	SysFileManager sysFileManager;
	@Resource
	FileUploadConfig fileUploadConfig;
	
	@RequestMapping
	public String index() {
		return "/ueditor/index";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	@ResponseBody
	public void upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String action = request.getParameter("action");
		if ("config".equals(action)) {
			OutputStream os = response.getOutputStream();
			FileInputStream is=new FileInputStream(ResourceUtils.getFile("classpath:ueditor-config.json"));
			IOUtils.copy(is, os);
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> upload(HttpServletRequest request, @RequestParam CommonsMultipartFile upfile) throws Exception {
		String action=RequestUtil.getString(request, "action");
		Map<String, String> result = new HashMap<String, String>();
		
		String absolutePath = WebAppUtil.getUploadPath();
		File folder = new File(absolutePath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		//用戶
		IUser curUser= ContextUtil.getCurrentUser();
		String account = curUser.getUsername();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String tempPath = sdf.format(new Date());
		String relFilePath = account + "/" + tempPath;
	
		String rawName = upfile.getFileItem().getName();
		String fileExt = rawName.substring(rawName.lastIndexOf("."));
		String newFileName = System.currentTimeMillis() + UUID.randomUUID().toString() + fileExt;
		String folderPath=WebAppUtil.getUploadPath()+File.separator +relFilePath;
		//构造新的上传路径
		String fullPath=folderPath+ File.separator + newFileName; 
		
		File folderFile=new File(WebAppUtil.getUploadPath()+File.separator +relFilePath);
		if(!folderFile.exists()){
			folderFile.mkdirs();
		}
		
	
		
		SysFile sysFile = new SysFile();
		sysFile.setFileId(idGenerator.getSID());
		sysFile.setFileName(rawName);
		//设置新的文件名
		sysFile.setNewFname(newFileName);

		sysFile.setPath(relFilePath + "/" + newFileName);
		
		//设置其来源
		sysFile.setFrom(SysFile.FROM_UEDITOR);
		sysFile.setExt(fileExt);
		// 设置上传文件的MINE TYPE
		
		FileExt fileExtend = fileUploadConfig.getFileExtMap().get(fileExt.replace(".", "").toLowerCase());
		if (fileExtend != null) {
			sysFile.setMineType(fileExtend.getMineType());
		} else {
			sysFile.setMineType("Unkown");
		}
		sysFile.setTotalBytes(upfile.getSize());
		sysFile.setDelStatus("undeleted");
		sysFileManager.create(sysFile);
		//进行文件写入
		File saveFile = new File(fullPath);
		
		try {
			//uploadimage
			if("uploadimage".equals(action)){
				FileOutputStream out=new FileOutputStream(saveFile);
				Integer width= SysPropertiesUtil.getGlobalPropertyInt("ueditor_upload_img_maxsize");
				if(width==null) width=800;
				ImageUtil.thumbnailImage(upfile.getInputStream(), fileExt.replace(".", "").toLowerCase(), out, width,  false);
			}
			else{
				
				upfile.getFileItem().write(saveFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String state = "SUCCESS";
		
		//String path = getFilePath(upfile);
		File file = new File(fullPath);
		// 返回类型
		String rootPath = request.getContextPath();
		result.put("url", rootPath + "/sys/core/file/previewFile.do?fileId=" + sysFile.getFileId());
		result.put("size", String.valueOf(file.length()));
		result.put("title", sysFile.getFileName());
		result.put("text", sysFile.getFileName() );
		result.put("alt", sysFile.getFileName());
		result.put("type", file.getName().substring(file.getName().lastIndexOf(".")));
		result.put("state", state);
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public void show(String filePath, HttpServletResponse response) throws IOException {

		File file = getFile(filePath);

		response.setDateHeader("Expires", System.currentTimeMillis() + 1000 * 60 * 60 * 24);
		response.setHeader("Cache-Control", "max-age=60");
		OutputStream os = response.getOutputStream();

		FileInputStream is = null;
		try {
			is = new FileInputStream(file);
			IOUtils.copy(is, os);
		} catch (FileNotFoundException e) {
			response.setStatus(404);
			return;
		} finally {
			if (null != is) {
				is.close();
			}
			if (null != os) {
				os.flush();
				os.close();
			}
		}
	}

	protected String getFilePath(CommonsMultipartFile uploadFile) throws Exception {
		String absolutePath = WebAppUtil.getUeditorUploadPath();
		File folder = new File(absolutePath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		String rawName = uploadFile.getFileItem().getName();
		String fileExt = rawName.substring(rawName.lastIndexOf("."));
		String newName = System.currentTimeMillis() + UUID.randomUUID().toString() + fileExt;
		File saveFile = new File(absolutePath + File.separator + newName);
		try {
			uploadFile.getFileItem().write(saveFile);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return absolutePath + "/" + newName;
	}

	protected File getFile(String path) {
		File file = new File(path);
		return file;
	}

}
