package br.luan.sp.bares.guidebar.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//target para declarar o elemento metod 
@Target(ElementType.METHOD)
//acessar anotação e tempo de runtime 
@Retention(RetentionPolicy.RUNTIME)
public @interface Privado {
	
}
