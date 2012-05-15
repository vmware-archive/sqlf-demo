package com.vmware.example.sqlfire.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {

	private static final long serialVersionUID = 8476690589642393815L;
	private String id;
	private Timestamp on;
	private List<OrderItem> items;

	public Order() {
		items = new ArrayList<OrderItem>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getOn() {
		return on;
	}

	public void setOn(Timestamp on) {
		this.on = on;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", on=" + on + ", items=" + items + "]";
	}

}
