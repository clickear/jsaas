
package com.redxun.oa.info.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.redxun.core.engine.FreemarkEngine;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.script.GroovyEngine;
import com.redxun.oa.info.entity.InsColumnDef;
import com.redxun.oa.info.manager.InsColumnDefManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;

import freemarker.template.TemplateException;

/**
 * ins_column_def控制器
 * @author mansan
 */
@Controller
@RequestMapping("/oa/info/insColumnDef/")
public class InsColumnDefController extends BaseMybatisListController{
    @Resource
    InsColumnDefManager insColumnDefManager;
    @Resource
	private FreemarkEngine freemarkEngine;
    @Resource
    private GroovyEngine groovyEngine;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
	

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return insColumnDefManager;
	}
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=RequestUtil.getString(request, "ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                insColumnDefManager.delete(id);
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
        InsColumnDef insColumnDef=null;
        if(StringUtils.isNotEmpty(pkId)){
           insColumnDef=insColumnDefManager.get(pkId);
        }else{
        	insColumnDef=new InsColumnDef();
        }
        return getPathView(request).addObject("insColumnDef",insColumnDef);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	InsColumnDef insColumnDef=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		insColumnDef=insColumnDefManager.get(pkId);
    		if("true".equals(forCopy)){
    			insColumnDef.setColId(null);
    		}
    	}else{
    		insColumnDef=new InsColumnDef();
    	}
    	return getPathView(request).addObject("insColumnDef",insColumnDef);
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
        InsColumnDef insColumnDef = insColumnDefManager.getInsColumnDef(uId);
        String json = JSONObject.toJSONString(insColumnDef);
        return json;
    }
    
    @RequestMapping(value="getColHtml")
    @ResponseBody
    public String getColHtml(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String userId = ContextUtil.getCurrentUserId();
    	String ctxPath = request.getContextPath();
    	//获取栏目ID
    	String colId=RequestUtil.getString(request, "colId");
    	String html = insColumnDefManager.getColHtml(colId, ctxPath);
        return html; 
    }
    
    @RequestMapping(value="getAllCol")
    @ResponseBody
    public List<InsColumnDef> getAllCol(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	List<InsColumnDef> cols = insColumnDefManager.getAll();
    	return cols;
    }
    
	/**
	 * 获得租户的栏目列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getNewsColumns")
	@ResponseBody
	public List<InsColumnDef> getNewsColumns(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter qf = new QueryFilter();
		qf.addFieldParam("isNews", "YES");
		qf.addFieldParam("tenantId", ContextUtil.getCurrentTenantId());
		List<InsColumnDef> insColumns = insColumnDefManager.getAll(qf);
		return insColumns;
	}
	
	@RequestMapping("getSQLHtml")
	@ResponseBody
	public JsonResult getSQLHtml(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException{
		Map<String,Object> params = new HashMap<String, Object>();
		String json = RequestUtil.getString(request, "resultField");
		String bokey = RequestUtil.getString(request, "bokey");
		JSONArray arr = JSONArray.parseArray(json);
		Iterator<Object> it = arr.iterator();
		Map<String,String> map = new HashMap<String, String>();
		while(it.hasNext()) {
		     JSONObject ob = (JSONObject) it.next();
		     String fieldName = ob.getString("fieldName");
		     map.put(fieldName, fieldName);
		}
		params.put("map", map);
		params.put("bokey", bokey);
		String html=freemarkEngine.mergeTemplateIntoString("list/insportalBoListTemp.ftl", params);

		
		return new JsonResult(true, "", html);
	}

	
	

}
