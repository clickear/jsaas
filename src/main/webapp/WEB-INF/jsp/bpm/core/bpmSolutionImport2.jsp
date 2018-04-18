<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>业务流程解决方案导入</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom" >
	   <table width="100%">
	       <tr>
	     		<td style="width:100%;">
					<a class="mini-button" plain="true" iconCls="icon-next" onclick="doNext">下一步</a>
					<a class="mini-button" plain="true" iconCls="icon-close" onclick="CloseWindow()">关闭</a>
				</td>
			</tr>
		</table>
	</div>
	
	<form id="zipForm" action="${ctxPath}/bpm/core/bpmSolution/import2.do" method="post" enctype="multipart/form-data">
		<input class="mini-hidden" id="ids" name="ids" value="${param['ids']}"/>
		<table cellpadding="0" cellspacing="0" class="table-detail" style="padding:6px;">
			<caption>第二步：流程方案解析结果</caption>
			<tr>
				<th width="150">方案名称</th>
				<th>导入结果分析</th>
			</tr>
			<c:forEach items="${bpmSolutionImpEntities}" var="ent">
			<tr>
    			<td>
    				${ent.solName}
    			</td>
    			<td>
    				<ul>
    					<li><span>流程业务解决方案-${ent.bpmSolutionResult.name}</span>
    						Key:<input type="text" value="${ent.bpmSolutionResult.newKey}"/>
    						操作:<input type="radio" name="importType" value="${ent.bpmSolutionResult.importType}"/>
    					</li>
    					<li><span>流程定义-${ent.bpmSolutionResult.name}</span>
    						Key:<input type="text" value="${ent.bpmDefResult.newKey}"/>
    						操作:<input type="radio" name="importType" value="${ent.bpmDefResult.importType}"/>
    					</li>
    					<c:if test="${fn:length(ent.bpmFormViewResults)>0}">
    					<li><span>流程表单</span>
    						<ul>
    							<c:forEach items="${ent.bpmFormViewResults}" var="vr">
    							<li>
    								${vr.name}-<input type="text" value="${vr.newKey}"/>
    								<input type="radio" name="importType" value="${vr.importType}"/>
    							</li>
    							</c:forEach>
    						</ul>	
    					</li>
    					</c:if>
    				</ul>
    				
    			</td>
			</tr>
			</c:forEach>
		</table>
	</form>
	<script type="text/javascript">
		mini.parse();
		
		
		function doNext(){
			var file=document.getElementById("zipFile");
    		if(file.value==''){
    			alert('请上传流程解决方案的压缩文件！');
    			return;
    		}
    		
    		$("#zipForm").submit();
		}
	</script>
</body>
</html>