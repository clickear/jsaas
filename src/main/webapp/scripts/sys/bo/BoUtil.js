function onSelectBo(e){
	var btn=e.sender;
	var multi=false;
	openBoDefDialog("",function(action,data){
		if(action=="clear"){
			btn.setValue("");
			btn.setText("");
		}
		else{
			btn.setValue(data.id);
			btn.setText(data.name);	
		}
	},multi)
}
	
