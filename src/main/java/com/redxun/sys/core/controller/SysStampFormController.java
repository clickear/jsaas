
package com.redxun.sys.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.core.util.EncryptUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.SysStamp;
import com.redxun.sys.core.manager.SysStampManager;
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
import com.redxun.sys.log.LogEnt;

/**
 * office印章 管理
 * @author ray
 */
@Controller
@RequestMapping("/sys/core/sysStamp/")
public class SysStampFormController extends BaseFormController {

    @Resource
    private SysStampManager sysStampManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysStamp")
    public SysStamp processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        SysStamp sysStamp = null;
        if (StringUtils.isNotEmpty(id)) {
            sysStamp = sysStampManager.get(id);
        } else {
            sysStamp = new SysStamp();
        }

        return sysStamp;
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
    @LogEnt(action = "save", module = "sys", submodule = "office印章")
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysStamp") @Valid SysStamp sysStamp, BindingResult result) throws Exception {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String pwd=sysStamp.getPassword();
        sysStamp.setPassword(EncryptUtil.encrypt(pwd));
        String msg = null;
        if (StringUtils.isEmpty(sysStamp.getId())) {
            sysStamp.setId(IdUtil.getId());
            
            sysStampManager.create(sysStamp);
            msg = getMessage("sysStamp.created", new Object[]{sysStamp.getIdentifyLabel()}, "[office印章]成功创建!");
        } else {
            sysStampManager.update(sysStamp);
            msg = getMessage("sysStamp.updated", new Object[]{sysStamp.getIdentifyLabel()}, "[office印章]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

