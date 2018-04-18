
package com.redxun.wx.core.controller;

import java.util.ArrayList;
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
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.core.util.SysPropertiesUtil;
import com.redxun.wx.core.entity.WxPubApp;
import com.redxun.wx.core.entity.WxTag;
import com.redxun.wx.core.manager.WxPubAppManager;
import com.redxun.wx.pub.util.PublicApi;
import com.redxun.wx.pub.util.TagUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 公众号管理控制器
 * @author ray
 */
@Controller
@RequestMapping("/wx/core/wxPubApp/")
public class WxPubAppController extends BaseMybatisListController{
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
                wxPubAppManager.delete(id);
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
        WxPubApp wxPubApp=null;
        if(StringUtils.isNotEmpty(pkId)){
           wxPubApp=wxPubAppManager.get(pkId);
        }else{
        	wxPubApp=new WxPubApp();
        }
        return getPathView(request).addObject("wxPubApp",wxPubApp);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	String tenantId=ContextUtil.getCurrentTenantId();
    	String publicAddress=SysPropertiesUtil.getTenantProperty("publicAddress", tenantId);
    	WxPubApp wxPubApp=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		wxPubApp=wxPubAppManager.get(pkId);
    	}else{
    		wxPubApp=new WxPubApp();
    	}
    	return getPathView(request).addObject("wxPubApp",wxPubApp).addObject("publicAddress", publicAddress);
    }
    
    @RequestMapping("panel")
    public ModelAndView panel(HttpServletRequest request,HttpServletResponse response){
    	String tenantId=ContextUtil.getCurrentTenantId();
    	List<WxPubApp> wxPubApps=wxPubAppManager.getAllByTenantId(tenantId);
    	JSONArray jsonArray=new JSONArray();
    	for (WxPubApp wxPubApp : wxPubApps) {
    		JSONObject pubObj=new JSONObject();
    		pubObj.put("id", wxPubApp.getId());
    		pubObj.put("name", wxPubApp.getName());
			jsonArray.add(pubObj);
		}
    	return this.getPathView(request).addObject("jsonArray", jsonArray);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return wxPubAppManager;
	}
	/**
	 * 返回菜单配置页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("menu")
	public ModelAndView menu(HttpServletRequest request,HttpServletResponse response){
		String pubId=RequestUtil.getString(request, "pubId");
		WxPubApp wxPubApp=wxPubAppManager.get(pubId);
		return this.getPathView(request).addObject("wxPubApp",wxPubApp);
	}
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		Boolean isSuperAdmin=ContextUtil.getCurrentUser().isSuperAdmin();
		return this.getPathView(request).addObject("isSuperAdmin", isSuperAdmin);
	}
	
	@RequestMapping("getPubTag")
	@ResponseBody
	public JsonPageResult<WxTag> getPubTag(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pubId=RequestUtil.getString(request, "pubId");
		String filter=RequestUtil.getString(request, "filter");
		String name=RequestUtil.getString(request, "name");
		int pageSize=RequestUtil.getInt(request, "pageSize");
		int pageIndex=RequestUtil.getInt(request, "pageIndex");
		
		String access_token=wxPubAppManager.getAccessToken(pubId);
		String json=TagUtil.getTag(access_token);
		JSONObject jsonObject=JSONObject.fromObject(json);//parseObject(json);
		
		JSONArray tags=(JSONArray) jsonObject.get("tags");
		List<WxTag> wxTags=(List<WxTag>) JSONArray.toCollection(tags, WxTag.class);
		int wxTagSize=wxTags.size();
		//处理过滤器搜索
		if("YES".equals(filter)){
			for (int i = 0; i < wxTags.size(); i++) {
				if(!(wxTags.get(i).getName().contains(name))){
					wxTags.remove(i);
					i--;
				}
			}
		}else {
			int end =(pageIndex+1)*pageSize>wxTags.size()?wxTags.size():(pageIndex+1)*pageSize;
			wxTags=wxTags.subList(pageIndex*pageSize,end);
		}
		JsonPageResult<WxTag> jsonPageResult=new JsonPageResult<WxTag>(wxTags, wxTagSize);
		return jsonPageResult;
		
		
	}
	
	@RequestMapping("listPubTag")
	@ResponseBody
	public List<WxTag> listPubTag(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pubId=RequestUtil.getString(request, "pubId");
		return TagUtil.getCacheTags(pubId);
		
	}
	
	
	/**
	 * 添加/编辑一个tag
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addTag")
	@ResponseBody
	public JSONObject addTag(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pubId=RequestUtil.getString(request, "pubId");
		String tagName=RequestUtil.getString(request, "tagName");
		String id=RequestUtil.getString(request, "id");
		String access_token=wxPubAppManager.getAccessToken(pubId);
		String rtnCode;
		WxTag tag=new WxTag();
		if(StringUtils.isNotBlank(id)){
			rtnCode=TagUtil.editTag(access_token, Integer.parseInt(id), tagName);//更新tag
			tag.setId(id);
			tag.setName(tagName);
			TagUtil.editCacheTag(pubId, tag);//修改缓存
		}else{
			tag.setId(id);
			tag.setName(tagName);
			rtnCode=TagUtil.addTag(access_token, tagName);//创建tag
			JSONObject rtnObj=JSONObject.fromObject(rtnCode);
			id=rtnObj.getJSONObject("tag").getString("id");
			tag.setId(id);
			TagUtil.addCacheTag(pubId, tag);//添加缓存
		}
		
		JSONObject jsonObject=JSONObject.fromObject(rtnCode);
		return jsonObject;
	}
	
	@RequestMapping("deleteTags")
	@ResponseBody
	public JsonResult deleteTags(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pubId=RequestUtil.getString(request, "pubId");
		
		String access_token=wxPubAppManager.getAccessToken(pubId);
		
		String uId=RequestUtil.getString(request, "ids");
		JsonResult jsonResult=new JsonResult();
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                String rtnCode=TagUtil.deleteTag(access_token, Integer.parseInt(id));
                TagUtil.deleteCacheTag(pubId, id);//删除缓存tag
                JSONObject rtnCodeJson=JSONObject.fromObject(rtnCode);
                if(!(rtnCodeJson.getInt("errcode")==0)){
                	jsonResult.setSuccess(false);
                	return jsonResult;
                }
            }
        }
        jsonResult.setSuccess(true);
    	return jsonResult;
	}
	
	@RequestMapping("getMenus")
	@ResponseBody
	public List<JSONObject> getMenus(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pubId=RequestUtil.getString(request, "pubId");
		WxPubApp wxPubApp=wxPubAppManager.get(pubId);
		List<JSONObject> list=wxPubAppManager.parseMenus(wxPubApp.getMenuConfig());
		return list;
	}
	
	@RequestMapping("myList")
	@ResponseBody
	public JsonPageResult<WxPubApp> myList(HttpServletRequest request,HttpServletResponse response){
		String tenantId=ContextUtil.getCurrentTenantId();
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("tenantId", tenantId);
		List<WxPubApp> wxPubApps=wxPubAppManager.getAll(queryFilter);
		JsonPageResult<WxPubApp> jsonPageResult=new JsonPageResult<WxPubApp>(wxPubApps, queryFilter.getPage().getTotalItems());
		return jsonPageResult;
		}
	
	@RequestMapping("createMenu")
	@ResponseBody
	public JSONObject createMenu(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String menus=RequestUtil.getString(request, "menus");
		String pubId=RequestUtil.getString(request, "pubId");
		Boolean submit=RequestUtil.getBoolean(request, "submit");
		JSONArray menuArray=JSONArray.fromObject(menus);
		List<JSONObject> baseMenuList=new ArrayList<JSONObject>();
		for (int i = 0; i <menuArray.size(); i++) {
			JSONObject menuObj=JSONObject.fromObject(menuArray.get(i));
			String menuType=menuObj.getString("type");
			if("sub".equals(menuType)){//如果有子菜单
				JSONArray subMenu=menuObj.getJSONArray("children");//取出子菜单
				List<JSONObject> subList=new ArrayList<JSONObject>();
				for(int j=0;j<subMenu.size();j++){
					JSONObject subObj=JSONObject.fromObject(subMenu.get(j));
					subList.add(subObj);
				}
				menuObj.put("sub_button", subList);
			}else{//如果没有子菜单
				
			}
			baseMenuList.add(menuObj);
		}
		JSONObject postObject=new JSONObject();
		postObject.put("button", baseMenuList);
		
		WxPubApp wxPubApp=wxPubAppManager.get(pubId);
		wxPubApp.setMenuConfig(postObject.toString());
		wxPubAppManager.update(wxPubApp);
		if(submit){//生成 
			String accessToken=wxPubAppManager.getAccessToken(pubId);
			JSONObject rtnObject=JSONObject.fromObject(PublicApi.createMenu(accessToken, postObject.toString()));
			//dealWithRtnObj
			System.out.println(1);
		}
		JSONObject jsonObject=new JSONObject();
		
		return jsonObject;
	}
	
	@RequestMapping("welcome")
	public ModelAndView welcome(HttpServletRequest request,HttpServletResponse response){
		String pubId=RequestUtil.getString(request, "pubId");
		WxPubApp wxPubApp=wxPubAppManager.get(pubId);
		return getPathView(request).addObject("wxPubApp", wxPubApp);
	}

	/**
	 * 保存回复的配置
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveReply")
	@ResponseBody
	public JSONObject saveReply(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pubId=RequestUtil.getString(request, "pubId");
		String form=RequestUtil.getString(request, "form");
		JSONObject formObj=JSONObject.fromObject(form);//前台传来的数据
		JSONObject saveObj=new JSONObject();//保存到数据库的字段
		String replyType=formObj.getString("replyType");
		saveObj.put("replyType", replyType);
		if("text".equals(replyType)){
			String replyText=formObj.getString("replyText");
			saveObj.put("replyText", replyText);
		}else if("image".equals(replyType)||"voice".equals(replyType)||"video".equals(replyType)||"article".equals(replyType)){
			String meterialText=formObj.getString("meterialText");
			String meterial=formObj.getString("meterial");
			saveObj.put("meterialText", meterialText);
			saveObj.put("meterial", meterial);
		}else if("music".equals(replyType)){
			String replyMusic=formObj.getString("replyMusic");
			String meterialText=formObj.getString("meterialText");
			String meterial=formObj.getString("meterial");
			saveObj.put("meterialText", meterialText);
			saveObj.put("meterial", meterial);
			saveObj.put("replyMusic", replyMusic);
		}
		WxPubApp wxPubApp=wxPubAppManager.get(pubId);
		wxPubApp.setOtherConfig(saveObj.toString());
		wxPubAppManager.update(wxPubApp);
		JSONObject rtnObj=new JSONObject();
		rtnObj.put("success", true);
		return rtnObj;
	}
}
