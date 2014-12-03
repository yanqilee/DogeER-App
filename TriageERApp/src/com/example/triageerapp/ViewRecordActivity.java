package com.example.triageerapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/** ViewRecordActivity is the class which handles viewing patient records. 
 * 
 * @author group_0861
 */
public class ViewRecordActivity extends Activity {

	private Patient patient;
	private String usertype;
	private String username;
	
	@Override
	/** Displaying the activity layout on creation with Patient object passed over based
	 * 	on his/her health card number, then print out his/her record.
	 * 
	 * @param savedInstanceState - auto generated variable.
	 * @return void - this method returns nothing.
	 * 
	 */
	protected void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_record);
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		usertype = intent.getStringExtra("usertype");
		patient = (Patient) intent.getSerializableExtra("extraPatient");
		try {
			getRecord();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** This method retrieves the record for a patient and parses the text.
	 * 
	 * @param this method takes no inputs.
	 * @throws IOException - this exception will be thrown if the record doesn't exist.
	 * @return void this method returns nothing.
	 * 
	 */
	public void getRecord() throws IOException {
		BufferedReader bufferedReader;
		
		String filename = patient.getHCNumber() + ".txt";

		String read = "";
		File f = new File(getFilesDir()+File.separator+filename);
		String[] tempArr;
		
		TextView recordText = (TextView) findViewById(R.id.textView1);
		String record = "";
		Integer count = new Integer(0);
		
		bufferedReader = new BufferedReader(new FileReader(f));
		// remember to handle letters and obscure numbers
		read = bufferedReader.readLine();
		tempArr = read.split(",,");
		record += "Arrival Time: " + tempArr[0] + ", " + tempArr[1] + "\n\t" + "Temperature: " + tempArr[2] + " degrees celcius\n\tHeart Rate: " + tempArr[3] + " bpm\n\tBlood Pressure(Systolic): " + tempArr[4] + " mmHg\n\tBlood Pressure(Diastolic): " + tempArr[5] + " mmHg"; 

		while((read = bufferedReader.readLine()) != null) {
			tempArr = read.split(",,");
			count += 1;
			if (tempArr[2].equals(" ")) {
				record += "\nUpdate " + count.toString() + "\n\tSeen By Doctor At: " + tempArr[0] + ", " + tempArr[1];
			} else if (tempArr[4].equals(" ")) {
				record += "\nUpdate " + count.toString() + "\n\tTime Recorded: " + tempArr[0] + ", " + tempArr[1] + "\n\tPrescription: " + tempArr[2] + "\n\tInstruction: " + tempArr[3];
			} else {
				record += "\nUpdate " + count.toString() + "\n\tTime Recorded: " + tempArr[0] + ", " + tempArr[1] + "\n\tTemperature: " + tempArr[2] + " degrees celcius\n\tHeart Rate: " + tempArr[3] + " bpm\n\tBlood Pressure(Systolic): " + tempArr[4] + " mmHg\n\tBlood Pressure(Diastolic): " + tempArr[5] + " mmHg"; 
			}
		}
		bufferedReader.close();

		recordText.setText(record);
		recordText.setMovementMethod(new ScrollingMovementMethod());
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
		getMenuInflater().inflate(R.menu.view_record, menu);
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
