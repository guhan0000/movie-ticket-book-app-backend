package com.movieBookV2.sample;

import java.time.LocalTime;

public class TimeDateTest {
	
	public static void main(String[] args) {
		LocalTime now=LocalTime.now();
		LocalTime m1 = LocalTime.of(10, 0);
		System.out.println(now);
		System.out.println(now.getHour());
//		System.out.println(now.minusHours()));
		System.out.println(now.isBefore(m1));
	}
}
