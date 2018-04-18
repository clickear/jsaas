<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>知识文档列表管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>

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
	
	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/kms/core/kdDoc/getByKey.do?key=KD_DOC" idField="docId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons" onLoad="setSelectColumn">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div field="sysTree.name" width="50" headerAlign="center" allowSort="true">所属分类</div>
				<div field="subject" width="50" headerAlign="center" allowSort="true">文档标题</div>
				<div field="author" width="50" headerAlign="center" allowSort="true">作者</div>
				<div field="status" width="50" headerAlign="center" allowSort="true">文档状态</div>
				<div field="version" width="50" headerAlign="center" allowSort="true">版本号</div>
			</div>
		</div>
	</div>
	
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.kms.core.entity.KdDoc" entityTitle="知识文档" baseUrl="kms/core/kdDoc" />
	<script type="text/javascript">
	/* $(function(){
		var noteId="${param['noteId']}";
		var docId="${param['docId']}";
		_SubmitJson({
			url:__rootPath+"/kms/core/kdDoc/getChooseDoc.do",
			data:{
				noteId:noteId,
				docId:docId
			},
			method:'POST',
			success:function(result){
				var data=result.data;
				var grid=mini.get("datagrid1");
				var rows = grid.getData();
				for (var i = 0; i < data.length; i++) {
					for (var j = 0; j < rows.length; j++) {
						if(data[i].docId==rows[j].docId){
							grid.setSelected(rows[j]);
							break;
						}
					}
				}
			}
		});
	}); */
	
	/* $(function(){
		grid.load();
		setSelectColumn();
	}); */
	
	function setSelectColumn(){
		var docIds="${param['docIds']}";
		var docIdsArray=docIds.split(',');
		var grid=mini.get("datagrid1");
		var rows = grid.getData();
		for(var i=0;i<docIdsArray.length;i++){
			 for(var j=0,l=rows.length;j<l;j++){
				var row= rows[j];
				if(row.docId ==docIdsArray[i]){
					grid.setSelected(row);
					break;
				}
			} 
		}
	}
	
	function getData() {
		var grid = mini.get("datagrid1");
		return grid.getSelecteds();
	}
	
	function addDocBack(){
		CloseWindow("ok");
	}
	</script>
</body>
</html>