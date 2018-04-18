package com.redxun.sys.core.handler;

import com.redxun.core.jms.IMessageHandler;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.manager.SysFileManager;
import com.redxun.sys.core.util.OpenOfficeUtil;

/**
 * 将office 文档转换成 PDF 文档。
 * @author cmc
 *
 */
public class OfficeCoverMessageHandler implements IMessageHandler{

	@Override
	public void handMessage(Object messageObj) {
		SysFile sysFile=(SysFile) messageObj;
		String filePath=WebAppUtil.getUploadPath() + "/"+ sysFile.getPath();
		String newFilePath=filePath.substring(0, filePath.lastIndexOf("."));
		OpenOfficeUtil.coverFromOffice2Pdf(filePath, newFilePath+".pdf");
		sysFile.setCoverStatus("YES");
		SysFileManager sysFileManager=WebAppUtil.getBean(SysFileManager.class);
		sysFileManager.update(sysFile);
	}

}
