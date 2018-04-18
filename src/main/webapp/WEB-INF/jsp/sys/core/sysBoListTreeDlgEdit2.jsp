
<%-- 
    Document   : [系统自定义业务管理列表]编辑页
    Created on : 2017-05-21 12:11:18
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>树型对话框编辑</title>
<%@include file="/commons/edit.jsp"%>
<style type="text/css">
	.mini-panel-border .mini-grid-row:last-of-type .mini-grid-cell {
		border-bottom: 1px solid #ececec;
	}
</style>
</head>
<body>
	<div class="mini-toolbar mini-toolbar-bottom" >
	    <table style="width:100%;">
	        <tr>
	            <td style="width:100%;" id="toolbarBody">
	            	<a class="mini-button" iconCls="icon-prev" plain="true" onclick="onPrev">上一步</a>
	               	<a class="mini-button" iconCls="icon-next" plain="true" onclick="onGen">生成页面</a>
	               	<a class="mini-button" iconCls="icon-html" plain="true" onclick="onShowHtml">查看HTML</a>
	                <a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeRow">删除字段</a>
	                <a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow()">关闭</a>
	            </td>
	        </tr>
	    </table>
	</div>

	<div class="mini-fit">
		  <div 
		  	id="returnFieldGrid" 
		  	class="mini-datagrid" 
		  	style="width:100%;height:100%" 
		  	showPager="false" 
		  	allowCellSelect="true" 
		  	multiSelect="true"
		  	allowCellEdit="true"
		  	allowAlternating="true"
	  	>
			    <div property="columns">
			    	<div type="indexcolumn" width="40">序号</div>
			    	<div type="checkcolumn"  width="50" headerAlign="center"></div>
			        <div name="header" field="header" width="200" headerAlign="center">字段名称
			        	<input property="editor" class="mini-textbox" style="width:100%;" />
			        </div>              
			        <div field="field" width="200" headerAlign="center">字段Key</div>
			   </div>
		  </div>
	 </div>
	<textarea  id="fieldJson" style="display:none">${sysBoList.fieldsJson}</textarea>
	<script type="text/javascript">
		mini.parse();
		var returnFieldGrid=mini.get('returnFieldGrid');
		var fieldJson=$('#fieldJson').val();
		returnFieldGrid.setData(mini.decode(fieldJson));
		
		function onGen(){
			var fieldJsons=returnFieldGrid.getData();
			_SubmitJson({
				url:__rootPath+'/sys/core/sysBoList/onGenTreeDlg.do',
				data:{
					id:'${sysBoList.id}',
					fieldsJson:mini.encode(fieldJsons)
				}
			});
		}
		
		function onShowHtml(){
			var id='${param.id}';
        	_OpenWindow({
        		title:'编辑页面代码',
        		url:__rootPath+'/sys/core/sysBoList/edit3.do?id='+id,
        		width:850,
        		height:400
        	});
		}
		
		function removeRow(){
			var rows=returnFieldGrid.removeRows(returnFieldGrid.getSelecteds());
		}
		
		function onPrev(){
			location.href=__rootPath+'/sys/core/sysBoList/treeDlgEdit.do?pkId=${param.id}';
		}
	</script>
</body>
</html>