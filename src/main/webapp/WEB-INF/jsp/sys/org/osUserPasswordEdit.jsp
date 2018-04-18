<%--
	//企业注册，生成企业需要的基本内容，
	//同时分配基础的账号
 --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>JSaaS私有云租用应用平台--企业注册</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
        <%@include file="/commons/dynamic.jspf" %>   
        <script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
        <script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
        <script src="${ctxPath}/scripts/anonymus.js" type="text/javascript"></script>
        <link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
        <link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
    	<div style="width:100%;text-align: center;padding-top:10px;">
        <div id="p1"  style="border:solid 1px #ccc;width:600px;margin-left: auto;margin-right: auto;">
            <form id="pwdForm" method="post">
               		<div>
               			<div style="width:100%">
               				<h2>修改密码</h2>
               			</div>
                		<input id="pkId" name="pkId" class="mini-hidden" value="${sysInst.instId}"/>
	                        <table class="table-detail" cellspacing="1" cellpadding="0">
	                            <tr>
	                                <th>原始密码*：</th>
	                                <td style="min-width: 200px"> 
	                                    <input name="oldPwd" value="" class="mini-password" required="true" vtype="" emptyText="请输入原始密码" style="width:80%"/>
	                                </td>
	                            </tr>
	                            <tr>
	                                <th>新的密码*：</th>
	                                <td>    
	                                    <input id="newPwd" name="newPwd" value="" class="mini-password" required="true" emptyText="请输入新密码" vtype="rangeLength:6,20" style="width:80%"/>
	                                </td>
	                            </tr>
	                            <tr>
	                                <th>确认密码*：</th>
	                                <td>    
	                                    <input id="comfirmPwd" name="comfirmPwd" value="" class="mini-password" required="true" emptyText="再一次输入新密码" vtype="rangeLength:6,20" style="width:80%"/>
	                                </td>
	                            </tr>
	                           
	                        </table>
						</div>
                        
            </form>
            <div class="mini-toolbar" style="border:0" bodyStyle="border:0;">
			    <div style="text-align:center">
			    	<a class="mini-button" iconCls="icon-ok"  onclick="onOk()">修改密码</a>
			   	 	<a class="mini-button" iconCls="icon-close" onclick="CloseWindow()">关闭</a>
			    </div>
			</div>
        </div>
        </div>
      
       <script type="text/javascript">
       		var form=new mini.Form('pwdForm');
	       
	       //修改密码
	       function onOk(){
	    	   form.validate();
	           if (form.isValid() == false) {
	               return;
	           }
	           var newPwd=mini.get("newPwd").getValue().replace(/^\s*$/g,'') ;
	           var comfirmPwd=mini.get("comfirmPwd").getValue().replace(/^\s*$/g,'');
	           if(newPwd!=comfirmPwd){
	        	   alert("两次输入密码不一致！");
	        	   return;
	           }
	           var formData=$("#pwdForm").serializeArray();
	           _SubmitJson({
		           	url:'${ctxPath}/sys/core/sysAccount/editPwd.do',
		           	method:'POST',
		           	data:formData,
		           	success:function(text){
		           		if(text.data=="true")
		           			CloseWindow("ok");
					}
	           });
	       }
		</script>
    </body>
</html>
