package com.vmware.example.sqlfire.data;

import com.vmware.example.sqlfire.domain.Order;
import com.vmware.example.sqlfire.domain.Request;

public interface StoreData {

	public abstract void saveRequest(Request request);

	public abstract void updateRequestDuration(Request request);

	public abstract Request getRequest(String id);

	public abstract void saveOder(Request request, Order order);

	public abstract void removeOders(String requestId);

	public abstract void removeOderItems(String orderId);

}