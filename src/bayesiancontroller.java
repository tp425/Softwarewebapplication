
import example.Bayesian;
import java.util.Date;
import example.ExtractData;
import example.ExtractData_ann;
import example.ann;
import example.ex;
import java.io.IOException;
import java.util.ArrayList;

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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		try {
			startDate = sdf.parse(period);
			System.out.println("Date"+sdf.format(startDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Date date1 = new Date();
		System.out.println("current"+sdf.format(date1));
		int diff=startDate.getDate()-date1.getDate();
		System.out.println("Difference"+ diff );
		
		
		 //Call ExtractData that makes connection to database and return values which are given
		 //to bayesian
		ArrayList<Double> predictedval = null;
		double predictedval_ann = 0;
		String predicted_val=null;
		if(diff==1){ 
		ExtractData bay=new ExtractData();
		predictedval=bay.bayesianprediction(ticker,selection);	
		predicted_val = predictedval.toString()
			    .replace("[", "")  //remove the right bracket
			    .replace("]", "")  //remove the left bracket
			    .trim();
		}
		else{
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
        // dispatch it to bayesian.jsp
		RequestDispatcher dispatch;
		String shortprediction=null;
				
		
		dispatch = request.getRequestDispatcher("bayesiansubmit.jsp");
		if(diff==1){//bayesian
			shortprediction="For selected date "+period+" predicted value is" +predicted_val;
			request.setAttribute("shorttermprediction", shortprediction);
//			request.setAttribute("predictedval", predictedval);
			//request.setAttribute("selection", selection);
//			request.setAttribute("date", period);
		}else{//ANN
			System.out.println("In ann");
			shortprediction="For selected date "+period+" predicted value is" +predictedval_ann;
			request.setAttribute("shorttermprediction", shortprediction);
//			request.setAttribute("predictedval", predictedval_ann);
			//request.setAttribute("selection", selection);
//			request.setAttribute("date", period);
		}
		
		dispatch.forward(request, response);
	}
	
}