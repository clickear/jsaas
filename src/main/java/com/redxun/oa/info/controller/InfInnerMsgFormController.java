package com.redxun.oa.info.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.org.manager.OsUserManager;
import com.redxun.oa.info.entity.InfInbox;
import com.redxun.oa.info.entity.InfInnerMsg;
import com.redxun.oa.info.manager.InfInboxManager;
import com.redxun.oa.info.manager.InfInnerMsgManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * [InfInnerMsg]管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/oa/info/infInnerMsg/")
public class InfInnerMsgFormController extends BaseFormController {

	@Resource
	private InfInnerMsgManager infInnerMsgManager;
	@Resource
	private OsUserManager osUserManager;
	@Resource
	private InfInboxManager infInboxManager;

	/**
	 * 处理表单
	 * 
	 * @param request
	 * @return
	 */
	@ModelAttribute("infInnerMsg")
	public InfInnerMsg processForm(HttpServletRequest request) {
		String msgId = request.getParameter("msgId");
		InfInnerMsg infInnerMsg = null;
		if (StringUtils.isNotEmpty(msgId)) {
			infInnerMsg = infInnerMsgManager.get(msgId);
		} else {
			infInnerMsg = new InfInnerMsg();
		}

		return infInnerMsg;
	}

	/**
	 * 保存实体数据
	 * 
	 * @param request
	 * @param infInnerMsg
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult save(HttpServletRequest request,
			@ModelAttribute("infInnerMsg") @Valid InfInnerMsg infInnerMsg,
			BindingResult result) {

		if (result.hasFieldErrors()) {
			return new JsonResult(false, getErrorMsg(result));
		}
		String msg = null;
		if (StringUtils.isEmpty(infInnerMsg.getMsgId())) {
			infInnerMsg.setMsgId(idGenerator.getSID());
			infInnerMsgManager.create(infInnerMsg);
			msg = getMessage("infInnerMsg.created",
					new Object[] { infInnerMsg.getIdentifyLabel() },
					"[InfInnerMsg]成功创建!");
		} else {
			infInnerMsgManager.update(infInnerMsg);
			msg = getMessage("infInnerMsg.updated",
					new Object[] { infInnerMsg.getIdentifyLabel() },
					"[InfInnerMsg]成功更新!");
		}
		// saveOpMessage(request, msg);
		return new JsonResult(true, msg);
	}
	
	/**
	 * 发送内部短消息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "sendMsg", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult sendMsg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String userId=request.getParameter("userId");
		String groupId=request.getParameter("groupId");
		String content = request.getParameter("context");//Msg内容
		String linkMsg = request.getParameter("linkMsg");//消息携带连接
		

		InfInnerMsg infInnerMsg =infInnerMsgManager.sendMessage(userId, groupId, content, linkMsg,false);
		
		
		String msg = getMessage("infInnerMsg.created",
				new Object[] { infInnerMsg.getIdentifyLabel() },
				"消息成功发送!");
		return new JsonResult(true, msg);
	}
}
