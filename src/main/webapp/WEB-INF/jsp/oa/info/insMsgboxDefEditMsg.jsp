
<%-- 
    Document   : [栏目消息盒子表]编辑页
    Created on : 2017-09-01 11:35:24
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[栏目消息盒子表]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="insMsgboxDef.id" >
		<div class="self-toolbar" style="padding: 2px; text-align: left; border-bottom: none;">
			<a class="mini-button" iconCls="icon-add" plain="true"	onclick="addRow()">增加</a> 
			<a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeRow()">删除</a>
			<span class="separator"></span> 
			<a class="mini-button" iconCls="icon-up" plain="true" onclick="upRow()">向上</a> 
			<a class="mini-button" iconCls="icon-down" plain="true"	onclick="downRow()">向下</a>
		</div>
	</rx:toolbar>
			
		<div id="grid" class="mini-datagrid" style="width: 100%;" allowResize="false"
			 url="${ctxPath}/oa/info/insMsgboxBoxDef/getByBoxId.do?boxId=${boxId}" idField="id"  allowCellEdit="true" allowCellSelect="true"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div field="sn"  sortField="SN_"  width="120" headerAlign="center" allowSort="true">序号
					<input property="editor" class="mini-textbox" style="width:100%;" />
					</div>
				<div field="msgId" displayfield="content" sortField="MSG_ID_"  width="120" headerAlign="center" allowSort="true">消息
					<input property="editor" class="mini-combobox"  textField="content" valueField="msgId" url="${ctxPath}/oa/info/insMsgDef/listData.do">
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	mini.parse();
	var grid = mini.get("grid");
	var boxId = '${boxId}';
	
	$(function(){
		$.ajax({
			url:"${ctxPath}/oa/info/insMsgboxBoxDef/getByBoxId.do?boxId=${boxId}",
			method:"POST",
			success:function(result){
				grid.setData(result);
			}
		})
	})
	
	function onOk(){        
	    var data=grid.getData();
	    var json = mini.encode(data);
		var config={
        	url:"${ctxPath}/oa/info/insMsgboxBoxDef/saveMsgEntity.do",
        	method:'POST',
        	data:{data:json,
        		  boxId:boxId},
        	success:function(result){
        		//如果存在自定义的函数，则回调
        		if(isExitsFunction('successCallback')){
        			successCallback.call(this,result);
        			return;	
        		}
        		
        		CloseWindow('ok');
        	}
        }
	        
		_SubmitJson(config);
	}
	
	function addRow(){
		var index = grid.getData().length;
		grid.addRow({sn:index+1});
	}
	
	function removeRow(){
		var selecteds=grid.getSelecteds();
		if(selecteds.length>0 && confirm('确定要删除？')){
			grid.removeRows(selecteds);
		}
	}
	
	function upRow() {
        var row = grid.getSelected();
        if (row) {
            var index = grid.indexOf(row);
            grid.moveRow(row, index - 1);
        }
    }
    function downRow() {
        var row = grid.getSelected();
        if (row) {
            var index = grid.indexOf(row);
            grid.moveRow(row, index + 2);
        }
    }

	</script>
</body>
</html>