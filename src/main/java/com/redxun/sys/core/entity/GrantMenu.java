package com.redxun.sys.core.entity;
/**
 *  授权菜单
 * @author csx
 *
 */
public class GrantMenu extends SysMenu{
	
	private boolean checked=false;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public GrantMenu() {
		
	}
}
