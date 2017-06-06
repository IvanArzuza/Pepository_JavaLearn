/**
 * 
 * Principal Class - ArrayDemo
 */
package com.java.exercise.languageBasic;
/**
 * 
 * @author IvanArzuza 
 */
public class ArrayDemo {
	/*
	 * @param args the commands line arguments
	 */
	public static void main (String[] args){
		// Declares an array of integer
		int[] v_array;
		int[] v_array2 = {1000,2000,3000,4000,5000}; // Declares and fill
		// Allocates memory for 10 integers
		v_array = new int[10];		
		// Fill the v_array		 
		for (int i = 0;i < v_array.length;i++){
			v_array[i] = 100*(i+1);
		}		
		// Write array´s fields
		for (int i = 0;i < v_array.length ;i++){
			System.out.println("(v_array)-> Element at index " + i + " : " + v_array[i]);
		}
		for (int i = 0;i < v_array2.length ;i++){
			System.out.println("(v_array2)-> Element at index " + i + " : " + v_array2[i]);
		}
	}
}