<!DOCTYPE html >
<html>
<head>
    <title>${name}</title>
	
	
	<script type="text/javascript">
		var __rootPath='<#noparse>${ctxPath}</#noparse>';
	</script>
	<link href="/jsaas/styles/skin/default/index.css" rel="stylesheet" type="text/css" /> 
	
	<link href="<#noparse>${ctxPath}</#noparse>/styles/commons.css" rel="stylesheet" type="text/css" />
	<link href="<#noparse>${ctxPath}</#noparse>/styles/list.css" rel="stylesheet" type="text/css" />
	<script src="<#noparse>${ctxPath}</#noparse>/scripts/mini/boot.js" type="text/javascript"></script>
	<script src="<#noparse>${ctxPath}</#noparse>/scripts/share.js" type="text/javascript"></script>
	
	<link href="<#noparse>${ctxPath}</#noparse>/styles/skin/default/index.css" rel="stylesheet" type="text/css" /> 
	<script src="<#noparse>${ctxPath}</#noparse>/scripts/common/list.js" type="text/javascript"></script>
	<script src="<#noparse>${ctxPath}</#noparse>/scripts/common/form.js" type="text/javascript"></script>
	<link href="<#noparse>${ctxPath}</#noparse>/styles/cover_list.css" rel="stylesheet" type="text/css" />
	

	<#function getUrl sysBoList>
		<#if sysBoList.url??>
			<#assign rtn>${sysBoList.url}</#assign>
		<#else>
			<#assign rtn><#noparse>${ctxPath}</#noparse>/dev/cus/customData/${sysBoList.key}/getData.do</#assign>
		</#if>
		 <#return rtn>
	</#function>
	<#-- 构建外部传入参数 -->
	<#noparse>
		<#assign query="">
		<#if params??&&(params?size>0)>
			<#assign query="?">
			<#assign  keys=params?keys/>
			<#list keys as key>
				<#if (key_index==0)>
					<#assign query=query + key +"=" + params[key] >		
				<#else>
					<#assign query=query +"&" +key +"=" + params[key] >
				</#if>
			</#list>
		</#if>
	</#noparse>
</head>
<body >   
<div id="layout1" class="mini-layout" style="width:100%;height:100%;">

 	<#if isLeftTree=="YES">
	    <div showHeader="true" title="${leftNav}" region="west" width="220" maxWidth="250" minWidth="150" >
	        <div class="mini-fit ">
		        <div id="tabs1" class="mini-tabs" tabAlign="left" tabPosition="bottom" activeIndex="0" style="100%;height:100%;" plain="false">
				    <#list treeConfigs as treeConfig>
					    <div title="${treeConfig.tabName}" >
					         <ul id="${treeConfig.treeId}" class="mini-tree" url="<#noparse>${ctxPath}</#noparse>/dev/cus/customData/getTreeJson.do?ds=${treeConfig.ds}&sql=${treeConfig.sql}" style="width:100%;" 
								parentField="${treeConfig.parentField}" textField="${treeConfig.textField}" idField="${treeConfig.idField}" expandOnLoad="${treeConfig.expandOnLoad}"
								resultAsTree="false" showTreeIcon="true"  
				                onnodeclick="${treeConfig.onnodeclick}">
				             </ul>
					    </div>
				    </#list>
				</div>
			</div>
	    </div>
     </#if>
      <div region="center" <#if isLeftTree=="YES"> showHeader="true"  </#if> title="${name}" showCollapseButton="false" >
     	<div class="mini-toolbar" >
	         <table style="width:100%;">
	             <tr>
	                 <td style="width:100%;">
	                 	<#list topButtonJson as item>
	                 		<#noparse><#if right</#noparse>.${item.btnName} <#noparse>></#noparse>
	                 		<#if item.children?exists>
	                 		<ul id="${item.btnName}Menu" class="mini-menu" style="display:none;">
	                 			<#list item.children as menu>
	                 			<li iconCls="${menu.btnIconCls}" onclick="${menu.btnClick}">${menu.btnLabel}</li>
	                 			</#list>
					        </ul>
	                 		<a id="${item.btnName}" class="mini-menubutton"  iconCls="${item.btnIconCls}"  menu="#${item.btnName}Menu">${item.btnLabel}</a>
	                 		<#else>
	                 		<a id="${item.btnName}" class="mini-button"  iconCls="${item.btnIconCls}" onclick="${item.btnClick}">${item.btnLabel}</a>
	                 		</#if>
	                 		<#noparse></#if></#noparse>
	                 	</#list>
	                    <a class="mini-button" iconCls="icon-search"  onclick="onSearch()">查询</a>
                     	<a class="mini-button" iconCls="icon-cancel"  onclick="onClear()">清空查询</a>
                     	<#if isDialog=="YES">
                     	<span class="separator"></span>
                     	<a class="mini-button" iconCls="icon-ok"   onclick="CloseWindow('ok');">确定</a>
		    			<a class="mini-button" iconCls="icon-cancel"  onclick="CloseWindow();">取消</a>
		    			</#if>
	                 </td>
	             </tr>
	              <tr>
	                  <td  class="search-form" style="white-space:nowrap;">
	                   	<div id="searchForm">
	                    	${searchHtml}
	                    </div>
	                  </td>
	              </tr>
	         </table>
	     </div>
	    
     	<div class="mini-fit rx-grid-fit">
     	<#if isShare=="YES">
	        <div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" 
	        	url="<#noparse>${ctxPath}</#noparse>/dev/cus/customData/share/${sysBoList.key}/getData.do<#noparse>${query}</#noparse>" idField="${sysBoList.idField}" multiSelect="${sysBoList.multiSelect}" showColumnsMenu="true" 
	        	sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true">
						${gridColumns}
			</div>
		<#elseif isShare=="NO">
			<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" 
	        	url="<#noparse>${ctxPath}</#noparse>/dev/cus/customData/${sysBoList.key}/getData.do<#noparse>${query}</#noparse>" idField="${sysBoList.idField}" multiSelect="${sysBoList.multiSelect}" showColumnsMenu="true" 
	        	sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true">
						${gridColumns}
			</div>
		</#if>
		</div>
    </div>
