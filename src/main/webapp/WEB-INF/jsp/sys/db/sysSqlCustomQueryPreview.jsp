<%-- 
    Document   : [自定义查询]编辑页
    Created on : 2017-02-21 15:32:09
    Author     : cjx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[自定义查询]预览</title>
<%@include file="/commons/edit.jsp"%>
<style type="text/css">
	.search-ui::after{
		clear:both;
		display: block;
		content:'' ;
	}
	.search-ui li{
		float:left;
	}
</style>
</head>
<body>
<body>
    
	<input id="pkId" name="id" class="mini-hidden" value="${sysSqlCustomQuery.id}" />
	<input id="alias" name="alias" class="mini-hidden" value="${sysSqlCustomQuery.key}" />
	<div class="mini-toolbar">
		
			<form id="form1" class="search-form">
						<ul class="search-ui">
							<li>
							<c:forEach items="${whereList}" var="column">
								<c:choose>
									<c:when test="${column.valueSource=='param'}">
										<c:choose>
											<c:when test="${column.typeOperate=='BETWEEN'}">
												<span>${column.comment}：</span><input name="${column.fieldName}_start" class="mini-textbox" /> 到<input name="${column.fieldName}_end" class="mini-textbox" />
											</c:when>
											
											<c:otherwise>
												<span>${column.comment}：</span><input name="${column.fieldName}" class="mini-textbox" />
											</c:otherwise>
										</c:choose>
									</c:when>
								</c:choose>
							</c:forEach>
							</li>
							<li><a class="mini-button" iconCls="icon-search" plain="true" onclick="doCustomQuery()">查询</a> 
							<li><a class="mini-button"	iconCls="icon-cancel" plain="true" onclick="clearQuery()">清空查询</a></li></li>				
						</ul>
				</form>
		</div>
		<div class="mini-fit">
			返回JSON数据:
			<div style="width:100%;height:90%;">
				<textarea class="mini-textarea" id="sqlDiy" name="sqlDiy" emptyText="请输入"  style="width:100%;height:80%;"></textarea>
			</div>
		</div>
	<script type="text/javascript">
		mini.parse();
		var gridSearch = mini.get("gridSearch");
		function clearQuery() {
			var parent = $("div.search-form");
			var inputArray = parent.find("input");
			inputArray.each(function(i) {
				var el = $(this);
				el.val("");
			});
			mini.get("sqlDiy").setValue('');
		
		} 

		function doCustomQuery(){
			var formData=_GetCustomQueryFormJson("form1");
			var postData = mini.encode(formData);
			var callBack=function(result){
				var dataJson = JSON.stringify(result.data);
				mini.get("sqlDiy").setValue(dataJson);
			};
			
			doQuery(mini.get("alias").getValue(),postData,callBack);
		}
		
		
		function _GetCustomQueryFormJson(formId){
			var modelJson={};
			var formData=$("#"+formId).serializeArray();
			for(var i=0;i<formData.length;i++){
				modelJson[formData[i].name]=formData[i].value;
			}
			
			var handleJson={};
			for (var p in modelJson){
				if(typeof(modelJson[p])=="function")
					continue;
				else{
					if(p.indexOf("_start")>-1||p.indexOf("_end")>-1){
						var fieldName="";
						if(p.indexOf("_start")>-1){
							fieldName=p.substring(0,p.length-6);
							if((typeof(handleJson[p])=="undefined"||handleJson[p]=="")&&modelJson[p]!="")
								handleJson[fieldName]=modelJson[p]+"|"+modelJson[fieldName+'_end'];
						}
						if(p.indexOf("_end")>-1){
							fieldName=p.substring(0,p.length-4);
							if((typeof(handleJson[p])=="undefined"||handleJson[p]=="")&&modelJson[p]!="")
								handleJson[fieldName]=modelJson[fieldName+'_start']+"|"+modelJson[p];
						}
					}else{
						if(modelJson[p]!="")
							handleJson[p]=modelJson[p];
					}
				}
				
			}
			return handleJson;
		}

		function formQuery() {
			//查询列的json
			var queryColumnJsonObj = null;
			
			$.ajax({
				url : "${ctxPath}/sys/db/sysSqlCustomQuery/findQueryField.do",
				type : "POST",
				async : false,
				data : {
					pkId:mini.get("pkId").getValue(),
					json:formJson
					
				},
				success : function(result, status) {
					queryColumnJsonObj = JSON.parse(result);
					//动态设置列
					gridSearch.clearRows();
					var arrayColumnNew = new Array();
					arrayColumnNew.push({
						type : "checkcolumn"
					});
					for (var i = 0; i < queryColumnJsonObj.length; i++) {
						arrayColumnNew[arrayColumnNew.length] = {
							field : queryColumnJsonObj[i].columnName,
							header : queryColumnJsonObj[i].comment
						};
					}
					gridSearch.set({
						columns : arrayColumnNew
					});
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("出错,错误码=" + XMLHttpRequest.status);
					alert("出错=" + XMLHttpRequest.responseText);
				}
			});
			$.ajax({
				url : "${ctxPath}/sys/db/sysSqlCustomQuery/queryForJson.do",
				type : "POST",
				async : false,
				data : postData,
				success : function(result, status) {
					var dataJson = JSON.stringify(result);
					mini.get("sqlDiy").setValue(dataJson);
					var rJson = JSON.stringify(result);

					//动态设置数据
					var list = result.data.list;
					//	alert('result.data.list.length='+list.length);
					var dataRowsLength = list.length;
					for (var j = 0; j < dataRowsLength; j++) {
						var newRow = {};
						for (var i = 0; i < queryColumnJsonObj.length; i++) {
							var columnName = queryColumnJsonObj[i].columnName;
							newRow[columnName] = list[j][columnName];
						}

						gridSearch.addRow(newRow);
					}
					//gridSearch.load();
					mini.parse();
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("出错,错误码=" + XMLHttpRequest.status);
					alert("出错=" + XMLHttpRequest.responseText);
				}
			});
		}
	</script>
</body>
</html>