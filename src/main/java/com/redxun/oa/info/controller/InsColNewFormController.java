package com.redxun.oa.info.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.info.entity.InsColNew;
import com.redxun.oa.info.entity.InsColumn;
import com.redxun.oa.info.entity.InsNews;
import com.redxun.oa.info.manager.InsColNewManager;
import com.redxun.oa.info.manager.InsColumnManager;
import com.redxun.oa.info.manager.InsNewsManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * [InsColNew]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/info/insColNew/")
public class InsColNewFormController extends BaseFormController {

    @Resource
    private InsColNewManager insColNewManager;
    @Resource
    private InsColumnManager insColumnManager;
    @Resource
    private InsNewsManager insNewsManager;
    
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("insColNew")
    public InsColNew processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        InsColNew insColNew = null;
        if (StringUtils.isNotEmpty(id)) {
            insColNew = insColNewManager.get(id);
        } else {
            insColNew = new InsColNew();
        }

        return insColNew;
    }
    /**
     * 保存实体数据
     * @param request
     * @param insColNew
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("insColNew") @Valid InsColNew insColNew, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(insColNew.getId())) {
            insColNew.setId(idGenerator.getSID());
            insColNewManager.create(insColNew);
            msg = getMessage("insColNew.created", new Object[]{insColNew.getIdentifyLabel()}, "成功创建!");
        } else {
            insColNewManager.update(insColNew);
            msg = getMessage("insColNew.updated", new Object[]{insColNew.getIdentifyLabel()}, "成功更新!");
        }
        return new JsonResult(true, msg);
    }
    
    @RequestMapping(value="saveTime", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveTime(HttpServletRequest request, @ModelAttribute("insColNew") @Valid InsColNew insColNew, BindingResult result) {
        String newId=request.getParameter("newId");
        String colId =request.getParameter("colId");
        String id = request.getParameter("Id");
        InsColumn insColumn = insColumnManager.get(colId);
        InsNews insNews = insNewsManager.get(newId);
        insColNew.setInsColumn(insColumn);
        insColNew.setInsNews(insNews);
        insColNewManager.update(insColNew);
        return new JsonResult(true,"成功更新！");
    }
}

