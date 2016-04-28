

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import example.ExpoMovingaverage;
import example.ExtractData_ann;
import example.ExtractData_anngraph;
import example.ExtractData_graph;
import example.ExtractData_indicator;
import example.ann;

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
		double[] graphval = null;
		String[] glabel = null;
		int futuretime=0;
		double predictedval_ann = 0;
		ArrayList<String>time_list=new ArrayList<String>();
		ArrayList<Double>anngraphval=new ArrayList<Double>(); 
				ExtractData_graph graph=new ExtractData_graph();
				if(dategraph!=null){
					graphval=graph.Indicatorval(gticker,gfromdate,gtodate);
					glabel=graph.date_label();
				}else if(fromtime.equals("n5")) {
					futuretime=5;
					ExtractData_anngraph annextract=new ExtractData_anngraph();
					try {
						annextract.extractdataann(gticker);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ann annpredict=new ann(5, "rawTrainingDatarealtime.csv");
					//annpredict.days_after_currentday=5;
					predictedval_ann=annpredict.ann_predictor(5);
					anngraphval=annpredict.prediction_value_list;						      
				      
						try {
							String currenttime=annextract.timeann(gticker);
							glabel=annextract.timearray(currenttime,5);
							
						} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
								| SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						anngraphval.add(0, ExtractData_anngraph.price);
						System.out.println("Price vale and predicted"+anngraphval);
						// converting ann predicted values to array
						graphval = new double[anngraphval.size()];					
					      for (int i=0; i < graphval.length; i++)
					      {
					    	  graphval[i] = anngraphval.get(i).doubleValue();
//					    	 			          
					      }	
						System.out.println("glabel with time"+glabel);
				      
				}
				else if(fromtime.equals("n10")){
					ExtractData_anngraph annextract=new ExtractData_anngraph();
					try {
						annextract.extractdataann(gticker);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ann annpredict=new ann(5, "rawTrainingDatarealtime.csv");
					//annpredict.days_after_currentday=10;
					predictedval_ann=annpredict.ann_predictor(10);
					anngraphval=annpredict.prediction_value_list;						      
				      
						try {
							String currenttime=annextract.timeann(gticker);
							glabel=annextract.timearray(currenttime,10);
							
						} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
								| SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						anngraphval.add(0, ExtractData_anngraph.price);
						System.out.println("Price vale and predicted"+anngraphval);
						// converting ann predicted values to array
						graphval = new double[anngraphval.size()];					
					      for (int i=0; i < graphval.length; i++)
					      {
					    	  graphval[i] = anngraphval.get(i).doubleValue();
//					    	 			          
					      }	
						System.out.println("glabel with time"+glabel);
				      
				}
				else if(fromtime.equals("n15")) {
					ExtractData_anngraph annextract=new ExtractData_anngraph();
					try {
						annextract.extractdataann(gticker);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ann annpredict=new ann(5, "rawTrainingData.csv");
					//annpredict.days_after_currentday=15;
					predictedval_ann=annpredict.ann_predictor(15);
					anngraphval=annpredict.prediction_value_list;						      
				      
						try {
							String currenttime=annextract.timeann(gticker);
							glabel=annextract.timearray(currenttime,15);
							
						} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
								| SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						anngraphval.add(0, ExtractData_anngraph.price);
						System.out.println("Price value and predicted"+anngraphval);
						// converting ann predicted values to array
						graphval = new double[anngraphval.size()];					
					      for (int i=0; i < graphval.length; i++)
					      {
					    	  graphval[i] = anngraphval.get(i).doubleValue();
//					    	 			          
					      }	
						System.out.println("glabel with time"+glabel);
				}
				else if(fromtime.equals("n20")) {
					ExtractData_anngraph annextract=new ExtractData_anngraph();
					try {
						annextract.extractdataann(gticker);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ann annpredict=new ann(5, "rawTrainingDatarealtime.csv");
					//annpredict.days_after_currentday=20;
					predictedval_ann=annpredict.ann_predictor(20);
					anngraphval=annpredict.prediction_value_list;						      
				      
						try {
							String currenttime=annextract.timeann(gticker);
							glabel=annextract.timearray(currenttime,20);
							
						} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
								| SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						anngraphval.add(0, ExtractData_anngraph.price);
						System.out.println("Price value and predicted"+anngraphval);
						// converting ann predicted values to array
						graphval = new double[anngraphval.size()];					
					      for (int i=0; i < graphval.length; i++)
					      {
					    	  graphval[i] = anngraphval.get(i).doubleValue();
//					    	 			          
					      }	
						System.out.println("glabel with time"+glabel);
				}
				else if(fromtime.equals("n25")) {
					ExtractData_anngraph annextract=new ExtractData_anngraph();
					try {
						annextract.extractdataann(gticker);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ann annpredict=new ann(5, "rawTrainingDatarealtime.csv");
//					annpredict.days_after_currentday=25;
					predictedval_ann=annpredict.ann_predictor(25);
					anngraphval=annpredict.prediction_value_list;						      
				      
						try {
							String currenttime=annextract.timeann(gticker);
							glabel=annextract.timearray(currenttime,25);
							
						} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
								| SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						anngraphval.add(0, ExtractData_anngraph.price);
						System.out.println("Price value and predicted"+anngraphval);
						// converting ann predicted values to array
						graphval = new double[anngraphval.size()];					
					      for (int i=0; i < graphval.length; i++)
					      {
					    	  graphval[i] = anngraphval.get(i).doubleValue();
//					    	 			          
					      }	
						System.out.println("glabel with time"+glabel);
				}
				else if(fromtime.equals("n30")) {
					ExtractData_anngraph annextract=new ExtractData_anngraph();
					try {
						annextract.extractdataann(gticker);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ann annpredict=new ann(5, "rawTrainingDatarealtime.csv");
					//annpredict.days_after_currentday=30;
					predictedval_ann=annpredict.ann_predictor(30);
					anngraphval=annpredict.prediction_value_list;						      
				      
						try {
							String currenttime=annextract.timeann(gticker);
							glabel=annextract.timearray(currenttime,30);
							
						} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
								| SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						anngraphval.add(0, ExtractData_anngraph.price);
						System.out.println("Price value and predicted"+anngraphval);
						// converting ann predicted values to array
						graphval = new double[anngraphval.size()];					
					      for (int i=0; i < graphval.length; i++)
					      {
					    	  graphval[i] = anngraphval.get(i).doubleValue();
//					    	 			          
					      }	
						System.out.println("glabel with time"+glabel);
				}
				else{
					System.out.println("else");
					 graphval=graph.Timegraphval(gticker,fromtime);				
					 glabel=graph.date_label();
				}
					 
				      
//					 String[] glabeltemp=null;
//					 String[] glabelres=null;
//					 for(int i=0;i<glabel.length;i++){
//						 glabeltemp= glabel[i].split(" ");
//						 glabelres[i]=glabeltemp[1];
//					 }
					
			//	for(int i=0;i<glabel.length;i++) System.out.println("Date"+glabel[i]+ "val"+ graphval[i]);
				
				RequestDispatcher dispatch;
				dispatch = request.getRequestDispatcher("RealChart.jsp");
				request.setAttribute("indicator", graphval);				
				request.setAttribute("label", glabel);
				System.out.println("send values to chart");
				
				dispatch.forward(request, response);
	}

}
