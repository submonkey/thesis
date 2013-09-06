package submonkey.locationmarker;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MarkerActivity extends Activity implements LocationListener {

	private LocationManager manager;
	private String provider;
	private DatabaseHelper db;
	
	private TextView latitudeText;
	private TextView longitudeText;
	private EditText area1Text;
	private EditText area2Text;
	private EditText area3Text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = manager.getBestProvider(criteria, false);
		android.location.Location location = manager.getLastKnownLocation(provider);
		db = new DatabaseHelper(this);
		
		latitudeText = (TextView) findViewById(R.id.latitude);
		longitudeText = (TextView) findViewById(R.id.longitude);
		area1Text = (EditText) findViewById(R.id.area1);
		area2Text = (EditText) findViewById(R.id.area2);
		area3Text = (EditText) findViewById(R.id.area3);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		manager.requestLocationUpdates(provider, 400, 1, this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		manager.removeUpdates(this);
	}
	
	@Override
	public void onLocationChanged(Location location) {
		double lat = location.getLatitude();
		double lng = location.getLongitude();
		latitudeText.setText(String.valueOf(lat));
		longitudeText.setText(String.valueOf(lng));
	}
	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
	
	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "Enabled new provider " + provider, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "Disabled new provider " + provider, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.marker, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			manager.requestLocationUpdates(provider, 400, 1, this);
			break;
		case R.id.action_save:
			Model model = new Model();
			model.setArea1(area1Text.getText().toString());
			model.setArea2(area2Text.getText().toString());
			model.setArea3(area3Text.getText().toString());
			model.setLatitude(Double.parseDouble(latitudeText.getText().toString()));
			model.setLatitude(Double.parseDouble(latitudeText.getText().toString()));
			db.addLocation(model);
			Toast.makeText(this, "Location has been saved", Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
		return true;
	}

}
