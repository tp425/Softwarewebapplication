package example;
/**
 * Extract data for bayesian prediction
 */

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ExtractData {
	static Connection conn = null;
	static Statement stmt = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//stockvaldb:data from database( taking 5 recent values)
		}
	public ArrayList<Double> bayesianprediction(String ticker, String selection){
		ArrayList<Double> stockvaldb=new ArrayList<Double>();
		ArrayList<Double> stockvaldbclose=new ArrayList<Double>();
		ArrayList<Double> stockvaldbhigh=new ArrayList<Double>();
		ArrayList<Double> stockvaldblow=new ArrayList<Double>();
		ArrayList<Double> stockvaldbvolume=new ArrayList<Double>();
		
		ArrayList<Double> res=new  ArrayList<Double>();		
			
		System.out.println("in before connection");	
		try{	
			Class.forName("com.mysql.jdbc.Driver").newInstance(); 
			//conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database_name", "root", "");
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost/stockhistory", "root", "root");
			stmt = (Statement) conn.createStatement();
			System.out.println("in after connection");			
			String query = "SELECT open,close,high,low,volume FROM History WHERE Ticker_symbol='"+ticker+"' order by Date desc limit 5";	
			Statement st=conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next())
		     {
				 stockvaldb.add(rs.getDouble("open")); 
				 stockvaldbclose.add(rs.getDouble("close")); 
				 stockvaldbhigh.add(rs.getDouble("high")); 
				 stockvaldblow.add(rs.getDouble("low")); 
				 stockvaldbvolume.add(rs.getDouble("volume")); 
		     }
			 st.close();			 
//			 System.out.println("Stock value from database"+stockvaldb);
//			 System.out.println("Stock value from database"+stockvaldbclose);
//			 System.out.println("Stock value from database"+stockvaldbhigh);
//			 System.out.println("Stock value from database"+stockvaldblow);
//			 System.out.println("Stock value from database"+stockvaldbvolume);
			 
//Converting list of array 
			 double[] stockvaldbarray = new double[stockvaldb.size()];
		      for (int i=0; i < stockvaldbarray.length; i++)
		      {
		    	  stockvaldbarray[i] = stockvaldb.get(i).doubleValue();
		          
		      } 
             double[] stockvaldbarrayclose = new double[stockvaldb.size()];
		      for (int i=0; i < stockvaldbarrayclose.length; i++)
		      {
		    	  stockvaldbarrayclose[i] = stockvaldbclose.get(i).doubleValue();
		          
		      }
		      double[] stockvaldbarrayhigh = new double[stockvaldbhigh.size()];
		      for (int i=0; i < stockvaldbarrayhigh.length; i++)
		      {
		    	  stockvaldbarrayhigh[i] = stockvaldbhigh.get(i).doubleValue();
		          
		      }
		      double[] stockvaldbarraylow = new double[stockvaldblow.size()];
		      for (int i=0; i < stockvaldbarraylow.length; i++)
		      {
		    	  stockvaldbarraylow[i] = stockvaldblow.get(i).doubleValue();
		          
		      }
		      double[] stockvaldbarrayvolume = new double[stockvaldbvolume.size()];
		      for (int i=0; i < stockvaldbarrayvolume.length; i++)
		      {
		    	  stockvaldbarrayvolume[i] = stockvaldbvolume.get(i).doubleValue();
		          
		      }
	      
//Conversion end
		      
// calling bayesian prediction
		     //     System.out.println("sel value in file "+selection);
		     if(selection.equals("open"))
		     {
		    	 Bayesian bay=new Bayesian(stockvaldbarray);			 
				 res=bay.Predict();			 			 
				 System.out.println("Predicted value for open price"+ res);	 
		     }
		     if(selection.equals("close"))
		     {
		    	 Bayesian bay=new Bayesian(stockvaldbarrayclose);			 
				 res=bay.Predict();			 			 
				 System.out.println("Predicted value for close price"+ res);
		     }
		     if(selection.equals("high"))
		     {
		    	 Bayesian bay=new Bayesian(stockvaldbarrayhigh);			 
				 res=bay.Predict();			 			 
				 System.out.println("Predicted value for high price"+ res);
		     }
		     if(selection.equals("low"))
		     {
		    	 
		    	 Bayesian bay=new Bayesian(stockvaldbarraylow);			 
				 res=bay.Predict();			 			 
				 System.out.println("Predicted value for low price"+ res);
		     }
		     if(selection.equals("volume"))
		     {
		    	 Bayesian bay=new Bayesian(stockvaldbarrayvolume);			 
				 res=bay.Predict();			 			 
				 System.out.println("Predicted value for volume"+ res);
		     } 		     
				 
		}catch(Exception e){
			e.printStackTrace();
			
		}
				

	return res;
	}
	
}

