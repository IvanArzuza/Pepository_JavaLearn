/**
 * 
 * Principal Class - DemoBicycle
 */
package com.java.exercise.bicycle;

/**
 * @author IvanArzuza
 *
 */
public class DemoBicycle {
	/*
	 * @param args the commands line arguments
	 */	
	public static void main(String[] args){
		
		// Code application Logic here
		//Create two different Bicycle objects
		Bicycle bike1 = new Bicycle();
		Bicycle bike2 = new Bicycle();
		Interface_Bicycle bike3 = new ACMEBicycle();
		
		//Invoke methods on those objects (bike1)		
		//Invoke methods on objects - bike1
		bike1.changeCadence(50);
		bike1.speedUp(10);
		bike1.changeGear(2);
		bike1.printStates();
		
		//Invoke methods on objects - bike2
		bike2.changeCadence(50);
		bike2.speedUp(10);
		bike2.changeGear(2);
		bike2.printStates();
		//Update bike2
		bike2.changeCadence(40);
		bike2.speedUp(10);
		bike2.changeGear(3);
		bike2.printStates();
		
		bike3.changeCadence(30);
		bike3.speedUp(30);
		bike3.changeGear(3);
		bike3.printStates_byInterface();
		
	}
	
}