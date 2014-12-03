package com.example.triageerapp;

import java.io.Serializable;

/** Patient is the class which handles a Patient objects info.
 * 
 * @author group_0861
 */
public class Patient implements Serializable {

	/**
	 * 
	 */
	private String name;
	private String DOB;
	private String HCNumber;
	private static final long serialVersionUID = 1L;
	
	/** This is the constructor for Patient.
	 * 
	 * @param patientName - a string which refers to a patients name.
	 * @param patientNumber - a string which refers to a patients healthcard number.
	 * @param birthDate - a string which refers to a patients dob. 
	 * 
	 */
	public Patient(String patientName, String patientNumber, String birthDate){
		name = patientName;
		DOB = birthDate;
		HCNumber = patientNumber;
	}
	
	public int getAge() {
		String curTime = SystemER.getDate();
		Integer age = new Integer(0);
		String[] curDate = curTime.split("-");
		String[] birthDate = this.DOB.split("-");
		age = Math.abs(Integer.parseInt(curDate[0]) - Integer.parseInt(birthDate[0]));
		// bit-wise comparator
		if ((Integer.parseInt(curDate[1]) < Integer.parseInt(birthDate[1])) |
			((Integer.parseInt(curDate[1]) == Integer.parseInt(birthDate[1])) &
			(Integer.parseInt(curDate[2]) == Integer.parseInt(birthDate[2])))) {
			age -= 1;
		}
		return age;
	}
	
	/** This method is a getter for the patients name. 
	 * 
	 * @param this method takes no inputs.
	 * @return name - the name of the patient for which this method is implemented. 
	 * 
	 */
	public String getName(){
		return name;
	}
	
	/** This method is a setter for the patients name.
	 * 
	 * @param Patientname - the new name of some patient.
	 * @return this method returns nothing.
	 * 
	 */
	public void setName(String Patientname){
		name = Patientname;
	}
	
	/** This method is a getter for a patient's dob.
	 * 
	 * @param this method takes no inputs.
	 * @return DOB - a string representation of some patients dob.
	 * 
	 */
	public String getDOB(){
		return DOB;
	}
	
	/** This method is a setter for a patient's dob.
	 * 
	 * @param birthdate - the new dob of some patient.
	 * @return this method returns nothing.
	 * 
	 */
	public void setDOB(String birthdate){
		DOB = birthdate;
	}
	
	/** This method is a getter for a patient's health card number.
	 * 
	 * @param this method takes no inputs.
	 * @return HCNumber - the health card number of some patient.
	 * 
	 */
	public String getHCNumber(){
		return HCNumber;
	}
	
	/** This method is a setter for a patient's health card number.
	 * 
	 * @param Patientnumber - the new health card number for some patient.
	 * @return this method returns nothing.
	 * 
	 */
	public void setHCNumber(String Patientnumber){
		HCNumber = Patientnumber;
	}
}

