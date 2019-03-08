package com.example.hand.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Base64.Decoder;

public class TimedPoint {
	public final float x;
	public final float y;
	public final long timestamp;

	public TimedPoint(float x, float y) {
		this.x = x;
		this.y = y;
		this.timestamp = System.currentTimeMillis();
	}

	public float velocityFrom(TimedPoint start) {
		float velocity = distanceTo(start) / (this.timestamp - start.timestamp);
		if (velocity != velocity)
			return 0f;
		return velocity;
	}

	public float distanceTo(TimedPoint point) {
		return (float) Math.sqrt(Math.pow(point.x - this.x, 2) + Math.pow(point.y - this.y, 2));
	}
	
	

}