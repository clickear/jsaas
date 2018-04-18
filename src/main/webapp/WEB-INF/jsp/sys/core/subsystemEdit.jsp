<%-- 
    Document   : 子系统编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>子系统编辑</title>
<%@include file="/commons/edit.jsp"%>
  <script src="${ctxPath}/scripts/share/dialog.js" type="text/javascript"></script>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${subsystem.sysId}"/>
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
			<input id="pkId" name="pkId" class="mini-hidden" value="${subsystem.sysId}" />
			
					<table  class="table-detail column_2_m" cellpadding="0" cellspacing="1">
						<caption>子系统基本信息</caption>
						<tr>
							<th>
								<span class="starBox">
									系统名称<span class="star">*</span>
								</span>
							</th>
							<td><input name="name" value="${subsystem.name}" class="mini-textbox" required="true" vtype="maxLength:80" emptyText="请输入系统名称" style="width:68%"/></td>
						
							<th>
								<span class="starBox">
									系统 Key<span class="star">*</span>
								</span>		
							</th>
							<td><input name="key" value="${subsystem.key}" class="mini-textbox" required="true" vtype="maxLength:64" emptyText="请输入系统Key" style="width:68%"/></td>
						</tr>
					
						
						<tr>
							<th>
								<span class="starBox">
									是否缺省 <span class="star">*</span>
								</span>
							</th>
							<td>
								 <div name="isDefault" class="mini-radiobuttonlist" value="${subsystem.isDefault}" required="true" repeatDirection="vertical"
								  repeatLayout="table" url="${ctxPath}/dics/commons/boolean_status.txt" textField="text" valueField="id" ></div>
							</td>
							<th>
								<span class="starBox">
									状　　态 <span class="star">*</span>
								</span>
							</th>
							<td>
								<div name="status" class="mini-radiobuttonlist" value="${subsystem.status}" required="true" repeatDirection="vertical"
								  repeatLayout="table" url="${ctxPath}/dics/commons/enable_status.txt" textField="text" valueField="id"  vtype="maxLength:20"></div>
							</td>
						</tr>
						<tr>
							<th>图标样式</th>
							<td>
								<input name="iconCls" id="iconCls"  value="${subsystem.iconCls}" class="mini-buttonedit" onbuttonclick="selectIcon" style="width:80%"/>
		    					<a class="mini-button" id="icnClsBtn" iconCls="${subsystem.iconCls}"></a>
							</td>
							<th>序　　号</th>
							<td><input name="sn" value="${subsystem.sn}" class="mini-spinner" minValue="0" maxValue="1000"/></td>
						</tr>
						
						<tr>
							<th>描　　述</th>
							<td colspan="3">
								<textarea rows="3" cols="60" class="mini-textarea" vtype="maxLength:256" name="descp" style="width:98%">${subsystem.descp}</textarea>
							</td>
						</tr>
					</table>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="sys/core/subsystem" />
	<script type="text/javascript">
	
		addBody();
		$(function(){
			
		});
		
		function selectIcon(e){
			 var btn=e.sender;
			 _IconSelectDlg(function(icon){
					//grid.updateRow(row,{iconCls:icon});
					btn.setText(icon);
					btn.setValue(icon);
					mini.get('icnClsBtn').setIconCls(icon);
				});
		}
	</script>
</body>
</html>