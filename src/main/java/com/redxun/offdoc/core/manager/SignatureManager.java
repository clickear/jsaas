package com.redxun.offdoc.core.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.mybatis.CommonDao;

@Service
public class SignatureManager {
	
	@Resource
	CommonDao commonDao;

	
	
	
	public boolean isExistByDocIdAndSignId(String mSignatureID,String mDocumentID){
		String sql="SELECT * from HTMLSignature where SignatureID=#{mSignatureID} and DocumentID=#{mDocumentID}";
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("mSignatureID", mSignatureID);
		params.put("mDocumentID", mDocumentID);
		List list= commonDao.query(sql,params);
		return list.size()>0;
	}
	
	public void add(String mDocumentID,String mSignatureID,String mSignature){
		String sql="insert into HTMLSignature (DocumentID,SignatureID,Signature) values (#{mDocumentID},#{mSignatureID},#{mSignature})";
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("mDocumentID", mDocumentID);
		params.put("mSignatureID", mSignatureID);
		params.put("mSignature", mSignature);
		commonDao.execute(sql, params);
	}
	
	public void update(String mSignatureID,String mDocumentID,String mSignature){
		String sql="update HTMLSignature set DocumentID=#{mDocumentID},SIGNATUREID=#{mSignatureID},Signature=#{mSignature} Where SignatureID=#{mSignatureID} and DocumentID=#{mDocumentID}";
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("mDocumentID", mDocumentID);
		params.put("mSignatureID", mSignatureID);
		params.put("mSignature", mSignature);
		commonDao.execute(sql, params);
	}
	public List<Object> getByDocId(String mDocumentID){
		String sql="SELECT SIGNATUREID from HTMLSignature Where DocumentID=#{mDocumentID}";
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("mDocumentID", mDocumentID);
		List list=commonDao.query(sql, params);
		return list;
	}
	public List<Object> getByDocIdAndSignId(String mSignatureID,String mDocumentID){
		String sql="SELECT SIGNATURE from HTMLSignature Where SignatureID=#{mSignatureID} and DocumentID=#{mDocumentID}";
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("mSignatureID", mSignatureID);
		params.put("mDocumentID", mDocumentID);
		List list=commonDao.query(sql, params);
		return list;
	}

	public List<Object> getAllByDocId(String mDocumentID){
		String sql="SELECT * FROM HTMLSignature WHERE DocumentID=#{mDocumentID}";
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("mDocumentID", mDocumentID);
		List list=commonDao.query(sql, params);
		return list;
	}
}
