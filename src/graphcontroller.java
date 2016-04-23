

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import example.ExpoMovingaverage;
import example.ExtractData_graph;
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
		String dategraph=request.getParameter("Dategraph");
		System.out.println("date: "+dategraph);
		String timegraph=request.getParameter("Timegraph");
		System.out.println("time: "+timegraph);
		String fromtime=request.getParameter("fromtime");
		System.out.println("fromtime: "+fromtime);
		double[] graphval;
		String[] glabel;       
				ExtractData_graph graph=new ExtractData_graph();
				if(dategraph!=null){
					graphval=graph.Indicatorval(gticker,gfromdate,gtodate);
					glabel=graph.date_label();
				}else
					 graphval=graph.Timegraphval(gticker,fromtime);
					 glabel=graph.date_label();
//					 String[] glabeltemp=null;
//					 String[] glabelres=null;
//					 for(int i=0;i<glabel.length;i++){
//						 glabeltemp= glabel[i].split(" ");
//						 glabelres[i]=glabeltemp[1];
//					 }
					
				for(int i=0;i<glabel.length;i++) System.out.println("Date"+glabel[i]+ "val"+ graphval[i]);
				
				RequestDispatcher dispatch;
				dispatch = request.getRequestDispatcher("RealChart.jsp");
				request.setAttribute("indicator", graphval);				
				request.setAttribute("label", glabel);
				System.out.println("send values to chart");
				
				dispatch.forward(request, response);
	}

}
