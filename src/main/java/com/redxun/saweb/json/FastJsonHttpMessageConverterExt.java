package com.redxun.saweb.json;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

public class FastJsonHttpMessageConverterExt extends FastJsonHttpMessageConverter{
	private static SerializeConfig mapping = new SerializeConfig();
	private static String dateFormat;
	static {  
        dateFormat = "yyyy-MM-dd HH:mm:ss";
        mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
        mapping.put(Timestamp.class, new SimpleDateFormatSerializer(dateFormat));
    }
	
	@Override
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
			System.out.println("json------============================================================================");
		  	OutputStream out = outputMessage.getBody(); 
	        String text = JSON.toJSONString(obj, mapping);
	        byte[] bytes = text.getBytes(this.getCharset());
	        out.write(bytes);
	}
}
