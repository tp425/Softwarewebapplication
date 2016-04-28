package example;


import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.ws.RespectBindingFeature;

import SVMlib.svm_node;
import SVMlib.svm_problem;
//import ca.uwo.csd.ai.nlp.libsvm.svm_node;
import SVMlib.Instance;
import SVMlib.KernelManager;
import SVMlib.LinearKernel;
import SVMlib.RBFKernel;
import SVMlib.SVMPredictor;
import SVMlib.SVMTrainer;
import SVMlib.svm_model;
import SVMlib.svm_parameter;
import SVMlib.Constants;
//import ca.uwo.csd.ai.nlp.libsvm.svm_problem;
import SVMlib.DataFileReader;



/**
 * Train svm for the tickers
 * 
 */
public class svmTrain {
	static ArrayList<Double> prices=new ArrayList();
	public static String[] symbols;
	 static String symbol;
	
    static String foldername = "C:\\Users\\Thara Philips\\Documents\\thara_docs\\Rutgers\\Software eng2\\svm\\SVM_data";
	public static void createModels() throws IOException, ClassNotFoundException {
		System.out.println("in create model");
		//*String[] symbols=new String[]{"YHOO","AMZN","GOOG","BBY","EBAY","LNKD","MSFT","FB","TWTR","AAPL"};		
	//	symbols=new String[]{"GOOG"};
	//	for (String symbol : symbols) {
		System.out.println("symbol name "+ symbol);
			String trainFileName=foldername+"\\Training\\"+symbol;
			System.out.println("Path "+trainFileName);
			//Read training file
		Instance[] trainingInstances = DataFileReader.readDataFile(trainFileName);             


			//Setup parameters
			svm_parameter param = new svm_parameter();
			param.probability = 1;
			param.gamma = 0.2;
			param.nu = 0.5;
			param.C = 1;
			param.svm_type = svm_parameter.C_SVC;       
			param.cache_size = 20000;
			param.eps = 0.001;

			//Register kernel function
			KernelManager.setCustomKernel(new RBFKernel(param));   

			//Train the model
			System.out.println("Training started...");
			svm_model model = SVMTrainer.train(trainingInstances, param);
			System.out.println("Training completed.");

			//Save the trained model
			SVMTrainer.saveModel(model,foldername+"\\Model\\"+symbol);
			//model = SVMPredictor.load_model("a1a.model");

//	}
	}

	private static void writeOutputs(String outputFileName, double[] predictions) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));
		for (double p : predictions) {
			writer.write(String.format("%.0f\n", p));
		}
		writer.close();
	}

//	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {    
//
//		createInstances();
//		createModels();   
//		SVMPredict sp= new SVMPredict();
//		for (String symbol : symbols) {
//			System.out.println(prices);
//		int vote=sp.predict(prices,symbol );
//		System.out.println(vote);
//		}
//	}
	public int svmprediction( String symbol) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException{
		System.out.println("In SVM prediction");
		this.symbol=symbol;
		int vote=0;
		createInstances();
		createModels();   
		SVMPredict prediction= new SVMPredict();
		//for (String symbol : symbols) {
			System.out.println(prices);	
			 vote=prediction.predict(prices,symbol );
	//	}
		
		return vote;
		
	}

	private static void createInstances() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		System.out.println("In CreateInstances");		
		Connection conn = null;
		Statement stmt = null;
	//	for (String symbol : symbols) {
			try{				
				Class.forName("com.mysql.jdbc.Driver").newInstance(); 
				conn = DriverManager.getConnection("jdbc:mysql://localhost/stockhistory", "root", "root");
				stmt = (Statement) conn.createStatement();
				System.out.println("hmm");
				String sql = " SELECT Open FROM History WHERE Ticker_symbol= '"+symbol+"' ORDER BY date Desc LIMIT 1501";				
				System.out.println(sql);
				ResultSet rs = stmt.executeQuery(sql);	
				System.out.println("after");			
				System.out.println("got");
				while(rs.next()){
					//Retrieve by column name
					Double price  = rs.getDouble("Open");
					prices.add(price);
				}
				createFile(prices,symbol);
				rs.close();
			}catch(SQLException se){
			}finally{
				try {
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}	
	//	}

	}

	private static void createFile(ArrayList<Double> prices, String symbol) {

		StringBuffer sb=new StringBuffer();
		int count=1;
		PrintWriter writer;
		try {
//			File f=new File(Constants.foldername+"\\Training\\"+symbol);
			File f=new File(foldername+"\\Training\\"+symbol);
//			 FileWriter writer = null;
			f.createNewFile();
			
		//writer = new FileWriter(f);
			writer = new PrintWriter(foldername+"\\Training\\"+symbol, "UTF-8");
			for(int i=0;i<prices.size();i++){
				if(count<=5){
					sb.append(" "+count+":"+prices.get(i));
					count++;
				}else{
					if(prices.get(i)>=prices.get(i-1)){
						sb.insert(0, "+1");
					}else{
						sb.insert(0, "-1");
					}
					count=1;
					//System.out.println(sb.toString());
					writer.println(sb.toString());
					sb.delete(0, sb.length());
				}
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}