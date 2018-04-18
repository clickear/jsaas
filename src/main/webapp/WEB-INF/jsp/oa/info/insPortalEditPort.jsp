<%-- 
    Document   : 门户编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html>
<html>
<head>
<title>门户编辑</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${insPortal.portId}" excludeButtons="remove,prevRecord,nextRecord" />
	<div id="p1" class="form-outer2">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="portId" class="mini-hidden" value="${insPortal.portId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>门户基本信息</caption>
					<tr>
						<th>列数 <span class="star">*</span> ：
						</th>
						<td><input name="colNums" value="${insPortal.colNums}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" required="true" emptyText="请输入列数" /></td>
					</tr>
					<tr>
						<th>栏目宽 <span class="star">*</span> ：
						</th>
						<td><input name="colWidths" value="${insPortal.colWidths}" class="mini-textbox" vtype="maxLength:64" style="width: 80%" required="true" emptyText="请输入栏目宽" /> <input type="button" value="帮助" onclick="showTips()" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<script type="text/javascript">
        mini.parse();
        
        //门户栏目的增加
        function addColumns(){
			var insPortCols=mini.get('insPortCols');
			insPortCols.setValue("");
			insPortCols.setText("");
			_ColumnsDlg(false,function(columns){
				var uIds=[];
				var uNames=[];
				for(var i=0;i<columns.length;i++){
					uIds.push(columns[i].colId);
					uNames.push(columns[i].name);
				}
				if(insPortCols.getValue()!=''){
					uIds.unshift(insPortCols.getValue().split(','));
				}
				if(insPortCols.getText()!=''){
					uNames.unshift(insPortCols.getText().split(','));	
				}
				insPortCols.setValue(uIds.join(','));
				insPortCols.setText(uNames.join(','));
				console.log(uIds.join(','));
			});
		}
      	
        //门户栏目点击增加时打开栏目list
        function _ColumnsDlg(single,callback){	
        	_OpenWindow({
        		url:__rootPath + '/oa/info/insColumn/Select.do?single='+single,
        		height:350,
        		width:780,
        		title:'栏目选择',
        		ondestroy:function(action){
        			if(action!='ok')return;
        			var iframe = this.getIFrameEl();
                    var users = iframe.contentWindow.GetData();
                    if(callback){
                    	if(single && users.length>0){
                    		callback.call(this,users[0]);
                    	}else{
                    		callback.call(this,users);
                    	}
                    }
        		}
        	});
        }
        
        //提示信息
        function showTips() {
            mini.showTips({
                content: "按照输入的列数来填写，例如3列：250,100%,250。百分号表示剩余空白的百分比。如果全是数字将按照数字比例。",
                state: "info",
                x: "center",
                y: "bottom",
                timeout: 10000
            });
        }
    </script>
	<rx:formScript formId="form1" baseUrl="oa/info/insPortal" />
</body>
</html>