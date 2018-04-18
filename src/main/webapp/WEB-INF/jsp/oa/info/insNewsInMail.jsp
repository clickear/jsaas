<%-- 
    Document   : Portal门户里我的消息的显示页面
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>新闻公告列表管理</title>
<%@include file="/commons/list.jsp"%>
	<style type="text/css">
		a:link,a:visited{
		text-decoration:none;  /*超链接无下划线*/
		}
	</style>
</head>
<body>
	<div class="mini-fit" style="height: 100px;">
		<div id="datagrid1" class="mini-datagrid" url="${ctxPath}/oa/mail/innerMail/getPortalInMail.do?pageSize=${param['pageSize']}"
			style="width: 100%; height: 100%; padding:100px;" allowResize="false" showVGridLines="false" showHGridLines="false"
			 idField="msgId" ondrawcell="onDrawCell"
			multiSelect="true" showColumnsMenu="true" showPager="false"
			sizeList="[5,20,50]" showColumns="false"
			allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns" >
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer1" cellStyle="padding:0;">#</div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
				<div field="subject"  headerAlign="center" width="180"
					allowSort="true">邮件主题</div>
				<div field="createTime" width="50" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss">收到时间</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		mini.parse();
		var grid = mini.get("datagrid1");
				
		//在最前面加一个图标
		function onActionRenderer(e) {
			var record = e.record;
            var isRead = record.isRead;
            if(isRead == "NO"){
				var s = '<span class="icon-newmail" title="未读"></span>';
            }else{
            	var s = '<span class="icon-readmail" title="已读"></span>';
            }
			return s;
		}
		
		function onActionRenderer1(e) {
			var record = e.record;
            var urge = record.urge;
            if(urge == "1"){
				var s = '<span class="icon-normal" title="一般"></span>';
            }else if(urge=="2"){
            	var s = '<span class="icon-important" title="重要"></span>';
            }else{
            	var s = '<span class="icon-vital" title="非常重要"></span>'
            }
			return s;
		}
		
		//将邮件名字超链接化
		function onDrawCell(e) {
            var record = e.record;
            var uid = record._uid;//行数
            if (e.field == "subject") {
                var sub = record.subject;
                e.cellStyle = "text-align:left";
                e.cellHtml = '<a href="javascript:detailRow1(\'' + uid + '\')">' + sub +'</a>';

            }
        }
		
		//点击邮件超链接弹出新网页显示内容
		 function detailRow1(uid) {
			 var folderId = '${folderId}';
			 var row=grid.getRowByUID(uid);
			 var pkId = row.pkId;
			 _OpenWindow({
					url : "${ctxPath}/oa/mail/innerMail/get.do?pkId=" + pkId +"&folderId="+folderId+"&isHome=YES",
					width : 1000,
					height : 800,
					title : "内部邮件",
					ondestroy : function(action) {
						}
	        	});
		 }
	</script>

	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.oa.info.entity.InfInnerMsg" winHeight="550"
		winWidth="780" entityTitle="消息" baseUrl="oa/info/infInnerMsg" />
</body>
</html>