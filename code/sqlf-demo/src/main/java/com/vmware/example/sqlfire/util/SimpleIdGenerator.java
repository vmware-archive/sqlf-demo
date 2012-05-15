package com.vmware.example.sqlfire.util;

import java.security.SecureRandom;
import java.util.UUID;

public class SimpleIdGenerator {

	private static final SecureRandom secureRandom = new SecureRandom();

	public String getNextId() {
		return UUID.randomUUID().toString() + secureRandom.nextLong();
	}
}
