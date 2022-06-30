package com.tecfrac.helpdesk.openfire.internal.reply;

public class VoidSuccess extends Success<Void>  {

	public VoidSuccess() {
		super(null);
	}

	public VoidSuccess(String code) {
		super(null, code, new Object[0]);
	}

	public VoidSuccess(String code, Object[] parameters) {
		super(null, code, parameters);
	}
}
