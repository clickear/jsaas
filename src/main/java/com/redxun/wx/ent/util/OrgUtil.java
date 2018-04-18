package com.redxun.wx.ent.util;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonResult;
import com.redxun.core.util.ExceptionUtil;
import com.redxun.core.util.HttpClientUtil;
import com.redxun.wx.ent.util.model.WxOrg;
import com.redxun.wx.ent.util.model.WxUser;

/**
 * 组织架构的API接口。
 * @author ray
 *
 */
public class OrgUtil {
	

	
	/**
	 * 创建组织。
	 * @param org
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static JsonResult<Void> createOrg(WxOrg org,String token) throws Exception{
		JsonResult<Void> result=new JsonResult<Void>(true);
		try{
			String url= ApiUrl.getCreateDepartmentUrl(token);
			
			String rtn= HttpClientUtil.postJson(url, org.toString());
			JSONObject jsonObj=JSONObject.parseObject(rtn);
			int errcode=jsonObj.getIntValue("errcode");
			if(errcode!=0){
				String errmsg=jsonObj.getString("errmsg");
				result.setSuccess(false);
				result.setMessage(errmsg);
			}
		}
		catch(Exception ex){
			result.setSuccess(false);
			result.setMessage(ExceptionUtil.getExceptionMessage(ex));
		}
		return result;
	}
	
	/**
	 * 创建用户。
	 * @param user
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static JsonResult<Void> createUser(WxUser user,String token) throws Exception{
		JsonResult<Void> result=new JsonResult<Void>(true);
		try{
			String url= ApiUrl.getUserCreateUrl(token);
			String rtn= HttpClientUtil.postJson(url, user.toString());
			JSONObject jsonObj=JSONObject.parseObject(rtn);
			int errcode=jsonObj.getIntValue("errcode");
			if(errcode!=0){
				//用户存在更新数据
				if(errcode==60102||errcode==60104){
					JsonResult<Void> updResult=updateUsers(user, token);
					if(!updResult.isSuccess()){
						result.setSuccess(false);
						result.setMessage(updResult.getMessage());
					}
				}
				else{
					String errmsg=jsonObj.getString("errmsg");
					result.setSuccess(false);
					result.setMessage(errmsg);
				}
				
			}
		}
		catch(Exception ex){
			result.setSuccess(false);
			result.setMessage(ExceptionUtil.getExceptionMessage(ex));
		}
		return result;
	}
	
	/**
	 * 批量删除用户。
	 * @param users		用户帐号使用逗号分隔。
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static JsonResult<Void> removeUsers(String users,String token) throws Exception{
		JsonResult<Void> result=new JsonResult<Void>(true);
		String url= ApiUrl.getDelUsersUrl(token);
		String[] aryUser=users.split(",");
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<aryUser.length;i++){
			if(i>0) sb.append(",");
			sb.append("\""+ aryUser[i] +"\"");
		}
		try{
			String json="{\"useridlist\": ["+sb.toString()+"] }";
			String rtn= HttpClientUtil.postJson(url, json);
			JSONObject jsonObj=JSONObject.parseObject(rtn);
			int errcode=jsonObj.getIntValue("errcode");
			if(errcode!=0){
				String errmsg=jsonObj.getString("errmsg");
				result.setSuccess(false);
				result.setMessage(errmsg);
			}
		}
		catch(Exception ex){
			result.setSuccess(false);
			result.setMessage(ExceptionUtil.getExceptionMessage(ex));
		}
		return result;
	}
	
	/**
	 * 更新人员信息。
	 * @param user
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static JsonResult<Void> updateUsers(WxUser user,String token) throws Exception{
		JsonResult<Void> result=new JsonResult<Void>(true);
		try{
			String url= ApiUrl.getUpdUserUrl(token);
			String rtn= HttpClientUtil.postJson(url, user.toString());
			JSONObject jsonObj=JSONObject.parseObject(rtn);
			int errcode=jsonObj.getIntValue("errcode");
			if(errcode!=0){
				String errmsg=jsonObj.getString("errmsg");
				result.setSuccess(false);
				result.setMessage(errmsg);
			}
		}
		catch(Exception ex){
			result.setSuccess(false);
			result.setMessage(ExceptionUtil.getExceptionMessage(ex));
		}
		return result;
	}
	
	
	
	
}
