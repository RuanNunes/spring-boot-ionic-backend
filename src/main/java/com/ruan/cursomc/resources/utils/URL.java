package com.ruan.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {
	public static List<Integer> decodeIntList(String s){
		// forma manual
//		String [] vet = s.split(",");
//		List<Integer> list = new ArrayList<>();
//		for (int i = 0; i < vet.length; i++) {
//			list.add(Integer.parseInt(vet[i]));
//		}
		
		//Forma abstrata utilizando lambda
		return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
	
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
}
