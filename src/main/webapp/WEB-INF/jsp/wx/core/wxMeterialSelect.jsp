<%-- 
    Document   : [微信素材]列表页
    Created on : 2017-07-11 16:03:13
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>微信素材管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
 
	 <div class="mini-toolbar" > 
         素材名:<input class="mini-textbox" name="Q_NAME__S_LK">
						<a class="mini-button" iconCls="icon-search" plain="true" onclick="searchFrm()">查询</a>
                     <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="clearForm()">清空查询</a>
     </div>
	
	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 90%;" allowResize="false"
			url="${ctxPath}/wx/core/wxMeterial/listAllByType.do?pubId=${param.pubId}&type=${param.type}" idField="id"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
			<c:if test="${param.type=='article'}">
			<div type="checkcolumn" width="20"></div>
			</c:if>
				<div field="termType"  sortField="TERM_TYPE_"  width="120" headerAlign="center" allowSort="true">期限类型</div>
				<div field="mediaType"  sortField="MEDIA_TYPE_"  width="120" headerAlign="center" allowSort="true">素材类型</div>
				<div field="name"  sortField="NAME_"  width="120" headerAlign="center" allowSort="true">素材名</div>
				<div field="mediaId"  sortField="MEDIA_ID_"  width="120" headerAlign="center" allowSort="true">微信后台指定ID</div>
			</div>
		</div>
		<div style="text-align: center;">
		<a class="mini-button" iconCls="icon-ok" onclick="confirmButton">确定</a> 
	<a class="mini-button" iconCls="icon-cancel" onclick="CloseWindow('cancel');">取消</a>
		</div>
	</div>
	
	
	<script type="text/javascript">
		mini.parse();
		var grid=mini.get("datagrid1");
		var meterialValue="";
		var meterialText="";
	
		function getMeterialValue(){
			var nodes=grid.getSelecteds();
			for(var i=0;i<nodes.length;i++){
				meterialValue+=nodes[i].mediaId+",";
			}
			if(meterialValue.length>0){
				meterialValue=meterialValue.substring(0,meterialValue.length-1);
			}
			return meterialValue;
		}
		function getMeterialText(){
			var nodes=grid.getSelecteds();
			for(var i=0;i<nodes.length;i++){
				meterialText+=nodes[i].name+",";
			}
			if(meterialValue.length>0){
				meterialValue=meterialValue.substring(0,meterialValue.length-1);
			}
			return meterialText;
		}
		
		function confirmButton(){
			var nodes=grid.getSelecteds();
			if(nodes.length>8){
				alert("图文消息连发最多不超过8条");
			}else{
				CloseWindow('ok');
			}
			
		}
		
		
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.wx.core.entity.WxMeterial" winHeight="450"
		winWidth="700" entityTitle="微信素材" baseUrl="wx/core/wxMeterial" />
</body>
</html>