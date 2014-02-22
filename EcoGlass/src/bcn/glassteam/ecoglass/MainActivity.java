package bcn.glassteam.ecoglass;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.beyondar.android.fragment.BeyondarFragmentSupport;
import com.beyondar.android.world.GeoObject;
import com.beyondar.android.world.World;

public class MainActivity extends FragmentActivity {
	
	private BeyondarFragmentSupport mBeyondarFragment;
	private World mWorld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        
        mBeyondarFragment = (BeyondarFragmentSupport) getSupportFragmentManager().findFragmentById(R.id.beyondarFragment);
        
        mWorld = new World(this);
        mWorld.setGeoPosition(41.386995, 2.171488);
        
        GeoObject obj1 = new GeoObject();
        obj1.setGeoPosition(41.387003, 2.171212);
        obj1.setImageResource(R.drawable.ic_launcher);
        mWorld.addBeyondarObject(obj1);
        
        mBeyondarFragment.setWorld(mWorld);
    }
}