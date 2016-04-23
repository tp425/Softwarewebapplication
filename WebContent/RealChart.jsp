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
    int lenlabel=date_label.length;
 %>
  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
  <script type="text/javascript">
  
    google.charts.load('current', {packages: ['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
      // Define the chart to be drawn.
      var data = new google.visualization.DataTable();
      data.addColumn('string', 'Element');
      data.addColumn('number', 'Price'); 		
  		

        data.addRows([
	<%
	for (int i=0;i<expind.length;i++){
    //Retrieve by column name    
    String time2=date_label[i];
    double price = expind[i];
    %>    
		 ['<%=time2%>',<%=price%> ],
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
      var chart = new google.visualization.LineChart(document.getElementById('myPieChart'));
      chart.draw(data, options);
    }
  </script>
</head>
<body>
<!-- Identify where the chart should be drawn. -->

<div id="myPieChart"/>
</body>
</html>