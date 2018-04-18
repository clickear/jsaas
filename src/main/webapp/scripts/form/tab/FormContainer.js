/**
 * 多页表单容器。
 * @returns {FormContainer}
 */
FormContainer=function(){
	{
		this.splitor="#page#";
		this.aryTitle=new Array();
		this.aryForm=new Array();
		
	};
	/**
	 * 初始化表单数据。
	 */
	this.init=function(title,form){
		this.aryTitle=title.split(this.splitor);
		this.aryForm=form.split(this.splitor);
	},
	/**
	 * 插入表单标题和表单。
	 */
	this.insertForm =function(title,html,index){
		this.insert(this.aryTitle,title,index);
		this.insert(this.aryForm,html,index);
	},
	this.insert=function(aryData,data,index){
		if(isNaN(index) || index<0 || index>aryData.length) {
			aryData.push(data);
		}else{
			var temp = aryData.slice(index);
			aryData[index]=data;
			for (var i=0; i<temp.length; i++){
				aryData[index+1+i]=temp[i];
			}
		}
	},
	/**
	 * 设置表单标题和表单。
	 */
	this.setForm=function(title,html,index){
		this.aryTitle[index]=title;
		this.aryForm[index]=html;
	},
	/**
	 * 设置表单数据。
	 */
	this.setFormHtml=function(html,index){
		this.aryForm[index]=html;
	},
	/**
	 * 设置标题。
	 */
	this.setFormTitle=function(title,index){
		
		this.aryTitle[index]=title;
	},
	/**
	 * 根据索引获取表单数据。
	 */
	this.getForm=function(index){
		return {title:this.aryTitle[index],form:this.aryForm[index]};
	},
	/**
	 * 移除表单对象数据。
	 */
	this.removeForm=function(index){
		this.aryTitle.splice(index,1);
		this.aryForm.splice(index,1);
	},
	/**
	 * 返回表单数据结果。
	 */
	this.getResult=function(){
		return {title:this.aryTitle.join(this.splitor),form:this.aryForm.join(this.splitor +" ")};
	};
};