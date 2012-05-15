package com.vmware.example.sqlfire.util;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Timestamp generator
 * 
 * @author mchmarny
 * 
 */
public class SimpleTimestampGenerator {

	public Timestamp getNow() {
		return new Timestamp(Calendar.getInstance().getTime().getTime());
	}

}
