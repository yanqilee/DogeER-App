package com.example.triageerapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/** PatientRecordActivity is the class which handles creating a record file for each patient.
 * 
 * @author group_0861
 */
public class PatientRecordActivity extends Activity {

	private Patient patient; 
	private String usertype;
	private String username;
	@Override
	/** Get the patient sent over from HCNSearchActivity, print out the name,
	 *  health card number, and date of birth of the patient.
	 * 
	 * @param savedInstanceState - auto generated variable.
	 * @return void - this method returns nothing.
	 * 
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_record);
		Intent intent = getIntent();
		patient = (Patient) intent.getSerializableExtra("extraPatient");
		usertype = intent.getStringExtra("usertype");
		username = intent.getStringExtra("username");
		
		int timesSeen = 0;
		String lastSeen = "";
		try {
			timesSeen = timesSeen(patient);
			lastSeen = lastSeenOn(patient);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TextView nameText = (TextView) findViewById(R.id.textView1);
		nameText.setText("Name: " + patient.getName());
		TextView hcnText = (TextView) findViewById(R.id.textView2);
		hcnText.setText("Health card number: " + patient.getHCNumber());
		TextView dobText = (TextView) findViewById(R.id.textView3);
		dobText.setText("Date of birth: " + patient.getDOB());
		TextView timeSeenText = (TextView) findViewById(R.id.textView4);
		timeSeenText.setText("Times seen by doctor: " + timesSeen);
		TextView lastSeenText = (TextView) findViewById(R.id.textView5);
		if (timesSeen > 0) {
			lastSeenText.setVisibility(View.VISIBLE);
			lastSeenText.setText("Last seen on: " + lastSeen);
		} else {
			lastSeenText.setVisibility(View.GONE);
		}
		
		Button butNew = (Button) findViewById(R.id.button1);
		Button butUpdate = (Button) findViewById(R.id.button2);
		Button butPrescription = (Button) findViewById(R.id.button5);
		Button markSeen = (Button) findViewById(R.id.button6);

		if (usertype.matches("physician")) {
			butNew.setVisibility(View.GONE);
			butUpdate.setVisibility(View.GONE);
			markSeen.setVisibility(View.GONE);
			butPrescription.setVisibility(View.VISIBLE);
		} else {
			butPrescription.setVisibility(View.GONE);
			butNew.setVisibility(View.VISIBLE);
			butUpdate.setVisibility(View.VISIBLE);
			markSeen.setVisibility(View.VISIBLE);
		}

	}
	/** This method returns the time in which a patient was last seen by a doctor.
     * 
     * @param patient - the patient for whom we'd like to find out when they were last seen.
     * @return time - a string representation of the time last seen.
     * @throws IOException - this is thrown if the textfile relating to the patient isn't found.
     */
	public String lastSeenOn(Patient patient) throws IOException {
		BufferedReader bufferedReader;
		
		String filename = patient.getHCNumber() + ".txt";

		String read = "";
		File f = new File(getFilesDir()+File.separator+filename);
		String[] tempArr;
		String time = "";
		
		bufferedReader = new BufferedReader(new FileReader(f));
		if(f.exists() && !f.isDirectory()) {
			while((read = bufferedReader.readLine()) != null) {
				tempArr = read.split(",,");
				if (tempArr[2].equals(" ")) {
					time = tempArr[0] + ", " + tempArr[1];
				}
			}	
		}
		bufferedReader.close();
		return time;
	}
	/** This method returns the number of times a patient has been seen by a doctor.
     * 
     * @param patient - the patient object for who'd we'd like to know how many times they were seen.
     * @return count - an integer representing the number of times the patient was seen.
     * @throws IOException - this is thrown if the text file relating to the patient isn't found.
     */
	public int timesSeen(Patient patient) throws IOException {
		BufferedReader bufferedReader;
		
		String filename = patient.getHCNumber() + ".txt";

		String read = "";
		File f = new File(getFilesDir()+File.separator+filename);
		String[] tempArr;
		int count = 0;
		
		bufferedReader = new BufferedReader(new FileReader(f));
		if(f.exists() && !f.isDirectory()) {
			while((read = bufferedReader.readLine()) != null) {
				tempArr = read.split(",,");
				if (tempArr[2].equals(" ")) {
					count += 1;
				}
			}	
		} else {
			count = 0;
		}
		bufferedReader.close();
		return count;
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
		getMenuInflater().inflate(R.menu.patient_record, menu);
		return true;
	}
	
