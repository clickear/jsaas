<%-- 
    Document   : 新闻显示页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>新闻公告明细</title>
<%@include file="/commons/get.jsp"%>
<%@include file="/commons/customForm.jsp"%>
<style type="text/css">

body{
	width:1200px;
	margin: 30px auto 0 auto;
}

a{
	text-decoration: none; /*超链接无下划线*/
}

li {
	border-bottom: 1px dotted #ccc;
	list-style-type: none;
}

.newsImage{
	text-align: center;
}

.column_1 tr ,.column_1 th{
	border:1px solid #ececec;
}

.column_1 th{
	text-align: center;
}

.mini-panel-header{
	border-bottom:none;
}

#validCode{
    float: left; 	
    margin-top: 6px;
}

</style>
</head>
<body >
<div class="shadowBox90">
	<div id="form1">		
		<table style="width: 100%; padding: 0;" cellspacing="1">
			<h1 name="tc" style="text-align: center; margin: 0px 0px 20px;">
				<span label="Title center" style="color: #666;font-family: '宋体' !important;font-weight: 400;;">
					${insNews.subject}
				</span>
			</h1>
			<div style="font-size: 12px; text-align: center; border-bottom-color: #cccccc; border-bottom-width: 1px; border-bottom-style: solid;">
				<span style="font-size: 12px; color: red;">${insNews.createTime}</span> 
				<span style="font-size: 24px;">&nbsp;&nbsp;&nbsp;</span> 
				<span style="font-size: 12px;">${insNews.author}</span> 
				<span style="font-size: 24px;">&nbsp;&nbsp;&nbsp;</span> 
				<span style="font-size: 12px;">浏览次数：&nbsp;</span> 
				<span style="font-size: 12px; color: red">${insNews.readTimes}</span>
			</div>
			
			
			
			<div style="text-align: center;">
				<tr <c:if test="${empty insNews.imgFileId}">style="display:none"</c:if>>
					<td class="newsImage">
						<img src="${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId=${insNews.imgFileId}" class="upload-file" />
					</td>
				</tr>
				<tr>
					<td style="margin-left: 5px; margin-right: 5px; height: 300px; text-align: left; vertical-align: top">
						${insNews.content}
					</td>
				</tr>
			</div>
		</table>
	</div>
	
	<br/>
	<br/>
	<c:if test="${isCheck}">
		<div id="files" name="files" class="upload-panel"  style="width:auto;" isDown="true" isPrint="false" value='${insNews.files}' readOnly="true" ></div>
	</c:if>
	<div id="comment">
		<div style="font-size: 12px; text-align: center; border-bottom-color: #cccccc; border-bottom-width: 1px; border-bottom-style: solid;"></div>
		<h3 align="center" style="font-size:22px;font-family: '宋体' !important;font-weight: 400;color:#666">相关评论</h3>
		<div class="mini-panel" title="我要评论" iconCls="icon-edit" style="width: 100%; height: 260px;" showFooter="true" bodyStyle="padding:6px;">
			
		
			<div property="footer" style="text-align: center; padding-bottom: 5px;padding-top:3px">
				<a id="submitCm" class="mini-button" iconCls="icon-check" ><b>提交</b></a> 
				<span style="font-size: 24px;">&nbsp;&nbsp;&nbsp;</span> 
				<a class="mini-button" onclick="clear()"><b>重置</b></a>
			</div>
			
			
			
			<div id="p1">
				<form id="form2" name="form1" method="post">
					<table style="width: 100%; padding: 0;border:1px solid #ececec;border-top:none;" cellspacing="0" cellpadding="0" class="column_1" >
						<tr>
							<th>用 户 名</th>
							<td width=90% style="padding-top:4px;padding-bottom:4px;">
								<input name="fullName" id="fullName" class="mini-textbox asLabel" value="${user}" readonly required="true" style="width: 80%" />
							</td>
						</tr>
						<tr>
							<th>评论内容</th>
							<td style="padding-top:4px;padding-bottom:4px;">
								<input 
									name="content" 
									class="mini-textarea" 
									id="content" 
									required="true" 
									emptyText="请输入评论内容" 
									vtype="maxLength:128" 
									style="height: 90px; width: 80%" 
								/>
							</td>
						</tr>
						<tr>
		                    <th>验 证 码</th>
		                    <td colspan="3">
		                 		<input name="validCode" class="mini-textbox" id="validCode" vtype="maxLength:4"  required="true" />                 
		       					<img 
		       						src="${ctxPath}/captcha-image.do" 
		       						width="75" height="35" 
		       						id="kaptchaImage"  
		       						style="margin-bottom: -3px;cursor:pointer" 
		       						onclick="refreshCode()"
	       						/> 
		                    </td>
		               </tr>
					</table>
				</form>
			</div>
		</div>
	</div>

