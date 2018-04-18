

<div class="thisPlayButton" style="position:absolute;left:0px;top:0px;"><a class="mini-button" iconCls="icon-activate" onclick="addRoadPath()" plain="false">播放流程</a></div>


<map name="imgHref" id="imgHref">
<#list list as bpms>
<area id='${bpms.bpmId}'  name='${bpms.taskName}' shape='rect' coords='${bpms.coord}' title='${bpms.taskName}' xposition='${bpms.XPosition}px'; yposition='${bpms.YPosition}px';   target='_blank' type='${bpms.shapeType}'  />
</#list>
</map>



	

	
	