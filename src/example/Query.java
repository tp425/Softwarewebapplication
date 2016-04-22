package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Query {
	static Connection conn = null;
	static Statement stmt = null;
	public ArrayList<String> tickername=new ArrayList<String>();
	public ArrayList<Double> stockvalue=new ArrayList<Double>();
	public ArrayList<String> date=new ArrayList<String>();
	
//	public void mainquery(String ticker,String pricetype,String days,String all){
//		
//		if(all.equals("all")){
//			switch (pricetype){
//			case "Latest" :
//	            query1(); 
//	            break;
//			case "Average" :
//				query1();
//			}
//		}
//		
//	}
	
	public void query1(String ticker,String stocktype, String datetype ){
		String flag = "Latest";
		
		double[] stockvaldbarrayclose = null;
		String query=null;
		String dateval=null;
//		if (datetype.equals("10 DAY")) dateval="10";
//		if (datetype.equals("1 MONTH"))dateval="30";
//		if (datetype.equals("1 YEAR"))dateval="365";
		try{	
			Class.forName("com.mysql.jdbc.Driver").newInstance(); 			
			conn = DriverManager.getConnection("jdbc:mysql://localhost/stockhistory", "root", "root");
			stmt = (Statement) conn.createStatement();
			System.out.println("in after connection");	
			
			if(ticker.equals("all")){
				if(stocktype.equals("Latest")){
					System.out.println("Latest all");
			       query = "SELECT Ticker_symbol,close,Date FROM history ORDER BY Date desc limit 10 ";
				}
				else if(stocktype.equals("Average")){
					System.out.println("Average all");
					query="SELECT DISTINCT(Ticker_symbol),AVG(Close) AS AVERAGE_PRICE ,Date FROM history "
							+ "WHERE  Date BETWEEN DATE_SUB(CURDATE(), INTERVAL "+datetype+") AND CURDATE() "
							+ "GROUP BY Ticker_symbol;";
				    flag="avg";
				}
				else if(stocktype.equals("Highest")){
					System.out.println("Highest all");
//					System.out.println("dateval : "+dateval);
					query="SELECT * FROM  history t1 JOIN ( SELECT max(Close) AS max_value,  Ticker_symbol  "
							+ " FROM history    WHERE Date BETWEEN DATE_SUB(CURDATE(), INTERVAL "+datetype+") AND "
							+ "CURDATE()  GROUP BY Ticker_symbol ) AS t2 ON t1.Ticker_symbol = t2.Ticker_symbol "
							+ "AND t1.Close = t2.max_value  where t1.Date BETWEEN DATE_SUB(CURDATE(), "
							+ "INTERVAL "+datetype+") AND CURDATE()";
					
					flag="high";
				}
				else if(stocktype.equals("Lowest")){
					System.out.println("Lowest all");					
					query="SELECT * FROM  history t1 JOIN ( SELECT min(Close) AS max_value,  Ticker_symbol  "
							+ " FROM history    WHERE Date BETWEEN DATE_SUB(CURDATE(), INTERVAL "+datetype+") AND "
							+ "CURDATE()  GROUP BY Ticker_symbol ) AS t2 ON t1.Ticker_symbol = t2.Ticker_symbol "
							+ "AND t1.Close = t2.max_value  where t1.Date BETWEEN DATE_SUB(CURDATE(), "
							+ "INTERVAL "+datetype+") AND CURDATE()";
					
					flag="low";
				}
			}
			
			else{
				if(stocktype.equals("Latest")){
					System.out.println("HERE latest");
				 query = "SELECT Ticker_symbol,close,Date FROM history WHERE Ticker_symbol='"+ticker+"' ORDER BY Date desc limit 1 ";	
				}
				 else if(stocktype.equals("Average")){
					System.out.println("HERE AVERAGE");
				query="SELECT DISTINCT *,AVG(Close) AS AVERAGE_PRICE FROM history "
						+ "WHERE Ticker_symbol='"+ticker+"' AND Date BETWEEN DATE_SUB(CURDATE(), INTERVAL "+datetype+")"
						+ " AND CURDATE() ORDER BY Date desc";
				flag="avg";
				System.out.println(query);
				}
				 else if(stocktype.equals("Highest")){
					 System.out.println("HERE highest");
//						System.out.println("dateval : "+dateval);						
						query="  SELECT Close AS max_value ,  Ticker_symbol,Date   FROM history   "
								+ " WHERE Ticker_symbol='"+ticker+"' AND Date BETWEEN DATE_SUB(CURDATE(),"
										+ " INTERVAL "+datetype+") AND CURDATE() ORDER BY Close DESC LIMIT 1";
						flag="high";
					}
				 else if(stocktype.equals("Lowest")){
					 System.out.println("HERE lowest");
//						System.out.println("dateval : "+dateval);						
						query="  SELECT Close AS max_value ,  Ticker_symbol,Date   FROM history   "
								+ " WHERE Ticker_symbol='"+ticker+"' AND Date BETWEEN DATE_SUB(CURDATE(),"
										+ " INTERVAL "+datetype+") AND CURDATE() ORDER BY Close LIMIT 1";
						flag="low";
					}
				 else if(stocktype.equals("lessAverage")){
					 System.out.println("HERE lessAverage");												
						query=" SELECT `Ticker_symbol`, avg(`Close`) AVERAGE_PRICE FROM history "
								+ "GROUP BY `Ticker_symbol` HAVING AVERAGE_PRICE< (SELECT MIN(`Close`)MIN_PRICE "
								+ "FROM history WHERE `Ticker_symbol`='"+ticker+"' AND `Date` BETWEEN DATE_SUB"
								+ "(CURDATE(),INTERVAL "+datetype+") AND CURDATE() HAVING AVERAGE_PRICE)AND "
										+ "Ticker_symbol NOT IN ('"+ticker+"') ";
						flag="lessavg";
					}
		}
			Statement st=conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next())
		     { 
				if(flag.equals("avg")|| flag.equals("lessavg")){
					System.out.println("flag avg");
					tickername.add(rs.getString("Ticker_symbol")); 
					stockvalue.add(rs.getDouble("AVERAGE_PRICE")); 
				//	 System.out.println("AVERAGE_PRICE: "+stockvalue);
					//date.add(rs.getString("Date"));
				}
				else if(flag.equals("high")||flag.equals("low")){
					System.out.println("flag high");
					tickername.add(rs.getString("Ticker_symbol")); 
					stockvalue.add(rs.getDouble("max_value")); 
					 System.out.println("Max value: "+stockvalue);
					date.add(rs.getString("Date"));
				}
				else{ 
				System.out.println("flag null");
				tickername.add(rs.getString("Ticker_symbol")); 
				stockvalue.add(rs.getDouble("close")); 
				date.add(rs.getString("Date")); 
				 }
		     }
			 st.close();	
			 System.out.println("Ticker name: "+tickername);
			 System.out.println("Stock price: "+stockvalue);
			 System.out.println("Date: "+date);

//              stockvaldbarrayclose = new double[stockvaldbclose.size()];
//		      for (int i=0; i < stockvaldbarrayclose.length; i++)
//		      {
//		    	  stockvaldbarrayclose[i] = stockvaldbclose.get(i).doubleValue();
////		    	 			          
//		      }	
		}catch(Exception e){
			e.printStackTrace();
			
		}

}
	
}
