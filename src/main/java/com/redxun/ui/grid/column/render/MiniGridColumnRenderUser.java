package com.redxun.ui.grid.column.render;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.redxun.core.entity.GridHeader;
import com.redxun.core.util.BeanUtil;
import com.redxun.sys.core.enums.MiniGridColumnType;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.entity.OsUser;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsUserManager;

/**
 * 用户类型的映射
 * @author mansan
 *
 */
public class MiniGridColumnRenderUser implements MiniGridColumnRender{

	@Resource
	OsUserManager osUserManager;
	@Resource
	OsGroupManager osGroupManager;
	
	
	@Override
	public String getRenderType() {
		return MiniGridColumnType.USER.name();
	}

	@Override
	public String render(GridHeader gridHeader,Map<String,Object> rowData, Object val, boolean isExport) {
		if(val==null) return "";
		OsUser osUser=osUserManager.get(val.toString());
		if(osUser==null){
			return "";
		}
		String depShowType=gridHeader.getRenderConfObj().getString("depShowType");
		String showLink=gridHeader.getRenderConfObj().getString("showLink");
		String showField=gridHeader.getRenderConfObj().getString("showField");
		String otherFields=gridHeader.getRenderConfObj().getString("otherFields");
		StringBuffer sb=new StringBuffer();
		if(!isExport &&"true".equals(showLink)){
			sb.append("<a href='javascript:void(0);' onclick=\"_ShowUserInfo('"+osUser.getUserId()+"')\">");
		}
		if("showName".equals(depShowType)){
			OsGroup osGroup=osGroupManager.getMainDeps(val.toString());
			if(osGroup!=null){
				sb.append(osGroup.getName()).append("/");
			}
		}else if("showFullName".equals(depShowType)){
			sb.append(osGroupManager.getMainDepFullNames(val.toString()));
		}
		if(StringUtils.isEmpty(showField)){
			showField="fullname";
		}
		String userLabel=(String)BeanUtil.getFieldValueFromObject(osUser, showField);
		sb.append(userLabel);
		if(StringUtils.isNotEmpty(otherFields)){
			String[]fields=otherFields.split("[,]");
			int i=0;
			if(fields.length>0){
				sb.append("(");
			}
			for(String f:fields){
				String fLabel=(String)BeanUtil.getFieldValueFromObject(osUser, f);
				if(i>0){
					sb.append(",");
				}
				sb.append(fLabel);
				i++;
			}
			if(fields.length>0){
				sb.append(")");
			}
		}
		
		if(!isExport && "true".equals(showLink)){
			sb.append("</a>");
		}
		return sb.toString();
	}

}
