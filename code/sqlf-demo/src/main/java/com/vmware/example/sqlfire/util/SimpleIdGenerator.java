package com.vmware.example.sqlfire.util;

import java.util.UUID;

public class SimpleIdGenerator {

	public String getNextId() {
		return UUID.randomUUID().toString();
	}
}
