
package com.redxun.wx.core.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.HttpClientUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.context.CurrentContext;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.manager.SysFileManager;
import com.redxun.sys.core.util.SysPropertiesUtil;
import com.redxun.wx.core.entity.WxMeterial;
import com.redxun.wx.core.manager.WxMeterialManager;
import com.redxun.wx.core.manager.WxPubAppManager;
import com.redxun.wx.pub.util.PublicApi;
import com.redxun.wx.pub.util.PublicApiUrl;

/**
 * 微信素材控制器
 * @author ray
 */
@Controller
@RequestMapping("/wx/core/wxMeterial/")
public class WxMeterialController extends BaseMybatisListController{
    @Resource
    WxMeterialManager wxMeterialManager;
    @Resource
	CurrentContext currentContext;
    @Resource
    SysFileManager sysFileManager;
    @Resource
    WxPubAppManager wxPubAppManager;
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=RequestUtil.getString(request, "ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
            	WxMeterial wxMeterial=wxMeterialManager.get(id);
            	String pubId=wxMeterial.getPubId();
            	String accessToken=wxPubAppManager.getAccessToken(pubId);
            	String mediaId=wxMeterial.getMediaId();
            	String delRtnCode=PublicApi.deleteMeterial(mediaId, accessToken);//调用接口删除微信后台素材
                wxMeterialManager.delete(id);
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
        WxMeterial wxMeterial=null;
        if(StringUtils.isNotEmpty(pkId)){
           wxMeterial=wxMeterialManager.get(pkId);
           String msgType=wxMeterial.getMediaType();
           if("article".equals(msgType)){//如果是图文则把原来editor的内容取出来
        	   JSONObject jsonObject=JSONObject.parseObject(wxMeterial.getArtConfig());
        	   wxMeterial.setArtConfig(jsonObject.getString("ueditorContent"));
           }else if("multiArticle".equals(msgType)){
        	   
           }else{
        	   JSONObject jsonObject=JSONObject.parseObject(wxMeterial.getArtConfig());
        	   wxMeterial.setArtConfig(jsonObject.getString("fileUpload"));
           }
          
           
        }else{
        	wxMeterial=new WxMeterial();
        }
        return getPathView(request).addObject("wxMeterial",wxMeterial);
    }
    @RequestMapping("openMeterial")
    public ModelAndView openMeterial(HttpServletRequest request,HttpServletResponse response){
    	String mediaId=RequestUtil.getString(request, "mediaId");
    	WxMeterial wxMeterial=wxMeterialManager.getByMediaId(mediaId);
    	JSONObject jsonObject=JSONObject.parseObject(wxMeterial.getArtConfig());
    	wxMeterial.setArtConfig(jsonObject.getString("ueditorContent"));
    	ModelAndView modelAndView=new ModelAndView("/wx/core/wxMeterialGet.jsp");
    	modelAndView.addObject("wxMeterial", wxMeterial);
    	return modelAndView;
    }
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	String pubId=RequestUtil.getString(request, "pubId");
    	WxMeterial wxMeterial=null;
    	ModelAndView rtnView=getPathView(request);
    	if(StringUtils.isNotEmpty(pkId)){
    		wxMeterial=wxMeterialManager.get(pkId);
    		JSONObject artConfig=JSONObject.parseObject(wxMeterial.getArtConfig());
    		if(wxMeterial.getMediaType().equals("article")){
    			String ueditorConfig=artConfig.getString("ueditorContent");
        		JSONArray jsonArray=(JSONArray) artConfig.get("articles");
        		JSONObject object=(JSONObject) jsonArray.get(0);
        		object.remove("content");
        		artConfig.remove("ueditorContent");
        		rtnView.addObject("ueditorContent", ueditorConfig);
    		}
    		
    		//JSONObject jsonObject=(JSONObject) artConfig.get("ueditorContent");//(JSONObject) jsonArray.get(0);
    		//rtnView.addObject("content", jsonObject.toString());
    		wxMeterial.setArtConfig(artConfig.toString().replace("\\n",""));
    		
    	}else{
    		wxMeterial=new WxMeterial();
    		wxMeterial.setPubId(pubId);
    		rtnView.addObject("", "");
    	}
    	rtnView.addObject("wxMeterial",wxMeterial);;
    	return rtnView;
    }

    
	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return wxMeterialManager;
	}

	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		String pubId=RequestUtil.getString(request, "pubId");
		return this.getPathView(request).addObject("pubId", pubId);
	}
	@RequestMapping("listAll")
	@ResponseBody
	public JsonPageResult<WxMeterial> listAll(HttpServletRequest request,HttpServletResponse response){
		String pubId=RequestUtil.getString(request, "pubId");
		String tenantId=ContextUtil.getCurrentTenantId();
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("PUB_ID_", pubId);
		queryFilter.addFieldParam("TENANT_ID_", tenantId);
		List<WxMeterial> wxMeterials=wxMeterialManager.getAll(queryFilter);
		JsonPageResult<WxMeterial> jsonPageResult=new JsonPageResult<WxMeterial>(wxMeterials, queryFilter.getPage().getTotalItems());
		return jsonPageResult;
		
	}
	
	@RequestMapping("listAllByType")
	@ResponseBody
	public JsonPageResult<WxMeterial> listAllByType(HttpServletRequest request,HttpServletResponse response){
		String pubId=RequestUtil.getString(request, "pubId");
		String type=RequestUtil.getString(request, "type");
		String tenantId=ContextUtil.getCurrentTenantId();
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("PUB_ID_", pubId);
		queryFilter.addFieldParam("TENANT_ID_", tenantId);
		queryFilter.addFieldParam("TERM_TYPE_", "1");
		queryFilter.addFieldParam("MEDIA_TYPE_", type);
		List<WxMeterial> wxMeterials=wxMeterialManager.getAll(queryFilter);
		JsonPageResult<WxMeterial> jsonPageResult=new JsonPageResult<WxMeterial>(wxMeterials, queryFilter.getPage().getTotalItems());
		return jsonPageResult;
		
	}
	
	
	@RequestMapping("submitMeterial")
	@ResponseBody
	public JSONObject submitMeterial(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");//原实体主键
		String termType=RequestUtil.getString(request, "termType");//时限类型
		String mediaType=RequestUtil.getString(request, "mediaType");//素材类型
		String name=RequestUtil.getString(request, "name");//素材名
		String pubId=RequestUtil.getString(request, "pubId");//公众号Id
		String fileUpload=RequestUtil.getString(request, "fileUpload");//普通素材路径sysfileId
		String fileUploadText=RequestUtil.getString(request, "fileUploadText");//普通素材中的文字
		String thumb_media=RequestUtil.getString(request, "thumb_media");//图文素材标题sysfileId
		String title=RequestUtil.getString(request, "title");//标题
		String content=RequestUtil.getString(request, "content");//图文素材内容
		String thumb_mediaText=RequestUtil.getString(request, "thumb_mediaText");//图文配置中的标题(文字)
		String mediaId=RequestUtil.getString(request, "mediaId");//
		String tenantId=ContextUtil.getCurrentTenantId();
		String contextPath=request.getContextPath();
		String access_token=wxPubAppManager.getAccessToken(pubId);
		

		String rtnCode;//素材上传返回标志
		
		JSONObject artConfig=new JSONObject();//图文素材json
		String sid=IdUtil.getId();//素材实体的ID,与全文链接中的pkId匹配
		if("article".equals(mediaType)){//如果是图文消息
			String wxContent=translateToWxContent(content,access_token);//经过转换的内容,里面的img地址都是微信后台的url
			
			String titleImgCodeJson=uploadOtherMeterialToWeChat(thumb_media, access_token, "thumb");
			JSONObject titleJson=JSONObject.parseObject(titleImgCodeJson);
			artConfig.put("thumb_url", titleJson.getString("url"));
			JSONObject object=new JSONObject();//单个图文消息
			object.put("title", title);
			object.put("thumb_media_id", titleJson.get("media_id"));
			object.put("show_cover_pic", 0);//是否显示封面
			object.put("content", wxContent);
			object.put("digest", "jsaas");//关键字
			object.put("author", "红迅软件");
			object.put("content_source_url", SysPropertiesUtil.getTenantProperty("publicAddress", tenantId)+contextPath+"/wx/core/wxMeterial/get.do?pkId="+sid);
			JSONArray jsonArray=new JSONArray();
			jsonArray.add(object);
			JSONObject rtnJson=new JSONObject();
			rtnJson.put("articles", jsonArray);
			if(StringUtil.isNotEmpty(id)){//如果为修改
				JSONObject updateObject=new JSONObject();
				updateObject.put("media_id", mediaId);
				updateObject.put("index", 0);
				updateObject.put("articles", object);
				String updateCode=PublicApi.updateMeterial(access_token,updateObject);
				JSONObject updateRtn=JSONObject.parseObject(updateCode);
				String errcode=updateRtn.getString("errcode");
				if("0".equals(errcode)){
					rtnCode="{\"media_id\":\""+mediaId+"\"}";
				}else{
					rtnCode=null;//error
				}
			}else{
				rtnCode=uploadArticleMeterialToWeChat(access_token, rtnJson.toString());//将数据上传
			}
			
			
			
			artConfig.put("articles", jsonArray);//将图文素材配置同步到实体的属性中
			artConfig.put("ueditorContent", content);
			artConfig.put("thumb_mediaText", thumb_mediaText);
			artConfig.put("thumb_media", thumb_media);
		}else{
			artConfig.put("fileUploadText", fileUploadText);
			artConfig.put("fileUpload", fileUpload);
			if("1".equals(termType)){
				rtnCode=uploadOtherMeterialToWeChat(fileUpload, access_token, mediaType);
			}else{
				rtnCode=uploadOtherTempMeterialToWeChat(fileUpload, access_token, mediaType);
			}
			
		}
		
		
		JSONObject jsonObject=new JSONObject();
		if(JSONObject.parseObject(rtnCode).containsKey("media_id")){
			jsonObject.put("success", true);
			
			String returnMediaId=JSONObject.parseObject(rtnCode).getString("media_id");
			WxMeterial wxMeterial;
			if(StringUtil.isNotEmpty(id)){//如果为修改
				wxMeterial=wxMeterialManager.get(id);
			}else{
				wxMeterial=new WxMeterial();
				wxMeterial.setId(sid);
			}
			wxMeterial.setName(name);
			wxMeterial.setPubId(pubId);
			wxMeterial.setTermType(termType);
			wxMeterial.setMediaType(mediaType);
			wxMeterial.setMediaId(returnMediaId);
			wxMeterial.setArtConfig(artConfig.toString());
			wxMeterial.setTenantId(tenantId);
			if(StringUtil.isNotEmpty(id)){//如果为修改
				wxMeterialManager.update(wxMeterial);
			}else{
				wxMeterialManager.create(wxMeterial);
			}
		}else{
			jsonObject.put("success", false);
		}
		
		return jsonObject;
	}
	
	
	/**
	 * 上传其他永久素材至微信后端获取mediaId和相应的url
	 * @param sysFileId
	 * @param token
	 * @param type
	 * @return
	 * @throws IOException
	 */
	public String uploadOtherMeterialToWeChat(String sysFileId,String token,String type) throws IOException{
		SysFile sysFile=sysFileManager.get(sysFileId);
		String path=WebAppUtil.getUploadPath()+ "/" +sysFile.getPath();
		Map<String, String> map=getFolderAndNameFromFilePath(path);
		String folder=map.get("folder");
		String fileName=map.get("name");
		return PublicApi.uploadOtherMeterial(folder, fileName, token, type);
	}
	
	/**
	 * 上传其他临时素材至微信后端获取mediaId和相应的url
	 * @param sysFileId
	 * @param token
	 * @param type
	 * @return
	 * @throws IOException
	 */
	public String uploadOtherTempMeterialToWeChat(String sysFileId,String token,String type) throws IOException{
		SysFile sysFile=sysFileManager.get(sysFileId);
		String path=WebAppUtil.getUploadPath()+ "/" +sysFile.getPath();
		Map<String, String> map=getFolderAndNameFromFilePath(path);
		String folder=map.get("folder");
		String fileName=map.get("name");
		return PublicApi.uploadOtherTempMeterial(folder, fileName, token, type);
	}
	
	@RequestMapping("getMediaIdContent")
	@ResponseBody
	public JSONObject getMediaIdContent(HttpServletRequest request,HttpServletResponse response){
		String mediaId=RequestUtil.getString(request, "mediaId");
		WxMeterial wxMeterial=wxMeterialManager.getByMediaId(mediaId);
		JSONObject jsonObject=JSONObject.parseObject(wxMeterial.getArtConfig());
		String rtnString=jsonObject.getString("ueditorContent");
		return jsonObject;
		
	}
	
	
	
	/**
	 * 上传图文消息
	 * data的json结构是
		{
		  "articles": [{
		       "title": TITLE,
		       "thumb_media_id": THUMB_MEDIA_ID,
		       "author": AUTHOR,
		       "digest": DIGEST,
		       "show_cover_pic": SHOW_COVER_PIC(0 / 1),
		       "content": CONTENT,
		       "content_source_url": CONTENT_SOURCE_URL
		    },
		    //若新增的是多图文素材，则此处应还有几段articles结构
		 ]
		}
	 * @param access_token
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String uploadArticleMeterialToWeChat(String access_token,String data) throws Exception{
		return HttpClientUtil.postJson(PublicApiUrl.getForeverMaterial(access_token), data);
	}
	
	/**
	 * 将图文内容中的图片上传至微信后台
	 * @param path
	 * @param accessToken
	 * @return
	 * @throws IOException
	 */
	public String uploadArticleImgToWeChat(String path,String accessToken) throws IOException{
		Map<String, String> map=getFolderAndNameFromFilePath(path);
		String folder=map.get("folder");
		String fileName=map.get("name");
		return PublicApi.uploadArticleImg(folder, fileName, accessToken);
	}
	/**
	 * 将ueditor内容中的本地图片转换成微信图片
	 * 返回一份替换过的content
	 * @throws IOException 
	 */
	public String translateToWxContent(String content,String accessToken) throws IOException{
		Document document=Jsoup.parse(content);
 		org.jsoup.select.Elements elements=document.select("img[src]");
		for (Element element : elements) {
	        String imgUrl = element.attr("src");
	        String[] urlArray=imgUrl.split("fileId=");
	        String fileId=urlArray[1];
	        SysFile sysFile=sysFileManager.get(fileId);	        
	        String path=WebAppUtil.getUploadPath()+ "/" +sysFile.getPath();
	        String rtn=uploadArticleImgToWeChat(path, accessToken);
	        JSONObject jsonObject=JSONObject.parseObject(rtn);
	        element.attr("src", jsonObject.getString("url"));
	      }
		Element element=document.getElementsByTag("body").first();
		return element.toString();
	}
	
	@RequestMapping("uploadImgToGetUrl")
	@ResponseBody
	public String uploadImgToGetUrl(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String fileId=RequestUtil.getString(request, "fileId");
		String pubId=RequestUtil.getString(request, "pubId");
		String accessToken=wxPubAppManager.getAccessToken(pubId);
		SysFile sysFile=sysFileManager.get(fileId);	        
		
        String path=WebAppUtil.getUploadPath()+ "/" +sysFile.getPath();
        String rtn=uploadArticleImgToWeChat(path, accessToken);
        JSONObject jsonObject=JSONObject.parseObject(rtn);
        return jsonObject.getString("url");
	}
	
	/**
	 * 通过完整的路径返回文件夹的路径和文件名(含后缀)
	 * @param path
	 * @return
	 */
	public Map<String, String> getFolderAndNameFromFilePath(String path){
		Map<String, String> map=new HashMap<String, String>();
		String[] array=path.split("/");
		String fileName=array[array.length-1];
		int num=path.indexOf(fileName);
		String folder=path.substring(0, num-1);
		map.put("name", fileName);
		map.put("folder", folder);
		return map;
	}
	
	
}
