package com.bmw.can2udpviewer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.hacktheride.ecowallet.R;


public class MainActivity extends Activity implements EventCallbackUDP {
	public static final String TAG = "HackTheRide";
	
	private UDPReceiver udpReceiver = new UDPReceiver(30002, this);
	private EcoPointsChangeListener ecoPointsListener;
	private int lastEcoPoints = 0;
	private int accEcoPoints = 0;
	
	//zaska
	public static UDPData udpData = new UDPData();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        udpReceiver.start();
    }
    
    public void setEcoPointsChangeListener(EcoPointsChangeListener listener) {
    	this.ecoPointsListener = listener;
    }
    
    @Override
    protected void onDestroy() {
    	udpReceiver.Terminate();
    	super.onDestroy();
    }

	@Override
	public void EventReceivedUDP(UDPData udpData) {
		double energyRechargeFactor = 0.00015259;
		double energyDischargeFactor = 0.00015259;
		double consumptionWeight = 0.5;
		double rechargeWeight = 0.5;
		
		double discharge = ((double) udpData.EnergyDischarged.getValue()) * energyDischargeFactor;
		double recharge = ((double) udpData.EnergyChargedByRecuperation.getValue()) * energyRechargeFactor;
		
		double dischargePoints = 0;
		if (discharge >= 8.0) dischargePoints = 1;
		else if (discharge >= 7.0) dischargePoints = 2;
		else if (discharge >= 6.0) dischargePoints = 3;
		else if (discharge >= 5.0) dischargePoints = 4;
		else dischargePoints = 5;
		
		double rechargePoints = 0;
		if (recharge <= 0.5) rechargePoints = 1;
		else if (recharge <= 1.0) rechargePoints = 2;
		else if (recharge <= 1.5) rechargePoints = 3;
		else if (recharge <= 2.0) rechargePoints = 4;
		else rechargePoints = 5;
		
		double ecoPointsF = (consumptionWeight * dischargePoints + rechargeWeight * rechargePoints) / 2.0;
		long ecoPointsR = Math.round(ecoPointsF);
		Log.i(TAG, "EcoPoints: " + discharge + " (" + dischargePoints + "), " + recharge + " (" + rechargePoints + ") " + ecoPointsF + "(" + ecoPointsR + ") vs " + udpData.ECOPoints.getValue());
		
		
		int ecoPoints = udpData.ECOPoints.getValue();
		if(ecoPoints != lastEcoPoints) {
			int delta = ecoPoints - ecoPoints;
			accEcoPoints += delta;
			
			if(ecoPointsListener != null) {
				ecoPointsListener.ecoPointsChange(delta);
			}
		}
	}

	@Override
	public void sessionStarted() {
		lastEcoPoints = -1;
		
		if (ecoPointsListener != null) {
			ecoPointsListener.sessionStarted();
		}
	}

	@Override
	public void sessionStopped() {
		if (ecoPointsListener != null) {
			ecoPointsListener.sessionStopped((accEcoPoints > 0) ? accEcoPoints : 0);
		}
	}    
}
