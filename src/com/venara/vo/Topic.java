package com.venara.vo;

import java.util.List;

public class Topic {
	
	private String name;
	
	private int size;
	
	private List<Topic> children;
	
	private String parentName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<Topic> getChildren() {
		return children;
	}

	public void setChildren(List<Topic> children) {
		this.children = children;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Override
	public String toString() {
		return "Topic [name=" + name + ", size=" + size + ", children=" + children + ", parentName=" + parentName + "]";
	}
	
	
}
