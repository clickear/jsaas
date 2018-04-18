<%-- 
    Document   : 业务表单视图列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>业务表单视图列表管理</title>
<%@include file="/commons/list.jsp"%>
<script src="${ctxPath}/scripts/share/dialog.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/sys/bo/BoUtil.js" type="text/javascript"></script>
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
	    <li iconCls="icon-edit" onclick="nodeGrant">控制权限</li>
	    <li iconCls="icon-remove" onclick="delNode">删除分类</li>
	</ul>
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
	    <div 
	    	title="业务表单分类" 
	    	region="west" 
	    	width="220"
	    	showSplitIcon="true"
	    	showCollapseButton="false"
	    	showProxy="false"
    	>
	        <div id="toolbar1" >
				<a class="mini-button" iconCls="icon-add" plain="true" onclick="addNode()">添加</a>
                <a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refreshSysTree()">刷新</a>           
	        </div>
	         <ul 
				id="systree" 
				class="mini-tree" 
				url="${ctxPath}/sys/core/sysTree/listAllByCatKey.do?catKey=CAT_FORM_VIEW" 
				style="width:100%;" 
				showTreeIcon="true" 
				textField="name" 
				idField="treeId" 
				resultAsTree="false" 
				parentField="parentId" 
				expandOnLoad="true"
				onnodeclick="treeNodeClick"  
				contextMenu="#treeMenu"
              ></ul>
	    </div>
	    <div showHeader="false" showCollapseButton="false">
			<ul id="popupAddMenu" class="mini-menu" style="display:none;">
		 		<li iconCls="icon-create" onclick="add()">新建</li>
            	<li iconCls="icon-copyAdd" onclick="addUrl()">添加URL表单</li>
        	</ul>
	        
	        
	        <div class="titleBar mini-toolbar" >
		         <ul>
					<li>
						<a class="mini-menubutton first" iconCls="icon-create"  menu="#popupAddMenu" >增加</a>
					</li>
					<li>
						<a class="mini-button" iconCls="icon-edit" onclick="edit(true)">编辑</a>
					</li>
					<li>
						<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
					</li>
					<li class="clearfix"></li>
				</ul>
				<div class="searchBox">
					<form id="searchForm" class="text-distance" style="display: none;">						
						<ul>
							<li>
								<span class="text">名　　称</span><input class="mini-textbox" name="Q_NAME__S_LK"/>
							</li>
							<li>
								<span class="text">标 识 键</span><input class="mini-textbox" name="Q_KEY__S_LK"/>
							</li>
							<li class="clearfix"></li>
						</ul>
						<ul>
							<li>
								<span class="text">表单类型</span>
		                   		<input 
		                   			name="Q_TYPE__S_EQ" 
		                   			class="mini-combobox"  
		                   			textField="text" 
		                   			valueField="id" 
    								data="[{id:'SEL-DEV',text:'自定义表单'},{id:'ONLINE-DESIGN',text:'在线设计表单'}]"  
    								showNullItem="true" 
   								/>
							</li>
							<li>
								<span class="text">业务模型</span>
	                    		<input 
	                    			id="btnBoDefId" 
	                    			name="Q_BO_DEFID__S_EQ" 
	                    			class="mini-buttonedit" 
	                    			text="" 
   									showClose="true" 
   									oncloseclick="clearButtonEdit" 
   									value="" onbuttonclick="onSelectBo"
								/>
							</li>
							<li>
								<span class="text">状态</span>
   								<input 
   									name="Q_STATUS__S_EQ" 
   									class="mini-combobox"  
   									textField="text" 
   									valueField="id" 
   									data="[{id:'INIT',text:'草稿'},{id:'DEPLOYED',text:'发布'}]"  
   									showNullItem="true" 
  								/>
							</li>
							
							
							<li class="searchBtnBox">
								<a class="mini-button _search" onclick="searchForm(this)" >搜索</a>
								<a class="mini-button _reset" onclick="onClearList(this)">清空</a>
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
					style="width: 100%; height: 100%;" 
					allowResize="false" 
					url="${ctxPath}/bpm/form/bpmFormView/listAll.do" 
					idField="viewId" 
					multiSelect="true" 
					showColumnsMenu="true" 
					sizeList="[5,10,20,50,100,200,500]" 
					pageSize="20" 
					allowAlternating="true" 
					pagerButtons="#pagerButtons"
				>
					<div property="columns" style="width: 100%;">
						<div type="checkcolumn" width="20"></div>
						<div name="action" cellCls="actionIcons" width="22" headerAlign="center"  renderer="onActionRenderer" cellStyle="padding:0;" >操作</div>
						<div field="name" width="140" headerAlign="center" sortField="NAME_" allowSort="true">名称</div>
						<div field="key" width="110" headerAlign="center" sortField="KEY_" allowSort="true">标识键</div>
						<div field="type" width="80" headerAlign="center" renderer="onTypeRenderer">类型</div>
						<div field="version" width="60" headerAlign="center"  >版本号</div>
						<div field="status" width="60" headerAlign="center" renderer="onStatusRenderer">状态</div>
						<div field="createTime" width="90" headerAlign="center" sortField="CREATE_TIME_" allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.form.entity.BpmFormView" winHeight="450" winWidth="700" entityTitle="业务表单视图" baseUrl="bpm/form/bpmFormView" />
	<script type="text/javascript">
		
	 function onTypeRenderer(e) {
         var record = e.record;
         var type = record.type;
          var arr = [
			            {'key' : 'ONLINE-DESIGN', 'value' : '在线表单','css' : 'green'}, 
			            {'key' : 'SEL-DEV','value' : '自开发表单','css' : 'gray'} ];
			return $.formatItemValue(arr,type);
     }
	  
	  function onStatusRenderer(e) {
         var record = e.record;
         var status = record.status;
          var arr = [
			            {'key' : 'DEPLOYED', 'value' : '发布','css' : 'green'}, 
			            {'key' : 'INIT','value' : '草稿','css' : 'blue'} ];
			return $.formatItemValue(arr,status);
       }

        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var uid = record.pkId;	           
	            var s="<div class='iconfont icon-all'></div>";
	            	
	            if(record.type=="ONLINE-DESIGN"){
	            	if(record.boDefId){
            			s+= ' <span class="icon-grant" title="权限" onclick="rightRow(\'' + record._uid + '\')"></span>';
            			s+= ' <span class="icon-clearbo" title="清除BO" onclick="clearBo(\'' + record._uid + '\')"></span>';
            		}
	            	if(findNode("edit",record.treeId)){
	            		s+= ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + uid + '\',true)"></span>';	
	            	}
            		s+= ' <span class="icon-version" title="版本" onclick="versionRow(\'' + record._uid + '\')"></span>';
            		if(findNode("delete",record.treeId)){
            			s+= ' <span class="icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';	
            		}
                    
                    s+= ' <span class="icon-copy" title="复制" onclick="copy(\'' + record._uid + '\')"></span>';
                    s+= ' <span class="icon-preview"  title="预览"  onclick="preview(\'' + uid + '\')"></span>'; 
                    s+= ' <span class="icon-card_editor"  title="编辑模板"  onclick="editTemplate(\'' + uid + '\')"></span>'; 
                    if(record.status=="INIT" && record.boDefId){
                    	s+= '<span class="icon-release"  title="发布" plain="true" onclick="deploy(\'' + uid + '\')"></span>'; 
                    }
                    if(record.boDefId){
                    	s+= ' <span class="icon-pdf"  title="PDF模板"  onclick="pdfTemp(\'' + uid + '\')"></span>'; 
                    }
	            }
	            else{
	            	s = '<span class="icon-detail" title="明细" onclick="urlGet(\'' + record._uid + '\')"></span>';
	            	if(findNode("edit",record.treeId)){
            		s+= ' <span class="icon-edit" title="编辑" onclick="editUrlRow(\'' + record._uid + '\',true)"></span>';
	            	}
            		if(findNode("delete",record.treeId)){
                    s+= ' <span class="icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
            		}
	            }
	            
	            return s;
	        }
        	    
        	function findNode(param,treeId){
        		if(${isSuperAdmin}){
        			return true;
        		}
        		var systree=mini.get("systree");
        		var nodes = systree.findNodes(function(node){
        		var right=node.right;
        			if(right){
        				var rightJson=JSON.parse(right);
        				if(rightJson[param]=="true"&&node.treeId==treeId){
        					return true;
        				}
        			}
        		});
        		if(nodes.length){
        			return true;
        		}else{
        			return false;
        		}
        	}
        	
	      	//版本管理
        	function versionRow(uid){
        		var row=grid.getRowByUID(uid);
        		
        		_OpenWindow({
					url:__rootPath+'/bpm/form/bpmFormView/versions.do?key='+row.key,
					title:'表单视图的版本管理--'+row.name,
					width:800,
					height:480,
					ondestroy:function(action){
						grid.load();
					}
				});
        	}
	      	
	      	function editUrlRow(uid){
				var row=grid.getRowByUID(uid);
			        		
        		_OpenWindow({
					url:__rootPath+'/bpm/form/bpmFormView/urlEdit.do?viewId='+row.viewId,
					title:'表单编辑'+row.name,
					width:780,
					height:480,
					ondestroy:function(action){
						grid.load();
					}
				});
	      	}

	      	function urlGet(uid){
				var row=grid.getRowByUID(uid);
			        		
        		_OpenWindow({
					url:__rootPath+'/bpm/form/bpmFormView/urlGet.do?pkId='+row.viewId,
					title:'表单明细'+row.name,
					width:780,
					height:480,
					ondestroy:function(action){
					}
				});
	      	}
	      	
        	function addUrl(){
        		var url=__rootPath+'/bpm/form/bpmFormView/urlEdit.do';
        		
        		_OpenWindow({
					url:url,
					title:'添加URL表单',
					width:780,
					height:480,
					ondestroy:function(action){
						grid.load();
					}
				});
        	}
	      	
	      	function copy(uid){
	      		var row=grid.getRowByUID(uid);
	      		var viewId=row.viewId;
	      		_OpenWindow({
					url:__rootPath+'/bpm/form/bpmFormView/copy.do?viewId='+viewId,
					title:'复制表单--'+row.name,
					width:800,
					height:400,
					ondestroy:function(action){
						grid.load();
					}
				});
	      	}
	      	
	      	
	      	function rightRow(uid){
	      		var row=grid.getRowByUID(uid);
	      		
	      		_OpenWindow({
					url:__rootPath+'/bpm/core/bpmFormRight/edit.do?formAlias='+row.key+'&nodeId=_FORM',
					title:'表单视图的字段管理--'+row.name,
					width:780,
					height:480,
					max:true
				});
	      	}
	      	
	      	function clearBo(uid){
	      		var row=grid.getRowByUID(uid);
	      		mini.confirm("该操作会删除BO定义和关联的表,确定清除BO设定吗?", "提示信息", function(action){
	      			if(action!="ok") return;
	      			var url=__rootPath+'/bpm/form/bpmFormView/clearBoDef.do'
	      			var conf={
	      				url:url,
	      				data:{formViewId:row.viewId},
	      				success:function(data){
	      					grid.load();
	      				}
	      			}
	      			_SubmitJson(conf);
	      		});
	      	}
        	
	        function addNode(e){
	    		var systree=mini.get("systree");
	    		var node = systree.getSelectedNode();
		   		var parentId=node?node.treeId:0;
		   		//findNode("add",node.treeId)
		   		_OpenWindow({
		   			title:'添加表单视图分类',
		   			url:__rootPath+'/sys/core/sysTree/edit.do?parentId='+parentId+'&catKey=CAT_FORM_VIEW',
		   			width:720,
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
		   	
		   	function delNode(e){
		   		var systree=mini.get("systree");
		   		var node = systree.getSelectedNode();
		   		_SubmitJson({
		   			url:__rootPath+'/sys/core/sysTree/del.do?ids='+node.treeId,
		   			success:function(text){
		   				systree.load();
		   			}
		   		});
		   	}
		   	
		   	/**
		   	* 发布表单。
		   	*/
		   	function deploy(id){
		   		_SubmitJson({
		   			url:__rootPath+'/bpm/form/bpmFormView/deploy.do',
		   			data:{viewId:id},
		   			success:function(text){
		   				grid.load();
		   			}
		   		});
		   	}
		   	
		   	
		   	//按分类树查找数据字典
		   	function treeNodeClick(e){
		   		var node=e.node;
		   		
		   		grid.setUrl(__rootPath+'/bpm/form/bpmFormView/listData.do?treeId='+node.treeId);
		   		grid.load();
		   	}
		   	
		   	function preview(viewId){
		    	var url=__rootPath+'/bpm/form/bpmFormView/previewById/'+viewId+'.do' ;
		    	openNewWindow(url,"bpmFormView");
		    }
		   	
		   	function pdfTemp(viewId){
	   		    _OpenWindow({
					title:'PDF模板',
					max:true,
					url:__rootPath+'/bpm/form/bpmFormView/genPdfTemplate.do?viewId='+viewId,
					ondestroy:function(action){
						if(action!='ok'){
							return;
						}
					}
				});
		   	}
		   	
		   	function editTemplate(viewId){
		   		var url=__rootPath+'/bpm/form/bpmFormView/editTemplate.do?pkId='+viewId;
    			_OpenWindow({
    	    		url:url,
    	    		title:'编辑表单组件',
    	    		width:780,
    	    		height:400,
    	    		max:true
    	    	});	
		   	}
		   	
		   	function nodeGrant(e){
		   		var systree=mini.get("systree");
		   		var node = systree.getSelectedNode();
		   		var treeId=node.treeId;
		   		var url=__rootPath+'/bpm/form/bpmFormView/grant.do?treeId='+treeId;
		   		_OpenWindow({
    	    		url:url,
    	    		title:'表单权限',
    	    		width:780,
    	    		height:400,
    	    		max:false,
    	    		ondestroy:function(action){
    	    			mini.showTips({
    			            content: "<b>成功</b> <br/>保存成功",
    			            state: 'success',
    			            x: 'center',
    			            y: 'center',
    			            timeout: 3000
    			        });
    	    		}
    	    	});
		   	}

	        $(function(){
	        	$('.icon-add').click(function(e) {
	                $('.actionIcons span').css('display','block');

	            });

	        });
	        
	        function searchThisForm(btn) {
	        	var parent=$(btn.el).closest(".mini-toolbar");
	            var inputAry=$("input",parent);
	            searchThisByInput(inputAry);
	            
	        }
	        
	        function searchThisByInput(inputAry){
	        	var params=[];
	        	inputAry.each(function(i){
	           	 var el=$(this);
	           	 var obj={};
	           	 obj.name=el.attr("name");
	           	 if(!obj.name) return true;
	           	 obj.value=el.val();
	           	 params.push(obj);
	           });
	           
	           var data={};
	           
	           data.filter=mini.encode(params);
	    		data.pageIndex=grid.getPageIndex();
	    		data.pageSize=grid.getPageSize();
	    	    data.sortField=grid.getSortField();
	    	    data.sortOrder=grid.getSortOrder();
	    		grid.load(data);
	        }
	        
	        </script>
	
</body>
</html>