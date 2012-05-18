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

	public SimpleStoreService() {
		logger.debug("SimpleStoreService instance created");
	}

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
	public Response runRequest(Request request) {
		logger.debug("Request: " + request);

		// Start timer
		SimpleStopWatch watch = new SimpleStopWatch(true);

		List<Order> orders = new ArrayList<Order>();

		for (int j = 0; j < request.getOrders(); j++) {

			Order order = new Order();
			order.setOn(timestampGenerator.getNow());
			order.setId(idGenerator.getNextId());

			for (int i = 0; i < request.getItems(); i++) {
				OrderItem item = new OrderItem();
				item.setName(ITEM_NAME_PREFIX + i);
				item.setPrice(new BigDecimal(i));
				item.setId(idGenerator.getNextId());
				order.getItems().add(item);
			}

			logger.debug("Saving Order: " + order);
			dao.saveOder(request, order);

			orders.add(order);

		}

		logger.debug("Update Order Items for: " + request);
		for (Order order : orders) {

			logger.debug("Update: " + order.getId());
			order.setOn(timestampGenerator.getNow());
			dao.updateOrder(order);

			for (OrderItem item : order.getItems()) {

				logger.debug("Update Order item: " + order.getId());
				item.setPrice(new BigDecimal(item.getPrice().doubleValue() * 2));
				dao.updateOrderItem(item);
			}

		}

		// Stop timer
		watch.stop();

		// returns stats only for the current call
		// summary from the separate call
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
	public void cleanup() {
		logger.debug("Cleanup");
		dao.cleanupOrdersAndOderItems();
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
