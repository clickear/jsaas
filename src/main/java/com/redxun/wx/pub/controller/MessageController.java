package com.redxun.wx.pub.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpResponse;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.redxun.core.util.AppBeanUtil;
import com.redxun.core.util.Dom4jUtil;
import com.redxun.core.util.FileUtil;
import com.redxun.wx.core.entity.WxPubApp;
import com.redxun.wx.core.manager.WxPubAppManager;
import com.redxun.wx.pub.IMessageHandler;

/**
 * 微信应用控制器
 * @author mansan
 */
@Controller
public class MessageController {
	
	/**
	 * validToken
	 * @param request
	 * @param response
	 */
	@RequestMapping(value={"/pubReceive/{alias}"})
	public void validToken(HttpServletRequest request,HttpServletResponse response,@PathVariable("alias") String alias){
		try {  
			String appId=alias;
			WxPubAppManager wxPubAppManager=AppBeanUtil.getBean(WxPubAppManager.class);
			WxPubApp wxPubApp=wxPubAppManager.getByAppId(appId);
			String token=wxPubApp.getTOKEN();
			response.getWriter().print(validateToken(request, token)); 
			receiveMsg(request, response,wxPubApp);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
	
	
	/**
	 * 验证token
	 * @param request
	 * @param token
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private String validateToken(HttpServletRequest request,String token) throws NoSuchAlgorithmException{
		 // 开发者提交信息后，微信服务器将发送GET请求到填写的服务器地址URL上，GET请求携带参数  
		String signature = request.getParameter("signature");// 微信加密签名（token、timestamp、nonce。）  
		String timestamp = request.getParameter("timestamp");// 时间戳  
		String nonce = request.getParameter("nonce");// 随机数  
		String echostr = request.getParameter("echostr");// 随机字符串  
		// 将token、timestamp、nonce三个参数进行字典序排序  
		String[] params = new String[] {token, timestamp, nonce };  
		Arrays.sort(params);  
		// 将三个参数字符串拼接成一个字符串进行sha1加密  
		String clearText = params[0] + params[1] + params[2];  
		String algorithm = "SHA-1";  
		byte[] bytes= MessageDigest.getInstance(algorithm).digest((clearText).getBytes());
		String sign = new String(Hex.encodeHex(bytes));  
		if (signature.equals(sign)) {  
		   return echostr;  
		}
		return null;
	}
	
	
	
	/**
	 * 接收消息。
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value={"/pubReceive"},method=RequestMethod.POST)
	public void receiveMsg(HttpServletRequest request,HttpServletResponse response,WxPubApp wxPubApp) throws IOException{
		String xml=FileUtil.inputStream2String(request.getInputStream());
		Document doc=Dom4jUtil.stringToDocument(xml);
		Element root= doc.getRootElement();
		List els= root.elements();
		Map<String,String> msgMap=new HashMap<String, String>();
		msgMap.put("appId", wxPubApp.getAppId());
		msgMap.put("pubId", wxPubApp.getId());
		for(Object o:els){
			Element el=(Element)o;
			msgMap.put(el.getName() , el.getStringValue());
		}
		Collection<IMessageHandler> list=(Collection<IMessageHandler>) AppBeanUtil.getBeanList(IMessageHandler.class);
		for(IMessageHandler handler:list){
			if(msgMap.get("MsgType").equals(handler.getType())){
				handler.handle(msgMap);
			}
		}
	}
	
	
	
	

}
