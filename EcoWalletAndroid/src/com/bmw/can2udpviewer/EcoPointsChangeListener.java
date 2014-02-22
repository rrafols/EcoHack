package com.bmw.can2udpviewer;

public interface EcoPointsChangeListener {
	public void sessionStarted();	
	public void sessionStopped(int sessionTotal);
	public void ecoPointsChange(int delta);
}
