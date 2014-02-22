package com.bmw.can2udpviewer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.hacktheride.ecowallet.R;


public class MainActivity extends Activity implements EventCallbackUDP {
	public static final String TAG = "HackTheRide";
	
	private UDPReceiver udpReceiver = new UDPReceiver(30002, this);
	
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
		Log.i(TAG, "Data received: " + udpData.SpeedWheelFront + " " + udpData.SpeedWheelFront);
	}    
}
