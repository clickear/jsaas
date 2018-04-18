<%@page import="com.redxun.offdoc.core.manager.DBManager2000"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<%@ page import="java.io.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%!
  DBManager2000 ObjConnBean = new DBManager2000();
  String XYBH;                                  //定义协议编号
  String BMJH;                                  //定义保密级别
  String JF;                                    //定义甲方
  String YF;                                    //定义乙方
  String HZNR;                                  //定义合作内容
  String QLZR;                                  //定义权利责任
  String CPMC;                                  //定义产品名称
  String DGSL;                                  //定义订购数量
  String DGRQ;                                  //定义订购日期
  String DocumentID;                            //文档编号
%>
<%
  String mScriptName = "/odDocumentSign.jsp";
  String mServerName="/Service.jsp";
  String mHttpUrlName=request.getRequestURI();
  String mServerUrl="http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/iWebOffice/Service.jsp";
System.out.println(mServerUrl);
  DocumentID=request.getParameter("DocumentID"); //取得编号
  if(DocumentID==null){
    DocumentID="";                              //编号为空
  }

	if (ObjConnBean.OpenConnection()){
  		String Sql="Select * From HTMLDocument Where DocumentID='"+ DocumentID + "'";
  		ResultSet rs = null;
  		rs = ObjConnBean.ExecuteQuery(Sql);
  	if (rs.next()) {
		DocumentID=rs.getString("DocumentID");
		XYBH=rs.getString("XYBH");
		BMJH=rs.getString("BMJH");
		JF=rs.getString("JF");
		YF=rs.getString("YF");
		HZNR=rs.getString("HZNR");
		QLZR=rs.getString("QLZR");
		CPMC=rs.getString("CPMC");
		DGSL=rs.getString("DGSL");
		DGRQ=rs.getString("DGRQ");
  	}else {
	//取得唯一值(DocumentID)
    	java.util.Date dt=new java.util.Date();
    	long lg=dt.getTime();
    	Long ld=new Long(lg);
    	DocumentID=ld.toString();
    	//System.out.println(DocumentID);  //保存的是文档的编号，通过该编号，可以在里找到所有属于这条纪录的文档
		XYBH="JGKJ2004-"+dt.getSeconds();
		BMJH="中级" ;
		JF="江西金格网络科技有限责任公司";
		YF="XXX有限责任公司";
		HZNR="订购甲方iSignature 可信电子签章系统" +"\r\n"+"For Word/Excel/Html/WPS/PDF/FORM/FILE/GDF 版本";
		QLZR="内容完整性鉴别";
		CPMC="iSignature电子签章系统";
		DGSL=String.valueOf(dt.getMinutes());
		DGRQ=ObjConnBean.GetDate().toString();
  	}
    rs.close();
    ObjConnBean.CloseConnection();
   }
%>

<html>
<head>
<title>金格电子签章系统iSignature HTML V8</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
<link REL="stylesheet" href="Test.css" type="text/css">
<style media=print>
    .Noprint{display:none;}   
</style>
<style type="text/css">
    .print{
    	display: inherit;
    }
</style>

<script language="javascript">
var wnd;  //定义辅助功能全局变量

mini.parse();
//作用：进行甲方签章
function DoJFSignature()
{
  DocForm.SignatureControl.FieldsList="XYBH=协议编号;BMJH=保密级别;JF=甲方签章;HZNR=合作内容;QLZR=权利责任;CPMC=产品名称;DGSL=订购数量;DGRQ=订购日期"       //所保护字段
  DocForm.SignatureControl.DivId="jfdiv";                             //设置签章位置是相对于哪个标记的什么位置 
  DocForm.SignatureControl.AutoSave = "True";
  DocForm.SignatureControl.UserName="lyj";                         //文件版签章用户
  DocForm.SignatureControl.RunSignature();                         //执行签章操作
}


