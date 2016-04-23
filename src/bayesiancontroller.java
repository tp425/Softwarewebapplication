
import example.Bayesian;
import java.util.Date;
import example.ExtractData;
import example.ExtractData_ann;
import example.ann;
import example.ex;
import example.svmTrain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Jama.Matrix;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Servlet implementation class bayesiancontroller
 */
@WebServlet("/bayesiancontroller")
public class bayesiancontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public bayesiancontroller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	
		//String ticker="EBAY";
		String ticker=request.getParameter("ticker");
		System.out.println("Ticker "+ticker);
		//String selection=request.getParameter("selection");
		String selection="close";
		System.out.println("selection "+selection);
		String period=request.getParameter("dateperiod");
		System.out.println("period "+period);
		
		//Converting period to Date and find number of days
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		Calendar c1 = Calendar.getInstance();
	    Calendar c2 = Calendar.getInstance();    
		
		try {
			startDate = sdf.parse(period);
			System.out.println("Date"+sdf.format(startDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Date date1 = new Date();		
		System.out.println("current"+sdf.format(date1));
		
		c1.setTime(date1);
	    c2.setTime(startDate);
	    int diff = 0;
	    if (c1.before(c2)) {
	        diff = countDiffDay(c1, c2);
	      } else {
	        diff = countDiffDay(c2, c1);
	      }
		System.out.println("Difference days"+ diff);
		
		 //Call ExtractData that makes connection to database and return values which are given
		 //to bayesian
		int vote=0;
		ArrayList<Double> predictedval = null;
		double predictedval_ann = 0;
		String predicted_val=null;
		
		//Depending of number of days call Bayesian, ANN, SVM
		if(diff==1){ //Bayes
		ExtractData bay=new ExtractData();
		predictedval=bay.bayesianprediction(ticker,selection);	
		predicted_val = predictedval.toString()
			    .replace("[", "")  //remove the right bracket
			    .replace("]", "")  //remove the left bracket
			    .trim();
		}
		else if(diff>1 && diff<5){ //ANN
			ExtractData_ann annextract=new ExtractData_ann();
			try {
				annextract.extractdataann(ticker, diff);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ann annpredict=new ann(5, "rawTrainingData.csv");
			predictedval_ann=annpredict.ann_predictor();
			
		}
		else if(diff>=5){//SVM
			svmTrain svm=new svmTrain();
			svm.symbol="AAPL";
			try {
				
				vote=svm.svmprediction();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        // dispatch it to bayesian.jsp
		RequestDispatcher dispatch;
		String shortprediction=null;
				
		
		dispatch = request.getRequestDispatcher("bayesiansubmit.jsp");
		if(diff==1){ //bayesian
			shortprediction="For selected date "+period+" predicted value is" +predicted_val;
			request.setAttribute("shorttermprediction", shortprediction);
//			request.setAttribute("predictedval", predictedval);
			//request.setAttribute("selection", selection);
//			request.setAttribute("date", period);
		}else if(diff>1 && diff<5){ //ANN
			System.out.println("In ann");
			shortprediction="For selected date "+period+" predicted value is" +predictedval_ann;
			request.setAttribute("shorttermprediction", shortprediction);
//			request.setAttribute("predictedval", predictedval_ann);
			//request.setAttribute("selection", selection);
//			request.setAttribute("date", period);
		}
		else if(diff>=5){ //SVM
			System.out.println("In SVM");
			if(vote==1)
			shortprediction="For selected date "+period+" prediction is +1 so **BUY or HOLD**";
			else shortprediction="For selected date "+period+" prediction is -1 so **SELL **";
			request.setAttribute("shorttermprediction", shortprediction);
		}
		
		dispatch.forward(request, response);
	}
	public static int countDiffDay(Calendar c1, Calendar c2) {
	    int returnInt = 0;
	    while (!c1.after(c2)) {
	      c1.add(Calendar.DAY_OF_MONTH, 1);
	      returnInt++;
	    }

	    if (returnInt > 0) {
	      returnInt = returnInt ;
	    }

	    return (returnInt);
	  }
	
}