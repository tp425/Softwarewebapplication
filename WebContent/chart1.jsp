<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.sql.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
  <script type="text/javascript">
    google.charts.load('current', {packages: ['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
      // Define the chart to be drawn.
      var data = new google.visualization.DataTable();
      data.addColumn('string', 'Element');
      data.addColumn('number', 'Percentage'); 
       
  		<% out.print("In chart");   
  	   double[] expind=(double[]) request.getAttribute("indicator");
  	   String[] date_label=(String[]) request.getAttribute("label");
  	   int len=expind.length; 
  	   int lenlabel=date_label.length;
  	%>
  	
  	data.addRows([
  	            ['Nitrogen', 0.78],
  	            ['Oxygen', 0.21],
  	            ['Other', 0.01]
  	          ]); 
       <%--data.addRows([
                     <%
                     for(int i=0; i<date_label.length; i++){%>
                        ['<%=date_label[i]%>', <%=expind[i]%>]
                     
                     
     						 <%}%>
   
   						 ]); --%>
    	  
		
      // Instantiate and draw the chart.
      var chart = new google.visualization.LineChart(document.getElementById('myPieChart'));
      chart.draw(data, null);
    }
  </script>
</head>
<body>
<!-- Identify where the chart should be drawn. -->

<div id="myPieChart"/>
</body>
</html>