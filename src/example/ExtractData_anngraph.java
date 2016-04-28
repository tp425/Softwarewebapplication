package example;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class ExtractData_anngraph {
	static int count=0;
	static double pred;
	static Connection connection = null;
	static Statement statement = null;
	static String driver = "com.mysql.jdbc.Driver";
	static int days_after_currentday;
	static String tick;
	
	static int countentries=0;
	static LinkedList<Double> valuesQueue= new LinkedList<Double>();
	public static double price=0;// Latest price from database
	
	
	public void extractdataann(String tick) throws SQLException, IOException, ClassNotFoundException{
		
//	Scanner in= new Scanner(System.in);
//	System.out.println("Enter the ticker of the stock");
//	 tick=in.next();
//	 System.out.println("Enter how many days from today");
//	 days_after_currentday=in.nextInt();
	 //File f=new File("rawTestingData.csv");
     Class.forName(driver);
	 connection = DriverManager.getConnection("jdbc:mysql://localhost/stockhistory", "root", "root");
		statement =  connection.createStatement();
//		String sql = "SELECT Date, Close FROM History WHERE Ticker_symbol='"+tick+"'ORDER BY Date DESC LIMIT 5";
		String sql = "SELECT Time, Price FROM realtime WHERE Ticker_symbol='"+tick+"' ORDER BY Time DESC LIMIT 500" ;

//		((java.sql.Statement) statement).executeUpdate(sql);
		ResultSet rs = statement.executeQuery(sql);
		BufferedWriter writer = new BufferedWriter(new FileWriter("rawTrainingDatarealtime.csv"));
		//PrintWriter pw = new PrintWriter(new FileOutputStream(new File("persons.txt"),true));
		while(rs.next()){
	         //Retrieve by column name
	         String date  = rs.getString("Time");
	         double closeprice = rs.getDouble("Price");
//	         PrintWriter writer = new PrintWriter("rawTrainingData.csv");
	         writer.write(date+","+closeprice);
				writer.newLine();
            	//writer.append(date+","+closeprice);
            	
	         //Display values
	         System.out.print("Date: " + date);
	         System.out.print(", Close: " + closeprice+"\n");
	         
	         
	      }
	      rs.close();
	      writer.close();

}
	
	public String timeann(String tick) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
		 Connection conn = null;
		 Statement stmt = null;
		 String date = null ;
		 
		 
		Class.forName("com.mysql.jdbc.Driver").newInstance(); 
		//conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database_name", "root", "");
		
		conn = DriverManager.getConnection("jdbc:mysql://localhost/stockhistory", "root", "root");
		stmt = (Statement) conn.createStatement();
		System.out.println("in after connection");			
		String query = "SELECT Time, Price FROM realtime WHERE Ticker_symbol='"+tick+"' ORDER BY Time DESC LIMIT 1" ;	
		Statement st=conn.createStatement();
		ResultSet rsnew = st.executeQuery(query);
		while(rsnew.next()){
	         date  = rsnew.getString("Time");
	         price  = rsnew.getDouble("Price");
	         System.out.println("Current time "+ date);
	         System.out.println("Current price "+ price);
		}
			 rsnew.close();
			 
		     
		return date;
	}
	public String[] timearray(String date,int limit){
		// Splitting time from date
		ArrayList<String>time_list=new ArrayList<String>();
		 String[] time1=date.split(" ");
        	String time2=time1[1];					
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			Date startDate = null;			    
			
			try {
				startDate = sdf.parse(time2);
				System.out.println("Time "+sdf.format(startDate));
					Calendar cal = Calendar.getInstance();								
					cal.setTime(startDate);
					time_list.add(sdf.format(startDate).toString());
					for(int i=0;i<limit;i++){
					cal.add(Calendar.MINUTE, 1);
					 String newTime = sdf.format(cal.getTime());
					 System.out.println("New time: "+newTime);
					 time_list.add(newTime.toString());
					 }
					System.out.println("List time"+time_list );
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//converting time list to glabel 
		      String[] glabel = new String[time_list.size()];
		      for (int i=0; i < time_list.size(); i++)
		      {
		    	   glabel[i] = time_list.get(i);
//		    	 			          
		      }	
		      return glabel;
	}
	
}
