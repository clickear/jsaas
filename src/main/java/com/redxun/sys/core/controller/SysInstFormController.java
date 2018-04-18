package com.redxun.sys.core.controller;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.saweb.util.IdUtil;
import com.redxun.sys.core.entity.SysInst;
import com.redxun.sys.core.manager.SysInstManager;
import com.redxun.sys.log.LogEnt;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 注册机构管理
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/core/sysInst/")
public class SysInstFormController extends BaseFormController {

    @Resource
    private SysInstManager sysInstManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysInst")
    public SysInst processForm(HttpServletRequest request) {

        String instId = request.getParameter("pkId");
        SysInst sysInst = null;
        if (StringUtils.isNotEmpty(instId)) {
            sysInst = sysInstManager.get(instId);
        } else {
            sysInst = new SysInst();
            sysInst.setStatus(MBoolean.NO.toString());
        }

        return sysInst;
    }
    /**
     * 保存实体数据
     * @param request
     * @param sysInst
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @LogEnt(action = "save", module = "系统内核", submodule = "注册机构")
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysInst") @Valid SysInst sysInst, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(sysInst.getInstId())) {
            sysInst.setInstId(idGenerator.getSID());
            sysInstManager.doRegister(sysInst);
            msg = getMessage("sysInst.created", new Object[]{sysInst.getIdentifyLabel()}, "租用机构成功创建!");
        } else {
        	if(StringUtils.isNotEmpty(sysInst.getParentId())){
    			SysInst pInst=sysInstManager.get(sysInst.getParentId());
    			if(pInst!=null){
    				sysInst.setPath(pInst.getPath()+ sysInst.getInstId()+".");
    			}
    		}
            sysInstManager.update(sysInst);
            msg = getMessage("sysInst.updated", new Object[]{sysInst.getIdentifyLabel()}, "租用机构成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}