</div>
<form name="hiform" id="hiform" action="${ctxPath}/sys/core/sysBoList/exportExcel.do" method="post">
		<input id="columns" type="hidden" name="columns">
		<input id="boKey" type="hidden" name="boKey">
</form>

    
    <script type="text/javascript">
        mini.parse();
		var grid=mini.get('datagrid1');
		grid.load();
		var searchForm=new mini.Form('searchForm');
		<#if isLeftTree=="YES">
		 <#list treeConfigs as treeConfig>
		 	 <#if treeConfig.onnodeclick !='' >
			 	function ${treeConfig.onnodeclick}(e){
					var node=e.node;
					var url=grid.getUrl();
					var index=url.indexOf('?');
					if(index!=-1){
						url=url.substring(0,index);
					}
					grid.setUrl(url + '?${treeConfig.paramName}='+ node.${treeConfig.idField});
					grid.load();
				}
			</#if>
		 </#list>
		</#if>
		<#if sysBoList.bodyScript??>
			${sysBoList.bodyScript}
		</#if>
		function onSearch(){
			var data=searchForm.getData();
			_ConvertFormData(data);
			grid.load(data);
		}
		
		 grid.on("drawcell", function (e) {
		   var record = e.record,
		   field = e.field,
		   value = e.value;
		   if(field=='CREATE_BY_' || field=='UPDATE_BY_' || field=='CREATE_USER_ID_'){
		     if(value){
		     	e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>';
		     }else{
		     	e.cellHtml='<span style="color:red">无</span>';
		     }
		   }
		   <#if enableFlow=="YES">
		   //用一列显示审批明细项
            if (field== "INST_ID_") {
            	if(record.INST_ID_!='' && record.INST_STATUS_=='RUNNING'){
            		e.cellHtml='<span class="mini-taskinfo" iconCls="icon-check" instId="'+record.INST_ID_+'"></span>';
            	}else if(record.INST_ID_!=''){
            		e.cellHtml='<a href="${ctxPath}/bpm/core/bpmInst/inform.do?instId="'+ record.INST_ID_ + ' target="_blank">审批明细</a>';
            	}else{
            		e.cellHtml='';
            	}
            }
            </#if>
            
            <#list urlColumns as col>
             if (field== "${col.field}") {
	             var url=getUrlFromRecord('${col.url}',record);
	             if(value){
	             	e.cellHtml='<a href="javascript:void(0);" onclick="showLink(\''+value+'\',\'${col.field}\',\''+url+'\',\'${col.urlType}\');">'+value+'</a>';
	             }
             }
            </#list>
            
            ${drawCellScript}
		 });
		
		 grid.on('update',function(){
		   _LoadUserInfo();
		    <#if enableFlow=="YES">
		   _LoadTaskInfo();
		   </#if>
		 });
		 
		 grid.on('rowdblclick',function(e){
		 	if('${sysBoList.formAlias}'==''){
				return ;
			}
			var record=e.record;
			
			if(record==null){
			   alert('请选择表格行');
			   return;
			}
			
		   _OpenWindow({
				title:'${sysBoList.name}--明细',
				height:400,
				width:800,
				max:true,
				url:__rootPath+'/sys/customform/sysCustomFormSetting/detail/${sysBoList.formAlias}/'+record.${sysBoList.idField}+'.do',
				ondestroy:function(action){
					if(action!='ok'){
						return;
					}
				}
			});
		 });
		 
		function getUrlFromRecord(url,record){
		
            url=url.replace('{ctxPath}',__rootPath);
            for(var field in record){
            	url=url.replace('{'+field+'}',record[field]);
            }
            return url;
		}
		
		function onClear(){
			searchForm.clear();
			var url=grid.getUrl();
			var index=url.indexOf('?');
			if(index!=-1){
				url=url.substring(0,index);
			}
			grid.setUrl(url);
			grid.load();
		}
		
		function onClose(){
			CloseWindow();
		}
		
		function onRemove(){
			var row=grid.getSelecteds();
			if(row==null){
			   alert('请选择表格行');
			   return;
			}
			
			mini.confirm("确定删除吗?", "提示信息", function(action){
                if (action != "ok")  return;
            	var url=__rootPath+'/sys/customform/sysCustomFormSetting/remove/${sysBoList.formAlias}.do';
				var ids = [];
				for(var i=0; i < row.length; i++){
					ids.push(row[i].${sysBoList.idField});
				}
				_SubmitJson({url:url,method:"POST",data:{id:ids.join(',')},success:function(){
					grid.load();
				}})    
            })
		}
		
		//返回选择的数据
		function getData(){
			var rows=grid.getSelecteds();
			return rows;
		}
		<#if isDialog!="YES">
		function onAdd(e){
			if('${sysBoList.formAlias}'==''){
				alert('请联系管理员配置行的表单方案！');
				return ;
			}
			_OpenWindow({
				title:'${sysBoList.name}--添加',
				height:400,
				width:800,
				max:true,
				url:__rootPath+'/sys/customform/sysCustomFormSetting/form/${sysBoList.formAlias}.do',
				ondestroy:function(action){
					if(action!='ok')	return;
					
					grid.load();
				}
			});
		}
		
		function onEdit(e){
			if('${sysBoList.formAlias}'==''){
				alert('请联系管理员配置行的表单方案！');
				return ;
			}
			var row=grid.getSelected();
			
			if(row==null){
			   alert('请选择表格行');
			   return;
			}
			
		   _OpenWindow({
				title:'${sysBoList.name}--编辑',
				height:400,
				width:800,
				max:true,
				url:__rootPath+'/sys/customform/sysCustomFormSetting/form/${sysBoList.formAlias}/'+row.${sysBoList.idField}+'.do',
				ondestroy:function(action){
					if(action!='ok')	return;
					grid.load();
				}
			});
		}
		
		function onDetail(e){
			if('${sysBoList.formAlias}'==''){
				alert('请联系管理员配置行的表单方案！');
				return ;
			}
			var row=grid.getSelected();
			
			if(row==null){
			   alert('请选择表格行');
			   return;
			}
			
		   _OpenWindow({
				title:'${sysBoList.name}--明细',
				height:400,
				width:800,
				max:true,
				url:__rootPath+'/sys/customform/sysCustomFormSetting/detail/${sysBoList.formAlias}/'+row.${sysBoList.idField}+'.do',
				ondestroy:function(action){
					if(action!='ok'){
						return;
					}
				}
			});
		}
		
		function onXLSExport(){
		debugger;
			var url=constructUrlParams(grid.getUrl(),{_export:true});
	        var columns = grid.getColumns();
	        var valCols = [];
	        for (var i = 0; i < columns.length; i++) {
	            var col = columns[i];
	            if (col.type == 'checkcolumn' || col.name == 'checkcolumn') {
	                continue;
	            }
	            valCols.push(col);
	        }
	        $("#columns").val(mini.encode(valCols));
	        $("#boKey").val('${sysBoList.key}');
	        document.getElementById("hiform").submit();
	        
	    }
		</#if>
		
		function showLink(value,field,url,linkType){
			if(linkType=='tabWindow'){
				top['index'].showTabFromPage({
        			tabId:'${sysBoList.formAlias}_'+field,
        			title:value + '-信息',
        			url:url
        		});
			}else if(linkType=="newWindow"){
				 window.open(url); 
			}else{
				 _OpenWindow({
					title:value,
					height:400,
					width:800,
					max:true,
					url:url
				});
			}
		}
    </script>

</body>
</html>