//作用：进行乙方签章
function DoYFSignature()
{
  if(wnd != undefined){
    var results = wnd.split(";");
    DocForm.SignatureControl.CharSetName = results[0];		  //多语言集
    DocForm.SignatureControl.DocProtect = results[12];      //自动锁定文档
    DocForm.SignatureControl.WebAutoSign = results[1];		  //自动数字签名
    DocForm.SignatureControl.WebCancelOrder = results[2];	  //撤消顺序
    DocForm.SignatureControl.PassWord = results[3];		  //签章密码
        
    var tmp = DocForm.SignatureControl.WebSetFontOther((results[4]=="1"?true:false),
		results[5],results[6],results[7],results[8],results[9],
			(results[10]=="1"?true:false));		    //设置签章附加文字格式
    DocForm.SignatureControl.WebIsProtect=results[11];		    //保护表单数据， 0不保护  1保护表单数据，可操作  2保存表单数据，并不能操作  默认值1
        
  }else{
    DocForm.SignatureControl.WebIsProtect=1;			    //保护表单数据， 0不保护  1保护表单数据，可操作  2保存表单数据，并不能操作  默认值1
    DocForm.SignatureControl.WebCancelOrder=0;			    //签章撤消原则设置, 0无顺序 1先进后出  2先进先出  默认值0
  }

  DocForm.SignatureControl.FieldsList="HTJB=合同级别;XYBH=协议编号;BMJH=保密级别;JF=甲方签章;YF=乙方签章;HZNR=合作内容;QLZR=权利责任;CPMC=产品名称;DGSL=订购数量;DGRQ=订购日期"       //所保护字段
  DocForm.SignatureControl.Position(0,0);                          //签章位置
  DocForm.SignatureControl.SaveHistory="False";                    //是否自动保存历史记录,true保存  false不保存  默认值false
  DocForm.SignatureControl.UserName="wjd";                         //文件版签章用户
  DocForm.SignatureControl.DivId="yfdiv";                          //设置签章位置是相对于哪个标记的什么位置 
  DocForm.SignatureControl.RunSignature();                         //执行签章操作
  
  if(DocForm.SignatureControl.DocProtect)
  {
     ProtectDocument();
  }
}


//作用：自动锁定文档
function ProtectDocument()
{
   var mLength=document.getElementsByName("iHtmlSignature").length; 
   var mProtect = false;
   for (var i=0;i<mLength;i++){
       var vItem=document.getElementsByName("iHtmlSignature")[i];
	   if(vItem.DocProtect)
	   {
          mProtect = true;
		  break;
	   }	   
   }
  if(!mProtect){
     var vItem = document.getElementsByName("iHtmlSignature")[mLength-1];
	 vItem.LockDocument(true);
   }
}


//作用：进行手写签名
function DoSXSignature()
{
  if(wnd != undefined){
    var results = wnd.split(";");
    DocForm.SignatureControl.CharSetName = results[0];		  //多语言集
    DocForm.SignatureControl.WebAutoSign = results[1];		  //自动数字签名
    DocForm.SignatureControl.DocPortect=results[2];
    DocForm.SignatureControl.WebCancelOrder = results[3];	  //撤消顺序
    DocForm.SignatureControl.PassWord = results[4];		  //签章密码
    
    var tmp = DocForm.SignatureControl.WebSetFontOther((results[5]=="1"?true:false),
		results[6],results[7],results[8],results[9],results[10],
			(results[11]=="1"?true:false));		    //设置签章附加文字格式
    DocForm.SignatureControl.WebIsProtect=results[12];		    //保护表单数据， 0不保护  1保护表单数据，可操作  2保存表单数据，并不能操作  默认值1
  }else{
    DocForm.SignatureControl.WebIsProtect=1;			    //保护表单数据， 0不保护  1保护表单数据，可操作  2保存表单数据，并不能操作  默认值1
    DocForm.SignatureControl.WebCancelOrder=0;			    //签章撤消原则设置, 0无顺序 1先进后出  2先进先出  默认值0
  }
  
  DocForm.SignatureControl.FieldsList="XYBH=协议编号;BMJH=保密级别;JF=甲方签章;YF=乙方签章;HZNR=合作内容;QLZR=权利责任;CPMC=产品名称;DGSL=订购数量;DGRQ=订购日期"       //所保护字段
  DocForm.SignatureControl.Position(0,0);                           //手写签名位置
  //DocForm.SignatureControl.SaveHistory="false";                   //是否自动保存历史记录,true保存   DocForm.SignatureControl.Phrase = "同意;不同意;请核实";           //设置文字批注常用词
  DocForm.SignatureControl.HandPenWidth = 1;                        //设置、读取手写签名的笔宽
  DocForm.SignatureControl.HandPenColor = 100;                      //设置、读取手写签名
  DocForm.SignatureControl.HandPenWidth = 1;                        //设置、读取手写签名的笔宽
  DocForm.SignatureControl.HandPenColor = 100;                      //设置、读取手写签名笔颜色笔颜色
  DocForm.SignatureControl.DivId="hznrdiv";                            //设置签章位置是相对于哪个标记的什么位置
  DocForm.SignatureControl.UserName="lyj";                        //文件版签章用户
  DocForm.SignatureControl.RunHandWrite();                          //执行手写签名
  if(DocForm.SignatureControl.DocPortect)
  {
     ProtectDocument();
  }             //执行手写签名
}


