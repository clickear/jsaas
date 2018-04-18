<%-- 
    Document   : 流程实例列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>流程实例列表管理</title>
<%@include file="/commons/list.jsp"%>
<style type="text/css">
	.mini-layout-border>#center{
 		background: transparent;
	}
</style>
</head>
<body>
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
		    <div 
		    	title="流程方案分类" 
		    	region="west" 
		    	width="200"  
		    	showSplitIcon="true"
		    	showCollapseButton="false"
		    	showProxy="false"
		    	class="layout-border-r"
	    	>
		         <ul 
		         	id="systree" 
		         	class="mini-tree" 
		         	url="${ctxPath}/bpm/core/bpmInst/getInstTree.do" 
		         	style="width:100%; height:100%;" 
					showTreeIcon="true" 
					textField="name" 
					idField="treeId" 
					resultAsTree="false" 
					parentField="parentId" 
					expandOnLoad="true"
	                onnodeclick="treeNodeClick"  
                >        
	            </ul>
		    </div>
		    <div title="流程实例列表" region="center" showHeader="false" showCollapseButton="false">
	    		<div class="titleBar mini-toolbar" >
			         <ul>
						<li>
							<a class="mini-button" iconCls="icon-remove"  onclick="remove()">删除</a>
						</li>
						<li class="clearfix"></li>
					</ul>
					<div class="searchBox">
						<form id="searchForm" class="search-form" style="display: none;">						
							<ul>
								<li>
									<span class="text">创建时间从：</span>
									<input class="mini-datepicker" style="width:150px;" name="Q_b.CREATE_TIME__D_GE" onenter="onSearch"/>
								</li>
								<li>
									<span class="text">至</span>
									<input class="mini-datepicker" style="width:150px;" name="Q_b.CREATE_TIME__D_LE" onenter="onSearch" />
								</li>
								<li>
									<div style="display:none">
										<span class="text">发起人：</span>
										<input class="mini-buttonedit" allowInput="false" name="Q_b.CREATE_BY__S_EQ" onbuttonclick="selectUser" value="${userId}"/>
									</div>
								</li>
								<li>
									<span class="text">事项：</span><input class="mini-textbox" name="Q_SUBJECT__S_LK" onenter="onSearch" />
								</li>
								<li>
									<span class="text">状态：</span>
									<input 
										class="mini-combobox" 
										style="width:150px;" 
										name="Q_b.STATUS__S_EQ"
									 	data="[{id:'DRAFTED',text:'草稿'},{id:'RUNNING',text:'运行中'},{id:'SUCCESS_END',text:'成功结束'},
									        {id:'DISCARD_END',text:'作废'},{id:'ABORT_END',text:'异常中止结束'},{id:'PENDING',text:'挂起'}]"
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
				
				
				
				
				
				
				
				
				<div class="mini-fit rx-grid-fit" style="height: 100%;">
					<div id="datagrid1" class="mini-datagrid" style="width: 100%; height:100%;" allowResize="false"
						url="${ctxPath}/bpm/core/bpmInst/listByTreeId.do" idField="instId" multiSelect="true" showColumnsMenu="true"
						sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" >
						<div property="columns">
							<div type="indexcolumn" width="20">序号</div>
							<div type="checkcolumn" width="20"></div>
							<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
							<div field="treeName" width="60" headerAlign="center" >分类</div>
							<div field="subject" sortField="subject_" width="160" headerAlign="center" allowSort="true">事项</div>
							<div field="status" sortField="status_"   width="60" headerAlign="center" allowSort="true" renderer="onStatusRenderer">运行状态</div>
							<div field="taskNodes" width="80" headerAlign="center" >当前节点</div>
							<div field="taskNodeUsers"    width="80" headerAlign="center">当前节点处理人</div>						
							<div field="createBy" sortField="create_by_"  width="60" headerAlign="center" allowSort="true">发起人</div>
							<!-- div field="version" sortField="version_"  width="40" headerAlign="center" allowSort="true">版本</div-->
							<div field="createTime" sortField="create_time_"  width="60" headerAlign="center" allowSort="true">创建时间</div>
							<!-- div field="endTime" sortField="end_time_"  width="60" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss">结束时间</div-->
						</div>
					</div>
				</div>
		    </div>
	</div>
	
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.core.entity.BpmInst" 
	winHeight="450" winWidth="700" entityTitle="流程实例"
		baseUrl="bpm/core/bpmInst" />
		<script type="text/javascript">
		$(function(){
			onSearch();
		})
	
			function selectUser(e){
				var btn=e.sender;
				_UserDlg(true,function(user){
					btn.setValue(user.userId);
					btn.setText(user.fullname);
				});
			}
			
			function onClear(){
				var form=new mini.Form("#searchForm");
				form.clear();
				grid.setUrl("${ctxPath}/bpm/core/bpmInst/listByTreeId.do");
				grid.load();
			}
		
			function onSearch(){
				var formData=$("#searchForm").serializeArray();
				var data=jQuery.param(formData);
				grid.setUrl("${ctxPath}/bpm/core/bpmInst/listByTreeId.do?"+data);
				grid.load();
			}
			
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pk = record.pkId;
	            var uid=record._uid;
	            var rightJson=record.rightJson;
	            var instJson=rightJson.inst;
	            
	            var s = '<span class="icon-detail" title="明细" onclick="myDetailRow(\'' + pk + '\')"></span>';
	            
