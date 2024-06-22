package com.mydemo.mytest.custom.type;

import java.lang.annotation.Annotation;

public class PersonTest {

	public static void main(String[] args) {
		PersonTest p = new PersonTest();
		p.printPerson(new Person("kim", 20));
		p.printTrash(new Trash("park", 28));
	}
	
	public void printPerson(Person p) {
		Annotation[] annotations = Person.class.getDeclaredAnnotations();
		for(Annotation annotation : annotations) {
			if (annotation instanceof PersonInfo) {
				PersonInfo personInfo = (PersonInfo) annotation;
				System.out.println(p.getName() + "(" + p.getAge() + ") 가 말합니다 : " + personInfo.mention());
			}
		}
	}
	public void printTrash(Trash p) {
		Annotation[] annotations = Trash.class.getDeclaredAnnotations();
		for(Annotation annotation : annotations) {
			if (annotation instanceof PersonInfo) {
				PersonInfo personInfo = (PersonInfo) annotation;
				System.out.println(p.getName() + "(" + p.getAge() + ") 가 말합니다 : " + personInfo.mention());
			}
		}
	}
}
