
package com.redxun.wx.core.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.ArrayUtil;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.wx.core.entity.WxPubApp;
import com.redxun.wx.core.entity.WxSubscribe;
import com.redxun.wx.core.entity.WxTag;
import com.redxun.wx.core.entity.WxTagAndSubscribe;
import com.redxun.wx.core.entity.WxTagUser;
import com.redxun.wx.core.manager.WxPubAppManager;
import com.redxun.wx.core.manager.WxSubscribeManager;
import com.redxun.wx.core.manager.WxTagUserManager;
import com.redxun.wx.pub.util.PublicApi;
import com.redxun.wx.pub.util.PublicApiUrl;
import com.redxun.wx.pub.util.TagUtil;
import com.redxun.wx.util.TokenModel;
import com.redxun.wx.util.TokenUtil;

/**
 * 微信关注者控制器
 * @author ray
 */
@Controller
@RequestMapping("/wx/core/wxSubscribe/")
public class WxSubscribeController extends BaseMybatisListController{
    @Resource
    WxSubscribeManager wxSubscribeManager;
    @Resource
    WxTagUserManager wxTagUserManager;
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
                wxSubscribeManager.delete(id);
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
        WxSubscribe wxSubscribe=null;
        if(StringUtils.isNotEmpty(pkId)){
           wxSubscribe=wxSubscribeManager.get(pkId);
        }else{
        	wxSubscribe=new WxSubscribe();
        }
        return getPathView(request).addObject("wxSubscribe",wxSubscribe);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	WxSubscribe wxSubscribe=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		wxSubscribe=wxSubscribeManager.get(pkId);
    	}else{
    		wxSubscribe=new WxSubscribe();
    	}
    	return getPathView(request).addObject("wxSubscribe",wxSubscribe);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return wxSubscribeManager;
	}
	
	@RequestMapping("listAll")
	@ResponseBody
	public JsonPageResult<WxSubscribe> listAll(HttpServletRequest request,HttpServletResponse response){
		String pubId=RequestUtil.getString(request, "pubId");
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("pubId", pubId);
		List<WxSubscribe> wxSubscribes=wxSubscribeManager.getAll(queryFilter);
		JsonPageResult<WxSubscribe> jsonPageResult=new JsonPageResult<WxSubscribe>(wxSubscribes, queryFilter.getPage().getTotalItems());
		return jsonPageResult;
	}
	
	@RequestMapping("listByTagId")
	@ResponseBody
	public JsonPageResult<WxSubscribe> listByTagId(HttpServletRequest request,HttpServletResponse response){
		String tagId=RequestUtil.getString(request, "tagId");
		String pubId=RequestUtil.getString(request, "pubId");
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addParam("tagId", tagId);
		queryFilter.addParam("pubId", pubId);
		List<WxSubscribe> wxSubscribes=wxSubscribeManager.getByTagId(queryFilter);
		JsonPageResult<WxSubscribe> jsonPageResult=new JsonPageResult<WxSubscribe>(wxSubscribes, queryFilter.getPage().getTotalItems());
		return jsonPageResult;
	}
	
	@RequestMapping("joinInTag")
	@ResponseBody
	public JsonResult joinInTag(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String useTag=RequestUtil.getString(request, "useTag");
		String pkId=RequestUtil.getString(request, "pkId");
		String pubId=RequestUtil.getString(request, "pubId");
		WxSubscribe wxSubscribe=wxSubscribeManager.get(pkId);//未加入标签前的关注者
		String tagList=wxSubscribe.getTagidList();
		String[] newTags=useTag.split(",");//新标签们
		String[] tagArray=tagList.substring(1, tagList.length()-1).split(",");//旧标签们
		List<String> addTag=new ArrayList<String>();//个人需要新增的tag
		List<String> deleteTag=new ArrayList<String>();//个人需要删除的tag
		distinguishArrayAtoB(newTags, tagArray, addTag);
		distinguishArrayAtoB(tagArray, newTags, deleteTag);
		
		String openId=wxSubscribe.getOpenId();
		WxPubApp wxPubApp=wxPubAppManager.get(pubId);
		String appId=wxPubApp.getAppId();
		String secret=wxPubApp.getSecret();
		TokenModel tokenModel=TokenUtil.getToken(appId, secret, false);
		String access_token=tokenModel.getToken();
		
		for (String string : addTag) {
			if(StringUtils.isNotBlank(string)){
				String rtnCode=TagUtil.addUserIntoTag(access_token, Integer.parseInt(string),openId );
				WxTagUser wxTagUser=new WxTagUser();
				wxTagUser.setId(idGenerator.getSID());
				wxTagUser.setPubId(pubId);
				wxTagUser.setTagId(string);
				wxTagUser.setUserId(pkId);
				wxTagUserManager.create(wxTagUser);
			}
		}
		for (String string : deleteTag) {
			if(StringUtils.isNotBlank(string)){
			String rtnCode=TagUtil.cancelUsersTag(access_token, openId, string);
			WxTagUser tagUser=wxTagUserManager.getByTagIdAndUserId(string, pkId);
			if(tagUser!=null){
				wxTagUserManager.deleteObject(tagUser);
			}
			}
		}	
		wxSubscribe.setTagidList("["+useTag+"]");
		wxSubscribeManager.saveOrUpdate(wxSubscribe);
		JsonResult jsonResult=new JsonResult();
		jsonResult.setSuccess(true);
	
		return jsonResult;
	}
	
	@RequestMapping("addInTag")
	public ModelAndView AddInTag(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pubId=RequestUtil.getString(request, "pubId");
		String pkId=RequestUtil.getString(request, "pkId");//openId
		WxSubscribe wxSubscribe=wxSubscribeManager.get(pkId);
		//wxSubscribe.getTagidList();
		
		String access_token=wxPubAppManager.getAccessToken(pubId);
		String tags=TagUtil.getUserTag(wxSubscribe.getOpenId(), access_token);
		JSONObject jsonObject=(JSONObject) JSONObject.parse(tags);
		String taglist=jsonObject.getString("tagid_list");
		taglist=taglist.substring(1,taglist.length()-1);
		return getPathView(request).addObject("pkId", pkId).addObject("pubId", pubId).addObject("tagIdList", taglist);
	}
	
	@RequestMapping("getTag")
	@ResponseBody
	public JSONArray getTag(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pubId=RequestUtil.getString(request, "pubId");
		String access_token=wxPubAppManager.getAccessToken(pubId);
		String json=TagUtil.getTag(access_token);
		JSONObject jsonObject=JSONObject.parseObject(json);//
		JSONArray tags=(JSONArray) jsonObject.get("tags");
		return tags;
	}
	@RequestMapping("getTagAndSubscribe")
	@ResponseBody
	public List<WxTagAndSubscribe> getTagAndSubscribe(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pubId=RequestUtil.getString(request, "pubId");
		String access_token=wxPubAppManager.getAccessToken(pubId);
		String json=TagUtil.getTag(access_token);
		JSONObject jsonObject=JSONObject.parseObject(json);
		List<WxTagAndSubscribe> wxTagAndSubscribes=new ArrayList<WxTagAndSubscribe>();                     
		JSONArray tags=(JSONArray) jsonObject.get("tags");
		
		for (Object object : tags) {
			JSONObject tag=JSONObject.parseObject(object.toString());
			String tagId=tag.getString("id");
			WxTagAndSubscribe wxTagAndSubscribe=new WxTagAndSubscribe();
			wxTagAndSubscribe.setId(tag.getString("id"));
			wxTagAndSubscribe.setName(tag.getString("name")+"(标签)");
			wxTagAndSubscribe.setParentId("0");
			wxTagAndSubscribe.setType("tag");
			wxTagAndSubscribes.add(wxTagAndSubscribe);
			QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
			queryFilter.addParam("pubId", pubId);
			queryFilter.addParam("tagId", tagId);
			List<WxSubscribe> wxSubscribes=wxSubscribeManager.getByTagId(queryFilter);
			for (WxSubscribe wxSubscribe : wxSubscribes) {
				WxTagAndSubscribe userInTag=new WxTagAndSubscribe();
				String openId=wxSubscribe.getOpenId();
				userInTag.setId(tagId+"#"+openId);
				userInTag.setName(wxSubscribe.getNickName());
				userInTag.setParentId(tagId);
				userInTag.setType("subscribe");
				wxTagAndSubscribes.add(userInTag);
			}
		} 
		return wxTagAndSubscribes;
		
	} 
	
	
	/**
	 * 将最新的标签同步到这个openId的用户
	 * @param access_token
	 * @param openId
	 * @throws Exception
	 */
	public void synchronizeUserTag(String access_token,String openId) throws Exception{
		String json=PublicApi.getUser(access_token, openId);
		JSONObject jsonObject=JSONObject.parseObject(json);
		WxSubscribe wxSubscribe=jsonObject.toJavaObject(WxSubscribe.class);
		WxSubscribe wxSubscribe2=wxSubscribeManager.getByOpenId(openId);
		wxSubscribe2.setTagidList(wxSubscribe.getTagidList());
		wxSubscribeManager.update(wxSubscribe2);
	}
	
	/**
	 * 从数组A中找出B数组中没有的元素,并把他们加入到临时列表中
	 * @param arrayA
	 * @param arrayB
	 * @param tempList
	 */
	public void  distinguishArrayAtoB(String[] arrayA,String[] arrayB,List<String> tempList){
		for (int i = 0; i < arrayA.length; i++) {
			boolean sameOrNot=false;
			for (int j = 0; j < arrayB.length; j++) {
				if(arrayA[i].equals(arrayB[j])){
					sameOrNot=true;
					break;
				}
			}
			if(!sameOrNot){
				//加入addTags
				tempList.add(arrayA[i]);
			}
		}
	}
	

}