//作用：获取签章信息，以XML格式返回，并且分析显示数据.具体的XML格式请参照技术白皮书
//      具体分析后的内容如何处理，请自己做适当处理,本示例仅将返回结果进行提示。
function WebGetSignatureInfo(){
  var mSignXMl=DocForm.SignatureControl.GetSignatureInfo();   //读取当前文档签章信息，以XML返回
  alert(mSignXMl);                                      //调试信息

  var XmlObj = new ActiveXObject("Microsoft.XMLDOM");
  XmlObj.async = false;
  var LoadOk=XmlObj.loadXML(mSignXMl);
  var ErrorObj = XmlObj.parseError;

  if (ErrorObj.errorCode != 0){
     alert("返回信息错误...");
  }else{

    var CurNodes=XmlObj.getElementsByTagName("iSignature_HTML");
    for (var iXml=0;iXml<CurNodes.length;iXml++){
	    var TmpNodes=CurNodes.item(iXml);
		/*
		alert(TmpNodes.selectSingleNode("SignatureOrder").text);  //签章序列号
		alert(TmpNodes.selectSingleNode("SignatureName").text);   //签章名称
		alert(TmpNodes.selectSingleNode("SignatureUnit").text);   //签章单位
		alert(TmpNodes.selectSingleNode("SignatureUser").text);   //签章用户
		alert(TmpNodes.selectSingleNode("SignatureDate").text);   //签章日期
		alert(TmpNodes.selectSingleNode("SignatureIP").text);     //签章电脑IP
		alert(TmpNodes.selectSingleNode("KeySN").text);           //钥匙盘序列号
		alert(TmpNodes.selectSingleNode("SignatureSN").text);     //签章序列号
		alert(TmpNodes.selectSingleNode("SignatureResult").text); //签章验测结果
		*/
    }

  }
}


//作用：设置禁止(允许)签章的密钥盘   具体参数信息请参照技术白皮书
function WebAllowKeySN()
{
  var KeySn=window.prompt("请输入禁止在此页面上签章的钥匙盘序列号:");
  DocForm.SignatureControl.WebAllowKeySN(false,KeySn);
}


//作用：获取KEY密钥盘的SN序列号
function WebGetKeySN()
{
  var KeySn=DocForm.SignatureControl.WebGetKeySN();
  alert("您的钥匙盘序列号为:"+KeySn);
}


//作用：校验用户的 PIN码是否正确
function WebVerifyKeyPIN()
{
  var KeySn = DocForm.SignatureControl.WebGetKeySN();
  var mBoolean = DocForm.SignatureControl.WebVerifyKeyPIN("123456");
  if (mBoolean){
  	alert(KeySn+":通过校验");
  }else{
  	alert(KeySn+":未通过校验");
  }
}


