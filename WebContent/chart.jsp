<!DOCTYPE HTML>
<html>

<head></head>

<body>
<canvas id="c" width="500" height="500"></canvas>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/1.0.2/Chart.min.js"></script>
  
  
<% out.print("In chart");   
   double[] expind=(double[]) request.getAttribute("indicator");
   String[] date_label=(String[]) request.getAttribute("label");
   int len=expind.length; 
   int lenlabel=date_label.length;
%>    
   <script>
    var ctx = document.getElementById("c").getContext("2d");      
   
    var data = {
      labels: [0, 0, 0, 0, , , , , , ],
      datasets: [{
        label: "My First dataset",
        fillColor: "rgba(220,220,220,0.2)",
        strokeColor: "rgba(220,220,220,1)",
        pointColor: "rgba(220,220,220,1)",
        pointStrokeColor: "#fff",
        pointHighlightFill: "#fff",
        pointHighlightStroke: "rgba(220,220,220,1)",
        data: [0, 0, 0, 0, 0,0,0,0,0,0]
      
      }]
    };
    var MyNewChart = new Chart(ctx).Line(data);
    var len = '<%=len%>';
    
    <%for(int i=0; i<expind.length; i++){ %>
    var ii= '<%=i %>';
    var expindi='<%=expind[i] %>';
    MyNewChart.datasets[0].points[ii].value = expindi; 
    <%}%>
    
    <%for(int i=2; i<date_label.length; i++){%>
    MyNewChart.scale.xLabels[<%=i-2%>] =['<%=date_label[i]%>'];    
   
    MyNewChart.update();
    <%}%>
    
  </script> 

</body>
