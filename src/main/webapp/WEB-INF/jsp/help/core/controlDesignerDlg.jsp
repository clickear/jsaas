<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>用户组选择框</title>
   <%@include file="/commons/list.jsp"%>
</head>
<body>
	<h2>控件在线自定义编辑示例</h2>
	<table style="width:100%;">
		<tr>
			<td>选择</td>
			<td><input type="button" value="控件设计器" onclick="showDesigner()"/></td>
		</tr>
		<tr>
			<td>设计后的内容</td>
			<td>
				<textarea class="mini-textarea" style="width:90%;height:350px;" id="htmlCode" name="htmlCode"></textarea>
			</td>
		</tr>
		<tr>
			<td>显示结果</td>
			<td id="resultTd">
				
			</td>
		</tr>
	</table>

	<script type="text/javascript">
		mini.parse();
		
		function showDesigner(){
			
			var htmlCode=mini.get('htmlCode');
			var val=htmlCode.getValue();
			
			_OpenWindow({
				title:'控件在线设计',
				max:true,
				height:500,
				width:680,
				url:__rootPath+'/sys/core/controlDesinger.do',
				onload:function(){
					var iframe = this.getIFrameEl();
					iframe.contentWindow.setContent(val);
				},
				ondestroy:function(){
					var iframe = this.getIFrameEl();
		            var content = iframe.contentWindow.getContent();
		            //回调结果
		            htmlCode.setValue(content);
		            //展示设计后的效果
		            $("#resultTd").html(content);
		            mini.parse();
				}
			});
		}
	</script>

</body>
</html>