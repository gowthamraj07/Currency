package com.gowtham;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Test2 {
	
	private static List<String> DATE_SET = new LinkedList<String>();
	private static final String DIR_NAME = "dataset";
	
	private static Set<String> ERROR_FILE_SET = new TreeSet<String>();
	
	private static String FILE_NAME = "output.txt";
	
	private static PrintWriter out = null;
	
	static {
		try {
			out = new PrintWriter(FILE_NAME);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if(out == null) {
			out = new PrintWriter(System.out);
		}
		readFolder(DIR_NAME);
		out.flush();
		out.close();
	}
	
	private static void readFolder(String folderName) {
		File dir = new File(folderName);
		if(dir.isDirectory()) {
			File files[] = dir.listFiles();
			for(File f : files) {
				try {
					readFile(f.getAbsolutePath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		for(String str: ERROR_FILE_SET) {
			System.out.println(str);
		}
	}
	
	private static void readFile(String fileName) throws IOException {
		FILE_NAME = fileName;
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
		String line = null;
		while((line = br.readLine()) != null) {
			if(isValid(line)) {
				if(isHeader(line)) {
					DATE_SET.clear();
					processHeader(line);
				} else {
					processContent(line);
				}
			}
		}
		
		br.close();
	}
	
	private static void processHeader(String line) {
		String headers[] = line.split("\t");
		for(String str  :headers) {
			DATE_SET.add(str);
		}
		
	}
	
	private static void processContent(String line) {
		String data[] = line.split("\t");
		for(int i=1; i<data.length;i++) {
			String tempStr = data[0]+";"+DATE_SET.get(i)+";"+data[i];
			//if(tempStr.length()>100) {
				//ERROR_FILE_SET.add(FILE_NAME);
				out.println(tempStr);
			//}
		}
	}
	
	private static boolean isValid(String line) {
		if(line.startsWith("Currency units per")
				|| line.startsWith("Notes:")
				|| line.startsWith("The value of the U.S")
				|| line.startsWith("These rates are the official")
				|| line.startsWith("The value in terms of")
				|| line.startsWith("Exchange rates are")
				|| line.trim().length() == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	private static boolean isHeader(String line) {
		return line.startsWith("Currency	");
	}

}
