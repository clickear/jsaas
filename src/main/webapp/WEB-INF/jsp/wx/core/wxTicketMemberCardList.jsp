<%-- 
    Document   : [微信卡券]列表页
    Created on : 2017-08-22 10:23:23
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[微信卡券]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	 <div class="mini-toolbar" >
         <table style="width:100%;">
             <tr>
                 <td style="width:100%;">
                     <a class="mini-button" iconCls="icon-create" plain="true" onclick="addTicket()">添加会员卡</a>
                     <a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
                     <a class="mini-button" iconCls="icon-search" plain="true" onclick="searchFrm()">查询</a>
                     <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="clearForm()">清空查询</a>
                 </td>
             </tr>
              <tr>
                  <td style="white-space:nowrap;padding:5px;" class="search-form" >
                     <ul>
						<li><span>卡名:</span><input class="mini-textbox" name="Q_TITLE__S_LK"></li>
					</ul>
                  </td>
              </tr>
         </table>
     </div>
	
	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/wx/core/wxTicket/myMemberCardList.do?pubId=${param.pubId}" idField="id"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="title"  sortField="TITLE_"  width="120" headerAlign="center" allowSort="true">卡名</div>
			</div>
		</div>
	</div>

<img id="htmlContent" style="display:none;width: 300px;height: 300px;" />
<div id="landingPage" style="display:none;width: 300px;height: 300px;"></div>
	<script type="text/javascript">
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>';
					s+='<span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
					/* s+='<span class="icon-info" title="查询领取详情" onclick="getRecInfo(\'' + pkId + '\')"></span>'; */
					s+='<span class="icon-Get-into" title="投放" onclick="putInRow(\'' + pkId + '\')"></span>';
			return s;
		}
		
		function onTicketTypeRender(e){
			var record = e.record;
			var codeType=record.codeType;
			var s="";
			if(codeType=="CODE_TYPE_TEXT"){
				s="文本";
			}else if(codeType=="CODE_TYPE_BARCODE"){
				s="一维码";
			}else if(codeType=="CODE_TYPE_QRCODE"){
				s="二维码";
			}else if(codeType=="CODE_TYPE_ONLY_QRCODE"){
				s="二维码无code显示";
			}else if(codeType=="CODE_TYPE_ONLY_BARCODE"){
				s="一维码无code显示";
			}else if(codeType=="CODE_TYPE_NONE"){
				s="无code无类型";
			}
			return s;
		}
		function addTicket(){
			_OpenWindow({
	    		url: "${ctxPath}/wx/core/wxTicket/memberCardCreate.do?pubId=${param.pubId}",
	            title: "添加卡券", width: 800, height:800,
	            ondestroy: function(action) {
	            	if (action == 'ok') {
	                    grid.reload();
	                }
	            }
	    	});
		}
	    var htmlContent = document.getElementById("htmlContent");
	    function htmlClick(val) {
	        var clone = htmlContent.cloneNode(true);
	        clone.style.display = "";
	       	clone.style.marginBottom="-30px";
	     	clone.src=val;
	        mini.showMessageBox({ 
	            width: 350,
	            height:500,
	            title: "卡券二维码",
	            buttons: ["ok"],
	            message: "自定义Html",
	            html: clone,
	            showModal: false,
	            callback: function (action) {
	            }
	        });
	    }
	    
		
		function putInRow(pkId){
			$.ajax({
				url:"${ctxPath}/wx/core/wxTicket/putInWxTicket.do",
				type:"post",
				data:{pubId:"${pubId}",cardId:pkId},
				success:function(result){
					htmlClick(result.show_qrcode_url);
				}
			});
		}
		
		
		
		function createLandingPage(){
			 var rows = grid.getSelecteds();
		        if (rows.length <= 0) {
		        	alert("请选中一条记录");
		        	return;
		        }
		        var ids = [];
		        for (var i = 0, l = rows.length; i < l; i++) {
		            var r = rows[i];
		            ids.push(r.id);
		        }
		        _UploadFileDlg({
					from:'SELF',
					types:"Image",
					single:true,
					onlyOne:true,
					showMgr:false,
					title:'上传横幅图片',
					callback:function(files){
						$.ajax({
				        	url:"${ctxPath}/wx/core/wxTicket/createLandingPage.do",
				        	type:"post",
				        	data:{ids: ids.join(','),pubId:'${param.pubId}',fileId:files[0].fileId},
				        	success:function(result){
				        		if(result.success){
				        			$("#landingPage").html("<input type='text' value='"+result.url+"' style='margin-top:220px;width:220px;'>");
				        			var landingPage = document.getElementById("landingPage");
				        			var clone = landingPage.cloneNode(true);
				        	        clone.style.display = "";

				        	        mini.showMessageBox({
				        	            width: 300,
				        	            height:200,
				        	            title: "请手动复制到微信",
				        	            buttons: ["确定"],
				        	            message: "url",
				        	            html: clone,
				        	            showModal: false,
				        	            callback: function (action) {
				        	            }
				        	        });
				        			grid.load();
				        		}
				        		
				        	}
				        });
					}
				});
		}
		
		/*废除卡券*/
		function unVailableTicket(){
			mini.prompt("请输入卡券code：", "请输入",
		            function (action, value) {
		                if (action == "ok") {
		                	$.ajax({
		        				url:"${ctxPath}/wx/core/wxTicket/unVailableTicket.do",
		        				type:"post",
		        				data:{pubId:"${param.pubId}",code:value},
		        				success:function(result){
		        					if(result.success){
		        						mini.alert("已废除卡券");
		        					}else{
		        						mini.alert("废除失败,原因:"+result.errMsg);
		        					}
		        				}
		        			});
		                } 

		            }
		        );
		}
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.wx.core.entity.WxTicket" winHeight="450"
		winWidth="700" entityTitle="微信卡券" baseUrl="wx/core/wxTicket" />
</body>
</html>