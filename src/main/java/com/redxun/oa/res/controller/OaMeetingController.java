package com.redxun.oa.res.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.Page;
import com.redxun.core.query.QueryFilter;
import com.redxun.oa.info.manager.InfInnerMsgManager;
import com.redxun.oa.res.entity.OaMeetAtt;
import com.redxun.oa.res.entity.OaMeeting;
import com.redxun.oa.res.manager.OaMeetAttManager;
import com.redxun.oa.res.manager.OaMeetRoomManager;
import com.redxun.oa.res.manager.OaMeetingManager;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.org.entity.OsUser;
import com.redxun.sys.org.manager.OsUserManager;

/**
 * 会议申请管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/res/oaMeeting/")
public class OaMeetingController extends BaseListController{
    @Resource
    OaMeetingManager oaMeetingManager;
	@Resource
	OsUserManager osUserManager;
	@Resource
	OaMeetAttManager oaMeetAttManager;
	@Resource
	OaMeetRoomManager oaMeetRoomManager;
	@Resource
	InfInnerMsgManager infInnerMsgManager;

   
    /**
     * 查看开始时间大于当前时间的会议室
     * 
     * 
     * */
    @RequestMapping("dateList")
    @ResponseBody
    public JsonPageResult<OaMeeting> dateList(HttpServletRequest request) throws Exception{
		String roomId=request.getParameter("roomId");
		Page page = QueryFilterBuilder.createPage(request);
		List<OaMeeting> list=oaMeetingManager.getByDateRoomId(roomId);
		return new JsonPageResult<OaMeeting>(list,page.getTotalItems());
    }
	
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                oaMeetingManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除！");
    }
    
    
    
    /**
     * 查看明细
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("get")
    public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pkId = request.getParameter("pkId");
		OaMeeting oaMeeting = null;
		StringBuffer fullName = new StringBuffer();// 会议参与人名字
		String roomName = "";//会议室名
		if (StringUtils.isNotEmpty(pkId)) {
			oaMeeting = oaMeetingManager.get(pkId);
			roomName=oaMeeting.getOaMeetRoom().getName();
			List<OaMeetAtt> list = oaMeetAttManager.getByMeetId(pkId);
			for (OaMeetAtt oAtt : list) {
				fullName.append(oAtt.getFullName()).append(",");
			}
			if (fullName.length() > 0) {// 删除最后一个逗号
				fullName.deleteCharAt(fullName.length() - 1);
			}
			if (StringUtils.isNotEmpty(oaMeeting.getHostUid())) {// 获取到主持人的名字
				OsUser osUser = osUserManager.get(oaMeeting.getHostUid());
				if (osUser != null) {
					oaMeeting.setHostName(osUser.getFullname());
				}
			}
			if (StringUtils.isNotEmpty(oaMeeting.getRecordUid())) {// 获取记录人的名字
				OsUser osUser = osUserManager.get(oaMeeting.getRecordUid());
				if (osUser != null) {
					oaMeeting.setRecordName(osUser.getFullname());
				}
			}
		} else {
			oaMeeting = new OaMeeting();
		}
		List<OaMeetAtt> meetatt=oaMeetAttManager.getByMeetIdSummary(pkId);
		return getPathView(request).addObject("oaMeeting", oaMeeting)
				.addObject("fullName", fullName).addObject("roomName", roomName).addObject("meetatt", meetatt);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pkId = request.getParameter("pkId");
		OaMeeting oaMeeting = null;
		StringBuffer userId = new StringBuffer();// 参与人Id
		StringBuffer fullName = new StringBuffer();// 参与人名字
		String roomName = "";//会议室名
		String roomId = "";//会议室ID
		if (StringUtils.isNotEmpty(pkId)) {
			oaMeeting = oaMeetingManager.get(pkId);
			roomName=oaMeeting.getOaMeetRoom().getName();
			roomId=oaMeeting.getOaMeetRoom().getRoomId();
			List<OaMeetAtt> list = oaMeetAttManager.getByMeetId(pkId);
			for (OaMeetAtt oAtt : list) {
				userId.append(oAtt.getUserId()).append(",");
				fullName.append(oAtt.getFullName()).append(",");
			}
			if (fullName.length() > 0) {// 删除最后一个逗号
				userId.deleteCharAt(userId.length() - 1);
				fullName.deleteCharAt(fullName.length() - 1);
			}
			if (StringUtils.isNotEmpty(oaMeeting.getHostUid())) {// 获取到主持人的名字
				OsUser osUser = osUserManager.get(oaMeeting.getHostUid());
				if (osUser != null) {
					oaMeeting.setHostName(osUser.getFullname());
				}
			}
			if (StringUtils.isNotEmpty(oaMeeting.getRecordUid())) {// 获取记录人的名字
				OsUser osUser = osUserManager.get(oaMeeting.getRecordUid());
				if (osUser != null) {
					oaMeeting.setRecordName(osUser.getFullname());
				}
			}

		} else {
			oaMeeting = new OaMeeting();
		}
		return getPathView(request).addObject("oaMeeting", oaMeeting)
				.addObject("userId", userId).addObject("fullName", fullName).addObject("roomId",roomId).addObject("roomName",roomName);
    }
    
    @RequestMapping("summary")
    public ModelAndView summary(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pkId = request.getParameter("pkId");
		OaMeeting oaMeeting = null;
		StringBuffer userId = new StringBuffer();// 参与人Id
		StringBuffer fullName = new StringBuffer();// 参与人名字
		String roomName = "";//会议室名
		String roomId = "";//会议室ID
		if (StringUtils.isNotEmpty(pkId)) {
			oaMeeting = oaMeetingManager.get(pkId);
			roomName=oaMeeting.getOaMeetRoom().getName();
			roomId=oaMeeting.getOaMeetRoom().getRoomId();
			List<OaMeetAtt> list = oaMeetAttManager.getByMeetId(pkId);
			for (OaMeetAtt oAtt : list) {
				userId.append(oAtt.getUserId()).append(",");
				fullName.append(oAtt.getFullName()).append(",");
			}
			if (fullName.length() > 0) {// 删除最后一个逗号
				userId.deleteCharAt(userId.length() - 1);
				fullName.deleteCharAt(fullName.length() - 1);
			}
			if (StringUtils.isNotEmpty(oaMeeting.getHostUid())) {// 获取到主持人的名字
				OsUser osUser = osUserManager.get(oaMeeting.getHostUid());
				if (osUser != null) {
					oaMeeting.setHostName(osUser.getFullname());
				}
			}
			if (StringUtils.isNotEmpty(oaMeeting.getRecordUid())) {// 获取记录人的名字
				OsUser osUser = osUserManager.get(oaMeeting.getRecordUid());
				if (osUser != null) {
					oaMeeting.setRecordName(osUser.getFullname());
				}
			}

		} else {
			oaMeeting = new OaMeeting();
		}
		return getPathView(request).addObject("oaMeeting", oaMeeting)
				.addObject("userId", userId).addObject("fullName", fullName).addObject("roomId",roomId).addObject("roomName",roomName);
    }
    /**
     * 保存会议纪要
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
     * */
	@RequestMapping("summaryUpdate")
    @ResponseBody
    public JsonResult summaryUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String summary=request.getParameter("summary");
		String pkId = request.getParameter("meetId");
		OaMeeting oaMeeting=oaMeetingManager.get(pkId);
		oaMeeting.setSummary(summary);
		oaMeetingManager.saveOrUpdate(oaMeeting);
    	return new JsonResult(true,"成功保存会议纪要！");
    }
	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return oaMeetingManager;
	}

}
