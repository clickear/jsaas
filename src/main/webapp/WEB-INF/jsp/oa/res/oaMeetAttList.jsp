<%-- 
    Document   : [OaMeetAtt]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>会议人员列表管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet"　type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet"　type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js"　type="text/javascript"></script>
</head>
<body>

<redxun:toolbar entityName="com.redxun.oa.res.entity.OaMeetAtt" excludeButtons="popupAddMenu,popupAttachMenu,popupSearchMenu,popupSettingMenu,detail,edit,remove,fieldSearch" >		  
<div class="self-search" >		  
	    <form id="searchForm">
	    <table>
	    <tr>
	    <td>	
	    <span>会议名：</span><input class="mini-textbox" name="Q_oaMeeting.name_S_LK"/>
	    <span>用户名：</span><input class="mini-textbox" name="Q_fullName_S_LK"/>
	    <span>开始时间：</span><input id="sDate" name="Q_oaMeeting.start_D_GE" class="mini-datepicker" format="yyyy-MM-dd" />&nbsp;至&nbsp;<input id="eDate" name="Q_oaMeeting.end_D_LE" class="mini-datepicker" format="yyyy-MM-dd" /> 		
	    <a class="mini-button" iconCls="icon-search" onclick="onSearch">查询</a>
	    <a class="mini-button" iconCls="icon-cancel" onclick="onClear()">清空</a>
	    </td>
	   </tr>
	</table> 
</form>	
	        		 	        			
</div>
	</redxun:toolbar>

	<div class="mini-fit">
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/oa/res/oaMeetAtt/listData.do" idField="attId"
			multiSelect="true" showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20"
			allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
				<div field="oaMeeting.name" width="120" headerAlign="center" allowSort="true">会议名称</div>
				<div field="fullName" width="120" headerAlign="center" allowSort="true">用户名</div>
				<div field="oaMeeting.start" width="80" dateFormat="yyyy-MM-dd HH:mm:ss" headerAlign="center" allowSort="true">开始时间</div>
				<div field="oaMeeting.end" width="80" dateFormat="yyyy-MM-dd HH:mm:ss" headerAlign="center" allowSort="true">结束时间</div>
			</div>
		</div>
	</div>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.oa.res.entity.OaMeetAtt" winHeight="450"
		winWidth="700" entityTitle="[OaMeetAtt]" baseUrl="oa/res/oaMeetAtt" />
	<script type="text/javascript">
	var searchForm=new mini.Form('searchForm');
	//行功能按钮
	function onActionRenderer(e) {
		var record = e.record;
		var pkId = record.pkId;
		var s ='<span class="icon-detail" title="明细" onclick="detailRow1(\'' + pkId + '\')"></span>'
				+' <span class="icon-remove" title="删除" onclick="delRow(\''+ pkId + '\')"></span>';
		return s;
	}
	
	/*搜索按钮事件处理*/
	function onSearch(e){
    	var sd=mini.get("sDate").getValue();
    	var ed=mini.get("eDate").getValue();
        var sDate = new Date(sd);
        var eDate = new Date(ed);
        if(sDate > eDate){  //结束日期小于开始日期
        	alert("结束日期不能小于开始日期");
        	return;
        }else{
			 var button = e.sender;
			 var el=button.getEl();
			 var form=$(el).parents('form');
			 
			 if(form!=null){
			 	var formData=form.serializeArray();
			 	var data={};
			 	//加到查询过滤器中
				data.filter=mini.encode(formData);
				data.pageIndex=grid.getPageIndex();  //页码
				data.pageSize=grid.getPageSize();    //每页的记录数
			    data.sortField=grid.getSortField();   //排序字段
			    data.sortOrder=grid.getSortOrder();    //排序顺序
				grid.load(data);
			 }
        }
	
	}
	function onClear(){
		searchForm.reset();
	}
	
	grid.on("drawcell", function (e) {
        var record = e.record,
        field = e.field,
        value = e.value;
        //格式化日期
        if (field == "oaMeeting.start") {
            if (mini.isDate(value)) e.cellHtml = mini.formatDate(value, "yyyy-MM-dd HH:mm");
        }
        
        if (field == "oaMeeting.end") {
            if (mini.isDate(value)) e.cellHtml = mini.formatDate(value, "yyyy-MM-dd HH:mm");
        }
        if(field=='oaMeeting.name'){
        	e.cellHtml= '<a href="javascript:detailRow1(\'' + record.oaMeeting.pkId + '\')">'+record.oaMeeting.name+'</a>';
        }
    });
    
    grid.on('update',function(){
    	_LoadUserInfo();
    });
    
    
    function detailRow1(pkId){
    	var row = grid.getSelected();
    	_OpenWindow({
			url : "${ctxPath}/oa/res/oaMeeting/get.do?pkId=" + row.oaMeeting.pkId,
			title : row.oaMeeting.name,
			width : 800,
			height : 500,
		//max:true,
		});
    }
	</script>

</body>
</html>