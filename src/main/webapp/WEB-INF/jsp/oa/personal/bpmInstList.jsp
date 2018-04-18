<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>流程实例列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<div id="layout1" class="mini-layout" style="width:100%;height:100%;" borderStyle="border-left:1px solid #aaa;border-right:1px solid #aaa">
		    <div title="流程方案分类" region="west" width="250"  showSplitIcon="true">
		         <ul id="systree" class="mini-tree" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_BPM_SOLUTION" style="width:100%;" 
					showTreeIcon="true" textField="name" idField="treeId" resultAsTree="false" parentField="parentId" expandOnLoad="true"
	                onnodeclick="treeNodeClick"  >        
	            </ul>
		    </div>
		    <div title="流程实例列表" region="center" showHeader="true" showCollapseButton="false">
	    		
				<div class="mini-toolbar">
					<form id="searchForm">
						<table style="width:100%">
							<tr>
								<td>
									<span>发起人:</span><input class="mini-buttonedit" allowInput="false" name="userId" onbuttonclick="selectUser"/>
									<span>事项:</span><input class="mini-textbox" name="subject" onenter="onSearch" />
									<span>状态:</span><input class="mini-combobox" name="status" data="[{id:'DRAFTED',text:'草稿'},{id:'RUNNING',text:'运行中'},{id:'SUCCESS_END',text:'成功结束'}]"/>
									<span>创建时间从:</span><input class="mini-datepicker" name="Q_createtime1_D_GE" onenter="onSearch"/>至<input class="mini-datepicker" name="Q_createtime2_D_LE" onenter="onSearch" />
									<a class="mini-button" iconCls="icon-search" onclick="onSearch">查询</a>
									<a class="mini-button" iconCls="icon-clear" onclick="onClear">清空查询</a>
								</td>
							</tr>
						</table>
					</form>
				</div>
				
				<div class="mini-fit" style="height: 100%;">
					<div id="datagrid1" class="mini-datagrid" style="width: 100%; height:100%;" allowResize="false"
						url="${ctxPath}/bpm/core/bpmInst/listByTreeId.do" idField="instId" multiSelect="true" showColumnsMenu="true"
						sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" >
						<div property="columns">
							<div type="indexcolumn" width="20"></div>
							<div type="checkcolumn" width="20"></div>
							<div field="subject" sortField="subject_" width="160" headerAlign="center" allowSort="true">事项</div>
							<div field="status" sortField="status_"   width="60" headerAlign="center" allowSort="true">运行状态</div>
							<div field="createBy" sortField="create_by_"  width="60" headerAlign="center" allowSort="true">发起人</div>
							<div field="createTime" sortField="create_time_"  width="60" headerAlign="center" allowSort="true">创建时间</div>
							<div field="endTime" sortField="end_time_"  width="60" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss">结束时间</div>
						</div>
					</div>
				</div>
		    </div>
	</div>
	
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.core.entity.BpmInst" 
	winHeight="450" winWidth="700" entityTitle="流程实例"
		baseUrl="bpm/core/bpmInst" />
		<script type="text/javascript">
	
			function selectUser(e){
				var btn=e.sender;
				_UserDlg(true,function(user){
					btn.setValue(user.userId);
					btn.setText(user.fullname);
				});
			}
			
			function onClear(){
				$("#searchForm")[0].reset();
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
	            var s = '<span class="icon-detail" title="明细" onclick="myDetailRow(\'' + pk + '\')"></span>';
	            
	            return s;
	        }
        	
        	
        	function myDetailRow(pkId){
        		var obj=getWindowSize();
                _OpenWindow({
                	url: __rootPath+"/bpm/core/bpmInst/inform.do?instId=" + pkId,
                    title: "流程明细", width: 600, height: 700,
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
	            if(field=='status'){
	            	if(value=='DRAFTED'){
	            		e.cellHtml='<img src="${ctxPath}/styles/icons/icon-bpm-draft.png" />草稿';
	            	}else if(value=='RUNNING'){
	            		e.cellHtml='<img src="${ctxPath}/styles/icons/icon-running.png" />运行中';
	            	}else if(value=='SUCCESS_END'){
	            		e.cellHtml='<img src="${ctxPath}/styles/icons/icon-success-end.png" />成功结束';
	            	}else if(value=='DISCARD_END'){
	            		e.cellHtml='作废';
	            	}
	            }
	        });
	        
	        grid.on('update',function(){
	        	_LoadUserInfo();
	        });
	        
	        //结束流程实例
	        function onEndProcess(){
	        	var sel=grid.getSelected();
	        	if(!sel) return;
	        	_SubmitJson({
	        		url:__rootPath+'/bpm/core/bpmInst/endProcess.do',
	        		method:'POST',
	        		data:{
	        			instId:sel.instId
	        		},
	        		success:function(text){
	        			grid.load();
	        		}
	        	});
	        }
	        
	        //挂起流程实例
	        function onPendingProcess(){
	        	var sel=grid.getSelected();
	        	if(!sel) return;
	        	_SubmitJson({
	        		url:__rootPath+'/bpm/core/bpmInst/pendingProcess.do',
	        		method:'POST',
	        		data:{
	        			instId:sel.instId
	        		},
	        		success:function(text){
	        			grid.load();
	        		}
	        	});
	        }
	        
	        //恢复流程实例
	        function onActivateProcess(){
	        	var sel=grid.getSelected();
	        	if(!sel) return;
	        	_SubmitJson({
	        		url:__rootPath+'/bpm/core/bpmInst/activateProcess.do',
	        		method:'POST',
	        		data:{
	        			instId:sel.instId
	        		},
	        		success:function(text){
	        			grid.load();
	        		}
	        	});
	        }
	        
	        
	    	function treeNodeClick(e){
		   		var node=e.node;
		   		grid.setUrl(__rootPath+'/bpm/core/bpmInst/listByTreeId.do?treeId='+node.treeId);
		   		grid.load();
		   	}
        </script>
	
</body>
</html>