/*             	for(var i=0;i<instJson.length;i++){
            		var obj=instJson[i];
            		if(obj.key=="del" && obj.val){
            			s+='<span class="icon-remove" title="删除" onclick="delRow(\'' + pk + '\')"></span>';
            		}
            	}
            	
            	if(record.status=='RUNNING'){       
	            	s= s + ' <span class="icon-property" title="干预" onclick="opRow(\'' + uid + '\')"></span>';
	            } */
	            
	            for(var i=0;i<instJson.length;i++){
           			var json=instJson[i];
           			s+=getByJson(record,json);
           			
           		}
	                    
	            return s;
	        }
        	
        	 function getByJson(record,json){
	            var pkId = record.pkId;
	            var uid=record._uid;
	            var name=record.name;
        		var str="";
        		switch(json.key){
        			case "del":
        				if(json.val){
        					str='  <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
        				}
        				break;
        			case "intervene":
        				if(json.val && record.status=='RUNNING'){
        					str=' <span class="icon-property" title="干预" onclick="opRow(\'' + uid + '\')"></span>';
        				}
        				break;
        		}
        		return str;
        	}
        	
        	
        	
	        function opRow(uid){
        		var row=grid.getRowByUID(uid);
        		_OpenWindow({
        			iconCls:'icon-group',
        			title:row.subject,
        			url:__rootPath+'/bpm/core/bpmInst/operator.do?instId='+row.instId,
        			height:500,
        			width:800,
        			ondestroy:function(action){
        				if(action!='ok')return;
        				grid.load();
        			}
        		});
        	}
        	
        	
        	function myDetailRow(pkId){
        		var obj=getWindowSize();
                _OpenWindow({
                	url: __rootPath+"/bpm/core/bpmInst/inform.do?instId=" + pkId,
                    title: "流程明细", width: 1000, height: 600,
                });
        	}
        	function showGet(pkId){
        		_OpenWindow({
    				title : '查看详细',
    				width : 700,
    				height : 400,
    				max:true,
    				url : __rootPath	+ '/bpm/core/bpmInst/inform.do?instId=' + pkId
    			});
        	}
        	
	        grid.on("drawcell", function (e) {
	            var record = e.record,
		        field = e.field,
		        value = e.value;

	            //格式化日期
	            if (field == "createTime") {
	                if (mini.isDate(value)) e.cellHtml = mini.formatDate(value, "yyyy-MM-dd HH:mm");
	            }
	            
	            if(field=='subject'){
	            	e.cellHtml= '<a href="javascript:showGet(\'' + record.pkId + '\')">'+record.subject+'</a>';
	            }
	            
	            if(field=='createBy'){
	            	if(value){
	            		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>';
	            	}else{
	            		e.cellHtml='<span style="color:red">无</span>';
	            	}
	            }
	   
	        });
	        
	        grid.on('update',function(){
	        	_LoadUserInfo();
	        });
	        
	        function onStatusRenderer(e) {
	            var record = e.record;
	            var status = record.status;
	            
	            var arr = [ {'key' : 'DRAFTED','value' : '草稿','css' : 'orange'}, 
	   		                {'key' : 'RUNNING','value' : '运行中','css' : 'green'},
	   		                {'key' : 'SUCCESS_END','value' : '成功结束','css' : 'blue'}, 
	   		                {'key' : 'DISCARD_END','value' : '作废','css' : 'red'},
	   		                {'key' : 'ABORT_END','value' : '异常中止结束','css' : 'red'}, 
	   		                {'key' : 'PENDING','value' : '挂起','css' : 'gray'} 
	   		             ];
	   		
	   			return $.formatItemValue(arr,status);
	        }
	        
	    	function treeNodeClick(e){
		   		var node=e.node;
		   		grid.setUrl(__rootPath+'/bpm/core/bpmInst/listByTreeId.do?userId=${userId}&treeId='+node.treeId);
		   		grid.load();
		   	}
        </script>
	
</body>
</html>