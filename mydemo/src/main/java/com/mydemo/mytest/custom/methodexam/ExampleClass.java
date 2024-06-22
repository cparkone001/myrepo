package com.mydemo.mytest.custom.methodexam;

import java.lang.reflect.Method;

public class ExampleClass {

    @MethodInfo(author = "John", date = "2023-08-19", version = 2)
    public void annotatedMethod() {
        System.out.println("This is an annotated method.");
    }	
	
	public static void main(String[] args) {
        ExampleClass example = new ExampleClass();
        example.annotatedMethod();
        
        Class clazz = ExampleClass.class;
        Method method;
		try {
			method = clazz.getMethod("annotatedMethod");
	        if (method.isAnnotationPresent(MethodInfo.class)) {
	            MethodInfo annotation = method.getAnnotation(MethodInfo.class);
	            System.out.println("Author: " + annotation.author());
	            System.out.println("Date: " + annotation.date());
	            System.out.println("Version: " + annotation.version());
	        }    			
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