//作用：修改钥匙盘PIN码,参数1为原PIN码,参数2为修改后的PIN码
function WebEditKeyPIN()
{
  var oldPIN = window.prompt("请输入原来的PIN码");
  if(oldPIN == null){
	return;
  }
  var newPIN = window.prompt("请输入修改后的PIN码");
  if(newPIN == null){
	return;
  }
  var mBoolean = DocForm.SignatureControl.WebEditKeyPIN(oldPIN,newPIN);
  if (mBoolean){
  	alert("钥匙盘PIN码修改成功!");
  }else{
  	alert("钥匙盘PIN码修改不成功!");
  }
}


//作用：批量验证签章
function BatchCheckSign()
{
   DocForm.SignatureControl.BatchCheckSign();
}


//作用：辅助功能
function ParameterSetting(){
	var mParameter = new Array();
	mParameter[0] = DocForm.SignatureControl.CharSetName;		//多语言集
	mParameter[5] = DocForm.SignatureControl.DocProtect;  //自动锁定文档
	mParameter[1] = DocForm.SignatureControl.WebAutoSign;		//自动数字签名
	mParameter[2] = DocForm.SignatureControl.WebCancelOrder;	//撤消顺序
	mParameter[3] = DocForm.SignatureControl.PassWord;		//签章密码
	if( wnd != undefined ){
		var results = wnd.split(";");
		mParameter[4] = results;
    }

	tmp =
	window.showModalDialog("ParameterSetting.jsp",mParameter,"dialogWidth:350px;dialogHeight:520px;menubar:no;toolbar:no;scrollbars:no;resizable:no;center:yes;status:no;help:no;");
	if(tmp != undefined){
		wnd = tmp;
	}
    if( wnd != undefined ){
		var results = wnd.split(";");
		DocForm.SignatureControl.CharSetName = results[0];
		DocForm.SignatureControl.WebAutoSign = results[1];
		DocForm.SignatureControl.WebCancelOrder = results[2];
		DocForm.SignatureControl.PassWord = results[3];
		DocForm.SignatureControl.DocProtect = results[12];

		var tmp = DocForm.SignatureControl.WebSetFontOther((results[4]=="1"?true:false),
			results[5],results[6],results[7],results[8],results[9],
			(results[10]=="1"?true:false));
    }
}


//作用：显示或隐藏签章
function ShowSignature(visibleValue)
{
   var mLength=document.getElementsByName("iHtmlSignature").length; 
   for (var i=0;i<mLength;i++){
       var vItem=document.getElementsByName("iHtmlSignature")[i];
       vItem.Visiabled = visibleValue;
   }
}

//作用：删除签章
function DeleteSignature()
{
   var mLength=document.getElementsByName("iHtmlSignature").length; 
   var mSigOrder = "";
   for (var i=mLength-1;i>=0;i--){
       var vItem=document.getElementsByName("iHtmlSignature")[i];
	   //mSigOrder := 
	   if (vItem.SignatureOrder=="1")
	   {
         vItem.DeleteSignature();
	   }
   }
}

//作用：移动签章
function MoveSignature()
{
  DocForm.SignatureControl.MovePositionByNoSave(100,100);
  alert("位置增加100");
  DocForm.SignatureControl.MovePositionByNoSave(-100,-100);
  alert("回到原来位置");
  DocForm.SignatureControl.MovePositionToNoSave(100,100);
  alert("移动到100，100");
}


//作用：脱密
function ShedCryptoDocument()
{
  DocForm.SignatureControl.ShedCryptoDocument();
}


//作用：脱密还原
function ResetCryptoDocument()
{
  DocForm.SignatureControl.ResetCryptoDocument();
}


//作用：打印文档
function PrintDocument(){
   var tagElement = document.getElementById('documentPrintID');
   tagElement.className = 'print';                                                 //样式改变为可打印
   var mCount = DocForm.SignatureControl.PrintDocument(false,2,5);  //打印控制窗体
   alert("实际打印份数："+mCount);
   tagElement.className = 'Noprint';                                               //样式改变为不可打印
}

