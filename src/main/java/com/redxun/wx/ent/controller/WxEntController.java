package com.redxun.wx.ent.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.entity.KeyValEnt;
import com.redxun.core.util.CookieUitl;
import com.redxun.core.util.FileUtil;
import com.redxun.core.util.HttpClientUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.filter.SecurityUtil;
import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.ImageUtil;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.entity.SysInst;
import com.redxun.sys.core.manager.SysFileManager;
import com.redxun.sys.core.manager.SysInstManager;
import com.redxun.wx.ent.entity.WxEntAgent;
import com.redxun.wx.ent.entity.WxEntCorp;
import com.redxun.wx.ent.manager.WxEntAgentManager;
import com.redxun.wx.ent.manager.WxEntCorpManager;
import com.redxun.wx.ent.util.ApiUrl;
import com.redxun.wx.ent.util.IFileHandler;
import com.redxun.wx.ent.util.WeixinUtil;
import com.redxun.wx.util.TokenModel;
import com.redxun.wx.util.TokenUtil;

@Controller
public class WxEntController {
	
	@Resource
	WxEntAgentManager wxEntAgentManager;
	@Resource
	SysInstManager sysInstManager;
	@Resource
	WxEntCorpManager wxEntCorpManager;
	@Resource
	SysFileManager sysFileManager;
	
	/**
     * 企业微信自动登录。
     * <pre>
     * 页面跳转地址
     * http://www.redxun.cn/proxy/[应用ID].do?url=http://www.redxun.cn/weixin/index.do
     * 1.如果用户已经登录，那么跳转到指定的页面.
     * 2.根据传入的应用ID取得相关对象。
     * 3.获取微信传递过来的code参数。
     * 	1.如果参数为空则跳转到微信服务端获取code。
     * 	2.如果不为空则表示已经跳转到微信，微信传递过来了一个code。
     * 		1.根据这个code去微信端获取这个代表的具体用户。
     * 		2.让这个用户自动登录。
     * </pre>
     * @param request
     * @param response
     * @param agentId
     * @throws Exception
     */
    @RequestMapping("proxy/{agentId}")
    public void proxy(HttpServletRequest request,HttpServletResponse response,@PathVariable(value="agentId") String agentId) throws Exception{
    	
    	String url=RequestUtil.getString(request, "url");
    	IUser user=ContextUtil.getCurrentUser();
    	if(user!=null){
    		response.sendRedirect(url);
    		return;
    	}
    	WxEntAgent agent= wxEntAgentManager.get(agentId);
    	String code=RequestUtil.getString(request, "code");
    	
    	if(StringUtil.isEmpty(code)){
    		String weixinUrl=ApiUrl.getOAuthUrl(request, agent, url);
    		response.sendRedirect(weixinUrl);
    	}
    	else{
    		String json=WeixinUtil.getUserByCode(agent.getCorpId(), agent.getSecret(), code);
    		SysInst inst= sysInstManager.get(agent.getTenantId());
    		
    		JSONObject jsonObj=JSONObject.parseObject(json);
    		String userId=jsonObj.getString("UserId");
    		String account=userId + "@" + inst.getDomain();
    		
    		SecurityUtil.login(request,account, "", true);
    		SecurityUtil.writeRememberMeCookie(request,response,account);
    		//应用ID
    		CookieUitl.addCookie("agentId", agentId, true, request, response);
    		
    		response.sendRedirect(url);
    	}
       
    }
    
    /**
     * 初始化参数。
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("initConfig")
    @ResponseBody
    public Map<String,String> initConfig(HttpServletRequest request) throws Exception{
    	String url=RequestUtil.getString(request, "url");
    	String agentId=CookieUitl.getValueByName("agentId", request);
    	WxEntCorp corp=wxEntCorpManager.getByAgentId(agentId);
    	Map<String, String> map=WeixinUtil. getWxConfig( url, corp.getCorpId(),corp.getSecret());
    	return map;
    }
    
    private String getExtType(String contentType){
    	if(contentType.indexOf("jpeg")!=-1 || contentType.indexOf("jpg")!=-1){
    		return "jpg"; 
    	}
    	if(contentType.indexOf("png")!=-1 ){
    		return "png"; 
    	}
    	if(contentType.indexOf("gif")!=-1 ){
    		return "gif"; 
    	}
    	if(contentType.indexOf("bmp")!=-1 ){
    		return "bmp"; 
    	}
    	return "jpg";
    }
    
    private String getFullPath(){
    	String userId = ContextUtil.getCurrentUserId();
		
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String tempPath = sdf.format(new Date());
		
		String relFilePath = "/" + userId + "/" + tempPath +"/";
		
		return relFilePath;
    }
    
    @RequestMapping("wx/saveFile")
    @ResponseBody
    public KeyValEnt<String> saveFile(HttpServletRequest request) throws Exception{
    	String mediaId=RequestUtil.getString(request, "mediaId");
    	
    	String agentId=CookieUitl.getValueByName("agentId", request);
    	WxEntCorp agent=wxEntCorpManager.getByAgentId(agentId);
    	TokenModel tokenModel=TokenUtil.getEntToken(agent.getCorpId(), agent.getSecret());
    	String url=ApiUrl.getDownloadUrl(tokenModel.getToken(), mediaId);
    	
    	final KeyValEnt<String> kv=new KeyValEnt<String>();
    	
    	HttpClientUtil.downLoad(url, new IFileHandler() {
			@Override
			public void handInputStream(HttpResponse response, InputStream is) throws IOException {
				SysFile file = new SysFile();
				
				Header header= response.getFirstHeader("Content-Type");
				String type= header.getValue();
				
				Header lengthHeader= response.getFirstHeader("Content-Length");
				
				String ext=getExtType(type);
				String fileId=IdUtil.getId();
				
				//Content-Type: image/jpeg
				String fileName=fileId +"." +ext;
				
				kv.setKey(fileId);
				kv.setVal(fileName);
				
				
				file.setFileId(fileId);
				file.setFileName(fileName);
				// 设置新的文件名
				file.setNewFname(fileName);
				
				String relFilePath= getFullPath() ;
				
				String relFileName=relFilePath + fileName;
				
				String fullPath = WebAppUtil.getUploadPath() + relFileName.replace("/",File.separator);
				
				FileUtil.createFolder(fullPath, true);
				
				FileUtil.writeFile(fullPath, is);
				
				String thumbnailPath = ImageUtil.thumbnailImage(fullPath, 150, 150);
				file.setThumbnail(relFilePath + "/" + thumbnailPath);
				file.setPath(relFileName);
				// 设置其来源
				file.setFrom("weixin");
				file.setMineType("Image");
				file.setExt(ext);

				file.setDesc("");
				file.setTotalBytes(Long.parseLong(lengthHeader.getValue()));
				file.setDelStatus("undeleted");
				sysFileManager.create(file);
			}
		});
    	return kv;
    	
    }

}
