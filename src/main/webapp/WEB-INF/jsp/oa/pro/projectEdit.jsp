<%-- 
    Document   : 项目编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : 陈茂昌
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>项目编辑</title>
<%@include file="/commons/edit.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/form-design/ueditor-ext.js"></script>
<script type="text/javascript">
var data2=[{ id: 'PROJECT', text: '项目' }, { id: 'PRODUCT', text: '产品'}, { id: 'OTHER', text: '其他'}];

</script>
<style type="text/css">
	.mini-tabs-bodys{
		background: transparent;
	}
	.mini-panel-border,
	.mini-tabs-bodys,
	#content>.edui-editor{
		border: none;
	}
	
	.newBody #toolbar1,
	.newBody .mini-tabs-space{
		background: #fff;
	}
	.newBody #toolbarBody a{
		border: 1px solid #ececec;
	}
	
</style>

</head>
<body>
        
    <rx:toolbar toolbarId="toolbar1" pkId="${project.projectId}"  hideRemove="true" hideRecordNav="true">
	<div class="self-toolbar">
	 <a class="mini-button" iconCls="icon-reload" plain="true" onclick="location.reload();">刷新</a>
	</div>
	</rx:toolbar>
        	<!-- tab -->
	<div style="width:100%;height: 660px;">
 		<div id="tabs1" class="mini-tabs" style="width: 100%;height: 100%;" >
			<div title="项目信息" id="tabPro" > 
				<div class="shadowBox" style="margin-top: 20px;">
					<form id="form1" method="post" class="form-outer">
						<div class="form-inner">
							<input id="pkId" name="projectId" class="mini-hidden" value="${project.projectId}" />
							 <input name="treeId" value="${project.treeId}" class="mini-hidden" vtype="maxLength:64" style="width: 90%" />
							<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
								<caption>项目基本信息</caption>
								<tr>
									<th>
										<span class="starBox">
											名　　称 <span class="star">*</span>
										</span>
									</th>
									<td>
										<input 
											name="name" 
											value="${project.name}" 
											class="mini-textbox" 
											vtype="maxLength:100" 
											style="width: 90%" 
											required="true"
											emptyText="请输入名称" 
										/>
									</td>
									<th>标签名称</th>
									<td>
										<input 
											name="tag" 
											value="${project.tag}" 
											class="mini-textbox" 
											vtype="maxLength:50" 
											style="width: 90%" 
										/>
									</td>
								</tr>
								<tr>
								    <th>
								    	<span class="starBox">
								    		编　　号 <span class="star">*</span>
							    		</span>
									</th>
									<td>
										<input 
											name="proNo" 
											value="${project.proNo}" 
											class="mini-textbox" 
											vtype="maxLength:50" 
											style="width: 90%" 
											required="true"
											emptyText="请输入编号" 
										/></td>
									
									<th>
										<span class="starBox">
											负  责  人 <span class="star">*</span>
										</span>
									</th>
									<td >
										<input 
											id="reporId" 
											class="mini-textboxlist" 
											name="reporId"   
											style="width:200px;" 
											value="${project.reporId}" 
											text="${repor}"  
											allowInput="false" 
											required="true"  
										/>
										<a class="mini-button" iconCls="icon-grant" plain="true" onclick="onSelectUser('reporId')">选择</a>
									</td>
								</tr>
			
								<tr>	
									<th>预计费用</th>
									<td>
										<input 
											name="costs" 
											value="${project.costs}" 
											class="mini-spinner" 
											minValue="0" 
											maxValue="99999999999" 
											style="width: 90%" 
										/></td>
			
								<th>类　　型</th>
									<td>
										<div 
											name="type" 
											id="type" 
											class="mini-radiobuttonlist" 
					    					textField="text" 
					    					valueField="id" 
					    					value="${project.type}"   
					    					data="data2" 
				    					></div>
			   					 	</td>
								</tr>
			
								<tr >
									<th>描　　述</th>
									<td colspan="5" style="padding: 0 !important;"><ui:UEditor height="250"  name="descp"  id="content">${project.descp}</ui:UEditor></td>
								</tr>
								
							</table>
						</div>
					</form>
				</div>
			</div>
			
			
 			<div title="评论内容" id="tabMat" url="${ctxPath}/oa/pro/proWorkMat/list.do?projectId=${project.projectId}"></div>
			<div title="参与人员" id="tabAtt" url="${ctxPath}/oa/pro/proAttend/list.do?projectId=${project.projectId}"></div>
			<div title="版本控制" id="tabVer" url="${ctxPath}/oa/pro/proVers/list.do?projectId=${project.projectId}"></div>
			<div title="项目需求" id="tabAct" url="${ctxPath}/oa/pro/reqMgr/list.do?projectId=${project.projectId}"></div>
		</div>
	</div>
	
	<rx:formScript formId="form1" baseUrl="oa/pro/project"  entityName="com.redxun.oa.pro.entity.Project"/>
	<script type="text/javascript">
	addBody();
	mini.parse();
	var tab=mini.get("tabs1");
	if(!${empty isadd}){//在添加新项目的时候隐藏其他tab防止空数据提示json error
	tab.removeAll(tab.getTab(0));
	}
	//选负责人
	function onSelectUser(where){
		_TenantUserDlg(tenantId,true,function(user){
			var reporId=mini.get(where);
			
			reporId.setValue(user.userId);
			reporId.setText(user.fullname);
			
		});
	}
	
	$(function(){
		$("#reporId1").dblclick(function(){
			onSelectUser('reporId1');
		});
		
	})
	
	//提示功能
	var tip = new mini.ToolTip();
tip.set({
    target: document,
    selector: '[title]'
});

//重写了saveData方法
function selfSaveData(issave){
	var form = new mini.Form("form1");
	form.validate();
    if (!form.isValid()) {//验证表格
        return; }
    var formData=$("#form1").serializeArray();
    //加上租用Id
    if(tenantId!=''){
        formData.push({
        	name:'tenantId',
        	value:tenantId });
    }
    _SubmitJson({
    	url:"${ctxPath}/oa/pro/project/save.do?",
    	method:'POST',
    	data:formData,
    	success:function(result){
    		//如果存在自定义的函数，则回调
    		if(isExitsFunction('successCallback')){
    			successCallback.call(this,result);
    			return;	
    		}
    		var pkId=mini.get("pkId").getValue();
    		//为更新
    		if (pkId!=''){
    			CloseWindow('ok');
    			return;
    		}
    		CloseWindow('ok');} });
}
	addBody();
	</script>
</body>
</html>