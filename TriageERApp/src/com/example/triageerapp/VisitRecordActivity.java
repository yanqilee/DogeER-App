package com.example.triageerapp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/** VisitRecordActivity is the class that handles vieiwing/generating a patients' visit record.
 *
 *
 * @author group_0861
 */

public class VisitRecordActivity extends Activity {

	private Patient patient;
	private String usertype;
	private String username;
	
	@Override
	/** Displaying the activity layout on creation with Patient object passed over based
	 * 	on his/her health card number.
	 * 
	 * @param savedInstanceState - auto generated variable.
	 * @return void - this method returns nothing.
	 * 
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visit_record);
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		usertype = intent.getStringExtra("usertype");
		patient = (Patient) intent.getSerializableExtra("extraPatient");
	}

	@Override
	/** This method will the initialize the contents of the menu.
	 * 
	 * @param menu - auto generated variable.
	 * @return boolean - return true.
	 * 
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.visit_record, menu);
		return true;
	}
	
	/** Brings user back to PatientRecordActivity page, sending the patient instance.
	 * 
	 * @param view - auto generated variable.
	 * @return void - this method returns nothing.
	 */
	public void back(View view) {
		Intent intent = new Intent(this, PatientRecordActivity.class);
		intent.putExtra("extraPatient", patient);
		intent.putExtra("username", username);
		intent.putExtra("usertype", usertype);
		startActivity(intent);
	}
	
	/** Update a patient's visit record, if patient has no previous visit record,
	 * 	prompt the user to go to create new visit record instead
	 * 
	 * @param view - auto generated variable.
	 * @throws IOException - exception is thrown if file is not found.
	 * @return void - this method returns nothing.
	 * 
	 */
	public void save(View view) throws IOException {
		BufferedWriter bufferedWriter;
		
		Context context = getApplicationContext();
		CharSequence text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast;
		
		EditText tempText = (EditText) findViewById(R.id.editText1);
		String temp1 = tempText.getText().toString();
		EditText heartText = (EditText) findViewById(R.id.editText2);
		String heart = heartText.getText().toString();
		EditText bloodText = (EditText) findViewById(R.id.editText3);
		String blood = bloodText.getText().toString();
		EditText bloodText2 = (EditText) findViewById(R.id.editText4);
		String blood2 = bloodText2.getText().toString();
		EditText tempText2 = (EditText) findViewById(R.id.editText5);
		String temp2 = tempText2.getText().toString();
		String temp;
		if (!temp1.matches("") & temp2.matches("")){
			temp = temp1 + ".0";
		} else if (!temp1.matches("") & !temp2.matches("")) {
			temp = temp1 + "." + temp2;
		} else {
			temp = ".";
		}
		
		String filename = patient.getHCNumber() + ".txt";

		String holder = "";
		File f = new File(getFilesDir()+File.separator+filename);
		
		if (checkTemp(temp) & checkRate(heart) & checkPressure1(blood2) & checkPressure2(blood)) {
			holder = SystemER.getDate() + ",," + SystemER.getTime() + ",," + temp + ",," + heart + ",," + blood2 + ",," + blood + "\n";
			bufferedWriter = new BufferedWriter(new FileWriter(f, true));
			bufferedWriter.write(holder);
			bufferedWriter.close();
			vitalFile(temp, heart, blood2, blood);
			text = "Visit Record Saved";
			toast = Toast.makeText(context, text, duration);
			toast.show();
			tempText.setText("");
			heartText.setText("");
			bloodText.setText("");
			bloodText2.setText("");
			tempText2.setText("");
		} else {
			text = "Invalid input(s), please check to make sure input is within valid range";
			toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}
	
    /** This method handles creating a vitals file for a patients' vital signs.
     * 
     *  @param temp, heart, blood2, blood - Strings that represent the respective vital signs.
     *  @return void - this method returns nothing
     *  @throws IOException
     */
	public void vitalFile(String temp, String heart, String blood2, String blood) throws IOException {
		BufferedWriter bufferedWriter;

		String holder = "";
		File f = new File(getFilesDir()+File.separator+"vitals.txt");
		holder = patient.getHCNumber() + "," + temp + "," + heart + "," + blood2 + "," + blood + "\n";
		bufferedWriter = new BufferedWriter(new FileWriter(f, true));
		bufferedWriter.write(holder);
		bufferedWriter.close();
	}

	/** This method checks the user input temperature to check it's within human boundaries.
	 * 
	 * @param temp - the temperature inputed by the user.
	 * @return boolean - returns false if there is no input or if the temperature is outside of 
	 * human boundaries. Returns true if the input is within expected boundaries.
	 * 
	 */
	public boolean checkTemp(String temp) {
		if (temp.matches(".")) {
			return false;
		} else {
			Double dblTemp = Double.parseDouble(temp);
			if (dblTemp > 47 | dblTemp < 14) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	/** This method checks the user input blood pressure (systolic) to check it's within human boundaries.
	 * 
	 * @param blood - the blood pressure (systolic) inputed by the user.
	 * @return boolean - returns false if the user entered nothing or if the blood pressure (systolic)
	 * inputed is beyond human boundaries. Returns true if the input is within expected boundaries.
	 * 
	 */
	public boolean checkPressure1(String blood) {
		if (blood.matches("")) {
			return false;
		} else {
			Double dblBlood = Double.parseDouble(blood);
			if (dblBlood > 190 | dblBlood < 70) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	/** This method checks the user input blood pressure (diastolic) to check it's within human boundaries.
	 * 
	 * @param blood - the blood pressure (diastolic) inputed by the user.
	 * @return boolean - returns false if the user entered nothing or if the blood pressure (diastolic)
	 * inputed is beyond human boundaries. Returns true if the input is within expected boundaries.
	 * 
	 */
	public boolean checkPressure2(String blood) {
		if (blood.matches("")) {
			return false;
		} else {
			Double dblBlood = Double.parseDouble(blood);
			if (dblBlood > 100 | dblBlood < 40) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	/** This method checks the user input heart rate to check it's within human boundaries.
	 * 
	 * @param heart - the heart rate inputed by the user.
	 * @return boolean - returns false if the user entered nothing or if the heart rate inputed is beyond
	 * human boundaries. Returns true if the input is within expected boundaries.
	 * 
	 */
	public boolean checkRate(String heart) {
		if (heart.matches("")) {
			return false;
		} else {
			Double dblHeart = Double.parseDouble(heart);
			if (dblHeart > 220 | dblHeart < 1) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	/** This method handles action bar item clicks. 
	 * 
	 * @param item - auto generated variable.
	 * @return boolean - return true/false.
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
