package com.redxun.sys.org.controller;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.log.LogEnt;
import com.redxun.sys.org.entity.OsRelType;
import com.redxun.sys.org.manager.OsDimensionManager;
import com.redxun.sys.org.manager.OsRelTypeManager;

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
 * 关系类型管理
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/org/osRelType/")
public class OsRelTypeFormController extends BaseFormController {

    @Resource
    private OsRelTypeManager osRelTypeManager;
    @Resource
    private OsDimensionManager osDimensionManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("osRelType")
    public OsRelType processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        OsRelType osRelType = null;
        if (StringUtils.isNotEmpty(id)) {
            osRelType = osRelTypeManager.get(id);
        } else {
            osRelType = new OsRelType();
            osRelType.setIsDefault(MBoolean.NO.toString());
            osRelType.setIsSystem(MBoolean.NO.toString());
        }

        return osRelType;
    }
    /**
     * 保存实体数据
     * @param request
     * @param osRelType
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @LogEnt(action = "save", module = "组织架构", submodule = "关系类型")
    public JsonResult save(HttpServletRequest request, @ModelAttribute("osRelType") @Valid OsRelType osRelType, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(osRelType.getId())) {
            osRelType.setId(idGenerator.getSID());
            osRelTypeManager.create(osRelType);
            msg = getMessage("osRelType.created", new Object[]{osRelType.getIdentifyLabel()}, "关系类型成功创建!");
        } else {
        	if(osRelType.getDim1()!=null){
        		osRelTypeManager.detach(osRelType.getDim1());
        	}
        	if(osRelType.getDim2()!=null){
        		osRelTypeManager.detach(osRelType.getDim2());
        	}
            osRelTypeManager.update(osRelType);
            msg = getMessage("osRelType.updated", new Object[]{osRelType.getIdentifyLabel()}, "关系类型成功更新!");
        }
        return new JsonResult(true, msg);
    }
}

