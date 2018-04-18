<%-- 
    Document   : [BpmFormTemplate]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
    <head>
        <title>表单模板列表管理</title>
        <%@include file="/commons/list.jsp" %>
    </head>
    <body>
        
        <!-- <div class="mini-toolbar" >
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;" >
                        <a class="mini-button" iconCls="icon-detail"  onclick="detail()">明细</a>
                        <a class="mini-button" iconCls="icon-edit"  onclick="edit()">编辑</a>
                        <a class="mini-button" iconCls="icon-init"  onclick="init()">初始化</a>
                        <a class="mini-button" iconCls="icon-search" onclick="searchForm(this)">查询</a>
	                    <a class="mini-button" iconCls="icon-clear" onclick="clearSearch()">清空</a>
                    </td>
                    
                </tr>
	                <tr>
	                    <td colspan="2" >
	                    	<form class="text-distance">
	                    		<div>
	                    			<span class="text">别名:</span><input name="Q_ALIAS__S_LK" class="mini-textbox"   />  
	                    		</div>                   	
	                    	</form>                  	
                    </td>
	                </tr>
            </table>
        </div> -->
    <div class="titleBar mini-toolbar">
		<ul>
			<li>
				<a class="mini-button" iconCls="icon-detail"  onclick="detail()">明细</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-edit"  onclick="edit()">编辑</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-init"  onclick="init()">初始化</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-remove"  onclick="remove()">删除</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-search" onclick="searchForm(this)">查询</a>
			</li>
			<li class="clearfix"></li>
		</ul>
		<div class="searchBox">
			<form id="searchForm" class="text-distance" style="display: none;">						
				<ul>
					<li>
						<span class="text">别名：</span>
						<input name="Q_ALIAS__S_LK" class="mini-textbox"   /> 
					</li>
					<li>
						<div class="searchBtnBox">
							<a class="mini-button _search" onclick="searchForm(this)" >搜索</a>
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
        <div class="mini-fit rx-grid-fit" style="height:100%;">
            <div 
            	id="datagrid1" 
            	class="mini-datagrid" 
            	style="width:100%;height:100%;" 
            	allowResize="false"
                url="${ctxPath}/bpm/form/bpmFormTemplate/listData.do"  
                idField="id" 
                multiSelect="true" 
                showColumnsMenu="true"
                sizeList="[5,10,20,50,100,200,500]" 
                pageSize="20" 
                allowAlternating="true" 
                pagerButtons="#pagerButtons"
            >
                <div property="columns">
                    <div type="checkcolumn" width="20"></div>
                    <div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>        
	                <div field="name" width="120" headerAlign="center">模版名称</div>
					<div field="alias" width="120" headerAlign="center">别名</div>
					<div field="refAlias" width="120" headerAlign="center">引用模版</div>
					<div field="type" width="120" headerAlign="center" renderer="renderType">模版类型</div>
					<div field="init" width="120" headerAlign="center" renderer="renderInit">系统默认</div>
				</div>
            </div>
        </div>
        
        <script type="text/javascript">
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>'
	                    if(record.init==0){
	                    	+ ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>'	
	                    }
	            return s;
	        }
        	
        	function renderInit(e){
        		var record = e.record;
        		if(record.init==0){
        			return "自定义模版";
        		}
        		else{
        			return "系统";
        		}
        	}
        	
        	function renderType(e){
        		var record = e.record;
        		if(record.type=="mobile"){
        			return "手机";
        		}
        		if(record.type=="print"){
        			return "打印表单";
        		}
        	}
        	
        	
        	function init(){
        		_SubmitJson({
		   			url:__rootPath+'/bpm/form/bpmFormTemplate/init.do',
		   			success:function(text){
		   			 grid.load();
		   			}
		   		});
        	}
        </script>
        
        <redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.form.entity.BpmFormTemplate" 
        	winHeight="512" winWidth="710"
          	entityTitle="表单模板" baseUrl="bpm/form/bpmFormTemplate"/>
    </body>
</html>