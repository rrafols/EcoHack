package com.hacktheride.ecowallet;

import com.bmw.can2udpviewer.HistoricEcoPoints;
import com.bmw.can2udpviewer.MainActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.TextView;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		TextView pin = (TextView) findViewById(R.id.editText2);
		pin.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(s.length() == 4) {
					loginOk();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {}
		});
	}
	
	private void loginOk() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}
