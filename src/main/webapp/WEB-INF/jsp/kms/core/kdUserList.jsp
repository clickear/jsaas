<%-- 
    Document   : [KdUser]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[KdUser]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<redxun:toolbar entityName="com.redxun.kms.core.entity.KdUser" />

	<div class="mini-fit rx-grid-fit" style="height: 100%;">
		<div 
			id="datagrid1" 
			class="mini-datagrid" 
			style="width: 100%; height: 100%;" 
			allowResize="false" 
			url="${ctxPath}/kms/core/kdUser/listData.do" 
			idField="kuserId" 
			multiSelect="true" 
			showColumnsMenu="true" 
			sizeList="[5,10,20,50,100,200,500]" 
			pageSize="20" 
			allowAlternating="true" 
			pagerButtons="#pagerButtons"
		>
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div field="sn" width="20" headerAlign="center" allowSort="true" align="center">序号</div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="fullname" width="100" headerAlign="center" allowSort="true" align="center">姓名</div>
				<div field="userType" width="100" headerAlign="center" allowSort="true" align="center">用户类型</div>
				<div field="point" width="50" headerAlign="center" allowSort="true" align="center">积分</div>
				<div field="grade" width="50" headerAlign="center" allowSort="true" align="center">等级</div>
				<div field="knSysTree.name" width="120" headerAlign="center" allowSort="true" align="center">知识领域</div>
				<div field="reqSysTree.name" width="120" headerAlign="center" allowSort="true" align="center">爱问领域</div>
			</div>
		</div>
	</div>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.kms.core.entity.KdUser" winHeight="450" winWidth="700" entityTitle="专家" baseUrl="kms/core/kdUser" />
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
        	
	    	grid.on("drawcell", function(e) {
	    		field = e.field, value = e.value;
	    		var s;
	    		if (field == 'sex') {
	    			if (value == "female") {
	    				s = "女";
	    			} else {
	    				s = "男";
	    			}
	    			e.cellHtml = s;
	    		}
	    		
	    		if (field == 'userType') {
	    			if (value == "DOMAIN") {
	    				s = "领域专家";
	    			} else {
	    				s = "专家个人";
	    			}
	    			e.cellHtml = s;
	    		}
	    		
	    		if (field == 'replyType') {
	    			if (value == "DOMAIN_EXPERT") {
	    				s = "领域专家";
	    			} else if(value == 'EXPERT'){
	    				s = "专家";
	    			}else{
	    				s="所有人";
	    			}
	    			e.cellHtml = s;
	    		}
	    	});
        </script>

</body>
</html>