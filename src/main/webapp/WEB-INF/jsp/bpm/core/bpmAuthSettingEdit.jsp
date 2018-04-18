<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>权限编辑编辑</title>
<%@include file="/commons/edit.jsp"%>
<script src="${ctxPath}/scripts/share/dialog.js" type="text/javascript"></script>


</head>
<body>
	<div id="toolbar1" class="mini-toolbar topBtn" >
		<a class="mini-button" iconCls="icon-save" plain="true" onclick="onOk">保存</a>	               
<!-- 		<a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a>	     -->
	</div>

	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
				<input id="pkId" name="id" class="mini-hidden" value="" />
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<caption>流程授权配置</caption>

					<tr>
						<th>授权名称 </th>
						<td>
							<input name="name" value="" required="true"
							class="mini-textbox" vtype="maxLength:50" style="width: 70%" />
							启用:<input name="enable" checked="true"
							class="mini-checkbox"   trueValue="yes" falseValue="no" value="yes" style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th><c:if test="${grantType=='bpmAssortment'}">流程分类</c:if>
						<c:if test="${grantType!='bpmAssortment'}">流程方案 </c:if>
						</th>
						<td  class="colums-padding">
							<div class="mini-toolbar">
						         <a class="mini-button" id="addBtn" iconCls="icon-add"  onclick="selSolution()" plain="true" enabled="true">选择</a>
						         <a class="mini-button" id="delBtn" iconCls="icon-remove" onclick="removeSol()" plain="true" enabled="true">删除</a>						         
						         
							</div>
							<div class="colums-left">
							
							<div id="solGrid" class="mini-datagrid" style="width: 100%; height: 200px" allowResize="false" showPager="false"
								 allowAlternating="true" allowCellWrap="true" multiSelect="true">
								<div property="columns">
									<div field="name" width="100" headerAlign="center" ><c:if test="${grantType=='actDefinition'}">解决方案名称</c:if><c:if test="${grantType=='bpmAssortment'}">分类名称</c:if></div>
									<div field="key" width="50" headerAlign="center" >标识键</div>
									<div name="action" width="200" cellCls="tdWrap" renderer="permissonRender" headerAlign="center" >权限</div>
									</div>									
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<th>流程人员 </th>
						<td>
							<table>
								<tr>
									<td width="50%">
										<div>方案管理:
											<div id="chkSolution" name="chkSolution" class="mini-checkbox" readOnly="false" text="使用同一配置" ></div>
											<a class="mini-button" id="addBtn" iconCls="icon-add"  onclick="setUser('def')" plain="true" enabled="true">选择</a>
											<a class="mini-button"   iconCls="icon-remove"  onclick="removeMember('def')" plain="true" enabled="true">删除</a>
										</div>
										<div  class="colums-left">
											<div id="defGrid" class="mini-datagrid" style="width: 100%; height: 150px" allowResize="false" showPager="false"
												 allowAlternating="true">
												<div property="columns" >
													<div field="name" width="140" headerAlign="center">类型</div>
													<div field="names" width="200" headerAlign="center">配置</div>
												</div>
											</div>
										</div>
									</td>
									<td width="50%">
										<div>发起人员:
											<a class="mini-button" id="addBtn" iconCls="icon-add"  onclick="setUser('start')" plain="true" enabled="true">选择</a>
											<a class="mini-button"   iconCls="icon-remove"  onclick="removeMember('start')" plain="true" enabled="true">删除</a>
										</div>
										<div class="colums-left">
											<div id="startGrid" class="mini-datagrid" style="width: 100%; height: 150px" allowResize="false" showPager="false"
											 allowAlternating="true">
											<div property="columns">
												<div field="name" width="140" headerAlign="center" >类型</div>
												<div field="names" width="100" headerAlign="center">配置</div>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td width="50%">
										<div>实例人员:
											<a class="mini-button" id="addBtn" iconCls="icon-add"  onclick="setUser('inst')" plain="true" enabled="true">选择</a>
											<a class="mini-button"   iconCls="icon-remove"  onclick="removeMember('inst')" plain="true" enabled="true">删除</a>
										</div>
										<div class="colums-left">
											<div id="instGrid" class="mini-datagrid" style="width: 100%; height: 150px" allowResize="false" showPager="false"
												 allowAlternating="true">
												<div property="columns">
													<div field="name" width="140" headerAlign="center" >类型</div>
													<div field="names" width="100" headerAlign="center">配置</div>
												</div>
											</div>
										</div>
									</td>
									<td width="50%">
										<div>任务人员:
											<a class="mini-button" id="addBtn" iconCls="icon-add"  onclick="setUser('task')" plain="true" enabled="true">选择</a>
											<a class="mini-button" iconCls="icon-remove"  onclick="removeMember('task')" plain="true" enabled="true">删除</a>
										</div>
										<div class="colums-left">
											<div id="taskGrid" class="mini-datagrid" style="width: 100%; height: 150px" allowResize="false" showPager="false"
											allowAlternating="true">
											<div property="columns">
												<div field="name" width="140" headerAlign="center">类型</div>
												<div field="names" width="100" headerAlign="center">配置</div>
											</div>
											</div>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
		</form>
	</div>
	<script type="text/javascript">
	addBody();
	var grantType="${grantType}";
	var nvJson={inst:"实例",def:"定义",task:"任务"};
	var rightJson=${rightJson};
	var pkId="${param.pkId}";
	mini.parse();
	
	var form = new mini.Form("#form1");            
	
	
	var solGrid=mini.get("solGrid");
	
	var defGrid=mini.get("defGrid");
	var startGrid=mini.get("startGrid");
	var instGrid=mini.get("instGrid");
	var taskGrid=mini.get("taskGrid");
	
	$(function(){
		init();
		initCheckAll();
	});
	
	function init(){
		
		if(!pkId) return;
		var url=__rootPath + "/bpm/core/bpmAuthSetting/getSetting.do?settingId="+pkId;
		$.get(url,function(data){
			form.setData(data.authSetting);
			var aryDef=[];
			for(var i=0;i<data.defs.length;i++){
				var def=data.defs[i];
				def.rightJson=mini.decode(def.rightJson);
				aryDef.push(def);
			}
			solGrid.setData(aryDef);
			
			var rights=data.rights;
			for(var key in rights){
				eval(key +"Grid.setData(rights[key])");
			}
			
		})
		
	}
	
	function initCheckAll(){
		var s="";
		for(var key in rightJson){
			var data=rightJson[key];
			s+="<span style='margin-left:10px;'><b>"+nvJson[key]+"</b>:</span>";
			
			s+="<span  key='"+key+"'>";
        	for(var i=0;i<data.length;i++){
        		var tmpObj=data[i];
        		s+='<input type="checkbox" id="'+key +"_" + tmpObj.key  +'"  value="'+key +"_" + tmpObj.key  +'" onclick="checkAll(this)" /><label for="'+key +"_" + tmpObj.key+'">' + tmpObj.name +"</label>";
        	}
        	s+="</span>";
		}
		$("#spanPermission").html(s);
	}
	
	function checkAll(obj){
		//console.info(obj.value + "," + obj.checked);
		 $("[name='"+obj.value+"']").each(function(){
			 $(this).click();
			 /* this.checked=obj.checked;控制全部 */
		 })
		
	}
	
	
	function removeSol(){
		var rows=solGrid.getSelecteds();
		solGrid.removeRows(rows);
	}
	
	function removeMember(type){
		var grid=eval(type+ "Grid");
		var rows=grid.getSelecteds();
		grid.removeRows(rows);
	}
	
	
	
	function permissonRender(e) {
		var record = e.record;
		var uid = record._uid;
        var aryPermission=record.rightJson;
        
        var s="";
        for(var key in aryPermission){
        	var data=aryPermission[key];
        	s+="<span style='margin-left:10px;'><b>"+nvJson[key]+"</b>:</span>";
        	s+="<span idx='"+uid +"' key='"+key+"'>";
        	for(var i=0;i<data.length;i++){
        		var tmpObj=data[i];
        		var checked=tmpObj.val?'checked="checked"':"";
        		s+='<input type="checkbox" name="'+key +"_" + tmpObj.key+'" value="'+tmpObj.key  +'" '+checked+' onclick="permissionClick(this,'+uid+')" />' + tmpObj.name;
        	}
        	s+="</span>";
        }
        return s;
    }
	
	function permissionClick(obj,uid){
		 var row = solGrid.getRowByUID(uid);
		 var objParent=$(obj).parent();
		 var jObj=$(obj);
		 
		 var type=objParent.attr("key");
		 var aryPerssion=row.rightJson[type];
		 for(var i=0;i<aryPerssion.length;i++){
			 var item=aryPerssion[i];
			 var key=jObj.val();
			 if(item.key==key){
				 item.val=obj.checked;
			 }
		 }
	}
	
	function selSolution(){
		if(grantType=='actDefinition'){
			selectSolution();
		}else{
			selectCategory();
		}
		
	}
	
	function selectCategory(){
		_OpenWindow({
    		url: __rootPath + "/bpm/core/bpmAuthSetting/selectTree.do",
            title: "选择分类", width: "800", height: "600",
            ondestroy: function(action) {
            	if(action=="ok"){
            		var iframe = this.getIFrameEl().contentWindow;
            		var trees=iframe.getTrees();
                	
                	var treeArray=[];
                	for(var i=0;i<trees.length;i++){
                		var tmp=trees[i];
                		var obj={name:tmp.name,key:tmp.key,treeId:tmp.treeId};
                		obj.rightJson=rightJson;
                		treeArray.push(obj);
                	}
                	solGrid.setData(treeArray);
            	}
            }
    	});	
	}
	
	function selectSolution(){
		openBpmSolutionDialog(false,function(solutions){
			var sourceGrid = solGrid.getData();
			var rawSource = [];
			var arySolution=[];
			for(var i=0; i<sourceGrid.length; i++){
				rawSource.push(sourceGrid[i].key);	
				var obj={name:sourceGrid[i].name,key:sourceGrid[i].key,solId:sourceGrid[i].solId};
           		obj.rightJson=rightJson;
           		arySolution.push(obj);
			}			
          	for(var i=0;i<solutions.length;i++){
          		if(rawSource.contains(solutions[i].key))continue;
				var tmp=solutions[i];
           		var obj={name:tmp.name,key:tmp.key,solId:tmp.solId};
           		obj.rightJson=rightJson;
           		arySolution.push(obj);
           	}          	
           	solGrid.setData(arySolution);
		});
	}
	
	function setUser(type){
		var  chkSol=mini.get("chkSolution").getChecked();
		openProfileDialog({onload:function(iframe){
			var json=eval(type +"Grid.getData()");
        	iframe.init(json);
		},onOk:function(data){
    		if(type=="def" && chkSol){
    			defGrid.setData($.clone(data));
    			startGrid.setData($.clone(data));
    			instGrid.setData($.clone(data));
    			taskGrid.setData($.clone(data));
    		}
    		else{
    			eval(type+ "Grid.setData(data)")
    		}
		}})
	}
	
	function onOk(){
		 form.validate();
         if (form.isValid() == false) return;
		var data = form.getData();  
		var authSetting = mini.encode(data); 
		
		var def= defGrid.getData();
		var start= startGrid.getData();
		var inst= instGrid.getData();
		var task= taskGrid.getData();
		
		var rights={def:def,start:start,inst:inst,task:task};
		var rightsJson=mini.encode(rights); 
		
		var defs=solGrid.getData();
		if(defs.length==0){
			alert("请选择方案数据!");
			return ;
		}
		
		var defJson=mini.encode( defs);
		
		var params={authSetting:authSetting,rightsJson:rightsJson,defJson:defJson};
		var url=__rootPath +"/bpm/core/bpmAuthSetting/saveAuth.do";
		msgId=mini.loading("正在保存", "操作信息");
		$.post(url,params,function(data){
			mini.get(msgId).hide();
			if(data.success){
				CloseWindow("ok");
			}
			else{
				mini.alert(data.message,"提示消息");
			}
		})
		
	}
</script>
	<rx:formScript formId="form1" baseUrl="bpm/core/bpmAuthSetting"
		entityName="com.redxun.bpm.core.entity.BpmAuthSetting" />
</body>
</html>