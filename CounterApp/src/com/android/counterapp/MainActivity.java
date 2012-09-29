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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.counterapp.model.Click;

public class MainActivity extends Activity {

	private int numOne;

	private int numTwo;

	private int numThree;

	private int numFour;

	private LinkedList<Click> clicks;

	// private LinkedList<Location> locations;

	Location lastLocation;

	LocationManager locationManager;

	LocationListener locationListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		clicks = new LinkedList<Click>();
		// locations = new LinkedList<Location>();

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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_clear:
			clearCounter();
			return true;
		case R.id.menu_save:
			save();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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

	public void clearCounter() {
		System.out.println("reseting counter: " + getDial());

		initCounter();

		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText(getDial());
	}

	public void save() {
		System.out.println("saving. ");
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

		String clickName = this.clicks.size() + 1 + "";
		Date date = new Date();

		Click click = new Click();

		click.setCount(1);
		click.setDate(date);
		click.setName(clickName);
		click.setUserId(1);

		if (this.lastLocation != null) {
			System.out.println("adding new location: "
					+ this.lastLocation.getLatitude() + ", "
					+ this.lastLocation.getLongitude());
			click.setLatitude(this.lastLocation.getLatitude());
			click.setLongitude(this.lastLocation.getLongitude());
		} else {
			System.out.println("there are no locations");
		}

		this.clicks.add(click);
	}

	private void removeClick() {
		if (!this.clicks.isEmpty())
			this.clicks.removeLast();
	}

	private void addLocation(Location location) {
		this.lastLocation = location;
	}

	private void initLocationManager() {
		// Acquire a reference to the system Location Manager
		this.locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		this.locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				if (location == null)
					return;

				addLocation(location);
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				System.out.println("status changed" + provider);
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
		try {
			this.locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0,
					this.locationListener);
		} catch (Exception e) {
			System.out.println("unable to register location listener: "
					+ e.getMessage());
		}

		try {

			this.locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, this.locationListener);
		} catch (Exception e) {
			System.out.println("unable to register location listener: "
					+ e.getMessage());
		}

	}

}
