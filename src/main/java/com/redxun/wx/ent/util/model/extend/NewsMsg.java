package com.redxun.wx.ent.util.model.extend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.redxun.wx.ent.util.model.BaseMsg;

/**
 * 图文消息
 * @author ray
 *
 */
public class NewsMsg extends BaseMsg{
	
	/**
	 * 消息为1-8条。
	 */
	@JSONField(serializeUsing=ModelValueSerializer.class)
	private List<News> news=new ArrayList<News>();

	public List<News> getNews() {
		return news;
	}

	public void setNews(List<News> news) {
		this.news = news;
	}
	
	public void addNews(News news_){
		this.news.add(news_);
	}

	@Override
	public String getMsgtype() {
		return "news";
	}
	
	public static class ModelValueSerializer implements ObjectSerializer {
		@Override
		public void write(JSONSerializer serializer, Object object, Object fieldName, java.lang.reflect.Type fieldType,
				int features) throws IOException {
			JSONObject obj=new JSONObject();
			obj.put("articles", object);
			serializer.write(obj);
		}
	}
	
	public static void main(String[] args) {
		NewsMsg img=new NewsMsg();
		News model=new News();
		model.setDescription("ddd");
		model.setTitle("国庆阅兵");
		
		img.addNews(model);
		
		System.out.println(img.toString());
	}
	
	
}
