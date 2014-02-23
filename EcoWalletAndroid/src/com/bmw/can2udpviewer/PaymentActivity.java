package com.bmw.can2udpviewer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
		balance.setText(Integer.toString(getIntent().getExtras().getInt("Balance")));
		
		Button botonpago = (Button) findViewById(R.id.confirmaPago);
		botonpago.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LinearLayout l = (LinearLayout) mActivity.findViewById(R.id.LayoutPaymentActivity);
				l.setVisibility(View.INVISIBLE);
				PopupWindow pwindo;
				Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.qr);
				// Para que se pueda cerrar el popup, se pone la imagen
				// de fondo del framelayout
				LayoutInflater inflater = (LayoutInflater) mActivity
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View layout = inflater.inflate(
						R.layout.qr_layout, null);

				pwindo = new PopupWindow(layout, bmp.getWidth(), bmp
						.getHeight(), true);

				pwindo.setBackgroundDrawable(new BitmapDrawable(
						mActivity.getResources(), bmp));

				final TextView fondoQr = (TextView) mActivity.findViewById(R.id.FondoQr);
				fondoQr.setVisibility(View.VISIBLE);
				pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
				pwindo.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss() {
						fondoQr.setVisibility(View.GONE);
						mActivity.finish();
					}
				});
			}
		});
	}
}
