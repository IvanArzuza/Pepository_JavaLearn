/**
 * Principal Class - ConditionalDemo2
 */
package com.java.exercise.arithmeticOperators;
/**
 * @author IvanArzuza
 *
 */
public class ConditionalDemo2 {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int value1 = 1;
		int value2 = 2;
		int result;
		boolean someCondition = true;
		//boolean someCondition = false;
		result = someCondition ? value1:value2; //If someCondition is true, assign the value of value1 to result. Otherwise, assign the value of value2 to result.
		System.out.println("Condition is \"true\": " + result);
		result = (value1 == value2) ? value1:value2;
		System.out.println("Condition is \"false\": " + result);
	}
}