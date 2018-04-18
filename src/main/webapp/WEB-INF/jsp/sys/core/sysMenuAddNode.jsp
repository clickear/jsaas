<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>菜单发布</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div class="mini-toolbar topBtn">
		<table cellpadding="0" cellspacing="0" >
			<tr>
				<td>
					<a class="mini-button" iconCls="icon-save" onclick="onSave">保存</a>
<!-- 					<a class="mini-button" iconCls="icon-close" onclick="CloseWindow()">关闭</a> -->
				</td>
			</tr>
		</table>
	</div>
	<div class="shadowBox90">
		<div id="menuForm" style="padding-top:8px;">
		    		<table class="table-detail column_1" style="width:100%; " >
		    			<tr>
		    				<td colspan="2" style="text-align:center;height:32px;font-size: 18px;">
		    					菜单明细
		    				</td>
		    			</tr>
		    			<tr>
		    				<th>
		    					<span class="starBox">
		    						所属子系统<span class="star">*</span>
		    					</span>		
    						</th>
		    				<td>
		    					<input id="sysId" name="sysId" class="mini-combobox" 
		    					url="${ctxPath}/sys/core/sysMenuMgr/listAllSubSys.do" 
		    					onvaluechanged="onSysIdChanged"
		    					textField="name" valueField="sysId" style="width:92%" required="true"/>
		    					
		    					<input class="mini-hidden" name="boListId" />
		    				</td>
		    			</tr>
		    			<tr>
		    				<th>父  菜  单</th>
		    				<td>
		    					 <input id="parentId" name="parentId" class="mini-treeselect" url="" style="width:90%"
		    					 multiSelect="false" textField="name" valueField="menuId" parentField="parentId"/>
		    				</td>
		    			</tr>
		    			<tr>
		    				<th>菜单名称</th>
		    				<td>
		    					<input class="mini-textbox" name="name" maxlength="120" required="true" style="width:90%" />
		    				</td>
		    			</tr>
		    			<tr>
		    				<th>菜单Key</th>
		    				<td>
		    					<input class="mini-textbox" name="key" maxlength="120" required="true" style="width:90%"/>
		    				</td>
		    			</tr>
		    		
		    			<tr>
		    				<th>访问地址</th>
		    				<td>
		    					<input class="mini-textbox" name="url" style="width:90%"/>
		    				</td>
		    			</tr>
		    			<tr>
		    				<th>菜单图标</th>
		    				<td>
		    					<input name="iconCls" id="iconCls"  class="mini-buttonedit" onbuttonclick="selectIcon" style="width:90%"/>
		    					<a class="mini-button" id="icnClsBtn"></a>
		    				</td>
		    			</tr>
		    			<tr>
		    				<th>序　　号</th>
		    				<td>
		    					<input name="sn" changeOnMousewheel="false" class="mini-spinner"  minValue="0" maxValue="100000" />
		    				</td>
		    			</tr>
		    			<tr>
		    				<th>访问方式</th>
		    				<td>
		    					<div class="mini-radiobuttonlist"  required="true" repeatItems="2" repeatLayout="table" repeatDirection="vertical" name="showType" value="URL" data="[{id:'URL',text:'URL访问'},{id:'NEW_WIN',text:'新窗口'},{id:'FUNS',text:'功能面板集'},{id:'FUN',text:'功能面板'}]"></div> 
		    				</td>
		    			</tr>
		    			<tr>
		    				<th>菜单类型</th>
		    				<td>
		    					<div class="mini-radiobuttonlist"  required="true" name="isBtnMenu" value="NO" data="[{id:'NO',text:'导航菜单'},{id:'YES',text:'按钮菜单'}]"></div> 
		    				</td>
		    			</tr>
		    	</table>
		  </div>
	</div>
	<script type="text/javascript">
		addBody();
		mini.parse();
		var menuForm=new mini.Form('menuForm');
		function onSave(){
			menuForm.validate();
        	if(!menuForm.isValid()){
        		return;
        	}
        	_SubmitJson({
        		url: "${ctxPath}/sys/core/sysMenuMgr/saveMenu.do",
        		data: { data: mini.encode(menuForm.getData())},
        		success:function(result){
        			CloseWindow('ok');
        		}
        	});
		}
		
		function setData(bo){
			menuForm.setData(bo);
		}
		
		function onSysIdChanged(e){
			var combo=e.sender;
			var sysId=mini.get('parentId');
            sysId.setUrl("${ctxPath}/sys/core/sysMenuMgr/treeBySysId.do?sysId="+combo.getValue());
            sysId.load();
		}
	
		//选择图标
       function selectIcon(e){
    	   var btn=e.sender;
    	   _IconSelectDlg(function(icon){
			//grid.updateRow(row,{iconCls:icon});
			btn.setText(icon);
			btn.setValue(icon);
			mini.get('icnClsBtn').setIconCls(icon);
		});
       }
	</script>
</body>
</html>