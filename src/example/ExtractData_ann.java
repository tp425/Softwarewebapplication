package example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Scanner;

public class ExtractData_ann {
	static int count=0;
	static double pred;
	static Connection connection = null;
	static Statement statement = null;
	static String driver = "com.mysql.jdbc.Driver";
	static int days_after_currentday;
	static String tick;
	
	static int countentries=0;
	static LinkedList<Double> valuesQueue= new LinkedList<Double>();
	
	
	public void extractdataann(String tick,int days_after_currentday) throws SQLException, IOException, ClassNotFoundException{
		
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
		String sql = "SELECT Date, Close FROM history WHERE Ticker_symbol='"+tick+"'ORDER BY Date ASC";

//		((java.sql.Statement) statement).executeUpdate(sql);
		ResultSet rs = statement.executeQuery(sql);
		BufferedWriter writer = new BufferedWriter(new FileWriter("rawTrainingData.csv"));
		//PrintWriter pw = new PrintWriter(new FileOutputStream(new File("persons.txt"),true));
		while(rs.next()){
	         //Retrieve by column name
	         String date  = rs.getString("Date");
	         double closeprice = rs.getDouble("Close");
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
}
