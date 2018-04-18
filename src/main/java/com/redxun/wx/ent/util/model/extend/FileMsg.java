package com.redxun.wx.ent.util.model.extend;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.redxun.wx.ent.util.model.BaseMsg;

/**
 * 文件消息。
 * @author ray
 *
 */
public class FileMsg extends BaseMsg {

	@Override
	public String getMsgtype() {
		return "file";
	}
	
	/**
	 * 这个为上传到微信企业号的文件ID.
	 */
	@JSONField(serializeUsing =ModelValueSerializer.class,name="file")
	private String mediaId="";

	
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		
		this.mediaId = mediaId;
	}
	
	public static class ModelValueSerializer implements ObjectSerializer {
		@Override
		public void write(JSONSerializer serializer, Object object, Object fieldName, java.lang.reflect.Type fieldType,
				int features) throws IOException {
			JSONObject obj=new JSONObject();
			obj.put("media_id", object.toString());
			serializer.write(obj);
		}
	}
	
	
	
	public static void main(String[] args) {
		FileMsg img=new FileMsg();
		img.setMediaId("111");
		System.out.println(img.toString());
	}
	
}
