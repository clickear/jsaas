<%-- 
    Document   : 系统模块列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>系统模块列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<redxun:toolbar entityName="com.redxun.sys.core.entity.SysModule" excludeButtons="popupAddMenu,popupAttachMenu,popupSettingMenu,remove">
		<div class="self-toolbar">
			<li>
				<a class="mini-button" iconCls="icon-detail" plain="true" onclick="regModule()">注册管理</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-remove" plain="true" onclick="delModules">删除</a>
			</li>
		</div>
	</redxun:toolbar>

	<div class="mini-fit rx-grid-fit" style="height: 100px;">
		<div 
			id="datagrid1" 
			class="mini-datagrid" 
			style="width: 100%; height: 100%;" 
			allowResize="false" 
			url="${ctxPath}/sys/core/sysModule/listData.do" 
			idField="moduleId" 
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
				<div field="title" width="150" headerAlign="center" allowSort="true">模块标题</div>
				<div field="tableName" width="100" headerAlign="center" allowSort="true">表名</div>
				<div field="entityName" width="150" headerAlign="center" allowSort="true">实体名</div>
				<div field="isDefault" width="80" headerAlign="center" allowSort="true" renderer="onIsDefaultRenderer">是否系统默认</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
        	//编辑
	        function onActionRenderer(e) {
	            var record = e.record;
	            var uid = record.pkId;
			    var uId=record._uid;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + uid + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + uid + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delModule(\'' + uId + '\')"></span>';
	            return s;
	        }
        	
        	//注册业务模块
	        function submitRegModule(action, value) {
                if (action != "ok") return;
                if(value=='' && value==null) return;
                $.ajax({
	                url: "${ctxPath}/sys/core/sysModule/regModule.do",
	                type: 'post',
	                data: {entityName:value},
	                cache: false,
	                success: function(result) {
	                	if(result.success){
		                	_ShowTips({
		                		msg:'成功保存信息!'
		                	});
		                	grid.load();
	                	}else{
	                		_ShowErr({
		                		content:result.message,
		                	});	
	                	}
	                },
	                error: function(jqXHR) {
	                	_ShowErr({
	                		content:jqXHR.responseText,
	                	});
	                }
	            });
            }
        	
	        function regModule(){
	        	//弹出输入框，允许用户输入注册的实体类名
	        	 mini.prompt("请输入实体类名，\n格式如com.redxun.sys.core.entity.SalesOrder：", "请输入实体类名",submitRegModule);
	        }
        	
	        /*删除注册实体*/
	        function delModule(uid){
	        	 if (!confirm("确定删除选中记录？")) return;

	        	var row=grid.getRowByUID(uid);
	        	var isDefault=row.isDefault;
	        	if(isDefault=='YES'){
	        		alert("选中记录不能是系统默认的记录！");
	        		return;
	        	}
              
              
               _SubmitJson({
               	url:__rootPath+"/sys/core/sysModule/del.do",
               	method:'POST',
               	data:{ids: row.moduleId},
               	 success: function(text) {
                       grid.load({pageIndex : grid.getPageIndex(),pageSize : grid.getPageSize()});
                   }
                });
	        }
	        
	        function delModules(){
	            if (!confirm("确定删除选中记录？")) return;
	            
	            var rows = grid.getSelecteds();
	            if (rows.length <= 0) {
	            	alert("请选中一条记录");
	            	return;
	            }
	            
	            var ids = [];
	            var flag=true;
	            for (var i = 0, l = rows.length; i < l; i++) {
	                var r = rows[i];
	                if(r.isDefault=="YES"){
	                	alert("选中记录不能是系统默认的记录！");
	                	flag=false;
	                	break;
	                }
	                ids.push(r.pkId);
	            }
	            if(flag){
		            _SubmitJson({
		            	url:__rootPath+"/sys/core/sysModule/del.do",
		            	method:'POST',
		            	data:{ids: ids.join(',')},
		            	 success: function(text) {
		            		 grid.load({pageIndex : grid.getPageIndex(),pageSize : grid.getPageSize()});
		                }
		            });
	            }
	        }
	        
	  	  function onIsDefaultRenderer(e) {
	            var record = e.record;
	            var isDefault = record.isDefault;
	             var arr = [{'key' : 'YES', 'value' : '是','css' : 'green'}, 
	    			        {'key' : 'NO','value' : '否','css' : 'red'} ];
	    			return $.formatItemValue(arr,isDefault);
	        }
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.core.entity.SysModule" 
	winHeight="450" winWidth="700" entityTitle="系统模块" baseUrl="sys/core/sysModule" />
</body>
</html>