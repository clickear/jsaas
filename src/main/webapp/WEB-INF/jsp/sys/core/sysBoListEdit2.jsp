<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>[系统自定义业务管理列表]编辑2</title>
		<%@include file="/commons/edit.jsp"%>
		<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
		<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
		<script src="${ctxPath}/scripts/codemirror/mode/javascript/javascript.js"></script>
		<script src="${ctxPath}/scripts/share/dialog.js" type="text/javascript"></script>
		<style type="text/css">
			.mini-panel-border .mini-grid-row:last-of-type .mini-grid-cell{
				border-bottom: 1px solid #ececec;
			}
			
			.mini-panel-border{
				border: none;
			}
			
			.mini-grid-row{
				background: #fff;
			}
			
			.mini-grid-row-alt{
				background: #f7f7f7
			}
			
			#center{
				border-top: none;
			}
			
			#center .mini-panel-border{
				width: 100%;
			}
		</style>
	</head>
<body>
	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom">
		<a class="mini-button" iconCls="icon-prev"  plain="true" onclick="onPre">上一步</a>
		<a class="mini-button" iconCls="icon-save"  plain="true" onclick="onSaveConfigJson">保存</a>
		<a class="mini-button" iconCls="icon-start"  plain="true" onclick="onGenHtml">生成页面</a>
		<a class="mini-button" iconCls="icon-edit"  plain="true" onclick="onEditHtml">编辑页面代码</a>
