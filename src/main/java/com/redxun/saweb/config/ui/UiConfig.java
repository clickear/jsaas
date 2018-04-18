package com.redxun.saweb.config.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.redxun.bpm.bm.entity.BpmFormAtt;
import com.redxun.bpm.bm.manager.BpmFormAttManager;
import com.redxun.bpm.form.entity.FormFieldControl;
import com.redxun.core.engine.FreemarkEngine;
import com.redxun.core.util.Dom4jUtil;

/**
 * UI配置类
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 *
 */
public class UiConfig implements InitializingBean{
	
	private Log logger=LogFactory.getLog(UiConfig.class);
	
	@javax.annotation.Resource
	FreemarkEngine freemarkEngine;
	
	@javax.annotation.Resource
	BpmFormAttManager bpmFormAttManager;
	
	
	/**
	 * 系统皮肤配置
	 */
	private static List<UiSkin> skins=new ArrayList<UiSkin>();
	/**
	 * UEditor扩展的miniui控件的配置
	 */
	private static Map<String,String> ueditorControls=new HashMap<String,String>();
	
	@Override
	public void afterPropertiesSet() throws Exception{
		loadSkins();
		loadUeditorControls();
	}
	
	/**
	 * 加载系统皮肤
	 * @throws Exception
	 */
	public void loadSkins() throws Exception {
		Resource resource = new ClassPathResource("config/miniui-skins.properties");
		 Properties pros=new Properties();
		 pros.load(resource.getInputStream());
		 Iterator<Object> keyIt=pros.keySet().iterator();
		 while(keyIt.hasNext()){
			 String key=(String)keyIt.next();
			 if(StringUtils.isEmpty(key)) continue;
			 String val=pros.getProperty(key);
			 UiSkin skin=new UiSkin(key,val);
			 skins.add(skin);
		 }
	}
	
	public void loadUeditorControls() throws Exception{
		Resource resource = new ClassPathResource("config/miniui-control-defs.xml");
		Document doc=Dom4jUtil.load(resource.getInputStream());
		Element rootEl=doc.getRootElement();
		Iterator<Element> conEls=rootEl.elementIterator();
		while(conEls.hasNext()){
			Element el=conEls.next();
			String id=el.attributeValue("id");
			String config=(String)el.getData();
			ueditorControls.put(id, config.trim());
		}
	}

	public List<UiSkin> getSkins() {
		return skins;
	}

	public static Map<String, String> getUeditorControls() {
		return ueditorControls;
	}
	
	
	 /**
		 * 通过模型属性初始化控件
		 * @param bpmFormAtt
		 * @return
		 */
		public FormFieldControl genControlHtml(BpmFormAtt bpmFormAtt){
			FormFieldControl control=new FormFieldControl();
			Map<String,Object> params=new HashMap<String, Object>();
			String controlConfig=getUeditorControls().get(bpmFormAtt.getCtlType());
			if(BpmFormAtt.DATA_TYPE_STRING.equals(bpmFormAtt.getDataType())||
					BpmFormAtt.DATA_TYPE_DATE.equals(bpmFormAtt.getDataType()) 
					||BpmFormAtt.DATA_TYPE_NUMBER.equals(bpmFormAtt.getDataType())){
				
				params.put("name", bpmFormAtt.getKey());
				params.put("label", bpmFormAtt.getTitle());
				params.put("required", bpmFormAtt.getRequired());
				params.put("style", "width:98%");
				params.put("minlen", "0");
				params.put("maxlen", bpmFormAtt.getLen());
				params.put("prec", bpmFormAtt.getPrec());
				params.put("mwidth", "90");
				params.put("wunit", "%");
				params.put("mheight", "0");
				params.put("hunit","px");
				params.put("allowinput", "true");
				params.put("value", bpmFormAtt.getDefVal());
				
				try{
					String controlHtml=freemarkEngine.parseByStringTemplate(params,controlConfig);
					
					control.setFieldLabel(bpmFormAtt.getTitle());
					control.setFieldName(bpmFormAtt.getKey());
					control.setControlHtml(controlHtml);
					return control;
				}catch(Exception ex){
					logger.error(ex.getMessage());
				}
			}else if(BpmFormAtt.DATA_TYPE_COMPOSITE_COLL.equals(bpmFormAtt.getDataType()) && StringUtils.isNotEmpty(bpmFormAtt.getType())){//TODO Collection
				try{
					params.put("name", bpmFormAtt.getKey());
					params.put("label", bpmFormAtt.getTitle());
					params.put("style", "width:98%");

					List<FormFieldControl> subFields=new ArrayList<FormFieldControl>();
					List<BpmFormAtt> atts=bpmFormAttManager.getByFmId(bpmFormAtt.getType());
					for(BpmFormAtt att:atts){
						FormFieldControl ctr=genControlHtml(att);
						subFields.add(ctr);
					}
					params.put("subFields",subFields);
					String controlHtml=freemarkEngine.parseByStringTemplate(params,controlConfig);
					control.setFieldLabel(bpmFormAtt.getTitle());
					control.setFieldName(bpmFormAtt.getKey());
					control.setControlHtml(controlHtml);
					return control;
				}catch(Exception e){
					logger.error(e.getMessage());
				}
			}
			return null;
		}
	
}
