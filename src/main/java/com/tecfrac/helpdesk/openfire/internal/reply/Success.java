package com.tecfrac.helpdesk.openfire.internal.reply;

public class Success<T> implements Return<T> {

	T bean;
	String error;
	Object[] parameters;

	public Success(T bean) {
		this(bean, null, new Object[0]);
	}

	public Success(T bean, String code) {
		this(bean, code, new Object[0]);
	}

	public Success(T bean, String code, Object[] parameters) {
		this.bean = bean;
		this.error = code;
		this.parameters = parameters;

		if (parameters == null) {
			parameters = new Object[0];
		}
	}

	public void setError(String error, Object... parameters) {
		this.error = error;
		this.parameters = parameters;
	}

	@Override
	public T getBean() {
		return bean;
	}

	@Override
	public boolean isSuccessfull() {
		return true;
	}

	@Override
	public String getError() {
		return this.error;
	}

	@Override
	public Object[] getParameters() {
		return parameters;
	}
}
