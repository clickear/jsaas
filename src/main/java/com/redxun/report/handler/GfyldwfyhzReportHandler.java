package com.redxun.report.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.redxun.sys.core.service.JasperParamCustomService;

/**
 * 
 * @author mansan
 * @Email keitch@redxun.cn
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
@Service
public class GfyldwfyhzReportHandler implements JasperParamCustomService {

	@Override
	public Map<String, Object> convert(Map<String, Object> params) {
		Calendar c1 = Calendar.getInstance();// 用来保存开始月份
		Calendar c2 = Calendar.getInstance();// 用来保存结束月份
		String startMon = null;
		String json = (String) params.get("json");
		if (StringUtils.isNotEmpty(json)) {
			JSONObject jsonObject = JSONObject.fromObject(json);// 前台选择了参数
			List<String> arr = jsonObject.names();// 获取名
			int months = 0;// 用来保存月数

			for (String name : arr) {
				if ("startMon".equals(name)) {
					String value = (String) jsonObject.get(name);
					startMon = value;
					params.put("startMonStr", value);
					StringBuffer yearMonSb = new StringBuffer();
					yearMonSb.append(value);
					yearMonSb.append("-01 00:00:00");// 获得锁定月份
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date costRpMonth = null;
					try {
						costRpMonth = sdf.parse(yearMonSb.toString());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					c1.setTime(costRpMonth);
					params.put("startMonth", costRpMonth);// 将(名,值)传入Map
				} else if ("endMon".equals(name)) {
					String value = (String) jsonObject.get(name);
					params.put("endMonthStr", value);
					StringBuffer yearMonSb = new StringBuffer();
					yearMonSb.append(value);
					yearMonSb.append("-01 23:59:59");// 获得锁定月份
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date costRpMonth = null;
					try {
						costRpMonth = sdf.parse(yearMonSb.toString());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Calendar cal = Calendar.getInstance();
					cal.setTime(costRpMonth);
					cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
					Date costMonth = cal.getTime();
					c2 = cal;
					params.put("endMonth", costMonth);// 将(名,值)传入Map
				}else{
					params.put(name, jsonObject.get(name));// 将(名,值)传入Map
				}
			}
			months = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH) + 1;
			params.put("months", months);// 将(名,值)传入Map
			return params;
		}else{
			return params;
		}
	}

}
