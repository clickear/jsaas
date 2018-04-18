<%-- 
    Document   : 流程抄送实例明细列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>抄送给我的流程实例</title>
<%@include file="/commons/list.jsp"%>
<style type="text/css">
	.label{
		font-weight: normal;
		font-size:12px;
		padding-left:6px;
		padding-right:6px;
	}
	.mini-layout-border>#center{
 		background: transparent;
	}
</style>
</head>
<body>
	
    
    <div id="layout1" class="mini-layout" style="width:100%;height:100%;" >
    	  <div 
    	   	title="抄送信息分类" 
    	   	region="west" 
    	   	width="180"  
    	   	showSplitIcon="true"
	    	showCollapseButton="false"
	    	showProxy="false"
   	   	 >
    	  		<div id="toolbar1" class="mini-toolbar" >
                    <a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refreshSysTree()">刷新</a>          
		        </div>
		        <div class="mini-fit">
			         <ul 
			         	id="systree" 
			         	class="mini-tree" 
			         	url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_BPM_SOLUTION" 
			         	style="width:100%;height:100%;" 
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
    	  </div>
    	  
    	  <div 
    	  	title="抄送信息" 
    	  	region="center" 
    	  	showHeader="false" 
    	  	showCollapseButton="false"
   	  	>
    	  <div class="titleBar mini-toolbar" >
	         <ul>
				<li>
					<a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
				</li>
				<li class="clearfix"></li>
			</ul>
			<div class="searchBox">
				<form id="searchForm" class="search-form" style="display: none;">						
					<ul>
						<li>
							<span class="label">创建时间：</span>
			        		<input name="Q_createtime_D_GE" class="mini-datepicker" format="yyyy-MM-dd" style="width:100px"/>
							<span class="label">至</span>
							<input name="Q_createtime_D_LE" format="yyyy-MM-dd" class="mini-datepicker" style="width:100px"/>
						</li>
						<li>
							<span class="label">事项：</span><input name="Q_subject_S_LK" class="mini-textbox" style="width:160px"/>
						</li>
						<li>
							<span class="label">审批环节：</span><input name="Q_nodeName_S_LK" class="mini-textbox" style="width:120px"/>
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
		    
		    <div class="mini-fit" style="height:100%;">
				<div 
					id="datagrid1" 
					class="mini-datagrid" 
					style="width:100%; height:100%;" 
					allowResize="false"
					url="${ctxPath}/bpm/core/bpmInstCc/toMeJson.do" 
					idField="ccId"
					multiSelect="true" 
					showColumnsMenu="true"
					sizeList="[5,10,20,50,100,200,500]" 
					pageSize="20" 
					allowAlternating="true" 
				>
					<div property="columns">
						<div type="checkcolumn" width="50"></div>
						<div field="subject" sortField="cc.SUBJECT_" width="160" headerAlign="center" allowSort="true">抄送标题</div>
						<div field="nodeName" width="100" headerAlign="center" >审批环节</div>
						<div field="fromUser" width="100" headerAlign="center" >抄送人</div>
						<div field="isRead" width="100" headerAlign="center" >已读</div>
						<div field="createTime" sortField="cc.CREATE_TIME_" dateFormat="yyyy-MM-dd HH:mm:ss" width="100" headerAlign="center" allowSort="true">抄送时间</div>
					</div>
				</div>
			</div>
    	  </div>
    </div>
	
	
	
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.bpm.core.entity.BpmInstCc" winHeight="450"
		winWidth="700" entityTitle="流程实例明细" baseUrl="bpm/core/bpmInstCc" />	
	<script type="text/javascript">

		var form=new mini.Form("#searchForm");
		function onSearch(){
			grid.load(form.getData(true));
		}
		
		function onClear(){
			form.reset();
			grid.load();
		}
      
       	
       	function instDetail(instId,cpId,isRead){
       		if(isRead=='NO'){
       			_SubmitJson({
                	url:__rootPath+"/bpm/core/bpmInstCp/cpRead.do?cpId="+cpId,
                	method:'GET',
                	showMsg:false,
                	success:function(result){
                		openWindow(instId,true);
                	}
                });
       		}
       		else{
       			openWindow(instId);
       		}
       	}
       	
       	function openWindow(instId,reload){
       		_OpenWindow({
       			title:'流程明细',
       			height:600,
       			width:800,
       			url:__rootPath+'/bpm/core/bpmInst/inform.do?instId='+instId,
       			ondestroy: function(action){
       				if(reload){
       					grid.load();
       				}
       			}
       		});
       	}
       	
        grid.on("drawcell", function (e) {
            var record = e.record,
	        field = e.field,
	        value = e.value;
            var instId = record.instId;
            var cpId = record.cpId;
            var isRead = record.isRead;
            
            if(field=='subject'){
            	e.cellHtml= '<a href="javascript:instDetail(\'' + instId + '\',\''+cpId+'\',\''+isRead+'\')">'+record.subject+'</a>';
            }
            else if(field=='isRead'){
            	var html=isRead=="YES"?"<span style='color:green'>已读</span>":"<span style='color:orange'>未读</span>";
            	e.cellHtml= html;
            }
            
        });
        
	   	//按分类树查找数据字典
	   	function treeNodeClick(e){
	   		var node=e.node;
	   		grid.setUrl(__rootPath+'/bpm/core/bpmInstCc/toMeJson.do?treeId='+node.treeId);
	   		grid.load();
	   	}
        	
	   	function refreshSysTree(){
	   		var systree=mini.get("systree");
	   		systree.load();
	   	}
       </script>
</body>
</html>