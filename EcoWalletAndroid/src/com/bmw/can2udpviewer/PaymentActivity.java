package com.bmw.can2udpviewer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.hacktheride.ecowallet.R;

public class PaymentActivity extends Activity {

	private Activity mActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment_layout);

		mActivity = this;

		TextView balance = (TextView) findViewById(R.id.textViewCurrentBalance);
		final EditText amount = (EditText) findViewById(R.id.amount2pay);

		balance.setText(Integer.toString(getIntent().getExtras().getInt(
				"Balance")));

		Button botonpago = (Button) findViewById(R.id.confirmaPago);
		botonpago.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		
				LinearLayout l = (LinearLayout) mActivity
						.findViewById(R.id.LayoutPaymentActivity);
				l.setVisibility(View.INVISIBLE);
				imm.hideSoftInputFromWindow(l.getWindowToken(), 0);
				PopupWindow pwindo;
				Bitmap bmp = BitmapFactory.decodeResource(getResources(),
						R.drawable.qr);
				// Para que se pueda cerrar el popup, se pone la imagen
				// de fondo del framelayout
				LayoutInflater inflater = (LayoutInflater) mActivity
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View layout = inflater.inflate(R.layout.qr_layout, null);

				pwindo = new PopupWindow(layout, bmp.getWidth(), bmp
						.getHeight(), true);

				pwindo.setBackgroundDrawable(new BitmapDrawable(mActivity
						.getResources(), bmp));

				final TextView fondoQr = (TextView) mActivity
						.findViewById(R.id.FondoQr);
				fondoQr.setVisibility(View.VISIBLE);
				pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
				pwindo.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss() {
						fondoQr.setVisibility(View.GONE);
						Intent returnIntent = new Intent();
						returnIntent.putExtra("result",
								Integer.parseInt(amount.getText().toString()));
						setResult(RESULT_OK, returnIntent);
						mActivity.finish();
					}
				});
			}
		});
	}
}
