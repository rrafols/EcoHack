package com.bmw.can2udpviewer;

import android.app.Activity;
import android.os.Bundle;

import com.hacktheride.ecowallet.R;

public class HistoricEcoPoints extends Activity {

	private EcoPointsAdaptor mEcoPointsAdaptor;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic);
        mEcoPointsAdaptor.addAll(savedInstanceState.getIntegerArrayList("HistoricPointsValue"));
    }
}
