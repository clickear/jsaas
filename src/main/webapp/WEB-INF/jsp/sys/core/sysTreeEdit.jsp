<%-- 
    Document   : 系统树节点编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>系统树节点编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${sysTree.treeId}" hideRecordNav="true"/>
	<div id="p1" class="form-outer shadowBox" style="width: 90%;margin-bottom: 0;">
		<form id="form1" method="post">
			<div>
				<input id="pkId" name="treeId" class="mini-hidden" value="${sysTree.treeId}" />
				<input name="parentId" value="${sysTree.parentId}" class="mini-hidden"/>
				<input name="catKey" value="${sysTree.catKey}" class="mini-hidden"  />
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
					<caption>系统树节点基本信息</caption>
					<tr>
						<th>
							<span class="starBox">
								名　　称 
								<span class="star">*</span>
							</span>
						</th>
						<td colspan="3">
							<input 
								name="name" 
								value="${sysTree.name}" 
								onblur="getPinyin" 
								class="mini-textbox" 
								vtype="maxLength:128" 
								required="true" 
								emptyText="请输入名称" 
								style="width:80%"
							/>
						</td>
					</tr>
					<tr>
						<th>
							<span class="starBox">
								标  识  键 
								<span class="star">*</span>
							</span>
						</th>
						<td>
							<input 
								id="key" 
								name="key" 
								value="${sysTree.key}" 
								class="mini-textbox" 
								vtype="maxLength:64" 
								required="true" 
								emptyText="标识键" 
								style="width:90%"
							/>
						</td>
						<th>编　　码 </th>
						<td>
							<input 
								name="code" 
								value="${sysTree.code}" 
								class="mini-textbox" 
								vtype="maxLength:64" 
								emptyText="编码" 
								style="width:90%"
							/></td>
					</tr>
					<tr>
						<th>
							<span class="starBox">
								序　　号 
								<span class="star">*</span>
							</span>
						</th>
						<td colspan="3">
							<input 
								name="sn" 
								value="${sysTree.sn}" 
								class="mini-spinner" 
								vtype="maxLength:10" 
								required="true" 
								minValue="1" 
								maxValue="10000" 
								emptyText="请输入序号" 
								style="width:120px"
							/>
						</td>
					</tr>
					<tr>
						<th>描　　述</th>
						<td colspan="3"><textarea name="descp" class="mini-textarea" vtype="maxLength:512" style="width:96%">${sysTree.descp}</textarea></td>
					</tr>
					<tr <c:if test="${sysTree.catKey!='CAT_DIM'}">style="display:none"</c:if>>
						<th>数据项展示类型 </th>
						<td colspan="3">
							<div 
								id="dataShowType" 
								name="dataShowType" 
								class="mini-radiobuttonlist"  
								repeatDirection="horizontal"
	                            textField="text" 
	                            valueField="id" 
	                            value="${sysTree.dataShowType}" 
	                            required="true" 
	                            style="width:80%"
	                            data="[{id:'FLAT',text:'平铺'},{id:'TREE',text:'树'}]"
                            ></div>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="sys/core/sysTree" />
	<script type="text/javascript">
		function getPinyin(e){
			var val=e.sender.getValue();
			var key=mini.get('key');
			if(key.getValue().trim()==''){
				_SubmitJson({
					url:__rootPath+'/pub/base/baseService/getPinyin.do',
					method:'POST',
					showMsg:false,
					showProcessTips:false,
					data:{
						words:val,
						isCap:'true',
						isHead:'true'
					},
					success:function(result){
						key.setValue(result.data);
					}
				});
			}
		}
		addBody();
	</script>
</body>
</html>