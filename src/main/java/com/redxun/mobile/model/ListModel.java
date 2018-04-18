package com.redxun.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class ListModel {
	public ListModel() {
	}
	
	public ListModel(int total, List rowList) {
		super();
		this.total = total;
		this.rowList = rowList;
	}
		
	private int total=0;
	
	private List rowList=new ArrayList();

	public List getRowList() {
		return rowList;
	}
	
	public void addRow(Object model){
		rowList.add(model);
	}

	

	public void setRowList(List rowList) {
		this.rowList = rowList;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	
			
			
}
