<%-- 
    Document   : [OaMeetRoom]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>会议室列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
    	<div class="mini-toolbar" >
            <table style="width:100%;">
                <tr>
                    <th  >
                        <a class="mini-button" iconCls="icon-detail" plain="true" onclick="detail()">明细</a>
                        <a class="mini-button" iconCls="icon-create" plain="true"  onclick="add()">增加</a>
                        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
                        <a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>						
						<a class="mini-button" iconCls="icon-search" onclick="searchForm(this)">查询</a>
						<a class="mini-button" iconCls="icon-clear" onclick="onClearList(this)">清空</a>
                    </th>
                   
                </tr>
	                <tr>
	                    <td>
		                    <form  class="text-distance">
	        					<div>
	        						<span class="long">会议室名称：</span><input class="mini-textbox" name="Q_name_S_LK"/>
	        						<span class="long">会议室地址：</span><input class="mini-textbox" name="Q_location_S_LK"/>
	        					</div>
							</form>
	                    </td>
	                </tr>
            </table>
        </div>
		<div class="mini-fit rx-grid-fit">
			<div id="datagrid1" class="mini-datagrid"  style="width:100%; height:100%;" allowResize="false" url="${ctxPath}/oa/res/oaMeetRoom/listData.do" idField="roomId" multiSelect="true" showColumnsMenu="true"
				sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowCellEdit="true" allowCellSelect="true" 
				allowAlternating="true" pagerButtons="#pagerButtons">
				<div property="columns" >
					<div type="checkcolumn" width="20"></div>
					<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
					<div field="name" width="120" headerAlign="center" allowSort="true">会议室名称
					<input property="editor" class="mini-textbox" style="width:100%;"/>
					</div>
					<div field="location" width="120" headerAlign="center" allowSort="true">会议室地址
					<input property="editor" class="mini-textbox" style="width:100%;"/>
					</div>
					<div field="descp" width="120" headerAlign="center" allowSort="true">会议室描述
					<input property="editor" class="mini-textbox" style="width:100%;"/>
					</div>
				</div>
			</div>
		</div>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.oa.res.entity.OaMeetRoom" winHeight="450"
		winWidth="700" entityTitle="会议室管理" baseUrl="oa/res/oaMeetRoom" />
	<script type="text/javascript">
			mini.parse();
			//编辑
			var grid = mini.get("datagrid1");
			grid.load();
        	//行功能按钮
	        function onActionRenderer(e) {
	        	var grid = e.sender;
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-save" title="保存" onclick="saveRow()"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            return s;
	        }
	        
			function saveRow() {
				//表格检验
				grid.validate();
				if (!grid.isValid()) {
					return;
				}
				var data = grid.getChanges();
				var json = mini.encode(data);
				$.ajax({
					url : "${ctxPath}/oa/res/oaMeetRoom/saveMenu.do",
					type : "POST",
					data : {
						data : json
					},
					success : function(text) {
						var result = mini.decode(text);
						//进行提示
						_ShowTips({
							msg : '成功保存会议室!'
						});
						if (result.data && result.data.roomId) {
							row.roomId = result.data.roomId;
						}
						grid.acceptRecord(row);
					},
					error : function(jqXHR, textStatus, errorThrown) {
						alert(jqXHR.responseText);
					}
				});
				
				
			}
        </script>

</body>
</html>