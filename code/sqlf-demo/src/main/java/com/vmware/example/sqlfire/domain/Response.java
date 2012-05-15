package com.vmware.example.sqlfire.domain;

import java.io.Serializable;

public class Response implements Serializable {

	private static final long serialVersionUID = -1508164853788758273L;

	private Request request;
	private String storeType;
	private String error;

	public Response() {
	}

	public Response(String error) {
		this.error = error;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "Response [request=" + request + ", storeType=" + storeType
				+ ", error=" + error + "]";
	}

}
