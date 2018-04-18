<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Diy Group Demo</title>
<%@include file="/commons/list.jsp"%>
 <script src="${ctxPath}/scripts/jquery/plugins/jquery-grid.js" type="text/javascript"></script>
</head>
<body>
	<h2>动态通过配置生成Grid</h2>
	
	<table class="rx-grid rxc" plugins="rx-grid" style="" label="abc" name="abc" url="" showpager="" pagesize="10" mwidth="0" wunit="px" mheight="0" hunit="px">
          <thead>
              <tr class="firstRow">
                  <td class="header" width="100" header="a" align="center" valign="top" style="background-color: rgb(146, 205, 220);">
                      a
                  </td>
                  <td class="header" width="100" header="b" align="center" valign="top" style="background-color: rgb(146, 205, 220);">
                      b
                  </td>
                  <td class="header" width="100" header="c" align="center" valign="top" style="background-color: rgb(146, 205, 220);">
                      c
                  </td>
                  <td class="header" width="100" header="w" align="center" valign="top" style="background-color: rgb(146, 205, 220);">
                      w
                  </td>
              </tr>
          </thead>
          <tbody>
              <tr>
                  <td header="a">
                      <input class="mini-textbox rxc" plugins="mini-textbox" vtype="rangeLength:0,50" label="xx" minlen="0" maxlen="50" rule="" required="false" allowinput="false" value="" mwidth="0" wunit="px" mheight="0" hunit="px" style=""/>
                  </td>
                  <td header="b">
                      <input class="mini-textbox rxc" plugins="mini-textbox" vtype="rangeLength:0,50" label="xx" minlen="0" maxlen="50" rule="" required="false" allowinput="false" value="" mwidth="0" wunit="px" mheight="0" hunit="px" style=""/>
                  </td>
                  <td header="c">
                      <input class="mini-textbox rxc" plugins="mini-textbox" vtype="rangeLength:0,50" label="xx" minlen="0" maxlen="50" rule="" required="false" allowinput="false" value="" mwidth="0" wunit="px" mheight="0" hunit="px" style=""/>
                  </td>
                  <td header="w">
                      <input class="mini-textbox rxc" plugins="mini-textbox" vtype="rangeLength:0,50" label="xx" minlen="0" maxlen="50" rule="" required="false" allowinput="false" value="" mwidth="0" wunit="px" mheight="0" hunit="px" style=""/>
                  </td>
              </tr>
          </tbody>
      </table>
      
      
          <!-- div id="datagrid1" class="mini-datagrid" style="width:800px;height:280px;" 
		        allowResize="true" pageSize="20" 
		        allowCellEdit="true" allowCellSelect="true" multiSelect="true" 
		        editNextOnEnterKey="true"  editNextRowCell="true"
		    >
		        <div property="columns">
		            <div type="indexcolumn"></div>
		            <div type="checkcolumn"></div>
		            <div name="LoginName"  field="loginname" headerAlign="center" allowSort="true" width="150" >员工帐号
		                <input class="mini-textbox rxc" property="editor"  label="xx" minlen="0" maxlen="50" rule="" required="false" value="" mwidth="0" wunit="px" mheight="0" hunit="px" style=""/>
		            </div>
		            <div field="age" width="100" allowSort="true" >年龄
		                <input property="editor" class="mini-spinner"  minValue="0" maxValue="200" value="25" style="width:100%;"/>
		            </div>            
		            <div name="birthday" field="birthday" width="100" allowSort="true" dateFormat="yyyy-MM-dd">出生日期
		                <input property="editor" class="mini-datepicker rxt" style="width:100%;"/>
		            </div>    
		            <div field="remarks" width="120" headerAlign="center" allowSort="true">备注
		                <input property="editor" class="mini-textarea rxt" style="width:200px;" minWidth="200" minHeight="50"/>
		            </div>
		            <div type="checkboxcolumn" field="married" trueValue="1" falseValue="0" width="60" headerAlign="center">婚否</div>                        
		        </div>
		    </div-->
      
      <script type="text/javascript">
      	/*mini.parse();
      	var grid=mini.get('datagrid1');
      	grid.addRow({});*/
      	$(function(){
      		$('.rx-grid').each(function(){$(this).GenGrid()});
      	});
      </script>
</body>
</html>