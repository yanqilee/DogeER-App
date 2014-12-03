package com.example.triageerapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/** HCNSearchActivity is the class which handles searching for a patient by their 
 *  health card number. 
 *  
 * @author group_0861
 */
public class HCNSearchActivity extends Activity {
	
	private String usertype;
	private String username;
	@Override
	/** Displaying the activity page on creation.
	 *
	 * @param saveInstanceState - auto generated variable.
	 * @return void - this method returns nothing
	 * 
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		usertype = intent.getStringExtra("usertype");
		username = intent.getStringExtra("username");
		setContentView(R.layout.activity_hcnsearch);
	}

	@Override
	/** This method initializes the contents of the Activity's standard options menu. 
	 * 
	 * @param menu - The options menu in which you place your items.
	 * @return boolean - Must return True for the menu to be displayed, if the return is false then 
	 * the menu will not be shown.
	 * 
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hcnsearch, menu);
		return true;
	}
	
	/** Look up the information of a patient based on his/her 6-digit health card number.
	 * 	If patient is not found or health card number is invalid, pop a toast saying so.
	 * 
	 * @param view - auto generated variable.
	 * @throws IOException - exception is thrown when text file is not found
	 * @return void - this method returns nothing
	 * 
	 */
	public void lookUp(View view) throws IOException {
		Intent intent = new Intent(this, PatientRecordActivity.class);
		Context context = getApplicationContext();
		CharSequence text = "Health card number must be 6 characters long.";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		Map<String, Patient> patientMap = new TreeMap<String, Patient>();
		Patient tempPat;
		String[] tempArr;
		
		EditText hcnText = (EditText) findViewById(R.id.editText1);
		String hcn = hcnText.getText().toString();
		
		// check health card number validity
		if (hcn.length() < 6) {
			toast.show();
		} else {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new 
				                 File(getFilesDir()+File.separator+"patient_records.txt")));
			String read;
				
			while((read = bufferedReader.readLine()) != null){
				tempArr = read.split(",");
				tempPat = new Patient(tempArr[1], tempArr[0], tempArr[2]);
				patientMap.put(tempArr[0], tempPat);
			}
			bufferedReader.close();
			if (patientMap.keySet().contains(hcn)) {
				tempPat = patientMap.get(hcn);
				intent.putExtra("extraPatient", tempPat);
				intent.putExtra("usertype", usertype);
				intent.putExtra("username", username);
				startActivity(intent);
			} else {
				text = "Patient does not exist.";
				toast = Toast.makeText(context, text, duration);
				toast.show();
			}			
		}
	}
	
	/** Brings user back to LoginActivity page.
	 * 
	 * @param view - auto generated variable.
	 * @return void - this method returns nothing.
	 */
	public void back(View view) {
		Intent intent = new Intent(this, LoginActivity.class);
		Intent intent2 = new Intent(this, NurseMenuActivity.class);
		intent2.putExtra("username", username);
		intent2.putExtra("usertype", usertype);
		
		if (usertype.matches("physician")) {
			startActivity(intent);
		} else {
			startActivity(intent2);
		}
	}
	
	@Override
	/** This method handles action bar item clicks. 
	 * 
	 * @param item - auto generated variable.
	 * @return boolean - Return false to allow normal menu processing to proceed, true to consume it here. 
	 * 
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
