package com.redxun.ext.formdatahandler;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.redxun.bpm.core.entity.config.ProcessConfig;
import com.redxun.bpm.core.manager.BoDataUtil;
import com.redxun.bpm.core.manager.IFormDataHandler;
import com.redxun.bpm.form.manager.BpmFormViewManager;
import com.redxun.sys.bo.entity.BoResult;
import com.redxun.sys.customform.manager.ICustomFormDataHandler;

@Service
public class CommonFormDataHandler implements ICustomFormDataHandler {
	
	@Resource
	BpmFormViewManager bpmFormViewManager;
	 

	@Override
	public JSONObject getInitData(String boDefId) {
		IFormDataHandler handler=BoDataUtil.getDataHandler(ProcessConfig.DATA_SAVE_MODE_DB);
		JSONObject jsonObj= handler.getInitData(boDefId);
		return jsonObj;
	}

	@Override
	public JSONObject getByPk(String pk,String boDefId) {
		IFormDataHandler handler=BoDataUtil.getDataHandler(ProcessConfig.DATA_SAVE_MODE_DB);
		JSONObject jsonObj= handler.getData(boDefId, pk);
		return jsonObj;
	}

	@Override
	public BoResult save(String boDefId, JSONObject jsonData) {
		IFormDataHandler handler=BoDataUtil.getDataHandler(ProcessConfig.DATA_SAVE_MODE_DB);
		BoResult result= handler.saveData(boDefId, "", jsonData);
		return result;
	}

	@Override
	public void delById(String id) {
		// TODO Auto-generated method stub
		
	}

}
