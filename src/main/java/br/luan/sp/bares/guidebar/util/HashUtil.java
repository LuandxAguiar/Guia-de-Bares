package br.luan.sp.bares.guidebar.util;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class HashUtil {
	public static String hash(String palavra) {
		//code usando o guava do mvn para gerar o hash na senha, ou criar um cod hexadecimal para "cripto" a senha "
		
		//"tempero" do hash
		String salt = "c@ch@c@";
		
		//add o "temp" a palavra
		palavra = salt + palavra ;
		
		//gerando o hash 
		String hash = Hashing.sha256().hashString(palavra, StandardCharsets.UTF_8).toString();
		
		return hash;
		
	}
}
