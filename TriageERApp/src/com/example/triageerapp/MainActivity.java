package com.example.triageerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/** MainActivity is the class which handles the starter screen of the app.
 *  
 *  
 * @author group_0861
 */
public class MainActivity extends Activity {

	@Override
	/** Create a splash page that last for 1 second, then brings user to LoginActivity page.
	 * 
	 * @param savedInstanceState - auto generated variable.
	 * @return void - this method returns nothing. 
	 * 
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		final Intent intent = new Intent(this, LoginActivity.class);
		Thread logoTimer = new Thread() {
            public void run(){
                try{
                    int logoTimer = 0;
                    while(logoTimer < 1000){
                        sleep(100);
                        logoTimer = logoTimer +100;
                    };
                    startActivity(intent);
                }
                 
                catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                 
                finally{
                    finish();
                }
            }
        };
         
        logoTimer.start();
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
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
