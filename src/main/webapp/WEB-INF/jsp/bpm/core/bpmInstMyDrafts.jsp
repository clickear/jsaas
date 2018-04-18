<%-- 
    Document   : 流程实例列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>我的草稿</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
    <div class="titleBar mini-toolbar" >
		<div class="searchBox">
			<form id="searchForm" class="search-form" style="display: none;">						
				<ul>
					<li>
						<span class="text">发起时间:</span>
	                	<input name="Q_createTime_D_GE" class="mini-datepicker" format="yyyy-MM-dd" />
	                	<span class="text">至</span>
	                	<input name="Q_createTime_D_LE" class="mini-datepicker" format="yyyy-MM-dd" />
					</li>
					<li>
						<span class="text">单号：</span><input class="mini-textbox" name="Q_billNo_S_LK"   />
					</li>
					<li>
						<span class="text">标题：</span><input class="mini-textbox" name="Q_subject_S_LK"   />
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
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/bpm/core/bpmInst/listDrafts.do" idField="instId" multiSelect="true" showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="indexcolumn" width="20">序号</div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="billNo" width="80" headerAlign="center" allowSort="true">单号</div>
				<div field="subject" width="200" headerAlign="center" allowSort="true">标题</div>
				<div field="createTime" width="60" headerAlign="center" allowSort="true">创建时间</div>
			</div>
		</div>
	</div>
<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.core.entity.BpmInst" winHeight="450" winWidth="700" entityTitle="流程实例"
		baseUrl="bpm/core/bpmInst" />
	<script type="text/javascript">
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pk = record.pkId;
	            
	            var s = '<span class="icon-detail" title="明细" onclick="informRow(\'' + pk + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delDraftRow(\'' + pk + '\')"></span>'
	            		+ ' <span class="icon-start" title="启动流程" onclick="startRow(\'' + record._uid + '\')"></span>';
	            return s;
	        }
        			
        	function informRow(pkId) {
		    	var obj=getWindowSize();
		        _OpenWindow({
		        	url: __rootPath +"/bpm/core/bpmInst/inform.do?instId=" + pkId,
		            title: "流程实例明细", width: obj.width, height: obj.height
		        });
		    }
        	
        	
			function delDraftRow(pkId) {
	            if (!confirm("确定删除选中记录？")) return;
	            _SubmitJson({
	            	url:__rootPath +"/bpm/core/bpmInst/delDraft.do",
	            	method:'POST',
	            	data:{ids: pkId},
	            	 success: function(text) {
	                    grid.load();
	                }
	             });
	        }

        	
	        function startRow(uid){
        		var row=grid.getRowByUID(uid);
        		_OpenWindow({
        			title:row.subject+'-流程启动',
        			url:__rootPath+'/bpm/core/bpmInst/start.do?instId='+row.instId,
        			max:true,
        			height:400,
        			width:800,
        			ondestroy:function(action){
        				if(action=='ok'){
        					grid.load();
        				}
        			}
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
	            	e.cellHtml= '<a href="javascript:startRow(\'' + record._uid + '\')">'+record.subject+'</a>';
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
	        
        </script>
	
</body>
</html>