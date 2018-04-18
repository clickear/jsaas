package com.redxun.sys.core.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.DocumentFormatRegistry;
import com.redxun.core.json.IJson;
import com.redxun.core.json.JsonResult;
import com.redxun.core.util.DateUtil;
import com.redxun.core.util.FileUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.config.upload.FileExt;
import com.redxun.saweb.config.upload.FileUploadConfig;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseController;
import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.ImageUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.entity.SysModule;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.manager.SysFileManager;
import com.redxun.sys.core.manager.SysModuleManager;
import com.redxun.sys.core.manager.SysOfficeManager;
import com.redxun.sys.core.manager.SysOfficeVerManager;
import com.redxun.sys.core.manager.SysTreeManager;
import com.redxun.sys.core.util.OpenOfficeUtil;
import com.redxun.sys.core.util.SysPropertiesUtil;
import com.redxun.sys.log.LogEnt;

/**
 * 文件上传及下载控件器
 * 
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn） 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@RequestMapping("/sys/core/file/")
@Controller
public class FileController extends BaseController {
	@Resource
	SysFileManager sysFileManager;
	
	@Resource
	SysTreeManager sysTreeManager;
	@Resource
	SysModuleManager sysModuleManager;
	@Resource
	FileUploadConfig fileUploadConfig;

	@Resource
	SysOfficeManager sysOfficeManager;
	@Resource
	SysOfficeVerManager sysOfficeVerManager;

	@Resource
	DocumentFormatRegistry documentFormatRegistry;
	@Resource(name = "iJsonLazy")
	IJson iJson;

	private static final int BUFFER_SIZE = 100 * 1024;
	
	/**
	 * 获得文件的绝对路径
	 * 
	 * @param sysFile
	 * @return
	 * @throws Exception 
	 */
	private String getFilePath(SysFile sysFile) throws Exception {
		String fullPath = null;
		if (SysFile.FROM_APP.equals(sysFile.getFrom()) && "Image".equals(sysFile.getMineType())) {
			fullPath = WebAppUtil.getAppAbsolutePath() + sysFile.getPath();
		} else if (SysFile.FROM_APP.equals(sysFile.getFrom()) && "Icon".equals(sysFile.getMineType())) {
			fullPath = WebAppUtil.getAppAbsolutePath() + sysFile.getPath();
		} else if (SysFile.FROM_ANONY.equals(sysFile.getFrom())) {// 匿名上传
			fullPath = WebAppUtil.getAnonymusUploadDir() + "/" + sysFile.getPath();
		} else {
			fullPath = WebAppUtil.getUploadPath() + "/" + sysFile.getPath();
		}
		return fullPath;
	}

	@RequestMapping("upload")
	@LogEnt(action = "upload", module = "系统内核", submodule = "文件")
	public void upload(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonResult jsonResult = new JsonResult();
		// 附件是针对哪一分类
		String sysTreeId = request.getParameter("sysTreeId");
		// 附件是针对哪一实体的
		String entityName = request.getParameter("entityName");
		// 附件针对哪一实体的记录行
		String recordId = request.getParameter("recordId");
		// 文件上传的来源，表示为应用级的附件还是个性化附件
		String from = request.getParameter("from");
		// 上传的文件类型
		String types = request.getParameter("types");
		// 是否为匿名上传，代表用户尚未登录
		String anonymus = request.getParameter("anonymus");

		Map<String, MultipartFile> files = request.getFileMap();
		Iterator<MultipartFile> it = files.values().iterator();

		String tempPath=DateUtil.formatDate(new Date(), "yyyyMM");
		SysModule sysModule = null;
		if (StringUtils.isNotEmpty(entityName)) {
			sysModule = sysModuleManager.getByEntityClass(entityName);
		}

		response.setContentType(" text/html");
		// 全路径
		String fullPath = null;
		// 上传的相对路径
		String relFilePath = null;
		if (SysFile.FROM_APP.equals(from) && "Image".equals(types)) {
			relFilePath = "/upload/images";
			fullPath = WebAppUtil.getAppAbsolutePath() + relFilePath;
		} else if (SysFile.FROM_APP.equals(from) && "Icon".equals(types)) {
			relFilePath = "/upload/icons";
			fullPath = WebAppUtil.getAppAbsolutePath() + relFilePath;
		} else if ("true".equals(anonymus)) {// 匿名上传
			from = SysFile.FROM_ANONY;
			relFilePath = tempPath;
			fullPath = WebAppUtil.getAnonymusUploadDir() + "/" + tempPath;
		} else {
			IUser curUser = ContextUtil.getCurrentUser();
			String account = curUser.getUserId();
			relFilePath = account + "/" + tempPath;
			fullPath = WebAppUtil.getUploadPath() + "/" + relFilePath;
		}

		File dirFile = new File(fullPath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		SysTree sysTree = null;
		if (StringUtils.isNotEmpty(sysTreeId)) {
			sysTree = sysTreeManager.get(sysTreeId);
		}
		List<SysFile> fileList = new ArrayList<SysFile>();

		while (it.hasNext()) {
			MultipartFile f = it.next();
			String oriFileName = f.getOriginalFilename();

			String extName = FileUtil.getFileExt(oriFileName);
			String fileId = IdUtil.getId();
			// 新文件名
			String newFileName = fileId + "." + extName;

			InputStream is = f.getInputStream();
			// 全路径
			String fileFullPath = fullPath + "/" + newFileName;
			FileUtil.writeFile(fileFullPath, is);
			String thumbnailPath = null;
			
			// 如果为图片，则生成图片的缩略图
			if ("Image".equals(types)) {
				thumbnailPath = ImageUtil.thumbnailImage(fileFullPath, 150, 150);
			}
			
			SysFile file = new SysFile();
			file.setFileId(fileId);
			file.setFileName(oriFileName);
			// 设置新的文件名
			file.setNewFname(newFileName);

			file.setSysTree(sysTree);
			file.setPath(relFilePath + "/" + newFileName);
			// 若存在缩略图，则设置相对路径
			if (thumbnailPath != null) {
				file.setThumbnail(relFilePath + "/" + thumbnailPath);
			}
			// 设置其来源
			file.setFrom(from);
			file.setExt(extName);
			file.setSysModule(sysModule);
			file.setRecordId(recordId);
			// 设置上传文件的MINE TYPE
			if (StringUtils.isEmpty(types)) {
				FileExt fileExt = fileUploadConfig.getFileExtMap().get(extName.toLowerCase());
				if (fileExt != null) {
					file.setMineType(fileExt.getMineType());
				} else {
					file.setMineType("Unkown");
				}
			} else {
				file.setMineType(types);
			}
			file.setDesc("");
			file.setTotalBytes(f.getSize());
			file.setDelStatus("undeleted");
			sysFileManager.create(file);
			//转换OFFICE
			convertOffice(file);
		
			fileList.add(file);
		}
		jsonResult.setSuccess(true);
		jsonResult.setData(fileList);
		jsonResult.setMessage("成功上传!");

		response.getWriter().print(iJson.toJson(jsonResult));
	}
		
	private void convertOffice(SysFile file) throws Exception{
		String openoffice=SysPropertiesUtil.getGlobalProperty("openoffice");
		if (!"YES".equals(openoffice)) return;
		String ext=file.getExt();
		if (("doc".equalsIgnoreCase(ext)||"docx".equalsIgnoreCase(ext)||"pptx".equalsIgnoreCase(ext)
				||"ppt".equalsIgnoreCase(ext)||"xlsx".equalsIgnoreCase(ext)||"xls".equalsIgnoreCase(ext))){
			//用消息队列处理doc转换成pdf
			OpenOfficeUtil.que2CoverOffice(file);
		}
	}
	
	
	/**
	 * 下载单个文件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("downloadOne")
	public void downloadOne(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileId = request.getParameter("fileId");
		download(response,fileId,false);
	}
	
	
	@RequestMapping("download/{fileId}")
	public void downloadOne(HttpServletResponse response,@PathVariable("fileId") String fileId) throws Exception {
		download(response,fileId,true);
	}
	
	
	
	private void download(HttpServletResponse response,String fileId,boolean transPdf) throws Exception{
		SysFile sysFile = sysFileManager.get(fileId);
		// 创建file对象
		String path=getFilePath(sysFile);
		
		String fileName=sysFile.getFileName();
		
		String ext=sysFile.getExt();
		//是否允许openoffice 转换。
		String enableOpenOffice=SysPropertiesUtil.getGlobalProperty("openoffice");
	
		if(("docx".equals(ext)||"doc".equals(ext)||"pptx".equals(ext)||"ppt".equals(ext)||"xlsx".equals(ext)||"xls".equals(ext)) && "YES".equals(enableOpenOffice) ){
			//判断对应的pdf是否存在
			String pdfPath=path.replace("."+ext, ".pdf");
			File fileTemp = new File(pdfPath);
			if(!fileTemp.exists()){
				try{
					OpenOfficeUtil.coverFromOffice2Pdf(path, pdfPath);
					path=pdfPath;
					fileName=fileName.replace("."+ext, ".pdf");
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}else{
				path=pdfPath;
				fileName=fileName.replace("."+ext, ".pdf");
			}
		}
		
		File file = new File(path);
		fileName = URLEncoder.encode(fileName,"UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=" +fileName);
		
		FileUtil.downLoad(file,response);
		
	}
	
	

	@RequestMapping("previewImage")
	public void previewImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileId = request.getParameter("fileId");
		SysFile sysFile = sysFileManager.get(fileId);
		// 创建file对象
		File file = new File(getFilePath(sysFile));
		// 设置response的编码方式
		response.setContentType("image/" + sysFile.getExt());
		response.addHeader("Content-Disposition", "attachment; filename=\"" + sysFile.getFileName() + "\"");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		
		FileUtil.downLoad(file,response);
	}

	/**
	 * 用于显示单图片上传
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("imageView")
	public void imageView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 文件ID
		String fileId = request.getParameter("fileId");
		// 是否为缩略图，true表示展示缩略图，可不传
		String thumb = request.getParameter("thumb");
		// 用于在明细页中展示图片，可不传
		String view = request.getParameter("view");

		SysFile sysFile = sysFileManager.get(fileId);
		String filePath = null;

		// 文件来用户上传
		if (sysFile != null) {
			String basePath = null;
			if (sysFile.getFrom().equals(SysFile.FROM_APP)) {
				basePath = WebAppUtil.getAppAbsolutePath();
			} else if (sysFile.getFrom().equals(SysFile.FROM_ANONY)) {
				basePath = WebAppUtil.getAnonymusUploadDir();
			} else {
				basePath = WebAppUtil.getUploadPath();
			}
			// 展示缩略图
			if ("true".equals(thumb)) {
				filePath = basePath + "/" + sysFile.getThumbnail();
			} else {
				filePath = basePath + "/" + sysFile.getPath();
			}

			// 设置response的编码方式
			response.setContentType("image/" + sysFile.getExt());
		} else {
			if ("true".equals(view)) {
				// 为系统预设的上传图标
				filePath = WebAppUtil.getAppAbsolutePath() + "/styles/images/no-photo.png";
			} else {
				// 为系统预设的上传图标
				filePath = WebAppUtil.getAppAbsolutePath() + "/styles/images/upload-file.png";
			}
			// 设置response的编码方式
			response.setContentType("image/PNG");
		}
		// 创建file对象
		File file = new File(filePath);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		FileUtil.downLoad(file,response);
	}

	@RequestMapping("previewXml")
	public void previewXml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileId = request.getParameter("fileId");
		SysFile sysFile = sysFileManager.get(fileId);

		response.setContentType("text/xml;");
		File file= new File(WebAppUtil.getUploadPath() + "/" + sysFile.getPath());
		
		FileUtil.downLoad(file,response);
	}

	@RequestMapping("previewHtml")
	public void previewHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileId = request.getParameter("fileId");
		SysFile sysFile = sysFileManager.get(fileId);
		response.setContentType("text/html;");
		
		File file =new File(WebAppUtil.getUploadPath() + "/" + sysFile.getPath());
		FileUtil.downLoad(file,response);
	}

	@RequestMapping("previewFile")
	public void previewFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileId = request.getParameter("fileId");
		SysFile sysFile = sysFileManager.get(fileId);
		if (sysFile == null)
			return;
		if ("Document".equals(sysFile.getMineType())) {
			// previewDocument(request, response);
			downloadOne(request, response);
		} else if ("Xml".equals(sysFile.getMineType())) {
			previewXml(request, response);
		} else if ("Image".equals(sysFile.getMineType()) || "Icon".equals(sysFile.getMineType())) {
			previewImage(request, response);
		} else {
			downloadOne(request, response);
		}
	}

	@RequestMapping("previewDocument")
	public void previewDocument(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileId = request.getParameter("fileId");
		SysFile sysFile = sysFileManager.get(fileId);
		// 创建file对象
		File file = new File(WebAppUtil.getUploadPath() + "/" + sysFile.getPath());
		InputStream is = new FileInputStream(file);
		DocumentFormat inputFormat = documentFormatRegistry.getFormatByFileExtension(sysFile.getExt());
		DocumentFormat outputFormat = documentFormatRegistry.getFormatByFileExtension("pdf");
//		documentConverter.convert(is, inputFormat, response.getOutputStream(), outputFormat);
	}

	/**
	 * 下载某个记录的多个文件
	 * 
	 * @param request
	 * @param reponse
	 * @throws Exception
	 */
	@RequestMapping("downloadRecordFiles")
	public void downloadRecordFiles(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String recordId = request.getParameter("recordId");
		String entityName = request.getParameter("entityName");

		List<SysFile> files = sysFileManager.getByModelNameRecordId(entityName, recordId);
		ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
		response.setContentType("application/zip");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + recordId + ".zip\"");
		for (SysFile sysFile : files) {
			File file = new File(WebAppUtil.getUploadPath() + "/" + sysFile.getPath());
			FileUtil.zipFile(file, zipOutputStream);
		}
		zipOutputStream.close();
	}

	/**
	 * 下载我的上传的附件
	 * 
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("downloadFiles")
	public void downloadFiles(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileIds = request.getParameter("fileIds");
		if (StringUtils.isEmpty(fileIds)) {
			return;
		}

		ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
		response.setContentType("application/zip");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String downFileName = "AttachFile-" + sdf.format(new Date());
		String[] fIds = fileIds.split("[,]");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + downFileName + ".zip\"");
		for (String fId : fIds) {
			SysFile sysFile = sysFileManager.get(fId);
			if (sysFile == null)
				continue;

			File file = new File(getFilePath(sysFile));
			FileUtil.zipFile(file, zipOutputStream);

		}
		zipOutputStream.close();
	}

	@RequestMapping("previewOffice")
	public ModelAndView previewOffice(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileId = request.getParameter("fileId");
		String print = request.getParameter("print");
		SysFile file = sysFileManager.get(fileId);
		String type = "";
		String fileName = file.getFileName();
		String name = file.getFileName().split("\\.")[1];
		name = name.toLowerCase();
		if (StringUtils.isNotEmpty(name)) {
			if ("png".equals(name) || "jpg".equals(name) || "jpeg".equals(name) || "gif".equals(name) || "bmp".equals(name)) {
				return new ModelAndView("/sys/core/filePreviewPic.jsp").addObject("fileId", fileId);
			}else if("pdf".equals(name)){
				return new ModelAndView("sys/core/filePreviewPdf.jsp").addObject("fileId", fileId).addObject("fileName", fileName).addObject("print", print);
			}else{
				type = "." + name;
			}
		}
		return getPathView(request).addObject("fileId", fileId).addObject("type", type).addObject("print", print).addObject("fileName", fileName);
	}
	
	
	@RequestMapping("iconSelectDialog")
	public ModelAndView iconSelectDialog(HttpServletRequest request,HttpServletResponse response){
		List<String> matchList = new ArrayList<String>();
		String icon=FileUtil.readFile(WebAppUtil.getAppAbsolutePath()+"/styles/icons.css");
		Pattern regex = Pattern.compile("\\.(icon-[\\w-]*?):\\s*before\\s*\\{.*?\\}", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.DOTALL);
		Matcher regexMatcher = regex.matcher(icon);
			while (regexMatcher.find()) {
				matchList.add(regexMatcher.group(1));
			}
		return this.getPathView(request).addObject("matchList", matchList);
	}
	
	
	@RequestMapping("uploadFiles")
	@ResponseBody
	public JsonResult<JSONArray> uploadFiles(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, MultipartFile> files = request.getFileMap();
		JsonResult<JSONArray> result=new JsonResult<JSONArray>(true);
		
		return result;
	}

}
