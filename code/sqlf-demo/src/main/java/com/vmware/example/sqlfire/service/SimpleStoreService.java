package com.vmware.example.sqlfire.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import com.vmware.example.sqlfire.data.StoreData;
import com.vmware.example.sqlfire.domain.Order;
import com.vmware.example.sqlfire.domain.OrderItem;
import com.vmware.example.sqlfire.domain.Request;
import com.vmware.example.sqlfire.domain.Response;
import com.vmware.example.sqlfire.util.SimpleIdGenerator;
import com.vmware.example.sqlfire.util.SimpleStopWatch;
import com.vmware.example.sqlfire.util.SimpleTimestampGenerator;

public class SimpleStoreService implements StoreService {

	private static final String ITEM_NAME_PREFIX = "Item-";

	private static final Logger logger = LoggerFactory
			.getLogger(SimpleStoreService.class);

	private StoreData dao;
	private String storeType;

	@Autowired
	SimpleIdGenerator idGenerator;

	@Autowired
	SimpleTimestampGenerator timestampGenerator;


	@Override
	public Response saveRequest(Request request) {
		logger.debug("Request: " + request);

		logger.debug("Saving: Request" + request);
		dao.saveRequest(request);

		logger.debug("Fetching Response: " + request);
		return getRequest(request.getId());
	}


	@Override
	@Transactional
	public synchronized Response runRequest(Request request) {
		logger.debug("Request: " + request);

		// Start timer
		SimpleStopWatch watch = new SimpleStopWatch(true);

		List<Order> orders = new ArrayList<Order>();

		for (int j = 0; j < request.getOrders(); j++) {

			Order order = new Order();
			order.setOn(timestampGenerator.getNow());
			String oId = request.getId() + "-" + request.getIndex() + "-" + j;
			order.setId(oId);

			for (int i = 0; i < request.getItems(); i++) {
				OrderItem item = new OrderItem();
				item.setName(ITEM_NAME_PREFIX + i);
				item.setPrice(new BigDecimal(i));
				item.setId(oId + "-" + i);
				order.getItems().add(item);
			}

			logger.debug("Saving Order: " + order);
			dao.saveOder(request, order);

			orders.add(order);

		}

		logger.debug("Delete Orders and Order Items for: " + request);
		for (Order order : orders) {
			logger.debug("Delete Order Items: " + order.getId());
			dao.removeOderItems(order.getId());

			logger.debug("Delete Order: " + request.getId());
			dao.removeOders(request.getId());
		}

		// Stop timer
		watch.stop();

		// returns stats only for the current call
		// summary from the seperate call
		Request storeRequest = dao.getRequest(request.getId());
		storeRequest.setOrders(request.getOrders());
		storeRequest.setItems(request.getItems());
		storeRequest.setDuration(watch.getDuration());

		logger.debug("Update Duration: " + storeRequest);
		dao.updateRequestDuration(storeRequest);

		logger.debug("Fetching Response: " + storeRequest);
		return getRequest(request.getId());
	}


	@Override
	public Response getRequest(String requestId) {
		logger.debug("Request Id: " + requestId);

		Response response = new Response();
		response.setRequest(dao.getRequest(requestId));
		response.setStoreType(storeType);

		return response;

	}

	@Required
	public void setDao(StoreData dao) {
		this.dao = dao;
	}


	@Override
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}


	@Override
	public String getStoreType() {
		return storeType;
	}

}
