<%-- 
    Document   : 产品类型编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>产品类型编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/common/form.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var oaType=[{ id: 'Radio-list', text: '单选' }, { id: 'Checkbox-list', text: '多选'}, { id: 'Textbox', text: '文本'}, { id: 'Number', text: '数字'}, { id: 'Date', text: '时间'}, { id: 'Textarea', text: '大文本'}];
var oaData=[{ id: 'String', text: '字符' }, { id: 'Number', text: '数字'}, { id: 'Date', text: '时间'}, { id: 'Textarea', text: '大文本'}];
</script>
</head>
<body>
	<div class="mini-toolbar" style="padding:2px;" >
	    <table style="width:100%;">
	        <tr>
	            <td style="width:100%;">
	             	<a class="mini-button" iconCls="icon-save" plain="true" onclick="onSave">保存</a>
	             	<span class="separator"></span>
	                <a class="mini-button" iconCls="icon-close" plain="true" onclick="onClose">关闭</a>
	            </td>
	        </tr>
	    </table>
	</div>
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="keyId" class="mini-hidden"
					value="${oaProductDefParaKey.keyId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>产品类型基本信息</caption>

					<tr>
						<th>所属分类* ：</th>
						<td>
						<input id="treeId" name="treeId" class="mini-treeselect" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_ASSETS_SORT" 
						multiSelect="false" textField="name" valueField="treeId" parentField="parentId"  required="true" value="${oaProductDefParaKey.treeId}"
						showFolderCheckBox="false"  expandOnLoad="true" showClose="true" oncloseclick="onClearTree" popupWidth="300" style="width:90%"/>

						</td>
						<th>名称 *：</th>
						<td><input name="name" value="${oaProductDefParaKey.name}" required="true" emptyText="请输入名称"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>

					<tr>

						<th>数据类型*：</th>
						<td><input id="dataType" name="dataType" required="true" data="oaData" value="String"
							 class="mini-combobox" textField="text" valueField="id" style="width: 90%" allowInput="false" />
						</td>
						
						<th>控件类型：</th>
						<td><input name="controlType" data="oaType" emptyText="请选择..."
							value="${oaProductDefParaKey.controlType}" class="mini-combobox"
							vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>是否唯一 *：</th>
						<td colspan="3">
							<div id="isUnique" name="isUnique" value="${oaProductDefParaKey.isUnique}"
								class="mini-radiobuttonlist" repeatItems="1"
								repeatLayout="table" repeatDirection="vertical" required="true"
								emptyText="请输入会议状态"
								data="[{id:'0',text:'是'},{id:'1',text:'否'}]">
							</div>
						</td>
					</tr>

					<tr>
						<th>描述 ：</th>
						<td colspan="3"><textarea name="desc" class="mini-textarea" vtype="maxLength:512" style="width: 90%">${oaProductDefParaKey.desc}</textarea></td>
					</tr>
				</table>
			</div>
		</form>
		<div class="mini-toolbar" style="padding:2px;">
		    <table style="width:100%;">
		        <tr>
		            <td style="width:100%;">
		             	<a class="mini-button" iconCls="icon-add" plain="true" onclick="onAdd">添加产品属性</a>
		             	<span class="separator"></span>
		                <a class="mini-button" iconCls="icon-remove" plain="true" onclick="onDelete()">删除产品属性</a>
		                <span class="separator"></span>
			            <a class="mini-button" iconCls="icon-up" plain="true" onclick="upRow">向上</a>
			            <a class="mini-button" iconCls="icon-down" plain="true" onclick="downRow">向下</a>
		            </td>
		        </tr>
		    </table>
		</div>
			<!-- 字段属性配置 -->
		 <div class="mini-fit" style="border:0;padding:5px;">
			<div id="datagrid1" class="mini-datagrid"
			style="width:100%; height:100%" allowResize="false" url="${ctxPath}/oa/product/oaProductDefParaValue/getByKeyId.do?keyId=${param['pkId']}" idField="valueId" multiSelect="true" showColumnsMenu="true"
			 sizeList="[5,10,20,50,100,200,500]" pageSize="20"
			allowAlternating="true" pagerButtons="#pagerButtons">
				<div property="columns">
					<div type="checkcolumn" width="20"></div>
					<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
					<div field="name" width="150" headerAlign="center">名称
						<input property="editor" class="mini-textbox" required="true" value="25" style="width:100%;"/>
					</div>
					<div field="number" width="100" headerAlign="center">数字类型
						<input property="editor" class="mini-textbox" required="true" value="25" style="width:100%;"/>
					</div>
					<div field="string" width="100" headerAlign="center">字符串类型
						<input property="editor" class="mini-textbox" required="true" value="25" style="width:100%;"/>
					</div>
					<div field="text" width="100" headerAlign="center">文本类型
						<input property="editor" class="mini-textbox" required="true" value="25" style="width:100%;"/>
					</div>
					<div field="datetime" width="100" headerAlign="center">时间类型
						<input property="editor" class="mini-textbox" required="true" value="25" style="width:100%;"/>
					</div>
				</div>
			</div>
		</div>
	<script type="text/javascript">
		mini.parse();
		var grid=mini.get('datagrid1');
		grid.load();
		var form=new mini.Form('form1');
		
		
		//编辑
		function onActionRenderer(e) {
		    var record = e.record;
		    var uid = record._uid;
		    var s = ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + uid + '\')"></span>'
		            + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
		    return s;
		}
		
		$(function(){
			var type="${oaProductDefParaKey.dataType}";
			if(type!=''){
				mini.get("dataType").setValue(type);
			}
		});
		
		//上移一行
		function upRow() {
            var row = grid.getSelected();
            if (row) {
                var index = grid.indexOf(row);
                grid.moveRow(row, index - 1);
            }
        }
		//下移一行
        function downRow() {
            var row = grid.getSelected();
            if (row) {
                var index = grid.indexOf(row);
                grid.moveRow(row, index + 2);
            }
        }
		
		//保存
		function onSave(){
			form.validate();
			if(!form.isValid()){
				return;
			}
			var formData=$("#form1").serializeArray();
	        //加上租用Grid的属性
	    	var data = grid.getData();
            var json = mini.encode(data);
            formData.push({
            	name:'atts',
            	value:json
            });
	       
	        _SubmitJson({
	        	url:"${ctxPath}/oa/product/oaProductDefParaKey/save.do",
	        	method:'POST',
	        	data:formData,
	        	success:function(result){
	        		CloseWindow('ok');
	        	}
	        });
		}
		
		//编辑产品属性
		function onAdd(){
			_OpenWindow({
				title:'产品属性管理',
				url:__rootPath+'/oa/product/oaProductDefParaValue/edit.do',
				width:600,
				height:600,
				onload:function(){
					var iframe = this.getIFrameEl();
					
					iframe.contentWindow.setMainGrid(grid);
					iframe.contentWindow.changeRow(mini.get('dataType').getValue());
				},
				ondestroy:function(action){
					if(action=='ok'){
						var iframe = this.getIFrameEl();
			            var modelJson = iframe.contentWindow.getModelJson();
						grid.addRow(modelJson);
					}
				}
			});
		}
		
		//关闭
		function onClose(){
			CloseWindow('close');
		}
		
		function onClearTree(e) {
	            var obj = e.sender;
	            obj.setText("");
	            obj.setValue("");
	    }
		
		//删除
		function delRow(uid){
			var rs=grid.getRowByUID(uid);
			if(rs.valueId!=undefined && rs.valueId!=''){
				_SubmitJson({
					url:__rootPath+'/oa/product/oaProductDefParaValue/del.do',
					data:{
						ids:rs.valueId
					},
					method:'POST'
				});
			}
			grid.removeRow(rs);
		}
		
		//编辑
		function editRow(uid){
			var rs=grid.getRowByUID(uid);
			_OpenWindow({
				url:__rootPath+'/oa/product/oaProductDefParaValue/edit.do?valueId='+rs.valueId,
				title:'编辑产品属性',
				width:600,
				height:600,
				onload:function(){
					var bodyWin=this.getIFrameEl().contentWindow;
					 bodyWin.setData(rs);
					 bodyWin.changeRow(mini.get('dataType').getValue());
				},
				ondestroy:function(action){
					if(action!='ok')return;
					var bodyWin=this.getIFrameEl().contentWindow;
		            var modelJson = bodyWin.getModelJson();
					grid.updateRow(rs,modelJson);
				}
			});
		}
		//删除子行
		function onDelete(){
			var rows=grid.getSelecteds();
			var mIds=[];
			for(var i=0;i<rows.length;i++){
				var rs=rows[i];
				if(rs.valueId!=undefined && rs.valueId!=''){
					mIds.push(rs.valueId);
				}
				grid.removeRow(rs);
			}
			if(mIds.length>0){
				_SubmitJson({
					url:__rootPath+'/oa/product/oaProductDefParaValue/del.do',
					data:{
						ids:mIds.join(',')
					},
					method:'POST'
				});
			}
		}
		
        grid.on("drawcell", function (e) {
            var record = e.record,
	        field = e.field,
	        value = e.value;
            //格式化日期
            if (field == "datetime") {
                if (mini.isDate(value)) e.cellHtml = mini.formatDate(value, "yyyy-MM-dd HH:mm");
            }

    		});
        grid.on('update',function(){
        	_LoadUserInfo();
        });
		
	</script>
</body>
</html>