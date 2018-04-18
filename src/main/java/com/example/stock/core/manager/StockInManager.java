package com.example.stock.core.manager;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.dao.mybatis.CommonDao;
import com.redxun.core.entity.SqlModel;

@Service
public class StockInManager {
	@Resource
	CommonDao commonDao;
	
	
	public void handleInstockData(String data){

		JSONObject cmdJson=JSONObject.parseObject(data);
		JSONArray bos=cmdJson.getJSONArray("bos");
		if(bos==null || bos.size()==0){
			return;
		}
		JSONObject jsonData=bos.getJSONObject(0).getJSONObject("data");
		
		String stockId=jsonData.getString("inStock");
		
		JSONArray items=jsonData.getJSONArray("SUB_inStockItem");
		
		String sql="update w_stockstiation_item set ";
		for(int i=0;i<items.size();i++){
			JSONObject row=items.getJSONObject(i);
			String code=row.getString("code");
			Double amt=row.getDouble("amt");
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("stockId",stockId);
			params.put("proCode", code);
			String rowSql="select i.* from w_stockstiation s left join w_stockstiation_item i on s.id_=i.REF_ID_ where s.f_stock=#{stockId} and i.f_code=#{proCode}";
			SqlModel sqlMode=new SqlModel(rowSql,params);
			List<Map<String,Object>> result=commonDao.query(sqlMode);
			if(result.size()==0){
				//TODO
				String s="insert into w_stockstiation_item";
			}else{
				for(Map<String,Object> r:result){
					String id=(String)r.get("ID_");
					BigDecimal d=(BigDecimal)r.get("F_CURNUMS");
					Double d2=d.doubleValue();
					String s="update w_stockstiation_item set F_CURNUMS=#{curNums} where id_=#{id}";
					Map<String,Object>p=new HashMap<String,Object>();
					p.put("id", id);
					p.put("curNums", d2+amt);
					commonDao.execute(new SqlModel(s,p));
				}
			}
			
		}
	}
}
