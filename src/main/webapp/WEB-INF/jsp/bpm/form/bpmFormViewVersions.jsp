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
</head>
<body>	
	<div class="mini-fit form-outer" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/bpm/form/bpmFormView/listForVersions.do?key=${param['key']}" 
		idField="viewId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" >
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="40" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="name" width="120" headerAlign="center" allowSort="true">名称</div>
				<div field="key" width="120" headerAlign="center" allowSort="true">标识键</div>
				<div field="type" width="100" headerAlign="center" allowSort="true" renderer="onTypeRenderer">类型</div>
				<div field="isMain" width="50" headerAlign="center" allowSort="true" renderer="onIsMainRenderer">主版本</div>
				<div field="version" width="100" headerAlign="center" allowSort="true">版本号</div>
				<div field="status" width="100" headerAlign="center" allowSort="true" renderer="onStatusRenderer">状态</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	//编辑
    function onActionRenderer(e) {
        var record = e.record;
        var uid = record.pkId;
        var s =  ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + uid + '\',true)"></span>'
                + ' <span class="icon-main" title="设置为主版本" onclick="setMainRow(\'' + uid + '\')"></span>';
                if(record.isMain!="YES"){
                	s+= ' <span class="icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
                }
                
        return s;
    }
	
    function onIsMainRenderer(e) {
        var record = e.record;
        var isMain = record.isMain;
         var arr = [{'key' : 'YES', 'value' : '是','css' : 'blue'}, 
			        {'key' : 'NO','value' : '否','css' : 'gray'} ];
			return $.formatItemValue(arr,isMain);
    }
	
    function onTypeRenderer(e) {
        var record = e.record;
        var type = record.type;
         var arr = [{'key' : 'ONLINE-DESIGN', 'value' : '在线表单','css' : 'green'}, 
			        {'key' : 'SEL-DEV','value' : '自开发表单','css' : 'gray'} ];
			return $.formatItemValue(arr,type);
    }
	  
	  function onStatusRenderer(e) {
        var record = e.record;
        var status = record.status;
         var arr = [{'key' : 'DEPLOYED', 'value' : '发布','css' : 'green'}, 
			        {'key' : 'INIT','value' : '草稿','css' : 'blue'} ];
			return $.formatItemValue(arr,status);
    }
	
	function setMainRow(pkId){
		_SubmitJson({
			url:__rootPath+'/bpm/form/bpmFormView/setMain.do',
			data:{
				viewId:pkId
			},
			success:function(text){
				grid.load();
			}
		});
	}
	
	//删除行
    function _delRow(pkId) {
        if (!confirm("确定删除选中记录？")) return;
       
        _SubmitJson({
        	url:__rootPath+'/bpm/form/bpmFormView/delByVersion.do',
        	method:'POST',
        	data:{id: pkId},
        	 success: function(text) {
                grid.load();
            }
         });
    }
	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.form.entity.BpmFormView" winHeight="450" winWidth="700" entityTitle="业务表单视图" baseUrl="bpm/form/bpmFormView" />
</body>
</html>