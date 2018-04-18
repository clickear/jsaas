
<%-- 
    Document   : [INS_MSG_DEF]编辑页
    Created on : 2017-08-31 14:54:23
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>消息编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="insMsgDef.id" />
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden" value="${insMsgDef.msgId}" />
				<table class="table-detail column_2" cellspacing="1" cellpadding="0">
					<caption>消息基本信息</caption>
					<tr>
						<th>标　　题</th>
						<td>
							
								<input name="content" value="${insMsgDef.content}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
<%-- 					<tr>
						<th>颜　色</th>
						<td>
							
								<input name="color" value="${insMsgDef.color}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr> --%>
					<tr>
						<th>更多URl</th>
						<td>
							
								<input name="url" value="${insMsgDef.url}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>图　　标</th>
						<td>
							<input name="icon" id="icon" value="${insMsgDef.icon}" text="${insMsgDef.icon}" class="mini-buttonedit" onbuttonclick="selectIcon" style="width:20%"/>
		    				<a class="mini-button" id="icnClsBtn"></a>
							
						</td>
					</tr>
					<%-- <tr>
						<th>数据源</th>
                         <td colspan="3">   
                         	<input id="btnDataSource" name="dsName" textName="dsAlias" text="${insMsgDef.dsName}" value="${insMsgDef.dsAlias}" class="mini-buttonedit" onbuttonclick="onDataSourceEdit"/> 
                         </td>
					</tr> --%>
					<tr>
						<th>类　　型</th>
						<td>
							<div class="mini-radiobuttonlist" value="${insMsgDef.type}" require=true
    						textField="text" valueField="id"  id="type" name="type"  data="[{id:'function',text:'调用方法'},{id:'sql',text:'调用sql语句'}]" ></div>
						</td>
					</tr>
					<tr>
						<th>调用方法或SQL语句</th>
						<td>
							
								<textarea name="sqlFunc"  require=true
							class="mini-textarea"   style="width: 90%;height:200px;" >${insMsgDef.sqlFunc}</textarea>
						</td>
					</tr>					
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/info/insMsgDef"
		entityName="com.redxun.oa.info.entity.InsMsgDef" />
	<script type="text/javascript">
		addBody();
		function onDataSourceEdit(e){
	 	   var btnEdit=e.sender;
	 	   _CommonDialogExt({dialogKey:"dataSourceDialog",title:"选择数据源",ondestroy:function(data){
	 		   var row=data[0];
	 		   btnEdit.setText(row.NAME_);
	 		   btnEdit.setValue(row.ALIAS_);
	 	   }})
	    }
		
		//选择图标
	    function selectIcon(e){
	 	   var btn=e.sender;
	 	   _IconSelectDlg(function(icon){
				//grid.updateRow(row,{iconCls:icon});
				btn.setText(icon);
				btn.setValue(icon);
				mini.get('icnClsBtn').setIconCls(icon);
			});
	    }
		
		$(function(){
			var icon = '${insMsgDef.icon}';
    		mini.get('icnClsBtn').setIconCls(icon);
		})
	</script>
</body>
</html>