package com.bmw.can2udpviewer;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.hacktheride.ecowallet.R;

public class HistoricEcoPoints extends Activity {

	private EcoPointsAdaptor mEcoPointsAdaptor;
	private ArrayList<PointsTransaction> mTransactions = new ArrayList<PointsTransaction>();
	private ArrayList<String> mEcoPointsOrigins = new ArrayList<String>();
	private ArrayList<Integer> mEcoPointsAmounts = new ArrayList<Integer>();
	private ArrayList<String> mEcoPointsDates = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historic);

		mEcoPointsAmounts.addAll(getIntent().getExtras()
				.getIntegerArrayList("TransactionAmounts"));
		mEcoPointsOrigins.addAll(getIntent().getExtras()
				.getStringArrayList("TransactionDates"));
		mEcoPointsDates.addAll(getIntent().getExtras()
				.getStringArrayList("TransactionOrigins"));

		for (int i = 0; i < mEcoPointsAmounts.size(); i++) {
			mTransactions.add(new PointsTransaction(mEcoPointsOrigins.get(i),
					mEcoPointsAmounts.get(i), mEcoPointsDates.get(i)));
		}
		mEcoPointsAdaptor = new EcoPointsAdaptor(this.getBaseContext(),
				R.id.layout_transaction, mTransactions);
		ListView list = (ListView) findViewById(R.id.listTransactions);
		list.setAdapter(mEcoPointsAdaptor);
		mEcoPointsAdaptor.notifyDataSetChanged();
	}
}
