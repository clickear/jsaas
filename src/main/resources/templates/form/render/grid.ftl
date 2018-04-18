<#assign gridId=grid.gridId>
<#assign dataoptions=grid.dataoptions>
<#assign edittype=grid.edittype>
<#assign oncellendedit=grid.oncellendedit>
<#assign fields=grid.fields>
<#assign formkey=grid.formkey>
<#assign treegrid=grid.treegrid>
<#assign treecolumn=grid.treecolumn>



<#assign endEidt=''>
<#if oncellendedit??>
	<#assign endEidt>oncellendedit="${oncellendedit}"</#assign>
</#if>

<#noparse><#assign tbright=permission_.sub.</#noparse>${gridId}<#noparse>.tbright ></#noparse>
<#--开始 隐藏-->
<#noparse><#if (!tbright.hidden )></#noparse>

<#noparse><#assign fieldsRight=permission_.sub.</#noparse>${gridId}<#noparse>.fields ></#noparse>
<#noparse><#if (!tbright.add && !tbright.edit && !tbright.del) || (edittype=='inline' && !tbright.edit) ><#assign readOnly=true></#if></#noparse>

	<div class="_initdata" grid="${gridId}"></div> 
	<#noparse><#if </#noparse>!readOnly  >
			<div class="mini-toolbar" id="tl${gridId}" >
				<#noparse><#if (tbright.add) > </#noparse>
		    	<a class="mini-button" name="addbutton" iconCls="icon-add" plain="true" onclick="_addGridRow('${gridId}','${edittype}',false)">添加</a>
		    	<#noparse></#if></#noparse>
		    	<#if (treegrid=="true")>
		    		<#noparse><#if (tbright.add) ></#noparse>
		    		<a class="mini-button" name="addbutton" iconCls="icon-add" plain="true" onclick="_addGridRow('${gridId}','${edittype}',true)">添加下级</a>
		    		<#noparse></#if></#noparse>
		    	</#if>
		    	
		    	<#if (edittype=='openwindow' || edittype=='editform')>
		    		<#noparse><#if (tbright.edit) ></#noparse>
		    		<a class="mini-button" name="editbutton" iconCls="icon-edit" plain="true" onclick="_editGridRow('${gridId}','${edittype}')">编辑</a>
		    		<#noparse></#if></#noparse>
		    		<a class="mini-button" name="editbutton" iconCls="icon-detail" plain="true" onclick="_detailGridRow('${gridId}','${edittype}')">明细</a>
		    	</#if>
		    	<#noparse><#if (tbright.del) ></#noparse>
				<a class="mini-button" name="delbutton" iconCls="icon-remove" plain="true" onclick="_delGridRow('${gridId}')">删除</a>
				<#noparse></#if></#noparse>
				
				<span class="separator"></span>
				<a class="mini-button" iconCls="icon-up" name="upbutton" plain="true" onclick="_upGridRow('grid_${gridId}','${edittype}')">上移</a>
				<a class="mini-button" iconCls="icon-down" name="downbutton" plain="true" onclick="_downGridRow('grid_${gridId}','${edittype}')">下移</a>
				
			</div>
	<#noparse><#else></#noparse><#if (edittype=='openwindow' || edittype=='editform')>
			<div class="mini-toolbar" id="tl${gridId}" >
	    		<a class="mini-button" name="editbutton" iconCls="icon-detail" plain="true" onclick="_detailGridRow('${gridId}','${edittype}')">明细</a>
	    	</div></#if>
	<#noparse>
		</#if>
	</#noparse>
	<#noparse><#assign dataoption>${util.setRequired(tbright,</#noparse>'{${dataoptions}}'<#noparse>)}</#assign></#noparse>
	
	<div name="grid_${gridId}" id="grid_${gridId}" ${endEidt}   class="<#if (treegrid=="true")>mini-treegrid<#else>mini-datagrid</#if>" 
					style="width:100%" height="auto" multiSelect="true" allowAlternating="true"
	        		allowCellValid="true" oncellvalidation="onCellValidation"   
	        		showPager="false"  allowCellWrap="true" allowCellSelect="true" 
	        		data-options="<#noparse>${dataoption}</#noparse>"
	        <#noparse><#if readOnly > </#noparse> 
				allowCellEdit="false"
			<#noparse><#else> </#noparse> 
				allowCellEdit="true"
			<#noparse>
				</#if>
			</#noparse>
			
			<#if (treegrid=="true")>
				showTreeIcon="true" resultAsTree="false"  expandOnLoad="true"
    			treeColumn="${treecolumn}" idField="ID_" parentField="PARENT_ID_"
			</#if>
			
	     >
	    <div property="columns">
	    	<div type="indexcolumn" width="20"></div>
	    	<#noparse><#if </#noparse>tableRight=="w" >
				<div type="checkcolumn" width="20"></div>
			<#noparse>
				</#if>
			</#noparse>
			
			<#list fields as field>
			<#noparse><#assign fieldRight=fieldsRight.</#noparse>${field.name} />	
			
			<#noparse><#if (fieldRight?? && fieldRight.right!='none') ></#noparse>
			<#noparse>
			<#assign require=false>
			<#if (fieldRight?? && fieldRight.require) ><#assign require=true></#if>
			</#noparse>
			
			
			<div headerAlign="center" name="${field.name}"  field="${field.name}" header="${field.comment}"
				<#if field.width?? &&  field.width!=""> width="${field.width}" </#if>
				<#if (field.format?? &&  field.format!="")   && field.datatype?? >
					<#if (field.datatype=="date")>
						dateFormat="${field.format}"
					<#elseif (field.datatype=="number")>
						numberFormat="${field.format}"
					</#if>
				</#if>
				<#if (field.visible=="false")> visible="${field.visible}" </#if>
				<#if field.dataoptions?? &&  field.dataoptions!=""> data-options="${field.dataoptions}" </#if>
				<#if field.displayfield?? &&  field.displayfield!="">displayfield="${field.displayfield}"</#if>
				vtype="<#noparse>${util.setFieldRequired(</#noparse>'${field.vtype}',<#noparse>require</#noparse>)}"
				<#if field.control?? >
					 <#if field.control=="upload-panel">renderer="onFileRender" </#if>
					 <#if field.control=="mini-img">renderer="onImgRender" </#if>
				</#if>
			>
			<#noparse><#if (fieldRight.right=='w') ></#noparse>
			<#if field.editor??  >${field.editor}</#if>
			<#noparse></#if></#noparse>
			</div>
			<#noparse></#if></#noparse>
			</#list>
	    </div>
	</div>
<#--结束隐藏-->
<#noparse></#if></#noparse>