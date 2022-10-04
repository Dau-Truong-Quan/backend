package com.Quan.TryJWT.dto;

import java.util.List;

public class DashboadCompare {
	List<Float> lastyear ;
	List<Float> thisYear;
	public DashboadCompare(List<Float> lastyear, List<Float> thisYear) {
		super();
		this.lastyear = lastyear;
		this.thisYear = thisYear;
	}
	public List<Float> getLastyear() {
		return lastyear;
	}
	public void setLastyear(List<Float> lastyear) {
		this.lastyear = lastyear;
	}
	public List<Float> getThisYear() {
		return thisYear;
	}
	public void setThisYear(List<Float> thisYear) {
		this.thisYear = thisYear;
	}
	public DashboadCompare() {
		super();
	}
	
	
}
