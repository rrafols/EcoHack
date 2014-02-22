package com.hacktheride.demoname;

import android.app.Activity;
import android.os.Bundle;

import com.bmw.can2udpviewer.EventCallbackUDP;
import com.bmw.can2udpviewer.UDPData;
import com.bmw.can2udpviewer.UDPReceiver;

public class MainActivity extends Activity implements EventCallbackUDP {

	private UDPReceiver udpReceiver = new UDPReceiver(30002, this);
	
	//zaska
	public static UDPData udpData;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        udpReceiver.start();
    }

	@Override
	public void EventReceivedUDP(UDPData udpData) {
		this.udpData = udpData;
	}    
}
