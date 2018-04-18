<!DOCTYPE html >
<html>
<head>
    <title>${name}</title>
	<script type="text/javascript">
		var __rootPath='<#noparse>${ctxPath}</#noparse>';
	</script>
	<link href="<#noparse>${ctxPath}</#noparse>/styles/skin/default/index.css" rel="stylesheet" type="text/css" /> 
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
	<style type="text/css">
		.mini-layout-border>#center{
	 		background: transparent;
		}
	</style>
</head>
<body >   
<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
 	<#if isDialog=="YES">
		<div region="south" showSplit="false" showHeader="false" height="40" showSplitIcon="false"  
	     style="width:100%" bodyStyle="border:0;text-align:center;padding-top:5px;">
			   	<a class="mini-button" iconCls="icon-ok"   onclick="CloseWindow('ok');">确定</a>
		    	<a class="mini-button" iconCls="icon-cancel"  onclick="CloseWindow();">取消</a> 
		 </div> 
	</#if>
 	<#if isLeftTree=="YES">
	    <div showHeader="true" title="${leftNav}" region="west" width="220" maxWidth="250" minWidth="150" showCollapseButton="false" showProxy="false" expanded="false" showSplitIcon="true">
	        <div class="mini-fit ">
		        <div id="tabs1" class="mini-tabs" tabAlign="left" tabPosition="bottom" activeIndex="0" style="100%;height:100%;" bodyStyle="background-color:white;border:none;" plain="false">
				    <#list treeConfigs as treeConfig>
					    <div title="${treeConfig.tabName}" >
					         <ul id="${treeConfig.treeId}" class="mini-tree" url="<#noparse>${ctxPath}</#noparse>/dev/cus/customData/${sysBoList.key}/${treeConfig.treeId}/getTabTreeJson.do" style="width:100%;" 
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
      <div region="center" <#if isLeftTree=="YES"> showHeader="false"  </#if> title="${name}" showCollapseButton="false" >
     	<div class="titleBar mini-toolbar">
	         	<ul>
					<#list topButtonJson as item>
						<li>
	                 		<#if item.url??>
	                 		<#noparse><#if (sysBoListRs.isAllowBtn</#noparse>('${item.url}')=="YES")<#noparse>></#noparse>
	                 		</#if>
	                 		<#if item.children?exists && (item.children?size >0)>
	                 		<ul id="${item.btnName}Menu" class="mini-menu" style="display:none;">
	                 			<#list item.children as menu>
	                 			<li iconCls="${menu.btnIconCls}" <#if item.url??>data-options="{url:'${item.url}'}" </#if> onclick="${menu.btnClick}">${menu.btnLabel}</li>
	                 			</#list>
					        </ul>
	                 		<a id="${item.btnName}" class="mini-menubutton"  iconCls="${item.btnIconCls}"  menu="#${item.btnName}Menu">${item.btnLabel}</a>
	                 		<#else>
	                 		<a id="${item.btnName}" class="mini-button" <#if item.url??>data-options="{url:'${item.url}'}" </#if>  iconCls="${item.btnIconCls}" onclick="${item.btnClick}">${item.btnLabel}</a>
	                 		</#if>
	                 		<#if item.url??>
	                 		<#noparse></#if></#noparse>
	                 		</#if>
                 		</li>
                 	</#list>
                 	<li id="rapidSearchForm">
	                 	${rapidSearchHtml}
	                </li>
	                <#if (rapidSerchJson?size>0)>
	                	 	<li><a class="mini-button" iconCls="icon-search" onclick="onRapidSearch()">快速搜索</a></li>
	                	</#if>
					<li class="clearfix"></li>
				</ul>
				<div class="searchBox">
					<div id="searchForm" class="search-form" style="display: none;">
                    	${searchHtml}
                    </div>
					<span class="searchSelectionBtn" onclick="no_search(this ,'searchForm')">
						<i class="icon-sc-lower"></i>
					</span>
				</div>
	     </div>
	    
     	<div class="mini-fit rx-grid-fit">
     	<#if isShare=="YES">
	        <div id="datagrid1" class="${controlClass}" style="width: 100%; height: 100%;" allowResize="false" allowResize="false" expandOnLoad="true"
	        	url="<#noparse>${ctxPath}</#noparse>/dev/cus/customData/share/${sysBoList.key}/getData.do<#noparse>${query}</#noparse>" idField="${sysBoList.idField}" 
	        	<#noparse>
		        	<#if params?? && params.single??>
		        		multiSelect="${(params.single=="true")?string('false','true')}"
		        	<#else>
		        </#noparse>
		        		multiSelect="${sysBoList.multiSelect}"
		        <#noparse>
		        	</#if>	  
	        	</#noparse>
	        	<#if showFroCol=="true">
	        		frozenStartColumn="${sysBoList.startFroCol}" frozenEndColumn="${sysBoList.endFroCol}"
	        	</#if>
	        	<#if sysBoList.showSummaryRow=="YES">
	        	showSummaryRow="true" 
	        	</#if>
	        	showColumnsMenu="true" parentField="${sysBoList.parentField}" treeColumn="${sysBoList.textField}"
	        	showTreeIcon="true" resultAsTree="false"  
	        	<#if sysBoList.rowEdit=="YES">
	        		allowCellEdit="true" allowCellSelect="true"
	        		allowCellValid="true" oncellvalidation="onCellValidation"
	        	</#if>
	        	sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true">
						${gridColumns}
			</div>
		<#elseif isShare=="NO">
			<div id="datagrid1" class="${controlClass}" style="width: 100%; height: 100%;" allowResize="false" 
	        	url="<#noparse>${ctxPath}</#noparse>/dev/cus/customData/${sysBoList.key}/getData.do<#noparse>${query}</#noparse>" idField="${sysBoList.idField}"
	        	<#noparse>
	        		<#if params?? && params.single??>
	        		multiSelect="${(params.single=="true")?string('false','true')}"
	        	</#noparse>
	        	<#noparse>
	        	<#else>
	        	</#noparse>
	        		multiSelect="${sysBoList.multiSelect}"
	        	<#noparse>
	        	</#if>
	        	</#noparse>
	        	<#if showFroCol=="true">
	        		frozenStartColumn="${sysBoList.startFroCol}" frozenEndColumn="${sysBoList.endFroCol}"
	        	</#if>
	        	<#if sysBoList.showSummaryRow=="YES">
	        	showSummaryRow="true" 
	        	</#if>
	        	parentField="${sysBoList.parentField}" treeColumn="${sysBoList.textField}" expandOnLoad="true"
	        	showTreeIcon="true" resultAsTree="false"
	        	<#if sysBoList.rowEdit=="YES">
	        		allowCellEdit="true"
	        		allowCellSelect="true"
	        		allowCellValid="true" oncellvalidation="onCellValidation" 
	        	</#if>
	        	sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true">
						${gridColumns}
			</div>
		</#if>
		</div>
    </div>
