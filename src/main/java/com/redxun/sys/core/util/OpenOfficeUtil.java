package com.redxun.sys.core.util;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.redxun.core.jms.MessageProducer;
import com.redxun.core.util.AppBeanUtil;
import com.redxun.sys.core.entity.SysFile;

/**
 * 系统参数工具类。
 *
 */
public class OpenOfficeUtil {
	
	/**
	 * 
	 * @param inputFilePath  D:\\outFile.doc 注意文件在不同系统内的区别,请使用File.separator作为分隔符
	 * @param outputFilePath D:\\intFile.pdf
	 * @return jsonObject   {success:false,reason:"openOffice系统参数缺失"}
	 */
	public static  JSONObject coverFromOffice2Pdf(String inputFilePath,String outputFilePath) {
		JSONObject jsonObject=new JSONObject();
		String opId="";//openOffice参数
		Integer opPort=null;//openOffice参数
		try {
			opId = SysPropertiesUtil.getGlobalProperty("openoffice.ip");
			opPort=SysPropertiesUtil.getGlobalPropertyInt("openoffice.port");
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("reason", "openOffice系统参数缺失");
			return jsonObject;
		}
		
		File inputFile = new File(inputFilePath);
		File outputFile = new File(outputFilePath);
		if (!inputFile.exists()) {    
			jsonObject.put("success", false);
			jsonObject.put("reason", "找不到源文件");
            return jsonObject;// 找不到源文件, 则返回    
        } 
		
		OpenOfficeConnection connection = new SocketOpenOfficeConnection(opId,opPort);
		try{
			connection.connect();
			DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
			converter.convert(inputFile, outputFile);
		}
	 	catch (ConnectException e) {
	 		jsonObject.put("success", false);
			jsonObject.put("reason", "连接错误");
			return jsonObject;
	 	}
		finally {
			if(connection!=null){
				connection.disconnect();
			}
		}
		
		jsonObject.put("success", true);
		jsonObject.put("reason", "成功执行,目标地址:"+outputFilePath);
        return jsonObject;
	}
	
	/**
	 * 获取连接状态
	 * @return
	 */
	public static boolean getConnectStatus(){
		String opId="";//openOffice参数
		Integer opPort=null;//openOffice参数
		try {
			opId = SysPropertiesUtil.getGlobalProperty("openoffice.ip");
			opPort=SysPropertiesUtil.getGlobalPropertyInt("openoffice.port");
		} catch (Exception e) {
			return false;
		}
		OpenOfficeConnection connection = new SocketOpenOfficeConnection(opId,opPort);
		try {
			connection.connect();
		} catch (ConnectException e) {
			return false;
		}
		return true;
		
	}
	
	/**
	 * 启动服务
	 * @return
	 */
	public static boolean startService(){
		String sys=System.getProperty("os.name");
		try {
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
			Runtime.getRuntime().exec(command);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	/**
	 * 结束服务
	 * @return
	 */
	public static boolean endService(){
		try {
			if(System.getProperty("os.name").toLowerCase().startsWith("win")){
				Runtime.getRuntime().exec("taskkill /f /im soffice.exe");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public static void  que2CoverOffice(SysFile file){
		MessageProducer messageProducer=AppBeanUtil.getBean(MessageProducer.class);
		messageProducer.send("officeCoverPdfMessageQueue", file);
	}
	
}
