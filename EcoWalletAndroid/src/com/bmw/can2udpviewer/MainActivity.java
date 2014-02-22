package com.bmw.can2udpviewer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.hacktheride.ecowallet.R;


public class MainActivity extends Activity implements EventCallbackUDP {
	public static final String TAG = "HackTheRide";
	
	private UDPReceiver udpReceiver = new UDPReceiver(30002, this);
	private int ecoPoints = -1;
	
	//zaska
	public static UDPData udpData = new UDPData();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        udpReceiver.start();
    }
    
    @Override
    protected void onDestroy() {
    	udpReceiver.Terminate();
    	super.onDestroy();
    }

	@Override
	public void EventReceivedUDP(UDPData udpData) {
//		Log.i(TAG, "Data received: " + udpData.Alive.getValue() + " :: " + udpData.ECOPoints.getValue() + " :: " + ((int) udpData.SpeedWheelFront.getValue()) + " " + ((int) udpData.SpeedWheelFront.getValue()));
		Log.i(TAG, "Data received: " + udpData.Range.getValue() + " :: " + (((float) udpData.StateOfCharge.getValue()) / 2.54) + " vrodes: " + ((int) udpData.SpeedWheelFront.getValue()) + " " + ((int) udpData.SpeedWheelFront.getValue()));
		int currentEco = udpData.ECOPoints.getValue();
		if(currentEco != ecoPoints) {
			Log.i(TAG, "EcoPoints Change: " + currentEco);
			ecoPoints = currentEco;
		}
	}    
}
