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

/** UpdatePrescriptionActivity is the class which handles creating/updating perscriptions for 
 *  patients.
 * 
 *  @author group_0861
 */
public class UpdatePrescriptionActivity extends Activity {
	
	private Patient patient;
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
		setContentView(R.layout.activity_update_prescription);
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		usertype = intent.getStringExtra("usertype");
		patient = (Patient) intent.getSerializableExtra("extraPatient");
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
		getMenuInflater().inflate(R.menu.update_prescription, menu);
		return true;
	}
	/** This method handles adding a perscription for a patient into their textfile.
     * 
     *  @param view - auto generated variable.
     *  @return void - this method returns nothing.
     *  @throws IOException - this is thrown if the patients' txt file is not found.
     */
	public void addPrescription(View view) throws IOException {
		BufferedWriter bufferedWriter;
		
		Context context = getApplicationContext();
		CharSequence text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast;

		EditText medicationText = (EditText) findViewById(R.id.editText1);
		String medication = medicationText.getText().toString();
		EditText instructionsText = (EditText) findViewById(R.id.editText2);
		String instructions = instructionsText.getText().toString();

		String filename = patient.getHCNumber() + ".txt";

		String holder = "";
		File f = new File(getFilesDir()+File.separator+filename);
		
		if (!(medication.equals("")) && !(instructions.equals(""))) {
			holder = SystemER.getDate() + ",," + SystemER.getTime() + ",," + medication + ",," + instructions + ",, ,, \n";
			bufferedWriter = new BufferedWriter(new FileWriter(f, true));
			bufferedWriter.write(holder);
			bufferedWriter.close();
			text = "Prescription Information Added";
			toast = Toast.makeText(context, text, duration);
			toast.show();
			medicationText.setText("");
			instructionsText.setText("");
		} else {
			text = "Invalid input(s), please check to make sure input is within valid range";
			toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}

    /** Brings user back to LoginActivity page.
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
