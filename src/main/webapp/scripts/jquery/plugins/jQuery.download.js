jQuery.download = function(url, data, method){
	//url and data options required
	if( url && data ){ 
		//data can be string of parameters or array/object
		//data = typeof data == 'string' ? data : jQuery.param(data);
		//split params into form inputs
                var inputs = '';
                if(typeof data =='string'){
                    jQuery.each(data.split('&'), function(){ 
                            var pair = this.split('=');
                            inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />'; 
                    });
                    inputs+='<input type="hidden" name="_download" value="true"/>';
                }else{
                    for(var _key in data){
                        inputs+='<textarea name="'+ _key +'" >'+ data[_key] +'</textarea>';
                    }
                }
		//send request
		jQuery('<form target="_blank" action="'+ url +'" method="'+ (method||'post') +'" style="visibility:hidden">'+inputs+'</form>')
		.appendTo('body').submit().remove();
	};
};