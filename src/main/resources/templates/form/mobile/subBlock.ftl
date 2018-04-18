<div class="div-form" v-if="hasRight('${ent.name}')">
	
	<div class="caption" >${ent.comment}</div>
	<div class="form-container" v-for="(item, index) in data.SUB_${ent.name}"  v-on:click="selected(item)" v-if="data.SUB_${ent.name} && data.SUB_${ent.name}.length>0">
		<div class="btnDel"  v-if="getDelRight('${ent.name}') && item.selected" v-on:click="remove('${ent.name}',index)"></div>
		<#list ent.sysBoAttrs as field>
		<div class="form">
			<div class="form-title">
				${field.comment}
			</div>
			<div class="form-input">
				<@fieldCtrl field=field type="sub" entName=ent.name/>
			</div>
		</div>
		</#list>
	</div>
	
	<div class="toolbar" v-if="getAddRight('${ent.name}')">
		<div class="button add" v-on:click="add('${ent.name}')">添加</div>
	</div>
	
	<div class="form-container" v-if="data.SUB_${ent.name}.length==0&&getAddRight('${ent.name}')">
		<div class="emptyData">子表数据为空!</div>
	</div>
</div>
