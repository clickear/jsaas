<div id="${toolbarId}" class="mini-toolbar topBtn" >
    <#if save=='true'>
   	 <a class="mini-button" iconCls="icon-save" plain="true" onclick="onOk">保存</a>
    </#if>
    <#if extToolbars??>
    ${extToolbars}
    </#if>
</div>