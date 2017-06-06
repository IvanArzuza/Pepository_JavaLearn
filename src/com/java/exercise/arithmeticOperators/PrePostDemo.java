/** 
 * Principal Class - ConcatDemo
 * 
 */
package com.java.exercise.arithmeticOperators;
/**
 * @author IvanArzuza
 *
 */
public class PrePostDemo {
	/*
	 * @param args the commands line arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i = 3;
        i++;        
        System.out.println(i);// prints 4
        
        ++i;
        System.out.println(i);// prints 5
                
        System.out.println(++i);// prints 6
        
        System.out.println(i++);// prints 6
        
        System.out.println(i);// prints 7
	}
}