<script type="text/javascript">
           mini.parse();

            function onDelete(){
                if (confirm("确定当前记录？")) {
                   var id=$("#pkId").val();
                   
                   $.ajax({
                       url: "${rootPath}/${baseUrl}/del.do",
                       data:{
                          ids:id
                       },
                       success: function() {
                          top['${entityName}'].grid.load();
                          CloseWindow(); 
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
            function onClose(){
            	CloseWindow();
            }
            
            function preRecord(){
                var pkId=top['${entityName}'].preRecord();
                if(pkId==0) return ;
                window.location='${rootPath}/${baseUrl}/get.do?pkId='+pkId;
            }
            
            function nextRecord(){
                var pkId=top['${entityName}'].nextRecord();
                if(pkId==0) return ;
                window.location='${rootPath}/${baseUrl}/get.do?pkId='+pkId;
            }
        </script>