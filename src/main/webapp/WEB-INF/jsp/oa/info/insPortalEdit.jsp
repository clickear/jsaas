<%-- 
    Document   : 门户编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>门户编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${insPortal.portId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="portId" class="mini-hidden" value="${insPortal.portId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>门户基本信息</caption>

					<tr>
						<th>门户名称 <span class="star">*</span> ：
						</th>
						<td><input name="name" value="${insPortal.name}" readonly class="mini-textbox" vtype="maxLength:128" style="width: 90%" required="true" emptyText="请输入门户名称" /></td>
					</tr>

					<tr>
						<th>门户KEY <span class="star">*</span> ：
						</th>
						<td><input name="key" value="${insPortal.key}" readonly class="mini-textbox" vtype="maxLength:64" style="width: 90%" required="true" emptyText="请输入门户KEY" /></td>
					</tr>
					<tr>
						<th>列数 <span class="star">*</span> ：
						</th>
						<td><input name="colNums" value="${insPortal.colNums}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" required="true" emptyText="请输入列数" /></td>
					</tr>
					<tr>
						<th>栏目宽 <span class="star">*</span> ：
						</th>
						<td><input name="colWidths" value="${insPortal.colWidths}" class="mini-textbox" vtype="maxLength:64" style="width: 80%" required="true" emptyText="请输入栏目宽" />
						<input type="button" value="帮助" onclick="showTips()" /></td>
					</tr>

					<tr>
						<th>是否为系统缺省 <span class="star">*</span> ：
						</th>
						<td><input name="isDefault" value="${insPortal.isDefault}" readonly class="mini-textbox" vtype="maxLength:20" style="width: 90%" required="true" emptyText="请输入是否为系统缺省" /></td>
					</tr>

					<tr>
						<th>门户栏目<span class="star">*</span> ：
						</th>
						<td><input id="insPortCols" name="insPortCols" class="mini-textboxlist" style="width: 80%;" value="${colId}" text="${colName}" readonly /> 
						<a class="mini-button" iconCls="icon-add" plain="true" onclick="addColumns()">添加</a> </td>
					</tr>

					<tr>
						<th>描述 ：</th>
						<td><textarea name="desc" class="mini-textarea" vtype="maxLength:512" style="width: 90%">${insPortal.desc}</textarea></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<div id="win1" class="mini-window" title="Window" style="width: 500px; height: 100px;" showCollapseButton="true" showShadow="true" showToolbar="true" showFooter="true" showModal="false" allowResize="true" allowDrag="true">按照输入的列数来填写，例如3列：250,100%,250。百分号表示剩余空白的百分比。如果全是数字将按照数字比例。</div>
	<script type="text/javascript">
        mini.parse();
		//增加栏目
        function addColumns(){
			var insPortCols=mini.get('insPortCols');
			insPortCols.setValue("");
			insPortCols.setText("");
			_ColumnsDlg(false,function(columns){
				var uIds=[];
				var uNames=[];
				for(var i=0;i<columns.length;i++){
					uIds.push(columns[i].colId);
					uNames.push(columns[i].name);
				}
				if(insPortCols.getValue()!=''){
					uIds.unshift(insPortCols.getValue().split(','));
				}
				if(insPortCols.getText()!=''){
					uNames.unshift(insPortCols.getText().split(','));	
				}
				insPortCols.setValue(uIds.join(','));
				insPortCols.setText(uNames.join(','));
			});
		}
        //栏目的选择框
        function _ColumnsDlg(single,callback){
        	
        	_OpenWindow({
        		url:__rootPath + '/oa/info/insColumn/Select.do?single='+single,
        		height:350,
        		width:780,
        		title:'栏目选择',
        		ondestroy:function(action){
        			if(action!='ok')return;
        			var iframe = this.getIFrameEl();
                    var users = iframe.contentWindow.GetData();//调用上一个页面的方法
                    if(callback){
                    	if(single && users.length>0){
                    		callback.call(this,users[0]);
                    	}else{
                    		callback.call(this,users);
                    	}
                    }
        		}
        	});
        }
        //帮助按钮
        function showTips() {
            mini.showTips({
                content: "按照输入的列数来填写，例如3列：250,100%,250。百分号表示剩余空白的百分比。如果全是数字将按照数字比例。",
                state: "info",
                x: "center",
                y: "bottom",
                timeout: 10000
            });
        }

    </script>
	<rx:formScript formId="form1" baseUrl="oa/info/insPortal" />
</body>
</html>