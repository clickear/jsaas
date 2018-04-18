 <script type="text/javascript">
 	var tenantId='${tenantId?html}';
    mini.parse();
    
    var form = new mini.Form("${formId}");
	
    function SaveData() {
    	//若有自定义函数，则调用页面本身的自定义函数
    	if(isExitsFunction('selfSaveData')){
    		selfSaveData.call();
    		return;
    	}
    	
        form.validate();
        if (!form.isValid()) {
            return;
        }
        
        var formData=$("#${formId}").serializeArray();
        //处理扩展控件的名称
        var extJson=_GetExtJsons("${formId}");
        for(var key in extJson){
        	formData.push({name:key,value:extJson[key]});
        }
        //加上租用Id
        if(tenantId!=''){
	        formData.push({
	        	name:'tenantId',
	        	value:tenantId
	        });
        }
       
       //若定义了handleFormData函数，需要先调用 
       if(isExitsFunction('handleFormData')){
        	var result=handleFormData(formData);
        	if(!result.isValid) return;
        	formData=result.formData;
        }
        
        var config={
        	url:"${rootPath}/${baseUrl}/save.do",
        	method:'POST',
        	data:formData,
        	success:function(result){
        		//如果存在自定义的函数，则回调
        		if(isExitsFunction('successCallback')){
        			successCallback.call(this,result);
        			return;	
        		}
        		
        		CloseWindow('ok');
        	}
        }
        
        if(result && result["postJson"]){
        	config.postJson=true;
        }
        
        _SubmitJson(config);
     }
   
    function onOk(e) {
        SaveData();
    }
    
    function onCancel(e) {
        CloseWindow("cancel");
    }
    
    function onDelete(){
         if (confirm("确定当前记录？")) {
                var id=$("#pkId").val();
                form.loading("操作中，请稍后......");
                $.ajax({
                    url: "${rootPath}/${baseUrl}/del.do",
                    data:{
                       ids:id
                    },
                    success: function(text) {
                    	try{
                       		top['${entityName}'].loadGrid();
                       }catch(e){}
                       CloseWindow('ok'); 
                    },
                    error: function(err) {
                        form.unmask();
                        mini.showMessageBox({
                        showHeader: false,
                        width: 450,
                        title: "操作出错",
                        buttons: ["关闭"],
                        html: err,
                        iconCls: 'mini-messagebox-error'
                    });
                    }
                });
            }
    }
    
    function preRecord() {
        //调用父窗口获得前一记录的PKID
        var pkId = top['${entityName}'].preRecord();
        if (pkId == 0){
            return;
        }
        form.loading();
        window.location="${rootPath}/${baseUrl}/edit.do?tenantId="+tenantId+"&pkId="+pkId;
    }

    function nextRecord() {
        //调用父窗口获得下条一记录
        var pkId = top['${entityName}'].nextRecord();
        if (pkId == 0){
            return;
        }
        form.loading();
        window.location="${rootPath}/${baseUrl}/edit.do?tenantId="+tenantId+"&pkId="+pkId;
    }

</script>