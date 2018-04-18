package com.redxun.sys.org.controller;

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

import com.redxun.core.constants.MBoolean;
import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.log.LogEnt;
import com.redxun.sys.org.entity.OsDimension;
import com.redxun.sys.org.manager.OsDimensionManager;

/**
 * 组织维度管理
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/org/osDimension/")
public class OsDimensionFormController extends BaseFormController {

    @Resource
    private OsDimensionManager osDimensionManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("osDimension")
    public OsDimension processForm(HttpServletRequest request) {
        String dimId = request.getParameter("dimId");
        OsDimension osDimension = null;
        if (StringUtils.isNotEmpty(dimId)) {
            osDimension = osDimensionManager.get(dimId);
        } else {
            osDimension = new OsDimension();
            osDimension.setIsGrant(MBoolean.YES.name());
            osDimension.setIsCompose(MBoolean.NO.name());
            osDimension.setIsSystem(MBoolean.NO.toString());
            osDimension.setStatus(MBoolean.ENABLED.toString());
        }

        return osDimension;
    }
    /**
     * 保存实体数据
     * @param request
     * @param osDimension
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @LogEnt(action = "save", module = "组织架构", submodule = "组织维度")
    public JsonResult save(HttpServletRequest request, @ModelAttribute("osDimension") @Valid OsDimension osDimension, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }

        String msg = null;
        if (StringUtils.isEmpty(osDimension.getDimId())) {
        	osDimension.setDimId(idGenerator.getSID());
            osDimensionManager.create(osDimension);
            msg = getMessage("osDimension.created", new Object[]{osDimension.getIdentifyLabel()}, "组织维度成功创建!");
        } else {
            osDimensionManager.update(osDimension);
            msg = getMessage("osDimension.updated", new Object[]{osDimension.getIdentifyLabel()}, "组织维度成功更新!");
        }
        return new JsonResult(true, msg);
    }
}

