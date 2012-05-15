package com.vmware.example.sqlfire.service;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import com.vmware.example.sqlfire.domain.Request;
import com.vmware.example.sqlfire.domain.Response;

public interface StoreService {

	/* (non-Javadoc)
	 * @see com.vmware.example.sqlfire.service.StoreService#saveRequest(com.vmware.example.sqlfire.domain.Request)
	 */
	public abstract Response saveRequest(Request request);

	@Transactional
	public abstract Response runRequest(Request request);

	/* (non-Javadoc)
	 * @see com.vmware.example.sqlfire.service.StoreService#getRequest(java.lang.String)
	 */
	public abstract Response getRequest(String requestId);

	@Required
	public abstract void setStoreType(String storeType);

	/* (non-Javadoc)
	 * @see com.vmware.example.sqlfire.service.StoreService#getStoreType()
	 */
	public abstract String getStoreType();

}