package com.movieBookV2.sample;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

 class Example{
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
}

public class TestShow {
	
	public static void create(LocalDate startDate, LocalDate endDate) {
//		System.out.println(startDate+" "+endDate);
//		if(LocalDate.now().equals(startDate))
		long between = ChronoUnit.DAYS.between(startDate, endDate);
		LocalDate today=LocalDate.now();
//		System.out.println(LocalDate.now().equals(startDate));
//		System.out.println(LocalDate.now().isAfter(startDate));
//		System.out.println(LocalDate.now().isBefore(endDate));
//		System.out.println(between);
		int showCount=0;
		List<Example> list=new ArrayList<>();
		if((startDate.isEqual(today) ) || startDate.isBefore(endDate)) {
			for(int i=0;i<7;i++) {
				Example example=new Example();
				example.setName(Integer.toString(i));
				example.setStartDate(startDate.plusDays(i));
				list.add(example);
				showCount++;
			}
			
		}
		System.out.println(showCount+" "+list);
		for(Example example:list) {
			System.out.println(example.getName()+" "+example.getStartDate());
		}
	}
	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		String startDate=scanner.next();
		String endDate=scanner.next();
		create(LocalDate.parse(startDate), LocalDate.parse(endDate));
//		System.out.println(localDate);
		
	}
}
