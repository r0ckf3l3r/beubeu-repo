package com.android.counterapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private int numOne = 0;

	private int numTwo = 0;

	private int numThree = 0;

	private int numFour = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);

		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText(getDial());

		return true;
	}

	public String getDial() {
		return numFour + "" + numThree + "" + numTwo + "" + numOne + "";
	}

	public void increment(View view) {
		incNumOne();
		System.out.println("incrementing: " + getDial());

		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText(getDial());
	}

	public void decrement(View view) {
		decNumOne();
		System.out.println("decrementing: " + getDial());

		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText(getDial());
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

}
