package com.redxun.sys.demo.manager;

import javax.annotation.Resource;

import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.redxun.bpm.activiti.handler.ProcessStartPreHandler;
import com.redxun.bpm.activiti.handler.TaskPreHandler;
import com.redxun.bpm.core.entity.IExecutionCmd;
import com.redxun.bpm.core.entity.ProcessStartCmd;
import com.redxun.core.util.StringUtil;
import com.redxun.saweb.util.IdUtil;
import com.redxun.sys.demo.dao.DemoQueryDao;
import com.redxun.sys.demo.entity.Demo;

@Service
public class DemoHandler  implements ProcessStartPreHandler ,TaskPreHandler{

	@Resource
	DemoQueryDao demoQueryDao;
	@Override
	public void taskPreHandle(IExecutionCmd cmd, Task task, String busKey) {
		String json=cmd.getJsonData();
		Demo demo=JSONObject.parseObject(json, Demo.class);
		demoQueryDao.update(demo);
	}

	@Override
	public void processStartPreHandle(ProcessStartCmd cmd) {
		String json=cmd.getJsonData();
		Demo demo=JSONObject.parseObject(json, Demo.class);
		if(StringUtil.isEmpty(demo.getId())){
			demo.setId(IdUtil.getId());
			demoQueryDao.create(demo);
			cmd.setBusinessKey(demo.getId());
		}
		else{
			demoQueryDao.update(demo);
		}
	}

}
