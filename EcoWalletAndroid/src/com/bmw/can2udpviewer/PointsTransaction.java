package com.bmw.can2udpviewer;


public class PointsTransaction {
	private String mDate;
	private int mAmount;
	private String mOrigin;
	
	PointsTransaction(String shop, int amount, String date){
		mDate = date;
		mAmount = amount;
		mOrigin = shop;
	}
	
	public String getDate(){
		return mDate;
	}
	
	public String getShop(){
		return mOrigin;
	}
	
	public int getAmount() {
		return mAmount;
	}
}
