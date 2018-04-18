$(function(){
	//默认选择第一个
	$('#selEveryDay')[0].selectedIndex =0;
	
	
	mini.parse();
	
});

function getPlan()
{
	var planType = $("input[name=rdoTimeType]:checked").val();
	var str="";
	switch(planType)
	{
	
		case "1":
			str=mini.get("txtOnceDate").value.format("yyyy-MM-dd hh:mm:ss");
			str="{\"type\":1,\"timeInterval\":\"" +str+"\"}"; 
			break; 
		case "2":
			str= $("#selEveryDay").val();
			str="{\"type\":2,\"timeInterval\":\"" +str+"\"}"; 
			break; 
		case "3":
			 var h= $("#txtDayHour").val();
			  var m= $("#txtDayMinute").val();
	          str=  h +":" + m ;
	          str="{\"type\":3,\"timeInterval\":\"" +str+"\"}"; 
			break; 
		case "4":
			 str=$.getChkValue("chkWeek");
			  var h= $("#txtWeekHour").val();
			  var m= $("#txtWeekMinute").val();
	          str+="|" + h +":" + m ;
	          str="{\"type\":4,\"timeInterval\":\"" +str+"\"}"; 
			break; 
		case "5":
			
			str=$.getChkValue("chkMon");
			  var h= $("#txtMonHour").val();
			  var m= $("#txtMonMinute").val();
		
	          str+="|" + h +":" + m ;
	          str="{\"type\":5,\"timeInterval\":\"" +str+"\"}"; 
			break; 
		case "6":
			str+=$("#txtCronExpression").val();
			str="{\"type\":6,\"timeInterval\":\"" +str+"\"}"; 
			break; 
		 
	}
	return str;
}

var beforeCallBack=function(formData){
		var data = getPlan();
		
		var obj={name:"planJson",value:data};
		formData.push(obj);
};
	
function save() {
    	var url= __rootPath +"/sys/core/scheduler/addTrigger.do";
    	_SaveData("form1",url,function(result){
    		CloseWindow('ok');
    	},beforeCallBack );
}

function onCancel(){
	CloseWindow('cancel');
}