//作用：获取IE版本，如果是高于IE9，设置打印方式解决签章打印黑白问题
function getIEVersion()
{
 
	var mXml = "<?xml version='1.0' encoding='GB2312' standalone='yes'?>";
  	mXml = mXml + "  <Signature>";
  	mXml = mXml + "    <OtherParam>";
  	mXml = mXml + "	    <PrintType>1</PrintType>";	//IE9以上设置为1解决签章打印黑白问题
  	//mXml = mXml + "	    <DIVIndex>-999999</DIVIndex>";	//IE9以上设置为1解决签章打印黑白问题  		
  	mXml = mXml + "    </OtherParam>";  		
  	mXml = mXml + "  </Signature>";
  	DocForm.SignatureControl.XmlConfigParam = mXml;				
 
}

function SaveForm(){
	var form = new mini.Form("DocForm");
}


</script>
</head>

<body id="documentPrintID" class="Noprint" onLoad="DocForm.SignatureControl.ShowSignature('<%=DocumentID%>');" onUnload="DocForm.SignatureControl.DeleteSignature();" topmargin="0" leftmargin="0" bgcolor="c0c0c0">

<form name="DocForm" >
<input type="hidden" name="DocumentID" value="<%=DocumentID%>">
<OBJECT id="SignatureControl"   classid="clsid:D85C89BE-263C-472D-9B6B-5264CD85B36E"  codebase="iSignatureHTML.cab#version=8,2,2,56" width=0 height=0>
<param name="ServiceUrl" value="<%=mServerUrl%>"><!--读去数据库相关信息-->
<param name="WebAutoSign" value="0">             <!--是否自动数字签名(0:不启用，1:启用)-->
<param name="PrintControlType" value=2>               <!--打印控制方式（0:不控制  1：签章服务器控制  2：开发商控制）-->
</OBJECT>
<table width=760 border="0" cellspacing="0" cellpadding="0" bgcolor="000000">
<tr>
  <td colspan=2 bgcolor="#c0c0c0" height=32>
    <a class="mini-button" iconCls="icon-edit" onclick="SaveForm()">保存文档</a>
    <a class="mini-button" iconCls="icon-edit" onclick="DoJFSignature()">甲方签章</a>
    <a class="mini-button" iconCls="icon-edit" onclick="DoYFSignature()">乙方签章</a>
    <a class="mini-button" iconCls="icon-edit" onclick="DoSXSignature()">签字演示</a>
    <a class="mini-button" iconCls="icon-edit" onclick="ParameterSetting()">辅助功能</a>
    <a class="mini-button" iconCls="icon-edit" onclick="WebGetSignatureInfo()">签章信息</a>
    <a class="mini-button" iconCls="icon-edit" onclick="BatchCheckSign()">批量验证</a>
    <a class="mini-button" iconCls="icon-edit" onclick="MoveSignature()">移动盖章</a>
    <a class="mini-button" iconCls="icon-edit" onclick="PrintDocument()">打印控制</a>
    <a class="mini-button" iconCls="icon-edit" onclick="ShowSignature('0')">隐藏签章</a>
    <a class="mini-button" iconCls="icon-edit" onclick="ShowSignature('1')">显示签章</a>
    <a class="mini-button" iconCls="icon-edit" onclick="ShedCryptoDocument()">脱密签章</a>
    <a class="mini-button" iconCls="icon-edit" onclick="ResetCryptoDocument()">脱密还原</a>
  </td>
</tr>


	<tr>
  <td id=Page>
    <table width="758" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="ffffff">
<tr><td height="80" align="center" valign="bottom"><font color="red" size="6"><b>金格网络科技公司产品订购协议<b></font></td></tr>
<tr><td height="10">&nbsp;</td></tr>
<tr>
<td>
<table width="600" border="0" cellspacing="0" cellpadding="0" align="center" >
<tr>
<td style="border-bottom:1px solid; border-color:#ff0000" nowrap>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
   <tr>
      <td width=96 align=left><font color="red">协议编号：</font></td>
      <td ><input type="text" name=XYBH class=inputcss value=<%=XYBH%>></td>

      <td width=96  align=right><font color="red">保密级别：</font></td>

      <td ><input type="text" name=BMJH  class=inputcss value=<%=BMJH%>></td>
   </tr>
