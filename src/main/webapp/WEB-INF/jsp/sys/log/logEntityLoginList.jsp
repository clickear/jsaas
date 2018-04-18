<%-- 
    Document   : [登录登出日志]列表页
    Created on : 2018-01-17 09:53:16
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[登录登出日志]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	 <div class="mini-toolbar" >
         <table style="width:100%;">
             <tr>
                 <td style="width:100%;">
                     <a class="mini-button" iconCls="icon-search" plain="true" onclick="searchFrm()">查询</a>
                     <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="clearForm()">清空查询</a>
                 </td>
             </tr>
              <tr>
                  <td  class="search-form" >
                     <ul>
						<li><span>操作人:</span><input class="mini-combobox" style="width:150px;" textField="fullname"  valueField="userId"  url="${ctxPath}/sys/org/osUser/listTenantUser.do" showNullItem="true" allowInput="true" name="Q_CREATE_BY__S_LK"></li>
						<li>
							<span>登录时间</span><input  name="Q_CREATE_TIME__D_GE"  class="mini-datepicker" format="yyyy-MM-dd" style="width:100px"/>
						</li>
						<li>
							<span>~</span><input  name="Q_CREATE_TIME__D_LE" class="mini-datepicker" format="yyyy-MM-dd" style="width:100px" />
						</li>
						<li>
							<span>登出时间 </span><input  name="Q_UPDATE_TIME__D_GE"  class="mini-datepicker" format="yyyy-MM-dd" style="width:100px"/>
						</li>
						<li>
							<span>~</span><input  name="Q_UPDATE_TIME__D_LE" class="mini-datepicker" format="yyyy-MM-dd" style="width:100px" />
						</li>
						<li><span>登录IP</span><input class="mini-textbox" name="Q_IP__S_LK"></li>
						<li><span>主部门</span><input class="mini-textbox" name="Q_MAIN_GROUP_NAME__S_LK"></li>
					</ul>
                  </td>
              </tr>
         </table>
     </div>
	
	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/sys/log/logEntity/getAllList.do" idField="id" ondrawcell="onDrawCell" onupdate="_LoadUserInfo();"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<!-- <div name="action" cellCls="actionIcons" width="50" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">相关操作</div> -->
				<div field="createBy"  sortField="CREATE_BY_"  width="120" headerAlign="center" allowSort="true">操作人</div>
				<div field="mainGroupName"  sortField="MAIN_GROUP_NAME_"  width="120" headerAlign="center"  allowSort="true">主部门</div>
				<div field="createTime" sortField="CREATE_TIME_" dateFormat="yyyy-MM-dd HH:mm:ss" width="120" headerAlign="center" allowSort="true">登录时间</div>
				<div field="updateTime" sortField="UPDATE_TIME_" dateFormat="yyyy-MM-dd HH:mm:ss" width="120" headerAlign="center" allowSort="true">登出时间</div>
				<div field="ip"  sortField="IP_"  width="90" headerAlign="center" allowSort="true">登录IP</div>
				<div field="userAgent"  sortField="USER_AGENT_"  width="120" headerAlign="center" allowSort="true">设备信息</div>
				<div field="duration"  sortField="DURATION_"  width="120" headerAlign="center" allowSort="true">持续时长</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>';
			return s;
		}
		
		//绘制负责人等等
		function onDrawCell(e) {
	        var tree = e.sender;
	        var record=e.record;
	        var field=e.field;
	        	if(field=='createBy'){//给reporId那列显示成名字
	        	e.cellHtml='<a class="mini-user"  userId="'+record.createBy+'"></a>';
	        	}else if(field=='duration'){
	        		if(record.duration){
	        			e.cellHtml=Math.round(record.duration/(1000*6))/10+'分钟';
	        		}else{
	        			e.cellHtml='<a style="text-decoration:none;color:red" >暂无统计</a>';
	        		}
	        	}else if(field=='logoutTime'){
	        		if(!record.updateTime){
	        			e.cellHtml='<a style="text-decoration:none;color:red" >无退出</a>';
	        		}
	        	}
	    }
	</script>
<redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.log.entity.LogEntity" winHeight="450"
		winWidth="700" entityTitle="日志实体" baseUrl="sys/log/logEntity" />
</body>
</html>