<%-- 
    Document   : 系统流水号编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>系统流水号编辑</title>
<%@include file="/commons/edit.jsp"%>
<style type="text/css">
	.mini-panel-border .mini-grid-row:last-of-type .mini-grid-cell{
		border-bottom: none;
	}
</style>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${sysSeqId.seqId}" />
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
			
				<input id="pkId" name="seqId" class="mini-hidden" value="${sysSeqId.seqId}" />
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
					<caption>系统流水号基本信息</caption>
					<tr>
						<th>
							<span class="starBox">
								名　　称 <span class="star">*</span> 
							</span>
						</th>
						<td><input name="name" value="${sysSeqId.name}" class="mini-textbox" vtype="maxLength:80" style="width: 90%" required="true" emptyText="请输入名称" /></td>
						<th>别　　名 </th>
						<td><input name="alias" value="${sysSeqId.alias}" required="true" class="mini-textbox" vtype="maxLength:50" style="width: 90%" /></td>
					</tr>
					
					<tr>
						<th>规　　则 </th>
						<td colspan="3">
							<div class="mini-toolbar " >
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
								id="ruleGrid" 
								class="mini-datagrid"   
								height="auto" 
								showPager="false"
								allowCellEdit="true" 
								allowCellSelect="true" 
								oncellendedit="OnCellEndEdit"
								allowAlternating="true"
							>
							    <div property="columns">
							                 
							        <div field="type" width="100" headerAlign="center">规则类型
							         	<input property="editor" class="mini-combobox" style="width:100%" textField="text" valueField="id" allowInput="true"
							         		data="[{id:'{yyyy}',text:'年-yyyy(如2016)'},{id:'{yy}',text:'年-yy(如16)'},{id:'{yyyyMMdd}',text:'年月日-yyyyMMdd(如20160102)'},{id:'{yyyyMM}',text:'年月-yyyyMM(如201601)'},{id:'{MM}',text:'月-MM(如03)'},{id:'{mm}',text:'月-mm(如3)'},{id:'{DD}',text:'日DD-(如03)'},{id:'{dd}',text:'日dd-(如3)'},{id:'{NO}',text:'序号-NO(如0003)'},{id:'{no}',text:'序号-no(如3)'}]" required="true"  />  
							        </div>    
							        
							        <div field="splitor" width="100" headerAlign="center">分割符
							        	<input property="editor" class="mini-combobox" allowInput="true" data="[{id:'_',text:'_'},{id:'-',text:'-'},{id:'#',text:'#'},{id:'*',text:'*'}]" style="width:100%;" minWidth="120" />
							        </div>   
							    </div>
							</div>
							<div style="padding-top:10px;float:left;width:100%;"><span style="padding-buttom:4px;">流水号规则:</span>
								&nbsp;<input class="mini-textbox" value="${sysSeqId.rule}" id="rule" name="rule" style="width:80%" />
							</div>
						</td>
					</tr>
					
					<tr>
						<th>生成方式 </th>
						<td colspan="3">
							<div class="mini-radiobuttonlist" name="genType" value="${sysSeqId.genType}" data="[{id:'DAY',text:'每天'},{id:'WEEK',text:'每周'},{id:'MONTH',text:'每月'},{id:'YEAR',text:'每年'},{id:'AUTO',text:'自动增长'}]"/>
						</td>
					</tr>
					<tr>
						<th>流水号长度 </th>
						<td><input name="len" value="${sysSeqId.len}" class="mini-spinner" minValue="2" maxValue="32" vtype="maxLength:10" /></td>
						<th>
							<span class="starBox">
								系统缺省<span class="star">*</span> 
							</span>
						</th>
						<td>
							<ui:radioBoolean name="isDefault" value="${sysSeqId.isDefault}" required="true"/>
						</td>
					</tr>
					<tr>
						<th>初  始  值 </th>
						<td><input name="initVal" value="${sysSeqId.initVal}" class="mini-spinner" minValue="1" maxValue="32" vtype="maxLength:10" /></td>
						<th>步　　长 </th>
						<td><input name="step" value="${sysSeqId.step}"  class="mini-spinner" minValue="1" maxValue="100" vtype="maxLength:5" /></td>
					</tr>
					<tr>
						<th>备　　注 </th>
						<td colspan="3"><textarea name="memo" class="mini-textarea" vtype="maxLength:512" style="width: 95%">${sysSeqId.memo}</textarea></td>
					</tr>
				</table>
		</form>
	</div>
	<div id="ruleConfig" style="display:none">${sysSeqId.ruleConf}</div>
	<rx:formScript formId="form1" baseUrl="sys/core/sysSeqId" entityName="com.redxun.sys.core.entity.SysSeqId" />
	<script type="text/javascript">
	addBody();
	var grid=mini.get('ruleGrid');
	
	$(function(){
		var data=$('#ruleConfig').html();
		if(data!=''){
			grid.setData(mini.decode(data));
		}
	});
	//添加行
	function addRow(){
		grid.addRow({});
		OnCellEndEdit();
	}
	
	function removeRow(){
		var selecteds=grid.getSelecteds();
		if(selecteds.length>0 && confirm('确定要删除？')){
			grid.removeRows(selecteds);
		}
		OnCellEndEdit();
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
    
    function handleFormData(formData){
    	var data=grid.getData();
    	if(data.length==0){
    		alert('请添加规则！');
    		return;
    	}
    	
    	formData.push({
    		name:'ruleConf',
    		value:mini.encode(data)
    	});
    	
    	return{
    		isValid:true,
    		formData:formData
    	};
    }
    
    
    function OnCellEndEdit(){
    	var rule='';
    	var data=grid.getData();
    	for(var i=0;i<data.length;i++){
    		
    		if(data[i].type==undefined || data[i].type==''){
    			continue;
    		}
    		
    		rule=rule+data[i].type;
    		
    		if(i<data.length-1){
    			if(data[i].splitor!=undefined){
    				rule=rule+data[i].splitor
    			}
    		}
    	}
    	mini.get('rule').setValue(rule);
    }
    
	</script>
</body>
</html>