</table>
</td>
</tr>
<tr>
<td  height="300">
 <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" height="100%">
   <tr>
      <td width="100%"  style="border-bottom:1px solid; border-color:#ff0000">
	 <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
            <tr height="150">
			<div id="jfdiv"  style='position:absolute;width:100%;height:100%;'  bgcolor=#ffffff>
              <td width=100 valign="top" ><font color="red" >甲方签章：</font> </td>   
			  
              <td style="border-bottom:1px solid; border-color:#ff0000;cursor:hand" valign="top"><input type="text"  name=JF  class=inputcss style="height:24 px" value="<%=JF%>"></td>
			</div>
            </tr>
	 </table>
	 <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">

            <tr height="150">
				<div id="yfdiv"  style="position:absolute;width:100%;height:100%;"  bgcolor=#ffffff>
              <td width=100  valign="top" ><font color="red" >乙方签章：</font></td>
			  
			  
              <td style="border-bottom:1px solid; border-color:#ff0000;cursor:hand" valign="top">
			  <input type="text" id="YF" name=YF class=inputcss style="height:24 px"  value="<%=YF%>"></td>
				</div>
            </tr>

	 </table>
      </td>
   </tr>

</table>
</td>
</tr>


<tr>
<td style="border-bottom:1px solid; border-color:#ff0000" height="160" nowrap >
<div name="mydiv" id="mydiv" style='position:absolute;width:100%;height:100%;'  bgcolor=#ffffff>
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" height="100%">
  <tr>
      <td  width="100%"  >
          <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" height="100%">
           <tr> <td><div id="hznrdiv"  style='position:absolute;width:100%;height:100%;'  ></div></td></tr> 
            <tr>
              <td valign="top"><font color="red" >合作内容：</font>&nbsp;
            </tr>
            <tr>
              <td height="100%">
		<Textarea name=HZNR id="HZNR" class=inputcss style="width:100%;height:100%"><%=HZNR%></Textarea>
       	      </td>
            </tr>
          </table>
      </td>
   </tr>
       </td>
            </tr>
</table>
</div>
</td>

</tr>



<tr>
<td style="border-bottom:1px solid; border-color:#ff0000" height="160" nowrap >
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" height="100%">
   <tr>
              <td valign="top"><font color="red" >权利责任：</font>&nbsp;
            </tr>
            <tr>
              <td height="100%">
		<Textarea name=QLZR  class=inputcss style="width:100%;height:100%" ><%=QLZR%></Textarea>
       </td>
            </tr>
</table>
</td>
</tr>
<tr>

<td style="border-bottom:1px solid; border-color:#ff0000" height="40" nowrap >
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" height="100%">
   <tr>
      <td width="100"  ><font color="red">&nbsp;产品名称：</font></td>
      <td><input type="text" name=CPMC  class=inputcss value=<%=CPMC%>></td>
   </tr>
</table>
</td>
</tr>
<tr>
<td style="border-bottom:1px solid; border-color:#ff0000" height="40" nowrap  >
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" height="100%" >
   <tr>
      <td width="100" ><font color="red">&nbsp;订购数量：</font></td>
      <td><input type="text" name=DGSL  class=inputcss  value=<%=DGSL%>></td>
   </tr>
</table>
</td>
</tr>
<tr>
<td style="border-bottom:1px solid; border-color:#ff0000" height="40" nowrap >
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" height="100%">
   <tr>
      <td width="100" ><font color="red">&nbsp;订购日期：</font></td>
      <td><input type="text" name=DGRQ  class=inputcss  value=<%=DGRQ%>></td>
   </tr>
</table>
</td>
</tr>


<tr>
<td style="border-bottom:1px solid; border-color:#ff0000" height="1" nowrap >
</td>
</tr>
</table>
</td>
</tr>
<tr><td height="60">&nbsp;</td></tr>
    </table>

  </td>
  <td width=2></td>
</tr>
<tr>
  <td height=2></td>
  <td></td>
</tr>

</table>


</form>
</body>
</html>