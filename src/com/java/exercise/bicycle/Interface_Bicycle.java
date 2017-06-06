/**
 * Interface Class Interface_Bicycle
 */
package com.java.exercise.bicycle;

/**
 * @author IvanArzuza
 *
 */
public interface Interface_Bicycle {
	
	//Wheel revolutions per minute
    void changeCadence(int newValue);
    void changeGear(int newValue);
    void speedUp(int increment);
    void applyBrakes(int decrement);
    void printStates_byInterface();
}