	/** Brings user back to HCNSearchActivity page, sending the patient instance.
	 * 
	 * @param view - auto generated variable.
	 * @return void - this method returns nothing.
	 */
	public void back(View view) {
		Intent intent = new Intent(this, HCNSearchActivity.class);
		intent.putExtra("username", username);
		intent.putExtra("usertype", usertype);
		startActivity(intent);
	}
	/** This method updates the perscription data for some patient.
     * 
     * @param view - auto generated variable.
     * @return void - this method returns nothing.
     */
	public void updatePrescription(View view) {
		Intent intent = new Intent(this, UpdatePrescriptionActivity.class);

		Context context = getApplicationContext();
		CharSequence text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast;
		
		String filename = patient.getHCNumber() + ".txt";
		File f = new File(getFilesDir()+File.separator+filename);
		
		if(f.exists() && !f.isDirectory()) {
			intent.putExtra("extraPatient", patient);
			intent.putExtra("usertype", usertype);
			intent.putExtra("username", username);
			startActivity(intent);
		} else {
			text = "No visit record exists for this patient.";
			toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}
	
	/** This method creates a new visit record text file for some patient.
	 * 
	 * @param view - auto generated variable.
	 * @return void - this method returns nothing.
	 * 
	 */
	public void newRecord(View view) {
		Intent intent = new Intent(this, NewRecordActivity.class);
		intent.putExtra("extraPatient", patient);
		intent.putExtra("usertype", usertype);
		intent.putExtra("username", username);
		
		Context context = getApplicationContext();
		CharSequence text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast;
		
		String filename = patient.getHCNumber() + ".txt";
		File f = new File(getFilesDir()+File.separator+filename);
		
		if(f.exists() && !f.isDirectory()) {
			text = "Visit record already exist, please update the record";
			toast = Toast.makeText(context, text, duration);
			toast.show();
		} else {
			startActivity(intent);
		}
	}
	
	/** This method allows us to view past record(s) of some patient.
	 * 
	 * @param view - auto generated variable.
	 * @return void - this method returns nothing.
	 * 
	 */
	public void viewRecord(View view) {
		Intent intent = new Intent(this, ViewRecordActivity.class);
		intent.putExtra("extraPatient", patient);
		intent.putExtra("usertype", usertype);
		intent.putExtra("username", username);
		
		Context context = getApplicationContext();
		CharSequence text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast;
		
		String filename = patient.getHCNumber() + ".txt";
		File f = new File(getFilesDir()+File.separator+filename);
		
		if(f.exists() && !f.isDirectory()) {
			startActivity(intent);
		} else {
			text = "Visit record does not exist, please create the record";
			toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}
	
	/** This method allows us to update the record of some patient.
	 * 
	 * @param view - auto generated variable.
	 * @return void - this method returns nothing. 
	 * 
	 */
	public void updateRecord(View view) {
		Intent intent = new Intent(this, VisitRecordActivity.class);
		intent.putExtra("extraPatient", patient);
		intent.putExtra("usertype", usertype);
		intent.putExtra("username", username);

		Context context = getApplicationContext();
		CharSequence text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast;
		
		String filename = patient.getHCNumber() + ".txt";
		File f = new File(getFilesDir()+File.separator+filename);
		
		if(f.exists() && !f.isDirectory()) {
			startActivity(intent);
		} else {
			text = "Visit record does not exist, please create the record";
			toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}
	/** This method handles marking that a patient who was previously unseen by a doctor, has now been
     *  seen.
     * 
     *  @param view - auto generated variable.
     *  @return void - this method returns nothing
     *  @throws IOException - this is thrown if the txt file relating to the patient is not found.
     */
	public void markSeen(View view) throws IOException {
		Context context = getApplicationContext();
		CharSequence text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast;
		
		String filename = patient.getHCNumber() + ".txt";
		File f = new File(getFilesDir()+File.separator+filename);
		
		if(f.exists() && !f.isDirectory()) {
			BufferedWriter bufferedWriter;
			String holder = "";
			holder = SystemER.getDate() + ",," + SystemER.getTime() + ",, ,, ,, ,, \n";
			bufferedWriter = new BufferedWriter(new FileWriter(f, true));
			bufferedWriter.write(holder);
			bufferedWriter.close();
			
			int timesSeen = 0;
			String lastSeen = "";
			try {
				timesSeen = timesSeen(patient);
				lastSeen = lastSeenOn(patient);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			TextView timeSeenText = (TextView) findViewById(R.id.textView4);
			TextView lastSeenText = (TextView) findViewById(R.id.textView5);
			timeSeenText.setText("Times seen by doctor: " + timesSeen);
			lastSeenText.setText("Last seen on: " + lastSeen);
			lastSeenText.setVisibility(View.VISIBLE);
			
			text = "Doctor Visit Recorded";
			toast = Toast.makeText(context, text, duration);
			toast.show();
		} else {
			text = "Please create a visit record first.";
			toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}
	
	@Override
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
