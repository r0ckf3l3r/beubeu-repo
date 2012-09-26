package com.android.counterapp;

import java.util.Date;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.android.counterapp.model.Click;

public class MainActivity extends Activity {

	private int numOne;

	private int numTwo;

	private int numThree;

	private int numFour;

	private LinkedList<Click> clicks;

	private LinkedList<Location> locations;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		clicks = new LinkedList<Click>();

		initCounter();
		initLocationManager();

		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText(getDial());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);

		return true;
	}

	public String getDial() {
		return numFour + "" + numThree + "" + numTwo + "" + numOne + "";
	}

	public void increment(View view) {
		incNumOne();
		addClick();

		System.out.println("incrementing: " + getDial());
		System.out.println("clicks size: " + clicks.size());

		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText(getDial());
	}

	public void decrement(View view) {
		decNumOne();
		removeClick();

		System.out.println("decrementing: " + getDial());
		System.out.println("clicks size: " + clicks.size());

		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText(getDial());
	}

	public void clearCounter(View view) {
		initCounter();
		System.out.println("reseting counter: " + getDial());

		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText(getDial());
	}

	private void initCounter() {
		numOne = 0;
		numTwo = 0;
		numThree = 0;
		numFour = 0;
	}

	private void incNumOne() {
		if (numOne < 9)
			numOne++;
		else {
			numOne = 0;
			incNumTwo();
		}
	}

	private void incNumTwo() {
		if (numTwo < 9)
			numTwo++;
		else {
			numTwo = 0;
			incNumThree();
		}
	}

	private void incNumThree() {
		if (numThree < 9)
			numThree++;
		else {
			numThree = 0;
			incNumFour();
		}
	}

	private void incNumFour() {
		if (numFour < 9)
			numFour++;
		else {
			numFour = 0;
		}
	}

	private void decNumOne() {
		if (numOne > 0)
			numOne--;
		else if (numTwo > 0 || numThree > 0 || numFour > 0) {
			numOne = 9;
			decNumTwo();
		}
	}

	private void decNumTwo() {
		if (numTwo > 0)
			numTwo--;
		else {
			numTwo = 9;
			decNumThree();
		}
	}

	private void decNumThree() {
		if (numThree > 0)
			numThree--;
		else {
			numThree = 9;
			decNumFour();
		}
	}

	private void decNumFour() {
		if (numFour > 0)
			numFour--;
		else {
			numFour = 9;
		}
	}

	private void addClick() {
		Location location;

		String clickName = this.clicks.size() + 1 + "";
		Date date = new Date();

		Click click = new Click();

		click.setCount(1);
		click.setDate(date);
		click.setName(clickName);
		click.setUserId(1);

		try {
			location = this.locations.getLast();

			System.out.println("adding new location: " + location.getLatitude()
					+ ", " + location.getLongitude());
			click.setLatitude(location.getLatitude());
			click.setLongitude(location.getLongitude());

		} catch (Exception e) {
			System.out.println("there are no locations");
		}

		this.clicks.add(click);
	}

	private void removeClick() {
		if (!this.clicks.isEmpty())
			this.clicks.removeLast();
	}

	private void addLocation(Location location) {
		this.locations.add(location);
	}

	// private void removeLocation() {
	// if (!this.locations.isEmpty())
	// this.locations.removeLast();
	// }

	private void initLocationManager() {
		// Acquire a reference to the system Location Manager
		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location
				// provider.
				addLocation(location);
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
				System.out.println("location provider enabled: " + provider);
			}

			public void onProviderDisabled(String provider) {
				System.out.println("location provider disabled: " + provider);
			}
		};

		// Register the listener with the Location Manager to receive location
		// updates
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, locationListener);

	}
}
