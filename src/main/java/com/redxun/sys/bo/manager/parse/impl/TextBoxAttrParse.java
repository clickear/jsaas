package com.redxun.sys.bo.manager.parse.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.database.api.model.Column;
import com.redxun.core.script.GroovyEngine;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.ITenant;
import com.redxun.sys.bo.entity.SysBoAttr;
import com.redxun.sys.bo.manager.parse.AbstractBoAttrParse;
import com.redxun.sys.core.entity.SysSeqId;
import com.redxun.sys.core.manager.SysSeqIdManager;

public class TextBoxAttrParse extends AbstractBoAttrParse {

	@Resource
	SysSeqIdManager sysSeqIdManager;
	@Resource
	GroovyEngine groovyEngine;

	@Override
	protected void parseExt(SysBoAttr field, Element el) {

		String datatype = el.attr("datatype");

		if (StringUtil.isEmpty(datatype)) {
			field.setDataType(Column.COLUMN_TYPE_VARCHAR);
			field.setLength(100);
		} else {
			field.setDataType(datatype);
			if (Column.COLUMN_TYPE_VARCHAR.equals(datatype)) {
				String len = el.attr("length").trim();
				int length = getLength(len, 50);
				field.setLength(length);
			} else if (Column.COLUMN_TYPE_NUMBER.equals(datatype)) {
				int length = getLength(el.attr("length").trim(), 14);
				int decimal = getLength(el.attr("decimal").trim(), 0);
				field.setLength(length);
				field.setDecimalLength(decimal);
			}
		}

		// 解析json。
		parseExtJson(field, el);
	}

	private void parseExtJson(SysBoAttr field, Element el) {
		JSONObject json = new JSONObject();
		// vtype="isChinese"
		// required="true"
		String from = el.attr("from");
		if (StringUtil.isNotEmpty(from)) {
			json.put("from", from);
			if ("sequence".equals(from)) {
				String sequcene = el.attr("sequence");
				json.put("sequence", sequcene);
			} else if ("scripts".equals(from)) {
				String scripts = el.attr("scripts");
				json.put("scripts", scripts);
			}
		}

		String vtype = el.attr("vtype");
		if (StringUtil.isNotEmpty(vtype)) {
			json.put("vtype", vtype);
		}

		String required = el.attr("required");
		if (StringUtil.isNotEmpty(required) && "true".equals(required)) {
			json.put("required", required);
		}

		String dataOptions = el.attr("data-options");
		if (StringUtil.isNotEmpty(dataOptions)) {
			json.put("conf", dataOptions);
		}

		if (json.size() > 1) {
			String jsonStr = json.toJSONString();
			field.setExtJson(jsonStr);
		}

	}

	@Override
	public JSONObject getInitData(SysBoAttr attr) {
		String from = attr.getPropByName("from");
		if (StringUtil.isEmpty(from))
			return null;

		JSONObject jsonObject = new JSONObject();

		if ("sequence".equals(from)) {
			String sequcene = attr.getPropByName("sequence");
			if (StringUtil.isNotEmpty(sequcene)) {
				SysSeqId seqId = sysSeqIdManager.get(sequcene);
				if (seqId == null)
					return null;
				String alias = seqId.getAlias();
				String no = sysSeqIdManager.genSequenceNo(alias,
						ITenant.ADMIN_TENANT_ID);
				AttrParseUtil.addKey(jsonObject, no);
			}
		} else if ("scripts".equals(from)) {
			String scripts = attr.getPropByName("scripts");
			if (StringUtil.isNotEmpty(scripts)) {
				Object rtn = groovyEngine.executeScripts(scripts,
						new HashMap<String, Object>());
				if (rtn != null) {
					AttrParseUtil.addKey(jsonObject, rtn.toString());
				}

			}
		}
		return jsonObject;
	}

	@Override
	public String getPluginName() {
		return "mini-textbox";
	}

	@Override
	public String getDescription() {
		return "文本框";
	}

	@Override
	public boolean isSingleAttr() {
		return true;
	}

}
