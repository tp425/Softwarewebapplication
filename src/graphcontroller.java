

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import example.ExpoMovingaverage;
import example.ExtractData_indicator;

/**
 * Servlet implementation class graphcontroller
 */
@WebServlet("/graphcontroller")
public class graphcontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public graphcontroller() {
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
		String gfromdate=request.getParameter("gfromdate");
		System.out.println("gfromdate "+gfromdate);
		String gtodate=request.getParameter("gtodate");
		System.out.println("gtodate "+gtodate);
		String gindicator=request.getParameter("gindicator");
		System.out.println("gIndicator "+gindicator);
		String gticker=request.getParameter("gticker");
		System.out.println("gTicker "+gticker);
		        
				ExtractData_indicator graph=new ExtractData_indicator();				
				double[] graphval=graph.Indicatorval(gticker,gfromdate,gtodate);
				String[] glabel=graph.date_label();
				for(int i=0;i<glabel.length;i++) System.out.println("Date"+glabel[i]+ "val"+ graphval[i]);
				
				RequestDispatcher dispatch;
				dispatch = request.getRequestDispatcher("chart.jsp");
				request.setAttribute("indicator", graphval);				
				request.setAttribute("label", glabel);
				
				dispatch.forward(request, response);
	}

}
