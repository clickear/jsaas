<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>业务流程解决方案列表管理</title>
<%@include file="/commons/list.jsp"%>
<script type="text/javascript" src="${ctxPath}/scripts/form/customFormUtil.js"></script>
<style type="text/css">
	.mini-layout-border>#center{
 		background: transparent;
	}
</style>
</head>
<body>
	<ul id="treeMenu" class="mini-contextmenu" >
		<li iconCls="icon-add" onclick="addNode">添加分类</li>        
	    <li iconCls="icon-edit" onclick="editNode">编辑分类</li>
	    <li iconCls="icon-remove" onclick="delNode">删除分类</li>
	</ul>
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
		    <div 
		    	title="业务解决方案分类" 
		    	region="west" 
		    	width="200" 
		    	height:"100%"; 
		    	showSplitIcon="true" 
		    	showCollapseButton="false"
		    	showProxy="false"
		    	class="layout-border-r" 
	    		
	    	>
		      	<div id="toolbar1" class="mini-toolbar-no" style="padding:2px;border-top:0;border-left:0;border-right:0;">		      		
					<a class="mini-button" iconCls="icon-add" plain="true" onclick="addNode()">添加</a>
                    <a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refreshSysTree()">刷新</a>			           	                 
			    </div>
		         <ul 
		         	id="systree" 
		         	class="mini-tree" 
		         	url="${ctxPath}/bpm/core/bpmSolution/getCatTree.do?isAdmin=true" 
		         	style="width:100%;height: 100%;" 
					showTreeIcon="true" 
					textField="name" 
					idField="treeId" 
					resultAsTree="false" 
					parentField="parentId" 
					expandOnLoad="true"
	                onnodeclick="treeNodeClick"  
	                contextMenu="#treeMenu"
                 >
	            </ul>
		    </div>
		    <div title="业务流程解决方案" region="center" showHeader="false" showCollapseButton="false">
				<div class="titleBar mini-toolbar">
					<ul>
						<li>
							<a class="mini-button" iconCls="icon-detail"  onclick="detail()">明细</a>
						</li>
						<li>
							<a class="mini-button" iconCls="icon-create"   onclick="add()">增加</a>
						</li>
						<li>
							<a class="mini-button" iconCls="icon-edit"  onclick="edit(true)">编辑</a>
						</li>
						<li>
							<a class="mini-button" iconCls="icon-remove"  onclick="remove()">删除</a>
						</li>
						<li>
							<a class="mini-button" iconcls="icon-export" onclick="doExport">导出</a>
						</li>
						<li>
							<a class="mini-button" iconcls="icon-import" onclick="doImport">导入</a>
						</li>
						<li class="clearfix"></li>
					</ul>
					<div class="searchBox">
						<form id="searchForm" class="text-distance" style="display: none;">						
							<ul>
								<li>
									<span class="text">创建时间从</span>
			                    	<input class="mini-datepicker" name="Q_CREATE_TIME__D_GE"/>
			                    	至
			                    	<input class="mini-datepicker" name="Q_CREATE_TIME__D_LE"/>
								</li>
								<li>
									<span class="text">方案</span>
									<input class="mini-textbox" name="Q_NAME__S_LK"  />
								</li>
								<li>
									<span class="text">别名</span>
									<input class="mini-textbox" name="Q_KEY__S_LK"  />
								</li>
								<li>
									<span class="text">状态</span>
									<input 
										class="mini-combobox" 
										name="Q_STATUS__S_EQ" 
										showNullItem="true"  
										emptyText="请选择..."
										data="[{id:'DEPLOYED',text:'发布'},{id:'CREATED',text:'创建'}]"
									/>
								</li>
								<li>
									<div class="searchBtnBox">
										<a class="mini-button _search" onclick="searchForm(this)">搜索</a>
										<a class="mini-button _reset" onclick="onClearList(this)">清空</a>
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
			
				<div class="mini-fit rx-grid-fit">
					<div 
						id="datagrid1" 
						class="mini-datagrid" 
						style="width: 100%; height:100%;"  
						allowResize="false" 
						url="${ctxPath}/bpm/core/bpmSolution/solutionList.do" 
						idField="solId" 
						multiSelect="true" 
						showColumnsMenu="true" 
						sizeList="[5,10,20,50,100,200,500]" 
						pageSize="20" 
						allowAlternating="true" 
						pagerButtons="#pagerButtons"
					>
							<div property="columns">
								<div type="checkcolumn" width="20"></div>
								<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center"  renderer="onActionRenderer" cellStyle="padding-left:2px;">操作</div>
								<div field="name" sortField="NAME_" width="140" headerAlign="center" allowSort="true">解决方案名称</div>
								<div field="key" width="100" sortField="KEY_" headerAlign="center" allowSort="true">别名</div>
								<div field="status" width="80" sortField="STATUS_" headerAlign="center" renderer="onStatusRenderer"  allowSort="true">发布状态</div>
								<div field="formal" width="80" sortField="FORMAL_" headerAlign="center" renderer="onFormalRenderer"  allowSort="true">正式状态</div>
								<div field="createTime" sortField="CREATE_TIME_" dateFormat="yyyy-MM-dd HH:mm:ss" width="80" headerAlign="center" allowSort="true">创建时间</div>
							</div>
					</div>
				</div>
			</div>
	</div>
	<script type="text/javascript">
			mini.parse();
			var systree=mini.get('systree');
		
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var rightJson=record.rightJson["def"];
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>';
           		for(var i=0;i<rightJson.length;i++){
           			var json=rightJson[i];
           			s+=getByJson(record,json)
           		}
	            return s;
	        }
        	
        	function getByJson(record,json){
	            var pkId = record.pkId;
	            var uid=record._uid;
	            var name=record.name;
	            var actDefId=record.actDefId;
        		var str="";
        		switch(json.key){
        			case "design":
        				if(json.val && actDefId){
        					str=' <span class="icon-mgr" title="方案配置" onclick="mgrRow(\'' + pkId + '\',\''+name+'\',\'' + actDefId + '\')"></span>';
        				}
        				break;
        			case "start":
        				if(json.val && ( record.status=='TEST'||record.status=='DEPLOYED')){
        					str=' <span class="icon-start" title="启动流程" onclick="startRow(\'' + uid + '\')"></span>';
			            	str = str + ' <span class="icon-main" title="发布菜单"  onclick="deployMenu(\'' + record._uid + '\')"></span>'; 
			            }
        				break;
        			case "edit":
        				if(json.val){
        					str=' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\',true)"></span>';
        				}
        				break;
        			case "del":
        				if(json.val){
        					str='  <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
        				}
        				break;
        		}
        		return str;
        	}
        	
        	function deployMenu(uid){
				var row=grid.getRowByUID(uid);
				_OpenWindow({
					title:'发布菜单 ',
					height:450,
					width:800,
					url:__rootPath+"/sys/core/sysMenu/addNode.do",
					onload:function(){
						var win=this.getIFrameEl().contentWindow;
						win.setData({
							name:row.name,
							key:row.key+'_Sol',
							parentId:'',
							url:'/bpm/core/bpmInst/'+row.key+'/start.do',
							showType:'URL',
							isBtnMenu:'NO',
							isMgr:'NO'
						});
					}
				});
			}
		
        	
	        function onStatusRenderer(e) {
	            var record = e.record;
	            var status = record.status;
	            
	            var arr = [ {'key' : 'DEPLOYED', 'value' : '发布','css' : 'green'}, 
				            {'key' : 'CREATED','value' : '创建','css' : 'orange'} ];
				
				return $.formatItemValue(arr,status);
	        }
	        
	        function onFormalRenderer(e) {
	            var record = e.record;
	            var formal = record.formal;
	            
	            var arr = [ {'key' : 'yes', 'value' : '正式','css' : 'green'}, 
				            {'key' : 'no','value' : '测试','css' : 'orange'} ];
				
				return $.formatItemValue(arr,formal);
	            
	        }
	        
        	
	        //该函数由添加调用时回调函数
	        function addCallback(openframe){
	        	var bpmSolution=openframe.getJsonData();
	        	mgrRow(bpmSolution.solId,bpmSolution.name,bpmSolution.actDefId);
	        }
        	
        	
        	
        	function mgrRow(pkId,name,actDefId){
        		window.open('${ctxPath}/bpm/core/bpmSolution/mgr.do?solId='+pkId+'&actDefId='+actDefId);
        	}
        	
        	function addNode(e){
	    		var systree=mini.get("systree");
	    		var node = systree.getSelectedNode();
		   		var parentId=node?node.treeId:0;
		   		
		   		_OpenWindow({
		   			title:'添加业务流程解决方案分类',
		   			url:__rootPath+'/sys/core/sysTree/edit.do?parentId='+parentId+'&catKey=CAT_BPM_SOLUTION',
		   			width:1024,
		   			height:350,
		   			ondestroy:function(action){
		   				systree.load();
		   			}
		   		});
		   	}
		   	
		   	function refreshSysTree(){
		   		var systree=mini.get("systree");
		   		systree.load();
		   	}
		   	
		   	function editNode(e){
		   		var systree=mini.get("systree");
		   		var node = systree.getSelectedNode();
		   		var treeId=node.treeId;
		   		_OpenWindow({
		   			title:'编辑节点',
		   			url:__rootPath+'/sys/core/sysTree/edit.do?pkId='+treeId,
		   			width:780,
		   			height:350,
		   			ondestroy:function(action){
		   				if(action=='ok'){
		   					systree.load();
		   				}
		   			}
		   		});
		   	}
		   	/**
		   	*导出
		   	**/
		   	function doExport(){
		   		var rows=grid.getSelecteds();
		   		if(rows.length==0){
		   			alert('请选择需要导出的流程方案记录！')
		   			return;
		   		}
		   		var ids=_GetIds(rows);
		   		_OpenWindow({
		   			title:'流程方案导出',
		   			url:__rootPath+'/bpm/core/bpmSolution/export.do?ids='+ids,
		   			height:350,
		   			width:600
		   		});
		   	}
		   	
		   	function doImport(){
		   		_OpenWindow({
		   			title:'流程方案导入',
		   			url:__rootPath+'/bpm/core/bpmSolution/import1.do',
		   			height:350,
		   			width:600
		   		});
		   	}
		   	
		   	function delNode(e){
		   		var systree=mini.get("systree");
		   		var node = systree.getSelectedNode();
		   
		         mini.confirm("确定删除记录？", "确定？",
		             function (action) {
			        	 if (action != "ok") return;
	                	 _SubmitJson({
	     		   			url:__rootPath+'/sys/core/sysTree/del.do?ids='+node.treeId,
	     		   			success:function(text){
	     		   				systree.load();
	     		   			}
	     		   		}); 
		             }
		         );
		   	}
		   	
		   	//按分类树查找数据字典
		   	function treeNodeClick(e){
		   		var node=e.node;
		   		grid.setUrl(__rootPath+'/bpm/core/bpmSolution/solutionList.do?treeId='+node.treeId);
		   		grid.load();
		   	}
		   	
		   	
        	
		   	
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.core.entity.BpmSolution" 
	winHeight="650" winWidth="900" entityTitle="业务流程解决方案" baseUrl="bpm/core/bpmSolution" />
	
</body>
</html>