<!-- 		<a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow()">关闭</a> -->
    </div>
	<div class="mini-fit">
		<div class="mini-tabs" style="width:100%;height:100%">
			
			<div title="列头配置" iconCls="icon-info">
				<c:if test="${not empty errorMsg}">
					<span style="color:red">${errorMsg}</span>
				</c:if>
				
				<div id="layout1" class="mini-layout" style="width:100%;height:100%;" borderStyle="border:none">
				    <div 
				    	title="表头字段设置"  
				    	region="center" 
				    	showSplitIcon="true" 
				    	bodyStyle="border:none;background-color:#f7f7f7;" 
				    	showCollapseButton="false" 
				    	showHeader="true"
			    	>
			    		<div class="titleBar mini-toolbar" >
					         <ul>
								<li>
									<span class="starBox">
			    						主键字段<span class="star">*</span>
		    						</span>
		    						<input 
			    					 	id="idField" 
			    					 	name="idField" 
			    					 	class="mini-combobox" 
			    					 	allowInput="true" 
							            textField="header" 
							            valueField="field" 
							            required="true" 
							            value="${sysBoList.idField}"
							            data="fieldDatas" 
						            />
								</li>
								<li>
									树节点字段
									<input 
			    					 	id="textField" 
			    					 	name="textField" 
			    					 	class="mini-combobox" 
			    					 	allowInput="true" 
							            textField="header" 
							            valueField="field"  
							            value="${sysBoList.textField}"
							            data="fieldDatas" 
						            />
								</li>
								<li>
									父Id字段
									<input 
			    					 	id="parentField" 
			    					 	name="parentField" 
			    					 	class="mini-combobox" 
			    					 	allowInput="true" 
							            textField="header" 
							            valueField="field"  
							            value="${sysBoList.parentField}"
							            data="fieldDatas" 
						            />
								</li>
								<li>
									锁定列第
			    					<input 
			    						id="startFroCol" 
			    						name="startFroCol" 
			    						class="mini-spinner" 
			    						minValue="0" 
			    						value="${sysBoList.startFroCol}" 
			    						maxValue="300" 
			    						style="width:50px"
		    						>
		    						列至
		    						<input 
		    							id="endFroCol" 
		    							name="endFroCol" 
		    							class="mini-spinner" 
		    							minValue="0" 
		    							value="${sysBoList.endFroCol}" 
		    							maxValue="300" 
		    							style="width:50px"
	    							>
	    							列
								</li>
								<li class="clearfix"></li>
							</ul>
							<ul>
								<li>
									<a class="mini-button" iconCls="icon-save" plain="true" onclick="reloadColumns">重新合并表头</a>
								</li>
								<li>
									<a class="mini-button" iconCls="icon-add" plain="true" onclick="addRow('headerGrid')">添加</a>
								</li>
								<li>
									<a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeRow('headerGrid')">删除</a>
								</li>
								<li>
									<a class="mini-button" iconCls="icon-up" plain="true" onclick="upRow('headerGrid')">向上</a>
								</li>
								<li>
									<a class="mini-button" iconCls="icon-down" plain="true" onclick="downRow('headerGrid')">向下</a>
								</li>
								<li>
									<a class="mini-button" iconCls="icon-left" plain="true" onclick="topRow('headerGrid')">上升一级</a>
								</li>
								<li>
									<a class="mini-button" iconCls="icon-right" plain="true" onclick="subRow('headerGrid')">下降一级</a>
								</li>
								<li>
									<a class="mini-button" iconCls="icon-expand" plain="true" onclick="expand();">展开</a>
								</li>
								<li>
									<a class="mini-button" iconCls="icon-collapse" plain="true" onclick="collapse();">收起</a>
								</li>
								<c:if test="${sysBoList.rowEdit=='YES'}">
			                		<li>
			                			<a class="mini-button" iconCls="icon-edit" plain="true" onclick="rowControlConfig">编辑控件配置</a>
			                		</li>
			                	</c:if>
								<li>
									<a class="mini-button" iconCls="icon-edit" plain="true" onclick="cellRenderConfig">字段渲染</a>
								</li>
								<li class="clearfix"></li>
							</ul>
					     </div>				    	
				    	<div class="mini-fit">
					        <div 
					        	id="headerGrid" 
					        	class="mini-treegrid" 
					        	style="width:100%;height:100%" 
					        	showPager="false"
				        		treeColumn="header" 
				        		idField="_uid" 
				        		parentField="_pid" 
				        		multiSelect="true"
				        		showTreeIcon="true" 
				        		allowCellEdit="true" 
				        		allowCellValid="true" 
				        		oncellvalidation="onHeaderGridCellValidation" 
					            resultAsTree="false" 
					            expandOnLoad="true" 
					            allowAlternating="true"
								allowCellSelect="true"
							>
								    <div property="columns" class="border-right">
								     	<div type="indexcolumn" width="20">序号</div>
								        <div type="checkcolumn" width="20"></div>
								        <div name="header" field="header" width="150" headerAlign="center">字段名称(*)
								        	<input property="editor" class="mini-textbox" style="width:100%;" />
								        </div>              
								        <div field="field" width="120" headerAlign="center">字段Key(*)
								            <input property="editor" class="mini-combobox" allowInput="true" 
								            textField="header" valueField="field" required="true" onvaluechanged="changeColumnFieldName"
								            style="width:100%;" data="fieldDatas" /> 
								        </div>
								        <div type="checkboxcolumn" field="allowSort" trueValue="true" falseValue="false" width="60" headerAlign="center">可排序</div>
								         
								        <div field="width" width="80" headerAlign="center">宽度
								        	<input property="editor" class="mini-spinner"  style="width:100%;" minValue="50" maxValue="1200"/>
								        </div>
								        <div field="dataType" width="80">数据类型
								        	<input 
								        		property="editor" 
								        		class="mini-combobox" 
								        		value="string"  
								        		textField="text" 
								        		valueField="id" 
								        		data="[{id:'string',text:'字符'},{id:'int',text:'整型'},{id:'float',text:'浮点型'},{id:'date',text:'日期型'},{id:'boolean',text:'布尔型'},{id:'currency',text:'货币型'}]"
							        		>
								        </div>
								        <div field="format" width="80" headerAlign="center">格式化
								        	<input property="editor" class="mini-textbox" style="width:100%;" />
								        </div> 
								        <c:if test="${sysBoList.rowEdit=='YES'}">
								        <div displayField="controlName" width="80" field="control">字段控件
								        	<input property="editor" class="mini-combobox" style="width:100%" data="rowControls" valueField="id" textField="text" allowInput="false" showNullItem="true" nullItemText="请选择"/>
								        </div>
								        </c:if>
								        <div field="headerAlign" width="80">文本位置
								        	<input property="editor" class="mini-combobox" value="center"  textField="text" valueField="id" data="[{id:'center',text:'居中'},{id:'left',text:'居左'},{id:'right',text:'居右'}]">
								        </div>
								        <!--div displayfield="linkField" field="linkFieldConf">关联字段
								        	<input property="editor" class="mini-buttonedit" onbuttonclick="linkButtonField">
								        </div-->
								        <div field="url" width="180" headerAlign="center">URL
								        	<input property="editor" class="mini-textbox" value="center" >
								        </div>
								        <div field="linkType" displayfield="linkTypeName" width="80">URL方式
								        	<input property="editor" class="mini-combobox" value="center"  textField="linkTypeName" valueField="linkType" data="[{linkType:'newWindow',linkTypeName:'新浏览器窗口'},{linkType:'openWindow',linkTypeName:'弹出窗口'},{linkType:'tabWindow',linkTypeName:'Tab窗口'}]">
								        </div>
								        <div field="renderType" displayfield="renderTypeName" width="100">渲染方式
								        	<input property="editor" class="mini-combobox" 
								        	value="center"  textField="renderTypeName" valueField="renderType" data="cololumRenderTypes">
								        </div>
								    </div>
								</div>
					    </div>
				    </div>
				    <div title="查询数据" region="south" showSplitIcon="true" showHeader="true" height="280" expanded="false">
					     <div id="sampleDataGrid" class="mini-datagrid" style="width:100%;height:100%;" allowCellEdit="true" 
					         url="${ctxPath}/sys/core/sysBoList/getSampleData.do?id=${param.id}"
					         allowCellSelect="true" >
						</div>
				    </div>
				</div>
			</div><!-- 列头配置结束  -->
			<c:if test="${sysBoList.isLeftTree=='YES'}">
			<div title="导航树配置" iconCls="icon-script">
				<div class="mini-toolbar">
						<a class="mini-button" iconCls="icon-add" plain="true" onclick="addTabRow">添加</a>
						<a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeTabRow">删除</a>
						<a class="mini-button" iconCls="icon-edit" plain="true" onclick="editTabRow">配置树</a>
				</div>
				<div class="mini-fit">
				        <div id="tabGrids" class="mini-datagrid" style="width:100%;height:100%" showPager="false"
								allowCellEdit="true" allowCellSelect="true" allowCellValid="true" allowAlternating="true"
								oncellvalidation="onTabGridsCellValidation">
							    <div property="columns" class="border-right">
							    	<div type="indexcolumn" width="20">序号</div>
							        <div name="tabName" field="tabName" width="100" headerAlign="center">导航Tab名称
							        	<input property="editor" class="mini-buttonedit" style="width:100%;" required="true" onbuttonclick="editTabRow"/>
							        </div>
							        <div field="treeId" width="100" headerAlign="center">树ID
							        	<input property="editor" class="mini-textbox" style="width:100%;" />
							        </div>
							        <div field="onnodeclick" width="100" headerAlign="center">点击事件
							        	<input property="editor" class="mini-textbox" style="width:100%;" />
							        </div>
							        
							        <div field="idField" width="80" headerAlign="center">值字段
							           <input property="editor" class="mini-textbox" style="width:100%;" />
							        </div>
							        <div field="textField" width="80" headerAlign="center">显示字段
							           <input property="editor" class="mini-textbox" style="width:100%;" />
							        </div>
							        <div field="parentField" width="80" headerAlign="center">父ID字段
							           <input property="editor" class="mini-textbox" style="width:100%;" />
							        </div>
							    </div>
						</div>
			    </div>
			    <textarea id="leftTreeJson" style="display:none">${sysBoList.leftTreeJson}</textarea>
			</div>
			</c:if>
			<div title="搜索条件配置" iconCls="icon-script">
				<div class="mini-toolbar">
							<a class="mini-button" iconCls="icon-add" plain="true" onclick="addSearchRow">添加</a>
							<a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeSearchRow">删除</a>
							<span class="separator"></span>
							<a class="mini-button" iconCls="icon-add" plain="true" onclick="addSearchSplit">添加分割行</a>
							<a class="mini-button" iconCls="icon-edit" plain="true" onclick="showSearchConfigEditor">配置</a>
							<a class="mini-button" iconCls="icon-up" plain="true" onclick="moveUpRow('searchGrid')">向上</a>
				            <a class="mini-button" iconCls="icon-down" plain="true" onclick="moveDownRow('searchGrid')">向下</a>
					</div>
				<div class="mini-fit">
					
					<div style="display:none;">
					 <input id="fieldOpEditor" class="mini-combobox" 
								textField="fieldOpLabel" valueField="fieldOp"
								style="width:100%;" data="numberOpData" />
					</div>
					 <div id="searchGrid" class="mini-datagrid" allowResize="true" showPager="false" oncellbeginedit="OnSearchCellBeginEdit" 
				        allowCellEdit="true" allowCellSelect="true" multiSelect="true" style="height:100%;width:100%;"
				        allowCellValid="true" oncellvalidation="onSearchGridCellValidation" 
				        editNextOnEnterKey="true" editNextRowCell="true" allowAlternating="true">
				        <div property="columns">
				            <div type="indexcolumn" width="20">序号</div>
				            <div field="type" displayField="typeName" width="100" headerAlign="center">参数类型
								<input property="editor" class="mini-combobox" 
					            textField="name" valueField="val" required="true"
					            style="width:100%;" data="[{name:'查询参数',val:'query'},{name:'快捷查询参数',val:'rapid_query'},{name:'传入参数',val:'income'}]" />
							</div>
				            <div field="fieldName" displayField="fieldText" headerAlign="center"  width="100">搜索字段
				                <input property="editor" class="mini-combobox" allowInput="true" required="true"
									textField="header" valueField="field" onvaluechanged="changeFieldName"
									style="width:100%;" data="fieldDatas" />
				            </div>
				            <div field="fieldLabel" width="100" headerAlign="center">标签
								<input property="editor" class="mini-textbox" style="width:100%;" />
							</div>
							 <div field="autoFilter" displayField="autoFilterName" width="100" headerAlign="center">自动过滤
								<input property="editor" class="mini-combobox" 
					            textField="name" valueField="id" 
					            style="width:100%;" data="[{id:'YES',name:'是'},{id:'NO',name:'否'}]" />
							</div>
				            <div field="fieldOp" displayField="fieldOpLabel" required="true"  headerAlign="center" width="100">比较符
				            </div>
				            <div field="fc" displayField="fcName"  headerAlign="center" width="100">输入控件
				                <input property="editor" class="mini-combobox" 
					            textField="fcName" valueField="fc" required="true"
					            style="width:100%;" data="fieldControls" />
				            </div>
				        </div>
			    	</div>
			    </div>	
			</div>
			<c:if test="${isAllowDataGrant==true}">
				<div title="数据权限" iconCls="icon-grant">
					<div class="mini-toolbar">
						<a class="mini-button" iconCls="icon-add" plain="true" onclick="addDataGrantRow">添加</a>
						<a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeDataGrantRow">删除</a>
					</div>
					<div style="display:none">
						<input id="userEditor" class="mini-combobox"  data="[{id:'SELF',text:'自己'},{id:'DUP_USERS',text:'直属上级用户'},{id:'UP_USERS',text:'所有上级用户'},{id:'DDOWN_USERS',text:'直属下级用户'},{id:'DOWN_USERS',text:'所有下级用户'}]">
						<input id="depEditor" class="mini-combobox"  data="[{id:'SELF_DEP',text:'默认部门'},{id:'DUP_DEPS',text:'直属上级部门'},{id:'UP_DEPS',text:'所有上部门'},{id:'DDOWN_DEPS',text:'直属下级部门'},{id:'DOWN_DEPS',text:'所有下级部门'}]">
						<input id="tenantEditor" class="mini-combobox"  data="[{id:'SELF_TENANT',text:'作者机构'},{id:'DUP_TENANTS',text:'上级机构'},{id:'UP_TENANTS',text:'所有上级机构'},{id:'DDOWN_TENANTS',text:'直属下级机构'},{id:'DOWN_TENANTS',text:'所有下级机构'}]">
						<textarea id="dataRightJson" style="display:none">${sysBoList.dataRightJson}</textarea>
					</div>
					<div class="mini-fit">
						<div id="dataGrantGrid" class="mini-datagrid" style="width:100%;height:100%" 
						  	showPager="false" allowAlternating="true"
						  	oncellbeginedit="onDataGrantGridCellEdit" 
						  	allowCellSelect="true" 
						  	allowCellEdit="true" >
							    <div property="columns">
							    	<div type="indexcolumn" width="40">序号</div>
							    	 <div type="checkcolumn" width="20"></div>
							    	<div name="field" field="field" displayField="field_name" width="200">筛选字段
							    		<input property="editor" class="mini-combobox" data="[{id:'CREATE_USER_ID_',text:'作者'},{id:'CREATE_GROUP_ID_',text:'作者部门'},{id:'TENANT_ID_',text:'作者机构'}]"/>
							    		<!-- {id:'ASSIGNEE_',text:'审批人'},{id:'HANDLER_ID_',text:'处理过'}, -->
							    	</div>
							        <div name="scope" field="scope" displayField="scope_name" width="200" headerAlign="center">作用域
							        	<input property="editor" class="mini-combobox" textField="scope_name" valueField="scope"  data="userGdDatas">
							        </div>
							   </div>
						  </div>
					</div>
				</div>
			</c:if>
			<c:if test="${sysBoList.isDialog=='NO'}">
				<div title="功能按钮" iconCls="icon-info">
						<div class="mini-toolbar">
							<a class="mini-button" iconCls="icon-add" plain="true" onclick="addToolbarRow">添加</a>
							<a class="mini-button" iconCls="icon-add" plain="true" onclick="addToolbarAllRows">添加全部默认</a>
							<a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeToolbarRow">删除</a>
							<a class="mini-button" iconCls="icon-up" plain="true" onclick="upRow('toolbarGrid')">向上</a>
			            	<a class="mini-button" iconCls="icon-down" plain="true" onclick="downRow('toolbarGrid')">向下</a>
			            	<a class="mini-button" iconCls="icon-left" plain="true" onclick="topRow('toolbarGrid')">上升一级</a>
				            <a class="mini-button" iconCls="icon-right" plain="true" onclick="subRow('toolbarGrid')">下降一级</a>
				            <a class="mini-button" iconCls="icon-add" plain="true" onclick="addToolbarSelfRow">添加脚本按钮</a>
				            <a class="mini-button" iconCls="icon-script" plain="true" onclick="editToolbarScript">配置自定义处理脚本</a>
				            <a class="mini-button" iconCls="icon-grant" plain="true" onclick="joinToolbarRs()">加入权限资源</a>
						</div>
						<div class="mini-fit">
						 <div id="toolbarGrid" class="mini-treegrid" allowResize="true" showPager="false" 
						 	idField="toolId" parentField="toolPid" treeColumn="btnLabel"
					        allowCellSelect="true" multiSelect="true" style="height:100%;width:100%;" 
					        showTreeIcon="true" allowCellEdit="true" allowCellValid="true" 
						      allowAlternating="true"   resultAsTree="false" expandOnLoad="true" oncellvalidation="onToolbarGridCellValidation" >
					        <div property="columns">
					           <div type="indexcolumn" width="20">序号</div>
							   <div type="checkcolumn" width="20"></div>
					            <div name="btnLabel" field="btnLabel" width="100" headerAlign="center">按钮标签
									<input property="editor" class="mini-textbox" style="width:100%;" required="true" />
								</div>
					            <div field="btnName" headerAlign="center"  width="80">按钮名称
					                <input property="editor" class="mini-combobox" allowInput="true" 
										textField="btnName" valueField="btnName" required="true"
										style="width:100%;" data="btns" />
					            </div>
					            <div field="btnIconCls" headerAlign="center" width="80">图标样式
					               <input property="editor" class="mini-buttonedit" style="width:100%;" required="true" onclick="selectIconCls" />
					            </div>
					            <div  field="btnClick" headerAlign="center" width="100">事件函数
					                <textara property="editor" class="mini-textbox" required="true"></textara>
					            </div>
					            <div name="url" field="url" width="200" headerAlign="center">URL
									<input property="editor" class="mini-textbox" style="width:100%;" />
								</div>
					        </div>
				    	</div>
				    </div>
				    <div id="topBtnsJson" style="display:none">${sysBoList.topBtnsJson}</div>
				</div>
			</c:if>
			<c:if test="${sysBoList.isDialog=='YES'}">
				<div title="返回字段列" iconCls="icon-info">
					<div class="mini-toolbar">
					  	 <div  class="mini-checkbox" checked="false" readOnly="false" text="是否全选" onvaluechanged="checkAllFields"></div>
					  </div>
					<div class="mini-fit">
						  <div id="returnFieldGrid" class="mini-datagrid" style="width:100%;height:100%" 
						  	showPager="false" allowAlternating="true"
						  	allowCellSelect="true" 
						  	allowCellEdit="true" >
								    <div property="columns">
								    	<div type="indexcolumn" width="40">序号</div>
								    	<div type="checkboxcolumn" name="isReturn" field="isReturn" trueValue="YES" falseValue="NO" width="50" headerAlign="center">返回显示</div>
								        <div name="header" field="header" width="200" headerAlign="center">字段名称
								        	<input property="editor" class="mini-textbox" style="width:100%;" />
								        </div>              
								        <div field="field" width="200" headerAlign="center">字段Key</div>
								   </div>
						  </div>
					 </div>
				 </div>
			</c:if>
			<div title="页面JS函数定义" iconCls="icon-script">
				<a class="mini-button" iconCls="icon-script" onclick="insertSummaryDrawFunction">插入统计行函数</a>
				<textarea id="bodyScript" name="bodyScript" style="width:90%;height:80%;">${sysBoList.bodyScript}</textarea>
				<textarea id="drawsummarycellArea">
					grid.on('drawsummarycell',function(e) {
					            var result = e.result;
					            var grid = e.sender;
					            var rows = e.data;
					            
					            if (e.field == "F_JE") {
					                var total = 0;
					                for (var i = 0, l = rows.length; i < l; i++) {
					                    var row = rows[i];
					                    var t = row.F_JE ;
					                    if (isNaN(t)) continue;
					                    total += parseFloat(t);
					                }
					
					                e.cellHtml = "总计: " + total;
					            }
					  });
				</textarea>
			</div>
			<div title="行数据显示脚本" iconCls="icon-script">

