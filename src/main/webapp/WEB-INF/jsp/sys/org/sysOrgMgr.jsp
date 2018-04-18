<%
	//组织架构管理，可管理全部租用组织架构，对于非SaaS管理员，仅能管理其本机构的
	//若传入InstId,并且需要检查当前组织机构的域名是否为在redxun.properties中指定的管理机构，
	//即可以进行格式化处理
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>系统组织架构管理</title>
	<%@include file="/commons/list.jsp"%>
	<style type="text/css">
		.mini-layout-border>#center{
	 		background: transparent;
		}
		.mini-tree .mini-grid-viewport{
			background: #fff;
		}
	</style>
</head>
<body>
	<div style="display:none;">
		<input class="mini-combobox" id="levelCombo" textField="name" valueField="level" 
               url="${ctxPath}/sys/org/osRankType/listByDimId.do?dimId=${osDimension.dimId}"/>
	</div>
	<ul id="dimNodeMenu" class="mini-contextmenu" onbeforeopen="onShowingNodeMenu">        
	    <li iconCls="icon-remove" onclick="delDim">删除维度</li>
	    <li iconCls="icon-edit" onclick="editDim">编辑维度</li>
	    <li iconCls="icon-add" onclick="addDim">增加维度</li>
	    <li iconCls="icon-edit" onclick="manageDimRank()">管理维度等级</li>  
	</ul>
	<div id="orgLayout" class="mini-layout" style="width:100%;height:100%;">
		    
		    <div 
		    	title="用户组维度" 
		    	region="west" 
		    	width="170"  
		    	showSplitIcon="true"
		    	showCollapseButton="false"
		    	showProxy="false"
	    	>
		        <div id="toolbar1" class="mini-toolbar" style="padding:2px; border-top:0;border-left:0;border-right:0; ">
					<a class="mini-button" iconCls="icon-add" plain="true" onclick="addDim()">添加</a>
		            <a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refreshDims()">刷新</a>
		        </div>
		        <div class="mini-fit">
			         <ul 
			         	id="dimTree" 
			         	class="mini-tree" 
			         	url="${ctxPath}/sys/org/osDimension/jsonAll.do?tenantId=${sysInst.instId}" 
			         	style="width:100%; height:100%;" 
						imgPath="${ctxPath}/upload/icons/"
						showTreeIcon="true" 
						textField="name" 
						idField="dimId" 
						resultAsTree="false"  
		                onnodeclick="dimNodeClick"  
		                contextMenu="#dimNodeMenu"
	                >        
		            </ul>
	            </div>
		    </div>
        
		    <div showHeader="false" showCollapseButton="false" iconCls="icon-group" region="center">
		         
              	<div class="titleBar mini-toolbar" >
			         <ul>
						<li>
							<a class="mini-button" iconCls="icon-save"  onclick="saveGroups()">保存</a>
						</li>
						<li>
							<a class="mini-button" iconCls="icon-addfolder"  onclick="newGroupRow();">添加</a>
						</li>
						<li>
							<a class="mini-button" iconCls="icon-addfolder"  onclick="newGroupSubRow();">添加子组</a>
						</li>
						<li>
							<a class="mini-button" iconCls="icon-remove"  onclick="delGroupRow();">删除</a>
						</li>
						<li>
							<ul id="moreMenu" class="mini-menu" style="display:none">
						        <li iconCls="icon-expand"  onclick="expandGrid()">展开</li>
		                       	<li iconCls="icon-collapse"  onclick="collapseGrid()">收起</li>
				                <li iconCls="icon-user"  onclick="userMgr('${sysInst.instId}','${sysInst.nameCn}');">管理用户</li>
				                <li iconCls="icon-account"  onclick="accountMgr('${sysInst.instId}','${sysInst.nameCn}');">管理账号</li>               	 	             
						    	<li iconCls="icon-relation" onclick="groupRelMgr('${sysInst.instId}','${sysInst.nameCn}');">管理用户组关系</li>
						    	<!-- 
						    	<li iconCls="icon-asyn" onclick="syncOrg('${sysInst.instId}')">同步第三方</li>
						    	-->
						    </ul>
							<a class="mini-menubutton"  menu="#moreMenu" >...</a>
						</li>
						
						<li class="clearfix"></li>
					</ul>
		     	</div>
		         <div class="mini-fit rx-grid-fit form-outer5" >
		         	<div 
		         		id="groupGrid" 
		         		class="mini-treegrid" 
		         		style="width:100%;height:100%;"     
			            showTreeIcon="true" 
			            treeColumn="name" idField="groupId" parentField="parentId" 
			            resultAsTree="false" 
			            allowResize="true"  allowAlternating="true"
			            oncellbeginedit="OnCellBeginEdit" 
			            oncellendedit="OnCellEndEdit"
			            allowRowSelect="true" 
			            onrowclick="groupRowClick" onbeforeload="onBeforeGridTreeLoad"
			            allowCellValid="true" oncellvalidation="onCellValidation" 
			            allowCellEdit="true" allowCellSelect="true" 
		            >
			            <div property="columns">
			            	<div name="action" cellCls="actionIcons" width="185" headerAlign="center" align="center" 
			            		renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
			                <div name="name" field="name" align="left" width="160">组名
			                	<input property="editor" class="mini-textbox" style="width:100%;" required="true"/>
			                </div>
			                <div field="key" name="key" align="left" width="80">组Key
			                	<input property="editor" class="mini-textbox" style="width:100%;" required="true"/>
			                </div>
			                <div name="rankLevel" field="rankLevel" align="left" width="80">用户组等级
			                	<input property="editor" class="mini-textbox" style="width:100%;" />
			                </div>
			                <div name="sn" field="sn" align="left" width="60">序号
			                	<input property="editor" changeOnMousewheel="false" class="mini-spinner"  minValue="1" maxValue="100000" required="true"/>
			                </div>
			            </div>
			        </div>
		        </div>
		    </div><!-- end of the center region  -->		    
		    <div 
		    	region="east"  
		    	width="430"  
		    	showSplitIcon="true" 
		    	showHeader="false"
		    	showCollapseButton="false"
		    	showProxy="false"
		    	bodyStyle="border:none;padding:2px;"
	    	>
		  	    	<div class="mini-fit">
			    	<div class="mini-tabs" style="height:100%;border:none;" onactivechanged="resetTab" bodyStyle="border:none;padding:2px;">
					    	<div title="用户关系" iconCls="icon-user">
					    		<div class="mini-toolbar">
					    			<table style="width:100%;">
										<tr>
											<td style="width:100%;">
								                <a class="mini-button" iconCls="icon-addfolder"  onclick="addUser();">添加</a>
								                <a class="mini-button" iconCls="icon-addfolder"  onclick="joinUser();">加入</a>
								                <a class="mini-button" iconCls="icon-close"  onclick="unjoinUser();">移除</a>
								                <a class="mini-button" iconCls="icon-remove"  onclick="deleteUser();">删除</a>
								                <a class="mini-button" iconCls="icon-search" onclick="onSearchUsers()">查询</a>
							                </td>
						                </tr>
						                <tr>
						                	<td>
						                		<form id="userForm" class="text-distance">
						                			 <input type="hidden" id="groupId" name="groupId" value=""/>
						                			 <input class="mini-textbox" id="fullname" name="fullname" emptyText="请输入姓名"/>
									            	 <input class="mini-textbox"  id="userNo" name="userNo" emptyText="请输入用户编号"/>
									            	 <!-- a class="mini-button" iconCls="icon-cancel"  onclick="onClear()">清空</a-->
						                		</form>
					                		</td>
						                 </tr>
						               </table>        
						        </div>
					       		<div class="mini-fit rx-grid-fit" style="padding:2px;">
					        		<div id="userGridTab" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;border:none;" bodyStyle="padding:2px;">
									</div>
					       		</div>
				    	</div>
				    	<div title="组关系" iconCls="icon-group">
			    			<div class="mini-toolbar" style="padding:2px;">
				               <div>
					                <a class="mini-button" iconCls="icon-addfolder"  onclick="addGroupRelType();">添加用户组关系</a>
					                <a class="mini-button" iconCls="icon-close" onclick="removeGroupRelType();">移除用户组关系</a>
					                <a class="mini-button" iconCls="icon-save" onclick="saveGroupRelInst();">保存</a>
			                   </div>
					        </div>
					        <div class="mini-fit" style="border:0;">
						    	<div id="groupRelGridTab" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;border:none" bodyStyle="padding:2px;"></div>
					    	</div>
				    	</div>
			    	</div><!-- end of tabs -->
		    	</div>
		    </div><!-- end of region east -->	
    </div>
	<script type="text/javascript">
		mini.parse();
		var dimTree=mini.get('#dimTree');
		var groupGrid=mini.get('#groupGrid');
		
		var userGrid=mini.get('#usergrid');
		var defaultDimId='${osDimension.dimId}';
		var userGridTab=mini.get('#userGridTab');
		var groupRelGridTab=mini.get('groupRelGridTab');
		var layout=mini.get('orgLayout');
		//当前操作的机构ID
		var tenantId='${sysInst.instId}';
		var instType='${sysInst.instType}';
		
		//是否显示用户组的授权按钮
		var isGrantButton=false;
		
		function expandGrid(){
			groupGrid.expandAll();
		}
		
		
		function collapseGrid(){
			groupGrid.collapseAll();
		}
		
		//查找用户
		function onSearchUsers(){
			var formData=$("#userForm").serializeArray();
			var data=jQuery.param(formData);
			var tab=userGridTab.getActiveTab();
			var relTypeId=tab.name;
			var grid=mini.get('userGrid_'+relTypeId);
			grid.setUrl("${ctxPath}/sys/org/osUser/listByGroupIdRelTypeId.do?tenantId="+tenantId+"&relTypeId="+relTypeId + "&"+ data);
			grid.load();
		}
		
		function onShowingNodeMenu(e){
			var node=dimTree.getSelectedNode();
			//为默认
			if(node && node.isSystem=='YES'){
				  e.cancel = true;
				  //阻止浏览器缺省右键菜单
				  e.htmlEvent.preventDefault();
			      return;
			}
		}
		
		function onClear(){
			$("#userForm")[0].reset();
		}
		
		//添加新的用户
		function addUser(){
			
			//获得激活的tab的标属性
			var tab=userGridTab.getActiveTab();
			
			var groupId=$("#groupId").val();
			
			var relTypeId=tab!=null?tab.name:'';
			
			_OpenWindow({
				title:'添加新用户',
				url:__rootPath+'/sys/org/osUser/edit.do?tenantId='+tenantId+'&groupId='+groupId+'&relTypeId='+relTypeId,
				height:350,
				width:650,
				max:true,
				ondestroy:function(action){
					if(action!='ok')return;
					var grid=mini.get('#userGrid_'+relTypeId);
					grid.load();
				}
			});
		}
		//重置tab的高度
		function resetTab(){
			mini.layout();
		}
		
		//加入已经存在的用户
		function joinUser(){
			var selectedRow=groupGrid.getSelected();
			if(!selectedRow){
				alert('请选择用户组行!');
				return;
			}
			
			//获得激活的tab的标属性
			var tab=userGridTab.getActiveTab();
			
			var groupId=$("#groupId").val();
			
			if(tab==null || groupId=='') return;
			var relTypeId=tab.name;
			var grid=mini.get('#userGrid_'+relTypeId);
			
			_TenantUserDlg(tenantId,false,function(users){
				if(users==null || users.length==0) return;
				var userIds=[];
				for(var i=0;i<users.length;i++){
					userIds.push(users[i].userId);
				}
				_SubmitJson({
					url:__rootPath+'/sys/org/osUser/joinUser.do',
					method:'POST',
					data:{
						userIds:userIds.join(','),
						relTypeId:tab.name,
						groupId:groupId
					},
					success:function(text){
						var result=mini.decode(text);
						if(result.success){
							grid.load();
						}
					}
				});
			});
			
		}
		
		//从用户与组的关系中移除该用户
		function unjoinUser(){
			var tab=userGridTab.getActiveTab();
			var groupId=$("#groupId").val();
			if(tab==null || groupId=='') return;
			var relTypeId=tab.name;
			var grid=mini.get('#userGrid_'+relTypeId);
			var selRows=grid.getSelecteds();
			if(selRows.length==0) return;
			var userIds=[];
			for(var i=0;i<selRows.length;i++){
				userIds.push(selRows[i].userId);
			}
			_SubmitJson({
				url:__rootPath+'/sys/org/osUser/unjoinUser.do',
				method:'POST',
				data:{
					userIds:userIds.join(','),
					relTypeId:relTypeId,
					groupId:groupId
				},
				success:function(text){
					var result=mini.decode(text);
					if(result.success){
						grid.load();
					}
				}
			});
		}
		
		function OnCellBeginEdit(e) {
	    	var node=dimTree.getSelectedNode();
	    	var dimId;
	    	if(!node){
	    		dimId=defaultDimId;
	    	}else{
	    		dimId=node.dimId;
	    	}
	    	
			var field = e.field;
	        
			 if (field == "rankLevel") {
	               e.editor=mini.get('levelCombo');
	               e.editor.setUrl("${ctxPath}/sys/org/osRankType/listByDimId.do?dimId="+dimId);
	               e.column.editor=e.editor;
	         }
	    }
		
		function OnCellEndEdit(e){
			var record = e.record, field = e.field;
            var val=e.value;
            
			 if(field=='name' && (record.key==undefined || record.key=='')){
	            	//自动拼音
	           		$.ajax({
	           			url:__rootPath+'/pub/base/baseService/getPinyin.do',
	           			method:'POST',
	           			data:{words:val,isCap:'true',isHead:'true'},
	           			success:function(result){
	           				groupGrid.updateRow(record,{key:result.data})
	           			}
	           		});
	            	return;
	            }
			
		}
		//添加维度
    	function addDim(){
    		_OpenWindow({
    			title:'添加维度',
    			height:410,
    			width:810,
    			url:'${ctxPath}/sys/org/osDimension/edit.do?tenantId='+tenantId,
    			ondestroy:function(action){
    				if(action!='ok') return;
    				dimTree.load();
    			}
    		});
    	}
		function refreshDims(){
			dimTree.load();
		}
		function editDim(e){
			 var node = dimTree.getSelectedNode();
			_OpenWindow({
    			title:'编辑维度-'+node.name,
    			height:400,
    			width:680,
    			url:'${ctxPath}/sys/org/osDimension/edit.do?pkId='+node.dimId,
    			ondestroy:function(action){
    				if(action!='ok') return;
    				dimTree.load();
    			}
    		});
		}
		
		function delDim(e){
			 var node = dimTree.getSelectedNode();
			 if(!confirm("确定删除维度-"+node.name+"?")){
				 return;
			 }
			 _SubmitJson({
				 url:'${ctxPath}/sys/org/osDimension/del.do?ids='+node.dimId,
				 success:function(result){
					 dimTree.load();
				 }
			 });
		}
		
		function onBeforeGridTreeLoad(e){
			var tree = e.sender;    //树控件
	        var node = e.node;      //当前节点
	        var params = e.params;  //参数对象
	        //可以传递自定义的属性
	        params.parentId = node.groupId; //后台：request对象获取"myField"
		}
		
		function dimNodeClick(e){
			var node=e.node;
			if(node.isGrant=='YES'){
				isGrantButton=true;
			}else{
				isGrantButton=false;
			}
			layout.updateRegion("center",{title:'用户组--'+node.name});
			
			groupGrid.setUrl('${ctxPath}/sys/org/sysOrg/listByDimId.do?dimId='+node.dimId+'&tenantId='+tenantId);
			//var url='${ctxPath}/sys/org/sysOrg/listByDimId.do?dimId='+node.dimId+'&tenantId='+tenantId;
			groupGrid.load({},function(){
				//加载多一层
				for(var i=0;i<groupGrid.getData().length;i++){
					var row=groupGrid.getData()[i];
					if(row.childs>0){
						groupGrid.loadNode(row);
					}
				}
			});
			layout.expandRegion("south");
			//动态加载该维度用户的关系
			$.getJSON(__rootPath+'/sys/org/osRelType/getRelTypesOfGroupUser.do?tenantId='+tenantId+'&dimId='+node.dimId,function(json){
				//alert(mini.encode(json));
				userGridTab.removeAll();
				for(var i=0;i<json.length;i++){
					var tab=userGridTab.addTab({
						title:json[i].name,
						name:json[i].id,
						iconCls:'icon-user'
					});
					var el=userGridTab.getTabBodyEl(tab);
					var grid=new mini.DataGrid();
					grid.set({
						id:"userGrid_"+json[i].id,
						style:"width:100%;height:100%;",
						url:"${ctxPath}/sys/org/osUser/listByGroupIdRelTypeId.do?relTypeId="+json[i].id,
						idField:"userId",
						multiSelect:true,
						allowAlternating:true,
						columns:[{
							type:'indexcolumn',
							header:'序号'
						},{
							type:'action',
							header:'操作',
							renderer:"onUserActionRenderer"
						},{
							field:'fullname',
							sortField:'FULLNAME_',
							header:'姓名',
							width:120,
							headerAlign:'center',
							allowSort:true
						},{
							field:'userNo',
							header:'用户编号',
							sortField:'USER_NO_',
							width:110,
							headerAlign:'center',
							allowSort:true
						}]
					});
					grid.render(el);
					
					grid.on("drawcell", function (e) {
			            var record = e.record,
				        field = e.field,
				        value = e.value;
			          
			            if(field=='sex'){
			            	if(value=='Male'){
			            		e.cellHtml='<img src="${ctxPath}/styles/images/male.png" alt="男性">';
			            	}else{
			            		e.cellHtml='<img src="${ctxPath}/styles/images/female.png" alt="女性">';
			            	}
				        }
					});
				}
				userGridTab.activeTab(userGridTab.getTab(0));
			});
			//动态加载该用户组下的用户组关系类型
			$.getJSON(__rootPath+'/sys/org/osRelType/getRelTypesOfGroupGroup.do?dimId='+node.dimId,function(json){
				groupRelGridTab.removeAll();
				for(var i=0;i<json.length;i++){
					var tab=groupRelGridTab.addTab({
						title:json[i].name,
						name:json[i].id
					});
					var el=groupRelGridTab.getTabBodyEl(tab);
					var grid=new mini.DataGrid();
					grid.set({
						id:'groupGroupGrid_'+json[i].id,
						style:"width:100%;height:100%;",
						url:"${ctxPath}/sys/org/sysOrg/listByGroupIdRelTypeId.do?relTypeId="+json[i].id,
						idField:"instId",
						multiSelect:true,
						allowCellEdit:true,
						allowCellSelect:true,
						allowAlternating:true,
						columns:[{
							type:'indexcolumn',
							header:'序号',
							width:40,
						},{
							type:'checkcolumn',
							width:30,
						},{
							type:'action',
							header:'操作',
							width:50,
							renderer:"groupRelActionRenderer"
						},{
							field:'groupName',
							header:'组名',
							sortField:'GROUP_NAME_',
							width:120,
							allowSort:true
						},{
							field:'alias',
							header:'别名',
							sortField:'ALIAS_',
							width:160,
							allowSort:true,
							editor:{type:'textbox'}
						},
						{
							field:'groupKey',
							header:'组Key',
							sortField:'GROUP_KEY_',
							width:80,
							allowSort:true
						}]
					});
					grid.render(el);
				}
				groupRelGridTab.activeTab(groupRelGridTab.getTab(0));
			});
		}
		
		//保存用户组实例
		function saveGroupRelInst(){
			var tabs2=groupRelGridTab.getTabs();
			var groupJsons=[];
        	for(var i=0;i<tabs2.length;i++){
        		var tab=tabs2[i];
        		var relTypeId=tab.name;
        		var grid=mini.get('groupGroupGrid_'+relTypeId);
        		groupJsons=groupJsons.concat(grid.getData());
        	}
        	
        	if(groupJsons.length==0){
        		return;
        	}
        	
        	_SubmitJson({
        		url:__rootPath+'/sys/org/sysOrg/saveGroupRelInst.do',
        		method:'POST',
        		data:{
        			insts:mini.encode(groupJsons)
        		},
        		success:function(result){
        			
        		}
        	});
		}
		
		function onCellValidation(e){
        	if(e.field=='key' && (!e.value||e.value=='')){
        		 e.isValid = false;
        		 e.errorText='用户组Key不能为空！';
        	}
        	if(e.field=='name' && (!e.value||e.value=='')){
        		e.isValid = false;
       		 	e.errorText='用户组名称不能为空！';
        	}
        	
        	if(e.field=='sn' && (!e.value||e.value=='')){
        		e.isValid = false;
       		 	e.errorText='序号不能为空！';
        	}
        }
        //用户组操作列表
        function onActionRenderer(e) {
            var record = e.record;
            var uid = record._uid;
            
            var s = '<span class="icon-button icon-rowbefore" title="在前添加用户组" onclick="newBeforeRow(\''+uid+'\')"></span>';
            s+='<span class="icon-button icon-rowafter" title="在后添加用户组" onclick="newAfterRow(\''+uid+'\')"></span>';
            s+='<span class="icon-button icon-add" title="添加子用户组" onclick="newGroupSubRow()"></span>';
            s+='<span class="icon-button icon-save" title="保存" onclick="saveGroupRow(\'' + uid + '\')"></span>';
            s+='<span class="icon-button icon-remove" title="删除" onclick="delGroupRow(\'' + uid + '\')"></span>';
            if(record.groupId && record.groupId!='' && isGrantButton){
            	s+='<span class="icon-button icon-grant" title="子系统授权" onclick="grantGroupRow(\'' + uid + '\')"></span>';
                s+='<span class="icon-button icon-card_pen" title="新增属性" onclick="grantNewAttribute(\'' + uid + '\')"></span>';
            }
           
            return s;
        }
        
        //用户操作编辑
        function onUserActionRenderer(e){
			var uid = e.record._uid;            
            var s = '<span class="icon-button icon-detail" title="用户明细" onclick="userDetail(\''+uid+'\')"></span>';
            s+=' <span class="icon-button icon-edit" title="编辑" onclick="userEdit(\''+uid+'\')"></span>';
            s+=' <span class="icon-button icon-close" title="移除" onclick="userUnjoin(\''+uid+'\')"></span>';
            return s;
        }
        
        function groupRelActionRenderer(e){
        	var uid = e.record._uid; 
            var s = '<span class="icon-button icon-user" title="组关系内用户" onclick="grDetail(\''+uid+'\')"></span>';
            return s;
        }
        
        function grDetail(uid){
        	var selectedRow=groupGrid.getSelected();
			if(!selectedRow){
				alert('请选择用户组行!');
				return;
			}
			
			var tab=groupRelGridTab.getActiveTab();
        	if(tab==null) return;
        	
        	var relTypeId=tab.name;
        	var relGrid=mini.get('groupGroupGrid_'+relTypeId);
			
        	var row = relGrid.getRowByUID(uid);
        	var title=selectedRow.name + '-' + row.groupName+'用户管理';
        	_OpenWindow({
        		title:title,
        		height:450,
        		width:800,
        		url:__rootPath+'/sys/org/osRelInst/groupRelUsers.do?p1='+row.party1+'&p2='+row.party2 +'&dimId1='+row.dimId1+'&dimId2='+row.dimId2
        	});
        }
        
        //授权用户组
        function grantGroupRow(uid){
			var row=groupGrid.getRowByUID(uid);
			_OpenWindow({
				title:'['+row.name + ']'+'--授权管理',
				url:__rootPath+'/sys/org/osGroup/toGrant.do?groupId='+row.groupId + '&tenantId='+tenantId + "&instType="+ instType,
				width:450,
				height:600
			});
        }
        
        function grantNewAttribute(uid){
        	var row=groupGrid.getRowByUID(uid);
        	_OpenWindow({
				title:'['+row.name + ']'+'--组织属性管理',
				url:__rootPath+'/sys/org/osCustomAttribute/grantGroupAttribute.do?attributeType=Group&targetId='+row.groupId,
				width:900,
				height:600
			});
        }
        /*
        function onGroupActionRenderer(){
        	var uid = e.record._uid;            
            var s = '<span class="icon-button icon-detail" title="用户组明细" onclick="groupDetail(\''+uid+'\')"></span>';
            s+=' <span class="icon-button icon-remove" title="删除" onclick="groupRelInstDel(\''+uid+'\')"></span>';
            return s;
        }*/
        
		//在当前选择行的下添加子记录
        function newGroupSubRow(){
        	var node = groupGrid.getSelectedNode();
            var newNode = {sn:1};
            groupGrid.addNode(newNode, "add", node);
            groupGrid.expandNode(node);
        }
		
        function newGroupRow() {            
            var node = groupGrid.getSelectedNode();
            groupGrid.addNode({sn:1}, "before", node);
            groupGrid.cancelEdit();
            groupGrid.beginEditRow(node);
        }

        function newAfterRow(row_uid){
        	var node = groupGrid.getRowByUID(row_uid);
        	groupGrid.addNode({sn:1}, "after", node);
        	groupGrid.cancelEdit();
        	groupGrid.beginEditRow(node);
        }
        
        function newBeforeRow(row_uid){
        	var node = groupGrid.getRowByUID(row_uid);
        	groupGrid.addNode({sn:1}, "before", node);
        	groupGrid.cancelEdit();
        	groupGrid.beginEditRow(node);
        }
		
        function saveGroupRow(row_uid) {
        	//表格检验
        	groupGrid.validate();
        	if(!groupGrid.isValid()){
            	return;
            }
			var row = groupGrid.getRowByUID(row_uid);
            
			var node = dimTree.getSelectedNode();
			var dimId;
			if(node){
				dimId=node.dimId;
			}else{
				alert("请选择维度！");
				return;
			}

            var json = mini.encode(row);
            
            _SubmitJson({
            	url: "${ctxPath}/sys/org/sysOrg/saveGroup.do",
            	data:{
            		dimId:dimId,
            		tenantId:tenantId,
            		data:json},
            	method:'POST',
            	success:function(text){
            		var result=mini.decode(text);
            		if(result.data && result.data.groupId){
            			groupGrid.updateRow(row,result.data);
            		}
            	}
            });
        }
        
      	//批量保存用户组
        function saveGroups(){
			var node = dimTree.getSelectedNode();
			var dimId=null;
			if(node){
				dimId=node.dimId;
			}else{
				alert("请选择维度！");
				return;
			}
			
        	//表格检验
        	groupGrid.validate();
        	if(!groupGrid.isValid()){
            	return;
            }
        	
        	//获得表格的每行值
        	var data = groupGrid.getData();
        	if(data.length<=0)return;
            var json = mini.encode(data);
            
            var postData={
            		dimId:dimId,
            		gridData:json,
            		tenantId:tenantId		
            };
            
            _SubmitJson({
            	url: "${ctxPath}/sys/org/sysOrg/batchSaveGroup.do",
            	data:postData,
            	method:'POST',
            	success:function(text){
            		groupGrid.load();
            	}
            });
        }
      	
        //删除用户组行
        function delGroupRow(row_uid) {
        	var row=null;
        	if(row_uid){
        		row = groupGrid.getRowByUID(row_uid);
        	}else{
        		row = groupGrid.getSelected();	
        	}
        	
        	if(!row){
        		alert("请选择删除的用户组！");
        		return;
        	}
        	
        	if (!confirm("确定删除此记录？")) {return;}

            if(row && !row.groupId){
            	groupGrid.removeNode(row);
            	return;
            }else if(row.groupId){
            	_SubmitJson({
            		url: "${ctxPath}/sys/org/sysOrg/delGroup.do?groupId="+row.groupId,
                	success:function(text){
                		groupGrid.removeNode(row);
                	}
                });
            } 
        }
        
        function manageDimRank(){
        	var node=dimTree.getSelectedNode();
        	_OpenWindow({
        		url:"${ctxPath}/sys/org/osRankType/list.do?dimId="+node.dimId,
        		title:node.name+"-等级管理",
        		width:600,
        		height:350
        	});
        }
        
        //组的行点击
        function groupRowClick(e){
        	var record=e.record;
        	var groupId=record.groupId;
        	if(!groupId) return;
        	$("#groupId").val(groupId);
        	
        	layout.updateRegion("south",{title:'用户组关系--'+record.name,visible: true });
        	layout.updateRegion("east",{title:'用户--'+record.name,visible: true });
        	
        	var tabs=userGridTab.getTabs();
        	for(var i=0;i<tabs.length;i++){
        		var tab=tabs[i];
        		var relTypeId=tab.name;
        		var userGrid=mini.get('#userGrid_'+relTypeId);
            	userGrid.setUrl("${ctxPath}/sys/org/osUser/listByGroupIdRelTypeId.do?groupId="+groupId+"&relTypeId="+relTypeId);
            	userGrid.load();
        	}
        	
        	var tabs2=groupRelGridTab.getTabs();
        	for(var i=0;i<tabs2.length;i++){
        		var tab=tabs2[i];
        		var relTypeId=tab.name;
        		var grid=mini.get('groupGroupGrid_'+relTypeId);
        		grid.setUrl(__rootPath+'/sys/org/sysOrg/listByGroupIdRelTypeId.do?groupId='+groupId+'&relTypeId='+relTypeId);
        		grid.load();
        	}
        	
        }
		
        //管理组的关系
        function groupRelMgr(tenantId,tenantName){ 
        	var title=tenantName==''?'用户与组关系定义':tenantName+'-用户与组关系定义';        	
    		var config = {};
    		config.title = title;
    		config.url = '${ctxPath}/sys/org/osRelType/list.do?tenantId='+tenantId;
    		config.width = '80%';
    		config.height = '80%';    		
    		_OpenWindow(config);    		
        }
        
      	//管理用户
        function userMgr(tenantId,tenantName){ 
        	var title=tenantName==''?'用户管理':tenantName+'-用户管理';       	
        	var config = {};
    		config.title = title;
    		config.url = '${ctxPath}/sys/org/osUser/list.do?tenantId='+tenantId
    		config.width = '80%';
    		config.height = '80%';    		
    		_OpenWindow(config);
        }
      	
      //管理账号
        function accountMgr(tenantId,tenantName){ 
        	var title=tenantName==''?'账号管理':tenantName+'-账号管理';
         	var config = {};
    		config.title = title;
    		config.url = '${ctxPath}/sys/core/sysAccount/list.do?tenantId='+tenantId;
    		config.width = '80%';
    		config.height = '80%';    		
    		_OpenWindow(config);
        }
        
        //用户明细
        function userDetail(uid){
        	var tab=userGridTab.getActiveTab();
        	var userGrid=mini.get('#userGrid_'+tab.name);
        	var row=userGrid.getRowByUID(uid);
        	_OpenWindow({
        		url:'${ctxPath}/sys/org/osUser/get.do?pkId='+row.userId,
        		title:'用户明细',
        		max:true,
        		height:350,
        		width:650
        	});
        }
        //移除用户
        function userUnjoin(uid){
        	
        	if(!confirm('确定要移除该用户吗？')){
        		return;
        	}
        	
        	var tab=userGridTab.getActiveTab();
			var groupId=$("#groupId").val();
			if(tab==null || groupId=='') return;
			var relTypeId=tab.name;
			var grid=mini.get('#userGrid_'+relTypeId);
			var row=grid.getRowByUID(uid);
			_SubmitJson({
				url:__rootPath+'/sys/org/osUser/unjoinUser.do',
				method:'POST',
				data:{
					userIds:row.userId,
					relTypeId:relTypeId,
					groupId:groupId
				},
				success:function(text){
					var result=mini.decode(text);
					if(result.success){
						grid.load();
					}
				}
			});
        }
        
        /*用户组明细
        function groupDetail(uid){
        	var tab=groupRelGridTab.getActiveTab();
        	var grid=mini.get('#groupGroupGrid_'+tab.name);
        	var row=grid.getRowByUID(uid);
        	_OpenWindow({
        		url:'${ctxPath}/sys/org/osGroup/get.do?pkId='+row.groupId,
        		title:'用户组明细',
        		max:true,
        		height:350,
        		width:650
        	});
        }*/
        
        //用户编辑
        function userEdit(uid){
        	var tab=userGridTab.getActiveTab();
        	var userGrid=mini.get('#userGrid_'+tab.name);
        	var row=userGrid.getRowByUID(uid);
        	_OpenWindow({
        		url:'${ctxPath}/sys/org/osUser/edit.do?pkId='+row.userId,
        		title:'用户编辑',
        		max:true,
        		height:350,
        		width:650
        	});
        }
        //删除用户
        function userDel(uid){
           	var tab=userGridTab.getActiveTab();
           	var userGrid=mini.get('#userGrid_'+tab.name);
           	var row=userGrid.getRowByUID(uid);
           	if(row==null || !confirm('确定要删除选择的用户吗？')){
       			return;
       		}
           	_SubmitJson({
           		url:__rootPath+'/sys/org/osUser/del.do?ids='+row.userId,
           		method:'POST',
           		success:function(text){
           			var tabs=userGridTab.getTabs();
                	for(var i=0;i<tabs.length;i++){
                		var tab=tabs[i];
                		var relTypeId=tab.name;
                		var userGrid=mini.get('#userGrid_'+relTypeId);
                    	userGrid.load();
                	}
           		}
           	});
        }
        
        function deleteUser(){
        	var tab=userGridTab.getActiveTab();
           	var userGrid=mini.get('#userGrid_'+tab.name);
           	var selRows=userGrid.getSelecteds();
           	if(selRows==null || selRows.length==0) return;
        	if(!confirm('确定要删除选择的用户吗？')){
       			return;
       		}
           	var ids=[];
           	for(var i=0;i<selRows.length;i++){
           		ids.push(selRows[i].userId);
           	}
           	
           	_SubmitJson({
           		url:__rootPath+'/sys/org/osUser/del.do?ids='+ids.join(','),
           		method:'POST',
           		success:function(text){
           			var tabs=userGridTab.getTabs();
                	for(var i=0;i<tabs.length;i++){
                		var tab=tabs[i];
                		var relTypeId=tab.name;
                		var userGrid=mini.get('#userGrid_'+relTypeId);
                    	userGrid.load();
                	}
           		}
           	});
           	
        }
        
        //添加用户组间的关系
        function addGroupRelType(){
        	var selectedRow=groupGrid.getSelected();
			if(!selectedRow){
				alert('请选择用户组行!');
				return;
			}
        	//获得当前的关系方是否包括另一维度，若仅是另一维度的用户组，则弹出的用户组仅能选择该维度下的用户组
        	var tab=groupRelGridTab.getActiveTab();
        	if(tab==null) return;
        	var groupId=selectedRow.groupId;
        	var relTypeId=tab.name;
        	var grid=mini.get('groupGroupGrid_'+relTypeId);
        	$.getJSON('${ctxPath}/sys/org/osRelType/getRecord.do?pkId='+relTypeId,function(relType){
        		var showDimId=relType.dimId2;
        		
        		//function _TenantGroupDlg(tenantId,single,showDimId,callback,reDim){
        		//为当前选择的用户组添加其对应的关系实例
            	_TenantGroupDlg(tenantId,false,showDimId,function(groups){
           			var groupIds=[];
   					//为多个用户组
   					for(var i=0;i<groups.length;i++){
   						groupIds.push(groups[i].groupId);
   					}
   					_SubmitJson({
   						url:__rootPath+'/sys/org/sysOrg/joinGroups.do',
   						method:'POST',
   						data:{
   							groupId:groupId,
   							relTypeId:relTypeId,
   							groupIds:groupIds.join(',')
   						},
   						success:function(text){
   							var result=mini.decode(text);
   							if(result.success){
   								grid.load();
   							}
   						}
   					});
    			},false);
        	});
        }
        
        //删除用户组关系
        function removeGroupRelType(){
        	var tab=groupRelGridTab.getActiveTab();
        	if(tab==null) return;
        	var relTypeId=tab.name;
        	var grid=mini.get('#groupGroupGrid_'+relTypeId);
        	var selRows=grid.getSelecteds();
        	if(selRows.length<=0){
        		return;
        	}
        	
        	var instIds=[];
        	for(var i=0;i<selRows.length;i++){
        		instIds.push(selRows[i].instId);	
        	}
        	
        	_SubmitJson({
        		url:__rootPath+'/sys/org/sysOrg/removeOsRelInst.do',
        		method:'POST',
        		data:{
        			instIds:instIds.join(',')
        		},
        		success:function(text){
        			var result=mini.decode(text);
        			if(result.success){
        				grid.load();
        			}
        		}
        	});
        }
        
        function syncOrg(instId){
        	_SubmitJson({
        		url:__rootPath+'/sys/org/sysOrg/syncOrg.do?instId='+instId,
        		method:'POST',
        		success:function(text){
        			try{
        				groupGrid.load();
        			}catch(e){
        				
        			}
        		}
        	});
        }
	</script>
</body>
</html>