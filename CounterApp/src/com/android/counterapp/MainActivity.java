package com.android.counterapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.counterapp.model.Click;
import com.android.counterapp.ListCountActivity;;

@SuppressLint("WorldWriteableFiles")
public class MainActivity extends Activity {

	private static final int DIALOG_CLEAR_COUNTER = 0;

	private static final int DIALOG_SAVE_COUNT = 1;

	private static final String TEXTVIEWNOGPS = "NO GPS";

	private static final String TEXTVIEWGPSOK = "GPS OK";

	private static final String TEXTVIEWNOWIRELESS = "No Wireless";

	private static final String TEXTVIEWWIRELESSOK = "Wireless OK";

	private static final String INTERNAL_STORAGE_FILENAME = "counts.json";

	private int numOne;

	private int numTwo;

	private int numThree;

	private int numFour;

	private LinkedList<Click> clicks;

	// private LinkedList<Location> locations;

	private Location lastLocation;

	private LocationManager locationManager;

	private LocationListener locationListener;

	private ConnectivityManager connectivityManager;

	private TextView tv;

	private TextView tvWireless;

	private TextView tvGPS;

	protected boolean wirelessOK = false;

	private boolean gpsOk = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initCounter();
		initLocationManager();
		initConnectivityManager();

		checkNetworkConnection();

		tv = (TextView) findViewById(R.id.textView1);
		tvWireless = (TextView) findViewById(R.id.TextViewWireless);
		tvGPS = (TextView) findViewById(R.id.TextViewGPS);

		tv.setText(getDial());

		updateGPSStatus();
		updateNetworkStatus();

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
		case R.id.menu_list_count:
			listCount();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		AlertDialog.Builder builder;

		switch (id) {
		case DIALOG_CLEAR_COUNTER:

			builder = new AlertDialog.Builder(this);
			builder.setMessage("Clear counter?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									initCounter();
									TextView tv = (TextView) findViewById(R.id.textView1);
									tv.setText(getDial());
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});

			dialog = builder.create();

			break;

		case DIALOG_SAVE_COUNT:

			LayoutInflater inflater = (LayoutInflater) this
					.getSystemService(LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.save_dialog,
					(ViewGroup) findViewById(R.id.layout_root));

			final EditText label = (EditText) layout
					.findViewById(R.id.save_dialog_text);

			Date date = new Date();
			label.setHint(date.toString());

			builder = new AlertDialog.Builder(this);
			builder.setView(layout)
					.setCancelable(false)
					.setPositiveButton("Save",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									try {
										saveClicksToFile(label.getText()
												.toString());
									} catch (IOException e) {
										System.out
												.println("Error saving clicks: "
														+ e.getMessage());
									}
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			;

			// This line will go in your OnClickListener.
			// chkBox.setEnabled(false);

			// dialog = builder.create();
			// CheckBox chkBox = (CheckBox) dialog
			// .findViewById(R.id.upload_checkbox);

			// chkBox.setEnabled(gpsOk);

			dialog = builder.create();
			break;

		default:
			dialog = null;
		}

		return dialog;
	}

	protected void onDestroy() {
		locationManager.removeUpdates(locationListener);
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

	@SuppressWarnings("deprecation")
	public void clearCounter() {
		System.out.println("reseting counter: " + getDial());

		showDialog(DIALOG_CLEAR_COUNTER);

		System.out.println("counter: " + getDial());
	}

	@SuppressWarnings("deprecation")
	public void save() {
		System.out.println("saving. ");
		showDialog(DIALOG_SAVE_COUNT);
	}

	public void listCount() {
		System.out.println("listing counts");
		Intent intent = new Intent(this, ListCountActivity.class);
		startActivity(intent);

	}

	private void initCounter() {

		if (clicks == null) {
			clicks = new LinkedList<Click>();
		} else {
			clicks.clear();
		}
		// locations = new LinkedList<Location>();

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

	private void initConnectivityManager() {
		connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	private void checkNetworkConnection() {
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			wirelessOK = true;
		} else {
			wirelessOK = false;
		}
	}

	private void updateGPSStatus() {
		if (gpsOk) {
			tvGPS.setText(TEXTVIEWGPSOK);
		} else {
			tvGPS.setText(TEXTVIEWNOGPS);

		}
	}

	private void updateNetworkStatus() {
		if (wirelessOK) {
			tvWireless.setText(TEXTVIEWWIRELESSOK);
		} else {
			tvWireless.setText(TEXTVIEWNOWIRELESS);

		}
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

	private void saveClicksToFile(String label) throws IOException {

		JSONObject jclick;
		JSONArray jclicks = new JSONArray();
		JSONArray jcounts = new JSONArray();

		JSONObject jcount = new JSONObject();

		try {

			for (Click click : clicks) {
				jclick = new JSONObject();

				jclick.put("date", click.getDate());
				jclick.put("userId", click.getUserId());
				jclick.put("name", click.getName());
				jclick.put("latitude", click.getLatitude());
				jclick.put("longitude", click.getLongitude());
				jclick.put("count", click.getCount());

				jclicks.put(jclick);
			}

			jcount.put("name", label);
			jcount.put("date", new Date());
			jcount.put("clicks", jclicks);

			jcounts.put(jcount);

			writeFileToInternalStorage(jcounts.toString());

		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		readClicks();
	}

	public void readClicks() {
		readFileFromInternalStorage();
	}

	private void writeFileToInternalStorage(String string) {
		String eol = System.getProperty("line.separator");

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(openFileOutput(
					INTERNAL_STORAGE_FILENAME, MODE_APPEND)));
			writer.write(string + eol);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	private void readFileFromInternalStorage() {
		String eol = System.getProperty("line.separator");
		String x = "";

		BufferedReader input = null;
		try {
			input = new BufferedReader(new InputStreamReader(
					openFileInput(INTERNAL_STORAGE_FILENAME)));
			String line;
			StringBuffer buffer = new StringBuffer();

			while ((line = input.readLine()) != null) {
				buffer.append(line + eol);
				String jsontext = new String(line);
				JSONArray entries = new JSONArray(jsontext);
				// JSONObject entries = new JSONObject(jsontext);

				x = "JSON parsed.\nThere are [" + entries.length() + "]\n\n";

				int i;
				for (i = 0; i < entries.length(); i++) {

					JSONObject post = entries.getJSONObject(i);
					x += "------------\n";
					x += "name:" + post.getString("name") + "\n";
					x += "name:" + post.getString("date") + "\n\n";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("saved: " + x);
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
