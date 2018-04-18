package com.redxun.saweb.security.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.redxun.core.util.BeanUtil;

/**
 * 正则表达式验证。
 * @author ray
 *
 */
public class RegMatchers {
	
	private List<Pattern> ingoreUrls=new ArrayList<Pattern>();
	
	public void setIngoreUrls(List<String> urls){
		if(BeanUtil.isEmpty(urls)) return;
		for(String url:urls){
			Pattern regex = Pattern.compile(url, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | 
					Pattern.DOTALL | Pattern.MULTILINE);
			ingoreUrls.add(regex);
		}
	}
	
	
	/**
	 * 判断当前URL是否在忽略的地址中。
	 * @param requestUrl
	 * @return
	 */
	public boolean isContainUrl(String requestUrl){
		for(Pattern pattern:ingoreUrls){
			Matcher regexMatcher = pattern.matcher(requestUrl);
			if(regexMatcher.find()){
				return true;
			}
		}
		return false;
	}

}
