
package com.redxun.sys.org.controller;

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

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.org.entity.OsAttributeValue;
import com.redxun.sys.org.entity.OsCustomAttribute;
import com.redxun.sys.org.manager.OsAttributeValueManager;
import com.redxun.sys.org.manager.OsCustomAttributeManager;
import com.redxun.sys.core.entity.SysInst;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.manager.SysTreeManager;
import com.redxun.sys.log.LogEnt;

/**
 * 自定义属性控制器
 * @author mansan
 */
@Controller
@RequestMapping("/sys/org/osCustomAttribute/")
public class OsCustomAttributeController extends BaseMybatisListController{
    @Resource
    OsCustomAttributeManager osCustomAttributeManager;
    @Resource
    SysTreeManager  sysTreeManager;
    @Resource
    OsAttributeValueManager osAttributeValueManager;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    @LogEnt(action = "del", module = "sys", submodule = "自定义属性")
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=RequestUtil.getString(request, "ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                osCustomAttributeManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除!");
    }
    
    @RequestMapping("getAttrsByTreeId")
    @ResponseBody
    public JsonPageResult<OsCustomAttribute> getAttrsByTreeId(HttpServletRequest request,HttpServletResponse response){
    	String treeId=RequestUtil.getString(request, "treeId");
    	String type=RequestUtil.getString(request, "type");
    	QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
    	if(StringUtils.isNotBlank(treeId)){
    		queryFilter.addFieldParam("TREE_ID_", treeId);
    	}
    	if(StringUtils.isNotBlank(type)){
    		queryFilter.addFieldParam("ATTRIBUTE_TYPE_", type);
    	}
    	List<OsCustomAttribute> osCustomAttributes=osCustomAttributeManager.getAll(queryFilter);
    	JsonPageResult<OsCustomAttribute> jsonPageResult=new JsonPageResult<OsCustomAttribute>(osCustomAttributes,queryFilter.getPage().getTotalItems());
    	return jsonPageResult;
    	
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
        OsCustomAttribute osCustomAttribute=null;
        if(StringUtils.isNotEmpty(pkId)){
           osCustomAttribute=osCustomAttributeManager.get(pkId);
        }else{
        	osCustomAttribute=new OsCustomAttribute();
        }
        return getPathView(request).addObject("osCustomAttribute",osCustomAttribute);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	OsCustomAttribute osCustomAttribute=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		osCustomAttribute=osCustomAttributeManager.get(pkId);
    	}else{
    		osCustomAttribute=new OsCustomAttribute();
    	}
    	return getPathView(request).addObject("osCustomAttribute",osCustomAttribute);
    }
    

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return osCustomAttributeManager;
	}
	
	@RequestMapping("getTreeByCat")
	@ResponseBody
	public List<SysTree> getTreeByCat (HttpServletRequest request,HttpServletResponse response){
		List<SysTree> treeList=sysTreeManager.getByCatKeyTenantId(SysTree.CAT_CUSTOMATTRIBUTE, SysInst.ADMIN_TENANT_ID);
		return treeList;
	}
	
	@RequestMapping("grantGroupAttribute")
	public ModelAndView grantGroupAttribute(HttpServletRequest request,HttpServletResponse response){
		String targetId=RequestUtil.getString(request, "targetId");
		String attributeType=RequestUtil.getString(request, "attributeType");
		List<OsAttributeValue> osAttributeValues=osAttributeValueManager.getUserAttributeValue(targetId);
		List<OsCustomAttribute> osCustomAttributes=new ArrayList<OsCustomAttribute>();
		for (OsAttributeValue osAttributeValue : osAttributeValues) {
			OsCustomAttribute osCustomAttribute=osCustomAttributeManager.get(osAttributeValue.getAttributeId());
			osCustomAttribute.setValue(osAttributeValue.getValue());
			osCustomAttributes.add(osCustomAttribute);
		}
		return this.getPathView(request).addObject("osCustomAttributes", osCustomAttributes).addObject("targetId", targetId).addObject("attributeType", attributeType);
	}

}