</div>

    
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
			var data=searchForm.getData(true);
			_ConvertFormData(data);
			grid.load(data);
		}
		
		//快速搜索
		function onRapidSearch(){
			var f=new mini.Form('rapidSearchForm');
			var data=f.getData(true);
			_ConvertFormData(data);
			grid.load(data);
		}
		
		//---------------开始生成服务器自定义按钮-------------
		 	<#list topButtonJson as item>
         		<#if item.scriptButton??>
	         		function ${item.btnClick}(e){
	         			var url=e.sender.url;
	         			var rows=grid.getSelecteds();
	         			_SubmitJson({
	         				url:__rootPath+url,
	         				data:{
	         					data:mini.encode(rows)
	         				},
	         				method:'POST',
	         				success:function(result){
	         					grid.load();
	         				}
	         			});
	         		}
         		</#if>
	        </#list>
		 //-------------结束生成服务器自定义按钮-------------
		
		 grid.on("drawcell", function (e) {
		   var record = e.record,
		   field = e.field,
		   value = e.value;
		  
            
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
		   //_LoadUserInfo();
		    <#if enableFlow=="YES">
		   //_LoadTaskInfo();
		   </#if>
		 });
		 
		 grid.on('rowdblclick',function(e){
		 	if('${sysBoList.formAlias}'=='' || '${sysBoList.rowEdit}'=='YES'){
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
 
		grid.on('cellendedit',function(e){
			if(e.column.displayfield && e.editor.getText && e.editor.getText()){
				e.record[e.column.displayfield]=e.editor.getText();
			}
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
		
		function onCellValidation(e){
			
			var col=e.column;
			var editor=col.editor;
			if(!editor){
				return;
			}
			if(editor.setValue && editor.validate){
				editor.setValue(e.value);
				editor.validate();
				if(!editor.isValid()){
					e.isValid = false;
					e.errorText='字段值不合法！';
				}
				editor.setValue('');
			}
			
		}
		
		function onClose(){
			CloseWindow();
		}
		
		function onRemove(e){
			var row=grid.getSelecteds();
			if(row.length==0){
			   alert('请选择表格行');
			   return;
			}
			
			var url="/sys/customform/sysCustomFormSetting/remove/${sysBoList.formAlias}.do";
			if(e.sender && e.sender.url){
				url=e.sender.url;
			}
			url=__rootPath+url;
			mini.confirm("确定删除吗?", "提示信息", function(action){
                if (action != "ok")  return;
				var ids = [];
				
				for(var i=0; i < row.length; i++){
					if(row[i]['${sysBoList.idField}']){
						ids.push(row[i]['${sysBoList.idField}']);
					}else{
						<#if controlClass=='mini-treegrid'>
						grid.removeNode(row[i]);
						<#else>
						grid.removeRow(row[i]);
						</#if>
					}
				}
				
				if(ids.length>0){
					_SubmitJson({url:url,method:"POST",data:{id:ids.join(',')},success:function(){
						grid.load();
					}}) ;
				}  
            })
		}
		
		//返回选择的数据
		function getData(){
			var single=${sysBoList.multiSelect};
			<#noparse>
	        	<#if params?? && params.single??>
	        		single=${params.single};
	        	</#if>
	        </#noparse>
			var rows=grid.getSelecteds();
			var data={rows:rows,single:single};
			return data;
		}
		<#if isDialog!="YES">
		function onAdd(e){
			
			//行编辑
			if('${sysBoList.rowEdit}'=='YES'){
				if('${sysBoList.dataStyle}'=='tree'){
	            	grid.addNode({}, "add", null);
				}else{
					grid.addRow({});
				}
				return;
			}
		
			if('${sysBoList.formAlias}'==''){
				alert('请联系管理员配置行的表单方案！');
				return ;
			}
			
			var url="/sys/customform/sysCustomFormSetting/form/${sysBoList.formAlias}.do";
			if(e.sender && e.sender.url){
				url=e.sender.url;
			}

			_OpenWindow({
				title:'${sysBoList.name}--添加',
				height:400,
				width:800,
				max:true,
				url:__rootPath+url,
				ondestroy:function(action){
					if(action!='ok'){
						return;
					}
					grid.load();
				}
			});
		}
		
		//对于树型表格才适用
		function onAddSub(){
			var node = grid.getSelectedNode();
            grid.addNode({}, "add", node);
		}
		
		function onRefresh(){
			grid.load();
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
			
			var url="/sys/customform/sysCustomFormSetting/form/${sysBoList.formAlias}/{pk}.do";
			if(e.sender && e.sender.url){
				url=e.sender.url;
			}
			url=url.replace('{pk}',row['${sysBoList.idField}']);
			
		   _OpenWindow({
				title:'${sysBoList.name}--编辑',
				height:400,
				width:800,
				max:true,
				url:__rootPath+url,
				ondestroy:function(action){
					if(action!='ok'){return;}
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
			
			var url="/sys/customform/sysCustomFormSetting/detail/${sysBoList.formAlias}/{pk}.do";
			if(e.sender && e.sender.url){
				url=e.sender.url;
			}
			url=url.replace('{pk}',row['${sysBoList.idField}']);
			
		   _OpenWindow({
				title:'${sysBoList.name}--明细',
				height:400,
				width:800,
				max:true,
				url:__rootPath+url,
				ondestroy:function(action){
					if(action!='ok'){
						return;
					}
				}
			});
		}
		
		function onExpand(e){
			grid.expandAll();
		}
		
		function onCollapse(e){
			grid.collapseAll();
		}
		
		//多行的数据保存
		function onRowsSave(e){
			grid.validate();
			
			if(!grid.isValid()){
				return;
			}
			
			var saveUrl=e.sender.url;
			//获得修改的数据
			var rows=grid.getChanges(null,true);
			//alert(mini.encode(rows));
			//更新bo对应的业务对象
			
			_SubmitJson({
				url:__rootPath+ saveUrl,
				method:"POST",
				data:{rows:mini.encode(rows)},
				success:function(){
					grid.load();
				}
			});
		}
		
		function onXLSExport(e){
	        var boKey = '${sysBoList.key}';
	        
	        var url="/sys/core/sysBoList/{boKey}/exportExcelDialog.do";
			if(e.sender && e.sender.url){
				url=e.sender.url;
			}
			url=url.replace('{boKey}',boKey);
	        _OpenWindow({
				title:'${sysBoList.name}导出EXCEL表单',
				height:600,
				width:800,
				url:__rootPath+url,
				ondestroy:function(action){
					if(action!='ok'){
						return;
					}
				}
			});
	        
	    }
	    
	    function onImport(){
	        var boKey = '${sysBoList.key}';
	        _OpenWindow({
				title:'${sysBoList.name}导入EXCEL表单',
				height:600,
				width:800,
				url:__rootPath+'/sys/core/sysBoList/importExcelDialog.do?boKey='+boKey,
				ondestroy:function(action){
					if(action!='ok'){
						grid.reload();
					}
				}
			});
	        
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