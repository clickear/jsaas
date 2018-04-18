<%-- 
    Document   : 流程业务模块绑定列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>流程业务模块绑定列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>      
	<div class="titleBar mini-toolbar">
		<ul>
			<li>
				<a class="mini-button" iconCls="icon-add" onclick="onAdd" >添加</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-save" onclick="onSave">保存</a>
			</li>
			<li class="clearfix"></li>
		</ul>
		<div class="searchBox">
			<form id="searchForm" class="text-distance" style="display: none;">						
				<ul>
					<li>
						<span class="text">模块名称</span>
						<input class="mini-textbox" name="Q_moduleName_S_LK"/>
					</li>
					<li>
						<span class="text">模块Key</span>
						<input class="mini-textbox" name="Q_moduleKey_S_LK"/>
					</li>
					<li>
						<span class="long">流程解决方案Key</span>
						<input class="mini-textbox" name="Q_solKey_S_LK"/>
					</li>
					<li>
						<span class="long">流程解决方案名称</span>
						<input class="mini-textbox" name="Q_solName_S_LK"/>
					</li>
					<li>
						<div class="searchBtnBox">
							<a class="mini-button _search" onclick="searchForm(this)">搜索</a>
							<a class="mini-button _reset" onclick="onClearList(this)">还原</a>
						</div>
					</li>
					<li class="clearfix"></li>
				</ul>
			</form>	
			<span class="searchSelectionBtn" onclick="no_search(this ,'searchForm')">
				<i class="icon-sc-lower"></i>
			</span>
		</div>
	</div>
	

	
	<div class="mini-fit rx-grid-fit"   >
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
		 	allowCellEdit="true" allowCellSelect="true" oncellvalidation="onCellValidation" 
			url="${ctxPath}/bpm/integrate/bpmModuleBind/listData.do" idField="bindId"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" >
			<div property="columns" >
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="moduleName" width="120" headerAlign="center" allowSort="true">模块名称
					<input property="editor" class="mini-textbox" style="width:100%;" vtype="maxLength:50" required="true"/>
				</div>
				<div field="moduleKey" width="140" headerAlign="center" allowSort="true">模块Key
					<input property="editor" class="mini-textbox" style="width:100%;" vtype="maxLength:80" required="true"/>
				</div>
				<div field="solKey" width="120" headerAlign="center" allowSort="true">流程解决方案Key
					<input property="editor" class="mini-buttonedit" allowInput="false"  onbuttonclick="selectSolution" />
				</div>
				<div field="solName" width="140" headerAlign="center" allowSort="true">流程解决方案名称</div>
			</div>
		</div>
	</div>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.integrate.entity.BpmModuleBind" 
		winHeight="450" winWidth="700" entityTitle="流程业务模块绑定"
		baseUrl="bpm/integrate/bpmModuleBind" />
	<script type="text/javascript">
    	function onAdd(){
    		grid.addRow({});
    	}
		//行功能按钮
        function onActionRenderer(e) {
            var record = e.record;
            var pkId = record.pkId;
            var s ='<span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
            return s;
        }
		
		function selectSolution(){
			_OpenWindow({
				title : '选择流程方案',
				url :__rootPath+'/bpm/core/bpmSolution/dialog.do?single=true',
				width : 780,
				height : 550,
				ondestroy : function(action) {
					if(action!='ok'){return;}
					var iframe = this.getIFrameEl();
				 	var sols = iframe.contentWindow.getSolutions();
				 	if(sols.length==0) return;
				 	
				 	grid.cancelEdit(); 
				 	var row=grid.getSelected();
				 	grid.updateRow(row,{
				 			solId:sols[0].solId,
				 			solName:sols[0].name,
				 			solKey:sols[0].key});
				}});
		}
			
		function onCellValidation(e){
        	if(e.field=='moduleKey' && (!e.value||e.value=='')){
        		 e.isValid = false;
        		 e.errorText='模块Key不能为空！';
        	}
        	if(e.field=='solKey' && (!e.value||e.value=='')){
        		e.isValid = false;
       		 	e.errorText='流程解决方案Key不能为空！';
        	}
        	
        	if(e.field=='moduleName' && (!e.value||e.value=='')){
        		e.isValid = false;
       		 	e.errorText='模块名称不能为空！';
        	}
        }
		
		function onSave(){
			//表格检验
        	grid.validate();
        	if(!grid.isValid()){
            	return;
            }
        	
        	//获得表格的每行值
        	var data = grid.getData();
            var json = mini.encode(data);
            
            _SubmitJson({
            	url:__rootPath+'/bpm/integrate/bpmModuleBind/batchSave.do',
            	method:'POST',
            	data:{
            		gridData:json
            	},
            	success:function(){
            		grid.load();
            	}
            });
		}
    </script>
</body>
</html>