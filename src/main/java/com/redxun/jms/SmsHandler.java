package com.redxun.jms;

import com.redxun.core.jms.IJmsHandler;
import com.redxun.core.jms.MessageModel;
import com.redxun.core.util.HttpClientUtil;

public class SmsHandler implements IJmsHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "短信";
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "sms";
	}

	@Override
	public void handleMessage(MessageModel model) {
		
		
	}
	
	public static void main(String[] args) throws Exception {
		String str="<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:web=\"http://WebXml.com.cn/\">"
				+"<soap:Header/>"
   	+"<soap:Body>"
     + "<web:getRegionCountry/>"
     +"</soap:Body>"
   +"</soap:Envelope>"	;
		
		String url="http://ws.webxml.com.cn/WebServices/WeatherWS.asmx";
		
		String rtn=HttpClientUtil.postJson(url, str);
		
		System.out.println(rtn);
		
	}

}
