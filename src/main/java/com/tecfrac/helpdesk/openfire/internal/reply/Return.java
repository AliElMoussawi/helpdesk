package com.tecfrac.helpdesk.openfire.internal.reply;

public interface Return<T> {

	public T getBean();

	public boolean isSuccessfull();

	public String getError();
	
	public Object[] getParameters();
}
