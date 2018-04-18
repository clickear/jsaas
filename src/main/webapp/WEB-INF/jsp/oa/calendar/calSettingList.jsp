<%-- 
    Document   : [CalSetting]列表页
    Created on : 2017-1-7, 0:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[CalSetting]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

<!-- <div id="toolbar1" class="mini-toolbar" style="padding:2px;">
    <table style="width:100%;">
        <tr>
	        <th style="width:100%;">
	            <a class="mini-button" iconCls="icon-addfolder" plain="true" enabled="true" onclick="newSetting()">增加</a>
	        </th>
        </tr>
    </table>
</div> -->

<div class="titleBar mini-toolbar" >
         <ul>
			<li>
				<a class="mini-button" iconCls="icon-addfolder" plain="true" enabled="true" onclick="newSetting()">增加</a>
			</li>
			<li class="clearfix"></li>
		</ul>
     </div>


	<div class="mini-fit rx-grid-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/oa/calendar/calSetting/tenantList.do" idField="settingId"  showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true">
			<div property="columns">
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer"
					cellStyle="padding:0;">操作</div>
				<div field="calName" width="120" headerAlign="center" allowSort="true">日历名称</div>
				<div field="isCommon" width="120" headerAlign="center" allowSort="true" renderer="onIsCommonRenderer">是否默认</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
			mini.parse();
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-calendar" title="日历设定" onclick="openCalendar(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>'
	                    + '<span class="icon-leader" title="分配" onclick="grant(\'' + pkId + '\')"></span>'
	                    +' <span class="icon-debug" title="测试" onclick="debugRow(\'' + pkId + '\')"></span>'
	                    +' <span class="icon-center" title="设置为默认" onclick="defaultCenter(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            return s;
	        }
        	
        	function openCalendar(pkId){
        		_OpenWindow({
            		url: '${ctxPath}/oa/calendar/calGrant/view.do?settingId='+pkId,
                    title: "日历",
                    width:800,
        			height:600,
                    max:true,
                    ondestroy: function(action) {
                    }
            	});	
        	}
        	
        	var grid=mini.get("datagrid1");
        	grid.on("drawcell", function (e) {
	            var record = e.record,
		        field = e.field,
		        value = e.value;
	         
	            if (record.isCommon=='YES') {
	                e.rowCls = "myrow";
	            }
	        });
        	
        	  function onIsCommonRenderer(e) {
  	            var record = e.record;
  	            var isCommon = record.isCommon;
  	             var arr = [{'key' : 'YES', 'value' : '是','css' : 'green'}, 
  	    			        {'key' : 'NO','value' : '否','css' : 'red'} ];
  	    			return $.formatItemValue(arr,isCommon);
  	        }
        	
        	function  grant(pkId){
        		_OpenWindow({
            		url: '${ctxPath}/oa/calendar/calGrant/list.do?settingId='+pkId,
                    title: "日历",
                    width:800,
        			height:600,
                    ondestroy: function(action) {
                    }
            	});	
        	}
        	
        	function newSetting(){
        		_OpenWindow({
           		 url: "${ctxPath}/oa/calendar/calSetting/edit.do",
                   title: "编辑设定",
                   width: 600, height: 300,
                   ondestroy: function(action) {
                       if (action == 'ok') {
                           grid.reload();
                       }
                   }
           	});
        	}
        	
        	function debugRow(pkId){
        		_OpenWindow({
              		 url: "${ctxPath}/oa/calendar/calSetting/testWindow.do?settingId="+pkId,
                      title: "计算测试",
                      width: 300, height: 400,
                      ondestroy: function(action) {
                          
                      }
              	});
        	}
        	
        	function defaultCenter(pkId){
        		mini.confirm("是否将其设置为默认日历", "确定？",
        	            function (action) {
        	                if (action == "ok") {
        	                	$.ajax({
			    	                type: "Post",
			    	                url : '${ctxPath}/oa/calendar/calSetting/setDefault.do',
			    	                data: {"settingId":pkId}
			    	            });
        	                } 
        	                location.reload();
        	            }
        	        );
        	}
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.calendar.entity.CalSetting" winHeight="450" winWidth="700"
		entityTitle="工作日历" baseUrl="oa/calendar/calSetting" />
</body>
</html>