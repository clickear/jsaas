<#assign colSize=colsJson?size>
<#assign searchSize=querySearchJson?size>

<#assign searchName="">

<#list querySearchJson as queryItem>
	<#if (queryItem_index==0)>
		<#assign searchName=getSearchParams(queryItem)>
	</#if>
</#list>

<#function getSearchParams item>
<#assign rtn>Q_${item.fieldName}_${item.queryDataType}_${item.fieldOp}</#assign>
 <#return rtn>
</#function>
<yd-layout>
	<yd-navbar slot="navbar" title="${sysBoList.name}">
      <a href="javascript:;" v-on:click="goBack" slot="left">
        <yd-navbar-back-icon></yd-navbar-back-icon>
      </a>
    </yd-navbar>
    <#if (searchSize>0) >
    <rx-search slot="top" v-model="search.${searchName}" v-on:input="handSearch" <#if (searchSize >1) > :senior="true" </#if><#if (searchSize>1) > v-on:showsenior="showSenior()" </#if> ></rx-search>
    </#if>
    <!--参数-->
    <#if (searchSize>1) >
    <rx-searchpanel v-on:search="handSearch" ref="searchPanel" >
    	<#list querySearchJson as item>
    	<div class="search-params">
    		<div class="title">
    			${item.fieldLabel}
    		</div>
    		<div class="content">
    			<#if (item.fc=="mini-textbox") >
    				<input  v-model="search.${getSearchParams(item)}"  placeholder="请输入${item.fieldLabel}"/>
    			<#elseif (item.fc=="mini-datepicker") >
    				<yd-datetime type="date" v-model="search.${getSearchParams(item)}"  placeholder="请输入${item.fieldLabel}" ></yd-datetime>
    			<#elseif (item.fc=="mini-combobox") >
    				<#if item.from??>
	    				<#switch item.from>
	    					<#case "self">   
	    						<rx-combo v-model="search.${getSearchParams(item)}" 
	    							conf='{from:"self",data:${SysBoListUtil.removeByKeys(item.selOptions,'_id,_uid,_state')}}' ></rx-combo>
	    					<#break>
	    					<#case "url">   
	    						<rx-combo v-model="search.${getSearchParams(item)}" 
	    							conf='{from:"url",textfield:"${item.url_textfield}",valuefield,"${item.url_valuefield}",url:"${item.url}"}' ></rx-combo>
	    					<#break> 
	    					<#case "dic">   
	    						<rx-combo v-model="search.${getSearchParams(item)}" 
	    						conf='{from:"dic",dic:"${item.dickey}"}' ></rx-combo>
	    					<#break> 
	    					<#case "sql">   
	    						<rx-combo v-model="search.${getSearchParams(item)}" 
	    							conf='{from:"sql",sql:"${item.sql}", data_options:{sql:{sql:"${item.sql}"}},textfield:"${item.sql_textfield}",valuefield:"${item.sql_valuefield}"}' ></rx-combo>
	    					<#break> 
	    					  
	    				</#switch>
    				</#if>
    			<#elseif (item.fc=="mini-dialog") >
    				<rx-btnedit v-model="search.${getSearchParams(item)}" :conf='{dialogalias:"${item.dialogalias}",valueField:"${item.valueField}",textField:"${item.textField}"}'></rx-btnedit>
    			</#if>
    		</div>
    	</div>
		</#list>
    </rx-searchpanel>
    </#if>
    <!-- 列表字段 -->
    <yd-pullrefresh :on-infinite="loadList" ref="pullrefreshList_">
    		<div v-for="item in list" v-if="list && list.length>0" class="custom-list">
    			<div class="head" <#if (colSize>0) >  v-on:click="toggle(item)" </#if> >
    				<#assign item=mobileCols[0]>
    				
					<div class="head-content">
						<#if (sysBoList.multiSelect=="true")>
							<rx-check v-model="selectIds" :val="item.${sysBoList.idField}">{{item.${item.field}}}</rx-check>
						<#else>
							<rx-radio name="selectId" :val="item.${sysBoList.idField}" v-model="selectIds">{{item.${item.field}}}</rx-radio>
						</#if>
					</div>
	    			
	    			<#if (colSize>0) >
	    			<i  v-bind:class="{ rotated: item.expand }"></i>
	    			</#if>
	    		</div>
	    		<#if (colSize>0) >
	    		<div class="body" v-show="item.expand">
	 	    			<#list mobileCols as item>
  					<#if (item_index>0)>
    						<#if (item.field=='INST_ID_')>
	    						<div class="body-item" v-if="item.${item.field}">
		    						<div class="title">流程实例</div>
			    					<div class="content">
			    						<router-link :to="{name:'getInstInfo', params: { instId: item.${item.field},type:'inst'  }}" >流程明细</router-link>
			    					</div>	
		    					</div>
		    				<#elseif (item.field=='INST_STATUS_')>
	    						<div class="body-item">
		    						<div class="title">状态</div>
			    					<div class="content">
			    						{{getStatusDisplay(item.INST_STATUS_)}}
			    					</div>	
		    					</div>
	    					<#else>
	    						<div class="body-item">
		    						<div class="title">${item.header}</div>
			    					<div class="content">
			    						{{item.${item.field}}}
			    					</div>	
		    					</div>
	    					</#if>
	    				</#if>
	
	    			</#list>
	    		</div>
	    		</#if>
    		</div>
    </yd-pullrefresh>
	<rx-nodata :amount="list.length"></rx-nodata>
	
	<#if (sysBoList.isDialog=='YES') >
	<yd-tabbar slot="tabbar">
		<tabbar-item title="确定" name="btnOk" type="a" v-on:handclick="clickOk()"  icon="yd-icon-ok" :active="true"></tabbar-item>
		<tabbar-item title="取消" name="btnClose" type="a" v-on:handclick="clickClose()"  icon="yd-icon-close" ></tabbar-item>
	</yd-tabbar>
	<#else>
		<#if (sysBoList.formAlias ?? && sysBoList.formAlias?length>0) >
			<yd-tabbar slot="tabbar">
		      <tabbar-item title="添加" name="add" type="a"  icon="yd-icon-add-user" v-on:handclick="formAdd()"   :active="true"></tabbar-item>
		      <tabbar-item title="编辑" name="edit" type="a"  icon="yd-icon-edit"   v-on:handclick="formEdit()"   ></tabbar-item>
		      <tabbar-item title="删除" name="del" type="a"  icon="yd-icon-remove"  v-on:handclick="formDel()" ></tabbar-item>
	    	</yd-tabbar>
    	</#if>
	</#if>
	
</yd-layout>
<script>
//参数
var params_json={
	search:{
		<#list querySearchJson as queryItem>
			<#if !queryItem_has_next>"${getSearchParams(queryItem)}":""<#else>"${getSearchParams(queryItem)}":"",</#if>
		</#list>
	},
	result:[<#list fieldsJson as item><#if !item_has_next>"${item.field}"<#else>"${item.field}",</#if></#list>]
};

</script>