/**
 * Principal Class - InstanceofDemo
 */
package com.java.exercise.arithmeticOperators;
/**
 * @author IvanArzuza
 *
 */
public class InstanceofDemo {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Parent obj1 = new Parent();
		Parent obj2 = new Child();
		
		System.out.println("obj1 instanceof Parent: " + (obj1 instanceof Parent));
		System.out.println("obj1 instanceof child: " + (obj1 instanceof Child));
		System.out.println("obj1 instanceof MyInterface: " + (obj1 instanceof MyInterface));
		
		System.out.println("obj2 instanceof Parent: " + (obj2 instanceof Parent));
		System.out.println("obj2 instanceof child: " + (obj2 instanceof Child));
		System.out.println("obj2 instanceof MyInterface: " + (obj2 instanceof MyInterface));
	}
}

class Parent{}
class Child extends Parent implements MyInterface{}
interface MyInterface{}