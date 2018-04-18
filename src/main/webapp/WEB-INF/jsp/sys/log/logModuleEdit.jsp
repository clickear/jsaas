
<%-- 
    Document   : [日志模块]编辑页
    Created on : 2017-09-25 16:49:23
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[日志模块]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="logModule.id" />
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden" value="${logModule.id}" />
				<table class="table-detail column_2" cellspacing="1" cellpadding="0">
					<caption>[日志模块]基本信息</caption>
					<tr>
						<th>模　　块</th>
						<td>
							
								<input 
									name="module" 
									value="${logModule.module}"
									class="mini-textbox"   
									style="width: 90%" 
								/>
						</td>
					</tr>
					<tr>
						<th>子  模  块</th>
						<td>
							
								<input name="subModule" value="${logModule.subModule}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>启　　用</th>
						<td>
							<div 
								id="enable" 
								name="enable" 
								class="mini-radiobuttonlist" 
								repeatItems="2" 
								repeatLayout="table" 
								repeatDirection="vertical"
    							textField="text" 
    							valueField="id" 
    							value="${logModule.enable}" 
    							data="[{'id':'TRUE','text':'启用'},{'id':'FALSE','text':'禁用'}]"
   							>
								<input name="enable" class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="sys/log/logModule"
		entityName="com.redxun.sys.log.entity.LogModule" />
	
	<script type="text/javascript">
	mini.parse();
	addBody();
	
	

	</script>
</body>
</html>