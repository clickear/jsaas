package com.redxun.sys.core.manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.redxun.bpm.view.control.MiniControlParseConfig;
import com.redxun.core.dao.IDao;
import com.redxun.core.engine.FreemarkEngine;
import com.redxun.core.manager.BaseManager;
import com.redxun.sys.core.dao.SysReportDao;
import com.redxun.sys.core.entity.SysReport;
import com.redxun.ui.query.QueryControlParseConfig;
import com.redxun.ui.query.QueryControlParseHandler;

/**
 * <pre>
 * 描述：SysReport业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class SysReportManager extends BaseManager<SysReport> {
	@Resource
	private SysReportDao sysReportDao;
	@Resource
	private FreemarkEngine freemarkEngine;
	@Resource
	MiniControlParseConfig miniControlParseConfig;
	@Resource
	SysBoListManager sysBoListManager;
	@Resource
	QueryControlParseConfig queryControlParseConfig;

	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysReportDao;
	}

	/**
	 * 解析模板HTML
	 * 
	 * @param html
	 * @param params
	 * @param modelJson
	 * @return
	 */
	public Map<String, Object> parseHtml(String html, Map<String, Object> params, String modelJson) {
		String afterHtml = null;
		Map<String, Object> parameters = new HashMap<String, Object>();

		try {
			afterHtml = freemarkEngine.parseByStringTemplate(params, html);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			afterHtml = html;
		}
		// 获得modelJson
		JSONObject jsonObj = JSONObject.fromObject(modelJson);

		Document doc = Jsoup.parse(afterHtml);
		// 查找有该样式的控件
		Elements ctls = doc.select(".rxc");

		Iterator<Element> it = ctls.iterator();

		while (it.hasNext()) {
			Element el = it.next();

			// 取得控件的名字
			String name = el.attr("name");

			if (StringUtils.isEmpty(name)) {// StringUtils.isEmpty(val)
				continue;
			}
			String value = el.attr("value");

			if (StringUtils.isEmpty(value)) {// StringUtils.isEmpty(val)
				continue;
			}

			parameters.put(name, value);

/*			String plugins = el.attr("plugins");

			if (StringUtils.isEmpty(plugins)) {
				continue;
			}
			// 通过视图来执行
			MiniViewHanlder handler = miniControlParseConfig.getElementViewHandler(plugins);
			handler.parse(el, params, jsonObj);*/
		}

		// head中会带有script及style的内容，需要返回给前台进行解析，Js可以有效获得变量的内容值
		// return doc.head().html() + doc.body().html();
		return parameters;
	}
	
	public String transferGridJsonToMiniWidget(String gridJson){
		Element divEl=new Element(Tag.valueOf("div"),"");
		JSONArray jsonArray=JSONArray.parseArray(gridJson);
		JSONArray jsonAry=sysBoListManager.getByType(jsonArray,"query");
		for(int i=0;i<jsonAry.size();i++){
			com.alibaba.fastjson.JSONObject config=jsonAry.getJSONObject(i);
			String fieldLabel=config.getString("fieldLabel");
			if(StringUtils.isEmpty(fieldLabel))continue;
			String fc=config.getString("fc");
			Element span=new Element(Tag.valueOf("span"),"");
			span.text(fieldLabel+":");
			QueryControlParseHandler handler=queryControlParseConfig.getControlParseHandler(fc);
			Element conEl=handler.parseReportEl(config);
			span.append(conEl.toString());
			divEl.appendChild(span);
			
		}
		return divEl.html();
	}
	
	/**
     * 根据租户Id和key值查询是否有这个报表
     * @param key
     * @param tenantId
     * @return
     */
	public SysReport getByKey(String key,String tenantId){
    	return sysReportDao.getByKey(key,tenantId);
    }
}