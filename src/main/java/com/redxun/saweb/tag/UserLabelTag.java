package com.redxun.saweb.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.redxun.org.api.model.IUser;
import com.redxun.org.api.service.GroupService;
import com.redxun.org.api.service.UserService;
import com.redxun.saweb.util.WebAppUtil;
/**
 * 用户显示标签
 * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class UserLabelTag extends TagSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userId;
	
	private String showDep;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getShowDep() {
		return showDep;
	}

	public void setShowDep(String showDep) {
		this.showDep = showDep;
	}

	@Override
	public int doStartTag() throws JspException {
		 JspWriter out=pageContext.getOut();
		 try{
			 if(StringUtils.isEmpty(userId)){
				 out.println("");
				 return SKIP_BODY;
			 }
			 UserService userService=WebAppUtil.getBean(UserService.class);
			IUser user=userService.getByUserId(userId);
			if("true".equals(showDep)){
				 GroupService groupService=WebAppUtil.getBean(GroupService.class);
				String depPath=groupService.getFullDepNames(userId);
				out.println(depPath);
			}
			 out.println(user.getFullname());
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		return SKIP_BODY;
	}
}
