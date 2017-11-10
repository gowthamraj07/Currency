package com.gowtham;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Test3 {

	public static void main(String[] args) {
		String test = "January 02, 1995";
		SimpleDateFormat sdf= new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
		SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
		try {
			System.out.println(date.format(sdf.parse(test)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}

}
