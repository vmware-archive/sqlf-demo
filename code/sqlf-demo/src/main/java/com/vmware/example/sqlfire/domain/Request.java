package com.vmware.example.sqlfire.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class Request implements Serializable {

	private static final long serialVersionUID = 7792722216893124128L;

	private String id;
	private Timestamp on;
	private Integer orders;
	private Integer items;
	private Long duration;
	private Integer index;

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

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	public Integer getItems() {
		return items;
	}

	public void setItems(Integer items) {
		this.items = items;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "Request [id=" + id + ", on=" + on + ", orders=" + orders
				+ ", items=" + items + ", duration=" + duration + ", index="
				+ index + "]";
	}

}
