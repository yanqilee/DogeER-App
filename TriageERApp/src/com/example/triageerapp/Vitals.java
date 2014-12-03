package com.example.triageerapp;

/** Vitals is the class that handles (a) getting a patient's urgency level (points), and (b)
 *  getters and setters for their vitals.
 *
 * @author group_0861
 */
public class Vitals {
	
	private static String temp;
	private static String heartRate;
	private static String bloodS;
	private static String bloodD;
	
    /** This is the constructor for the Vitals class.
     *
     *  @param vTemp, vHeartRate, vBloodS, vBloodD - Strings representing the respective
     *  vitals.
     */
	public Vitals(String vTemp, String vHeartRate, String vBloodS, String vBloodD) {
		temp = vTemp;
		heartRate = vHeartRate;
		bloodS = vBloodS;
		bloodD = vBloodD;
	}
	/** This method gets the urgency level of a patient.
     *
     *  @param age - an integer representing a patients' age.
     *  @return urgency - their urgency score.
     */
	public int getUrgency(int age) {
		int urgency = 0;
		if (Double.parseDouble(temp) >= 39) {
			urgency += 1;
		}
		if (Integer.parseInt(heartRate) >= 100 | Integer.parseInt(heartRate) <= 50) {
			urgency += 1;
		}
		if (Integer.parseInt(bloodS) >= 140 | Integer.parseInt(bloodD) >= 90) {
			urgency += 1;
		}
		if (age < 2) {
			urgency += 1;
		}
		return urgency;
	}
    /** Getter for temperature.
     *
     *  @temp - a string representation of a patients' temperature.
     */
	public String getTemp() {
		return temp;
	}
    /** Setter for temperature.
     * 
     *  @return void - this method returns nothing.
     */
	public void setTemp(String xTemp) {
		temp = xTemp;
	}
    /** Getter for heart rate.
     *
     *  @return heartRate - a string representation of a patients' heart rate.
     */
	public String getHeartRate() {
		return heartRate;
	}
    /** Setter for heart rate.
     * 
     *  @return void - this method returns nothing.
     */
	public void setHeartRate(String xHeartRate) {
		heartRate = xHeartRate;
	}
    /** Getter for sysolic blood pressure.
     *
     *  @return bloodS - a string representation of a patients' systolic blood pressure.
     */
	public String getBloodS() {
		return bloodS;
	}
    /** Setter for systolic blood pressure.
     *
     *  @return void - this method returns nothing.
     */
	public void setBloodS(String xBloodS) {
		bloodS = xBloodS;
	}
    /** Getter for diatolic blood pressure.
     *
     *  @return bloodS - a string representation of a patients' diatolic blood pressure.
     */
	public String getBloodD() {
		return bloodD;
	}
    /** Setter for diatolic blood pressure.
     *
     *  @return void - this method returns nothing.
     */
	public void setBloodD(String xBloodD) {
		bloodD = xBloodD;
	}
}
