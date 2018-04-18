<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[${sysMenu.name}]菜单的按钮管理</title>
        <%@include file="/commons/list.jsp"%>
         <link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
 
    </head>
    <body>
    <div class="mini-toolbar" style="padding:2px;border:0;/* border-bottom:1px solid #aaa; */">
	    <table style="width:100%;">
	        <tr>
	        <td style="width:100%;">
	            <a class="mini-button" iconCls="icon-save" plain="true" onclick="onSave">保存</a>
	            <a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow('cancel')">关闭</a>
	        </td>
	      </tr>
	    </table>
	</div>
    
    <div class="mini-fit">
	    <div id="layout1" class="mini-layout" style="width:100%;height:100%;">
		    <div title="north" region="north" height="60"  showHeader="false" showSplitIcon="false" showSplit="false" style="border:0;padding:5px;">
			      <form id="menuForm" >
			       <table class="table-detail column_2" cellspacing="1" cellpadding="0" style="width:100%">
			              <tr>
			                  <th style="white-space: nowrap;">菜单名称<span class="star">*</span> </th>
			                  <td> 
			                  	  <input class="mini-hidden" name="menuId" value="${sysMenu.menuId}"/>
			                      <input name="name" value="${sysMenu.name}" class="mini-textbox" required="true" vtype="maxLength:60" emptyText="请输入菜单名称" style="width:80%"/>
			                  </td>
			                  <th style="white-space: nowrap;">菜单绑定的模块名<span class="star">*</span> </th>
			                  <td >    
			                      <input name="entityName" value="${sysMenu.entityName}" class="mini-textbox" required="true" vtype="maxLength:256" emptyText="请输入模块名" style="width:80%"/>
			                  </td>
			              </tr>
			 		</table>
			 	</form>
		    </div>
	
		    <div title="center" region="center"   showHeader="false" showSplitIcon="false" showSplit="false" style="border:0;padding:5px;">
			    <div id="toolbar1" class="mini-toolbar" style="padding:2px;border-bottom: 1px solid #ccc;">
				    <table style="width:100%;">
				        <tr>
				        <td style="width:100%;">
				            <a class="mini-button" iconCls="icon-add" plain="true" onclick="addButton">添加</a>
				            <a class="mini-button" iconCls="icon-add" plain="true" onclick="addButtons">添加默认全部</a>
				            <a class="mini-button" iconCls="icon-remove" plain="true" onclick="delSelectedRow">删除</a>
				        </td>
				      </tr>
				    </table>
				</div>
				<div class="mini-fit">
					<div id="grid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"  idField="gdFieldId" 
			       		showPager="false" style="border:0"
			            allowCellEdit="true" allowCellSelect="true" oncellendedit="OnCellEndEdit"
			            url="${ctxPath}/sys/core/sysMenu/getByParentId.do?menuId=${sysMenu.menuId}">
			            <div property="columns">
			            	<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div> 
			                <div field="key" name="key" align="left" width="100">按钮标识键
			                  <input property="editor" class="mini-combobox" textField="text" valueField="name" url="${ctxPath}/sys/core/sysMenu/buttonTypes.do"  required="true" allowInput="true" />   
			                </div>
			                <div field="name" name="name"  align="left" width="100">按钮名称
			                  <input property="editor" class="mini-textbox" style="width:100%;" required="true"/>
			                </div>
			                <div name="url" field="url" align="left" width="160">访问地址
			                  <input property="editor" class="mini-textbox" style="width:100%;"/>
			                 </div>
			                <div name="sn" field="sn" align="left" align="left" width="50">序号
			                  <input property="editor" class="mini-spinner" style="width:100%;"/>
			                </div>
			            </div>
			        </div>
		       </div>
		    </div>
		</div>
  	</div>
	<script type="text/javascript">
		mini.parse();
		var grid=mini.get('grid1');
		grid.load();
		var form=new mini.Form('menuForm');
		//编辑
        function onActionRenderer(e) {
            var record = e.record;
            var uid=record._uid;
            var s = '<span class="icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
            return s;
        }
		
		//用于用户选择下拦按钮时，进行配置相应行的功能
        function OnCellEndEdit(e){
			var record=e.record;
			var grid=e.sender;
			var field=e.field;
			var val=e.value;
			var editor=e.editor;
			if(field=='key'){
				var data=editor.getData();
				for(var i=0;i<data.length;i++){
					if(val==data[i].name){
						grid.updateRow(record,{name:data[i].text});
						break;
					}
				}
			}
		}
		
		function delSelectedRow(){
			var selected=grid.getSelected();
			if(!selected){
				alert('请选择要删除的行！');
				return;
			}
			delRow(selected._uid);
		}
		
		//删除行
		function delRow(uid){
			var row=grid.getRowByUID(uid);
			if(!row) return;
			if(!confirm('确定要删除选择的行？')) return;
			if(row.pkId){
				_SubmitJson({
					url:__rootPath+'/sys/core/sysMenuMgr/del.do?menuId='+row.pkId,
					success:function(text){
						grid.removeRow(row);
					}
				});
			}else{
				grid.removeRow(row);
			}
		}
		
		function onSave(){
			form.validate();
			if(!form.isValid()){
				return;
			}
			var data=form.getData();
			var gridData=grid.getData();
			data.buttonJson=mini.encode(gridData);
			_SubmitJson({
				url:__rootPath+'/sys/core/sysMenu/saveButtons.do',
				method:'POST',
				data:data,
				success:function(text){
					CloseWindow('ok');
				}
			});
		}
		function addButton(){
			grid.addRow({},0);
		}
		
		function addButtons(){
			$.ajax({
		        url: __rootPath+'/sys/core/sysMenu/buttonTypes.do',
		        type: 'GET',
		        cache: false,
		        success: function(text) {
		        	var result=mini.decode(text);
					for(var i=0;i<result.length;i++){
						var key=result[i].name;
						var found=false;
						
						for(var j=0;j<grid.getData().length;j++){
							var row=grid.getRow(j);
							if(row.key==key){
								found=true;
								break;
							}
						}
						if(!found){
							grid.addRow({
								name:result[i].text,
								key:key
							});
						}
					}
		        }
			});
		}
		
	</script>
</body>
</html>