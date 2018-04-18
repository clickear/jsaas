package com.redxun.saweb.listener;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;

import com.redxun.sys.core.util.SysPropertiesUtil;

/**
 * 在系统启动时自动启动openoffice 服务。
 * @author cmc
 *
 */
public class OpenofficeListener implements ApplicationListener<ContextRefreshedEvent>,Ordered{

	@Override
	public int getOrder() {
		
		return 10;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent ev) {
		if (ev.getApplicationContext().getParent() != null) return;
		
		String sys=System.getProperty("os.name");
		
		try {
			boolean openOfficeSwitch=SysPropertiesUtil.getGlobalPropertyBool("openOfficeSwitch");
			if(openOfficeSwitch){//r如果有开关或者开关为开
				String OpenOffice_HOME=SysPropertiesUtil.getGlobalProperty("OpenOffice_HOME");
				String opIp=SysPropertiesUtil.getGlobalProperty("openoffice.ip");
				String opPort=SysPropertiesUtil.getGlobalProperty("openoffice.port");
				if(StringUtils.isBlank(OpenOffice_HOME)||StringUtils.isBlank(opIp)||StringUtils.isBlank(opPort)){
					throw new Exception("系统参数不齐全");
				}
				String command="";
				if(sys.toLowerCase().startsWith("win")){
					command= OpenOffice_HOME   + "program\\soffice.exe -headless -accept=\"socket,host="+opIp+",port="+opPort+";urp;StarOffice.ServiceManager\" -nofirststartwizard";
				}else{
					command=OpenOffice_HOME+"soffice -headless -accept=\"socket,host="+opIp+",port="+opPort+";urp;\" -nofirststartwizard &";
				}
				Process pro = Runtime.getRuntime().exec(command);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
