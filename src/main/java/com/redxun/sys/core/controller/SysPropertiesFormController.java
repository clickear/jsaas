
package com.redxun.sys.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.cache.CacheUtil;
import com.redxun.core.json.JsonResult;
import com.redxun.core.util.EncryptUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.SysProperties;
import com.redxun.sys.core.manager.SysPropertiesManager;
import com.redxun.sys.core.util.SysPropertiesUtil;
import com.redxun.sys.log.LogEnt;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.util.WebAppUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统参数 管理
 * @author ray
 */
@Controller
@RequestMapping("/sys/core/sysProperties/")
public class SysPropertiesFormController extends BaseFormController {

    @Resource
    private SysPropertiesManager sysPropertiesManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysProperties")
    public SysProperties processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        SysProperties sysProperties = null;
        if (StringUtils.isNotEmpty(id)) {
            sysProperties = sysPropertiesManager.get(id);
        } else {
            sysProperties = new SysProperties();
        }

        return sysProperties;
    }
    /**
     * 保存实体数据
     * @param request
     * @param orders
     * @param result
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @LogEnt(action = "save", module = "系统内核", submodule = "系统参数")
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysProperties") @Valid SysProperties sysProperties, BindingResult result) throws Exception {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        String encrypt=sysProperties.getEncrypt();
        if("YES".equals(encrypt)){//加密
        	String originValue=sysProperties.getValue();
        	EncryptUtil encryptUtil=WebAppUtil.getBean(EncryptUtil.class);
        	sysProperties.setValue(encryptUtil.encrypt(originValue));
        }
        //验证alias唯一性
        String alias=sysProperties.getAlias();
		String id=sysProperties.getProId();
		SysProperties sysPropertiesBefore=sysPropertiesManager.getPropertiesByName(alias);
		if(sysPropertiesBefore==null){//如果没有找到这个alias的说明没有重复冲突
		}else if(sysPropertiesBefore.getProId().equals(id)){
		}else{
			 return new JsonResult(false, "别名不允许重复");
		}
        
        if (StringUtils.isEmpty(sysProperties.getProId())) {
            sysProperties.setProId(IdUtil.getId());
            sysPropertiesManager.create(sysProperties);
            msg = getMessage("sysProperties.created", new Object[]{sysProperties.getIdentifyLabel()}, "[系统参数]成功创建!");
        } else {
        	//修改时清空这个缓存
        	CacheUtil.delCache("property_"+sysProperties.getAlias());
            sysPropertiesManager.update(sysProperties);
            msg = getMessage("sysProperties.updated", new Object[]{sysProperties.getIdentifyLabel()}, "[系统参数]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

