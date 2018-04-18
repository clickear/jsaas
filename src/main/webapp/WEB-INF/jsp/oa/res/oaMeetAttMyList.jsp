<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun" %>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的会议列表</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
    	<div class="mini-toolbar" >
            <table style="width:100%;">
                <tr>
                    <th style="width:100%;" >
					<a class="mini-button" iconCls="icon-meeting" onclick="addMeeting()" plain="true">申请会议</a>
					<a class="mini-button" iconCls="icon-search" onclick="searchForm(this)">查询</a>
                  	<a class="mini-button" iconCls="icon-clear" onclick="onClearList(this)">清空</a>
                    </th>
                   
                </tr>
	                <tr>
	                    <td colspan="2">
		                    <form class="text-distance">
        						<div>
        							<span class="text">开始时间：</span><input id="sDate" name="Q_oaMeeting.start_D_GE" class="mini-datepicker" format="yyyy-MM-dd" />至<input id="eDate" name="Q_oaMeeting.end_D_LE" class="mini-datepicker" format="yyyy-MM-dd" />
	        						<span class="text">会议名：</span><input class="mini-textbox" name="Q_oaMeeting.name_S_LK"/>
		        					<span class="text">用户名：</span><input class="mini-textbox" name="Q_fullName_S_LK"/>
		        					<span class="text">会议地点：</span><input class="mini-textbox" name="Q_oaMeeting.location_S_LK"/>		        					
		        					<span class="text">状态：</span> 
		        					<input name="Q_oaMeeting.status_S_EQ" class="mini-combobox" data="[{id:'DISABLED',text:'禁用'},{id:'ENABLED',text:'启用'}]"
		        					 value="" showNullItem="true" nullItemText="所有.."/> 
        						</div>
							</form>
	                    </td>
	                </tr>
            </table>
        </div>
        

<%-- 		<redxun:toolbar entityName="com.redxun.oa.res.entity.OaMeetAtt" excludeButtons="popupAddMenu,popupAttachMenu,popupSearchMenu,popupSettingMenu,detail,edit,remove,fieldSearch" > --%>

<%-- 		</redxun:toolbar> --%>
		<div class="mini-fit rx-grid-fit">
			<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/oa/res/oaMeetAtt/listOameet.do" idField="attId"
				multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
				<div property="columns">
					<div type="checkcolumn" width="20"></div>
					<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
					<div field="oaMeeting.name" width="120" headerAlign="center" allowSort="true">会议名称</div>
					<div field="fullName" width="120" headerAlign="center" allowSort="true">用户名</div>
					<div field="oaMeeting.start" width="80" dateFormat="yyyy-MM-dd HH:mm:ss" headerAlign="center" allowSort="true">开始时间</div>
					<div field="oaMeeting.end" width="80" dateFormat="yyyy-MM-dd HH:mm:ss" headerAlign="center" allowSort="true">结束时间</div>
					<div field="oaMeeting.location" width="120" headerAlign="center" allowSort="true">会议地点</div>
					<div field="oaMeeting.status" width="120" headerAlign="center" allowSort="true">会议状态</div>
				</div>
			</div>
		</div>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.oa.res.entity.OaMeetAtt" winHeight="450"
		winWidth="700" entityTitle="我参与的会议总结" baseUrl="oa/res/oaMeetAtt" />
	<script type="text/javascript">
	//行功能按钮
    function onActionRenderer(e) {
    	var record = e.record;
        var pkId = record.pkId;
        var s = '<span class="icon-detail" title="明细" onclick="detailRow1(\'' + pkId + '\')"></span>';
        if(record.status=='DRAFT'){//如果是草稿需求才可以进行编辑
            s+= ' <span class="icon-writemail" title="个人总结" onclick="editRow(\'' + pkId + '\')"></span>';
            }
        return s;
    }
	
    grid.on("drawcell", function (e) {
        var record = e.record,
        field = e.field,
        value = e.value;
        var status = record.oaMeeting.status;
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
        if(field=='oaMeeting.status'){
        	if(status=='ENABLED'){
        		e.cellHtml='启用';
        	}else if(status=='DISABLED'){
        		e.cellHtml='禁止';
        	}
        }
    });
    
    grid.on('update',function(){
    	_LoadUserInfo();
    });
    
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
    
    function editRow(pkId){
    	var row = grid.getSelected();
    	_OpenWindow({
			url : "${ctxPath}/oa/res/oaMeetAtt/edit.do?pkId=" + row.pkId,
			title : row.oaMeeting.name,
			width : 800,
			height : 500,
		//max:true,
		});
    }
    
    
    /*会议室申请*/
    function addMeeting(){
		_OpenWindow({
			title : '会议申请',
			url : __rootPath+ "/oa/res/oaMeeting/edit.do",
			iconCls:'icon-edit',
			width : 800,
			height : 500,
			ondestroy : function(action) {
					if(action=='ok')
						location.reload();
			}
		});
    }
    
	</script>
</body>
</html>