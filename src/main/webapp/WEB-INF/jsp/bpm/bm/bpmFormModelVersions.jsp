<%-- 
    Document   : 业务模型管理历史版本
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>业务模型管理历史版本</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
		<div class="mini-fit" style="border:0;">
	        <div class="mini-fit" style="height: 100px;">
				<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/bpm/bm/bpmFormModel/listByMainModelId.do?fmId=${param['fmId']}" idField="fmId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
					<div property="columns">
						<div type="checkcolumn" width="20"></div>
						<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
						<div field="name" width="140" headerAlign="center" allowSort="true">名称</div>
						<div field="key" width="140" headerAlign="center" allowSort="true">标识键</div>
						<div field="isMain" width="80" headerAlign="center" allowSort="true">是否为主版本</div>
						<div field="version" width="80" headerAlign="center" allowSort="true">版本号</div>
						<div field="status" width="60" headerAlign="center" allowSort="true">状态</div>
						<div field="isPublic" width="60" headerAlign="center" allowSort="true">是否公共</div>
					</div>
				</div>
			</div>
        </div>
	
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.bm.entity.BpmFormModel" winHeight="450" winWidth="700" entityTitle="业务模型管理" baseUrl="bpm/bm/bpmFormModel" />
	
	<script type="text/javascript">
	//编辑
    function onActionRenderer(e) {
        var record = e.record;
        var uid = record.pkId;
        var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + uid + '\')"></span>'
                + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + uid + '\')"></span>'
                + ' <span class="icon-main" title="设置为主版本" onclick="setMainRow(\'' + uid + '\')"></span>'
                + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
        return s;
    }
	
	function setMainRow(pkId){
		_SubmitJson({
			url:__rootPath+'/bpm/bm/bpmFormModel/setMain.do',
			data:{
				fmId:pkId
			},
			success:function(text){
				grid.load();
			}
		});
	}
	
	</script>
</body>
</html>

