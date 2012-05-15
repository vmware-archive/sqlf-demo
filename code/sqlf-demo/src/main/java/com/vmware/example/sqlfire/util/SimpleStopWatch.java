package com.vmware.example.sqlfire.util;

public class SimpleStopWatch {

	private long startTime = 0;
	private long stopTime = 0;
	private boolean running = false;

	public SimpleStopWatch() {
	}

	public SimpleStopWatch(Boolean start) {
		if (start) {
			start();
		}
	}

	public void start() {
		this.startTime = System.currentTimeMillis();
		this.running = true;
	}

	public void stop() {
		this.stopTime = System.currentTimeMillis();
		this.running = false;
	}

	public long getDuration() {
		long elapsed;
		if (running) {
			elapsed = (System.currentTimeMillis() - startTime);
		} else {
			elapsed = (stopTime - startTime);
		}
		return elapsed;
	}

}
