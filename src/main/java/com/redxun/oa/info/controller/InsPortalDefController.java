
package com.redxun.oa.info.controller;

import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.oa.info.entity.InsPortal;
import com.redxun.oa.info.entity.InsPortalDef;
import com.redxun.oa.info.manager.InsColumnDefManager;
import com.redxun.oa.info.manager.InsPortalDefManager;
import com.redxun.org.api.model.ITenant;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;

/**
 * ins_portal_def控制器
 * @author mansan
 */
@Controller
@RequestMapping("/oa/info/insPortalDef/")
public class InsPortalDefController extends BaseMybatisListController{
    @Resource
    InsPortalDefManager insPortalDefManager;
    @Resource
    InsColumnDefManager insColumnDefManager;
   
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
                insPortalDefManager.delete(id);
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
        InsPortalDef insPortalDef=null;
        if(StringUtils.isNotEmpty(pkId)){
           insPortalDef=insPortalDefManager.get(pkId);
        }else{
        	insPortalDef=new InsPortalDef();
        }
        return getPathView(request).addObject("insPortalDef",insPortalDef);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	InsPortalDef insPortalDef=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		insPortalDef=insPortalDefManager.get(pkId);
    		if("true".equals(forCopy)){
    			insPortalDef.setPortId(null);
    		}
    	}else{
    		insPortalDef=new InsPortalDef();
    	}
    	return getPathView(request).addObject("insPortalDef",insPortalDef);
    }
    
    @RequestMapping("editTmp")
    public ModelAndView editTmp(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "portId");
    	//复制添加
    	InsPortalDef insPortalDef=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		insPortalDef=insPortalDefManager.get(pkId);
    	}else{
    		insPortalDef=new InsPortalDef();
    	}
    	return getPathView(request).addObject("insPortalDef",insPortalDef).addObject("portId", pkId);
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
        InsPortalDef insPortalDef = insPortalDefManager.getInsPortalDef(uId);
        String json = JSONObject.toJSONString(insPortalDef);
        return json;
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return insPortalDefManager;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("saveHtml")
	@ResponseBody
    public JsonResult saveHtml(HttpServletRequest request,HttpServletResponse respose){
    	String html = RequestUtil.getString(request, "formatSrc");
    	String editHtml = RequestUtil.getString(request, "editHtml");
		String portId = RequestUtil.getString(request, "portId");
    	
		
		InsPortalDef insPortal = new InsPortalDef();
		if(StringUtils.isNotEmpty(portId)){
			insPortal = insPortalDefManager.get(portId);
			insPortal.setLayoutHtml(html);
			insPortal.setEditHtml(editHtml);
			insPortalDefManager.update(insPortal);
		}
		
    	String msg = html;
    	return new JsonResult(true,msg);
    }
	
	@RequestMapping("setPersonalPort")
	@ResponseBody
	public String setPersonalPort(HttpServletRequest request,HttpServletResponse respose){
		String userId = ContextUtil.getCurrentUserId();
		String tenantId = ContextUtil.getCurrentTenantId();
		InsPortalDef insPortal = insPortalDefManager.getByIdKey(InsPortal.TYPE_PERSONAL, tenantId, userId);
		if (insPortal == null) {
			insPortal = new InsPortalDef();
			insPortal.setPortId(idGenerator.getSID());
			insPortal.setKey("PERSONAL");
			insPortal.setName("个人");
			insPortal.setIsDefault("NO");
			insPortal.setUserId(userId);
			insPortalDefManager.create(insPortal);
		}
		return insPortal.getPortId();
	}
	
	@RequestMapping("getPersonalPort")
	@ResponseBody
	public String getPersonalPort(HttpServletRequest request,HttpServletResponse respose){
		String ctxPath = request.getContextPath();
		InsPortalDef insPortal = insPortalDefManager.getPortalDef();
		if(insPortal==null){
			return "";
		}
		String html = insPortal.getLayoutHtml();
		if(StringUtils.isEmpty(html)){
			return "";
		}
		Document doc = Jsoup.parse(html);
    	Elements els = doc.getElementsByAttributeValueStarting("class", "colId");
    	Iterator<Element> elIt=els.iterator();
    	while(elIt.hasNext()){
			Element el=elIt.next();
			String name = el.className();
			String colId = name.split("_")[1];
			String Colhtml = insColumnDefManager.getColHtml(colId,ctxPath);
			el.html(Colhtml);			
		}
    	
		return doc.body().html();
	}

}
