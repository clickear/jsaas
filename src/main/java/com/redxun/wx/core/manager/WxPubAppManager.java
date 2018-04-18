
package com.redxun.wx.core.manager;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.groovy.runtime.NullObject;
import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.saweb.util.IdUtil;
import com.redxun.wx.core.dao.WxPubAppDao;
import com.redxun.wx.core.dao.WxPubAppQueryDao;
import com.redxun.wx.core.entity.WxPubApp;
import com.redxun.wx.util.TokenModel;
import com.redxun.wx.util.TokenUtil;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;

/**
 * 
 * <pre> 
 * 描述：公众号管理 处理接口
 * 作者:ray
 * 日期:2017-06-29 16:57:29
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class WxPubAppManager extends ExtBaseManager<WxPubApp>{
	@Resource
	private WxPubAppDao wxPubAppDao;
	@Resource
	private WxPubAppQueryDao wxPubAppQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return wxPubAppDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return wxPubAppQueryDao;
	}
	
	public WxPubApp getByAppId(String appId){
		return wxPubAppDao.getByAppId(appId);
	}
	/**
	 * 转换菜单的json配置成list列表
	 * @param menuConfig
	 * @return
	 */
	public List<JSONObject> parseMenus(String menuConfig){
		JSONObject menuJson=JSONObject.fromObject(menuConfig);
		if(menuJson.isNullObject()){
			return new ArrayList<JSONObject>();
		}
		JSONObject btnJson=menuJson.getJSONObject("menu");
		JSONArray btnArray;
		if(btnJson.isNullObject()){
			btnArray=menuJson.getJSONArray("button");
		}else{
			btnArray=btnJson.getJSONArray("button");
		}
		
		List<JSONObject> jsonObjects=new ArrayList<JSONObject>();
		for(int i=0;i<btnArray.size();i++){
			JSONObject obj=btnArray.getJSONObject(i);
			String key=obj.get("key")==null?IdUtil.getId():obj.getString("key");
			obj.put("key", key);
			obj.put("parent", "0");
			if(obj.get("sub_button")!=null){
				JSONArray subArray=obj.getJSONArray("sub_button");
				if(subArray.size()>0){//有子菜单
					obj.put("type", "sub");
					for(int j=0;j<subArray.size();j++){
						JSONObject subBtn=subArray.getJSONObject(j);
						subBtn.put("parent", key);
						subBtn.put("key", IdUtil.getId());
						jsonObjects.add(subBtn);//加入返回list
					}
				}
			}
			jsonObjects.add(obj);//加入返回list
		}
		return jsonObjects;
	}
	/**
	 * 获取accessToken
	 * @param pubId
	 * @return
	 * @throws Exception
	 */
	public String getAccessToken(String pubId) throws Exception{
		WxPubApp wxPubApp=get(pubId);
		String appId=wxPubApp.getAppId();
		String secret=wxPubApp.getSecret();
		TokenModel tokenModel=TokenUtil.getToken(appId, secret, false);
		return tokenModel.getToken();
	}
}
