package com.example.triageerapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/** NewPatientActivity is the class which handles the creation of a new
 *  patient by a nurse.
 *
 * @author group_0861
 */
public class NewPatientActivity extends Activity {
	
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
		setContentView(R.layout.activity_new_patient);
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		usertype = intent.getStringExtra("usertype");
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
		getMenuInflater().inflate(R.menu.new_patient, menu);
		return true;
	}
	/** This method checks if the health card number provided exists in 
     *  patient_records.txt
     *
     * @param hcn - this is the health card number of the patient we want to create.
     * @return boolean - Returns true if the person doesn't exist in the txt file (i.e. they're new).
     * @throws IOException - thrown if there is no patient_records.txt file
     */
	private boolean checkUniqueHCN(String hcn) throws IOException {

		BufferedReader bufferedReader;
		String[] tempArr;
		List<String> healthCards = new ArrayList<String>();
		String line = null;
		
		File f = new File(getFilesDir()+File.separator+"patient_records.txt");
		bufferedReader = new BufferedReader(new FileReader(f));
		
		 while ((line = bufferedReader.readLine()) != null)
		    {
		        tempArr = line.split(",");
		        healthCards.add(tempArr[0]);
		    }
		 
		 bufferedReader.close();
		 return !(healthCards.contains(hcn));
	}
	/** This method actually placing the patient into the txt file (i.e. creating them).
     *
     * @param view - auto generated variable.
     * @return void - this method returns nothing.
     * @throws IOException - this is thrown if the there is no patient_records.txt file.
     */
	public void addPatient(View view) throws IOException {
		BufferedWriter bufferedWriter;
		
		Context context = getApplicationContext();
		CharSequence text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast;

		EditText nameText = (EditText) findViewById(R.id.editText1);
		String name = nameText.getText().toString();
		EditText hcnText = (EditText) findViewById(R.id.editText2);
		String hcn = hcnText.getText().toString();
		EditText dobYText = (EditText) findViewById(R.id.editText3);
		String dobY = dobYText.getText().toString();
		EditText dobMText = (EditText) findViewById(R.id.editText4);
		String dobM = dobMText.getText().toString();
		EditText dobDText = (EditText) findViewById(R.id.editText5);
		String dobD = dobDText.getText().toString();


		String holder = "";
		File f = new File(getFilesDir()+File.separator+"patient_records.txt");
		
		if (!(name.equals("")) && !(hcn.length() < 6) && (Integer.parseInt(dobY) >= 1884) && (Integer.parseInt(dobY) <= 2014) &&
				(Integer.parseInt(dobM) <= 12) && (Integer.parseInt(dobM) >= 1) && (dobCheck(dobY, dobM, dobD))) {
			if (checkUniqueHCN(hcn)) {
				holder = "\n" + hcn + "," + name + "," + dobY + "-" + dobM + "-" + dobD;
				bufferedWriter = new BufferedWriter(new FileWriter(f, true));
				bufferedWriter.write(holder);
				bufferedWriter.close();
				text = "Patient has been added to the database.";
				toast = Toast.makeText(context, text, duration);
				toast.show();
				nameText.setText("");
				hcnText.setText("");
				dobYText.setText("");
				dobMText.setText("");
				dobDText.setText("");
			} else {
				text = "A patient with that health card number already exists.";
				toast = Toast.makeText(context, text, duration);
				toast.show();
			}
		} else {
			text = "Invalid input(s), please check to make sure inputs are within valid range.";
			toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}
	
    /** Checks if date is within boundary of the month.
    *
    * @param dobY - year input.
    * @param dobM - month input.
    * @param dobD - date input.
    * @return boolean - this method return true if date is possible within the month, false otherwise
    */
	public boolean dobCheck(String dobY, String dobM, String dobD) {
		List<Integer> thirtyDays = new ArrayList<Integer>();
		int[] arrThirtyDays = {4, 6, 9, 11};
		for (int i: arrThirtyDays) {
			thirtyDays.add(i);
		}
		List<Integer> thirtyOneDays = new ArrayList<Integer>();
		int[] arrThirtyOneDays = {1, 3, 5, 7, 8, 10, 12};
		for (int i: arrThirtyOneDays) {
			thirtyOneDays.add(i);
		}
		int intDobD = Integer.parseInt(dobD);
		if (thirtyOneDays.contains(Integer.parseInt(dobM))) {
			if (intDobD > 0 & intDobD < 32) {
				return true;
			}
		}
		if (thirtyDays.contains(Integer.parseInt(dobM))) {
			if (intDobD > 0 & intDobD < 31) {
				return true;
			}
		}
		if (Integer.parseInt(dobM) == 2) {
			if (intDobD > 0 & intDobD < 30 & Integer.parseInt(dobY) % 4 == 0) {
				return true;
			} else if (intDobD > 0 & intDobD < 29 & Integer.parseInt(dobY) % 4 != 0) {
				return true;
			}
		}
		return false;
	}
	
    /** Brings user back to LoginActivity page.
     *
     * @param view - auto generated variable.
     * @return void - this method returns nothing.
     */
	public void back(View view) {
		Intent intent = new Intent(this, NurseMenuActivity.class);
		intent.putExtra("username", username);
		intent.putExtra("usertype", usertype);
		startActivity(intent);
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
