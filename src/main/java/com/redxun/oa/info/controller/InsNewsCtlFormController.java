
package com.redxun.oa.info.controller;

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

import com.redxun.core.json.JsonResult;
import com.redxun.core.util.StringUtil;
import com.redxun.oa.info.entity.InsNews;
import com.redxun.oa.info.entity.InsNewsCtl;
import com.redxun.oa.info.manager.InsNewsCtlManager;
import com.redxun.oa.info.manager.InsNewsManager;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.saweb.util.IdUtil;
import com.redxun.sys.log.LogEnt;

/**
 * 新闻公告权限表 管理
 * @author mansan
 */
@Controller
@RequestMapping("/oa/info/insNewsCtl/")
public class InsNewsCtlFormController extends BaseFormController {

    @Resource
    private InsNewsCtlManager insNewsCtlManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("insNewsCtl")
    public InsNewsCtl processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        InsNewsCtl insNewsCtl = null;
        if (StringUtils.isNotEmpty(id)) {
            insNewsCtl = insNewsCtlManager.get(id);
        } else {
            insNewsCtl = new InsNewsCtl();
        }

        return insNewsCtl;
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
    @LogEnt(action = "save", module = "oa", submodule = "新闻公告权限表")
    public JsonResult save(HttpServletRequest request, @ModelAttribute("insNewsCtl") @Valid InsNewsCtl insNewsCtl, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(insNewsCtl.getCtlId())) {
            insNewsCtl.setCtlId(IdUtil.getId());
            insNewsCtlManager.create(insNewsCtl);
            msg = getMessage("insNewsCtl.created", new Object[]{insNewsCtl.getIdentifyLabel()}, "[新闻公告权限表]成功创建!");
        } else {
            insNewsCtlManager.update(insNewsCtl);
            msg = getMessage("insNewsCtl.updated", new Object[]{insNewsCtl.getIdentifyLabel()}, "[新闻公告权限表]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
    
    @SuppressWarnings("rawtypes")
    @RequestMapping("saveCtl")
    @ResponseBody
	public JsonResult saveCtl(HttpServletRequest request){
    	String newsId = request.getParameter("newsId");		
		String checkAuserIds = request.getParameter("checkAuserIds");// 打印权限
		String checkAgroupIds = request.getParameter("checkAgroupIds");
		String downAuserIds = request.getParameter("downAuserIds");// 下载权限
		String downAgroupIds = request.getParameter("downAgroupIds");
		
		if(StringUtil.isEmpty(newsId)) return new JsonResult(false);
		insNewsCtlManager.deleteByNewsId(newsId);

		if(StringUtil.isNotEmpty(checkAgroupIds)||StringUtil.isNotEmpty(checkAuserIds)){
			InsNewsCtl ctl = new InsNewsCtl();
			ctl.setCtlId(IdUtil.getId());
			ctl.setNewsId(newsId);
			ctl.setRight(InsNewsCtl.CTL_RIGHT_CHECK);
			ctl.setType(InsNewsCtl.CTL_TYPE_LIMIT);
			if(StringUtil.isNotEmpty(checkAgroupIds)){
				ctl.setGroupId(checkAgroupIds);
			}
			if(StringUtil.isNotEmpty(checkAuserIds)){
				ctl.setUserId(checkAuserIds);
			}
			insNewsCtlManager.create(ctl);
		}else{
			InsNewsCtl ctl = new InsNewsCtl();
			ctl.setCtlId(IdUtil.getId());
			ctl.setNewsId(newsId);
			ctl.setRight(InsNewsCtl.CTL_RIGHT_CHECK);
			ctl.setType(InsNewsCtl.CTL_TYPE_ALL);
			insNewsCtlManager.create(ctl);
		}
		
		if(StringUtil.isNotEmpty(downAgroupIds)||StringUtil.isNotEmpty(downAuserIds)){
			InsNewsCtl ctl = new InsNewsCtl();
			ctl.setCtlId(IdUtil.getId());
			ctl.setNewsId(newsId);
			ctl.setRight(InsNewsCtl.CTL_RIGHT_DOWN);
			ctl.setType(InsNewsCtl.CTL_TYPE_LIMIT);
			if(StringUtil.isNotEmpty(downAgroupIds)){
				ctl.setGroupId(downAgroupIds);
			}
			if(StringUtil.isNotEmpty(downAuserIds)){
				ctl.setUserId(downAuserIds);
			}
			insNewsCtlManager.create(ctl);
		}else{
			InsNewsCtl ctl = new InsNewsCtl();
			ctl.setCtlId(IdUtil.getId());
			ctl.setNewsId(newsId);
			ctl.setRight(InsNewsCtl.CTL_RIGHT_DOWN);
			ctl.setType(InsNewsCtl.CTL_TYPE_ALL);
			insNewsCtlManager.create(ctl);
		}		
    	
    	return new JsonResult(true,"成功保存权限");
    }
}

