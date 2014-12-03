package com.example.triageerapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.app.Activity;

/** SystemER is the class which handles generating the list of all patients
 *  as well as some helpers for getting the date/time.
 *
 * @author group_0861
 */

public class SystemER extends Activity{
	
    /** This method generates a list of the patients in the ER (referncing them by their
     *  healthcard number).
     *
     *  @param this method takes no inputs.
     *  @return healthList - a list of string representation of all patients' health card numbers.
     *  @throws IOException - thrown if the patient_records.txt file is not found.
     */
	public List<String> hcnList() throws IOException {
		String read = "";
		File f = new File(getFilesDir()+File.separator+"patient_records.txt");
		String[] tempArr;
		List<String> healthList = new ArrayList<String>();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
		
		while((read = bufferedReader.readLine()) != null) {
			tempArr = read.split(",");
			healthList.add(tempArr[0]);
		}
		bufferedReader.close();

		return healthList;
	}
	
	/** This method returns the date at the time the method is invoked.
	 * 
	 * @param - this method takes no inputs.
	 * @return - this method returns a String representing the date when the method was invoked.
	 * 
	 */
	public static String getDate() {
		Calendar calendar = Calendar.getInstance();
		Integer year = new Integer(calendar.get(Calendar.YEAR));
		String strYear = year.toString();
		Integer month = new Integer(calendar.get(Calendar.MONTH));
		String strMonth = month.toString();
		Integer day = new Integer(calendar.get(Calendar.DAY_OF_MONTH));
		String strDay = day.toString();
		String date = strYear + "-" + strMonth + "-" + strDay;
		return date;
	}
	
	/** This method returns the time at the time that the method is invoked.
	 * 
	 * @param - this method takes no inputs.
	 * @return - this method returns a String representing the time at the time that the method was invoked.
	 * 
	 */
	public static String getTime() {
		Calendar calendar = Calendar.getInstance();
		Integer hour = new Integer(calendar.get(Calendar.HOUR));
		String strHour = hour.toString();
		Integer minute = new Integer(calendar.get(Calendar.MINUTE));
		String strMinute = minute.toString();
		Integer second = new Integer(calendar.get(Calendar.SECOND));
		String strSecond = second.toString();
		String time = strHour + ":" + strMinute + ":" + strSecond;
		return time;
	}
}
