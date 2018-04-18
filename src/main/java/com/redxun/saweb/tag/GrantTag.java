package com.redxun.saweb.tag;

import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.redxun.org.api.model.IUser;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.security.provider.SecurityDataSourceProvider;
/**
 * 用户显示标签
 * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class GrantTag extends TagSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	SecurityDataSourceProvider securityDataSourceProvider;
	//授权的Url
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	@Override
	public int doStartTag() throws JspException {
		 JspWriter out=pageContext.getOut();
		 try{
			 IUser curUser=ContextUtil.getCurrentUser();
			 if(curUser.isSuperAdmin()){
				 return EVAL_PAGE;
			 }
			 //空URL
			 if(StringUtils.isEmpty(url)){
				 out.println("");
				 return SKIP_BODY;
			 }
			 
			 Set<String> groupIds=curUser.getGroupIds();
			 Set<String> grantGroupIds= securityDataSourceProvider.getMenuGroupIdsMap().get(url);
			 if(grantGroupIds==null){
				 return SKIP_BODY;
			 }
			 for(String gId:groupIds){
				 if(grantGroupIds.contains(gId)){
					 return EVAL_PAGE;
				 }
			 }
			 //获得当前用户组，判断用户组内是否包含该功能
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		return EVAL_PAGE;
	}
}
