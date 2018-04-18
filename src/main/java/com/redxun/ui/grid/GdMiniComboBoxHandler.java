package com.redxun.ui.grid;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import com.alibaba.fastjson.JSONObject;

public class GdMiniComboBoxHandler implements GridColEditParseHandler{

	@Override
	public String getPluginName() {
		return "mini-combobox";
	}

	@Override
	public Element parse(Element columnEl,JSONObject elJson, Map<String, Object> params) {
		
		Element el=new Element(Tag.valueOf("DIV"),"");
		el.attr("class",getPluginName());
		el.attr("property","editor");
		String from=elJson.getString("from");
		if(StringUtils.isEmpty(from)){
			from="self";
		}
		
		if("self".equals(from)){
			String selOptions=elJson.getString("selOptions");
			if(StringUtils.isNotEmpty(selOptions)){
				selOptions=selOptions.replace("\"","'");
				el.attr("data",selOptions);
				el.attr("textfield","name");
				el.attr("valuefield","key");
			}
		}else if("sql".equals(from)){
			String sqlKey=elJson.getString("sql");
			String sql_textfield=elJson.getString("sql_textfield");
			String sql_valuefield=elJson.getString("sql_valuefield");
			if(StringUtils.isNotEmpty(sqlKey)){
				String url="${ctxPath}/sys/db/sysSqlCustomQuery/queryForJson_"+ sqlKey +".do";
				el.attr("url",url);
			}
			el.attr("textField",sql_textfield);
			el.attr("valueField",sql_valuefield);
		}else if("url".equals(from)){
			String url=elJson.getString("url");
			String url_textfield=elJson.getString("url_textfield");
			String url_valuefield=elJson.getString("url_valuefield");
			el.attr("url",url);
			el.attr("textfield",url_textfield);
			el.attr("valuefield",url_valuefield);
		}else if("dic".equals(from)){
			String key=elJson.getString("dickey");
			if(StringUtils.isNotEmpty(key)){
				String dicUrl="${ctxPath}/sys/core/sysDic/listByKey.do?key="+key;
				el.attr("url",dicUrl);
				el.attr("textfield","name");
				el.attr("valuefield","value");
			}
		}
		
		//若标签不为空，则设置父类的displayField
		String fieldLabel=elJson.getString("fieldLabel");
		if(StringUtils.isNotEmpty(fieldLabel)){
			columnEl.attr("displayField",fieldLabel);
		}
		return el;
	}

}
