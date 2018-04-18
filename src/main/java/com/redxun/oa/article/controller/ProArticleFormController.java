
package com.redxun.oa.article.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.article.entity.ProArticle;
import com.redxun.oa.article.manager.ProArticleManager;
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
 * 文章 管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/oa/article/proArticle/")
public class ProArticleFormController extends BaseFormController {

    @Resource
    private ProArticleManager proArticleManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("proArticle")
    public ProArticle processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        ProArticle proArticle = null;
        if (StringUtils.isNotEmpty(id)) {
            proArticle = proArticleManager.get(id);
        } else {
            proArticle = new ProArticle();
        }

        return proArticle;
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
    @LogEnt(action = "save", module = "oa", submodule = "文章")
    public JsonResult save(HttpServletRequest request, @ModelAttribute("proArticle") @Valid ProArticle proArticle, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if(StringUtils.isBlank(proArticle.getContent())){//内容为空
        	proArticle.setType("index");
        }else{
        	proArticle.setType("article");
        }
        if (StringUtils.isEmpty(proArticle.getId())) {
            proArticle.setId(IdUtil.getId());
            proArticleManager.create(proArticle);
            msg = getMessage("proArticle.created", new Object[]{proArticle.getIdentifyLabel()}, "[文章]成功创建!");
        } else {
            proArticleManager.update(proArticle);
            msg = getMessage("proArticle.updated", new Object[]{proArticle.getIdentifyLabel()}, "[文章]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg,proArticle.getId());
    }
}

