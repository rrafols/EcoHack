package com.bmw.can2udpviewer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hacktheride.ecowallet.R;

public class MainActivity extends Activity implements EventCallbackUDP, EcoPointsChangeListener {
	public static final String TAG = "HackTheRide";

	private static final int CHARGING_CONDITION_CHARGING = 1;
	private static final int CHARGING_CONDITION_NOT_CHARGING = 2;
	
	private UDPReceiver udpReceiver = new UDPReceiver(30002, this);
	private EcoPointsChangeListener ecoPointsListener;
	private int lastEcoPoints = 0;
	private int accEcoPoints = 0;
	private ArrayList<Integer> mHistoricEcoPointsValues = new ArrayList<Integer>();
	private ArrayList<String> mHistoricEcoPointsDates = new ArrayList<String>();
	private ArrayList<String> mHistoricEcoPointsOrigins = new ArrayList<String>();
	private Activity mThisActivity;

	private boolean sessionStarted = false;
	
	public static UDPData udpData = new UDPData();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		udpReceiver.start();

		// Hardcoded historic EcoPoints
		mHistoricEcoPointsValues.add(Integer.valueOf(5));
		mHistoricEcoPointsDates.add("02/12/2014 15:00");
		mHistoricEcoPointsOrigins.add("Moto");
		mHistoricEcoPointsValues.add(Integer.valueOf(-2));
		mHistoricEcoPointsDates.add("02/12/2014 17:43");
		mHistoricEcoPointsOrigins.add("Moto2");
		mHistoricEcoPointsValues.add(Integer.valueOf(3));
		mHistoricEcoPointsDates.add("02/13/2014 09:03");
		mHistoricEcoPointsOrigins.add("Moto3");
		mHistoricEcoPointsValues.add(Integer.valueOf(0));
		mHistoricEcoPointsDates.add("02/14/2014 14:12");
		mHistoricEcoPointsOrigins.add("Moto4");
		mHistoricEcoPointsValues.add(Integer.valueOf(1));
		mHistoricEcoPointsDates.add("02/14/2014 15:00");
		mHistoricEcoPointsOrigins.add("Moto5");

		mThisActivity = this;
		
		// Historic button listener
		ImageButton historic = (ImageButton) findViewById(R.id.historicButton);
		historic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mThisActivity, HistoricEcoPoints.class);
				intent.putIntegerArrayListExtra("TransactionAmounts", mHistoricEcoPointsValues);
				intent.putStringArrayListExtra("TransactionDates", mHistoricEcoPointsDates);
				intent.putStringArrayListExtra("TransactionOrigins", mHistoricEcoPointsOrigins);
				mThisActivity.startActivity(intent);
			}
		});
		
		// Payment button listener
		Button payment = (Button) findViewById(R.id.botonPago);
		payment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mThisActivity, PaymentActivity.class);
				TextView balance = (TextView) mThisActivity.findViewById(R.id.currentBalance);
				intent.putExtra("Balance", Integer.parseInt(balance.getText().toString()));
				mThisActivity.startActivity(intent);
			}
		});
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
		/*
		double energyRechargeFactor = 0.00015259;
		double energyDischargeFactor = 0.00015259;
		double consumptionWeight = 0.5;
		double rechargeWeight = 0.5;

		double discharge = ((double) udpData.EnergyDischarged.getValue())
				* energyDischargeFactor;
		double recharge = ((double) udpData.EnergyChargedByRecuperation
				.getValue()) * energyRechargeFactor;

		double dischargePoints = 0;
		if (discharge >= 8.0)
			dischargePoints = 1;
		else if (discharge >= 7.0)
			dischargePoints = 2;
		else if (discharge >= 6.0)
			dischargePoints = 3;
		else if (discharge >= 5.0)
			dischargePoints = 4;
		else
			dischargePoints = 5;

		double rechargePoints = 0;
		if (recharge <= 0.5)
			rechargePoints = 1;
		else if (recharge <= 1.0)
			rechargePoints = 2;
		else if (recharge <= 1.5)
			rechargePoints = 3;
		else if (recharge <= 2.0)
			rechargePoints = 4;
		else
			rechargePoints = 5;

		double ecoPointsF = (consumptionWeight * dischargePoints + rechargeWeight
				* rechargePoints) / 2.0;
		long ecoPointsR = Math.round(ecoPointsF);
		Log.i(TAG, "EcoPoints: " + discharge + " (" + dischargePoints + "), " + recharge + " (" + rechargePoints + ") " + ecoPointsF + "(" + ecoPointsR + ") vs " + udpData.ECOPoints.getValue());
		*/
		
		if(sessionStarted) {
			int ecoPoints = udpData.ECOPoints.getValue();
			if(ecoPoints != lastEcoPoints) {
				int delta = ecoPoints - ecoPoints;
				accEcoPoints += delta;
				
				if(ecoPointsListener != null) {
					ecoPointsListener.ecoPointsChange(delta);
				}
			}
			
			int chargingCondition = udpData.ChargingCondition.getValue();
			
			// if we're charging the bike and session has been started, we stop the session
			if(chargingCondition == CHARGING_CONDITION_CHARGING) {
				stopSession();
			}
		}
	}
	
	@Override
	public void sessionStopped() {
		stopSession();
	}
	
	@Override
	public void sessionStarted() {
		lastEcoPoints = -1;

		if (ecoPointsListener != null) {
			ecoPointsListener.sessionStarted();
		}
		
		sessionStarted = true;
	}

	private void stopSession() {
		if (ecoPointsListener != null) {
			ecoPointsListener.sessionStopped((accEcoPoints > 0) ? accEcoPoints
					: 0);
		}
		sessionStarted = false;
	}

	@Override
	public void sessionStopped(int sessionTotal) {
		// TODO Comunicar al servidor

		mHistoricEcoPointsValues.add(Integer.valueOf(sessionTotal));

		// UI global ecoPoints score
		TextView totalEcopints = (TextView) findViewById(R.id.currentBalance);
		int lastTotalScore = Integer.parseInt((String) totalEcopints.getText());
		totalEcopints.setText(Integer.toString(lastTotalScore + sessionTotal));

		// UI last ride update
		TextView lastRide = (TextView) findViewById(R.id.lastRidePunctuations);
		if (sessionTotal == 0)
			lastRide.setTextColor(getResources().getColor(
					android.R.color.holo_orange_light));
		else
			lastRide.setTextColor(getResources().getColor(
					android.R.color.holo_green_light));
		lastRide.setText(Integer.toString(sessionTotal));
	}

	@Override
	public void ecoPointsChange(int delta) {
		// TODO Auto-generated method stub
		// TODO Modificar la UI
	}
}
