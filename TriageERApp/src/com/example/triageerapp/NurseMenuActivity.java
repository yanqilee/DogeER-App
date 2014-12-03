package com.example.triageerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/** NurseMenuActivity is the class which handles displaying the menu that is specific
 *  to a nurse user (since their features differ from a physicians).
 *
 * @author group_0861
 */
public class NurseMenuActivity extends Activity {
	
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
		setContentView(R.layout.activity_nurse_menu);
		
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		usertype = intent.getStringExtra("usertype");
		TextView nurseName = (TextView) findViewById(R.id.textView1);
		nurseName.setText("Welcome, " + username);
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
		getMenuInflater().inflate(R.menu.nurse_menu, menu);
		return true;
	}
	
	/** Brings user back to LoginActivity page.
	 * 
	 * @param view - auto generated variable.
	 * @return void - this method returns nothing.
	 */
	public void back(View view) {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}
    /** This method handles the case where the nurse is creating a new patient.
     *
     * @param view - auto generated variable.
     * @return void - this method returns nothing.
     */
	public void newPatient(View view) {
		Intent intent = new Intent(this, NewPatientActivity.class);
		intent.putExtra("usertype", usertype);
		intent.putExtra("username", username);
		startActivity(intent);
	}
	/** This method handles the case where a nurse wants to search for a patient.
     *
     * @param view - auto generated variable.
     * @return void - this method returns nothing.
     */
	public void updatePatient(View view) {
		Intent intent = new Intent(this, HCNSearchActivity.class);
		intent.putExtra("usertype", usertype);
		intent.putExtra("username", username);
		startActivity(intent);
	}
	/** This handles the case where a nurse wants to view the urgency list.
     *
     * @param view - auto generated variable
     * @return void - this method returns nothing.
     */
	public void urgencyList(View view) {
		Intent intent = new Intent(this, UrgencyListActivity.class);
		intent.putExtra("usertype", usertype);
		intent.putExtra("username", username);
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
