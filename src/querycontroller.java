

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import example.Query;

/**
 * Servlet implementation class querycontroller
 */
@WebServlet("/querycontroller")
public class querycontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public querycontroller() {
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
		String ticker_val=null;
		String qticker=request.getParameter("qticker");
		System.out.println("Ticker "+qticker);
		String all_val=request.getParameter("all");
		System.out.println("all is "+all_val);
		
		String priceoption=request.getParameter("priceoption");
		System.out.println("Priceoption "+priceoption);
		String daterange=request.getParameter("daterange");
		System.out.println("daterange "+daterange);
		
		if(all_val==null){
			System.out.println("not all");
			ticker_val=qticker;
			}
		else {
			System.out.println("all");
			ticker_val=all_val;			
		}
		System.out.println(ticker_val);
		Query qq=new Query();
		
		qq.query1(ticker_val, priceoption,daterange );
		System.out.println("In controller"+qq.stockvalue);
		String stockvalues = qq.stockvalue.toString()
			    .replace("[", "")  //remove the right bracket
			    .replace("]", "")  //remove the left bracket
			    .trim(); 
		String datevalues = qq.date.toString()
			    .replace("[", "")  //remove the right bracket
			    .replace("]", "")  //remove the left bracket
			    .trim(); 
		String tickernames = qq.tickername.toString()
			    .replace("[", "")  //remove the right bracket
			    .replace("]", "")  //remove the left bracket
			    .trim();
		
		String hello="helo";
		if(priceoption.equals("Latest")){
			hello="You have selected to find Latest value of "+ ticker_val+ " ticker and value :"+stockvalues;
		}
		if(priceoption.equals("Highest")){
			hello="You have selected to find Highest value of "+ ticker_val+ " ticker for time period"+ daterange
					+ "and value :"+stockvalues+" on date " + datevalues;
		}
		if(priceoption.equals("Lowest")){
			hello="You have selected to find Lowest value of "+ ticker_val+ " ticker for time period"+ daterange
					+ "and value :"+stockvalues+" on date " + datevalues;
		}
		if(priceoption.equals("Average")){
			hello="You have selected to find Average value of "+ ticker_val+ " ticker for time period"+ daterange
					+ "and value :"+stockvalues;
		}
		if(priceoption.equals("lessAverage")){
			hello="You have selected to find tickers whose Average value is less than "+ ticker_val+ " "
					+ " for time period "+ daterange
					+ "and values are :"+ tickernames;
		}
		
		// dispatch it to bayesiansubmit.jsp
		
		RequestDispatcher dispatch;
		dispatch = request.getRequestDispatcher("bayesiansubmit.jsp");
		System.out.println("despatch");
		request.setAttribute("resp",hello );		
		dispatch.forward(request, response);
		BufferedWriter outputWriter = null;
	    outputWriter = new BufferedWriter(new FileWriter("C:/Users/Thara Philips/workspace/Software/WebContent/ajaxout.txt"));	
	    outputWriter.write(hello);	    
	    System.out.println("wrote");
	    outputWriter.flush();
	    outputWriter.close();
	    
	    
		
	}

}
