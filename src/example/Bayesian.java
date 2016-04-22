package example;


/**
 *  Bayesian Prediction
 *  Submitted by Thara Philipson
 *  Program takes 10 input values and predict the 11th value as output
 */
 

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.commons.math3.distribution.NormalDistribution;
import Jama.Matrix;
public class Bayesian {
	static int M=3;
	double alpha=0.005;//5*10^3
	double beta=11.1;
	int N;
	Matrix S;
 //Prediction value	
	static double x=11;
// Data set
	double[] xval;
	double[] tval;
	Matrix xvalM,tvalM;
	static double[] stockprice=new double[11];
	static double[] astockprice=new double[10];
	static double actualval;
	
	public Bayesian(double[] price){
	tval=price;
    N=tval.length;
    
// Data set of observation x	 
	 xval= new double[N];
	 for(int i=0;i<N;i++){
		 xval[i]=i;
	 }
//Creating Matrix for data set x and t xval and tval	 	 
	// Matrix xvalM= new Matrix(xval,N);
//	 Matrix tvalM=new Matrix(tval,N);	 
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan=new Scanner(System.in);
//		System.out.println("Enter the file location");
//		String csv=scan.nextLine();
//		
//		BufferedReader br = null;
//		String line = "";
//		String actual="";
//		String cvsSplitBy = ",";  
// //Reading data from csv file       
//		try {
//			br = new BufferedReader(new FileReader(csv));
////			   
//				for(int i=0;i<10;i++){
//                line=br.readLine();                
//			    // use comma as separator
//				String[] price = line.split(cvsSplitBy);				 
//				stockprice[i]=Double.parseDouble(price[1]);	
//			}


//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (br != null) {
//				try {
//					br.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//Creating object and passing stockprice value to constructor	
        double[]sprice={10,11,12,11,13,14,11,11,11,11};
        stockprice=sprice;
 		Bayesian by=new Bayesian(stockprice);
		by.Predict();		

	}	
	
//Predict function
	public ArrayList<Double> Predict(){
		ArrayList<Double> meanlist=new ArrayList<Double>();
		ArrayList<Double> variancelist=new ArrayList<Double>();
		ArrayList<Double> predictionlist=new ArrayList<Double>();
	//	for (x=11;x<21;x++){		
		Matrix phiM= phi(x);
		Matrix phiTM=phiT(phiM);
		MatrixS(phiTM);		
		meanlist.add(Mean(phiTM));
		variancelist.add(Variance(phiM,phiTM));
//Normal distribution for predicting	
		NormalDistribution gaussian = new NormalDistribution(Mean(phiTM),Math.sqrt(Variance(phiM,phiTM)));
		double prediction = gaussian.sample();	
		predictionlist.add(prediction);		
//		}	
// printing the values Mean, Variance, Prediction		
			System.out.println("Mean: "+meanlist);
			System.out.println("Variance: "+variancelist);
			System.out.println("Predicted values: "+predictionlist);
//			System.out.println("Actual value: "+ actualval);
			
//			System.out.print("Actual values: [");
//			for(int k=0;k<astockprice.length;k++){
		//	System.out.print(astockprice[k]+",");
			//}
//			System.out.print("]");
		    return predictionlist;
	}
	
	
//Finding Vector Phi phi(x)=x^i
	public Matrix phi(double x){
		Matrix phi= new Matrix(M+1,1);
		for(int i=0;i<M+1;i++){
			phi.set(i,0,Math.pow(x,i) );
		}			
	return phi;	
		
	}
	
//Finding Vector PhiT PhiT=Transpose(phi(x))
	public Matrix phiT(Matrix phi){
		Matrix phiT=phi.transpose();
		return phiT;	
	}
	
// Finding Matrix S
    public Matrix MatrixS(Matrix phiT){		
	 Matrix I=Matrix.identity(M+1, M+1);
	 Matrix Result=new Matrix(M+1,M+1);
	 I=I.times(alpha);
	 for(int j=0;j<N-1;j++){
	 	Result.plusEquals(phi(xval[j]).times(phiT));
	 }  
	 Result= Result.times(beta);
	 S= (I.plusEquals(Result)).inverse();
	 return S;	
  }

// Mean
  public  double Mean(Matrix phiTM){
	Matrix MeanMatrix;
	double mean;
	MeanMatrix= phiTM.times(S).times(beta);	
	Matrix sum=phi(xval[0]).times(tval[0]);
	for(int j=1;j<N-1;j++){
		sum.plusEquals(phi(xval[j]).times(tval[j]));
	}
	MeanMatrix=MeanMatrix.times(sum);
	mean=MeanMatrix.get(0, 0);
	return mean;	  
  }
  
  
  
//Variance
 public double Variance(Matrix phiM,Matrix phiTM){
	 Matrix VarMatrix;
	 double variance;
	 VarMatrix=phiTM.times(S).times(phiM);		 
	 variance=(1/beta)+VarMatrix.get(0, 0);
     return variance;
	  
 } 
  
// Printing Matrix  
  static public void printMatrix(Matrix m){
      double[][] d = m.getArray();

      for(int row = 0; row < d.length; row++){
          for(int col = 0; col < d[row].length; col++){
              System.out.printf("%6.4f\t", m.get(row, col));
          }
          System.out.println();
      }
      System.out.println();
  }
  
  public void printing(){
	  System.out.println("In bayesian");
  }
  
}
