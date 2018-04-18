<%-- 
    Document   : 业务模型属性编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>业务模型属性编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div id="toolbar1" class="mini-toolbar" style="padding:2px;" >
	    <table style="width:100%;">
	        <tr>
	            <td style="width:100%;">
	                <a class="mini-button" iconCls="icon-save" plain="true" onclick="saveModel()">保存</a>
	                <a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow('cancel')">关闭</a>
	            </td>
	        </tr>
	    </table>
	</div>
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div style="padding: 5px;">
				<input id="pkId" name="attId" class="mini-hidden" value="${bpmFormAtt.attId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>业务模型属性基本信息</caption>
					<tr>
						<th>属性标签 <span class="star">*</span> ：</th>
						<td><input name="title" value="${bpmFormAtt.title}" class="mini-textbox" vtype="maxLength:64" required="true" emptyText="请输入属性标签" style="width:90%"/></td>
						<th>属性标识 <span class="star">*</span> ：
						</th>
						<td><input name="key" id="key" value="${bpmFormAtt.key}" class="mini-textbox" vtype="maxLength:100" required="true" emptyText="请输入属性标识" style="width:90%"/></td>
					</tr>
					<tr>
						<th>属性数据类型 <span class="star">*</span> ：</th>
						<td>
						
							<input id="dataType" name="dataType" class="mini-combobox" style="width:80%" onvaluechanged="dataTypeChanged"
							data="[{'id':'String','text':'字符串'},{'id':'Date','text':'日期'},{'id':'Number','text':'数字'},{'id':'CompositeType','text':'复合类型'}]" textField="text" valueField="id"
						    value="String"  required="true" allowInput="false" />
							
							
						</td>
						<th>
							绑定控件类型
						</th>
						<td>
							<ui:dicCombox id="ctlType" name="ctlType" value="${bpmFormAtt.ctlType}" dicKey="CTL_TYPE_"/>
						</td>
					</tr>
					<tr id="typeRow">
						<th>属性类型 <span class="star">*</span> ：</th>
						<td colspan="3">
						 	<input id="type" name="type" class="mini-buttonedit" emptyText="请输入..."  onbuttonclick="onSelectType" allowInput="false" selectOnFocus="true" style="width:80%"/>
						</td>
					</tr>
					<tr id="lenRow">
						<th>长度 ：</th>
						<td colspan="3"><input id="len" name="len" value="50" class="mini-spinner" minValue="1" maxValue="60000" vtype="maxLength:10" /></td>
					</tr>
					<tr id="precRow">
						<th>精度(小数位) ：</th>
						<td colspan="3"><input id="prec" name="prec" value="${bpmFormAtt.prec}" class="mini-spinner" minValue="0" maxValue="8" vtype="maxLength:10" /></td>
					</tr>
					<tr>
						<th>默认值 ：</th>
						<td>
							<input name="defVal" value="${bpmFormAtt.defVal}" class="mini-textbox" vtype="maxLength:255" style="width:90%"/>
						</td>
						<th>分组标题 ：</th>
						<td><input name="groupTitle" value="${bpmFormAtt.groupTitle}" class="mini-textbox" vtype="maxLength:100" /></td>								
					</tr>
					<tr>
						<th>是否必须 <span class="star">*</span> ：</th>
						<td><input name="required" class="mini-checkbox" vtype="maxLength:20" required="true" emptyText="请输入是否必须" />&nbsp;是</td>
						<th>序号 <span class="star">*</span> ：</th>
						<td><input name="sn" value="${bpmFormAtt.sn}" class="mini-spinner" minValue="1" maxValue="1000" required="true" emptyText="请输入序号" /></td>
					</tr>
					<tr>
						<th>属性帮助描述 ：</th>
						<td colspan="3"><textarea name="remark" class="mini-textarea" vtype="maxLength:512" style="width: 90%">${bpmFormAtt.remark}</textarea></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	
	<script type="text/javascript">
		mini.parse();
		//记录当前编辑记录行的uid
		var uid=null;
		var form=new mini.Form('form1');
		function getModelJson(){
			return form.getData();
		}
		//父窗口的grid
		var parentGrid=null;
		
		function setMainGrid(grid){
			parentGrid=grid;
		}
		
		function saveModel(){
			form.validate();
			if(!form.isValid()) return;
			var key=mini.get('key').getValue();
			
			if(parentGrid){
				var isKeyExist=false;
				for(var i=0;i<parentGrid.getData().length;i++){
					var row=parentGrid.getData()[i];
					if(row.key==key && (uid==null || uid!=row._uid)){
						isKeyExist=true;
						break;
					}
				}
				if(isKeyExist){
					alert('属性标识值['+key+']已经存在！');
					return;
				}
			}
			
			CloseWindow('ok');
		}
		
		$(function(){
			//默认选择第一项
			changeRow(mini.get('dataType').getValue());
		});
		
		function changeRow(dataType){
			if(dataType=='String'){
				$("#typeRow").css('display','none');
				$("#lenRow").css('display','');
				mini.get("len").setValue(50);
				$("#precRow").css('display','none');
			}else if(dataType=='CompositeType'|| dataType=='CompositeCollection'){
				$("#typeRow").css('display','');
				$("#lenRow").css('display','none');
				$("#precRow").css('display','none');
			}else if(dataType=='Number'){
				$("#typeRow").css('display','none');
				$("#lenRow").css('display','');
				$("#precRow").css('display','');
				mini.get("len").setValue(18);
			}else if(dataType=='Date'){
				$("#typeRow").css('display','none');
				$("#lenRow").css('display','none');
				$("#precRow").css('display','none');
			}
		}
		
		//若为复合类型，则弹出类型选择器供用户进行选择具体的类型
		function dataTypeChanged(){
			var val=mini.get('dataType').getValue();
			var type=mini.get('type');
			if(val=='CompositeType'|| val=='CompositeCollection'){
				type.setShowButton(true);		
			}else{
				type.setValue('');
				type.setText('');
				type.setShowButton(false);
			}
			
			if(val=='Number'){
				mini.get('prec').setAllowInput(true);
			}else{
				mini.get('prec').setAllowInput(false);
			}
			changeRow(val);
		}
		
		function onSelectType(){
			var type=mini.get('type');
			_OpenWindow({
				title:'业务模型选择',
				url:__rootPath+'/bpm/bm/bpmFormModel/dialog.do',
				width:780,
				height:450,
				ondestroy:function(action){
					if(action!='ok')return;
					var iframe = this.getIFrameEl();
		            var formModel = iframe.contentWindow.getFormModel();
		            if(formModel){
			            type.setValue(formModel.fmId);
			            type.setText(formModel.name);
		            }
				}
			});
		}
		
		function setData(data){
			uid=data._uid;
			form.setData(data);
			changeRow(data.dataType);
		}
	</script>
</body>
</html>