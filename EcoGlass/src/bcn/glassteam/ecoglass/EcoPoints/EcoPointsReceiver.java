package bcn.glassteam.ecoglass.EcoPoints;

import java.util.Random;

import android.content.Context;
import android.os.Handler;


public class EcoPointsReceiver {

	private Handler mMainThreadHandler;
	private IEcoPointsListener mListener;
	private Thread mExecutor;
	
	public EcoPointsReceiver(Context context) {
		mMainThreadHandler = new Handler(context.getMainLooper());
	}
	
	public boolean startReceiving() {
		mExecutor = new Thread(createFakeReceiver());
		mExecutor.start();
		return true;
	}
	
	public void setEcoPointsListener(IEcoPointsListener listener) {
		mListener = listener;
	}
	
	public boolean stopReceiving() {
		if (null != mExecutor) {
			mExecutor.interrupt();
			mExecutor = null;
		}
		return true;
	}
	
	private Runnable createFakeReceiver() {
		return new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						final int points = new Random().nextInt(6) + 1; // Varation of [1, 6)
						
						if (null != mListener) {
							mMainThreadHandler.post(new Runnable() {
								
								@Override
								public void run() {
									mListener.onEcoPointsUpdated(points);
								}
							});
						}
						
						int variation = new Random().nextInt(2) + 1;
						Thread.sleep(variation * 1000); // Fake variation every [1, 2) seconds
						
					}
				} catch (InterruptedException e) {
					// This is expected
				}
			}
		};
	}
}
