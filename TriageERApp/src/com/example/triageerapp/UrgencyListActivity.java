package com.example.triageerapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/** The UrgencyListActivity class is what handles the generation of the urgency list as per
 *  the requirements specified in the handout. (Unseen patients in descending order of 
 *  urgency points).
 * 
 *  @author group_0861
 */
public class UrgencyListActivity extends Activity {

	private Map<String, String[]> vital;
	private Map<String, Integer> urgency;
	private String username;
	private String usertype;
	
	@Override
    /** Displaying the activity page on creation.
     *
     * @param saveInstanceState - auto generated variable.
     * @return void - this method returns nothing
     *
     */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_urgency_list);
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		usertype = intent.getStringExtra("usertype");
		vital = new TreeMap<String, String[]>();
		urgency = new TreeMap<String, Integer>();
		try {
			loadVital();
			genUrgencyList();
			displayList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    /** This method handles actually displaying the urgency list.
     *
     *  @param this method takes no parameters.
     *  @return void - this method returns nothing.
     *  @throws IOException
     */
	public void displayList() throws IOException {
		TextView urgencyText = (TextView) findViewById(R.id.textView1);
		String record = "";
		Patient tempPatient;
		List<String> urg0 = new ArrayList<String>();
		List<String> urg1 = new ArrayList<String>();
		List<String> urg2 = new ArrayList<String>();
		List<String> urg3 = new ArrayList<String>();
		List<String> urg4 = new ArrayList<String>();
		for (String key: urgency.keySet()) {
			if (urgency.get(key) == 0) {
				urg0.add(key);
			} else if (urgency.get(key) == 1) {
				urg1.add(key);
			} else if (urgency.get(key) == 2) {
				urg2.add(key);
			} else if (urgency.get(key) == 3) {
				urg3.add(key);
			} else if (urgency.get(key) == 4) {
				urg4.add(key);
			}
		}
		Integer counter = new Integer(0);
		if (!urg4.isEmpty()) {
			record += "Urgency level: 4\n";
		}
		for (String hcn: urg4) {
			tempPatient = getPatient(hcn);
			counter += 1;
			record += "\t" + counter.toString() + ". Name: " + tempPatient.getName() + "\n\t     Health card number: " + tempPatient.getHCNumber() + "\n\t     Date of birth: " + tempPatient.getDOB() + "\n";
		}
		if (!urg3.isEmpty()) {
			record += "Urgency level: 3\n";
		}
		for (String hcn: urg3) {
			tempPatient = getPatient(hcn);
			counter += 1;
			record += "\t" + counter.toString() + ". Name: " + tempPatient.getName() + "\n\t     Health card number: " + tempPatient.getHCNumber() + "\n\t     Date of birth: " + tempPatient.getDOB() + "\n";
		}
		if (!urg2.isEmpty()) {
			record += "Urgency level: 2\n";
		}
		for (String hcn: urg2) {
			tempPatient = getPatient(hcn);
			counter += 1;
			record += "\t" + counter.toString() + ". Name: " + tempPatient.getName() + "\n\t     Health card number: " + tempPatient.getHCNumber() + "\n\t     Date of birth: " + tempPatient.getDOB() + "\n";
		}
		if (!urg1.isEmpty()) {
			record += "Urgency level: 1\n";
		}
		for (String hcn: urg1) {
			tempPatient = getPatient(hcn);
			counter += 1;
			record += "\t" + counter.toString() + ". Name: " + tempPatient.getName() + "\n\t     Health card number: " + tempPatient.getHCNumber() + "\n\t     Date of birth: " + tempPatient.getDOB() + "\n";
		}
		if (!urg0.isEmpty()) {
			record += "Urgency level: 0\n";
		}
		for (String hcn: urg0) {
			tempPatient = getPatient(hcn);
			counter += 1;
			record += "\t" + counter.toString() + ". Name: " + tempPatient.getName() + "\n\t     Health card number: " + tempPatient.getHCNumber() + "\n\t     Date of birth: " + tempPatient.getDOB() + "\n";
		}
		
		urgencyText.setText(record);
		urgencyText.setMovementMethod(new ScrollingMovementMethod());
	}
	
    /** This method loads the vitals.
     *  
     *  @param this method takes no inputs.
     *  @return void - this method returns nothing.
     *  @throws IOException - this is thrown if the vitals.txt file is not found.
     
     */
	public void loadVital() throws IOException {
		File f = new File(getFilesDir()+File.separator+"vitals.txt");
		BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
		
		String read = "";
		String[] tempArr;
		String[] tempList = {"0", "1", "2", "3"};
		
		while((read = bufferedReader.readLine()) != null) {
			tempArr = read.split(",");
			tempList[0] = tempArr[1];
			tempList[1] = tempArr[2];
			tempList[2] = tempArr[3];
			tempList[3] = tempArr[4];
			vital.put(tempArr[0], tempList.clone());
		}
		bufferedReader.close();
	}
	
    /** This method actually generates the urgency list.
     * 
     *  @param this method takes no inputs.
     *  @return void - this method returns nothing
    *   @throws IOException
     */
	public void genUrgencyList() throws IOException {
		Integer urgent = new Integer(0);
		Vitals vitals;
		String[] tempArr;
		for (String key: this.vital.keySet()) {
			tempArr = vital.get(key);
			vitals = new Vitals(tempArr[0], tempArr[1], tempArr[2], tempArr[3]);
			urgent = vitals.getUrgency(getPatient(key).getAge());
			urgency.put(key, urgent);
		}
	}
	
    /** This method returns the patient object with a given health card number.
     *
     *  @param hcn - a String of some patients' health card number.
     *  @return tempPatient - a Patient object whose hcn is the one given.
     *  @throws IOException - this is thrown if the txt file is not found.
     */
	public Patient getPatient(String hcn) throws IOException {
		BufferedReader bufferedReader;

		String read = "";
		File f = new File(getFilesDir()+File.separator+"patient_records.txt");
		String[] tempArr;
		String name = "";
		String dob = "";
		
		bufferedReader = new BufferedReader(new FileReader(f));
		// remember to handle letters and obscure numbers

		while((read = bufferedReader.readLine()) != null) {
			tempArr = read.split(",");
			if (tempArr[0].equals(hcn)) {
				name = tempArr[1];
				dob = tempArr[2];
			}
		}
		bufferedReader.close();
		Patient tempPatient = new Patient(name, hcn, dob);
		return tempPatient;
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
    /** This method initializes the contents of the Activity's standard options menu.
     *
     * @param menu - The options menu in which you place your items.
     * @return boolean - Must return True for the menu to be displayed, if the return is false then
     * the menu will not be shown.
     * 
     */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.urgency_list, menu);
		return true;
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
