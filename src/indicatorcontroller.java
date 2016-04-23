

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import example.ExpoMovingaverage;
import example.ExtractData_indicator;

/**
 * Servlet implementation class indicatorcontroller
 */
@WebServlet("/indicatorcontroller")
public class indicatorcontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public indicatorcontroller() {
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
		String fromdate=request.getParameter("fromdate");
		System.out.println("fromdate "+fromdate);
		String todate=request.getParameter("todate");
		System.out.println("todate "+todate);
		String indicator=request.getParameter("indicator");
		System.out.println("Indicator "+indicator);
		String iticker=request.getParameter("iticker");
		System.out.println("Ticker "+iticker);
		        
				ExtractData_indicator ind=new ExtractData_indicator();
				ExpoMovingaverage indavg=new ExpoMovingaverage();
				double[] indival=ind.Indicatorval(iticker,fromdate,todate);
				//String[] label= ind.stockvaldbarraydate;
				String[] label=ind.date_label();
				
				
				double[] indivalres=null;
				if(indicator.equals("sma")){
					indivalres=indavg.myAverage(indival, 3);
				}else if (indicator.equals("ema")){
					indivalres=indavg.myEMAverage(indival, 3);
				}
				for(int i=0;i<label.length;i++) System.out.println("Date"+label[i]);
		        
		       // double[] expind={90,15,20,10,15};//[65, 59, 80, 81, 56, 55, 40]
		// dispatch it to bayesian.jsp
				RequestDispatcher dispatch;
				dispatch = request.getRequestDispatcher("RealChart.jsp");
				request.setAttribute("indicator", indivalres);
				String[] label_temp = Arrays.copyOfRange(label, 2, label.length);
				request.setAttribute("label", label_temp);
				
				System.out.println("end");
				dispatch.forward(request, response);
	}

}
