package com.example.triageerapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/** LoginActivity is the class which handles nurse/physician
 *  login at each session.
 *
 * @author group_0861
 */

public class LoginActivity extends Activity {

	// consider Map<String, List<String>> adding nurse/physician attribute
	// or a different data structure you are comfortable with
	private Map<String, String[]> user;
	
	@Override
    /** Displaying the activity page on creation.
     *
     * @param saveInstanceState - auto generated variable.
     * @return void - this method returns nothing
     *
     */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		// To-do list:
		/** parse file, put into data structure with: username, password, usertype
		 *  note: we can also do this in the splash screen phase so that it won't re-parse
		 *  	  every time we press log out, but that can be copy and pasted over later
		 */
		try {
			loginInfo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/** This method reads the username/passwords (i.e. login info) from the
     *  passwords.txt file.
     *  @param this method takes no parameters.
     *  @return void - this method returns nothing
     *  @throws IOException - this is thrown if the file is not found
     */
	public void loginInfo() throws IOException {
		File f = new File(getFilesDir()+File.separator+"passwords.txt");
		String read;
		String[] tempArr;
		String[] tempList = {"1", "2"};
		
		user = new TreeMap<String, String[]>();
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
		while((read = bufferedReader.readLine()) != null) {
			tempArr = read.split(",");
			tempList[0] = tempArr.clone()[1];
			tempList[1] = tempArr.clone()[2];
			user.put(tempArr[0], tempList.clone());
		}
		bufferedReader.close();
	}

	@Override
    /** Displaying the activity page on creation.
     *
     * @param saveInstanceState - auto generated variable.
     * @return void - this method returns nothing
     *
     */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	/** This method actually handles confirming that the login info is correct
     *  and matches the passwords.txt file info.
     *  
     *  @param view - auto generated variable.
     *  @return void - this method returns nothing.
     */
	public void login(View view) {
		Intent intent = new Intent(this, HCNSearchActivity.class);
		Intent intent2 = new Intent(this, NurseMenuActivity.class);
		// To-do list:
		/** 
		 * - check if username in data, if not -> prompt user
		 * - if username exist, check password match
		 * - successful -> pass intent(I'm thinking passing both username and usertype
		 *   so that every time something changed, we can say which user changed it(change log)
		 *  
		*/
		EditText nameText = (EditText) findViewById(R.id.editText1);
		String username = nameText.getText().toString();
		EditText passText = (EditText) findViewById(R.id.editText2);
		String password = passText.getText().toString();
		
		Context context = getApplicationContext();
		CharSequence text = "Username or password is invalid.";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		
		String[] tempList = {"1", "2"};
		
		if (!user.containsKey(username)) {
			toast.show();
		} else {
			tempList = user.get(username);
			if (password.matches(tempList[0])) {
				intent.putExtra("usertype", tempList[1]);
				intent.putExtra("username", username);
				intent2.putExtra("usertype", tempList[1]);
				intent2.putExtra("username", username);
				if (tempList[1].matches("physician")) {
					startActivity(intent);				
				} else {
					startActivity(intent2);
				}
			} else {
				toast.show();
			}
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
