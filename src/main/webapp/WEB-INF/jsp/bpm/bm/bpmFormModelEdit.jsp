<%-- 
    Document   : 业务模型编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>业务模型编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div class="mini-toolbar" style="padding:2px;" >
	    <table style="width:100%;">
	        <tr>
	            <td style="width:100%;">
	             	<a class="mini-button" iconCls="icon-save" plain="true" onclick="onSave">保存</a>
	             	<c:choose>
	             		<c:when test="${empty bpmFormModel.fmId or bpmFormModel.status=='INIT'}">
	             			<a class="mini-button" iconCls="icon-save" plain="true" onclick="deploy">发布</a>	
	             		</c:when>
	             		<c:otherwise>
	             			<a class="mini-button" iconCls="icon-save" plain="true" onclick="deployNew">发布为新版本</a>		
	             		</c:otherwise>
	             	</c:choose>
	             	<span class="separator"></span>
	                <a class="mini-button" iconCls="icon-close" plain="true" onclick="onClose">关闭</a>
	            </td>
	        </tr>
	    </table>
	</div>
	<form id="form1" method="post">
  			<div class="form-inner">
				<input id="pkId" name="fmId" class="mini-hidden" value="${bpmFormModel.fmId}" /> 
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>业务模型基本信息</caption>
					<tr>
						<th>分类 ：</th>
						<td>
							 <input id="treeId" name="treeId" class="mini-treeselect" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_FORM_MODEL" 
							 	multiSelect="false" textField="name" valueField="treeId" parentField="parentId"  required="true" value="${bpmFormModel.treeId}"
						        showFolderCheckBox="false"  expandOnLoad="true" showClose="true" oncloseclick="onClearTree"
						        popupWidth="300" style="width:96%"/>
						</td>
						<th>
							版本号 ：
						</th>
						<td>
							${bpmFormModel.version}
						</td>
					</tr>
					<tr>
						<th>名称 ：</th>
						<td><input name="name" value="${bpmFormModel.name}" class="mini-textbox" vtype="maxLength:128" style="width:90%" required="true"/></td>

						<th>标识键 <span class="star">*</span> ：
						</th>
						<td><input name="key" value="${bpmFormModel.key}" class="mini-textbox" vtype="key;maxLength:64" required="true" emptyText="请输入标识键" style="width:90%"/></td>
					</tr>

					<tr>
						<th>描述 ：</th>
						<td colspan="3"><textarea name="descp" class="mini-textarea" vtype="maxLength:512" style="width: 90%">${bpmFormModel.descp}</textarea></td>
					</tr>

				</table>
			</div>
		</form>
		<div class="mini-toolbar" style="padding:2px;">
		    <table style="width:100%;">
		        <tr>
		            <td style="width:100%;">
		             	<a class="mini-button" iconCls="icon-add" plain="true" onclick="onAdd">添加属性</a>
		             	<span class="separator"></span>
		                <a class="mini-button" iconCls="icon-remove" plain="true" onclick="onDelete()">删除属性</a>
		                <span class="separator"></span>
			            <a class="mini-button" iconCls="icon-up" plain="true" onclick="upRow">向上</a>
			            <a class="mini-button" iconCls="icon-down" plain="true" onclick="downRow">向下</a>
		            </td>
		        </tr>
		    </table>
		</div>
		<!-- 字段属性配置 -->
		 <div class="mini-fit" style="border:0;padding:5px;">
			<div id="datagrid1" class="mini-datagrid" showPager="false"
			style="width:100%; height:100%" allowResize="false" url="${ctxPath}/bpm/bm/bpmFormAtt/getByFmId.do?fmId=${param['pkId']}" idField="attId" multiSelect="true" showColumnsMenu="true"
			 allowCellEdit="true" allowCellSelect="true"
			allowAlternating="true">
				<div property="columns">
					<div type="checkcolumn" width="20"></div>
					<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
					<div field="title" width="150" headerAlign="center">属性标签
						<input property="editor" class="mini-textbox" required="true" value="25" style="width:100%;"/>
					</div>
					<div field="key" width="100" headerAlign="center">属性标识
						<input property="editor" class="mini-textbox" required="true" value="25" style="width:100%;"/>
					</div>
					<div field="dataType" width="100" headerAlign="center">属性数据类型</div>
					<div type="checkboxcolumn" trueValue="true" falseValue="false" field="required" width="50" headerAlign="center">是否必须</div>
					<div field="sn" width="50" headerAlign="center" allowSort="true">序号
						<input property="editor" class="mini-spinner"  minValue="0" maxValue="200" value="1" required="true" style="width:100%;"/>
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
		
		//发布新版本
		function deployNew(){
			postData(false,true);	
		}
		
		function deploy(){
			postData(true,false);
		}
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
		
		function onSave(){
			postData(false,false);
		}
		
		function postData(isDeploy,deployNew){
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
            formData.push({
            	name:'deploy',
            	value:isDeploy
            });
            
            formData.push({
            	name:'deployNew',
            	value:deployNew
            });
	       
	        _SubmitJson({
	        	url:"${ctxPath}/bpm/bm/bpmFormModel/save.do",
	        	method:'POST',
	        	data:formData,
	        	success:function(result){
	        		CloseWindow('ok');
	        	}
	        });
		}
		
		
		function onAdd(){
			_OpenWindow({
				title:'字段管理',
				url:__rootPath+'/bpm/bm/bpmFormAtt/edit.do',
				width:780,
				height:400,
				onload:function(){
					var iframe = this.getIFrameEl();
					iframe.contentWindow.setMainGrid(grid);
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
		
		function onClose(){
			CloseWindow('close');
		}
		
		function onClearTree(e) {
	            var obj = e.sender;
	            obj.setText("");
	            obj.setValue("");
	    }
		
		function delRow(uid){
			var rs=grid.getRowByUID(uid);
			if(rs.attId!=undefined && rs.attId!=''){
				_SubmitJson({
					url:__rootPath+'/bpm/bm/bpmFormAtt/del.do',
					data:{
						ids:rs.attId
					},
					method:'POST'
				});
			}
			grid.removeRow(rs);
		}
		
		function editRow(uid){
			var rs=grid.getRowByUID(uid);
			_OpenWindow({
				url:__rootPath+'/bpm/bm/bpmFormAtt/edit.do?attId='+rs.attId,
				title:'编辑属性',
				width:780,
				height:400,
				onload:function(){
					var bodyWin=this.getIFrameEl().contentWindow;
					bodyWin.setData(rs);
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
				if(rs.attId!=undefined && rs.attId!=''){
					mIds.push(rs.attId);
				}
				grid.removeRow(rs);
			}
			if(mIds.length>0){
				_SubmitJson({
					url:__rootPath+'/bpm/bm/bpmFormAtt/del.do',
					data:{
						ids:mIds.join(',')
					},
					method:'POST'
				});
			}
		}
		
	</script>
</body>
</html>