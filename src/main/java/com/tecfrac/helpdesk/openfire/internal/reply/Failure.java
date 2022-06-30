package com.tecfrac.helpdesk.openfire.internal.reply;

public class Failure<T> implements Return<T> {

	String error;
	Object[] parameters;
	T bean;

	
	public Failure(String code,String errorMessage) {
		this(null, code, new Object[] {errorMessage});
		
	}
	
	public Failure(String code) {
		this(null, code, new Object[0]);
	}

	public Failure(String code, Object[] parameters) {
		this(null, code, parameters);
	}

	public Failure(T bean, String code) {
		this(bean, code, new Object[0]);
	}

	public Failure(T bean, String code, Object[] parameters) {
		this.bean = bean;
		this.error = code;
		this.parameters = parameters;

		if (parameters == null) {
			parameters = new Object[0];
		}
	}

	@Override
	public T getBean() {
		return bean;
	}

	@Override
	public boolean isSuccessfull() {
		return false;
	}

	@Override
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public void setBean(T bean) {
		this.bean = bean;
	}

	@Override
	public String toString() {
		return error + (parameters == null || parameters.length == 0 ? "" : " " + parameters.toString());
	}
}
