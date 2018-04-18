<%-- 
    Document   : 子系统管理
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>子系统明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<div class="heightBox"></div>
 	<div id="form1" class="form-outer shadowBox90">
		<table class="table-detail column_2" cellpadding="0" cellspacing="1">
			<caption>基本信息</caption>
			<tr>
				<th>
					<span class="starBox">
						系统名称<span class="star">*</span>
					</span>
				</th>
				<td>${subsystem.name}</td>
				<th>
					<span class="starBox">
						系统 Key <span class="star">*</span>
					</span>
				</th>
				<td>${subsystem.key}</td>
			</tr>
			
			<tr>
				<th>
					<span class="starBox">
						是否缺省<span class="star">*</span>
					</span>
				</th>
				<td>${subsystem.isDefault}</td>
				<th>
					<span class="starBox">
						状　　态 <span class="star">*</span>
					</span>		
				</th>
				<td>${subsystem.status}</td>
			</tr>
			<tr>
				<th>描　　述</th>
				<td colspan="3">${subsystem.descp}</td>
			</tr>
		</table>
	</div>
	
	<rx:detailScript baseUrl="sys/core/subsystem" formId="form1" />
	<script type="text/javascript">
	  	addBody();
       	$(function(){
       		$(".view-img").css('cursor','pointer');
       		$(".view-img").on('click',function(){
       			var fileId=$(this).attr('id');
       			if(fileId=='')return;
       			_ImageViewDlg(fileId);
       		});
       	});
	</script>
</body>
</html>