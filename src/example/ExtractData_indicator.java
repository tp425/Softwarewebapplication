package example;

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

	public class ExtractData_indicator {
		static Connection conn = null;
		static Statement stmt = null;
		public String[] stockvaldbarraydate;
		public int[] stockvaldbvolumearray;
		
		public int[] volume(){			
			return stockvaldbvolumearray;			
		}
		
		public String[] date_label(){
			for(int i = 0; i < stockvaldbarraydate.length / 2; i++)
			{
			    String temp = stockvaldbarraydate[i];
			    stockvaldbarraydate[i] = stockvaldbarraydate[stockvaldbarraydate.length - i - 1];
			    stockvaldbarraydate[stockvaldbarraydate.length - i - 1] = temp;
			}
			
			return stockvaldbarraydate;
			
		}

		public static void main(String[] args) {
			// TODO Auto-generated method stub

			//stockvaldb:data from database( taking 5 recent values)
			}
		public double[] Indicatorval(String ticker, String fromdate, String todate){

			ArrayList<Double> stockvaldbclose=new ArrayList<Double>();
			ArrayList<String> stockvaldbclosedate=new ArrayList<String>();
			ArrayList<Integer> stockvaldbvolume=new ArrayList<Integer>();
			double[] stockvaldbarrayclose = null;	
			
						
			System.out.println("in before connection");	
			try{	
				Class.forName("com.mysql.jdbc.Driver").newInstance(); 
				//conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database_name", "root", "");
				
				conn = DriverManager.getConnection("jdbc:mysql://localhost/stockhistory", "root", "root");
				stmt = (Statement) conn.createStatement();
				System.out.println("in after connection");			
				String query = "SELECT close,Date,Volume FROM History WHERE Ticker_symbol='"+ticker+"' "
						+ "AND Date >='"+ fromdate+"' AND Date<= '"+todate+"'  ";	
				Statement st=conn.createStatement();
				ResultSet rs = st.executeQuery(query);
				while (rs.next())
			     {
//					 
					 stockvaldbclose.add(rs.getDouble("close")); 
					 stockvaldbclosedate.add(rs.getString("Date")); 
					 stockvaldbvolume.add(rs.getInt("Volume"));
//					 
			     }
				 st.close();	
				 System.out.println("Stock value from database"+stockvaldbclose);
	
	              stockvaldbarrayclose = new double[stockvaldbclose.size()];
			      for (int i=0; i < stockvaldbarrayclose.length; i++)
			      {
			    	  stockvaldbarrayclose[i] = stockvaldbclose.get(i).doubleValue();
//			    	 			          
			      }	
			      stockvaldbarraydate = new String[stockvaldbclosedate.size()];
			      for (int i=0; i < stockvaldbarraydate.length; i++)
			      {
			    	  stockvaldbarraydate[i] = stockvaldbclosedate.get(i);
//			    	 			          
			      }	
			      stockvaldbvolumearray = new int[stockvaldbvolume.size()];
			      for (int i=0; i < stockvaldbvolumearray.length; i++)
			      {
			    	  stockvaldbvolumearray[i] = stockvaldbvolume.get(i);
//			    	 			          
			      }	
					 
			}catch(Exception e){
				e.printStackTrace();
				
			}
					

		return stockvaldbarrayclose;
		}
		
	}



