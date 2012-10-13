package com.android.counterapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ListCountActivity extends Activity {

	// This is the Adapter being used to display the list's data
	SimpleAdapter adapter;

	private static final String INTERNAL_STORAGE_FILENAME = "counts.json";

	// These are the Contacts rows that we will retrieve

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_list_count);
		ListView listView = (ListView) findViewById(R.id.listViewCount);

		// ListView listView = getListView();

		List<Map<String, String>> clicks = new ArrayList<Map<String, String>>();
		clicks = getClicksFromFile();

		adapter = new SimpleAdapter(this, clicks,
				android.R.layout.simple_list_item_2, new String[] { "name",
						"date" }, new int[] { android.R.id.text1,
						android.R.id.text2 });

		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_2, myStringArray);

		listView.setAdapter(adapter);

	}

	private List<Map<String, String>> getClicksFromFile() {
		String eol = System.getProperty("line.separator");
		String x = "";

		List<Map<String, String>> clicks = new ArrayList<Map<String, String>>();

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

				// x += "JSON parsed.\nThere are [" + entries.length() +
				// "]\n\n";

				int i;
				for (i = 0; i < entries.length(); i++) {

					JSONObject post = entries.getJSONObject(i);
					String name = post.getString("name");
					String date = post.getString("date");
					// x += "------------\n";
					// x += "name:" + name + "\n";
					// x += "name:" + date + "\n\n";
					Map<String, String> datum = new HashMap<String, String>(2);
					datum.put("name", name);
					datum.put("date", date);
					clicks.add(datum);
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
		return clicks;
	}

}
