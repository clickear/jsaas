
package com.redxun.oa.info.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.info.entity.InsNewsColumn;
import com.redxun.oa.info.manager.InsNewsColumnManager;
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
 * 公告栏目管理 管理
 * @author mansan
 */
@Controller
@RequestMapping("/oa/info/insNewsColumn/")
public class InsNewsColumnFormController extends BaseFormController {

    @Resource
    private InsNewsColumnManager insNewsColumnManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("insNewsColumn")
    public InsNewsColumn processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        InsNewsColumn insNewsColumn = null;
        if (StringUtils.isNotEmpty(id)) {
            insNewsColumn = insNewsColumnManager.get(id);
        } else {
            insNewsColumn = new InsNewsColumn();
        }

        return insNewsColumn;
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
    @LogEnt(action = "save", module = "oa", submodule = "公告栏目管理")
    public JsonResult save(HttpServletRequest request, @ModelAttribute("insNewsColumn") @Valid InsNewsColumn insNewsColumn, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(insNewsColumn.getId())) {
            insNewsColumn.setId(IdUtil.getId());
            insNewsColumnManager.create(insNewsColumn);
            msg = getMessage("insNewsColumn.created", new Object[]{insNewsColumn.getIdentifyLabel()}, "[公告栏目管理]成功创建!");
        } else {
            insNewsColumnManager.update(insNewsColumn);
            msg = getMessage("insNewsColumn.updated", new Object[]{insNewsColumn.getIdentifyLabel()}, "[公告栏目管理]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

