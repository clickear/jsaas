<%-- 
    Document   : new Search
    Created on : 2015-3-31, 14:16:16
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>高级查询</title>
  <%@include file="/commons/edit.jsp"%>
   <link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <div style="display:none;">
	        <input class="mini-textbox" id="textboxEditor" />
	        <input class="mini-spinner" id="spinnerEditor" />
	        <input class="mini-datepicker" id="datepickerEditor" format="yyyy-MM-dd HH:mm:ss" />
	
	        <input id="fieldNodeTypeEditor" class="mini-combobox" textField="nodeTypeLabel" valueField="nodeType" url="${ctxPath}/dics/ui/search_node_types.txt"/>
	        <input id="fieldOpEditor" class="mini-combobox" textField="fieldOpLabel" valueField="fieldOp"/>
	        <input class="mini-combobox" id="fieldEditor" textField="title" valueField="attrName" 
	               parentField="parentId"  onvaluechanged="onFieldChanged"
	               url="${ctxPath}/ui/module/getModuleFields.do?entityName=${sysModule.entityName}"/>
	 </div>
    <div class="mini-layout" style="width:100%;height:100%;margin:0;padding:0" >
		    
		    <div title="north" region="north" height="100" showSplitIcon="false" showHeader="false" bodyStyle="padding:0;">
			    <div class="mini-toolbar" >
			        <table style="width:100%;">
			            <tr>
				            <td style="width:100%;">
				                <a class="mini-button" iconCls="icon-save" plain="true" onclick="onSave()">保存</a>
				                <span class="separator"></span>
				                <a class="mini-button" iconCls="icon-addfolder" plain="true" onclick="onAddRow();">增加条件</a>
				                <a class="mini-button" iconCls="icon-addfolder" plain="true" onclick="onAddSubRow();">增加子条件</a>
				                <a class="mini-button" iconCls="icon-remove" plain="true" onclick="onRemoveRow();">删除</a>
				                 <a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow();">关闭</a>
				            </td>
			            </tr>
			        </table>
			    </div>
		        <form id="searchForm" method="post">
		            <input type="hidden" name="searchId" class="mini-hidden" value="${sysSearch.searchId}"/>
		            <table style="width:100%">
		                <tr>
		                    <td style="width:95px;">搜索名称</td>
		                    <td colspan="3">
		                        <input type="hidden" name="entityName" class="mini-hidden" value="${sysModule.entityName}"/>
		                        <input name="name" class="mini-textbox" required="true" style="width:90%" vtype="maxLength:100" value="${sysSearch.name}" emptyText="请输入搜索名称"/>
		                    </td>
		                </tr>
		                <tr>
		                    <td style="width:95px;">是否启用</td>
		                    <td>
		                        <div id="enabledRb" name="enabled" class="mini-radiobuttonlist" repeatItems="4" repeatLayout="table" repeatDirection="horizontal"
		                            textField="text" valueField="id" value="${sysSearch.enabled}" 
		                            url="${ctxPath}/dics/commons/boolean_status.txt">
		                        </div>
		                    </td>
		                    <td style="width:95px;">是否缺省</td>
		                    <td>
		                        <div id="isDefaultRb" name="isDefault" class="mini-radiobuttonlist" repeatItems="4" repeatLayout="table" repeatDirection="horizontal"
		                            textField="text" valueField="id" value="${sysSearch.isDefault}" 
		                            url="${ctxPath}/dics/commons/boolean_status.txt">
		                        </div>
		                    </td>
		                </tr>
		            </table>
		        </form>
		     </div>
			<div region="center" showHeader="false" showSplitIcon="false" style="border:0;" bodyStyle="border:0;"> 
		        <div id="searchGrid" class="mini-treegrid" style="width:100%;height:100%"     
		            showTreeIcon="true" 
		            treeColumn="nodeType" idField="itemId" parentField="parentId" 
		            resultAsTree="false"  
		            allowResize="true" expandOnLoad="true" oncellbeginedit="OnCellBeginEdit"
		            allowCellEdit="true" allowCellSelect="true" url="${ctxPath}/ui/search/sysSearch/getSearchItems.do?searchId=${param.searchId}">
		            <div property="columns">
		                <div displayfield="nodeTypeLabel"  name="nodeType" field="nodeType" align="left" width="100">条件类型</div>
		                <div field="fieldName" name="fieldName" displayfield="fieldTitle" align="left" width="100">字段名</div>
		                <div displayfield="fieldOpLabel" name="fieldOp" field="fieldOp" align="left" width="60">比较符</div>
		                <div name="fieldVal" field="fieldVal" width="80" align="left" dateFormat="yyyy-MM-dd HH:mm:ss">值</div>
		            </div>
		        </div>
	    	</div>
    	</div>
    <script type="text/javascript">
        mini.parse();
        //var searchId='${param.searchId}';
        var grid = mini.get("searchGrid");
        var form=new mini.Form("searchForm");
        //grid.load();  
        function OnCellBeginEdit(e) {
            var record = e.record, field = e.field;
            if (field == "nodeType") {
                e.editor=mini.get('fieldNodeTypeEditor');
            }else if(record.nodeType=='FIELD'){//当行为字段条件时，为每个列设置其编辑器
                if (field=='fieldName'){
                    e.editor = mini.get('fieldEditor');
                }else if(field=='fieldOp'){
                    e.editor=mini.get('fieldOpEditor');
                    if(record.fieldType=='java.lang.String'){
                        e.editor.setUrl('${ctxPath}/dics/ui/compares_of_string.txt');
                    }else{
                        e.editor.setUrl('${ctxPath}/dics/ui/compares_of_num_date.txt');
                    }
                }else if(field=='fieldVal'){
                    if(record.fieldType=='java.lang.Long' || record.fieldType=='java.lang.Integer'
                            || record.fieldType=='java.lang.Decimal' || record.fieldType=='java.lang.Float'){
                        e.editor=mini.get('spinnerEditor');
                    }else if(record.fieldType=='java.util.Date'){
                        e.editor=mini.get('datepickerEditor');
                    }else{
                        e.editor=mini.get('textboxEditor');
                    }
                }
            }else{
              e.record.fieldName='';
              e.record.fieldOp='';
              e.record.fieldVal='';
              e.editor=null;
              //不允许编辑
              e.cancel=true;
            }
            e.column.editor=e.editor;
        }
        
        //当字段值变化时，存储其值放在另一属性
        function onFieldChanged(e){
            var combo=e.sender;
            var row=grid.getEditorOwnerRow(combo);
            var val=combo.getValue();
            var comboData=combo.getData();
            for(var i=0;i<comboData.length;i++){
                if(val==comboData[i].attrName){
                    row.fieldType=comboData[i].fieldType;
                    row.fieldId=comboData[i].fieldId;
                    row.label=comboData[i].title;
                    row.ctlType=comboData[i].compType;
                    break;
                }
            }
        }
        
        function onSave(){
            var data = grid.getData();
            var json = mini.encode(data);
        
            form.validate();
            if (form.isValid() == false) {
                return;
            }
            var formData = form.getData();
            formData.searchItems=json;
            
            
            _SubmitJson({
            	 url: "${ctxPath}/ui/search/sysSearch/save.do",
                 data: formData,
                 method: "POST",
                 success:function(result){
                	 CloseWindow('ok');
                 }
            });
            
           
        } 
        //删除行
        function onRemoveRow(){
             var node = grid.getSelectedNode();
             if (node) {
                if (confirm("确定删除该条件？")) {
                     if(node.itemId!=null){
                        $.ajax({
                               url: "${ctxPath}/ui/search/sysSearch/delItem.do",
                               data:{
                                  searchId:pkId
                               },
                               success: function(text) {
                                  grid.removeNode(node);
                               },
                               error: function() {
                                   
                               }
                       });
                    }else{
                        grid.removeNode(node);
                    }
                }
            }
        }
        
        //添加父行
        function onAddRow(){
            var node = grid.getSelectedNode();
            var newNode = {nodeType:'AND',nodeTypeLabel:'与'};//
            grid.addNode(newNode, "before", node);
        }
        
        function onAddSubRow(){
            var node = grid.getSelectedNode();
            if(node!=null && node.nodeType=='FIELD'){
                alert('字段条件下不能增加子条件！');
                return;
            }
            var newNode = {nodeType:'FIELD',nodeTypeLabel:'字段'};
            grid.addNode(newNode, "add", node);
        }
        
        var parentWin=null;
        function setData(data){
            //接收父窗口中window对象
            parentWin = data.pWin;
        }

    </script>
</body>
</html>