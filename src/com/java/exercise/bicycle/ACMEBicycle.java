/**
 * ACMEBicycle Class
 * 
 */
package com.java.exercise.bicycle;

/**
 * @author IvanArzuza
 *
 */
public class ACMEBicycle implements Interface_Bicycle {
	 int cadence = 0;
	    int speed = 0;
	    int gear = 1;

	   // The compiler will now require that methods
	   // changeCadence, changeGear, speedUp, and applyBrakes
	   // all be implemented. Compilation will fail if those
	   // methods are missing from this class.
	    
	    public void changeCadence(int newValue) {
	         cadence = newValue;
	    }

	    public void changeGear(int newValue) {
	         gear = newValue;
	    }

	    public void speedUp(int increment) {
	         speed = speed + increment;   
	    }

	    public void applyBrakes(int decrement) {
	         speed = speed - decrement;
	    }

	    void printStates() {
	         System.out.println("cadence:" + cadence + " speed:" + speed + " gear:" + gear);
	    }
	    
	    public void printStates_byInterface() {
	         System.out.println("By Interfaces *cadence:" + cadence + " *speed:" + speed + " *gear:" + gear);
	    }
}
