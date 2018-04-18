 
<%-- 
    Document   : [微信卡券]编辑页
    Created on : 2017-08-24 14:26:26
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[微信卡券]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
<div class="mini-fit">
<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pubId" name="pubId" class="mini-hidden" value="${param.pubId}" />
				<input id="cardId" name="cardId" class="mini-hidden" value="${param.cardId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<tr>
						<td colspan="2">
						<input id="startQ" name="startQ" class="mini-datepicker" />至<input id="endQ" name="endQ" class="mini-datepicker" />
						<a class="mini-button" iconCls="icon-search" onclick="queryApi()" >查询</a> 
						</td>
					</tr>
				</table>
			</div>
		</form>
		
		
	</div>
</div>
	
	<script type="text/javascript">
	mini.parse();
	var form = new mini.Form("#form1");
	var startQ=mini.get("startQ");
	var endQ=mini.get("endQ");
	
	function queryApi(){
		var startQValue=startQ.getValue();
		var endQValue=endQ.getValue();
		form.validate();
        if (!form.isValid()) {
            return;
        }
        if((startQValue>endQValue)||(endQValue>=new Date())){
        	mini.showTips({
                content: "<b>请不要包含今天及未来</b><br/><b>开始必须小于结束</b>",
                state: 'warning',
                x: 'center',
                y: 'center',
                timeout: 3000
            });
        	return;
        }
        
		var data=form.getData(true);
		$.ajax({
			type:"post",
			url:"${ctxPath}/wx/core/wxTicket/queryTicketInfo.do",
			data:data,
			success:function (result){
				if(result.success){
					$(".showInfoTable").remove();
					var list=result.list;
					var htmlContent="";
					if(list.length==0){
						htmlContent="<div class='showInfoTable'>暂无数据</div>";
						$("#p1").append(htmlContent);
					}else{
						htmlContent='<div id="datagrid1" class="mini-datagrid" style="width:auto;height:300px;"  showPager="false" ><div property="columns"><div field="ref_date" width="120" headerAlign="center">日期</div><div field="view_cnt" width="120" headerAlign="center">浏览次数</div><div field="view_user" width="120" headerAlign="center">浏览人数</div><div field="receive_cnt" width="120" headerAlign="center">领取次数</div><div field="receive_user" width="120" headerAlign="center">领取人数</div><div field="verify_cnt" width="120" headerAlign="center">使用次数</div><div field="verify_user" width="120" headerAlign="center">使用人数</div>    <div field="given_cnt" width="120" headerAlign="center">转赠次数</div><div field="given_user" width="120" headerAlign="center">转赠人数</div><div field="expire_cnt" width="120" headerAlign="center">过期次数</div><div field="expire_user" width="120" headerAlign="center">过期人数</div></div></div>';
						$("#p1").append(htmlContent);
						mini.parse();
						var grid=mini.get("datagrid1");
						var gridList=[];
						for ( var i=0;i<list.length;i++){
							var rowObj={ref_date:list[i].ref_date,view_user:list[i].view_user,receive_cnt:list[i].receive_cnt,receive_user:list[i].receive_user,verify_cnt:list[i].verify_cnt,verify_user:list[i].verify_user,given_cnt:list[i].given_cnt,given_user:list[i].given_user,expire_cnt:list[i].expire_cnt,expire_user:list[i].expire_user};
							gridList.push(rowObj);
						}
						grid.setData(gridList);
						
					}
					
				}else{
					mini.showTips({
		                content: "<b>"+result.errMsg+"</b>",
		                state: 'warning',
		                x: 'center',
		                y: 'center',
		                timeout: 3000
		            });
					
				}
			}
		})
	}

	</script>
</body>
</html>