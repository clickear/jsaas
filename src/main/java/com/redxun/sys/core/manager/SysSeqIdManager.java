package com.redxun.sys.core.manager;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.util.DateUtil;
import com.redxun.sys.core.dao.SysSeqIdDao;
import com.redxun.sys.core.dao.SysSeqIdQueryDao;
import com.redxun.sys.core.entity.SysSeqId;
/**
 * <pre> 
 * 描述：SysSeqId业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class SysSeqIdManager extends BaseManager<SysSeqId>{
	@Resource
	private SysSeqIdDao sysSeqIdDao;
	
	@Resource
	private SysSeqIdQueryDao sysSeqIdQueryDao;
	
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysSeqIdDao;
	}
	
	
	/**
     * 通过别名及机构Id获得流水号的设置
     * @param alias
     * @param tenantId
     * @return
     */
    public SysSeqId getByAlias(String alias,String tenantId){
    	return sysSeqIdQueryDao.getByAlias(alias, tenantId);
    }
    
    /**
     * 检查别名是否存在
     * @param alias
     * @param tenantId
     * @return
     */
    public boolean isAliasExsist(String alias,String tenantId){
    	SysSeqId seq=sysSeqIdQueryDao.getByAlias(alias, tenantId);
    	return seq!=null;
    }
    
    
    /**
	 * 根据流程规则别名获取得当前流水号。
	 * @param alias	 流水号规则别名。
	 * @return
	 */
	public String getCurIdByAlias(String alias,String tenantId){
		SysSeqId idSeq=getByAlias(alias,tenantId);
		if(idSeq==null) return "";
		Integer curValue=idSeq.getCurVal();
		if(curValue==null) curValue=idSeq.getInitVal();
		String val=getByRule(idSeq.getRule(),idSeq.getLen(),curValue);
		return val;
	}

	private  Result getSequenceId(String alias,String tenantId){
		
		SysSeqId identity=getByAlias(alias,tenantId);
		
		String rule=identity.getRule();
		
		String genType =identity.getGenType();
		
		Integer curValue=identity.getCurVal();
		
		if(curValue==null) curValue=identity.getInitVal();
		
		Date oldDate=(identity.getCurDate()==null)?new Date():identity.getCurDate();
		
		int curTimeVal=0;
		int oldTimeVal=0;
		if(SysSeqId.GEN_TYPE_DAY.equals(genType)){//每天产生
			curTimeVal=DateUtil.getCurDay();
			oldTimeVal=DateUtil.getDay(oldDate);
		}else if(SysSeqId.GEN_TYPE_WEEK.equals(genType)){//每周产生
			curTimeVal=DateUtil.getCurWeekOfYear();
			oldTimeVal=DateUtil.getWeekOfYear(oldDate);
		}else if(SysSeqId.GEN_TYPE_MONTH.equals(genType)){//每月产生
			curTimeVal=DateUtil.getCurMonth();
			oldTimeVal=DateUtil.getMonth(oldDate);
		}else if(SysSeqId.GEN_TYPE_YEAR.equals(genType)){//每年产生
			curTimeVal=DateUtil.getCurYear();
			oldTimeVal=DateUtil.getYear(oldDate);
		}
		if(curTimeVal!=oldTimeVal){
			oldDate=new Date();
			curValue=identity.getInitVal();
		}else{
			curValue+=identity.getStep();
		}
		identity.setNewVal(curValue);
		identity.setCurDate(oldDate);
		Result result=new Result();
		int amount=sysSeqIdQueryDao.updateVersion(identity);
		if(amount>0){
			result.setResult(true);
			String no=getByRule(rule,identity.getLen(),curValue);
			result.setNo(no);
			
		}
		return result;
	}
	
	
	
	
	
	
	/**
	 * 根据流程规则别名获取得下一个流水号。
	 * @param alias		流水号规则别名。
	 * @return
	 */
	public   String genSequenceNo(String alias,String tenantId){
		Result result=getSequenceId(alias,tenantId);
		
		while(!result.getResult()){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			result=getSequenceId(alias,tenantId);
		}
		return result.getNo();
		
	}

	
	
	
	/**
	 * 根据规则返回需要显示的流水号。
	 * @param rule			流水号规则。
	 * @param length		流水号的长度。
	 * @param curValue		流水号的当前值。
	 * @return
	 */
	private String getByRule(String rule,int length, int curValue){
		Calendar now= Calendar.getInstance(); 
		NumberFormat nf=new DecimalFormat("00");
		int year=now.get(Calendar.YEAR);
		int month=now.get(Calendar.MONTH)+1;
		int day=now.get(Calendar.DATE);
		
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<length;i++){
			sb.append("0");
		}
		SimpleDateFormat fullDateFormat=new SimpleDateFormat("yyyMMdd");
		SimpleDateFormat shortDateFormat=new SimpleDateFormat("yyyMM");
		
		NumberFormat seqFt=new DecimalFormat(sb.toString());
		
		String seqNo=seqFt.format(curValue);
		
		String rtn=rule.replace("{yyyy}", year+"")
				.replace("{yy}",nf.format(year))
				.replace("{MM}", nf.format(month))
				.replace("{mm}",month+"")
				.replace("{DD}", nf.format(day))
				.replace("{dd}", day+"")
				.replace("{NO}", seqNo)
				.replace("{no}", curValue+"")
				.replace("{yyyyMM}", shortDateFormat.format(now.getTime()))
				.replace("{yyyyMMdd}", fullDateFormat.format(now.getTime()));

		return rtn;
	}
	
	
	public static void main(String[]args){
		
		int length=5;
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<length;i++){
			sb.append("0");
		}
		NumberFormat seqFt=new DecimalFormat(sb.toString());
		for(int i=0;i<30;i++){
			String seqNo=seqFt.format(i);
			System.out.println("seqNo:"+seqNo);
		}
	}
	
	/**
	 * 返回结果
	 * @author ray
	 *
	 */
	class Result{
		private boolean result=false;
		private String no="";
		
		
		public boolean getResult() {
			return result;
		}
		public void setResult(boolean result) {
			this.result = result;
		}
		public String getNo() {
			return no;
		}
		public void setNo(String no) {
			this.no = no;
		}
		
		
	}

}