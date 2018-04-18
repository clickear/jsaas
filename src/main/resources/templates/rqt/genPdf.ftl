<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
 <#macro menuTree menus num> 
  <#if menus?? && menus?size gt 0> 
   <#list menus as menu> 
  	<bookmark name="${num}${menu_index+1}${menu.title}" href="#${menu.id}">
  	<#if menu.children?? && menu.children?size gt 0> 
    <@menuTree menus = menu.children num=num+((menu_index+1))+'.'/> 
   </#if> 
  	</bookmark>
   </#list> 
  </#if> 
 </#macro>
 
 <bookmarks>
  <@menuTree menus = dto  num=''/>
 </bookmarks>
 
 <style>
	body{
	font-family:"Microsoft YaHei";
	}
 </style>
  </head>
  
<body>
 <#macro article arts> 
  <#if arts?? && arts?size gt 0>
   <#list arts as art> 
     <div style="<#if !art.outLine?? >page-break-before: always;</#if>" id="${art.id}">
  	 <#if art.type?? && art.type=='article'>
  	 <div style='width:700px;'><div style='text-align:left;font-size:${39-art.titleLevel*3}px;font-weight:bold'>${art.prefixTitle+(art_index+1)}${art.title}</div>${art.content}</div>
  	 </#if>
  	 <#if art.type?? && art.type=='index'>
  	 <div style='text-align:left;font-size:${48-art.titleLevel*3}px;font-weight:bold'>${art.prefixTitle+(art_index+1)}${art.title}</div>
  	 </#if>
  	</div>
  	
  	<#if art.children?? && art.children?size gt 0> 
    <@article arts = art.children/> 
   </#if> 
   </#list> 
  </#if> 
 </#macro>

<div id="cover"> 
  ${cover}
  </div>
<@article arts = dto/>


</body>
</html>