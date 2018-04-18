<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[手机表单]列表管理</title>
        <%@include file="/commons/list.jsp" %>
        <script src="${ctxPath}/scripts/share/dialog.js" type="text/javascript"></script>
        <script src="${ctxPath}/scripts/sys/bo/BoUtil.js" type="text/javascript"></script>
    </head>
    <body>
   	<div class="titleBar mini-toolbar">
		<ul>
			<li>
				<a class="mini-button" iconCls="icon-detail"  onclick="detail()">明细</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-create" onclick="addForm()">增加</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-remove"  onclick="remove()">删除</a>
			</li>
			<li class="clearfix"></li>
		</ul>
		<div class="searchBox">
			<form id="searchForm" class="text-distance" style="display: none;">						
				<ul>
					<li>
						<span class="text">别名</span>
						<input name="Q_a.ALIAS__S_LK"  class="mini-textbox" />
					</li>
					<li>
						<span class="text">业务模型：</span>
						<input 
							id="btnBoDefId" 
							name="Q_a.BO_DEF_ID__S_EQ" 
							class="mini-buttonedit" 
							text="" 
	    					showClose="true" 
	    					oncloseclick="clearButtonEdit" 
	    					value="" 
	    					onbuttonclick="onSelectBo"
    					/>
					</li>
					<li class="searchBtnBox">
						<a class="mini-button _search" onclick="searchForm(this)" >搜索</a>
						<a class="mini-button _reset" onclick="onClearList(this)">还原</a>
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
            <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false"
                 url="${ctxPath}/bpm/form/bpmMobileForm/listData.do"  idField="id" multiSelect="true" showColumnsMenu="true"
                 sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
                <div property="columns">
                    <div type="checkcolumn" width="20"></div>
                    <div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>        
	                <div field="boName" width="120" headerAlign="center" >关联BO</div>
					<div field="name" width="120" headerAlign="center" >手机表单名称</div>
					<div field="alias" width="120" headerAlign="center" >手机表单标识</div>
				</div>						 																 																							                  </div>
            </div>
        </div>
        
        <script type="text/javascript">
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\',true)"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            return s;
	        }
        	
        	function addForm(){
        		_OpenWindow({
    	    		url: __rootPath + "/bpm/form/bpmMobileForm/add.do",
    	            title: "新增表单", width: "600", height: "400",
    	            ondestroy: function(action) {
    	            	if(action=="ok"){
    	            		grid.reload();	
    	            	}
    	            }
    	    	});
        	}
        	
        	
        </script>
        
        <redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.form.entity.BpmMobileForm" 
        	winHeight="450" winWidth="700"
          	entityTitle="[手机表单]" baseUrl="bpm/form/bpmMobileForm"/>
    </body>
</html>