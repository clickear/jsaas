
package com.redxun.wx.core.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.redxun.core.query.QueryParam;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.core.util.SysPropertiesUtil;
import com.redxun.wx.core.entity.WxTicket;
import com.redxun.wx.core.manager.WxPubAppManager;
import com.redxun.wx.core.manager.WxTicketManager;
import com.redxun.wx.pub.util.PublicApi;
import com.redxun.wx.pub.util.WxCode;

/**
 * 微信卡券控制器
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/wx/core/wxTicket/")
public class WxTicketController extends BaseMybatisListController{
    @Resource
    WxTicketManager wxTicketManager;
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
                WxTicket wxTicket=wxTicketManager.get(id);
                String pubId=wxTicket.getPubId();
                String accessToken=wxPubAppManager.getAccessToken(pubId);
                PublicApi.deleteWxTiket(accessToken, id);
                wxTicketManager.delete(id);
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
        WxTicket wxTicket=null;
        if(StringUtils.isNotEmpty(pkId)){
           wxTicket=wxTicketManager.get(pkId);
        }else{
        	wxTicket=new WxTicket();
        }
        return getPathView(request).addObject("wxTicket",wxTicket);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	WxTicket wxTicket=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		wxTicket=wxTicketManager.get(pkId);
    		if("true".equals(forCopy)){
    			wxTicket.setId(null);
    		}
    	}else{
    		wxTicket=new WxTicket();
    	}
    	return getPathView(request).addObject("wxTicket",wxTicket);
    }
    
    @RequestMapping("create")
    public ModelAndView create(HttpServletRequest request,HttpServletResponse response){
    	String pubId=RequestUtil.getString(request, "pubId");
    	String cardId=RequestUtil.getString(request, "cardId");
    	WxTicket wxTicket=null;
    	if(StringUtils.isNotBlank(cardId)){
    		wxTicket=wxTicketManager.get(cardId);
    		String logoUrl=wxTicket.getLogoUrl();
    		logoUrl=logoUrl.replace("/", "\\/");
    		wxTicket.setLogoUrl(logoUrl);
    	}
    	return getPathView(request).addObject("pubId", pubId).addObject("cardId", cardId).addObject("wxTicket", wxTicket);
    }
    
    @RequestMapping("memberCardCreate")
    public ModelAndView memberCardCreate(HttpServletRequest request,HttpServletResponse response){
    	String pubId=RequestUtil.getString(request, "pubId");
    	String cardId=RequestUtil.getString(request, "cardId");
    	WxTicket wxTicket=null;
    	if(StringUtils.isNotBlank(cardId)){
    		wxTicket=wxTicketManager.get(cardId);
    		String logoUrl=wxTicket.getLogoUrl();
    		logoUrl=logoUrl.replace("/", "\\/");
    		wxTicket.setLogoUrl(logoUrl);
    	}
    	return getPathView(request).addObject("pubId", pubId).addObject("cardId", cardId).addObject("wxTicket", wxTicket);
    }
    
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
    	String pubId=RequestUtil.getString(request, "pubId");
    	return getPathView(request).addObject("pubId", pubId);
    }

    

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return wxTicketManager;
	}
	
	@RequestMapping("myList")
	@ResponseBody
	public JsonPageResult<WxTicket> myList(HttpServletRequest request,HttpServletResponse response){
		String pubId=RequestUtil.getString(request, "pubId");
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("PUB_ID_", pubId);
		queryFilter.addFieldParam("CARD_TYPE_",QueryParam.OP_NOT_EQUAL , "MEMBER_CARD");
		List<WxTicket> wxTickets=wxTicketManager.getAll(queryFilter);
		JsonPageResult<WxTicket> jsonPageResult=new JsonPageResult<WxTicket>(wxTickets, queryFilter.getPage().getTotalItems());
		return jsonPageResult;
	}
	
	@RequestMapping("myMemberCardList")
	@ResponseBody
	public JsonPageResult<WxTicket> myMemberCardList(HttpServletRequest request,HttpServletResponse response){
		String pubId=RequestUtil.getString(request, "pubId");
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("PUB_ID_", pubId);
		queryFilter.addFieldParam("CARD_TYPE_",QueryParam.OP_EQUAL , "MEMBER_CARD");
		List<WxTicket> wxTickets=wxTicketManager.getAll(queryFilter);
		JsonPageResult<WxTicket> jsonPageResult=new JsonPageResult<WxTicket>(wxTickets, queryFilter.getPage().getTotalItems());
		return jsonPageResult;
	}
	
	
	@RequestMapping("editTicket")
	@ResponseBody
	public JSONObject editTicket(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pubId=RequestUtil.getString(request, "pubId");
		String accessToken=wxPubAppManager.getAccessToken(pubId);
		String id=RequestUtil.getString(request, "id");
		String logoUrl=RequestUtil.getString(request, "logoUrl");
		String cardColor=RequestUtil.getString(request, "cardColor");
		String notice=RequestUtil.getString(request, "notice");
		String type=RequestUtil.getString(request, "type");
		Date begin_timestamp=RequestUtil.getDate(request, "begin_timestamp");
		Date end_timestamp=RequestUtil.getDate(request, "end_timestamp");
		Integer fixed_begin_term=RequestUtil.getInt(request, "fixed_begin_term");
		Integer fixed_term=RequestUtil.getInt(request, "fixed_term");
		String description=RequestUtil.getString(request, "description");
		String cardType=RequestUtil.getString(request, "cardType");
		Boolean toggleAdvancedFormSign=RequestUtil.getBoolean(request, "toggleAdvancedFormSign");
		Integer quantity=RequestUtil.getInt(request, "quantity");
		//构建json
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("card_id", id);
		JSONObject cardTypeName=new JSONObject();
		JSONObject baseInfoJson=new JSONObject();
		
		if("GROUPON".equals(cardType)){
			cardType="groupon";
		}else if("CASH".equals(cardType)){
			cardType="cash";
		}else if("DISCOUNT".equals(cardType)){
			cardType="discount";
		}else if("GIFT".equals(cardType)){
			cardType="gift";
		}else if("GENERAL_COUPON".equals(cardType)){
			cardType="general_coupon";
		}
		baseInfoJson.put("logo_url", logoUrl);
		baseInfoJson.put("color", cardColor);
		baseInfoJson.put("notice", notice);
		
		JSONObject dataInfoJson=new JSONObject();
		dataInfoJson.put("type", type);
		if("DATE_TYPE_FIX_TIME_RANGE".equals(type)){
			dataInfoJson.put("begin_timestamp", begin_timestamp.getTime()/1000);
			dataInfoJson.put("end_timestamp", end_timestamp.getTime()/1000);
		}else if("DATE_TYPE_FIX_TERM".equals(type)){
			dataInfoJson.put("fixed_term", fixed_term);
			dataInfoJson.put("fixed_begin_term", fixed_begin_term);
		}
		
		baseInfoJson.put("date_info", dataInfoJson);
		baseInfoJson.put("description", description);
		if(toggleAdvancedFormSign){//如果有非必填重要字段,则加入
			putAdvancedDataIntoJson(request, baseInfoJson);
		}
		cardTypeName.put("base_info", baseInfoJson);
		jsonObject.put(cardType, cardTypeName);
		String rtnCode=PublicApi.updateWxTicket(accessToken, jsonObject.toString());
		JSONObject resultJson=JSONObject.parseObject(rtnCode);
		Integer errcode=resultJson.getInteger("errcode");
		String card_id=resultJson.getString("card_id");
		JSONObject rtnObject=new JSONObject();
		if(errcode!=null&&errcode!=0){//调用失败
			rtnObject.put("success", false);
			WxCode wxCode=new WxCode();	
			String msg=wxCode.getTheCode(errcode);
			rtnObject.put("errMsg", msg);
		}else{
			rtnObject.put("success", true);
			WxTicket wxTicket=wxTicketManager.get(id);
			
			/*修改库存*/
			JSONObject sku=JSONObject.parseObject(wxTicket.getSku());
			int changeQuantity=quantity-sku.getInteger("quantity");
			String rtn=PublicApi.modifyTicketCount(accessToken, id, changeQuantity);//调用微信修改库存接口
			Integer changeErrcode=resultJson.getInteger("errcode");
			if(changeErrcode!=null&&errcode!=0){//调用失败
				WxCode wxCode=new WxCode();	
				String msg=wxCode.getTheCode(errcode);
				rtnObject.put("errMsg", msg);
			}else{
				sku.put("quantity", quantity);
				wxTicket.setSku(sku.toString());
			}
			/*修改库存*/
			
			wxTicket.setLogoUrl(logoUrl);
			wxTicket.setColor(cardColor);
			wxTicket.setDateInfo(dataInfoJson.toString());
			wxTicket.setDescription(description);
			wxTicket.setNotice(notice);
			wxTicketManager.update(wxTicket);
			//更新实体实体
			
		}
		return rtnObject;
	}
	
	@RequestMapping("createMemberCard")
	@ResponseBody
	public JSONObject createMemberCard(HttpServletRequest request,HttpServletResponse response){
		String pubId=RequestUtil.getString(request, "pubId");
		String toggleAdvancedFormSign=RequestUtil.getString(request, "toggleAdvancedFormSign");
		String togglePlusFormSign=RequestUtil.getString(request, "togglePlusFormSign");
		String brandName=RequestUtil.getString(request, "brandName");
		String title=RequestUtil.getString(request, "title");
		String logoUrl=RequestUtil.getString(request, "logoUrl");
		String logoUrlFileId=RequestUtil.getString(request, "logoUrlFileId");
		String cardColor=RequestUtil.getString(request, "cardColor");
		String codeType=RequestUtil.getString(request, "codeType");
		int quantity=RequestUtil.getInt(request, "quantity");
		String type=RequestUtil.getString(request, "type");
		String begin_timestamp=RequestUtil.getString(request, "begin_timestamp");
		String end_timestamp=RequestUtil.getString(request, "end_timestamp");
		int fixed_begin_term=RequestUtil.getInt(request, "fixed_begin_term");
		int fixed_term=RequestUtil.getInt(request, "fixed_term");
		String notice=RequestUtil.getString(request, "notice");
		String description=RequestUtil.getString(request, "description");
		String prerogative=RequestUtil.getString(request, "prerogative");
		
		
		String actictWay=RequestUtil.getString(request, "actictWay");
		int service_phone=RequestUtil.getInt(request, "service_phone");
		String center_title=RequestUtil.getString(request, "center_title");
		String center_url=RequestUtil.getString(request, "center_url");
		String background_pic_url=RequestUtil.getString(request, "background_pic_url");
		String background_pic_Field=RequestUtil.getString(request, "background_pic_Field");
		String center_sub_title=RequestUtil.getString(request, "center_sub_title");
		String custom_url_name=RequestUtil.getString(request, "custom_url_name");
		String custom_url=RequestUtil.getString(request, "custom_url");
		String custom_url_sub_title=RequestUtil.getString(request, "custom_url_sub_title");
		Boolean can_share=RequestUtil.getBoolean(request, "can_share");
		Boolean can_give_friend=RequestUtil.getBoolean(request, "can_give_friend");
		int discount=RequestUtil.getInt(request, "discount");
		Boolean supply_bonus=RequestUtil.getBoolean(request, "supply_bonus");
		int cost_money_unit=RequestUtil.getInt(request, "cost_money_unit");
		int increase_bonus=RequestUtil.getInt(request, "increase_bonus");
		int cost_bonus_unit=RequestUtil.getInt(request, "cost_bonus_unit");
		int reduce_money=RequestUtil.getInt(request, "reduce_money");
		int least_money_to_use_bonus=RequestUtil.getInt(request, "least_money_to_use_bonus");
		int max_reduce_bonus=RequestUtil.getInt(request, "max_reduce_bonus");
		String bonus_url=RequestUtil.getString(request, "bonus_url");
		String bonus_cleared=RequestUtil.getString(request, "bonus_cleared");
		String bonus_rules=RequestUtil.getString(request, "bonus_rules");
		Boolean supply_balance=RequestUtil.getBoolean(request, "supply_balance");
		String balance_url=RequestUtil.getString(request, "balance_url");
		String balance_rules=RequestUtil.getString(request, "balance_rules");
		String custom_field1=RequestUtil.getString(request, "custom_field1");
		String custom_field1_url=RequestUtil.getString(request, "custom_field1_url");
		String custom_field2=RequestUtil.getString(request, "custom_field2");
		String custom_field2_url=RequestUtil.getString(request, "custom_field2_url");
		String custom_field3=RequestUtil.getString(request, "custom_field3");
		String custom_field3_url=RequestUtil.getString(request, "custom_field3_url");
		String custom_cell1_name=RequestUtil.getString(request, "custom_cell1_name");
		String custom_cell1_tips=RequestUtil.getString(request, "custom_cell1_tips");
		String custom_cell1_url=RequestUtil.getString(request, "custom_cell1_url");
		
		
		
		
		
		
		Boolean can_use_with_other_discount=RequestUtil.getBoolean(request, "can_use_with_other_discount");
		String icon_url_list=RequestUtil.getString(request, "icon_url_list");
		String icon_url_listField=RequestUtil.getString(request, "icon_url_listField");
		String abstractContent=RequestUtil.getString(request, "abstract");
		String business_service=RequestUtil.getString(request, "business_service");
		String week=RequestUtil.getString(request, "week");
		String beginUseTime=RequestUtil.getString(request, "beginUseTime");
		
		
		
		JSONObject sendObj=new JSONObject();
		JSONObject cardObj=new JSONObject();
		cardObj.put("card_type","MEMBER_CARD");
		JSONObject memberCardObj=new JSONObject();
		memberCardObj.put("background_pic_url", background_pic_url);
		memberCardObj.put("supply_bonus", supply_bonus);
		memberCardObj.put("supply_balance", supply_balance);
		memberCardObj.put("prerogative", prerogative);
		return null;
	}
	
	@RequestMapping("createTicket")
	@ResponseBody
	public JSONObject createTicket(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pubId=RequestUtil.getString(request, "pubId");
		String logoUrl=RequestUtil.getString(request, "logoUrl");
		String cardColor=RequestUtil.getString(request, "cardColor");
		String brandName=RequestUtil.getString(request, "brandName");
		String title=RequestUtil.getString(request, "title");
		String codeType=RequestUtil.getString(request, "codeType");
		String notice=RequestUtil.getString(request, "notice");
		String description=RequestUtil.getString(request, "description");
		Integer quantity=RequestUtil.getInt(request, "quantity");
		String type=RequestUtil.getString(request, "type");
		Date begin_timestamp=RequestUtil.getDate(request, "begin_timestamp");
		Date end_timestamp=RequestUtil.getDate(request, "end_timestamp");
		Integer fixed_begin_term=RequestUtil.getInt(request, "fixed_begin_term");
		Integer fixed_term=RequestUtil.getInt(request, "fixed_term");
		String cardType=RequestUtil.getString(request, "cardType");
		String deal_detail=RequestUtil.getString(request, "deal_detail");
		Float least_cost=RequestUtil.getFloat(request, "least_cost");
		Float reduce_cost=RequestUtil.getFloat(request, "reduce_cost");
		Integer discount=RequestUtil.getInt(request, "discount");
		String gift=RequestUtil.getString(request, "gift");
		String logoUrlFileId=RequestUtil.getString(request, "logoUrlFileId");
		String default_detail=RequestUtil.getString(request, "default_detail");
		Boolean toggleAdvancedFormSign=RequestUtil.getBoolean(request, "toggleAdvancedFormSign");//是否有非必填字段
		Boolean togglePlusFormSign=RequestUtil.getBoolean(request, "togglePlusFormSign");//是否额外字段字段
		String accessToken=wxPubAppManager.getAccessToken(pubId);
		String cardTypeName="";
		JSONObject specialConfig=new JSONObject();
		specialConfig.put("logoUrlFileId", logoUrlFileId);//将本地显示用的图片fileId存进去回显时调用
		//构建需要post的Json
		JSONObject baseInfoJson=new JSONObject();
		baseInfoJson.put("logo_url", logoUrl);
		baseInfoJson.put("brand_name", brandName);
		baseInfoJson.put("code_type", codeType);
		baseInfoJson.put("title", title);
		baseInfoJson.put("color", cardColor);
		baseInfoJson.put("notice", notice);
		baseInfoJson.put("description", description);
		
		JSONObject dataInfoJson=new JSONObject();
		dataInfoJson.put("type", type);
		if("DATE_TYPE_FIX_TIME_RANGE".equals(type)){
			dataInfoJson.put("begin_timestamp", begin_timestamp.getTime()/1000);
			dataInfoJson.put("end_timestamp", end_timestamp.getTime()/1000);
		}else if("DATE_TYPE_FIX_TERM".equals(type)){
			dataInfoJson.put("fixed_term", fixed_term);
			dataInfoJson.put("fixed_begin_term", fixed_begin_term);
		}
		baseInfoJson.put("date_info", dataInfoJson);
		
		JSONObject skuJson=new JSONObject();
		skuJson.put("quantity", quantity);
		baseInfoJson.put("sku", skuJson);
		
		JSONObject typeName=new JSONObject();
		JSONObject baseJson=new JSONObject();//非必填重要字段:存字段
		if(toggleAdvancedFormSign){//如果有非必填重要字段,则加入
			baseJson=putAdvancedDataIntoJson(request, baseInfoJson);
		}
		typeName.put("base_info", baseInfoJson);
		
		JSONObject plusJson=new JSONObject();//额外字段:存字段
		if(togglePlusFormSign){//如果有额外字段,则加入
			putPlusIntoJson(request,plusJson);
			typeName.put("advanced_info",plusJson);
			String icon_url_listField=RequestUtil.getString(request, "icon_url_listField");
			if(StringUtils.isNotBlank(icon_url_listField)){
				specialConfig.put("icon_url_listField", icon_url_listField);//将本地显示用的图片fileId存进去回显时调用	
			}
			String beginUseTime=RequestUtil.getString(request, "beginUseTime");
			specialConfig.put("beginUseTime", beginUseTime);
			String endUseTime=RequestUtil.getString(request, "endUseTime");
			specialConfig.put("endUseTime",endUseTime);
		}
		JSONObject card=new JSONObject();
		card.put("card_type",cardType );
		
		//5种不同的优惠券的不同特殊配置
		if("GROUPON".equals(cardType)){
			cardTypeName="groupon";
			typeName.put("deal_detail", deal_detail);
			specialConfig.put("deal_detail", deal_detail);
		}else if("CASH".equals(cardType)){
			cardTypeName="cash";
			typeName.put("least_cost", (int) (least_cost*100));
			typeName.put("reduce_cost",(int)(reduce_cost*100));
			specialConfig.put("least_cost", least_cost);
			specialConfig.put("reduce_cost", reduce_cost);
		}else if("DISCOUNT".equals(cardType)){
			cardTypeName="discount";
			typeName.put("discount", discount);
			specialConfig.put("discount", discount);
		}else if("GIFT".equals(cardType)){
			cardTypeName="gift";
			typeName.put("gift", gift);
			specialConfig.put("gift", gift);
		}else if("GENERAL_COUPON".equals(cardType)){
			cardTypeName="general_coupon";
			typeName.put("default_detail", default_detail);
			specialConfig.put("default_detail", default_detail);
		}
		card.put(cardTypeName, typeName);
		
		JSONObject postJson=new JSONObject();
		postJson.put("card",card);
		
		//post至api接口
		String rtnJson=PublicApi.createCard(accessToken, postJson.toString());
		JSONObject resultJson=JSONObject.parseObject(rtnJson);
		Integer errcode=resultJson.getInteger("errcode");
		String card_id=resultJson.getString("card_id");
		JSONObject rtnObject=new JSONObject();
		if(errcode!=null&&errcode!=0){//调用失败
			rtnObject.put("success", false);
			WxCode wxCode=new WxCode();	
			String msg=wxCode.getTheCode(errcode);
			rtnObject.put("errMsg", msg);
		}else{
			rtnObject.put("success", true);
			//创建实体
			WxTicket wxTicket=new WxTicket();
			wxTicket.setId(card_id);
			wxTicket.setLogoUrl(logoUrl);
			wxTicket.setSpecialConfig(specialConfig.toString());
			wxTicket.setBrandName(brandName);
			wxTicket.setCodeType(codeType);
			wxTicket.setColor(cardColor);
			wxTicket.setDateInfo(dataInfoJson.toString());
			wxTicket.setDescription(description);
			wxTicket.setSku(skuJson.toString());
			wxTicket.setTitle(title);
			wxTicket.setNotice(notice);
			wxTicket.setPubId(pubId);
			wxTicket.setCardType(cardType);
			wxTicket.setBaseInfo(baseJson.toString());
			wxTicket.setAdvancedInfo(plusJson.toString());
			wxTicketManager.create(wxTicket);
		}
		return rtnObject;
	}
	/**
	 * 以二维码形式投放微信卡券
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("putInWxTicket")
	@ResponseBody
	public JSONObject putInWxTicket(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String cardId=RequestUtil.getString(request, "cardId");
		String pubId=RequestUtil.getString(request, "pubId");
		String accessToken=wxPubAppManager.getAccessToken(pubId);
		JSONObject card=new JSONObject();
		card.put("card_id", cardId);
		JSONObject action_info=new JSONObject();
		action_info.put("card", card);
		JSONObject postJson=new JSONObject();
		postJson.put("action_name", "QR_CARD");
		postJson.put("action_info", action_info);
		JSONObject resultJson=JSONObject.parseObject(PublicApi.putInWxTicket(accessToken, postJson.toString()));
		Integer errcode=resultJson.getInteger("errcode");
		JSONObject rtnObject=new JSONObject();
		if(errcode!=null&&errcode!=0){//调用失败
			rtnObject.put("success", false);
			WxCode wxCode=new WxCode();
			String msg=wxCode.getTheCode(errcode);
			rtnObject.put("errMsg", msg);
		}else{
			rtnObject=resultJson;
			rtnObject.put("success", true);
		}
		return rtnObject;
	}

	/**
	 * 将表单中重要非必填字段填入json中
	 * @param request
	 * @param jsonObject
	 */
	private JSONObject putAdvancedDataIntoJson(HttpServletRequest request,JSONObject jsonObject){
		String service_phone=RequestUtil.getString(request, "service_phone");
		String center_title=RequestUtil.getString(request, "center_title");
		String center_url=RequestUtil.getString(request, "center_url");
		String center_sub_title=RequestUtil.getString(request, "center_sub_title");
		String custom_url=RequestUtil.getString(request, "custom_url");
		String custom_url_name=RequestUtil.getString(request, "custom_url_name");
		String custom_url_sub_title=RequestUtil.getString(request, "custom_url_sub_title");
		Boolean can_share=RequestUtil.getBoolean(request, "can_share");
		Boolean can_give_friend=RequestUtil.getBoolean(request, "can_give_friend"); 
		
		jsonObject.put("service_phone", service_phone);
		jsonObject.put("center_title", center_title);
		jsonObject.put("center_url", center_url);
		jsonObject.put("center_sub_title", center_sub_title);
		jsonObject.put("custom_url", custom_url);
		jsonObject.put("custom_url_name", custom_url_name);
		jsonObject.put("custom_url_sub_title",custom_url_sub_title);
		jsonObject.put("can_share", can_share);
		jsonObject.put("can_give_friend", can_give_friend);
		
		JSONObject rtnJson=new JSONObject();
		rtnJson.put("service_phone", service_phone);
		rtnJson.put("center_title", center_title);
		rtnJson.put("center_url", center_url);
		rtnJson.put("center_sub_title", center_sub_title);
		rtnJson.put("custom_url", custom_url);
		rtnJson.put("custom_url_name", custom_url_name);
		rtnJson.put("custom_url_sub_title",custom_url_sub_title);
		rtnJson.put("can_share", can_share);
		rtnJson.put("can_give_friend", can_give_friend);
		return rtnJson;
	}
	
	/**
	 * 将额外字段添加进post的json里
	 * @param request
	 * @param jsonObject
	 */
	private void putPlusIntoJson(HttpServletRequest request,JSONObject jsonObject){
		//JSONObject jsonObject=new JSONObject();
		JSONObject useCondiction=new JSONObject();
		JSONObject abstractJson=new JSONObject();
		JSONArray weekJson=new JSONArray();
		JSONArray businessInfo=new JSONArray();
		String cardType=RequestUtil.getString(request, "cardType");
		
		Boolean can_use_with_other_discount=RequestUtil.getBoolean(request, "can_use_with_other_discount");
		String abstractMsg=RequestUtil.getString(request, "abstract");
		String icon_url_list=RequestUtil.getString(request, "icon_url_list");
		String business_service=RequestUtil.getString(request, "business_service");
		
		
		String week=RequestUtil.getString(request, "week");
		String beginUseTime=RequestUtil.getString(request, "beginUseTime");
		String[] beginHourAndMinute=beginUseTime.split(":");
		Integer beginHour=Integer.parseInt(beginHourAndMinute[0]);
		Integer beginMinute=Integer.parseInt(beginHourAndMinute[1]);
		String endUseTime=RequestUtil.getString(request, "endUseTime");
		String[] endHourAndMinute=endUseTime.split(":");
		Integer endHour=Integer.parseInt(endHourAndMinute[0]);
		Integer endMinute=Integer.parseInt(endHourAndMinute[1]);
		JSONArray iconSoloArray=new JSONArray();
		if(StringUtils.isNotBlank(icon_url_list)){
			iconSoloArray.add(icon_url_list);
		}
		
		if("CASH".equals(cardType)){
			String accept_category=RequestUtil.getString(request, "accept_category");
			if(StringUtils.isNotBlank(accept_category)){
				useCondiction.put("accept_category", accept_category);
			}
			String reject_category=RequestUtil.getString(request, "reject_category");
			if(StringUtils.isNotBlank(reject_category)){
				useCondiction.put("reject_category", reject_category);
			}
			
			Integer least_cost=RequestUtil.getInt(request, "plus_least_cost");
			useCondiction.put("least_cost", least_cost);
		}else if("GIFT".equals(cardType)){
			Integer least_cost=RequestUtil.getInt(request, "plus_least_cost");
			useCondiction.put("least_cost", least_cost);
			String object_use_for=RequestUtil.getString(request, "object_use_for");
			if(StringUtils.isNotBlank(object_use_for)){
				useCondiction.put("object_use_for", object_use_for);
			}
		}
		
		if(can_use_with_other_discount){
			useCondiction.put("can_use_with_other_discount", can_use_with_other_discount);
		}
		jsonObject.put("use_condition", useCondiction);
		
		if (StringUtils.isNotBlank(abstractMsg)) {
			abstractJson.put("abstract", abstractMsg);
		}
		if (iconSoloArray.size() > 0) {
			abstractJson.put("icon_url_list", iconSoloArray);
		}
		jsonObject.put("abstract", abstractJson);
		
		if(StringUtils.isNotBlank(week)){
			String[] weekArray=week.split(",");
			for (int i = 0; i < weekArray.length; i++) {
				JSONObject object=new JSONObject();
				object.put("type", weekArray[i]);
				object.put("begin_hour", beginHour);
				object.put("begin_minute",beginMinute);
				object.put("end_hour", endHour);
				object.put("end_minute",endMinute);
				weekJson.add(object);
			}
			jsonObject.put("time_limit", weekJson);
		}
		
		if(StringUtils.isNotBlank(business_service)){
			String[] businessArray=business_service.split(",");
			for (int i = 0; i < businessArray.length; i++) {
				businessInfo.add(businessArray[i]);
			}	
			jsonObject.put("business_service", businessInfo);
		}
		
		
	}
	
	@RequestMapping("queryTicketInfo")
	@ResponseBody
	public JSONObject queryTicketInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pubId=RequestUtil.getString(request, "pubId");
		String cardId=RequestUtil.getString(request, "cardId");
		String accessToken=wxPubAppManager.getAccessToken(pubId);
		String startQ=RequestUtil.getString(request, "startQ");
		String endQ=RequestUtil.getString(request, "endQ");
		JSONObject jsonParams=new JSONObject();
		jsonParams.put("begin_date", startQ);
		jsonParams.put("end_date", endQ);
		jsonParams.put("cond_source", 1);
		jsonParams.put("card_id", cardId);
		String rtnCode=PublicApi.getFreeTicketInfo(accessToken, jsonParams.toString());
		JSONObject resultJson=JSONObject.parseObject(rtnCode);
		Integer errcode=resultJson.getInteger("errcode");
		JSONObject rtnObject=new JSONObject();
		if(errcode!=null&&errcode!=0){//调用失败
			rtnObject.put("success", false);
			WxCode wxCode=new WxCode();
			String msg=wxCode.getTheCode(errcode);
			rtnObject.put("errMsg", msg);
		}else{
			rtnObject=resultJson;
			rtnObject.put("success", true);
		}
		return rtnObject;
	}
	
	@RequestMapping("createLandingPage")
	@ResponseBody
	public JSONObject createLandingPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pubId=RequestUtil.getString(request, "pubId");
		String ids=RequestUtil.getString(request, "ids");
		String fileId=RequestUtil.getString(request, "fileId");
		String tenantId=ContextUtil.getCurrentTenantId();
    	String publicAddress=SysPropertiesUtil.getTenantProperty("publicAddress", tenantId);
		String[] idArray=ids.split(",");
		JSONArray jsonArray=new JSONArray();
		for (String string : idArray) {
			WxTicket wxTicket=wxTicketManager.get(string);
			JSONObject object=new JSONObject();
			object.put("card_id", string);
			object.put("thumb_url", wxTicket.getLogoUrl());
			jsonArray.add(object);
		}
		String accessToken=wxPubAppManager.getAccessToken(pubId);
		JSONObject json=new JSONObject();
		json.put("banner", publicAddress+request.getContextPath()+"/sys/core/file/download/"+fileId+".do");
		//json.put("banner", "http://a3.qpic.cn/psb?/V1024uWD20LDwT/o*n7WPadgJyMST10juYD8qPxt2GXG9JU20niQc16Q4A!/b/dHkBAAAAAAAA&bo=AAQABAAEAAQFCSo!&rf=viewer_4");
		json.put("page_title", "卡券货架");
		json.put("can_share", true);
		json.put("scene", "SCENE_H5");
		json.put("card_list", jsonArray);
		String rtnCode=PublicApi.createLandingPage(accessToken, json.toString());
		JSONObject resultJson=JSONObject.parseObject(rtnCode);
		Integer errcode=resultJson.getInteger("errcode");
		JSONObject rtnObject=new JSONObject();
		if(errcode!=null&&errcode!=0){//调用失败
			rtnObject.put("success", false);
			WxCode wxCode=new WxCode();
			String msg=wxCode.getTheCode(errcode);
			rtnObject.put("errMsg", msg);
		}else{
			rtnObject=resultJson;
			rtnObject.put("success", true);
		}
		return rtnObject;
	}
	
	/**
	 * 核销卡券code,期间会先验证卡券是否可核销
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("consumeTicket")
	@ResponseBody
	public JSONObject consumeTicket(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String code=RequestUtil.getString(request, "code");
		String pubId=RequestUtil.getString(request, "pubId");
		String accessToken=wxPubAppManager.getAccessToken(pubId);
		
		JSONObject rtnObject=new JSONObject();
		JSONObject jsonObject=checkTicket(accessToken, code);
		if(jsonObject.getBoolean("success")){
			String rtnCode=PublicApi.consumeWxTicket(accessToken, code);
			JSONObject resultJson=JSONObject.parseObject(rtnCode);
			Integer errcode=resultJson.getInteger("errcode");
			if(errcode!=null&&errcode!=0){//调用失败
				rtnObject.put("success", false);
				WxCode wxCode=new WxCode();
				String msg=wxCode.getTheCode(errcode);
				rtnObject.put("errMsg", msg);
			}else{
				rtnObject=resultJson;
				rtnObject.put("success", true);
			}
		}else{
			rtnObject=jsonObject;
		}
		return rtnObject;
	}
	
	/**
	 * 检查卡券状态是否正常
	 * @param accessToken
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public JSONObject checkTicket(String accessToken,String code) throws Exception{
		String rtnCode=PublicApi.checkTicketCode(accessToken, code);
		JSONObject resultJson=JSONObject.parseObject(rtnCode);
		Integer errcode=resultJson.getInteger("errcode");
		JSONObject rtnObject=new JSONObject();
		if(errcode!=null&&errcode!=0){//调用失败
			rtnObject.put("success", false);
			WxCode wxCode=new WxCode();
			String msg=wxCode.getTheCode(errcode);
			rtnObject.put("errMsg", msg);
		}else{
			rtnObject=resultJson;
			if(resultJson.getBoolean("can_consume")){
				rtnObject.put("success", true);
			}else{
				rtnObject.put("success", false);
			}
			rtnObject.put("success", true);
		}
		return rtnObject;
	}
	
	
	@RequestMapping("unVailableTicket")
	@ResponseBody
	public JSONObject unVailableTicket(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String code=RequestUtil.getString(request, "code");
		String pubId=RequestUtil.getString(request, "pubId");
		String accessToken=wxPubAppManager.getAccessToken(pubId);
		String rtnCode=PublicApi.cancelTicket(accessToken, code);
		JSONObject resultJson=JSONObject.parseObject(rtnCode);
		Integer errcode=resultJson.getInteger("errcode");
		JSONObject rtnObject=new JSONObject();
		if(errcode!=null&&errcode!=0){//调用失败
			rtnObject.put("success", false);
			WxCode wxCode=new WxCode();
			String msg=wxCode.getTheCode(errcode);
			rtnObject.put("errMsg", msg);
		}else{
			rtnObject=resultJson;
			rtnObject.put("success", true);
		}
		return rtnObject;
	}
}
