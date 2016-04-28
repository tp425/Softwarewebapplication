<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.sql.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <%   
    double[] expind=(double[]) request.getAttribute("indicator");
    String[] date_label=(String[]) request.getAttribute("label");
    int len=expind.length; 
  //  int lenlabel=date_label.length;
 %>
  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
  <script type="text/javascript">
  
    google.charts.load('current', {packages: ['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
      // Define the chart to be drawn.
      <%--var data = new google.visualization.DataTable();
      data.addColumn('string', 'Element',{ role: "style" });
      data.addColumn('number', 'Price',{ role: "style" }); 	--%>	
  		

      var data = google.visualization.arrayToDataTable([
		['Element', 'Volume', { role: 'style' }],          
	<%
	for (int i=0;i<expind.length-1;i++){
    //Retrieve by column name    
    String time2=date_label[i];
    //String time2="Date";
    double price = expind[i];
    if(expind[i]<expind[i+1]){
        %>    
    		 ['<%=time2%>',<%=price%>,"red" ],
    	 <%} else {%>
    		 ['<%=time2%>',<%=price%>,"blue"],
	 	<%}%>
	 <%}%> 
      ]); 
        var options = {
        		width: 700,
                height: 500,   	        
        	      
                title: 'Stock Price',
                titleTextStyle: {
                    color: 'FF0000',
                    fontName: 'Arial',
                    fontSize: 15
                  },
                curveType: 'function',
                legend: { position: 'bottom' }
        
              };
    	  
		
      // Instantiate and draw the chart.
      var chart = new google.visualization.BarChart(document.getElementById('myPieChart'));
      chart.draw(data, options);
    }
  </script>
</head>
<body>
<!-- Identify where the chart should be drawn. -->

<div id="myPieChart"/>
</body>
</html>