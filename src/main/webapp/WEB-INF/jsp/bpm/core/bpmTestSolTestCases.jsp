<%-- 
    Document   : 测试方案列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>业务流程解决方案测试用例</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div class="mini-toolbar mini-toolbar-bottom">
		<a class="mini-button" iconCls="icon-add" onclick="addTestCase()" plain="true">添加测试方案用例</a>
		<a class="mini-button" iconCls="icon-remove" plain="true" onclick="delTestCase()">删除测试方案用例</a>
		<a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow();">关闭</a>
	</div>
	<div class="mini-fit" style="height: 100%;">
		<div id="caseTabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" plain="false">
		    <c:forEach items="${testCases}" var="testCase">
			    <div title="${testCase.caseName}" name="${testCase.testId}" url="${ctxPath}/bpm/core/bpmTestCase/get.do?pkId=${testCase.testId}">
			    </div>
		    </c:forEach>
		</div>
	</div>
	
	<div id="caseWindow" class="mini-window" iconCls="icon-debug-white" title="添加测试用例" style="width:400px;height:150px;"
	 	showModal="true" showFooter="true" allowResize="true" allowDrag="true">
	    <form id="caseForm">
	    	<table class="table-detail table-detailNoBorder" cellpadding="0" cellspacing="1">
		    	<tbody>
			    	<tr>
			    		<th>用例名称:</th>
			    		<td>
			    			<input name="testSolId" class="mini-hidden" value="${param.testSolId}"/>
			    			<input name="actDefId" class="mini-hidden" value="${param.actDefId}"/>
			    			<input name="caseName" class="mini-textbox" required="true" style="width:90%"/>
			    		</td>
			    	</tr>
		    	</tbody>
	    	</table>
	    </form>
	    <div property="footer" >
   			<a class="mini-button" iconCls="icon-ok" onclick="submitTestCase()">提交</a>
   			<a class="mini-button" iconCls="icon-cancel" onclick="closeWin()">关闭</a>
	    </div>
	</div>
	
	<script type="text/javascript">
		mini.parse();
		var actDefId='${param.actDefId}';
		var caseWin=mini.get('caseWindow');
		var form=new mini.Form('caseForm');
		var tabs=mini.get('caseTabs');
		function closeWin(){
			caseWin.hide();
		}
		
		function addTestCase(){
			form.reset();
			caseWin.show();
		}
		
		function delTestCase(){
			var tab = tabs.getActiveTab();
			if(!tab) return;
			var id=tab.name;
			_SubmitJson({
				url:__rootPath+'/bpm/core/bpmTestCase/del.do?ids='+id,
				success:function(){
					location.reload();
				}
			});
		}
		
		function submitTestCase(){
			_SubmitJson({
				url:__rootPath+'/bpm/core/bpmTestCase/addNew.do',
				data:form.getData(),
				method:'POST',
				success:function(result){
					location.reload();
				}
			});
		}
		
		
	</script>
	
</body>
</html>