package com.gowtham;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataSetDownloader {

	static String input[] = {
		"yyyy-01-31",	
		"yyyy-02-zz",
		"yyyy-03-31",
		"yyyy-04-30",
		"yyyy-05-31",	
		"yyyy-06-30",
		"yyyy-07-31",
		"yyyy-08-31",
		"yyyy-09-30",	
		"yyyy-10-31",
		"yyyy-11-30",
		"yyyy-12-31",
	};
	
	static String URL_STR = "https://www.imf.org/external/np/fin/data/rms_mth.aspx?SelectDate=###input###&reportType=CVSDR&tsvflag=Y";
	
	public static void main(String[] args) {
		System.out.println();
		for(int i=1995; i< 2017; i++) {
			for(int j=0; j<12; j++) {
				String year = null;
				if(i%4 == 0) {
					year = input[j].replaceAll("yyyy", ""+i).replaceAll("zz", "29");
				} else {
					year = input[j].replaceAll("yyyy", ""+i).replaceAll("zz", "28");
				}
				System.out.println(year);
				try {
					downloadDataSet(year);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		for(int j=0; j<10; j++) {
			String year = input[j].replaceAll("yyyy", "2017").replaceAll("zz", "28");
			System.out.println(year);
			try {
				downloadDataSet(year);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private static void downloadDataSet(String date) throws IOException {
		
		URL url = new URL(URL_STR.replace("###input###", date));
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.addRequestProperty("User-Agent", "Mozilla/4.0");
		conn.connect();
		InputStream is = null;
		if(conn.getResponseCode() == 200) {
			is = conn.getInputStream();
		} else {
			is = conn.getErrorStream();
		}
		PrintWriter out = new PrintWriter("dataset"+File.separator+date+".txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		while((line = br.readLine()) != null) {
			out.println(line);
		}
		out.flush();
		out.close();
		br.close();
		
	}

}
