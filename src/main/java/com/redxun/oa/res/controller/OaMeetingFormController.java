package com.redxun.oa.res.controller;

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
import com.redxun.oa.info.manager.InfInnerMsgManager;
import com.redxun.oa.res.entity.OaMeetAtt;
import com.redxun.oa.res.entity.OaMeetRoom;
import com.redxun.oa.res.entity.OaMeeting;
import com.redxun.oa.res.manager.OaMeetAttManager;
import com.redxun.oa.res.manager.OaMeetRoomManager;
import com.redxun.oa.res.manager.OaMeetingManager;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.manager.SysFileManager;
/**
 * 会议申请管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/res/oaMeeting/")
public class OaMeetingFormController extends BaseFormController {

    @Resource
    private OaMeetingManager oaMeetingManager;
    @Resource
	OaMeetRoomManager oaMeetRoomManager;
	@Resource
	OaMeetAttManager oaMeetAttManager;
	@Resource
	InfInnerMsgManager infInnerMsgManager;
    @Resource
    private SysFileManager sysFileManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("oaMeeting")
    public OaMeeting processForm(HttpServletRequest request) {
        String meetId = request.getParameter("meetId");
        OaMeeting oaMeeting = null;
        if (StringUtils.isNotEmpty(meetId)) {
            oaMeeting = oaMeetingManager.get(meetId);
        } else {
            oaMeeting = new OaMeeting();
        }

        return oaMeeting;
    }
    /**
     * 保存实体数据
     * @param request
     * @param oaMeeting
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("oaMeeting") @Valid OaMeeting oaMeeting, BindingResult result) {

		if (result.hasFieldErrors()) {
			return new JsonResult(false, getErrorMsg(result));
		}
		String  msg = null;
		String userId = request.getParameter("receive");
		String roomId=request.getParameter("roomId");
//		String starDate=request.getParameter("start");
//		String endDate=request.getParameter("end");
//		Date start=DateUtil.parseDate(starDate);
//		Date end=DateUtil.parseDate(endDate);
//		boolean flag=oaMeetingManager.getByRoomId(roomId, start, end);
		oaMeeting.getOaMeetAtts().clear();
		if (StringUtils.isNotEmpty(userId)) {
			String[] userIds = userId.split(",");
			String[] fullName = request.getParameter("fullName").split(",");// 人员隶属那个会议
			OaMeetAtt oaMeetAtt = null;
			if (userIds.length > 0) {
				for (int i = 0; i < userIds.length; i++) {
					if (StringUtils.isNotEmpty(oaMeeting.getMeetId())) {
						oaMeetAtt=oaMeetAttManager.getMeetIdByuserId(oaMeeting.getMeetId(),userIds[i]);
						if (oaMeetAtt != null) {
							continue;
						}
					}
					oaMeetAtt = new OaMeetAtt();
					oaMeetAtt.setAttId(idGenerator.getSID());
					oaMeetAtt.setOaMeeting(oaMeeting);
					oaMeetAtt.setUserId(userIds[i]);// 设置会议人Id
					oaMeetAtt.setFullName(fullName[i]);// 设置会议人名
					oaMeeting.getOaMeetAtts().add(oaMeetAtt);
					infInnerMsgManager.sendMessage(userIds[i],"","会议开始时间"+oaMeeting.getStart()+"地点在"+oaMeeting.getLocation(),"",false);
				}
				
			}
		}
		if (StringUtils.isEmpty(oaMeeting.getMeetId())) {
		
			OaMeetRoom oRoom=oaMeetRoomManager.get(roomId);
			oaMeeting.setMeetId(idGenerator.getSID());
			oaMeeting.setOaMeetRoom(oRoom);
			oaMeetingManager.create(oaMeeting);
			msg = getMessage("oaMeeting.created",
					new Object[] { oaMeeting.getName() },
					"会议申请成功创建!");
			

		} else {
			OaMeetRoom oRoom=oaMeetRoomManager.get(roomId);
			oaMeeting.setOaMeetRoom(oRoom);
			oaMeetingManager.update(oaMeeting);
			msg = getMessage("oaMeeting.updated",
					new Object[] { oaMeeting.getName() },
					"会议申请成功更新!");
		}
		// saveOpMessage(request, msg);
		return new JsonResult(true, msg);
    }
}