<textarea readonly="readonly" style="width:100%;height:100%;border:none;padding: 0;">
	grid.on("drawcell", function (e) {
	  var record = e.record,
	  field = e.field,
	  value = e.value;
	  if(field=='CREATE_BY_' || field=='UPDATE_BY_' || field=='CREATE_USER_ID_'){
	    if(value){
	    	e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>';
	    }else{
	    	e.cellHtml='<span style="color:red">无</span>';
	    }
	  }
	//用一列显示审批明细项
	 if (field== "INST_ID_") {
	 	if(record.INST_ID_!='' && record.INST_STATUS_=='RUNNING'){
	 		e.cellHtml='<span class="mini-taskinfo" iconCls="icon-check" instId="'+record.INST_ID_+'"></span>';
	 	}else{
	 		e.cellHtml='';
	 	}
	 }
</textarea><br/>
//----------------------------------请在以下部分编写代码实现Grid的drawcell的其他字段自定义展示的部分-------------------------------------------------------
				<textarea id="drawCellScript" name="drawCellScript" style="width:90%;height:100%;">${sysBoList.drawCellScript}</textarea>
<pre>
}
</pre>
			</div>
		</div>
	</div>
	<textarea id="headerColumns" style="display:none">${headerColumns}</textarea>
	<textarea id="fieldColumns" style="display:none">${fieldColumns}</textarea>
	<textarea id="searchJson" style="display:none">${sysBoList.searchJson}</textarea>
	<script type="text/javascript">
		mini.parse();
		var isShowLeft='${sysBoList.isLeftTree}';
		var isDialog='${sysBoList.isDialog}';
		var isAllowDataGrant='${isAllowDataGrant}';
		function onPre(){
			location.href=__rootPath+'/sys/core/sysBoList/edit.do?pkId=${param.id}';
		}
		var sampleGrid = mini.get("sampleDataGrid");
		var headerGrid=mini.get('headerGrid');
		var searchGrid=mini.get('searchGrid');
		var toolbarGrid=mini.get('toolbarGrid');
		//字段下拉的编辑控件
		var rowControls=[{id:'mini-textbox',text:'单行文本',tag:'input'},{id:'mini-textarea',text:'多行文本',tag:'textarea'},
				{id:'mini-checkbox',text:'复选框',tag:'input'},{id:'mini-checkboxlist',text:'复选框列表',tag:'input'},{id:'mini-spinner',text:'数字输入',tag:'input'},{id:'mini-datepicker',text:'日期',tag:'input'},{id:'mini-month',text:'月份',tag:'input'},
		        {id:'mini-time',text:'时间',tag:'input'},{id:'mini-combobox',text:'下拉选择',tag:'input'},{id:'mini-buttonedit',text:'编辑按钮',tag:'input'},{id:'mini-user',text:'用户',tag:'input'},{id:'mini-group',text:'用户组',tag:'input'},{id:'mini-dep',text:'部门',tag:'input'},{id:'upload-panel',text:'附件',tag:'input'}];

		var cololumRenderTypes=[{renderType:'COMMON',renderTypeName:'普通',isConfig:false},
								{renderType:'USER',renderTypeName:'用户',isConfig:true},
								{renderType:'GROUP',renderTypeName:'部门与组',isConfig:true},
								{renderType:'SYSINST',renderTypeName:'机构',isConfig:true},
								{renderType:'DATE',renderTypeName:'日期',isConfig:true},
								{renderType:'NUMBER',renderTypeName:'数字',isConfig:true},
								{renderType:'DISPLAY_LABEL',renderTypeName:'单色标签',isConfig:true},
								{renderType:'DISPLAY_ITEMS',renderTypeName:'多色标签',isConfig:true},
								{renderType:'DISPLAY_RANGE',renderTypeName:'数值范围标签',isConfig:true},
								{renderType:'DISPLAY_PERCENT',renderTypeName:'百分比标签',isConfig:true},
								{renderType:'lINK_URL',renderTypeName:'关联链接',isConfig:true},
								{renderType:'LINK_FLOW',renderTypeName:'关联流程',isConfig:true},
								{renderType:'FLOW_STATUS',renderTypeName:'流程状态',isConfig:false},
								{renderType:'SCRIPT',renderTypeName:'脚本计算',isConfig:true}];
		//设置其对应的下拉值
		searchGrid.on('cellendedit',function(e){
			if(e.column.displayField && e.editor.getText()){
				var newRow=jQuery.extend({},e.record);
				newRow[e.column.displayField]=e.editor.getText();
				newRow[e.column.field]=e.editor.getValue();
				searchGrid.updateRow(e.record,newRow);
			}
		});
		<c:if test="${isAllowDataGrant==true}">
		
		var userGdDatas=[{scope:'SELF',scope_name:'自己'},{scope:'DUP_USERS',scope_name:'直属上级用户'},
			 {scope:'UP_USERS',scope_name:'所有上级用户'},{scope:'DDOWN_USERS',scope_name:'直属下级用户'},
			 {scope:'DOWN_USERS',scope_name:'所有下级用户'}];
			 
		var depGdDatas=[{scope:'SELF_DEP',scope_name:'默认部门'},{scope:'DUP_DEPS',scope_name:'直属上级部门'},
			 {scope:'UP_DEPS',scope_name:'所有上部门'},{scope:'DDOWN_DEPS',scope_name:'直属下级部门'},
			 {scope:'DOWN_DEPS',scope_name:'所有下级部门'}];
			 
		var tenantGdDatas=[{scope:'SELF_TENANT',scope_name:'作者机构'},{scope:'DUP_TENANTS',scope_name:'上级机构'},
			 {scope:'UP_TENANTS',scope_name:'所有上级机构'},{scope:'DDOWN_TENANTS',scope_name:'直属下级机构'},
			 {scope:'DOWN_TENANTS',scope_name:'所有下级机构'}];
			 
		function addDataGrantRow(){
			var g=mini.get('dataGrantGrid');
			g.addRow({});
		}
		
		function removeDataGrantRow(){
			var g=mini.get('dataGrantGrid');
			var sels=g.getSelecteds();
			g.removeRows(sels);
		}
		
		 mini.get('dataGrantGrid').on('cellendedit',function(e){
			if(e.column.displayField && e.editor.getText && e.editor.getText()){
				e.record[e.column.displayField]=e.editor.getText();
			}
		 });
		 
		 //插入自定义的统计行函数
		 function insertSummaryDrawFunction(){
		 	var val=$('#drawsummarycellArea').val();
		 	var doc = bodyScriptEditor.getDoc();
       		var cursor = doc.getCursor();
      		doc.replaceRange(val, cursor); 
      		
		 }
		 
		 function onDataGrantGridCellEdit(e){
			var grid=e.sender;
		 	var record = e.record, field = e.field;
           	if(field=='scope'){
           		if(record.field=='CREATE_GROUP_ID_'){
            		e.editor.setData(depGdDatas);
           		}else if(record.field=='TENANT_ID_'){
           			e.editor.setData(tenantGdDatas);
           		}else{
           			e.editor.setData(userGdDatas);
           		} 
            }
		 }
		</c:if>
		
		//表头的字段验证
		function onHeaderGridCellValidation(e){
			if(e.field=='header' && (!e.value||e.value=='')){
	       		 e.isValid = false;
	       		 e.errorText='字段名称不能为空！';
	       	}
			var record=e.record;
	       	if(e.allowSort && (!record.field||e.field=='')){
	       		e.isValid = false;
	      		e.errorText='排序字段不能为空！';
	       	}
		}
		//表头的字段验证
		function onSearchGridCellValidation(e){
			if(e.field=='fieldName' && (!e.value||e.value=='')){
	       		 e.isValid = false;
	       		 e.errorText='搜索字段不能为空！';
	       	}
			if(e.field=='fieldOp' && (!e.value||e.value=='')){
	       		 e.isValid = false;
	       		 e.errorText='搜索比较符不能为空！';
	       	}
			if(e.field=='fc' && (!e.value||e.value=='')){
	       		 e.isValid = false;
	       		 e.errorText='搜索输入字段不能为空！';
	       	}
		}
		
		//导航tab不能为
		function onTabGridsCellValidation(e){
			if(e.field=='sql' && (!e.value||e.value=='')){
	       		 e.isValid = false;
	       		 e.errorText='导航Sql不能为空！';
	       	}
		}
		
		//导航tab不能为
		function onToolbarGridCellValidation(e){
			if((e.field=='btnLabel' || e.field=='btnName' ||e.field=='btnIconCls' || e.field=='btnClick') && (!e.value||e.value=='')){
				 e.isValid = false;
	       		 e.errorText='按钮不能为空！';
	       	}
		}
		//外部关联列
		function linkButtonField(e){
			headerGrid.cancelEdit();
			var selRow=headerGrid.getSelected();
			var link='';
			_OpenWindow({
				title:'关联字段查询',
				height:500,
				width:800,
				url:'${ctxPath}/sys/core/sysBoList/externalField.do',
				onload:function(){
					var iframe = this.getIFrameEl();
					var win=iframe.contentWindow;
					var data=mini.decode(selRow.linkFieldConf);
					win.setData(data);
					win.setFields(fieldDatas);
				},
				ondestroy:function(action){
					
					var iframe = this.getIFrameEl();
					var win=iframe.contentWindow;
					
					if(action=='clear'){
						headerGrid.updateRow(selRow,{linkField:'',linkFieldConf:'{}'});
						return;
					}
					if(action!='ok'){
						return;
					}
					var formData=win.getData();
					//console.log(formData);
					headerGrid.updateRow(selRow,{linkField:formData.replaceRule,linkFieldConf:mini.encode(formData)})
				}
			});
		}

		function checkAllFields(e){
			var reGrid=mini.get('returnFieldGrid');
			var checked = this.getChecked();
			var returnVal='NO';
			if(checked){
				returnVal='YES';
			}
			for(var i=0;i<reGrid.getData().length;i++){
				var row=reGrid.getData()[i];
				reGrid.updateRow(row,{isReturn:returnVal});
			}
		}
		
		var fieldDatas=null;
		$(function(){
			var headerColumns=$('#headerColumns').val();
			var fieldColumns=$('#fieldColumns').val();
			if(isShowLeft=='YES'){
				var leftTreeJson=$("#leftTreeJson").val();
				mini.get('tabGrids').setData(mini.decode(leftTreeJson));
			}
			
			if(isAllowDataGrant=='true'){
				var dataRightJson=$("#dataRightJson").val();
				mini.get('dataGrantGrid').setData(mini.decode(dataRightJson));
			}
			var headers=mini.decode(headerColumns);
			var fields=mini.decode(fieldColumns);
			searchGrid.setData(mini.decode($("#searchJson").val()));
			headerGrid.setData(headers);
			if('NO'==isDialog){
				toolbarGrid.setData(mini.decode($('#topBtnsJson').html()));
			}else{
				var returnFieldGrid=mini.get('returnFieldGrid');
				returnFieldGrid.setData(mini.decode(fieldColumns));
			}
			fieldDatas=fields;
			var cols=[{type: "indexcolumn"}];
			for(var i=0;i<fields.length;i++){
				cols.push({
					header:fields[i].header,
					field:fields[i].field,
					editor: { type: "textbox"}
				});
			}
			mini.get('idField').setData(fieldDatas);
			mini.get('textField').setData(fieldDatas);
			mini.get('parentField').setData(fieldDatas);
			
			sampleGrid.set({
				columns:cols
			});
			sampleGrid.load();
		});
		
		CodeMirror.defineMode("bodyJavaScript", function(config) {
			  return CodeMirror.multiplexingMode(
			    CodeMirror.getMode(config, "text/javascript"),
			    {
			     mode: CodeMirror.getMode(config, "application/json"),
			     delimStyle: "delimit"
			    }
			  );
		});
		
		var bodyScriptEditor = CodeMirror.fromTextArea(document.getElementById("bodyScript"), {
			  mode: "selfHtml",
			  lineNumbers: false,
			  lineWrapping: true
		});
		
		bodyScriptEditor.setSize('auto','100%');
		
		var drawCellScriptEditor = CodeMirror.fromTextArea(document.getElementById("drawCellScript"), {
			  mode: "selfHtml",
			  lineNumbers: false,
			  lineWrapping: true
		});
		
		drawCellScriptEditor.setSize('auto','100%');
		
	    var numberOpData=[
							{'fieldOp':'EQ','fieldOpLabel':'等于'},
							{'fieldOp':'NEQ','fieldOpLabel':'不等于'},
							{'fieldOp':'LT','fieldOpLabel':'小于'},
							{'fieldOp':'LE','fieldOpLabel':'小于等于'},
							{'fieldOp':'GT','fieldOpLabel':'大于'},
							{'fieldOp':'GE','fieldOpLabel':'大于等于'}
		                  ];
		var strOpData=[
							{'fieldOp':'EQ','fieldOpLabel':'等于'},
							{'fieldOp':'NEQ','fieldOpLabel':'不等于'},
							{'fieldOp':'LK','fieldOpLabel':'%模糊匹配%'},
							{'fieldOp':'LEK','fieldOpLabel':'%左模糊匹配'},
							{'fieldOp':'RIK','fieldOpLabel':'右模糊匹配%'}
		               ];
		               
		               /// <a class="mini-button" iconCls="icon-expand"  onclick="expand();">展开菜单</a>
			           //     <a class="mini-button" iconCls="icon-collapse"  onclick="collapse();">收起菜单</a>
		        var btns=[{toolId:'1',toolPid:'0',btnLabel:'添加',btnName:'Add',btnIconCls:'icon-add',
		        	btnClick:'onAdd',url:'/sys/customform/sysCustomFormSetting/${sysBoList.formAlias}/add.do'},
		        <c:if test="${sysBoList.dataStyle=='tree'}">
		          {toolId:'11',toolPid:'0',btnLabel:'添加子记录',btnName:'AddSub',btnIconCls:' icon-rowbefore',btnClick:'onAddSub'},
		          {toolId:'21',toolPid:'0',btnLabel:'展开',btnName:'Expand',btnIconCls:' icon-expand',btnClick:'onExpand'},
		          {toolId:'31',toolPid:'0',btnLabel:'收起',btnName:'Collapse',btnIconCls:' icon-collapse',btnClick:'onCollapse'},
		        </c:if>
				  {toolId:'2',toolPid:'0',btnLabel:'编辑',btnName:'Edit',btnIconCls:'icon-edit',btnClick:'onEdit',
				  	url:'/sys/customform/sysCustomFormSetting/${sysBoList.formAlias}/edit.do?pk={pk}'
				  },
		          {toolId:'3',toolPid:'0',btnLabel:'删除',btnName:'Remove',btnIconCls:'icon-remove',btnClick:'onRemove',
		          	url:'/sys/customform/sysCustomFormSetting/${sysBoList.formAlias}/removeById.do'
		          },
		          {toolId:'4',toolPid:'0',btnLabel:'明细',btnName:'Detail',btnIconCls:'icon-detail',btnClick:'onDetail',
		          	url:'/sys/customform/sysCustomFormSetting/${sysBoList.formAlias}/detail.do?pk={pk}'
		          },
		          {toolId:'5',toolPid:'0',btnLabel:'关闭',btnName:'Close',btnIconCls:'icon-close',btnClick:'onClose'},
		          {toolId:'6',toolPid:'0',btnLabel:'刷新',btnName:'Refresh',btnIconCls:'icon-refresh',btnClick:'onRefresh'},
		          {toolId:'7',toolPid:'0',btnLabel:'保存',btnName:'Save',btnIconCls:'icon-save',btnClick:'onRowsSave',
		          	url:'/sys/customform/sysCustomFormSetting/${sysBoList.formAlias}/rowsSave.do'
		          }, 
		          {toolId:'8',toolPid:'0',btnLabel:'导出',btnName:'Export',btnIconCls:'icon-export',btnClick:'onExport'},
		          {toolId:'10',toolPid:'8',btnLabel:'导出EXCEL',btnName:'XLSExport',btnIconCls:'icon-export',btnClick:'onXLSExport',
		          	url:'/sys/core/sysBoList/${sysBoList.formAlias}/exportExcelDialog.do'
		          },
		          {toolId:'11',toolPid:'0',btnLabel:'导入',btnName:'Import',btnIconCls:'icon-import',btnClick:'onImport',
		          	url:'/sys/core/sysBoList/${sysBoList.formAlias}/importExcelDialog.do'
		          }];
		       
		
		var fieldControls=[{
			fc:'mini-textbox',
			fcName:'文本框'
		},{
			fc:'mini-datepicker',
			fcName:'日期'
		},{
			fc:'mini-combobox',
			fcName:'下拉选项'
		},
		{
			fc:'mini-dialog',
			fcName:'自定义对话框'
		}];
		
		function addToolbarRow(){
			var toolbarGrid=mini.get('toolbarGrid');
			toolbarGrid.addNode({});
		}
		
		function addToolbarSelfRow(){
			 mini.prompt("必须为英文字母组合，不带空格:<br/>如abcAction", "请输入按钮的标识键",function(action,value){
			 	if(action!='ok') return;
			 	var toolbarGrid=mini.get('toolbarGrid');
				toolbarGrid.addNode({btnName:value,
					btnClick:'on'+value,scriptButton:true,
					url:'/dev/cus/customData/${sysBoList.key}/selfButtonExe/'+value+'.do'
				});
			 });
		}
		
		function selectIconCls(e){
					var toolbarGrid=mini.get('toolbarGrid');
					var btn=e.sender;
					var row=toolbarGrid.getSelected();
					toolbarGrid.cancelEdit();
		    	   _IconSelectDlg(function(icon){
						toolbarGrid.updateRow(row,{btnIconCls:icon});
						btn.setText(icon);
						btn.setValue(icon);
				    });
			}
			
		//把按钮对应的访问资源URL加至全局的权限控制中	
		function joinToolbarRs(){
			var btns=toolbarGrid.getData();
			_OpenWindow({
				title:'选择同步的按钮至该菜单',
				width:300,
				height:450,
				url:__rootPath+'/sys/core/subsystemAllMenu.do',
				ondestroy:function(action){
					if(action!='ok'){
						return;
					}
					
					var win=this.getIFrameEl().contentWindow;
					
					var menu=win.getSelectMenu();
					var data=mini.clone(menu);
					
					_SubmitJson({
						url:__rootPath+'/sys/core/sysMenu/syncMenuBtns.do',
						data:{
							menuId:data.id,
							btns:mini.encode(btns)
						},
						success:function(result){
							
						}
					});
				}
			});
		}
		
		function editToolbarScript(){
			var row=toolbarGrid.getSelected();
			_OpenWindow({
				url:__rootPath+'/sys/core/sysBoList/btnServerScript.do',
				iconCls:'icon-script',
				title:'按钮服务端响应脚本定义',
				height:500,
				width:800,
				max:true,
				onload:function(){
					var win=this.getIFrameEl().contentWindow;
					var data=mini.clone(row);
					win.setData(data,fieldDatas);
				},
				ondestroy:function(action){
					if(action!='ok') return;
					var win=this.getIFrameEl().contentWindow;
					var serverHandleScript=win.getData();
					toolbarGrid.updateRow(row,{serverHandleScript:serverHandleScript})
				}
			});
		}
		
		function addToolbarAllRows(e){
			var toolbarGrid=mini.get('toolbarGrid');
			toolbarGrid.loadList(btns, 'toolId', 'toolPid');
			/* for(var c=0;c<btns.length;c++){
				var found=false;
				for(var i=0;i<toolbarGrid.getData().length;i++){
					var row=toolbarGrid.getData()[i];
					if(row.btnName==btns[c].btnName){
						found=true;
						break;
					}
				}
				if(!found){
					toolbarGrid.addNode(btns[i]);
				}
			} */
		}
		
		function removeToolbarRow(){
			var toolbarGrid=mini.get('toolbarGrid');
			var rows=toolbarGrid.getSelecteds();
			toolbarGrid.removeNodes(rows);
		}
		
		//动态设置每列的编辑器
		function OnSearchCellBeginEdit(e) {
            var record = e.record, field = e.field;
           	if(field=='fieldName' && record.fieldName=='-'){
           		return;
           	}
           	if(field=='fieldOp'){
            	if(!record.dataType){
            		alert('请选择字段列!');
            		e.editor=null;
            	}else{
            		var fieldOpEditor=mini.get('fieldOpEditor');
            		e.editor=fieldOpEditor;
            		if(record.dataType=='number' || record.dataType=='date'){
        				fieldOpEditor.setData(numberOpData);
        			}else{
        				fieldOpEditor.setData(strOpData);
        			}
            	}
            }
        }
		//更改字段名称，即同时更换其比较符
		function changeFieldName(e){
			var s=e.sender;
			
			var searchGrid=mini.get('searchGrid');
			var fieldOpEditor=mini.get('fieldOpEditor');
			var sel=s.getSelected();
			if(sel.dataType=='number' || sel.dataType=='date'){
				fieldOpEditor.setData(numberOpData);
			}else{
				fieldOpEditor.setData(strOpData);
			}
			var row=searchGrid.getSelected();
			searchGrid.updateRow(row,{
				fieldLabel:s.getText(),
				dataType:sel.dataType,
				queryDataType:sel.queryDataType});
		}
		
		function changeColumnFieldName(e){
			var s=e.sender;
			var headerGrid=mini.get('headerGrid');
			var row=headerGrid.getSelected();
			headerGrid.updateRow(row,{
				header:s.getText(),
				field:s.getValue()});
		}
		//弹出搜索条件配置
		function showSearchConfigEditor(){
			var searchGrid=mini.get('searchGrid');
			var row=searchGrid.getSelected();
			
			_OpenWindow({
				title:'搜索条件配置',
				url:__rootPath+'/scripts/miniQuery/'+ row.fc+'.jsp',
				width:800,
				height:500,
				onload:function(){
					var win=this.getIFrameEl().contentWindow;
					var data=mini.clone(row);
					win.setData(data,fieldDatas);
				},
				ondestroy:function(action){
					if(action!='ok') return;
					var win=this.getIFrameEl().contentWindow;
					var data=win.getData();
					searchGrid.updateRow(row,data);
				}
			});
		}
		
		//保存Json
		function onSaveConfigJson(){
			
			//验证所有的表格是否合法
			 headerGrid.validate();
			 searchGrid.validate();
			 var isValid=headerGrid.isValid() && searchGrid.isValid();
			 var tabGrids=mini.get('tabGrids');
			 
			var id="${param.id}";
			var leftTreeJson={};
			var topBtnsJson={};
			var dataRightJson={};
			var fieldJson={};
			
			if(isAllowDataGrant=='true'){
				dataRightJson=mini.encode(mini.get('dataGrantGrid').getData());
			}
			
			if(isShowLeft=='YES'){
				var tabGrid=mini.get('tabGrids');
				leftTreeJson=mini.encode(tabGrid.getData());
				tabGrid.validate();
				isValid=isValid && tabGrid.isValid();
			}
			var columns=headerGrid.getData();
			if(isDialog=='NO'){
				var toolbarGrid=mini.get('toolbarGrid');
				topBtnsJson=mini.encode(toolbarGrid.getData());
				toolbarGrid.validate();
				isValid=isValid && toolbarGrid.isValid();
			}else{
				var returnFieldGrid=mini.get('returnFieldGrid');
				fieldJson=mini.encode(returnFieldGrid.getData());
			}
			if(!isValid){
				alert('请检查所有Tab下的表格下的数据的合法性！');
				return;
			}
			
			var idField=mini.get('idField').getValue();
			var textField=mini.get('textField').getValue();
			var parentField=mini.get('parentField').getValue();
			if(!idField){
				alert('请选择主键字段！');
				return;
			}
			_SubmitJson({
				url:__rootPath+'/sys/core/sysBoList/saveConfigJson.do',
				data:{
					id:id,
					idField:idField,
					textField:textField,
					parentField:parentField,
					isShowLeft:isShowLeft,
					isDialog:isDialog,
					colsJson:mini.encode(columns),
					leftTreeJson:leftTreeJson,
					fieldJson:fieldJson,
					startFroCol:mini.get('startFroCol').getValue(),
					endFroCol:mini.get('endFroCol').getValue(),
					searchJson:mini.encode(searchGrid.getData()),
					topBtnsJson:topBtnsJson,
					dataRightJson:dataRightJson,
					bodyScript:bodyScriptEditor.getValue(),
					drawCellScript:drawCellScriptEditor.getValue()
				},
				success:function(){
					searchGrid.accept();
					headerGrid.accept();
				}
			});
		}
		
		function addSearchRow(){
			var searchGrid=mini.get('searchGrid');
			searchGrid.addRow({fc:'mini-textbox',
			fcName:'文本框',type:'query',typeName:'查询参数',
			autoFilter:'YES',autoFilterName:'是',
			fieldOp:'EQ',fieldOpLabel:'等于'});
		}
		
		function addSearchSplit(){
			var searchGrid=mini.get('searchGrid');
			searchGrid.addRow({fieldName:'-',fieldLabel:'-',fieldOp:'-',fieldOpLabel:'-',fc:'-',fcName:'-'});
		}
		
		function removeSearchRow(){
			var searchGrid=mini.get('searchGrid');
			var selecteds=searchGrid.getSelecteds();
			searchGrid.removeRows(selecteds);
		}
		
		function addTabRow(){
			var tabGrid=mini.get('tabGrids');
			tabGrid.addRow({});
		}
		
	
		function removeTabRow(){
			var tabGrid=mini.get('tabGrids');
			var selecteds=tabGrid.getSelecteds();
			tabGrid.removeRows(selecteds);
		}

		
		function editTabRow(e){
			
			var tabGrid=mini.get('tabGrids');
			var ds='${sysBoList.dbAs}';
			var row=tabGrid.getSelected();
			tabGrid.cancelEdit();
			_OpenWindow({
				title:'编辑Tab导航',
				height:450,
				width:680,
				max:true,
				url:__rootPath+'/sys/core/sysBoList/treeTabBuild.do?ds='+ds,
				onload:function(){
				  var iframe = this.getIFrameEl();
				  var win=iframe.contentWindow;
				  var data=mini.clone(row);
				  win.setData(row,ds);
				},
				ondestroy:function(action){
					if(action!='ok'){return;}
					var iframe = this.getIFrameEl();
					var win=iframe.contentWindow;
					tabGrid.cancelEdit();
					var data= win.getData();
					tabGrid.updateRow(row,data);
					
				}
			});
		}
		function cellRenderConfig(){
			var row=headerGrid.getSelected();
			if(row==null || (!row.renderType)){
				alert('请选择行与渲染!');
				return;
			}
			var isConfig=true;
			for(var i=0;i<cololumRenderTypes.length;i++){
				if(cololumRenderTypes[i].renderType==row.renderType){
					isConfig=cololumRenderTypes[i].isConfig;
					break;
				}
			}
			if(!isConfig){
				alert('该渲染类型不需要配置！');
				return;
			}
			
			_OpenWindow({
				title:'字段渲染配置('+row.renderTypeName+')',
				url:__rootPath+'/scripts/cellRender/renderConfig_'+row.renderType.toLowerCase()+'.jsp',
				width:780,
				height:420,
				onload:function(){
					var iframe = this.getIFrameEl();
					var win=iframe.contentWindow;
					win.setData(mini.decode(row.renderConf));
					if(win.setFields){
						win.setFields(fieldDatas);
					}
				},
				ondestroy:function(action){
					if(action!='ok'){
						return;
					}
					var iframe = this.getIFrameEl();
					var win=iframe.contentWindow;
					var data=win.getData();
					var format=data.format;
					var dataType=data.dataType;
					headerGrid.updateRow(row,{format:format,dataType:dataType,renderConf:mini.encode(data)});
				}
			});
		}
		
		function rowControlConfig(){
			var row=headerGrid.getSelected();
			if(row==null || !row.control){
				alert('请选择行与编辑控件！');
				return;
			}
			_OpenWindow({
				title:'行控件'+row.controlName+'编辑属性',
				url:__rootPath+'/scripts/rowControls/'+row.control+'.jsp',
				width:650,
				height:450,
				onload:function(){
					var iframe = this.getIFrameEl();
					var win=iframe.contentWindow;
					win.setData(mini.decode(row.controlConf));
					if(win.setColumnFields){
						win.setColumnFields(fieldDatas);
					}
				},
				ondestroy:function(action){
					if(action!='ok'){
						return;
					}
					var iframe = this.getIFrameEl();
					var win=iframe.contentWindow;
					var data=win.getData();
					headerGrid.updateRow(row,{controlConf:mini.encode(data)});
				}
			});
		}
		function expand(){
			headerGrid.expandAll();
        }
        
        function collapse(){
        	headerGrid.collapseAll();
        }
		//添加行
		function addRow(gridName){
			var grid = mini.get(gridName);
		 	var node = grid.getSelectedNode();
		 	grid.addNode({}, "after", node);
		 	grid.cancelEdit();
		 	grid.beginEditRow(node);
		}
		
		function removeRow(gridName){
			var grid = mini.get(gridName);
			var selecteds=grid.getSelecteds();
			grid.removeNodes(selecteds);
			
		}
		
		function upRow(gridName) {
			var grid = mini.get(gridName);
            var moveNode = grid.getSelected();
            var index=grid.indexOf(moveNode);
            var targetNode=null;
            for(var i=index-1;i>=0;i--){
            	targetNode=grid.getRow(i);
            	if(targetNode._level==moveNode._level){
            		break;
            	}
            }
            if (targetNode!=null) {
            	grid.moveNode(moveNode, targetNode,"before");
               
            }
        }
        
        function downRow(gridName) {
       	  	var grid = mini.get(gridName);
       	  	var moveNode = grid.getSelected();
             var index=grid.indexOf(moveNode);
             var targetNode=null;
             for(var i=index+1;i<grid.getData().length;i++){
             	targetNode=grid.getRow(i);
             	if(targetNode._level==moveNode._level){
             		break;
             	}
             }
             if (targetNode!=null) {
            	 grid.moveNode(moveNode, targetNode,"after");
             }
        }
       
        //下降当前选择节点
        function subRow(gridName){
        	var grid = mini.get(gridName);
        	//当前要下降的节点
        	var moveNode = grid.getSelectedNode();
        	
        	var index=grid.indexOf(moveNode);
        	var pNode=null;
        	for(var i=index-1;i>=0;i--){
        		pNode=grid.getRow(i);
        		if(pNode._level==moveNode._level){
        			break;
        		}
        	}
        	if(pNode!=null){
        		grid.moveNode(moveNode,pNode,"add");
        		
        	}else{
        		alert('前一级没有父节点！');
        	}
        }
        
        function topRow(gridName){
        	var grid = mini.get(gridName);
        	var moveNode = grid.getSelectedNode();
        	var pNode=grid.getParentNode(moveNode);
        	if(pNode!=null){
        		grid.moveNode(moveNode,pNode,"after");
        	}
        }
        
        function reloadColumns(){
        	var id='${param.id}';
        	_SubmitJson({
        		url:__rootPath+'/sys/core/sysBoList/reloadColumns.do',
        		data:{
        			id:id
        		},
        		success:function(result){
        			var data=result.data;
        			headerGrid.setData(data);
        		}
        	});
        }
        
 
        
        function onGenHtml(){
        	
        	//验证所有的表格是否合法
			 headerGrid.validate();
			 searchGrid.validate();
			 var isValid=headerGrid.isValid() && searchGrid.isValid();

			if(isShowLeft=='YES'){
				var tabGrid=mini.get('tabGrids');
				tabGrid.validate();
				isValid=isValid && tabGrid.isValid();
			}

			if(isDialog=='NO'){
				var toolbarGrid=mini.get('toolbarGrid');
				toolbarGrid.validate();
				isValid=isValid && toolbarGrid.isValid();
			}
			if(!isValid){
				alert('请检查所有Tab下的表格下的数据的合法性！');
				return;
			}
        	
        	var id='${param.id}';
        	_SubmitJson({
	        	url:__rootPath+'/sys/core/sysBoList/genHtml.do?id='+id,
	    		success:function(){
	    			_OpenWindow({
	    				title:'生成的代码预览--${sysBoList.name}',
	    				width:800,
	    				height:450,
	    				max:true,
	    				url:__rootPath+'/sys/core/sysBoList/edit3.do?id='+id
	    			});
	    			
	    		}
	    	});
        }
        
        function onEditHtml(){
        	var id='${param.id}';
        	_OpenWindow({
        		title:'编辑页面代码',
        		url:__rootPath+'/sys/core/sysBoList/edit3.do?id='+id,
        		width:850,
        		height:400
        	});
        }
        
        /**
        	权限编辑。
        */
        function onPermissionEdit(e){
        	var btn=e.sender;
        	var val=btn.getValue();
    		openProfileDialog({hideRadio:'YES', onload:function(iframe){
    			var json=[];
    			if(val){
    				json=mini.decode(val);
    			}
            	iframe.init(json);
    		},onOk:function(data){
    			var text="";
    			for(var i=0;i<data.length;i++){
    				var obj=data[i];
    				text+="["+ obj.name +"]:" + obj.names;
    			}
    			var val=mini.encode(data);
    			btn.setValue(val);
    			btn.setText(text);
    		}})
        	
        }
        
	</script>
</body>
</html>