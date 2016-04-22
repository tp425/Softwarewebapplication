

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import example.realtimedata;

/**
 * Servlet implementation class realdatacontroller
 */
@WebServlet("/realdatacontroller")
public class realdatacontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public realdatacontroller() {
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
		String Ticker="GOOG";
		String price;
		ArrayList<String> pricelist=new ArrayList<String>();
		while (true) {
			
		
		realtimedata realD=new realtimedata();
		try {
		    price=realD.collectrealData(Ticker);
		    
		    if(pricelist.size()!=5){
		    	pricelist.add(price);	
		    }else {
		    	pricelist.remove(0);
		    	pricelist.add(price);
		    }	    
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher dispatch;
		dispatch = request.getRequestDispatcher("bayesiansubmit.jsp");
		request.setAttribute("realprice", pricelist);
		System.out.println(pricelist);
		
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dispatch.forward(request, response);
		}
		
	}
	

}
