package com.tecfrac.helpdesk.openfire.beans;

public class ResponseBean<T> 
{
	protected Long id;
	protected String name;
	protected T children;
	
	public ResponseBean() {
		super();
	}
	public ResponseBean(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public ResponseBean(Long id, String name, T children) {
		super();
		this.id = id;
		this.name = name;
		this.children = children;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public T getChildren() {
		return children;
	}
	public void setChildren(T children) {
		this.children = children;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResponseBean other = (ResponseBean) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ResponseBean [id=" + id + ", name=" + name + ", children=" + children + "]";
	}
	
}
