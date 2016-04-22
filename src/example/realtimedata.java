package example;


//import java.beans.Statement;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class  realtimedata {
	static Connection conn = null;
	static Statement stmt = null;
	static BufferedWriter writer = null;  

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		realtimedata realdata=new  realtimedata();
		JSONObject jsonObject=null;		
		try{	
			
			//try {
				while (true) {
					realdata.collectrealData("EBAY");
					if(jsonObject!=null){						
						System.out.println(jsonObject.toString());						
					}					
					Thread.sleep(60000);					
				}
				
				
		}catch(Exception e){
			e.printStackTrace();			
		}
		

	}
	
	public String collectrealData(String Ticker) throws JSONException {
		URL url;
		HttpURLConnection conn = null;
		JSONObject jsonObject=null;
		String Price = null;
		try {
			url = new URL("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22"+Ticker+"%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=");
//			url = new URL("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22YHOO%22%2C%22AMZN%22%2C%22GOOG%22%2C%22EBAY%22%2C%22BBY%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=");
			
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			StringBuilder sb = new StringBuilder();
			String inputStr;
			//Read response to StringBuilder object
			while ((inputStr = br.readLine()) != null)
				sb.append(inputStr);
			br.close();

		       System.out.println("Done");
			
			//Convert the String to JSON object
			jsonObject = new JSONObject(sb.toString());
			System.out.println(jsonObject.toString());
		} catch(Exception e){
			e.printStackTrace();
		}		
		System.out.println("before");
		
		try {
			
			JSONObject quotes = jsonObject.getJSONObject("query").getJSONObject("results").getJSONObject("quote");
			    Price=quotes.get("Ask").toString();
				//String date=quote.get("Date").toString();
				//String Volume=quote.get("AverageDailyVolume").toString();
				System.out.println(Ticker+" "+Price);
				
		//	}
			
			System.out.println("Got value");
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		
		return Price;
	}
}







