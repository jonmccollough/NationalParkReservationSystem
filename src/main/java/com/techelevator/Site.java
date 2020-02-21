package com.techelevator;

import java.math.BigDecimal;

public class Site {
	
	private Long siteId;
	private Long campgroundId;
	private Long siteNumber;
	private boolean accessible;
	private int maxRvLength;
	private boolean utilities;
	private BigDecimal dailyFee;
	
	
	
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public Long getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(Long campgroundId) {
		this.campgroundId = campgroundId;
	}
	public Long getSiteNumber() {
		return siteNumber;
	}
	public void setSiteNumber(Long site_number) {
		this.siteNumber = site_number;
	}
	public boolean isAccessible() {
		return accessible;
	}
	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}
	public int getMaxRvLength() {
		return maxRvLength;
	}
	public void setMaxRvLength(int max_rv_length) {
		this.maxRvLength = max_rv_length;
	}
	public boolean isUtilities() {
		return utilities;
	}
	public void setUtilities(boolean utilities) {
		this.utilities = utilities;
	}
	public BigDecimal getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(BigDecimal dailyFee) {
		this.dailyFee = dailyFee;
	}
	
	

}
