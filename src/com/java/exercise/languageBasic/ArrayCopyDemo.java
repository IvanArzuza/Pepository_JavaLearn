/**
 * 
 * Principal Class - ArrayCopyDemo
 */
package com.java.exercise.languageBasic;
/**
 * 
 * @author IvanArzuza
 */
public class ArrayCopyDemo {
	/*
	 * @param args the commands line arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char[] array_copyFrom = {'d','e','c','a','f','f','e','i','n','a','t','e','d'};
		char[] array_copyTo = new char[array_copyFrom.length - 6];
		// Copy the values from an array to other array
		System.arraycopy(array_copyFrom, 2, array_copyTo, 0, array_copyTo.length);
		// Print target array
		System.out.println(new String(array_copyTo));
	}
}