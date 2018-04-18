
package com.redxun.sys.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.SysBoList;
import com.redxun.sys.core.manager.SysBoListManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.redxun.saweb.util.IdUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统自定义业务管理列表 管理
 * @author mansan
 */
@Controller
@RequestMapping("/sys/core/sysBoList/")
public class SysBoListFormController extends BaseFormController {

    @Resource
    private SysBoListManager sysBoListManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysBoList")
    public SysBoList processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        SysBoList sysBoList = null;
        if (StringUtils.isNotEmpty(id)) {
            sysBoList = sysBoListManager.get(id);
        } else {
            sysBoList = new SysBoList();
        }

        return sysBoList;
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
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysBoList") @Valid SysBoList sysBoList, BindingResult result) {
		boolean isKeyExist=sysBoListManager.isKeyExist(sysBoList.getKey(),ContextUtil.getCurrentTenantId(),sysBoList.getId());
    	if(isKeyExist){
    		result.rejectValue("key", "key.exist", "标识键："+sysBoList.getKey()+"已经存在！");
    	}
        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        
        String sql=sysBoList.getSql();
        if(StringUtils.isNotEmpty(sql)){
        	sql=sql.toUpperCase();
        }
        
//        if(sql.indexOf("CREATE ")!=-1 || sql.indexOf("UPDATE ")!=-1 || sql.indexOf("DELETE ")!=-1 || sql.indexOf("TRUNCATE ")!=-1){
//        	String error="执行的SQL中含有非法的数据库更新操作！";
//        	result.rejectValue("sql","sql.warn",error);
//        	return new JsonResult(false, error);
//        }
        
        String msg = null;
        if (StringUtils.isEmpty(sysBoList.getId())) {
            sysBoList.setId(IdUtil.getId());
            sysBoListManager.create(sysBoList);
            msg = getMessage("sysBoList.created", new Object[]{sysBoList.getIdentifyLabel()}, "[系统自定义业务管理列表]成功创建!");
        } else {
            sysBoListManager.update(sysBoList);
            msg = getMessage("sysBoList.updated", new Object[]{sysBoList.getIdentifyLabel()}, "[系统自定义业务管理列表]成功更新!");
        }
        return new JsonResult(true, msg,sysBoList);
    }
}

