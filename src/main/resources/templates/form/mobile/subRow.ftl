<div class="div-form" v-if="hasRight('${ent.name}')">
	<div class="caption" >${ent.comment}</div>
	<div class="table-container">
		<table class="table-row">
			<tr>
				<th  v-if="getMgrRight('${ent.name}')">管理</th>
				<#list ent.sysBoAttrs as field>
				<th>${field.comment}</th>
				</#list>
			</tr>
			<tr v-for="(item, index) in data.SUB_${ent.name}">
				<td v-if="getMgrRight('${ent.name}')">
					<div class="btnDel" v-if="getDelRight('${ent.name}')"  v-on:click="remove('${ent.name}',index)">删除</div>
					<div class="btnEdit" v-if="getEditRight('${ent.name}')"   v-on:click="editDialog('${ent.name}',index)">编辑</div>
				</td>
				<#list ent.sysBoAttrs as field>
				<td><@fieldCtrl field=field type="subRow" entName=ent.name/></td>
				</#list>
			</tr>
		</table>
	</div>
	<div class="toolbar" v-if="getAddRight('${ent.name}')">
		<div class="button " v-on:click="addDialog('${ent.name}')">添加数据</div>
	</div>
	<rx-dialog title="${ent.comment}" ref="dialog_${ent.name}" v-on:handok="handOk">
		<div class="div-form">
			<div class="form-container" >
				<#list ent.sysBoAttrs as field>
					<#if field.control!="mini-hidden">
					<div class="form">
						<div class="form-title">
							${field.comment}
						</div>
						<div class="form-input">
							<@fieldCtrl field=field type="curRow" entName=""/>
						</div>
					</div>
					</#if>
				</#list>
			</div>
		</div>
	</rx-dialog>
	
	<div class="form-container" v-if="data.SUB_${ent.name}.length==0&&getAddRight('${ent.name}')=='w'">
		<div class="emptyData">子表数据为空!</div>
	</div>
</div>
