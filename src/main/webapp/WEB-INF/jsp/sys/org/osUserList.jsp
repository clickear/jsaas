<%-- 
    Document   : 用户列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>用户列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div class="titleBar mini-toolbar" >
         <ul>
			<li>
				<a class="mini-button" iconCls="icon-create"  onclick="add()">增加</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-edit"  onclick="edit()">编辑</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-remove"  onclick="remove()">删除</a>
			</li>
			<li>
				<c:if test="${isWxEanble}">
					<a iconCls="icon-sync-user" class="mini-menubutton" menu="#popupMenu" >同步用户</a>
   					<ul id="popupMenu" class="mini-menu" style="display:none;">      			    
				   		<li iconCls="icon-all" onclick="syncOrgUser">同步所有</li>	
				        <li iconCls="icon-new" onclick="syncChoiceOrgUser">同步选中</li>
				    </ul>
				</c:if>
			</li>
			<li class="clearfix"></li>
		</ul>
		<div class="searchBox">
			<form id="searchForm" class="search-form" style="display: none;">						
				<ul>
					<li>
						<span>姓名：</span><input class="mini-textbox" name="Q_fullname_S_LK">
					</li>
					<li>
						<span>用户编号：</span><input class="mini-textbox" name="Q_userNo_S_LK">
					</li>
					<li>
						<span>性别：</span>
						<input 
							class="mini-combobox" 
							name="Q_sex_S_LK" 
                            showNullItem="true"  
                            emptyText="请选择..."
							data="[{id:'Male',text:'男'},{id:'Female',text:'女'}]" 
						/>
					</li>
					<li class="searchBtnBox">
						<a class="mini-button _search" onclick="searchForm(this)" >搜索</a>
						<a class="mini-button _reset" onclick="clearForm(this)" >清空</a>
					</li>
					<li class="clearfix"></li>
				</ul>
			</form>	
			<span class="searchSelectionBtn" onclick="no_search(this ,'searchForm')">
				<i class="icon-sc-lower"></i>
			</span>
		</div>
	</div>
	<div class="mini-fit rx-grid-fit">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/sys/org/osUser/listData.do?tenantId=${param['tenantId']}" idField="userId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22"  headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="fullname" width="100"  allowSort="true">姓名</div>
				<div field="userNo" width="100"  allowSort="true">用户编号</div>
				<div field="status" width="80"  allowSort="true">状态</div>
				<div field="mobile" width="120"  allowSort="true">手机</div>
				<div field="email" width="120"  allowSort="true">邮件</div>
				<div field="sex" width="80"  allowSort="true" renderer="onSexRenderer">性别</div>
				<div field="syncWx" width="80" renderer="onWxRenderer"  allowSort="true">同步到微信</div>
			</div>
		</div>
	</div>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.org.entity.OsUser" 
	tenantId="${param['tenantId']}"
	winHeight="450" winWidth="700" entityTitle="用户" baseUrl="sys/org/osUser" />
	
	<script type="text/javascript">
	var noSwtich=${cookie.switchUser==null  };
	
	
    var tenantId='${param.tenantId}';    	
	//编辑
	        function onActionRenderer(e) {
	            var record = e.record;
	            var uid = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + uid + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + uid + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>'
	                    + ' <span class="icon-add" title="拓展属性" onclick="grantNewAttribute(\'' + record.userId + '\')"></span>';
	                    
	                    if(noSwtich){
	                    	s+= '<f:a alias="OsUser_Switch" iconCls="icon-refresh" tag="span" onclick="switchUser(\\'' + uid + '\\')" title="切换用户" showNoRight="false" ></f:a>';
	                    }
	            return s;
	        }
        	function _add(){
        		_OpenWindow({
        			title:'用户添加',
        			height:560,
        			width:1024,
        			url:__rootPath+'/sys/org/osUser/edit.do?tenantId='+tenantId,
        			ondestroy: function(action) {
                    	if (action != 'ok') return;
                        grid.reload();
                    }
        		});
        	}
        	
        	function grantNewAttribute(userId){
            	_OpenWindow({
    				title:'用户属性管理',
    				url:__rootPath+'/sys/org/osCustomAttribute/grantGroupAttribute.do?attributeType=User&targetId='+userId,
    				width:900,
    				height:600
    			});        		
        	}
        	
        	function _eidtRow(uid){
        	
        	}
        	
        	grid.on('drawcell',function(e){
        		var record = e.record,
		        field = e.field,
		        value = e.value;
	            if(field=='status'){
	            	if(value=='IN_JOB'){
	            		e.cellHtml='<font color="green">在职(IN_JOB)</font>';
	            	}else if(value=='OUT_JOB'){
	            		e.cellHtml='<font color="blue">在职(OUT_JOB)</font>';
	            	}else{
	            		e.cellHtml='<font color="red">删除(DEL_JOB)</font>';
	            	}
	            }
	            
        	});
        	
        	function switchUser(userId){
        		mini.confirm("确定切换用户?", "提示信息", function(action){
        			if (action != "ok") return;
        			location.href=__rootPath +"/j_spring_security_switch_user?userId=" + userId;
        		});
        	}
        	
        	function onSexRenderer(e){
        		var record = e.record;
 	            var sex = record.sex;
 	            var arr = [ {'key' : 'Male', 'value' : '男(Male)','css' : 'green'}, 
 				            {'key' : 'Female','value' : '女(Female)','css' : 'red'} ];
 				return $.formatItemValue(arr,sex);
        		
        	}
        	
        	function onWxRenderer(e) {
	            var record = e.record;
	            var syncWx = record.syncWx;
	            
	            var arr = [ {'key' : 1, 'value' : '是','css' : 'green'}, 
				            {'key' : 0,'value' : '否','css' : 'red'} ];
				
				return $.formatItemValue(arr,syncWx);
	        }
        	
        	/**
        	*同步用户和组织数据到企业微信。
        	*/
        	function syncOrgUser(){        		
        		mini.confirm("确定同步用户到企业微信吗?", "提示信息", function(action){
        			if (action != "ok") return;
        			var url=__rootPath +"/wx/ent/wxEntOrg/syncOrgUser.do";
            		_SubmitJson({url:url,success:function(data){
            			console.info(data);
            			grid.load();
            		}});	
        		});
        	}
        	
        	/**
        	*同步选中用户和组织数据到企业微信。
        	*/
        	function syncChoiceOrgUser(){        		
        		var choiceData = grid.getSelecteds();
        		var userIds = [];
        		for(var i = 0; i<choiceData.length; i++){
        			userIds.push(choiceData[i].userId);
        		}
         		
        		if(choiceData.length==0){
        			mini.alert("请选择用户");
        			return;
        		}
        		mini.confirm("确定同步("+choiceData.length+")个用户到企业微信吗?", "提示信息", function(action){
        			if (action != "ok") return;
        			var url=__rootPath +"/wx/ent/wxEntOrg/syncOrgUser.do";
            		_SubmitJson({url:url,data:{"userIds":userIds.join(',')},success:function(data){
            			grid.load();
            		}});
        		});
        		
        	}
        	
        	function switchUser(userId){
        		mini.confirm("确定切换用户?", "提示信息", function(action){
        			if (action != "ok") return;
        			location.href=__rootPath +"/j_spring_security_switch_user?userId=" + userId;
        		});
        	}
        	
        	
        	
        	
        </script>
</body>
</html>