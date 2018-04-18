package com.redxun.report.handler;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.redxun.core.util.StringUtil;
import com.redxun.sys.core.service.JasperParamCustomService;

import net.sf.json.JSONObject;

/**
 * 
 * @author mansan
 * @Email keitch@redxun.cn
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（www.redxun.cn） 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
@Service
public class CxgfryReportHandler implements JasperParamCustomService {

	@Override
	public Map<String, Object> convert(Map<String, Object> params) throws Exception {

	
		try {

			String json = (String) params.get("json");
			JSONObject jsonObject = JSONObject.fromObject(json);// 前台选择参数
			//json转成sql
			String sql = doBuildSqlByJSONObject(jsonObject);

			params.put("code", sql);
		} catch (Exception e) {
			throw e;
		}

		return params;
	}

	public String doBuildSqlByJSONObject(JSONObject jsonObject)
			throws UnsupportedEncodingException {
		if (jsonObject == null || jsonObject.isNullObject()) {
			return "";
		}
		StringBuffer sb = new StringBuffer();

		List<String> keyList = jsonObject.names();
		int index = 0;
		for (String keyStr : keyList) {
			String[] keyArray = keyStr.split("\\#");
			if (keyArray.length == 1) {
				continue;
			}
			String key = keyArray[1];
			String value = jsonObject.get(keyStr).toString();
			if (StringUtil.isEmpty(value))  continue;
			if (keyArray.length == 2) {
				if ("Q".equals(keyArray[0])) {
					if (index == 0) {
						sb.append(" where ");
						index = index + 1;
					} else {
						sb.append(" and ");
					}
					sb.append(key);
					sb.append(" like '%");
					sb.append(value);
					sb.append("%' ");
				}
			}
			
		}
		return sb.toString();
	}
}