</div>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<rx:detailScript baseUrl="oa/info/insNews" formId="form1"  />
	<script type="text/javascript">
		mini.parse();
		var cmId = null;//评论Id,用于子评论的回复评论Id
		var comments = '${comments}';//评论
		var permit = '${permit}';//权限,是否允许删除评论
		var commentsArr = mini.decode(comments);//转换成数组
		var form = new mini.Form("form2");//回复评论的Form
		var newId = ${insNews.newId};//当前新闻Id
		//判断是否允许评论
		$(function() {
			if('${insNews.allowCmt}' == 'NO'){
				$("#comment").hide();
			}
		});
		
		//刷新验证码
		function refreshCode(){
	    	   var randId=parseInt(10000000*Math.random());
	    	   $('#kaptchaImage').attr('src','${ctxPath}/captcha-image.do?randId='+randId);
	       }
		//点击图片预览原图
		$(function() {
			$(".view-img").on('click', function() {
				var fileId = $(this).attr('id');
				if (fileId == '')
					return;
				_ImageViewDlg(fileId);//预览原图
			});
		});
		//点击回复
		$(function() {
			$("#submitCm").click(function() {
				submitCm();
			});
		});
		//显示评论
		$(function() {
			for (var i = 0; i < commentsArr.length; i++) {//遍历每个评论
				$("#comment").append("<div style='margin-top:8px;'><input id='cmId' type='hidden' value='"+commentsArr[i].cmId+"' />" + "<span id='name'><b>" + commentsArr[i].name + "</b></span>&nbsp;发表于&nbsp;<span id='time'><b>" + mini.formatDate(commentsArr[i].time, 'yyyy-MM-dd HH:mm:ss') + "</b></span>" + "<div style='text-align:right;float:right;'><span class='delPart'><a class='del' href='Javascript:void(0);''>[删除]</a>&nbsp;|&nbsp;</span>" + "<a class='reply' href='Javascript:void(0);''>[回复]</a></div>" + "</b></span></div><div id='content'>" + commentsArr[i].content + "</div><hr/>");
				var rpcomment = commentsArr[i].rpcomment;//得到每条评论的子评论
				for (var j = 0; j < rpcomment.length; j++) {//遍历该评论下的子评论
					$("#comment").append("<div id='reply'><ul><li><p><input id='cmId' type='hidden' value='"+rpcomment[j].cmId+"'/><b>" + rpcomment[j].name + "</b>&nbsp;回复&nbsp;<b>" + rpcomment[j].replyName + "</b>&nbsp;：" + "<span style='text-align:right;float:right;'>" + mini.formatDate(rpcomment[j].time, 'yyyy-MM-dd HH:mm:ss') + "&nbsp;&nbsp;&nbsp;" + "<span class='delPart'><a class='del' href='Javascript:void(0);''>[删除]</a>&nbsp;|&nbsp;</span>" + "<a class='sonReply' href='Javascript:void(0);''>[回复]</a></span></p>" + "<span id='reply-content'>" + rpcomment[j].content + "</span></li>");
				}
				$("#comment").append("</ul></div><br>");

			}
			//点击提交评论触发
			$(".reply").each(function() {
				$(this).on("click", function() {
					$("#submitCm").unbind('click');//解除原来的提交点击事件
					$("#submitCm").click(function() {//绑定新的提交点击事件
						submitReply();
					});
					cmId = $(this).parent().parent().find("#cmId").val();
				});
			});
			//点击子评论的回复评论触发
			$(".sonReply").each(function() {
				$(this).on("click", function() {
					$("#submitCm").unbind('click');//解除原来的提交点击事件
					$("#submitCm").click(function() {//绑定新的提交点击事件
						submitReply();
					});
					cmId = $(this).parent().parent().find("#cmId").val();
				});
			});
			$(".del").each(function() {
				$(this).on("click", function() {
					cmId = $(this).parent().parent().parent().find("#cmId").val();
					//console.log(cmId);
					delCm();
				});
			});
			$(".delPart").each(function() {
				if (permit != "yes") {
					//console.log($(".delPart").html());
					$(".delPart").hide();
				}
			});

		});

		//提交评论
		function submitCm() {
			var formJson = _GetFormJson("form2");
			form.validate();

			if (!form.isValid()) {
				return;
			}
			_SubmitJson({
				url : __rootPath + '/oa/info/insNewsCm/save.do?reply=no&newId=' + newId,
				method : 'POST',
				data : formJson,
				success : function(result) {
					location.reload();
				}
			});
		}
		//删除评论
		function delCm() {
			_SubmitJson({
				url : __rootPath + '/oa/info/insNewsCm/del.do',
				method : 'POST',
				data : {
					ids : cmId
				},
				success : function(result) {
					location.reload();
					return;
				}
			});
		}
		
		//点击回复后的提交评论
		function submitReply() {
			var formJson = _GetFormJson("form2");
			form.validate();

			if (!form.isValid()) {
				return;
			}
			_SubmitJson({
				url : __rootPath + '/oa/info/insNewsCm/save.do?reply=yes&newId=' + newId + '&cmId=' + cmId,
				method : 'POST',
				data : formJson,
				success : function(result) {
					location.reload();
				}
			});
		}
		
		function clear() {
			$(".mini-textarea").find(".mini-textbox-input").val("");
		}

/* 		function reply() {
			console.log($(this).parent().parent().find("#user").html());
			$(".mini-textarea").find(".mini-textbox-input").val("");
		} */
	</script>
</body>
</html>