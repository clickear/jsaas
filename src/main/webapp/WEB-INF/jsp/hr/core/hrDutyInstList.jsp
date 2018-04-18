<%-- 
    Document   : [HrDutyInst]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[HrDutyInst]列表管理</title>
<%@include file="/commons/list.jsp"%>
<style type="text/css">
	.mini-panel-border{
		border: none;
	}
</style>
</head>
<body>
	<%-- <redxun:toolbar entityName="com.redxun.hr.core.entity.HrDutyInst"> --%>
		<!-- <a class="mini-button" iconCls="icon-add" plain="true" onclick="ff">ff</a> -->
	<%-- </redxun:toolbar>--%>
	<!-- <div class="mini-toolbar"> 
		<span class="text">姓名：</span><input id="name" class="mini-textbox"  /> 
		<span class="text">部门：</span><input id="dep" class="mini-textbox"  />  
		<span class="text">月份：</span><input id="date" class="mini-monthpicker" allowInput="false"  />
		<a class="mini-button" iconCls="icon-add" plain="true" onclick="getInst">查询排班</a>
		<div>
			提示：<span style="color:red;">休</span>=<span style="color:red;">休息日</span>，
				 <span style="color:red;">节</span>=<span style="color:red;">节假日</span>，
				 <span style="color:green;">假</span>=<span style="color:green;">请假</span>，
				 <span style="color:brown;">加</span>=<span style="color:brown;">加班</span>，
				 <span style="color:blue;">调</span>=<span style="color:blue;">调休</span>
		</div>
	</div>
	 -->
	<div class="titleBar mini-toolbar" >
         <ul>
			<li>
				<span class="text">姓名：</span><input id="name" class="mini-textbox"  />
			</li>
			<li>
				<span class="text">部门：</span><input id="dep" class="mini-textbox"  />
			</li>
			<li>
				<span class="text">月份：</span><input id="date" class="mini-monthpicker" allowInput="false"  />
			</li>
			<li>
				<a class="mini-button" iconCls="icon-add" plain="true" onclick="getInst">查询排班</a>
			</li>
			
			<li class="clearfix"></li>
		</ul>
		<div class="searchBox" style="padding-left: 12px;">
			提示：<span style="color:red;">休</span>=<span style="color:red;">休息日</span>，
				 <span style="color:red;">节</span>=<span style="color:red;">节假日</span>，
				 <span style="color:green;">假</span>=<span style="color:green;">请假</span>，
				 <span style="color:brown;">加</span>=<span style="color:brown;">加班</span>，
				 <span style="color:blue;">调</span>=<span style="color:blue;">调休</span>
		</div>
     </div>
	
	
	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" allowAlternating="true"  showPager="false">
			
		</div>
	</div>
	

	 <script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
<%--  <redxun:gridScript gridId="datagrid1" entityName="com.redxun.hr.core.entity.HrDutyInst" winHeight="450" winWidth="700" entityTitle="[HrDutyInst]" baseUrl="/hr/core/hrDutyInst" />  --%>
	<script type="text/javascript">
		mini.parse();
		var grid=mini.get("datagrid1");
		
		var flag=false;
		 
		
		function getInst(){
			var date=mini.get("date").getValue();
			var name=mini.get("name").getValue();
			var dep=mini.get("dep").getValue();
			var dateValue=mini.formatDate ( mini.parseDate(date), "yyyy-MM" );
			_SubmitJson({
				url:__rootPath+"/hr/core/hrDutyInst/getDutyInst.do",
				method:'POST',
				data:{
					name:name,
					date:dateValue,
					dep:dep
				},
				showMsg:false,
				success:function(result){
					var data=result.data;
					var header=data.tableHead;
					setColumns(header);
					//grid.setData();
					var content=data.tableContent;
					var showData=[];
					for(var i=0;i<content.length;i++){
						var obj={};
						obj.depId=content[i].depId;
						obj.depName=content[i].depName;
						obj.userId=content[i].userId;
						obj.userName=content[i].userName;
						obj.type=content[i].type;
						for(var j=0;j<content[i].hrDutyInsts.length;j++){
							obj['sectionId'+(j+1)]=content[i].hrDutyInsts[j].sectionId;
							obj['sectionName'+(j+1)]=content[i].hrDutyInsts[j].sectionName;
							obj['sectionShortName'+(j+1)]=content[i].hrDutyInsts[j].sectionShortName;
							obj['dutyInstId'+(j+1)]=content[i].hrDutyInsts[j].dutyInstId;
						}
						showData.push(obj);
					}
					if(!flag){
						bindGridDrawCell();
					}
					grid.setData(showData);
				}
			});
		}
		
		function setColumns(header){
			var columns=[];
			var indexColumn={};
			indexColumn.type="indexcolumn";
			columns.push(indexColumn);
			for(var i=0;i<header.length;i++){
				var column={};
				column.field=header[i].field;
				if(i==0)
					column.width=80;
				else if(i==1)
					column.width=100;
				else
					column.width=70;
				column.headerAlign="center";
				column.align="center";
				column.header=header[i].header;
				column.displayField=header[i].displayName;
				columns.push(column);
			}
			grid.set({
		        columns:columns
		    });
			/*grid.setFrozenStartColumn(0);
			grid.setFrozenEndColumn(2);*/
			grid.frozenColumns ( 0, 2 );
		}
		
		function bindGridDrawCell(){
			flag=true;
			grid.on("drawcell", function (e) {
		        field = e.field,
		        value = e.value;
	            var s;
	            if(typeof(field)!="undefined"){
					if(field.indexOf("dutyInst")>=0){  
						if(e.cellHtml.indexOf('休')>-1){
							s=e.cellHtml.replace('休','<span style="color:red;">休</span>');
							e.cellHtml= s;
						}
						if(e.cellHtml.indexOf("节")>-1){
							s=e.cellHtml.replace('节','<span style="color:red;">节</span>');
							e.cellHtml= s;
						}
						if(e.cellHtml.indexOf("加")>-1){
							s=e.cellHtml.replace('加','<span style="color:brown;">加</span>');
							e.cellHtml= s;
						}
						if(e.cellHtml.indexOf("假")>-1){
							s=e.cellHtml.replace('假','<span style="color:green;">假</span>');
							e.cellHtml= s;
						}
						if(e.cellHtml.indexOf("调")>-1){
								s=e.cellHtml.replace('调','<span style="color:blue;">调</span>');
								e.cellHtml= s;
						}
						s='<span style="cursor:pointer;" onclick="dutyInstDetail(\'' + e.value + '\')">'+e.cellHtml+'</span>';
						e.cellHtml= s;
						
					}
	            }
	        }); 
		}
		
		function dutyInstDetail(instId){
			_OpenWindow({
				url:__rootPath+"/hr/core/hrDutyInst/edit.do?pkId="+instId,
				width:'700',
				height:'450',
				title:'班次详情',
				ondestroy:function(action){
					if(action=='ok'){
						getInst();
					}
				}
			});
		}
		
		$(function(){
			mini.get("date").setValue(new Date());
		});
		
		
	</script>
</body>
</html>