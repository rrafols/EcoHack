package com.bmw.can2udpviewer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.hacktheride.ecowallet.R;

public class PaymentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment_layout);
		
		TextView balance = (TextView) findViewById(R.id.textViewCurrentBalance);
		balance.setText(Integer.toString(getIntent().getExtras().getInt("Balance")));
	}
}
