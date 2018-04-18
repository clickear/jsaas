<%-- 
    Document   : [HrDutySystem]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[HrDutySystem]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${hrDutySystem.systemId}" />
	<div id="p1" class="form-outer shadowBox">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="systemId" class="mini-hidden"
					value="${hrDutySystem.systemId}" />
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<caption>班制基本信息</caption>

					<tr>
						<th>名　称 <span class="star">*</span>
						</th>
						<td><input name="name" value="${hrDutySystem.name}"
							class="mini-textbox" vtype="maxLength:100" style="width: 90%"
							required="true" emptyText="请输入名称" />

						</td>
					</tr>

					<tr>
						<th>是否缺省<span class="star">*</span>
						</th>
						<td><%-- <input name="isDefault" value="${hrDutySystem.isDefault}"
							class="mini-textbox" vtype="maxLength:8" style="width: 90%"
							required="true" emptyText="请输入是否缺省  1＝缺省 0＝非缺省" /> --%>
							<div name="isDefault" required="true" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="horizontal" textField="text" valueField="id" value="${hrDutySystem.isDefault}" data="[{'id':'1','text':'是'},{'id':'0','text':'否'}]" ></div>
						</td>
					</tr>

					<tr>
						<th>分　 类</th>
						<td><%-- <input name="type" value="${hrDutySystem.type}"
							class="mini-textbox" vtype="maxLength:20" style="width: 90%" /> --%>
							<div id="type" name="type" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="horizontal" textField="text" valueField="id" value="${hrDutySystem.type}" data="[{'id':'WEEKLY','text':'标准一周'},{'id':'PERIODIC','text':'周期'}]" onValuechanged="typeHandler(e)"></div>
						</td>
					</tr>

					<tr id="work" style="">
						<th>标准作息班次 </th>
						<td><%-- <input name="workSection"
							value="${hrDutySystem.workSection}" class="mini-textbox"
							vtype="maxLength:64" style="width: 90%" /> --%>
							<%-- <div class="mini-toolbar" style="padding:2px;text-align:left;border-bottom: none;">
							    <table style="width:100%;">
							        <tr>
								        <td style="width:100%;">
								            <a class="mini-button" iconCls="icon-add" plain="true" onclick="addRow('workSection')">添加</a>
								            <a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeRow('workSection')">删除</a>
								            <span class="separator"></span>
								            <a class="mini-button" iconCls="icon-up" plain="true" onclick="upRow('workSection')">向上</a>
								            <a class="mini-button" iconCls="icon-down" plain="true" onclick="downRow('workSection')">向下</a>
								        </td>
							        </tr>
							    </table>
							</div>
							<div id="workSection" class="mini-datagrid" style="width:70%;min-height:100px;" height="auto" showPager="false"
								allowCellEdit="true" allowCellSelect="true">
							    <div property="columns">
							        <div type="indexcolumn" width="30"></div>                   
							        <div field="name" width="120" headerAlign="center" displayField="sectionName">班次名称
							        	<input property="editor" class="mini-combobox" style="width:100%;" minWidth="120" url="${ctxPath}/hr/core/hrDutySection/getAllSection.do" valueField="sectionId" textField="sectionName"  />
							        </div>    
							    </div>
							</div> --%>
							<input name="workSection" value="${hrDutySystem.workSection}" class="mini-combobox"  style="width: 90%" url="${ctxPath}/hr/core/hrDutySection/getAllSection.do" valueField="sectionId" textField="sectionName" />
						</td>
					</tr>
					
					<tr id="periodic" style="display:none;">
						<th> 周期作息时间</th>
						<td>
						<div class="mini-toolbar" style="padding:2px;text-align:left;border-bottom: none;">
							    <table style="width:100%;">
							        <tr>
								        <td style="width:100%;">
								            <a class="mini-button" iconCls="icon-add" plain="true" onclick="addRow('periodicSection')">添加</a>
								            <a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeRow('periodicSection')">删除</a>
								            <span class="separator"></span>
								        </td>
							        </tr>
							    </table>
							</div>
							<div id="periodicSection" class="mini-datagrid" style="width:70%;min-height:100px;" height="auto" showPager="false"
								allowCellEdit="true" allowCellSelect="true">
							    <div property="columns">
							        <div type="indexcolumn"></div>                   
							        <div field="sectionId" width="120" headerAlign="center" displayField="sectionName">班次名称
							        	<input property="editor" class="mini-combobox" style="width:100%;" minWidth="120" url="${ctxPath}/hr/core/hrDutySection/getAllSectionAndRestSection.do" valueField="sectionId" textField="sectionName"  />
							        </div>    
							    </div>
							</div>
						</td>
					</tr>

					<tr>
						<th>休息日加班班次 </th>
						<td><%-- <input name="wkRestSection"
							value="${hrDutySystem.wkRestSection}" class="mini-textbox"
							vtype="maxLength:64" style="width: 90%" /> --%>
							<%-- <div class="mini-toolbar" style="padding:2px;text-align:left;border-bottom: none;">
							    <table style="width:100%;">
							        <tr>
								        <td style="width:100%;">
								            <a class="mini-button" iconCls="icon-add" plain="true" onclick="addRow('restDayWorkSection')">添加</a>
								            <a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeRow('restDayWorkSection')">删除</a>
								            <span class="separator"></span>
								            <a class="mini-button" iconCls="icon-up" plain="true" onclick="upRow('restDayWorkSection')">向上</a>
								            <a class="mini-button" iconCls="icon-down" plain="true" onclick="downRow('restDayWorkSection')">向下</a>
								        </td>
							        </tr>
							    </table>
							</div>
							<div id="restDayWorkSection" class="mini-datagrid" style="width:70%;min-height:100px;" height="auto" showPager="false"
								allowCellEdit="true" allowCellSelect="true">
							    <div property="columns">
							        <div type="indexcolumn"></div>                   
							        <div field="name" width="120" headerAlign="center" displayField="sectionName">班次名称
							        	<input property="editor" class="mini-combobox" style="width:100%;" minWidth="120" url="${ctxPath}/hr/core/hrDutySection/getAllSection.do" valueField="sectionId" textField="sectionName"  />
							        </div>    
							    </div>
							</div> --%>
							<input name="wkRestSection" value="${hrDutySystem.wkRestSection}" class="mini-combobox"  style="width: 90%" url="${ctxPath}/hr/core/hrDutySection/getAllSection.do" valueField="sectionId" textField="sectionName" />
						</td>
					</tr>

					<tr id="restDay" style="">
						<th>休息日 </th>
						<td><%-- <input name="restSection"
							value="${hrDutySystem.restSection}" class="mini-textbox"
							vtype="maxLength:100" style="width: 90%" /> --%>
							<div name="restSection" class="mini-checkboxlist" repeatItems="1" textField="text" valueField="id" value="${hrDutySystem.restSection}" data="[{'id':'1','text':'星期一'},{'id':'2','text':'星期二'},{'id':'3','text':'星期三'},{'id':'4','text':'星期四'},{'id':'5','text':'星期五'},{'id':'6','text':'星期六'},{'id':'0','text':'星期天'}]"></div>
						</td>
					</tr>
					
					
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="hr/core/hrDutySystem"
		entityName="com.redxun.hr.core.entity.HrDutySystem" />
		
		<script type="text/javascript">
		addBody();
		$(function(){
			var type="${hrDutySystem.type}";
			var systemId="${hrDutySystem.systemId}";
			if(type=='')
				mini.get('type').setValue('WEEKLY');
			else if(type=='PERIODIC'){
				$("#restDay").css('display','none');
				$("#work").css('display','none');
				$("#periodic").css('display','');
				var grid=mini.get("periodicSection");
				grid.setUrl("${ctxPath}/hr/core/hrSystemSection/getBySystemId.do");
				grid.load({sId:systemId});
			}
			
			
				
		});
		
		function typeHandler(e){
			var value=e.value;
			if(value=='PERIODIC'){
				$("#restDay").css('display','none');
				$("#work").css('display','none');
				$("#periodic").css('display','');
			}else if(value=='WEEKLY'){
				$("#restDay").css('display','');
				$("#work").css('display','');
				$("#periodic").css('display','none');
			}
		}
		
		//添加行
		function addRow(gridId){
			var grid= mini.get(gridId);
			grid.addRow({});
		}
		
		function removeRow(gridId){
			var grid= mini.get(gridId);
			var selecteds=grid.getSelecteds();
			if(selecteds.length>0 && confirm('确定要删除？')){
				grid.removeRows(selecteds);
			}
		}
		
		function upRow(gridId) {
			var grid= mini.get(gridId);
            var row = grid.getSelected();
            if (row) {
                var index = grid.indexOf(row);
                grid.moveRow(row, index - 1);
            }
        }
        function downRow(gridId) {
        	var grid= mini.get(gridId);
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
            var type=mini.get("type").getValue();
            var workSection;
            if(type=='PERIODIC')
            	workSection=mini.get("periodicSection").getData();
            else
            	workSection="";
          /*   alert(mini.encode(workSection));
            return; */
            var objData=_GetFormJson("form1");
            //var gridData=mini.get("section").getData();
            _SubmitJson({
            	url:__rootPath+"/hr/core/hrDutySystem/saveDutySystem.do",
            	method:'POST',
            	data:{
            		workData:workSection==''?workSection:mini.encode(workSection),
            		objData:mini.encode(objData)
            	},
            	success:function(result){
            		CloseWindow('ok');
            	}
            });
        }
		</script>
</body>
</html>