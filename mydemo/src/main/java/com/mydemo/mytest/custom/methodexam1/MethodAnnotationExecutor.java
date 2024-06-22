package com.mydemo.mytest.custom.methodexam1;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MethodAnnotationExecutor {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
        Method method = TheClass.class.getMethod("doThis");  //자바 리플렉션 getMethod로 메서드 doThis를 얻어온다
        Annotation[] annotations = method.getDeclaredAnnotations(); //메서드에 선언된 어노테이션 객체를 얻어온다

        for (Annotation annotation : annotations) {
            if (annotation instanceof MyAnnotation) {
                MyAnnotation myAnnotation = (MyAnnotation) annotation;
                System.out.println("name: " + myAnnotation.name()); //어노테이션에 지정한 값을 프린트한다

                System.out.println("value: " + myAnnotation.value());
            }
        }

        Annotation annotation = TheClass.class.getMethod("doThat") 
                            .getAnnotation(MyAnnotation.class); //메서드 doThat에 선언된 MyAnnotation의 어노테이션 객체를 얻어온다


        if (annotation instanceof MyAnnotation) {
            MyAnnotation myAnnotation = (MyAnnotation) annotation;
            System.out.println("name: " + myAnnotation.name());
            System.out.println("value: " + myAnnotation.value());
        }
	}

}
