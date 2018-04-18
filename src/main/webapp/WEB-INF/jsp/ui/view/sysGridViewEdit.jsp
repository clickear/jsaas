<%-- 
    Document   : sysGridViewEdit
    Created on : 2015-4-17, 11:53:13
    Author     : 表格方案的编辑
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>表格方案的编辑</title>
    <%@include file="/commons/edit.jsp"%>
</head>
<body>
    <div style="display:none;">
        <input id="snEditor" class="mini-spinner" style="width:100%"/>
        <input class="mini-spinner" id="spinnerEditor" minValue="10" maxValue="200"/>
        <input class="mini-textbox" id="textboxEditor" />
        <textarea class="mini-textarea" id="textareaEditor"></textarea> 
        <input class="mini-combobox" id="fieldEditor" textField="title" valueField="attrName" 
               parentField="parentId"  onvaluechanged="onFieldChanged" allowInput="true"
               url="${ctxPath}/ui/module/getModuleFields.do?entityName=${sysModule.entityName}"/>
    </div>
    <div id="toolbar1" class="mini-toolbar" style="padding:2px;">
        <table style="width:100%;">
            <tr>
	            <td style="width:100%;">
	                <a class="mini-button" iconCls="icon-save"  onclick="onSave()">保存</a>
	                <span class="separator"></span>
	                <a class="mini-button" iconCls="icon-addfolder"  onclick="onAddTopGroupRow();">增加一级分组</a>
	                <a class="mini-button" iconCls="icon-addfolder"  onclick="onAddGroupRow();">增加分组</a>
	                <a class="mini-button" iconCls="icon-addfolder"  onclick="onAddFieldRow();">增加字段</a>
	                <a class="mini-button" iconCls="icon-remove" onclick="onRemoveRow();">删除</a>
	                <span class="separator"></span>
	                <a class="mini-button" iconCls="icon-close" onclick="CloseWindow();">关闭</a>
	            </td>
            </tr>
        </table>
    </div>
        <form id="sysGridViewForm" method="post">
            <c:if test="${empty asNew}">
                <input type="hidden" name="gridViewId" class="mini-hidden" value="${sysGridView.gridViewId}"/>
            </c:if>
            <table style="border-left:1px solid #909aa6;border-right:1px solid #909aa6;padding:5px;width:100%" >
                <tr>
                    <td style="width:95px;">视图方案名称</td>
                    <td colspan="3">
                        <input type="hidden" name="entityName" class="mini-hidden" value="${sysModule.entityName}"/>
                        <input name="name" class="mini-textbox" required="true" style="width:90%" vtype="maxLength:100" value="${sysGridView.name}" emptyText="视图方案名称"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:95px;">是否系统默认</td>
                    <td>
                        <div id="enabledRb" name="isSystem" class="mini-radiobuttonlist" repeatItems="4" repeatLayout="table" repeatDirection="horizontal"
                            textField="text" valueField="id" value="${sysGridView.isSystem}" 
                            url="${ctxPath}/dics/commons/boolean_status.txt">
                        </div>
                    </td>
                    <td style="width:95px;">是否用户缺省</td>
                    <td>
                        <div id="isDefaultRb" name="isDefault" class="mini-radiobuttonlist" repeatItems="4" repeatLayout="table" repeatDirection="horizontal"
                            textField="text" valueField="id" value="${sysGridView.isDefault}" 
                            url="${ctxPath}/dics/commons/boolean_status.txt">
                        </div>
                    </td>
                </tr>
            </table>
        </form>
        <div id="grid1" class="mini-treegrid" style="width:100%;height:270px;"     
            showTreeIcon="true" 
            treeColumn="fieldName" idField="gdFieldId" parentField="parentId" 
            resultAsTree="false"  
            allowResize="true" expandOnLoad="true" oncellbeginedit="OnCellBeginEdit"
            allowCellEdit="true" allowCellSelect="true" url="${ctxPath}/ui/view/sysGridView/fields.do?gdViewId=${param.gdViewId}">
            <div property="columns">
            	<div name="action" width="120" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
                <div displayfield="fieldTitle"  name="fieldName" field="fieldName" align="left" width="120">列名</div>
                <div field="colWidth" name="colWidth"  align="left" width="100">宽度</div>
                <div type="checkboxcolumn" field="isHidden" name="isHidden" trueValue="YES" falseValue="NO" align="left" width="60">是否隐藏</div>
                <div type="checkboxcolumn" field="allowSort" name="allowSort" trueValue="YES" falseValue="NO" align="left" width="60">是否排序</div>
                <div name="sn" field="sn" align="left" align="left" width="80">序号</div>
                <div name="format" field="format" align="left" width="100">格式</div>
            </div>
        </div>
    
    <script type="text/javascript">
        mini.parse();
        //var searchId='${param.searchId}';
        var grid = mini.get("grid1");
        var form=new mini.Form("sysGridViewForm");
        //grid.load();  
        function OnCellBeginEdit(e) {
            var record = e.record, field = e.field;
            if (field == "fieldName") {
                if(record.itemType=='FIELD'){
                    e.editor=mini.get('fieldEditor');
                    e.editor.setUrl('${ctxPath}/ui/module/getModuleFields.do?entityName=${sysModule.entityName}');
                }else{
                    e.editor=mini.get('fieldEditor');
                    e.editor.setUrl('${ctxPath}/dics/commons/empty_json.txt');
                }
            }else if(field=='colWidth'){
                e.editor=mini.get('spinnerEditor');
            }else if(field=='isHidden'){
                return;
            }else if(field=='format'){
                e.editor=mini.get('textareaEditor');
            }else if(field=='sn'){
                e.editor=mini.get('snEditor');
            }
            e.column.editor=e.editor;
        }
        
        
        function onActionRenderer(e) {
            var record = e.record;
            var uid = record._uid;
            
            var s = '<span class="icon-button icon-rowbefore" title="在前添加字段" onclick="newBeforeFieldRow(\''+uid+'\')"></span>';
            s+=' <span class="icon-button icon-rowafter" title="在后添加字段" onclick="newAfterFieldRow(\''+uid+'\')"></span>';
            
            //为树类型才允许往下添加
            if(record.itemType=='GROUP'){
            	s+= '<span class="icon-button icon-add" title="添加字段" onclick="newSubFieldRow(\''+uid+'\')"></span>';
            	s+= '<span class="icon-button icon-add" title="添加组" onclick="newSubGroupRow(\''+uid+'\')"></span>';
            }
            
            s+=' <span class="icon-button icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
            
            return s;
        }
        
        function delRow(uid){
        	
        	var node = grid.getRowByUID(uid);
            if (node) {
               if (confirm("确定删除该字段？")) {
                   
                    if(node.gdFieldId!=null && node.gdFieldId.indexOf('v_')==-1){
                       $.ajax({
                              url: "${ctxPath}/ui/view/sysGridView/delItems.do",
                              data:{
                                 gdFieldId:node.gdFieldId
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
        
        //当字段值变化时，存储其值放在另一属性
        function onFieldChanged(e){
            var combo=e.sender;
            var row=grid.getEditorOwnerRow(combo);
            var val=combo.getValue();
            var comboData=combo.getData();
            for(var i=0;i<comboData.length;i++){
                if(val==comboData[i].attrName){
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
            formData.fieldItems=json;
            
            _SubmitJson({
            	url: "${ctxPath}/ui/view/sysGridView/save.do",
                data: formData,
                method: "post",
                success:function(text){
                	CloseWindow('ok');
                }
            });
            
        } 
        //删除行
        function onRemoveRow(){
             var node = grid.getSelectedNode();
             if (node) {
                if (confirm("确定删除该字段？")) {
                     if(node.gdFieldId!=null && node.gdFieldId.indexOf('v_')==-1){
                        _SubmitJson({
                        	   url: "${ctxPath}/ui/view/sysGridView/delItems.do",
                               method:'POST',
                        	   data:{
                                  gdFieldId:node.gdFieldId
                               },
                               success: function(text) {
                                  grid.removeNode(node);
                               }
                        });
                    }else{
                        grid.removeNode(node);
                    }
                }
            }
        }
        //添加父行
        function onAddGroupRow(){
            var node = grid.getSelectedNode();
            var newNode = {itemType:'GROUP',fieldName:'未命名',fieldTitle:'未命名',colWidth:100,sn:1};//
            if(node && node.itemType=='GROUP'){
                 grid.addNode(newNode, "add", node);
            }else{
                grid.addNode(newNode, "before", node);
            }
        }
        
        function onAddTopGroupRow(){
            var node = grid.getSelectedNode();
            var newNode = {itemType:'GROUP',fieldName:'未命名',fieldTitle:'未命名',colWidth:100,sn:1};//
            grid.addNode(newNode, "before", node);
        }
        
        function onAddFieldRow(){
            var node = grid.getSelectedNode();
            if(node && node.itemType=='FIELD'){
                node=grid.getRootNode();
            }
            var newNode = {itemType:'FIELD',fieldName:'未命名',fieldTitle:'未命名',colWidth:100,sn:1};
            grid.addNode(newNode, "add", node);
        }
        
        //添加子字段行
        function newSubFieldRow(uid){
        	var node = grid.getRowByUID(uid);
            var newNode = {itemType:'FIELD',fieldName:'未命名',fieldTitle:'未命名',colWidth:100,sn:1};
            grid.addNode(newNode, "add", node);
        }
        
        function newBeforeFieldRow(uid){
        	var node = grid.getRowByUID(uid);
            var newNode = {itemType:'FIELD',fieldName:'未命名',fieldTitle:'未命名',colWidth:100,sn:1};
            grid.addNode(newNode, "before", node);
        }
        
        function newAfterFieldRow(uid){
        	var node = grid.getRowByUID(uid);
            var newNode = {itemType:'FIELD',fieldName:'未命名',fieldTitle:'未命名',colWidth:100,sn:1};
            grid.addNode(newNode, "after", node);
        }
        
        function newSubGroupRow(uid){
        	var node = grid.getRowByUID(uid);
        	var newNode = {itemType:'GROUP',fieldName:'未命名',fieldTitle:'未命名',colWidth:100,sn:1};//
            grid.addNode(newNode, "add", node);
        }
        
        //初始化表格控件
        function initGrids(columns){
            var rootNode=grid.getRootNode();
            var cn=0;
            for(var i=0;i<columns.length;i++){
                var col=columns[i];
                if(col.type=='checkcolumn' || col.name=='action'){
                    continue;
                }
              initGdRow(col,rootNode,++cn);
            }
        }
        
        function initGdRow(col,parentNode,cn){
 
            if(!col) return;
            
            var index=col.width.lastIndexOf('px');
            
            var width=col.width.substring(0,index);
            
            var itemType=col.columns?'GROUP':'FIELD';
            
            grid.addNode({
                    fieldName:itemType=='GROUP'?col.header:col.field,
                    fieldTitle:col.header,
                    colWidth:width,
                    isHidden:col.visible?'NO':'YES',
                    gdFieldId:'v_'+cn,
                    itemType:itemType
                },
                'add',
                parentNode);
            
            if(!col.columns) return ;
            
            var tmpNode=grid.getNode('v_'+cn);
            
            if(!tmpNode) return;
            
            for(var i=0;i<col.columns.length;i++){
                var c=col.columns[i];
                initGdRow(c,tmpNode,++cn);
            }
        }
    </script>
</body>
</html>