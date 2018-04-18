<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
    <head>
        <title>选择模版</title>
        <%@include file="/commons/edit.jsp" %>
        <script type="text/javascript" src="${ctxPath}/scripts/share/dialog.js"></script>
        <script type="text/javascript" src="${ctxPath}/scripts/sys/bo/BoUtil.js"></script>
        <script type="text/javascript" src="${ctxPath}/scripts/flow/form/bpmMobileForm.js"></script>
    </head>
    <body > 
         <div id="toolbar1" class="mini-toolbar mini-toolbar-bottom" >
		    <table style="width:100%;">
		        <tr>
		            <td style="width:100%;" id="toolbarBody">
		               	<a class="mini-button" iconCls="icon-next" onclick="next()">下一步</a>
<!-- 		                <a class="mini-button" iconCls="icon-close" onclick="onCancel">关闭</a> -->
		            </td>
		        </tr>
		    </table>
		</div>
       <div id="p1" class="form-outer2 shadowBox">
            <form id="form1" method="post" >
                <table class="table-detail" cellspacing="1" cellpadding="0">
                    <caption>PDF表单模板</caption>
					<tbody id="tbody"></tbody>
				</table>
																																																																																																																																																																						                       </table>
                     
            </form>
        </div>
        <script type="text/javascript">
        addBody();
        var boDefId = "";
        $(function(){
        	var data = '${data}';
        	var jsonArr = mini.decode(data);
        	boDefId = '${boDefId}';
        	var html= baidu.template('templateList',{list:jsonArr});  
    		$("#tbody").html(html);
        });
        
        function next(){
        	var form = new mini.Form("#form1");
        	form.validate();
        	if(!form.isValid()) return;
        	
        	var viewId = "${viewId}";
        	var templates=encodeURI( getTemplate());
        	var url=__rootPath + "/bpm/form/bpmFormView/pdfTempEdit.do?boDefId=" + boDefId +"&templates=" + templates+"&viewId="+viewId;
        	
        	_OpenWindow({
        		url: url,
                title: "pdf导出表单", width: "100%", height:"100%",
                ondestroy: function(action) {
                	CloseWindow('ok');
                }
        	});
        }
        
        function getTemplate(){
        	var aryTr=$("tr",$("#tbody"));
        	var json={};
        	aryTr.each(function(){
        		var row=$(this);
        		var selObj=$("select",row);
        		var alias=selObj.attr("alias");
        		var type=selObj.attr("type");
        		var template=selObj.val();
        		var obj={};
        		obj[alias]=template;
        		if(type=="main"){
        			if(!json[type]){
        				json[type]=obj;
        			}
        		}
        		else if(type=="opinion"){
        			json["_opinion_"]=template;
        		}
        		else{
        			if(!json[type]){
        				json[type]=[obj];
        			}
        			else{
        				json[type].push(obj);
        			}
        		}
        	})
        	
        	var templates=JSON.stringify(json);
        	
        	return templates;
        }
        
        </script>
        <script id="templateList"  type="text/html">
		<#for(var i=0;i<list.length;i++){
			var obj=list[i];
			var tempAry=obj.template;
		#>
		<tr >
				<th ><#=obj.name#></th>
				<td>
					<select name="template" alias="<#=obj.key#>" type="<#=obj.type#>">
						<#for(var n=0;n<tempAry.length;n++){
							var tmp=tempAry[n];
						#>
							<option value="<#=tmp.alias#>"><#=tmp.name#></option>
						<#}#>
					</select>
				</td>
		</tr>
		<#}#>
		</script>

    </body>
</html>