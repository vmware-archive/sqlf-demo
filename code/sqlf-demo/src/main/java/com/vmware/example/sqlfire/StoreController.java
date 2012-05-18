package com.vmware.example.sqlfire;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vmware.example.sqlfire.domain.Request;
import com.vmware.example.sqlfire.domain.Response;
import com.vmware.example.sqlfire.service.StoreService;
import com.vmware.example.sqlfire.util.SimpleTimestampGenerator;

@Controller
@RequestMapping(value = "/store")
public class StoreController {

	private Map<String, StoreService> services;

	@Autowired
	SimpleTimestampGenerator timestampGenerator;

	private StoreService getService(String storeType) {
		if (!services.containsKey(storeType)) {
			throw new RuntimeException("Invalid store type");
		}
		return services.get(storeType);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getDataStoreServices() {
		return services.keySet();
	}

	@RequestMapping(value = "/{storeType}/request/{requestId}", method = RequestMethod.POST)
	public @ResponseBody
	Response makeRequest(@PathVariable String storeType,
			@PathVariable String requestId) {

		Request request = new Request();
		request.setId(requestId);
		request.setOn(timestampGenerator.getNow());
		request.setOrders(0);
		request.setItems(0);
		request.setDuration(new Long(0L));

		return getService(storeType).saveRequest(request);
	}

	@RequestMapping(value = "/{storeType}/request/{requestId}", method = RequestMethod.GET)
	public @ResponseBody
	Response getRequest(@PathVariable String storeType,
			@PathVariable String requestId) {

		Response response = getService(storeType).getRequest(requestId);
		response.getRequest().setIndex(0);

		return response;
	}

	@RequestMapping(value = "/{storeType}/order/", method = RequestMethod.POST)
	public @ResponseBody
	Response runRequest(@PathVariable String storeType,
			@RequestBody Request request) {

		request.setOn(timestampGenerator.getNow());
		request.setDuration(0L);

		StoreService service = getService(storeType);

		Response response = service.runRequest(request);
		response.getRequest().setIndex(request.getIndex());

		return response;
	}

	@RequestMapping(value = "/{storeType}/order/", method = RequestMethod.DELETE)
	public @ResponseBody
	String cleanup(@PathVariable String storeType) {

		getService(storeType).cleanup();

		return "OK";
	}

	@ExceptionHandler
	public @ResponseBody
	Response handle(Exception e) {
		return new Response(e.getMessage());
	}

	@Required
	public void setServices(Map<String, StoreService> services) {
		this.services = services;
	}

}
