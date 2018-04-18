<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建信息</title>
<%@include file="/commons/dynamic.jspf" %>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="toolbar1" class="mini-toolbar" style="padding:2px;">
	    <table style="width:100%;">
	        <tr>
		        <td style="width:100%;">
		            <a class="mini-button" iconCls="icon-add" plain="true" onclick="addDocBack">添加</a>
		            <a class="mini-button" iconCls="icon-canel" plain="true">取消</a>
		        </td>
	        </tr>
	    </table>
	</div>
	<div style="width: 100%; text-align: center">
		<div style="margin-left: auto; margin-right: auto; padding: 5px;">
			<form id="form1">
				<table class="form-table" cellpadding="1" cellspacing="0">
					<tr>
						<th class="form-table-th">链接类型</th>
						<td colspan="3" class="form-table-td">
							<div id="type" name="type" class="mini-radiobuttonlist" onvaluechanged="fromChanged" required="true"
							data="[{id:'kmsDoc',text:'知识文档'},{id:'url',text:'外部链接'}]" value="kmsDoc"></div>
						</td>
					</tr>
					<tr id="kmsDoc_row" style="">
						<th class="form-table-th">
							文档列表
						</th>
						<td class="form-table-td" colspan="2">
							<input id="kdDocName" class="mini-textboxlist" name="kdDocName" style="width:300px;" valueField="docId" textField="subject" allowInput="false" />
						</td>
						<td class="form-table-td">
							<a class="mini-button" iconCls="icon-add" plain="true" onclick="addDoc"></a>
						</td>
					</tr>
					<tr id="url_row" style="display:none">
						<th class="form-table-th">
							外部链接
						</th>
						<td class="form-table-td">
							链接文字：<input id="note" name="note" class="mini-textbox" />
							<br/>
							链接地址：<input id="link" name="link" class="mini-textbox" value="http://" />
						</td>
						<td class="form-table-td" colspan="2">
							
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<script type="text/javascript">
	
	function fromChanged(e){
		var val=mini.get('type').getValue();
		if(val=='kmsDoc'){
			$("#kmsDoc_row").css('display','');
			$("#url_row").css('display','none');
		}else if(val=='url'){
			$("#kmsDoc_row").css('display','none');
			$("#url_row").css('display','');
		}
	}
	
	function addDoc(){
		_OpenWindow({
			url:__rootPath+'/kms/core/kdDoc/chooseKdDoc.do',
			title:'知识文档列表',
			width:'400',
			height:'250',
			ondestroy : function(action) {
				if (action == "ok") {
					var iframe = this.getIFrameEl();
					var data = iframe.contentWindow.GetData();
					data = mini.clone(data); 
					var tbl=mini.get("kdDocName");
					if (data) {
						for (var i = 0; i < data.length; i++) {
							var id=tbl.getValue();
							var text=tbl.getText();
							if(id!=''&&text!=''){
								tbl.setValue(id+","+data[i].docId);
								tbl.setText(text+","+data[i].subject);
							}
							else{
								tbl.setValue(data[i].docId);
								tbl.setText(data[i].subject);
							}
						}
						
					}
				}
			}
		});
	}
	
	function addDocBack(){
		CloseWindow("ok");
	}
	
	function GetData() {
		var docName = mini.get("kdDocName");
		return docName.getValue();
	}
	</script>
</body>
</html>