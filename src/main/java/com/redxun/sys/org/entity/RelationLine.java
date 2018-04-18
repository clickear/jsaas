package com.redxun.sys.org.entity;

/**
 * 关系线
 * @author mansan
 *
 */
public class RelationLine {
	//关系Id
	private String relationId;
	//用户或组Id
	private String id;
	//全名
	private String name;
	//父Id
	private String parentId;
	
	private String iconCls;

	public RelationLine() {
		
	}
	
	public RelationLine(String id,String name,String parentId){
		this.id=id;
		this.name=name;
		this.parentId=parentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

}
