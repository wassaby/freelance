package com.rs.vio.webapp.test.trash;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GoogleMaps {
	private final String USER_AGENT = "Mozilla/5.0";
	 
	public static void main(String[] args) throws Exception {
 
		GoogleMaps http = new GoogleMaps();
 
		System.out.println("Testing 1 - Send Http GET request");
		http.sendGet();
 
	}
 
	// HTTP GET request
	private void sendGet() throws Exception {
		String charset = java.nio.charset.StandardCharsets.UTF_8.name();
		String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng=43.2397335302487,76.9486504700752&sensor=false&language=ru";
 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestProperty("Accept-Charset", charset);
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL: " + url);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
 
	}
}
