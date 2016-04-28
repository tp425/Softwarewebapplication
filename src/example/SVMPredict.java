package example;




import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import SVMlib.DataFileReader;
import SVMlib.KernelManager;
import SVMlib.LinearKernel;
import SVMlib.RBFKernel;
import SVMlib.svm_model;
import SVMlib.svm_parameter;
import SVMlib.Instance;
import SVMlib.SVMPredictor;
import SVMlib.Constants;



/**
 * Class which predicts using SVM, RSI and MACD
 * @author Saumya
 *
 */
public class SVMPredict {
	static String foldername = "C:\\Users\\Thara Philips\\Documents\\thara_docs\\Rutgers\\Software eng2\\svm\\SVM_data";
	
	public static int predict(ArrayList<Double> prices, String symbol){		
        System.out.println("************SVM PREDICTION*************");
		int vote=0;
		if(prices!=null && prices.size()>0){
			createTestFile(prices, symbol);
			svm_model model;
			try {
				//Setup parameters
				svm_parameter param = new svm_parameter();
				param.probability = 1;
				param.gamma = 0.1;
				param.nu = 0.5;
				param.C = 1;
				param.svm_type = svm_parameter.C_SVC;       
				param.cache_size = 20000;
				param.eps = 0.001;
				
				//Register kernel function
				//KernelManager.setCustomKernel(new RBFKernel(param));
				 KernelManager.setCustomKernel(new LinearKernel());
				 // svm_parameter param = new svm_parameter(); 
				model = SVMPredictor.loadModel(foldername+"\\model\\"+symbol);
				Instance[] testingInstances = DataFileReader.readDataFile(foldername+"\\test\\"+symbol);
				//Instance[] testingInstances = DataFileReader.readDataFile(testfile);
				//Predict results
				//double[] pre= SVMPredictor.predict(testingInstances, model);
				double[] predictions = SVMPredictor.predict(testingInstances, model, true);
				//for(int i=0;i<predictions.length;i++)System.out.println(predictions[i]);
				vote= predictions[0] >0 ? vote+1 : vote-1;
				System.out.println("predictions[0] ..."+predictions[0] );
				if(predictions[0]>0)
				{
					System.out.println("BUY");
				}
				else
				{
					System.out.println("SELL");
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
						
				
		}
		
		System.out.println("vote..."+vote);
		return vote;
		
	}	
	private static void createTestFile(ArrayList<Double> prices, String symbol) {

		StringBuffer sb=new StringBuffer();
		int count=1;
		PrintWriter writer;
		try {
			File f=new File(foldername+"\\test\\"+symbol);
			f.createNewFile();
			writer = new PrintWriter(foldername+"\\test\\"+symbol, "UTF-8");
			for(int i=0;i<prices.size();i++){
				if(count<5){
					sb.append(" "+count+":"+prices.get(i));
					count++;
				}else{
//					if(prices.get(i)>=prices.get(i-1)){
//						sb.insert(0, "+1");
//					}else{
//						sb.insert(0, "-1");
//					}
					sb.insert(0, "+1");
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
