/**
 * 
 * Principal Class - MultiDimArrayDemo
 */
package com.java.exercise.languageBasic;
/**
 * 
 * @author IvanArzuza
 */
public class MultiDimArrayDemo {
	/*
	 * @param args the commands line arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[][] array_names = {
				{"Mr.","Mrs.","Ms."},
				{"Smith","Jones"}
		};
		// Mr. Smith
		System.out.println(array_names[0][0] + array_names[1][0]);
		// Ms. Jones
		System.out.println(array_names[0][2] + array_names[1][1]);
	}

}
