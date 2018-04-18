package com.redxun.oa.info.service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;

import com.redxun.core.util.Dom4jUtil;
import com.redxun.oa.info.entity.PortalConfig;
/**
 * 首页Portal配置
 * @author mansan
 *
 */
public class PortalConfigService implements InitializingBean{
	
	private static Map<String,PortalConfig> portalConfigsMap=new LinkedHashMap<String,PortalConfig>();
	
	//初始化模板设置
	public void initPortalConfigs() throws IOException{
		org.springframework.core.io.Resource resource = new ClassPathResource("templates/portal/portal-config.xml");
		Document doc = Dom4jUtil.load(resource.getInputStream());
		List<Node> nodes = doc.getRootElement().selectNodes("/templates/template");
		for (Node template : nodes) {
			Element el = (Element) template;
			String id = el.attributeValue("id");
			String name = el.attributeValue("name");
			String service = el.attributeValue("service");

			PortalConfig config=new PortalConfig(id,name,service);
			portalConfigsMap.put(id,config);
		}
	}
	
	public Map<String,PortalConfig> getPortalConfigMap(){
		return portalConfigsMap;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initPortalConfigs();
	}
	
}
