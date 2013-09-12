package submonkey.simpletracking;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PublisherActivity extends Activity implements LocationListener {
	
	Publisher publisher;
	private TextView latitudeText;
	private TextView longitudeText;
	private LocationManager manager;
	private String provider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		publisher = new Publisher();
		setContentView(R.layout.main);
		
		final EditText serverText = (EditText) findViewById(R.id.server);
		final EditText portText = (EditText) findViewById(R.id.port);
	
		final EditText userText = (EditText) findViewById(R.id.user);
		final Spinner roleSpinner = (Spinner) findViewById(R.id.role);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, 
			R.array.roles, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		roleSpinner.setAdapter(adapter);
		
		latitudeText = (TextView) findViewById(R.id.latitude);
		longitudeText = (TextView) findViewById(R.id.longitude);
		
		manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = manager.getBestProvider(criteria, false);
		Location location = manager.getLastKnownLocation(provider);
		
		if (location != null) {
			onLocationChanged(location);
		} else {
			latitudeText.setText("Location not available");
			longitudeText.setText("Location not available");
		}
		
		Button connectButton = (Button) findViewById(R.id.connectButton);
		connectButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String server = serverText.getText().toString();
				int port = Integer.parseInt(portText.getText().toString());
				Log.i("Publisher", "Server: " + server + ", Port: " + port);
				publisher.connect(server, port);
				Log.i("Publisher", "connect");
			}
		});
		
		Button trackButton = (Button) findViewById(R.id.trackButton);
		trackButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				manager.requestLocationUpdates(provider, 400, 1, PublisherActivity.this);
			}
		});
		Button publishButton = (Button) findViewById(R.id.publishButton);
		publishButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("Publisher", "publish started");
				String user = userText.getText().toString();
				String role = roleSpinner.getSelectedItem().toString();
				double latitude = Double.parseDouble(latitudeText.getText().toString());
				double longitude = Double.parseDouble(longitudeText.getText().toString());
				publisher.publish(user, role, latitude, longitude);
				Log.i("Publisher", "publish");
			}
		});;
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
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		latitudeText.setText(String.valueOf(latitude));
		longitudeText.setText(String.valueOf(longitude));
	}
	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
	
	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "Enabled new provider " + provider, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "Disabled provider " + provider, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.publisher, menu);
		return true;
	}

}
