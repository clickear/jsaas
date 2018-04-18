
package com.redxun.wx.core.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.springframework.expression.spel.ast.OpNE;
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
import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.wx.core.entity.WxMessageSend;
import com.redxun.wx.core.entity.WxMeterial;
import com.redxun.wx.core.entity.WxPubApp;
import com.redxun.wx.core.entity.WxSubscribe;
import com.redxun.wx.core.entity.WxTag;
import com.redxun.wx.core.manager.WxMessageSendManager;
import com.redxun.wx.core.manager.WxMeterialManager;
import com.redxun.wx.core.manager.WxPubAppManager;
import com.redxun.wx.core.manager.WxSubscribeManager;
import com.redxun.wx.ent.util.ApiUrl;
import com.redxun.wx.pub.util.PublicApi;
import com.redxun.wx.pub.util.PublicApiUrl;
import com.redxun.wx.pub.util.TagUtil;
import com.redxun.wx.pub.util.WxCode;
import com.redxun.wx.util.TokenModel;
import com.redxun.wx.util.TokenUtil;

/**
 * WX_MESSAGE_SEND控制器
 * @author ray
 */
@Controller
@RequestMapping("/wx/core/wxMessageSend/")
public class WxMessageSendController extends BaseMybatisListController{
    @Resource
    WxMessageSendManager wxMessageSendManager;
    @Resource
    WxMeterialManager wxMeterialManager;
    @Resource
    WxPubAppManager wxPubAppManager;
    @Resource
    WxSubscribeManager wxSubscribeManager;
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
                wxMessageSendManager.delete(id);
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
        WxMessageSend wxMessageSend=null;
        if(StringUtils.isNotEmpty(pkId)){
           wxMessageSend=wxMessageSendManager.get(pkId);
           String msgType=wxMessageSend.getMsgType();
           String sendType=wxMessageSend.getSendType();
           String receiver=wxMessageSend.getReceiver();
           String recText="";//接收者中文
           if("openId".equals(sendType)){//如果是指定用户
        	   String[] openIds=receiver.split(",");
        	   for (int i = 0; i < openIds.length; i++) {
					WxSubscribe wxSubscribe=wxSubscribeManager.getByOpenId(openIds[i]);//关注者
					recText+=wxSubscribe.getNickName()+",";
        	   }
        	   recText=recText.substring(0, recText.length()-1);
        	   wxMessageSend.setReceiver(recText);
           }else if("tag".equals(sendType)){//如果是标签
        	   List<WxTag> wxTags=TagUtil.getCacheTags(wxMessageSend.getPubId());
        	   for (WxTag wxTag : wxTags) {
				if(receiver.equals(wxTag.getId())){
					wxMessageSend.setReceiver(wxTag.getName());//将标签名写入
				}
        	   }
           }else{
        	   wxMessageSend.setReceiver("");
           }
           wxMessageSend.setContent(translateContent(msgType, wxMessageSend.getContent()));
        }else{
        	wxMessageSend=new WxMessageSend();
        }
        return getPathView(request).addObject("wxMessageSend",wxMessageSend);
    }
    
    /**
     * 将发送消息里的mediaId转换成可视化的内容,如图文消息则将多条图文拆分成单条图文逗号分隔,其他消息返回fileId前台用以直接下载
     * @param msgType
     * @param mediaId
     * @return
     */
    public String translateContent(String msgType,String mediaId){
    	String rtnContent="";
    	if("article".equals(msgType)){//如果是单图文
    		rtnContent=mediaId;
    	}else if("multiArticle".equals(msgType)){//如果是多素材
    		WxMeterial wxMeterial=wxMeterialManager.getByMediaId(mediaId);
    		rtnContent=wxMeterial.getArtConfig();
    	}else if("image".equals(msgType)||"voice".equals(msgType)||"thumb".equals(msgType)||"video".equals(msgType)){
    		WxMeterial wxMeterial=wxMeterialManager.getByMediaId(mediaId);
    		String artConfig=wxMeterial.getArtConfig();
    		JSONObject jsonObject=JSONObject.parseObject(artConfig);
    		rtnContent=jsonObject.getString("fileUpload");
    	}
    	return rtnContent;
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	String pubId=RequestUtil.getString(request, "pubId");
    	WxMessageSend wxMessageSend=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		wxMessageSend=wxMessageSendManager.get(pubId);
    	}else{
    		wxMessageSend=new WxMessageSend();
    		wxMessageSend.setPubId(pubId);
    	}
    	return getPathView(request).addObject("wxMessageSend",wxMessageSend);
    }
    
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
    	String pkId=RequestUtil.getString(request, "pkId");
    	
    	return getPathView(request).addObject("pkId", pkId);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return wxMessageSendManager;
	}
	
	@RequestMapping("listAll")
	@ResponseBody
	public JsonPageResult<WxMessageSend> listAll(HttpServletRequest request,HttpServletResponse response){
		String pkId=RequestUtil.getString(request, "pkId");
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("PUB_ID_", pkId);
		List<WxMessageSend> wxMessageSends=wxMessageSendManager.getAll(queryFilter);
		JsonPageResult<WxMessageSend> jsonPageResult=new JsonPageResult<WxMessageSend>(wxMessageSends, queryFilter.getPage().getTotalItems());
		return jsonPageResult;
	}
	/**
	 * 使用素材发送消息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sendAtion")
	@ResponseBody
	public JSONObject sendAtion(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pubId=RequestUtil.getString(request, "pubId");
		String mediaId=RequestUtil.getString(request, "mediaId");
		String targetIds=RequestUtil.getString(request, "ids");
		String sendType;
		
		String accessToken=wxPubAppManager.getAccessToken(pubId);
		
		WxMeterial wxMeterial=wxMeterialManager.getByMediaId(mediaId);
		String msgType=wxMeterial.getMediaType();
		if(StringUtils.isBlank(targetIds)){//如果为空则直接发送消息
			sendType=WxMessageSend.EVERYONE;
			String rtnCode=PublicApi.sendMsgToTag(0, accessToken, true, mediaId, msgType);//发送全体,只发一条
			JSONObject jsonObject=JSONObject.parseObject(rtnCode);
			WxMessageSend wxMessageSend=new WxMessageSend();
			wxMessageSend.setID(idGenerator.getSID());
			wxMessageSend.setPubId(pubId);
			wxMessageSend.setMsgType(msgType);
			wxMessageSend.setSendType(sendType);
			wxMessageSend.setContent(mediaId);
			wxMessageSend.setReceiver("");
			wxMessageSendManager.create(wxMessageSend);
			jsonObject.put("success", true);
			return jsonObject;
		}
		String[] idArray=targetIds.split(",");
		List<String> openIds=new ArrayList<String>();
		for (int i = 0; i < idArray.length; i++) {
			String id=idArray[i];
			if(id.contains("#")){//如果是tagId+openId
				String openId=id.split("#")[1];
				openIds.add(openId);//将openId取出来加入到发送list的队列中一起提交发送,只发送一条
			}else{
				String tagRtnCode=PublicApi.sendMsgToTag(Integer.parseInt(id), accessToken, false, mediaId, msgType);
				//挨个给Tag发送消息,由于接口限制,tag只能一个,所以没选择一个tag就发送一条
				sendType=WxMessageSend.TAG;
				WxMessageSend wxMessageSend=new WxMessageSend();
				wxMessageSend.setID(idGenerator.getSID());
				wxMessageSend.setPubId(pubId);
				wxMessageSend.setMsgType(msgType);
				wxMessageSend.setSendType(sendType);
				wxMessageSend.setContent(mediaId);
				wxMessageSend.setReceiver(id);
				wxMessageSendManager.create(wxMessageSend);
			}
		}
		if(openIds.size()>0){//如果所选有openId则发送给指定人员
			String openIdsRtnCode=PublicApi.sendMsgToOpenId(openIds, msgType, mediaId, accessToken);
			sendType=WxMessageSend.OPENID;
			WxMessageSend wxMessageSend=new WxMessageSend();
			wxMessageSend.setID(idGenerator.getSID());
			wxMessageSend.setPubId(pubId);
			wxMessageSend.setMsgType(msgType);
			wxMessageSend.setSendType(sendType);
			wxMessageSend.setContent(mediaId);
			String receiver="";
			for (String string : openIds) {
				receiver+=string+",";
			}
			receiver=receiver.substring(0, receiver.length()-1);
			wxMessageSend.setReceiver(receiver);
			wxMessageSendManager.create(wxMessageSend);
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success", true);
		return jsonObject;
	}
	
	@RequestMapping("sendMsg")
	@ResponseBody
	public  JSONObject sendMsg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String receiver=RequestUtil.getString(request, "receiver");
		String receiveType=RequestUtil.getString(request, "receiveType");
		String mediaType=RequestUtil.getString(request, "mediaType");
		String pubId=RequestUtil.getString(request, "pubId");
		String meterial=RequestUtil.getString(request, "meterial");//mediaId
		String accessToken=wxPubAppManager.getAccessToken(pubId);
		if("article".equals(mediaType)){
			int multiOrNot=meterial.indexOf(",");
			if(multiOrNot!=-1){
				mediaType="multiArticle";
			}
			try {
				meterial=PublicApi.transformMediaIdsIntoMediaId(meterial, accessToken,pubId);//整合多个素材
			} catch (Exception e) {
				JSONObject object=new JSONObject();//整合出错临时返回
				object.put("success", false);
				object.put("errMsg",e.getMessage());
				return object;
			}
			
		}
		String rtnCode="";
		
		if ("everyone".equals(receiveType)) {// 发送给所有人
			rtnCode=PublicApi.sendMsgToTag(0, accessToken, true, meterial, mediaType);
		}else if("tag".equals(receiveType)){
			rtnCode=PublicApi.sendMsgToTag(Integer.parseInt(receiver), accessToken, false, meterial, mediaType);
		}else if("openId".equals(receiveType)){
			List<String> openIds=new ArrayList<String>();
			String[] opIds=receiver.split(",");
			for (int i = 0; i < opIds.length; i++) {
				openIds.add(opIds[i]);
			}
			rtnCode=PublicApi.sendMsgToOpenId(openIds, mediaType, meterial, accessToken);
		}
		
		JSONObject object=JSONObject.parseObject(rtnCode);
		Integer errcode=object.getInteger("errcode");
		JSONObject rtnObject=new JSONObject();//最终返回前台
		if(errcode!=null&&errcode!=0){//调用失败
			rtnObject.put("success", false);
			WxCode wxCode=new WxCode();
			String msg=wxCode.getTheCode(errcode);
			rtnObject.put("errMsg", msg);
		}else{
			rtnObject.put("success", true);
			errcode=0;
			WxMessageSend wxMessageSend=new WxMessageSend();
			wxMessageSend.setID(idGenerator.getSID());
			wxMessageSend.setPubId(pubId);
			wxMessageSend.setReceiver(receiver);
			wxMessageSend.setSendType(receiveType);
			wxMessageSend.setMsgType(mediaType);
			wxMessageSend.setContent(meterial);
			wxMessageSend.setTenantId(ContextUtil.getCurrentTenantId());
			wxMessageSend.setCreateTime(new Date());
			wxMessageSend.setSendState(errcode.toString());
			wxMessageSendManager.create(wxMessageSend);
		}
		return rtnObject;
	}
	

}
