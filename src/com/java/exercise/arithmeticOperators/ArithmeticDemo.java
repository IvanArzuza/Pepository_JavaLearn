/** 
 * Principal Class - ArithmeticDemo
 * 
 */
package com.java.exercise.arithmeticOperators;
/**
 * @author IvanArzuza
 *
 */
public class ArithmeticDemo {
	/*
	 * @param args the commands line arguments
	 */	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int result = 1 + 2; //result = 3
		System.out.println("1 + 2 = " + result);
		int original_result = result;
		
		result = result - 1;//result = 2
		System.out.println(original_result + " - 1 = " + result);
		original_result = result;
		
		result = result * 2;//result = 4
		System.out.println(original_result + " * 2 = " + result);
		original_result = result;
		
		result = result / 2;//result = 2
		System.out.println(original_result + " / 2 = " + result);
		original_result = result;
		
		result = result + 8;//result = 10
		System.out.println(original_result + " + 8 = " + result);
		original_result = result;
		
		result = result % 7;//result = 3
		System.out.println(original_result + " % 7 = " + result);
		original_result = result;
	}
}