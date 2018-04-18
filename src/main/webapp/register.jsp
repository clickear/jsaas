<%--
	//企业注册，生成企业需要的基本内容，
	//同时分配基础的账号
 --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>JSaaS私有云租用应用平台--企业注册</title>  
        <%@include file="/commons/edit.jsp"%>
        <script src="${ctxPath}/scripts/anonymus.js" type="text/javascript"></script>
    </head>
    <body>
    	<div style="width:100%;text-align: center">
        <div id="p1" class="form-outer" style="border:solid 1px #ccc;width:800px;margin-left: auto;margin-right: auto;">
            <form id="regForm" method="post">
                	<div style="padding:5px;">
                			<div style="width:100%">
                				<h2>企业注册</h2>
                				
                			</div>
                		<input id="pkId" name="pkId" class="mini-hidden" value="${sysInst.instId}"/>
	                        <table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
	                        	<caption>机构基本信息</caption>
	                            <tr>
	                                <th>公司中文名*：</th>
	                                <td style="min-width: 250px"> 
	                                    <input name="nameCn" value="${sysInst.nameCn}" class="mini-textbox" required="true" vtype="maxLength:256" emptyText="请输入公司中文名" style="width:80%"/>
	                                </td>
	                                <th>公司英文名*：</th>
	                                <td style="min-width: 250px">    
	                                    <input name="nameEn" value="${sysInst.nameEn}" class="mini-textbox" required="true" vtype="maxLength:256" emptyText="请输入公司英文名" style="width:80%"/>
	                                </td>
	                            </tr>
	                            <tr>
	                                <th>组织机构代码*：</th>
	                                <td>    
	                                    <input name="instNo" value="${sysInst.instNo}" class="mini-textbox" required="true" emptyText="请输入组织机构代码" vtype="maxLength:50" style="width:80%"/>
	                                </td>
	                               <th>营业执照代码*：</th>
	                                <td>    
	                                    <input name="busLiceNo" value="${sysInst.busLiceNo}" class="mini-textbox" required="true" emptyText="请输入营业执照代码" vtype="maxLength:50" style="width:80%"/>
	                                </td>
	                            </tr>
	                            <tr>
	                            	<th>公司域名：</th>
	                                <td>    
	                                    <input name="domain" value="${sysInst.domain}" class="mini-textbox" required="true" vtype="maxLength:100" style="width:80%" emptyText="请输入公司域名，格式如abc.com"/>
	                                </td>
	                            	 <th>公司法人：</th>
	                                <td>    
	                                    <input name="legalMan" value="${sysInst.legalMan}" class="mini-textbox" required="true" vtype="maxLength:64" style="width:80%"/>
	                                </td>
	                            </tr>
	                            <tr>
	                                <th>公司简称(中文)：</th>
	                                <td>    
	                                    <input name="nameCnS" value="${sysInst.nameCnS}" class="mini-textbox" vtype="maxLength:80" style="width:80%"/>
	                                </td>
	                                <th>公司简称(英文)：</th>
	                                <td>
	                                	<input name="nameEnS" value="${sysInst.nameEnS}" class="mini-textbox" vtype="maxLength:80" style="width:80%"/>
	                                </td>
	                            </tr>
	                             <tr>
	                                <th>组织机构图片：</th>
	                                <td colspan="3">    
	                                    <input id="regCodeFileId" name="regCodeFileId" value="${sysInst.regCodeFileId}" type="hidden" required="true"/>
	                                    <img src="${ctxPath}/pub/anony/imageView.do?thumb=true&fileId=${sysInst.regCodeFileId}" class="upload-file"/>
	                                </td>
	                            </tr>
	                             <tr>
	                                <th>营业执照图片*：</th>
	                                <td colspan="3">    
	                                    <input id="busLiceFileId" name="busLiceFileId" value="${sysInst.busLiceFileId}" type="hidden" required="true"/>
	                                    <img src="${ctxPath}/pub/anony/imageView.do?thumb=true&fileId=${sysInst.busLiceFileId}" class="upload-file"/>
	                                </td>
	                            </tr>
	                            <tr>
	                                <th>公司描述：</th>
	                                <td colspan="3">    
	                                    <textarea id="descp" name="descp" class="mini-textarea" vtype="maxLength:2147483647" style="width:90%">${sysInst.descp}</textarea>
	                                </td>
	                            </tr>
	                        </table>
						</div>
						<div style="padding:5px;">
	                        <table class="table-detail column_2_m" cellpadding="0" cellspacing="1">
	                        	<caption>联系信息</caption>
	                            <tr>
	                                <th>联系人：</th>
	                                <td>                        
	                                    <input name="contractor" value="${sysInst.contractor}" class="mini-textbox" vtype="maxLength:30" style="width:80%"/>
	                                </td>
	                                <th>邮箱:</th>
	                                <td>    
	                                    <input name="email" class="mini-textbox" required="true" vtype="email;rangeLength:5,255" emptyText="必填，用于收取注册的详细信息" value="${sysInst.email}" style="width:80%"/>
	                                </td>
	                            </tr>
	                            <tr>
	                                <th>电话：</th>
	                                <td >                        
	                                    <input name="phone" value="${sysInst.phone}" required="true"  class="mini-textbox" vtype="maxLength:30" style="width:80%"/>
	                                </td>
	                                <th>传真：</th>
	                                <td>                        
	                                    <input name="fax" value="${sysInst.fax}" class="mini-textbox" vtype="maxLength:30" style="width:80%"/>
	                                </td>
	                            </tr>
	                            <tr>
	                                <th>地址:</th>
	                                <td colspan="3">    
	                                    <input name="address" value="${sysInst.address}" class="mini-textbox" vtype="maxLength:128" style="width:90%"/>
	                                </td>
	                            </tr>
	                            <tr>
	                            	<th>验证码</th>
	                            	<td colspan="3">
	                            		<input name="validCode" class="mini-textbox" id="validCode" vtype="maxLength:4"  required="true" />                 
        								<img src="${ctxPath}/captcha-image.do" width="75" height="35" id="kaptchaImage"  style="margin-bottom: -3px;cursor:pointer" onclick="refreshCode()"/> 
	                            	</td>
	                            </tr>
	                        </table>
                        </div>
                        
            </form>
            <div class="mini-toolbar" style="border:0" bodyStyle="border:0">
            	<input type="checkbox" checked="checked" />我已经阅读并接受&nbsp;&nbsp;<a href="#">服务条款</a>&nbsp;&nbsp;<a href="#">保密协议</a>
            	<br/>
			    <a class="mini-button" iconCls="icon-ok"  onclick="onOk()">确定</a>
			    <span style="display:inline-block;width:25px;"></span>
			    <a class="mini-button" iconCls="icon-cancel"  onclick="onCancel()">重置</a>
			</div>
        </div>
        </div>
      
       <script type="text/javascript">
       		var form=new mini.Form('regForm');
       		top['index']=window;
	       $(function(){
	    	   $(".upload-file").on('click',function(){
					var img=this;
					_UploadSingleImg({
						callback:function(files){
							if(files.length==0) return;
							$(img).attr('src','${ctxPath}/pub/anony/imageView.do?thumb=true&fileId='+files[0].id);
							$(img).siblings('input[type="hidden"]').val(files[0].id);
						}
					});
					
				});	
			});
	       
			//刷新编码
	       function refreshCode(){
	    	   var randId=parseInt(10000000*Math.random());
	    	   $('#kaptchaImage').attr('src','${ctxPath}/captcha-image.do?randId='+randId);
	       }
	       
	       //确认
	       function onOk(){
	    	   form.validate();
	           if (form.isValid() == false) {
	               return;
	           }
	           var formData=$("#regForm").serializeArray();
	           _SubmitJson({
		           	url:'${ctxPath}/register.do',
		           	method:'POST',
		           	data:formData,
		           	success:function(text){
		           		var result=mini.decode(text);
		           		window.location=__rootPath+'/pub/anony/sysInst/regSuccess.do?instId='+result.data.instId;
					}
	           });
	       }
	       //取消
	       function onCancel(){
	    	   $("#regForm")[0].reset();
	       }
		</script>
    </body>
</html>
