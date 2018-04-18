<#list list as item>
<div class="noPrint">
	<input type="checkbox" id="tab_${item_index}" name="chkPrint" checked="checked"/><label for="tab_${item_index}">打印</label>
</div>
<div class="form-print" id="form_${item_index}">
	${item.val}
</div>
</#list>

	
	