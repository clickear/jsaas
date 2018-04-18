
package com.redxun.sys.org.controller;

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

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.log.LogEnt;
import com.redxun.sys.org.entity.OsAttributeValue;
import com.redxun.sys.org.manager.OsAttributeValueManager;

/**
 * 人员属性值控制器
 * @author mansan
 */
@Controller
@RequestMapping("/sys/org/osAttributeValue/")
public class OsAttributeValueController extends BaseMybatisListController{
    @Resource
    OsAttributeValueManager osAttributeValueManager;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    @LogEnt(action = "del", module = "sys", submodule = "人员属性值")
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=RequestUtil.getString(request, "ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                osAttributeValueManager.delete(id);
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
        OsAttributeValue osAttributeValue=null;
        if(StringUtils.isNotEmpty(pkId)){
           osAttributeValue=osAttributeValueManager.get(pkId);
        }else{
        	osAttributeValue=new OsAttributeValue();
        }
        return getPathView(request).addObject("osAttributeValue",osAttributeValue);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	OsAttributeValue osAttributeValue=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		osAttributeValue=osAttributeValueManager.get(pkId);
    	}else{
    		osAttributeValue=new OsAttributeValue();
    	}
    	return getPathView(request).addObject("osAttributeValue",osAttributeValue);
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
        OsAttributeValue osAttributeValue = osAttributeValueManager.getOsAttributeValue(uId);
        String json = JSONObject.toJSONString(osAttributeValue);
        return json;
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return osAttributeValueManager;
	}
	
	@RequestMapping("getMyAttribute")
	@ResponseBody
	public JsonResult<OsAttributeValue> getMyAttribute(HttpServletRequest request,HttpServletResponse response){
		String targetId=RequestUtil.getString(request, "targetId");
		return null;
	}
	
	@RequestMapping("saveTargetAttributes")
	@ResponseBody
	public JSONObject saveTargetAttributes(HttpServletRequest request,HttpServletResponse response){
		Map<String, String[]> map=request.getParameterMap();
		String createBy=ContextUtil.getCurrentUserId();
		String tenantId=ContextUtil.getCurrentTenantId();
		String targetId=RequestUtil.getString(request, "targetId");
		removeAllTargetAttributes(targetId);
		for (String key : map.keySet()) {
			if(!"targetId".equals(key)){
				String value=RequestUtil.getString(request, key);
				String valueId=key.replace("widgetType_", "");
				OsAttributeValue osAttributeValue=new OsAttributeValue();
				osAttributeValue.setId(IdUtil.getId());
				osAttributeValue.setCreateBy(createBy);
				osAttributeValue.setTenantId(tenantId);
				osAttributeValue.setValue(value);
				osAttributeValue.setAttributeId(valueId);
				osAttributeValue.setTargetId(targetId);
				osAttributeValueManager.create(osAttributeValue);
			}
		}
		return new JSONObject();
	}
	
	/**
	 * 删除目标的所有自定义属性值
	 * @param targetId
	 * @return
	 */
	private boolean removeAllTargetAttributes(String targetId){
		List<OsAttributeValue> osAttributeValues=osAttributeValueManager.getUserAttributeValue(targetId);
		try {
			for (OsAttributeValue osAttributeValue : osAttributeValues) {
				osAttributeValueManager.delete(osAttributeValue.getId());
			}
		} catch (Exception e) {
			return false;
		}
		return true;
		
	}

}
