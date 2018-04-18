
package com.redxun.oa.info.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.info.entity.InsPortalPermission;
import com.redxun.oa.info.manager.InsPortalPermissionManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.RequestUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 布局权限设置 管理
 * @author mansan
 */
@Controller
@RequestMapping("/oa/info/insPortalPermission/")
public class InsPortalPermissionFormController extends BaseFormController {

    @Resource
    private InsPortalPermissionManager insPortalPermissionManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("insPortalPermission")
    public InsPortalPermission processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        InsPortalPermission insPortalPermission = null;
        if (StringUtils.isNotEmpty(id)) {
            insPortalPermission = insPortalPermissionManager.get(id);
        } else {
            insPortalPermission = new InsPortalPermission();
        }

        return insPortalPermission;
    }
    /**
     * 保存实体数据
     * @param request
     * @param orders
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("insPortalPermission") @Valid InsPortalPermission insPortalPermission, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(insPortalPermission.getId())) {
            insPortalPermission.setId(IdUtil.getId());
            insPortalPermissionManager.create(insPortalPermission);
            msg = getMessage("insPortalPermission.created", new Object[]{insPortalPermission.getIdentifyLabel()}, "[布局权限设置]成功创建!");
        } else {
            insPortalPermissionManager.update(insPortalPermission);
            msg = getMessage("insPortalPermission.updated", new Object[]{insPortalPermission.getIdentifyLabel()}, "[布局权限设置]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
    
    @RequestMapping("savePortal")
    @ResponseBody
    public JsonResult savePortal(HttpServletRequest request,HttpServletResponse response){
    	String portalType = RequestUtil.getString(request, "portalType");
    	String userIds = RequestUtil.getString(request, "user");
    	String groupIds = RequestUtil.getString(request, "group");
    	String subGroupIds = RequestUtil.getString(request, "subGroup");
    	String layoutId = RequestUtil.getString(request, "layoutId");
    	InsPortalPermission insPortalPermission = new InsPortalPermission();
    	insPortalPermissionManager.delByLayoutId(layoutId);
    	
    	if("ALL".equals(portalType)){
    		insPortalPermission.setId(IdUtil.getId());
    		insPortalPermission.setType(InsPortalPermission.TYPE_ALL);
    		insPortalPermission.setLayoutId(layoutId);
    		insPortalPermission.setOwnerId(InsPortalPermission.TYPE_ALL);
    		insPortalPermissionManager.create(insPortalPermission);
    		return new JsonResult(true, "成功设置权限");
    	}else{
    		if(StringUtils.isNotEmpty(userIds)){
    			String[] ids = userIds.split(",");
    			for(String id:ids){
    	    		insPortalPermission.setId(IdUtil.getId());
    	    		insPortalPermission.setType(InsPortalPermission.TYPE_USER);
    	    		insPortalPermission.setLayoutId(layoutId);
    	    		insPortalPermission.setOwnerId(id);
    	    		insPortalPermissionManager.create(insPortalPermission);
    			}
    		}
    		if(StringUtils.isNotEmpty(groupIds)){
    			String[] ids = groupIds.split(",");
    			for(String id:ids){
    	    		insPortalPermission.setId(IdUtil.getId());
    	    		insPortalPermission.setType(InsPortalPermission.TYPE_GROUP);
    	    		insPortalPermission.setLayoutId(layoutId);
    	    		insPortalPermission.setOwnerId(id);
    	    		insPortalPermissionManager.create(insPortalPermission);
    			}
    		}
    		if(StringUtils.isNotEmpty(subGroupIds)){
    			String[] ids = subGroupIds.split(",");
    			for(String id:ids){
    	    		insPortalPermission.setId(IdUtil.getId());
    	    		insPortalPermission.setType(InsPortalPermission.TYPE_SUBGROUP);
    	    		insPortalPermission.setLayoutId(layoutId);
    	    		insPortalPermission.setOwnerId(id);
    	    		insPortalPermissionManager.create(insPortalPermission);
    			}
    		}
    		return new JsonResult(true, "成功设置权限");
    	}
    }
}

