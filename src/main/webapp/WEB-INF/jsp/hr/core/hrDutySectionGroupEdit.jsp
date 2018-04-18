<%-- 
    Document   : [HrDutySection]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[HrDutySection]编辑</title>
<%@include file="/commons/edit.jsp"%>

<style type="text/css">
	.mini-panel-body{
		border-left: 1px solid #ececec;
		border-right: 1px solid #ececec;
		box-sizing: border-box !important;
	}
	.mini-grid-row{
		background: #fff;
	}
	.mini-grid-row-alt{
		background: #f7f7f7;
	}
</style>

</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${hrDutySection.sectionId}" />
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="sectionId" class="mini-hidden" value="${hrDutySection.sectionId}" />
				<input name="isGroup" class="mini-hidden" value="YES" />
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
					<caption>组合班次基本信息</caption>

					<tr>
						<th>
							<span class="starBox">
								班次名称 
								<span class="star">*</span>
							</span>  
						</th>
						<td>
							<input 
								name="sectionName"
								value="${hrDutySection.sectionName}" 
								class="mini-textbox"
								vtype="maxLength:8" 
								style="width: 90%" 
								required="true"
								emptyText="请输入班次名称" 
							/>
						</td>
					</tr>
					
					<tr>
						<th>
							<span class="starBox">
								班次简称 
								<span class="star">*</span>
							</span> 
						</th>
						<td>
							<input 
								name="sectionShortName"
								value="${hrDutySection.sectionShortName}" 
								class="mini-textbox"
								vtype="maxLength:2" 
								style="width: 90%" 
								required="true"
								emptyText="请输入班次简称" 
							/>
						</td>
					</tr>

					<tr>
						<th>班次详情  </th>
						<td>
						<div class="mini-toolbar mini-toolbar-bottom" style="padding:2px;text-align:left;border-bottom: none;">
							    <table style="width:100%;">
							        <tr>
								        <td style="width:100%;">
								            <a class="mini-button" iconCls="icon-add" plain="true" onclick="addRow">添加</a>
								            <a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeRow">删除</a>
								            <span class="separator"></span>
								            <a class="mini-button" iconCls="icon-up" plain="true" onclick="upRow">向上</a>
								            <a class="mini-button" iconCls="icon-down" plain="true" onclick="downRow">向下</a>
								        </td>
							        </tr>
							    </table>
							</div>
							<div 
								id="section" 
								class="mini-datagrid" 
								style="width:100%;" 
								height="auto" 
								showPager="false"
								allowCellEdit="true" 
								allowCellSelect="true"
								allowAlternating="true"
							>
							    <div property="columns">
							        <div type="indexcolumn">序号</div>                   
							        <div field="sectionId" width="120" headerAlign="center" displayField="sectionName">
							        	班次名称
							        	<input 
							        		property="editor" 
							        		class="mini-combobox" 
							        		style="width:100%;" 
							        		minWidth="120" 
							        		url="${ctxPath}/hr/core/hrDutySection/getSingleSection.do" 
							        		valueField="sectionId" 
							        		textField="sectionName"  
						        		/>
							        </div>    
							    </div>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="hr/core/hrDutySection"
		entityName="com.redxun.hr.core.entity.HrDutySection" />
		
	<script type="text/javascript">
		var grid=mini.get('section');
		
		$(function(){
			var pkId="${hrDutySection.sectionId}";
			if(pkId!=''){
				var data=mini.decode('${hrDutySection.groupList}');
				var groupList=data['sections'];
				grid.setData(groupList);
			}
		});
		
		//添加行
		function addRow(){
			grid.addRow({});
		}
		
		function removeRow(){
			var selecteds=grid.getSelecteds();
			if(selecteds.length>0 && confirm('确定要删除？')){
				grid.removeRows(selecteds);
			}
		}
		
		function upRow() {
            var row = grid.getSelected();
            if (row) {
                var index = grid.indexOf(row);
                grid.moveRow(row, index - 1);
            }
        }
        function downRow() {
            var row = grid.getSelected();
            if (row) {
                var index = grid.indexOf(row);
                grid.moveRow(row, index + 2);
            }
        }
        
        function selfSaveData(){
        	form.validate();
            if (!form.isValid()) {
                return;
            }
            var objData=_GetFormJson("form1");
            var gridData=mini.get("section").getData();
            _SubmitJson({
            	url:__rootPath+"/hr/core/hrDutySection/groupSave.do",
            	method:'POST',
            	data:{
            		gridData:mini.encode(gridData),
            		objData:mini.encode(objData)
            	},
            	success:function(result){
            		CloseWindow('ok');
            	}
            });
        }
        
        addBody();
	</script>	
</body>
</html>