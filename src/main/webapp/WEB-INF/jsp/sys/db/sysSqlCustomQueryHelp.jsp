<%-- 
    Document   : [自定义查询]编辑页
    Created on : 2017-02-21 15:32:09
    Author     : cjx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[自定义查询]编辑</title>

<%@include file="/commons/edit.jsp"%>
<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
<script src="${ctxPath}/scripts/codemirror/mode/groovy/groovy.js"></script>
<script src="${ctxPath}/scripts/codemirror/addon/edit/matchbrackets.js"></script>
<script src="${ctxPath}/scripts/share/dialog.js"></script>


</head>
<body>
	<div class="heightBox"></div>
	<div class="shadowBox90" style="padding-top: 8px;">
		<input id="pkId" name="id" class="mini-hidden" value="${sysSqlCustomQuery.id}" />


		<table class="table-detail column_1" cellspacing="1" cellpadding="0">
<!-- 			<caption></caption> -->
	
			<tr>
				<th>js引用文件地址</th>
				<td>该方法文件定义在scripts/share.js中</td>
			</tr>
			<tr>
				<th>方法使用</th>
				<td>
				参数格式：var params = <span id="paramExample"></span>;
				<span>
					doQuery('${sysSqlCustomQuery.key}', params,function(data){
						//data 返回数据格式 <span id="resultText"></span>
					});
				</span>
				</td>
				
			</tr>
			
			
			<tr>
			<th>备　　注</th>
				<td><div>如果是in操作的参数的格式,如:{"fieldName":"value1,value2"},</div>
				<div>between操作的参数格式,如{"fieldName":"value1|value2"}</div></td>
			</tr>
			
		</table>
	</div>

	<script type="text/javascript">
		addBody();
		mini.parse();

		var pageParamString="";
		var dataString="";
		
		$(function(){
			var resultFieldString='${sysSqlCustomQuery.resultField}';
			if(resultFieldString!=""){
				var resultField=mini.decode(resultFieldString);
				var resultFieldText="";
				var resultText="";
				for(var i=0;i<resultField.length;i++){
					if(i==0){
						resultFieldText=resultField[i].comment+"("+resultField[i].fieldName+")";
						resultText='"'+resultField[i].fieldName+'":""';
						continue;
					}
					resultFieldText+=","+resultField[i].comment+"("+resultField[i].fieldName+")";
					resultText+=',"'+resultField[i].fieldName+'":""';
				}
				
				$("#resultField").html(resultFieldText);
				dataString=(${sysSqlCustomQuery.isPage}==0)?('[{'+resultText+'},{'+resultText+'},...]'):('{total:20,data:[{'+resultText+'},{'+resultText+'},...]}');
				$("#resultText").html(dataString);
			}
			
			var whereFieldString='${sysSqlCustomQuery.whereField}';
			var text="";
			if(whereFieldString!=""){
				var whereField=mini.decode(whereFieldString);
				var whereFieldText="";
				
				for(var i=0;i<whereField.length;i++){
					if(whereField[i].valueSource=='param'){
						if(text==""){
							text='"'+whereField[i].fieldName+'":""';
							continue;
						}
						text+=',"'+whereField[i].fieldName+'":""';
					}
				}
			}
			pageParamString=(${sysSqlCustomQuery.isPage}==0)?('{'+text+'}'):('{'+text+',"pageIndex":"0"} //pageIndex为页码(0是第一页)');
			$("#paramExample").html(pageParamString);

			
		});
		
		

	
		
	</script>
</body>
</html>


